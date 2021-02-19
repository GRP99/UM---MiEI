%{
#define _GNU_SOURCE

#include <stdio.h>
#include <string.h>
#include <ctype.h>

int nextaddress = 0;
int idtable[26];
%}


%union {char c; int n; char *str;}
%token VAR INTEGER BEG END IF THEN ELSE FI
%token <c>id
%token <n>num
%type <str>Declaracoes Instrucoes Decls Decl Cond
%type <str>Tipo Variaveis Variavel Atrib Escrita Leitura Instrucao Expr Fator Termo Condicional
%type <n>Indice 

%%
LPIS : Declaracoes BEG Instrucoes END     {printf("%s START\n %s STOP\n",$1,$3);}
     ;

Declaracoes : VAR Decls                     {$$ = $2;}
            ;

Decls :                                     {$$ = "";}
	    | Decls Decl ';'                      {asprintf(&$$,"%s %s",$1,$2);}
	    ;

Decl : Variaveis ':' Tipo                   {$$ = $1;}
	   ;

Tipo : INTEGER                              {$$= "";}
	   ;

Variaveis : Variavel                        {$$ = $1;}
		      | Variaveis ',' Variavel          {asprintf(&$$,"%s %s",$1,$3);}
		      ;

Variavel : id                               { if(idtable[$1-'A'] == -1){
                                                idtable[$1-'A'] = nextaddress++;
                                                asprintf(&$$,"PUSHI %d\n", 0);
                                              } else {
                                                $$ = "";
                                                yyerror("variável redeclarada!");
                                              }
                                            }
		     | id '[' Indice ']'                { idtable[$1-'A'] = nextaddress; nextaddress += $3;
                                              asprintf(&$$,"PUSHN %d\n", $3);
                                            }
	       ;

Indice : id                                 {$$ = idtable[$1-'A'];}
	     | num                                {$$ = $1;}
	     ;

Instrucoes :                                {$$ = "";}
           | Instrucoes Instrucao ';'       {asprintf(&$$,"%s %s",$1,$2);}
           ;

Instrucao : Atrib         {$$ = $1;}
          | Escrita       {$$ = $1;}
          | Leitura       {$$ = $1;}
          | Condicional   {$$ = $1;}
          ;

Condicional : IF Cond THEN Instrucoes ELSE Instrucoes FI    { 
                                                              asprintf(&$$,"%s JZ senao\n %s JUMP fi\n senao:NOP\n %s fi:NOP\n",$2,$4,$6);
                                                            }
            ;

Atrib : id '=' Expr                         { if(idtable[$1-'A'] != -1){
                                                asprintf(&$$,"%s STOREG %d\n",$3,idtable[$1-'A']);      
                                              }
                                              else {
                                                $$ = "";
                                                yyerror("variavel nao declarada!");
                                              }
                                            }
      ;

Escrita : Expr '!'                          {asprintf(&$$,"%s WRITEI\n",$1);}
        ;

Leitura : id '?'                      { if(idtable[$1-'A'] != -1){
                                                asprintf(&$$,"READ\nATOI\nSTOREG %d\n",idtable[$1-'A']);
                                              }
                                              else {
                                                $$ = "";
                                                yyerror("variavel nao declarada!");
                                              }
                                            }
        ;

Cond : Expr '=''=' Expr       {asprintf(&$$,"%s%s EQUAL \n",$1,$4);}
     | Expr '<''=' Expr       {asprintf(&$$,"%s%s LE \n",$1,$4);}
     ;


Expr : Termo                  {$$ = $1; }
     | Expr '+' Termo         {asprintf(&$$,"%s %s ADD\n",$1,$3);}
     | Expr '-' Termo         {asprintf(&$$,"%s %s SUB\n",$1,$3);}
     ;

Termo : Fator               
      | Termo '*' Fator       {asprintf(&$$,"%s %s MUL\n",$1,$3);}
      | Termo '/' Fator       {asprintf(&$$,"%s %s DIV\n",$1,$3);}
      ;

Fator : id                    {asprintf(&$$,"PUSHG %d\n",idtable[$1-'A']);}
      | num                   {asprintf(&$$,"PUSHI %d\n",$1);}
      | '(' Expr ')'          { $$ = $2;}
%%

#include "lex.yy.c"


int yyerror(char *s){
	printf("erro: %s\n",s);
	return(0);
}


int main(){
  for(int i = 0; i < 26; i++){
    idtable[i] = -1;
  }

  yyparse();

  /*
  for(int i = 0; i < 26; i++){
    printf("Nome da variavel - %c - endereço - %d\n",i+65,idtable[i]);
  }
  */

	return 0;
}