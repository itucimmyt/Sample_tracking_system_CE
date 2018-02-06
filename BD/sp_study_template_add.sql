delimiter //
CREATE PROCEDURE `sp_study_template_add`(id_study INT, id_template_params INT)
begin

		INSERT INTO st_sample_det_result (studysampleid, templateparamid, result)
			SELECT d.studysampleid, t.templateparamid, ''
			FROM st_sample_detail d JOIN st_study_template_params t
			where d.labstudyid = id_study
			and t.studytemplateid = id_template_params;

	commit;
 end//
delimiter ;
