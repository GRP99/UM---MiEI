#Q1 - Apresentar informacao de um paciente indentica ao csv:
CREATE VIEW CSV AS SELECT fps.id as id, n.name as name, d.id_date as date, 
	m.mannerofdeath as manner_of_death, a.armed as armed, fps.age as age, g.gender as gender,
    r.race as race, p.city as city, p.state as state, fps.signs_of_mental_illness as signs_of_mental_illness,
    t.threatlevel as threat_level, f.flee as flee, fps.bodycamera as body_camera
		FROM fatal_police_shootings fps, dim_armed a, dim_date d, dim_flee f, dim_gender g, 
			dim_mannerofdeath m, dim_name n, dim_place p, dim_race r, dim_threatlevel t
		WHERE fps.fk_id_armed=a.id_armed AND fps.fk_id_date=d.id_date AND fps.fk_id_flee=f.id_flee
			AND fps.fk_id_gender=g.id_gender AND fps.fk_id_mannerofdeath=m.id_mannerofdeath AND
            fps.fk_id_name=n.id_name AND fps.fk_id_place=p.id_place AND fps.fk_id_race=r.id_race
            AND fps.fk_id_threatlevel=t.id_threatlevel; 
            
#Q2 - Mostrar as 3 cidades com mais mortes:
select p.city as city, p.state as state, count(fps.id) as Tot_Deaths from dim_place p, fatal_police_shootings fps
	where p.id_place=fps.fk_id_place group by city order by Tot_Deaths desc limit 3;            
    
#Q3 - Saber quantas mortes por raca:
select r.race as race, count(fps.id) as Tot_Deaths from dim_race r, fatal_police_shootings fps
	where r.id_race=fps.fk_id_race group by race order by Tot_Deaths desc ;
    
#Q4 - Média de idades associada ao facto de estar armados
select a.armed as armed, avg(fps.age) as AVG_AGE from dim_armed a, fatal_police_shootings fps
		where a.id_armed=fps.fk_id_armed group by armed;
        
#Q5 - Saber as mulheres que com problemas mentais fugiram
select fps.id as id, n.name as name, g.gender as gender, f.flee as flee, fps.signs_of_mental_illness as mentalillness
	from fatal_police_shootings fps, dim_name n, dim_gender g, dim_flee f
    where fps.fk_id_flee=f.id_flee and fps.fk_id_name=n.id_name and fps.fk_id_gender=g.id_gender
		and g.gender = 'F' and fps.signs_of_mental_illness=True and not(f.flee='Not fleeing' or f.flee='');