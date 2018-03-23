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
-- Truncate all tables
-- -----------------------------------------------------
truncate commercedb.item_images;
truncate commercedb.item_attributes;
truncate commercedb.item_options;
truncate commercedb.item;
truncate commercedb.sku_generator;
truncate commercedb.style_generator;
truncate commercedb.item_stock_details;
truncate commercedb.item_stock;


-- -----------------------------------------------------
-- Seed data for style generation
-- -----------------------------------------------------
insert into commercedb.style_generator(status) values('Y');
insert into commercedb.style_generator(status) values('Y');
insert into commercedb.style_generator(status) values('Y');
insert into commercedb.style_generator(status) values('Y');
insert into commercedb.style_generator(status) values('Y');
insert into commercedb.style_generator(status) values('Y');
insert into commercedb.style_generator(status) values('Y');
insert into commercedb.style_generator(status) values('Y');
insert into commercedb.style_generator(status) values('Y');

-- -----------------------------------------------------
-- Seed data for styles
-- -----------------------------------------------------
INSERT INTO `commercedb`.`item` (`item_id`, `name`, `long_desc`, `item_level`, `hierarchy_id`, `item_type`, `record_status`, `created_date`, `created_by`) VALUES ('1999999', 'Expense Parent', 'This is parent item for all type of expenses', '1', '1', 'Expense', 'A', now(), 'admin');
INSERT INTO `commercedb`.`item` (`item_id`, `name`, `long_desc`, `item_level`, `parent_item_id`, `hierarchy_id`, `item_type`, `record_status`, `created_date`, `created_by`) VALUES ('19999990001', 'Order Payment', 'This item is to do the order payment', '2', '1999999', '1', 'Expense', 'A', now(), 'admin');
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000000', 'Pants', 'This style is for all type of pants', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000001', 'Shirts', 'This style is for all type of Shirts', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000002', 'Jeans', 'This style is for all type of Jeans', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000003', 'Formal Shirts', 'This style is for all type of Formal Shirts', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000004', 'Soap', 'This style is for all type of Soap', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000005', 'Powder', 'This style is for all type of Powder', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000006', 'Deoderants', 'This style is for all type of Deoderants', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000007', 'Perfumes', 'This style is for all type of Perfumes', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000008', 'Sports Shoes', 'This style is for all type of Sports Shoes', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('1000009', 'Hair Bands', 'This style is for all type of Hair Bands', '1', NULL, '1', 'Merchandise', 'A', '2018-01-19 20:12:57', 'admin@gmail.com', NULL, NULL);

-- -----------------------------------------------------
-- Seed data for style options
-- -----------------------------------------------------
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000000', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000001', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000002', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000003', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000004', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000005', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000006', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000007', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000008', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options( item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('1000009', 'Units', '1', '1', '1', '0', '20', '22', '28', '30', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');

-- -----------------------------------------------------
-- Seed data for style images
-- -----------------------------------------------------
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000000', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000001', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000002', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000003', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000004', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000005', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000006', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000007', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000008', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');
insert into commercedb.item_images(item_id, feature_name, image_url, `name`, created_date, created_by)
values('1000009', 'Listing', 'abc.jpg', 'Pants', '2018-01-19 20:12:57', 'admin@gmail.com');


-- -----------------------------------------------------
-- Seed data for style attributes
-- -----------------------------------------------------
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000000', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000000', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000000', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000000', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000000', '1', 'L');

insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000001', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000001', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000001', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000001', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000001', '1', 'L');

insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000002', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000002', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000002', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000002', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000002', '1', 'L');

insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000003', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000003', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000003', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000003', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000003', '1', 'L');


insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000004', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000004', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000004', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000004', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000004', '1', 'L');

insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000005', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000005', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000005', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000005', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000005', '1', 'L');

insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000006', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000006', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000006', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000006', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000006', '1', 'L');

insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000007', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000007', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000007', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000007', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000007', '1', 'L');


insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000008', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000008', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000008', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000008', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000008', '1', 'L');


insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000009', '5', 'Green');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000009', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000009', '3', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000009', '2', 'M');
insert into commercedb.item_attributes( item_id, attribute_id, `value`) values('1000009', '1', 'L');


