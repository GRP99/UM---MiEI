#Q1 - Apresentar informacao de um paciente indentica ao csv:
SELECT p.patient_id as ID, dim_sex.sex, dim_age.age, dim_origin.origin, dim_region.province,
	dim_region.city,  dim_infection.infection_case, p.infected_by, dim_infection.contact_number,
    dim_date.symptom_start_date, dim_date.confirmed_case_date, dim_date.released_date, 
    dim_date.deceased_date, dim_state.state
	FROM fact_patient p, dim_age, dim_date, dim_infection, dim_origin, dim_region, dim_sex, dim_state 
	WHERE p.id_age=dim_age.idAge AND p.id_region=dim_region.idRegion AND
		p.id_date=dim_date.idDate AND p.id_infection=dim_infection.idInfection AND
        p.id_origin=dim_origin.idOrigin AND p.id_sex=dim_sex.idSex AND p.id_state=dim_state.idState;
        
        
#Q2 - Saber quantos pacientes estao isolados:
SELECT count(*) as TOT_ISOLATEDS FROM fact_patient, dim_state 
	WHERE dim_state.state="isolated" AND dim_state.idState=fact_patient.id_state;


#Q3 - Saber quais os pacientes que a origem veio fora da Korea
SELECT fact_patient.patient_id, dim_region.province, dim_region.city
	FROM fact_patient, dim_origin, dim_region 
	WHERE not(dim_origin.origin="Korea") AND dim_origin.idOrigin=fact_patient.id_origin 
		AND dim_region.idRegion=fact_patient.id_region;