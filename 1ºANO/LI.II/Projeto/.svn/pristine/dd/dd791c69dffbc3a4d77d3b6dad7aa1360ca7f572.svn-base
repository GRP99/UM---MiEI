#ifndef ___ESTADO_H___
#define ___ESTADO_H___
/**
@file estado.h
Definicao do estado e das funcoes que convertem estados em strings e vice-versa
*/

/** \brief O tamanho maximo da grelha */
#define MAX_GRID		20
#define MAX_STRING_USER 20
#define MAX_STACK		100	

typedef enum {BLOQUEADA, FIXO_X, FIXO_O, VAZIA, SOL_X, SOL_O} VALOR;
typedef enum {NADA, MARCA_X, MARCA_O, MARCA_VAZIO, UNDO, MARCA_ANCORA, VOLTA_ANCORA,VALIDA,PUZZLE1,PUZZLE2,PUZZLE3,AJUDA,COMECAR,RESOLVER} JOGADAS;

/**
\brief Estrutura que armazena as diferentes jogadas
*/
typedef struct acao {
	int decisao;
	int dec_lin;
	int dec_col;
} ACAO;

/**
\brief Estrutura que armazena a estrutura acao e a variavel "ancora"
*/
typedef struct estadopos {
	ACAO acao;
	char ancora;
} ESTADOPOS;

/**
\brief Estrutura que armazena a stack e o apontador para o seu topo
*/
typedef struct stackaltpos {
	ESTADOPOS sap[MAX_STACK];
	int top;
} STACKALTPOS;

/**
\brief Estrutura que armazena o estado do jogo
*/
typedef struct estado {
	char num_lins;
	char num_cols;
	char num_jog;
	char num_tab;
	char ancora;
	unsigned char grelha[MAX_GRID][MAX_GRID];
	STACKALTPOS stack;
	ACAO acao;
	char user [MAX_STRING_USER];
} ESTADO;

char *estado2str(ESTADO e);

ESTADO str2estado(char *argumentos);

#endif