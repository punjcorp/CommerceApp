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
-- Table structure for table `txn_li_tax`
--

DROP TABLE IF EXISTS `txn_li_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_li_tax` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `seq_no` int(3) NOT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `total_taxable_amount` decimal(12,2) DEFAULT NULL,
  `total_tax_amount` decimal(12,2) DEFAULT NULL,
  `total_tax_exempt_amount` decimal(12,2) DEFAULT NULL,
  `tax_override_amount` decimal(12,2) NOT NULL,
  `tax_override_percentage` decimal(12,2) DEFAULT NULL,
  `tax_override_flag` tinyint(4) DEFAULT NULL,
  `override_reason_code` varchar(20) DEFAULT NULL,
  `void_flag` tinyint(4) DEFAULT NULL,
  `tax_rule_percentage` decimal(12,2) DEFAULT NULL,
  `tax_rule_amount` decimal(12,2) DEFAULT NULL,
  `org_taxable_amount` decimal(12,2) DEFAULT NULL,
  `org_tax_group_id` int(11) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `tax_group_id` int(11) NOT NULL,
  `tax_rule_rate_id` int(11) NOT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`,`seq_no`),
  KEY `fk_txn_li_tax_txn_line_item_master1_idx` (`location_id`,`business_date`,`register`,`txn_no`,`seq_no`),
  CONSTRAINT `fk_txn_li_tax_txn_line_item_master1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`) REFERENCES `txn_line_item_master` (`location_id`, `business_date`, `register`, `txn_no`, `seq_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_li_tax`
--

LOCK TABLES `txn_li_tax` WRITE;
/*!40000 ALTER TABLE `txn_li_tax` DISABLE KEYS */;
INSERT INTO `txn_li_tax` VALUES (7997,'2018-05-24 00:00:00',1,29,2,10000060002,50.00,3.00,0.00,0.00,0.00,0,NULL,NULL,6.00,NULL,0.00,NULL,'admin@gmail.com','2018-06-03 12:07:47',4,7),(7997,'2018-05-24 00:00:00',1,29,3,10000060002,50.00,3.00,0.00,0.00,0.00,0,NULL,NULL,6.00,NULL,0.00,NULL,'admin@gmail.com','2018-06-03 12:07:47',4,8);
/*!40000 ALTER TABLE `txn_li_tax` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 10:44:23
