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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items_tax`
--

LOCK TABLES `purchase_order_items_tax` WRITE;
/*!40000 ALTER TABLE `purchase_order_items_tax` DISABLE KEYS */;
INSERT INTO `purchase_order_items_tax` VALUES (1,1,4,8,32.40,6.00,'CGST',540.00,0.00,0.00),(2,1,4,7,32.40,6.00,'SGST',540.00,0.00,0.00),(3,2,3,6,1.50,5.00,'CGST',30.00,0.00,0.00),(4,2,3,5,1.50,5.00,'SGST',30.00,0.00,0.00),(5,3,4,8,7.20,6.00,'CGST',120.00,120.00,7.20),(6,3,4,7,7.20,6.00,'SGST',120.00,120.00,7.20),(7,4,5,10,5.22,9.00,'CGST',58.00,0.00,0.00),(8,4,5,9,5.22,9.00,'SGST',58.00,0.00,0.00),(9,5,3,6,1.15,5.00,'CGST',23.00,0.00,0.00),(10,5,3,5,1.15,5.00,'SGST',23.00,0.00,0.00),(11,6,3,6,1.70,5.00,'CGST',34.00,0.00,0.00),(12,6,3,5,1.70,5.00,'SGST',34.00,0.00,0.00),(13,7,4,8,10.50,6.00,'CGST',175.00,216.00,12.96),(14,7,4,7,10.50,6.00,'SGST',175.00,216.00,12.96),(15,8,4,8,2.40,6.00,'CGST',40.00,117.00,7.02),(16,8,4,7,2.40,6.00,'SGST',40.00,117.00,7.02),(17,9,4,8,12.96,6.00,'CGST',216.00,252.00,15.12),(18,9,4,7,12.96,6.00,'SGST',216.00,252.00,15.12),(19,10,3,6,9.00,5.00,'CGST',180.00,320.00,16.00),(20,10,3,5,9.00,5.00,'SGST',180.00,320.00,16.00),(21,11,5,10,2.07,9.00,'CGST',23.00,92.00,8.28),(22,11,5,9,2.07,9.00,'SGST',23.00,92.00,8.28),(23,12,3,6,5.60,5.00,'CGST',112.00,119.00,5.95),(24,12,3,5,5.60,5.00,'SGST',112.00,119.00,5.95);
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

-- Dump completed on 2018-06-08 10:44:23
