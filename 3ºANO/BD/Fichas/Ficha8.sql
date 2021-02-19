SET SQL_SAFE_UPDATES = 0;
###################################################################################
DELIMITER $$
CREATE PROCEDURE atualizar_preco(in ano int, in percentagem int) 
	BEGIN
		Declare v_media decimal (5,2);
        Declare v_designacao varchar(100);
        Declare v_finished integer default 0;
        Declare designacao_cursor cursor for
			Select e.designacao, avg(c.preco) from consulta c, especialidade e, medico m where e.id_especialidade=m.especialidade and m.id_medico=c.id_medico and year(c.data_hora)=ano group by e.designacao;
            Declare continue handler for not found set v_finished = 1;
            Open designacao_cursor;
            get_designacao : Loop
				FETCH designacao_cursor into v_designacao, v_media;
                if v_finished = 1 then leave get_designacao;  end if;
                update especialidade set preco = (v_media*(percentagem/100)) where designacao = v_designacao;
			end loop;
		close designacao_cursor;			
END $$
Call atualizar_preco(10,2016);
###################################################################################
DELIMITER $$
CREATE FUNCTION temconsultas(id int) Returns boolean
Deterministic
BEGIN
		Declare val int;
		Declare temConsultas bool;
        Select Count(1) into val from consulta c, medico m where m.id_medico=id and m.id_medico=c.id_medico;
        if (val < 1) then set temConsultas = false; else set temConsultas = true; end if;
        RETURN temConsultas;
END $$
###################################################################################
DELIMITER $$
CREATE FUNCTION CodigoPostalUsado(CodPost varchar(8)) returns bool
deterministic 
Begin
	Declare usado bool;
    Declare resultado int;
    Select count(1) into resultado from medico m, paciente p where m.codigo_postal = codPost or p.codigo_postal=CodPost;
    if (resultado < 1) then set usado=false; else set usado = true; end if;
    return usado;
END $$
###################################################################################
ALTER TABLE MEDICO
	ADD column TotalFaturado decimal(10,2);
DELIMITER $$
    Create trigger acumularFacturado
		after insert on consulta
		for each row
		begin
			update medico set TotalFaturado = TotalFaturado + new.preco;
		end $$
###################################################################################
DELIMITER $$
Create view AcumuladoPaciente as select c.id_paciente, month(c.data_hora) as mes, year(c.data_hora) as ano, sum(preco) as valor 
	from consulta c group by c.id_paciente, mes, ano;
###################################################################################
DELIMITER $$
Create view MensalEspecialidade as select especialidade, month(data_hora) as mes, year(data_hora) as ano, sum(preco) as valor
	from medico m, consulta c where m.id_medico=c.id_medico group by especialidade,mes,ano;
