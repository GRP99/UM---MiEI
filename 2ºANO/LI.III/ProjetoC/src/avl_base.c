#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "avl_base.h"


struct AVL{
    char* id; 
    struct AVL *esq;
    struct AVL *dir;
    int alt;
}AVL;


/*----------------------------------------------------------------------------------------------------*/
char* strdup(char*);
int max(int a, int b){
    if (a > b) return a;
    else return b;
}
int altura(BASICAVL a){
    if (a == NULL) return 0;
    return a->alt;
}
int getInclinacao (BASICAVL a){
    if (a == NULL) return 0;
    return altura(a->esq) - altura(a->dir);
}

BASICAVL rodarDireita(BASICAVL a){
    BASICAVL x;
    x = a->esq;
    a->esq = x->dir;
    x->dir = a;
    a->alt = max(altura(a->esq), altura(a->dir))+1;
    x->alt = max(altura(x->esq), altura(x->dir))+1;
    return x;
}

BASICAVL rodarEsquerda(BASICAVL a){
    BASICAVL x;
    x = a->dir;
    a->dir = x->esq;
    x->esq = a;
    a->alt = max(altura(a->esq), altura(a->dir))+1;
    x->alt = max(altura(x->esq), altura(x->dir))+1;
    return x;
}

BASICAVL novoNo (char* id){
    BASICAVL no = (BASICAVL) malloc(sizeof(AVL));
    no->id = strdup(id);
    no->esq = NULL;
    no->dir = NULL;
    no->alt = 1;
    return no;
}

void free_BAVL(BASICAVL x){
    if (x==NULL) return;
    free_BAVL(x->esq);
    free_BAVL(x->dir);
    free(x->id);
    free(x);
    return;
}

BASICAVL init_BAVL(){
    BASICAVL aux = (BASICAVL)malloc(sizeof(struct AVL));
    aux->id = NULL;
    aux->esq = NULL;
    aux->dir = NULL;
    aux->alt = 0;
    return aux;
}

int existe(BASICAVL a, char* id){
    if (a == NULL) return 0;
    if (strcmp(id,a->id) == 0) return 1;
    else if(strcmp(id,a->id)<0) return existe(a->esq,id);
         else return existe(a->dir,id);
}

BASICAVL procura(BASICAVL a, char * id){
    if (a == NULL) return NULL;
    if (strcmp(id,a->id) == 0) return a;
    else if(strcmp(id,a->id)<0) return procura(a->esq,id);
         else return procura(a->dir,id);
}

BASICAVL insere(BASICAVL a, char*id){
    int inc=0;
    if (a == NULL) return novoNo(id);
    if (strcmp(id,a->id) != 0){
        if (strcmp(id,a->id) < 0) a->esq = insere(a->esq,id);
        else a->dir = insere(a->dir,id);
        a->alt = max(altura(a->dir), altura(a->esq)) +1;
        inc = getInclinacao(a);
        if (inc > 1 && strcmp(id,a->esq->id) < 0) return rodarDireita(a);
        if (inc < -1 && strcmp(id,a->dir->id) >= 0) return rodarEsquerda(a);
        if (inc > 1 && strcmp(id,a->esq->id) >= 0){
            a->esq = rodarEsquerda(a->esq);
            return rodarDireita(a);
        }
        if (inc < -1 && strcmp(id,a->dir->id) < 0){
            a->dir = rodarDireita(a->dir);
            return rodarEsquerda(a);
        }
        return a;
    }
    else return a;
}


/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>QUERIES<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<2*/
int giveMeAll(BASICAVL a,char** list, int tam){
    if(a != NULL){
        tam = giveMeAll(a->esq,list,tam);
        (list[tam])=strdup(a->id);
        tam++;
        tam = giveMeAll(a->dir,list,tam);
        return tam;
        }
    return tam;
    }
char** giveMe (BASICAVL ba,int tot,int* size){
    char** array=NULL;
    int x=0;
    if (ba!=NULL){
        array = (char**) malloc(tot*sizeof(char*));
        array[0] = NULL;
        x = giveMeAll(ba,array,0);
    }
    *size = x;
    return array;
}

/*#################################################################################################*/