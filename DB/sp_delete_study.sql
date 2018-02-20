delimiter //
CREATE PROCEDURE `sp_delete_study`(id_study INT)
begin
        DECLARE not_found CONDITION FOR SQLSTATE '45000';
        start transaction;
                SET @id_project = null;
                select @id_project := `projectid`
                from `st_lab_study`
                where `labstudyid` = id_study;
                if (@id_project is null) then
                        rollback;
                        SIGNAL not_found SET MESSAGE_TEXT = 'study does not exist.';
                elseif exists(select `labstudyid` from `st_sample_detail`
                                          where `labstudyid` = id_study and `selforsend` <> 'N')then
                        rollback;
                        SIGNAL not_found SET MESSAGE_TEXT = 'cannot delete study, some samples are marked for sending.';
                else
                        delete I
                        from `st_sampleid` I
                        inner join `st_sample_detail` D ON
                                  D.`sampleid` = I.`sampleid`
                          AND I.`projectid` = @id_project
                          and D.`labstudyid` = id_study
                          and I.`gid` = D.`breedergid`
                          and I.`nplant` = D.`nplanta`
                          and I.`locationid` = D.`locationid`
                          and I.`seasonid` = D.`seasonid`;
                        delete C
                        from `st_sample_det_result` C
                        inner join `st_sample_detail` D on
                              D.`studysampleid` = C.`studysampleid`
                          and D.`labstudyid` = id_study;
                        delete R
                        from `st_constants_cornell_report` R
                        inner join `st_lab_study` D on
                              D.`labstudyid` = R.`labstudyid`
                          and D.`labstudyid` = id_study;
                        delete from `st_sample_detail`
                        where `labstudyid` = id_study;
                        delete from `st_lab_study`
                        where `labstudyid` = id_study;
                        commit;
                end if;
        commit;
 end//
delimiter ;
