%{
#include <stdio.h>
#include <stdlib.h>
#include <strings.h>

int comp=0;
float soma=0;
%}

%union{float val; int inteiro;}

%token pal
%token <val>num
%type <inteiro>SexpList

%%
Lisp : SExp
	 ;

SExp : pal
	 | num					{ soma += $1; }
	 | '(' SexpList ')' 	{ printf("%d\n",$2); }
	 ;

SexpList : SExp SexpList	{ comp++; $$=$2+1; }
		 |					{ comp=0; $$=0; }
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
	printf("Somatório total dos números das Listas : %f\n",soma);
	return 0;
}