#include "estado.h"

void push(ESTADO *e, ESTADOPOS ep){
	if (e->stack.top < MAX_STACK -1) {
		e->stack.top = e->stack.top + 1;
		e->stack.sap [e->stack.top] = ep;
	}
}

int isempty(ESTADO e){
	if (e.stack.top == -1) {return 1;}
	else {return 0;}
}

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