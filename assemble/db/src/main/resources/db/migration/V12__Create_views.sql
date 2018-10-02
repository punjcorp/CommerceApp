-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- View `commercedb`.`v_location_tax`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_location_tax` ;
DROP TABLE IF EXISTS `commercedb`.`v_location_tax`;
USE `commercedb`;
CREATE  
VIEW `commercedb`.`v_location_tax` AS
    SELECT 
        `tlm`.`location_id` AS `location_id`,
        `tl`.`code` AS `billing_location`,
        `tg`.`tax_group_id` AS `tax_group_id`,
        `tg`.`name` AS `tax_group_name`,
        `tg`.`description` AS `tax_group_desc`,
        `tgr`.`seq_no` AS `tax_group_rate_seq`,
        `tgr`.`name` AS `tax_group_rate_name`,
        `tgr`.`description` AS `tax_group_rate_desc`,
        `tgr`.`compound_flag` AS `compound_flag`,
        `tgr`.`type_code` AS `type_code`,
        `trr`.`tax_rate_rule_id` AS `tax_rate_rule_id`,
        `trr`.`seq_no` AS `seq_no`,
        `trr`.`effective_date` AS `effective_Date`,
        `trr`.`expiry_date` AS `expiry_date`,
        `trr`.`percentage` AS `percentage`,
        `trr`.`amount` AS `amount`
    FROM
        ((((`tax_location_mapping` `tlm`
        JOIN `tax_location` `tl`)
        JOIN `tax_group` `tg`)
        JOIN `tax_group_rule` `tgr`)
        JOIN `tax_rate_rule` `trr`)
    WHERE
        ((`tl`.`tax_location_id` = `tlm`.`tax_location_id`)
            AND (`tg`.`tax_group_id` = `tgr`.`tax_group_id`)
            AND (`tlm`.`tax_location_id` = `tgr`.`tax_location_id`)
            AND (`tg`.`tax_group_id` = `trr`.`tax_group_id`)
            AND (`tlm`.`tax_location_id` = `trr`.`tax_location_id`)
            AND (`tgr`.`tax_group_id` = `trr`.`tax_group_id`)
            AND (`tgr`.`tax_location_id` = `trr`.`tax_location_id`)
            AND (`tgr`.`tax_authority_id` = `trr`.`tax_authority_id`)
            AND (`tgr`.`seq_no` = `trr`.`tax_group_rule_seq`))
    ORDER BY `tg`.`name` , `tgr`.`name` , `tgr`.`seq_no` , `trr`.`seq_no`;



-- -----------------------------------------------------
-- View `commercedb`.`v_item_location_tax`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_item_location_tax` ;
DROP TABLE IF EXISTS `commercedb`.`v_item_location_tax`;
USE `commercedb`;
CREATE 
VIEW `commercedb`.`v_item_location_tax` AS
    SELECT 
        `itm`.`item_id` AS `item_id`,
        `itm`.`name` AS `name`,
        `itm`.`long_desc` AS `long_desc`,
        `itmopt`.`unit_cost` AS `base_unit_cost`,
        `taxdtl`.`location_id` AS `location_id`,
        `taxdtl`.`tax_group_id` AS `tax_group_id`,
        `taxdtl`.`tax_group_name` AS `tax_group_name`,
        `taxdtl`.`sgst_rate_rule_id` AS `sgst_rate_rule_id`,
        `taxdtl`.`sgst_rate` AS `sgst_rate`,
        `taxdtl`.`sgst_amount` AS `sgst_amount`,
        `taxdtl`.`sgst_code` AS `sgst_code`,
        `taxdtl`.`cgst_rate_rule_id` AS `cgst_rate_rule_id`,
        `taxdtl`.`cgst_rate` AS `cgst_rate`,
        `taxdtl`.`cgst_amount` AS `cgst_amount`,
        `taxdtl`.`cgst_code` AS `cgst_code`,
        `taxdtl`.`igst_rate_rule_id` AS `igst_rate_rule_id`,
        `taxdtl`.`igst_rate` AS `igst_rate`,
        `taxdtl`.`igst_amount` AS `igst_amount`,
        `taxdtl`.`igst_code` AS `igst_code`
    FROM
        ((`commercedb`.`item` `itm`
        LEFT JOIN `commercedb`.`item_options` `itmopt` ON ((`itm`.`item_id` = `itmopt`.`item_id`)))
        LEFT JOIN (SELECT 
            `vlt`.`location_id` AS `location_id`,
                `vlt`.`tax_group_id` AS `tax_group_id`,
                `vlt`.`tax_group_name` AS `tax_group_name`,
                MAX((CASE
                    WHEN (`vlt`.`type_code` = 'SGST') THEN `vlt`.`tax_rate_rule_id`
                END)) AS `sgst_rate_rule_id`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'SGST') THEN `vlt`.`percentage`
                END)) AS `sgst_rate`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'SGST') THEN `vlt`.`amount`
                END)) AS `sgst_amount`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'SGST') THEN `vlt`.`type_code`
                END)) AS `sgst_code`,
                MAX((CASE
                    WHEN (`vlt`.`type_code` = 'CGST') THEN `vlt`.`tax_rate_rule_id`
                END)) AS `cgst_rate_rule_id`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'CGST') THEN `vlt`.`percentage`
                END)) AS `cgst_rate`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'CGST') THEN `vlt`.`amount`
                END)) AS `cgst_amount`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'CGST') THEN `vlt`.`type_code`
                END)) AS `cgst_code`,
                MAX((CASE
                    WHEN (`vlt`.`type_code` = 'IGST') THEN `vlt`.`tax_rate_rule_id`
                END)) AS `igst_rate_rule_id`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'IGST') THEN `vlt`.`percentage`
                END)) AS `igst_rate`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'IGST') THEN `vlt`.`amount`
                END)) AS `igst_amount`,
                MAX((CASE
                    WHEN (`vlt`.`tax_group_rate_name` = 'IGST') THEN `vlt`.`type_code`
                END)) AS `igst_code`
        FROM
            `commercedb`.`v_location_tax` `vlt`
        GROUP BY `vlt`.`location_id` , `vlt`.`tax_group_id`) `taxdtl` ON ((`itmopt`.`tax_group_id` = `taxdtl`.`tax_group_id`)));


