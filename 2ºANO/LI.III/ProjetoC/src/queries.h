#ifndef QUERIES
#define QUERIES

typedef struct cp* ConjProds;

void querie0Cli(); /*Cria um catalogo de Clientes -> cria uma BASICAVL com o id do cliente e uma AVLFILIAL com o id do cliente*/
void querie0Pro(); /*Cria um catalogo de Produtos -> cria uma BASICAVL com o id do produto e uma AVLFACTURACAO com o id do produto*/
void querie0Ven(); /*Regista quantas vendas foram validas -> preenchendo a informacao em AVLFILIAL E AVLFACTURACAO*/

void querie1Cli(); /*Detalhes sobre a leitura do ficheiro de Clientes*/ 
void querie1Pro(); /*Detalhes sobre a leitura do ficheiro de Produtos*/ 
void querie1Ven(); /*Detalhes sobre a leitura do ficheiro de Vendas*/ 

void querie2(); /*Obter a lista de produtos iniciados por uma letra e o numero total de produtos dessa dada letra.*/

void querie3(); /*Determinar o total de compras e o total facturado num mês de um determinado produto distinguido por N e P.*/

void querie4(); /*Obter a lista dos codigos dos produtos que ninguem comprou.*/

void querie5(); /*Obter a lista de codigos de clientes que realizaram compras em todas as filiais.*/

void querie6_0(); /*Determinar o numero de clientes registados que nao realizaram compras*/

void querie6_1(); /*Determinar numero de produtos registados que ninguem comprou.*/

void querie7(); /*Obter tabela com o número total de produtos comprados de um cliente, separados por mês.*/

void querie8(); /*Determinar o total de vendas registadas e o total facturado num intervalo.*/

void querie9(); /*Obter os clientes que compraram um produto distinguido entre normal e promocao.*/

void querie10(); /*Determinar a lista de codigos de produtos que um dado cliente comprou num mes.*/

void querie11(); /*Determinar a lista dos N produtos mais vendidos em todo o ano.*/

void querie12(); /*Obter quais os codigos dos 3 produtos que um dado cliente mais gastou durante o ano.*/


#endif