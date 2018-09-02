-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`customer` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`customer` (
  `customer_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `email` VARCHAR(80) NULL,
  `phone` VARCHAR(20) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`customer_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`customer_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`customer_address` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`customer_address` (
  `customer_id` BIGINT NOT NULL,
  `address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`customer_id`, `address_id`),
  INDEX `fk_table1_address_master1_idx` (`address_id` ASC),
  CONSTRAINT `fk_table1_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `pi_pos_industry`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_table1_address_master1`
    FOREIGN KEY (`address_id`)
    REFERENCES `pi_pos_industry`.`address_master` (`address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
