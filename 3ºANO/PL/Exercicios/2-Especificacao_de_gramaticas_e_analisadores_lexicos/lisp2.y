%{
#include <stdio.h>
#include <stdlib.h>
#include <strings.h>

int conta=0;
int comp=0;
%}

%token pal
%token num

%%
Lisp : SExp
	 ;

SExp : pal
	 | num
	 | '(' SexpList ')' 	{ conta++; }
	 ;

SexpList : SExp SexpList	{ comp++; }
		 |					{ comp=0; }			
		 ;

%%
#include "lex.yy.c"

int yyerror(char *s) { 
	printf("ERRO: %s\n",s);
	return(0);
}

int main(){
	yyparse();
	printf("Comprimento da Lista : %d\n",comp);
	printf("Número de Listas encontradas : %d\n",conta);
	return 0;
}