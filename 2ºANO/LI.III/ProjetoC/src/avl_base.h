#ifndef AVL_BASE
#define AVL_BASE


typedef struct AVL* BASICAVL;

void free_BAVL(BASICAVL x); /*Liberta espaço ocupado por uma determinada BASICAVL.*/
BASICAVL init_BAVL(); /*Cria uma BASICAVL.*/
int existe (BASICAVL a, char* id); /*Indica se um dado ID existe ou nao retornando 1 ou 0 respetivamente.*/
BASICAVL procura(BASICAVL a, char * id); /*Retorna o nodo da AVL caso exista um determinado ID numa BASICAVL.*/
BASICAVL insere(BASICAVL a, char*id); /*Insere um determinado ID numa BASICAVL.*/

char** giveMe (BASICAVL ba,int tot,int* size); /*QUERIE.2 -> Transforma uma BASICAVL numa lista com os codigos que tinha nela.*/


#endif