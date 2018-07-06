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
-- Table structure for table `purchase_order_items_tax`
--

DROP TABLE IF EXISTS `purchase_order_items_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_order_items_tax` (
  `order_item_tax_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_item_id` bigint(20) NOT NULL,
  `tax_group_id` int(11) NOT NULL,
  `tax_rate_rule_id` int(11) NOT NULL,
  `tax_amount` decimal(12,2) NOT NULL,
  `tax_percentage` decimal(12,2) NOT NULL,
  `tax_code` varchar(30) NOT NULL,
  `taxable_amount` decimal(12,2) NOT NULL,
  `actual_taxable_amount` decimal(12,2) DEFAULT NULL,
  `actual_tax_amount` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`order_item_tax_id`),
  KEY `fk_purchase_order_items_tax_purchase_order_items1_idx` (`order_item_id`),
  CONSTRAINT `fk_purchase_order_items_tax_purchase_order_items1` FOREIGN KEY (`order_item_id`) REFERENCES `purchase_order_items` (`order_item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items_tax`
--

LOCK TABLES `purchase_order_items_tax` WRITE;
/*!40000 ALTER TABLE `purchase_order_items_tax` DISABLE KEYS */;
INSERT INTO `purchase_order_items_tax` VALUES (1,1,2,4,7.50,2.50,'CGST',300.00,600.00,15.00),(2,1,2,3,7.50,2.50,'SGST',300.00,600.00,15.00),(3,2,2,4,1.25,2.50,'CGST',50.00,0.00,0.00),(4,2,2,3,1.25,2.50,'SGST',50.00,0.00,0.00),(5,3,4,8,8.40,6.00,'CGST',140.00,0.00,0.00),(6,3,4,7,8.40,6.00,'SGST',140.00,0.00,0.00);
/*!40000 ALTER TABLE `purchase_order_items_tax` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:33
