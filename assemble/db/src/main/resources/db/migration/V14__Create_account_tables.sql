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
  `location_id` INT(4) NOT NULL,
  `entity_type` VARCHAR(50) NOT NULL,
  `entity_id` BIGINT NOT NULL,
  `advance_amount` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `due_amount` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`account_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `account_head_unique_idx` ON `commercedb`.`account_head` (`location_id` ASC, `entity_type` ASC, `entity_id` ASC);


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
  `comments` VARCHAR(150) NULL,
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







-- -----------------------------------------------------
-- Table `commercedb`.`repository_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`repository_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`repository_master` (
  `repository_id` INT NOT NULL AUTO_INCREMENT,
  `tender_id` INT(3) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(150) NULL,
  `begin_date_time` DATETIME NOT NULL,
  `end_date_time` DATETIME NULL,
  `status` VARCHAR(15) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`repository_id`),
  CONSTRAINT `fk_repository_master_tender_master1`
    FOREIGN KEY (`tender_id`)
    REFERENCES `commercedb`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_repository_master_tender_master1_idx` ON `commercedb`.`repository_master` (`tender_id` ASC);



-- -----------------------------------------------------
-- Table `commercedb`.`daily_totals`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`daily_totals` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`daily_totals` (
  `daily_totals_id` BIGINT NOT NULL AUTO_INCREMENT,
  `location_id` INT NOT NULL,
  `register_id` VARCHAR(45) NULL,
  `business_date` DATETIME NOT NULL,
  `total_txn_count` INT NULL,
  `total_sales_count` INT NULL,
  `total_returns_count` INT NULL,
  `total_txn_amount` DECIMAL(12,2) NULL,
  `total_sales_amount` DECIMAL(12,2) NULL,
  `total_returns_amount` DECIMAL(12,2) NULL,
  `sod_amount` DECIMAL(12,2) NULL,
  `eod_amount` DECIMAL(12,2) NULL,
  `total_no_sales_amount` DECIMAL(12,2) NULL,
  `total_no_sales_count` INT NULL,
  PRIMARY KEY (`daily_totals_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`ledger_journal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`ledger_journal` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`ledger_journal` (
  `ledger_journal_id` BIGINT NOT NULL AUTO_INCREMENT,
  `business_date` DATETIME NOT NULL,
  `txn_no` VARCHAR(50) NOT NULL,
  `txn_type` VARCHAR(10) NOT NULL,
  `location_id` INT NOT NULL,
  `amount` DECIMAL NOT NULL,
  `action_code` VARCHAR(45) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`ledger_journal_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `business_date_UNIQUE` ON `commercedb`.`ledger_journal` (`business_date` ASC, `txn_no` ASC, `txn_type` ASC, `location_id` ASC, `action_code` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`location_repo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`location_repo` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`location_repo` (
  `location_repository_id` BIGINT NOT NULL AUTO_INCREMENT,
  `repository_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `reconcilation_flag` TINYINT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_repository_id`),
  CONSTRAINT `fk_location_repository_repository_master1`
    FOREIGN KEY (`repository_id`)
    REFERENCES `commercedb`.`repository_master` (`repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_repository_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_repository_tender_master1`
    FOREIGN KEY (`tender_id`)
    REFERENCES `commercedb`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_location_repository_location1_idx` ON `commercedb`.`location_repo` (`location_id` ASC);

CREATE INDEX `fk_location_repository_tender_master1_idx` ON `commercedb`.`location_repo` (`tender_id` ASC);




-- -----------------------------------------------------
-- Table `commercedb`.`daily_repository`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`daily_repository` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`daily_repository` (
  `daily_repository_id` BIGINT NOT NULL AUTO_INCREMENT,
  `location_repository_id` BIGINT NOT NULL,
  `business_date` DATETIME NOT NULL,
  `amount` DECIMAL NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`daily_repository_id`),
  CONSTRAINT `fk_location_repository_daily_location_repository1`
    FOREIGN KEY (`location_repository_id`)
    REFERENCES `commercedb`.`location_repo` (`location_repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


    
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

