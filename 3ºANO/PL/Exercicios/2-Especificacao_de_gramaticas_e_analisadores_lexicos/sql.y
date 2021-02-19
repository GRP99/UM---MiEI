%{
  #include <stdio.h>
  #include <stdlib.h>
  #include <strings.h>
%}

%union {float valor; char* str;}

%token SELECT FROM WHERE ORDER GROUP BY AND OR AS NOT
%token <str>id
%token <valor>num

%%
STMSQL : Projecao Origem Filtro Opcoes 
       ;

Projecao : SELECT Colunas
         ;

Colunas : '*'
     	| Nome
     	;

Nome : id
     | Nome ',' id
     ;

Origem : FROM Tabelas
       ;

Tabelas : Tabelas ',' Tabela
     	| Tabela
     	;

Tabela : id
       | id AS id
       ;

Filtro : WHERE '(' Condicao ')'
       ;

Condicao : Expressao
         | Expressao '=' Expressao
         ;

Expressao : Termo
          | Expressao OR Termo
          ;

Termo : Fator
      | Termo AND Fator
      ;

Fator : id
      | num
      | NOT Fator
      | '(' Condicao ')'
      ;

Opcoes : 
       | Opcoes Opcao
       ;

Opcao : ORDER BY id
      | GROUP BY id
      ;
%%

#include "lex.yy.c"

int yyerror(char *s) { 
  printf("ERRO: %s\n", s);
  return(0);
}

int main(){
  yyparse();
	return 0;
}
