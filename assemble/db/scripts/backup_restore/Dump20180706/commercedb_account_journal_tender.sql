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
-- Table structure for table `account_journal_tender`
--

DROP TABLE IF EXISTS `account_journal_tender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_journal_tender` (
  `journal_id` bigint(20) NOT NULL,
  `tender_id` int(11) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `branch_name` varchar(100) DEFAULT NULL,
  `account_no` varchar(50) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`journal_id`,`tender_id`),
  CONSTRAINT `fk_account_journal_tender_account_journal1` FOREIGN KEY (`journal_id`) REFERENCES `account_journal` (`journal_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_journal_tender`
--

LOCK TABLES `account_journal_tender` WRITE;
/*!40000 ALTER TABLE `account_journal_tender` DISABLE KEYS */;
INSERT INTO `account_journal_tender` VALUES (1,1,630.00,'admin@gmail.com','2018-06-08 11:18:21',NULL,NULL,NULL,NULL,NULL,'Order Tender is Cash By Default'),(2,1,630.00,'admin@gmail.com','2018-06-08 11:27:23',NULL,NULL,'','','',''),(3,1,630.00,'admin@gmail.com','2018-06-09 09:40:20',NULL,NULL,'','','',''),(4,1,1200.00,'admin@gmail.com','2018-06-09 15:28:34',NULL,NULL,'','','','here is cash description'),(5,1,150.00,'admin@gmail.com','2018-06-09 15:41:06',NULL,NULL,'','','','fffffffffffffff'),(6,1,150.00,'admin@gmail.com','2018-06-09 15:42:54',NULL,NULL,'','','','fffffffffffffff'),(7,1,150.00,'admin@gmail.com','2018-06-09 15:46:21',NULL,NULL,'','','','fffffffffffffff'),(8,1,125.00,'admin@gmail.com','2018-06-09 15:47:00',NULL,NULL,'','','','gsdgsdfsdssd'),(9,1,150.00,'admin@gmail.com','2018-06-09 16:00:33',NULL,NULL,'','','','werwerwerwerwerwerew'),(10,1,150.00,'admin@gmail.com','2018-06-09 16:01:02',NULL,NULL,'','','','werwerwerwerwerwerew'),(11,1,150.00,'admin@gmail.com','2018-06-09 18:18:10',NULL,NULL,'','','','sf sdf sdf sf sdf ds fsd ffsd ds ds '),(12,1,120.00,'admin@gmail.com','2018-06-09 18:30:56',NULL,NULL,'','','','dddddddddddddddd'),(15,7,10.50,'admin','2018-06-19 17:06:02',NULL,NULL,NULL,NULL,NULL,'The Credit tender amounts has been summed up'),(16,7,10.50,'admin','2018-06-19 17:13:10',NULL,NULL,NULL,NULL,NULL,'The Credit tender amounts has been summed up'),(17,7,105.00,'admin','2018-06-19 17:42:41',NULL,NULL,NULL,NULL,NULL,'The Credit tender amounts has been summed up'),(18,7,304.50,'admin','2018-06-20 14:37:50',NULL,NULL,NULL,NULL,NULL,'The Credit tender amounts has been summed up'),(19,7,-105.00,'admin','2018-06-22 23:19:38',NULL,NULL,NULL,NULL,NULL,'The Credit tender amounts has been summed up'),(20,1,100.00,'admin','2018-06-22 23:27:52',NULL,NULL,'','','','sgsdgsgsd');
/*!40000 ALTER TABLE `account_journal_tender` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:31
