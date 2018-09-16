-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

-- -----------------------------------------------------
-- Procedure `pi_pos_industry`.`debug_msg`
-- -----------------------------------------------------
drop procedure if exists `pi_pos_industry`.`debug_msg`;
DELIMITER //
CREATE PROCEDURE debug_msg(enabled INTEGER, msg VARCHAR(255))
BEGIN
  IF enabled THEN BEGIN
    select concat("** ", msg) AS '** DEBUG:';
  END; END IF;
END

//
DELIMITER ;
-- -----------------------------------------------------
-- Procedure `pi_pos_industry`.`p_create_txn_audit_version`
-- -----------------------------------------------------
drop procedure if exists `pi_pos_industry`.`p_create_txn_audit_version`;
DELIMITER //

CREATE  PROCEDURE `p_create_txn_audit_version`(IN i_location_id INT(4), IN i_business_date DATETIME, IN i_register_id INT(3), IN i_txn_no INT(5), OUT o_status tinyint, OUT o_version bigint)
begin

DECLARE current_version BIGINT DEFAULT 0;

set o_status=0;

SELECT 
    max(audit_version)
INTO @current_version FROM
    pi_pos_industry.txn_master_audit
WHERE
    location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;

call debug_msg(@enabled, "my first debug message");



if @current_version IS NULL then
	SET @current_version=1;
ELSE
	SET @current_version=@current_version+1;
END IF;

call debug_msg(@enabled, (select concat_ws('',"Inserting Audit version :", @current_version)));

insert into pi_pos_industry.txn_master_audit(location_id, business_date, register, txn_no, audit_version, 
  `start_time`,
  `end_time`,
  `offline_flag`,
  `session_id`,
  `created_by`,
  `created_date`,
  `total` ,
  `tax_total`,
  `discount_total`,
  `rounded_amount`,
  `subtotal`,
  `cancel_reason_code`,
  `txn_type`,
  `status`,
  `post_void_flag`,
  `modified_by`,
  `modified_date`,
  `comments`) select location_id, business_date, register, txn_no, @current_version as audit_version, 
  `start_time`,
  `end_time`,
  `offline_flag`,
  `session_id`,
  `created_by`,
  `created_date`,
  `total` ,
  `tax_total`,
  `discount_total`,
  `rounded_amount`,
  `subtotal`,
  `cancel_reason_code`,
  `txn_type`,
  `status`,
  `post_void_flag`,
  `modified_by`,
  `modified_date`,
  `comments` from pi_pos_industry.txn_master where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;

call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_master_audit:", @current_version)));


insert into pi_pos_industry.txn_line_item_master_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `seq_no`,
  `type_code`,
  `status`,
  `void_flag`,
  `void_reason_code`,
  `begin_date`,
  `end_date`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `seq_no`,
  `type_code`,
  `status`,
  `void_flag`,
  `void_reason_code`,
  `begin_date`,
  `end_date`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date` from pi_pos_industry.txn_line_item_master where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;


call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_line_item_master_audit:", @current_version)));

insert into pi_pos_industry.txn_li_item_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `seq_no`,
  `item_id`,
  `item_desc`,
  `qty`,
  `gross_qty`,
  `unit_price`,
  `suggested_price`,
  `max_retail_price`,
  `discount_amount`,
  `discount_percentage`,
  `extended_amount`,
  `tax_amount`,
  `return_flag`,
  `entry_method`,
  `net_amount`,
  `gross_amount`,
  `returned_qty`,
  `upc_no`,
  `txn_type`,
  `inv_action_code`,
  `org_location_id`,
  `org_business_date`,
  `org_register`,
  `org_txn_no`,
  `return_reason_code`,
  `return_comment`,
  `return_type_code`,
  `rcpt_count`,
  `base_unit_price`,
  `base_extended_price`,
  `new_description`,
  `exclude_from_sales_flag`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date`,
  `hsn_no`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `seq_no`,
  `item_id`,
  `item_desc`,
  `qty`,
  `gross_qty`,
  `unit_price`,
  `suggested_price`,
  `max_retail_price`,
  `discount_amount`,
  `discount_percentage`,
  `extended_amount`,
  `tax_amount`,
  `return_flag`,
  `entry_method`,
  `net_amount`,
  `gross_amount`,
  `returned_qty`,
  `upc_no`,
  `txn_type`,
  `inv_action_code`,
  `org_location_id`,
  `org_business_date`,
  `org_register`,
  `org_txn_no`,
  `return_reason_code`,
  `return_comment`,
  `return_type_code`,
  `rcpt_count`,
  `base_unit_price`,
  `base_extended_price`,
  `new_description`,
  `exclude_from_sales_flag`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date`,
  `hsn_no`  from pi_pos_industry.txn_li_item where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;


call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_li_item_audit:", @current_version)));


insert into pi_pos_industry.txn_receipt_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `receipt_type`,
  `receipt_data`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `receipt_type`,
  `receipt_data`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date` from pi_pos_industry.txn_receipt where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;



