-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_stock` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_stock` (
  `item_id` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `total_qty` INT NOT NULL DEFAULT 0,
  `non_sellable_qty` INT NOT NULL DEFAULT 0,
  `reserved_qty` INT NOT NULL DEFAULT 0,
  `stock_on_hand` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`item_id`, `location_id`),
  CONSTRAINT `fk_item_stock_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `pi_pos_industry`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_stock_location1_idx` ON `pi_pos_industry`.`item_stock` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`stock_bucket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`stock_bucket` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`stock_bucket` (
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
-- Table `pi_pos_industry`.`stock_reason_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`stock_reason_code` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`stock_reason_code` (
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
    REFERENCES `pi_pos_industry`.`stock_bucket` (`stock_bucket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_reason_code_stock_bucket2`
    FOREIGN KEY (`to_bucket_id`)
    REFERENCES `pi_pos_industry`.`stock_bucket` (`stock_bucket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_stock_reason_code_stock_bucket1_idx` ON `pi_pos_industry`.`stock_reason_code` (`from_bucket_id` ASC);

CREATE INDEX `fk_stock_reason_code_stock_bucket2_idx` ON `pi_pos_industry`.`stock_reason_code` (`to_bucket_id` ASC);

-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_stock_journal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_stock_journal` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_stock_journal` (
  `stock_journal_id` BIGINT NOT NULL AUTO_INCREMENT,
  `item_id` BIGINT NOT NULL,
  `reason_code_id` INT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `functionality` VARCHAR(45) NOT NULL,
  `qty` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`stock_journal_id`),
  CONSTRAINT `fk_stock_journal_stock_reason_code1`
    FOREIGN KEY (`reason_code_id`)
    REFERENCES `pi_pos_industry`.`stock_reason_code` (`reason_code_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_journal_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_journal_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `pi_pos_industry`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_stock_journal_stock_reason_code1_idx` ON `pi_pos_industry`.`item_stock_journal` (`reason_code_id` ASC);

CREATE INDEX `fk_stock_journal_item1_idx` ON `pi_pos_industry`.`item_stock_journal` (`item_id` ASC);

CREATE INDEX `fk_item_stock_journal_location1_idx` ON `pi_pos_industry`.`item_stock_journal` (`location_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`item_stock_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`item_stock_details` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`item_stock_details` (
  `item_id` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `stock_bucket_id` INT NOT NULL,
  `total_qty` INT NOT NULL,
  PRIMARY KEY (`item_id`, `location_id`, `stock_bucket_id`),
  CONSTRAINT `fk_item_stock_details_item_stock1`
    FOREIGN KEY (`item_id`)
    REFERENCES `pi_pos_industry`.`item_stock` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_details_stock_bucket1`
    FOREIGN KEY (`stock_bucket_id`)
    REFERENCES `pi_pos_industry`.`stock_bucket` (`stock_bucket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_details_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `pi_pos_industry`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_stock_details_stock_bucket1_idx` ON `pi_pos_industry`.`item_stock_details` (`stock_bucket_id` ASC);

CREATE INDEX `fk_item_stock_details_location1_idx` ON `pi_pos_industry`.`item_stock_details` (`location_id` ASC);



-- -----------------------------------------------------
-- Table `pi_pos_industry`.`stock_adjustment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`stock_adjustment` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`stock_adjustment` (
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
    REFERENCES `pi_pos_industry`.`stock_reason_code` (`reason_code_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_stock_adjustment_stock_reason_code1_idx` ON `pi_pos_industry`.`stock_adjustment` (`reason_code_id` ASC);


-- -----------------------------------------------------
-- Table `pi_pos_industry`.`stock_adjustment_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pi_pos_industry`.`stock_adjustment_items` ;

CREATE TABLE IF NOT EXISTS `pi_pos_industry`.`stock_adjustment_items` (
  `stock_adjust_li_id` BIGINT NOT NULL AUTO_INCREMENT,
  `stock_adjust_id` BIGINT NOT NULL,
  `reason_code_id` INT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `item_desc` VARCHAR(150) NOT NULL,
  `qty` INT NOT NULL,
  PRIMARY KEY (`stock_adjust_li_id`),
  CONSTRAINT `fk_stock_adjustment_items_stock_adjustment1`
    FOREIGN KEY (`stock_adjust_id`)
    REFERENCES `pi_pos_industry`.`stock_adjustment` (`stock_adjust_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
