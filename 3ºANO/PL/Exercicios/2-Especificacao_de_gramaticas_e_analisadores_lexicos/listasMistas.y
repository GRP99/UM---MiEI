%code{
	#define _GNU_SOURCE 
	#include <stdio.h>
	int yylex();
	int yyerror(char* s);
}

%union {char* n;}
%token num pal VIRG FIM INICIO 
%type <n> num pal elem cauda VIRG lista FIM INICIO 


%%
frase : INICIO lista FIM 	{ printf("%s %s %s \n",$1,$2,$3);}
	  ;	   

lista : elem				{ asprintf(&$$,"%s",$1); }
	  | elem VIRG cauda		{ asprintf(&$$,"%s %s %s \n",$1,$2,$3); }
	  ;

cauda : lista				{ asprintf(&$$,"%s",$1);}
	  ;

elem : pal 					{ asprintf(&$$,"%s",$1); }
	 | num					{ asprintf(&$$,"%s",$1); }
	 ;
%%

#include "lex.yy.c"

int main(){
   yyparse();
   return 0;
}

int yyerror(char* s){
   printf("erro %d: %s junto a'%s'\n",yylineno,s,yytext);
}