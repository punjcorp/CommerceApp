-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;


-- -----------------------------------------------------
-- Table `commercedb`.`tax_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_group` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_group` (
  `tax_group_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(300) NULL,
  `aggregated_rate` DECIMAL(12,2) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`tax_group_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`tax_authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_authority` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_authority` (
  `tax_authority_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `rounding_code` VARCHAR(5) NOT NULL,
  `rounding_digit_qty` INT(2) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`tax_authority_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`tax_location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_location` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_location` (
  `tax_location_id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(1) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(200) NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`tax_location_id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`tax_group_rule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_group_rule` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_group_rule` (
  `tax_group_id` INT NOT NULL,
  `tax_location_id` INT NOT NULL,
  `tax_authority_id` INT NOT NULL,
  `seq_no` INT(2) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `description` VARCHAR(300) NULL,
  `compound_seq_nbr` INT(2) NOT NULL,
  `compound_flag` VARCHAR(1) NOT NULL,
  `trans_level_flag` VARCHAR(1) NOT NULL,
  `type_code` VARCHAR(40) NOT NULL,
  `status` VARCHAR(5) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`),
  CONSTRAINT `fk_tax_group_rule_tax_authority1`
    FOREIGN KEY (`tax_authority_id`)
    REFERENCES `commercedb`.`tax_authority` (`tax_authority_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_group_rule_tax_location1`
    FOREIGN KEY (`tax_location_id`)
    REFERENCES `commercedb`.`tax_location` (`tax_location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_group_rule_tax_group1`
    FOREIGN KEY (`tax_group_id`)
    REFERENCES `commercedb`.`tax_group` (`tax_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_tax_group_rule_tax_location1_idx` ON `commercedb`.`tax_group_rule` (`tax_location_id` ASC);

CREATE INDEX `fk_tax_group_rule_tax_group1_idx` ON `commercedb`.`tax_group_rule` (`tax_group_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`tax_bracket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_bracket` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_bracket` (
  `tax_bracket_id` INT NOT NULL AUTO_INCREMENT,
  `seq_no` INT(2) NOT NULL,
  `breakpoint` DECIMAL(12,2) NULL,
  `tax_amount` DECIMAL(12,2) NULL,
  `created_date` DATETIME NULL,
  `created_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  `modified_by` VARCHAR(50) NULL,
  PRIMARY KEY (`tax_bracket_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`tax_rate_rule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_rate_rule` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_rate_rule` (
  `tax_group_id` INT NOT NULL,
  `tax_location_id` INT NOT NULL,
  `tax_authority_id` INT NOT NULL,
  `tax_group_rule_seq` INT(2) NOT NULL,
  `tax_rate_rule_id` INT NOT NULL AUTO_INCREMENT,
  `tax_bracket_id` INT NOT NULL,
  `seq_no` INT(2) NOT NULL,
  `min_taxable_amt` DECIMAL(12,2) NULL,
  `max_taxable_amt` DECIMAL(12,2) NULL,
  `effective_date` DATETIME NOT NULL,
  `expiry_date` DATETIME NOT NULL,
  `percentage` DECIMAL(12,2) NULL,
  `amount` DECIMAL(12,2) NULL,
  `type_code` VARCHAR(30) NOT NULL,
  `status` VARCHAR(5) NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `modified_by` VARCHAR(50) NULL,
  `modified_date` DATETIME NULL,
  PRIMARY KEY (`tax_rate_rule_id`),
  CONSTRAINT `fk_tax_rate_rule_tax_bracket1`
    FOREIGN KEY (`tax_bracket_id`)
    REFERENCES `commercedb`.`tax_bracket` (`tax_bracket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_rate_rule_tax_group_rule1`
    FOREIGN KEY (`tax_group_id` , `tax_location_id` , `tax_authority_id` , `tax_group_rule_seq`)
    REFERENCES `commercedb`.`tax_group_rule` (`tax_group_id` , `tax_location_id` , `tax_authority_id` , `seq_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_tax_rate_rule_tax_bracket1_idx` ON `commercedb`.`tax_rate_rule` (`tax_bracket_id` ASC);

CREATE INDEX `fk_tax_rate_rule_tax_group_rule1_idx` ON `commercedb`.`tax_rate_rule` (`tax_group_id` ASC, `tax_location_id` ASC, `tax_authority_id` ASC, `tax_group_rule_seq` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`tax_location_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`tax_location_mapping` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`tax_location_mapping` (
  `location_id` INT(4) NOT NULL,
  `tax_location_id` INT NOT NULL,
  PRIMARY KEY (`location_id`, `tax_location_id`),
  CONSTRAINT `fk_tax_location_mapping_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_location_mapping_tax_location1`
    FOREIGN KEY (`tax_location_id`)
    REFERENCES `commercedb`.`tax_location` (`tax_location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_tax_location_mapping_tax_location1_idx` ON `commercedb`.`tax_location_mapping` (`tax_location_id` ASC);



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
