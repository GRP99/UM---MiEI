### TPC: jcrpubs

1. Analisar o [dataset](jcrpubs.xml) das pubs do jcr;

2. Usar o Protégé para construir a parte estrutural da [ontologia](jcrpubs-base.ttl) e criar alguns indivíduos para servirem de modelo;

3. Criar um [script](XMLtoTTL.py) para povoar a [ontologia](jcrpubs.ttl) com os [indivíduos](pubs.txt) do dataset e inferição da [ontologia](jcrpubs-inf.ttl) povoada;

4. Carregar a ontologia no [Ontobud](http://ontobud.di.uminho.pt/):
    * Criação do repositório **A83732-TP5**;
    * Importação do ficheiro inferido.

5. Criar uma [API](api-server/routes/index.js) de dados com as funcionalidades de CRUD sobre o modelo criado:
    - GET /pubs
    - GET /pubs?type={article|book|inbook|inproceedings|masterthesis|misc|phdthesis|proceedings}
    - GET /pubs/{id}
    - GET /authors
    - GET /authors/{id}
    - ~~POST /pubs~~
    - ~~POST /authors~~
    - ~~DELETE /pubs/{id}~~
    - ~~DELETE /authors/{id}~~
    - ~~PUT /pubs/{id}~~
    - ~~PUT /authors/{id}~~