-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;

SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------
-- Seed data for location
-- -----------------------------------------------------
truncate `commercedb`.`location`;
INSERT INTO `commercedb`.`location` (`location_id`,`location_type`,`name`,`description`,`status`,`address1`,`address2`,`city`,`state`,`country`,`pincode`,`neighborhood`,`locale`,`currency`,`telephone1`,`telephone2`,`store_manager`,`email_address`,`created_date`,`created_by`,`modified_date`,`modified_by`) VALUES (7997,'S','commerce site','This is the descripton for only location in commerce site as of now','A','Kharar',NULL,'Kharar','Panjab','India','140301','Arya Collage road','en_US','INR','8847523677',NULL,'Amit','eramitpunj@gmail.com','2017-12-11 12:56:07','Admin',NULL,NULL);
commit;

-- -----------------------------------------------------
-- Seed data for tax tables
-- -----------------------------------------------------

truncate `commercedb`.`tax_rate_rule`;
truncate `commercedb`.`tax_bracket`;
truncate `commercedb`.`tax_group_rule`;
truncate `commercedb`.`tax_group`;
truncate `commercedb`.`tax_location`;
truncate `commercedb`.`tax_authority`;


INSERT INTO `commercedb`.`tax_authority` (`name`, `rounding_code`, `rounding_digit_qty`, `created_by`, `created_date`) VALUES ('Central Govt of India', 'N', '2', 'admin', now());
INSERT INTO `commercedb`.`tax_authority` (`name`, `rounding_code`, `rounding_digit_qty`, `created_by`, `created_date`) VALUES ('State Govt of India', 'N', '2', 'admin', now());


INSERT INTO `commercedb`.`tax_location` (`name`, `description`, `created_by`, `created_date`) VALUES ('Within State', 'This tax location is for all sales within state', 'admin', now());
INSERT INTO `commercedb`.`tax_location` (`name`, `description`, `created_by`, `created_date`) VALUES ('Outside State', 'This tax location is for all sales outside state', 'admin', now());

INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST0', 'This tax group is applied when no GST is applicable', 'admin', now());
INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST5', 'This group is applied for 5 percent tax rate of GST', 'admin', now());
INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST10', 'This group is applied for 10 percent tax rate of GST', 'admin', now());
INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST12', 'This group is applied for 12 percent tax rate of GST', 'admin', now());
INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST18', 'This group is applied for 18 percent tax rate of GST', 'admin', now());
INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('GST28', 'This group is applied for 28 percent tax rate of GST', 'admin', now());


INSERT INTO `commercedb`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '0', 'SGST', 'This is the rules for group SGST', '0', '1', '0', 'SGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', 'CGST', 'This is the rules for group CGST', '1', '1', '0', 'CGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '2', '1', '0', 'IGST', 'This is the rules for group IGST', '0', '0', '0', 'IGST', 'A', 'admin', now()); 


INSERT INTO `commercedb`.`tax_bracket` (`seq_no`, `breakpoint`, `tax_amount`, `created_date`, `created_by`) VALUES ('1', '-1', '-1', now(), 'admin');


INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '0', '0', now(), now(), '0', 'SGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '1', '1', now(), now(), '0', 'CGST', 'A', 'admin', now());

INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '0', '0', now(), now(), '2.5', 'SGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '1', '1', now(), now(), '2.5', 'CGST', 'A', 'admin', now());

INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '0', '0', now(), now(), '5', 'SGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '1', '1', now(), now(), '5', 'CGST', 'A', 'admin', now());

INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '0', '0', now(), now(), '6', 'SGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '1', '1', now(), now(), '6', 'CGST', 'A', 'admin', now());

INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '0', '0', now(), now(), '9', 'SGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '1', '1', now(), now(), '9', 'CGST', 'A', 'admin', now());

INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '0', '0', now(), now(), '14', 'SGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', '1', '1', now(), now(), '14', 'CGST', 'A', 'admin', now());

INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '2', '1', '1', '0', '0', now(), now(), '0', 'IGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '2', '1', '1', '0', '0', now(), now(), '5', 'IGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '2', '1', '1', '0', '0', now(), now(), '10', 'IGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '2', '1', '1', '0', '0', now(), now(), '12', 'IGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '2', '1', '1', '0', '0', now(), now(), '18', 'IGST', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`, `tax_bracket_id`, `seq_no`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '2', '1', '1', '0', '0', now(), now(), '28', 'IGST', 'A', 'admin', now());
commit;


-- -----------------------------------------------------
-- Seed data for item_hierarchy
-- -----------------------------------------------------
truncate `commercedb`.`item_hierarchy`;
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('Dept','Mens',1,'N','2017-12-07 17:55:32','admin',NULL);
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('SubDept','Clothes',1,'N','2017-12-07 17:56:25','admin',1);
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('Class','Shirts',1,'N','2017-12-07 18:01:00','admin',2);
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('SubClass','Formal',1,'N','2017-12-07 18:01:00','admin',3);
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('SubDept','Shoes',1,'N','2017-12-07 18:01:00','admin',1);
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('SubDept','Personal Care',1,'N','2017-12-07 18:01:00','admin',1);
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('Class','Pants',1,'N','2017-12-07 18:01:00','admin',2);
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('SubClass','Formal',1,'N','2017-12-07 18:01:00','admin',3);
INSERT INTO `item_hierarchy` (`level_code`,`description`,`sort_order`,`hidden_flag`,`created_date`,`created_by`,`parent_id`) VALUES ('SubClass','Casual',1,'N','2017-12-07 18:01:00','admin',3);

commit;


-- -----------------------------------------------------
-- Seed data for attribute_master
-- -----------------------------------------------------
truncate `commercedb`.`attribute_master`;
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (1,'L','S','Large Size','Size for an item',1);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (2,'M','S','Medium Size','Medium Size',2);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (3,'S','S','Small Size',NULL,3);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (4,'Red','C','Red Color','Red Color',1);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (5,'Green','C','Green',NULL,2);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (6,'4.5','D','Length',NULL,1);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (7,'6','D','Breadth',NULL,2);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (8,'8','D','Height',NULL,3);

commit;


-- -----------------------------------------------------
-- Seed data for user role
-- -----------------------------------------------------
INSERT INTO `commercedb`.`role` (`role_name`, `description`, `created_by`, `created_date`) VALUES ('ADMIN', 'This is admin role for administrators of the website', 'admin', now());
INSERT INTO `commercedb`.`role` (`role_name`, `description`, `created_by`, `created_date`) VALUES ('CASHIER', 'This is cashier role for cashiers ', 'admin', now());

-- -----------------------------------------------------
-- Seed data for user
-- -----------------------------------------------------
insert into commercedb.user (username, first_name, last_name, phone1, phone2, email, login_count, `status`, created_by, created_date)
values('admin@gmail.com', 'admin', 'admin', '8847523677', '8968834880', 'admin@gmail.com', '0', 'A', 'admin', '2018-01-16 20:19:09');
insert into commercedb.user (username, first_name, last_name, phone1, phone2, email, login_count, `status`, created_by, created_date)
values('cashier@gmail.com', 'cashier', 'cashier', '8847523677', '8968834880', 'cashier@gmail.com', '0', 'A', 'admin', '2018-01-16 20:19:09');


-- -----------------------------------------------------
-- Seed data for user_password
-- -----------------------------------------------------
insert into commercedb.user_password( username, password, modified_by, modified_date, `status`)
values('admin@gmail.com', '$2a$10$dsyfqFN/BAS0mmaiN00VD.8.NRcbA/VjR8aD8Q.xZyldJy59Vt/tO', 'admin', '2018-01-22 19:40:52', 'A');
insert into commercedb.user_password( username, password, modified_by, modified_date, `status`)
values('cashier@gmail.com', '$2a$10$dsyfqFN/BAS0mmaiN00VD.8.NRcbA/VjR8aD8Q.xZyldJy59Vt/tO', 'admin', '2018-01-22 19:40:52', 'A');

-- -----------------------------------------------------
-- Seed data for user role
-- -----------------------------------------------------
INSERT INTO `commercedb`.`user_role` (`username`, `role_id`, `location_id`, `created_by`, `created_date`, `begin_date`, `end_date`) VALUES ('admin@gmail.com', '1', '7997', 'admin', now(), now(), now());
INSERT INTO `commercedb`.`user_role` (`username`, `role_id`, `location_id`, `created_by`, `created_date`, `begin_date`, `end_date`) VALUES ('cashier@gmail.com', '2', '7997', 'admin', now(), now(), now());

-- -----------------------------------------------------
-- Seed data for user addresses
-- -----------------------------------------------------
insert into commercedb.address_master ( `primary`, address_type, address1, address2, city, state, country, pincode)
values('Y', 'Home', 'Kharar', '', 'Kharar', 'Punjab', 'India', '140301');
insert into commercedb.address_master ( `primary`, address_type, address1, address2, city, state, country, pincode)
values('Y', 'Home', 'Kharar', '', 'Kharar', 'Punjab', 'India', '140301');

insert into commercedb.user_address ( username, address_id)
values('admin@gmail.com', '1');

insert into commercedb.user_address ( username, address_id)
values('cashier@gmail.com', '2');


SET FOREIGN_KEY_CHECKS = 1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
