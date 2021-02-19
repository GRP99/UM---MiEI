#include <stdio.h>
#include <string.h>
// < ÁRVORES BINÁRIAS >

//1
typedef struct abin {
		char *pal;
		int ocorr;
		struct abin *esq, *dir;
} *Dicionario;

void initDic (Dicionario *d){
	*d = NULL;
}
int acrescenta (Dicionario *d, char *pal){
	int res = 1;
	while (*d != NULL && strcmp(pal,(*d)->pal)!= 0){
		if (strcmp(pal,(*d)->pal)<0) d =&((*d)->esq);
		else d = &((*d)->dir);
	}
	if ((*d)!=NULL) res = ++(*d)->ocorr;
	else {
		Dicionario new = (Dicionario) malloc(sizeof(struct abin));
		new->pal = pal;
		new->ocorr = 1;
		new->esq=new->dir=NULL;
		*d = new;
	}
	return res;
}
char *maisFreq (Dicionario d, int *c){
	char *pesq,*pdir,*pal = NULL;
	int maxesq, maxdir;
	*c = 0;
	if (*d != NULL){
		pesq = maisFreq(d->esq,&maxesq);
		pdir = maisFreq(d->dir,&maxesq);
		*ec = d->ocorr;
		pal = d->pal;
		if (maxesq > *c){
			*c = maxesq;
			pal = pesq;
		}
		if (maxdir > *c){
			*c = maxdir;
			pal = pdir;
		}
	}
	return pal;
}

//2
typedef struct abin {
	int valor;
	struct abin *esq,
				*dir;
} *ABin;
typedef struct lista {
	int valor;
	struct lista *prox;
} *LInt;
LInt fromListN (ABin *el,LInt l,int N){
	if (N==0 || l = NULL) *el = NULL;
	else {
		N--;
		(*el) = (ABin) malloc (sizeof(struct *abin));
		fromListN(&((*el)->esq,l,N/2));
		if (l != NULL){
			(*el)->valor = l->valor;
			l = fromListN(&((*el)->dir),l->prox,N-N/2);
		}
		else {
			(*el)->valor = 0;
			(*el)->dir = NULL;
		}
	}
	return l;
}
ABin fromList (LInt l){
	int len = 0;
	LInt aux;
	ABin res;
	for (aux=l;aux != NULL; aux = aux->prox){
		len++;
	}
	fromListN(&res,l,len);
	return res;
}

LInt inorderAux (ABin a, LInt *end){
	LInt res = NULL, laste,new,lastd;
	if (a != NULL){
		res = inorderAux (a->esq,&laste);
		new = malloc(sizeof(struct lista));
		new->valor = a->valor;
		if (res != NULL) laste->prox=new;
		else res = new;
		new->dir = inorderAux (a->dir,&lastd);
		if (new->dir != NULL) *last = lastd;
		else *last = new;
	}
	return res;
}

