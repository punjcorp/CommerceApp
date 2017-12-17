-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema commercedb
-- -----------------------------------------------------
USE `commercedb` ;


-- -----------------------------------------------------
-- Seed data for location
-- -----------------------------------------------------
INSERT INTO `location` (`location_id`,`location_type`,`name`,`description`,`status`,`address1`,`address2`,`city`,`state`,`country`,`pincode`,`neighborhood`,`locale`,`currency`,`telephone1`,`telephone2`,`store_manager`,`email_address`,`created_date`,`created_by`,`modified_date`,`modified_by`) VALUES (7997,'S','commerce site','This is the descripton for only location in commerce site as of now','A','Kharar',NULL,'Kharar','Panjab','India','140301','Arya Collage road','en_US','INR','8847523677',NULL,'Amit','eramitpunj@gmail.com','2017-12-11 12:56:07','Admin',NULL,NULL);

-- -----------------------------------------------------
-- Seed data for tax tables
-- -----------------------------------------------------
INSERT INTO `commercedb`.`tax_authority` (`name`, `rounding_code`, `rounding_digit_qty`, `created_by`, `created_date`) VALUES ('Central Govt of India', 'N', '2', 'admin', now());
INSERT INTO `commercedb`.`tax_authority` (`name`, `rounding_code`, `rounding_digit_qty`, `created_by`, `created_date`) VALUES ('State Govt of India', 'N', '2', 'admin', now());


INSERT INTO `commercedb`.`tax_location` (`name`, `description`, `created_by`, `created_date`) VALUES ('Kharar', 'This tax location is for all kharar stores', 'admin', now());
INSERT INTO `commercedb`.`tax_location` (`name`, `description`, `created_by`, `created_date`) VALUES ('Chandigarh', 'This tax location is for all chandigarh stores', 'admin', now());


INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('SGST', 'This is the tax group for State Goods and Services Tax', 'admin', now());
INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('UGST', 'This is similar to SGST but for Union Territories', 'admin', now());
INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('IGST', 'This is for inter state within the country', 'admin', now());
INSERT INTO `commercedb`.`tax_group` (`name`, `description`, `created_by`, `created_date`) VALUES ('CGST', 'This tax group is for all the items where the central goods and services tax is applied', 'admin', now());


INSERT INTO `commercedb`.`tax_bracket` (`seq_no`, `breakpoint`, `tax_amount`, `created_date`, `created_by`) VALUES ('1', '-1', '-1', now(), 'admin');


INSERT INTO `commercedb`.`tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`, `name`, `description`, `compound_seq_nbr`, `compound_flag`, `trans_level_flag`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '1', 'SGST', 'This is the rules for group of SGST', '0', '1', '0', 'GST', 'A', 'admin', now());


INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_bracket_id`, `seq_no`, `min_taxable_amt`, `max_taxable_amt`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '-1', '-1', now(), now() + interval 99 YEAR, '2.5', 'Percentage', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_bracket_id`, `seq_no`, `min_taxable_amt`, `max_taxable_amt`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '-1', '-1', now(), now() + interval 99 YEAR, '9', 'Percentage', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_bracket_id`, `seq_no`, `min_taxable_amt`, `max_taxable_amt`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '-1', '-1', now(), now() + interval 99 YEAR, '6', 'Percentage', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_bracket_id`, `seq_no`, `min_taxable_amt`, `max_taxable_amt`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '-1', '-1', now(), now() + interval 99 YEAR, '14', 'Percentage', 'A', 'admin', now());
INSERT INTO `commercedb`.`tax_rate_rule` (`tax_group_id`, `tax_bracket_id`, `seq_no`, `min_taxable_amt`, `max_taxable_amt`, `effective_date`, `expiry_date`, `percentage`, `type_code`, `status`, `created_by`, `created_date`) VALUES ('1', '1', '1', '-1', '-1', now(), now() + interval 99 YEAR, '0', 'Percentage', 'A', 'admin', now());

-- -----------------------------------------------------
-- Seed data for item_hierarchy
-- -----------------------------------------------------
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
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (1,'L','S','Large Size','Size for an item',1);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (2,'M','S','Medium Size','Medium Size',2);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (3,'S','S','Small Size',NULL,3);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (4,'Red','C','Red Color','Red Color',1);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (5,'Green','C','Green',NULL,2);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (6,'4.5','D','Length',NULL,1);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (7,'6','D','Breadth',NULL,2);
INSERT INTO `commercedb`.`attribute_master` (`attribute_id`,`value`,`code`,`name`,`description`,`seq_no`) VALUES (8,'8','D','Height',NULL,3);

commit;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
