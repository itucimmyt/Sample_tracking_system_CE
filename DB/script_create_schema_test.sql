drop schema If EXISTS sampletrackingTest;

CREATE DATABASE  IF NOT EXISTS `sampletrackingTest` /*!40100 DEFAULT CHARACTER SET latin1 */;
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
-- Table structure for table `st_storage_location`
--

DROP TABLE IF EXISTS `st_storage_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_storage_location` (
  `imslocid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique ID for the location',
  `lsname` varchar(10) DEFAULT NULL COMMENT 'Location short name ',
  `lname` varchar(45) DEFAULT NULL COMMENT 'Location''s full name',
  `comments` varchar(250) DEFAULT NULL COMMENT 'Additional info',
  `imslocidparent` int(11) DEFAULT NULL COMMENT 'Recursive parent for this locations (LAB/FREZER/SECTION 1)',
  PRIMARY KEY (`imslocid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COMMENT='Information about all Locations in the Lab where the user st';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_season`
--

DROP TABLE IF EXISTS `st_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_season` (
  `seasonid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Season identifier',
  `season_name` varchar(255) DEFAULT NULL COMMENT 'Season name',
  `season_description` varchar(255) DEFAULT NULL COMMENT 'Season description (optional)',
  PRIMARY KEY (`seasonid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_company`
--

DROP TABLE IF EXISTS `st_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_company` (
  `idCompany` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id for the company',
  `name` varchar(45) NOT NULL COMMENT 'Company name',
  `addresss` varchar(150) DEFAULT NULL COMMENT 'Postal address',
  `email` varchar(45) DEFAULT NULL COMMENT 'Email address',
  `contactname` varchar(45) DEFAULT NULL COMMENT 'Name of the principal contact',
  `phone` varchar(45) DEFAULT NULL COMMENT 'Phone(s) number(s)',
  `imslocid` int(11) DEFAULT NULL COMMENT 'Foreign key storage location',
  PRIMARY KEY (`idCompany`),
  KEY `FK_st_company` (`imslocid`),
  CONSTRAINT `FK_//st_company` FOREIGN KEY (`imslocid`) REFERENCES `st_storage_location` (`imslocid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COMMENT='Contact info for the companies (like KBiosciences) that make';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_funtions`
--

DROP TABLE IF EXISTS `st_funtions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_funtions` (
  `idst_funtions` int(11) NOT NULL AUTO_INCREMENT,
  `funtion_key` varchar(45) NOT NULL,
  PRIMARY KEY (`idst_funtions`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_investigator`
--

DROP TABLE IF EXISTS `st_investigator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_investigator` (
  `investigatorid` int(11) NOT NULL AUTO_INCREMENT,
  `invest_abbreviation` varchar(20) DEFAULT NULL,
  `invest_name` varchar(255) DEFAULT NULL,
  `status` BIT(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`investigatorid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `st_load_type`
--

DROP TABLE IF EXISTS `st_load_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_load_type` (
  `id_load_type` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id_load_type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_location`
--

DROP TABLE IF EXISTS `st_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_location` (
  `locationid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Location identifier',
  `location_name` varchar(255) DEFAULT NULL COMMENT 'Location name',
  `location_description` varchar(255) DEFAULT NULL COMMENT 'Location description (optional)',
  PRIMARY KEY (`locationid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_log_type_entity`
--

DROP TABLE IF EXISTS `st_log_type_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_log_type_entity` (
  `idst_log_type_entity` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(150) NOT NULL,
  PRIMARY KEY (`idst_log_type_entity`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_log_type_operation`
--

DROP TABLE IF EXISTS `st_log_type_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_log_type_operation` (
  `idst_log_type_operation` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idst_log_type_operation`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `st_mixture_detail`
--

DROP TABLE IF EXISTS `st_mixture_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_mixture_detail` (
  `mixtureDetailId` int(11) NOT NULL,
  `locationid` int(11) DEFAULT NULL COMMENT 'Location identifier',
  `seasonid` int(11) DEFAULT NULL COMMENT 'Season identifier',
  `plantNumber` decimal(4,0) DEFAULT NULL,
  PRIMARY KEY (`mixtureDetailId`),
  KEY `FK_Reference_34` (`locationid`),
  KEY `FK_Reference_35` (`seasonid`),
  CONSTRAINT `FK_Reference_34` FOREIGN KEY (`locationid`) REFERENCES `st_location` (`locationid`),
  CONSTRAINT `FK_Reference_35` FOREIGN KEY (`seasonid`) REFERENCES `st_season` (`seasonid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_mixture_method`
--

DROP TABLE IF EXISTS `st_mixture_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_mixture_method` (
  `mixtureMethodId` int(11) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`mixtureMethodId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_organism`
--

DROP TABLE IF EXISTS `st_organism`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_organism` (
  `organismid` int(11) NOT NULL AUTO_INCREMENT,
  `organismName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`organismid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_project`
--

DROP TABLE IF EXISTS `st_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_project` (
  `projectid` int(11) NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) DEFAULT NULL,
  `projectDescription` varchar(255) DEFAULT NULL,
  `purposeName` varchar(255) DEFAULT NULL,
  `purposeDescription` varchar(255) DEFAULT NULL,
  `lastsampleid` int(11) DEFAULT NULL,
  PRIMARY KEY (`projectid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_last_plate_project`
--

DROP TABLE IF EXISTS `st_last_plate_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_last_plate_project` (
  `lastplateprojectid` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) DEFAULT NULL,
  `organismid` int(11) DEFAULT NULL,
  `investigatorid` int(11) DEFAULT NULL,
  `platenumber` int(11) DEFAULT NULL,
  PRIMARY KEY (`lastplateprojectid`),
  KEY `FK_st_last_plate_project_st_investigator` (`investigatorid`),
  KEY `FK_st_last_plate_project_st_project` (`projectid`),
  KEY `FK_st_last_plate_project_st_organism` (`organismid`),
  CONSTRAINT `FK_st_last_plate_project_st_investigator` FOREIGN KEY (`investigatorid`) REFERENCES `st_investigator` (`investigatorid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_st_last_plate_project_st_organism` FOREIGN KEY (`organismid`) REFERENCES `st_organism` (`organismid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_st_last_plate_project_st_project` FOREIGN KEY (`projectid`) REFERENCES `st_project` (`projectid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `st_register`
--

DROP TABLE IF EXISTS `st_register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_register` (
  `idst_register` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(200) NOT NULL,
  `inmediate_boss` varchar(100) NOT NULL,
  `city` varchar(45) DEFAULT NULL,
  `contry` varchar(100) NOT NULL,
  `industry` varchar(100) NOT NULL,
  `provider_name` varchar(100) NOT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  `isPending` bit(1) NOT NULL DEFAULT b'1',
  `register_date` date NOT NULL,
  PRIMARY KEY (`idst_register`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_results_preferences_detail`
--

DROP TABLE IF EXISTS `st_results_preferences_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_results_preferences_detail` (
  `detailresprefid` int(11) NOT NULL AUTO_INCREMENT,
  `resultpreferencesid` int(11) DEFAULT NULL,
  `fijo` varchar(5) DEFAULT NULL COMMENT 'N quiere decir que es un parametro del template, S es un campo fijo',
  `paramid` int(11) DEFAULT NULL,
  `paramname` varchar(255) DEFAULT NULL,
  `header` varchar(255) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`detailresprefid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_rol`
--

DROP TABLE IF EXISTS `st_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_rol` (
  `idst_rol` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  PRIMARY KEY (`idst_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_status`
--

DROP TABLE IF EXISTS `st_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_status` (
  `id_status` char(1) NOT NULL,
  `status_description` varchar(45) NOT NULL,
  PRIMARY KEY (`id_status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `st_sample_mixture`
--

DROP TABLE IF EXISTS `st_sample_mixture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_sample_mixture` (
  `sampleid` int(11) NOT NULL,
  `mixtureDetailId` int(11) NOT NULL,
  `mixtureMethodId` int(11) DEFAULT NULL,
  PRIMARY KEY (`sampleid`,`mixtureDetailId`),
  KEY `FK_Reference_33` (`mixtureDetailId`),
  KEY `FK_Reference_36` (`mixtureMethodId`),
  CONSTRAINT `FK_Reference_33` FOREIGN KEY (`mixtureDetailId`) REFERENCES `st_mixture_detail` (`mixtureDetailId`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`mixtureMethodId`) REFERENCES `st_mixture_method` (`mixtureMethodId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_sampleid`
--

DROP TABLE IF EXISTS `st_sampleid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_sampleid` (
  `idsample` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) DEFAULT NULL,
  `gid` int(11) DEFAULT NULL,
  `nplant` int(11) DEFAULT NULL,
  `locationid` int(11) DEFAULT NULL,
  `seasonid` int(11) DEFAULT NULL,
  `sampleid` int(11) DEFAULT NULL,
  PRIMARY KEY (`idsample`),
  KEY `index_repeat_samples` (`projectid`,`gid`,`nplant`,`locationid`,`seasonid`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_shipment_set`
--

DROP TABLE IF EXISTS `st_shipment_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_shipment_set` (
  `id_shipment_set` int(11) NOT NULL AUTO_INCREMENT,
  `fk_investigator` int(11) NOT NULL,
  `dat_created` date NOT NULL,
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_shipment_set`),
  KEY `fk_st_shipment_set_st_investigator` (`fk_investigator`),
  CONSTRAINT `fk_st_shipment_set_st_investigator` FOREIGN KEY (`fk_investigator`) REFERENCES `st_investigator` (`investigatorid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_shipment`
--

DROP TABLE IF EXISTS `st_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_shipment` (
  `id_shipment` int(11) NOT NULL AUTO_INCREMENT,
  `fk_shipment_set` int(11) NOT NULL,
  `dat_register` date NOT NULL,
  `dat_send` date DEFAULT NULL,
  `dat_receive` date DEFAULT NULL,
  `fk_status` char(1) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `fk_company` int(11) NOT NULL,
  `tracking_number_local` varchar(20) DEFAULT NULL,
  `tracking_number_delivery` varchar(20) DEFAULT NULL,
  `sent_to_kb` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id_shipment`),
  KEY `fk_st_shipment_status` (`fk_status`),
  KEY `fk_st_shipment_company` (`fk_company`),
  KEY `fk_st_shipment_set` (`fk_shipment_set`),
  CONSTRAINT `fk_st_shipment_company` FOREIGN KEY (`fk_company`) REFERENCES `st_company` (`idCompany`),
  CONSTRAINT `fk_st_shipment_set` FOREIGN KEY (`fk_shipment_set`) REFERENCES `st_shipment_set` (`id_shipment_set`),
  CONSTRAINT `fk_st_shipment_status` FOREIGN KEY (`fk_status`) REFERENCES `st_status` (`id_status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_shipmentfiles`
--

DROP TABLE IF EXISTS `st_shipmentfiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_shipmentfiles` (
  `shipmentfileid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique ID',
  `shipmentid` int(11) NOT NULL COMMENT 'Associated shipment ID',
  `filename` varchar(45) DEFAULT NULL COMMENT 'Name of the excel file ',
  PRIMARY KEY (`shipmentfileid`),
  KEY `fk_shipmentfiles_shipment1` (`shipmentid`),
  CONSTRAINT `fk_shipmentfiles_shipment1` FOREIGN KEY (`shipmentid`) REFERENCES `st_shipment` (`id_shipment`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `st_study_template`
--

DROP TABLE IF EXISTS `st_study_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_study_template` (
  `studytemplateid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID for the study template',
  `templatename` varchar(45) DEFAULT NULL COMMENT 'Name for the template, for example SEED TEMPLATE, LEAF TEMPLATE',
  `comments` varchar(255) DEFAULT NULL COMMENT 'Additional info for the template',
  PRIMARY KEY (`studytemplateid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COMMENT='Template for different studies in the wet lab for leaf, seed';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_results_preferences`
--

DROP TABLE IF EXISTS `st_results_preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_results_preferences` (
  `resultpreferencesid` int(11) NOT NULL AUTO_INCREMENT,
  `preferencesname` varchar(255) DEFAULT NULL,
  `columnscount` int(11) DEFAULT NULL,
  `studytemplateid` int(11) DEFAULT NULL,
  PRIMARY KEY (`resultpreferencesid`),
  KEY `FK_st_results_preferences_st_template` (`studytemplateid`),
  CONSTRAINT `FK_st_results_preferences_st_template` FOREIGN KEY (`studytemplateid`) REFERENCES `st_study_template` (`studytemplateid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_study_template_params`
--

DROP TABLE IF EXISTS `st_study_template_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_study_template_params` (
  `templateparamid` int(11) NOT NULL AUTO_INCREMENT,
  `studytemplateid` int(11) NOT NULL COMMENT 'Study template parent for the parameter',
  `parametername` varchar(20) DEFAULT NULL COMMENT 'Short name for the parameter',
  `description` varchar(200) DEFAULT NULL COMMENT 'Large description for the parameter',
  `datatype` char(1) DEFAULT NULL COMMENT 'Data type for the parameter (CHAR, DATE, NUMERIC)',
  PRIMARY KEY (`templateparamid`),
  KEY `fk_study_template_params_study_template1` (`studytemplateid`),
  CONSTRAINT `fk_study_template_params_study_template1` FOREIGN KEY (`studytemplateid`) REFERENCES `st_study_template` (`studytemplateid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COMMENT='All the parameters associated to an study template';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_temp_sampleid`
--

DROP TABLE IF EXISTS `st_temp_sampleid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_temp_sampleid` (
  `idconsec` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) DEFAULT NULL,
  `nplant` int(11) DEFAULT NULL,
  `locationid` int(11) DEFAULT NULL,
  `seasonid` int(11) DEFAULT NULL,
  `sampleid` int(11) DEFAULT NULL,
  PRIMARY KEY (`idconsec`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_temp_samples`
--

DROP TABLE IF EXISTS `st_temp_samples`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_temp_samples` (
  `id_temp_sample` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL,
  `acc` varchar(255) NOT NULL,
  `plant_no` int(11) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `entry_no` int(11) DEFAULT NULL,
  `id_specie` int(11) NOT NULL,
  `id_location` int(11) NOT NULL,
  `id_season` int(11) NOT NULL,
  `id_researcher` int(11) NOT NULL,
  PRIMARY KEY (`id_temp_sample`),
  KEY `fk_st_tem_samp_st_organism` (`id_specie`),
  KEY `fk_st_tem_samp_st_location` (`id_location`),
  KEY `fk_st_tem_samp_st_season` (`id_season`),
  KEY `fk_st_tem_samp_st_investigator` (`id_researcher`),
  CONSTRAINT `fk_st_tem_samp_st_investigator` FOREIGN KEY (`id_researcher`) REFERENCES `st_investigator` (`investigatorid`),
  CONSTRAINT `fk_st_tem_samp_st_location` FOREIGN KEY (`id_location`) REFERENCES `st_location` (`locationid`),
  CONSTRAINT `fk_st_tem_samp_st_organism` FOREIGN KEY (`id_specie`) REFERENCES `st_organism` (`organismid`),
  CONSTRAINT `fk_st_tem_samp_st_season` FOREIGN KEY (`id_season`) REFERENCES `st_season` (`seasonid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_tissue`
--

DROP TABLE IF EXISTS `st_tissue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_tissue` (
  `tissueid` int(11) NOT NULL AUTO_INCREMENT,
  `tissuename` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tissueid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_lab_study`
--

DROP TABLE IF EXISTS `st_lab_study`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_lab_study` (
  `labstudyid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identification for each lab Study',
  `projectid` int(11) DEFAULT NULL COMMENT 'Project id',
  `title` varchar(255) DEFAULT NULL COMMENT 'Large name ',
  `objective` varchar(255) DEFAULT NULL COMMENT 'Description about the purpose of the study',
  `investigatorid` int(11) DEFAULT NULL COMMENT 'Breeders GID',
  `startdate` date DEFAULT NULL COMMENT 'Date start of the study',
  `enddate` date DEFAULT NULL COMMENT 'Finish date of the study',
  `platetype` char(1) DEFAULT NULL COMMENT 'Type of Plate (Separated Tubes  or Rack)',
  `platesize` int(11) DEFAULT NULL COMMENT 'The size plate, 96 or 384',
  `studytemplateid` int(11) NOT NULL COMMENT 'The study template ID used in this study',
  `numindiv` int(11) DEFAULT NULL COMMENT 'Number of Individuals in the study',
  `numofplates` int(11) DEFAULT NULL COMMENT 'Number of plates used for the study',
  `prefix` varchar(50) DEFAULT NULL COMMENT 'Prefix ',
  `organismid` int(11) DEFAULT NULL COMMENT 'Organism id (Maize or wheat)',
  `path_last_file_loaded` varchar(255) DEFAULT NULL COMMENT 'Path from the last file loaded to study',
  `name_last_file_loaded` varchar(255) DEFAULT NULL COMMENT 'Name from the last file loaded to study',
  `numcontrols` int(11) DEFAULT NULL COMMENT 'Tissue id',
  `tissueid` int(11) DEFAULT NULL,
  `locationid` int(11) DEFAULT NULL,
  `seasonid` int(11) DEFAULT NULL,
  `fk_status` char(1) NOT NULL DEFAULT 'N',
  `fk_load_type` int(11) DEFAULT NULL,
  `uses_padded` bit(1) DEFAULT b'0',
  PRIMARY KEY (`labstudyid`),
  KEY `fk_lab_study_study_template1` (`studytemplateid`),
  KEY `FK_st_lab_study_st_project` (`projectid`),
  KEY `FK_st_lab_study_st_investigator` (`investigatorid`),
  KEY `FK_st_lab_study_st_organism` (`organismid`),
  KEY `FK_st_lab_study` (`tissueid`),
  KEY `FK_st_lab_study_st_location` (`locationid`),
  KEY `FK_st_lab_study_st_season` (`seasonid`),
  KEY `FK_st_lab_study_st_status` (`fk_status`),
  KEY `FK_st_lab_study_st_load_type` (`fk_load_type`),
  CONSTRAINT `FK_st_lab_study` FOREIGN KEY (`tissueid`) REFERENCES `st_tissue` (`tissueid`),
  CONSTRAINT `FK_st_lab_study_st_investigator` FOREIGN KEY (`investigatorid`) REFERENCES `st_investigator` (`investigatorid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_st_lab_study_st_load_type` FOREIGN KEY (`fk_load_type`) REFERENCES `st_load_type` (`id_load_type`),
  CONSTRAINT `FK_st_lab_study_st_location` FOREIGN KEY (`locationid`) REFERENCES `st_location` (`locationid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_st_lab_study_st_organism` FOREIGN KEY (`organismid`) REFERENCES `st_organism` (`organismid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_st_lab_study_st_project` FOREIGN KEY (`projectid`) REFERENCES `st_project` (`projectid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_st_lab_study_st_season` FOREIGN KEY (`seasonid`) REFERENCES `st_season` (`seasonid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_st_lab_study_st_status` FOREIGN KEY (`fk_status`) REFERENCES `st_status` (`id_status`),
  CONSTRAINT `fk_lab_study_study_template1` FOREIGN KEY (`studytemplateid`) REFERENCES `st_study_template` (`studytemplateid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COMMENT='Information about the study in the Lab, for example plate ty';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_sample_detail`
--

DROP TABLE IF EXISTS `st_sample_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_sample_detail` (
  `studysampleid` int(11) NOT NULL AUTO_INCREMENT,
  `labstudyid` int(11) NOT NULL COMMENT 'Lab study ID for the sample',
  `breedergid` int(11) DEFAULT NULL COMMENT 'Breeders GID for the sample',
  `sampleid` int(11) DEFAULT NULL COMMENT 'Sample GID',
  `entryNo` int(11) DEFAULT NULL COMMENT 'Entry number in the study, this value is in the range of 1',
  `nplanta` int(11) DEFAULT NULL COMMENT 'Number of individual',
  `nval` varchar(255) DEFAULT NULL COMMENT 'Name ',
  `platename` varchar(50) DEFAULT NULL COMMENT 'Name of the plate',
  `plateloc` varchar(7) DEFAULT NULL COMMENT 'Location of the sample in the plate, for example A01, A10, H20',
  `selforsend` char(1) DEFAULT NULL COMMENT 'This sample was selected for send  for genotyping?',
  `currentlocation` int(11) DEFAULT NULL COMMENT 'Current Location for the sample, when value is NULL means that the sample has not been assigned to a location.',
  `lastmovdate` date DEFAULT NULL COMMENT 'Date of last movement of',
  `specie` varchar(100) DEFAULT NULL COMMENT 'Specie',
  `priority` varchar(255) DEFAULT NULL COMMENT 'Used for Comments in DART export',
  `controltype` varchar(4) DEFAULT NULL COMMENT 'If is a control and type of control R=Random, C=Control D=Dart Control',
  `locationid` int(11) DEFAULT NULL,
  `seasonid` int(11) DEFAULT NULL,
  PRIMARY KEY (`studysampleid`),
  KEY `fk_sample_detail_lab_study1` (`labstudyid`),
  KEY `fk_st_sample_detail_st_storage_location1` (`currentlocation`),
  KEY `FK_st_sample_detail_st_location` (`locationid`),
  KEY `FK_st_sample_detail_st_season` (`seasonid`),
  KEY `fk_st_sample_detail_st_status` (`selforsend`),
  CONSTRAINT `FK_st_sample_detail_st_location` FOREIGN KEY (`locationid`) REFERENCES `st_location` (`locationid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_st_sample_detail_st_season` FOREIGN KEY (`seasonid`) REFERENCES `st_season` (`seasonid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sample_detail_lab_study1` FOREIGN KEY (`labstudyid`) REFERENCES `st_lab_study` (`labstudyid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_st_sample_detail_st_status` FOREIGN KEY (`selforsend`) REFERENCES `st_status` (`id_status`),
  CONSTRAINT `fk_st_sample_detail_st_storage_location1` FOREIGN KEY (`currentlocation`) REFERENCES `st_storage_location` (`imslocid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COMMENT='Information for each sample (tube) in the plate or plates in';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `st_storage_mov`
--

DROP TABLE IF EXISTS `st_storage_mov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_storage_mov` (
  `idstoragemov` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique ID for each movement\n',
  `movdate` date DEFAULT NULL COMMENT 'Date of the movement',
  `movtype` char(2) DEFAULT NULL COMMENT 'movement type: IA=Inital Assigment, LC=Location change, SE=Sent to external company',
  `studysampleid` int(11) NOT NULL COMMENT 'ID of the sample',
  `destlocation` int(11) DEFAULT NULL COMMENT 'destination location for the sample NULL if the sample was sent to external company2',
  `quantity` int(11) DEFAULT NULL COMMENT 'Quantity of items moved',
  `comments` varchar(255) DEFAULT NULL COMMENT 'Additional comments for the movement\n',
  `hourmov` char(2) DEFAULT NULL COMMENT 'Hour for movement',
  `minmov` char(2) DEFAULT NULL COMMENT 'minute for the movement\n',
  PRIMARY KEY (`idstoragemov`),
  KEY `fk_st_storage_mov_st_sample_detail1` (`studysampleid`),
  KEY `fk_st_storage_mov_st_storage_location1` (`destlocation`),
  CONSTRAINT `fk_st_storage_mov_st_sample_detail1` FOREIGN KEY (`studysampleid`) REFERENCES `st_sample_detail` (`studysampleid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_st_storage_mov_st_storage_location1` FOREIGN KEY (`destlocation`) REFERENCES `st_storage_location` (`imslocid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_shipment_detail`
--

DROP TABLE IF EXISTS `st_shipment_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_shipment_detail` (
  `id_shipment_detail` int(11) NOT NULL AUTO_INCREMENT,
  `fk_shipment_set` int(11) NOT NULL,
  `fk_study_sampleid` int(11) NOT NULL,
  PRIMARY KEY (`id_shipment_detail`),
  UNIQUE KEY `fk_st_shipment_detail_set_sample` (`fk_shipment_set`,`fk_study_sampleid`),
  KEY `fk_st_shipment_detail_set` (`fk_shipment_set`),
  KEY `fk_st_shipment_detail_sample` (`fk_study_sampleid`),
  CONSTRAINT `fk_st_shipment_detail_sample` FOREIGN KEY (`fk_study_sampleid`) REFERENCES `st_sample_detail` (`studysampleid`),
  CONSTRAINT `fk_st_shipment_detail_set` FOREIGN KEY (`fk_shipment_set`) REFERENCES `st_shipment_set` (`id_shipment_set`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_plates_not_used`
--

DROP TABLE IF EXISTS `st_plates_not_used`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_plates_not_used` (
  `plateid` int(11) NOT NULL AUTO_INCREMENT,
  `platename` varchar(50) DEFAULT NULL,
  `labstudyid` int(11) DEFAULT NULL,
  PRIMARY KEY (`plateid`),
  KEY `FK_st_plates_not_used` (`labstudyid`),
  CONSTRAINT `FK_st_plates_not_used` FOREIGN KEY (`labstudyid`) REFERENCES `st_lab_study` (`labstudyid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_constants_cornell_report`
--

DROP TABLE IF EXISTS `st_constants_cornell_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_constants_cornell_report` (
  `idconstant` int(11) NOT NULL AUTO_INCREMENT,
  `labstudyid` int(11) NOT NULL,
  `sampleDNAConcentration` varchar(255) DEFAULT NULL,
  `sampleVolume` varchar(255) DEFAULT NULL,
  `sampleDNAMass` varchar(255) DEFAULT NULL,
  `preparer` varchar(255) DEFAULT NULL,
  `kingdom` varchar(255) DEFAULT NULL,
  `genus` varchar(255) DEFAULT NULL,
  `sourcelab` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idconstant`),
  KEY `FK_st_constants_lab_study` (`labstudyid`),
  CONSTRAINT `FK_st_constants_lab_study` FOREIGN KEY (`labstudyid`) REFERENCES `st_lab_study` (`labstudyid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_user_version`
--

DROP TABLE IF EXISTS `st_user_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_user_version` (
  `user_version_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `investigator_name` varchar(255) DEFAULT NULL,
  `investigator_email` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `password` varchar(45) NOT NULL DEFAULT 'dna2013',
  `investigator_id` int(11) NOT NULL DEFAULT '1',
  `organismid` int(11) NOT NULL,
  `idst_rol` int(11) NOT NULL,
  `status` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`user_version_id`),
  KEY `fk_st_user_version_1_idx` (`organismid`),
  KEY `fk_st_user_version_2_idx` (`idst_rol`),
  KEY `fk_st_user_version_3_idx` (`investigator_id`),
  CONSTRAINT `fk_st_user_version_1` FOREIGN KEY (`organismid`) REFERENCES `st_organism` (`organismid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_st_user_version_2` FOREIGN KEY (`idst_rol`) REFERENCES `st_rol` (`idst_rol`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_st_user_version_3` FOREIGN KEY (`investigator_id`) REFERENCES `st_investigator` (`investigatorid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `st_log`
--

DROP TABLE IF EXISTS `st_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_log` (
  `idst_log` int(11) NOT NULL AUTO_INCREMENT,
  `user_version_id` int(11) NOT NULL,
  `idst_log_type_operation` int(11) NOT NULL,
  `operation_date` date NOT NULL,
  `description` varchar(250) NOT NULL,
  `idst_log_type_entity` int(11) NOT NULL,
  `id_row_entity` int(11) NOT NULL,
  PRIMARY KEY (`idst_log`),
  KEY `fk_st_log_1_idx` (`user_version_id`),
  KEY `fk_st_log_2_idx` (`idst_log_type_operation`),
  KEY `fk_st_log_3_idx` (`idst_log_type_entity`),
  CONSTRAINT `fk_st_log_1` FOREIGN KEY (`user_version_id`) REFERENCES `st_user_version` (`user_version_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_st_log_2` FOREIGN KEY (`idst_log_type_operation`) REFERENCES `st_log_type_operation` (`idst_log_type_operation`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_st_log_3` FOREIGN KEY (`idst_log_type_entity`) REFERENCES `st_log_type_entity` (`idst_log_type_entity`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `st_user_funtions`
--

DROP TABLE IF EXISTS `st_user_funtions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_user_funtions` (
  `idst_user_funtions` int(11) NOT NULL AUTO_INCREMENT,
  `user_version_id` int(11) NOT NULL,
  `idst_funtions` int(11) NOT NULL,
  PRIMARY KEY (`idst_user_funtions`),
  KEY `fk_st_user_funtions_1_idx` (`idst_funtions`),
  KEY `fk_st_user_funtions_2_idx` (`user_version_id`),
  CONSTRAINT `fk_st_user_funtions_1` FOREIGN KEY (`idst_funtions`) REFERENCES `st_funtions` (`idst_funtions`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_st_user_funtions_2` FOREIGN KEY (`user_version_id`) REFERENCES `st_user_version` (`user_version_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TABLE IF NOT EXISTS `st_program` (
  `idst_program` INT(11) NOT NULL AUTO_INCREMENT,
  `organismid` INT(11) NOT NULL,
  `letter_code` VARCHAR(3) NOT NULL,
  `program_name` VARCHAR(200) NOT NULL,
  `description` VARCHAR(245) NOT NULL,
    `status` BIT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`idst_program`),
  INDEX `fk_st_program_1_idx` (`organismid` ASC),
  CONSTRAINT `fk_st_program_1`
    FOREIGN KEY (`organismid`)
    REFERENCES `st_organism` (`organismid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE TABLE IF NOT EXISTS `st_purpose` (
  `idst_purpose` INT(11) NOT NULL AUTO_INCREMENT,
  `organismid` INT(11) NOT NULL,
  `letter_code` VARCHAR(3) NOT NULL,
  `purpose_name` VARCHAR(200) NOT NULL,
  `description` VARCHAR(245) NOT NULL,
    `status` BIT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`idst_purpose`),
  INDEX `fk_st_purpose_1_idx` (`organismid` ASC),
  CONSTRAINT `fk_st_purpose_1`
    FOREIGN KEY (`organismid`)
    REFERENCES `st_organism` (`organismid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
--
-- Table structure for table `st_sample_det_result`
--

DROP TABLE IF EXISTS `st_sample_det_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_sample_det_result` (
  `idsampledetresult` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique ID for each parameter result for the sample',
  `studysampleid` int(11) NOT NULL COMMENT 'Sample ID related with this result',
  `templateparamid` int(11) NOT NULL COMMENT 'Parameter ID assigned to this result',
  `result` varchar(255) DEFAULT NULL COMMENT 'Result according to the parameter for this sample',
  PRIMARY KEY (`idsampledetresult`),
  KEY `fk_sample_det_result_sample_detail1` (`studysampleid`),
  KEY `fk_sample_det_result_study_template_params1` (`templateparamid`),
  CONSTRAINT `fk_sample_det_result_sample_detail1` FOREIGN KEY (`studysampleid`) REFERENCES `st_sample_detail` (`studysampleid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sample_det_result_study_template_params1` FOREIGN KEY (`templateparamid`) REFERENCES `st_study_template_params` (`templateparamid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COMMENT='The result for each SAMPLE DETAIL according to the parameter';
/*!40101 SET character_set_client = @saved_cs_client */;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


-- Dump completed on 2017-03-13 16:13:18