-- -----------------------------------------------------
-- View `commercedb`.`v_receipt_li_item`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_receipt_li_item` ;
DROP TABLE IF EXISTS `commercedb`.`v_receipt_li_item`;
USE `commercedb`;
CREATE 
VIEW `commercedb`.`v_receipt_li_item` AS
    SELECT 
        `itm`.`name` AS `name`,
        `itm`.`long_desc` AS `long_desc`,
        `li_itm`.`seq_no` AS `seq_no`,
        `li_itm`.`upc_no` AS `upc_no`,
        `itmopt`.`hsn_no` AS `hsn_no`,
        `li_itm`.`qty` AS `qty`,
        `li_itm`.`unit_price` AS `unit_price`,
        `li_itm`.`extended_amount` AS `extended_amount`,
        `li_itm`.`discount_amount` AS `discount_amount`,
        `li_itm`.`tax_amount` AS `tax_amount`,
        `li_itm`.`gross_amount` AS `gross_amount`,
        `tax_dtl`.`location_id` AS `location_id`,
        `tax_dtl`.`register` AS `register`,
        `tax_dtl`.`business_date` AS `business_date`,
        `tax_dtl`.`txn_no` AS `txn_no`,
        `tax_dtl`.`item_id` AS `item_id`,
        `tax_dtl`.`SGST_tax_group_id` AS `SGST_tax_group_id`,
        `tax_dtl`.`SGST_tax_rule_rate_id` AS `SGST_tax_rule_rate_id`,
        `tax_dtl`.`SGST_percentage` AS `SGST_percentage`,
        `tax_dtl`.`SGST_amount` AS `SGST_amount`,
        `tax_dtl`.`CGST_tax_group_id` AS `CGST_tax_group_id`,
        `tax_dtl`.`CGST_tax_rule_rate_id` AS `CGST_tax_rule_rate_id`,
        `tax_dtl`.`CGST_percentage` AS `CGST_percentage`,
        `tax_dtl`.`CGST_amount` AS `CGST_amount`,
        `tax_dtl`.`IGST_tax_group_id` AS `IGST_tax_group_id`,
        `tax_dtl`.`IGST_tax_rule_rate_id` AS `IGST_tax_rule_rate_id`,
        `tax_dtl`.`IGST_percentage` AS `IGST_percentage`,
        `tax_dtl`.`IGST_amount` AS `IGST_amount`
    FROM
        (((`commercedb`.`txn_li_item` `li_itm`
        JOIN `commercedb`.`item` `itm`)
        JOIN `commercedb`.`item_options` `itmopt`)
        JOIN (SELECT 
            `li_tax`.`location_id` AS `location_id`,
                `li_tax`.`register` AS `register`,
                `li_tax`.`business_date` AS `business_date`,
                `li_tax`.`txn_no` AS `txn_no`,
                `li_tax`.`item_id` AS `item_id`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'SGST') THEN `li_tax`.`tax_group_id`
                END)) AS `SGST_tax_group_id`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'SGST') THEN `li_tax`.`tax_rule_rate_id`
                END)) AS `SGST_tax_rule_rate_id`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'SGST') THEN `li_tax`.`tax_rule_percentage`
                END)) AS `SGST_percentage`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'SGST') THEN `li_tax`.`total_tax_amount`
                END)) AS `SGST_amount`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'CGST') THEN `li_tax`.`tax_group_id`
                END)) AS `CGST_tax_group_id`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'CGST') THEN `li_tax`.`tax_rule_rate_id`
                END)) AS `CGST_tax_rule_rate_id`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'CGST') THEN `li_tax`.`tax_rule_percentage`
                END)) AS `CGST_percentage`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'CGST') THEN `li_tax`.`total_tax_amount`
                END)) AS `CGST_amount`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'IGST') THEN `li_tax`.`tax_group_id`
                END)) AS `IGST_tax_group_id`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'IGST') THEN `li_tax`.`tax_rule_rate_id`
                END)) AS `IGST_tax_rule_rate_id`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'IGST') THEN `li_tax`.`tax_rule_percentage`
                END)) AS `IGST_percentage`,
                MAX((CASE
                    WHEN (`tax_rr`.`type_code` = 'IGST') THEN `li_tax`.`total_tax_amount`
                END)) AS `IGST_amount`
        FROM
            (`commercedb`.`txn_li_tax` `li_tax`
        JOIN `commercedb`.`tax_rate_rule` `tax_rr`)
        WHERE
            (`tax_rr`.`tax_rate_rule_id` = `li_tax`.`tax_rule_rate_id`)
        GROUP BY `li_tax`.`location_id` , `li_tax`.`register` , `li_tax`.`business_date` , `li_tax`.`txn_no` , `li_tax`.`item_id`) `tax_dtl`)
    WHERE
        ((`li_itm`.`item_id` = `itm`.`item_id`)
            AND (`li_itm`.`location_id` = `tax_dtl`.`location_id`)
            AND (`li_itm`.`register` = `tax_dtl`.`register`)
            AND (`itm`.`item_id` = `itmopt`.`item_id`)
            AND (`li_itm`.`business_date` = `tax_dtl`.`business_date`)
            AND (`li_itm`.`txn_no` = `tax_dtl`.`txn_no`)
            AND (`li_itm`.`item_id` = `tax_dtl`.`item_id`));

            
-- -----------------------------------------------------
-- View `commercedb`.`v_location_sales_data`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_location_sales_data` ;
DROP TABLE IF EXISTS `commercedb`.`v_location_sales_data`;
USE `commercedb`;

