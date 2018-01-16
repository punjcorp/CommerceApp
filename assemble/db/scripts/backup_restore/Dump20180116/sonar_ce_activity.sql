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
-- Table structure for table `ce_activity`
--

DROP TABLE IF EXISTS `ce_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ce_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) COLLATE utf8_bin NOT NULL,
  `task_type` varchar(15) COLLATE utf8_bin NOT NULL,
  `component_uuid` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `status` varchar(15) COLLATE utf8_bin NOT NULL,
  `is_last` tinyint(1) NOT NULL,
  `is_last_key` varchar(55) COLLATE utf8_bin NOT NULL,
  `submitter_login` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `submitted_at` bigint(20) NOT NULL,
  `started_at` bigint(20) DEFAULT NULL,
  `executed_at` bigint(20) DEFAULT NULL,
  `created_at` bigint(20) NOT NULL,
  `updated_at` bigint(20) NOT NULL,
  `execution_time_ms` bigint(20) DEFAULT NULL,
  `analysis_uuid` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `error_message` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `error_stacktrace` longtext COLLATE utf8_bin,
  `worker_uuid` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `execution_count` int(11) NOT NULL,
  `error_type` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ce_activity_uuid` (`uuid`),
  KEY `ce_activity_component_uuid` (`component_uuid`),
  KEY `ce_activity_islast_status` (`is_last`,`status`),
  KEY `ce_activity_islastkey` (`is_last_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ce_activity`
--

LOCK TABLES `ce_activity` WRITE;
/*!40000 ALTER TABLE `ce_activity` DISABLE KEYS */;
INSERT INTO `ce_activity` VALUES (1,'AWDlr3rdQ399a3HcZKpD','REPORT','AWDlrzjyQ399a3HcZKo9','SUCCESS',0,'REPORTAWDlrzjyQ399a3HcZKo9',NULL,1515681972143,1515681974330,1515682164741,1515682164741,1515747810395,190411,'AWDlsItK6x5FibHtmdSw',NULL,NULL,'AWDljgLq6x5FibHtmdSs',1,NULL),(2,'AWDpmXYZ-i5c0fxVoCGA','REPORT','AWDlrzjyQ399a3HcZKo9','SUCCESS',1,'REPORTAWDlrzjyQ399a3HcZKo9',NULL,1515747637746,1515747642212,1515747810395,1515747810395,1515747810395,168183,'AWDpmqD6IyCG1-aW1NXP',NULL,NULL,'AWDpf4FtIyCG1-aW1NXL',1,NULL);
/*!40000 ALTER TABLE `ce_activity` ENABLE KEYS */;
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
