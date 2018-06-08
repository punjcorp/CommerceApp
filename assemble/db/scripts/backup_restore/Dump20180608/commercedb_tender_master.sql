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
-- Table structure for table `tender_master`
--

DROP TABLE IF EXISTS `tender_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tender_master` (
  `tender_id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `type` varchar(30) NOT NULL,
  `description` varchar(150) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `sub_tender_id` int(3) DEFAULT NULL,
  PRIMARY KEY (`tender_id`),
  KEY `fk_tender_master_tender_master1_idx` (`sub_tender_id`),
  CONSTRAINT `fk_tender_master_tender_master1` FOREIGN KEY (`sub_tender_id`) REFERENCES `tender_master` (`tender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tender_master`
--

LOCK TABLES `tender_master` WRITE;
/*!40000 ALTER TABLE `tender_master` DISABLE KEYS */;
INSERT INTO `tender_master` VALUES (1,'Cash','CASH','This is the cash tender description','admin','2018-05-24 19:49:28',NULL,NULL,NULL),(2,'Credit Card','CC','This is credit card tender','admin','2018-05-24 19:49:28',NULL,NULL,NULL),(3,'Paypal','PAYPAL','This is paypal online payment','admin','2018-05-24 19:49:28',NULL,NULL,NULL),(4,'Paytm','PAYTM','This is PAYTM online payment','admin','2018-05-24 19:49:28',NULL,NULL,NULL),(5,'Cheque','CHEQUE','This is bank account cheque','admin','2018-05-24 19:49:28',NULL,NULL,NULL),(6,'Change','CHANGE','This is change for cash tender','admin','2018-05-24 19:49:28',NULL,NULL,NULL);
/*!40000 ALTER TABLE `tender_master` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 10:44:07
