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
-- Table structure for table `item_images`
--

DROP TABLE IF EXISTS `item_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_images` (
  `item_id` bigint(20) NOT NULL,
  `feature_name` varchar(80) NOT NULL,
  `image_url` varchar(300) NOT NULL,
  `name` varchar(80) NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` varchar(50) NOT NULL,
  PRIMARY KEY (`item_id`,`feature_name`),
  KEY `fk_item_images_item1_idx` (`item_id`),
  CONSTRAINT `fk_item_images_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_images`
--

LOCK TABLES `item_images` WRITE;
/*!40000 ALTER TABLE `item_images` DISABLE KEYS */;
INSERT INTO `item_images` VALUES (1000000,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000001,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000002,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000003,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000004,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000005,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000006,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000007,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000008,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(1000009,'Listing','abc.jpg','Pants','2018-01-19 20:12:57','admin@gmail.com'),(10000000102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000000103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000010102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000010103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000020102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000020103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000030102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000030103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000040102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000040103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000050102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000050103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000060102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000060103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000070102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000070103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000080102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000080103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000090102,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com'),(10000090103,'Listing','abc.jpg','Formal Pants','2018-01-19 20:29:48','admin@gmail.com');
/*!40000 ALTER TABLE `item_images` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:03
