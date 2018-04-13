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
-- Table structure for table `txn_li_item`
--

DROP TABLE IF EXISTS `txn_li_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_li_item` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `seq_no` int(3) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `qty` int(5) NOT NULL,
  `gross_qty` int(5) NOT NULL,
  `unit_price` decimal(12,2) NOT NULL,
  `discount_amount` decimal(12,2) NOT NULL,
  `extended_amount` decimal(12,2) NOT NULL,
  `tax_amount` decimal(12,2) NOT NULL,
  `return_flag` tinyint(4) NOT NULL,
  `entry_method` varchar(20) NOT NULL,
  `net_amount` decimal(12,2) DEFAULT NULL,
  `gross_amount` decimal(12,2) DEFAULT NULL,
  `returned_qty` int(5) DEFAULT NULL,
  `upc_no` varchar(20) DEFAULT NULL,
  `txn_type` varchar(20) DEFAULT NULL,
  `inv_action_code` varchar(20) DEFAULT NULL,
  `org_location_id` int(4) DEFAULT NULL,
  `org_business_date` datetime DEFAULT NULL,
  `org_register` int(3) DEFAULT NULL,
  `org_txn_no` int(5) DEFAULT NULL,
  `return_reason_code` varchar(20) DEFAULT NULL,
  `return_comment` varchar(150) DEFAULT NULL,
  `return_type_code` varchar(20) DEFAULT NULL,
  `rcpt_count` int(2) DEFAULT NULL,
  `base_unit_price` decimal(12,2) DEFAULT NULL,
  `base_extended_price` decimal(12,2) DEFAULT NULL,
  `new_description` varchar(150) DEFAULT NULL,
  `exclude_from_sales_flag` tinyint(4) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`,`seq_no`,`item_id`),
  KEY `fk_txn_li_item_item1_idx` (`item_id`),
  KEY `fk_txn_li_item_txn_line_item_master1_idx` (`location_id`,`business_date`,`register`,`txn_no`,`seq_no`),
  CONSTRAINT `fk_txn_li_item_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_li_item_txn_line_item_master1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`) REFERENCES `txn_line_item_master` (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_li_item`
--

LOCK TABLES `txn_li_item` WRITE;
/*!40000 ALTER TABLE `txn_li_item` DISABLE KEYS */;
INSERT INTO `txn_li_item` VALUES (7997,'2018-03-26 00:00:00',1,3,1,10000050103,3,3,45.00,0.00,135.00,37.80,0,'MANUAL',135.00,172.80,NULL,'10000050103','SALE','SUBTRACT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,45.00,135.00,NULL,0,'admin@gmail.com','2018-03-27 19:59:15',NULL,NULL),(7997,'2018-03-26 00:00:00',1,5,1,10000050102,1,1,30.00,0.00,30.00,5.40,0,'MANUAL',30.00,35.40,NULL,'10000050102','SALE','SUBTRACT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,30.00,30.00,NULL,0,'admin@gmail.com','2018-04-12 10:38:26',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,1,10000050103,2,2,45.00,0.00,90.00,25.20,0,'MANUAL',90.00,115.20,NULL,'10000050103','SALE','SUBTRACT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,45.00,90.00,NULL,0,'admin@gmail.com','2018-04-12 10:43:19',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,4,10000050102,3,3,30.00,3.00,87.00,15.66,0,'MANUAL',87.00,102.66,NULL,'10000050102','SALE','SUBTRACT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,30.00,90.00,NULL,0,'admin@gmail.com','2018-04-12 10:43:19',NULL,NULL);
/*!40000 ALTER TABLE `txn_li_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:44:57
