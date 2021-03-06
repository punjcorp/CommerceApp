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
-- Table structure for table `sku_generator`
--

DROP TABLE IF EXISTS `sku_generator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sku_generator` (
  `style_id` mediumint(7) unsigned zerofill NOT NULL,
  `color` int(2) unsigned zerofill NOT NULL,
  `size` int(2) unsigned zerofill NOT NULL,
  `status` varchar(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`style_id`,`color`,`size`),
  KEY `fk_sku_generator_style_generator1_idx` (`style_id`),
  CONSTRAINT `fk_sku_generator_style_generator1` FOREIGN KEY (`style_id`) REFERENCES `style_generator` (`style_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sku_generator`
--

LOCK TABLES `sku_generator` WRITE;
/*!40000 ALTER TABLE `sku_generator` DISABLE KEYS */;
INSERT INTO `sku_generator` VALUES (1000000,01,02,'Y'),(1000000,01,03,'Y'),(1000001,01,02,'Y'),(1000001,01,03,'Y'),(1000002,01,02,'Y'),(1000002,01,03,'Y'),(1000003,01,02,'Y'),(1000003,01,03,'Y'),(1000004,01,02,'Y'),(1000004,01,03,'Y'),(1000005,01,02,'Y'),(1000005,01,03,'Y'),(1000006,01,02,'Y'),(1000006,01,03,'Y'),(1000007,01,02,'Y'),(1000007,01,03,'Y'),(1000008,01,02,'Y'),(1000008,01,03,'Y'),(1000009,01,02,'Y'),(1000009,01,03,'Y');
/*!40000 ALTER TABLE `sku_generator` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:05