call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_receipt_audit:", @current_version)));


insert into pi_pos_industry.txn_li_tax_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `seq_no`,
   `item_id`,
  `total_taxable_amount`,
  `total_tax_amount`,
  `total_tax_exempt_amount`,
  `tax_override_amount`,
  `tax_override_percentage`,
  `tax_override_flag`,
  `override_reason_code`,
  `void_flag`,
  `tax_rule_percentage`,
  `tax_rule_amount`,
  `org_taxable_amount`,
  `org_tax_group_id`,
  `created_by`,
  `created_date`,
  `tax_group_id`,
  `tax_rule_rate_id`,
  `tax_code`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `seq_no`,
   `item_id`,
  `total_taxable_amount`,
  `total_tax_amount`,
  `total_tax_exempt_amount`,
  `tax_override_amount`,
  `tax_override_percentage`,
  `tax_override_flag`,
  `override_reason_code`,
  `void_flag`,
  `tax_rule_percentage`,
  `tax_rule_amount`,
  `org_taxable_amount`,
  `org_tax_group_id`,
  `created_by`,
  `created_date`,
  `tax_group_id`,
  `tax_rule_rate_id`,
  `tax_code` from pi_pos_industry.txn_li_tax where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;


call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_li_tax_audit:", @current_version)));


insert into pi_pos_industry.txn_li_tender_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `seq_no`,
  `tender_id`,
  `amount`,
  `change_flag`,
  `type_code`,
  `action_code`,
  `foreign_amount`,
  `exchange_rate`,
  `created_by`,
  `created_date`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `seq_no`,
  `tender_id`,
  `amount`,
  `change_flag`,
  `type_code`,
  `action_code`,
  `foreign_amount`,
  `exchange_rate`,
  `created_by`,
  `created_date` from pi_pos_industry.txn_li_tender where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;



call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_li_tender_audit:", @current_version)));


insert into pi_pos_industry.txn_customer_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `customer_id`,
  `customer_type`,
  `billing_address_id`,
  `shipping_address_id`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `customer_id`,
  `customer_type`,
  `billing_address_id`,
  `shipping_address_id` from pi_pos_industry.txn_customer where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;


call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_customer_audit:", @current_version)));


insert into pi_pos_industry.txn_shipment_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `order_request_id`,
  `order_request_date`,
  `driver_name`,
  `driver_phone`,
  `vehicle_no`,
  `transport_company`,
  `gp_pr_no`,
  `gp_pr_date`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`,
  `order_request_id`,
  `order_request_date`,
  `driver_name`,
  `driver_phone`,
  `vehicle_no`,
  `transport_company`,
  `gp_pr_no`,
  `gp_pr_date` from pi_pos_industry.txn_shipment where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;

call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_shipment_audit:", @current_version)));


insert into pi_pos_industry.sale_txn_invoices_audit(audit_version,`invoice_no`,`location_id`,
  `business_date`,
  `register`,
  `txn_no`) select @current_version as audit_version, `invoice_no`,`location_id`,
  `business_date`,
  `register`,
  `txn_no` from pi_pos_industry.sale_txn_invoices where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;

call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in sale_txn_invoices_audit:", @current_version)));


insert into pi_pos_industry.txn_tender_count_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`, `txn_type`,
  `tender_id`,
  `amount`,
  `media_count`,
  `actual_amount`,
  `actual_media_count`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`, `txn_type`,
  `tender_id`,
  `amount`,
  `media_count`,
  `actual_amount`,
  `actual_media_count`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date` from pi_pos_industry.txn_tender_count where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;

