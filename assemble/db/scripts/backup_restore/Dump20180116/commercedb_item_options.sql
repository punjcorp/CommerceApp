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
-- Table structure for table `item_options`
--

DROP TABLE IF EXISTS `item_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_options` (
  `item_id` bigint(20) NOT NULL,
  `uom` varchar(20) NOT NULL,
  `discount_flag` tinyint(4) NOT NULL DEFAULT '1',
  `tax_flag` tinyint(4) NOT NULL DEFAULT '1',
  `ask_qty_flag` tinyint(4) NOT NULL DEFAULT '1',
  `ask_price_flag` tinyint(4) NOT NULL DEFAULT '0',
  `unit_cost` decimal(10,0) NOT NULL DEFAULT '0',
  `suggested_price` decimal(10,0) NOT NULL DEFAULT '0',
  `current_price` decimal(10,0) NOT NULL DEFAULT '0',
  `compare_at_price` decimal(10,0) NOT NULL,
  `return_flag` tinyint(4) NOT NULL DEFAULT '1',
  `desc_flag` tinyint(4) NOT NULL DEFAULT '0',
  `related_item_flag` tinyint(4) NOT NULL DEFAULT '0',
  `tax_group_id` int(11) NOT NULL,
  `restocking_fee` decimal(10,0) DEFAULT NULL,
  `price_change_flag` tinyint(4) NOT NULL DEFAULT '1',
  `non_merch_flag` tinyint(4) NOT NULL DEFAULT '0',
  `min_age_flag` tinyint(4) NOT NULL DEFAULT '0',
  `stock_status` varchar(20) NOT NULL DEFAULT '0',
  `customer_prompt` tinyint(4) NOT NULL DEFAULT '0',
  `shipping_weight` decimal(10,0) DEFAULT NULL,
  `pack_size` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `fk_item_options_item1_idx` (`item_id`),
  CONSTRAINT `fk_item_options_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_options`
--

LOCK TABLES `item_options` WRITE;
/*!40000 ALTER TABLE `item_options` DISABLE KEYS */;
INSERT INTO `item_options` VALUES (1,'Units',1,1,1,1,20,22,30,32,1,0,0,1,2,1,0,0,'NIL',0,0,'1'),(10102,'Units',1,1,1,1,20,22,30,32,1,0,0,1,2,1,0,0,'NIL',1,0,'1'),(10103,'Units',1,1,1,1,20,22,30,32,1,0,0,1,2,1,0,0,'NIL',1,0,'1');
/*!40000 ALTER TABLE `item_options` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:46
