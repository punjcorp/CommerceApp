-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pi_pos_industry
-- -----------------------------------------------------
USE `pi_pos_industry` ;

SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------
-- Seed data for location
-- -----------------------------------------------------
truncate `pi_pos_industry`.`location`;
INSERT INTO `pi_pos_industry`.`location` (`location_id`,`location_type`,`name`,`description`,`status`,`address1`,`address2`,`city`,`state`,`country`,`pincode`,`neighborhood`,`locale`,`currency`,`telephone1`,`telephone2`,`store_manager`,`email_address`,`created_date`,`created_by`,`modified_date`,`modified_by`) VALUES (PI_POS_T_STORE_NO,'S','PI_POS_T_STORE_NAME','PI_POS_T_STORE_DESC','A','PI_POS_T_STORE_ADDRESS_1','PI_POS_T_STORE_ADDRESS_2','PI_POS_T_STORE_CITY','PI_POS_T_STORE_STATE','PI_POS_T_STORE_COUNTRY','PI_POS_T_STORE_PINCODE','PI_POS_T_STORE_NEIGHBORHOOD','en_US','INR','PI_POS_T_STORE_PHONE','PI_POS_T_STORE_PHONE2','PI_POS_T_STORE_MANAGER','PI_POS_T_STORE_EMAIL',now(),'admin',NULL,NULL);
commit;

-- -----------------------------------------------------
-- Seed data for reason_codes
-- -----------------------------------------------------
truncate `pi_pos_industry`.`reason_codes`;
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Electricity Bill', 'Bill', 'This is for electricity Bill', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Water Bill', 'Bill', 'This is for MC water bill', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Internet Bill', 'Bill', 'This is for Internet Bill', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Tea', 'Daily Use', 'This is for tea ', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Snacks', 'Daily Use', 'This is for snacks', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Lease Rent', 'Bill', 'This is rent for the room', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Payment', 'General Expense', 'this is payment for anything which will be described in remarks', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Repository To Register', 'Move', 'This reason code is used to move money from store repository to register till', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Register To Repository', 'Move', 'This reason code is used to move money from register till to store repository', 'admin', now());
INSERT INTO `pi_pos_industry`.`reason_codes` (`reason_name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Register To Register', 'Move', 'This reason code is used to move money from one register till to another register till', 'admin', now());

commit;

-- -----------------------------------------------------
-- Seed data for uom_master
-- -----------------------------------------------------
truncate `pi_pos_industry`.`uom_master`;
INSERT INTO `pi_pos_industry`.`uom_master` (`name`, `code`, `description`, `type`, `is_primary`, `created_by`, `created_date`) VALUES ('Each', 'EA', 'This is UOM for measuring a single item qty', 'Qty', '1', 'admin', now());
INSERT INTO `pi_pos_industry`.`uom_master` (`name`, `code`, `description`, `type`, `is_primary`, `created_by`, `created_date`) VALUES ('Dozen', 'DZ', 'This is UOM for measuring 12 item qty', 'Qty', '0', 'admin', now());
INSERT INTO `pi_pos_industry`.`uom_master` (`name`, `code`, `description`, `type`, `is_primary`, `created_by`, `created_date`) VALUES ('Grams', 'gms', 'This is UOM for measuring item weigth', 'Weight', '1', 'admin', now());
INSERT INTO `pi_pos_industry`.`uom_master` (`name`, `code`, `description`, `type`, `is_primary`, `created_by`, `created_date`) VALUES ('Kilogram', 'kgs', 'This is UOM for measuring 1000 gm item weight', 'Weight', '0', 'admin', now());
commit;

-- -----------------------------------------------------
-- Seed data for denomination_master
-- -----------------------------------------------------
truncate `pi_pos_industry`.`denomination_master`;
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`, `description`) VALUES ('INR', '0.50', '0.50', '50 paisa');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '1.00', '1');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '2.00', '2');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '5.00', '5');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '10.00', '10');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '20.00', '20');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '50.00', '50');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '100.00', '100');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '200.00', '200');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '500.00', '500');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '1000.00', '1000');
INSERT INTO `pi_pos_industry`.`denomination_master` (`currency_code`, `code`, `value`) VALUES ('INR', '2000.00', '2000');
commit;