call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_tender_count_audit:", @current_version)));




insert into pi_pos_industry.txn_tender_denomination_audit(audit_version,`location_id`,
  `business_date`,
  `register`,
  `txn_no`, `tender_id`,
  `denomination_id`,
  `amount`,
  `media_count`,
  `difference_amount`,
  `difference_media_count`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date`) select @current_version as audit_version, `location_id`,
  `business_date`,
  `register`,
  `txn_no`, `tender_id`,
  `denomination_id`,
  `amount`,
  `media_count`,
  `difference_amount`,
  `difference_media_count`,
  `created_by`,
  `created_date`,
  `modified_by`,
  `modified_date` from pi_pos_industry.txn_tender_denomination where location_id = i_location_id
        AND business_date = i_business_date
        AND register = i_register_id
        AND txn_no = i_txn_no;

call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in txn_tender_denomination_audit:", @current_version)));



set o_status=1;
SET o_version=@current_version;

end

//
DELIMITER ;


-- -----------------------------------------------------
-- Procedure `pi_pos_industry`.`p_create_daily_totals_audit_version`
-- -----------------------------------------------------
drop procedure if exists `pi_pos_industry`.`p_create_daily_totals_audit_version`;
DELIMITER //

CREATE  PROCEDURE `p_create_daily_totals_audit_version`(IN i_location_id INT(4), IN i_business_date DATETIME, IN i_register_id INT(3), OUT o_status tinyint, OUT o_version bigint)
begin

DECLARE current_version BIGINT DEFAULT 0;

set o_status=0;

SELECT 
    max(audit_version)
INTO @current_version FROM
    pi_pos_industry.daily_totals_audit
WHERE
    location_id = i_location_id
        AND business_date = i_business_date
        AND register_id = i_register_id;

call debug_msg(@enabled, "The current version has been retrieved");



if @current_version IS NULL then
	SET @current_version=1;
ELSE
	SET @current_version=@current_version+1;
END IF;

call debug_msg(@enabled, (select concat_ws('',"Inserting Register Daily Totals Audit version :", @current_version)));

insert into pi_pos_industry.daily_totals_audit(audit_version, `daily_totals_id`,
  `location_id`,
  `register_id`,
  `business_date`,
  `total_txn_count`,
  `total_sales_count`,
  `total_returns_count`,
  `total_txn_amount`,
  `total_sales_amount`,
  `total_returns_amount`,
  `sod_amount`,
  `eod_amount`,
  `total_no_sales_amount`,
  `total_no_sales_count`) select @current_version as audit_version, 
  `daily_totals_id`,
  `location_id`,
  `register_id`,
  `business_date`,
  `total_txn_count`,
  `total_sales_count`,
  `total_returns_count`,
  `total_txn_amount`,
  `total_sales_amount`,
  `total_returns_amount`,
  `sod_amount`,
  `eod_amount`,
  `total_no_sales_amount`,
  `total_no_sales_count` from pi_pos_industry.daily_totals where location_id = i_location_id
        AND business_date = i_business_date
        AND register_id = i_register_id;

call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in daily_totals_audit for register:", @current_version)));



insert into pi_pos_industry.daily_totals_audit(audit_version, `daily_totals_id`,
  `location_id`,
  `register_id`,
  `business_date`,
  `total_txn_count`,
  `total_sales_count`,
  `total_returns_count`,
  `total_txn_amount`,
  `total_sales_amount`,
  `total_returns_amount`,
  `sod_amount`,
  `eod_amount`,
  `total_no_sales_amount`,
  `total_no_sales_count`) select @current_version as audit_version, 
  `daily_totals_id`,
  `location_id`,
  `register_id`,
  `business_date`,
  `total_txn_count`,
  `total_sales_count`,
  `total_returns_count`,
  `total_txn_amount`,
  `total_sales_amount`,
  `total_returns_amount`,
  `sod_amount`,
  `eod_amount`,
  `total_no_sales_amount`,
  `total_no_sales_count` from pi_pos_industry.daily_totals where location_id = i_location_id
        AND business_date = i_business_date
        AND register_id IS NULL;

call debug_msg(@enabled, (select concat_ws('',"Audit version Insertion completed in daily_totals_audit for store:", @current_version)));

SET o_version=@current_version;

set o_status=1;

end

//
DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

