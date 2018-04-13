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
-- Table structure for table `txn_line_item_master`
--

DROP TABLE IF EXISTS `txn_line_item_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_line_item_master` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `seq_no` int(3) NOT NULL,
  `type_code` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `void_flag` tinyint(4) NOT NULL DEFAULT '0',
  `void_reason_code` varchar(20) DEFAULT NULL,
  `begin_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`,`seq_no`),
  KEY `fk_txn_line_item_master_txn_master1_idx` (`location_id`,`business_date`,`register`,`txn_no`),
  CONSTRAINT `fk_txn_line_item_master_txn_master1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`) REFERENCES `txn_master` (`location_id`, `business_date`, `register`, `txn_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_line_item_master`
--

LOCK TABLES `txn_line_item_master` WRITE;
/*!40000 ALTER TABLE `txn_line_item_master` DISABLE KEYS */;
INSERT INTO `txn_line_item_master` VALUES (7997,'2018-03-26 00:00:00',1,3,1,'SALE_ITEM','COMPLETED',0,NULL,'2018-03-27 07:58:48','2018-03-27 07:59:14','admin@gmail.com','2018-03-27 19:59:15',NULL,NULL),(7997,'2018-03-26 00:00:00',1,3,2,'TAX_ITEM','COMPLETED',0,NULL,'2018-03-27 07:58:48','2018-03-27 07:59:14','admin@gmail.com','2018-03-27 19:59:15',NULL,NULL),(7997,'2018-03-26 00:00:00',1,3,3,'TAX_ITEM','COMPLETED',0,NULL,'2018-03-27 07:58:48','2018-03-27 07:59:14','admin@gmail.com','2018-03-27 19:59:15',NULL,NULL),(7997,'2018-03-26 00:00:00',1,3,4,'TENDER_ITEM','COMPLETED',0,NULL,'2018-03-27 07:58:48','2018-03-27 07:59:14','admin@gmail.com','2018-03-27 19:59:15',NULL,NULL),(7997,'2018-03-26 00:00:00',1,5,1,'SALE_ITEM','COMPLETED',0,NULL,'2018-04-12 10:37:57','2018-04-12 10:38:25','admin@gmail.com','2018-04-12 10:38:26',NULL,NULL),(7997,'2018-03-26 00:00:00',1,5,2,'TAX_ITEM','COMPLETED',0,NULL,'2018-04-12 10:37:57','2018-04-12 10:38:25','admin@gmail.com','2018-04-12 10:38:26',NULL,NULL),(7997,'2018-03-26 00:00:00',1,5,3,'TAX_ITEM','COMPLETED',0,NULL,'2018-04-12 10:37:57','2018-04-12 10:38:25','admin@gmail.com','2018-04-12 10:38:26',NULL,NULL),(7997,'2018-03-26 00:00:00',1,5,4,'TENDER_ITEM','COMPLETED',0,NULL,'2018-04-12 10:37:57','2018-04-12 10:38:25','admin@gmail.com','2018-04-12 10:38:26',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,1,'SALE_ITEM','COMPLETED',0,NULL,'2018-04-12 10:42:31','2018-04-12 10:43:18','admin@gmail.com','2018-04-12 10:43:19',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,2,'TAX_ITEM','COMPLETED',0,NULL,'2018-04-12 10:42:31','2018-04-12 10:43:18','admin@gmail.com','2018-04-12 10:43:19',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,3,'TAX_ITEM','COMPLETED',0,NULL,'2018-04-12 10:42:31','2018-04-12 10:43:18','admin@gmail.com','2018-04-12 10:43:19',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,4,'SALE_ITEM','COMPLETED',0,NULL,'2018-04-12 10:42:31','2018-04-12 10:43:18','admin@gmail.com','2018-04-12 10:43:19',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,5,'TAX_ITEM','COMPLETED',0,NULL,'2018-04-12 10:42:31','2018-04-12 10:43:18','admin@gmail.com','2018-04-12 10:43:19',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,6,'TAX_ITEM','COMPLETED',0,NULL,'2018-04-12 10:42:31','2018-04-12 10:43:18','admin@gmail.com','2018-04-12 10:43:19',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,7,'TENDER_ITEM','COMPLETED',0,NULL,'2018-04-12 10:42:31','2018-04-12 10:43:18','admin@gmail.com','2018-04-12 10:43:19',NULL,NULL);
/*!40000 ALTER TABLE `txn_line_item_master` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:44:59
