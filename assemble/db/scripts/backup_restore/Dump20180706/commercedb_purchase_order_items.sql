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
  `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `actual_suggested_price` decimal(12,2) DEFAULT '0.00',
  `actual_max_retail_price` decimal(12,2) DEFAULT '0.00',
  `actual_discount_amount` decimal(12,2) DEFAULT '0.00',
  `actual_tax_amount` decimal(12,2) DEFAULT '0.00',
  `actual_total_cost` decimal(12,2) DEFAULT '0.00',
  PRIMARY KEY (`order_item_id`),
  KEY `fk_purchase_order_items_purchase_order1_idx` (`order_id`),
  KEY `fk_purchase_order_items_item1_idx` (`item_id`),
  CONSTRAINT `fk_purchase_order_items_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_purchase_order1` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items`
--

LOCK TABLES `purchase_order_items` WRITE;
/*!40000 ALTER TABLE `purchase_order_items` DISABLE KEYS */;
INSERT INTO `purchase_order_items` VALUES (1,1,10000010001,'Dove Coconut Milk Soap L',50.00,6.00,300.00,15.00,315.00,50.00,'2018-06-08 11:18:20',12.00,600.00,70.00,80.00,0.00,30.00,630.00),(2,2,10000010001,'Dove Coconut Milk Soap L',50.00,1.00,50.00,2.50,52.50,0.00,'2018-06-22 23:24:23',NULL,0.00,0.00,0.00,0.00,0.00,0.00),(3,2,10000140001,'item image 1  L',140.00,1.00,140.00,16.80,156.80,0.00,'2018-06-22 23:24:23',NULL,0.00,0.00,0.00,0.00,0.00,0.00);
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

-- Dump completed on 2018-07-06 19:32:33
