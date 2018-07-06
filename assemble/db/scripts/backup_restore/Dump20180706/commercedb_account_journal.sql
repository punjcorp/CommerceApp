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
-- Table structure for table `account_journal`
--

DROP TABLE IF EXISTS `account_journal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_journal` (
  `journal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `journal_type` varchar(50) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `comments` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`journal_id`),
  KEY `fk_account_journal_account_head1` (`account_id`),
  CONSTRAINT `fk_account_journal_account_head1` FOREIGN KEY (`account_id`) REFERENCES `account_head` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_journal`
--

LOCK TABLES `account_journal` WRITE;
/*!40000 ALTER TABLE `account_journal` DISABLE KEYS */;
INSERT INTO `account_journal` VALUES (1,4,'payment_due',630.00,'admin@gmail.com','2018-06-08 11:18:21',NULL,NULL,'Order :1 Received on: 2018-06-08T11:18:19.936'),(2,4,'full',630.00,'admin@gmail.com','2018-06-08 11:27:23',NULL,NULL,''),(3,4,'full',630.00,'admin@gmail.com','2018-06-09 09:40:20',NULL,NULL,'dsfsfsfa'),(4,4,'advance',1200.00,'admin@gmail.com','2018-06-09 15:28:34',NULL,NULL,'here is advance description'),(5,4,'advance',150.00,'admin@gmail.com','2018-06-09 15:41:06',NULL,NULL,'sfsfsdfsdfs '),(6,4,'advance',150.00,'admin@gmail.com','2018-06-09 15:42:54',NULL,NULL,'sfsfsdfsdfs '),(7,4,'advance',150.00,'admin@gmail.com','2018-06-09 15:46:21',NULL,NULL,'sfsfsdfsdfs '),(8,16,'advance',125.00,'admin@gmail.com','2018-06-09 15:47:00',NULL,NULL,'sffaf'),(9,16,'advance',150.00,'admin@gmail.com','2018-06-09 16:00:33',NULL,NULL,'sdddddddddddddddddddddd'),(10,16,'advance',150.00,'admin@gmail.com','2018-06-09 16:01:02',NULL,NULL,'sdddddddddddddddddddddd'),(11,8,'advance',150.00,'admin@gmail.com','2018-06-09 18:18:10',NULL,NULL,'fsfsdfsdf'),(12,8,'advance',120.00,'admin@gmail.com','2018-06-09 18:30:56',NULL,NULL,'sdfsdf sdf sdf sf sdf '),(15,17,'credit',10.50,'admin','2018-06-19 17:06:02',NULL,NULL,'Transaction 79970010004208062018Credit related entries'),(16,17,'credit',10.50,'admin','2018-06-19 17:13:10',NULL,NULL,'Transaction 79970010004308062018Credit related entries'),(17,21,'credit',105.00,'admin','2018-06-19 17:42:41',NULL,NULL,'Transaction 79970010004408062018Credit related entries'),(18,18,'credit',304.50,'admin','2018-06-20 14:37:50',NULL,NULL,'Transaction 79970010004908062018Credit related entries'),(19,18,'credit_return',-105.00,'admin','2018-06-22 23:19:38',NULL,NULL,'Transaction 79970010005908062018Credit related entries'),(20,8,'part',100.00,'admin','2018-06-22 23:27:52',NULL,NULL,'sdgsdgs');
/*!40000 ALTER TABLE `account_journal` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:10
