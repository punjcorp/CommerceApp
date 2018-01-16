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
-- Table structure for table `tax_rate_rule`
--

DROP TABLE IF EXISTS `tax_rate_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tax_rate_rule` (
  `tax_rate_rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `tax_group_id` int(11) NOT NULL,
  `tax_bracket_id` int(11) NOT NULL,
  `seq_no` int(2) NOT NULL,
  `min_taxable_amt` decimal(10,0) DEFAULT NULL,
  `max_taxable_amt` decimal(10,0) DEFAULT NULL,
  `effective_date` datetime NOT NULL,
  `expiry_date` datetime NOT NULL,
  `percentage` decimal(10,0) DEFAULT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `type_code` varchar(30) NOT NULL,
  `status` varchar(5) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tax_rate_rule_id`),
  KEY `fk_tax_rate_rule_tax_group1` (`tax_group_id`),
  KEY `fk_tax_rate_rule_tax_bracket1_idx` (`tax_bracket_id`),
  CONSTRAINT `fk_tax_rate_rule_tax_bracket1` FOREIGN KEY (`tax_bracket_id`) REFERENCES `tax_bracket` (`tax_bracket_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_rate_rule_tax_group1` FOREIGN KEY (`tax_group_id`) REFERENCES `tax_group` (`tax_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_rate_rule`
--

LOCK TABLES `tax_rate_rule` WRITE;
/*!40000 ALTER TABLE `tax_rate_rule` DISABLE KEYS */;
INSERT INTO `tax_rate_rule` VALUES (1,1,1,1,-1,-1,'2017-12-18 11:21:54','2116-12-18 11:21:54',3,NULL,'Percentage','A','admin','2017-12-18 11:21:54',NULL,NULL),(2,1,1,1,-1,-1,'2017-12-18 11:21:54','2116-12-18 11:21:54',9,NULL,'Percentage','A','admin','2017-12-18 11:21:54',NULL,NULL),(3,1,1,1,-1,-1,'2017-12-18 11:21:54','2116-12-18 11:21:54',6,NULL,'Percentage','A','admin','2017-12-18 11:21:54',NULL,NULL),(4,1,1,1,-1,-1,'2017-12-18 11:21:54','2116-12-18 11:21:54',14,NULL,'Percentage','A','admin','2017-12-18 11:21:54',NULL,NULL),(5,1,1,1,-1,-1,'2017-12-18 11:21:54','2116-12-18 11:21:54',0,NULL,'Percentage','A','admin','2017-12-18 11:21:54',NULL,NULL);
/*!40000 ALTER TABLE `tax_rate_rule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:56
