 Alter Table st_sampleid ADD INDEX index_repeat_samples ( projectid,gid,nplant,locationid,seasonid) USING HASH;
