--[1] Criação de um novo schema
----[a] Criar um Tablespace e respetivo Datale
CREATE TABLESPACE aebd_tables DATAFILE 'aebd_tables_01.dbf' SIZE 100m;
----[b] Criar um Tablespace temporário e respetivo Datale
CREATE TABLESPACE aebd_temp DATAFILE 'aebd_temp_01.dbf' SIZE 50m autoextend on;
----[c] Através da view de system dba_tablespaces, confirmar a correta execução dos passos anteriores
SELECT * FROM dba_tablespaces;
----[d] Criar um user para a base de dados
CREATE USER aebd IDENTIFIED BY "aebd2020" DEFAULT TABLESPACE aebd_tables QUOTA UNLIMITED ON aebd_tables;
----[e] Conceder o Grant de acesso à base de dados ao user criado
GRANT CONNECT, RESOURCE , CREATE VIEW, CREATE SEQUENCE TO aebd;
----[f] Através da view de system dba_users, confirmar a correta execução do passo anterior
SELECT * FROM dba_users;


-------------------------------------------------------------------------------------------


--[2] Usando o SQLDeveloper
----[a] Com o modelo relacional da Fig. 1 crie a base de dados no schema criado na alínea [1]
----[b] Criar as tabelas com as respetivas relações
CREATE TABLE patrocinador
(
"id_patrocinador" NUMBER NOT NULL ENABLE,
"nome" VARCHAR2(200 byte) NOT NULL ENABLE,
"montante" NUMBER NOT NULL ENABLE,
CONSTRAINT "PATROCINADOR_PK" PRIMARY KEY ("id_patrocinador")
);
---As restantes tabelas foram criadas através da interface do SQLDeveloper.

----[c] Utilizando a função Data Modeler do SQL developer faça a criação do modelo relacional idêntico ao da Fig
--- aebd.png


-------------------------------------------------------------------------------------------


--[3] Recorrendo ao SQLDeveloper e aos scripts disponibilizados (scripts.zip) execute o povoamento de todas as tabelas criadas.

--[4] Execute os seguintes comandos SQL:
----[a] Quantos jogadores fazem parte do plantel do “Porto�?;
SELECT COUNT(*) as TotJogFCP FROM JOGADOR J, CLUBE C WHERE J.ID_CLUBE=C.ID_CLUBE AND C.NOME='FC Porto';
----[b] Listar todos os jogadores que são “Defesa Direito�? de clubes fundados em 1910;
SELECT J.NOME, P.DESC_POSICAO, C.NOME, C.ANO_FUNDACAO FROM JOGADOR J, POSICAO P, CLUBE C 
    WHERE J.ID_CLUBE=C.ID_CLUBE AND J.ID_POSICAO=P.ID_POSICAO AND P.DESC_POSICAO='Defesa Direito'
        AND C.ANO_FUNDACAO=1910;
----[c] Qual a média de idades, com 1 casa decimal, dos jogadores do “Braga�? por posição em campo;
SELECT AVG(J.IDADE) AS MEDIA_IDADES, CL.NOME, P.DESC_POSICAO FROM JOGADOR J, CLUBE CL, POSICAO P
    WHERE J.ID_CLUBE = CL.ID_CLUBE AND J.ID_POSICAO=P.ID_POSICAO AND CL.NOME='Braga'
    GROUP BY P.DESC_POSICAO, CL.NOME ORDER BY P.DESC_POSICAO;
----[d] Quais os cargos e os nomes dos treinadores dos clubes formados antes de 1950;
SELECT C.DESC_CARGO, T.NOME, CL.NOME, CL.ANO_FUNDACAO FROM TREINADOR T, CARGO C, CLUBE CL
    WHERE T.ID_CARGO = C.ID_CARGO AND T.ID_CLUBE=CL.ID_CLUBE AND CL.ANO_FUNDACAO<1950;
----[e] Listar todos o nome do treinador, nome do clube, cargo do treinador, cidade do clube e ano de fundação de todos os clubes fundados após 1945
SELECT T.NOME, CL.NOME, CC.DESC_CARGO, CL.CIDADE, CL.ANO_FUNDACAO
    FROM TREINADOR T, CLUBE CL, CARGO CC
    WHERE T.ID_CLUBE=CL.ID_CLUBE AND T.ID_CARGO=CC.ID_CARGO AND CL.ANO_FUNDACAO>1945;


-------------------------------------------------------------------------------------------


--[5] Construir uma view denominada JOGADOR_NEW que contenha a seguinte informação: ID_JOGADOR; NOME; IDADE; NOME_CLUBE; DESCRICAO_POSICAO, NOME_LIGA; CIDADE_CLUBE; ANO DE FUNDACAO
CREATE VIEW JOGADOR_NEW AS
    SELECT J.ID_JOGADOR, J.NOME, J.IDADE, CLUBE.NOME AS NOME_CLUBE, P.DESC_POSICAO AS DESCRICAO_POSICAO, L.NOME AS NOME_LIGA, CLUBE.CIDADE AS CIDADE_CLUBE, CLUBE.ANO_FUNDACAO AS ANODEFUNDACAO
    FROM JOGADOR J, CLUBE, POSICAO P, LIGA L
    WHERE J.ID_CLUBE = CLUBE.ID_CLUBE AND J.ID_POSICAO = P.ID_POSICAO AND CLUBE.ID_LIGA=L.ID_LIGA;


-------------------------------------------------------------------------------------------


-- [6] Listar todos os defesas direitos da Segunda Liga.
SELECT J.ID_JOGADOR, J.NOME, P.DESC_POSICAO, L.NOME AS LIGA_NOME FROM JOGADOR J, CLUBE CL, POSICAO P, LIGA L
    WHERE J.ID_POSICAO=P.ID_POSICAO AND J.ID_CLUBE=CL.ID_CLUBE AND CL.ID_LIGA=L.ID_LIGA 
        AND P.DESC_POSICAO='Defesa Direito' AND L.NOME='II Liga';


-------------------------------------------------------------------------------------------


-- [7] Listar todos os jogadores com menos de 27 anos cuja posição é Trinco e que não jogam na II Liga.
SELECT J.ID_JOGADOR, J.NOME, J.IDADE, P.DESC_POSICAO, L.NOME AS LIGA_NOME FROM JOGADOR J, CLUBE CL, POSICAO P, LIGA L
    WHERE J.ID_POSICAO=P.ID_POSICAO AND J.ID_CLUBE=CL.ID_CLUBE AND CL.ID_LIGA=L.ID_LIGA
        AND NOT(P.DESC_POSICAO='Trinco') AND NOT(L.NOME='II Liga') AND J.IDADE<27 ;
        

-------------------------------------------------------------------------------------------


-- [8] Construir uma view denominada TREINADOR_NEW que contenha a seguinte informação: ID_TREINADOR; NOME_TREINADOR; NOME_CLUBE; DESCRICAO_DO_CARGO
CREATE VIEW TREINADOR_NEW AS 
    SELECT TREINADOR.ID_TREINADOR, TREINADOR.NOME AS NOME_TREINADOR, CLUBE.NOME AS NOME_CLUBE, CARGO.DESC_CARGO AS DESCRICAO_DO_CARGO
    FROM TREINADOR, CARGO, CLUBE
    WHERE TREINADOR.ID_CARGO=CARGO.ID_CARGO AND TREINADOR.ID_CLUBE=CLUBE.ID_CLUBE;


-------------------------------------------------------------------------------------------