CREATE VIEW `commercedb`.`v_location_sales_data` AS
    SELECT 
        t1.*,
        t2.customer_count,
        t3.discount_percent,
        t4.discount_amount
    FROM
        (SELECT 
            location_id,
                business_date,
                ROUND(SUM(txn_item_count) / COUNT(txn_no), 2) AS basket_size,
                ROUND(SUM(basket_amounts) / COUNT(txn_no), 2) AS basket_amount,
                COUNT(txn_no) AS txn_count,
                ROUND(SUM(gross_amounts), 2) AS revenue
        FROM
            (SELECT 
            txn_itm.location_id,
                txn_itm.business_date,
                txn_itm.register,
                txn_itm.txn_no,
                SUM(gross_qty) AS txn_item_count,
                SUM(net_amount) AS basket_amounts,
                SUM(gross_amount) AS gross_amounts
        FROM
            commercedb.txn_master txn, commercedb.txn_li_item txn_itm
        WHERE
            txn.txn_type IN ('SALE' , 'RETURN')
                AND status IN ('COMPLETED')
                AND txn.business_date = txn_itm.business_date
                AND txn.register = txn_itm.register
                AND txn.location_id = txn_itm.location_id
                AND txn.txn_no = txn_itm.txn_no
        GROUP BY txn_itm.location_id , txn_itm.business_date , txn_itm.register , txn_itm.txn_no) txn_basket
        GROUP BY location_id , business_date) t1,
        (SELECT 
            `txn`.`location_id` AS `location_id`,
                `txn`.`business_date` AS `business_date`,
                count(txn_cust.customer_id) AS `customer_count`
        FROM
            commercedb.txn_master `txn` LEFT JOIN commercedb.txn_customer txn_cust
            on (txn.location_id=txn_cust.location_id
            and txn.register=txn_cust.register
            and txn.business_date=txn_cust.business_date
            and txn.txn_no=txn_cust.txn_no)
            where `txn`.`txn_type` IN ('SALE' , 'RETURN')
                AND (`txn`.`status` = 'COMPLETED')
                
        GROUP BY `txn`.`location_id` , `txn`.`business_date`) t2,
        (SELECT 
            location_id,
                business_date,
                SUM(total_discount),
                SUM(total_price),
                ABS(ROUND((SUM(total_discount) / SUM(total_price)) * 100, 2)) AS discount_percent
        FROM
            (SELECT 
            txn_itm.location_id,
                txn_itm.business_date,
                txn_itm.register,
                txn_itm.txn_no,
                SUM(discount_amount) AS total_discount,
                SUM(extended_amount) AS total_price
        FROM
            commercedb.txn_master txn, commercedb.txn_li_item txn_itm
        WHERE
            txn.txn_type IN ('SALE' , 'RETURN')
                AND status IN ('COMPLETED')
                AND txn.business_date = txn_itm.business_date
                AND txn.register = txn_itm.register
                AND txn.location_id = txn_itm.location_id
                AND txn.txn_no = txn_itm.txn_no
                AND (IFNULL(`txn_itm`.`discount_percentage`,-1) = -1 OR `txn_itm`.`discount_percentage` IS NOT NULL)
        GROUP BY txn_itm.location_id , txn_itm.business_date , txn_itm.register , txn_itm.txn_no) txn_discount_percent
        GROUP BY location_id , business_date) t3,
        (SELECT 
            `txn_discount_percent`.`location_id` AS `location_id`,
                `txn_discount_percent`.`business_date` AS `business_date`,
                ROUND(SUM(`txn_discount_percent`.`total_discount`), 2) AS `discount_amount`
        FROM
            (SELECT 
            `txn_itm`.`location_id` AS `location_id`,
                `txn_itm`.`business_date` AS `business_date`,
                `txn_itm`.`register` AS `register`,
                `txn_itm`.`txn_no` AS `txn_no`,
                SUM(`txn_itm`.`discount_amount`) AS `total_discount`
        FROM
            (`commercedb`.`txn_master` `txn`
        JOIN `commercedb`.`txn_li_item` `txn_itm`)
        WHERE
            (`txn`.`txn_type` IN ('SALE' , 'RETURN'))
                AND (`txn`.`status` = 'COMPLETED')
                AND (`txn`.`business_date` = `txn_itm`.`business_date`)
                AND (`txn`.`register` = `txn_itm`.`register`)
                AND (`txn`.`location_id` = `txn_itm`.`location_id`)
                AND (`txn`.`txn_no` = `txn_itm`.`txn_no`)              
                AND (ISNULL(`txn_itm`.`discount_percentage`) OR `txn_itm`.`discount_percentage`=0)
                AND ((`txn_itm`.`discount_amount` IS NOT NULL)
                OR (IFNULL(`txn_itm`.`discount_amount`, -(1)) = -(1)))
        GROUP BY `txn_itm`.`location_id` , `txn_itm`.`business_date` , `txn_itm`.`register` , `txn_itm`.`txn_no`) `txn_discount_percent`
        GROUP BY `txn_discount_percent`.`location_id` , `txn_discount_percent`.`business_date`) AS t4
    WHERE
        t1.location_id = t2.location_id
            AND t1.business_date = t2.business_date
            AND t3.location_id = t2.location_id
            AND t3.business_date = t2.business_date
            AND t3.location_id = t4.location_id
            AND t3.business_date = t4.business_date;    
            
            
