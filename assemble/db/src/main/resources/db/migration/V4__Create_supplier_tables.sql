-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`supplier`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`supplier` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`supplier` (
  `supplier_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `phone1` VARCHAR(20) NOT NULL,
  `phone2` VARCHAR(20) NULL,
  `email` VARCHAR(80) NULL,
  `gst_no` VARCHAR(20) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`supplier_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`supplier_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`supplier_item` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`supplier_item` (
  `item_id` BIGINT NOT NULL,
  `supplier_id` INT NOT NULL,
  `unit_cost` DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (`item_id`, `supplier_id`),
  CONSTRAINT `fk_supplier_item_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_supplier_item_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `pi_pos_industry`.`supplier` (`supplier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_supplier_item_supplier1_idx` ON `pi_pos_industry`.`supplier_item` (`supplier_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`supplier_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`supplier_address` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`supplier_address` (
  `supplier_id` INT NOT NULL,
  `address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`supplier_id`, `address_id`),
  CONSTRAINT `fk_supplier_addresses_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `pi_pos_industry`.`supplier` (`supplier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_supplier_addresses_address_master1`
    FOREIGN KEY (`address_id`)
    REFERENCES `pi_pos_industry`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_supplier_addresses_address_master1_idx` ON `pi_pos_industry`.`supplier_address` (`address_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
