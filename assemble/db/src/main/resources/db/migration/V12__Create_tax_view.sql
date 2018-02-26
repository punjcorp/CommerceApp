-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- View Creation `commercedb`.`v_location_tax`
-- -----------------------------------------------------
CREATE OR REPLACE VIEW `commercedb`.`v_location_tax`
AS
SELECT 
    tlm.location_id , tl.code `billing_location`, tg.tax_group_id, tg.name `tax_group_name`, tg.description `tax_group_desc`, tgr.seq_no `tax_group_rate_seq`, tgr.name `tax_group_rate_name`, tgr.description `tax_group_rate_desc`,
    tgr.compound_flag, tgr.type_code, trr.seq_no, trr.effective_Date, trr.expiry_date, trr.percentage, trr.amount
FROM
    commercedb.tax_location_mapping tlm,
    commercedb.tax_location tl,
    commercedb.tax_group tg,
    commercedb.tax_group_rule tgr,
    commercedb.tax_rate_rule trr
    where tl.tax_location_id=tlm.tax_location_id
    and tg.tax_group_id=tgr.tax_group_id
    and tlm.tax_location_id=tgr.tax_location_id
    and tg.tax_group_id=trr.tax_group_id
    and tlm.tax_location_id=trr.tax_location_id
    and tgr.tax_group_id=trr.tax_group_id
    and tgr.tax_location_id=trr.tax_location_id
    and tgr.tax_authority_id=trr.tax_authority_id
    and tgr.seq_no=trr.tax_group_rule_seq order by tg.name, tgr.name, tgr.seq_no, trr.seq_no;    
    
    
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
