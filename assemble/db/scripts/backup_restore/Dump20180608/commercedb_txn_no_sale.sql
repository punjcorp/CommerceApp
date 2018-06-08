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
-- Table structure for table `txn_no_sale`
--

DROP TABLE IF EXISTS `txn_no_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_no_sale` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `reason_code_id` int(11) NOT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`),
  CONSTRAINT `fk_txn_no_sale_txn_master1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`) REFERENCES `txn_master` (`location_id`, `business_date`, `register`, `txn_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_no_sale`
--

LOCK TABLES `txn_no_sale` WRITE;
/*!40000 ALTER TABLE `txn_no_sale` DISABLE KEYS */;
INSERT INTO `txn_no_sale` VALUES (7997,'2018-05-24 00:00:00',1,3,1,500.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 06:26:33',NULL,NULL),(7997,'2018-05-24 00:00:00',1,4,1,600.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 10:33:31',NULL,NULL),(7997,'2018-05-24 00:00:00',1,5,1,200.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 10:42:35',NULL,NULL),(7997,'2018-05-24 00:00:00',1,6,1,145.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 10:57:35',NULL,NULL),(7997,'2018-05-24 00:00:00',1,7,1,140.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 11:00:29',NULL,NULL),(7997,'2018-05-24 00:00:00',1,8,1,120.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 11:12:19',NULL,NULL),(7997,'2018-05-24 00:00:00',1,9,1,50.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 11:42:48',NULL,NULL),(7997,'2018-05-24 00:00:00',1,10,3,707.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 18:51:14',NULL,NULL),(7997,'2018-05-24 00:00:00',1,11,6,98.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 19:09:53',NULL,NULL),(7997,'2018-05-24 00:00:00',1,12,6,72.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 20:07:44',NULL,NULL),(7997,'2018-05-24 00:00:00',1,13,6,55.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 20:12:25',NULL,NULL),(7997,'2018-05-24 00:00:00',1,14,1,46.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 20:13:57',NULL,NULL),(7997,'2018-05-24 00:00:00',1,15,2,12.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 20:15:03',NULL,NULL),(7997,'2018-05-24 00:00:00',1,16,2,22.00,'Bill','COMPLETED','admin@gmail.com','2018-05-26 20:16:36',NULL,NULL),(7997,'2018-05-24 00:00:00',1,17,1,40.00,'Bill','COMPLETED','admin@gmail.com','2018-05-28 09:27:38',NULL,NULL),(7997,'2018-05-24 00:00:00',1,18,1,241.00,'Bill','COMPLETED','admin@gmail.com','2018-05-28 10:36:36',NULL,NULL),(7997,'2018-05-24 00:00:00',1,19,1,23.40,'Bill','COMPLETED','admin@gmail.com','2018-05-28 11:13:22',NULL,NULL),(7997,'2018-05-24 00:00:00',1,20,2,23.30,'Bill','COMPLETED','admin@gmail.com','2018-05-28 11:23:03',NULL,NULL),(7997,'2018-05-24 00:00:00',1,21,1,234.00,'Bill','COMPLETED','admin@gmail.com','2018-05-28 11:38:52',NULL,NULL),(7997,'2018-05-24 00:00:00',1,22,1,23.00,'Bill','COMPLETED','admin@gmail.com','2018-05-28 11:47:23',NULL,NULL),(7997,'2018-05-24 00:00:00',1,23,3,23.00,'Bill','COMPLETED','admin@gmail.com','2018-05-28 12:31:11',NULL,NULL),(7997,'2018-05-24 00:00:00',1,24,1,23.00,'Bill','COMPLETED','admin@gmail.com','2018-05-28 12:51:13',NULL,NULL),(7997,'2018-05-24 00:00:00',1,25,3,22.00,'Bill','COMPLETED','admin@gmail.com','2018-05-28 13:13:07',NULL,NULL),(7997,'2018-05-24 00:00:00',1,26,3,23.50,'Bill','COMPLETED','admin@gmail.com','2018-05-28 13:29:36',NULL,NULL),(7997,'2018-05-24 00:00:00',1,27,3,22.00,'Bill','COMPLETED','admin@gmail.com','2018-05-28 13:49:08',NULL,NULL),(7997,'2018-05-24 00:00:00',1,28,3,22.20,'Bill','COMPLETED','admin@gmail.com','2018-05-28 13:59:03',NULL,NULL),(7997,'2018-05-24 00:00:00',1,30,3,57.00,'Bill','COMPLETED','admin@gmail.com','2018-06-03 12:14:37',NULL,NULL);
/*!40000 ALTER TABLE `txn_no_sale` ENABLE KEYS */;
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
