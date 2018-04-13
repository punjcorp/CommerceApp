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
-- Table structure for table `item_price`
--

DROP TABLE IF EXISTS `item_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_price` (
  `item_price_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NOT NULL,
  `location_id` int(4) NOT NULL,
  `price_change_type` varchar(5) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `item_price` decimal(12,2) NOT NULL DEFAULT '0.00',
  `status` varchar(1) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`item_price_id`),
  KEY `fk_item_price_item1_idx` (`item_id`),
  KEY `fk_item_price_location1_idx` (`location_id`),
  CONSTRAINT `fk_item_price_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_price_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_price`
--

LOCK TABLES `item_price` WRITE;
/*!40000 ALTER TABLE `item_price` DISABLE KEYS */;
INSERT INTO `item_price` VALUES (1,10000050103,7997,'1','2018-02-24 16:25:00',NULL,45.00,'C','admin@gmail.com','2018-02-15 10:14:19','admin@gmail.com','2018-02-15 12:51:50'),(2,10000050102,7997,'1','2018-02-18 14:15:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:15:50','admin@gmail.com','2018-02-15 12:55:04'),(3,10000050102,7997,'2','2018-02-19 17:20:00','2018-02-27 17:20:00',35.00,'A','admin@gmail.com','2018-02-15 10:16:25','admin@gmail.com','2018-02-15 22:16:05'),(4,10000050102,7997,'3','2018-02-19 00:00:00',NULL,20.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07'),(5,10000050102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'C','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41'),(6,10000030102,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07'),(7,10000020102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41'),(8,10000040102,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07'),(9,10000050102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41'),(10,10000060102,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07'),(11,10000070102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41'),(12,10000080102,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07'),(13,10000090102,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41'),(14,10000030103,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07'),(15,10000020103,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41'),(16,10000040103,7997,'1','2018-02-19 00:00:00',NULL,30.00,'A','admin@gmail.com','2018-02-15 10:16:55','admin@gmail.com','2018-02-16 19:15:07'),(17,10000050103,7997,'1','2018-02-20 09:25:00',NULL,20.00,'A','admin@gmail.com','2018-02-16 18:55:26','admin@gmail.com','2018-02-17 10:36:41');
/*!40000 ALTER TABLE `item_price` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:07
