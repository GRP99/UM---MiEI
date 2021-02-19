#include <stdio.h>

// < Buffers >

//1
typedef struct slist *LInt;
typedef struct slist {
	int valor;
	LInt prox;
} Nodo;
	//A)
LInt l;
l = (LInt) malloc(sizeof(Nodo));
l -> valor = 10;
l -> prox = (LInt) malloc(sizeof(Nodo));
l -> prox -> valor = 5;
l -> prox -> prox = (LInt) malloc(sizeof(Nodo));
l -> prox -> prox -> valor = 15;
l -> prox -> prox -> prox = NULL;
	//B)
		///i)
LInt cons (LInt l, int x){
	LInt new;
	new = (LInt) malloc(sizeof(Nodo));
	new -> valor = x;
	new -> prox = l;
	return new;
}  
		///ii)
LInt tail (LInt l) {
	LInt aux;
	if (l != NULL){
		aux = l;
		l = l -> prox;
		free (aux);
	}
	return l;
}
		///iii)
LInt init (LInt l){
	LInt last, ant = NULL;
	if (l == NULL) return NULL;
	for (last = l; last->prox !=NULL; last = last->prox){
		ant = last;
	}
	if (ant != NULL){
		ant->prox = NULL;
		free (last);
	}
	else {
		l=NULL;
		free(last);
	}
	return l;
}
		///iv)
LInt snoc (LInt l, int x){
	LInt new,aux,ant = NULL;
	new = cons (x,NULL);
	for (aux = l; aux != NULL; aux = aux->prox){
		ant = aux;
	}
	if (ant) {ant->prox = new;} else {l=new;}
	return l;
}
		///v)
LInt concat (LInt a, LInt b){
	while (a != NULL){
		a = a->prox;
	}
	a->prox = b;
}


//2
	//A)
typedef struct {
	int num;
	char nome[61];
	float nota;
} Aluno;
typedef struct turma {
	Aluno aluno;
	struct turma *prox;
} Nodo, *Turma;
	//B)
int acrescentaAluno (Turma *t, Aluno a){
	Turma t = *t,ant = NULL,new,aux;
	for (aux = t; aux != NULL; aux = aux->prox){
		if ((aux->aluno).nome == a.nome) return 1;
		ant = aux;
	}
	new = (Turma) malloc (sizeof(Nodo));
	new -> aluno = a;
	new -> prox  = NULL;
	if (ant){ant->prox = new;}
	else {t=new;}
	*t = t;
	return 0;
}