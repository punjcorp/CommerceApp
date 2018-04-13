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
INSERT INTO `item` VALUES (1000000,'Pants','This style is for all type of pants',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000001,'Shirts','This style is for all type of Shirts',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000002,'Jeans','This style is for all type of Jeans',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000003,'Formal Shirts','This style is for all type of Formal Shirts',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000004,'Soap','This style is for all type of Soap',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000005,'Powder','This style is for all type of Powder',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000006,'Deoderants','This style is for all type of Deoderants',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000007,'Perfumes','This style is for all type of Perfumes',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000008,'Sports Shoes','This style is for all type of Sports Shoes',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1000009,'Hair Bands','This style is for all type of Hair Bands',1,NULL,1,'Merchandise','A','2018-01-19 20:12:57','admin@gmail.com',NULL,NULL),(1999999,'Expense Parent','This is parent item for all type of expenses',1,NULL,1,'Expense','A','2018-03-24 11:15:08','admin',NULL,NULL),(10000000102,'Item Name','Item Description',2,1000000,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000000103,'Item Name','Item Description',2,1000000,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000010102,'Item Name','Item Description',2,1000001,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000010103,'Item Name','Item Description',2,1000001,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000020102,'Item Name','Item Description',2,1000002,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000020103,'Item Name','Item Description',2,1000002,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000030102,'Item Name','Item Description',2,1000003,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000030103,'Item Name','Item Description',2,1000003,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000040102,'Item Name','Item Description',2,1000004,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000040103,'Item Name','Item Description',2,1000004,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000050102,'Item Name','Item Description',2,1000005,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000050103,'Item Name','Item Description',2,1000005,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000060102,'Item Name','Item Description',2,1000006,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000060103,'Item Name','Item Description',2,1000006,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000070102,'Item Name','Item Description',2,1000007,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000070103,'Item Name','Item Description',2,1000007,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000080102,'Item Name','Item Description',2,1000008,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000080103,'Item Name','Item Description',2,1000008,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000090102,'Item Name','Item Description',2,1000009,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(10000090103,'Item Name','Item Description',2,1000009,1,'Merchandise','A','2018-01-19 20:29:48','admin@gmail.com',NULL,NULL),(19999990001,'Order Payment','This item is to do the order payment',2,1999999,1,'Expense','A','2018-03-24 11:15:08','admin',NULL,NULL);
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

-- Dump completed on 2018-04-13 13:45:09
