

/*!40101 SET character_set_client = @saved_cs_client */;

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
INSERT INTO `st_storage_location` VALUES (1,'GRAL LOC','GENERAL LOCATIONS','GENERAL LOCATIONS',NULL),(2,'CIMMYT-HQ','CIMMYT-HQ','CIMMYT-HQ',1),(5,'CIMMYT MEX','CIMMYT MEXICO','CIMMYT MEX',2),(6,'PRINCIPAL','PRINCIPAL LABORATORY','PRINCIPAL LABORATORY',5),(7,'FREEZER-30','FREEZER -30','MINUS 30 FREEZER',6),(8,'FREEZER-80','FREEZER -80','MUNIS 80 FREEZER',6),(9,'MAIN DRYER','MAIN DRYER','MAIN DRYER FOR LAB',6),(10,'KBIOSCIENC','KBIOSCIENCE','SENDED TO KBIOSCIENCE',1),(11,'DART','DART','SENDED TO DART',1),(12,'CORNELL','CORNELL','SENDED TO CORNELL ',1),(13,'','SAGA Genetic Analysis Service for AgriculturA','',1);
/*!40000 ALTER TABLE `st_storage_location` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `st_company`
--

LOCK TABLES `st_company` WRITE;
/*!40000 ALTER TABLE `st_company` DISABLE KEYS */;
INSERT INTO `st_company` VALUES (1,'K-BIOSCIENCE','KBioscience Unit 7 Maple Park Hoddesdon Herts','info@kbioscience.co.uk','','1 978 232 9430 ',10),(2,'Institute for Genomic Diversity','130 Biotechnology Building Cornell University Ithaca, NY 14853-2703','','Ed Buckler','',13),(3,'Diversity Arrays Technology Pty Ltd','1 Wilf Crane Crescent, Yarralumla PO Box 7141, Yarralumla, \r\nACT 2600, Australia','a.kilian@diversityarrays.com','Andrzej Kilian','+61 2 6122 7319 Fax +61 2 61227333',12),(4,'SAGA Genetic Analysis Service for AgriculturA','Carretera Mexico-Veracruz KM45. El Batan. Texcoco. Estado de Mexico.','C.Sansaloni@cgiar.org','Sansaloni Carolina Paola','+52 (55) 5804 2004, Ext. 2210',13);
/*!40000 ALTER TABLE `st_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_investigator`
--

LOCK TABLES `st_investigator` WRITE;
/*!40000 ALTER TABLE `st_investigator` DISABLE KEYS */;
INSERT INTO `st_investigator` VALUES (1,'RL','Researcher Lead');
/*!40000 ALTER TABLE `st_investigator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_user_version`
--

LOCK TABLES `st_user_version` WRITE;
/*!40000 ALTER TABLE `st_user_version` DISABLE KEYS */;
INSERT INTO `st_user_version` VALUES (1,'Administrator','email@domin.org','Administrator User',NULL,'2.0','Admin',1,1,1,'');
/*!40000 ALTER TABLE `st_user_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `st_season`
--

LOCK TABLES `st_season` WRITE;
/*!40000 ALTER TABLE `st_season` DISABLE KEYS */;
INSERT INTO `st_season` VALUES (1,'Mixed','More than one season.');
/*!40000 ALTER TABLE `st_season` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `st_location` WRITE;
/*!40000 ALTER TABLE `st_location` DISABLE KEYS */;
INSERT INTO `st_location` VALUES (1,'Mixed','More than one location.');
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


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-18 14:41:48
