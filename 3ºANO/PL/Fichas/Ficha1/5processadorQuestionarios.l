%{
	#include <string.h>
	#include <stdlib.h>

	char *eu;
	char *ele;
	int i;
%}
%option noyywrap

/* declara 2 Start-Condictions para captar os dois nomes */
%x DEFeu DEFele

%%
^(?i:eu)[ ]*\=[ ]* 		{ BEGIN DEFeu; }
						/* quando encontra o contexto esquerdo apropriado, ativa a SC */
						/* e muda para um estado especifico */

<DEFeu>\..*\n 			{ BEGIN INITIAL; }
						/* quando encontra o fim deste estado, volta ao estado Incial */

<DEFeu>[^.]+ 			{ eu = strdup(yytext); }
						/* dentro do estado especifico capta todos os carateres e guarda-os */


^(?i:ele)[ ]*\=[ ]* 	{ BEGIN DEFele; }
<DEFele>\..*\n 			{ BEGIN INITIAL; }
<DEFele>[^.]+ 			{ ele = strdup(yytext); }

^(?i:eu)[ ]*\: 			{ printf("%s:", eu); }
^(?i:ele)[ ]*\: 		{ printf("%s:", ele); }
%%


int main()
{
	eu = ele = "";
	yylex();
	return(0);
}