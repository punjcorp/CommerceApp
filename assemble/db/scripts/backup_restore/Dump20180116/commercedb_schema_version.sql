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
INSERT INTO `schema_version` VALUES (1,'1','Create commercedb schema','SQL','V1__Create_commercedb_schema.sql',172601508,'root','2017-12-18 05:51:00',393,1),(2,'2','Create foundation tables','SQL','V2__Create_foundation_tables.sql',-1854073382,'root','2017-12-18 05:51:02',1770,1),(3,'3','Create tax tables','SQL','V3__Create_tax_tables.sql',104635380,'root','2017-12-18 05:51:09',5963,1),(4,'4','Create supplier tables','SQL','V4__Create_supplier_tables.sql',1142988645,'root','2017-12-18 05:51:11',2017,1),(5,'5','Create item tables','SQL','V5__Create_item_tables.sql',-544945529,'root','2017-12-18 05:51:19',8583,1),(6,'6','Create inventory tables','SQL','V6__Create_inventory_tables.sql',-498211236,'root','2017-12-18 05:51:30',10611,1),(7,'7','Create pricing tables','SQL','V7__Create_pricing_tables.sql',275293126,'root','2017-12-18 05:51:32',1808,1),(8,'8','Create user tables','SQL','V8__Create_user_tables.sql',256342280,'root','2017-12-18 05:51:49',16214,1),(9,'9','Create po tables','SQL','V9__Create_po_tables.sql',-685568460,'root','2017-12-18 05:51:51',2461,1),(10,'10','Alter Item Options','SQL','V10__Alter_Item_Options.sql',-673108100,'root','2017-12-18 05:51:53',1970,1),(11,'51','Insert Seed Data','SQL','seed_data/V51__Insert_Seed_Data.sql',195029915,'root','2017-12-18 05:51:54',940,1),(12,'52','Insert Sample Items','SQL','seed_data/V52__Insert_Sample_Items.sql',-56653662,'root','2017-12-18 05:51:55',163,1);
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

-- Dump completed on 2018-01-16 18:57:43