-- -----------------------------------------------------
-- Seed data for tender_master table
-- -----------------------------------------------------
truncate `pi_pos_industry`.`tender_master`;
INSERT INTO `pi_pos_industry`.`tender_master` (`name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Cash', 'CASH', 'This is the cash tender description', 'admin', now());
INSERT INTO `pi_pos_industry`.`tender_master` (`name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Change', 'CHANGE', 'This is change for cash tender', 'admin', now());
INSERT INTO `pi_pos_industry`.`tender_master` (`name`, `type`, `description`, `created_by`, `created_date`) VALUES ('Credit', 'CREDIT', 'This is for Credit tender to give udhar', 'admin', '2018-07-06 19:46:21');

commit;

-- -----------------------------------------------------
-- Seed data for repository and tenders
-- -----------------------------------------------------
truncate `pi_pos_industry`.`location_repo`;
truncate `pi_pos_industry`.`repository_master`;
INSERT INTO `pi_pos_industry`.`repository_master` (`tender_id`, `name`, `description`, `begin_date_time`, `end_date_time`, `status`, `created_by`, `created_date`) VALUES (1, 'Main Safe', 'This is main safe in the store', now(), now(), 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`repository_master` (`tender_id`, `name`, `description`, `begin_date_time`, `end_date_time`, `status`, `created_by`, `created_date`) VALUES (2, 'Main Safe', 'This is main safe in the store', now(), now(), 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`repository_master` (`tender_id`, `name`, `description`, `begin_date_time`, `end_date_time`, `status`, `created_by`, `created_date`) VALUES (3, 'Main Safe', 'This is main safe in the store', now(), now(), 'A', 'admin', now());


INSERT INTO `pi_pos_industry`.`location_repo` (`repository_id`, `location_id`, `tender_id`, `reconcilation_flag`, `created_by`, `created_date`) VALUES (1, PI_POS_T_STORE_NO, 1, 1, 'admin', now());
INSERT INTO `pi_pos_industry`.`location_repo` (`repository_id`, `location_id`, `tender_id`, `reconcilation_flag`, `created_by`, `created_date`) VALUES (1, PI_POS_T_STORE_NO, 2, 0, 'admin', now());
INSERT INTO `pi_pos_industry`.`location_repo` (`repository_id`, `location_id`, `tender_id`, `reconcilation_flag`, `created_by`, `created_date`) VALUES (1, PI_POS_T_STORE_NO, 3, 0, 'admin', now());

commit;


-- -----------------------------------------------------
-- Seed data for register_master table
-- -----------------------------------------------------
truncate `pi_pos_industry`.`register_master`;
INSERT INTO `pi_pos_industry`.`register_master` (`location_id`, `register_id`, `name`, `created_by`, `created_date`) VALUES (PI_POS_T_STORE_NO, 1, 'Register One', 'admin', now());
commit;

-- -----------------------------------------------------
-- Seed data for tax tables
-- -----------------------------------------------------
truncate `pi_pos_industry`.`tax_rate_rule`;
truncate `pi_pos_industry`.`tax_bracket`;
truncate `pi_pos_industry`.`tax_group_rule`;
truncate `pi_pos_industry`.`tax_group`;
truncate `pi_pos_industry`.`tax_location`;
truncate `pi_pos_industry`.`tax_authority`;
truncate `pi_pos_industry`.`tax_location_mapping`;


INSERT INTO `pi_pos_industry`.`tax_authority` (`name`, `rounding_code`, `rounding_digit_qty`, `created_by`, `created_date`) VALUES ('Central Govt of India', 'N', '2', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_authority` (`name`, `rounding_code`, `rounding_digit_qty`, `created_by`, `created_date`) VALUES ('State Govt of India', 'N', '2', 'admin', now());


INSERT INTO `pi_pos_industry`.`tax_location` (`name`,`code`, `description`, `created_by`, `created_date`) VALUES ('Within State', 'I', 'This tax location is for all sales within state', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_location` (`name`,`code`, `description`, `created_by`, `created_date`) VALUES ('Outside State', 'O','This tax location is for all sales outside state', 'admin', now());

INSERT INTO `pi_pos_industry`.`tax_location_mapping` (`location_id`, `tax_location_id`) VALUES (PI_POS_T_STORE_NO, 1);
INSERT INTO `pi_pos_industry`.`tax_location_mapping` (`location_id`, `tax_location_id`) VALUES (PI_POS_T_STORE_NO, 2);

INSERT INTO `pi_pos_industry`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST 0', 'This tax group is applied when no GST is applicable', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST 5', 'This group is applied for 5 percent tax rate of GST', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST 10', 'This group is applied for 10 percent tax rate of GST', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST 12', 'This group is applied for 12 percent tax rate of GST', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST 18', 'This group is applied for 18 percent tax rate of GST', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST 28', 'This group is applied for 28 percent tax rate of GST', 'admin', now());


INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '0', 'SGST', 'This is the rules for group SGST', '0', '1', '0', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', 'CGST', 'This is the rules for group CGST', '1', '1', '0', 'CGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '2', '1', '0', 'IGST', 'This is the rules for group IGST', '0', '0', '0', 'IGST', 'A', 'admin', now()); 

INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('2', '1', '1', '0', 'SGST', 'This is the rules for group SGST', '0', '1', '0', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('2', '1', '1', '1', 'CGST', 'This is the rules for group CGST', '1', '1', '0', 'CGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('2', '2', '1', '0', 'IGST', 'This is the rules for group IGST', '0', '0', '0', 'IGST', 'A', 'admin', now()); 

INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('3', '1', '1', '0', 'SGST', 'This is the rules for group SGST', '0', '1', '0', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('3', '1', '1', '1', 'CGST', 'This is the rules for group CGST', '1', '1', '0', 'CGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('3', '2', '1', '0', 'IGST', 'This is the rules for group IGST', '0', '0', '0', 'IGST', 'A', 'admin', now()); 

INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('4', '1', '1', '0', 'SGST', 'This is the rules for group SGST', '0', '1', '0', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('4', '1', '1', '1', 'CGST', 'This is the rules for group CGST', '1', '1', '0', 'CGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('4', '2', '1', '0', 'IGST', 'This is the rules for group IGST', '0', '0', '0', 'IGST', 'A', 'admin', now()); 

INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('5', '1', '1', '0', 'SGST', 'This is the rules for group SGST', '0', '1', '0', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('5', '1', '1', '1', 'CGST', 'This is the rules for group CGST', '1', '1', '0', 'CGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('5', '2', '1', '0', 'IGST', 'This is the rules for group IGST', '0', '0', '0', 'IGST', 'A', 'admin', now()); 

INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('6', '1', '1', '0', 'SGST', 'This is the rules for group SGST', '0', '1', '0', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('6', '1', '1', '1', 'CGST', 'This is the rules for group CGST', '1', '1', '0', 'CGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`)
 VALUES ('6', '2', '1', '0', 'IGST', 'This is the rules for group IGST', '0', '0', '0', 'IGST', 'A', 'admin', now()); 



INSERT INTO `pi_pos_industry`.`tax_bracket` (`seq_no`, `breakpoint`, `tax_amount`, `created_date`, `created_by`) VALUES ('1', '-1', '-1', now(), 'admin');


INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('1', '1', '1', '0', '1', '0', now(), now(), '0', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('1', '1', '1', '1', '1', '1', now(), now(), '0', 'CGST', 'A', 'admin', now());

INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('2', '1', '1', '0', '1', '0', now(), now(), '2.5', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('2', '1', '1', '1', '1', '1', now(), now(), '2.5', 'CGST', 'A', 'admin', now());

INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('3', '1', '1', '0', '1', '0', now(), now(), '5', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('3', '1', '1', '1', '1', '1', now(), now(), '5', 'CGST', 'A', 'admin', now());

INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('4', '1', '1', '0', '1', '0', now(), now(), '6', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('4', '1', '1', '1', '1', '1', now(), now(), '6', 'CGST', 'A', 'admin', now());

INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('5', '1', '1', '0', '1', '0', now(), now(), '9', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('5', '1', '1', '1', '1', '1', now(), now(), '9', 'CGST', 'A', 'admin', now());

INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('6', '1', '1', '0', '1', '0', now(), now(), '14', 'SGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('6', '1', '1', '1', '1', '1', now(), now(), '14', 'CGST', 'A', 'admin', now());

INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('1', '2', '1', '0', '1', '0', now(), now(), '0', 'IGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('2', '2', '1', '0', '1', '0', now(), now(), '5', 'IGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('3', '2', '1', '0', '1', '0', now(), now(), '10', 'IGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('4', '2', '1', '0', '1', '0', now(), now(), '12', 'IGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('5', '2', '1', '0', '1', '0', now(), now(), '18', 'IGST', 'A', 'admin', now());
INSERT INTO `pi_pos_industry`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) 
VALUES ('6', '2', '1', '0', '1', '0', now(), now(), '28', 'IGST', 'A', 'admin', now());
commit;


-- -----------------------------------------------------
-- Seed data for inventory
-- -----------------------------------------------------
truncate pi_pos_industry.stock_reason_code;
truncate pi_pos_industry.stock_bucket;

insert into pi_pos_industry.stock_bucket (`name`, system_bucket, created_by, created_date, modified_by, modified_date, `status`)
values('Stock on Hand', 'SOH', 'admin', '2018-01-27 11:18:45', NULL, NULL, 'A');
insert into pi_pos_industry.stock_bucket (`name`, system_bucket, created_by, created_date, modified_by, modified_date, `status`)
values('Order', 'RESERVED', 'admin', '2018-01-27 11:18:45', NULL, NULL, 'A');
insert into pi_pos_industry.stock_bucket (`name`, system_bucket, created_by, created_date, modified_by, modified_date, `status`)
values('Expired Stock', 'NON_SELL', 'admin', '2018-01-27 11:18:45', NULL, NULL, 'A');
insert into pi_pos_industry.stock_bucket (`name`, system_bucket, created_by, created_date, modified_by, modified_date, `status`)
values('Damaged', 'NON_SELL', 'admin', '2018-01-27 11:18:45', NULL, NULL, 'A');
insert into pi_pos_industry.stock_bucket (`name`, system_bucket, created_by, created_date, modified_by, modified_date, `status`)
values('Not Sellable', 'NON_SELL', 'admin', '2018-01-27 11:18:45', NULL, NULL, 'A');


insert into pi_pos_industry.stock_reason_code(reason_code, `name`, from_bucket_id, to_bucket_id, from_stock_action, to_stock_action, created_by, created_date)
values('STKIN', 'commerce.reason.code.inventory.stockin', NULL, '1', NULL, 'ADD', 'admin', '2018-01-27 11:23:31');
insert into pi_pos_industry.stock_reason_code(reason_code, `name`, from_bucket_id, to_bucket_id, from_stock_action, to_stock_action, created_by, created_date)
values('STKOUT', 'commerce.reason.code.inventory.stockout', '1', NULL, 'SUBTRACT', NULL, 'admin', '2018-01-27 11:23:31');



-- -----------------------------------------------------
-- Seed data for item_hierarchy
-- -----------------------------------------------------
truncate `pi_pos_industry`.`item_hierarchy`;
INSERT INTO `pi_pos_industry`.`item_hierarchy` (`level_code`, `name`, `description`, `sort_order`, `hidden_flag`, `created_by`, `created_date`) VALUES ('dept', 'Default', 'This is default department for any item', '1', 'N', 'admin', now());

commit;


-- -----------------------------------------------------
-- Seed data for attribute_master
-- -----------------------------------------------------
truncate `pi_pos_industry`.`attribute_master`;
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('S', 'Size', 'This is to capture SIZE', 'L', 'Large', 'This is Large SIZE', '1');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('S', 'Size', 'This is to capture SIZE', 'M', 'Medium', 'This is Medium SIZE', '2');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('S', 'Size', 'This is to capture SIZE', 'S', 'Small', 'This is Small SIZE', '3');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('C', 'Color', 'This is to capture Color', 'RED', 'Red', 'This is RED Color', '1');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('C', 'Color', 'This is to capture Color', 'GREEN', 'Green', 'This is GREEN Color', '2');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('C', 'Color', 'This is to capture Color', 'BLUE', 'Blue', 'This is BLUE Color', '3');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('D', 'Dimension', 'This is to capture Dimension', 'L', 'Length', 'This is Length of the Dimension', '1');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('D', 'Dimension', 'This is to capture Dimension', 'B', 'Breadth', 'This is Breadth of the Dimension', '2');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('D', 'Dimension', 'This is to capture Dimension', 'W', 'Width', 'This is Width of the Dimension', '3');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('W', 'Weight', 'This is to measure weight ', '100', '100 gms', 'This is the Weight of 100 gms', '1');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('W', 'Weight', 'This is to measure weight ', '200', '200 gms', 'This is the Weight of 200 gms', '2');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('W', 'Weight', 'This is to measure weight ', '250', '250 gms', 'This is the Weight of 250 gms', '3');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('W', 'Weight', 'This is to measure weight ', '500', '500 gms', 'This is the Weight of 500 gms', '4');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('W', 'Weight', 'This is to measure weight ', '1', '1 Kg', 'This is the Weight of 1 Kg', '5');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('W', 'Weight', 'This is to measure weight ', '2', '2 Kgs', 'This is the Weight of 2 Kgs', '6');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('W', 'Weight', 'This is to measure weight ', '5', '5 Kgs', 'This is the Weight of 5  Kgs', '7');
INSERT INTO `pi_pos_industry`.`attribute_master` (`attr_code`, `attr_name`, `attr_description`, `value_code`, `value_name`, `value_description`, `value_seq_no`) VALUES ('NA', 'None', 'This is for items with no attributes and have only one SKU ', 'P', 'Plain', 'This is plain version for SKU related to item', '1');

commit;


-- -----------------------------------------------------
-- Seed data for user role
-- -----------------------------------------------------
INSERT INTO `pi_pos_industry`.`role` (`role_name`, `description`, `created_by`, `created_date`) VALUES ('ADMIN', 'This is admin role for administrators of the website', 'admin', now());
INSERT INTO `pi_pos_industry`.`role` (`role_name`, `description`, `created_by`, `created_date`) VALUES ('CASHIER', 'This is cashier role for cashiers ', 'admin', now());

-- -----------------------------------------------------
-- Seed data for user
-- -----------------------------------------------------
insert into pi_pos_industry.user (username, first_name, last_name, phone1, phone2, email, login_count, `status`, created_by, created_date)
values('admin', 'admin', 'admin', '8847523677', '8968834880', 'admin', '0', 'A', 'admin', '2018-01-16 20:19:09');
insert into pi_pos_industry.user (username, first_name, last_name, phone1, phone2, email, login_count, `status`, created_by, created_date)
values('cashier', 'cashier', 'cashier', '8847523677', '8968834880', 'cashier', '0', 'A', 'admin', '2018-01-16 20:19:09');


-- -----------------------------------------------------
-- Seed data for user_password
-- -----------------------------------------------------
insert into pi_pos_industry.user_password( username, password, modified_by, modified_date, `status`)
values('admin', '$2a$10$IWjH12W2tgjFRcfEGWgFBekSZwfiFnJTkCk9vwct05CoTU5ZArOrO', 'admin', '2018-01-22 19:40:52', 'A');
insert into pi_pos_industry.user_password( username, password, modified_by, modified_date, `status`)
values('cashier', '$2a$10$iHH8hs7bMG.7ud5.DdCMteuxwXZRNikGUYuySm4r5drTBkTsVC7B2', 'admin', '2018-01-22 19:40:52', 'A');

-- -----------------------------------------------------
-- Seed data for user role
-- -----------------------------------------------------
INSERT INTO `pi_pos_industry`.`user_role` (`username`, `role_id`, `location_id`, `created_by`, `created_date`, `begin_date`, `end_date`) VALUES ('admin', '1', PI_POS_T_STORE_NO, 'admin', now(), now(), now());
INSERT INTO `pi_pos_industry`.`user_role` (`username`, `role_id`, `location_id`, `created_by`, `created_date`, `begin_date`, `end_date`) VALUES ('cashier', '2', PI_POS_T_STORE_NO, 'admin', now(), now(), now());

-- -----------------------------------------------------
-- Seed data for user addresses
-- -----------------------------------------------------
insert into pi_pos_industry.address_master ( `primary`, address_type, address1, address2, city, state, country, pincode)
values('Y', 'Work', 'Kharar', '', 'Kharar', 'Punjab', 'India', '140301');
insert into pi_pos_industry.address_master ( `primary`, address_type, address1, address2, city, state, country, pincode)
values('Y', 'Work', 'Kharar', '', 'Kharar', 'Punjab', 'India', '140301');

insert into pi_pos_industry.user_address ( username, address_id)
values('admin', '1');

insert into pi_pos_industry.user_address ( username, address_id)
values('cashier', '2');



SET FOREIGN_KEY_CHECKS = 1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
