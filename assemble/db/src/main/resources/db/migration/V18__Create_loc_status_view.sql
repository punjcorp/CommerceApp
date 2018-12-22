-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- View `commercedb`.`v_loc_status`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_loc_status` ;
DROP TABLE IF EXISTS `commercedb`.`v_loc_status`;
USE `commercedb`;
CREATE  
VIEW `commercedb`.`v_loc_status` AS
    SELECT 
        `a2`.`location_id` AS `location_id`,
        `a2`.`business_date` AS `business_date`,
        `a2`.`store_open_exists` AS `store_open_exists`,
        IFNULL(`a1`.`sale_exists`, 0) AS `sale_exists`,
        IFNULL(`a3`.`store_close_exists`, 0) AS `store_close_exists`
    FROM
        ((((SELECT 
            `commercedb`.`txn_master`.`location_id` AS `location_id`,
                `commercedb`.`txn_master`.`business_date` AS `business_date`,
                IF(`commercedb`.`txn_master`.`business_date`, 1, 0) AS `store_open_exists`
        FROM
            `commercedb`.`txn_master`
        WHERE
            (`commercedb`.`txn_master`.`txn_type` = 'OPEN_STORE')
        GROUP BY `commercedb`.`txn_master`.`business_date` , `commercedb`.`txn_master`.`location_id`)) `a2`
        LEFT JOIN (SELECT 
            `commercedb`.`txn_master`.`location_id` AS `location_id`,
                `commercedb`.`txn_master`.`business_date` AS `business_date`,
                IF(`commercedb`.`txn_master`.`business_date`, 1, 0) AS `sale_exists`
        FROM
            `commercedb`.`txn_master`
        WHERE
            (`commercedb`.`txn_master`.`txn_type` = 'SALE')
        GROUP BY `commercedb`.`txn_master`.`business_date` , `commercedb`.`txn_master`.`location_id`) `a1` ON (((`a1`.`business_date` = `a2`.`business_date`)
            AND (`a1`.`location_id` = `a2`.`location_id`))))
        LEFT JOIN (SELECT 
            `commercedb`.`txn_master`.`location_id` AS `location_id`,
                `commercedb`.`txn_master`.`business_date` AS `business_date`,
                IF(`commercedb`.`txn_master`.`business_date`, 1, 0) AS `store_close_exists`
        FROM
            `commercedb`.`txn_master`
        WHERE
            (`commercedb`.`txn_master`.`txn_type` = 'CLOSE_STORE')
        GROUP BY `commercedb`.`txn_master`.`business_date` , `commercedb`.`txn_master`.`location_id`) `a3` ON (((`a3`.`business_date` = `a2`.`business_date`)
            AND (`a3`.`location_id` = `a2`.`location_id`))));

            
            
-- -----------------------------------------------------
-- View `commercedb`.`v_item_price_latest`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_item_price_latest` ;
DROP TABLE IF EXISTS `commercedb`.`v_item_price_latest`;
USE `commercedb`;
CREATE  
VIEW `commercedb`.`v_item_price_latest` AS
    SELECT 
        MAX(`item_price`.`item_price_id`) AS `item_price_id`,
        `item_price`.`item_id` AS `item_id`,
        `item_price`.`location_id` AS `location_id`,
        `item_price`.`price_change_type` AS `price_change_Type`,
        MAX(`item_price`.`start_date`) AS `start_date`,
        MAX(`item_price`.`end_date`) AS `end_date`,
        MAX(`item_price`.`item_price`) AS `item_price`
    FROM
        `item_price`
    WHERE
        ((`item_price`.`status` = 'A')
            AND (`item_price`.`start_date` < NOW())
            AND ((`item_price`.`end_date` >= NOW())
            OR ISNULL(`item_price`.`end_date`))
            AND ISNULL(`item_price`.`clearance_reset_id`))
    GROUP BY `item_price`.`price_change_type` , `item_price`.`item_id` , `item_price`.`location_id`
    ORDER BY `item_price`.`price_change_type` DESC , `start_date` DESC;            
            
