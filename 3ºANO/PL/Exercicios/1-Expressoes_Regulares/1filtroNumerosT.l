%{
/* Declaracoes C diversas */
%}

%%
[2][0-9]{8}     	{ printf("Telefones: %s\n",yytext); }
[9][0-9]{8}     	{ printf("Telemoveis: %s\n",yytext); }
112					{ printf("Emergencia: %s\n",yytext); }
[78]0[078][0-9]{6}  { printf("Outros: %s\n",yytext); }
.|\n				{;}

%%
int yywrap()
{ return(1); }

int main()
{ yylex(); return 0; }
