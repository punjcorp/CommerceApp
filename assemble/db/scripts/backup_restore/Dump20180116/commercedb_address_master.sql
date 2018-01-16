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
-- Table structure for table `address_master`
--

DROP TABLE IF EXISTS `address_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address_master` (
  `address_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `primary` varchar(1) NOT NULL DEFAULT 'N',
  `address_type` varchar(20) NOT NULL,
  `address1` varchar(150) NOT NULL,
  `address2` varchar(150) DEFAULT NULL,
  `city` varchar(30) NOT NULL,
  `state` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `pincode` varchar(6) NOT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_master`
--

LOCK TABLES `address_master` WRITE;
/*!40000 ALTER TABLE `address_master` DISABLE KEYS */;
INSERT INTO `address_master` VALUES (1,'Y','Home','kharar','','kharar','punjab','India','140301'),(2,'Y','Home','kharar','','kharar','punjab','India','140301'),(5,'Y','Home','kharar','','kharar','punjab','India','140301'),(6,'Y','Home','kharar','','kharar','punjab','India','140301'),(12,'Y','Home','kharar 12','Vizag','kharar','punjab','India','140301'),(13,'Y','Home','kharar 13','','kharar','punjab','India','140301'),(14,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(15,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(16,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(17,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(18,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(19,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(20,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(21,'Y','Home','kharar 13','','kharar','punjab','India','140301'),(22,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(23,'Y','Home','Vizag','Vizag','Vizag','AP','India','140301'),(24,'Y','Home','Vizag','','Vizag','AP','India','140301'),(25,'Y','Home','Vizag','','Vizag','AP','India','140301'),(26,'Y','Home','kharar 12','','kharar','punjab','India','140301'),(27,'Y','Home','kharar 12','Vizag','kharar 12','punjab','India','140301'),(29,'N','Home','kharar 12','','kharar','punjab','India','140301'),(30,'N','Home','Sanam','','Sanam','Punjab','India','140301'),(31,'Y','','Vizag','Vizag','Vizag','AP','India','140301'),(32,'Y','Home','Kharar','Kharar','Kharar','Punjab','India','140301'),(33,'Y','Home','# 2848, W No 12','Near Imli wala Mandir','Kharar','Punjab','India','140301'),(34,'Y','Home','# 2848, W No 12','Near Imli wala Mandir','Kharar','Punjab','India','140301');
/*!40000 ALTER TABLE `address_master` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:54
