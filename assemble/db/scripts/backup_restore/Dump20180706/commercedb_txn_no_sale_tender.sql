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
-- Table structure for table `txn_no_sale_tender`
--

DROP TABLE IF EXISTS `txn_no_sale_tender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_no_sale_tender` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `seq_no` int(11) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `tender_id` int(3) NOT NULL,
  `to_account_no` varchar(100) DEFAULT NULL,
  `to_bank_name` varchar(100) DEFAULT NULL,
  `to_bank_branch` varchar(100) DEFAULT NULL,
  `to_payee_name` varchar(100) DEFAULT NULL,
  `to_payee_phone` varchar(20) DEFAULT NULL,
  `to_details` varchar(100) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`,`tender_id`,`created_date`),
  KEY `fk_tender_id_no_sale_tender_fk1_idx` (`tender_id`),
  CONSTRAINT `fk_tender_id_no_sale_tender_fk1` FOREIGN KEY (`tender_id`) REFERENCES `tender_master` (`tender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_no_sale_tender_txn_no_sale1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`) REFERENCES `txn_no_sale` (`location_id`, `business_date`, `register`, `txn_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_no_sale_tender`
--

LOCK TABLES `txn_no_sale_tender` WRITE;
/*!40000 ALTER TABLE `txn_no_sale_tender` DISABLE KEYS */;
/*!40000 ALTER TABLE `txn_no_sale_tender` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:14
