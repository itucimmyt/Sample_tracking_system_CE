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
INSERT INTO `st_investigator` VALUES (1,'CI','CIMMYT1',1),(2,'CM','CIMMYT2',1);
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
INSERT INTO `st_storage_location` VALUES (1,'GRAL LOC','GENERAL LOCATIONS','GENERAL LOCATIONS',NULL),(2,'CIMMYT-HQ','CIMMYT-HQ','CIMMYT-HQ',1),(5,'CIMMYT MEX','CIMMYT MEXICO','CIMMYT MEX',2),(6,'PRINCIPAL','PRINCIPAL LABORATORY','PRINCIPAL LABORATORY',5),(7,'FREEZER-30','FREEZER -30','MINUS 30 FREEZER',6),(8,'FREEZER-80','FREEZER -80','MUNIS 80 FREEZER',6),(9,'MAIN DRYER','MAIN DRYER','MAIN DRYER FOR LAB',6),(10,'KBIOSCIENC','KBIOSCIENCE','SENDED TO KBIOSCIENCE',1),(11,'DART','DART','SENDED TO DART',1),(12,'CORNELL','CORNELL','SENDED TO CORNELL ',1),(13,'','SAGA','',1),(14,'','SAGA','',1),(15,'','SAGA','',1),(16,'','Diversity Arrays Technology Pty Ltd','',1),(17,'','SAGA-Centro Nacional de Recursos Geneticos','',1)
,(18,'','SAGA','',1),(19,'','Intertek 1','',1),(20,'','Intertek 2','',1),(21,'','Intertek-Hyderabad','',1),(22,'','Intertek-Sweeden','',1),(23,'','Intertek-Sweeden','',1);
/*!40000 ALTER TABLE `st_storage_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_company`
--

LOCK TABLES `st_company` WRITE;
/*!40000 ALTER TABLE `st_company` DISABLE KEYS */;
INSERT INTO `st_company` VALUES (1,'K-BIOSCIENCE','KBioscience Unit 7 Maple Park Hoddesdon Herts','info@kbioscience.co.uk','','1 978 232 9430 ',10),
								(2,'Institute for Genomic Diversity','130 Biotechnology Building Cornell University Ithaca, NY 14853-2703','','Ed Buckler','',12),
								(3,'Diversity Arrays Technology Pty Ltd','Bldg 3, Lv D, University of Canberra,\nKirinari st., Bruce, ACT 2617, Australia','a.kilian@diversityarrays.com','Andrzej Kilian','+ 61 2 6122 7300',16),
								(4,'SAGA','Carretera Mexico Veracruz KM45. El Batan. Texcoco. Estado de Mexico.','C.Sansaloni@cgiar.org','Sansaloni, Carolina Paola','+52 (55) 5804 2004, Ext. 2210',18),
								(5,'Intertek-Hyderabad','Intertek-Hyderabad','agritech.india@intertek.com','Petra van Roggen','',21),
								(6,'Intertek-Sweeden','Intertek-Sweeden','agritech.sweden@intertek.co','Petra van Roggen','',23);
