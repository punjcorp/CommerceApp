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
-- Table structure for table `purchase_order_items`
--

DROP TABLE IF EXISTS `purchase_order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_order_items` (
  `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_name` varchar(150) NOT NULL,
  `unit_cost` decimal(12,2) NOT NULL,
  `ordered_qty` decimal(12,2) NOT NULL,
  `cost_amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `tax_amount` decimal(12,2) DEFAULT '0.00',
  `total_cost` decimal(12,2) NOT NULL DEFAULT '0.00',
  `actual_unit_cost` decimal(12,2) DEFAULT '0.00',
  `delievered_date` datetime DEFAULT NULL,
  `delievered_qty` decimal(12,2) DEFAULT NULL,
  `actual_cost_amount` decimal(12,2) DEFAULT '0.00',
  `actual_suggested_price` decimal(12,2) DEFAULT '0.00',
  `actual_max_retail_price` decimal(12,2) DEFAULT '0.00',
  `actual_discount_amount` decimal(12,2) DEFAULT '0.00',
  `actual_tax_amount` decimal(12,2) DEFAULT '0.00',
  `actual_total_cost` decimal(12,2) DEFAULT '0.00',
  PRIMARY KEY (`order_item_id`),
  KEY `fk_purchase_order_items_purchase_order1_idx` (`order_id`),
  KEY `fk_purchase_order_items_item1_idx` (`item_id`),
  CONSTRAINT `fk_purchase_order_items_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_order_items_purchase_order1` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_items`
--

LOCK TABLES `purchase_order_items` WRITE;
/*!40000 ALTER TABLE `purchase_order_items` DISABLE KEYS */;
INSERT INTO `purchase_order_items` VALUES (1,1,10000040006,'India Organic tulsi tummy syrup 2',90.00,6.00,540.00,64.80,604.80,0.00,'2018-05-25 09:38:58',NULL,0.00,0.00,0.00,0.00,0.00,0.00),(2,2,10000030001,'Good Day Biscuits 100',30.00,1.00,30.00,3.00,33.00,0.00,'2018-05-25 09:45:00',NULL,0.00,0.00,0.00,0.00,0.00,0.00),(3,3,10000040001,'India Organic tulsi tummy syrup 100',120.00,1.00,120.00,14.40,134.40,120.00,'2018-05-25 10:01:49',1.00,120.00,125.00,150.00,0.00,14.40,134.40),(4,4,10000000001,'Dove Coconut Milk Soap 100',58.00,1.00,58.00,10.44,68.44,0.00,'2018-05-25 11:31:52',NULL,0.00,0.00,0.00,0.00,0.00,0.00),(5,5,10000030004,'Good Day Biscuits 500',23.00,1.00,23.00,2.30,25.30,0.00,'2018-05-25 11:32:39',NULL,0.00,0.00,0.00,0.00,0.00,0.00),(6,6,10000030002,'Good Day Biscuits 200',34.00,1.00,34.00,3.40,37.40,0.00,'2018-05-30 13:20:15',NULL,0.00,0.00,0.00,0.00,0.00,0.00),(7,7,10000060002,'MOSRELIEF Mosquito repellent M',35.00,5.00,175.00,21.00,196.00,36.00,'2018-06-03 12:05:59',6.00,216.00,42.00,50.00,0.00,25.92,241.92),(8,8,10000060001,'MOSRELIEF Mosquito repellent L',40.00,1.00,40.00,4.80,44.80,39.00,'2018-06-04 02:03:36',3.00,117.00,45.00,55.00,0.00,14.04,131.04),(9,9,10000060002,'MOSRELIEF Mosquito repellent M',36.00,6.00,216.00,25.92,241.92,36.00,'2018-06-04 02:21:44',7.00,252.00,45.00,52.00,0.00,30.24,282.24),(10,10,10000030001,'Good Day Biscuits 100',18.00,10.00,180.00,18.00,198.00,16.00,'2018-06-04 02:28:13',20.00,320.00,19.00,20.00,0.00,32.00,352.00),(11,11,10000000002,'Dove Coconut Milk Soap 200',23.00,1.00,23.00,4.14,27.14,23.00,'2018-06-08 09:40:29',4.00,92.00,28.00,32.00,0.00,16.56,108.56),(12,12,10000030001,'Good Day Biscuits 100',16.00,7.00,112.00,11.20,123.20,17.00,'2018-06-08 09:53:45',7.00,119.00,19.00,20.00,0.00,11.90,130.90);
/*!40000 ALTER TABLE `purchase_order_items` ENABLE KEYS */;
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
