-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`txn_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_master` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NULL,
  `offline_flag` TINYINT NOT NULL DEFAULT 0,
  `session_id` VARCHAR(45) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `total` DECIMAL NOT NULL,
  `tax_total` DECIMAL NOT NULL,
  `round_total` DECIMAL NOT NULL,
  `subtotal` DECIMAL NOT NULL,
  `cancel_reason_code` VARCHAR(20) NOT NULL,
  `txn_type` VARCHAR(50) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `post_void_flag` TINYINT NOT NULL DEFAULT 0,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_master_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_master_location1_idx` ON `commercedb`.`txn_master` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_line_item_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_line_item_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_line_item_master` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `seq_no` INT(3) NOT NULL,
  `type_code` VARCHAR(20) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `void_flag` TINYINT NOT NULL DEFAULT 0,
  `void_reason_code` VARCHAR(20) NULL,
  `begin_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`),
  CONSTRAINT `fk_txn_line_item_master_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`txn_li_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_li_item` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_li_item` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `txn_li_seq_no` INT(3) NOT NULL,
  `seq_no` INT(3) NOT NULL,
  `item_id` BIGINT NOT NULL,
  `qty` INT(5) NOT NULL,
  `gross_qty` INT(5) NOT NULL,
  `unit_price` DECIMAL NOT NULL,
  `extended_amount` DECIMAL NOT NULL,
  `tax_amount` DECIMAL NOT NULL,
  `return_flag` TINYINT NOT NULL,
  `entry_method` VARCHAR(20) NOT NULL,
  `net_amount` DECIMAL NULL,
  `gross_amount` DECIMAL NULL,
  `returned_qty` INT(5) NULL,
  `upc_no` BIGINT NULL,
  `txn_type` VARCHAR(20) NULL,
  `tax_group_id` INT NOT NULL,
  `inv_action_code` VARCHAR(20) NULL,
  `org_location_id` INT(4) NULL,
  `org_business_date` DATETIME NULL,
  `org_register` INT(3) NULL,
  `org_txn_no` INT(5) NULL,
  `return_reason_code` VARCHAR(20) NULL,
  `return_comment` VARCHAR(150) NULL,
  `return_type_code` VARCHAR(20) NULL,
  `rcpt_count` INT(2) NULL,
  `base_unit_price` DECIMAL NULL,
  `base_extended_price` DECIMAL NULL,
  `new_description` VARCHAR(150) NULL,
  `exclude_from_sales_flag` TINYINT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `txn_li_seq_no`, `item_id`, `seq_no`),
  CONSTRAINT `fk_txn_li_item_txn_line_item_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `txn_li_seq_no`)
    REFERENCES `commercedb`.`txn_line_item_master` (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_li_item_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_li_item_tax_group1`
    FOREIGN KEY (`tax_group_id`)
    REFERENCES `commercedb`.`tax_group` (`tax_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_li_item_item1_idx` ON `commercedb`.`txn_li_item` (`item_id` ASC);

CREATE INDEX `fk_txn_li_item_tax_group1_idx` ON `commercedb`.`txn_li_item` (`tax_group_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_receipt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_receipt` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_receipt` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `receipt_type` VARCHAR(20) NOT NULL,
  `receipt_data` LONGBLOB NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_receipt_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`txn_no_sale`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_no_sale` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_no_sale` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `reason_code` VARCHAR(20) NOT NULL,
  `status` VARCHAR(20) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_no_sale_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`txn_post_void`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_post_void` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_post_void` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `reason_code` VARCHAR(20) NOT NULL,
  `entry_code` VARCHAR(30) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_post_void_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`txn_li_tax`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_li_tax` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_li_tax` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `txn_li_seq_no` INT(3) NOT NULL,
  `seq_no` INT(3) NOT NULL,
  `total_taxable_amount` DECIMAL NULL,
  `total_tax_amount` DECIMAL NULL,
  `total_tax_exempt_amount` DECIMAL NULL,
  `tax_group_id` INT NOT NULL,
  `tax_rate_rule_id` INT NOT NULL,
  `tax_override_amount` DECIMAL NOT NULL,
  `tax_override_percentage` DECIMAL NULL,
  `tax_override_flag` TINYINT NULL,
  `override_reason_code` VARCHAR(20) NULL,
  `void_flag` TINYINT NULL,
  `tax_rule_percentage` DECIMAL NULL,
  `tax_rule_amount` DECIMAL NULL,
  `org_taxable_amount` DECIMAL NULL,
  `org_tax_group_id` INT NULL,
  `created_by` VARCHAR(50) NULL,
  `created_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `txn_li_seq_no`, `tax_group_id`, `tax_rate_rule_id`),
  CONSTRAINT `fk_txn_li_tax_txn_line_item_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `txn_li_seq_no`)
    REFERENCES `commercedb`.`txn_line_item_master` (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_li_tax_tax_rate_rule1`
    FOREIGN KEY (`tax_rate_rule_id`)
    REFERENCES `commercedb`.`tax_rate_rule` (`tax_rate_rule_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_li_tax_tax_group1`
    FOREIGN KEY (`tax_group_id`)
    REFERENCES `commercedb`.`tax_group` (`tax_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_li_tax_tax_rate_rule1_idx` ON `commercedb`.`txn_li_tax` (`tax_rate_rule_id` ASC);

CREATE INDEX `fk_txn_li_tax_tax_group1_idx` ON `commercedb`.`txn_li_tax` (`tax_group_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_li_tender`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_li_tender` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_li_tender` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `seq_no` INT(3) NOT NULL,
  `amount` DECIMAL NOT NULL,
  `change_flag` TINYINT NOT NULL,
  `type_code` VARCHAR(40) NOT NULL,
  `action_code` VARCHAR(40) NOT NULL,
  `foreign_amount` DECIMAL NULL,
  `exchange_rate` DECIMAL NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`),
  CONSTRAINT `fk_txn_li_tender_txn_line_item_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    REFERENCES `commercedb`.`txn_line_item_master` (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
