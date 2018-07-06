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
-- Table structure for table `account_head`
--

DROP TABLE IF EXISTS `account_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_head` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_id` int(4) NOT NULL,
  `entity_type` varchar(50) NOT NULL,
  `entity_id` bigint(20) NOT NULL,
  `advance_amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `due_amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_head_unique_idx` (`location_id`,`entity_type`,`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_head`
--

LOCK TABLES `account_head` WRITE;
/*!40000 ALTER TABLE `account_head` DISABLE KEYS */;
INSERT INTO `account_head` VALUES (1,2,'SUPPLIER',1,0.00,0.00,'admin@gmail.com','2018-06-08 10:56:09',NULL,NULL),(2,3,'SUPPLIER',1,0.00,0.00,'admin@gmail.com','2018-06-08 10:56:09',NULL,NULL),(3,27,'SUPPLIER',1,0.00,0.00,'admin@gmail.com','2018-06-08 10:56:09',NULL,NULL),(4,7997,'SUPPLIER',1,1650.00,0.00,'admin@gmail.com','2018-06-08 10:56:09','admin@gmail.com','2018-06-09 15:46:21'),(5,2,'SUPPLIER',2,0.00,0.00,'admin@gmail.com','2018-06-08 10:56:33',NULL,NULL),(6,3,'SUPPLIER',2,0.00,0.00,'admin@gmail.com','2018-06-08 10:56:33',NULL,NULL),(7,27,'SUPPLIER',2,0.00,0.00,'admin@gmail.com','2018-06-08 10:56:33',NULL,NULL),(8,7997,'SUPPLIER',2,270.00,100.00,'admin@gmail.com','2018-06-08 10:56:33','admin','2018-06-22 23:27:52'),(9,2,'SUPPLIER',3,0.00,0.00,'admin@gmail.com','2018-06-08 10:57:08',NULL,NULL),(10,3,'SUPPLIER',3,0.00,0.00,'admin@gmail.com','2018-06-08 10:57:08',NULL,NULL),(11,27,'SUPPLIER',3,0.00,0.00,'admin@gmail.com','2018-06-08 10:57:08',NULL,NULL),(12,7997,'SUPPLIER',3,0.00,0.00,'admin@gmail.com','2018-06-08 10:57:08',NULL,NULL),(13,2,'SUPPLIER',4,0.00,0.00,'admin@gmail.com','2018-06-08 10:57:31',NULL,NULL),(14,3,'SUPPLIER',4,0.00,0.00,'admin@gmail.com','2018-06-08 10:57:31',NULL,NULL),(15,27,'SUPPLIER',4,0.00,0.00,'admin@gmail.com','2018-06-08 10:57:31',NULL,NULL),(16,7997,'SUPPLIER',4,425.00,0.00,'admin@gmail.com','2018-06-08 10:57:31','admin@gmail.com','2018-06-09 16:01:02'),(17,7997,'CUSTOMER',1,10.50,0.00,'admin','2018-06-08 10:57:31','admin','2018-06-19 17:13:10'),(18,7997,'CUSTOMER',2,304.50,105.00,'admin','2018-06-08 10:57:31','admin','2018-06-22 23:19:38'),(19,7997,'CUSTOMER',3,0.00,0.00,'admin','2018-06-08 10:57:31',NULL,NULL),(20,7997,'CUSTOMER',4,0.00,0.00,'admin','2018-06-08 10:57:31',NULL,NULL),(21,7997,'CUSTOMER',5,105.00,0.00,'admin','2018-06-19 17:42:39','admin','2018-06-19 17:42:41');
/*!40000 ALTER TABLE `account_head` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:19
