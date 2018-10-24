-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Procedure `pi_pos_industry`.`v_gstr_one_invoice`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `pi_pos_industry`.`v_gstr_one_invoice` ;
DROP TABLE IF EXISTS `pi_pos_industry`.`v_gstr_one_invoice`;

create view pi_pos_industry.v_gstr_one_invoice as
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
        pi_pos_industry.v_receipt_li_item) txn_item_tax
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
            txn_inv.invoice_no
    FROM
        pi_pos_industry.txn_master txn
    LEFT JOIN pi_pos_industry.sale_txn_invoices txn_inv ON txn.location_id = txn_inv.location_id
        AND txn.business_date = txn_inv.business_date
        AND txn.register = txn_inv.register
        AND txn.txn_no = txn_inv.txn_no
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
        pi_pos_industry.txn_master txn
    LEFT JOIN pi_pos_industry.txn_customer txn_cust ON txn.location_id = txn_cust.location_id
        AND txn.business_date = txn_cust.business_date
        AND txn.register = txn_cust.register
        AND txn.txn_no = txn_cust.txn_no
    LEFT JOIN pi_pos_industry.customer cust ON cust.customer_id = txn_cust.customer_id
        AND txn_cust.customer_type = 'CUSTOMER'
    WHERE
        txn.txn_type = 'SALE') cust_dtl ON txn_dtl.txn_no = cust_dtl.txn_no
        AND txn_dtl.business_date = cust_dtl.business_date
        AND txn_dtl.register = cust_dtl.register
        AND txn_dtl.location_id = cust_dtl.location_id;

-- -----------------------------------------------------
-- Procedure `pi_pos_industry`.`v_gstr_one_hsn`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `pi_pos_industry`.`v_gstr_one_hsn` ;
DROP TABLE IF EXISTS `pi_pos_industry`.`v_gstr_one_hsn`;

create view pi_pos_industry.v_gstr_one_hsn as 
SELECT 
    location_id, business_date, hsn_no, name, sum(qty) qty, sum(extended_amount) extended_amount, 
    sum(discount_amount) discount_amount, (SUM(`v_receipt_li_item`.`extended_amount`) - SUM(`v_receipt_li_item`.`discount_amount`)) AS `taxable_amount`,
    sum(tax_amount) tax_amount, sum(gross_amount) gross_amount, sum(SGST_amount) SGST_amount,sum(CGST_amount) CGST_amount,
    sum(IGST_amount) IGST_amount
FROM
    pi_pos_industry.v_receipt_li_item
        group by location_id, business_date, hsn_no, name
ORDER BY location_id ASC, business_date ASC, hsn_no ASC;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

