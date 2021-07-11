%{
  #include <stdio.h>
  #include <strings.h>
  #include <stdlib.h>
  #include <math.h> //compilar com -lm

  int erro = 0;
%}


%union{ float num; int um_ou_zero; }

%token <num>NUM
%token <um_ou_zero>boolean
%token AND
%token OR
%token NOT

%type  <num>Fator
%type  <num>Termo
%type  <num>Expr


%%
Calc : Expr '\n'         { if(!erro) printf("=> %.3f\n", $1); erro=0;}
     | Calc Expr '\n'    { if(!erro) printf("=> %.3f\n", $2); erro=0;}
     ;

Expr : Termo              { $$ = $1;       }
     | Expr OR  Termo     { $$ = $1 || $3; }
     | Expr '+' Termo     { $$ = $1 +  $3; }
     | Expr '-' Termo     { $$ = $1 -  $3; }
     ;

Termo : Fator             { $$ = $1;       }
      | Termo AND Fator   { $$ = $1 && $3; }
      | Termo '*' Fator   { $$ = $1 *  $3; }
      | Termo '/' Fator   { if ($3 == 0)   {yyerror("Divisao por 0\n"); erro = 1;} else { $$ = $1 / $3;} }
      | Termo '^' Fator   { $$ = pow($1,$3); }
      ;

Fator : NUM               { $$ = $1;       }
      | boolean           { $$ = $1;       }
      | '-' Fator         { $$ = -1 * $2;  }   
      | NOT Fator         { $$ = !$2;      }
      | '(' Expr ')'      { $$ = $2;       }
      ;
%%


#include "lex.yy.c"


int yyerror(char *s) { 
  printf("ERRO: %s\n", s);
  return(0);
}


int main()
{
  yyparse();
  return(0);
}