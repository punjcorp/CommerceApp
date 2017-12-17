-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Alter Table `commercedb`.`item_options`
-- -----------------------------------------------------

ALTER TABLE `commercedb`.`item_options` 
CHANGE COLUMN `discount_flag` `discount_flag` TINYINT NOT NULL DEFAULT 1 ,
CHANGE COLUMN `tax_flag` `tax_flag` TINYINT NOT NULL DEFAULT 1,
CHANGE COLUMN `ask_qty_flag` `ask_qty_flag` TINYINT NOT NULL DEFAULT 1,
CHANGE COLUMN `ask_price_flag` `ask_price_flag` TINYINT NOT NULL DEFAULT 0,
CHANGE COLUMN `return_flag` `return_flag` TINYINT NOT NULL DEFAULT 1,
CHANGE COLUMN `desc_flag` `desc_flag` TINYINT NOT NULL DEFAULT 0,
CHANGE COLUMN `related_item_flag` `related_item_flag` TINYINT NOT NULL DEFAULT 0,
CHANGE COLUMN `price_change_flag` `price_change_flag` TINYINT NOT NULL DEFAULT 1,
CHANGE COLUMN `non_merch_flag` `non_merch_flag` TINYINT NOT NULL DEFAULT 0,
CHANGE COLUMN `min_age_flag` `min_age_flag` TINYINT NOT NULL DEFAULT 0,
CHANGE COLUMN `customer_prompt` `customer_prompt` TINYINT NOT NULL DEFAULT 0;


-- -----------------------------------------------------
-- Alter Table `commercedb`.`attribute_master`
-- -----------------------------------------------------
ALTER TABLE `commercedb`.`attribute_master` 
ADD COLUMN `seq_no` INT(2) NOT NULL AFTER `description`;

CREATE UNIQUE INDEX `attribute_master_index2`
ON `commercedb`.`attribute_master`(`code`,`seq_no`);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
