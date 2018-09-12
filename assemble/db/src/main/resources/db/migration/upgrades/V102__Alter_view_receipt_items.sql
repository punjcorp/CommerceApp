SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- View `pi_pos_industry`.`v_receipt_li_item`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `pi_pos_industry`.`v_receipt_li_item` ;
DROP TABLE IF EXISTS `pi_pos_industry`.`v_receipt_li_item`;
USE `pi_pos_industry`;
CREATE 
VIEW `pi_pos_industry`.`v_receipt_li_item` AS
    SELECT 
        `li_itm`.`item_desc` AS `name`,
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
        (((`pi_pos_industry`.`txn_li_item` `li_itm`
        JOIN `pi_pos_industry`.`item` `itm`)
        JOIN `pi_pos_industry`.`item_options` `itmopt`)
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
            (`pi_pos_industry`.`txn_li_tax` `li_tax`
        JOIN `pi_pos_industry`.`tax_rate_rule` `tax_rr`)
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
