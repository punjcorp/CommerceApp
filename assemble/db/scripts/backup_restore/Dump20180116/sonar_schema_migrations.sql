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
-- Table structure for table `schema_migrations`
--

DROP TABLE IF EXISTS `schema_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_migrations` (
  `version` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_migrations`
--

LOCK TABLES `schema_migrations` WRITE;
/*!40000 ALTER TABLE `schema_migrations` DISABLE KEYS */;
INSERT INTO `schema_migrations` VALUES ('1'),('2'),('1153'),('1200'),('1205'),('1207'),('1208'),('1209'),('1210'),('1211'),('1212'),('1213'),('1214'),('1215'),('1216'),('1217'),('1218'),('1219'),('1220'),('1221'),('1222'),('1223'),('1224'),('1225'),('1226'),('1227'),('1228'),('1229'),('1230'),('1231'),('1232'),('1233'),('1234'),('1235'),('1236'),('1237'),('1238'),('1239'),('1240'),('1241'),('1242'),('1243'),('1244'),('1246'),('1247'),('1248'),('1249'),('1250'),('1251'),('1252'),('1253'),('1254'),('1256'),('1257'),('1258'),('1259'),('1260'),('1261'),('1262'),('1263'),('1264'),('1265'),('1266'),('1267'),('1268'),('1269'),('1270'),('1271'),('1272'),('1273'),('1274'),('1275'),('1276'),('1277'),('1300'),('1301'),('1302'),('1303'),('1304'),('1307'),('1309'),('1310'),('1311'),('1312'),('1313'),('1314'),('1315'),('1316'),('1317'),('1318'),('1319'),('1400'),('1401'),('1402'),('1403'),('1404'),('1405'),('1406'),('1407'),('1408'),('1409'),('1410'),('1411'),('1412'),('1413'),('1414'),('1415'),('1416'),('1417'),('1418'),('1419'),('1420'),('1421'),('1422'),('1423'),('1500'),('1501'),('1502'),('1503'),('1504'),('1505'),('1506'),('1507'),('1508'),('1509'),('1510'),('1511'),('1512'),('1513'),('1514'),('1515'),('1516'),('1517'),('1518'),('1600'),('1601'),('1602'),('1603'),('1604'),('1605'),('1606'),('1607'),('1608'),('1609'),('1610'),('1611'),('1612'),('1613'),('1614'),('1615'),('1616'),('1617'),('1618'),('1619'),('1620'),('1621'),('1622'),('1623'),('1624'),('1625'),('1626'),('1627'),('1628'),('1629'),('1630'),('1631'),('1632'),('1633'),('1634'),('1635'),('1636'),('1637'),('1638'),('1639'),('1640'),('1641'),('1642'),('1643'),('1644'),('1700'),('1701'),('1702'),('1703'),('1704'),('1705'),('1706'),('1707'),('1708'),('1709'),('1710'),('1711'),('1712'),('1713'),('1714'),('1715'),('1716'),('1717'),('1718'),('1719'),('1720'),('1721'),('1722'),('1723'),('1724'),('1725'),('1726'),('1727'),('1728'),('1729'),('1730'),('1731'),('1732'),('1733'),('1734'),('1735'),('1801'),('1802'),('1803'),('1804'),('1805'),('1806'),('1807'),('1808'),('1809'),('1810'),('1811'),('1812'),('1814'),('1830'),('1831'),('1832'),('1833'),('1834'),('1835'),('1836'),('1837');
/*!40000 ALTER TABLE `schema_migrations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-16 18:57:15
