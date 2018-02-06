$$
CREATE PROCEDURE `sp_delete_fields_template`(IN id_template_params INT, IN studytemplateid INT)
BEGIN

Delete result
	from  	st_sample_det_result result, 
			st_sample_detail detail,
            st_study_template_params params,
            st_study_template template
	where 
			detail.studysampleid = result.studysampleid
    and     result.templateparamid = params.templateparamid
	and 	params.templateparamid = id_template_params
    and 	template.studytemplateid = studytemplateid
	and  	template.studytemplateid = params.studytemplateid;

Delete params From st_study_template_params params
		where params.studytemplateid = studytemplateid
        and params.templateparamid = id_template_params;

END
$$