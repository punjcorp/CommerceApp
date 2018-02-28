-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `commercedb` ;

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `commercedb` DEFAULT CHARACTER SET utf8 ;
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user` (
  `username` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NULL,
  `phone1` VARCHAR(15) NULL,
  `phone2` VARCHAR(15) NULL,
  `email` VARCHAR(100) NOT NULL,
  `login_count` INT NOT NULL DEFAULT 0,
  `status` VARCHAR(2) NOT NULL,
  `photo` BLOB(2000) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `commercedb`.`user` (`email` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`user_password`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_password` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_password` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `modified_by` VARCHAR(50) NOT NULL,
  `modified_date` DATETIME NOT NULL,
  `status` VARCHAR(2) NOT NULL DEFAULT 'A',
  PRIMARY KEY (`username`, `modified_date`, `password`),
  CONSTRAINT `fk_user_password_user`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`role` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(75) NOT NULL,
  `description` VARCHAR(200) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`access` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`access` (
  `access_id` INT NOT NULL AUTO_INCREMENT,
  `access_name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(200) NULL,
  PRIMARY KEY (`access_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`role_access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`role_access` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`role_access` (
  `role_id` INT NOT NULL,
  `access_id` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`role_id`, `access_id`),
  CONSTRAINT `fk_role_access_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `commercedb`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_access_access1`
    FOREIGN KEY (`access_id`)
    REFERENCES `commercedb`.`access` (`access_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_role_access_role1_idx` ON `commercedb`.`role_access` (`role_id` ASC);

CREATE INDEX `fk_role_access_access1_idx` ON `commercedb`.`role_access` (`access_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`location` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`location` (
  `location_id` INT(4) NOT NULL,
  `location_type` VARCHAR(15) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(150) NULL,
  `status` VARCHAR(5) NOT NULL,
  `address1` VARCHAR(150) NOT NULL,
  `address2` VARCHAR(150) NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `pincode` VARCHAR(6) NOT NULL,
  `neighborhood` VARCHAR(100) NULL,
  `locale` VARCHAR(10) NOT NULL,
  `currency` VARCHAR(3) NOT NULL,
  `telephone1` VARCHAR(12) NOT NULL,
  `telephone2` VARCHAR(12) NULL,
  `store_manager` VARCHAR(80) NULL,
  `email_address` VARCHAR(150) NULL,
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `modified_date` DATETIME NULL,
  `modified_by` VARCHAR(50) NULL,
  PRIMARY KEY (`location_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_role` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_role` (
  `username` VARCHAR(50) NOT NULL,
  `role_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `begin_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  PRIMARY KEY (`username`, `role_id`, `location_id`),
  CONSTRAINT `fk_user_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `commercedb`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_role_user1_idx` ON `commercedb`.`user_role` (`username` ASC);

CREATE INDEX `fk_user_role_location1_idx` ON `commercedb`.`user_role` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`user_recovery`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_recovery` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_recovery` (
  `username` VARCHAR(50) NOT NULL,
  `question1` VARCHAR(150) NOT NULL,
  `answer1` VARCHAR(45) NULL,
  `question2` VARCHAR(150) NULL,
  `answer2` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `fk_user_recovery_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`login_logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`login_logs` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`login_logs` (
  `username` VARCHAR(50) NOT NULL,
  `login_time` DATETIME NOT NULL,
  `login_result` VARCHAR(20) NOT NULL,
  `password_used` VARCHAR(150) NULL,
  PRIMARY KEY (`username`, `login_time`),
  CONSTRAINT `fk_login_logs_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_login_logs_user1_idx` ON `commercedb`.`login_logs` (`username` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`address_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`address_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`address_master` (
  `address_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `primary` VARCHAR(1) NOT NULL DEFAULT 'N',
  `address_type` VARCHAR(20) NOT NULL,
  `address1` VARCHAR(150) NOT NULL,
  `address2` VARCHAR(150) NULL,
  `city` VARCHAR(30) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `pincode` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`address_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`user_credit_debit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_credit_debit` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_credit_debit` (
  `username` VARCHAR(50) NOT NULL,
  `card_no` VARCHAR(20) NOT NULL,
  `address_id` BIGINT NOT NULL,
  `name_on_card` VARCHAR(100) NOT NULL,
  `expiry_date` VARCHAR(5) NOT NULL,
  `cvv_no` INT(3) NULL,
  PRIMARY KEY (`username`, `card_no`),
  CONSTRAINT `fk_user_credit_debit_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_credit_debit_user_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `commercedb`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_credit_debit_user_address1_idx` ON `commercedb`.`user_credit_debit` (`address_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_hierarchy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_hierarchy` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_hierarchy` (
  `hierarchy_id` INT NOT NULL AUTO_INCREMENT,
  `level_code` VARCHAR(20) NOT NULL,
  `description` VARCHAR(250) NULL,
  `sort_order` INT NOT NULL DEFAULT 1,
  `hidden_flag` VARCHAR(1) NOT NULL DEFAULT 'N',
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `parent_id` INT NULL,
  PRIMARY KEY (`hierarchy_id`),
  CONSTRAINT `fk_item_hierarchy_item_hierarchy1`
    FOREIGN KEY (`parent_id`)
    REFERENCES `commercedb`.`item_hierarchy` (`hierarchy_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_hierarchy_item_hierarchy1_idx` ON `commercedb`.`item_hierarchy` (`parent_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item` (
  `item_id` BIGINT NOT NULL,
  `name` VARCHAR(150) NOT NULL,
  `long_desc` VARCHAR(300) NOT NULL,
  `item_level` INT NOT NULL,
  `parent_item_id` BIGINT NULL,
  `hierarchy_id` INT NOT NULL,
  `item_type` VARCHAR(45) NULL,
  `record_status` VARCHAR(1) NOT NULL DEFAULT 'C',
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `modified_date` DATETIME NULL,
  `modified_by` VARCHAR(50) NULL,
  PRIMARY KEY (`item_id`),
  CONSTRAINT `fk_item_item_hierarchy1`
    FOREIGN KEY (`hierarchy_id`)
    REFERENCES `commercedb`.`item_hierarchy` (`hierarchy_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_item_hierarchy1_idx` ON `commercedb`.`item` (`hierarchy_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_options`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_options` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_options` (
  `item_id` BIGINT NOT NULL,
  `uom` VARCHAR(20) NOT NULL,
  `discount_flag` TINYINT NOT NULL DEFAULT 1,
  `tax_flag` TINYINT NOT NULL DEFAULT 1,
  `ask_qty_flag` TINYINT NOT NULL DEFAULT 1,
  `ask_price_flag` TINYINT NOT NULL DEFAULT 0,
  `unit_cost` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
  `suggested_price` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
  `current_price` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
  `compare_at_price` DECIMAL(12,2) NOT NULL,
  `return_flag` TINYINT NOT NULL DEFAULT 1,
  `desc_flag` TINYINT NOT NULL DEFAULT 0,
  `related_item_flag` TINYINT NOT NULL DEFAULT 0,
  `tax_group_id` INT NOT NULL,
  `restocking_fee` DECIMAL(12,2) NULL,
  `price_change_flag` TINYINT NOT NULL DEFAULT 1,
  `non_merch_flag` TINYINT NOT NULL DEFAULT 0,
  `min_age_flag` TINYINT NOT NULL DEFAULT 0,
  `stock_status` VARCHAR(20) NOT NULL DEFAULT 0,
  `customer_prompt` TINYINT NOT NULL DEFAULT 0,
  `shipping_weight` DECIMAL(12,2) NULL,
  `pack_size` VARCHAR(45) NULL,
  PRIMARY KEY (`item_id`),
  CONSTRAINT `fk_item_options_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_options_item1_idx` ON `commercedb`.`item_options` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`attribute_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`attribute_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`attribute_master` (
  `attribute_id` BIGINT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(80) NOT NULL,
  `code` VARCHAR(5) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(150) NULL,
  `seq_no` INT(2) NOT NULL,
  PRIMARY KEY (`attribute_id`, `value`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `index2` ON `commercedb`.`attribute_master` (`code` ASC, `seq_no` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_attributes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_attributes` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_attributes` (
  `item_id` BIGINT NOT NULL,
  `attribute_id` BIGINT NOT NULL,
  `value` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`item_id`, `attribute_id`, `value`),
  CONSTRAINT `fk_item_attributes_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_style_attribute_values_attribute_master1`
    FOREIGN KEY (`attribute_id`)
    REFERENCES `commercedb`.`attribute_master` (`attribute_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_style_attribute_values_attribute_master1_idx` ON `commercedb`.`item_attributes` (`attribute_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_images`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_images` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_images` (
  `item_id` BIGINT NOT NULL,
  `feature_name` VARCHAR(80) NOT NULL,
  `image_url` VARCHAR(300) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`item_id`, `feature_name`),
  CONSTRAINT `fk_item_images_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_images_item1_idx` ON `commercedb`.`item_images` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_kit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_kit` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_kit` (
  `item_id` BIGINT NOT NULL,
  `component_item_id` BIGINT NOT NULL,
  `qty` INT NOT NULL,
  `display_order` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`item_id`, `component_item_id`),
  CONSTRAINT `fk_item_kit_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_kit_item1_idx` ON `commercedb`.`item_kit` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_substitute`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_substitute` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_substitute` (
  `item_id` BIGINT NOT NULL,
  `substitute_item_id` BIGINT NOT NULL,
  `begin_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL,
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`item_id`, `substitute_item_id`),
  CONSTRAINT `fk_item_substitute_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`item_related`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_related` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_related` (
  `item_id` BIGINT NOT NULL,
  `related_item_id` BIGINT NOT NULL,
  `mandatory` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`item_id`, `related_item_id`),
  CONSTRAINT `fk_item_related_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_related_item1_idx` ON `commercedb`.`item_related` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`supplier`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`supplier` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`supplier` (
  `supplier_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `phone1` VARCHAR(20) NOT NULL,
  `phone2` VARCHAR(20) NULL,
  `email` VARCHAR(80) NULL,
  PRIMARY KEY (`supplier_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`supplier_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`supplier_item` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`supplier_item` (
  `item_id` BIGINT NOT NULL,
  `supplier_id` INT NOT NULL,
  `unit_cost` DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (`item_id`, `supplier_id`),
  CONSTRAINT `fk_supplier_item_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_supplier_item_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `commercedb`.`supplier` (`supplier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_supplier_item_supplier1_idx` ON `commercedb`.`supplier_item` (`supplier_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order` (
  `order_id` BIGINT NOT NULL AUTO_INCREMENT,
  `supplier_id` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `total_estimated_cost` DECIMAL(12,2) NOT NULL,
  `discount_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `tax_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `paid_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `status` VARCHAR(1) NOT NULL,
  `invoice` BLOB NULL,
  `modified_date` DATETIME NULL,
  `modified_by` VARCHAR(50) NULL,
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
  `order_id` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `item_id` BIGINT NOT NULL,
  `ordered_qty` INT NOT NULL,
  `cost_amount` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `total_cost` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `delievered_date` DATETIME NULL,
  `delievered_qty` INT NULL,
  `cost_actual_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_actual_cost` DECIMAL(12,2) NULL DEFAULT 0.00,
  `discount_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `tax_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  `total_actual_amount` DECIMAL(12,2) NULL DEFAULT 0.00,
  PRIMARY KEY (`order_id`, `location_id`, `item_id`),
  CONSTRAINT `fk_purchase_order_items_purchase_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `commercedb`.`purchase_order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_purchase_order_items_purchase_order1_idx` ON `commercedb`.`purchase_order_items` (`order_id` ASC);

CREATE INDEX `fk_purchase_order_items_item1_idx` ON `commercedb`.`purchase_order_items` (`item_id` ASC);

CREATE INDEX `fk_purchase_order_items_location1_idx` ON `commercedb`.`purchase_order_items` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`user_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_address` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_address` (
  `username` VARCHAR(50) NOT NULL,
  `address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`username`, `address_id`),
  CONSTRAINT `fk_user_address_address_master1`
    FOREIGN KEY (`address_id`)
    REFERENCES `commercedb`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_address_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_address_user1_idx` ON `commercedb`.`user_address` (`username` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_stock` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_stock` (
  `item_id` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `total_qty` INT NOT NULL DEFAULT 0,
  `non_sellable_qty` INT NOT NULL DEFAULT 0,
  `reserved_qty` INT NOT NULL DEFAULT 0,
  `stock_on_hand` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`item_id`, `location_id`),
  CONSTRAINT `fk_item_stock_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_stock_location1_idx` ON `commercedb`.`item_stock` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`stock_bucket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`stock_bucket` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`stock_bucket` (
  `stock_bucket_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `system_bucket` VARCHAR(45) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` DATETIME NULL,
  `modified_date` DATETIME NULL,
  `status` VARCHAR(1) NOT NULL DEFAULT 'I',
  PRIMARY KEY (`stock_bucket_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`stock_reason_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`stock_reason_code` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`stock_reason_code` (
  `reason_code_id` INT NOT NULL AUTO_INCREMENT,
  `reason_code` VARCHAR(15) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `from_bucket_id` INT NULL,
  `to_bucket_id` INT NULL,
  `from_stock_action` VARCHAR(45) NULL,
  `to_stock_action` VARCHAR(45) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`reason_code_id`),
  CONSTRAINT `fk_stock_reason_code_stock_bucket1`
    FOREIGN KEY (`from_bucket_id`)
    REFERENCES `commercedb`.`stock_bucket` (`stock_bucket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_reason_code_stock_bucket2`
    FOREIGN KEY (`to_bucket_id`)
    REFERENCES `commercedb`.`stock_bucket` (`stock_bucket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_stock_reason_code_stock_bucket1_idx` ON `commercedb`.`stock_reason_code` (`from_bucket_id` ASC);

CREATE INDEX `fk_stock_reason_code_stock_bucket2_idx` ON `commercedb`.`stock_reason_code` (`to_bucket_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_stock_journal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_stock_journal` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_stock_journal` (
  `stock_journal_id` BIGINT NOT NULL AUTO_INCREMENT,
  `item_id` BIGINT NOT NULL,
  `reason_code_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `functionality` VARCHAR(45) NOT NULL,
  `qty` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`stock_journal_id`),
  CONSTRAINT `fk_stock_journal_stock_reason_code1`
    FOREIGN KEY (`reason_code_id`)
    REFERENCES `commercedb`.`stock_reason_code` (`reason_code_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_journal_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_journal_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_stock_journal_stock_reason_code1_idx` ON `commercedb`.`item_stock_journal` (`reason_code_id` ASC);

CREATE INDEX `fk_stock_journal_item1_idx` ON `commercedb`.`item_stock_journal` (`item_id` ASC);

CREATE INDEX `fk_item_stock_journal_location1_idx` ON `commercedb`.`item_stock_journal` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_stock_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_stock_details` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_stock_details` (
  `item_id` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `stock_bucket_id` INT NOT NULL,
  `total_qty` INT NOT NULL,
  PRIMARY KEY (`item_id`, `location_id`, `stock_bucket_id`),
  CONSTRAINT `fk_item_stock_details_item_stock1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item_stock` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_details_stock_bucket1`
    FOREIGN KEY (`stock_bucket_id`)
    REFERENCES `commercedb`.`stock_bucket` (`stock_bucket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_details_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_stock_details_stock_bucket1_idx` ON `commercedb`.`item_stock_details` (`stock_bucket_id` ASC);

CREATE INDEX `fk_item_stock_details_location1_idx` ON `commercedb`.`item_stock_details` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_price`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_price` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_price` (
  `item_price_id` BIGINT NOT NULL AUTO_INCREMENT,
  `item_id` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `price_change_type` VARCHAR(5) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  `item_price` DECIMAL(12,2) NOT NULL DEFAULT '0.0',
  `status` VARCHAR(1) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`item_price_id`),
  CONSTRAINT `fk_item_price_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_price_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_price_item1_idx` ON `commercedb`.`item_price` (`item_id` ASC);

CREATE INDEX `fk_item_price_location1_idx` ON `commercedb`.`item_price` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_price_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_price_history` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_price_history` (
  `item_price_id` BIGINT NOT NULL,
  `archived_date` DATETIME NOT NULL,
  `item_id` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `price_change_type` VARCHAR(5) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  `item_price` DECIMAL(12,2) NOT NULL DEFAULT '0.0',
  `status` VARCHAR(1) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`item_price_id`, `archived_date`),
  CONSTRAINT `fk_item_price_history_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`style_generator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`style_generator` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`style_generator` (
  `style_id` MEDIUMINT(7) ZEROFILL NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`style_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1000000;


-- -----------------------------------------------------
-- Table `commercedb`.`sku_generator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`sku_generator` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`sku_generator` (
  `style_id` MEDIUMINT(7) ZEROFILL NOT NULL,
  `color` INT(2) ZEROFILL NOT NULL,
  `size` INT(2) ZEROFILL NOT NULL,
  `status` VARCHAR(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`style_id`, `color`, `size`),
  CONSTRAINT `fk_sku_generator_style_generator1`
    FOREIGN KEY (`style_id`)
    REFERENCES `commercedb`.`style_generator` (`style_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_sku_generator_style_generator1_idx` ON `commercedb`.`sku_generator` (`style_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`tax_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_group` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_group` (
  `tax_group_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(300) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`tax_group_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`tax_authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_authority` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_authority` (
  `tax_authority_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `rounding_code` VARCHAR(5) NOT NULL,
  `rounding_digit_qty` INT(2) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`tax_authority_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`tax_location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_location` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_location` (
  `tax_location_id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(1) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(200) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`tax_location_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `commercedb`.`tax_location` (`code` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`tax_group_rule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_group_rule` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_group_rule` (
  `tax_group_id` INT NOT NULL,
  `tax_location_id` INT NOT NULL,
  `tax_authority_id` INT NOT NULL,
  `seq_no` INT(2) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(300) NULL,
  `compound_seq_nbr` INT(2) NOT NULL,
  `compound_flag` VARCHAR(1) NOT NULL,
  `trans_level_flag` VARCHAR(1) NOT NULL,
  `type_code` VARCHAR(40) NOT NULL,
  `status` VARCHAR(5) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`),
  CONSTRAINT `fk_tax_group_rule_tax_authority1`
    FOREIGN KEY (`tax_authority_id`)
    REFERENCES `commercedb`.`tax_authority` (`tax_authority_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_group_rule_tax_location1`
    FOREIGN KEY (`tax_location_id`)
    REFERENCES `commercedb`.`tax_location` (`tax_location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_group_rule_tax_group1`
    FOREIGN KEY (`tax_group_id`)
    REFERENCES `commercedb`.`tax_group` (`tax_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_tax_group_rule_tax_location1_idx` ON `commercedb`.`tax_group_rule` (`tax_location_id` ASC);

CREATE INDEX `fk_tax_group_rule_tax_group1_idx` ON `commercedb`.`tax_group_rule` (`tax_group_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`tax_bracket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_bracket` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_bracket` (
  `tax_bracket_id` INT NOT NULL AUTO_INCREMENT,
  `seq_no` INT(2) NOT NULL,
  `breakpoint` DECIMAL(12,2) NULL,
  `tax_amount` DECIMAL(12,2) NULL,
  `created_date` DATETIME NULL,
  `created_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `modified_by` VARCHAR(50) NULL,
  PRIMARY KEY (`tax_bracket_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`tax_rate_rule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_rate_rule` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_rate_rule` (
  `tax_group_id` INT NOT NULL,
  `tax_location_id` INT NOT NULL,
  `tax_authority_id` INT NOT NULL,
  `tax_group_rule_seq` INT(2) NOT NULL,
  `tax_rate_rule_id` INT NOT NULL AUTO_INCREMENT,
  `tax_bracket_id` INT NOT NULL,
  `seq_no` INT(2) NOT NULL,
  `min_taxable_amt` DECIMAL(12,2) NULL,
  `max_taxable_amt` DECIMAL(12,2) NULL,
  `effective_date` DATETIME NOT NULL,
  `expiry_date` DATETIME NOT NULL,
  `percentage` DECIMAL(12,2) NULL,
  `amount` DECIMAL(12,2) NULL,
  `type_code` VARCHAR(30) NOT NULL,
  `status` VARCHAR(5) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`tax_rate_rule_id`),
  CONSTRAINT `fk_tax_rate_rule_tax_bracket1`
    FOREIGN KEY (`tax_bracket_id`)
    REFERENCES `commercedb`.`tax_bracket` (`tax_bracket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_rate_rule_tax_group_rule1`
    FOREIGN KEY (`tax_group_id` , `tax_location_id` , `tax_authority_id` , `tax_group_rule_seq`)
    REFERENCES `commercedb`.`tax_group_rule` (`tax_group_id` , `tax_location_id` , `tax_authority_id` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_tax_rate_rule_tax_bracket1_idx` ON `commercedb`.`tax_rate_rule` (`tax_bracket_id` ASC);

CREATE INDEX `fk_tax_rate_rule_tax_group_rule1_idx` ON `commercedb`.`tax_rate_rule` (`tax_group_id` ASC, `tax_location_id` ASC, `tax_authority_id` ASC, `tax_group_rule_seq` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`tax_location_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_location_mapping` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_location_mapping` (
  `location_id` INT(4) NOT NULL,
  `tax_location_id` INT NOT NULL,
  PRIMARY KEY (`location_id`, `tax_location_id`),
  CONSTRAINT `fk_tax_location_mapping_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_location_mapping_tax_location1`
    FOREIGN KEY (`tax_location_id`)
    REFERENCES `commercedb`.`tax_location` (`tax_location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_tax_location_mapping_tax_location1_idx` ON `commercedb`.`tax_location_mapping` (`tax_location_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`supplier_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`supplier_address` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`supplier_address` (
  `supplier_id` INT NOT NULL,
  `address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`supplier_id`, `address_id`),
  CONSTRAINT `fk_supplier_addresses_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `commercedb`.`supplier` (`supplier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_supplier_addresses_address_master1`
    FOREIGN KEY (`address_id`)
    REFERENCES `commercedb`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_supplier_addresses_address_master1_idx` ON `commercedb`.`supplier_address` (`address_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`register_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`register_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`register_master` (
  `location_id` INT(4) NOT NULL,
  `register_id` INT(3) NOT NULL,
  `name` VARCHAR(100) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `register_id`),
  CONSTRAINT `fk_register_master_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_register_master_location1_idx` ON `commercedb`.`register_master` (`location_id` ASC);


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
  `offline_flag` TINYINT NULL,
  `session_id` VARCHAR(45) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `total` DECIMAL(12,2) NOT NULL,
  `tax_total` DECIMAL(12,2) NOT NULL,
  `round_total` DECIMAL(12,2) NOT NULL,
  `subtotal` DECIMAL(12,2) NOT NULL,
  `cancel_reason_code` VARCHAR(20) NULL,
  `txn_type` VARCHAR(50) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `post_void_flag` TINYINT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_master_register_master1`
    FOREIGN KEY (`register` , `location_id`)
    REFERENCES `commercedb`.`register_master` (`register_id` , `location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_master_register_master1_idx` ON `commercedb`.`txn_master` (`register` ASC, `location_id` ASC);


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

CREATE INDEX `fk_txn_line_item_master_txn_master1_idx` ON `commercedb`.`txn_line_item_master` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_li_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_li_item` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_li_item` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `seq_no` INT(3) NOT NULL,
  `item_id` BIGINT NOT NULL,
  `qty` INT(5) NOT NULL,
  `gross_qty` INT(5) NOT NULL,
  `unit_price` DECIMAL(12,2) NOT NULL,
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
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`, `item_id`),
  CONSTRAINT `fk_txn_li_item_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_li_item_txn_line_item_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    REFERENCES `commercedb`.`txn_line_item_master` (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_li_item_item1_idx` ON `commercedb`.`txn_li_item` (`item_id` ASC);

CREATE INDEX `fk_txn_li_item_txn_line_item_master1_idx` ON `commercedb`.`txn_li_item` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC, `seq_no` ASC);


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
  `amount` DECIMAL(12,2) NULL,
  `type` VARCHAR(30) NULL,
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
  `seq_no` INT(3) NOT NULL,
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
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`),
  CONSTRAINT `fk_txn_li_tax_txn_line_item_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    REFERENCES `commercedb`.`txn_line_item_master` (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_li_tax_txn_line_item_master1_idx` ON `commercedb`.`txn_li_tax` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC, `seq_no` ASC);


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
  `amount` DECIMAL(12,2) NOT NULL,
  `change_flag` TINYINT NOT NULL,
  `type_code` VARCHAR(40) NOT NULL,
  `action_code` VARCHAR(40) NOT NULL,
  `foreign_amount` DECIMAL(12,2) NULL,
  `exchange_rate` DECIMAL(12,2) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`),
  CONSTRAINT `fk_txn_li_tender_txn_line_item_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    REFERENCES `commercedb`.`txn_line_item_master` (`location_id` , `business_date` , `register` , `txn_no` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_li_tender_txn_line_item_master1_idx` ON `commercedb`.`txn_li_tender` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC, `seq_no` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`repository_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`repository_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`repository_master` (
  `repository_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(150) NULL,
  `begin_date_time` DATETIME NOT NULL,
  `end_date_time` DATETIME NULL,
  `status` VARCHAR(15) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`repository_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`tender_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tender_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tender_master` (
  `tender_id` INT(3) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  `description` VARCHAR(150) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `sub_tender_id` INT(3) NULL,
  PRIMARY KEY (`tender_id`),
  CONSTRAINT `fk_tender_master_tender_master1`
    FOREIGN KEY (`sub_tender_id`)
    REFERENCES `commercedb`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_tender_master_tender_master1_idx` ON `commercedb`.`tender_master` (`sub_tender_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`location_repository`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`location_repository` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`location_repository` (
  `repository_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`repository_id`, `location_id`, `tender_id`),
  CONSTRAINT `fk_location_repository_repository_master1`
    FOREIGN KEY (`repository_id`)
    REFERENCES `commercedb`.`repository_master` (`repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_repository_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_repository_tender_master1`
    FOREIGN KEY (`tender_id`)
    REFERENCES `commercedb`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_location_repository_location1_idx` ON `commercedb`.`location_repository` (`location_id` ASC);

CREATE INDEX `fk_location_repository_tender_master1_idx` ON `commercedb`.`location_repository` (`tender_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_tender_count`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_tender_count` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_tender_count` (
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
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `tender_id`),
  CONSTRAINT `fk_txn_tndr_count_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tndr_count_tender_master1`
    FOREIGN KEY (`tender_id`)
    REFERENCES `commercedb`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tndr_count_tender_master1_idx` ON `commercedb`.`txn_tender_count` (`tender_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_tender_denomination`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_tender_denomination` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_tender_denomination` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `denomination` DECIMAL(12,2) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `media_count` INT NOT NULL,
  `difference_amount` DECIMAL(12,2) NULL,
  `difference_media_count` INT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `tender_id`, `denomination`),
  CONSTRAINT `fk_txn_tndr_denomination_txn_tndr_count1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `tender_id`)
    REFERENCES `commercedb`.`txn_tender_count` (`location_id` , `business_date` , `register` , `txn_no` , `tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`txn_tender_control`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_tender_control` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_tender_control` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `txn_type` VARCHAR(50) NOT NULL,
  `reason_code` VARCHAR(30) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `from_repository_id` INT NULL,
  `to_repository_id` INT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_tender_control_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tender_control_location_repository1`
    FOREIGN KEY (`from_repository_id`)
    REFERENCES `commercedb`.`location_repository` (`repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tender_control_location_repository2`
    FOREIGN KEY (`to_repository_id`)
    REFERENCES `commercedb`.`location_repository` (`repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tender_control_txn_master1_idx` ON `commercedb`.`txn_tender_control` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC);

CREATE INDEX `fk_txn_tender_control_location_repository1_idx` ON `commercedb`.`txn_tender_control` (`from_repository_id` ASC);

CREATE INDEX `fk_txn_tender_control_location_repository2_idx` ON `commercedb`.`txn_tender_control` (`to_repository_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`stock_adjustment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`stock_adjustment` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`stock_adjustment` (
  `stock_adjust_id` BIGINT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(150) NULL,
  `reason_code_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `status` VARCHAR(1) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`stock_adjust_id`),
  CONSTRAINT `fk_stock_adjustment_stock_reason_code1`
    FOREIGN KEY (`reason_code_id`)
    REFERENCES `commercedb`.`stock_reason_code` (`reason_code_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_stock_adjustment_stock_reason_code1_idx` ON `commercedb`.`stock_adjustment` (`reason_code_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`stock_adjustment_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`stock_adjustment_items` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`stock_adjustment_items` (
  `stock_adjust_li_id` BIGINT NOT NULL AUTO_INCREMENT,
  `stock_adjust_id` BIGINT NOT NULL,
  `reason_code_id` INT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `qty` INT NOT NULL,
  PRIMARY KEY (`stock_adjust_li_id`),
  CONSTRAINT `fk_stock_adjustment_items_stock_adjustment1`
    FOREIGN KEY (`stock_adjust_id`)
    REFERENCES `commercedb`.`stock_adjustment` (`stock_adjust_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
