-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_hierarchy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_hierarchy` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_hierarchy` (
  `hierarchy_id` INT NOT NULL AUTO_INCREMENT,
  `level_code` VARCHAR(20) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(250) NULL,
  `sort_order` INT NOT NULL DEFAULT 1,
  `hidden_flag` VARCHAR(1) NOT NULL DEFAULT 'N',
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `parent_id` INT NULL,
  PRIMARY KEY (`hierarchy_id`),
  CONSTRAINT `fk_item_hierarchy_item_hierarchy1`
    FOREIGN KEY (`parent_id`)
    REFERENCES `pi_pos_industry`.`item_hierarchy` (`hierarchy_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_hierarchy_item_hierarchy1_idx` ON `pi_pos_industry`.`item_hierarchy` (`parent_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item` (
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
    REFERENCES `pi_pos_industry`.`item_hierarchy` (`hierarchy_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_item_hierarchy1_idx` ON `pi_pos_industry`.`item` (`hierarchy_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_options`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_options` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_options` (
  `item_id` BIGINT NOT NULL,
  `uom` VARCHAR(20) NOT NULL,
  `discount_flag` TINYINT NOT NULL DEFAULT 1,
  `tax_flag` TINYINT NOT NULL DEFAULT 1,
  `tax_inclusive_flag` TINYINT NOT NULL,
  `ask_qty_flag` TINYINT NOT NULL DEFAULT 1,
  `ask_price_flag` TINYINT NOT NULL DEFAULT 0,
  `unit_cost` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
  `suggested_price` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
  `current_price` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
  `compare_at_price` DECIMAL(12,2) NULL,
  `max_retail_price` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
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
  `hsn_no` VARCHAR(15) NULL,
  `next_level_created` VARCHAR(2) NULL DEFAULT 'N',
  PRIMARY KEY (`item_id`),
  CONSTRAINT `fk_item_options_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_options_item1_idx` ON `pi_pos_industry`.`item_options` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`attribute_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`attribute_master` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`attribute_master` (
  `attribute_id` BIGINT NOT NULL AUTO_INCREMENT,
  `attr_code` VARCHAR(5) NOT NULL,
  `attr_name` VARCHAR(80) NOT NULL,
  `attr_description` VARCHAR(150) NULL,
  `value_code` VARCHAR(50) NOT NULL,
  `value_name` VARCHAR(80) NOT NULL,
  `value_description` VARCHAR(150) NULL,
  `value_seq_no` INT(2) NOT NULL,
  PRIMARY KEY (`attribute_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `index2` ON `pi_pos_industry`.`attribute_master` (`attr_code` ASC, `value_code` ASC, `value_seq_no` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_attributes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_attributes` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_attributes` (
  `item_id` BIGINT NOT NULL,
  `attribute_id` BIGINT NOT NULL,
  PRIMARY KEY (`item_id`, `attribute_id`),
  CONSTRAINT `fk_item_attributes_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_style_attribute_values_attribute_master1`
    FOREIGN KEY (`attribute_id`)
    REFERENCES `pi_pos_industry`.`attribute_master` (`attribute_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_style_attribute_values_attribute_master1_idx` ON `pi_pos_industry`.`item_attributes` (`attribute_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_images`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_images` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_images` (
  `item_image_id` BIGINT NOT NULL AUTO_INCREMENT,
  `item_id` BIGINT NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `image_type` VARCHAR(120) NOT NULL,
  `image_url` VARCHAR(300) NOT NULL,
  `image_data` MEDIUMBLOB NOT NULL,
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`item_image_id`),
  CONSTRAINT `fk_item_images_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_images_item1_idx` ON `pi_pos_industry`.`item_images` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_kit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_kit` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_kit` (
  `item_id` BIGINT NOT NULL,
  `component_item_id` BIGINT NOT NULL,
  `qty` INT NOT NULL,
  `display_order` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`item_id`, `component_item_id`),
  CONSTRAINT `fk_item_kit_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_kit_item1_idx` ON `pi_pos_industry`.`item_kit` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_substitute`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_substitute` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_substitute` (
  `item_id` BIGINT NOT NULL,
  `substitute_item_id` BIGINT NOT NULL,
  `begin_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL,
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`item_id`, `substitute_item_id`),
  CONSTRAINT `fk_item_substitute_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_related`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_related` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_related` (
  `item_id` BIGINT NOT NULL,
  `related_item_id` BIGINT NOT NULL,
  `mandatory` VARCHAR(1) NOT NULL DEFAULT 'Y',
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`item_id`, `related_item_id`),
  CONSTRAINT `fk_item_related_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_related_item1_idx` ON `pi_pos_industry`.`item_related` (`item_id` ASC);




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
