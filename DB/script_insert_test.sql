USE `sampletrackingCE`;
-- MySQL dump 10.13  Distrib 5.7.13, for Win64 (x86_64)
--
-- Host: 172.17.60.85    Database: sampletrackingBMS
-- ------------------------------------------------------
-- Server version	5.5.44-0ubuntu0.14.04.1

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
-- Dumping data for table `st_company`
--

LOCK TABLES `st_company` WRITE;
/*!40000 ALTER TABLE `st_company` DISABLE KEYS */;
INSERT INTO `st_company` VALUES (3,'Diversity Arrays Technology Pty Ltd','Bldg 3, Lv D, University of Canberra,\nKirinari st., Bruce, ACT 2617, Australia','a.kilian@diversityarrays.com','Andrzej Kilian','+ 61 2 6122 7300',2),(5,'Intertek-Sweeden','Intertek-Sweeden','agritech.sweden@intertek.co','Petra van Roggen','',3);
/*!40000 ALTER TABLE `st_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_constants_cornell_report`
--

LOCK TABLES `st_constants_cornell_report` WRITE;
/*!40000 ALTER TABLE `st_constants_cornell_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_constants_cornell_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_funtions`
--

LOCK TABLES `st_funtions` WRITE;
/*!40000 ALTER TABLE `st_funtions` DISABLE KEYS */;
INSERT INTO `st_funtions` VALUES (1,'studyTemplate'),(2,'studyTemplate$idAdd'),(3,'studyTemplate$idEdit'),(4,'studyTemplate$idDelete'),(5,'storeLocations'),(6,'storeLocations$idAdd'),(7,'storeLocations$idEdit'),(8,'storeLocations$idDelete'),(9,'storeLocations$idBarCode'),(10,'storeLocations$idStorageSample'),(11,'studies'),(12,'studies$idAdd'),(13,'studies$idEdit'),(14,'studies$idDelete'),(15,'studies$idLaboratoryReport'),(16,'studies$idResultData'),(17,'studies$idUpdateResult'),(18,'serviceProvider'),(19,'serviceProvider$idAdd'),(20,'serviceProvider$idEdit'),(21,'serviceProvider$idDelete'),(22,'shipmentManagment'),(23,'shipmentManagment$idAdd'),(24,'shipmentManagment$idEdit'),(25,'shipmentManagment$idDelete'),(26,'projects'),(27,'projects$idAdd'),(28,'projects$idEdit'),(29,'projects$idDelete'),(30,'researchers'),(31,'researchers$idAdd'),(32,'researchers$idEdit'),(33,'researchers$idDelete'),(34,'tissues'),(35,'tissues$idAdd'),(36,'tissues$idEdit'),(37,'tissues$idDelete'),(38,'location'),(39,'location$idAdd'),(40,'location$idEdit'),(41,'location$idDelete'),(42,'season'),(43,'season$idAdd'),(44,'season$idEdit'),(45,'season$idDelete');
/*!40000 ALTER TABLE `st_funtions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_investigator`
--

LOCK TABLES `st_investigator` WRITE;
/*!40000 ALTER TABLE `st_investigator` DISABLE KEYS */;
INSERT INTO `st_investigator` VALUES (1,'CI','CIMMYT1',''),(2,'CM','CIMMYT2',''),(3,'MK','Milcah Kigoni',''),(4,'HG','HTPG',''),(5,'II','IITA','');
/*!40000 ALTER TABLE `st_investigator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_lab_study`
--

LOCK TABLES `st_lab_study` WRITE;
/*!40000 ALTER TABLE `st_lab_study` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_lab_study` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_last_plate_project`
--

LOCK TABLES `st_last_plate_project` WRITE;
/*!40000 ALTER TABLE `st_last_plate_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_last_plate_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_load_type`
--

LOCK TABLES `st_load_type` WRITE;
/*!40000 ALTER TABLE `st_load_type` DISABLE KEYS */;
INSERT INTO `st_load_type` VALUES (1,'columns'),(2,'rows'),(3,'CIMMYT format');
/*!40000 ALTER TABLE `st_load_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_location`
--

LOCK TABLES `st_location` WRITE;
/*!40000 ALTER TABLE `st_location` DISABLE KEYS */;
INSERT INTO `st_location` VALUES (1,'Mixed','More than one location.'),(2,'Location_1','Field site 1'),(3,'IITA','IITA'),(4,'Unknown','Unknown');
/*!40000 ALTER TABLE `st_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_log`
--

LOCK TABLES `st_log` WRITE;
/*!40000 ALTER TABLE `st_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_log_type_entity`
--

LOCK TABLES `st_log_type_entity` WRITE;
/*!40000 ALTER TABLE `st_log_type_entity` DISABLE KEYS */;
INSERT INTO `st_log_type_entity` VALUES (1,'ENTITY_STUDY');
/*!40000 ALTER TABLE `st_log_type_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_log_type_operation`
--

LOCK TABLES `st_log_type_operation` WRITE;
/*!40000 ALTER TABLE `st_log_type_operation` DISABLE KEYS */;
INSERT INTO `st_log_type_operation` VALUES (1,'Study_Add'),(2,'Study_Edit'),(3,'Study_Delite');
/*!40000 ALTER TABLE `st_log_type_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_mixture_detail`
--

LOCK TABLES `st_mixture_detail` WRITE;
/*!40000 ALTER TABLE `st_mixture_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_mixture_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_mixture_method`
--

LOCK TABLES `st_mixture_method` WRITE;
/*!40000 ALTER TABLE `st_mixture_method` DISABLE KEYS */;
INSERT INTO `st_mixture_method` VALUES (0,'BULK'),(1,'POOL');
/*!40000 ALTER TABLE `st_mixture_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_organism`
--

LOCK TABLES `st_organism` WRITE;
/*!40000 ALTER TABLE `st_organism` DISABLE KEYS */;
INSERT INTO `st_organism` VALUES (1,'MAIZE'),(2,'WHEAT');
/*!40000 ALTER TABLE `st_organism` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_plates_not_used`
--

LOCK TABLES `st_plates_not_used` WRITE;
/*!40000 ALTER TABLE `st_plates_not_used` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_plates_not_used` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_program`
--

LOCK TABLES `st_program` WRITE;
/*!40000 ALTER TABLE `st_program` DISABLE KEYS */;
INSERT INTO `st_program` VALUES (1,1,'CP','Cowpea','Cowpea',''),(2,1,'CK','Chickpea','Chickpea',''),(3,1,'RI','Rice','Rice',''),(4,1,'MZ','Maize','Maize','');
/*!40000 ALTER TABLE `st_program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_project`
--

LOCK TABLES `st_project` WRITE;
/*!40000 ALTER TABLE `st_project` DISABLE KEYS */;
INSERT INTO `st_project` VALUES (1,'CP18','','MSII','',NULL);
/*!40000 ALTER TABLE `st_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_purpose`
--

LOCK TABLES `st_purpose` WRITE;
/*!40000 ALTER TABLE `st_purpose` DISABLE KEYS */;
INSERT INTO `st_purpose` VALUES (1,1,'MS','Marker-assisted selection','Marker-assisted selection',''),(2,1,'FV','F1 verification','F1 verification',''),(3,1,'QC','Quality Control (general)','Any general quality control process without specifying the exact nature of the quality control process',''),(4,1,'LV','Line Verification','Quality control tests to establish whether the genotypic data for a line matches the genotypic data for its parental germplasm','');
/*!40000 ALTER TABLE `st_purpose` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_register`
--

LOCK TABLES `st_register` WRITE;
/*!40000 ALTER TABLE `st_register` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_register` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_results_preferences`
--

LOCK TABLES `st_results_preferences` WRITE;
/*!40000 ALTER TABLE `st_results_preferences` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_results_preferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_results_preferences_detail`
--

LOCK TABLES `st_results_preferences_detail` WRITE;
/*!40000 ALTER TABLE `st_results_preferences_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_results_preferences_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_rol`
--

LOCK TABLES `st_rol` WRITE;
/*!40000 ALTER TABLE `st_rol` DISABLE KEYS */;
INSERT INTO `st_rol` VALUES (1,'ADMINISTRATOR'),(2,'DATA MANAGER'),(3,'RESEARCHER'),(4,'RESEARCH ASSISTANT'),(5,'ASSISTANT');
/*!40000 ALTER TABLE `st_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_sample_det_result`
--

LOCK TABLES `st_sample_det_result` WRITE;
/*!40000 ALTER TABLE `st_sample_det_result` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_sample_det_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_sample_detail`
--

LOCK TABLES `st_sample_detail` WRITE;
/*!40000 ALTER TABLE `st_sample_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_sample_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
--
-- Dumping data for table `st_sample_mixture`
--

LOCK TABLES `st_sample_mixture` WRITE;
/*!40000 ALTER TABLE `st_sample_mixture` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_sample_mixture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_sampleid`
--

LOCK TABLES `st_sampleid` WRITE;
/*!40000 ALTER TABLE `st_sampleid` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_sampleid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_season`
--

LOCK TABLES `st_season` WRITE;
/*!40000 ALTER TABLE `st_season` DISABLE KEYS */;
INSERT INTO `st_season` VALUES (1,'Mixed','More than one season.'),(2,'2018','2018'),(25,'2017','2017'),(3,'2016','2016');
/*!40000 ALTER TABLE `st_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_shipment`
--

LOCK TABLES `st_shipment` WRITE;
/*!40000 ALTER TABLE `st_shipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_shipment_detail`
--

LOCK TABLES `st_shipment_detail` WRITE;
/*!40000 ALTER TABLE `st_shipment_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_shipment_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_shipment_set`
--

LOCK TABLES `st_shipment_set` WRITE;
/*!40000 ALTER TABLE `st_shipment_set` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_shipment_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_shipmentfiles`
--

LOCK TABLES `st_shipmentfiles` WRITE;
/*!40000 ALTER TABLE `st_shipmentfiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_shipmentfiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_status`
--

LOCK TABLES `st_status` WRITE;
/*!40000 ALTER TABLE `st_status` DISABLE KEYS */;
INSERT INTO `st_status` VALUES ('F','For sending'),('N','No selected'),('R','Received'),('S','Sent');
/*!40000 ALTER TABLE `st_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_storage_location`
--

LOCK TABLES `st_storage_location` WRITE;
/*!40000 ALTER TABLE `st_storage_location` DISABLE KEYS */;
INSERT INTO `st_storage_location` VALUES (1,'GRAL LOC','GENERAL LOCATIONS','GENERAL LOCATIONS',NULL),(2,'','Diversity Arrays Technology Pty Ltd','',1),(3,'','Intertek-Sweeden','',1);
/*!40000 ALTER TABLE `st_storage_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_storage_mov`
--

LOCK TABLES `st_storage_mov` WRITE;
/*!40000 ALTER TABLE `st_storage_mov` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_storage_mov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_study_template`
--

LOCK TABLES `st_study_template` WRITE;
/*!40000 ALTER TABLE `st_study_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_study_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_study_template_params`
--

LOCK TABLES `st_study_template_params` WRITE;
/*!40000 ALTER TABLE `st_study_template_params` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_study_template_params` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_temp_sampleid`
--

LOCK TABLES `st_temp_sampleid` WRITE;
/*!40000 ALTER TABLE `st_temp_sampleid` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_temp_sampleid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_temp_samples`
--

LOCK TABLES `st_temp_samples` WRITE;
/*!40000 ALTER TABLE `st_temp_samples` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_temp_samples` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_tissue`
--

LOCK TABLES `st_tissue` WRITE;
/*!40000 ALTER TABLE `st_tissue` DISABLE KEYS */;
INSERT INTO `st_tissue` VALUES (1,'seed:PO:0009010'),(2,'leaf:PO:0025034');
/*!40000 ALTER TABLE `st_tissue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_user_funtions`
--

LOCK TABLES `st_user_funtions` WRITE;
/*!40000 ALTER TABLE `st_user_funtions` DISABLE KEYS */;
INSERT INTO `st_user_funtions` VALUES (1,3,1),(2,3,2),(3,3,3),(4,3,4),(5,3,5),(6,3,6),(7,3,7),(8,3,8),(9,3,9),(10,3,10),(11,3,11),(12,3,12),(13,3,13),(14,3,14),(15,3,15),(16,3,16),(17,3,17),(18,3,18),(19,3,19),(20,3,20),(21,3,21),(22,3,22),(23,3,23),(24,3,24),(25,3,25),(26,3,26),(27,3,27),(28,3,28),(29,3,29),(30,3,30),(31,3,31),(32,3,32),(33,3,33),(34,3,34),(35,3,35),(36,3,36),(37,3,37),(38,3,38),(39,3,39),(40,3,40),(41,3,41),(42,3,42),(43,3,43),(44,3,44),(45,3,45);
/*!40000 ALTER TABLE `st_user_funtions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_user_version`
--

LOCK TABLES `st_user_version` WRITE;
/*!40000 ALTER TABLE `st_user_version` DISABLE KEYS */;
INSERT INTO `st_user_version` VALUES (1,'Administrator','administrator@cgair.org','Administrator','administrator@cgair.org','2.0','Admin2012',1,1,1,''),(2,'CIMMYT1_DM','cimmyt1dm@cgiar.org','CIMMYT1_DM','cimmyt1dm@cgiar.org','2.0','cimmyt1dm2012',1,1,2,''),(3,'CIMMYT1_researcher','cimmyt1researcher@cgiar.org','CIMMYT1_researcher','cimmyt1researcher@cgiar.org','2.0','cimmyt1researcher2012',1,1,3,''),(4,'CIMMYT1_researcher_assistant','cimmyt1researcher_assistant@cgiar.org','CIMMYT1_researcher_assistant','cimmyt1researcher_assistant@cgiar.org','2.0','cimmyt1researcher_assistant2012',1,1,4,''),(5,'CIMMYT1_assistant','cimmyt1_assistant@cgiar.org','CIMMYT1_assistant','cimmyt1_assistant@cgiar.org','2.0','cimmyt1_assistant2012',1,1,5,'');
/*!40000 ALTER TABLE `st_user_version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-12 13:10:13
