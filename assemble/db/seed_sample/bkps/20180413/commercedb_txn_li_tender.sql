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
-- Table structure for table `txn_li_tender`
--

DROP TABLE IF EXISTS `txn_li_tender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_li_tender` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `seq_no` int(3) NOT NULL,
  `tender_id` int(11) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `change_flag` tinyint(4) NOT NULL,
  `type_code` varchar(40) NOT NULL,
  `action_code` varchar(40) DEFAULT NULL,
  `foreign_amount` decimal(12,2) DEFAULT NULL,
  `exchange_rate` decimal(12,2) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`,`seq_no`),
  KEY `fk_txn_li_tender_txn_line_item_master1_idx` (`location_id`,`business_date`,`register`,`txn_no`,`seq_no`),
  CONSTRAINT `fk_txn_li_tender_txn_line_item_master1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`) REFERENCES `txn_line_item_master` (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_li_tender`
--

LOCK TABLES `txn_li_tender` WRITE;
/*!40000 ALTER TABLE `txn_li_tender` DISABLE KEYS */;
INSERT INTO `txn_li_tender` VALUES (7997,'2018-03-26 00:00:00',1,3,4,1,172.80,0,'CASH',NULL,NULL,NULL,'admin@gmail.com','2018-03-27 19:59:15'),(7997,'2018-03-26 00:00:00',1,5,4,1,35.40,0,'CASH',NULL,NULL,NULL,'admin@gmail.com','2018-04-12 10:38:26'),(7997,'2018-03-26 00:00:00',1,6,7,1,217.86,0,'CASH',NULL,NULL,NULL,'admin@gmail.com','2018-04-12 10:43:19');
/*!40000 ALTER TABLE `txn_li_tender` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:13
