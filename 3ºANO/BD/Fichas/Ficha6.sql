#a
Select nome from medico where idade(data_inicio_servico) > 10;

#b
Select m.nome, e.designacao from medico m, especialidade e where m.especialidade = e.id_especialidade;

#c
Select p.nome, p.morada from paciente p, codigo_postal cp where p.codigo_postal = cp.codigo_postal && cp.localidade='Braga';

#d
Select m.nome from medico m, especialidade e where m.especialidade = e.id_especialidade && e.designacao='Oftalmologia';

#e
Select m.nome, idade(m.data_nascimento) as idade from medico m, especialidade e where m.especialidade = e.id_especialidade and e.designacao = 'Clinica Geral' and idade(m.data_inicio_servico) > 40;

#f
Select distinct m.nome from medico m, especialidade e, paciente p, codigo_postal cp, consulta c where m.especialidade=e.id_especialidade && e.designacao='Oftalmologia' && p.codigo_postal=cp.codigo_postal && cp.localidade='Braga' && c.id_medico=m.id_medico && c.id_paciente=p.id_paciente;

#g
Select distinct m.nome, idade(m.data_inicio_servico) as idade from medico m, consulta c, paciente p where idade(m.data_nascimento)>50 && m.id_medico=c.id_medico && p.id_paciente=c.id_paciente && idade(p.data_nascimento)<20 && hour(c.data_hora)>13;

#h
select distinct p.nome, idade(p.data_nascimento) as idade from MEDICO m,CONSULTA c,PACIENTE p, ESPECIALIDADE e where c.id_paciente = p.id_paciente and m.id_medico = c.id_medico and e.id_especialidade = m.especialidade and idade(p.data_nascimento) > 10 and c.id_paciente not in (
select p.id_paciente from MEDICO m,CONSULTA c,PACIENTE p, ESPECIALIDADE e where c.id_paciente = p.id_paciente and m.id_medico = c.id_medico and e.id_especialidade = m.especialidade and e.designacao = 'Oftalmologia');

#i
Select distinct e.designacao from especialidade e, medico m, consulta c where c.id_medico=m.id_medico && m.especialidade=e.id_especialidade && MONTH(c.data_hora)=01 && year(c.data_hora)=2016;

#j
Select nome from medico where idade(data_nascimento) > 30 or idade(data_inicio_servico) < 5;

#k
SELECT distinct m.nome, idade(data_nascimento) from MEDICO m, ESPECIALIDADE e, CONSULTA c where m.especialidade = e.id_especialidade 
and e.designacao = 'Clinica Geral' and c.id_medico = m.id_medico and c.id_medico not in (
SELECT c.id_medico from CONSULTA c where month(data_hora)  = 1 and year(data_hora) = 2016);

#l
SELECT DISTINCT x.id_paciente as ID, p.nome, idade(p.data_nascimento) as idade FROM CONSULTA AS x, paciente p WHERE NOT EXISTS (
SELECT * FROM MEDICO AS y WHERE NOT EXISTS (
SELECT * FROM CONSULTA AS z  WHERE (z.id_paciente=x.id_paciente) AND (z.id_medico=y.id_medico))) and p.id_paciente=x.id_paciente;

#m
Select distinct e.designacao from consulta c inner join medico m on m.id_medico = c.id_medico inner join especialidade e on e.id_especialidade= m.especialidade where month(data_hora)=1 or month(data_hora)=3 and year(data_hora)=2016;

#n
SELECT distinct m.nome from CONSULTA c, MEDICO m where c.id_medico = m.id_medico and c.id_paciente not in (
SELECT p.id_paciente from PACIENTE p, CODIGO_POSTAL cp, CONSULTA c where c.id_paciente = p.id_paciente and cp.codigo_postal = p.codigo_postal and cp.localidade = 'Braga');

#o
SELECT distinct p.id_paciente, p.nome from PACIENTE p, CONSULTA c where p.id_paciente = c.id_paciente and c.id_paciente not in (
SELECT p.id_paciente from PACIENTE p, CONSULTA c, MEDICO m, ESPECIALIDADE e where p.id_paciente = c.id_paciente 
and m.especialidade = e.id_especialidade and m.id_medico = c.id_medico
and c.id_medico not in (
SELECT id_medico from MEDICO m, ESPECIALIDADE e where m.especialidade = e.id_especialidade 
and e.designacao = 'Clinica Geral'));