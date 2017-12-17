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
  `created_by` VARCHAR(50) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `total_amount` DECIMAL NOT NULL DEFAULT 0.0,
  `paid_amount` DECIMAL NOT NULL DEFAULT 0.0,
  `discount_amount` DECIMAL NOT NULL DEFAULT 0.0,
  `tax_amount` DECIMAL NOT NULL DEFAULT 0.0,
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `commercedb`.`purchase_order_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `commercedb`.`purchase_order_items` ;

CREATE TABLE IF NOT EXISTS `commercedb`.`purchase_order_items` (
  `order_id` BIGINT NOT NULL,
  `supplier_id` INT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `ordered_qty` INT NOT NULL,
  `cost_amount` DECIMAL NOT NULL,
  `total_cost` DECIMAL NOT NULL,
  `delievered_qty` INT NULL,
  `delievered_date` DATETIME NULL,
  `discount_amount` DECIMAL NULL,
  `total_discount_amount` DECIMAL NULL,
  PRIMARY KEY (`order_id`, `supplier_id`, `item_id`, `cost_amount`),
  CONSTRAINT `fk_purchase_order_items_purchase_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `commercedb`.`purchase_order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_supplier1`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `commercedb`.`supplier` (`supplier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `commercedb`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_purchase_order_items_purchase_order1_idx` ON `commercedb`.`purchase_order_items` (`order_id` ASC);

CREATE INDEX `fk_purchase_order_items_supplier1_idx` ON `commercedb`.`purchase_order_items` (`supplier_id` ASC);

CREATE INDEX `fk_purchase_order_items_item1_idx` ON `commercedb`.`purchase_order_items` (`item_id` ASC);



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
