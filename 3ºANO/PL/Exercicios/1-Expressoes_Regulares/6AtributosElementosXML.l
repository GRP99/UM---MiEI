%{
#include <stdio.h>
#include <string.h>
#define max 20
%}
%option noyywrap
%option stack

%x TXT
%%
\<[a-zA-Z]+[^>]*\> 				{ yytext[yyleng-1]=’\0’; printf("\nTexto anotado (%s)[",yytext+1); yy_push_state(TXT);}
								/* quando encontra uma Marca de Abertura ativa um novo Estado */

<TXT>\<\/[a-zA-Z]+\> 			{ yytext[yyleng-1]=’\0’; printf("](%s)",yytext+2); yy_pop_state(); }
								/* ao encontrar uma Marca de Fecho retorna ao Estado anterior */

<TXT>\<[a-zA-Z]+[^>]*\>			{ yytext[yyleng-1]=’\0’; printf("\nTexto anotado (%s)[",yytext+1); yy_push_state(TXT);}

<TXT>[^<]* 						{ printf("%s",yytext); }

<*>.|\n 						/* todos os restantes carateres dentro ou fora das marcas sao eliminados */
%%

int main() {
	yylex();
	return 0;
}