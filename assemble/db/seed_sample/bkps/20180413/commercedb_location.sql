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
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `location_id` int(4) NOT NULL,
  `location_type` varchar(15) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(150) DEFAULT NULL,
  `status` varchar(5) NOT NULL,
  `address1` varchar(150) NOT NULL,
  `address2` varchar(150) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `pincode` varchar(6) NOT NULL,
  `neighborhood` varchar(100) DEFAULT NULL,
  `locale` varchar(10) NOT NULL,
  `default_tender` varchar(30) NOT NULL DEFAULT 'CASH',
  `currency` varchar(3) NOT NULL,
  `telephone1` varchar(12) NOT NULL,
  `telephone2` varchar(12) DEFAULT NULL,
  `store_manager` varchar(80) DEFAULT NULL,
  `email_address` varchar(150) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (2,'W','Warehouse 1','this is sample warehouse 1','A','Mohali',NULL,'Mohali','Punjab','India','140301','7 Phase','en_US','CASH','INR','8847523677',NULL,'Amit','eramitpunj@gmail.com','2018-03-24 11:14:53','Admin',NULL,NULL),(3,'S','Store 2','this is sample store 2','A','Mohali',NULL,'Mohali','Punjab','India','140301','7 Phase','en_US','CASH','INR','8847523677',NULL,'Amit','eramitpunj@gmail.com','2018-03-24 11:14:53','Admin',NULL,NULL),(27,'S','Store 3','this is sample store 3','A','Chandigarh',NULL,'Chandigarh','Punjab','India','140301','Sector 34','en_US','CASH','INR','8847523677',NULL,'Amit','eramitpunj@gmail.com','2018-03-24 11:14:53','Admin',NULL,NULL),(7997,'S','commerce site','This is the descripton for only location in commerce site as of now','A','Kharar',NULL,'Kharar','Panjab','India','140301','Arya Collage road','en_US','CASH','INR','8847523677',NULL,'Amit','eramitpunj@gmail.com','2017-12-11 12:56:07','Admin',NULL,NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:00
