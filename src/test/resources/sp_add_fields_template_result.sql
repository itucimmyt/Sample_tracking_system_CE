$$
CREATE  PROCEDURE `sp_add_fields_template_result` (IN id_template_params INT, IN studytemplateid INT)
BEGIN
 INSERT INTO st_sample_det_result (studysampleid, templateparamid, result)
 Select detail.studysampleid, id_template_params,''
        from
                st_sample_detail detail,
                st_lab_study study
        where
               
                study.labstudyid = detail.labstudyid
        and        study.studytemplateid = studytemplateid;
END 
$$