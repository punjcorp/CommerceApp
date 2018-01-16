-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order` (
  `order_id` BIGINT NOT NULL AUTO_INCREMENT,
  `supplier_id` INT NOT NULL,
  `created_by` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `total_estimated_cost` DECIMAL NOT NULL,
  `discount_amount` DECIMAL NULL DEFAULT 0.00,
  `tax_amount` DECIMAL NULL DEFAULT 0.00,
  `total_amount` DECIMAL NULL DEFAULT 0.00,
  `paid_amount` DECIMAL NULL DEFAULT 0.00,
  `status` VARCHAR(1) NOT NULL,
  `invoice` BLOB NULL,
  `modified_date` DATETIME NULL,
  `modified_by` VARCHAR(50) NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `fk_purchase_order_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `commercedb`.`supplier` (`supplier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_purchase_order_supplier1_idx` ON `commercedb`.`purchase_order` (`supplier_id` ASC);


-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order_items` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order_items` (
  `order_id` BIGINT NOT NULL,
  `location_id` INT(4) NOT NULL,
  `item_id` BIGINT NOT NULL,
  `ordered_qty` INT NOT NULL,
  `cost_amount` DECIMAL NOT NULL DEFAULT 0.00,
  `total_cost` DECIMAL NOT NULL DEFAULT 0.00,
  `delievered_date` DATETIME NULL,
  `delievered_qty` INT NULL,
  `cost_actual_amount` DECIMAL NULL DEFAULT 0.00,
  `total_actual_cost` DECIMAL NULL DEFAULT 0.00,
  `discount_amount` DECIMAL NULL DEFAULT 0.00,
  `tax_amount` DECIMAL NULL DEFAULT 0.00,
  `total_actual_amount` DECIMAL NULL DEFAULT 0.00,
  PRIMARY KEY (`order_id`, `location_id`, `item_id`),
  CONSTRAINT `fk_purchase_order_items_purchase_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `commercedb`.`purchase_order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `commercedb`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_purchase_order_items_purchase_order1_idx` ON `commercedb`.`purchase_order_items` (`order_id` ASC);

CREATE INDEX `fk_purchase_order_items_item1_idx` ON `commercedb`.`purchase_order_items` (`item_id` ASC);

CREATE INDEX `fk_purchase_order_items_location1_idx` ON `commercedb`.`purchase_order_items` (`location_id` ASC);



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
