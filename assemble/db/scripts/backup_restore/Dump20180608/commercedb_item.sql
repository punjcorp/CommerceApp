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
INSERT INTO `item` VALUES (10000000000,'Dove Coconut Milk Soap','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',1,NULL,1,'merchandise','A','2018-05-24 20:07:31','admin@gmail.com','2018-05-24 20:12:00','admin@gmail.com'),(10000000001,'Dove Coconut Milk Soap 100','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000000000,8,'merchandise','A','2018-05-24 20:11:59','admin@gmail.com',NULL,NULL),(10000000002,'Dove Coconut Milk Soap 200','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000000000,1,'merchandise','A','2018-05-24 20:11:59','admin@gmail.com','2018-06-08 09:40:30','admin@gmail.com'),(10000000003,'Dove Coconut Milk Soap 250','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000000000,1,'merchandise','A','2018-05-24 20:11:59','admin@gmail.com',NULL,NULL),(10000000004,'Dove Coconut Milk Soap 500','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000000000,1,'merchandise','A','2018-05-24 20:11:59','admin@gmail.com',NULL,NULL),(10000000005,'Dove Coconut Milk Soap 1','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000000000,1,'merchandise','A','2018-05-24 20:11:59','admin@gmail.com',NULL,NULL),(10000000006,'Dove Coconut Milk Soap 2','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000000000,1,'merchandise','A','2018-05-24 20:12:00','admin@gmail.com',NULL,NULL),(10000000007,'Dove Coconut Milk Soap 5','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000000000,1,'merchandise','A','2018-05-24 20:12:00','admin@gmail.com',NULL,NULL),(10000010000,'Dove Coconut Milk Soap','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',1,NULL,1,'merchandise','A','2018-05-24 20:15:34','admin@gmail.com','2018-05-24 20:17:39','admin@gmail.com'),(10000010001,'Dove Coconut Milk Soap 100','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000010000,1,'merchandise','A','2018-05-24 20:17:39','admin@gmail.com',NULL,NULL),(10000010002,'Dove Coconut Milk Soap 200','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000010000,1,'merchandise','A','2018-05-24 20:17:39','admin@gmail.com',NULL,NULL),(10000010003,'Dove Coconut Milk Soap 250','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000010000,1,'merchandise','A','2018-05-24 20:17:39','admin@gmail.com',NULL,NULL),(10000010004,'Dove Coconut Milk Soap 500','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000010000,1,'merchandise','A','2018-05-24 20:17:39','admin@gmail.com',NULL,NULL),(10000010005,'Dove Coconut Milk Soap 1','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000010000,1,'merchandise','A','2018-05-24 20:17:39','admin@gmail.com',NULL,NULL),(10000010006,'Dove Coconut Milk Soap 2','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000010000,1,'merchandise','A','2018-05-24 20:17:39','admin@gmail.com',NULL,NULL),(10000010007,'Dove Coconut Milk Soap 5','Dove Coconut Milk Soap Dove Coconut Milk Soap Dove Coconut Milk Soap',2,10000010000,1,'merchandise','A','2018-05-24 20:17:39','admin@gmail.com',NULL,NULL),(10000030000,'Good Day Biscuits','Good Day Biscuits Good Day Biscuits Good Day Biscuits',1,NULL,1,'merchandise','A','2018-05-25 09:19:57','admin@gmail.com','2018-05-25 09:20:53','admin@gmail.com'),(10000030001,'Good Day Biscuits 100','Good Day Biscuits Good Day Biscuits Good Day Biscuits',2,10000030000,1,'merchandise','A','2018-05-25 09:20:53','admin@gmail.com','2018-06-08 09:53:45','admin@gmail.com'),(10000030002,'Good Day Biscuits 200','Good Day Biscuits Good Day Biscuits Good Day Biscuits',2,10000030000,1,'merchandise','A','2018-05-25 09:20:53','admin@gmail.com',NULL,NULL),(10000030003,'Good Day Biscuits 250','Good Day Biscuits Good Day Biscuits Good Day Biscuits',2,10000030000,1,'merchandise','A','2018-05-25 09:20:53','admin@gmail.com',NULL,NULL),(10000030004,'Good Day Biscuits 500','Good Day Biscuits Good Day Biscuits Good Day Biscuits',2,10000030000,1,'merchandise','A','2018-05-25 09:20:53','admin@gmail.com',NULL,NULL),(10000030005,'Good Day Biscuits 1','Good Day Biscuits Good Day Biscuits Good Day Biscuits',2,10000030000,1,'merchandise','A','2018-05-25 09:20:53','admin@gmail.com',NULL,NULL),(10000030006,'Good Day Biscuits 2','Good Day Biscuits Good Day Biscuits Good Day Biscuits',2,10000030000,1,'merchandise','A','2018-05-25 09:20:53','admin@gmail.com',NULL,NULL),(10000030007,'Good Day Biscuits 5','Good Day Biscuits Good Day Biscuits Good Day Biscuits',2,10000030000,1,'merchandise','A','2018-05-25 09:20:53','admin@gmail.com',NULL,NULL),(10000040000,'India Organic tulsi tummy syrup','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup',1,NULL,1,'merchandise','A','2018-05-25 09:22:23','admin@gmail.com','2018-05-25 09:29:45','admin@gmail.com'),(10000040001,'India Organic tulsi tummy syrup 100','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup',2,10000040000,1,'merchandise','A','2018-05-25 09:29:45','admin@gmail.com','2018-05-25 10:01:49','admin@gmail.com'),(10000040002,'India Organic tulsi tummy syrup 200','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup',2,10000040000,1,'merchandise','A','2018-05-25 09:29:45','admin@gmail.com',NULL,NULL),(10000040003,'India Organic tulsi tummy syrup 250','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup',2,10000040000,1,'merchandise','A','2018-05-25 09:29:45','admin@gmail.com',NULL,NULL),(10000040004,'India Organic tulsi tummy syrup 500','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup',2,10000040000,1,'merchandise','A','2018-05-25 09:29:45','admin@gmail.com',NULL,NULL),(10000040005,'India Organic tulsi tummy syrup 1','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup',2,10000040000,1,'merchandise','A','2018-05-25 09:29:45','admin@gmail.com',NULL,NULL),(10000040006,'India Organic tulsi tummy syrup 2','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup',2,10000040000,1,'merchandise','A','2018-05-25 09:29:45','admin@gmail.com',NULL,NULL),(10000040007,'India Organic tulsi tummy syrup 5','India Organic tulsi tummy syrup India Organic tulsi tummy syrup India Organic tulsi tummy syrup',2,10000040000,1,'merchandise','A','2018-05-25 09:29:45','admin@gmail.com',NULL,NULL),(10000050000,'ASUS Phone F Model','ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model',1,NULL,1,'merchandise','A','2018-05-25 09:32:50','admin@gmail.com','2018-05-25 09:35:16','admin@gmail.com'),(10000050001,'ASUS Phone F Model RED R','ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model',2,10000050000,1,'merchandise','A','2018-05-25 09:35:16','admin@gmail.com','2018-06-07 19:01:52','admin@gmail.com'),(10000050002,'ASUS Phone F Model GREEN','ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model',2,10000050000,1,'merchandise','A','2018-05-25 09:35:16','admin@gmail.com','2018-06-07 19:01:52','admin@gmail.com'),(10000050003,'ASUS Phone F Model BLUE','ASUS Phone F Model ASUS Phone F Model ASUS Phone F Model',2,10000050000,1,'merchandise','A','2018-05-25 09:35:16','admin@gmail.com','2018-06-07 19:01:52','admin@gmail.com'),(10000060000,'MOSRELIEF Mosquito repellent','',1,NULL,1,'merchandise','A','2018-06-03 12:01:22','admin@gmail.com','2018-06-03 12:02:43','admin@gmail.com'),(10000060001,'MOSRELIEF Mosquito repellent L','',2,10000060000,1,'merchandise','A','2018-06-03 12:02:43','admin@gmail.com','2018-06-04 02:03:37','admin@gmail.com'),(10000060002,'MOSRELIEF Mosquito repellent M','',2,10000060000,1,'merchandise','A','2018-06-03 12:02:43','admin@gmail.com','2018-06-04 02:22:26','admin@gmail.com'),(10000060003,'MOSRELIEF Mosquito repellent S','',2,10000060000,1,'merchandise','A','2018-06-03 12:02:43','admin@gmail.com',NULL,NULL),(10000070000,'Himalayas Face Deep Cleaning','Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning ',1,NULL,1,'merchandise','A','2018-06-06 11:59:57','admin@gmail.com','2018-06-06 12:04:49','admin@gmail.com'),(10000070001,'Himalayas Face Deep Cleaning L','Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning ',2,10000070000,1,'merchandise','A','2018-06-06 12:04:49','admin@gmail.com',NULL,NULL),(10000070002,'Himalayas Face Deep Cleaning M','Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning ',2,10000070000,1,'merchandise','A','2018-06-06 12:04:49','admin@gmail.com',NULL,NULL),(10000070003,'Himalayas Face Deep Cleaning S','Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning Himalayas Face Deep Cleaning ',2,10000070000,1,'merchandise','A','2018-06-06 12:04:49','admin@gmail.com',NULL,NULL);
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

-- Dump completed on 2018-06-08 10:44:13
