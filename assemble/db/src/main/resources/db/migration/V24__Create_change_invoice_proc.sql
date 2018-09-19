-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Procedure `p_temp_alter`.`p_alter_auto_increment`
-- -----------------------------------------------------
drop procedure if exists `pi_pos_industry`.`p_alter_auto_increment`;

DELIMITER //

CREATE PROCEDURE `p_alter_auto_increment`(IN i_table_name varchar(50), OUT o_status tinyint)
BEGIN
	set o_status = false;
    set @enabled=true;
    set @l_max_temp=0;
    
	-- retrieve maximum no for the auto increment for the table
    SET @INVOICE_SQL =concat('select @l_max_temp:= max(invoice_no)+1  from pi_pos_industry.' ,i_table_name);
	PREPARE stmt FROM @INVOICE_SQL;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
    
    -- alter table auto increment
	SET @SQL =concat('ALTER TABLE pi_pos_industry.',i_table_name,' AUTO_INCREMENT = ' ,@l_max_temp);
	PREPARE stmt FROM @SQL;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
    
	set o_status = true;
    
END 

//
DELIMITER ;


-- -----------------------------------------------------
-- Procedure `pi_pos_industry`.`p_decrement_invoice_nos`
-- -----------------------------------------------------
drop procedure if exists `pi_pos_industry`.`p_decrement_invoice_nos`;

DELIMITER //

CREATE PROCEDURE `p_decrement_invoice_nos`(IN i_invoice_no  BIGINT, OUT o_status tinyint)
BEGIN
	
	DECLARE done INT DEFAULT FALSE;
    
     -- Declare INVOICE variables --
    DECLARE l_invoice_no BIGINT;
    DECLARE l_location_id INT(4);
    DECLARE l_business_date DATETIME;
    DECLARE l_register INT(3);
    DECLARE l_txn_no INT(5);
    
    DECLARE l_max_invoice_no BIGINT DEFAULT 1;

	-- Declare Invoice cursor --
	DECLARE sale_invoices_cursor CURSOR FOR
    SELECT invoice_no, location_id, business_date, register, txn_no from pi_pos_industry.sale_txn_invoices where invoice_no> i_invoice_no order by invoice_no asc;

	-- Declare Continue Handler --
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		rollback;
        
		RESIGNAL;
    END;
    
    set o_status = false;
    set @enabled=true;
    OPEN sale_invoices_cursor;
    
    LOOPROWS: LOOP
    
		-- Fetch data from cursor --
        FETCH sale_invoices_cursor 
        INTO l_invoice_no, l_location_id, l_business_date, l_register,
              l_txn_no;

		-- Check for no more records --
        IF done THEN
            LEAVE LOOPROWS;
        END IF;

		update pi_pos_industry.sale_txn_invoices set invoice_no= l_invoice_no-1 where invoice_no=l_invoice_no and location_id=l_location_id and business_date=l_business_date and register=l_register and txn_no=l_txn_no;

    END LOOP;

	CLOSE sale_invoices_cursor;
    
    call debug_msg(@enabled, (select "Till cursor closure"));
    
	set o_status = true;
END 

//
DELIMITER ;

-- -----------------------------------------------------
-- Procedure `pi_pos_industry`.`p_increment_invoice_nos`
-- -----------------------------------------------------
drop procedure if exists `pi_pos_industry`.`p_increment_invoice_nos`;

DELIMITER //

CREATE PROCEDURE `p_increment_invoice_nos`(IN i_invoice_no  BIGINT, OUT o_status tinyint)
BEGIN
	   
    DECLARE done INT DEFAULT FALSE;
	    
     -- Declare INVOICE variables --
    DECLARE l_invoice_no BIGINT;
    DECLARE l_location_id INT(4);
    DECLARE l_business_date DATETIME;
    DECLARE l_register INT(3);
    DECLARE l_txn_no INT(5);
    
    DECLARE l_max_invoice_no BIGINT DEFAULT 1;

	-- Declare Invoice cursor --
	DECLARE sale_invoices_cursor CURSOR FOR
    SELECT invoice_no, location_id, business_date, register, txn_no from pi_pos_industry.sale_txn_invoices where invoice_no>= i_invoice_no order by invoice_no asc;

	-- Declare Continue Handler --
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		rollback;
            
        RESIGNAL;  -- raise again the sql exception to the caller
    END;
    
    set o_status = false;
    
	        
    OPEN sale_invoices_cursor;
    
    LOOPROWS: LOOP
    
		-- Fetch data from cursor --
        FETCH sale_invoices_cursor 
        INTO l_invoice_no, l_location_id, l_business_date, l_register,
              l_txn_no;

		-- Check for no more records --
        IF done THEN
            LEAVE LOOPROWS;
        END IF;

		update pi_pos_industry.sale_txn_invoices set invoice_no= l_invoice_no+1 where invoice_no=l_invoice_no and location_id=l_location_id and business_date=l_business_date and register=l_register and txn_no=l_txn_no;

    END LOOP;

	CLOSE sale_invoices_cursor;
    
    set o_status = true;
    
END 

//
DELIMITER ;





SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

