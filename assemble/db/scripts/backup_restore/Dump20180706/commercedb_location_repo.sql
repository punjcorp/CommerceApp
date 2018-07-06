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
-- Table structure for table `location_repo`
--

DROP TABLE IF EXISTS `location_repo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_repo` (
  `location_repository_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `repository_id` int(11) NOT NULL,
  `location_id` int(4) NOT NULL,
  `tender_id` int(3) NOT NULL,
  `reconcilation_flag` tinyint(4) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`location_repository_id`),
  KEY `fk_location_repository_repository_master1` (`repository_id`),
  KEY `fk_location_repository_location1_idx` (`location_id`),
  KEY `fk_location_repository_tender_master1_idx` (`tender_id`),
  CONSTRAINT `fk_location_repository_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_repository_repository_master1` FOREIGN KEY (`repository_id`) REFERENCES `repository_master` (`repository_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_location_repository_tender_master1` FOREIGN KEY (`tender_id`) REFERENCES `tender_master` (`tender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_repo`
--

LOCK TABLES `location_repo` WRITE;
/*!40000 ALTER TABLE `location_repo` DISABLE KEYS */;
INSERT INTO `location_repo` VALUES (1,1,7997,1,1,'admin','2018-06-08 10:48:48',NULL,NULL),(2,1,7997,2,0,'admin','2018-06-08 10:48:48',NULL,NULL),(3,1,7997,3,0,'admin','2018-06-08 10:48:48',NULL,NULL),(4,1,7997,4,0,'admin','2018-06-08 10:48:48',NULL,NULL),(5,1,7997,5,0,'admin','2018-06-08 10:48:48',NULL,NULL),(6,1,7997,6,0,'admin','2018-06-08 10:48:48',NULL,NULL),(7,1,2,1,1,'admin','2018-06-08 10:48:48',NULL,NULL),(8,1,3,1,1,'admin','2018-06-08 10:48:48',NULL,NULL),(9,1,27,1,1,'admin','2018-06-08 10:48:48',NULL,NULL),(10,1,7997,7,0,'admin','2018-06-08 10:48:48',NULL,NULL);
/*!40000 ALTER TABLE `location_repo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:16
