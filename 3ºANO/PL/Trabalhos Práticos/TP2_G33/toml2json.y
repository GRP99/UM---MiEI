%{
#define _GNU_SOURCE
#include <stddef.h>
#include <string.h>
#include <glib.h>
#include <stdio.h>

typedef struct atrib {
    GSList* nome;
    char* valor_json;
} * Atrib;

typedef struct tabela {
    GSList* nome;
    GSList* at;
} * Tabela;

typedef struct values {
    GHashTable* atr;
    GHashTable* filhos;
    int flag_tabela_ou_array;
} * Values;

GHashTable* tabelas;

GHashTable* atribuicoes;

char* chave_para_array_tabelas = "0";

void insereTabela(Tabela t, int tab_array){
    GHashTable* current = tabelas;
    Values atual_base = NULL;

    int l = g_slist_length(t->nome);
    for (int i = 0; i < l; i++) {
        if (g_hash_table_contains(current, g_slist_nth(t->nome, i)->data)) {
            Values aux = (Values)g_hash_table_lookup(current, g_slist_nth(t->nome, i)->data);

            current = aux->filhos;
            atual_base = aux;
        }
        else {
            Values para_inserir = (Values)malloc(sizeof(struct values));

            para_inserir->filhos = g_hash_table_new(g_str_hash, g_str_equal);
            para_inserir->atr = g_hash_table_new(g_str_hash, g_str_equal);
            para_inserir->flag_tabela_ou_array = 1;

            g_hash_table_insert(current, g_slist_nth(t->nome, i)->data, para_inserir);

            current = para_inserir->filhos;

            atual_base = para_inserir;
        }
    }

    int latrib = g_slist_length(t->at);
    for (int c = 0; c < latrib; c++) {
        Values atual = atual_base;
        GHashTable* current_por_Atribuicao = current;

        Atrib a = (Atrib)g_slist_nth(t->at, c)->data;

        int listAtrib = g_slist_length(a->nome);
        for (int p = 0; p < listAtrib - 1; p++) {
            if (g_hash_table_contains(current_por_Atribuicao, g_slist_nth(a->nome, p)->data)) {
                Values aux = (Values)g_hash_table_lookup(current_por_Atribuicao, g_slist_nth(a->nome, p)->data);

                current_por_Atribuicao = aux->filhos;
                atual = aux;
            }
            else {
                Values para_inserir = (Values) malloc(sizeof(struct values));

                para_inserir->filhos = g_hash_table_new(g_str_hash, g_str_equal);
                para_inserir->atr = g_hash_table_new(g_str_hash, g_str_equal);

                g_hash_table_insert(current_por_Atribuicao, g_slist_nth(a->nome, p)->data, para_inserir);

                current_por_Atribuicao = para_inserir->filhos;

                atual = para_inserir;
            }
        }

        atual->flag_tabela_ou_array = tab_array;

        if (tab_array == 0) { 
            // Array elemento = list atributos
            GSList* atribuis = (GSList*)g_hash_table_lookup(atual->atr, chave_para_array_tabelas);
            atribuis = g_slist_append(atribuis, a);
            g_hash_table_insert(atual->atr, chave_para_array_tabelas, atribuis);
        }
        else{
            // tabela elemento = atributo
            g_hash_table_insert(atual->atr, g_slist_last(a->nome)->data, a);
        }
          
    }

    if (tab_array == 0) {
        if (latrib == 0) {
            g_hash_table_insert(atual_base->atr, chave_para_array_tabelas, NULL);
            atual_base->flag_tabela_ou_array = 0;
        }
        asprintf(&chave_para_array_tabelas, "%d", atoi(chave_para_array_tabelas) + 1);
    }
}


void insereAtribuicoes(GSList* list){
    int size = g_slist_length(list);

    for (int i = 0; i < size; i++) {
        Atrib a = (Atrib)g_slist_nth(list, i)->data;

        if (g_slist_length(a->nome) > 1) {
            Tabela t = malloc(sizeof(struct tabela));
            t->nome = NULL;
            t->at = g_slist_append(NULL, a);
            insereTabela(t, 1);
        }
        else {
            g_hash_table_insert(atribuicoes, g_slist_nth(a->nome, 0)->data, a);
        }
    }
}

int yylex();
int yyerror(char* s);
%}


