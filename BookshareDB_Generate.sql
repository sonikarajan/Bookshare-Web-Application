CREATE DATABASE  IF NOT EXISTS `bookshare` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bookshare`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: bookshare
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `ad`
--

DROP TABLE IF EXISTS `ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad` (
  `AD_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BOOK_NAME` varchar(90) NOT NULL,
  `SUBJECT` varchar(45) NOT NULL,
  `URL` varchar(180) DEFAULT NULL,
  `IMAGE_PATH` varchar(90) DEFAULT NULL,
  `PRICE` int(11) NOT NULL,
  `RENT_OR_SELL` varchar(45) NOT NULL,
  `STATUS` varchar(45) NOT NULL,
  `OWNER_ID` int(11) NOT NULL,
  PRIMARY KEY (`AD_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad`
--

LOCK TABLES `ad` WRITE;
/*!40000 ALTER TABLE `ad` DISABLE KEYS */;
INSERT INTO `ad` VALUES (1,'Murach\'s Java Servlets and JSP 3rd Edition (2014)','Network Based Application Development','https://www.amazon.com/Murachs-Java-Servlets-JSP-Murach/dp/1890774782','images/earth.jpg',25,'Sell','Reported',3),(2,'Interaction Design: Beyond Human-Computer Interaction 4th Edition','Human Computer Interaction','https://www.amazon.com/Interaction-Design-Beyond-Human-Computer/dp/1119020751','images/colors.jpg',46,'Rent','Active',4),(3,'Software Engineering 10th Edition','Software System Design and Implementation','https://www.amazon.com/Software-Engineering-10th-Ian-Sommerville/dp/0133943038','images/small_tree.jpg',155,'Sell','Active',4),(4,'PISP','Principles of Information Security','https://en.wikipedia.org/wiki/User_guide','images/children.jpg',20,'Sell','Active',8);
/*!40000 ALTER TABLE `ad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  `EMAIL_ID` varchar(90) NOT NULL,
  `TYPE` varchar(45) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `EMAIL_ID_UNIQUE` (`EMAIL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'Sonika','Sonika@abc.com','Admin'),(3,'Richard','Richard@abc.com','User'),(4,'Joseph','Joseph@abc.com','User'),(5,'Andrew','Andrew@abc.com','User'),(8,'Rachel','Rachel@abc.com','User');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_ad_map`
--

DROP TABLE IF EXISTS `user_ad_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_ad_map` (
  `MAP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AD_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `STATUS` varchar(45) NOT NULL,
  PRIMARY KEY (`MAP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_ad_map`
--

LOCK TABLES `user_ad_map` WRITE;
/*!40000 ALTER TABLE `user_ad_map` DISABLE KEYS */;
INSERT INTO `user_ad_map` VALUES (1,1,3,'Pending'),(2,1,4,'Pending'),(3,2,5,'Pending'),(4,2,8,'Pending'),(5,3,8,'Pending');
/*!40000 ALTER TABLE `user_ad_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_key_map`
--

DROP TABLE IF EXISTS `user_key_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_key_map` (
  `USER_ID` int(11) NOT NULL,
  `SALT` varchar(45) NOT NULL,
  `HASH` varchar(90) NOT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_key_map`
--

LOCK TABLES `user_key_map` WRITE;
/*!40000 ALTER TABLE `user_key_map` DISABLE KEYS */;
INSERT INTO `user_key_map` VALUES (2,'3TVp6bMy+cCS5tGyvdo+wr24Xsx07cbqlYk88v43eaM=','4ab2afa69b67c052cebe5e43f69ce050d42b91563f1f3155acf64b3a5f23db9c'),(3,'3Y0hiwkMNpsJUsGJKSuKI5c724TXdSxYCPg3pqe5f0c=','4f18569c5ca0b31b222654a3c2268c4db8bf12f61908718d66a097381702ef1c'),(4,'+XUt4sljjJ9IOcLW7lGHi82MC2qfZgZ7JmuFCke1Q2s=','1817a475fd011262443c4d99c50739f9c855c7c03fb010ac292dd4db57b00d39'),(5,'FK+2LAo4TOdbG3qeHJxLh0dZf3kjqlRkkXl7cmvntUA=','bab67582ef8ccc4f6b25ed4a95d54e619f29671af5414b188e124c0edd0084e0'),(8,'HYx2FSs3JSJXlAO0n+piIesEiJUp9T7ArcozjFbRPx4=','93e901af90a0a24cd6e5cd36c5490d6b2150947286fbe35a1c790f49247b7127');
/*!40000 ALTER TABLE `user_key_map` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-02 22:33:00
