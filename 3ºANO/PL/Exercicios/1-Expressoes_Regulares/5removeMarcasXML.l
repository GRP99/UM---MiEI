%{
	#include <stdio.h>
	#include <string.h>

	int abre=0, fecho=0;
%}

%option noyywrap

%%
\<\/[^>]*\>		{ abre++; }
				/* retira a Marca de Fecho e conta os fechos */

\<[^>]+\> 		{ fecho++; }
				/* retira a Marca de Abertura e conta as aberturas */
				/* todos os restantes carateres fora das marcas s~ao copiadas para a sa´ıda */
%%

int main() {
	yylex();
	if (abre != fecho) { printf("ERRO! texto desbalanceado\n"); }
	return 0;
}