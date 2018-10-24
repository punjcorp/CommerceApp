-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Procedure `commercedb`.`v_gstr_one_invoice`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_gstr_one_invoice` ;
DROP TABLE IF EXISTS `commercedb`.`v_gstr_one_invoice`;

create view commercedb.v_gstr_one_invoice as
SELECT 
    txn_dtl.*,
    tax_dtl.rate,
    tax_dtl.taxable_amount,
    tax_dtl.sgst_amount,
    tax_dtl.cgst_amount,
    tax_dtl.igst_amount,
    cust_dtl.customer_id,
    cust_dtl.name,
    cust_dtl.gst_no
FROM
    (SELECT 
        location_id,
            business_date,
            register,
            txn_no,
            total_s_rate AS rate,
            SUM(extended_amount) AS taxable_amount,
            SUM(SGST_amount) AS sgst_amount,
            SUM(CGST_amount) AS cgst_amount,
            SUM(IGST_amount) AS igst_amount
    FROM
        (SELECT 
        *,
            IF(IGST_percentage IS NULL, (SGST_percentage + CGST_percentage), IGST_percentage) AS total_s_rate
    FROM
        commercedb.v_receipt_li_item) txn_item_tax
    GROUP BY location_id , business_date , register , txn_no , total_s_rate) tax_dtl
        JOIN
    (SELECT 
        txn.location_id,
            txn.business_date,
            txn.register,
            txn.txn_no,
            txn.created_date,
            txn.subtotal,
            txn.tax_total,
            txn.total,
            concat(lpad(txn.location_id, 4, '0'),lpad(txn.register, 3, '0'),lpad(txn.txn_no, 5, '0'),date_format(txn.business_date, '%d%m%Y'))
            as invoice_no
    FROM
        commercedb.txn_master txn
    WHERE
        txn.txn_type = 'SALE') txn_dtl ON txn_dtl.txn_no = tax_dtl.txn_no
        AND txn_dtl.business_date = tax_dtl.business_date
        AND txn_dtl.register = tax_dtl.register
        AND txn_dtl.location_id = tax_dtl.location_id
        JOIN
    (SELECT 
        txn.location_id,
            txn.business_date,
            txn.register,
            txn.txn_no,
            txn.created_date,
            txn_cust.customer_id,
            cust.name,
            cust.gst_no
    FROM
        commercedb.txn_master txn
    LEFT JOIN commercedb.txn_customer txn_cust ON txn.location_id = txn_cust.location_id
        AND txn.business_date = txn_cust.business_date
        AND txn.register = txn_cust.register
        AND txn.txn_no = txn_cust.txn_no
    LEFT JOIN commercedb.customer cust ON cust.customer_id = txn_cust.customer_id
        AND txn_cust.customer_type = 'CUSTOMER'
    WHERE
        txn.txn_type = 'SALE') cust_dtl ON txn_dtl.txn_no = cust_dtl.txn_no
        AND txn_dtl.business_date = cust_dtl.business_date
        AND txn_dtl.register = cust_dtl.register
        AND txn_dtl.location_id = cust_dtl.location_id;

-- -----------------------------------------------------
-- Procedure `commercedb`.`v_gstr_one_hsn`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_gstr_one_hsn` ;
DROP TABLE IF EXISTS `commercedb`.`v_gstr_one_hsn`;

create view commercedb.v_gstr_one_hsn as 
SELECT 
    location_id, business_date, hsn_no, name, sum(qty) qty, sum(extended_amount) extended_amount, sum(discount_amount) discount_amount, (sum(extended_amount)- sum(discount_amount)) taxable_amount, sum(tax_amount) tax_amount, sum(gross_amount) gross_amount,
    sum(SGST_amount) SGST_amount,sum(CGST_amount) CGST_amount,sum(IGST_amount) IGST_amount
FROM
    commercedb.v_receipt_li_item
        group by location_id, business_date, hsn_no, name
ORDER BY location_id ASC, business_date ASC, hsn_no ASC;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

