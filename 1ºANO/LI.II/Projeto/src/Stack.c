#include "estado.h"

#define FALSE 0
#define TRUE  1

/**
\brief Funcao que acrescenta um estado na stack
@param e Uma string contendo o estado
@param ep A stack
*/
void push(ESTADO *e, ESTADOPOS ep){
    if (e->stack.top < MAX_STACK -1) {
        e->stack.top = e->stack.top + 1;
        e->stack.sap [e->stack.top] = ep;
    }
}

/**
\brief Funcao que testa se a stack esta vazia
@param e Estado
@returns A veracidade do que se está a testar
*/
int isempty(ESTADO e){
    if (e.stack.top == -1) {return TRUE;}
    else {return FALSE;}
}

/**
\brief Funcao que retira um estado da stack
@param e Uma string contendo o estado
@returns Stack
*/
ESTADOPOS pop(ESTADO *e) {
    ESTADOPOS ep;
    ep.acao.decisao = NADA;
    ep.acao.dec_lin = 0;
    ep.acao.dec_col = 0;
    ep.ancora = 0;
    if (e->stack.top == -1) {
        return ep;
    }
    else {
        e->stack.top = e->stack.top - 1;
        return e->stack.sap [e->stack.top +1];
    }
}