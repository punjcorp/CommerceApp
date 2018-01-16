CREATE DATABASE  IF NOT EXISTS `sonar` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sonar`;
-- MySQL dump 10.13  Distrib 5.7.18, for Win64 (x86_64)
--
-- Host: localhost    Database: sonar
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
-- Table structure for table `quality_gate_conditions`
--

DROP TABLE IF EXISTS `quality_gate_conditions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quality_gate_conditions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qgate_id` int(11) DEFAULT NULL,
  `metric_id` int(11) DEFAULT NULL,
  `period` int(11) DEFAULT NULL,
  `operator` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `value_error` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `value_warning` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quality_gate_conditions`
--

LOCK TABLES `quality_gate_conditions` WRITE;
/*!40000 ALTER TABLE `quality_gate_conditions` DISABLE KEYS */;
INSERT INTO `quality_gate_conditions` VALUES (1,1,134,1,'GT','1',NULL,'2018-01-11 19:04:21',NULL),(2,1,130,1,'GT','1',NULL,'2018-01-11 19:04:21',NULL),(3,1,122,1,'GT','1',NULL,'2018-01-11 19:04:21',NULL),(4,1,38,1,'LT','80',NULL,'2018-01-11 19:04:21',NULL),(5,1,94,1,'GT','3',NULL,'2018-01-11 19:04:21',NULL);
/*!40000 ALTER TABLE `quality_gate_conditions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:17
