USE `sampletrackingTest`;
-- MySQL dump 10.13  Distrib 5.5.47, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: sampletrackingw
-- ------------------------------------------------------
-- Server version	5.6.28-0ubuntu0.14.04.1

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
-- Dumping data for table `st_temp_sampleid`
--

LOCK TABLES `st_temp_sampleid` WRITE;
/*!40000 ALTER TABLE `st_temp_sampleid` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_temp_sampleid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_investigator`
--

LOCK TABLES `st_investigator` WRITE;
/*!40000 ALTER TABLE `st_investigator` DISABLE KEYS */;
INSERT INTO `st_investigator` (investigatorid,invest_abbreviation, invest_name) VALUES (1,'CI','CIMMYT1'),(2,'CM','CIMMYT2');
/*!40000 ALTER TABLE `st_investigator` ENABLE KEYS */;
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
-- Dumping data for table `st_load_type`
--

LOCK TABLES `st_load_type` WRITE;
/*!40000 ALTER TABLE `st_load_type` DISABLE KEYS */;
INSERT INTO `st_load_type` VALUES (1,'columns'),(2,'rows'),(3,'CIMMYT format');
/*!40000 ALTER TABLE `st_load_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_tissue`
--

LOCK TABLES `st_tissue` WRITE;
/*!40000 ALTER TABLE `st_tissue` DISABLE KEYS */;
INSERT INTO `st_tissue` VALUES (1,'SEED'),(2,'LEAF');
/*!40000 ALTER TABLE `st_tissue` ENABLE KEYS */;
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
-- Dumping data for table `st_storage_location`
--

LOCK TABLES `st_storage_location` WRITE;
/*!40000 ALTER TABLE `st_storage_location` DISABLE KEYS */;
INSERT INTO `st_storage_location` VALUES (1,'GRAL LOC','GENERAL LOCATIONS','GENERAL LOCATIONS',NULL),(2,'CIMMYT-HQ','CIMMYT-HQ','CIMMYT-HQ',1),(5,'CIMMYT MEX','CIMMYT MEXICO','CIMMYT MEX',2),(6,'PRINCIPAL','PRINCIPAL LABORATORY','PRINCIPAL LABORATORY',5),(7,'FREEZER-30','FREEZER -30','MINUS 30 FREEZER',6),(8,'FREEZER-80','FREEZER -80','MUNIS 80 FREEZER',6),(9,'MAIN DRYER','MAIN DRYER','MAIN DRYER FOR LAB',6),(10,'KBIOSCIENC','KBIOSCIENCE','SENDED TO KBIOSCIENCE',1),(11,'DART','DART','SENDED TO DART',1),(12,'CORNELL','CORNELL','SENDED TO CORNELL ',1),(13,'WMB','WMB LABORATORY','Wheat laboratory bioscience complex',5),(14,'CF','CUARTO FRIO','WMB storage in CF',14),(15,'CF','CUARTO FRIO','WMB storage in CF',13),(18,'CF','CUARTO','DNA',18),(19,'','Diversity Arrays Technology Pty Ltd','',1);
/*!40000 ALTER TABLE `st_storage_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_company`
--

LOCK TABLES `st_company` WRITE;
/*!40000 ALTER TABLE `st_company` DISABLE KEYS */;
INSERT INTO `st_company` VALUES (1,'K-BIOSCIENCE','KBioscience Unit 7 Maple Park Hoddesdon Herts','info@kbioscience.co.uk','','1 978 232 9430 ',10),(2,'Institute for Genomic Diversity','130 Biotechnology Building Cornell University Ithaca, NY 14853-2703','','Ed Buckler','',12),(3,'Diversity Arrays Technology Pty Ltd','Bldg 3, Lv D, University of Canberra,\nKirinari st., Bruce, ACT 2617, Australia','a.kilian@diversityarrays.com','Andrzej Kilian','+ 61 2 6122 7300',19);
/*!40000 ALTER TABLE `st_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_study_template`
--

LOCK TABLES `st_study_template` WRITE;
/*!40000 ALTER TABLE `st_study_template` DISABLE KEYS */;
INSERT INTO `st_study_template` VALUES (1,'SEED TEMPLATE','');
/*!40000 ALTER TABLE `st_study_template` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `st_study_template_params`
--

LOCK TABLES `st_study_template_params` WRITE;
/*!40000 ALTER TABLE `st_study_template_params` DISABLE KEYS */;
INSERT INTO `st_study_template_params` VALUES (1,1,'SAMPLEORIGIN','Sample origin','C'),(2,1,'DNAEXTLOC','DNA extraction location','C'),(3,1,'CONCUGL','Concentration ug/ul','N'),(4,1,'VOLUL','Volume ul','N'),(5,1,'ORIGQTYUG','Origional Quantity ug','N'),(6,1,'DNAEXTMTHD','DNA extraction method ID','C');
/*!40000 ALTER TABLE `st_study_template_params` ENABLE KEYS */;
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
-- Dumping data for table `st_organism`
--

