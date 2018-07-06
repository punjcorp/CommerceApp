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
INSERT INTO `txn_line_item_master` VALUES (7997,'2018-07-04 00:00:00',3,3,1,'SALE_ITEM','COMPLETED',0,NULL,'2018-07-05 11:52:51','2018-07-05 11:53:09','admin','2018-07-05 23:53:10',NULL,NULL),(7997,'2018-07-04 00:00:00',3,3,2,'TAX_ITEM','COMPLETED',0,NULL,'2018-07-05 11:52:51','2018-07-05 11:53:09','admin','2018-07-05 23:53:10',NULL,NULL),(7997,'2018-07-04 00:00:00',3,3,3,'TAX_ITEM','COMPLETED',0,NULL,'2018-07-05 11:52:51','2018-07-05 11:53:09','admin','2018-07-05 23:53:10',NULL,NULL),(7997,'2018-07-04 00:00:00',3,3,4,'TENDER_ITEM','COMPLETED',0,NULL,'2018-07-05 11:52:51','2018-07-05 11:53:09','admin','2018-07-05 23:53:10',NULL,NULL),(7997,'2018-07-04 00:00:00',3,4,1,'SALE_ITEM','COMPLETED',0,NULL,'2018-07-06 07:20:45','2018-07-06 07:21:14','admin','2018-07-06 19:21:16',NULL,NULL),(7997,'2018-07-04 00:00:00',3,4,2,'TAX_ITEM','COMPLETED',0,NULL,'2018-07-06 07:20:45','2018-07-06 07:21:14','admin','2018-07-06 19:21:16',NULL,NULL),(7997,'2018-07-04 00:00:00',3,4,3,'TAX_ITEM','COMPLETED',0,NULL,'2018-07-06 07:20:45','2018-07-06 07:21:14','admin','2018-07-06 19:21:16',NULL,NULL),(7997,'2018-07-04 00:00:00',3,4,4,'TENDER_ITEM','COMPLETED',0,NULL,'2018-07-06 07:20:45','2018-07-06 07:21:14','admin','2018-07-06 19:21:16',NULL,NULL);
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

-- Dump completed on 2018-07-06 19:32:11
