#include <stdio.h>
#include <string.h>
#include <ctype.h>
        //»»»ÁRVORES BINÁRIAS«««//
typedef struct nodo {
    int valor;
    struct nodo *esq, *dir;
} *ABin;

//28
int maior(int a, int b){
    if (a>b) return a;
    else return b;
}
int altura(ABin a){
   if ((a == NULL) || (a->esq == NULL && a->dir == NULL)){
       if (a == NULL) return -1;
       else return 0;
   }
   else return 1 + maior(altura(a->esq), altura(a->dir));
}

//29
ABin cloneAB (ABin a){
    ABin ac;
    if (a==NULL) ac=NULL;
    else {
        ac = (ABin) malloc(sizeof(struct nodo));
        ac->valor=a->valor;
        ac->esq=cloneAB(a->esq);
        ac->dir=cloneAB(a->dir);
    }
    return ac;
}

//30
void mirror (ABin *a){
    ABin m;
    if ((*a) != NULL){
        m = (*a)->dir;
        (*a)->dir = (*a)->esq;
        (*a)->esq = m;        
        mirror(&((*a)->dir));
        mirror(&((*a)->esq));
    }
}

//31 --- ____
void inorder (ABin a, LInt *l){
    LInt aux;
    if (a != NULL){
        inorder(a->esq,l);
        aux = *l;
        *l = (LInt) malloc(sizeof(struct lligada));
        (*l)->valor = a->valor;
        (*l)->prox = aux;
        inorder(a->dir,l);
    }
}

//32 --- Killed
void preorder (ABin a, LInt * l) {
   LInt aux;
   while (a != NULL){
       aux = *l;
       *l = (LInt) malloc(sizeof(struct lligada));
       (*l)->valor = a->valor;
       (*l)->prox = aux;
       preorder(a->esq,l);
       preorder(a->dir,l);
   }
}

//33 --- Killed
void posorder (ABin a, LInt * l) {
   LInt aux;
   while (a != NULL){
       posorder(a->esq,l);
       posorder(a->dir,l);
       aux = *l;
       *l = (LInt) malloc(sizeof(struct lligada));
       (*l)->valor = a->valor;
       (*l)->prox = aux;
   }
}

//34
int depth (ABin a, int x){
    int r = -1;
    int resq, rdir;
    if (a!=NULL){
        if (a->valor == x) r=1;
        else {
            resq=depth(a->esq,x);
            rdir=depth(a->dir,x);
            if (resq>0 && rdir>0)
                if (resq<rdir) r = 1+resq;
                else r = 1+rdir;
            else if (resq<0 && rdir>0) r = 1+rdir;
                 else if (rdir<0 && resq>0) r = 1+resq; 
        }
    }
    return r;
}

//35
int freeAB (ABin a){
    int r=0;
    if (a!=NULL){
        r = r + freeAB(a->esq);
        r = r + freeAB(a->dir);
        free(a);
        r++;
    }
    return r;   
}

//36
int pruneAB (ABin *a, int l){
    int r = 0;
    if (*a != NULL){
        r = r + pruneAB(&((*a)->esq),l-1);
        r = r + pruneAB(&((*a)->dir),l-1);
        if (l<=0){
            free(*a);
            *a=0;
            r++;
        }
    }
    return r;
}

//37
int iguaisAB (ABin a, ABin b){
    int r = 0;
    if (a!= NULL && b!=NULL){
         r = (a->valor == b ->valor) && iguaisAB(a->esq,b->esq) && iguaisAB(a->dir,b->dir);
    }
    if (a == NULL && b == NULL) r = 1;
    return r;
}

//38
LInt nivelL (ABin a, int n){
    LInt aux=NULL, l= NULL, i;
    if (a != NULL){
        if (n==1){
            l = (LInt) malloc(sizeof(struct lligada));
            l->valor=a->valor;
            l->prox=NULL;
        }
        else {
            aux=nivelL(a->dir,n-1);
            l=nivelL(a->esq,n-1);
            if (l != NULL){
                for (i=l; i!=NULL && i->prox!=NULL; i=i->prox);
                i->prox=aux;
            }
            else if (aux != NULL) l=aux;
        }
    }
    return l;
}

//39
int nivelV (ABin a, int n, int v[]){
    int i=0;
    if (a!=NULL){
        if (n>1){
            i = i + nivelV(a->esq,n-1,v);
            i = i + nivelV(a->dir,n-1,v+i);
        }
        if (n==1){
            v[i]=a->valor;
            i++;
        }
    }
    return i;
}

