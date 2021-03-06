-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

-- -----------------------------------------------------
-- Procedure `commercedb`.`p_get_current_item_price`
-- -----------------------------------------------------
drop procedure if exists `commercedb`.`p_get_current_item_price`;
DELIMITER //
create procedure `commercedb`.`p_get_current_item_price`(IN i_item_id bigint, IN i_location_id int, OUT o_item_price_id bigint) 
begin

SET @prev_value = NULL;
SET @rank_count = 0;
select item_price_id into o_item_price_id from (select *, CASE
    WHEN @prev_value = start_date then  @rank_count
    WHEN @prev_value := start_date THEN @rank_count := @rank_count + 1
END AS rank from (
select *, max(start_date) from `commercedb`.`item_price` where 
item_id = i_item_id
        AND location_id = i_location_id  and status='A'
        and start_date<now()
        and (end_date>=now() or end_date is null)
        group by price_change_type,start_date order by price_change_type desc, start_date desc) curr_price)itm_price where rank=1;
end
//
DELIMITER ;

-- -----------------------------------------------------
-- Procedure `commercedb`.`p_get_oldest_clearance`
-- -----------------------------------------------------
drop procedure if exists `commercedb`.`p_get_oldest_clearance`;
DELIMITER //

CREATE PROCEDURE `commercedb`.`p_get_oldest_clearance`(IN i_item_id bigint, IN i_location_id int, OUT o_clearane_id bigint)
begin

SET @prev_value = NULL;
SET @rank_count = 0;
select item_price_id into o_clearane_id from (select *, CASE
    WHEN @prev_value = start_date then  @rank_count
    WHEN @prev_value := start_date THEN @rank_count := @rank_count + 1
END AS rank  from `commercedb`.`item_price` where 
item_id = i_item_id
        AND location_id = i_location_id  and status='A' and price_change_type=3 order by start_date asc)itm_price where rank=1;
end
//
DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