%union{ char*str; int num; float fnum; GSList * list; Atrib at; Tabela t; }

%token <str>  string expo datahora hora dataa
%token <num>  numero trueOrfalse
%token <fnum> numero_decimal

%type <str> Valor Array 
%type <at> Atribuicao
%type <t>  Tabela
%type <list> Nome
%type <list> ListaAtribuicoes


%%
TOML :  ListaAtribuicoes Tabs                   { insereAtribuicoes($1); }
     ;
  

Tabs : Tabs Tipo
     | 
     ;


Tipo : 't' Tabela                               {   insereTabela($2,1); }
     | 'a' Tabela                               {   insereTabela($2,0); } 
     ;


Tabela : Nome ':' ListaAtribuicoes              {   Tabela t = malloc(sizeof(struct tabela));
                                                    t->nome = $1;
                                                    t->at=$3;
                                                    $$ = t;
                                                }
       ;


Nome : Nome string                              { $$ = g_slist_append ($1,$2); }
     |                                          { $$ = NULL; }       
     ;
    

ListaAtribuicoes : ListaAtribuicoes Atribuicao  { $$=g_slist_append ($1,$2); }
                 |                              { $$=NULL; }
                 ;


Atribuicao : Nome '=' Valor                     {   Atrib a = malloc(sizeof(struct atrib));
                                                    a->nome = $1;
                                                    a->valor_json = strdup($3);
                                                    $$ = a;
                                                }                           
           ;

Valor :   string                                { asprintf(&$$,"\"%s\"",$1); }
      |   numero                                { asprintf(&$$,"%d",$1); }
      |   numero_decimal                        { asprintf(&$$,"%f",$1); }
      |   trueOrfalse                           { if($1) $$="true"; else $$="false"; }
      |   expo                                  { asprintf(&$$,"%s",$1); }
      |   dataa                                 { asprintf(&$$,"\"%s\"",$1); }
      |   datahora                              { asprintf(&$$,"\"%s\"",$1); }
      |   hora                                  { asprintf(&$$,"\"%s\"",$1); }
      |   '[' Array ']'                         { asprintf(&$$,"[ %s ]",$2);}
      ;

Array :  Array ',' Valor                        { asprintf(&$$,"%s,%s",$1,$3); }
      |  Valor                                  { asprintf(&$$,"%s",$1); }
      ;
%%

#include "lex.yy.c"

int yyerror(char* s){
    printf("ERRO: %s\n", s);
    return (0);
}

void escrevepofileAtrib(FILE* file){
    GHashTableIter iter;
    char* key;
    Atrib value;
    g_hash_table_iter_init(&iter, atribuicoes);

    int size_keys = g_hash_table_size(atribuicoes);
    int i = 0;

    while (g_hash_table_iter_next(&iter, (gpointer)&key, (gpointer)&value)) {
        char vir = ',';
        char* aux;
        asprintf(&aux, " \"%s\" : %s\n ", key, value->valor_json);
        printf("%s", aux);              //fwrite(aux,1,strlen(aux),file);
        if (size_keys - i - 1 != 0){
            printf("%c", vir);          //fwrite(&vir,1,1,file);
        }        
        i++;
    }
}

