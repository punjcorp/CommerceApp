-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

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