//40
int dumpABin (ABin a, int v[], int N){
    int i=0;
    if (a!=NULL && i<N){
        i = i + dumpABin(a->esq,v,N);
        if (i<N){
            v[i]=a->valor;
            i++;
        }
        i = i + dumpABin(a->dir,v+i,N-i);
    }
    return i;
}

//41
int somaABin (ABin a){
    int res=0;
    if (a!=NULL) res = a->valor+somaABin(a->esq)+somaABin(a->dir);
    return res;
}
ABin newNode(int x, ABin e, ABin d) {
    ABin new = (ABin) malloc(sizeof(struct nodo));
    new->valor = x;
    new->esq = e;
    new->dir = d;
    return new;
}
ABin somasAcA (ABin a){
    ABin new = NULL; 
    if (a!=NULL) new = newNode(somaABin(a), somasAcA (a->esq),somasAcA (a->dir));
    return new;
}

//42
int contaFolhas (ABin a){
    int conta=0;
    if (a!=NULL){
        if (a->esq == NULL && a->dir == NULL) conta++;
        else {
            conta = conta + contaFolhas(a->esq);
            conta = conta + contaFolhas(a->dir);
        }
    }
    return conta;
}

//43
ABin cloneMirror (ABin a){
    ABin a2=NULL;
    if (a!=NULL){
        a2 = (ABin) malloc(sizeof(struct nodo));
        a2->valor = a->valor;
        a2->esq = cloneMirror(a->dir);
        a2->dir = cloneMirror(a->esq);
    }
    return a2;
}

//44
int addOrd (ABin *a, int x){
    int r=0;
    while ((*a)!=NULL && r==0){
        if ((*a)->valor > x) a=&((*a)->esq);
        else if ((*a)->valor < x) a=&((*a)->dir);
             else r=1;
    }
    if (r==0){
        *a = (ABin) malloc(sizeof(struct nodo));
        (*a)->valor = x;
        (*a)->esq = NULL;
        (*a)->dir = NULL;
    }
    return r;
}

//45
int lookupAB (ABin a, int x){
    int r = 0;
    while (a != NULL && r==0){
        if (a->valor > x ) a = a->esq;
        else if (a->valor < x) a = a->dir;
             else r = 1;
    }
    return r;
}

//46
int depthOrd (ABin a, int x){
    int r = 0, i = 0;
    while (a != NULL && r==0){
        if (a->valor < x) a = a->dir;
        else if (a->valor > x) a = a->esq;
             else r = 1;
        i++;
    }
    if (r==0) i=-1;
    return i;
}

//47
int maiorAB (ABin a){
    while (a->dir != NULL){
        a=a->dir;
    }
    return a->valor;
}

//48
void removeMaiorA (ABin *a){
    ABin ant = NULL;
    ABin aux = *a;
    while (aux->dir != NULL){
        ant = aux;
        aux = aux->dir;
    }
    if (ant == NULL) *a= aux->esq;
    else ant->dir = aux->esq;
    free(aux);
}

//49
int quantosMaiores (ABin a, int x){
    int c = 0; 
    if (a != NULL){
        if (a->valor >= x){
            if (a->valor > x) c++;
            c = c + quantosMaiores(a->esq,x);
            c = c + quantosMaiores(a->dir,x);
        }
        else c = c + quantosMaiores(a->dir,x);
    }
    return c;
}

//50
void insere (ABin a, ABin *ea){
    while (*ea != NULL){
        if ((*ea)->valor > a->valor) ea=&((*ea)->esq);
        else if((*ea)->valor<a->valor) ea=&((*ea)->dir);
    }
    *ea = a;
}
void listToBTree (LInt l, ABin *ea){
    ABin a;
    while (l != NULL){
        a = (ABin) malloc(sizeof(struct nodo));
        a->valor = l->valor;
        a->esq = a->dir = NULL;
        insere (a,ea);
        l = l->prox;
    }
}

//51
int procura (ABin a, int min, int max){
    int r = 1;
    if (a != NULL){
        r = procura(a->esq,min,a->valor) && procura(a->dir,a->valor,max) && (a->valor >= min && a->valor <= max);
    }
    return r;
}
int deProcura (ABin a){
    int r = 1;
    ABin min=a, max=a;
    if (a != NULL){
        while (min->esq != NULL) min = min->esq;
        while (max->dir != NULL) max = max->dir;
        r = procura(a,min->valor,max->valor);
    }
    return r;
}