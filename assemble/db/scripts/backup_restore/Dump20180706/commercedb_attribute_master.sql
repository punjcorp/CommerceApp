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
-- Table structure for table `attribute_master`
--

DROP TABLE IF EXISTS `attribute_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute_master` (
  `attribute_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attr_code` varchar(5) NOT NULL,
  `attr_name` varchar(80) NOT NULL,
  `attr_description` varchar(150) DEFAULT NULL,
  `value_code` varchar(50) NOT NULL,
  `value_name` varchar(80) NOT NULL,
  `value_description` varchar(150) DEFAULT NULL,
  `value_seq_no` int(2) NOT NULL,
  PRIMARY KEY (`attribute_id`),
  UNIQUE KEY `index2` (`attr_code`,`value_code`,`value_seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_master`
--

LOCK TABLES `attribute_master` WRITE;
/*!40000 ALTER TABLE `attribute_master` DISABLE KEYS */;
INSERT INTO `attribute_master` VALUES (1,'S','Size','This is to capture SIZE','L','Large','This is Large SIZE',1),(2,'S','Size','This is to capture SIZE','M','Medium','This is Medium SIZE',2),(3,'S','Size','This is to capture SIZE','S','Small','This is Small SIZE',3),(4,'C','Color','This is to capture Color','RED','Red','This is RED Color',1),(5,'C','Color','This is to capture Color','GREEN','Green','This is GREEN Color',2),(6,'C','Color','This is to capture Color','BLUE','Blue','This is BLUE Color',3),(7,'D','Dimension','This is to capture Dimension','L','Length','This is Length of the Dimension',1),(8,'D','Dimension','This is to capture Dimension','B','Breadth','This is Breadth of the Dimension',2),(9,'D','Dimension','This is to capture Dimension','W','Width','This is Width of the Dimension',3),(10,'W','Weight','This is to measure weight ','100','100 gms','This is the Weight of 100 gms',1),(11,'W','Weight','This is to measure weight ','200','200 gms','This is the Weight of 200 gms',2),(12,'W','Weight','This is to measure weight ','250','250 gms','This is the Weight of 250 gms',3),(13,'W','Weight','This is to measure weight ','500','500 gms','This is the Weight of 500 gms',4),(14,'W','Weight','This is to measure weight ','1','1 Kg','This is the Weight of 1 Kg',5),(15,'W','Weight','This is to measure weight ','2','2 Kgs','This is the Weight of 2 Kgs',6),(16,'W','Weight','This is to measure weight ','5','5 Kgs','This is the Weight of 5  Kgs',7);
/*!40000 ALTER TABLE `attribute_master` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:18
