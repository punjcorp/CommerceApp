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
-- Table structure for table `snapshots`
--

DROP TABLE IF EXISTS `snapshots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `snapshots` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(4) COLLATE utf8_bin NOT NULL DEFAULT 'U',
  `islast` tinyint(1) NOT NULL DEFAULT '0',
  `version` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `purge_status` int(11) DEFAULT NULL,
  `period1_mode` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period1_param` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period2_mode` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period2_param` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period3_mode` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period3_param` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period4_mode` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period4_param` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period5_mode` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `period5_param` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `build_date` bigint(20) DEFAULT NULL,
  `period1_date` bigint(20) DEFAULT NULL,
  `period2_date` bigint(20) DEFAULT NULL,
  `period3_date` bigint(20) DEFAULT NULL,
  `period4_date` bigint(20) DEFAULT NULL,
  `period5_date` bigint(20) DEFAULT NULL,
  `component_uuid` varchar(50) COLLATE utf8_bin NOT NULL,
  `uuid` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `analyses_uuid` (`uuid`),
  KEY `snapshot_component` (`component_uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snapshots`
--

LOCK TABLES `snapshots` WRITE;
/*!40000 ALTER TABLE `snapshots` DISABLE KEYS */;
INSERT INTO `snapshots` VALUES (1,'P',0,'1.0-SNAPSHOT',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1515681658976,1515682076968,NULL,NULL,NULL,NULL,NULL,'AWDlrzjyQ399a3HcZKo9','AWDlsItK6x5FibHtmdSw'),(2,'P',1,'1.0-SNAPSHOT',NULL,'previous_version',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1515747189875,1515747754633,1515681658976,NULL,NULL,NULL,NULL,'AWDlrzjyQ399a3HcZKo9','AWDpmqD6IyCG1-aW1NXP');
/*!40000 ALTER TABLE `snapshots` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:35
