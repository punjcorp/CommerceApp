-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`item_price`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_price` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_price` (
  `item_price_id` BIGINT NOT NULL AUTO_INCREMENT,
  `item_id` BIGINT NOT NULL,
  `price_change_type` VARCHAR(5) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  `item_price` VARCHAR(45) NOT NULL DEFAULT '0.0',
  `status` VARCHAR(1) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`item_price_id`),
  CONSTRAINT `fk_item_price_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_price_item1_idx` ON `commercedb`.`item_price` (`item_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`item_price_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`item_price_history` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`item_price_history` (
  `item_price_id` BIGINT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `price_change_type` VARCHAR(5) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  `item_price` VARCHAR(45) NOT NULL DEFAULT '0.0',
  `status` VARCHAR(1) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `archived_date` DATETIME NOT NULL,
  PRIMARY KEY (`item_price_id`),
  CONSTRAINT `fk_item_price_history_item_price1`
    FOREIGN KEY (`item_price_id`)
    REFERENCES `commercedb`.`item_price` (`item_price_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_item_price_history_item_price1_idx` ON `commercedb`.`item_price_history` (`item_price_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
