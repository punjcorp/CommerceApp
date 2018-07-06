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
INSERT INTO `txn_receipt` VALUES (7997,'2018-07-04 00:00:00',3,3,'Sale Store Copy','%PDF-1.5\n%\â\ã\Ï\Ó\n4 0 obj\n<</Filter/FlateDecode/Length 1021>>stream\nxœÍ˜\İo\Û6À\ßõW0h†£ø!’~Zg]³,H+\íe\ëTYI”\éÃ“\å­\í_ß£$gQI\î(Ëƒx±\Ï÷\ãO\Ç#)øp\æÁ>“\Âû\Ó;<\0\×D+\ï2ò\Şx®\ì§V\ÂN;*¼\ï~ğÁ§\İyÏGV—’@	‰*DK­\Ñ$18¨\ï=\n<`PxÒˆVÊŸ¤\Ã1i¿şN\ÇJ‡c‚ö:n\Ü&=+\ï\Çıg\â\İy¿ıÿ¬ö\Üi\ßsqN|\Ü(\"T\ç(_XGi«‚\î<S\Æ(8ƒ¤*Š´NR\ØdMj£@\á\ŞF\âÀzÏ´‘\áJC$\Æ@sò„\Õÿ¦şô\×qıv\ãm\\>\Æ\ï‰+˜*‚Á\áŠ‚oª2]€\ÖBI\Æ¥^Àegù\Ò:.²f½-¿¿·Ÿ‰\ã|‚Ñ©¼\n£\×7`\ìü:¼\r6ô\ÊÿU8\"1İ¹Ğ„\Ë]b@a\Ü5Ê\Î\Å\éÂŒ2Â—×—_¶5\09\Ïòn*W_˜i\ã5Dy›\Şg›&­¿™ƒ3\åMôq\íúz1\ÎY\Æ\ÎD´i<¾üŒúúŒª3*\"J”Î\n#‹t±\İ4VHX¦\r‡\ÍN¼Cñª\È\Ê\Ê—\'}U\Ût‹\\¼y\È\Òú(\Êø¾‹{t¿ï¢”?I‡c·\ïr¦w:(\í\Ş\Újt\ãş3\éYy?\î?\Úw…¶‘$v•\ß„K¿c\Ô%“ãŒ—Eµ-çµğ$t÷„\ë	O–\Ù&™Á>±\Ë\r\Û:K\\«—?\nk\×F‡óq\Â\ë&-fªTßˆQ\Ö/e\Ö\Ì66•oš\î+\Ã\ÌÈ¶â»¯Ë¸}jÿ\ì\Ï+°¬şJ\á¢Jªr\ÛÀ\ÏYş„U¼†\ë\İ$ö­jl©µú±\Ãkã ıgH‚)Çˆól\à´\ËÃ¦\0s¸Á§Ü˜aó(ƒIó¨ı\Ñ\é]«\rß¾{\î\Ü\Õü—Y\é\ÏJN¯º<~EşF¥«ş\Ço_í¾£€\0Ù·\'­˜{á‘µÉ¾`“ŠM®µ+`7£\Ñ\Ê\ÊRx¼\'>\ï$e›L{\×`i{}cÀšÀ!i¢{\\ö\ç(\ã9½\×o%{¹`4šwB\Ë\éõ‘\İ\ÏË¶Bÿû)†\Óİš\Ì\ŞV\èS—)}\"8(u\Ò\Â\ĞS‚‘ÿ+’}‚\"ùq¯ºEø§¥t\ë\Ï\Ì\àE\ÍWÕ‡Á\Ú\ès,6Í†0\áö}S5q]\ëg\"†OÚ„\"\î:\ëùˆ”Hi“oˆµFñ‡Ùxò´\Åcøª±CÚƒ\Ê,0ôrv11\Ø\n\äe[nSg–\Ç<5x‚¯\Ê¶\åªNWp—aSYo\×iº¸\\Á§´®`g›ª2w\í\Ï9 F.o\ë¬lp\ç\Îw\nl8G*\Ëñ·/ŸjnM\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180705235324+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180705235324+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001104 00000 n \n0000001399 00000 n \n0000001487 00000 n \n0000000015 00000 n \n0000001580 00000 n \n0000001364 00000 n \n0000001643 00000 n \n0000001697 00000 n \n0000001729 00000 n \n0000001832 00000 n \ntrailer\n<</Info 10 0 R/ID [<2c8a2e33a51c0bf9913c40936bbc9336><c4057817cf3fe5eb0725350dca6ed2b5>]/Root 9 0 R/Size 11>>\nstartxref\n2000\n%%EOF\n','admin','2018-07-05 23:53:26',NULL,NULL),(7997,'2018-07-04 00:00:00',3,4,'Sale Store Copy','%PDF-1.5\n%\â\ã\Ï\Ó\n4 0 obj\n<</Filter/FlateDecode/Length 1022>>stream\nxœÍ˜\İo\Û6À\ßõW0hF\ã§DúiMœuÍ² ­´—­{Pe%Q¦O–·¶}’œyFE¹£,\â\Å>ßw<$@\áŒÁ>\Ó\Òû\Ó;=\0W\Ä+\ï2ö\Şx®Ì§FÀN;.½\ï~ @	\ÄwŞ³\çñ£\Ñ%~\n‰*¾’J¡I_\ã  ¹÷ğ€A\éI-:©x’Ç´ûFĞ‘\Ç\íõ\Zı¸ÿLV1Œû\ÏÔ»ó~ûÿY\í¹\Ó9¾\ç \ãÜ§\Z¸}ö2 \Â8J:t\çY¨ug\Öe™5i›¼\ÍLÜ›HX˜&’®Ô¾\Ä(\î?aÕ¿©?=$MÒ¼€\İx›T\É{\ßLBƒ\ÃC2\n¾}¨«lJ‰P2„\á¸,“¼X@\Ö$eŞ®·\Õ\ã÷÷\æC\â8ŸÀ:•WQüúfŒ_G·‘Vš\\\Ñ_…#Ó\ås¹K\ì(,ÀÁœ+ƒ\ÏÁ\ÚÊˆ^^_:b¨\ì(\á\ä</\n¸©]}aº‹\×\åmvŸoÚ¬ùfÎ”7ñÇµ\ë\ë-„³Lœ+ˆ\è\ÒØ¾üŒPuF\Â3\"bB„Ì\n–Eº\Øn\Ú\Z+$,³‹\Ãf\'Ş¡dU\æ\Õ\å€Ë“¾ª]:\Ø\"—lò¬9Šb\ßwqö]”Š\'\ép\ì÷]\Î\ÔN¥Ã±\Û[;~\Ü¦«\Æı\çQû®Pf\"\Ò‚]\å\×!Á\Ò\ïuÉ¤ñ²¬·Uë¼¶D\îp5\á\É2ß¤3øÂ§\"vù¡õ\á¶\ÉS\×\êE\'@Q\ã\Ú\èpn\'¼n³r¦J\Åğ°²~©òv–°±©Dx\Ó~t_¦-\Û\nu_»}bş\Ì\Ï+°¬ÿ\Ê\à¢N\ëj\Û\Â\ÏyñDu²†\ë\İ$ö­*l©µ±ú±£Šµq\Ô\Îş\Ç3$Á”c¾ól\à´\ËÃ¦\0s¸Á§Ü˜a\n³•Á|Ió¨û\Ñ\é]§\rß¾{\î\Ü\Õü—Y©\ÏJN¯º<~EşF¥«\á\Ço_\í¾#€\09´\'XxÑ‘±É¾`“k‚M®±h#`72_…F–a€\Ç{Ÿò^\nMÓƒ‚\î\î\ZŒ â ¯58¤Q\n«Àüe<§úd.Œ€F‹^\è8ƒ>²‡y™V\è?\Åhº[“‚™\Û\nu\ê² %õ‡0<ia(¥\çÿŠdŸ H~Ü«\îFô´”~ı™½¨ùªú0Z)\Ç`\Òlmß·u›Ğ·\î°p&bø¤I¸1â®³H|)Mò\ã\ÎÁ8ù0O¦xŒ_5öHsP™†şaB\Úa³Àƒ­°%/{\Ør›9³(ó\Â\Ñ|]eğ°­VM¶‚»›\Êf»Î²\r$\Õ\n>eM\r\ë$\ßdPW…kÎ‰\0a¹¼mòª\ÅIœ;s(\î\ØpZ*\Ëñ·/Ÿ¾N\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180706192148+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180706192148+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001105 00000 n \n0000001400 00000 n \n0000001488 00000 n \n0000000015 00000 n \n0000001581 00000 n \n0000001365 00000 n \n0000001644 00000 n \n0000001698 00000 n \n0000001730 00000 n \n0000001833 00000 n \ntrailer\n<</Info 10 0 R/ID [<1efab34bb300b48e79ab7745345d66e3><13a6d535266aaa0caab3fe9273ca9ff5>]/Root 9 0 R/Size 11>>\nstartxref\n2001\n%%EOF\n','admin','2018-07-06 19:21:50',NULL,NULL);
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
