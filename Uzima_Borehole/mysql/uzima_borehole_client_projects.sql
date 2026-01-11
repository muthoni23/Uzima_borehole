-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: uzima_borehole
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `client_projects`
--

DROP TABLE IF EXISTS `client_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_projects` (
  `ProjectID` int NOT NULL AUTO_INCREMENT,
  `ClientID` int NOT NULL,
  `ServiceID` int NOT NULL,
  `PumpID` int NOT NULL,
  `Depth_of_borehole` decimal(6,2) NOT NULL,
  `Height_of_tank` decimal(6,2) NOT NULL,
  `Tank_capacity_litres` int DEFAULT NULL,
  `Tank_installation_fee` decimal(10,2) DEFAULT NULL,
  `Drilling_cost` decimal(10,2) NOT NULL,
  `Pump_cost` decimal(10,2) NOT NULL,
  `Pump_installation_cost` decimal(10,2) NOT NULL,
  `Plumbing_cost` decimal(10,2) DEFAULT NULL,
  `Survey_fee` decimal(10,2) NOT NULL,
  `Authority_fee` decimal(10,2) NOT NULL,
  `Subtotal` decimal(10,2) NOT NULL,
  `Tax_amount` decimal(10,2) NOT NULL,
  `Grand_total` decimal(10,2) NOT NULL,
  `Project_date` date DEFAULT NULL,
  `Project_status` varchar(45) DEFAULT NULL,
  `Created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`ProjectID`),
  KEY `ClientID_idx` (`ClientID`),
  KEY `ServiceID_idx` (`ServiceID`),
  KEY `PumpID_idx` (`PumpID`),
  CONSTRAINT `ClientID` FOREIGN KEY (`ClientID`) REFERENCES `clients` (`ClientID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PumpID` FOREIGN KEY (`PumpID`) REFERENCES `pipe_types` (`PipeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ServiceID` FOREIGN KEY (`ServiceID`) REFERENCES `services` (`ServiceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_projects`
--

LOCK TABLES `client_projects` WRITE;
/*!40000 ALTER TABLE `client_projects` DISABLE KEYS */;
INSERT INTO `client_projects` VALUES (1,1,1,1,5.00,5.00,NULL,NULL,130000.00,90000.00,10000.00,0.00,7000.00,10000.00,230000.00,36800.00,283800.00,'2025-12-09','Approved','2025-12-09 03:14:10'),(2,1,1,1,5.00,10.00,1000,8000.00,130000.00,90000.00,15000.00,8000.00,7000.00,10000.00,251000.00,40160.00,308160.00,'2026-01-11','In Progress','2025-12-09 03:16:00'),(3,1,1,1,3.00,2.00,1000,8000.00,130000.00,90000.00,5000.00,4000.00,7000.00,10000.00,237000.00,37920.00,291920.00,'2025-12-09','Pending','2025-12-09 03:18:55'),(4,1,1,1,5.00,2.00,1000,8000.00,130000.00,90000.00,7000.00,4000.00,7000.00,10000.00,239000.00,38240.00,294240.00,'2025-12-09','Pending','2025-12-09 04:02:33'),(5,1,3,2,10.00,2.00,10000,25000.00,335000.00,65000.00,12000.00,13200.00,7000.00,10000.00,450200.00,72032.00,539232.00,'2025-12-21','Pending','2025-12-21 23:10:53'),(6,3,3,1,2567.00,100.00,2000,10000.00,335000.00,90000.00,6667500.00,2527000.00,20000.00,50000.00,9629500.00,1540720.00,11240220.00,'2025-12-25','Pending','2025-12-25 15:13:27'),(7,1,2,3,5.00,2.00,NULL,NULL,225000.00,30000.00,7000.00,0.00,7000.00,10000.00,262000.00,41920.00,320920.00,'2026-01-11','Completed','2025-12-30 15:05:24');
/*!40000 ALTER TABLE `client_projects` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-11 14:05:23
