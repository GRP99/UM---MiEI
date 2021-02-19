# Repositório do Trabalho Prático

[1] Criação de um agente (`/Agente/`), em JAVA, que através das views de administração (`/queries_ToGetData.sql`) executa a recolha da informação necessária considerada necessária. 

[2] Criação de uma nova PDB, respetivo schema com tablespaces permanentes e temporários e datafiles associados, de forma a armazenar os dados recolhidos no ponto [1] (`/tutorial_PDB - Q2.txt`). A estrutura de dados a recolher foi modelada com um modelo relacional de dados (`/schema_database.sql` e `/RelationalModel.png` ) , contempla o histórico dos registos.  

[3] Criação de uma API REST que se liga à PBD criada no ponto [2] e devolve os resultados no formato necessário, JSON. (`/dbmonitor/api/`)

[4] Criação de uma interface web que apresente os dados recolhidos no ponto [3] (`/dbmonitor/interface/`).
