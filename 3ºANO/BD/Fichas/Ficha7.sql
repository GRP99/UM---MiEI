#a
Select avg(idade(data_nascimento)) as media from medico m where idade(m.data_inicio_servico)>15;

#b
Select e.designacao as Especialidade, avg(idade(m.data_inicio_servico)) as media from medico m, especialidade e
	where m.especialidade=e.id_especialidade group by e.id_especialidade order by especialidade;

#c
Select m.nome as nome, count(c.id_medico) as total from medico m left join consulta c on c.id_medico = m.id_medico
	group by m.id_medico order by nome;

#d
Select cp.localidade as localidade, avg(idade(p.data_nascimento)) as media from paciente p, codigo_postal cp
	where cp.codigo_postal=p.codigo_postal group by cp.localidade;

#e
Select m.nome as nome, ifnull(sum(c.preco),0) as total from medico m left join consulta c on m.id_medico=c.id_medico
	and year(c.data_hora)=2016 group by m.id_medico;

#f
Select e.designacao as especialidade, count(m.id_medico) as total from medico m, especialidade e 
	where m.especialidade=e.id_especialidade group by e.id_especialidade order by total desc;

#g
Select aux.designacao, min(c.preco), max(c.preco), avg(c.preco) from 
	(Select e.designacao, e.id_especialidade, count(m.id_medico) as total from medico m, especialidade e where m.especialidade=e.id_especialidade group by e.id_especialidade) 
		as aux, medico m, consulta c where c.id_medico=m.id_medico and m.especialidade=aux.id_especialidade and aux.total<=2
			group by aux.id_especialidade;

#h
Select m.nome, sum(preco) as faturado from consulta c, medico m where c.id_medico=m.id_medico and year(c.data_hora)=2016 
	group by m.id_medico having sum(preco) > (Select avg(faturado2016.faturado) from 
		(Select m.nome, sum(preco) as faturado from consulta c, medico m where c.id_medico=m.id_medico and year(c.data_hora)=2016
			group by m.id_medico) as faturado2016);

#i
Select designacao, sum(c.preco) from consulta c inner join medico m on c.id_medico=m.id_medico 
	inner join especialidade e on m.especialidade=e.id_especialidade where year(c.data_hora)=2016 
		group by id_especialidade order by sum(c.preco) desc;

#j
Select m.nome,count(m.id_medico) as total from medico m, consulta c where m.id_medico=c.id_medico and year(c.data_hora)=2016 
	group by c.id_medico order by total desc,m.nome limit 3;
