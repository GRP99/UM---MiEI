DELIMITER $$
create procedure registaAtleta(nif int(11), nome varchar(128), apelido varchar(128), datanascimento date, nacionalidade varchar(64), genero varchar(48), email varchar(128), peso decimal(5,2), altura decimal(4,1), rua varchar(64), nome_equipa varchar(128), codigo_postal varchar(8))
begin
	insert into atleta values(nif,nome,apelido,datanascimento,nacionalidade,genero,email,peso,altura,rua, (select e.idequipa from equipa e where e.nome=nome_equipa),codigo_postal);
end$$

DELIMITER $$
create procedure registaCategoria(desig varchar(64), idA int(11), modalidade varchar(64))
begin
	insert into categoria values(idA,desig,(select m.idmodalidade from modalidade m where m.designacao=modalidade));
end$$
####################################################################################################################

DELIMITER $$
create procedure marcarTestes (tipo varchar(64), nifAtleta int(11), nomeproprioMedico varchar(128), apelidoMedico varchar(128),data_hora datetime)
begin
	if EXISTS (select * from testeclinico tc, medico m where m.nome_proprio=nomeproprioMedico and m.apelido=apelidoMedico and tc.idMedico=m.nif and tc.data_hora=data_hora and tc.agendado=false) then
		insert into teste_clinico values (nifAtleta,(select m.nif from medico m where m.nome_proprio=nomeproprioMedico and m.apelido=apelidoMedico),null,data_hora,tipo,true,null);
	end if;
end$$
###################################################################################################################

CREATE USER 'equipa'@'localhost' IDENTIFIED BY 'equipa_password';
GRANT EXECUTE ON PROCEDURE registaAtleta TO 'equipa'@'localhost';
GRANT EXECUTE ON PROCEDURE registaCategoria TO 'equipa'@'localhost';
GRANT EXECUTE ON PROCEDURE verGastoEquipa TO 'equipa'@'localhost';
GRANT EXECUTE ON PROCEDURE getTestesMarcadosEquipa TO 'equipa'@'localhost';
GRANT EXECUTE ON PROCEDURE localidades_dos_atletas TO 'equipa'@'localhost';
SHOW GRANTS FOR 'equipa'@'localhost';
DROP USER'equipa'@'localhost';

CREATE USER 'atleta'@'localhost' IDENTIFIED BY 'atleta_password';
GRANT EXECUTE ON PROCEDURE marcarTestes TO 'atleta'@'localhost';
GRANT EXECUTE ON PROCEDURE verTestes TO 'atleta'@'localhost';
GRANT EXECUTE ON PROCEDURE medicosDisponiveis TO 'atleta'@'localhost';
SHOW GRANTS FOR 'atleta'@'localhost';
DROP USER 'atleta'@'localhost';


CREATE USER 'medico'@'localhost' IDENTIFIED BY 'medico_password';
GRANT EXECUTE ON PROCEDURE atualizaRegisto TO 'medico'@'localhost';
GRANT EXECUTE ON PROCEDURE faltaAtualizar TO 'medico'@'localhost';
GRANT EXECUTE ON PROCEDURE getTestes TO 'medico'@'localhost';
SHOW GRANTS FOR 'medico'@'localhost';
DROP USER 'medico'@'localhost';