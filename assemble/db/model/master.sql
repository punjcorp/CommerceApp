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
-- Table `commercedb`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`user_role` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`user_role` (
  `username` VARCHAR(50) NOT NULL,
  `role_id` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `begin_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  PRIMARY KEY (`username`, `role_id`),
  CONSTRAINT `fk_user_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `commercedb`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_user1`
    FOREIGN KEY (`username`)
    REFERENCES `commercedb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_role_user1_idx` ON `commercedb`.`user_role` (`username` ASC);


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
-- Table `commercedb`.`timestamps`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`timestamps` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`timestamps` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL);


-- -----------------------------------------------------
-- Table `commercedb`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`category` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`category` (
  `category_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`category_id`));


-- -----------------------------------------------------
-- Table `commercedb`.`item_hierarchy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_hierarchy` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_hierarchy` (
  `hierarchy_id` INT NOT NULL AUTO_INCREMENT,
  `dept_name` VARCHAR(100) NOT NULL,
  `sub_dept_name` VARCHAR(100) NULL,
  `class_name` VARCHAR(100) NULL,
  `sub_class_name` VARCHAR(100) NULL,
  PRIMARY KEY (`hierarchy_id`))
ENGINE = InnoDB;


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
  `discount_flag` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `tax_flag` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `ask_qty_flag` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `ask_price_flag` VARCHAR(1) NOT NULL DEFAULT 'N',
  `unit_cost` DECIMAL NOT NULL DEFAULT 0.0,
  `suggested_price` DECIMAL NOT NULL DEFAULT 0.0,
  `current_price` DECIMAL NOT NULL DEFAULT 0.0,
  `compare_at_price` DECIMAL NOT NULL,
  `return_flag` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `desc_flag` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `related_item_flag` VARCHAR(1) NOT NULL DEFAULT 'N',
  `tax_group_id` INT NOT NULL,
  `restocking_fee` DECIMAL NULL,
  `price_change_flag` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `non_merch_flag` VARCHAR(1) NOT NULL DEFAULT 'N',
  `min_age_flag` VARCHAR(1) NOT NULL DEFAULT 'N',
  `stock_status` VARCHAR(20) NOT NULL,
  `customer_prompt` VARCHAR(1) NOT NULL DEFAULT 'N',
  `shipping_weight` DECIMAL NULL,
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
-- Table `commercedb`.`item_dimensions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_dimensions` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_dimensions` (
  `item_id` BIGINT NOT NULL,
  `size_code` VARCHAR(30) NOT NULL,
  `dim_name` VARCHAR(80) NOT NULL,
  `dim_value` VARCHAR(80) NOT NULL,
  `dim_description` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`item_id`, `size_code`),
  CONSTRAINT `fk_item_dimensions_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_dimensions_item1_idx` ON `commercedb`.`item_dimensions` (`item_id` ASC);


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
  `address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`supplier_id`),
  CONSTRAINT `fk_supplier_address_master1`
    FOREIGN KEY (`address_id`)
    REFERENCES `commercedb`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_supplier_address_master1_idx` ON `commercedb`.`supplier` (`address_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`supplier_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`supplier_item` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`supplier_item` (
  `item_id` BIGINT NOT NULL,
  `supplier_id` INT NOT NULL,
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
  `created_by` VARCHAR(50) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `total_amount` DECIMAL NOT NULL DEFAULT 0.0,
  `paid_amount` DECIMAL NOT NULL DEFAULT 0.0,
  `discount_amount` DECIMAL NOT NULL DEFAULT 0.0,
  `tax_amount` DECIMAL NOT NULL DEFAULT 0.0,
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order_items` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order_items` (
  `order_id` BIGINT NOT NULL,
  `supplier_id` INT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `ordered_qty` INT NOT NULL,
  `cost_amount` DECIMAL NOT NULL,
  `total_cost` DECIMAL NOT NULL,
  `delievered_qty` INT NULL,
  `delievered_date` DATETIME NULL,
  `discount_amount` DECIMAL NULL,
  `total_discount_amount` DECIMAL NULL,
  PRIMARY KEY (`order_id`, `supplier_id`, `item_id`, `cost_amount`),
  CONSTRAINT `fk_purchase_order_items_purchase_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `commercedb`.`purchase_order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `commercedb`.`supplier` (`supplier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_purchase_order_items_purchase_order1_idx` ON `commercedb`.`purchase_order_items` (`order_id` ASC);

CREATE INDEX `fk_purchase_order_items_supplier1_idx` ON `commercedb`.`purchase_order_items` (`supplier_id` ASC);

CREATE INDEX `fk_purchase_order_items_item1_idx` ON `commercedb`.`purchase_order_items` (`item_id` ASC);


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
  `total_qty` INT NOT NULL,
  `non_sellable_qty` INT NOT NULL DEFAULT 0,
  `reserved_qty` INT NOT NULL DEFAULT 0,
  `stock_on_hand` INT NOT NULL,
  PRIMARY KEY (`item_id`),
  CONSTRAINT `fk_item_stock_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


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
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_stock_journal_stock_reason_code1_idx` ON `commercedb`.`item_stock_journal` (`reason_code_id` ASC);

CREATE INDEX `fk_stock_journal_item1_idx` ON `commercedb`.`item_stock_journal` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_stock_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_stock_details` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_stock_details` (
  `item_id` BIGINT NOT NULL,
  `stock_bucket_id` INT NOT NULL,
  `total_qty` INT NOT NULL,
  PRIMARY KEY (`item_id`, `stock_bucket_id`),
  CONSTRAINT `fk_item_stock_details_item_stock1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item_stock` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_details_stock_bucket1`
    FOREIGN KEY (`stock_bucket_id`)
    REFERENCES `commercedb`.`stock_bucket` (`stock_bucket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_stock_details_stock_bucket1_idx` ON `commercedb`.`item_stock_details` (`stock_bucket_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_price`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_price` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_price` (
  `item_price_id` BIGINT NOT NULL AUTO_INCREMENT,
  `item_id` BIGINT NOT NULL,
  `price_change_type` VARCHAR(5) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  `item_price` VARCHAR(45) NOT NULL DEFAULT '0.0',
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
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_price_item1_idx` ON `commercedb`.`item_price` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_price_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_price_history` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_price_history` (
  `item_price_id` BIGINT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `price_change_type` VARCHAR(5) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  `item_price` VARCHAR(45) NOT NULL DEFAULT '0.0',
  `status` VARCHAR(1) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `archived_date` DATETIME NOT NULL,
  PRIMARY KEY (`item_price_id`),
  CONSTRAINT `fk_item_price_history_item_price1`
    FOREIGN KEY (`item_price_id`)
    REFERENCES `commercedb`.`item_price` (`item_price_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_price_history_item_price1_idx` ON `commercedb`.`item_price_history` (`item_price_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
