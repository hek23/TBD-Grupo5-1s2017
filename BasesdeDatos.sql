CREATE DATABASE  IF NOT EXISTS `WW3App` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `WW3App`;
-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: WW3App
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

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
-- Table structure for table `Country`
--

DROP TABLE IF EXISTS `Country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Country` (
  `Name` char(52) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `Continent` enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Asia',
  `Population` int(11) NOT NULL DEFAULT '0',
  `Code` char(2) CHARACTER SET utf8 NOT NULL,
  `idCountry` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idCountry`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Country`
--

LOCK TABLES `Country` WRITE;
/*!40000 ALTER TABLE `Country` DISABLE KEYS */;
INSERT INTO `Country` VALUES ('Andorra','Europe',78000,'AD',1),('United Arab Emirates','Asia',2441000,'AE',2),('Afghanistan','Asia',22720000,'AF',3),('Antigua and Barbuda','North America',68000,'AG',4),('Anguilla','North America',8000,'AI',5),('Albania','Europe',3401200,'AL',6),('Armenia','Asia',3520000,'AM',7),('Netherlands Antilles','North America',217000,'AN',8),('Angola','Africa',12878000,'AO',9),('Antarctica','Antarctica',0,'AQ',10),('Argentina','South America',37032000,'AR',11),('American Samoa','Oceania',68000,'AS',12),('Austria','Europe',8091800,'AT',13),('Australia','Oceania',18886000,'AU',14),('Aruba','North America',103000,'AW',15),('Azerbaijan','Asia',7734000,'AZ',16),('Bosnia and Herzegovina','Europe',3972000,'BA',17),('Barbados','North America',270000,'BB',18),('Bangladesh','Asia',129155000,'BD',19),('Belgium','Europe',10239000,'BE',20),('Burkina Faso','Africa',11937000,'BF',21),('Bulgaria','Europe',8190900,'BG',22),('Bahrain','Asia',617000,'BH',23),('Burundi','Africa',6695000,'BI',24),('Benin','Africa',6097000,'BJ',25),('Bermuda','North America',65000,'BM',26),('Brunei','Asia',328000,'BN',27),('Bolivia','South America',8329000,'BO',28),('Brazil','South America',170115000,'BR',29),('Bahamas','North America',307000,'BS',30),('Bhutan','Asia',2124000,'BT',31),('Bouvet Island','Antarctica',0,'BV',32),('Botswana','Africa',1622000,'BW',33),('Belarus','Europe',10236000,'BY',34),('Belize','North America',241000,'BZ',35),('Canada','North America',31147000,'CA',36),('Cocos (Keeling) Islands','Oceania',600,'CC',37),('Congo, The Democratic Republic of the','Africa',51654000,'CD',38),('Central African Republic','Africa',3615000,'CF',39),('Congo','Africa',2943000,'CG',40),('Switzerland','Europe',7160400,'CH',41),('Côte d’Ivoire','Africa',14786000,'CI',42),('Cook Islands','Oceania',20000,'CK',43),('Chile','South America',15211000,'CL',44),('Cameroon','Africa',15085000,'CM',45),('China','Asia',1277558000,'CN',46),('Colombia','South America',42321000,'CO',47),('Costa Rica','North America',4023000,'CR',48),('Cuba','North America',11201000,'CU',49),('Cape Verde','Africa',428000,'CV',50),('Christmas Island','Oceania',2500,'CX',51),('Cyprus','Asia',754700,'CY',52),('Czech Republic','Europe',10278100,'CZ',53),('Germany','Europe',82164700,'DE',54),('Djibouti','Africa',638000,'DJ',55),('Denmark','Europe',5330000,'DK',56),('Dominica','North America',71000,'DM',57),('Dominican Republic','North America',8495000,'DO',58),('Algeria','Africa',31471000,'DZ',59),('Ecuador','South America',12646000,'EC',60),('Estonia','Europe',1439200,'EE',61),('Egypt','Africa',68470000,'EG',62),('Western Sahara','Africa',293000,'EH',63),('Eritrea','Africa',3850000,'ER',64),('Spain','Europe',39441700,'ES',65),('Ethiopia','Africa',62565000,'ET',66),('Finland','Europe',5171300,'FI',67),('Fiji Islands','Oceania',817000,'FJ',68),('Falkland Islands','South America',2000,'FK',69),('Micronesia, Federated States of','Oceania',119000,'FM',70),('Faroe Islands','Europe',43000,'FO',71),('France','Europe',59225700,'FR',72),('Gabon','Africa',1226000,'GA',73),('United Kingdom','Europe',59623400,'GB',74),('Grenada','North America',94000,'GD',75),('Georgia','Asia',4968000,'GE',76),('French Guiana','South America',181000,'GF',77),('Ghana','Africa',20212000,'GH',78),('Gibraltar','Europe',25000,'GI',79),('Greenland','North America',56000,'GL',80),('Gambia','Africa',1305000,'GM',81),('Guinea','Africa',7430000,'GN',82),('Guadeloupe','North America',456000,'GP',83),('Equatorial Guinea','Africa',453000,'GQ',84),('Greece','Europe',10545700,'GR',85),('South Georgia and the South Sandwich Islands','Antarctica',0,'GS',86),('Guatemala','North America',11385000,'GT',87),('Guam','Oceania',168000,'GU',88),('Guinea-Bissau','Africa',1213000,'GW',89),('Guyana','South America',861000,'GY',90),('Hong Kong','Asia',6782000,'HK',91),('Heard Island and McDonald Islands','Antarctica',0,'HM',92),('Honduras','North America',6485000,'HN',93),('Croatia','Europe',4473000,'HR',94),('Haiti','North America',8222000,'HT',95),('Hungary','Europe',10043200,'HU',96),('Indonesia','Asia',212107000,'ID',97),('Ireland','Europe',3775100,'IE',98),('Israel','Asia',6217000,'IL',99),('India','Asia',1013662000,'IN',100),('British Indian Ocean Territory','Africa',0,'IO',101),('Iraq','Asia',23115000,'IQ',102),('Iran','Asia',67702000,'IR',103),('Iceland','Europe',279000,'IS',104),('Italy','Europe',57680000,'IT',105),('Jamaica','North America',2583000,'JM',106),('Jordan','Asia',5083000,'JO',107),('Japan','Asia',126714000,'JP',108),('Kenya','Africa',30080000,'KE',109),('Kyrgyzstan','Asia',4699000,'KG',110),('Cambodia','Asia',11168000,'KH',111),('Kiribati','Oceania',83000,'KI',112),('Comoros','Africa',578000,'KM',113),('Saint Kitts and Nevis','North America',38000,'KN',114),('North Korea','Asia',24039000,'KP',115),('South Korea','Asia',46844000,'KR',116),('Kuwait','Asia',1972000,'KW',117),('Cayman Islands','North America',38000,'KY',118),('Kazakstan','Asia',16223000,'KZ',119),('Laos','Asia',5433000,'LA',120),('Lebanon','Asia',3282000,'LB',121),('Saint Lucia','North America',154000,'LC',122),('Liechtenstein','Europe',32300,'LI',123),('Sri Lanka','Asia',18827000,'LK',124),('Liberia','Africa',3154000,'LR',125),('Lesotho','Africa',2153000,'LS',126),('Lithuania','Europe',3698500,'LT',127),('Luxembourg','Europe',435700,'LU',128),('Latvia','Europe',2424200,'LV',129),('Libyan Arab Jamahiriya','Africa',5605000,'LY',130),('Morocco','Africa',28351000,'MA',131),('Monaco','Europe',34000,'MC',132),('Moldova','Europe',4380000,'MD',133),('Madagascar','Africa',15942000,'MG',134),('Marshall Islands','Oceania',64000,'MH',135),('Macedonia','Europe',2024000,'MK',136),('Mali','Africa',11234000,'ML',137),('Myanmar','Asia',45611000,'MM',138),('Mongolia','Asia',2662000,'MN',139),('Macao','Asia',473000,'MO',140),('Northern Mariana Islands','Oceania',78000,'MP',141),('Martinique','North America',395000,'MQ',142),('Mauritania','Africa',2670000,'MR',143),('Montserrat','North America',11000,'MS',144),('Malta','Europe',380200,'MT',145),('Mauritius','Africa',1158000,'MU',146),('Maldives','Asia',286000,'MV',147),('Malawi','Africa',10925000,'MW',148),('Mexico','North America',98881000,'MX',149),('Malaysia','Asia',22244000,'MY',150),('Mozambique','Africa',19680000,'MZ',151),('Namibia','Africa',1726000,'NA',152),('New Caledonia','Oceania',214000,'NC',153),('Niger','Africa',10730000,'NE',154),('Norfolk Island','Oceania',2000,'NF',155),('Nigeria','Africa',111506000,'NG',156),('Nicaragua','North America',5074000,'NI',157),('Netherlands','Europe',15864000,'NL',158),('Norway','Europe',4478500,'NO',159),('Nepal','Asia',23930000,'NP',160),('Nauru','Oceania',12000,'NR',161),('Niue','Oceania',2000,'NU',162),('New Zealand','Oceania',3862000,'NZ',163),('Oman','Asia',2542000,'OM',164),('Panama','North America',2856000,'PA',165),('Peru','South America',25662000,'PE',166),('French Polynesia','Oceania',235000,'PF',167),('Papua New Guinea','Oceania',4807000,'PG',168),('Philippines','Asia',75967000,'PH',169),('Pakistan','Asia',156483000,'PK',170),('Poland','Europe',38653600,'PL',171),('Saint Pierre and Miquelon','North America',7000,'PM',172),('Pitcairn','Oceania',50,'PN',173),('Puerto Rico','North America',3869000,'PR',174),('Palestine','Asia',3101000,'PS',175),('Portugal','Europe',9997600,'PT',176),('Palau','Oceania',19000,'PW',177),('Paraguay','South America',5496000,'PY',178),('Qatar','Asia',599000,'QA',179),('Réunion','Africa',699000,'RE',180),('Romania','Europe',22455500,'RO',181),('Russian Federation','Europe',146934000,'RU',182),('Rwanda','Africa',7733000,'RW',183),('Saudi Arabia','Asia',21607000,'SA',184),('Solomon Islands','Oceania',444000,'SB',185),('Seychelles','Africa',77000,'SC',186),('Sudan','Africa',29490000,'SD',187),('Sweden','Europe',8861400,'SE',188),('Singapore','Asia',3567000,'SG',189),('Saint Helena','Africa',6000,'SH',190),('Slovenia','Europe',1987800,'SI',191),('Svalbard and Jan Mayen','Europe',3200,'SJ',192),('Slovakia','Europe',5398700,'SK',193),('Sierra Leone','Africa',4854000,'SL',194),('San Marino','Europe',27000,'SM',195),('Senegal','Africa',9481000,'SN',196),('Somalia','Africa',10097000,'SO',197),('Suriname','South America',417000,'SR',198),('Sao Tome and Principe','Africa',147000,'ST',199),('El Salvador','North America',6276000,'SV',200),('Syria','Asia',16125000,'SY',201),('Swaziland','Africa',1008000,'SZ',202),('Turks and Caicos Islands','North America',17000,'TC',203),('Chad','Africa',7651000,'TD',204),('French Southern territories','Antarctica',0,'TF',205),('Togo','Africa',4629000,'TG',206),('Thailand','Asia',61399000,'TH',207),('Tajikistan','Asia',6188000,'TJ',208),('Tokelau','Oceania',2000,'TK',209),('Turkmenistan','Asia',4459000,'TM',210),('Tunisia','Africa',9586000,'TN',211),('Tonga','Oceania',99000,'TO',212),('East Timor','Asia',885000,'TP',213),('Turkey','Asia',66591000,'TR',214),('Trinidad and Tobago','North America',1295000,'TT',215),('Tuvalu','Oceania',12000,'TV',216),('Taiwan','Asia',22256000,'TW',217),('Tanzania','Africa',33517000,'TZ',218),('Ukraine','Europe',50456000,'UA',219),('Uganda','Africa',21778000,'UG',220),('United States Minor Outlying Islands','Oceania',0,'UM',221),('United States','North America',278357000,'US',222),('Uruguay','South America',3337000,'UY',223),('Uzbekistan','Asia',24318000,'UZ',224),('Holy See (Vatican City State)','Europe',1000,'VA',225),('Saint Vincent and the Grenadines','North America',114000,'VC',226),('Venezuela','South America',24170000,'VE',227),('Virgin Islands, British','North America',21000,'VG',228),('Virgin Islands, U.S.','North America',93000,'VI',229),('Vietnam','Asia',79832000,'VN',230),('Vanuatu','Oceania',190000,'VU',231),('Wallis and Futuna','Oceania',15000,'WF',232),('Samoa','Oceania',180000,'WS',233),('Yemen','Asia',18112000,'YE',234),('Mayotte','Africa',149000,'YT',235),('Yugoslavia','Europe',10640000,'YU',236),('South Africa','Africa',40377000,'ZA',237),('Zambia','Africa',9169000,'ZM',238),('Zimbabwe','Africa',11669000,'ZW',239);
/*!40000 ALTER TABLE `Country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CountryStat`
--

DROP TABLE IF EXISTS `CountryStat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CountryStat` (
  `idTweetsCount` int(11) NOT NULL AUTO_INCREMENT,
  `RetweetsCount` varchar(45) NOT NULL,
  `TweetsCount` varchar(45) NOT NULL,
  `Country` int(11) NOT NULL,
  `Date` date DEFAULT NULL,
  `Keyword` int(11) NOT NULL,
  PRIMARY KEY (`idTweetsCount`),
  KEY `fk_CountryStat_Country_idx` (`Country`),
  KEY `fk_CountryStat_Keyword_idx` (`Keyword`),
  CONSTRAINT `fk_CountryStat_Country` FOREIGN KEY (`Country`) REFERENCES `Country` (`idCountry`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CountryStat_Keyword` FOREIGN KEY (`Keyword`) REFERENCES `Keyword` (`idKeyword`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CountryStat`
--

LOCK TABLES `CountryStat` WRITE;
/*!40000 ALTER TABLE `CountryStat` DISABLE KEYS */;
/*!40000 ALTER TABLE `CountryStat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Keyword`
--

DROP TABLE IF EXISTS `Keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Keyword` (
  `idKeyword` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(45) NOT NULL,
  `creator` int(11) NOT NULL,
  PRIMARY KEY (`idKeyword`),
  UNIQUE KEY `word_UNIQUE` (`word`),
  KEY `fk_Keyword_1_idx` (`creator`),
  CONSTRAINT `fk_creator_word` FOREIGN KEY (`creator`) REFERENCES `User` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Keyword`
--

LOCK TABLES `Keyword` WRITE;
/*!40000 ALTER TABLE `Keyword` DISABLE KEYS */;
INSERT INTO `Keyword` VALUES (1,'Trump',1),(2,'Bashar',1),(3,'Al-Assad',1),(4,'ISIS',1),(5,'Corea del Norte',1),(6,'North Korea',1),(7,'Syria',1),(8,'Siria',1);
/*!40000 ALTER TABLE `Keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `privileges` enum('Admin','Moderator') NOT NULL DEFAULT 'Moderator',
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `idUser_UNIQUE` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'hek23','1234','Admin'),(2,'camus','camus','Admin'),(3,'daguilar','daguilar','Admin'),(4,'jcabello','jcabello','Admin');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-12 11:29:46
