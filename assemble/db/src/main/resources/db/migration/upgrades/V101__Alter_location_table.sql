SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

ALTER TABLE `pi_pos_industry`.`location` 
ADD COLUMN `district` VARCHAR(50) NULL;

ALTER TABLE `pi_pos_industry`.`ledger_journal` 
DROP INDEX `business_date_UNIQUE` ,
ADD UNIQUE INDEX `business_date_UNIQUE` (`business_date` ASC, `txn_no` ASC, `txn_type` ASC, `location_id` ASC, `action_code` ASC, `created_date` ASC);

ALTER TABLE pi_pos_industry.sale_txn_invoices AUTO_INCREMENT = 64;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
