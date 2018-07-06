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
-- Table structure for table `txn_tender_count`
--

DROP TABLE IF EXISTS `txn_tender_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_tender_count` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `txn_type` varchar(30) NOT NULL,
  `tender_id` int(3) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `media_count` bigint(20) NOT NULL,
  `actual_amount` decimal(12,2) DEFAULT NULL,
  `actual_media_count` bigint(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`,`tender_id`),
  KEY `fk_txn_tndr_count_tender_master1_idx` (`tender_id`),
  CONSTRAINT `fk_txn_tndr_count_tender_master1` FOREIGN KEY (`tender_id`) REFERENCES `tender_master` (`tender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tndr_count_txn_master1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`) REFERENCES `txn_master` (`location_id`, `business_date`, `register`, `txn_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_tender_count`
--

LOCK TABLES `txn_tender_count` WRITE;
/*!40000 ALTER TABLE `txn_tender_count` DISABLE KEYS */;
INSERT INTO `txn_tender_count` VALUES (7997,'2018-07-04 00:00:00',1,91,'OPEN_STORE',1,1450.00,62,NULL,NULL,'admin','2018-07-04 22:48:06',NULL,NULL),(7997,'2018-07-04 00:00:00',3,2,'OPEN_REGISTER',1,550.00,53,NULL,NULL,'admin','2018-07-04 22:49:18',NULL,NULL);
/*!40000 ALTER TABLE `txn_tender_count` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:34
