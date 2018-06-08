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
-- Table structure for table `denomination_master`
--

DROP TABLE IF EXISTS `denomination_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `denomination_master` (
  `denomination_id` int(11) NOT NULL AUTO_INCREMENT,
  `currency_code` varchar(5) NOT NULL DEFAULT 'INR',
  `code` varchar(15) NOT NULL,
  `value` decimal(10,0) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`denomination_id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `denomination_master`
--

LOCK TABLES `denomination_master` WRITE;
/*!40000 ALTER TABLE `denomination_master` DISABLE KEYS */;
INSERT INTO `denomination_master` VALUES (1,'INR','0.50',1,'50 paisa'),(2,'INR','1.00',1,NULL),(3,'INR','2.00',2,NULL),(4,'INR','5.00',5,NULL),(5,'INR','10.00',10,NULL),(6,'INR','20.00',20,NULL),(7,'INR','50.00',50,NULL),(8,'INR','100.00',100,NULL),(9,'INR','200.00',200,NULL),(10,'INR','500.00',500,NULL),(11,'INR','1000.00',1000,NULL),(12,'INR','2000.00',2000,NULL);
/*!40000 ALTER TABLE `denomination_master` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 10:44:02
