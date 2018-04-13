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
INSERT INTO `txn_receipt` VALUES (7997,'2018-03-26 00:00:00',1,3,'Sale Store Copy','%PDF-1.5\n%\�\�\�\�\n4 0 obj\n<</Filter/FlateDecode/Length 1006>>stream\nx�\�XK�\�6�\�WP\�\0�J�!�>5o��E���SӃ\"3�z���d�}���p��rC���=�o^I �E\08��L\�o\�6�XL�o��\�{\�Qxc~5r�\�q\���k\0���w�<~0�\�#.PėBJT\�+$\�G��\nO(\�q�wN\�\�ewNS\�\�K����X�@O���\���/��9q�s�\�Aʘ(`*�y\�;J!\�\�Q҉�;7�R, ��Bש�&k�����ę�\�D҂+�/0��O��ߨ�\�:�_��\�\'\�C�\�w&���a�\�U�^��<��Q�\�$˗�\�\�\���|�ek~�1$��`\�1.}&�Uvf,���J`�[1�90��b�_�q\��%\Z�\��\�V��P\�\�k\�\�fM�\�\����&~ܻ�5\�\�8�\�y9�\�\�$�\�4�	Y2C)X��\�д�+X\�Wj3��k(\�Y9kc`⪋�+[�f�\��\"�v�[\�\"�?q\�\��G\�\�i�\�u==}�V>\�\�\�E\�!�\�\�\�qoPg!y���.��c�,�C\�:\�v��\'LNx�ʚt_\�T\�>\�\�Y\�\�ǂ	�u\�:0fG��\�\�L=�⊰b}(�v��ѩBx\�>�g�*\�\�\�1/v�\�|	\�Q�\�\�ۤx\nܩ\Z���2j�o�t�\�پͪr�<[m\�\�w\�nM�� �S�Y\���\�`Snw\�z\�H_��\�\�\��N\�|�	8\�\�>>w�a~\�.ym�\�d\�#\�\�K���\�/�}��\0�a\�\�\�[_8���;:�\"8\�\Z��2\�,\�\�\��\"\n�l\��\�\"3\� ����a�\��I\�)E&��\�\�\�\�!y�\�8s�7*\�{�\�\�{�\�<�{\�\�3�\�\�\\\�k�!�3���6�C�,-�=�.��	�.\\\�n0<�6LWT�\�\���1\� �]�T\�\���\�$�~F��3\"֜057�x�\�C$���\�\�\��8�6$1M��\�\�!͉d0�+\��j0���\�R�=\�ꠝ�<\�E�G��԰;��Zo�\�_t\�>.گԇ�\�\r$\�t�ݵ��O�FCU\�9#�\��\�\���uš���!SK�����\0\��\�\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180327195937+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180327195937+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001089 00000 n \n0000001384 00000 n \n0000001472 00000 n \n0000000015 00000 n \n0000001565 00000 n \n0000001349 00000 n \n0000001628 00000 n \n0000001682 00000 n \n0000001714 00000 n \n0000001817 00000 n \ntrailer\n<</Info 10 0 R/ID [<c0125ac01f30b90c1db71dd674c9b6c0><c64eabbdd5d69116ee0abae62f4e761e>]/Root 9 0 R/Size 11>>\nstartxref\n1985\n%%EOF\n','admin@gmail.com','2018-03-27 19:59:39',NULL,NULL),(7997,'2018-03-26 00:00:00',1,5,'Sale Store Copy','%PDF-1.5\n%\�\�\�\�\n4 0 obj\n<</Filter/FlateDecode/Length 986>>stream\nx�\�X\�o\�6\�_q�0 j�\�H�iM�\�\"���u��$\n�\�\�r\���;Jr\�\�2�<�.�\�~�\�E!�Ec��e�wp<�#�7�M���O���t\\?�BH!��^ďV��H	�\"DK�Q%1H4�1(iD\�\�\�)M�_Dx��\�)MQ_/\�\�\�5����ip����92�3�\�@\�9	\rp��P��Ba\r���s��Q���.ˬI3\�\�mf�@\�\�z\�D��i=\����H�\�\�V�����I��p��I��|\"��TtWt�����%h-�d<R\�%ܔI^,!k�2o��\��\�{���K<���ǅ&\\�\�d3�\�kr��s\�\�\��Ɖ�~���\'L(;5r���}ma��\�ʇ\�>ߵY�\�8S\�\�O[߳&�g�xgѥ�;���zA��E1�KJgHG�^\�wm�\�\nVY�\'u7��g(ٔy5ka\�򢇶K��\�C�5g��\�!�Ρ\"W<s��o��\�r��ky�DO�\�t�*z��\����$�����\�\�:���dҍ��U\�\�	K\���p=a\�*ߥ3\�§<v�%p\�\�o\'�֍\����\�\�6+g�YO�\�*ogq�J��\�d�q4\�Z�qq\��OҐ2��(�K\�g\��\�8\�\Z�\���*ۥM�m�!\�νsJ�ۭ\r�e�&\0氂OY1���NF=͛$\�\�+�;�?��`�gW�»��A�D��P\�\��\�7�\�(�~9�[\�3\'��}E\'7\�Y�32��iE1��奊�VMB\�s\�5Ș\�oiyc��@�vJ�)�#�:�x=\�;\�\�\�-�J��\�py\��eG���\�\�Ә\�􅋂�!��hYP\"\�H�\r�>�\"�y\�E��E\�\�\�\�藚o���1\�X\0l������ںM\n\�sXz#���ͷ1\�\�\�<\"%R\�\�C�;\�\�\�l�x���c��^i�!���}��n�׳�!���\��V�\�+\�K�\Z���y\�>-\��\�4�m�\� �6pW\�S\�&�.��*|\'oN\�\�\�&�\�l׾8\�p�\�9�\��|a�QA\�A\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180412103837+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180412103837+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001068 00000 n \n0000001363 00000 n \n0000001451 00000 n \n0000000015 00000 n \n0000001544 00000 n \n0000001328 00000 n \n0000001607 00000 n \n0000001661 00000 n \n0000001693 00000 n \n0000001796 00000 n \ntrailer\n<</Info 10 0 R/ID [<59617625135f2987fa9f1eb4a478dc2b><d5afb395321cc0068f0238f19a80ff37>]/Root 9 0 R/Size 11>>\nstartxref\n1964\n%%EOF\n','admin@gmail.com','2018-04-12 10:38:38',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,'Sale Store Copy','%PDF-1.5\n%\�\�\�\�\n4 0 obj\n<</Filter/FlateDecode/Length 1092>>stream\nx�\�Y\�o\�6~\�_q�0 \Z�?E2Ok��X7i�=-{Pe5Q ə$�\���,g�SYI˃x�O�\��ߑ4�(H\��Y4ɟ\�y���I![&�Y�1\��\�}\��(l��&�\�\'�B�%9y�\�9]JR-���A�\�\� ��I(��C�(+G�~��\�b�F�����\�\�m46\��\�i\�}ɗ\��?�\�N8c\�;r!� �&Ro\�����*Ή�V\�)��)����\ZJ�\n7.{\�\'L�I��Da� O�\�ߨ�\�\�]޽�\�x��w�g\nL5�\�M_ݮ\����Zq�j�\Z.���Ϡ\��\Z\�\�\�ݏ7\��)	�kOHC�\�Vٞ3pihȩ!��\���֋�x�\�e S#�>\0r^\�5|X�\�\�혯C(�ʛ�\�\�8s\�d���kMJ?\�E��\�X\��\�甙S*Ny�QzFi�R�L\�\�u?���\�p��1�YC���ڨ\� \�Q\�X�\��mUv/B�Cl�S;D�~��\�M;\�luP\�ǖ7jl\�\�g1a\�Ӹ�|Q;�\�9�H�n{�\�KɻE�uŕ\�M�Z�C�\�\�D��D��H.�����\�\�\�\�@ફ�Pc3@�.t�!�\�\�l\"q\�\�\�����(i\�s��qx�n=\r&�q^����S�Q�[\�q�>\�\�S\�v\�\�\�Zg\���E\�]u?T�6\�<{}���[7\�^a�{,\r�B\�E\�1���8I\�~\�o=S9\�\'L�O����\�-\�7�e�헚�x�\�\�#l�fG,_\���9�H��?���\�终�*F���\�\��ё�\�E$��ahbĳ��L\�\\�؈\��2G�J\�M:.ܕ�\�+*��^��n�$\'5�_F�N/<�8���\�R<;��uw4\'F;Y\�� Ll$\�NE(\��\�	\�)N�ւ3�C1\ZE�N�I\�\�(#&�Qr��N@��Fq&}Ğ�rg������㜒\�\�2\Z_s1\�M)F�\0��JJ�XY�z.$\�`\�u\����YR7���\�(0c	p{�3�4\�����C0��\�a5\�5l��p��5�\\\�Bܞ�\�!R����C�\�`�?D��8�8�+�\�]fD\���\"�`o��!�=u��X��XL�\�o���+�]�ˮ\\B_�U�CY�Э\�˲��]BY\�\���}�\0�y՗�j\�\�㼠�\�\'��B7�p�í ؒ��e�\�\��o\�F\�\�\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180412104322+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180412104322+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001175 00000 n \n0000001470 00000 n \n0000001558 00000 n \n0000000015 00000 n \n0000001651 00000 n \n0000001435 00000 n \n0000001714 00000 n \n0000001768 00000 n \n0000001800 00000 n \n0000001903 00000 n \ntrailer\n<</Info 10 0 R/ID [<de4e029061a2c400052048a1321afa40><ce31f3607c422d6c6b0c4b9c01793f08>]/Root 9 0 R/Size 11>>\nstartxref\n2071\n%%EOF\n','admin@gmail.com','2018-04-12 10:43:22',NULL,NULL);
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

-- Dump completed on 2018-04-13 13:45:09
