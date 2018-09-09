-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- View `pi_pos_industry`.`v_location_sales_data`
-- -----------------------------------------------------
DROP VIEW IF EXISTS pi_pos_industry.v_txn_lookup ;
DROP TABLE IF EXISTS pi_pos_industry.v_txn_lookup;

create view pi_pos_industry.v_txn_lookup as 
SELECT 
	concat(LPAD(txn_ma.location_id,4,'0'),LPAD(txn_ma.register,3,'0'),LPAD(txn_ma.txn_no,4,'0'),DATE_FORMAT(txn_ma.business_date, '%d%m%y')) as unique_txn,
    txn_inv.invoice_no as invoice_no,
    txn_ma.business_date as business_date,
    txn_ma.txn_type as txn_type,
    cust.name as customer_name,
    txn_ma.created_by as created_by,
    txn_ma.tax_total as tax_total,
    txn_ma.total as total_amount
FROM
    pi_pos_industry.txn_master txn_ma,
    pi_pos_industry.txn_customer txn_cust,
    pi_pos_industry.customer cust,
    pi_pos_industry.sale_txn_invoices txn_inv
WHERE
    txn_ma.location_id = txn_inv.location_id
        AND txn_ma.business_date = txn_inv.business_date
        AND txn_ma.register = txn_inv.register
        AND txn_ma.txn_no = txn_inv.txn_no
        AND txn_ma.location_id = txn_cust.location_id
        AND txn_ma.business_date = txn_cust.business_date
        AND txn_ma.register = txn_cust.register
        AND txn_ma.txn_no = txn_cust.txn_no
        AND txn_cust.customer_id = cust.customer_id
        AND txn_cust.customer_type = 'CUSTOMER';               
            
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

