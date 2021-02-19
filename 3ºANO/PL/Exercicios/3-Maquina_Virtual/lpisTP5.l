%{
	#include "y.tab.h"
%}

%%
[;:,\[\]!?()=\-+*\/<>]		{return yytext[0];}
(?i:if)	   					{return IF;}
(?i:then)	   				{return THEN;}
(?i:else)	   				{return ELSE;}
(?i:fi)	   					{return FI;}
(?i:var)	   				{return VAR;}
(?i:integer)				{return INTEGER;}
(?i:begin)	  				{return BEG;}
(?i:end)	    			{return END;}
[a-zA-Z]	   				{yylval.c = toupper(yytext[0]); return id;}
[0-9]+						{yylval.n = atoi(yytext); return num;}

#[^#]+#		 				{;}
.|\n 						{;}
%%

int yywrap(){
	return(1);
}