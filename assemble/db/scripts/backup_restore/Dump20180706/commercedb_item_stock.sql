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
-- Table structure for table `item_stock`
--

DROP TABLE IF EXISTS `item_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_stock` (
  `item_id` bigint(20) NOT NULL,
  `location_id` int(4) NOT NULL,
  `total_qty` int(11) NOT NULL DEFAULT '0',
  `non_sellable_qty` int(11) NOT NULL DEFAULT '0',
  `reserved_qty` int(11) NOT NULL DEFAULT '0',
  `stock_on_hand` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`,`location_id`),
  KEY `fk_item_stock_location1_idx` (`location_id`),
  CONSTRAINT `fk_item_stock_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_stock_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_stock`
--

LOCK TABLES `item_stock` WRITE;
/*!40000 ALTER TABLE `item_stock` DISABLE KEYS */;
INSERT INTO `item_stock` VALUES (10000000001,2,12,0,0,12),(10000000001,3,12,0,0,12),(10000000001,27,10,0,0,10),(10000000001,7997,7,0,0,7),(10000000002,2,5,0,0,5),(10000000002,3,5,0,0,5),(10000000002,27,5,0,0,5),(10000000002,7997,2,0,0,2),(10000000003,2,5,0,0,5),(10000000003,3,5,0,0,5),(10000000003,27,5,0,0,5),(10000000003,7997,3,0,0,3),(10000010001,2,3,0,0,3),(10000010001,3,4,0,0,4),(10000010001,27,2,0,0,2),(10000010001,7997,-15,0,0,-15),(10000010002,2,12,0,0,12),(10000010002,3,14,0,0,14),(10000010002,27,14,0,0,14),(10000010002,7997,-24,0,0,-24),(10000010003,2,2,0,0,2),(10000010003,3,2,0,0,2),(10000010003,27,2,0,0,2),(10000010003,7997,1,0,0,1),(10000020001,2,7,0,0,7),(10000020001,3,7,0,0,7),(10000020001,27,7,0,0,7),(10000020001,7997,6,0,0,6),(10000020002,2,0,0,0,0),(10000020002,3,0,0,0,0),(10000020002,27,0,0,0,0),(10000020002,7997,-10,0,0,-10),(10000020003,2,0,0,0,0),(10000020003,3,0,0,0,0),(10000020003,27,0,0,0,0),(10000020003,7997,-3,0,0,-3),(10000030001,2,12,0,0,12),(10000030001,3,12,0,0,12),(10000030001,27,12,0,0,12),(10000030001,7997,12,0,0,12),(10000030002,2,2,0,0,2),(10000030002,3,2,0,0,2),(10000030002,27,2,0,0,2),(10000030002,7997,2,0,0,2),(10000030003,2,4,0,0,4),(10000030003,3,4,0,0,4),(10000030003,27,4,0,0,4),(10000030003,7997,4,0,0,4),(10000040001,2,0,0,0,0),(10000040001,3,0,0,0,0),(10000040001,27,0,0,0,0),(10000040001,7997,0,0,0,0),(10000040002,2,0,0,0,0),(10000040002,3,0,0,0,0),(10000040002,27,0,0,0,0),(10000040002,7997,0,0,0,0),(10000040003,2,0,0,0,0),(10000040003,3,0,0,0,0),(10000040003,27,0,0,0,0),(10000040003,7997,0,0,0,0),(10000050001,2,0,0,0,0),(10000050001,3,0,0,0,0),(10000050001,27,0,0,0,0),(10000050001,7997,0,0,0,0),(10000050002,2,0,0,0,0),(10000050002,3,0,0,0,0),(10000050002,27,0,0,0,0),(10000050002,7997,0,0,0,0),(10000050003,2,0,0,0,0),(10000050003,3,0,0,0,0),(10000050003,27,0,0,0,0),(10000050003,7997,0,0,0,0),(10000060001,2,0,0,0,0),(10000060001,3,0,0,0,0),(10000060001,27,0,0,0,0),(10000060001,7997,0,0,0,0),(10000060002,2,0,0,0,0),(10000060002,3,0,0,0,0),(10000060002,27,0,0,0,0),(10000060002,7997,0,0,0,0),(10000060003,2,0,0,0,0),(10000060003,3,0,0,0,0),(10000060003,27,0,0,0,0),(10000060003,7997,0,0,0,0),(10000070001,2,0,0,0,0),(10000070001,3,0,0,0,0),(10000070001,27,0,0,0,0),(10000070001,7997,0,0,0,0),(10000070002,2,0,0,0,0),(10000070002,3,0,0,0,0),(10000070002,27,0,0,0,0),(10000070002,7997,0,0,0,0),(10000070003,2,0,0,0,0),(10000070003,3,0,0,0,0),(10000070003,27,0,0,0,0),(10000070003,7997,0,0,0,0),(10000080001,2,0,0,0,0),(10000080001,3,0,0,0,0),(10000080001,27,0,0,0,0),(10000080001,7997,0,0,0,0),(10000080002,2,0,0,0,0),(10000080002,3,0,0,0,0),(10000080002,27,0,0,0,0),(10000080002,7997,0,0,0,0),(10000080003,2,0,0,0,0),(10000080003,3,0,0,0,0),(10000080003,27,0,0,0,0),(10000080003,7997,0,0,0,0),(10000140001,2,0,0,0,0),(10000140001,3,0,0,0,0),(10000140001,27,0,0,0,0),(10000140001,7997,0,0,0,0),(10000140002,2,0,0,0,0),(10000140002,3,0,0,0,0),(10000140002,27,0,0,0,0),(10000140002,7997,0,0,0,0),(10000140003,2,0,0,0,0),(10000140003,3,0,0,0,0),(10000140003,27,0,0,0,0),(10000140003,7997,0,0,0,0),(10000150001,2,0,0,0,0),(10000150001,3,0,0,0,0),(10000150001,27,0,0,0,0),(10000150001,7997,0,0,0,0),(10000150002,2,0,0,0,0),(10000150002,3,0,0,0,0),(10000150002,27,0,0,0,0),(10000150002,7997,0,0,0,0),(10000150003,2,0,0,0,0),(10000150003,3,0,0,0,0),(10000150003,27,0,0,0,0),(10000150003,7997,0,0,0,0);
/*!40000 ALTER TABLE `item_stock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:21
