-- MySQL dump 10.13  Distrib 5.7.18, for Win64 (x86_64)
--
-- Host: localhost    Database: commercedb
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary table structure for view `v_item_location_tax`
--

DROP TABLE IF EXISTS `v_item_location_tax`;
/*!50001 DROP VIEW IF EXISTS `v_item_location_tax`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_item_location_tax` AS SELECT 
 1 AS `item_id`,
 1 AS `name`,
 1 AS `long_desc`,
 1 AS `base_unit_cost`,
 1 AS `location_id`,
 1 AS `tax_group_id`,
 1 AS `tax_group_name`,
 1 AS `sgst_rate_rule_id`,
 1 AS `sgst_rate`,
 1 AS `sgst_amount`,
 1 AS `sgst_code`,
 1 AS `cgst_rate_rule_id`,
 1 AS `cgst_rate`,
 1 AS `cgst_amount`,
 1 AS `cgst_code`,
 1 AS `igst_rate_rule_id`,
 1 AS `igst_rate`,
 1 AS `igst_amount`,
 1 AS `igst_code`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `v_receipt_li_item`
--

DROP TABLE IF EXISTS `v_receipt_li_item`;
/*!50001 DROP VIEW IF EXISTS `v_receipt_li_item`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_receipt_li_item` AS SELECT 
 1 AS `name`,
 1 AS `long_desc`,
 1 AS `seq_no`,
 1 AS `upc_no`,
 1 AS `hsn_no`,
 1 AS `qty`,
 1 AS `unit_price`,
 1 AS `extended_amount`,
 1 AS `discount_amount`,
 1 AS `tax_amount`,
 1 AS `gross_amount`,
 1 AS `location_id`,
 1 AS `register`,
 1 AS `business_date`,
 1 AS `txn_no`,
 1 AS `item_id`,
 1 AS `SGST_tax_group_id`,
 1 AS `SGST_tax_rule_rate_id`,
 1 AS `SGST_percentage`,
 1 AS `SGST_amount`,
 1 AS `CGST_tax_group_id`,
 1 AS `CGST_tax_rule_rate_id`,
 1 AS `CGST_percentage`,
 1 AS `CGST_amount`,
 1 AS `IGST_tax_group_id`,
 1 AS `IGST_tax_rule_rate_id`,
 1 AS `IGST_percentage`,
 1 AS `IGST_amount`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `v_location_tax`
--

DROP TABLE IF EXISTS `v_location_tax`;
/*!50001 DROP VIEW IF EXISTS `v_location_tax`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_location_tax` AS SELECT 
 1 AS `location_id`,
 1 AS `billing_location`,
 1 AS `tax_group_id`,
 1 AS `tax_group_name`,
 1 AS `tax_group_desc`,
 1 AS `tax_group_rate_seq`,
 1 AS `tax_group_rate_name`,
 1 AS `tax_group_rate_desc`,
 1 AS `compound_flag`,
 1 AS `type_code`,
 1 AS `tax_rate_rule_id`,
 1 AS `seq_no`,
 1 AS `effective_Date`,
 1 AS `expiry_date`,
 1 AS `percentage`,
 1 AS `amount`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `v_item_location_tax`
--

/*!50001 DROP VIEW IF EXISTS `v_item_location_tax`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_item_location_tax` AS select `itm`.`item_id` AS `item_id`,`itm`.`name` AS `name`,`itm`.`long_desc` AS `long_desc`,`itmopt`.`unit_cost` AS `base_unit_cost`,`taxdtl`.`location_id` AS `location_id`,`taxdtl`.`tax_group_id` AS `tax_group_id`,`taxdtl`.`tax_group_name` AS `tax_group_name`,`taxdtl`.`sgst_rate_rule_id` AS `sgst_rate_rule_id`,`taxdtl`.`sgst_rate` AS `sgst_rate`,`taxdtl`.`sgst_amount` AS `sgst_amount`,`taxdtl`.`sgst_code` AS `sgst_code`,`taxdtl`.`cgst_rate_rule_id` AS `cgst_rate_rule_id`,`taxdtl`.`cgst_rate` AS `cgst_rate`,`taxdtl`.`cgst_amount` AS `cgst_amount`,`taxdtl`.`cgst_code` AS `cgst_code`,`taxdtl`.`igst_rate_rule_id` AS `igst_rate_rule_id`,`taxdtl`.`igst_rate` AS `igst_rate`,`taxdtl`.`igst_amount` AS `igst_amount`,`taxdtl`.`igst_code` AS `igst_code` from ((`commercedb`.`item` `itm` left join `commercedb`.`item_options` `itmopt` on((`itm`.`item_id` = `itmopt`.`item_id`))) left join (select `vlt`.`location_id` AS `location_id`,`vlt`.`tax_group_id` AS `tax_group_id`,`vlt`.`tax_group_name` AS `tax_group_name`,max((case when (`vlt`.`type_code` = 'SGST') then `vlt`.`tax_rate_rule_id` end)) AS `sgst_rate_rule_id`,max((case when (`vlt`.`tax_group_rate_name` = 'SGST') then `vlt`.`percentage` end)) AS `sgst_rate`,max((case when (`vlt`.`tax_group_rate_name` = 'SGST') then `vlt`.`amount` end)) AS `sgst_amount`,max((case when (`vlt`.`tax_group_rate_name` = 'SGST') then `vlt`.`type_code` end)) AS `sgst_code`,max((case when (`vlt`.`type_code` = 'CGST') then `vlt`.`tax_rate_rule_id` end)) AS `cgst_rate_rule_id`,max((case when (`vlt`.`tax_group_rate_name` = 'CGST') then `vlt`.`percentage` end)) AS `cgst_rate`,max((case when (`vlt`.`tax_group_rate_name` = 'CGST') then `vlt`.`amount` end)) AS `cgst_amount`,max((case when (`vlt`.`tax_group_rate_name` = 'CGST') then `vlt`.`type_code` end)) AS `cgst_code`,max((case when (`vlt`.`type_code` = 'IGST') then `vlt`.`tax_rate_rule_id` end)) AS `igst_rate_rule_id`,max((case when (`vlt`.`tax_group_rate_name` = 'IGST') then `vlt`.`percentage` end)) AS `igst_rate`,max((case when (`vlt`.`tax_group_rate_name` = 'IGST') then `vlt`.`amount` end)) AS `igst_amount`,max((case when (`vlt`.`tax_group_rate_name` = 'IGST') then `vlt`.`type_code` end)) AS `igst_code` from `commercedb`.`v_location_tax` `vlt` group by `vlt`.`location_id`,`vlt`.`tax_group_id`) `taxdtl` on((`itmopt`.`tax_group_id` = `taxdtl`.`tax_group_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_receipt_li_item`
--

/*!50001 DROP VIEW IF EXISTS `v_receipt_li_item`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_receipt_li_item` AS select `itm`.`name` AS `name`,`itm`.`long_desc` AS `long_desc`,`li_itm`.`seq_no` AS `seq_no`,`li_itm`.`upc_no` AS `upc_no`,`li_itm`.`upc_no` AS `hsn_no`,`li_itm`.`qty` AS `qty`,`li_itm`.`unit_price` AS `unit_price`,`li_itm`.`extended_amount` AS `extended_amount`,`li_itm`.`discount_amount` AS `discount_amount`,`li_itm`.`tax_amount` AS `tax_amount`,`li_itm`.`gross_amount` AS `gross_amount`,`tax_dtl`.`location_id` AS `location_id`,`tax_dtl`.`register` AS `register`,`tax_dtl`.`business_date` AS `business_date`,`tax_dtl`.`txn_no` AS `txn_no`,`tax_dtl`.`item_id` AS `item_id`,`tax_dtl`.`SGST_tax_group_id` AS `SGST_tax_group_id`,`tax_dtl`.`SGST_tax_rule_rate_id` AS `SGST_tax_rule_rate_id`,`tax_dtl`.`SGST_percentage` AS `SGST_percentage`,`tax_dtl`.`SGST_amount` AS `SGST_amount`,`tax_dtl`.`CGST_tax_group_id` AS `CGST_tax_group_id`,`tax_dtl`.`CGST_tax_rule_rate_id` AS `CGST_tax_rule_rate_id`,`tax_dtl`.`CGST_percentage` AS `CGST_percentage`,`tax_dtl`.`CGST_amount` AS `CGST_amount`,`tax_dtl`.`IGST_tax_group_id` AS `IGST_tax_group_id`,`tax_dtl`.`IGST_tax_rule_rate_id` AS `IGST_tax_rule_rate_id`,`tax_dtl`.`IGST_percentage` AS `IGST_percentage`,`tax_dtl`.`IGST_amount` AS `IGST_amount` from ((`commercedb`.`txn_li_item` `li_itm` join `commercedb`.`item` `itm`) join (select `li_tax`.`location_id` AS `location_id`,`li_tax`.`register` AS `register`,`li_tax`.`business_date` AS `business_date`,`li_tax`.`txn_no` AS `txn_no`,`li_tax`.`item_id` AS `item_id`,max((case when (`tax_rr`.`type_code` = 'SGST') then `li_tax`.`tax_group_id` end)) AS `SGST_tax_group_id`,max((case when (`tax_rr`.`type_code` = 'SGST') then `li_tax`.`tax_rule_rate_id` end)) AS `SGST_tax_rule_rate_id`,max((case when (`tax_rr`.`type_code` = 'SGST') then `li_tax`.`tax_rule_percentage` end)) AS `SGST_percentage`,max((case when (`tax_rr`.`type_code` = 'SGST') then `li_tax`.`total_tax_amount` end)) AS `SGST_amount`,max((case when (`tax_rr`.`type_code` = 'CGST') then `li_tax`.`tax_group_id` end)) AS `CGST_tax_group_id`,max((case when (`tax_rr`.`type_code` = 'CGST') then `li_tax`.`tax_rule_rate_id` end)) AS `CGST_tax_rule_rate_id`,max((case when (`tax_rr`.`type_code` = 'CGST') then `li_tax`.`tax_rule_percentage` end)) AS `CGST_percentage`,max((case when (`tax_rr`.`type_code` = 'CGST') then `li_tax`.`total_tax_amount` end)) AS `CGST_amount`,max((case when (`tax_rr`.`type_code` = 'IGST') then `li_tax`.`tax_group_id` end)) AS `IGST_tax_group_id`,max((case when (`tax_rr`.`type_code` = 'IGST') then `li_tax`.`tax_rule_rate_id` end)) AS `IGST_tax_rule_rate_id`,max((case when (`tax_rr`.`type_code` = 'IGST') then `li_tax`.`tax_rule_percentage` end)) AS `IGST_percentage`,max((case when (`tax_rr`.`type_code` = 'IGST') then `li_tax`.`total_tax_amount` end)) AS `IGST_amount` from (`commercedb`.`txn_li_tax` `li_tax` join `commercedb`.`tax_rate_rule` `tax_rr`) where (`tax_rr`.`tax_rate_rule_id` = `li_tax`.`tax_rule_rate_id`) group by `li_tax`.`location_id`,`li_tax`.`register`,`li_tax`.`business_date`,`li_tax`.`txn_no`,`li_tax`.`item_id`) `tax_dtl`) where ((`li_itm`.`item_id` = `itm`.`item_id`) and (`li_itm`.`location_id` = `tax_dtl`.`location_id`) and (`li_itm`.`register` = `tax_dtl`.`register`) and (`li_itm`.`business_date` = `tax_dtl`.`business_date`) and (`li_itm`.`txn_no` = `tax_dtl`.`txn_no`) and (`li_itm`.`item_id` = `tax_dtl`.`item_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_location_tax`
--

/*!50001 DROP VIEW IF EXISTS `v_location_tax`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_location_tax` AS select `tlm`.`location_id` AS `location_id`,`tl`.`code` AS `billing_location`,`tg`.`tax_group_id` AS `tax_group_id`,`tg`.`name` AS `tax_group_name`,`tg`.`description` AS `tax_group_desc`,`tgr`.`seq_no` AS `tax_group_rate_seq`,`tgr`.`name` AS `tax_group_rate_name`,`tgr`.`description` AS `tax_group_rate_desc`,`tgr`.`compound_flag` AS `compound_flag`,`tgr`.`type_code` AS `type_code`,`trr`.`tax_rate_rule_id` AS `tax_rate_rule_id`,`trr`.`seq_no` AS `seq_no`,`trr`.`effective_date` AS `effective_Date`,`trr`.`expiry_date` AS `expiry_date`,`trr`.`percentage` AS `percentage`,`trr`.`amount` AS `amount` from ((((`tax_location_mapping` `tlm` join `tax_location` `tl`) join `tax_group` `tg`) join `tax_group_rule` `tgr`) join `tax_rate_rule` `trr`) where ((`tl`.`tax_location_id` = `tlm`.`tax_location_id`) and (`tg`.`tax_group_id` = `tgr`.`tax_group_id`) and (`tlm`.`tax_location_id` = `tgr`.`tax_location_id`) and (`tg`.`tax_group_id` = `trr`.`tax_group_id`) and (`tlm`.`tax_location_id` = `trr`.`tax_location_id`) and (`tgr`.`tax_group_id` = `trr`.`tax_group_id`) and (`tgr`.`tax_location_id` = `trr`.`tax_location_id`) and (`tgr`.`tax_authority_id` = `trr`.`tax_authority_id`) and (`tgr`.`seq_no` = `trr`.`tax_group_rule_seq`)) order by `tg`.`name`,`tgr`.`name`,`tgr`.`seq_no`,`trr`.`seq_no` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Dumping events for database 'commercedb'
--

--
-- Dumping routines for database 'commercedb'
--
/*!50003 DROP PROCEDURE IF EXISTS `p_get_current_item_price` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_get_current_item_price`(IN i_item_id bigint, IN i_location_id int, OUT o_item_price_id bigint)
begin

SET @prev_value = NULL;
SET @rank_count = 0;
select item_price_id into o_item_price_id from (select *, CASE
    WHEN @prev_value = start_date then  @rank_count
    WHEN @prev_value := start_date THEN @rank_count := @rank_count + 1
END AS rank from (
select *, max(start_date) from `commercedb`.`item_price` where 
item_id = i_item_id
        AND location_id = i_location_id  and status='A' and price_change_type<>4
        and start_date<now()
        and (end_date>=now() or end_date is null)
        group by price_change_type,start_date order by price_change_type desc, start_date desc) curr_price)itm_price where rank=1;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `p_get_oldest_clearance` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `p_get_oldest_clearance`(IN i_item_id bigint, IN i_location_id int, OUT o_clearane_id bigint)
begin

SET @prev_value = NULL;
SET @rank_count = 0;
select item_price_id into o_clearane_id from (select *, CASE
    WHEN @prev_value = start_date then  @rank_count
    WHEN @prev_value := start_date THEN @rank_count := @rank_count + 1
END AS rank  from `commercedb`.`item_price` where 
item_id = i_item_id
        AND location_id = i_location_id  and status='A' and price_change_type=3 order by start_date asc)itm_price where rank=1;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 10:44:26
