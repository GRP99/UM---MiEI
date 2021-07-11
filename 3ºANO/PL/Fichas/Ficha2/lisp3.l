%{
#include "y.tab.h"
%}

%option noyywrap

%%
[()] 				{ return(yytext[0]); }
[a-zA-Z]+ 			{ return(pal); }
[0-9]+(\.[0-9]+)?   { yylval.val=atof(yytext); return(num); }
.|\n 				{ ; }
%%