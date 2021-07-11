%{
#include <stdio.h>
#include <string.h>
#define max 20
%}
%option noyywrap
%option stack

%x TXT
%%
\<[a-zA-Z]+[^>]*\> 					{ yy_push_state(TXT);}
								/* quando encontra uma Marca de Abertura ativa um novo Estado */
<TXT>\<\/[a-zA-Z]+\> 				{ yy_pop_state(); }
								/* ao encontrar uma Marca de Fecho retorna ao Estado anterior */
<TXT>\<[a-zA-Z]+[^>]*\> 			{ yy_push_state(TXT);}
<TXT>[^<]* 							{ printf("Texto anotado: [%s]\n",yytext); }
.|\n 							/* todos os restantes carateres dentro ou fora das marcas s~ao eliminados */
%%

int main() {
	yylex();
	return 0;
}