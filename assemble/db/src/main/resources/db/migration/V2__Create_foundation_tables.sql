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
  `style_id` MEDIUMINT(7) ZEROFILL NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`style_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1000000;

alter table commercedb.style_generator auto_increment=1000000;

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
-- Table `commercedb`.`seq_generator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`seq_generator` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`seq_generator` (
  `name` VARCHAR(100) NOT NULL,
  `value` BIGINT NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