void escrevepofile(FILE* file, GHashTable* t){
    if (t != NULL) {
        GHashTableIter iter;
        char* key;
        Values value;
        g_hash_table_iter_init(&iter, t);

        int size_keys = g_hash_table_size(t);
        int indice = 0;

        while (g_hash_table_iter_next(&iter, (gpointer)&key, (gpointer)&value)) {
            char vir = ',';
            char* aux;

            if (value->flag_tabela_ou_array){
                asprintf(&aux, " \"%s\" : {\n ", key);
            }
                
            else{
                asprintf(&aux, " \"%s\" : [\n ", key);
            }     
            
            printf("%s", aux);          //fwrite(aux,1,strlen(aux),file);

            if (value->flag_tabela_ou_array) {
                GHashTableIter iter2;
                char* key_atr;
                Atrib a;
                g_hash_table_iter_init(&iter2, value->atr);

                int size_keys_atr = g_hash_table_size(value->atr);
                int indice_atr = 0;

                while (g_hash_table_iter_next(&iter2, (gpointer)&key_atr, (gpointer)&a)) {

                    char* nome = g_slist_last(a->nome)->data;

                    char* auxili;

                    asprintf(&auxili, "\"%s\" : %s ", nome, a->valor_json);

                    
                    printf("%s", auxili);           //fwrite(auxili,1,strlen(auxili),file);

                    if (size_keys_atr - indice_atr - 1 != 0)
                    printf("%c", vir);              //fwrite(&vir,1,1,file);
                    indice_atr++;
                }

                if (size_keys_atr > 0 && value->filhos != NULL && g_hash_table_size(value->filhos))
                printf("%c", vir);                  //fwrite(&vir,1,1,file);
                escrevepofile(file, value->filhos);

                char fechar_curvo = '}';
                /* 
                char fechar_reto=']';
                if (value->flag_tabela_ou_array) fwrite(&fechar_curvo,1,1,file);
                else fwrite(&fechar_reto,1,1,file);
                */
                printf("%c", fechar_curvo);

                if (size_keys - indice - 1 != 0){
                    printf("%c", vir);              //fwrite(&vir,1,1,file);
                }
                indice++;
            }
            else {
                GHashTableIter iter2;
                char* key_array;
                GSList* la;
                g_hash_table_iter_init(&iter2, value->atr);

                int size_keys_atr = g_hash_table_size(value->atr);
                int indice_atr = 0;

                // Array Tabelas
                while (g_hash_table_iter_next(&iter2, (gpointer)&key_array, (gpointer)&la)) {
                    char abre_curvo = '{';
                    char fecha_curvo = '}';
                    
                    printf("%c", abre_curvo);       // fwrite(&abre_curvo,1,1,file);

                    if (la != NULL) {
                        int tam_l = g_slist_length(la);

                        for (int i = 0; i < tam_l; i++) {
                            Atrib a = (Atrib)g_slist_nth(la, i)->data;
                            char* nome = g_slist_last(a->nome)->data;

                            char* auxili;
                            asprintf(&auxili, " \"%s\" : %s ", nome, a->valor_json);
                            printf("%s", auxili);   //fwrite(auxili,1,strlen(auxili),file);
                            if (tam_l - i - 1 != 0){
                                printf("%c", vir);  //fwrite(&vir,1,1,file);
                            }
                                
                        }
                    }

                    printf("%c", fecha_curvo);      // fwrite(&fecha_curvo,1,1,file);

                    if (size_keys_atr - indice_atr - 1 != 0){
                        printf("%c", vir);          // fwrite(&vir,1,1,file);
                    }                        
                    indice_atr++;
                }

                if (size_keys_atr > 0 && value->filhos != NULL && g_hash_table_size(value->filhos)){
                    printf("%c", vir);              // fwrite(&vir,1,1,file);
                }
                    
                escrevepofile(file, value->filhos);

                char fechar_curvo = ']';
                /* 
                char fechar_reto=']';
                if (value->flag_tabela_ou_array) fwrite(&fechar_curvo,1,1,file);
                else fwrite(&fechar_reto,1,1,file);
                */
                printf("%c", fechar_curvo);

                if (size_keys - indice - 1 != 0)
                    printf("%c", vir);              //fwrite(&vir,1,1,file);

                indice++;
            }
        }
    }
}


void paraJSON(){
    char* a = "{\n";
    char* b = "}";
    char* v = ",";

    FILE* file = fopen("teste.json", "w");
    printf("%s", a);                        //fwrite(a,1,2,file);

    escrevepofileAtrib(file);

    if (g_hash_table_size(atribuicoes) && g_hash_table_size(tabelas)){
        printf("%s", v);                    // fwrite(v,1,1,file);
    }
        

    escrevepofile(file, tabelas);

    printf("%s", b);                        //fwrite(b,1,1,file);
    fclose(file);
}


int main(){
    tabelas = g_hash_table_new(g_str_hash, g_str_equal);
    atribuicoes = g_hash_table_new(g_str_hash, g_str_equal);

    yyparse();

    paraJSON();

    return 0;
}