CREATE DATABASE  IF NOT EXISTS `commercedb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `commercedb`;
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
-- Table structure for table `purchase_order_items_tax`
--

DROP TABLE IF EXISTS `purchase_order_items_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_order_items_tax` (
  `order_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `tax_group_id` int(11) NOT NULL,
  `tax_rate_rule_id` int(11) NOT NULL,
  `tax_amount` decimal(12,2) NOT NULL,
  `tax_percentage` decimal(12,2) NOT NULL,
  `tax_code` varchar(30) NOT NULL,
  `taxable_amount` decimal(12,2) NOT NULL,
  `actual_tax_amount` decimal(12,2) DEFAULT NULL,
  `actual_taxable_amount` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`order_id`,`item_id`,`tax_group_id`,`tax_rate_rule_id`),
  CONSTRAINT `fk_purchase_order_items_tax_purchase_order_items1` FOREIGN KEY (`order_id`, `item_id`) REFERENCES `purchase_order_items` (`order_id`, `item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items_tax`
--

LOCK TABLES `purchase_order_items_tax` WRITE;
/*!40000 ALTER TABLE `purchase_order_items_tax` DISABLE KEYS */;
INSERT INTO `purchase_order_items_tax` VALUES (61,10000050103,6,11,5.60,14.00,'SGST',40.00,31.50,225.00),(61,10000050103,6,12,5.60,14.00,'CGST',40.00,31.50,225.00),(62,10000050103,6,11,5.60,14.00,'SGST',40.00,0.00,0.00),(62,10000050103,6,12,5.60,14.00,'CGST',40.00,0.00,0.00),(63,10000050103,6,11,5.60,14.00,'SGST',40.00,0.00,0.00),(63,10000050103,6,12,5.60,14.00,'CGST',40.00,0.00,0.00),(64,10000050103,6,11,4.20,14.00,'SGST',30.00,0.00,0.00),(64,10000050103,6,12,4.20,14.00,'CGST',30.00,0.00,0.00),(65,10000050103,6,11,5.60,14.00,'SGST',40.00,0.00,0.00),(65,10000050103,6,12,5.60,14.00,'CGST',40.00,0.00,0.00),(66,10000050103,6,11,5.60,14.00,'SGST',40.00,5.60,40.00),(66,10000050103,6,12,5.60,14.00,'CGST',40.00,5.60,40.00),(67,10000050103,6,11,4.20,14.00,'SGST',30.00,0.00,0.00),(67,10000050103,6,12,4.20,14.00,'CGST',30.00,0.00,0.00),(68,10000050103,6,11,5.60,14.00,'SGST',40.00,0.00,0.00),(68,10000050103,6,12,5.60,14.00,'CGST',40.00,0.00,0.00),(69,10000050103,6,11,4.20,14.00,'SGST',30.00,0.00,0.00),(69,10000050103,6,12,4.20,14.00,'CGST',30.00,0.00,0.00),(70,10000050103,6,11,5.60,14.00,'SGST',40.00,0.00,0.00),(70,10000050103,6,12,5.60,14.00,'CGST',40.00,0.00,0.00);
/*!40000 ALTER TABLE `purchase_order_items_tax` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:17
