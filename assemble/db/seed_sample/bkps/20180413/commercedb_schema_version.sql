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
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_version` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_version`
--

LOCK TABLES `schema_version` WRITE;
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` VALUES (1,'1','Create commercedb schema','SQL','V1__Create_commercedb_schema.sql',172601508,'admin','2018-03-24 05:44:02',237,1),(2,'2','Create foundation tables','SQL','V2__Create_foundation_tables.sql',-1888051161,'admin','2018-03-24 05:44:05',2685,1),(3,'3','Create tax tables','SQL','V3__Create_tax_tables.sql',-885844047,'admin','2018-03-24 05:44:09',3522,1),(4,'4','Create supplier tables','SQL','V4__Create_supplier_tables.sql',2070738615,'admin','2018-03-24 05:44:11',1877,1),(5,'5','Create item tables','SQL','V5__Create_item_tables.sql',642897236,'admin','2018-03-24 05:44:18',6388,1),(6,'6','Create inventory tables','SQL','V6__Create_inventory_tables.sql',28658784,'admin','2018-03-24 05:44:25',6845,1),(7,'7','Create pricing tables','SQL','V7__Create_pricing_tables.sql',-1638107080,'admin','2018-03-24 05:44:25',638,1),(8,'8','Create user tables','SQL','V8__Create_user_tables.sql',256342280,'admin','2018-03-24 05:44:32',7039,1),(9,'9','Create po tables','SQL','V9__Create_po_tables.sql',16682604,'admin','2018-03-24 05:44:35',2629,1),(10,'10','Create txn tables','SQL','V10__Create_txn_tables.sql',-306942233,'admin','2018-03-24 05:44:46',10163,1),(11,'11','Create daily operations tables','SQL','V11__Create_daily_operations_tables.sql',-2110864366,'admin','2018-03-24 05:44:51',5678,1),(12,'12','Create views','SQL','V12__Create_views.sql',-2015481837,'admin','2018-03-24 05:44:52',341,1),(13,'51','Insert Seed Data','SQL','seed_data/V51__Insert_Seed_Data.sql',-1190170519,'admin','2018-03-24 05:45:03',11106,1),(14,'52','Insert Sample Items','SQL','seed_data/V52__Insert_Sample_Items.sql',955539874,'admin','2018-03-24 05:45:11',6865,1);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 13:45:10
