CREATE DATABASE  IF NOT EXISTS `donationmanager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `donationmanager`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: donationmanager
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `campaigns`
--

DROP TABLE IF EXISTS `campaigns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaigns` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) DEFAULT NULL,
                             `purpose` varchar(255) DEFAULT NULL,
                             `version` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `UK_5rhrd2ghk2m15qnrfc7xyv81i` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaigns`
--

LOCK TABLES `campaigns` WRITE;
/*!40000 ALTER TABLE `campaigns` DISABLE KEYS */;
INSERT INTO `campaigns` VALUES (1,'Kidsspire','\"Kidsspire: Igniting Young Imaginations\" is a transformative campaign dedicated to nurturing creativity and curiosity among children.',0),(7,'Loveven','\"Loveven: Embrace the Moments, Share the Love\" is a heartwarming campaign inviting people to celebrate and express their love and gratitude for the special moments and people in their lives.',0),(9,'Lerment','\"Lerment: Empowering Minds, Transforming Futures\" is a visionary campaign committed to providing accessible education and skills training to underserved communities, fostering a brighter and more equitable tomorrow.',0),(10,'Brunloy','\"Brunloy: Savor the Flavors, Unite the Community\" is a delectable campaign that invites food enthusiasts to explore diverse cuisines while fostering connections and understanding among cultures.',0),(11,'Ladven','\"Ladven: Empowering Her Voice, Shaping Tomorrow\" is an inspiring campaign dedicated to amplifying the voices of young women and providing them with the tools and platform to become influential leaders in their communities and beyond.',0),(13,'Charkids','\"Charkids: Nurturing Healthy Bodies and Bright Minds\" is a dynamic campaign focused on promoting physical activity, balanced nutrition, and holistic education for children, ensuring their all-round development and well-being.',0),(30,'Brunder','\"Brunder: Building Bonds, Fostering Unity\" is a heartfelt campaign that encourages meaningful conversations and connections between siblings, reinforcing the importance of lifelong support and friendship within families.',0),(85,'Unicef','\"Unicef: Every Child, Every Opportunity\" is a global humanitarian campaign dedicated to ensuring that every child has the chance to thrive, providing them with essential healthcare, education, protection, and advocacy for their rights.',0),(86,'Kidsten','\"Kidsten: Cultivating Curious Minds, One Adventure at a Time\" is an engaging campaign that encourages children to explore the world around them through interactive learning experiences, fostering a lifelong love for discovery and knowledge.',0);
/*!40000 ALTER TABLE `campaigns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `amount` double DEFAULT NULL,
                            `approved` tinyint(1) DEFAULT '0',
                            `approvedBy` varchar(255) DEFAULT NULL,
                            `approvedDate` date DEFAULT NULL,
                            `createdBy` varchar(255) DEFAULT NULL,
                            `createdDate` date DEFAULT NULL,
                            `currency` enum('EUR','GBP','JPY','RON','RUB','USD') DEFAULT NULL,
                            `notes` varchar(255) DEFAULT NULL,
                            `donor_id` bigint DEFAULT NULL,
                            `campaign_id` bigint DEFAULT NULL,
                            `version` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK21pq3ymhhlhelfmnqjc51cliy` (`donor_id`),
                            KEY `FK8npnv534qmo6098ki6s76vgco` (`campaign_id`),
                            CONSTRAINT `FK21pq3ymhhlhelfmnqjc51cliy` FOREIGN KEY (`donor_id`) REFERENCES `donor` (`id`),
                            CONSTRAINT `FK8npnv534qmo6098ki6s76vgco` FOREIGN KEY (`campaign_id`) REFERENCES `campaigns` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES (1,100.5,0,NULL,NULL,'ManagM','2023-07-20','RON','XOXO',11,1,0),(2,50,0,NULL,NULL,'ManagM','2023-07-20','RON',NULL,3,1,0),(3,100.5,0,NULL,NULL,'ManagM','2023-08-07','USD','With love <3',4,1,0),(4,50.75,0,NULL,NULL,'ManagM','2023-08-07','EUR',NULL,2,10,0),(5,200,0,NULL,NULL,'ManagM','2023-08-08','GBP','From my whole family',9,10,0),(6,75.25,0,NULL,NULL,'ManagM','2023-08-08','USD',NULL,11,7,0),(7,50,0,NULL,NULL,'ManagM','2023-07-20','RON',NULL,14,7,0),(8,12,0,NULL,NULL,'ManagM','2023-08-26','EUR',NULL,2,11,0),(9,150,0,NULL,NULL,'ManagM','2023-08-26','EUR',NULL,8,11,0),(10,500,0,NULL,NULL,'ManagM','2023-08-26','EUR',NULL,10,11,0),(11,2500,0,NULL,NULL,'ManagM','2023-08-12','GBP',NULL,7,85,0),(12,577,0,NULL,NULL,'ManagM','2023-08-07','EUR',NULL,6,85,0),(13,30,0,NULL,NULL,'ManagM','2023-08-14','GBP',NULL,5,85,0),(14,687,0,NULL,NULL,'ManagM','2023-08-07','GBP',NULL,14,86,0),(15,133,0,NULL,NULL,'ManagM','2023-08-10','EUR',NULL,6,9,0),(16,22,0,NULL,NULL,'ManagM','2023-08-10','RON',NULL,5,86,0),(17,36,0,NULL,NULL,'ManagM','2023-08-01','EUR',NULL,13,86,0),(18,570,0,NULL,NULL,'ManagM','2023-05-12','EUR',NULL,12,9,0),(19,400,0,NULL,NULL,'ManagM','2023-05-30','RON',NULL,3,30,0),(20,3000,0,NULL,NULL,'ManagM','2023-06-17','USD',NULL,7,9,0),(21,455,0,NULL,NULL,'ManagM','2023-06-18','RON',NULL,5,30,0),(22,200,0,NULL,NULL,'ManagM','2023-06-22','RON',NULL,3,85,0),(23,870,0,NULL,NULL,'ManagM','2023-07-30','RON',NULL,14,9,0),(24,50,0,NULL,NULL,'ManagM','2023-07-26','USD',NULL,9,13,0),(25,50,0,NULL,NULL,'ManagM','2023-07-28','USD',NULL,5,13,0);
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donor`
--

DROP TABLE IF EXISTS `donor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donor` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `additionalName` varchar(255) DEFAULT NULL,
                         `firstName` varchar(255) DEFAULT NULL,
                         `lastName` varchar(255) DEFAULT NULL,
                         `maidenName` varchar(255) DEFAULT NULL,
                         `version` bigint DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donor`
--

LOCK TABLES `donor` WRITE;
/*!40000 ALTER TABLE `donor` DISABLE KEYS */;
INSERT INTO `donor` VALUES (2,'Francis','Joey','Tribbiani','',0),(3,'','Sam','Smith','',0),(4,'Joe','John','Green','',0),(5,'James','Harry','Styles',NULL,0),(6,NULL,'Marry','Poppins','Pops',0),(7,'James','Harry','Potter',NULL,0),(8,'Percival','Albus','Dumbledore',NULL,0),(9,NULL,'Donkey','Shrek',NULL,0),(10,'Captain','Jack','Sparrow',NULL,0),(11,NULL,'Arthur','King',NULL,0),(12,NULL,'Bean','Mr',NULL,0),(13,NULL,'Forrest','Gump',NULL,0),(14,NULL,'Chandler','Bing',NULL,0);
/*!40000 ALTER TABLE `donor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
                                 `refreshToken` varchar(255) NOT NULL,
                                 `expiryDate` datetime(6) NOT NULL,
                                 `user_id` bigint DEFAULT NULL,
                                 PRIMARY KEY (`refreshToken`),
                                 UNIQUE KEY `UK_ktwna62rqu1wooo3c217o5oh0` (`user_id`),
                                 CONSTRAINT `FKfep3ej40jxlnoc7sbb0ed0n3f` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES ('00cfc095-db3e-4677-b2b9-cf29a3b7eb6d','2023-08-27 07:49:57.947070',1),('13fd075e-8749-43d7-b5ec-1452afd36a82','2023-08-27 08:15:22.222626',2),('7d46643f-232b-4427-8103-4e655dc13ec0','2023-08-27 09:26:13.900639',5),('838e112e-65bf-44ba-b578-0c8338e66c8a','2023-08-26 13:54:06.112276',10),('90a474fd-832f-4f00-9df5-d0f928c8683a','2023-08-26 07:53:11.398289',4);
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `right_role`
--

DROP TABLE IF EXISTS `right_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `right_role` (
                              `right_id` bigint NOT NULL,
                              `role_id` bigint NOT NULL,
                              KEY `FK9c8h6591ayyi1s8pjcajp71q1` (`role_id`),
                              KEY `FKjnyhsi5wch5o9qp04tgcxar0r` (`right_id`),
                              CONSTRAINT `FKjnyhsi5wch5o9qp04tgcxar0r` FOREIGN KEY (`right_id`) REFERENCES `rights` (`id`),
                              CONSTRAINT `FKohkqt87eacnyh8ve98we4m1jk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `right_role`
--

LOCK TABLES `right_role` WRITE;
/*!40000 ALTER TABLE `right_role` DISABLE KEYS */;
INSERT INTO `right_role` VALUES (10,1),(11,1),(1,2),(2,2),(2,3),(3,2),(3,3),(4,2),(5,2),(5,3),(6,2),(6,3),(7,2),(9,4);
/*!40000 ALTER TABLE `right_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rights`
--

DROP TABLE IF EXISTS `rights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rights` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `name` enum('BENEF_MANAGEMENT','CAMP_IMPORT','CAMP_MANAGEMENT','CAMP_REPORTING','CAMP_REPORT_RESTRICTED','DONATION_APPROVE','DONATION_MANAGEMENT','DONATION_REPORTING','PERMISSION_MANAGEMENT','USER_MANAGEMENT') DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rights`
--

LOCK TABLES `rights` WRITE;
/*!40000 ALTER TABLE `rights` DISABLE KEYS */;
INSERT INTO `rights` VALUES (1,'CAMP_MANAGEMENT'),(2,'BENEF_MANAGEMENT'),(3,'DONATION_MANAGEMENT'),(4,'DONATION_APPROVE'),(5,'DONATION_REPORTING'),(6,'CAMP_REPORTING'),(7,'CAMP_IMPORT'),(9,'CAMP_REPORT_RESTRICTED'),(10,'PERMISSION_MANAGEMENT'),(11,'USER_MANAGEMENT');
/*!40000 ALTER TABLE `rights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `name` enum('ADM','CEN','MGN','REP') DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UK8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADM'),(3,'CEN'),(2,'MGN'),(4,'REP');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `email` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `username` varchar(255) DEFAULT NULL,
                        `active` bit(1) NOT NULL,
                        `attempts` smallint NOT NULL,
                        `firstLogin` tinyint(1) DEFAULT '1',
                        `firstName` varchar(255) DEFAULT NULL,
                        `lastName` varchar(255) DEFAULT NULL,
                        `mobileNumber` varchar(255) DEFAULT NULL,
                        `webSocketName` varchar(255) DEFAULT NULL,
                        `version` bigint DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`),
                        UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@donationmanager.com','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','AdminA',_binary '',0,0,'Admin','Admin','0739076747','',0),(2,'manager@donationmanager.com','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','ManagM',_binary '',0,0,'Manager','Manager','0751936433','',0),(4,'cenzor@donationmanager.com','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','CenzoC',_binary '',0,0,'Cenzor','Cenzor','+40748558100','',0),(5,'reporter@donationmanager.com','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','ReporR',_binary '',0,0,'Reporter','Reporter','0733652865','',0),(6,'Radu.Siderias@msg.group','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','SiderR',_binary '',0,1,'Radu','Siderias','0040783666169','',0),(7,'Alex.Buretia@msg.group','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','BuretA',_binary '',0,1,'Alex','Buretia','0777505242','',0),(8,'Sarolta.Jakab-Gyik@msg.group','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','JakabS',_binary '',0,1,'Sarolta','Jakab','0750482126','',0),(9,'Daniel.David@msg.group','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','DavidD',_binary '',0,1,'Daniel','David','+40744092383','',0),(10,'Alex.Fantana@msg.group','$2a$12$wDW2kQz.6YcuJ1/8DqHwAeIC3qC2UAnkL3Nfd3iqAg/t/loO.Mgz2','FantaA',_binary '',0,1,'Alex','Fantana','0782857639','',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_campaign`
--

DROP TABLE IF EXISTS `user_campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_campaign` (
                                 `user_id` bigint NOT NULL,
                                 `campaign_id` bigint NOT NULL,
                                 KEY `FKbu0y6e2r7humst2jpigb12jqw` (`campaign_id`),
                                 KEY `FK4ixmadta3vpjpmc3r87vp18nm` (`user_id`),
                                 CONSTRAINT `FK4ixmadta3vpjpmc3r87vp18nm` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                 CONSTRAINT `FKbu0y6e2r7humst2jpigb12jqw` FOREIGN KEY (`campaign_id`) REFERENCES `campaigns` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_campaign`
--

LOCK TABLES `user_campaign` WRITE;
/*!40000 ALTER TABLE `user_campaign` DISABLE KEYS */;
INSERT INTO `user_campaign` VALUES (5,1),(5,7),(5,10),(5,11),(5,13);
/*!40000 ALTER TABLE `user_campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
                             `user_id` bigint NOT NULL,
                             `role_id` bigint NOT NULL,
                             PRIMARY KEY (`user_id`,`role_id`),
                             KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
                             CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                             CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(6,1),(7,1),(8,1),(9,1),(10,1),(2,2),(4,3),(5,4);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-26 13:08:58
