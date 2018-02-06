delimiter //
CREATE PROCEDURE `sp_count_params_data`(IN id_template_paramas INT, OUT total INT)
BEGIN
Select  Count(*) INTO total
	from 	st_sample_det_result result, 
			st_sample_detail detail,
            st_study_template_params params,
            st_study_template template
	where 
			detail.studysampleid = result.studysampleid
    and     result.templateparamid = params.templateparamid
    and 	params.templateparamid = id_template_paramas
    and 	result.result <> '';

END//
delimiter ;
