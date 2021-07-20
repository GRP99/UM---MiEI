# Ficha pri-2021-normal
## Avaliação: Processamento e Representação de Informação
O objectivo principal desta ficha é testar os conhecimentos obtidos durante as aulas no desenvolvimento de aplicações Web e outras tarefas afins.
- - - -
### Exercício Nº 1: Registos paroquiais do Curral das Freiras da ilha da Madeira
1. *["Data cleansing"](ex1/casamentos.json)*;
2. Criar uma BD em MongoDB para onde importou-se o conteúdo do ficheiro JSON disponibilizado;
3. Criar um servidor nodejs que dê suporte à seguinte API de dados:
    * **GET /api/casamentos** - Devolve a lista dos casamentos, com os campos: date, title e ref;
    * **GET /api/casamentos/:id** - Devolve a informação completa de um casamento (nesta rota, considere para id o campo ref);
    * **GET /api/casamentos?nome=X** - Devolve apenas uma lista com os casamentos onde o nome X aparece incluído no título;
    * **GET /api/casamentos?ano=YYYY** - Devolve a lista de casamentos realizados no ano YYYY;
    * **GET /api/casamentos?byAno=true** - Devolve a lista de casamentos agrupadas por ano, ou seja, devolve uma lista de anos em que a cada ano está associada uma lista de casamentos (coloque apenas a ref e o title do casamento);
    * **GET /api/casamentos/noivos** - Devolve uma lista de nomes dos noivos, ordenada alfabeticamente, e o id do respetivo registo..
- - - -
### Exercício Nº 2: CLAV: Classificação e Avaliação da Informação Pública
1. Desenvolvimento de uma aplicação em nodejs que permita navegar na estrutura de classes do CLAV que é uma plataforma em desenvolvimento no DI/UM em parceria e sob encomenda da Direção Geral do Livro, Arquivos e Bibliotecas (DGLAB) que visa a classificação e a avaliação de toda a documentação circulante na administração pública portuguesa. Requisitos:
    * Necessidade de integrar um token de autorização para realizar os pedidos à API de dados;
    * Na página inicial, para além de um título e outra informação de contexto, deve aparecer a lista de classes de nível 1 (código e título);
    * Todos os campos de informação com códigos de classes devem ser transformados em links que realizam pedidos à tua aplicação de nova página;
    * Na página de cada classe, deve ser mostrada a informação base da classe, uma lista dos seus descendentes caso existam e, se a classe for de nível 3 uma lista dos processos relacionados (cada um destes deve ser um link para o respetivo processo), apenas deves contemplar as relações: eCruzadoCom, eComplementarDe, eSuplementoDe e eSuplementoPara;
    * Em todas as páginas deverá haver um link para voltar à página inicial e outro para a página anterior (nos casos em que justifique);