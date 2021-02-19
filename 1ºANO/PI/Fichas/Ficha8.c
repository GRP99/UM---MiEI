#include <stdio.h>
#include <string.h>

int main (int argc, char *argv[]){
	FILE *input;
	int r=0, count=0;
	char word[100];
	if (argc==1) input=stdin;
	else input=fopen(argv[1],"r");
	if (input==NULL) r=1;
	else {
		while ((fscanf(input,"%s",word)==1))
			count ++;
			fclose (input);
			printf ("%d palavras\n", count);
		}
	return r;
}
typedef struct entrada{
	char *palavra;
	int ocorr;
	struct entrada *prox;
} *Palavras;


//1
int main (int argc, char *argv[]){
	FILE *input;
	char word[100];
	int o;
	char *w;
	Dicionario d;
	initDic (&d);
	while ((fscanf(input,"%s",word)==1)){
		acrescenta (&d,word);
		w = maisFreq (d,&o);
	}
	return 0;
}

//2
	//(A)
typedef Palavras Dicionario;
void initDic (Dicionario *d){
	*d = NULL;
}
int acrescenta (Dicionario *d, char *pal){
	Palavras ant = NULL, act = *d;
	while (act != NULL && strcmp(pal, act->palavra)>0){
		ant = act;
		act = act -> prox;
	}
	if (act == NULL){
		Palavras n =  malloc(sizeof(struct entrada));
		n -> prox = NULL;
		n -> ocorr = 1;
		char c = malloc (sizeof (char) * strlen (pal));
		n -> palavra = c;
		strcpy(pal,c);
		if (ant != NULL) ant -> prox = n;
		else *d=n;
	}
	else {
		if (strcmp(pal, act->palavra)==0) act -> ocorr ++;
		else {
			Palavras n =  malloc (sizeof(struct entrada));
			n -> prox = act;
			n -> ocorr = 1;
			char c = malloc(sizeof(char)*strlen (pal));
			n -> palavra = c;
			strcpy(pal,c);
			if (ant != NULL)ant -> prox = n;
			else *d=n;
		}
	}
}
char *maisFreq (Dicionario d, int *c){
	Palavras act = *d;
	int max = 0;
	while (act != NULL){
		if (act -> ocorr > max) {
			max = act -> prox;
			c = &max;
		}
		act = act -> prox;
	}
	return c;
}
	//(B)
#define HSIZE 1000
typedef Palavras Dicionario[HSIZE];
int hash (char *pal, int s){
	int r;
	for (r=0; *pal != '\0'; pal++)
		r += *pal;
	return (r%s);
}
void initDic2 (Dicionario d){
	int i;
	for (i=0; i<HSIZE; i++){
		d[i] = NULL;
	}
}
int acrescenta2 (Dicionario d, char *pal){
	int r = 1;
	int h = hash(pal,HSIZE);
	Palavras l,ant;
	l = d[h];
	while (l !=NULL && strcmp(pal,l->pal)!=0){
		ant = l
		l = l->prox;
	}
	if (l!=NULL) r =++ l->ocorr; 
	else{
		l = (Palavras) malloc (sizeof(struct entrada));
		l->pal = pal;
		l->ocorr = 1;
		l->prox = NULL;
		if (ant != NULL) ant->prox=l;
		else d[h]=l;
	}
	return r;
}
char maisFreq2 (Dicionario d,int *c){
	int i;
	char *pal = NULL;
	Palavras l;
	for (i=0; i<HSIZE; i++){
		for (l=d[i]; l!NULL; l=l->prox){
			if (l->ocorr>*c){
				*c = l->ocorr:
				pal = l->pal;
			}
		}
	}
	return pal;
}