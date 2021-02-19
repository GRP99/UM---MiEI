%{
	#include "y.tab.h"
%}

%option noyywrap
%option yylineno

%%

[()]	{ return *yytext; }

.		{ return ERRO; }


%%