-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_master_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_master_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_master_audit` (
  `audit_version` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NULL,
  `offline_flag` TINYINT NULL,
  `session_id` VARCHAR(45) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `total` DECIMAL(12,2) NULL DEFAULT 0.00,
  `tax_total` DECIMAL(12,2) NULL DEFAULT 0.00,
  `discount_total` DECIMAL(12,2) NULL DEFAULT 0.00,
  `rounded_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `subtotal` DECIMAL(12,2) NULL DEFAULT 0.00,
  `cancel_reason_code` VARCHAR(20) NULL,
  `txn_type` VARCHAR(50) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `post_void_flag` TINYINT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `comments` VARCHAR(300) NULL,
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_master_register_master_audit1`
    FOREIGN KEY (`location_id` , `register`)
    REFERENCES `pi_pos_industry`.`register_master` (`location_id` , `register_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_master_register_master1_idx` ON `pi_pos_industry`.`txn_master_audit` (`location_id` ASC, `register` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_customer_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_customer_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_customer_audit` (
  `audit_version` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `customer_id` BIGINT NOT NULL,
  `customer_type` VARCHAR(10) NOT NULL,
  `billing_address_id` BIGINT NULL,
  `shipping_address_id` BIGINT NULL,
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`, `customer_id`, `customer_type`),
  CONSTRAINT `fk_txn_customer_txn_master_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_line_item_master_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_line_item_master_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_line_item_master_audit` (
  `audit_version` BIGINT NOT NULL,
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
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`, `seq_no`),
  CONSTRAINT `fk_txn_line_item_master_txn_master_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_li_item_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_li_item_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_li_item_audit` (
  `audit_version` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `seq_no` INT(3) NOT NULL,
  `item_id` BIGINT NOT NULL,
  `item_desc` VARCHAR(150) NOT NULL,
  `qty` INT(5) NOT NULL,
  `gross_qty` INT(5) NOT NULL,
  `unit_price` DECIMAL(12,2) NOT NULL,
  `suggested_price` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `max_retail_price` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `discount_amount` DECIMAL(12,2) NOT NULL,
  `discount_percentage` DECIMAL(12,2) NULL,
  `extended_amount` DECIMAL(12,2) NOT NULL,
  `tax_amount` DECIMAL(12,2) NOT NULL,
  `return_flag` TINYINT NOT NULL,
  `entry_method` VARCHAR(20) NOT NULL,
  `net_amount` DECIMAL(12,2) NULL,
  `gross_amount` DECIMAL(12,2) NULL,
  `returned_qty` INT(5) NULL,
  `upc_no` VARCHAR(20) NULL,
  `txn_type` VARCHAR(20) NULL,
  `inv_action_code` VARCHAR(20) NULL,
  `org_location_id` INT(4) NULL,
  `org_business_date` DATETIME NULL,
  `org_register` INT(3) NULL,
  `org_txn_no` INT(5) NULL,
  `return_reason_code` VARCHAR(20) NULL,
  `return_comment` VARCHAR(150) NULL,
  `return_type_code` VARCHAR(20) NULL,
  `rcpt_count` INT(2) NULL,
  `base_unit_price` DECIMAL(12,2) NULL,
  `base_extended_price` DECIMAL(12,2) NULL,
  `new_description` VARCHAR(150) NULL,
  `exclude_from_sales_flag` TINYINT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `hsn_no` VARCHAR(20) NULL,
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`, `seq_no`, `item_id`),
  CONSTRAINT `fk_txn_li_item_item_audit1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_li_item_txn_line_item_master_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    REFERENCES `pi_pos_industry`.`txn_line_item_master_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_li_item_item1_idx` ON `pi_pos_industry`.`txn_li_item_audit` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_li_tax_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_li_tax_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_li_tax_audit` (
  `audit_version` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `seq_no` INT(3) NOT NULL,
  `item_id` BIGINT NULL,
  `total_taxable_amount` DECIMAL(12,2) NULL,
  `total_tax_amount` DECIMAL(12,2) NULL,
  `total_tax_exempt_amount` DECIMAL(12,2) NULL,
  `tax_override_amount` DECIMAL(12,2) NOT NULL,
  `tax_override_percentage` DECIMAL(12,2) NULL,
  `tax_override_flag` TINYINT NULL,
  `override_reason_code` VARCHAR(20) NULL,
  `void_flag` TINYINT NULL,
  `tax_rule_percentage` DECIMAL(12,2) NULL,
  `tax_rule_amount` DECIMAL(12,2) NULL,
  `org_taxable_amount` DECIMAL(12,2) NULL,
  `org_tax_group_id` INT NULL,
  `created_by` VARCHAR(50) NULL,
  `created_date` DATETIME NULL,
  `tax_group_id` INT NOT NULL,
  `tax_rule_rate_id` INT NOT NULL,
  `tax_code` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`, `seq_no`),
  CONSTRAINT `fk_txn_li_tax_audit_txn_line_item_master_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    REFERENCES `pi_pos_industry`.`txn_line_item_master_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_li_tender_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_li_tender_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_li_tender_audit` (
  `audit_version` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `seq_no` INT(3) NOT NULL,
  `tender_id` INT NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `change_flag` TINYINT NOT NULL,
  `type_code` VARCHAR(40) NOT NULL,
  `action_code` VARCHAR(40) NULL,
  `foreign_amount` DECIMAL(12,2) NULL,
  `exchange_rate` DECIMAL(12,2) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`, `seq_no`),
  CONSTRAINT `fk_txn_li_tender_txn_line_item_master_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    REFERENCES `pi_pos_industry`.`txn_line_item_master_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_receipt_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_receipt_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_receipt_audit` (
  `audit_version` BIGINT NOT NULL,
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
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`, `receipt_type`),
  CONSTRAINT `fk_txn_receipt_txn_master_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_shipment_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_shipment_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_shipment_audit` (
  `audit_version` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `order_request_id` VARCHAR(20) NULL,
  `order_request_date` DATETIME NULL,
  `driver_name` VARCHAR(30) NULL,
  `driver_phone` VARCHAR(10) NULL,
  `vehicle_no` VARCHAR(15) NULL,
  `transport_company` VARCHAR(50) NULL,
  `gp_pr_no` VARCHAR(15) NULL,
  `gp_pr_date` DATETIME NULL,
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_shipment_txn_master_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_tender_count_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_tender_count_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_tender_count_audit` (
  `audit_version` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `txn_type` VARCHAR(30) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `media_count` BIGINT NOT NULL,
  `actual_amount` DECIMAL(12,2) NULL,
  `actual_media_count` BIGINT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`, `tender_id`),
  CONSTRAINT `fk_txn_tndr_count_txn_master_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tndr_count_tender_master_audit1`
    FOREIGN KEY (`tender_id`)
    REFERENCES `pi_pos_industry`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tndr_count_tender_master1_idx` ON `pi_pos_industry`.`txn_tender_count_audit` (`tender_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_tender_denomination_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_tender_denomination_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_tender_denomination_audit` (
  `audit_version` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `denomination_id` INT NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `media_count` INT NOT NULL,
  `difference_amount` DECIMAL(12,2) NULL,
  `difference_media_count` INT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`audit_version`, `location_id`, `business_date`, `register`, `txn_no`, `tender_id`, `denomination_id`),
  CONSTRAINT `fk_txn_tndr_denomination_txn_tndr_count_audit1`
    FOREIGN KEY (`audit_version` , `location_id` , `business_date` , `register` , `txn_no` , `tender_id`)
    REFERENCES `pi_pos_industry`.`txn_tender_count_audit` (`audit_version` , `location_id` , `business_date` , `register` , `txn_no` , `tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tender_denomination_denomination_master_audit1`
    FOREIGN KEY (`denomination_id`)
    REFERENCES `pi_pos_industry`.`denomination_master` (`denomination_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tender_denomination_denomination_master1_idx` ON `pi_pos_industry`.`txn_tender_denomination_audit` (`denomination_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`sale_txn_invoices_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`sale_txn_invoices_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`sale_txn_invoices_audit` (
  `audit_version` BIGINT NOT NULL,
  `invoice_no` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  PRIMARY KEY (`invoice_no`, `audit_version`),
  UNIQUE INDEX `uq_sale_txn_invoices_txn_master1_idx` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC, `audit_version` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`daily_totals_audit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`daily_totals_audit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`daily_totals_audit` (
  `audit_version` BIGINT NOT NULL,
  `daily_totals_id` BIGINT NOT NULL,
  `location_id` INT NOT NULL,
  `register_id` VARCHAR(45) NULL,
  `business_date` DATETIME NOT NULL,
  `total_txn_count` INT NULL DEFAULT 0,
  `total_sales_count` INT NULL DEFAULT 0,
  `total_returns_count` INT NULL DEFAULT 0,
  `total_txn_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_sales_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_returns_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `sod_amount` DECIMAL(12,2) NULL,
  `eod_amount` DECIMAL(12,2) NULL,
  `total_no_sales_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_no_sales_count` INT NULL DEFAULT 0,
  PRIMARY KEY (`audit_version`, `daily_totals_id`))
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

