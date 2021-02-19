%{
	#include "y.tab.h"
%}


%%
[0-9]+(\.[0-9]+)? 		{ yylval.num = atof(yytext); return NUM; }
(?i:true)				{ yylval.um_ou_zero=1; return(boolean);  }
(?i:false)				{ yylval.um_ou_zero=0; return(boolean);  }
(?i:and)				{ return(AND); }
(?i:or)					{ return(OR);  }
(?i:not)			    { return(NOT); }
[+\-*/\n()\^]           { return yytext[0]; }
.                       { ; }
%%


int yywrap(){
  return(1);
}
