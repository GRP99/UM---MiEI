%{
#include <stdio.h>
#include <strings.h>
/* Declaracoes C diversas */
%}

%union{ char *sval; int nval; }

%token FIM <nval>term
%type  X <sval>Y

%%
X : Y 'c'
  | FIM
  ;
Y : 
  | Y term
  ;
%%

int yyerror(char *s)
{
  fprintf(stderr, "ERRO: %s \n", s);
}

int main()
{
  /*prologo*/
    yyparse();
  /*epilogo*/
  return(0);
}
