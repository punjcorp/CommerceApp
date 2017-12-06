-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;


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



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
