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
-- Table structure for table `txn_receipt`
--

DROP TABLE IF EXISTS `txn_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txn_receipt` (
  `location_id` int(4) NOT NULL,
  `business_date` datetime NOT NULL,
  `register` int(3) NOT NULL,
  `txn_no` int(5) NOT NULL,
  `receipt_type` varchar(20) NOT NULL,
  `receipt_data` longblob NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`location_id`,`business_date`,`register`,`txn_no`),
  CONSTRAINT `fk_txn_receipt_txn_master1` FOREIGN KEY (`location_id`, `business_date`, `register`, `txn_no`) REFERENCES `txn_master` (`location_id`, `business_date`, `register`, `txn_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `txn_receipt`
--

LOCK TABLES `txn_receipt` WRITE;
/*!40000 ALTER TABLE `txn_receipt` DISABLE KEYS */;
INSERT INTO `txn_receipt` VALUES (7997,'2018-07-04 00:00:00',3,3,'Sale Store Copy','%PDF-1.5\n%\�\�\�\�\n4 0 obj\n<</Filter/FlateDecode/Length 1021>>stream\nx�͘\�o\�6�\��W0h����!�~Zg]�,H+\�e\�TYI�\�Ó\�\�_ߣ$g�QI\�(˃x�\��\�O\�#)�p\��>�\��\�;�<\0\�D+\�2�\�x�\�V�\�N;*�\�~���\�yϞG�V��@	�*DK�\�$18�\�=\n<`Px҈Vʟ�\�1i��N\�J�c��:�n\�&=+\�\��g\�\�y������\�i\�s�qN|\�(\"T\�(_XGi��\�<S\�(8��*��NR\�dMj�@\�\�F\��zϴ�\�JC$\�@s�\�����\�q�v\�m\\>\�\�+�*��\��o�2]�\�BI\��^�eg�\�:.�f�-�����\�|�ѩ�\n�\�7`\��:�\r�6�\��U8\"1ݹЄ\�]b@a\�5ʁ\�\�\�2ח�_�5\09\��n*W_�i\�5Dy�\�g�&����3\�M�q\��z1\�Y\�\�D�i<�������3*\"J�ΐ\n#�t�\�4VHX�\r�\�N�C�\�\�\��\'}U\�t�\\�y\�\��(\����{t�?I�c�\�r�w:(�\�\�\�jt\��3\�Yy?\�?�\�w���$v�\��K�c\�%�㌗E�-絝�$t��\�	O�\�&��>�\�\r�\�:K\\��?\nk\�F��q\�\�&-f�T߈Q\�/e\�\�66�o��\�+\�\�ȶ⻯˸}j�\�\�+���J\�J�r\��\�Y��U��\�\�$��jl������\�k㠝��gH�)ǈ�l\�\�æ\0s���ܘ�a�(�I��\�\�]�\r߾{\�\�\���Y\�\�JN��<~E�F���\�o_��\0ٷ\'��{ᑝ��ɾ`��M��+`7�\�\�\�Rx�\'>\�$e�L{\�`i{}c���!i���{\\�\�(\�9�\�o%{�`4�wB\�\���\�\�˶B��)�\�ݚ\�\�V\�S�)}\"8(u\�\�\�S����+�}�\"�q��E���t\�\�\�\�E\�WՇ�\�\�s,6͆0\��}S5q]\�g\"�Oڄ\"\�:\����Hi�o��F�ِx�\�c���Cڃ\�,0�rv11\�\n�\�e[nSg��\�<5x��\��\�NWp�aSYo\�i���\\����`g��2w\�\�9 F.o\�lp\�\�w\nl8G*\��/�jnM\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180705235324+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180705235324+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001104 00000 n \n0000001399 00000 n \n0000001487 00000 n \n0000000015 00000 n \n0000001580 00000 n \n0000001364 00000 n \n0000001643 00000 n \n0000001697 00000 n \n0000001729 00000 n \n0000001832 00000 n \ntrailer\n<</Info 10 0 R/ID [<2c8a2e33a51c0bf9913c40936bbc9336><c4057817cf3fe5eb0725350dca6ed2b5>]/Root 9 0 R/Size 11>>\nstartxref\n2000\n%%EOF\n','admin','2018-07-05 23:53:26',NULL,NULL),(7997,'2018-07-04 00:00:00',3,4,'Sale Store Copy','%PDF-1.5\n%\�\�\�\�\n4 0 obj\n<</Filter/FlateDecode/Length 1022>>stream\nx�͘\�o\�6�\��W0h�F\�D�iM�uͲ ����{Pe%Q�O���}���yFE��,\�\�>ߏw<�$@\��>\�\��\�;�=\0W\�+\�2�\�x�̧F��N;.�\�~�@	\�w޳\��\�%~\n�*��J�I_\� �����A\�I-:�x�Ǵ�FН��\�\��\Z���LV1��\�Ի�~��Y\�\�9�\� \�ܧ\Z�}��2�\�8J:t\�Y�ug�\�e�5i��\�LܛHX�&��Ծ\�(\�?aտ�?=$MҼ�\�x�T�\�{\�LB�\�C2\n�}��lJ�P2�\��,��X@\�$eޮ�\�\���\�C\�8��:�WQ��f��_G��V�\\\�_�#ӝ\�s�K\�(,���+�\��\�ʈ^^_:b�\�(\�\�</\n��]}a��\�\�mv�oڬ�fΔ7�ǵ\�\�-���L�+�\�\�ؾ��PuF\�3\"bB�̐\n�E�\�n\�\Z+$,��\�f\'ޡdU\�\�\�˓��]:\�\"�l�9�b\�wq��]��\'\�p\��]\�\�N�ñ\�[;�~\���\��\�Q��Pf\"\��]\�\�!�\�\�uɤ�񲬷U뼶�D\�p5\�\�2ߤ3�§\"v���\�\�S\�\�E\'@Q\�\�\�pn\'�n�r�J\�����~��v����Dx\�~t_�-\�\nu_�}b�\�\�+���\�\�N\�j\�\�\�y�Du��\�\�$��*l���������q\�\��\�3$��c��l\�\�æ\0s���ܘ�a\n���|I��\�\�]�\r߾{\�\�\���Y�\�JN��<~E�F��\�\�o_\�#�\09�\'�Xxё���ɾ`�k�M��h#`72_�F�a�\�{��^\nMӃ�\�\�\Z� �⠯58��Q\n����e<���d.��F�^\�8�>��y�V\�?\�h�[���\�\nu\� %��0<ia(��\���d��H~ܫ\�F���~�������0Z)\�`\�lm߷u�з\�p&b��I�1⮳��H|)M�\�\��8�0O��x�_5�HsP���aB\�a�����%/{\�r�9�(�\�\�|]e�VM����\�f�β\r$\�\n>eM\r\�$\�dPW�kΉ\0a��m�\�I�;s(\�\�pZ*\��/��N\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180706192148+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180706192148+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001105 00000 n \n0000001400 00000 n \n0000001488 00000 n \n0000000015 00000 n \n0000001581 00000 n \n0000001365 00000 n \n0000001644 00000 n \n0000001698 00000 n \n0000001730 00000 n \n0000001833 00000 n \ntrailer\n<</Info 10 0 R/ID [<1efab34bb300b48e79ab7745345d66e3><13a6d535266aaa0caab3fe9273ca9ff5>]/Root 9 0 R/Size 11>>\nstartxref\n2001\n%%EOF\n','admin','2018-07-06 19:21:50',NULL,NULL);
/*!40000 ALTER TABLE `txn_receipt` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 19:32:22
