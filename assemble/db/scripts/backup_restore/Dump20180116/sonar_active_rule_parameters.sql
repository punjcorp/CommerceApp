CREATE DATABASE  IF NOT EXISTS `sonar` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sonar`;
-- MySQL dump 10.13  Distrib 5.7.18, for Win64 (x86_64)
--
-- Host: localhost    Database: sonar
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
-- Table structure for table `active_rule_parameters`
--

DROP TABLE IF EXISTS `active_rule_parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `active_rule_parameters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active_rule_id` int(11) NOT NULL,
  `rules_parameter_id` int(11) NOT NULL,
  `value` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `rules_parameter_key` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_arp_on_active_rule_id` (`active_rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `active_rule_parameters`
--

LOCK TABLES `active_rule_parameters` WRITE;
/*!40000 ALTER TABLE `active_rule_parameters` DISABLE KEYS */;
INSERT INTO `active_rule_parameters` VALUES (1,18,110,'^([A-Z]{1,3}[a-z0-9]+)*([A-Z]{2})?$','format'),(2,18,111,'^([A-Z]{1,3}[a-z0-9]+)*([A-Z]{2})?s$','flagsAttributeFormat'),(3,21,103,'5','max'),(4,125,96,'2','max'),(5,145,108,'15','threshold'),(6,145,109,'3','propertyThreshold'),(7,152,107,'30','maximum'),(8,185,100,'7','max'),(9,239,164,'3','threshold'),(10,243,202,'^[a-z][a-zA-Z0-9]*$','format'),(11,370,217,'15','Threshold'),(12,371,213,'30','maximum'),(13,383,197,'^[A-Z][a-zA-Z0-9]*$','format'),(14,384,198,'^[a-z][a-zA-Z0-9]*$','format'),(15,401,218,'7','max'),(16,401,219,'7','constructorMax'),(17,408,228,'^[A-Z][a-zA-Z0-9]*$','format'),(18,434,229,'^[a-z][a-zA-Z0-9]*$','format'),(19,435,230,'^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$','format'),(20,436,226,'^[a-z][a-zA-Z0-9]*$','format'),(21,437,227,'^[A-Z][0-9]?$','format'),(22,439,224,'^[a-z]+(\\.[a-z][a-z0-9]*)*$','format'),(23,476,182,'5','max'),(24,487,61,'^[A-Z][a-zA-Z0-9]*$','format'),(25,489,71,'80','maximumClassComplexityThreshold'),(26,490,60,'^[a-z][a-zA-Z0-9]*$','format'),(27,492,58,'true','countNonpublicMethods'),(28,492,59,'20','maximumFunctionThreshold'),(29,493,78,'7','max'),(30,502,66,'3','max'),(31,508,67,'LOG(?:GER)?','format'),(32,516,65,'10','maximumFunctionComplexityThreshold'),(33,517,62,'5','max'),(34,519,75,'^[a-z]+(\\.[a-z][a-z0-9]*)*$','format'),(35,522,74,'3','max'),(36,537,70,'^[_a-z][a-zA-Z0-9]*$','format'),(37,538,68,'^[_a-z][a-zA-Z0-9]*$','format'),(38,539,69,'^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$','format'),(39,547,34,'200','max'),(40,572,51,'false','considerJSDoc'),(41,592,53,'180','maximumLineLength'),(42,611,43,'7','maximumFunctionParameters'),(43,616,52,'1000','maximum'),(44,619,50,'15','threshold'),(45,695,51,'false','considerJSDoc'),(46,729,138,'7','max'),(47,729,139,'7','constructorMax'),(48,732,125,'^[A-Z][a-zA-Z0-9]*$','format'),(49,736,114,'true','countNonpublicMethods'),(50,736,115,'20','maximumMethodThreshold'),(51,741,136,'3','max'),(52,746,131,'^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$','format'),(53,747,133,'^[A-Z][a-zA-Z0-9]*$','format'),(54,751,119,'3','max'),(55,752,129,'15','threshold'),(56,756,126,'3','threshold'),(57,772,113,'150','max'),(58,775,128,'30','max'),(59,785,131,'^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$','format'),(60,788,141,'120','maximumLineLength'),(61,790,142,'true','extends_implements_line'),(62,790,143,'true','no_space_method_name'),(63,790,144,'true','closure_format'),(64,790,145,'true','space_comma'),(65,790,146,'true','open_curly_brace_classes_functions'),(66,790,147,'true','namespace_blank_line'),(67,790,148,'true','open_curly_brace_control_structures'),(68,790,149,'true','one_space_after'),(69,790,150,'true','interfaces_indentation'),(70,790,151,'true','foreach_space'),(71,790,152,'true','no_space'),(72,790,153,'true','function_calls_arguments_indentation'),(73,790,154,'true','closing_curly_brace'),(74,790,155,'true','function_declaration_arguments_indentation'),(75,790,156,'true','use_blank_line'),(76,790,157,'true','one_space_for'),(77,790,158,'true','use_after_namespace'),(78,790,159,'true','one_space_before'),(79,791,124,'^[a-z][a-zA-Z0-9]*$','format'),(80,795,125,'^[A-Z][a-zA-Z]*$','format'),(81,804,131,'^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$','format'),(82,806,132,'^[a-z][a-zA-Z0-9]*$','format'),(83,808,141,'140','maximumLineLength'),(84,809,130,'^[a-z0-9_]*$','format'),(85,810,142,'false','extends_implements_line'),(86,810,143,'true','no_space_method_name'),(87,810,144,'false','closure_format'),(88,810,145,'true','space_comma'),(89,810,146,'false','open_curly_brace_classes_functions'),(90,810,147,'false','namespace_blank_line'),(91,810,148,'false','open_curly_brace_control_structures'),(92,810,149,'true','one_space_after'),(93,810,150,'false','interfaces_indentation'),(94,810,151,'true','foreach_space'),(95,810,152,'true','no_space'),(96,810,153,'false','function_calls_arguments_indentation'),(97,810,154,'false','closing_curly_brace'),(98,810,155,'false','function_declaration_arguments_indentation'),(99,810,156,'false','use_blank_line'),(100,810,157,'false','one_space_for'),(101,810,158,'false','use_after_namespace'),(102,810,159,'true','one_space_before'),(103,813,125,'^[A-Z][a-zA-Z0-9]*$','format'),(104,822,18,'7','max'),(105,827,10,'15','threshold'),(106,828,2,'^[a-z_][a-z0-9_]{2,}$','format'),(107,829,3,'^[A-Z_][a-zA-Z0-9]+$','format'),(108,833,11,'^[_a-z][a-z0-9_]*$','format'),(109,837,12,'^[_a-z][_a-z0-9]*$','format'),(110,838,15,'^[a-z_][a-z0-9_]{2,}$','format'),(112,920,233,'1000','Max'),(113,921,234,'180','maximumLineLength'),(114,221,778,'password,passwd,pwd','credentialWords'),(115,952,258,'3','deepnessThreshold'),(116,961,247,'1000','Max'),(117,964,248,'^[a-z][-a-z0-9]*$','Format'),(118,979,245,'120','maximumLineLength'),(119,984,249,'^[a-z][-a-z0-9]*$','Format'),(120,988,255,'500','Max'),(121,1000,246,'2','fontFaceThreshold'),(122,1011,256,'^[a-z][-a-z0-9]*$','Format'),(123,2419,764,'1000','maxLength'),(124,2422,276,'^[a-z][-a-z0-9]*$','Format'),(125,2430,267,'^[a-z][-a-z0-9]*$','Format'),(126,2435,262,'2','fontFaceThreshold'),(127,2438,268,'3','Max'),(128,2446,275,'^[a-z][-a-z0-9]*$','Format'),(129,2448,278,'3','deepnessThreshold'),(130,2451,264,'1000','Max'),(131,2466,261,'^[a-z][-a-z0-9]*$','Format'),(132,2473,259,'120','maximumLineLength'),(133,2478,265,'^[a-z][-a-z0-9]*$','Format'),(134,2479,273,'3','Max'),(135,2482,260,'3','Max'),(136,2484,274,'500','Max'),(137,2495,263,'^[a-z][-a-z0-9]*$','Format'),(138,2534,729,'2','fontFaceThreshold'),(139,2540,728,'^[a-z][-a-z0-9]*$','Format'),(140,2553,739,'^[a-z][-a-z0-9]*$','Format'),(141,2560,733,'3','Max'),(142,2567,738,'500','Max'),(143,2571,741,'3','deepnessThreshold'),(144,2589,727,'120','maximumLineLength'),(145,2598,731,'^[a-z][-a-z0-9]*$','Format'),(146,2600,730,'1000','Max'),(147,2612,353,'15','Threshold');
/*!40000 ALTER TABLE `active_rule_parameters` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:18
