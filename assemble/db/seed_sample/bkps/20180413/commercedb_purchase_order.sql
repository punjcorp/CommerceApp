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
-- Table structure for table `purchase_order`
--

DROP TABLE IF EXISTS `purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supplier_id` int(11) NOT NULL,
  `location_id` int(4) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `sub_total_cost` decimal(12,2) NOT NULL,
  `discount_amount` decimal(12,2) DEFAULT '0.00',
  `tax_amount` decimal(12,2) DEFAULT '0.00',
  `total_amount` decimal(12,2) DEFAULT '0.00',
  `paid_amount` decimal(12,2) DEFAULT '0.00',
  `status` varchar(1) NOT NULL,
  `invoice` blob,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `actual_sub_total_cost` decimal(12,2) DEFAULT '0.00',
  `actual_tax_amount` decimal(12,2) DEFAULT '0.00',
  `actual_total_amount` decimal(12,2) DEFAULT '0.00',
  PRIMARY KEY (`order_id`),
  KEY `fk_purchase_order_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_purchase_order_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order`
--

LOCK TABLES `purchase_order` WRITE;
/*!40000 ALTER TABLE `purchase_order` DISABLE KEYS */;
INSERT INTO `purchase_order` VALUES (61,9,7997,'admin@gmail.com','2018-04-09 20:03:43',40.00,0.00,11.20,51.20,0.00,'A',NULL,'2018-04-09 20:05:41','admin@gmail.com','here we go again',225.00,63.00,288.00),(62,9,7997,'admin@gmail.com','2018-04-10 14:22:49',40.00,0.00,11.20,51.20,0.00,'A',NULL,NULL,NULL,'here we go',0.00,0.00,0.00),(63,9,7997,'admin@gmail.com','2018-04-10 14:26:59',40.00,0.00,11.20,51.20,0.00,'A',NULL,NULL,NULL,'',0.00,0.00,0.00),(64,10,7997,'admin@gmail.com','2018-04-10 14:43:30',30.00,0.00,8.40,38.40,0.00,'A',NULL,NULL,NULL,'here it is ',0.00,0.00,0.00),(65,9,7997,'admin@gmail.com','2018-04-10 14:47:07',40.00,0.00,11.20,51.20,0.00,'A',NULL,NULL,NULL,'',0.00,0.00,0.00),(66,9,7997,'admin@gmail.com','2018-04-10 14:55:19',40.00,0.00,11.20,51.20,0.00,'A',NULL,'2018-04-10 15:15:18','admin@gmail.com','',40.00,11.20,51.20),(67,10,7997,'admin@gmail.com','2018-04-10 15:06:01',30.00,0.00,8.40,38.40,0.00,'A',NULL,NULL,NULL,'sdgdsg sdg dsg ',0.00,0.00,0.00),(68,9,7997,'admin@gmail.com','2018-04-10 18:23:38',40.00,0.00,11.20,51.20,0.00,'A',NULL,'2018-04-10 18:25:08','admin@gmail.com','here we go',0.00,0.00,0.00),(69,10,7997,'admin@gmail.com','2018-04-12 17:28:35',30.00,0.00,8.40,38.40,0.00,'A',NULL,'2018-04-12 17:30:40','admin@gmail.com','here we go',0.00,0.00,0.00),(70,9,7997,'admin@gmail.com','2018-04-12 19:04:04',40.00,0.00,11.20,51.20,0.00,'A',NULL,NULL,NULL,'here we go',0.00,0.00,0.00);
/*!40000 ALTER TABLE `purchase_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:00
