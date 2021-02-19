-- dim actor
call netflix.Convert_Line_to_Rows_Casts(',') ;
insert into dim_actor(name) select distinct value from casts;

-- dim country
call netflix.Convert_Line_to_Rows_Country(',') ;
insert into dim_country(country) select distinct value from countries;

-- dim date
CALL Generate_Dates('2009-05-05','2020-01-17');

-- dim director
call netflix.Convert_Line_to_Rows_Director(',');
insert into dim_director(name) select distinct value from directors;

-- dim duration
insert into dim_duration(duration) 
select distinct netflix_dataset.duration from netflix_dataset;

-- dim imdb
insert into dim_imdb(imdb_titleID,rating,rating_votes,title_name)
select distinct netflix_dataset.IMDB_titleID, netflix_dataset.IMDB_rating, netflix_dataset.IMDB_rating_votes, netflix_dataset.IMDB_title_name 
	from netflix_dataset;

-- dim listed_in
call netflix.Convert_Line_to_Rows_Listed(',');
insert into dim_listed_in(listed_in) select distinct value from Listeds;

-- dim rating
insert into dim_rating (rating)
select distinct netflix_dataset.rating from netflix_dataset;

-- dim titledescription
insert into dim_titledescription (title,description)
select distinct netflix_dataset.title,netflix_dataset.description from netflix_dataset;

-- dim type
insert into dim_type (type)
select distinct netflix_dataset.type from netflix_dataset;

-- fact netflix
insert ignore into fact_netflix (show_id,fk_type,fk_titledescription,date_added,release_year,fk_rating,fk_duration,fk_imdb)
select nd.show_id, t.id_type, td.id_titledescription, d.id_date, nd.release_year, r.id_rating, du.id_duration, i.id_imdb
	from netflix_dataset nd, dim_type t, dim_titledescription td, dim_date d, dim_rating r, dim_duration du, dim_imdb i
	where nd.type=t.type and nd.title=td.title and nd.description=td.description 
		and d.id_date=nd.date_added and nd.rating=r.rating and nd.duration=du.duration 
        and nd.imdb_titleID=i.imdb_titleID and nd.IMDB_rating=i.rating and nd.IMDB_rating_votes = i.rating_votes 
        and nd.IMDB_title_name = i.title_name;       
show warnings;

-- dim cast
call netflix.Convert_Line_to_Rows_Casts(',') ;
insert into dim_cast(fk_show,fk_actor) 
select distinct n.show_id, a.id_actor from casts c, dim_actor a, fact_netflix n where c.value=a.name and n.show_id=c.id;

-- dim countries
call netflix.Convert_Line_to_Rows_Country(',') ;
insert into dim_countries(fk_showid,fk_country)
select distinct n.show_id, cc.id_country from countries c, dim_country cc, fact_netflix n where c.value=cc.country and n.show_id=c.id;

-- dim directors
call netflix.Convert_Line_to_Rows_Director(',');
insert into dim_directors(fkshowid,fk_director)
select distinct n.show_id, dd.id_director from directors d, dim_director dd, fact_netflix n where n.show_id=d.id and d.value=dd.name;

-- dim listeds_in
call netflix.Convert_Line_to_Rows_Listed(',');
insert into dim_listeds_in(fk_id,fk_listedin) 
select distinct n.show_id, ll.id_listedin from listeds l, dim_listed_in ll, fact_netflix n where n.show_id=l.id and l.value=ll.listed_in;



-- TESTES : 
-- Apresentar informacao  indentica ao csv:
CREATE VIEW CSV AS select fn.show_id as show_id, t.type as type, td.title as title, d.id_date as date_added, fn.release_year as release_year, r.rating as rating, du.duration as duration, td.description as description, i.imdb_titleID as IMDB_titleID, i.rating as IMDB_rating, i.rating_votes as IMDB_rating_votes, i.title_name as IMDB_title_name
	from fact_netflix fn, dim_type t, dim_titledescription td, dim_date d, dim_rating r, dim_duration du, dim_imdb i
	where fn.fk_type=t.id_type and fn.fk_titledescription=td.id_titledescription and fn.date_added=d.id_date and fn.fk_rating = r.id_rating
		and fn.fk_duration=du.id_duration and fn.fk_imdb=i.id_imdb;
-- TABELAS COMPOSTAS 
DELIMITER $$
create procedure getCast(in show_id int)
begin
	select show_id, a.name as Actor, a.id_actor as id from dim_cast c, dim_actor a 
    where c.fk_show=show_id and c.fk_actor = a.id_actor;
end $$
DELIMITER $$
create procedure getCountries(in show_id int)
begin
	select show_id, c.country as Country, c.id_country as id from dim_countries cc, dim_country c 
    where cc.fk_showid=show_id and cc.fk_country = c.id_country;
end $$
DELIMITER $$
create procedure getDirectors(in show_id int)
begin
	select show_id, d.name as Director, d.id_director as id from dim_directors dd, dim_director d 
    where dd.fkshowid=show_id and dd.fk_director = d.id_director;
end $$
DELIMITER $$
create procedure getListedsIn(in show_id int)
begin
	select show_id, l.listed_in as Listed_in, l.id_listedin as id from dim_listeds_in ll, dim_listed_in l 
    where ll.fk_id=show_id and ll.fk_listedin = l.id_listedin;
end $$