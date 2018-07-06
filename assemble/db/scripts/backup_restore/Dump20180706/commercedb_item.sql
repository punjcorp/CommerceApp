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
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `item_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `long_desc` varchar(300) NOT NULL,
  `item_level` int(11) NOT NULL,
  `parent_item_id` bigint(20) DEFAULT NULL,
  `hierarchy_id` int(11) NOT NULL,
  `item_type` varchar(45) DEFAULT NULL,
  `record_status` varchar(1) NOT NULL DEFAULT 'C',
  `created_date` datetime NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `fk_item_item_hierarchy1_idx` (`hierarchy_id`),
  CONSTRAINT `fk_item_item_hierarchy1` FOREIGN KEY (`hierarchy_id`) REFERENCES `item_hierarchy` (`hierarchy_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (10000000000,'Good Day Biscuits','Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits ',1,NULL,1,'merchandise','A','2018-06-08 11:01:48','admin@gmail.com','2018-06-08 11:03:56','admin@gmail.com'),(10000000001,'Good Day Biscuits L','Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits ',2,10000000000,1,'merchandise','A','2018-06-08 11:03:56','admin@gmail.com',NULL,NULL),(10000000002,'Good Day Biscuits M','Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits ',2,10000000000,1,'merchandise','A','2018-06-08 11:03:56','admin@gmail.com',NULL,NULL),(10000000003,'Good Day Biscuits S','Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits Good Day Biscuits ',2,10000000000,1,'merchandise','A','2018-06-08 11:03:56','admin@gmail.com',NULL,NULL),(10000010000,'Dove Coconut Milk Soap','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap ',1,NULL,1,'merchandise','A','2018-06-08 11:08:14','admin@gmail.com','2018-06-08 11:09:04','admin@gmail.com'),(10000010001,'Dove Coconut Milk Soap L','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap ',2,10000010000,1,'merchandise','A','2018-06-08 11:09:04','admin@gmail.com','2018-06-08 11:18:20','admin@gmail.com'),(10000010002,'Dove Coconut Milk Soap M','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap ',2,10000010000,1,'merchandise','A','2018-06-08 11:09:04','admin@gmail.com',NULL,NULL),(10000010003,'Dove Coconut Milk Soap S','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap ',2,10000010000,1,'merchandise','A','2018-06-08 11:09:04','admin@gmail.com',NULL,NULL),(10000020000,'India Organic tulsi tummy syrup','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup ',1,NULL,1,'merchandise','A','2018-06-08 11:10:35','admin@gmail.com','2018-06-08 11:11:02','admin@gmail.com'),(10000020001,'India Organic tulsi tummy syrup L','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup ',2,10000020000,1,'merchandise','A','2018-06-08 11:11:02','admin@gmail.com',NULL,NULL),(10000020002,'India Organic tulsi tummy syrup M','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup ',2,10000020000,1,'merchandise','A','2018-06-08 11:11:02','admin@gmail.com',NULL,NULL),(10000020003,'India Organic tulsi tummy syrup S','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup ',2,10000020000,1,'merchandise','A','2018-06-08 11:11:02','admin@gmail.com',NULL,NULL),(10000030000,'ASUS Phone F Model','ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ',1,NULL,1,'merchandise','A','2018-06-08 11:11:48','admin@gmail.com','2018-06-08 11:12:29','admin@gmail.com'),(10000030001,'ASUS Phone F Model RED','ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ',2,10000030000,1,'merchandise','A','2018-06-08 11:12:29','admin@gmail.com',NULL,NULL),(10000030002,'ASUS Phone F Model GREEN','ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ',2,10000030000,1,'merchandise','A','2018-06-08 11:12:29','admin@gmail.com',NULL,NULL),(10000030003,'ASUS Phone F Model BLUE','ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model ',2,10000030000,1,'merchandise','A','2018-06-08 11:12:29','admin@gmail.com',NULL,NULL),(10000040000,'Lays Masala chips','Lays Masala chips  Lays Masala chips  Lays Masala chips ',1,NULL,1,'merchandise','A','2018-06-21 13:24:16','admin','2018-06-21 13:27:54','admin'),(10000040001,'Lays Masala chips L','Lays Masala chips  Lays Masala chips  Lays Masala chips ',2,10000040000,1,'merchandise','A','2018-06-21 13:27:54','admin',NULL,NULL),(10000040002,'Lays Masala chips M','Lays Masala chips  Lays Masala chips  Lays Masala chips ',2,10000040000,1,'merchandise','A','2018-06-21 13:27:54','admin',NULL,NULL),(10000040003,'Lays Masala chips S','Lays Masala chips  Lays Masala chips  Lays Masala chips ',2,10000040000,1,'merchandise','A','2018-06-21 13:27:54','admin',NULL,NULL),(10000050000,'Dettol sensitive pro balanced hand wash','Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash ',1,NULL,1,'merchandise','A','2018-06-21 13:43:01','admin','2018-06-21 13:43:43','admin'),(10000050001,'Dettol sensitive pro balanced hand wash L','Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash ',2,10000050000,1,'merchandise','A','2018-06-21 13:43:43','admin',NULL,NULL),(10000050002,'Dettol sensitive pro balanced hand wash M','Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash ',2,10000050000,1,'merchandise','A','2018-06-21 13:43:43','admin',NULL,NULL),(10000050003,'Dettol sensitive pro balanced hand wash S','Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash Dettol sensitive pro balanced hand wash ',2,10000050000,1,'merchandise','A','2018-06-21 13:43:43','admin',NULL,NULL),(10000060000,'TreSemme Shampoo','TreSemme Shampoo TreSemme Shampoo TreSemme Shampoo TreSemme Shampoo ',1,NULL,1,'merchandise','A','2018-06-21 18:00:09','admin','2018-06-21 18:00:39','admin'),(10000060001,'TreSemme Shampoo L','TreSemme Shampoo TreSemme Shampoo TreSemme Shampoo TreSemme Shampoo ',2,10000060000,1,'merchandise','A','2018-06-21 18:00:39','admin',NULL,NULL),(10000060002,'TreSemme Shampoo M','TreSemme Shampoo TreSemme Shampoo TreSemme Shampoo TreSemme Shampoo ',2,10000060000,1,'merchandise','A','2018-06-21 18:00:39','admin',NULL,NULL),(10000060003,'TreSemme Shampoo S','TreSemme Shampoo TreSemme Shampoo TreSemme Shampoo TreSemme Shampoo ',2,10000060000,1,'merchandise','A','2018-06-21 18:00:39','admin',NULL,NULL),(10000070000,'Organic India Wheat Grass','Organic India Wheat Grass Organic India Wheat Grass Organic India Wheat Grass',1,NULL,1,'merchandise','A','2018-06-21 18:39:10','admin','2018-06-21 18:40:28','admin'),(10000070001,'Organic India Wheat Grass L','Organic India Wheat Grass Organic India Wheat Grass Organic India Wheat Grass',2,10000070000,1,'merchandise','A','2018-06-21 18:40:28','admin',NULL,NULL),(10000070002,'Organic India Wheat Grass M','Organic India Wheat Grass Organic India Wheat Grass Organic India Wheat Grass',2,10000070000,1,'merchandise','A','2018-06-21 18:40:28','admin',NULL,NULL),(10000070003,'Organic India Wheat Grass S','Organic India Wheat Grass Organic India Wheat Grass Organic India Wheat Grass',2,10000070000,1,'merchandise','A','2018-06-21 18:40:28','admin',NULL,NULL),(10000080000,'image new ','image new image new image new image new ',1,NULL,1,'merchandise','A','2018-06-21 18:50:01','admin','2018-06-21 18:52:35','admin'),(10000080001,'image new  L','image new image new image new image new ',2,10000080000,1,'merchandise','A','2018-06-21 18:52:35','admin',NULL,NULL),(10000080002,'image new  M','image new image new image new image new ',2,10000080000,1,'merchandise','A','2018-06-21 18:52:35','admin',NULL,NULL),(10000080003,'image new  S','image new image new image new image new ',2,10000080000,1,'merchandise','A','2018-06-21 18:52:35','admin',NULL,NULL),(10000140000,'item image 1 ','item image 1 item image 1 item image 1 item image 1 ',1,NULL,1,'merchandise','A','2018-06-21 19:31:19','admin','2018-06-21 19:32:05','admin'),(10000140001,'item image 1  L','item image 1 item image 1 item image 1 item image 1 ',2,10000140000,1,'merchandise','A','2018-06-21 19:32:04','admin',NULL,NULL),(10000140002,'item image 1  M','item image 1 item image 1 item image 1 item image 1 ',2,10000140000,1,'merchandise','A','2018-06-21 19:32:04','admin',NULL,NULL),(10000140003,'item image 1  S','item image 1 item image 1 item image 1 item image 1 ',2,10000140000,1,'merchandise','A','2018-06-21 19:32:05','admin',NULL,NULL),(10000150000,'item image 2','item image 2 item image 2 item image 2',1,NULL,1,'merchandise','A','2018-06-21 19:42:00','admin','2018-06-21 19:42:59','admin'),(10000150001,'item image 2 L','item image 2 item image 2 item image 2',2,10000150000,1,'merchandise','A','2018-06-21 19:42:59','admin',NULL,NULL),(10000150002,'item image 2 M','item image 2 item image 2 item image 2',2,10000150000,1,'merchandise','A','2018-06-21 19:42:59','admin',NULL,NULL),(10000150003,'item image 2 S','item image 2 item image 2 item image 2',2,10000150000,1,'merchandise','A','2018-06-21 19:42:59','admin',NULL,NULL);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:22
