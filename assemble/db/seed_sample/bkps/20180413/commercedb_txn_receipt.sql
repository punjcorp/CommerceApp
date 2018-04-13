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
INSERT INTO `txn_receipt` VALUES (7997,'2018-03-26 00:00:00',1,3,'Sale Store Copy','%PDF-1.5\n%\‚\„\œ\”\n4 0 obj\n<</Filter/FlateDecode/Length 1006>>stream\nxú\ÕXKè\€6æ\ÎWP\ÿ\0±JÒ!ë>5oÉ¶E∞âïS”É\"3∂z∏íúdˇ}áíºpçàrC®ö±=öo^I ÄE\08•¯L\Ôo\Ô6ˆXLÜoºª\ÿ{\ÁQxc~5ré\“q\·˝¸k\0Å¯≥wÛ<~0≤\ƒ#.PƒóBJT\È+$\Í≠GÄÖ\nO(\ﬁq˘wN\”\ÓewNS\‘\◊KÙÙÙôX˘@Oü©˜\Ÿ˚Û/¸≤9qßs¸\ƒA ò(`*Úy\‘;J!\‡\∆Q“â†;7ëR, ≠äB◊©Ü&kµâÅ≠âƒôˆ\”D“Ç+î/0í˘O∞Úﬂ®ø\Ôí:©_¿ë\ﬁ\'\ÂCÚ\…w&ëè¡aæ\ﬂU•^Çî<îÖQÙ\Óä$Àó†\Î§\»\⁄˝°|¯ek~Ò1$éˆ`\Ì1.}&éUvf,Åπ∫J`å[1Ç90∏≤b¨_˛q\Áà%\Zπ\ÕÚ\ﬁVÆæP\’\≈k\ÂΩ\ﬁfM´\Îü\Ê¿ôÚ&~‹ªÆ5\Œ\Ì8´\ƒy9ÛÆå\Ì\Èß$ê\¬4å	Y2C)XíÙ\Í–¥∂+X\ÈWj3áÉk(\ŸY9kc`‚™ã∂+[ìfó\È˙\"˚và[\Á∞\"ó?q\Á¥\ﬂïG\‰\Œi∑\Âu==}¶V>\–\”\ÁE\€!ó\∆\·á\·qoPg!yΩé£.®∞cº,™C\Ÿ:\Áv¬ìµª\'LNx≤ öt_\ÿT\ƒÓæµ>\‹\◊Y\Í\⁄«Ç	†u\Ì:0fG¯≠\’\≈L=ã‚ä∞b}(≥vñ∞—©Bx\◊>∫gÜ*\À\„\⁄1/v˝\ƒ|	\√Q∏\À\“€§x\n‹©\Zâ£≠2j¨oØtì\÷ŸæÕ™rÜ<[m\Á\¬w\ﬁnMä≠ ÆS™Y\‰ˆ∞\‹`Snw\”z\ÌûH_ëÛ\ \È\ﬁ˙N\È|º	8\⁄\œ>>wûa~\».ymª\ƒd\Ê#\Í\ÀK≥ÚÖ\ﬁ/ø}¸è\0àa\È\ÿ\‹[_8øùÙ;:ô\"8\‘\Zù°2\Œ,\∆\–\»\"\nÒl\Ì¨\Á\"3\⁄ £∫ÉæaÑ\‰ï£I\⁄)E&˜ò\Õ\Î\»\„!yê\Ô8s≤7*\Õ{¶\√\‰{∞\À<ˇ{\◊\”3ô\‡\‘\\\»k∑!ü3à¢´6¡C¨,-ì=ã.Øˆ	¡.\\\Ìn0<∏6LWTç\ﬁ\Ÿ¸ß1\⁄ Ü]¿T\⁄\Ã˙©≠\⁄$á~Fá•3\"÷ú057Üx°\ÁC$æ¶˛\∆\„\Œ¡8˘6$1Mˇø\Í\Î!Õâd0Ù+\“ˆj0Ñ¡ô\◊Ró=\ÿÍ†ù±<\œE£Gı™‘∞;îõZo†\—_t\Ÿ>.⁄Ø‘áΩ\÷\r$\Ât∂›µè∞O≤FCU\ÊÆ9#∏\Âµ\Œ\ ≠πu≈°ä˘∏!SKè˘ëõó\0\√˙\ÏÑ\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180327195937+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180327195937+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001089 00000 n \n0000001384 00000 n \n0000001472 00000 n \n0000000015 00000 n \n0000001565 00000 n \n0000001349 00000 n \n0000001628 00000 n \n0000001682 00000 n \n0000001714 00000 n \n0000001817 00000 n \ntrailer\n<</Info 10 0 R/ID [<c0125ac01f30b90c1db71dd674c9b6c0><c64eabbdd5d69116ee0abae62f4e761e>]/Root 9 0 R/Size 11>>\nstartxref\n1985\n%%EOF\n','admin@gmail.com','2018-03-27 19:59:39',NULL,NULL),(7997,'2018-03-26 00:00:00',1,5,'Sale Store Copy','%PDF-1.5\n%\‚\„\œ\”\n4 0 obj\n<</Filter/FlateDecode/Length 986>>stream\nxú\ÕX\›o\€6\◊_q¿0 jé\‚áH˙iMù\ÎÜ\"≠µßu™¨$\nÙ\·\…r\◊¸˜;Jr\‡\Âñ2∞<.Ú\È~º\ﬁE!ÑEc∏¶ewp<Æ#à7¡MººµO≠ÖÉt\\?˝BH!æÆ^ƒèVñíH	â\"DK≠Q%1H4˜1(iD\«\œ\‹)Mª_Dxê±\‹)MQ_/\—\”\„5∞äÅØip¸˘˛≥92ß3¸\»@\∆9	\rp£àPΩ°Ba\r•ùös•åQ∞Ä¥.À¨I3\ÿ\ÂmfΩ@\·\ﬁz\‚D˚Äi=\È¿ïÜHÙÅ\Ê\‰Vˇı∑á§Iöóp†∑Iıò|\"æ¿TtWt¯ˆ°Æ≤%h-îd<R\Í%‹îI^,!kí2o∑˚\ÍÒ\Á{˚Ñ†K<˜Éπ«Ö&\\≤\Ïd3∞\Èkr§Ås\·\ƒ\Á¿∆â±~ı˚ç\'L(;5rùº´}ma¶Û\◊ á\Ï>ﬂµYÛ\√8S\÷\ƒO[ﬂ≥&Ñgïxg—•±;¸åÜzA˘ÇE1•KJgHGê^\Ôwmç\Â\nVYã\'u7áÅg(Ÿîy5ka\‡Ú¢á∂Kóì\›Cû5g°∏\€!∂Œ°\"W<sß¥oáú\ÈÉrß¥kyùDOè\◊t¿*zºû\’Ö∂ë$äΩ¡ú∏\‰\Õ:ˆÙ∫d“çÒ™¨˜U\Î\€	K\÷˛ñp=a\…*ﬂ•3\ÿ¬ß<vÛ•%p\€\‰©o\'Ä÷ç\Ô¸¡π\·\◊6+g™YOÑ\Îè*ogqõJÑ˜\Ììdòq4\ﬂZàqq\ÎßˆO“ê2Öª(ΩK\ g\«´\—8\⁄\Z´\∆˘ˆ*€•MæmÛ∫ö!\ŒŒΩsJº€≠\rÒe¿&\0Ê∞ÇOY1Ü≠ºNF=ÕõÓ•Ø$\Œ\«+É;Ç?æ`ægW˙¬ªíìAóDúêP\Ë\Ì\Óá7á\ﬂ(†~9å[\Î3\'´ì}E\'7\«Y´32ñ¡iE1¢ïÂ•äVMB\ﬁs\ 5»ò\Óäoiyc¿™@ívJë)Æ#˚:Úx=\‰;\Œ\ﬁ\È-ÉJãû\Èpy\ƒˆeGùˇ˝\◊\””ò\∆ÙÖãÇî!î∫hYP\"\«Hˇ\rπ>Å\"˘y\›EÑóE\È\√\œ\ÃËóöo©£Ö1\‰X\0líç°¨˜ü⁄∫M\n\ËsXz#¢˜§Õ∑1\ƒ\√\‹<\"%R\⁄\‹Cå;\„\‰\ÀlêxØ¥•c¸˚^iØ!≥Ä°}òèn∞◊≥Ä!∫é¥\Ï¡V˚\Ã+\ƒKú\ZΩü∑y\”>-\ÓÚ\œ4˚mñ\Ì ©6pW\„S\ÿ&˘.É∫*|\'oN\«\◊\“&Ø\⁄l◊æ8\ÃpÇ\Ìó9ä\ ˜|a˘QA\ÂA\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180412103837+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180412103837+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001068 00000 n \n0000001363 00000 n \n0000001451 00000 n \n0000000015 00000 n \n0000001544 00000 n \n0000001328 00000 n \n0000001607 00000 n \n0000001661 00000 n \n0000001693 00000 n \n0000001796 00000 n \ntrailer\n<</Info 10 0 R/ID [<59617625135f2987fa9f1eb4a478dc2b><d5afb395321cc0068f0238f19a80ff37>]/Root 9 0 R/Size 11>>\nstartxref\n1964\n%%EOF\n','admin@gmail.com','2018-04-12 10:38:38',NULL,NULL),(7997,'2018-03-26 00:00:00',1,6,'Sale Store Copy','%PDF-1.5\n%\‚\„\œ\”\n4 0 obj\n<</Filter/FlateDecode/Length 1092>>stream\nxú\ÕY\ﬂo\€6~\◊_q¿0 \Zé?E2Okö¨X7i≠=-{Pe5Q …ô$Ø\…ø£,gûSYIÀÉx±O˜\›èﬂë4ß(H\ŒÒY4…ü\…yñàÑI![&óYÚ1\·\ﬁ}\ÍÙ(lµ≥&˘\·\'åBˆ%9yï\›9]JR-™£åAì\ƒ\‚ °ªI(àîCì(+G©~íˆ\«b¸F≤≠éìˆ\«\Ìm46\„Ó≥ò∞\Íi\‹}…ó\‰˜?ü\ÂN8c\‡;r!≥ ¨&Ro\Â¿§îé*Œâ∂V\√)´¶)ª¢Ñæ\ZJó\n7.{\÷\'LóIÆ≤Daå O∞\Êﬂ®ø\‹\Ê]ﬁΩÜ\Ìxï∑w˘g\nL5¡\‰M_›Æ\⁄ÚåëZqëj˝\Z.õº™œ†\ÏÚ¶\Z\Ó\◊\Ì›è7\ÓÇ)	ÙkOHCÑ\⁄VŸû3pih»©!§É\≈¿ê÷ã±xÛ\Îe S#ä>\0r^\’5|XÖ\∆\¬ÌòØC(ü õ™\ \Óª8s\—dè˜°kMJ?\ŒEºú\ÂX\∆˛\ÈÁîôS*NyöQzFiÑRL\“\€u?¨êÆ\‡¢p•ˆ1úYC˘≤©⁄®\ƒ \‘Q\ÌXæ\Ê˝mUv/BÒ∑ClùS;D©~íˆ\«M;\‹luP\⁄«ñ7jl\∆\›g1a\’”∏˚|Q;î\∆9¢Hön{É\›K…ªEòu≈ï\„M≥Z∑C\‹\ŒD≤èDòôH.™æàãò\À\ÿ\Â\√@‡™´äPc3@ã.tˇ!Ñ\·\Á°l\"q\«\·\≈˙≠≠Ü(i\„sÖqxün=\r&îq^¸ˆ©˚SîQÅ[\·qñ>\‰\ÕS\‚v\Õ\‹\⁄Zg\∆˚ˆE\Ÿ]u?T´6\¬<{}óä∑[7\≈^aë{,\rèB\ÃE\√1Øøî8I\È~\·åo=S9\◊\'L¢O˝ı´\‡-\Ã7˘eéÌóöõxÜ\Ã\¬#lπfG,_\·∑ˇ9˛Hôá?¯ë¯\√Áªà∞*F˛Ç\ƒ\‡Ä—ë¯\√E$˛ahbƒ≥ÙÒL\·\\üÿà\ÏÒüΩ2GˆJ\ÕM:.‹ïº\Â+*Ωü^˛Ùn˚$\'5ù_F±N/<˙8õ¸õ\¬R<;õ©uw4\'F;Y\È∏ Ll$\ÌNE(\ÿÒé\–	\ )N˙÷Ç3ÅC1\ZE°NÑI\›\Î(#&˝QróÇN@£ıFq&}ƒû¸rg•ˇΩãã˘„úí\‹\›2\Z_s1\»M)F§\0≠èJJ¶XYêz.$\÷`\Ëu\·£ÑßYR7åÙı\‰(0c	p{∫ó3û4\…≤Ä´¥C0ãı\Áa5\‰5lé˜påà5ß\\\ÕB‹ûæ\„!R¢î´øCà\Ÿ`ñ?DÉí8˛8¸+¡\“]fD\√¯∞\"˝`o£Ä!ó=uπªXó¡XLÇ\–o˘ÜØ+∏]∑ÀÆ\\B_˛U∂CY∂–≠\ÔÀ≤áº]BY\›\‹èß}ı\0˜y’ó∞j\Î\–„º†§\Á\'òÆB7ñpä√≠ ÿíπáeæ\Â\⁄ˆo\‹F\√\Ô\nendstream\nendobj\n1 0 obj\n<</Tabs/S/Group<</S/Transparency/Type/Group/CS/DeviceRGB>>/Contents 4 0 R/Type/Page/Resources<</ColorSpace<</CS/DeviceRGB>>/ProcSet [/PDF /Text /ImageB /ImageC /ImageI]/Font<</F1 2 0 R/F2 3 0 R>>>>/Parent 5 0 R/Rotate 90/MediaBox[0 0 422 594]>>\nendobj\n6 0 obj\n[1 0 R/XYZ 0 432 0]\nendobj\n2 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica/Encoding/WinAnsiEncoding>>\nendobj\n3 0 obj\n<</Subtype/Type1/Type/Font/BaseFont/Helvetica-Bold/Encoding/WinAnsiEncoding>>\nendobj\n5 0 obj\n<</Kids[1 0 R]/Type/Pages/Count 1/ITXT(2.1.7)>>\nendobj\n7 0 obj\n<</Names[(JR_PAGE_ANCHOR_0_1) 6 0 R]>>\nendobj\n8 0 obj\n<</Dests 7 0 R>>\nendobj\n9 0 obj\n<</Names 8 0 R/Type/Catalog/Pages 5 0 R/ViewerPreferences<</PrintScaling/AppDefault>>>>\nendobj\n10 0 obj\n<</ModDate(D:20180412104322+05\'30\')/Creator(JasperReports Library version 6.5.1)/CreationDate(D:20180412104322+05\'30\')/Producer(iText 2.1.7 by 1T3XT)>>\nendobj\nxref\n0 11\n0000000000 65535 f \n0000001175 00000 n \n0000001470 00000 n \n0000001558 00000 n \n0000000015 00000 n \n0000001651 00000 n \n0000001435 00000 n \n0000001714 00000 n \n0000001768 00000 n \n0000001800 00000 n \n0000001903 00000 n \ntrailer\n<</Info 10 0 R/ID [<de4e029061a2c400052048a1321afa40><ce31f3607c422d6c6b0c4b9c01793f08>]/Root 9 0 R/Size 11>>\nstartxref\n2071\n%%EOF\n','admin@gmail.com','2018-04-12 10:43:22',NULL,NULL);
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
