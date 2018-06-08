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
-- Table structure for table `stock_reason_code`
--

DROP TABLE IF EXISTS `stock_reason_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_reason_code` (
  `reason_code_id` int(11) NOT NULL AUTO_INCREMENT,
  `reason_code` varchar(15) NOT NULL,
  `name` varchar(80) NOT NULL,
  `from_bucket_id` int(11) DEFAULT NULL,
  `to_bucket_id` int(11) DEFAULT NULL,
  `from_stock_action` varchar(45) DEFAULT NULL,
  `to_stock_action` varchar(45) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`reason_code_id`),
  KEY `fk_stock_reason_code_stock_bucket1_idx` (`from_bucket_id`),
  KEY `fk_stock_reason_code_stock_bucket2_idx` (`to_bucket_id`),
  CONSTRAINT `fk_stock_reason_code_stock_bucket1` FOREIGN KEY (`from_bucket_id`) REFERENCES `stock_bucket` (`stock_bucket_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_reason_code_stock_bucket2` FOREIGN KEY (`to_bucket_id`) REFERENCES `stock_bucket` (`stock_bucket_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_reason_code`
--

LOCK TABLES `stock_reason_code` WRITE;
/*!40000 ALTER TABLE `stock_reason_code` DISABLE KEYS */;
INSERT INTO `stock_reason_code` VALUES (1,'STKIN','commerce.reason.code.inventory.stockin',NULL,1,NULL,'ADD','admin','2018-01-27 11:23:31'),(2,'STKOUT','commerce.reason.code.inventory.stockout',1,NULL,'SUBTRACT',NULL,'admin','2018-01-27 11:23:31');
/*!40000 ALTER TABLE `stock_reason_code` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 10:44:01
