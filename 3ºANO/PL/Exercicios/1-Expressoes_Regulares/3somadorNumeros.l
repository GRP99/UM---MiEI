%{
	int pessoas = 0;
	int familias = 0;
%}

Abrev ER
%%
[0-9]+ 		{pessoas += atoi(yytext); familias++; }
.|\n       	{ ; }

%%
int yywrap(){ 
	return(1); 
}

int main(){ 
	yylex();
	float media = (float) pessoas/familias;
	printf("Total de familias = %d !!\n", familias);
	printf("Total de pessoas = %d !!\n", pessoas);
	printf("A media do agregado familiar = %f. \n", media);
	return 0;
}
