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
  `actual_sub_total_cost` decimal(12,2) DEFAULT NULL,
  `actual_tax_amount` decimal(12,2) DEFAULT NULL,
  `actual_total_amount` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_purchase_order_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_purchase_order_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order`
--

LOCK TABLES `purchase_order` WRITE;
/*!40000 ALTER TABLE `purchase_order` DISABLE KEYS */;
INSERT INTO `purchase_order` VALUES (1,3,7997,'admin@gmail.com','2018-05-25 09:38:58',540.00,0.00,64.80,604.80,0.00,'A',NULL,NULL,NULL,'sdf sdf sdf sdf sdf ',0.00,0.00,0.00),(2,3,7997,'admin@gmail.com','2018-05-25 09:43:13',30.00,0.00,3.00,33.00,0.00,'A',NULL,'2018-05-25 09:45:00','admin@gmail.com','sg sdg sdg dsg sd ',0.00,0.00,0.00),(3,5,7997,'admin@gmail.com','2018-05-25 09:46:19',120.00,0.00,14.40,134.40,0.00,'R',NULL,'2018-05-25 10:01:49','admin@gmail.com','fgh dfh dfh dfh fdh df',120.00,14.40,134.40),(4,1,7997,'admin@gmail.com','2018-05-25 11:31:52',58.00,0.00,10.44,68.44,0.00,'A',NULL,NULL,NULL,'df dfh dfh dfhdf hd df ',0.00,0.00,0.00),(5,4,7997,'admin@gmail.com','2018-05-25 11:32:39',23.00,0.00,2.30,25.30,0.00,'C',NULL,NULL,NULL,'sdf dsfs df sdfs ',0.00,0.00,0.00),(6,3,7997,'admin@gmail.com','2018-05-30 13:20:15',34.00,0.00,3.40,37.40,0.00,'A',NULL,NULL,NULL,'here is my comment',0.00,0.00,0.00),(7,2,7997,'admin@gmail.com','2018-06-03 12:04:24',175.00,0.00,21.00,196.00,0.00,'R',NULL,'2018-06-03 12:05:59','admin@gmail.com','',216.00,25.92,241.92),(8,2,7997,'admin@gmail.com','2018-06-04 02:02:17',40.00,0.00,4.80,44.80,0.00,'R',NULL,'2018-06-04 02:03:36','admin@gmail.com','gj jfj ',117.00,14.04,131.04),(9,3,7997,'admin@gmail.com','2018-06-04 02:20:49',216.00,0.00,25.92,241.92,0.00,'R',NULL,'2018-06-04 02:21:44','admin@gmail.com','here is the comment',252.00,30.24,282.24),(10,1,7997,'admin@gmail.com','2018-06-04 02:27:36',180.00,0.00,18.00,198.00,0.00,'R',NULL,'2018-06-04 02:28:13','admin@gmail.com','gg dgsdgs',320.00,32.00,352.00),(11,1,7997,'admin@gmail.com','2018-06-08 09:39:17',23.00,0.00,4.14,27.14,0.00,'R',NULL,'2018-06-08 09:40:29','admin@gmail.com','afs asf asf asf as',92.00,16.56,108.56),(12,2,7997,'admin@gmail.com','2018-06-08 09:52:14',112.00,0.00,11.20,123.20,0.00,'R',NULL,'2018-06-08 09:53:45','admin@gmail.com','sgsdg sdg sdg ',119.00,11.90,130.90);
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

-- Dump completed on 2018-06-08 10:44:03
