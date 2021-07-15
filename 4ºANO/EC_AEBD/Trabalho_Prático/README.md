# Trabalho Prático
1. Criação de um [agente](Agente/), em JAVA, que através das views de administração executa a [recolha](queries_ToGetData.sql) da informação necessária considerada necessária.
2. Criação de uma nova [PDB](criacao_PDB-Schema-Tablespaces-Datafiles.txt), respetivo schema com tablespaces permanentes e temporários e datafiles associados, de forma a armazenar os dados recolhidos no ponto 1. A estrutura de dados a recolher foi modelada com um [modelo relacional de dados](schema_database.sql), contempla o histórico dos registos.
3. Criação de uma [API REST](dbmonitor/api/) que se liga à PBD criada no ponto 2 e devolve os resultados no formato necessário, JSON.
4. Criação de uma [interface web](dbmonitor/interface/) que apresente os dados recolhidos no ponto 3.
