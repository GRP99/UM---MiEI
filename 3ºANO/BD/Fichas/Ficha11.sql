#1
create table ListaFaltas(
	NrCromo int primary key
);

#2
insert into listafaltas select Nr from Cromo where Adquirido='N';

#3
DELIMITER $$
create procedure ex3(in nrc int)
begin
	declare erro bool default 0;
    declare continue handler for sqlexception set erro=1;
    set autocommit = off;
    start transaction;
    update cromo c set Adquirido='S' where c.Nr = nrc;
    delete from listafaltas lf where lf.NrCromo=nrc;
    if(erro) then rollback;
	end if;
	commit;
end$$

#4
DELIMITER $$
create procedure ex4(in nrc int)
begin
	declare erro bool default 0;
    declare continue handler for sqlexception set erro=1;
    set autocommit = off;
    start transaction;
    update cromo c set Adquirido='N' where c.Nr = nrc;
    insert into listafaltas select Nr from cromo c where c.Nr = nrc;
    if(erro) then rollback;
	end if;
	commit;
end$$
 
 #5
 DELIMITER $$
 create trigger ex5 after update on cromo for each row
	begin
		if (old.Adquirido='N' and new.Adquirido='S') then
			delete from listafaltas where NrCromo=old.Nr;
		end if;
	end$$
    
#6
 DELIMITER $$
create procedure ex6(in idEquipa char(3), out result_list varchar(2000))
begin
	declare v_finished integer default 0;
    declare v_treinador varchar(50);
    declare v_nome_equipa varchar(45);
    declare v_nome_jogador varchar(75);
    declare v_posicao varchar(20);
    declare listagem cursor for select j.nome, p.designacao 
		from jogador j, posicao p where j.posicao = p.id and j.Equipa = idEquipa;
    declare continue handler for not found set v_finished=1;
	select treinador,designacao into v_treinador, v_nome_equipa from equipa where id=idEquipa;
	set result_list = concat(v_nome_equipa,",",v_treinador);
    open listagem;
    get_jogador : loop
		fetch listagem into v_nome_jogador,v_posicao;
		if v_finished= 1 then leave get_jogador;
        end if;
        set result_list = concat(v_nome_jogador,":",v_posicao,",",result_list);
        end loop;
	close listagem;
end$$