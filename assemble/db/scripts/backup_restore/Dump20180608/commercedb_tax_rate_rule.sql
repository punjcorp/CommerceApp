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
  `tax_group_id` int(11) NOT NULL,
  `tax_location_id` int(11) NOT NULL,
  `tax_authority_id` int(11) NOT NULL,
  `tax_group_rule_seq` int(2) NOT NULL,
  `tax_rate_rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `tax_bracket_id` int(11) NOT NULL,
  `seq_no` int(2) NOT NULL,
  `min_taxable_amt` decimal(12,2) DEFAULT NULL,
  `max_taxable_amt` decimal(12,2) DEFAULT NULL,
  `effective_date` datetime NOT NULL,
  `expiry_date` datetime NOT NULL,
  `percentage` decimal(12,2) DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `type_code` varchar(30) NOT NULL,
  `status` varchar(5) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tax_rate_rule_id`),
  KEY `fk_tax_rate_rule_tax_bracket1_idx` (`tax_bracket_id`),
  KEY `fk_tax_rate_rule_tax_group_rule1_idx` (`tax_group_id`,`tax_location_id`,`tax_authority_id`,`tax_group_rule_seq`),
  CONSTRAINT `fk_tax_rate_rule_tax_bracket1` FOREIGN KEY (`tax_bracket_id`) REFERENCES `tax_bracket` (`tax_bracket_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tax_rate_rule_tax_group_rule1` FOREIGN KEY (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `tax_group_rule_seq`) REFERENCES `tax_group_rule` (`tax_group_id`, `tax_location_id`, `tax_authority_id`, `seq_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_rate_rule`
--

LOCK TABLES `tax_rate_rule` WRITE;
/*!40000 ALTER TABLE `tax_rate_rule` DISABLE KEYS */;
INSERT INTO `tax_rate_rule` VALUES (1,1,1,0,1,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',0.00,NULL,'SGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(1,1,1,1,2,1,1,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',0.00,NULL,'CGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(2,1,1,0,3,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',2.50,NULL,'SGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(2,1,1,1,4,1,1,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',2.50,NULL,'CGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(3,1,1,0,5,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',5.00,NULL,'SGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(3,1,1,1,6,1,1,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',5.00,NULL,'CGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(4,1,1,0,7,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',6.00,NULL,'SGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(4,1,1,1,8,1,1,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',6.00,NULL,'CGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(5,1,1,0,9,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',9.00,NULL,'SGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(5,1,1,1,10,1,1,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',9.00,NULL,'CGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(6,1,1,0,11,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',14.00,NULL,'SGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(6,1,1,1,12,1,1,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',14.00,NULL,'CGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(1,2,1,0,13,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',0.00,NULL,'IGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(2,2,1,0,14,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',5.00,NULL,'IGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(3,2,1,0,15,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',10.00,NULL,'IGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(4,2,1,0,16,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',12.00,NULL,'IGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(5,2,1,0,17,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',18.00,NULL,'IGST','A','admin','2018-05-24 19:49:31',NULL,NULL),(6,2,1,0,18,1,0,NULL,NULL,'2018-05-24 19:49:31','2018-05-24 19:49:31',28.00,NULL,'IGST','A','admin','2018-05-24 19:49:31',NULL,NULL);
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

-- Dump completed on 2018-06-08 10:44:09
