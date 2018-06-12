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
    
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

