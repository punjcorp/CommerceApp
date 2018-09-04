-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`sale_txn_invoices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`sale_txn_invoices` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`sale_txn_invoices` (
  `invoice_no` BIGINT NOT NULL AUTO_INCREMENT,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  PRIMARY KEY (`invoice_no`),
  INDEX `fk_sale_txn_invoices_txn_master1_idx` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC),
  CONSTRAINT `fk_sale_txn_invoices_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`return_txn_vouchers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`return_txn_vouchers` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`return_txn_vouchers` (
  `voucher_no` BIGINT NOT NULL AUTO_INCREMENT,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  PRIMARY KEY (`voucher_no`),
  INDEX `fk_return_txn_voucher_txn_master1_idx` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC),
  CONSTRAINT `fk_return_txn_voucher_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`expense_txn_vouchers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`expense_txn_vouchers` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`expense_txn_vouchers` (
  `voucher_no` BIGINT NOT NULL AUTO_INCREMENT,
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  PRIMARY KEY (`voucher_no`),
  INDEX `fk_expenses_txn_vouchers_txn_master1_idx` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC),
  UNIQUE INDEX `voucher_no_UNIQUE` (`voucher_no` ASC),
  UNIQUE INDEX `location_id_UNIQUE` (`location_id` ASC),
  UNIQUE INDEX `business_date_UNIQUE` (`business_date` ASC),
  UNIQUE INDEX `txn_no_UNIQUE` (`txn_no` ASC),
  UNIQUE INDEX `register_UNIQUE` (`register` ASC),
  CONSTRAINT `fk_expenses_txn_vouchers_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `pi_pos_industry`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
