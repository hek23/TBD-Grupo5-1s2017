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
-- Temporary table structure for view `CountryResume`
--

DROP TABLE IF EXISTS `CountryResume`;
/*!50001 DROP VIEW IF EXISTS `CountryResume`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `CountryResume` AS SELECT 
 1 AS `Name`,
 1 AS `TweetsCount`,
 1 AS `ReTweetsCount`*/;
SET character_set_client = @saved_cs_client;

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
) ENGINE=InnoDB AUTO_INCREMENT=7410 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `KeywordJSONResume`
--

DROP TABLE IF EXISTS `KeywordJSONResume`;
/*!50001 DROP VIEW IF EXISTS `KeywordJSONResume`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `KeywordJSONResume` AS SELECT 
 1 AS `rtc`,
 1 AS `tc`,
 1 AS `word`,
 1 AS `Date`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `KeywordResume`
--

DROP TABLE IF EXISTS `KeywordResume`;
/*!50001 DROP VIEW IF EXISTS `KeywordResume`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `KeywordResume` AS SELECT 
 1 AS `word`,
 1 AS `SUM(CountryStat.TweetsCount)`,
 1 AS `SUM(CountryStat.ReTweetsCount)`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `Sinonimos`
--

DROP TABLE IF EXISTS `Sinonimos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sinonimos` (
  `id_sinonimo` int(11) NOT NULL AUTO_INCREMENT,
  `sinonimo` varchar(45) NOT NULL,
  `concepto` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_sinonimo`),
  KEY `fk_keyword_idx` (`concepto`),
  CONSTRAINT `fk_keyword` FOREIGN KEY (`concepto`) REFERENCES `Keyword` (`idKeyword`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Final view structure for view `CountryResume`
--

/*!50001 DROP VIEW IF EXISTS `CountryResume`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `CountryResume` AS select `Country`.`Name` AS `Name`,sum(`CountryStat`.`TweetsCount`) AS `TweetsCount`,sum(`CountryStat`.`RetweetsCount`) AS `ReTweetsCount` from (`CountryStat` join `Country` on((`CountryStat`.`Country` = `Country`.`idCountry`))) group by `CountryStat`.`Country` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `KeywordJSONResume`
--

/*!50001 DROP VIEW IF EXISTS `KeywordJSONResume`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `KeywordJSONResume` AS select sum(`CountryStat`.`RetweetsCount`) AS `rtc`,sum(`CountryStat`.`TweetsCount`) AS `tc`,`Keyword`.`word` AS `word`,`CountryStat`.`Date` AS `Date` from (`CountryStat` join `Keyword` on((`Keyword`.`idKeyword` = `CountryStat`.`Keyword`))) group by `Keyword`.`idKeyword`,`CountryStat`.`Date` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `KeywordResume`
--

/*!50001 DROP VIEW IF EXISTS `KeywordResume`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `KeywordResume` AS select `Keyword`.`word` AS `word`,sum(`CountryStat`.`TweetsCount`) AS `SUM(CountryStat.TweetsCount)`,sum(`CountryStat`.`RetweetsCount`) AS `SUM(CountryStat.ReTweetsCount)` from (`CountryStat` join `Keyword` on((`CountryStat`.`Keyword` = `Keyword`.`idKeyword`))) group by `CountryStat`.`Keyword` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-04 14:33:41