-- -----------------------------------------------------
-- View `commercedb`.`v_txn_lookup`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_txn_lookup` ;
DROP TABLE IF EXISTS `commercedb`.`v_txn_lookup`;
USE `commercedb`;
            
CREATE 
VIEW `commercedb`.`v_txn_lookup` AS
    SELECT 
		
        CONCAT(CONVERT( LPAD(`txn_ma`.`location_id`, 4, '0') USING UTF8),
                CONVERT( LPAD(`txn_ma`.`register`, 3, '0') USING UTF8),
                CONVERT( LPAD(`txn_ma`.`txn_no`, 5, '0') USING UTF8),
                DATE_FORMAT(`txn_ma`.`business_date`, '%d%m%y')) AS `unique_txn`,
		`txn_ma`.location_id,
        `txn_ma`.txn_no,
        `txn_ma`.register,
        `txn_ma`.`business_date` AS `business_date`,
        `txn_ma`.`txn_type` AS `txn_type`,
        `cust`.`name` AS `customer_name`,
        `txn_ma`.`business_date` AS `created_date`,
        `txn_ma`.`created_by` AS `created_by`,
        `txn_ma`.`tax_total` AS `tax_total`,
        `txn_ma`.`total` AS `total_amount`
    FROM
        `commercedb`.`txn_master` `txn_ma` left join
        `commercedb`.`txn_customer` `txn_cust` on
        `txn_ma`.`location_id` = `txn_cust`.`location_id`
            AND (`txn_ma`.`business_date` = `txn_cust`.`business_date`)
            AND (`txn_ma`.`register` = `txn_cust`.`register`)
            AND (`txn_ma`.`txn_no` = `txn_cust`.`txn_no`)
		left join `commercedb`.`customer` `cust` on
            (`txn_cust`.`customer_id` = `cust`.`customer_id`)
            AND (`txn_cust`.`customer_type` = 'CUSTOMER')
            where `txn_ma`.txn_Type in ('SALE','RETURN');            
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

