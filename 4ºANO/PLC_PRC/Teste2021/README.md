# Ficha prc2021-teste
## Avaliação: Processamento e Representação de Conhecimento
O objetivo desta ficha foi aferir os conhecimentos adquiridos sobre Web Semântica e as tecnologias a ela associadas.
- - - -
### Exercício Nº 1: Gestão de EMDs:
1. Criar uma [ontologia](/Ex1/ontologia.ttl) OWL para modelar o universo da gestão de exames médicos desportivos (EMD) de acordo com os seguintes requisitos:
    * Cria as classes: EMD, Modalidade, Atleta e Clube;
    * Analisa o dataset que te é dado e cria as relações e propriedades que achares convenientes;
    * [Povoa](/Ex1/script.py) a tua ontologia com o dataset.
2. Criar um repositório no teu GraphDB local de nome "EMD" e importa para lá a tua ontologia;
3. Sobre a ontologia especifica as [queries](/Ex1/queries.txt) SPARQL capazes de gerar os seguintes resultados:
    * Quantos exames estão registados?
    * Quantos exames tiveram um resultado válido?
    * Qual a distribuição dos exames por género?
    * Qual a distribuição dos exames por modalidade?
    * Quantos atletas federados do "GDGoma" fizeram EMD?
    * Quantos atletas do género feminino que praticam Triatlo fizeram EMD?
4. Cria uma [API](/Ex1/API/routes/index.js) de dados com as seguintes rotas/pedidos:
    * **GET /api/emd**- Devolve a lista de EMD apenas com os campos "id", "nome", "data" e "resultado";
    * **GET /api/emd/:id**- Devolve a informação completa de um EMD;
    * **GET /api/modalidades**- Devolve a lista de modalidades, sem repetições;
    * **GET /api/emd?res=OK**- Devolve a lista de EMD com resultado "true";
    * **GET /api/modalidades/:id**- Devolve a lista de EMD referentes à modalidade passada como parâmetro;
    * **GET /api/atletas?gen=F**- Devolve uma lista ordenada alfabeticamente com os nomes dos atletas de género feminino;
    * **GET /api/atletas?clube=X**- Devolve uma lista ordenada alfabeticamente com os nomes dos atletas do clube X.
- - - -
### Exercício Nº 2: Relações familiares:
**Parte 1:** Especifica as seguintes [queries](/Ex2/queries.txt) CONSTRUCT:
1. Constrói os triplos da relação Irmão;
2. Constrói os triplos da relação Bisavô;
3. Constrói os triplos da relação Descendentes;
4. Constrói os triplos da relação Primo;
5. Acrescenta o atributo sexo à tua ontologia, há várias formas de o fazer, escolhe a que entenderes. Constrói os triplos relacionados com esta propriedade usando as propriedades existentes: temPai e temMae;
6. Constrói os triplos das pessoas relacionadas com "José_Carlos_Leite_Ramalho_1967", diretamente ou indiretamente, ou seja, para todas as pessoas com caminho no grafo até ao indivíduo em questão constrói o triplo: :José_Carlos_Leite_Ramalho_1967 :temRelacaoCom :indivíduoX.

**Parte 2:** Constrói um [programa](/Ex2/script.py), numa linguagem à tua escolha, para enviar ao graphdb uma das queries anteriores (à tua escolha) e de seguida com o resultado obtido enviar novo pedido para aumentar a ontologia (realizar um "insert" dos triplos obtidos).
- - - -
<div dir="rtl"> 
realizado por <i>Gonçalo Pinto - A83732</i>