-- -----------------------------------------------------
-- Seed data for sku generator
-- -----------------------------------------------------
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000000', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000000', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000001', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000001', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000002', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000002', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000003', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000003', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000004', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000004', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000005', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000005', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000006', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000006', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000007', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000007', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000008', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000008', '01', '02', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000009', '01', '03', 'Y');
insert into commercedb.sku_generator (style_id, color, size, `status`)
values('1000009', '01', '02', 'Y');


-- -----------------------------------------------------
-- Seed data for items
-- -----------------------------------------------------
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000000102', 'Item Name', 'Item Description', '2', '1000000', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000000103', 'Item Name', 'Item Description', '2', '1000000', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000010102', 'Item Name', 'Item Description', '2', '1000001', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000010103', 'Item Name', 'Item Description', '2', '1000001', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000020102', 'Item Name', 'Item Description', '2', '1000002', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000020103', 'Item Name', 'Item Description', '2', '1000002', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000030102', 'Item Name', 'Item Description', '2', '1000003', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000030103', 'Item Name', 'Item Description', '2', '1000003', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000040102', 'Item Name', 'Item Description', '2', '1000004', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000040103', 'Item Name', 'Item Description', '2', '1000004', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000050102', 'Item Name', 'Item Description', '2', '1000005', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000050103', 'Item Name', 'Item Description', '2', '1000005', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000060102', 'Item Name', 'Item Description', '2', '1000006', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000060103', 'Item Name', 'Item Description', '2', '1000006', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000070102', 'Item Name', 'Item Description', '2', '1000007', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000070103', 'Item Name', 'Item Description', '2', '1000007', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000080102', 'Item Name', 'Item Description', '2', '1000008', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000080103', 'Item Name', 'Item Description', '2', '1000008', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);

insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000090102', 'Item Name', 'Item Description', '2', '1000009', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);
insert into commercedb.item(item_id, `name`, long_desc, item_level, parent_item_id, hierarchy_id, item_type, record_status, created_date, created_by, modified_date, modified_by)
values('10000090103', 'Item Name', 'Item Description', '2', '1000009', '1', 'Merchandise', 'A', '2018-01-19 20:29:48', 'admin@gmail.com', NULL, NULL);



-- -----------------------------------------------------
-- Seed data for items options
-- -----------------------------------------------------

insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000000102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000000103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '2', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000010102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '3', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000010103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '4', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000020102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '5', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000020103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '6', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000030102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '2', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000030103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '3', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000040102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000040103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '4', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000050102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '5', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000050103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '6', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000060102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000060103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '2', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000070102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '3', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000070103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '4', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000080102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '5', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000080103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '6', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000090102', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '1', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');
insert into commercedb.item_options (item_id, uom, discount_flag, tax_flag, ask_qty_flag, ask_price_flag, unit_cost, suggested_price, current_price, compare_at_price, return_flag, desc_flag, related_item_flag, tax_group_id, restocking_fee, price_change_flag, non_merch_flag, min_age_flag, stock_status, customer_prompt, shipping_weight, pack_size)
values('10000090103', 'Units', '1', '1', '1', '0', '30', '32', '33', '35', '1', '0', '0', '2', NULL, '1', '0', '0', 'NIL', '0', NULL, '1');



-- -----------------------------------------------------
-- Seed data for items images
-- -----------------------------------------------------
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000000102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000000103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000010102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000010103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000020102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000020103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');

insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000030102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000030103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');

insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000040102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000040103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');

insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000050102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000050103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');

insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000060102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000060103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');

insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000070102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000070103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');

insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000080102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000080103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');

insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000090102', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');
insert into commercedb.item_images ( item_id, feature_name, image_url, `name`, created_date, created_by)
values('10000090103', 'Listing', 'abc.jpg', 'Formal Pants', '2018-01-19 20:29:48', 'admin@gmail.com');




-- -----------------------------------------------------
-- Seed data for items attributes
-- -----------------------------------------------------
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000000103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000000103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000000102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000000102', '2', 'M');

insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000010103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000010103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000010102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000010102', '2', 'M');


insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000020103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000020103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000020102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000020102', '2', 'M');

insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000030103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000030103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000030102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000030102', '2', 'M');

insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000040103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000040103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000040102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000040102', '2', 'M');

insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000050103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000050103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000050102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000050102', '2', 'M');


insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000060103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000060103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000060102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000060102', '2', 'M');

insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000070103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000070103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000070102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000070102', '2', 'M');


insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000080103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000080103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000080102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000080102', '2', 'M');

insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000090103', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000090103', '2', 'S');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000090102', '4', 'Red');
insert into commercedb.item_attributes( item_id, attribute_id, `value`)
values('10000090102', '2', 'M');
commit;


-- -----------------------------------------------------
-- Seed data for item stock 
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS commercedb.p_inv_seed_data;

DELIMITER //
CREATE PROCEDURE commercedb.p_inv_seed_data()
BEGIN
	DECLARE no_more_rows BOOLEAN DEFAULT FALSE; 
	DECLARE v_item BIGINT;
	DECLARE v_location INTEGER(4);   
    DECLARE v_bucket INTEGER;
	DECLARE c_item CURSOR FOR
        select item_id from commercedb.item where item_level='2';
	DECLARE  c_location CURSOR FOR 
		select location_id from commercedb.location ;
	DECLARE  c_bucket CURSOR FOR 
		select stock_bucket_id from commercedb.stock_bucket ;        
	DECLARE CONTINUE HANDLER FOR NOT FOUND
	SET no_more_rows := TRUE;    
            
	TRUNCATE commercedb.item_stock;
    TRUNCATE commercedb.item_stock_details;
			
	OPEN c_item;
			
	get_item: LOOP
		FETCH c_item INTO v_item;
		IF no_more_rows THEN
			CLOSE c_item;
			LEAVE get_item;
		END IF;

		OPEN c_location;
           
        get_location: LOOP
			FETCH c_location INTO v_location;
            IF no_more_rows THEN
				SET no_more_rows := FALSE;
				CLOSE c_location;
				LEAVE get_location;
			END IF;
			
            INSERT INTO commercedb.item_stock(item_id, location_id, total_qty, non_sellable_qty,reserved_qty, stock_on_hand) VALUES(v_item,v_location,0,0,0,0);

			OPEN c_bucket;
			get_bucket: LOOP
				FETCH c_bucket INTO v_bucket;
				IF no_more_rows THEN
					SET no_more_rows := FALSE;
					CLOSE c_bucket;
					LEAVE get_bucket;
				END IF;
            
				INSERT INTO commercedb.item_stock_details(item_id, location_id, stock_bucket_id, total_qty) VALUES(v_item,v_location,v_bucket,0);
			END LOOP get_bucket;
            
		END LOOP get_location;
	END LOOP get_item;
	
    COMMIT;
    
END//
DELIMITER ;

CALL commercedb.p_inv_seed_data();

DROP PROCEDURE commercedb.p_inv_seed_data;


INSERT INTO `commercedb`.`item_price` (`item_price_id`,`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (1,10000050103,7997,'1','2018-02-24 16:25:00',NULL,45.00,'C','admin@gmail.com','2018-02-15 10:14:19','admin@gmail.com','2018-02-15 12:51:50');
INSERT INTO `commercedb`.`item_price` (`item_price_id`,`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (2,10000050102,7997,'1','2018-02-18 14:15:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:15:50','admin@gmail.com','2018-02-15 12:55:04');
INSERT INTO `commercedb`.`item_price` (`item_price_id`,`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (3,10000050102,7997,'2','2018-02-19 17:20:00','2018-02-27 17:20:00',35.00,'A','admin@gmail.com','2018-02-15 10:16:25','admin@gmail.com','2018-02-15 22:16:05');
INSERT INTO `commercedb`.`item_price` (`item_price_id`,`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (4,10000050102,7997,'3','2018-02-19 00:00:00',NULL,20.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07');
INSERT INTO `commercedb`.`item_price` (`item_price_id`,`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (5,10000050102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'C','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000030102,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000020102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000040102,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000050102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000060102,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000070102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000080102,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000090102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000030103,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000020103,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000040103,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07');
INSERT INTO `commercedb`.`item_price` (`item_id`,`location_id`,`price_change_type`,`start_date`,`end_date`,`item_price`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) VALUES (10000050103,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41');
commit;

SET FOREIGN_KEY_CHECKS = 1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