LOCK TABLES `st_organism` WRITE;
/*!40000 ALTER TABLE `st_organism` DISABLE KEYS */;
INSERT INTO `st_organism` VALUES (1,'MAIZE'),(2,'WHEAT');
/*!40000 ALTER TABLE `st_organism` ENABLE KEYS */;
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
-- Dumping data for table `st_log_type_operation`
--

LOCK TABLES `st_log_type_operation` WRITE;
/*!40000 ALTER TABLE `st_log_type_operation` DISABLE KEYS */;
INSERT INTO `st_log_type_operation` VALUES (1,'Study_Add'),(2,'Study_Edit'),(3,'Study_Delite');
/*!40000 ALTER TABLE `st_log_type_operation` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `st_temp_samples`
--

LOCK TABLES `st_temp_samples` WRITE;
/*!40000 ALTER TABLE `st_temp_samples` DISABLE KEYS */;
/*!40000 ALTER TABLE `st_temp_samples` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_location`
--

LOCK TABLES `st_location` WRITE;
/*!40000 ALTER TABLE `st_location` DISABLE KEYS */;
INSERT INTO `st_location` VALUES (1,'Mixed','More than one location.'),(2,'Germplasm Bank','Germplasm Bank');
/*!40000 ALTER TABLE `st_location` ENABLE KEYS */;
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
-- Dumping data for table `st_season`
--

LOCK TABLES `st_season` WRITE;
/*!40000 ALTER TABLE `st_season` DISABLE KEYS */;
INSERT INTO `st_season` VALUES (1,'Mixed','More than one season.'),(2,'Main','Main');
/*!40000 ALTER TABLE `st_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_project`
--

LOCK TABLES `st_project` WRITE;
/*!40000 ALTER TABLE `st_project` DISABLE KEYS */;
INSERT INTO `st_project` VALUES (2,'SEED','seed','GWAS','gwas',NULL),(3,'SEED','Project Seed','DIV','Diversity',NULL),(4,'SEED','Seed Project','Map','Genetic mapping',NULL);
/*!40000 ALTER TABLE `st_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_user_version`
--

LOCK TABLES `st_user_version` WRITE;
/*!40000 ALTER TABLE `st_user_version` DISABLE KEYS */;
INSERT INTO `st_user_version` VALUES (1,'Administrator','administrator@cgair.org','Administrator','administrator@cgair.org','2.0','Admin2012',1,1,1,''),
(2,'CIMMYT1_DM','cimmyt1dm@cgiar.org','CIMMYT1_DM','cimmyt1dm@cgiar.org','2.0','cimmyt1dm2012',1,1,2,''),
(3,'CIMMYT1_researcher','cimmyt1researcher@cgiar.org','CIMMYT1_researcher','cimmyt1researcher@cgiar.org','2.0','cimmyt1researcher2012',1,1,3,''),
(4,'CIMMYT1_researcher_assistant','cimmyt1researcher_assistant@cgiar.org','CIMMYT1_researcher_assistant','cimmyt1researcher_assistant@cgiar.org','2.0','cimmyt1researcher_assistant2012',1,1,4,''),
(5,'CIMMYT1_assistant','cimmyt1_assistant@cgiar.org','CIMMYT1_assistant','cimmyt1_assistant@cgiar.org','2.0','cimmyt1_assistant2012',1,1,5,'');
/*!40000 ALTER TABLE `st_user_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_user_funtions`
--

LOCK TABLES `st_user_funtions` WRITE;
/*!40000 ALTER TABLE `st_user_funtions` DISABLE KEYS */;
INSERT INTO `st_user_funtions` VALUES (1,3,1),(2,3,2),(3,3,3),(4,3,4),(5,3,5),(6,3,6),(7,3,7),(8,3,8),(9,3,9),(10,3,10),(11,3,11),(12,3,12),(13,3,13),(14,3,14),
(15,3,15),(16,3,16),(17,3,17),(18,3,18),(19,3,19),(20,3,20),(21,3,21),(22,3,22),(23,3,23),(24,3,24),(25,3,25),(26,3,26),(27,3,27),(28,3,28),(29,3,29),(30,3,30),
(31,3,31),(32,3,32),(33,3,33),(34,3,34),(35,3,35),(36,3,36),(37,3,37),(38,3,38),(39,3,39),(40,3,40),(41,3,41),(42,3,42),(43,3,43),(44,3,44),(45,3,45);
/*!40000 ALTER TABLE `st_user_funtions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_user_funtions`
--

LOCK TABLES `st_program` WRITE;
/*!40000 ALTER TABLE `st_program` DISABLE KEYS */;

Insert into st_program (organismid,letter_code,program_name,description) values (2,'CG','Test Program Wheat','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'BW','Test Program Maize','');
/*!40000 ALTER TABLE `st_program` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `st_purpose` WRITE;
/*!40000 ALTER TABLE `st_purpose` DISABLE KEYS */;
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'ZZ','Test Maize','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'MS','Test Wheat','');

/*!40000 ALTER TABLE `st_purpose` ENABLE KEYS */;
UNLOCK TABLES

-- Dump completed on 2017-03-13 16:18:37
