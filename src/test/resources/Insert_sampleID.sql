$$
CREATE TRIGGER Insert_sampleID AFTER INSERT  ON st_sample_detail
FOR EACH ROW
	BEGIN
		DECLARE id_SampleID int;
		DECLARE id_project int;
                declare pactions  varchar(1000);
		SET id_project = null;
		SELECT lab.projectid into id_project 
		FROM st_lab_study lab where lab.labstudyid = NEW.labstudyid;
		select Count(*)
		INTO id_SampleID
		   FROM st_sampleid id
			WHERE 
				id.gid = NEW.breedergid
				and id.nplant = NEW.nplanta
				and id.locationid = NEW.locationid
				and id.seasonid = NEW.seasonid
				and id.projectid = id_project;

       IF(id_SampleID = 0)
       THEN
	  
		  IF(NEW.nplanta IS NOT NULL AND  NEW.breedergid IS NOT NULL)
			THEN
				Insert into st_sampleid 		
					values(0,id_project,NEW.breedergid,NEW.nplanta,NEW.locationid, NEW.seasonid, NEW.sampleid);

			END IF;
        END IF;
	END
$$