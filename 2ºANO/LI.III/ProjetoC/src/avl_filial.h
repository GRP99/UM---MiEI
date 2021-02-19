#ifndef AVL_FILIAL
#define AVL_FILIAL


typedef struct DADOS* INFORM;
typedef struct FIL* AVLFILIAL;

void free_FIAVL(AVLFILIAL x); /*Liberta espaço ocupado por uma determinada AVLFILIAL.*/
AVLFILIAL init_FIAVL(); /*Cria uma AVLFILIAL.*/
int existeFil(AVLFILIAL afil, char* idC); /*Indica se um dado cliente existe ou nao retornando 1 ou 0 respetivamente.*/
AVLFILIAL procuraFil(AVLFILIAL afil, char* idC); /*Retorna o nodo da AVL,ou seja, retorna toda a informaçao que um determinado cliente existente numa AVLFILIAL possui.*/
AVLFILIAL insereCli(AVLFILIAL afil, char* idP,double preco, int unidades, char tipo, char* idC, int mes, int fil); /*Insere um determinado cliente juntamente com informacao sobre uma compra feita numa AVLFILIAL.*/

char** criaArrayFi(AVLFILIAL afi[],int to,int* ta); /*QUERIE.5 -> Cria uma lista com todos os clientes que compraram em todas as filiais*/
int ngmCompra (AVLFILIAL afi); /*QUERIE.6 -> Retorna quantos clientes nao realizaram qualquer compra*/
void getCompras(AVLFILIAL fi,char cli[],int totCF1[],int totCF2[],int totCF3[]); /*QUERIE.7 -> Preenche os totCF1[],totCF2[],totCF3[] com o numero total de compras mensalmente de um determinado cliente.*/
char** inicArrayCliN(AVLFILIAL fi[],int max,int* tam,char*produto,int f); /*QUERIE.9 -> Cria uma lista com todos os clientes que compraram o produto em modo normal.*/
char** inicArrayCliP(AVLFILIAL fi[],int max,int* tam,char*produto,int f); /*QUERIE.9 -> Cria uma lista com todos os clientes que compraram o produto em modo promocao.*/
char** inspector(AVLFILIAL afi,char c[],int m,int* lTot); /*QUERIE.10 -> Percorre a filial retornando uma lista com todos os clientes ordenados pelo numero de compras que compraram o produto num determinado mes.*/
int* encontra(AVLFILIAL afi,char c[],int m,char** lProd); /*QUERIE.10 -> Retorna uma lista com a quantidade que cada produto ordenado numa determinada lista atraves de inspector possui correspondendo a posicao na lista respetivamente.*/
int percorreCli(AVLFILIAL afi, char* codigo, int ind, int fil); /*QUERIE.11 -> Conta quantos clientes compraram um determinado produto numa dada filial.*/
void top3C(AVLFILIAL fi,char* cl,char* aPr[],int aTPr[]); /*QUERIE.12 -> Insere aProd os 3 produtos mais comprados por um determinado cliente tambem insere em aTPr os valores correspondentes.*/

#endif