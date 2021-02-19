#1
select e.Id as Equipa, c.PagCaderneta as Pagina from equipa e, posicao p, jogador j, cromo c 
	where (e.Designacao="Sporting Clube de Braga" or e.Designacao="Rio Ave Futebol Clube") 
		and j.Equipa=e.Id and p.Designacao="Defesa" and j.Posicao=p.Id and j.Nr=c.Nr;
        
#2
select j.Nome as Jogador, j.Nr as Numero from equipa e, posicao p, jogador j
	where (e.Treinador="Jorge Jesus" or e.Treinador="Nuno Espírito Santo")
		and (p.designacao="Guarda-Redes" or p.designacao="Avançado") and j.Posicao=p.Id and j.Equipa=e.Id;
        
#3
create view ListaCromosFalta as select c.Nr as Numero, j.Nome as NomeJogador, e.Designacao as NomeEquipa 
	from cromo c, jogador j, equipa e
    where e.Id=j.Equipa and j.Nr=c.Nr and c.Adquirido="N" order by Numero;
    
#4
DELIMITER $$
create procedure listaCromos (in nomeEquipa varchar(45))
begin
	select j.Nome as Nome, c.Nr as Numero, c.Adquirido as Adquirido, c.PagCaderneta as Pagina from equipa e, jogador j, cromo c 
		where e.Designacao=nomeEquipa and e.Id=j.Equipa and j.Nr=c.Nr order by c.PagCaderneta, c.Nr ASC;
end 
$$

#5
DELIMITER $$
create procedure cadernetaCompleta ()
begin
	select c.Nr as Numero, t.Descricao as Tipo, j.Nome as Nome_Jogador, e.Designacao as Equipa, c.Adquirido as Adquirido 
		from cromo c, equipa e, jogador j, tipocromo t
		where c.Tipo=t.Nr and e.Id=j.Equipa and j.Nr=c.Nr;
end$$

#6
DELIMITER $$
create function repetido (nrcromo int(11)) returns char(1) DETERMINISTIC
begin
	declare existe char(1);
    if ("S" = (select c.Adquirido from cromo c where c.Nr=nrcromo)) then set existe = "S";
    else set existe="N";
    end if;
    return existe;
end$$

#7
DELIMITER $$
create function getInfo (nrcromo int(11)) returns varchar(200) deterministic
begin
	declare tipo varchar(75);
    declare jogador varchar(75);
    declare equipa varchar(75);
	select t.Descricao, j.Nome, e.Designacao into tipo,jogador,equipa
		from cromo c, tipocromo t, jogador j, equipa e 
		where nrcromo=c.Nr and c.Tipo=t.Nr and c.Jogador=j.Nr and j.Equipa=e.Id;
	return (concat(tipo,";",jogador,";",equipa,";"));
end$$

#8
create table audCromos (nr int(11) PRIMARY KEY, data_hora datetime NOT NULL);
DELIMITER $$
create trigger auditoriaCromos after update on cromo for each row
begin 
    if (old.Adquirido='N' and new.Adquirido='S') then
		insert into audCromos values(new.nr,now());
	end if;
end $$