-- -----------------------------------------------------
-- View `commercedb`.`v_sku_dtls`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `commercedb`.`v_sku_dtls` ;
DROP TABLE IF EXISTS `commercedb`.`v_sku_dtls`;
USE `commercedb`;
CREATE  
VIEW `commercedb`.`v_sku_dtls` AS
    SELECT 
        CONCAT(`itmtax`.`item_id`,
                '-',
                `itmtax`.`location_id`) AS `loc_sku_id`,
        `itm`.`name` AS `item_name`,
        `itm`.`long_desc` AS `item_desc`,
        `itmopt`.`suggested_price` AS `suggested_price`,
        `itmopt`.`max_retail_price` AS `max_retail_price`,
        `itmstk`.`total_qty` AS `total_qty`,
        `itmstk`.`stock_on_hand` AS `stock_on_hand`,
        `itmstk`.`non_sellable_qty` AS `non_sellable_qty`,
        `itmstk`.`reserved_qty` AS `reserved_qty`,
        `itmimg`.`item_image_id` AS `item_image_id`,
        `itmimg`.`name` AS `image_name`,
        `itmimg`.`image_type` AS `image_type`,
        `itmimg`.`image_url` AS `image_url`,
        `itmimg`.`image_data` AS `image_data`,
        `itmtax`.`item_id` AS `item_id`,
        `itmtax`.`name` AS `name`,
        `itmtax`.`long_desc` AS `long_desc`,
        `itmtax`.`hsn_no` AS `hsn_no`,
        `itmtax`.`base_unit_cost` AS `base_unit_cost`,
        `itmtax`.`location_id` AS `location_id`,
        `itmtax`.`tax_group_id` AS `tax_group_id`,
        `itmtax`.`tax_group_name` AS `tax_group_name`,
        `itmtax`.`sgst_rate_rule_id` AS `sgst_rate_rule_id`,
        `itmtax`.`sgst_rate` AS `sgst_rate`,
        `itmtax`.`sgst_amount` AS `sgst_amount`,
        `itmtax`.`sgst_code` AS `sgst_code`,
        `itmtax`.`cgst_rate_rule_id` AS `cgst_rate_rule_id`,
        `itmtax`.`cgst_rate` AS `cgst_rate`,
        `itmtax`.`cgst_amount` AS `cgst_amount`,
        `itmtax`.`cgst_code` AS `cgst_code`,
        `itmtax`.`igst_rate_rule_id` AS `igst_rate_rule_id`,
        `itmtax`.`igst_rate` AS `igst_rate`,
        `itmtax`.`igst_amount` AS `igst_amount`,
        `itmtax`.`igst_code` AS `igst_code`,
        `itmprc`.`item_price_id` AS `item_price_id`,
        `itmprc`.`price_change_Type` AS `price_change_type`,
        `itmprc`.`item_price` AS `item_price`,
        `itmprc`.`start_date` AS `start_date`,
        `itmprc`.`end_date` AS `end_date`
    FROM
        ((`commercedb`.`item` `itm`
        JOIN `commercedb`.`item_options` `itmopt`)
        JOIN (((`commercedb`.`item_stock` `itmstk`
        LEFT JOIN `commercedb`.`item_images` `itmimg` ON ((`itmstk`.`item_id` = `itmimg`.`item_id`)))
        LEFT JOIN `commercedb`.`v_item_location_tax` `itmtax` ON ((`itmtax`.`item_id` = `itmstk`.`item_id`)))
        LEFT JOIN `commercedb`.`v_item_price_latest` `itmprc` ON (((`itmprc`.`item_id` = `itmstk`.`item_id`)
            AND (`itmprc`.`location_id` = `itmstk`.`location_id`)))))
    WHERE
        ((`itm`.`item_id` = `itmopt`.`item_id`)
            AND (`itm`.`item_id` = `itmstk`.`item_id`)
            AND (`itm`.`item_level` = 2));
            
            
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
