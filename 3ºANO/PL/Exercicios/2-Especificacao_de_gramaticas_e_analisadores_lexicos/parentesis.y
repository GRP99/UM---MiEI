%{
	#include <stdio.h>
	#include <strings.h>

	extern int yylex();
	extern int yylineno;
	extern char* yytext;

	int yyerror();
	int erroSem(char* s);
%}

%token ERRO

%%
Par : '(' Par ')' Par
	| 
	;
%%
#include "lex.yy.c"

int erroSem(char* s){
    printf("Erro Semântico na linha: %d: %s\n", yylineno, s);
    return 0;
}

int yyerror(){
    printf("Erro Sintático ou Léxico na linha: %d, com o texto: %s\n", yylineno, yytext);
    return 0;
}

int main(){
    yyparse();
    return 0;
}