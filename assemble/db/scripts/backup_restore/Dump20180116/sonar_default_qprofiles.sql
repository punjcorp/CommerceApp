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
-- Table structure for table `default_qprofiles`
--

DROP TABLE IF EXISTS `default_qprofiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `default_qprofiles` (
  `organization_uuid` varchar(40) COLLATE utf8_bin NOT NULL,
  `language` varchar(20) COLLATE utf8_bin NOT NULL,
  `qprofile_uuid` varchar(255) COLLATE utf8_bin NOT NULL,
  `created_at` bigint(20) NOT NULL,
  `updated_at` bigint(20) NOT NULL,
  PRIMARY KEY (`organization_uuid`,`language`),
  UNIQUE KEY `uniq_default_qprofiles_uuid` (`qprofile_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `default_qprofiles`
--

LOCK TABLES `default_qprofiles` WRITE;
/*!40000 ALTER TABLE `default_qprofiles` DISABLE KEYS */;
INSERT INTO `default_qprofiles` VALUES ('AWDlavjffBbpnGTMEcqe','cs','AWDlbtbvfBbpnGTMEdE5',1515677734639,1515677734639),('AWDlavjffBbpnGTMEcqe','css','AWDliyCSQ399a3HcZJ1W',1515679588514,1515679588514),('AWDlavjffBbpnGTMEcqe','flex','AWDlb1UIfBbpnGTMEdR_',1515677766920,1515677766920),('AWDlavjffBbpnGTMEcqe','java','AWDlbw3OfBbpnGTMEdMZ',1515677748686,1515677748686),('AWDlavjffBbpnGTMEcqe','js','AWDlb2qzfBbpnGTMEdXl',1515677772467,1515677772467),('AWDlavjffBbpnGTMEcqe','jsp','AWDli8WNQ399a3HcZKlH',1515679630733,1515679630733),('AWDlavjffBbpnGTMEcqe','less','AWDljBGaQ399a3HcZKnG',1515679650202,1515679650202),('AWDlavjffBbpnGTMEcqe','php','AWDlb3LRfBbpnGTMEdZk',1515677774545,1515677774545),('AWDlavjffBbpnGTMEcqe','py','AWDlb6QifBbpnGTMEdcV',1515677787170,1515677787170),('AWDlavjffBbpnGTMEcqe','scss','AWDli6gxQ399a3HcZKjt',1515679623217,1515679623217),('AWDlavjffBbpnGTMEcqe','ts','AWDlb71IfBbpnGTMEddZ',1515677793608,1515677793608),('AWDlavjffBbpnGTMEcqe','web','AWDli51EQ399a3HcZKiJ',1515679620420,1515679620420),('AWDlavjffBbpnGTMEcqe','xml','AWDlb0wffBbpnGTMEdRA',1515677764639,1515677764639);
/*!40000 ALTER TABLE `default_qprofiles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:32
