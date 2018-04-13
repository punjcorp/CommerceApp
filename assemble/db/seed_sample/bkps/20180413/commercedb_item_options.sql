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
  `unit_cost` decimal(12,2) NOT NULL DEFAULT '0.00',
  `suggested_price` decimal(12,2) NOT NULL DEFAULT '0.00',
  `current_price` decimal(12,2) NOT NULL DEFAULT '0.00',
  `compare_at_price` decimal(12,2) NOT NULL,
  `return_flag` tinyint(4) NOT NULL DEFAULT '1',
  `desc_flag` tinyint(4) NOT NULL DEFAULT '0',
  `related_item_flag` tinyint(4) NOT NULL DEFAULT '0',
  `tax_group_id` int(11) NOT NULL,
  `restocking_fee` decimal(12,2) DEFAULT NULL,
  `price_change_flag` tinyint(4) NOT NULL DEFAULT '1',
  `non_merch_flag` tinyint(4) NOT NULL DEFAULT '0',
  `min_age_flag` tinyint(4) NOT NULL DEFAULT '0',
  `stock_status` varchar(20) NOT NULL DEFAULT '0',
  `customer_prompt` tinyint(4) NOT NULL DEFAULT '0',
  `shipping_weight` decimal(12,2) DEFAULT NULL,
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
INSERT INTO `item_options` VALUES (1000000,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000001,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000002,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000003,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000004,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000005,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000006,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000007,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000008,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(1000009,'Units',1,1,1,0,20.00,22.00,28.00,30.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(10000000102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(10000000103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,2,NULL,1,0,0,'NIL',0,NULL,'1'),(10000010102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,3,NULL,1,0,0,'NIL',0,NULL,'1'),(10000010103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,4,NULL,1,0,0,'NIL',0,NULL,'1'),(10000020102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,5,NULL,1,0,0,'NIL',0,NULL,'1'),(10000020103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,6,NULL,1,0,0,'NIL',0,NULL,'1'),(10000030102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,2,NULL,1,0,0,'NIL',0,NULL,'1'),(10000030103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,3,NULL,1,0,0,'NIL',0,NULL,'1'),(10000040102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(10000040103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,4,NULL,1,0,0,'NIL',0,NULL,'1'),(10000050102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,5,NULL,1,0,0,'NIL',0,NULL,'1'),(10000050103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,6,NULL,1,0,0,'NIL',0,NULL,'1'),(10000060102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(10000060103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,2,NULL,1,0,0,'NIL',0,NULL,'1'),(10000070102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,3,NULL,1,0,0,'NIL',0,NULL,'1'),(10000070103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,4,NULL,1,0,0,'NIL',0,NULL,'1'),(10000080102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,5,NULL,1,0,0,'NIL',0,NULL,'1'),(10000080103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,6,NULL,1,0,0,'NIL',0,NULL,'1'),(10000090102,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,1,NULL,1,0,0,'NIL',0,NULL,'1'),(10000090103,'Units',1,1,1,0,30.00,32.00,33.00,35.00,1,0,0,2,NULL,1,0,0,'NIL',0,NULL,'1');
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

-- Dump completed on 2018-04-13 13:45:01
