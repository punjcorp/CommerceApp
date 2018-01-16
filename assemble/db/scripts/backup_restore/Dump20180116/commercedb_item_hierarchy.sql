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
-- Table structure for table `item_hierarchy`
--

DROP TABLE IF EXISTS `item_hierarchy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_hierarchy` (
  `hierarchy_id` int(11) NOT NULL AUTO_INCREMENT,
  `level_code` varchar(20) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `sort_order` int(11) NOT NULL DEFAULT '1',
  `hidden_flag` varchar(1) NOT NULL DEFAULT 'N',
  `created_date` datetime NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`hierarchy_id`),
  KEY `fk_item_hierarchy_item_hierarchy1_idx` (`parent_id`),
  CONSTRAINT `fk_item_hierarchy_item_hierarchy1` FOREIGN KEY (`parent_id`) REFERENCES `item_hierarchy` (`hierarchy_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_hierarchy`
--

LOCK TABLES `item_hierarchy` WRITE;
/*!40000 ALTER TABLE `item_hierarchy` DISABLE KEYS */;
INSERT INTO `item_hierarchy` VALUES (1,'Dept','Mens',1,'N','2017-12-07 17:55:32','admin',NULL),(2,'SubDept','Clothes',1,'N','2017-12-07 17:56:25','admin',1),(3,'Class','Shirts',1,'N','2017-12-07 18:01:00','admin',2),(4,'SubClass','Formal',1,'N','2017-12-07 18:01:00','admin',3),(5,'SubDept','Shoes',1,'N','2017-12-07 18:01:00','admin',1),(6,'SubDept','Personal Care',1,'N','2017-12-07 18:01:00','admin',1),(7,'Class','Pants',1,'N','2017-12-07 18:01:00','admin',2),(8,'SubClass','Formal',1,'N','2017-12-07 18:01:00','admin',3),(9,'SubClass','Casual',1,'N','2017-12-07 18:01:00','admin',3);
/*!40000 ALTER TABLE `item_hierarchy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:51