/*!40000 ALTER TABLE `st_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_study_template`
--

LOCK TABLES `st_study_template` WRITE;
/*!40000 ALTER TABLE `st_study_template` DISABLE KEYS */;
INSERT INTO `st_study_template` VALUES (1,'SEED TEMPLATE',''),(2,'LEAF TEMPLATE','DW ENVIO '),(6,'LEAF-ME','Include information used for wheat');
INSERT INTO `st_study_template` VALUES (7,'SD-LEAF-DArT','DArT array template'),(9,'LEAF-ME-INTRID','Include information used for wheat'),(10,'BW ',' ENVÍO  DNA BW '),(11,'PopVeery-Attila','GBS geno-Sister lines-Genebank'),(12,'ESWYT','GBS genon'),(15,'YTB1415','GBS  geno   BW'),(16,'CIMCOG 2','35K geno'),(17,'M49IWSN','MAS '),(18,'YPT ','YPT Chinese Spring comparison'),(19,'MOROCO POP','FHB/STB mapping'),(20,'YTB14-15','GBS geno'),(21,'YTB14-15-2','GBS geno'),(22,'YTB14-15-3','GBS geno'),(23,'DW15','D15B_F7HR_GSLR  ENVIO DE ADN '),(24,'Template_General_1','Plantilla general / Template General'),(25,'KSU Template ','The template with the fields that are required to fill out by WML while sending DNA samples to KSU.');
/*!40000 ALTER TABLE `st_study_template` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `st_study_template_params`
--

LOCK TABLES `st_study_template_params` WRITE;
/*!40000 ALTER TABLE `st_study_template_params` DISABLE KEYS */;
INSERT INTO `st_study_template_params` VALUES (1,1,'SAMPLEORIGIN','Sample origin','C'),(2,1,'DNAEXTLOC','DNA extraction location','C'),(3,1,'CONCUGL','Concentration ug/ul','N'),(4,1,'VOLUL','Volume ul','N'),(5,1,'ORIGQTYUG','Origional Quantity ug','N'),(6,1,'DNAEXTMTHD','DNA extraction method ID','C'),(7,2,'DATEHARVEST','Date of Harvest','D'),(8,2,'SAMPVESS','Sample vessel','C'),(9,2,'SAMPLETYPE','Sample type  (bulk/single plant)','C'),(10,2,'LYOPDTIN','Lyophilizer date in','D'),(11,2,'LYOPDTOUT','Lyophilizer date out','D'),(12,2,'SAMPORIG','Sample origin e.g. field / greenhouse, seed lot','C'),(15,6,'CID','Cross Identifier used in IWIS2','N'),(16,6,'SID','Selection Identifier used in IWIS2','N'),(17,6,'SelHis','Selection History recorded for the plant','C'),(18,6,'Line','Pedigree Origin  ','C'),(19,6,'Abbrev','Cross abreviation ','C'),(20,6,'OrPlate','Original plate used to send DNA','C'),(21,7,'PlateID','PlateID','N'),(22,7,'Row','Row','N'),(23,7,'Column','Column','N'),(24,7,'Organism','Organism','C'),(25,7,'Species','Species','C'),(26,7,'Genotype','Genotype ID','N'),(27,7,'Tissue','Tissue','C'),(28,7,'Comments','Comments','C'),(51,9,'INTRID','Introduction Trial ID IWIS2','C'),(52,9,'CID','Cross Identifier used in IWIS2','N'),(53,9,'OrPlate','Original plate used to send DNA','C'),(54,9,'Line','Pedigree Origin','C'),(55,9,'Abbrev','Cross abreviation','C'),(56,9,'SID','Selection Identifier used in IWIS2','N'),(57,9,'SelHis','Selection History recorded for the plant','C'),(58,10,'BW','2015','N'),(59,11,'SISTER','GBS GENO','N'),(60,12,'ESWYT','GBS geno','N'),(66,15,'sampleorigin','sample origen','N'),(67,15,'ytb14-15','GBS geno','C'),(68,16,'35K geno','CIMCOG','N'),(69,17,'M49IWSN','MAS','C'),(70,18,'GBS GENO','DNA','C'),(71,19,'MAPPING','MOROCO','C'),(72,20,'YTB14-15','GBS geno','C'),(73,21,'GBS','GBS geno','C'),(74,22,'YTB14-15-3','GBS geno','C'),(75,23,'DW15','ADN','C'),(76,24,'sample_number','Sequential DNA sample number within the batch','N'),(77,24,'par1_donor','parent 1 of sample which can be the donor parent','C'),(78,24,'sample_group_cycle','Cycle eg backcrossing cycle for a sample group','C'),(79,24,'sample_group','Sample Group','C'),(80,24,'germplasm_population','germplasm that can be grouped together for analysi','C');
INSERT INTO `st_study_template_params` VALUES (81,24,'germplasm_heterotic','Heterotic group within a species','C'),(82,24,'germplasm_subsp','Sub-species','C'),(83,24,'trial_name','Trial name for field experiment that the sample is','C'),(84,24,'germplasm_type','Type of material for GID/Germplasm name - describe','C'),(85,24,'selection_history','Selection history of the germplasm','C'),(86,24,'species_taxonomy','Species - taxonomic name, e.g. Triticum aestivum L','C'),(87,24,'markers','Semi-colon separated list if there are 10 or fewer','C'),(88,24,'mgid','Management germplasm ID to group together germplas','N'),(89,24,'par_sample_group','Is a parent of a sample_group','C'),(90,24,'par4','parent 4 of sample','C'),(91,24,'par2_recurrent','parent 2 of sample which can be the recurrent pare','C'),(92,24,'par3','parent 3 of sample','C'),(93,24,'stock_id','Stock ID','C'),(94,24,'origen','Seed source / origen','C'),(95,24,'publish_data','Y or N?','C'),(96,24,'transgenic','Y or N?','C'),(97,24,'project_pmu_abbr','PMU abbreviation for project','C'),(98,24,'project_pmu_id','PMU numerical ID for project','C'),(99,24,'icc','billing ICC per sample','C'),(100,24,'unique_plant_id','Link to a unique plant in breeding database','C'),(101,24,'unique_plot_id','Link to a unique plot in breeding database','C'),(102,24,'dna_suspension_buff','buffer that the DNA is resuspended in','C'),(103,24,'extraction_protocol','abbreviation for extraction protocol, e.g. CTAB','C'),(104,24,'sample_type','[empty] or \"B\"=blank or \"C\"=control','C'),(105,24,'plate_barcode','plate_barcode','C'),(106,24,'subject_barcode','subject_barcode','C'),(107,24,'ref_sample','[empty] or Y for a reference sample for an MGID','C'),(108,24,'no_plant','Additional name assigned to a plant, e.g. PL-12','C'),(109,24,'dna_concentrat_unit','Unit for the DNA concentration, e.g. ug/ml','C'),(110,24,'dna_concentration','DNA concentration','C'),(111,24,'dna_quality','Free text description, e.g. High/Low','C'),(112,24,'tech_extract_id','id of technician who extracted DNA','C'),(113,24,'tech_extract_name','last; first name of technician who extracted DNA','C');
INSERT INTO `st_study_template_params` VALUES (114,24,'other_information','free text field to add any other information','C'),(115,24,'dna_isolation_date','YYYY-MM-DD Date of DNA isolation','D'),(116,24,'original_plate_id','Original plate_id / name for historical data','C'),(117,24,'original_sample_id','Original sample_id / name for historical data','C'),(118,24,'dna_quantity_unit','Unit for DNA quantity','C'),(119,24,'dna_quantity','DNA quantity','N'),(120,24,'tech_extract_id','id of technician who extracted DNA','C'),(121,24,'tech_extract_name','last; first name of technician who extracted DNA','C'),(122,24,'plate_barcode','plate_barcode','C'),(123,24,'sample_type','[empty] or \"B\"=blank or \"C\"=control','C'),(124,24,'ref_sample','[empty] or Y for a reference sample for an MGID','C'),(125,24,'subject_barcode','subject_barcode','C'),(126,24,'dna_concentrat_unit','Unit for the DNA concentration, e.g. ug/ml','C'),(127,24,'no_plant','Additional name assigned to a plant, e.g. PL-12','C'),(128,24,'dna_quality','Free text description, e.g. High/Low','C'),(129,24,'dna_concentration','DNA concentration','N'),(130,24,'project_pmu_abbr','PMU abbreviation for project','C'),(131,24,'transgenic','Y or N?','C'),(132,24,'icc','billing ICC per sample','C'),(133,24,'project_pmu_id','PMU numerical ID for project','C'),(134,24,'germplasm_subsp','Sub-species','C'),(135,24,'trial_name','Trial name for field experiment that the sample is','C'),(136,24,'germplasm_population','germplasm that can be grouped together for analysi','C'),(137,24,'germplasm_heterotic','Heterotic group within a species','C'),(138,24,'species_taxonomy','Species - taxonomic name, e.g. Triticum aestivum L','C'),(139,24,'markers','Semi-colon separated list if there are 10 or fewer','C'),(140,24,'germplasm_type','Type of material for GID/Germplasm name - describe','C'),(141,24,'selection_history','Selection history of the germplasm','C'),(142,24,'sample_group','Sample Group','C'),(143,24,'sample_group_cycle','Cycle eg backcrossing cycle for a sample group','C'),(144,24,'par1_donor','parent 1 of sample which can be the donor parent','C'),(145,24,'sample_number','Sequential DNA sample number within the batch','N'),(146,24,'stock_id','Stock ID','C'),(147,24,'par3','parent 3 of sample','C'),(148,24,'publish_data','Y or N?','C'),(149,24,'origen','Seed source / origen','C'),(150,24,'sample_parent_prop','Type of parent: donor, recurrent, [empty]','C'),(151,24,'mgid','Management germplasm ID to group together germplas','N'),(152,24,'par2_recurrent','parent 2 of sample which can be the recurrent pare','C'),(153,24,'par4','parent 4 of sample','C'),(154,24,'dna_suspension_buff','buffer that the DNA is resuspended in','C'),(155,24,'extraction_protocol','abbreviation for extraction protocol, e.g. CTAB','C'),(156,24,'unique_plant_id','Link to a unique plant in breeding database','C'),(157,24,'unique_plot_id','Link to a unique plot in breeding database','C'),(158,25,'project_name','Project name','C'),(159,25,'external_id','External ID','N'),(160,25,'sample_name','Sample name','C'),(161,25,'tissue_id','Tissue ID','N'),(162,25,'well_labels','Well labels','N'),(163,25,'dna_date (yyyymmdd)','DNA extraction date','C'),(164,25,'date','Date','N'),(165,25,'notes','Notes','C'),(166,25,'species','Species','C'),(167,25,'dna_person','DNA person','C'),(168,25,'extraction','DNA extraction methods','C'),(169,25,'tissue_type','Tissue type','C'),(170,25,'plate_num','Plate number','N'),(171,25,'well_01A','Well 01A','C'),(172,25,'well_A01','Well A01','C'),(173,25,'sample_id','Sample ID','C'),(174,25,'plate_name','Plate name','C'),(175,25,'plate_id','Plate ID','C');
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
INSERT INTO `st_location` VALUES (1,'Mixed','More than one location.'),(2,'Germplasm Bank','Germplasm Bank'),(3,'Agua Fria','AF Agua Fria'),(4,'Tlaltizapan','TL Tlaltizapan'),(5,'Batan','BT Batan'),(6,'Santa Lucia Station INIFAP CEVAMEX','Santa Lucia Station INIFAP CEVAMEX'),(7,'Obregon','Y Obregon'),(8,'Toluca','MX Toluca Station'),(10,'Mexicali','Mexicali'),(11,'Tepatitlan Jalisco','Jalisco'),(12,'Estados Unidos Americanos','USA'),(13,'Genebank','procedencia-Genebank'),(14,'Quality lab','lab '),(15,'Celaya','Celaya INIFAP');
/*!40000 ALTER TABLE `st_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_constants_cornell_report`
--

--LOCK TABLES `st_constants_cornell_report` WRITE;
--/*!40000 ALTER TABLE `st_constants_cornell_report` DISABLE KEYS */;
--INSERT INTO `st_constants_cornell_report` VALUES (2,79,'100ng/ul','100ul','10ug','','Plant','','CIMMYT'),(3,77,'100ng/ul','100ul','10ug','HS','Plant','','CIMMYT'),(4,100,'100ng/ul','100ul','10ug','','Plant','','CIMMYT');
--/*!40000 ALTER TABLE `st_constants_cornell_report` ENABLE KEYS */;
--UNLOCK TABLES;

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
INSERT INTO `st_season` VALUES (1,'Mixed','More than one season.'),(2,'Main','Main'),(3,'11A','11A'),(4,'11B','11B'),(6,'10A','10A'),(7,'11','PATHGreenHouse'),(8,'screenhouse','screenhouse'),(9,'Y11-12','Y11-12'),(10,'2011-12','2011-12'),(11,'MXI14-15','Ciclo Agrícola 14-15'),(12,'Y13-14','Obregon season'),(13,'BATAN- 2015','2015'),(14,'B2015','BATAN'),(15,'Y14-15','Y14-15'),(16,'B2014','Batan 2014 '),(17,'Y16-17','Obregon 2016-2017'),(18,'2016A','2016A'),(19,'2015B','15-16'),(20,'2016B','16-17'),(21,'2017A','2017A'),(22,'2015A','2015A');
/*!40000 ALTER TABLE `st_season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_project`
--

LOCK TABLES `st_project` WRITE;
/*!40000 ALTER TABLE `st_project` DISABLE KEYS */;
INSERT INTO `st_project` VALUES (2,'SEED','seed','GWAS','gwas',NULL),(3,'SEED','Project Seed','DIV','Diversity',40787),(4,'SEED','Seed Project','Map','Genetic mapping',1542),(5,'SEED','Seed Project','Synt','Synthetics',1812),(6,'SEED','Seed Project','RYET','RYE_Trans',NULL),(7,'SEED','Seed Project','WAcc','Within_Acc',144),(8,'SEED','Seed Project','YT&N','Yield_T&N',4954),(9,'SEED','Seed Project','DivS','Diversity_Survey',433),(10,'SEED','Seed Project','TAUS','Taushii',718),(11,'SEED','Seed Project','GSCA','GS_Candidates',NULL),(12,'SEED','Seed Project','CRS','Cruzas',NULL),(13,'SEED','Seed Project','MIX','Mixed',2288),(14,'PATH','Maize Pathology','QTLM','QTL Mapping',NULL),(15,'F2GS','F2GS-CIMMYT','GSEL','genomic selection genotyping ',2966),(16,'SEED','Seed Project','DICD','DICDISTA',654),(17,'SEED','Seed Project','SYBT','SYN_YP',2274),(18,'SEED','Seed Project','IDYN','IDYN',2068),(19,'SEED','Seed Project','SPE','Others',509),(20,'SEED','Seed Project','FIGS','MES',1127),(22,'SEED','Seed Project','WAMI','WAMI',282),(23,'SEED','Seed Project','SUBS','CHRS SUBST',376),(24,'SEED','1Seed Tepatitlan Jal','MAT','TEPATITLAN JAL',979),(25,'SEED','Seed Mult. y Variedades/Hist. Checks','MVHC','Multilineas y Variedades/Historical Checks',85),(26,'SEED','Seed Kenya Swara','KNSW','Kenya Swara',188),(27,'SEED','Seed Project','FHB','FHB',282),(28,'SEED','Seed Project','SB','SB',470),(29,'SEED','SeeD Project','PHOS','2SEED PHOSPH.USE EFFICIENCY IO',832),(30,'SEED','SeeD Project','ELIT','ELITE',25),(31,'SEED','Seed Project','SDP2','Septoria ED P2',282),(32,'SEED','Seed Project','SRP2','Septoria Ravi Singh  P2',188),(33,'SEED','Seed Project','SBP3','Spot Blotch P3',564),(34,'SEED','Seed Project','SBP4','Spot Blotch  P4',188),(35,'SEED','Seed Project','ALAG','Alien & Aegilops',568),(36,'SEED','Seed Project','IMTI','IMTI Population (GHB)',1349),(37,'SEED','Seed Project','PBKB','PBW 343* KINGBIRD',400),(38,'SEED','Seed Project','PBMU','PBW 343* MUU',150),(39,'SEED','Seed Project','PBKS','PBW 343* KENYA SWARA',225),(40,'SEED','Seed Project','ALEM','Alemania',354),(41,'SEED','Seed Project','SIRI','Siria',1187),(42,'SEED','Seed Project','SEPT','SEPTORIA',470),(43,'SEED','Seed Project','KSG','KSG',6654),(44,'SEED','Seed Project','SPP','SPP (Genebank material)',1161),(45,'SEED','Seed Project','KBP','Karnal Bunt Popultation',222),(46,'SEED','Seed Project','XCT','XCT, A, B, C, D',912),(47,'SEED','Seed Project','POP','POPULATION',9733),(48,'SEED','Seed Project','RIL','Exotic-Elite',19),(49,'SEED','Seed Project','LTP','LTP',3241),(50,'SEED','Project Seed','ELEX','ELITE & EXOTIC',42),(51,'SEED','Seed Project','VERY','Plates from Sussane (VEERY, ATTILA, BABAX)',249),(52,'SEED','Seed Project','ICAR','Samples ICARDA',33882),(53,'SEED','Seed Project','WR','Wheat Wild relative greenhouse',2205),(54,'SEED','Seed Project','MIXE','Recalling Plates',384),(55,'SEED','Seed Project','PBW','PBW343 KENYA SWARA-MUU',375),(56,'DW14','WMB_LAB','geno','DNA extr, MAP, MAS',1310),(57,'DW15','WMB LAB','GENO','DNA EXTRR. MAP. MAS',225),(58,'SEED','Seed Project','LTPP','LTP, SSD, ELITE, PARENTS',3185),(59,'SEED','Seed Project','PARE','POP&TC1F4',29),(60,'PopV','PopVeery-Attila','GBS','GBS geno',249),(61,'ESWY','GBS geno','GBS','ESWYT-GBS geno',982),(62,'BW15','YTB14-15','GBS','GBS geno',3800),(64,'BW15','CIMCOG','35K','CIMCOG 2',60),(65,'BW15','M49IWSN','MAS','M49IWSN',973),(66,'BW','YPT Chinesse Spring comparison','GBS','GBS geno',54),(67,'BW15','FHB/STB mapping','15K','MOROCO POP',392),(68,'BW15','YTB14-15','GBSY','GBS GENO',9100),(69,'DW15','envio AND','D15B','envio AND',4844),(70,'DW15','durum wheat','D152','envío ADN',NULL),(71,'SEED','Seed Project','PBWC','Crosses PBW343/JUCHI, DINIZA, PAVON, K.N, K.K',323),(72,'SEED','Siva samples','DURO','PANEL DUROS',225),(73,'SEED','Seed Project','SARD','SARDARI MATERIAL',406),(74,'SEED','Project Seed','MEXP','Mexican Parents from Celaya',213),(75,'SEED','Seed Project','MXIR','Mexican LR & Iran LR, Mex Checks',651),(76,'SEED','Seed Project','SPDC','Spanish, Dicoccums, Synt & Parents',865),(77,'SEED','Seed Project','POP6','POBLACION 6 M4 2016',165),(78,'SEED','Seed Project','LTPD','LTPDrought M4 2016',821),(79,'SEED','Seed Project','LTPH','LTP HEAT M4 2016',290),(80,'SEED','CRP genotyping','CRP','CRP genotyping',6247),(81,'BW17','Bread Wheat 2017','MBAG','Marker-assisted backcrossing',1102),(82,'PT17','Physyiology program 2017','GS','Genomic selection',1304),(83,'SEED','Seed Project','LTPI','LTP 1 Obregon',1575),(84,'SEED','Seed Project','MEXL','Mexican Landraces',1246),(85,'SEED','Seed Project','IRL','Iranian Landraces',242),(86,'HW16','HW16','QTAD','QTAD',300),(87,'PT16','PT16','GSBM','GSBM',1178),(88,'PT16','PT16','PVBM','PVBM',148),(89,'BW16','BW16','GSBL','GSBL',9940),(90,'DW17','DW17','MVAC','MVAC',92),(91,'BW16','BW16','MSAG','MSAG',4428),(92,'DW16','DW16','MSAC','MSAC',1118),(93,'DW16','DW16','MVAC','MVAC',260),(94,'TC16','TC16','PVAC','PVAC',386),(95,'DW16','DW16','QTAC','QTAC',NULL),(96,'BW17','BW17','PVSD','PVSD',NULL);
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
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CA','CIMMYT Asia office in Hyderabad','Asia office in Hyderabad');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CC','CIMMYT China office in Kunming','China office in Kunming');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CE','CIMMYT Ethiopia office in Addis Ababa','Ethiopia office in Addis Ababa');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CH','CIMMYT Highland program in Mexico','Highland program in Mexico');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CK','CIMMYT Kenya office in Nairobi','Kenya office in Nairobi');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CB','CIMMYT Latin America office in Cali','Latin America office in Cali');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CL','CIMMYT Lowland Tropical program in Mexico','Lowland Tropical program in Mexico');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CS','CIMMYT Sub-tropical program in Mexico','Sub-tropical program in Mexico');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CZ','CIMMYT Zimbabwe office in Harare','Zimbabwe office in Harare');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'EX','External Samples','External samples');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'SD','Seeds of Discovery - MasAgro Biodiversidad','Seeds of Discovery');
Insert into st_program (organismid,letter_code,program_name,description) values (2,'CG','Global / general materials','Global / General');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'BW','Bread Wheat','BW, HAR');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'DW','Durum Wheat','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'BA','Barley','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'TC','Triticale','TCL');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'WW','Winter Wheat','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'3','Physiology','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'PA','Pathology','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'QL','Quality','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'HW','Hybrid Wheat','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'WB','Wheat Germplasm Bank','WGB');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'GR','Genetic Resources (e.g. Seed Wheat)','SD');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'WC','Wide Crosses','');
Insert into st_program (organismid,letter_code,program_name,description) values (1,'EX','External','');
/*!40000 ALTER TABLE `st_program` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `st_purpose` WRITE;
/*!40000 ALTER TABLE `st_program` DISABLE KEYS */;
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'MS','Marker-assisted selection','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'MB','Marker-assisted backcrossing','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'MR','Marker-assisted recurrent selection','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'GS','Genomic selection','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'ID','Identity verification','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'HZ','Residual Heterozygosity','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'PV','Parental / Pedigree Verification','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'FP','Fingerprinting of new CMLs, etc.','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'GW','Genome-wide association study','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'DV','Diversity analysis','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'DC','Discovery (general)','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'FM','Fine-mapping','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'QT','QTL mapping','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'MV','Marker validation','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'YY','Mixed purpose','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (1,'ZZ','Unknown purpose (for historical dataset)','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'MS','Marker-assisted selection','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'MB','Marker-assisted backcrossing','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'MR','Marker-assisted recurrent selection','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'GS','Genomic selection','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'ID','Identity verification','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'HZ','Residual Heterozygosity','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'PV','Parental / Pedigree Verification','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'FP','Fingerprinting of new CMLs, etc.','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'GW','Genome-wide association study','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'DV','Diversity analysis','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'DC','Discovery (general)','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'FM','Fine-mapping','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'QT','QTL mapping','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'MV','Marker validation','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'YY','Mixed purpose','');
Insert into st_purpose (organismid,letter_code,purpose_name,description) values (2,'ZZ','Unknown purpose (for historical dataset)','');
/*!40000 ALTER TABLE `st_purpose` ENABLE KEYS */;
UNLOCK TABLES
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-13 16:18:37
