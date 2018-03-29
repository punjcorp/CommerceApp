-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;



-- -----------------------------------------------------
-- Table `commercedb`.`account_head`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`account_head` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`account_head` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `entity_type` VARCHAR(50) NOT NULL,
  `entity_id` BIGINT NOT NULL,
  `advance_amount` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `due_amount` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`account_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`account_journal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`account_journal` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`account_journal` (
  `journal_id` BIGINT NOT NULL AUTO_INCREMENT,
  `account_id` INT NOT NULL,
  `journal_type` VARCHAR(50) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`journal_id`),
  CONSTRAINT `fk_account_journal_account_head1`
    FOREIGN KEY (`account_id`)
    REFERENCES `commercedb`.`account_head` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`account_journal_tender`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`account_journal_tender` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`account_journal_tender` (
  `journal_id` BIGINT NOT NULL,
  `tender_id` INT NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `bank_name` VARCHAR(100) NULL,
  `branch_name` VARCHAR(100) NULL,
  `account_no` VARCHAR(50) NULL,
  `description` VARCHAR(150) NULL,
  PRIMARY KEY (`journal_id`, `tender_id`),
  CONSTRAINT `fk_account_journal_tender_account_journal1`
    FOREIGN KEY (`journal_id`)
    REFERENCES `commercedb`.`account_journal` (`journal_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

    
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

