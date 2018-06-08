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
-- Table structure for table `txn_no_sale_tender`
--

DROP TABLE IF EXISTS `txn_no_sale_tender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_no_sale_tender` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `tender_id` int(3) NOT NULL,
  `seq_no` int(11) NOT NULL,
  `to_account_no` varchar(100) DEFAULT NULL,
  `to_bank_name` varchar(100) DEFAULT NULL,
  `to_bank_branch` varchar(100) DEFAULT NULL,
  `to_payee_name` varchar(100) DEFAULT NULL,
  `to_payee_phone` varchar(20) DEFAULT NULL,
  `to_details` varchar(100) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`,`tender_id`,`created_date`),
  KEY `fk_txn_no_sale_tender_tender_id_fk1_idx` (`tender_id`),
  CONSTRAINT `fk_txn_no_sale_tender_tender_id_fk1` FOREIGN KEY (`tender_id`) REFERENCES `tender_master` (`tender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_txn_no_sale_tender_txn_no_sale1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`) REFERENCES `txn_no_sale` (`location_id`, `business_date`, `register`, `txn_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_no_sale_tender`
--

LOCK TABLES `txn_no_sale_tender` WRITE;
/*!40000 ALTER TABLE `txn_no_sale_tender` DISABLE KEYS */;
INSERT INTO `txn_no_sale_tender` VALUES (7997,'2018-05-24 00:00:00',1,3,500.00,1,0,'','','','ABC Inc','',NULL,'admin@gmail.com','2018-05-26 06:26:33'),(7997,'2018-05-24 00:00:00',1,4,600.00,1,0,'','','','PEB','',NULL,'admin@gmail.com','2018-05-26 10:33:31'),(7997,'2018-05-24 00:00:00',1,5,200.00,1,0,'','','','asaf as','',NULL,'admin@gmail.com','2018-05-26 10:42:35'),(7997,'2018-05-24 00:00:00',1,6,145.00,1,0,'','','','sdf sdf dsf sd','',NULL,'admin@gmail.com','2018-05-26 10:57:35'),(7997,'2018-05-24 00:00:00',1,7,140.00,1,0,'','','','guhiih','',NULL,'admin@gmail.com','2018-05-26 11:00:29'),(7997,'2018-05-24 00:00:00',1,8,120.00,1,0,'','','','asf as sa','',NULL,'admin@gmail.com','2018-05-26 11:12:19'),(7997,'2018-05-24 00:00:00',1,9,50.00,1,0,'','','','sfa fasf asf asf saf as','',NULL,'admin@gmail.com','2018-05-26 11:42:48'),(7997,'2018-05-24 00:00:00',1,10,707.00,1,1,'','','','fg sdg sd','',NULL,'admin@gmail.com','2018-05-26 18:51:14'),(7997,'2018-05-24 00:00:00',1,11,98.00,1,1,'','','','sdfsd fs','',NULL,'admin@gmail.com','2018-05-26 19:09:53'),(7997,'2018-05-24 00:00:00',1,12,72.00,1,1,'','','','asfasf asf','',NULL,'admin@gmail.com','2018-05-26 20:07:44'),(7997,'2018-05-24 00:00:00',1,13,55.00,1,1,'','','','dfafafs ','',NULL,'admin@gmail.com','2018-05-26 20:12:25'),(7997,'2018-05-24 00:00:00',1,14,46.00,1,1,'','','','sdfg fs f','',NULL,'admin@gmail.com','2018-05-26 20:13:57'),(7997,'2018-05-24 00:00:00',1,15,12.00,1,1,'','','','afasfas fa as f','',NULL,'admin@gmail.com','2018-05-26 20:15:03'),(7997,'2018-05-24 00:00:00',1,16,22.00,1,1,'','','','sdf sdf sdf ','',NULL,'admin@gmail.com','2018-05-26 20:16:36'),(7997,'2018-05-24 00:00:00',1,17,40.00,1,1,'','','','afsaf asf asf a','',NULL,'admin@gmail.com','2018-05-28 09:27:38'),(7997,'2018-05-24 00:00:00',1,18,241.00,1,1,'','','','fdg sdgs dgsd g','2323232',NULL,'admin@gmail.com','2018-05-28 10:36:36'),(7997,'2018-05-24 00:00:00',1,19,23.40,1,1,'','','','sdg sdg sdg s','3453454353','fghsdh dh ds fh s','admin@gmail.com','2018-05-28 11:13:22'),(7997,'2018-05-24 00:00:00',1,20,23.30,1,1,'','','','payee name','23423432','details','admin@gmail.com','2018-05-28 11:23:03'),(7997,'2018-05-24 00:00:00',1,21,234.00,1,1,'','','','Billing board','234324','this all was given in cash to so and so person','admin@gmail.com','2018-05-28 11:38:52'),(7997,'2018-05-24 00:00:00',1,22,23.00,1,1,'','','','safas f as','03453254234','sdf sdf dsf dsf sdf dsf dsf dsf dsf sdf sdf dsf dsf sd','admin@gmail.com','2018-05-28 11:47:23'),(7997,'2018-05-24 00:00:00',1,23,23.00,1,1,'','','','zffsdwwwww','08847523677','here are more details','admin@gmail.com','2018-05-28 12:31:11'),(7997,'2018-05-24 00:00:00',1,24,23.00,1,1,'','','','sdfdsfs','09876039378','sdf dsf dsf ds','admin@gmail.com','2018-05-28 12:51:13'),(7997,'2018-05-24 00:00:00',1,25,22.00,1,1,'','','','sdfdfdsf','03453254234','dfs detete e re','admin@gmail.com','2018-05-28 13:13:07'),(7997,'2018-05-24 00:00:00',1,26,23.50,1,1,'','','','sdfsdf sdf sdf','09876039378','sdf dsf sdf ds','admin@gmail.com','2018-05-28 13:29:36'),(7997,'2018-05-24 00:00:00',1,27,22.00,1,1,'','','','dsfg sdf dsf sd','233322323232','3223232323232','admin@gmail.com','2018-05-28 13:49:08'),(7997,'2018-05-24 00:00:00',1,28,22.20,1,1,'','','','asfafasfsaf ','','','admin@gmail.com','2018-05-28 13:59:03'),(7997,'2018-05-24 00:00:00',1,30,23.00,1,1,'','','','atert','','','admin@gmail.com','2018-06-03 12:14:37'),(7997,'2018-05-24 00:00:00',1,30,34.00,4,2,'45645654645','','','atert','4545454545','','admin@gmail.com','2018-06-03 12:14:37');
/*!40000 ALTER TABLE `txn_no_sale_tender` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 10:44:05
