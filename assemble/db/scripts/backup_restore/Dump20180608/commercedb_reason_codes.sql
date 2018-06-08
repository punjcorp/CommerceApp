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
-- Table structure for table `reason_codes`
--

DROP TABLE IF EXISTS `reason_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reason_codes` (
  `reason_code_id` int(11) NOT NULL AUTO_INCREMENT,
  `reason_name` varchar(100) NOT NULL,
  `type` varchar(50) NOT NULL,
  `description` varchar(150) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`reason_code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reason_codes`
--

LOCK TABLES `reason_codes` WRITE;
/*!40000 ALTER TABLE `reason_codes` DISABLE KEYS */;
INSERT INTO `reason_codes` VALUES (1,'Electricity Bill','Bill','This is for electricity Bill','admin','2018-05-24 19:49:27'),(2,'Water Bill','Bill','This is for MC water bill','admin','2018-05-24 19:49:27'),(3,'Internet Bill','Bill','This is for Internet Bill','admin','2018-05-24 19:49:27'),(4,'Tea','Daily Use','This is for tea ','admin','2018-05-24 19:49:27'),(5,'Snacks','Daily Use','This is for snacks','admin','2018-05-24 19:49:27'),(6,'Lease Rent','Bill','This is rent for the room','admin','2018-05-24 19:49:27'),(7,'Payment','General Expense','this is payment for anything which will be described in remarks','admin','2018-05-24 19:49:27'),(8,'Repository To Register','Move','This reason code is used to move money from store repository to register till','admin','2018-05-24 19:49:27'),(9,'Register To Repository','Move','This reason code is used to move money from register till to store repository','admin','2018-05-24 19:49:27'),(10,'Register To Register','Move','This reason code is used to move money from one register till to another register till','admin','2018-05-24 19:49:27');
/*!40000 ALTER TABLE `reason_codes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 10:44:24
