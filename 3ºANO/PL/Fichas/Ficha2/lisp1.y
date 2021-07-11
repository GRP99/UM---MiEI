%{
#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
%}

%token pal
%token num

%%
Lisp : SExp
	 ;

SExp : pal
	 | num
	 | '(' SexpList ')'
	 ;

SexpList : SExp SexpList
		 |
		 ;

%%
#include "lex.yy.c"

int yyerror(char *s) { 
	printf("ERRO: %s\n",s);
	return(0);
}

int main(){
	yyparse();
	return 0;
}