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
-- Table structure for table `tax_group_rule`
--

DROP TABLE IF EXISTS `tax_group_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tax_group_rule` (
  `tax_group_id` int(11) NOT NULL,
  `tax_location_id` int(11) NOT NULL,
  `tax_authority_id` int(11) NOT NULL,
  `seq_no` int(2) NOT NULL,
  `name` varchar(80) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `compound_seq_nbr` int(2) NOT NULL,
  `compound_flag` varchar(1) NOT NULL,
  `trans_level_flag` varchar(1) NOT NULL,
  `type_code` varchar(40) NOT NULL,
  `status` varchar(5) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tax_group_id`,`tax_location_id`,`tax_authority_id`,`seq_no`),
  KEY `fk_tax_group_rule_tax_authority1` (`tax_authority_id`),
  KEY `fk_tax_group_rule_tax_location1_idx` (`tax_location_id`),
  KEY `fk_tax_group_rule_tax_group1_idx` (`tax_group_id`),
  CONSTRAINT `fk_tax_group_rule_tax_authority1` FOREIGN KEY (`tax_authority_id`) REFERENCES `tax_authority` (`tax_authority_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_group_rule_tax_group1` FOREIGN KEY (`tax_group_id`) REFERENCES `tax_group` (`tax_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_group_rule_tax_location1` FOREIGN KEY (`tax_location_id`) REFERENCES `tax_location` (`tax_location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_group_rule`
--

LOCK TABLES `tax_group_rule` WRITE;
/*!40000 ALTER TABLE `tax_group_rule` DISABLE KEYS */;
INSERT INTO `tax_group_rule` VALUES (1,1,1,0,'SGST','This is the rules for group SGST',0,'1','0','SGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(1,1,1,1,'CGST','This is the rules for group CGST',1,'1','0','CGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(1,2,1,0,'IGST','This is the rules for group IGST',0,'0','0','IGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(2,1,1,0,'SGST','This is the rules for group SGST',0,'1','0','SGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(2,1,1,1,'CGST','This is the rules for group CGST',1,'1','0','CGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(2,2,1,0,'IGST','This is the rules for group IGST',0,'0','0','IGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(3,1,1,0,'SGST','This is the rules for group SGST',0,'1','0','SGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(3,1,1,1,'CGST','This is the rules for group CGST',1,'1','0','CGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(3,2,1,0,'IGST','This is the rules for group IGST',0,'0','0','IGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(4,1,1,0,'SGST','This is the rules for group SGST',0,'1','0','SGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(4,1,1,1,'CGST','This is the rules for group CGST',1,'1','0','CGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(4,2,1,0,'IGST','This is the rules for group IGST',0,'0','0','IGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(5,1,1,0,'SGST','This is the rules for group SGST',0,'1','0','SGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(5,1,1,1,'CGST','This is the rules for group CGST',1,'1','0','CGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(5,2,1,0,'IGST','This is the rules for group IGST',0,'0','0','IGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(6,1,1,0,'SGST','This is the rules for group SGST',0,'1','0','SGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(6,1,1,1,'CGST','This is the rules for group CGST',1,'1','0','CGST','A','admin','2018-03-24 11:15:00',NULL,NULL),(6,2,1,0,'IGST','This is the rules for group IGST',0,'0','0','IGST','A','admin','2018-03-24 11:15:00',NULL,NULL);
/*!40000 ALTER TABLE `tax_group_rule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:16
