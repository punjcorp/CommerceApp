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
-- Table structure for table `txn_tender_denomination`
--

DROP TABLE IF EXISTS `txn_tender_denomination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_tender_denomination` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `tender_id` int(3) NOT NULL,
  `denomination_id` int(11) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `media_count` int(11) NOT NULL,
  `difference_amount` decimal(12,2) DEFAULT NULL,
  `difference_media_count` int(11) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`,`tender_id`,`denomination_id`),
  KEY `fk_txn_tender_denomination_denomination_master1_idx` (`denomination_id`),
  CONSTRAINT `fk_txn_tender_denomination_denomination_master1` FOREIGN KEY (`denomination_id`) REFERENCES `denomination_master` (`denomination_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_tndr_denomination_txn_tndr_count1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`, `tender_id`) REFERENCES `txn_tender_count` (`location_id`, `business_date`, `register`, `txn_no`, `tender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_tender_denomination`
--

LOCK TABLES `txn_tender_denomination` WRITE;
/*!40000 ALTER TABLE `txn_tender_denomination` DISABLE KEYS */;
INSERT INTO `txn_tender_denomination` VALUES (7997,'2018-05-24 00:00:00',1,1,1,6,460.00,23,NULL,NULL,'admin@gmail.com','2018-05-24 20:00:24',NULL,NULL),(7997,'2018-05-24 00:00:00',1,1,1,8,2200.00,22,NULL,NULL,'admin@gmail.com','2018-05-24 20:00:24',NULL,NULL),(7997,'2018-05-24 00:00:00',1,2,1,6,460.00,23,NULL,NULL,'admin@gmail.com','2018-05-24 20:00:32',NULL,NULL),(7997,'2018-05-24 00:00:00',1,2,1,8,2200.00,22,NULL,NULL,'admin@gmail.com','2018-05-24 20:00:32',NULL,NULL);
/*!40000 ALTER TABLE `txn_tender_denomination` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 10:44:18
