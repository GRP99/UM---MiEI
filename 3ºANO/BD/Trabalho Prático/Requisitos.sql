#r0 - Saber a idade de um atleta ou médico
DELIMITER $$
CREATE FUNCTION `idade` (dta date) RETURNS INT
Deterministic
	BEGIN
		RETURN TIMESTAMPDIFF(YEAR, dta, CURDATE());
	END $$

#req1 - A partir de uma equipa conseguir saber quais os testes marcados pelos atletas entre duas datas
DELIMITER $$
create procedure getTestesMarcadosEquipa(in nomequipa varchar(128), in d_a_t_a_i datetime, in d_a_t_a_f datetime)
begin
	select a.nif as nif, a.nome_proprio as nome, a.apelido as apelido, tc.data_hora as data, tc.tipo_teste as tipo 
		from atleta a, equipa e, teste_clinico tc
		where e.nome=nomequipa and a.id_equipa=e.idequipa and tc.idAtleta = a.nif 
			and tc.agendado=true and (tc.data_hora between d_a_t_a_i and d_a_t_a_f);
end $$

#req2 - A partir de uma especialidade saber o total faturado dos testes dessa
DELIMITER $$
create procedure total_faturado_da_especialidade(in nome_especialidade varchar(300))
begin
	select sum(t.preco) as Total_Faturado from teste_clinico t,medico m,especialidade e 
    where t.idMedico=m.nif and m.id_especialidade=e.id_especialidade and e.designacao=nome_especialidade;
end $$

#req3 - A partir de uma especialidade saber quais os atletas que não efetuaram qualquer teste
DELIMITER $$
create procedure atletas_nunca_testados_por_especialidade(in nome_especialidade varchar(300))
	begin
		select b.nome_proprio as Nome_do_Atleta from atleta b where b.nome_proprio not in(select distinct a.nome_proprio 
			from teste_clinico t,medico m,especialidade e,atleta a
			where t.idMedico=m.nif and m.id_especialidade=e.id_especialidade 
				and e.designacao=nome_especialidade and a.nif=t.idAtleta);
	end $$
    
#req4 - Ver o quanto foi gasto por cada atleta e ordenar estes
Create view Total_Gasto_Atleta as select a.nome_proprio as Nome, a.apelido as Apelido ,a.genero as Genero, e.nome as Equipa, sum(t.preco) as Total_Gasto 
	from atleta a,teste_clinico t, equipa e
		where a.nif=t.idAtleta and a.id_equipa=e.idequipa and t.agendado=false group by a.nif order by Equipa, Total_Gasto desc;

#req5 - A partir de uma especialidade, uma data e um intervalo de horas ver quais os médicos disponíveis
DELIMITER $$
create procedure medicosDisponiveis (in especialidade varchar(128), in d_a_t_a datetime)
	begin
		select m.nome_proprio, m.apelido from medico m, especialidade e 
        where not exists (select * from teste_clinico tc 
					where e.designacao=especialidade and e.id_especialidade=m.id_especialidade 
						and tc.idMedico=m.nif and d_a_t_a=tc.data_hora)
			and e.designacao=especialidade and e.id_especialidade=m.id_especialidade;
    end$$
	
#req6 -	Ver o total gasto por cada modalidade em testes clínicos
select distinct mo.designacao as Modalidade,sum(t.preco) as Total_Gasto from modalidade mo, teste_clinico t,atleta a,categoria c
    where c.id_atleta=a.nif and c.id_modalidade=mo.idmodalidade and t.idAtleta=a.nif 
		group by mo.idmodalidade order by Total_Gasto desc;
          
#req7 -	A partir de uma equipa mostrar todos os atletas dessa equipa e a sua respetiva localidade
DELIMITER $$
create procedure localidades_dos_atletas(in nome_equipa varchar(300))
	begin
        select a.nome_proprio as Nome, a.apelido as Apelido, cp.localidade as Localidade
			from atleta a,codigo_postal cp,equipa e
            where a.cod_postal=cp.cod_postal and a.id_equipa=e.idequipa and e.nome=nome_equipa;
	end $$

#req8 - Mostrar os 5 atletas que mais testes clínicos realizaram
select a.nif,a.nome_proprio,sum(1) as NConsultas from atleta a,teste_clinico t
	where t.idAtleta=a.nif and t.agendado= false group by a.nif order by NConsultas desc limit 5;

#req9 - Mostrar os 5 médicos e a respetiva especialidade que mais testes clínicos realizaram
select m.nif,m.nome_proprio,e.designacao,sum(1) as NConsultas from medico m,especialidade e,teste_clinico t
	where e.id_especialidade=m.id_especialidade and t.idMedico=m.nif and t.agendado=false group by m.nif 
		order by NConsultas desc limit 5;

