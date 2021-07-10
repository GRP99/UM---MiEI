#include <stdio.h>  
#include <string.h>
#include <ctype.h>
        //»»»LISTAS LIGADAS«««//
typedef struct lligada {
    int valor;
    struct lligada *prox;
} *LInt;

//1
int length (LInt l){
    int i=0;
    while (l != NULL){
        i++;
        l=l->prox;
    }
    return i;
}

//2
void freeL (LInt l){
    LInt * aux;
    while (l != NULL){  
        aux=&(l->prox);
        free(l);
        l=*aux;
    }
}

//3
void imprimeL (LInt l){
    while (l != NULL){
        printf("%d\n",x->valor);
        x=x->prox;
    }
}

//4
LInt reverseL (LInt l){
   LInt r,t;
   r=NULL;
   while (l != NULL){
       t=l;
       l = l->prox;
       t->prox=r;
       r=t;
   }
   return r;
}

//5
void insertOrd (LInt *l, int x){
   LInt new;
   while(*l != NULL && (*l)->valor<x){
       l=&((*l)->prox);
   }
   new = (LInt) malloc (sizeof (struct lligada));
   new->valor = x;
   new->prox  = *l;
   *l=new;
}

//6
int removeOneOrd (LInt *l, int x){
    LInt aux;
    int r = 1;
    while (*l != NULL && (*l)->valor!=x){
        l=&((*l)->prox);
    }
    if (*l != NULL){
        aux = (*l)->prox;
        free(*l);
        *l = aux;
        r = 0;
    }
    return r;
}

//7
void merge (LInt *r, LInt a, LInt b){
    while (a != NULL || b != NULL){
        if ((a==NULL)||(b != NULL && a->valor > b->valor)){
            *r = b;
            r = &(b->prox);
            b = b->prox; 
        }
        else {
            *r = a;
            r = &(a->prox);
            a = a->prox;
        }
    }
}

//8
void splitQS (LInt l, int x, LInt *mx, LInt *Mx){
    LInt aux;
    *Mx = NULL;
    *mx = NULL;
    while (l != NULL){
        aux = l->prox;
        if (l->valor < x){
            *mx=l;
            (*mx)->prox=NULL;
            mx = &(*mx)->prox;
        }
        else{
            *Mx=l;
            (*Mx)->prox=NULL;
            Mx = &(*Mx)->prox;
        }
        l = aux;
    }
}

//9
LInt parteAmeio (LInt *l){
    int i = (length(*l) / 2); //1-»int length (LInt l)
    int j;
    LInt *aux = l;
    LInt r = *l;
    for (j=0;j<i;j++){
        aux=&((*aux)->prox);
    }
    *l = *aux;
    *aux = NULL;
    if (i==0){
        *l = r;
        return NULL;
    }
    else return r;
}

//10
int removeAll (LInt *l, int x){
    int i = 0;
    LInt aux;
    while (*l != NULL){
        if ((*l)->valor == x){
            aux = (*l)->prox;
            free(*l);
            *l = aux;
            i++;
        }
        else l=&((*l)->prox);
    }
    return i;
}

//11
int removeDups (LInt *l){
    int i = 0;
    while (*l != NULL){
        i = i + removeAll(&((*l)->prox),(*l)->valor);   //9-»int removeAll (LInt *l, int x)
        l=&((*l)->prox);
    }
    return i;
}

//12
int removeMaiorL (LInt *l){
    int maior;
    LInt *aux, r;
    for (aux = l; *aux != NULL; aux = &((*aux)->prox)){
        if ((*l)->valor < (*aux)->valor){
            l = aux;
        }
    }
    maior = (*l)->valor;
    r = (*l)->prox;
    free (*l);
    *l = r;
    return maior;
}

//13
void init (LInt *l){
    while (*l != NULL && (*l)->prox != NULL){
        l = &((*l)->prox);
    }
    if ((*l)->prox == NULL){
        free (*l);
        *l = NULL;
    }
}

//14
    void appendL (LInt *l, int x){
        while((*l) != NULL){
            l=&((*l)->prox);
        }
        *l = (LInt)malloc(sizeof(struct lligada));
        (*l)->valor = x;
        (*l)->prox = NULL;
    }

//15
void concatL (LInt *a, LInt b){
    while(*a != NULL){
        a=&((*a)->prox);
    }
    *a=b;
}

//16
LInt cloneL (LInt l){
    LInt aux;
    LInt *r = &r;
    while (*l != NULL){
        *r = (LInt)malloc(sizeof(struct lligada));
        (*r)->valor = l->valor;
        l=l->prox;
        r=&((*r)->prox);
    }
    *r = NULL;
    return aux;
}

//17
LInt cloneRev (LInt l){
    LInt r=0,aux=0;
    while(l != NULL){
        r = (LInt)malloc (sizeof (struct lligada));
        r->valor = l->valor;
        r->prox=aux;
        aux=r;
        l=l->prox;
    }
    return r;
}

//18
int maximo (LInt l){
    int max;
    for (max = l->valor; l != NULL; l=l->prox){
        if (max < l->valor) max = l->valor;
    }
    return max;
}

//19
int take (int n, LInt *l){
    LInt aux;
    int i;
    for (i = 0; (*l)!=NULL && i<n; i++){
        l = &((*l)->prox);
    }
    while ((*l)!=NULL){
        aux = (*l)->prox;
        free(*l);
        *l = aux;
    }
    return i;
}

//20
int drop (int n, LInt *l){
    LInt aux;
    int i;
    for (i=0; (*l)!=NULL && i<n; i++){
        aux = (*l)->prox;
        free(*l);
        *l = aux;
    }
    return i;
}

//21
LInt NForward (LInt l, int N){
    int i ;
    for (i=0;l != NULL && i<N;i++){
        l = l->prox;
    }
    return l;
}

//22
int listToArray (LInt l, int v[], int N){
    int i;
    for (i=0;l!=NULL && i<N;i++){
        v[i]=l->valor;
        l = l ->prox;
    }
    return i;
}

//23
LInt arrayToList (int v[], int N){
    int i = 0;
    LInt inic, *aux=&inic;
    while(i<N){
        *aux = malloc(sizeof(struct lligada));
        (*aux)->valor=v[i];
        aux = &((*aux)->prox);
        i++;
    }
    *aux=NULL;
    return inic;
}

//24
LInt somasAcL (LInt l){
    int i = 0;
    LInt inic, *aux=&inic;
    while(l != NULL){
        *aux = malloc(sizeof(struct lligada));
        i = i + l->valor;
        (*aux)->valor = i;
        aux = &((*aux)->prox);
        l=l->prox;
    }
    *aux = NULL;
    return inic;
}

//25
void remreps (LInt l){
    LInt next,aux;
    while (l != NULL){
        next = l->prox;
        while (next !=NULL && l->valor==next->valor){
            aux=next->prox;
            free(next);
            next=aux;
        }
        l->prox=next;
        l=l->prox;
    }
}

//26
LInt rotateL (LInt l){
    LInt *aux=&l;
    LInt i=NULL;
    while (*aux != NULL){
        aux = &((*aux)->prox);
    }
    *aux=l;
    if (l != NULL){
        i=l->prox;
        l->prox=NULL;
    }
    return i;
}

//27
LInt parte (LInt l){
    LInt ppares;
    LInt *efim = &ppares;
    while (l != NULL){
        if (l->prox != NULL){
            *efim = l->prox;
            efim = &(l->prox->prox);
            l->prox = l ->prox->prox;
        }
        *efim = NULL;
        l=l->prox;
    }
    return ppares;
}