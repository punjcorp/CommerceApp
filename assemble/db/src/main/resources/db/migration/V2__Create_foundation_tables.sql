-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;


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
  `default_tender` VARCHAR(30) NOT NULL DEFAULT 'CASH',
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
-- Table `commercedb`.`style_generator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`style_generator` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`style_generator` (
  `style_id` BIGINT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`style_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1000000;


-- -----------------------------------------------------
-- Table `commercedb`.`sku_generator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`sku_generator` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`sku_generator` (
  `style_id` BIGINT NOT NULL,
  `sku_id` BIGINT ZEROFILL NOT NULL,
  `status` VARCHAR(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`sku_id`, `style_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`seq_generator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`seq_generator` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`seq_generator` (
  `name` VARCHAR(100) NOT NULL,
  `value` BIGINT NOT NULL,
  PRIMARY KEY (`name`))
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
-- Table `commercedb`.`reason_codes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`reason_codes` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`reason_codes` (
  `reason_code_id` INT NOT NULL AUTO_INCREMENT,
  `reason_name` VARCHAR(100) NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `description` VARCHAR(150) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`reason_code_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`uom_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`uom_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`uom_master` (
  `uom_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(15) NOT NULL,
  `description` VARCHAR(100) NULL,
  `type` VARCHAR(45) NOT NULL,
  `parent_uom_id` INT NULL,
  `formula_to_parent_uom` VARCHAR(45) NULL,
  `is_primary` TINYINT NOT NULL DEFAULT 0,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`uom_id`),
  INDEX `fk_uom_master_uom_master1_idx` (`parent_uom_id` ASC),
  CONSTRAINT `fk_uom_master_uom_master1`
    FOREIGN KEY (`parent_uom_id`)
    REFERENCES `commercedb`.`uom_master` (`uom_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `commercedb`.`denomination_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`denomination_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`denomination_master` (
  `denomination_id` INT NOT NULL AUTO_INCREMENT,
  `currency_code` VARCHAR(5) NOT NULL DEFAULT 'INR',
  `code` VARCHAR(15) NOT NULL,
  `value` DECIMAL NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`denomination_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `code_UNIQUE` ON `commercedb`.`denomination_master` (`code` ASC);




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
