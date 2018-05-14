-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order` (
  `order_id` BIGINT NOT NULL AUTO_INCREMENT,
  `supplier_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `sub_total_cost` DECIMAL(12,2) NOT NULL,
  `discount_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `tax_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `paid_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `status` VARCHAR(1) NOT NULL,
  `invoice` BLOB NULL,
  `modified_date` DATETIME NULL,
  `modified_by` VARCHAR(50) NULL,
  `comments` VARCHAR(200) NULL,
  `acutal_sub_total_cost` DECIMAL(12,2) NULL,
  `actual_tax_amount` DECIMAL(12,2) NULL,
  `actual_total_amount` DECIMAL(12,2) NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `fk_purchase_order_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `commercedb`.`supplier` (`supplier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_purchase_order_supplier1_idx` ON `commercedb`.`purchase_order` (`supplier_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order_items` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order_items` (
  `order_item_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `item_name` VARCHAR(150) NOT NULL,
  `unit_cost` DECIMAL(12,2) NOT NULL,
  `ordered_qty` DECIMAL(12,2) NOT NULL,
  `cost_amount` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `tax_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_cost` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `actual_unit_cost` DECIMAL(12,2) NULL DEFAULT 0.00,
  `delievered_date` DATETIME NULL,
  `delievered_qty` DECIMAL(12,2) NULL,
  `actual_cost_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `actual_discount_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `actual_tax_amount` DECIMAL(12,2) NULL DEFAULT '0.00',
  `actual_total_cost` DECIMAL(12,2) NULL DEFAULT 0.00,
  PRIMARY KEY (`order_item_id`),
  CONSTRAINT `fk_purchase_order_items_purchase_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `commercedb`.`purchase_order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_purchase_order_items_purchase_order1_idx` ON `commercedb`.`purchase_order_items` (`order_id` ASC);

CREATE INDEX `fk_purchase_order_items_item1_idx` ON `commercedb`.`purchase_order_items` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order_items_tax`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order_items_tax` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order_items_tax` (
  `order_item_tax_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_item_id` BIGINT NOT NULL,
  `tax_group_id` INT NOT NULL,
  `tax_rate_rule_id` INT NOT NULL,
  `tax_amount` DECIMAL(12,2) NOT NULL,
  `tax_percentage` DECIMAL(12,2) NOT NULL,
  `tax_code` VARCHAR(30) NOT NULL,
  `taxable_amount` DECIMAL(12,2) NOT NULL,
  `actual_taxable_amount` DECIMAL(12,2) NULL,
  `actual_tax_amount` DECIMAL(12,2) NULL,
  PRIMARY KEY (`order_item_tax_id`),
  INDEX `fk_purchase_order_items_tax_purchase_order_items1_idx` (`order_item_id` ASC),
  CONSTRAINT `fk_purchase_order_items_tax_purchase_order_items1`
    FOREIGN KEY (`order_item_id`)
    REFERENCES `commercedb`.`purchase_order_items` (`order_item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order_bills`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order_bills` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order_bills` (
  `order_bill_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `bill_no` VARCHAR(50) NOT NULL,
  `bill_date` DATETIME NOT NULL,
  `bill_data` MEDIUMBLOB NULL,
  `bill_file` VARCHAR(300) NULL,
  `bill_file_type` VARCHAR(50) NULL,
  PRIMARY KEY (`order_bill_id`),
  CONSTRAINT `fk_table1_purchase_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `commercedb`.`purchase_order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order_payment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order_payment` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order_payment` (
  `order_payment_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `tender_id` INT NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `paye_account` VARCHAR(40) NULL,
  `bank_name` VARCHAR(80) NULL,
  `bank_branch` VARCHAR(80) NULL,
  `description` VARCHAR(150) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`order_payment_id`),
  CONSTRAINT `fk_purchase_order_payment_purchase_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `commercedb`.`purchase_order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