#req10 - Determinar a média de idades dos atletas por equipa
Select e.nome as localidade, avg(idade(a.data_de_nascimento)) as media_idades from atleta a, equipa e
	where e.idequipa=a.id_equipa group by e.nome;

#req11 - Saber o total gasto por género dos atletas
select a.genero as Genero, sum(t.preco) as Preco from teste_clinico t,atleta a where t.idAtleta=a.nif group by a.genero;

#req12 - Saber quais as especialidades com menos médicos
select e.designacao as Especialidade, count(1) as Numero_de_Medicos from medico m,especialidade e 
    where m.id_especialidade=e.id_especialidade group by e.designacao order by Numero_de_Medicos desc;
    
#req13 - Saber quais as especialidades com mais testes
select e.designacao as Especialidade, count(1) as Numero_de_Consultas
	from teste_clinico t,medico m,especialidade e  
	where m.id_especialidade=e.id_especialidade and t.idMedico=m.nif and t.agendado=false
		  group by e.designacao order by Numero_de_Consultas desc;

#req14 - Dado um id de um médico saber os testes que ainda atualizou
DELIMITER $$
create procedure faltaAtualizar(in idMed int(11))
	begin
        select a.nif, a.nome_proprio as Nome, a.apelido as Apelido, tc.data_hora, tc.tipo_teste as Designacao
			from atleta a, teste_clinico tc where tc.idMedico=idMed and tc.idAtleta=a.nif and tc.agendado=true;
	end $$
    
#req15 - Um médico feito um teste seu pode atualizar o registo dela
DELIMITER $$
create procedure atualizaRegisto(in idMed int(11), in prec decimal(7,2), in quemfaltou int(11), in d_a_t_a datetime)
	begin
		update teste_clinico 
			set teste_clinico.preco=prec, teste_clinico.como_correu=quemfaltou, teste_clinico.agendado=false
			where teste_clinico.agendado=true and teste_clinico.data_hora=d_a_t_a and teste_clinico.idMedico=idMed;
	end$$

#req16 - Determinar o número de testes em que cada atleta faltou
select a.nif,a.nome_proprio as Nome, a.apelido as Apelido, sum(1) as NFaltas from atleta a, teste_clinico tc
	where a.nif=tc.idAtleta and tc.como_correu = -1 and tc.agendado=false group by a.nif order by NFaltas desc;
    
#req17 - Determinar o número de testes em que cada médico faltou
select m.nif,m.nome_proprio as Nome, m.apelido as Apelido, sum(1) as NFaltas from medico m, teste_clinico tc
	where m.nif=tc.idMedico and tc.como_correu = 1 and tc.agendado=false group by m.nif order by NFaltas desc;
    
#req18 - Dado um id de um médico saber os testes que fez
DELIMITER $$
create procedure getTestes(in idMed int(11))
	begin
        select a.nif, a.nome_proprio as Nome, a.apelido as Apelido, tc.data_hora, tc.preco, tc.tipo_teste as Designacao
			from atleta a, teste_clinico tc where tc.idMedico=idMed and tc.idAtleta=a.nif and tc.agendado=false;
	end $$

#req19 - Saber para cada médico o valor total faturado num determinado ano
DELIMITER $$
create procedure valorAnual(in ano int(4))
begin
	Select m.nome_proprio as nome, m.apelido as apelido, ifnull(sum(tc.preco),0) as total from medico m left join teste_clinico tc on m.nif=tc.idMedico
		and year(tc.data_hora)=ano group by m.nif;
end$$

#req20 - Saber quais as categorias distintas de cada modalidade
select distinct m.designacao, c.designacao from categoria c, modalidade m where c.id_modalidade=m.idmodalidade 

#req21 - Dado um nif de um atleta apresentar todos os testes realizados
DELIMITER $$
create procedure verTestes (mynif int(11))
begin
	select m.nome_proprio as Nome_Proprio_Medico, m.apelido as Apelido_Medico, tc.tipo_teste as Tipo, 
		tc.data_hora as Data_Hora, tc.preco as Preco, tc.como_correu as Como_Correu 
			from teste_clinico tc, medico m 
				where tc.idAtleta=mynif and tc.agendado=false and tc.idMedico=m.nif;
end$$

#req22 - Ficar a conhecer quanto os atletas de uma dada equipa gastaram
DELIMITER $$
create procedure verGastoEquipa (equipa varchar(64))
begin
	select tga.nome, tga.apelido, tga.genero, tga.Total_Gasto from total_gasto_atleta tga where tga.Equipa=equipa;
end$$