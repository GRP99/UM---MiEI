%{
/* Declaracoes C diversas */
%}


%%
Cm 			{ printf("Como"); }
Hj 			{ printf("Hoje"); }
bibl 		{ printf("biblioteca"); }
Ñ   		{ printf("Não"); }
ñ  			{printf("não"); }
amer[.]+ 	{printf("americano");}
autobiogr 	{printf("autobiografia");}
Jan[.]+ 	{printf("Janeiro");}
apres[.]+ 	{printf("apresentação");}
%%

int yywrap()
{ return(1); }

int main()
{ yylex(); return 0; }
