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
-- Table structure for table `rule_repositories`
--

DROP TABLE IF EXISTS `rule_repositories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule_repositories` (
  `kee` varchar(200) COLLATE utf8_bin NOT NULL,
  `language` varchar(20) COLLATE utf8_bin NOT NULL,
  `name` varchar(4000) COLLATE utf8_bin NOT NULL,
  `created_at` bigint(20) NOT NULL,
  PRIMARY KEY (`kee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rule_repositories`
--

LOCK TABLES `rule_repositories` WRITE;
/*!40000 ALTER TABLE `rule_repositories` DISABLE KEYS */;
INSERT INTO `rule_repositories` VALUES ('Pylint','py','Pylint',1515745696771),('Sonargraph','java','Sonargraph Rules',1515745696771),('Web','web','SonarAnalyzer',1515745696771),('checkstyle','java','Checkstyle',1515745696771),('common-css','css','Common CSS',1515745696771),('common-flex','flex','Common Flex',1515745696771),('common-java','java','Common Java',1515745696771),('common-js','js','Common JavaScript',1515745696771),('common-jsp','jsp','Common JSP',1515745696771),('common-less','less','Common Less',1515745696771),('common-php','php','Common PHP',1515745696771),('common-py','py','Common Python',1515745696771),('common-scss','scss','Common SCSS',1515745696771),('common-ts','ts','Common TypeScript',1515745696771),('common-web','web','Common Web',1515745696771),('common-xml','xml','Common XML',1515745696771),('css','css','SonarQube',1515745696771),('fb-contrib','java','FindBugs Contrib',1515745696771),('findbugs','java','FindBugs',1515745696771),('findsecbugs','java','Find Security Bugs',1515745696771),('findsecbugs-jsp','jsp','Find Security Bugs (JSP)',1515745696771),('flex','flex','SonarQube',1515745696771),('javascript','js','SonarAnalyzer',1515745696771),('jdepend','java','jDepend',1515745696771),('less','less','SonarQube',1515745696771),('php','php','SonarAnalyzer',1515745696771),('pmd','java','PMD',1515745696771),('pmd-unit-tests','java','PMD Unit Tests',1515745696771),('python','py','SonarAnalyzer',1515745696771),('qualinsight-smells','java','Smells',1515745696771),('scss','scss','SonarQube',1515745696771),('sonargraphintegration','java','Sonargraph Integration Rules',1515745696771),('squid','java','SonarAnalyzer',1515745696771),('typescript','ts','SonarAnalyzer',1515745696771),('xml','xml','SonarAnalyzer',1515745696771);
/*!40000 ALTER TABLE `rule_repositories` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:31
