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
  `location_id` int(4) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `ordered_qty` int(11) NOT NULL,
  `cost_amount` decimal(10,0) NOT NULL DEFAULT '0',
  `total_cost` decimal(10,0) NOT NULL DEFAULT '0',
  `delievered_date` datetime DEFAULT NULL,
  `delievered_qty` int(11) DEFAULT NULL,
  `cost_actual_amount` decimal(10,0) DEFAULT '0',
  `total_actual_cost` decimal(10,0) DEFAULT '0',
  `discount_amount` decimal(10,0) DEFAULT '0',
  `tax_amount` decimal(10,0) DEFAULT '0',
  `total_actual_amount` decimal(10,0) DEFAULT '0',
  PRIMARY KEY (`order_id`,`location_id`,`item_id`),
  KEY `fk_purchase_order_items_purchase_order1_idx` (`order_id`),
  KEY `fk_purchase_order_items_item1_idx` (`item_id`),
  KEY `fk_purchase_order_items_location1_idx` (`location_id`),
  CONSTRAINT `fk_purchase_order_items_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_purchase_order1` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items`
--

LOCK TABLES `purchase_order_items` WRITE;
/*!40000 ALTER TABLE `purchase_order_items` DISABLE KEYS */;
INSERT INTO `purchase_order_items` VALUES (1,7997,10102,12,50,600,'2018-01-12 21:46:38',12,50,0,12,12,600),(1,7997,10103,2,10,20,'2018-01-12 21:46:38',2,10,0,12,2,10),(2,7997,10103,25,250,250,'2018-01-12 21:51:16',25,10,0,0,0,250),(3,7997,10102,4,45,180,'2018-01-15 18:46:13',NULL,0,0,0,0,0),(3,7997,10103,8,40,320,'2018-01-15 18:46:13',NULL,0,0,0,0,0);
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

-- Dump completed on 2018-01-16 18:57:55
