%{
	/* Declaracoes C diversas */
%}

Abrev ER
%%
^[eE][uU]\: 		{printf("Pedro Henriques: ");}
^[eE][lL][eE]\: 	{printf("Marcelo Fernandes: ");}
%%

int yywrap()
{ return(1); }

int main()
{ yylex(); return 0; }
