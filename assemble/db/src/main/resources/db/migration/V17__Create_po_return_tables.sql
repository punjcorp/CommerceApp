-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`po_return`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`po_return` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`po_return` (
  `order_return_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `reason_code_id` INT NOT NULL,
  `sub_total_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `discount_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `tax_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `received_refund_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `status` VARCHAR(1) NOT NULL,
  `comments` VARCHAR(200) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`order_return_id`),
  INDEX `fk_purchase_order_return_purchase_order1_idx` (`order_id` ASC),
  CONSTRAINT `fk_purchase_order_return_purchase_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `commercedb`.`purchase_order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`po_return_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`po_return_items` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`po_return_items` (
  `order_return_item_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_return_id` BIGINT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `item_name` VARCHAR(150) NULL,
  `reason_code_id` INT NOT NULL,
  `actual_unit_cost` DECIMAL(12,2) NULL,
  `return_qty` DECIMAL(12,2) NULL,
  `actual_cost_amount` DECIMAL(12,2) NULL,
  `actual_discount_amount` DECIMAL(12,2) NULL,
  `actual_tax_amount` DECIMAL(12,2) NULL,
  `actual_total_cost` DECIMAL(12,2) NULL,
  PRIMARY KEY (`order_return_item_id`),
  CONSTRAINT `fk_po_return_items_po_return1`
    FOREIGN KEY (`order_return_id`)
    REFERENCES `commercedb`.`po_return` (`order_return_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`po_return_items_tax`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`po_return_items_tax` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`po_return_items_tax` (
  `po_return_item_tax_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_return_item_id` BIGINT NOT NULL,
  `tax_group_id` INT NOT NULL,
  `tax_rate_rule_id` INT NOT NULL,
  `tax_amount` DECIMAL(12,2) NOT NULL,
  `tax_percentage` DECIMAL(12,2) NOT NULL,
  `tax_code` VARCHAR(30) NOT NULL,
  `taxable_amount` DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (`po_return_item_tax_id`),
  CONSTRAINT `fk_po_return_items_tax_po_return_items1`
    FOREIGN KEY (`order_return_item_id`)
    REFERENCES `commercedb`.`po_return_items` (`order_return_item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
