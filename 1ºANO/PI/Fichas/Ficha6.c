#include <stdio.h>
#include <stdlib.h>
// < Buffers >

//1
#define MAX 100
typedef struct stack {
	int sp;
	int valores [MAX];
} STACK;
	//a)
void initStack (STACK *s){
	s->sp = 0;
}
	//b)
int isEmptyS (STACK *s){
	if (s->sp == -1) return 1;
	else return 0;
}
	//c)
int push (STACK *s, int x){
	int r = 0;
	if (s->sp != MAX) {
		s->sp = s->sp + 1;
		s->valores [s->sp] = x;
	}
	else r=1;
	return r;
}
	//d)
int pop (STACK *s, int *x){
	int r = 0;
	if (s->sp != -1) {
		*x = s->valores [s->sp];
		s->sp = s->sp -1;
	}
	else {r= 1;}
	return r;
}
	//e)
int top (STACK *s, int *x){
	int r = 0;
	if (s->sp != -1){
		*x = s->valores[s->sp];
	}
	else {r=1;}
	return r;
}

//2
#define MAX 100
typedef struct queue {
	int inicio, tamanho;
	int valores [MAX];
} QUEUE;
	//a)
void initQueue (QUEUE *q){
	q->inicio = 0;
	(*q).tamanho = 0;
}
	//b)
int isEmptyQ (QUEUE *q){
	return (q -> tamanho == 0);
}
	//c)
int enqueue (QUEUE *q, int x){
	if ((q->tamanho) >= MAX) return 1;
	else {
		q->valores[(q->tamanho + q->inicio)%MAX] = x;
		(q->tamanho)++;
	 	 return 0;
	}
}
	//d)
int dequeue (QUEUE *q, int *x){
	if ((q->tamanho) == 0) {return 1;}
	else {&x = q->valores[q->inicio];
	      (q->tamanho) --;
	      (q->inicio) = (q->inicio + 1) % MAX;
		 }
}
	//e)
int front (QUEUE *q, int *x){
	if ((q->tamanho) == 0) {return 1;}
	else {&x = q->valores[q->inicio]; return 0;}
}

//3
typedef struct stack {
	int size; // guarda o tamanho do array valores
	int sp;
	int *valores;
} STACK;
//////////////////////////////////////////////////
typedef struct queue {
	int size; // guarda o tamanho do array valores
	int inicio, tamanho;
	int *valores;
} QUEUE;

void initQueue (QUEUE *q){
	q->inicio = 0;
	q->tamanho = 0;
	q->size = 2;
	q->valores = (int*) malloc(2*sizeof(int))
}
int enqueue (QUEUE *q, int x){
	if ((q->tamanho) >= size) {
		int *rew = (int*) malloc(2*q->size*sizeof(int));
		int i;
		for (i=0; i<q-tamanho;i++){
			aux[i] = q -> valores [(q->inicio + i)%q->size];
		}
		q->size *= 2;
		q->inicio = 0;
		free (q->valores);
		q->valores = rew;
	}
	else {q->valores[(q->tamanho + q->inicio)%MAX] = x;
		  (q->tamanho)++;}
}