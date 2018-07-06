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
-- Table structure for table `daily_totals`
--

DROP TABLE IF EXISTS `daily_totals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_totals` (
  `daily_totals_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location_id` int(11) NOT NULL,
  `register_id` varchar(45) DEFAULT NULL,
  `business_date` datetime NOT NULL,
  `total_txn_count` int(11) DEFAULT NULL,
  `total_sales_count` int(11) DEFAULT NULL,
  `total_returns_count` int(11) DEFAULT NULL,
  `total_txn_amount` decimal(12,2) DEFAULT NULL,
  `total_sales_amount` decimal(12,2) DEFAULT NULL,
  `total_returns_amount` decimal(12,2) DEFAULT NULL,
  `sod_amount` decimal(12,2) DEFAULT NULL,
  `eod_amount` decimal(12,2) DEFAULT NULL,
  `total_no_sales_amount` decimal(12,2) DEFAULT NULL,
  `total_no_sales_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`daily_totals_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_totals`
--

LOCK TABLES `daily_totals` WRITE;
/*!40000 ALTER TABLE `daily_totals` DISABLE KEYS */;
INSERT INTO `daily_totals` VALUES (1,7997,NULL,'2018-07-04 00:00:00',4,2,NULL,1660.00,210.00,NULL,1450.00,NULL,NULL,NULL),(2,7997,'3','2018-07-04 00:00:00',3,2,NULL,760.00,210.00,NULL,550.00,NULL,NULL,NULL);
/*!40000 ALTER TABLE `daily_totals` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:09
