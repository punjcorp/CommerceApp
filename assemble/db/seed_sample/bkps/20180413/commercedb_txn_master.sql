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
-- Table structure for table `txn_master`
--

DROP TABLE IF EXISTS `txn_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_master` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `offline_flag` tinyint(4) DEFAULT NULL,
  `session_id` varchar(45) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `total` decimal(12,2) NOT NULL,
  `tax_total` decimal(12,2) NOT NULL,
  `discount_total` decimal(12,2) NOT NULL,
  `round_total` decimal(12,2) NOT NULL,
  `subtotal` decimal(12,2) NOT NULL,
  `cancel_reason_code` varchar(20) DEFAULT NULL,
  `txn_type` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL,
  `post_void_flag` tinyint(4) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`),
  KEY `fk_txn_master_register_master1_idx` (`location_id`,`register`),
  CONSTRAINT `fk_txn_master_register_master1` FOREIGN KEY (`location_id`, `register`) REFERENCES `register_master` (`location_id`, `register_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_master`
--

LOCK TABLES `txn_master` WRITE;
/*!40000 ALTER TABLE `txn_master` DISABLE KEYS */;
INSERT INTO `txn_master` VALUES (7997,'2018-03-26 00:00:00',1,1,'2018-03-26 14:39:57',NULL,NULL,NULL,'admin@gmail.com','2018-03-26 14:39:57',0.00,0.00,0.00,0.00,0.00,NULL,'OPEN_STORE','STARTED',NULL,NULL,NULL),(7997,'2018-03-26 00:00:00',1,2,'2018-03-26 14:40:09',NULL,NULL,NULL,'admin@gmail.com','2018-03-26 14:40:09',0.00,0.00,0.00,0.00,0.00,NULL,'OPEN_REGISTER','STARTED',NULL,NULL,NULL),(7997,'2018-03-26 00:00:00',1,3,'2018-03-27 07:58:48','2018-03-27 07:59:14',NULL,NULL,'admin@gmail.com','2018-03-27 19:59:15',172.80,37.80,0.00,0.00,135.00,NULL,'SALE','COMPLETED',NULL,NULL,NULL),(7997,'2018-03-26 00:00:00',1,4,'2018-03-27 22:22:17','2018-03-27 22:22:17',NULL,NULL,'admin@gmail.com','2018-03-27 22:22:17',43.00,0.00,0.00,0.00,43.00,NULL,'NOSALE','COMPLETED',NULL,NULL,NULL),(7997,'2018-03-26 00:00:00',1,5,'2018-04-12 10:37:57','2018-04-12 10:38:25',NULL,NULL,'admin@gmail.com','2018-04-12 10:38:26',35.40,5.40,0.00,0.00,30.00,NULL,'SALE','COMPLETED',NULL,NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,'2018-04-12 10:42:31','2018-04-12 10:43:18',NULL,NULL,'admin@gmail.com','2018-04-12 10:43:19',217.86,40.86,3.00,0.00,180.00,NULL,'SALE','COMPLETED',NULL,NULL,NULL);
/*!40000 ALTER TABLE `txn_master` ENABLE KEYS */;
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
