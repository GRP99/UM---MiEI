#ifndef AVL_FACTURACAO
#define AVL_FACTURACAO


typedef struct FACT* AVLFACTURACAO;


void free_FAAVL(AVLFACTURACAO x); /*Liberta espaço ocupado por uma determinada AVLFACTURACAO.*/
AVLFACTURACAO init_FAAVL(); /*Cria uma AVLFACTURACAO.*/
int existeFact (AVLFACTURACAO af, char* id); /*Indica se um dado produto existe ou nao retornando 1 ou 0 respetivamente.*/
AVLFACTURACAO procuraFact (AVLFACTURACAO af, char* id); /*Retorna o nodo da AVL,ou seja, retorna toda a informaçao que um determinado produto existente numa AVLFACTURACAO possui.*/
AVLFACTURACAO insereFact (AVLFACTURACAO af, char* id, double preco, int unidades, char tipo, int mes, int fil); /*Insere um determinado produto associado a informacao numa AVLFACTURACAO.*/

void conta_Fact1(AVLFACTURACAO af,char* i, int m,double res[]); /*QUERIE.3 -> Retorna atraves de res[] quantas unidades e foi facturado num determinado mes.*/
void conta_Fact2(AVLFACTURACAO af,char* i, int m,double res[]); /*QUERIE.3 -> Retorna atraves de res[] quantas unidades e foi facturado por filial num determinado mes.*/
char** criaArrayF(AVLFACTURACAO af[],int total,int* tam); /*QUERIE.4 -> Cria uma lista com todos produtos que nao foram vendidos.*/
int comprasEntre(AVLFACTURACAO af, int ini, int fim); /*QUERIE.8 -> Devolve quantas unidades foram vendidas entre ini e fim.*/
int facturacaoEntre(AVLFACTURACAO af, int ini, int fim); /*QUERIE.8 -> Devolve quanto foi facturado entre ini e fim.*/
int percorreProd3(AVLFACTURACAO a, char** li, int un[], int ind, int n); /*QUERIE.11 -> Vai insirindo e ordenando todos os produtos mais vendidos na filial 3 em un e em li no maximo tem n elementos cada uma.*/
int percorreProd2(AVLFACTURACAO a, char** li, int un[], int ind, int n); /*QUERIE.11 -> Vai insirindo e ordenando todos os produtos mais vendidos na filial 2 em un e em li no maximo tem n elementos cada uma.*/
int percorreProd1(AVLFACTURACAO a, char** li, int un[], int ind, int n); /*QUERIE.11 -> Vai insirindo e ordenando todos os produtos mais vendidos na filial 1 em un e em li no maximo tem n elementos cada uma.*/ 


#endif