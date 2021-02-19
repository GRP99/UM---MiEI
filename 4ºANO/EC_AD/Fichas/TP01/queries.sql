#Q1 - Saber quantos testes os data_source fizeram:
select t.testing_data_source as Data_Source, count(1) as Tests from testing t group by Data_Source order by Tests desc ;

#Q2 - Determinar a média de novos casos e testes feitos por pais:
Select c.name as Country, c.population as Population, avg(t.new_cases) as AVG_NewCases, avg(t.tests_done) as AVG_TestsDone from country c, testing t
	where c.country_code=t.country_code group by c.name;
    
#Q3 - Mostrar os 3 países que mais casos tiveram:
select c.countryterritoryCode as CTC, c.name as Country, c.population as Population, sum(d.cases) as Tot_Cases from country c, data d
	where c.country_code=d.geoId group by Country order by Tot_Cases desc limit 3;
    
#P1 - A partir de um nome de um pais saber o maior testing_rate registado:
DELIMITER $$
create procedure max_testing_rates_of_country(in country_name varchar(10))
begin
	select t.country_code as Country_Code, t.refweek as Week, t.testing_rate as Testing, t.positivity_rate as Positivity from testing t, country c
    where c.name=country_name and t.country_code=c.country_code order by Testing desc limit 1;
end $$

#P2 - A partir de um nome de um pais saber o menor positivity_rate registado:
create procedure min_positivity_rates_of_country(in country_name varchar(10))
begin
	select t.country_code as Country_Code, t.refweek as Week, t.testing_rate as Testing, t.positivity_rate as Positivity from testing t, country c
    where c.name=country_name and t.country_code=c.country_code order by Positivity asc limit 1;
end $$

#F1 - A partir de um nome de um pais saber o número total de mortes registados:
DELIMITER $$
CREATE FUNCTION tot_Deaths(country_name varchar(50)) Returns int
Deterministic
BEGIN
		Declare Total_Mortes int;
        select sum(d.deaths) into Total_Mortes from data d, country c where c.name=country_name and d.geoId=c.country_code;
        RETURN Total_Mortes;
END $$

#V1 -  Ver o quantos casos foram registados por mes e ano em cada pais:
DELIMITER $$
Create view Acumulado as select c.name, month(d.date) as Month, year(d.date) as Year, sum(d.cases) as Cases 
	from country c, data d where c.country_code=d.geoId group by c.name, Month, Year;

#T1 - Atualizar a populacao de um pais após insercao de mortes:
DELIMITER $$
create trigger updatePopulation after insert on data for each row 
begin 
    Update country c set NEW.c.population=OLD.c.population-NEW.data.deaths where c.country_code = d.geoId ;
end $$


