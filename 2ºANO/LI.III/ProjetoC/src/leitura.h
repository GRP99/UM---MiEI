#ifndef LEITURA
#define LEITURA


typedef struct ler* Leitura; 

char* getNome(Leitura l); /*Devolve o nome do ficheiro que se encontra numa Leitura.*/
int getLinhas(Leitura l); /*Devolve o numero de linhas lidas de uma dada Leitura.*/
int getCorretas(Leitura l); /*Devolve o numero de linhas corretas de uma dada Leitura.*/
int getIncorretas(Leitura l); /*Devolve o numero de linhas incorretas.*/
double getTempo(Leitura l); /*Devolve o tempo que demorou a efectuar uma dada Leitura.*/
void free_Lei(Leitura l); /*Liberta espaço ocupado por uma determinada Leitura.*/
Leitura initLer(); /*Cria uma Leitura.*/

void lerFpers(); /*Realiza a leitura dos ficheiros que o utilizador pretende*/
void lerFpred(); /*Realiza a leitura dos ficheiros predefinidos: Produtos.txt; Clientes.txt; Vendas_1M.txt*/

void lerClientes(Leitura cli); /*Funcao designada para ler ficheiros de produtos*/
void lerProdutos(Leitura prod); /*Funcao designada para ler ficheiros de clientes*/
void lerVendas(Leitura vend); /*Funcao designada para ler ficheiros de vendas*/


#endif