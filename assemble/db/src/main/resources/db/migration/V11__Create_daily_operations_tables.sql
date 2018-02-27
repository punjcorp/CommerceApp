-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`repository_master`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`repository_master` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`repository_master` (
  `repository_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(150) NULL,
  `begin_date_time` DATETIME NOT NULL,
  `end_date_time` DATETIME NULL,
  `status` VARCHAR(15) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`repository_id`))
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
-- Table `commercedb`.`location_repository`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`location_repository` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`location_repository` (
  `repository_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`repository_id`, `location_id`, `tender_id`),
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

CREATE INDEX `fk_location_repository_location1_idx` ON `commercedb`.`location_repository` (`location_id` ASC);

CREATE INDEX `fk_location_repository_tender_master1_idx` ON `commercedb`.`location_repository` (`tender_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_tender_count`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_tender_count` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_tender_count` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `txn_type` VARCHAR(30) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `media_count` BIGINT NOT NULL,
  `actual_amount` DECIMAL(12,2) NULL,
  `actual_media_count` BIGINT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `tender_id`),
  CONSTRAINT `fk_txn_tndr_count_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tndr_count_tender_master1`
    FOREIGN KEY (`tender_id`)
    REFERENCES `commercedb`.`tender_master` (`tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tndr_count_tender_master1_idx` ON `commercedb`.`txn_tender_count` (`tender_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`txn_tender_denomination`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_tender_denomination` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_tender_denomination` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `tender_id` INT(3) NOT NULL,
  `denomination` DECIMAL(12,2) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `media_count` INT NOT NULL,
  `difference_amount` DECIMAL(12,2) NULL,
  `difference_media_count` INT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`, `tender_id`, `denomination`),
  CONSTRAINT `fk_txn_tndr_denomination_txn_tndr_count1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no` , `tender_id`)
    REFERENCES `commercedb`.`txn_tender_count` (`location_id` , `business_date` , `register` , `txn_no` , `tender_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`txn_tender_control`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`txn_tender_control` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`txn_tender_control` (
  `location_id` INT(4) NOT NULL,
  `business_date` DATETIME NOT NULL,
  `register` INT(3) NOT NULL,
  `txn_no` INT(5) NOT NULL,
  `txn_type` VARCHAR(50) NOT NULL,
  `reason_code` VARCHAR(30) NOT NULL,
  `amount` DECIMAL(12,2) NOT NULL,
  `from_repository_id` INT NULL,
  `to_repository_id` INT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`location_id`, `business_date`, `register`, `txn_no`),
  CONSTRAINT `fk_txn_tender_control_txn_master1`
    FOREIGN KEY (`location_id` , `business_date` , `register` , `txn_no`)
    REFERENCES `commercedb`.`txn_master` (`location_id` , `business_date` , `register` , `txn_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tender_control_location_repository1`
    FOREIGN KEY (`from_repository_id`)
    REFERENCES `commercedb`.`location_repository` (`repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tender_control_location_repository2`
    FOREIGN KEY (`to_repository_id`)
    REFERENCES `commercedb`.`location_repository` (`repository_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_txn_tender_control_txn_master1_idx` ON `commercedb`.`txn_tender_control` (`location_id` ASC, `business_date` ASC, `register` ASC, `txn_no` ASC);

CREATE INDEX `fk_txn_tender_control_location_repository1_idx` ON `commercedb`.`txn_tender_control` (`from_repository_id` ASC);

CREATE INDEX `fk_txn_tender_control_location_repository2_idx` ON `commercedb`.`txn_tender_control` (`to_repository_id` ASC);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
