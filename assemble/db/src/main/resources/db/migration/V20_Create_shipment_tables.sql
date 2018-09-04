-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`txn_shipment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`txn_shipment`;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`txn_shipment` (
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
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_shipment_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
