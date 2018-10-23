-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- View `pi_pos_industry`.`v_item_price_latest`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `pi_pos_industry`.`v_item_price_latest` ;
DROP TABLE IF EXISTS `pi_pos_industry`.`v_item_price_latest`;

create view pi_pos_industry.v_item_price_latest as
SELECT 
            MAX(item_price_id) AS item_price_id,
                item_id,
                location_id,
                price_change_Type,
                MAX(start_date) AS start_date,
                MAX(end_date) AS end_date,
                MAX(item_price) AS item_price
        FROM
            pi_pos_industry.item_price
        WHERE
            status = 'A' AND start_date < NOW()
                AND (end_date >= NOW() OR end_date IS NULL)
                AND clearance_reset_id IS NULL
        GROUP BY price_change_Type , item_id, location_id
        ORDER BY price_change_type DESC , start_date DESC
        LIMIT 1;

-- -----------------------------------------------------
-- View `pi_pos_industry`.`v_sku_dtls`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `pi_pos_industry`.`v_sku_dtls` ;
DROP TABLE IF EXISTS `pi_pos_industry`.`v_sku_dtls`;

CREATE VIEW pi_pos_industry.v_sku_dtls AS
    SELECT 
    	concat(itmtax.item_id, "-",itmtax.location_id) as loc_sku_id,
        itm.`name` AS item_name,
        itm.long_desc AS item_desc,
        itmopt.hsn_no,
        itmopt.suggested_price,
        itmopt.max_retail_price,
        itmstk.total_qty,
        itmstk.stock_on_hand,
        itmstk.non_sellable_qty,
        itmstk.reserved_qty,
        itmimg.item_image_id,
        itmimg.name AS image_name,
        itmimg.image_type,
        itmimg.image_url,
        itmimg.image_data,
        itmtax.*,
        itmprc.item_price_id,
        itmprc.price_change_type,
        itmprc.item_price,
        itmprc.start_date,
        itmprc.end_date
    FROM
        pi_pos_industry.item itm,
        pi_pos_industry.item_options itmopt,
        pi_pos_industry.item_stock itmstk
            LEFT JOIN
        pi_pos_industry.item_images itmimg ON itmstk.item_id = itmimg.item_id
            LEFT JOIN
        pi_pos_industry.v_item_location_tax itmtax ON itmtax.item_id = itmstk.item_id
            LEFT JOIN
        pi_pos_industry.v_item_price_latest itmprc ON itmprc.item_id = itmstk.item_id
            AND itmprc.location_id = itmstk.location_id
    WHERE
        itm.item_id = itmopt.item_id
            AND itm.item_id = itmstk.item_id
            AND itm.item_level = 2;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

