#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>

#include "avl_base.h"
#include "avl_facturacao.h"
#include "avl_filial.h"
#include "leitura.h"
#include "teste.h"

extern foilido;
extern Leitura leiCli;
extern Leitura leiPro;
extern Leitura leiVen;
extern BASICAVL catClientes[26];
extern BASICAVL catProdutos[26];
extern AVLFACTURACAO facturacao[26];
extern AVLFILIAL filial[26];

/*----------------------------------------------------------------------------------------------------*/
struct ler{
	char* nome;
	int linhas;
	int lval;
    int lmal;
    double tempo;
};

char* getNome(Leitura l){
	return l->nome;
}
int getLinhas(Leitura l){
    return l->linhas;
}
int getCorretas(Leitura l){
    return l->lval;
}
int getIncorretas(Leitura l){
    return l->lmal;
}
double getTempo(Leitura l){
	return l->tempo;
}
void free_Lei(Leitura l){
	if (l==NULL) return;
	free(l->nome);
	free(l);
	return;
}
Leitura initLer(){
    Leitura aux = (Leitura)malloc(sizeof(struct ler));
    aux->nome=NULL;
    aux->linhas=0;
	aux->lval=0;
    aux->lmal=0;
    aux->tempo=0;
    return aux;
}

/*----------------------------------------------------------------------------------------------------*/
void lerFpers(){
	int x,y,z;
	char fCli[1999];
	char fPro[1999];
	char fVen[1999];
	if(foilido == 1){
		free_Lei(leiCli);
		free_Lei(leiPro);
		free_Lei(leiVen);
	}
	leiCli=initLer();
	leiPro=initLer();
	leiVen=initLer();
	printf("Insira o nome do Ficheiro dos Produtos (não se esqueça da terminação neste caso .txt): \n");
	x=scanf(" %s", fPro);
	FILE* teste1 = fopen(fPro,"r");
	if (teste1 == NULL){
		printf("\033c");
		printf("ERRO! Experimente outra vez.\n");
		menuInicial();
	}
	fclose(teste1);
	printf("Insira o nome do Ficheiro dos Clientes (não se esqueça da terminação neste caso .txt): \n");
	y=scanf(" %s", fCli);
	FILE* teste2 = fopen(fCli,"r");
	if (teste2 == NULL){
		printf("\033c");
		printf("ERRO! Experimente outra vez.\n");
		menuInicial();
	}
	fclose(teste2);
	printf("Insira o nome do Ficheiro das Vendas (não se esqueça da terminação neste caso .txt): \n");
	z=scanf(" %s", fVen);
	FILE* teste3 = fopen(fVen,"r");
	if (teste3 == NULL){
		printf("\033c");
		printf("ERRO! Experimente outra vez.\n");
		menuInicial();
	}
	fclose(teste3);
	leiCli->nome = (char*)malloc(strlen(fCli)+1*sizeof(char));
    strcpy(leiCli->nome, fCli);
	leiPro->nome = (char*)malloc(strlen(fPro)+1*sizeof(char));
    strcpy(leiPro->nome, fPro);
    leiVen->nome = (char*)malloc(strlen(fVen)+1*sizeof(char));
    strcpy(leiVen->nome, fVen);
}

void lerFpred(){
	if(foilido == 1){
		free_Lei(leiCli);
		free_Lei(leiPro);
		free_Lei(leiVen);	
	}
	leiCli=initLer();
	leiPro=initLer();
	leiVen=initLer();
	leiCli->nome = (char*)malloc(strlen("Clientes.txt")+1*sizeof(char));
    strcpy(leiCli->nome, "Clientes.txt");
	leiPro->nome = (char*)malloc(strlen("Produtos.txt")+1*sizeof(char));
    strcpy(leiPro->nome, "Produtos.txt");
    leiVen->nome = (char*)malloc(strlen("Vendas_1M.txt")+1*sizeof(char));
    strcpy(leiVen->nome, "Vendas_1M.txt");
}

/*----------------------------------------------------------------------------------------------------*/
void lerClientes(Leitura cli){
	char lin[10];
	char* tok;
	double time_used;
	clock_t start, end;
    start = clock();
	FILE* fc = fopen(cli->nome,"r");
	if (fc==NULL){
		printf("Impossível abrir ficheiro de clientes.\n");
		exit(0);
	}
	while(fgets(lin,10,fc) != NULL){
		tok=strtok(lin,"\r\n");
		if(valida_cliente(tok)){
			catClientes[*tok-'A'] = insere(catClientes[*tok-'A'],tok);
			filial[*tok-'A'] = insereCli(filial[*tok-'A'],NULL,0,0,'N',tok,1,1);
			cli->lval++;
		}
		else cli->lmal++;
		cli->linhas++;
	}
	fclose(fc);
	end = clock();
	time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
	cli->tempo = time_used;
}

void lerProdutos(Leitura prod){
	char lin[10];
	char* tok;
	double time_used;
	clock_t start, end;
    start = clock();
	FILE* fp = fopen(prod->nome,"r");
	if (fp==NULL){
		printf("Impossível abrir ficheiro de produtos.\n");
		exit(0);
	}
	while(fgets(lin,10,fp)!= NULL){
		tok=strtok(lin,"\r\n");
		if(valida_produto(tok)){
			catProdutos[*tok -'A'] = insere(catProdutos[*tok-'A'],tok);
			facturacao[*tok -'A'] = insereFact(facturacao[*tok -'A'],tok,0,0,'N',1,1);
			prod->lval++;
		}
		else prod->lmal++;
		prod->linhas++;
	}
	fclose(fp);
	end = clock();
	time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
	prod->tempo = time_used;
}

void lerVendas(Leitura vend){
	int aux=0;
	char *tok;
	char s[64];
	char produto[64];
	double preco=0;
	int unidades=0;
	char tipo='G';
	char cliente[64];
	int mes=0;
	int fil=0;
	clock_t start, end;
	double time_used;
    start = clock();
	FILE* fv = fopen(vend->nome,"r");
	if (fv==NULL){
		printf("Impossível abrir ficheiro de vendas.\n");
		exit(0);
	}
	while(fgets(s,64,fv)!= NULL){
		s[(strlen(s))-1] = '\0';
		tok=strtok(s," ");
		strcpy(produto,tok);
		aux=1;
		while(tok!=NULL){
			tok=strtok(NULL," ");
			if(tok != NULL){
				switch(aux){
					case 1:{preco=atof(tok);aux++;}break;
					case 2:{unidades=atoi(tok);aux++;}break;
					case 3:{tipo=tok[0];aux++;}break;
					case 4:{strcpy(cliente,tok);aux++;}break;
					case 5:{mes=atoi(tok);aux++;}break;
					case 6:{fil=atoi(tok);aux++;}break;
				}
			}
		}
		if(testeVenda(produto,preco,unidades,tipo,cliente,mes,fil) && existe(catProdutos[*produto-'A'],produto) && existe(catClientes[*cliente-'A'],cliente)){
			filial[*cliente-'A'] = insereCli(filial[*cliente-'A'],produto,preco,unidades,tipo,cliente,mes,fil);
			facturacao[*produto-'A'] = insereFact(facturacao[*produto-'A'],produto,preco,unidades,tipo,mes,fil);
			vend->lval++;
		}
		else vend->lmal++;
		vend->linhas++;
	}
	fclose(fv);
	end = clock();
	time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
	vend->tempo = time_used;
}