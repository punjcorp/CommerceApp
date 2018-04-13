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
-- Table structure for table `purchase_order_items`
--

DROP TABLE IF EXISTS `purchase_order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_order_items` (
  `order_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_name` varchar(150) NOT NULL,
  `unit_cost` decimal(12,2) NOT NULL,
  `ordered_qty` decimal(12,2) NOT NULL,
  `cost_amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `tax_amount` decimal(12,2) DEFAULT '0.00',
  `total_cost` decimal(12,2) NOT NULL DEFAULT '0.00',
  `actual_unit_cost` decimal(12,2) DEFAULT '0.00',
  `delievered_date` datetime DEFAULT NULL,
  `delievered_qty` decimal(12,2) DEFAULT NULL,
  `actual_cost_amount` decimal(12,2) DEFAULT '0.00',
  `actual_discount_amount` decimal(12,2) DEFAULT '0.00',
  `actual_tax_amount` decimal(12,2) DEFAULT '0.00',
  `actual_total_cost` decimal(12,2) DEFAULT '0.00',
  PRIMARY KEY (`order_id`,`item_id`),
  KEY `fk_purchase_order_items_purchase_order1_idx` (`order_id`),
  KEY `fk_purchase_order_items_item1_idx` (`item_id`),
  CONSTRAINT `fk_purchase_order_items_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_purchase_order1` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items`
--

LOCK TABLES `purchase_order_items` WRITE;
/*!40000 ALTER TABLE `purchase_order_items` DISABLE KEYS */;
INSERT INTO `purchase_order_items` VALUES (61,10000050103,'Item Name',40.00,1.00,40.00,11.20,51.20,45.00,'2018-04-09 20:05:41',5.00,225.00,0.00,63.00,288.00),(62,10000050103,'Item Name',40.00,1.00,40.00,11.20,51.20,0.00,'2018-04-10 14:22:49',NULL,0.00,0.00,0.00,0.00),(63,10000050103,'Item Name',40.00,1.00,40.00,11.20,51.20,0.00,'2018-04-10 14:26:59',NULL,0.00,0.00,0.00,0.00),(64,10000050103,'Item Name',30.00,1.00,30.00,8.40,38.40,0.00,'2018-04-10 14:43:30',NULL,0.00,0.00,0.00,0.00),(65,10000050103,'Item Name',40.00,1.00,40.00,11.20,51.20,0.00,'2018-04-10 14:47:07',NULL,0.00,0.00,0.00,0.00),(66,10000050103,'Item Name',40.00,1.00,40.00,11.20,51.20,40.00,'2018-04-10 15:15:18',1.00,40.00,0.00,11.20,51.20),(67,10000050103,'Item Name',30.00,1.00,30.00,8.40,38.40,0.00,'2018-04-10 15:06:01',NULL,0.00,0.00,0.00,0.00),(68,10000050103,'Item Name',40.00,1.00,40.00,11.20,51.20,0.00,'2018-04-10 18:23:38',NULL,0.00,0.00,0.00,0.00),(69,10000050103,'Item Name',30.00,1.00,30.00,8.40,38.40,0.00,'2018-04-12 17:28:35',NULL,0.00,0.00,0.00,0.00),(70,10000050103,'Item Name',40.00,1.00,40.00,11.20,51.20,0.00,'2018-04-12 19:04:04',NULL,0.00,0.00,0.00,0.00);
/*!40000 ALTER TABLE `purchase_order_items` ENABLE KEYS */;
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
