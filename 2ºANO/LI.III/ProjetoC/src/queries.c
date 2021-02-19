#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#include "avl_base.h"
#include "avl_facturacao.h"
#include "avl_filial.h"
#include "interface.h"
#include "leitura.h"
#include "navega.h"
#include "queries.h"
#include "teste.h"

extern totVen;
extern totPro;
extern totCli;
extern foilido;
extern Leitura leiVen;
extern Leitura leiPro;
extern Leitura leiCli;
extern BASICAVL catClientes[26];
extern BASICAVL catProdutos[26];
extern AVLFACTURACAO facturacao[26];
extern AVLFILIAL filial[26];

/*----------------------------------------------------------------------------------------------------*/
struct cp {
    char** lista;
    int* unidades;
    int* clientes;
    int tamanho;
};
ConjProds initCP(int N){
    ConjProds tmp = (ConjProds)malloc(sizeof(struct cp));
    tmp->lista=NULL;
    tmp->unidades=malloc(sizeof(int)*N);
    tmp->clientes=malloc(sizeof(int)*N);
    tmp->tamanho=0;
    return tmp;
}
char* getLista(ConjProds cp, int i){
    return cp->lista[i];
}
int getUnidades(ConjProds cp, int i){
    return cp->unidades[i];
}
int getClientes(ConjProds cp, int i){
    return cp->clientes[i];
}
int getTamanho(ConjProds cp){
    return cp->tamanho;
}
/*----------------------------------------------------------------------------------------------------*/
void querie0Cli(){
	int x;
	if (foilido == 1){
		for(x=0; x<26; x++){
			free_BAVL(catClientes[x]);
			free_FIAVL(filial[x]);
		}
		for(x=0; x<26; x++){
			init_BAVL(catClientes[x]);
			init_FIAVL(filial[x]);
		}
	}
	lerClientes(leiCli);
	totCli = getCorretas(leiCli);
	printf("Foi criado um catálogo de %d clientes.\n",totCli);
}
void querie0Pro(){
	int x;
	if (foilido == 1){
		for(x=0; x<26; x++){
			free_BAVL(catProdutos[x]);
			free_FAAVL(facturacao[x]);
		}
	}
	for(x=0; x<26; x++){
			init_BAVL(catProdutos[x]);
			init_FAAVL(facturacao[x]);
	}
	lerProdutos(leiPro);
	totPro = getCorretas(leiPro);
	printf("Foi criado um catálogo de %d produtos.\n",totPro);
}
void querie0Ven(){
	lerVendas(leiVen);
	totVen = getCorretas(leiVen);
	printf("Foram registadas %d vendas corretas.\n",totVen);
}

void querie1Cli(){
	printf("Ficheiro lido para Clientes: %s --- Linhas Totais Lidas: %d ; Linhas Válidas: %d ; Linhas Incorretas: %d ; \n",getNome(leiCli),getLinhas(leiCli),getCorretas(leiCli),getIncorretas(leiCli));
	printf("Tempo demorado na leitura %f segundos\n",getTempo(leiCli));
}
void querie1Pro(){
	printf("Ficheiro lido para Produtos: %s --- Linhas Totais Lidas: %d ; Linhas Válidas: %d ; Linhas Incorretas: %d ; \n",getNome(leiPro),getLinhas(leiPro),getCorretas(leiPro),getIncorretas(leiPro));
	printf("Tempo demorado na leitura %f segundos\n",getTempo(leiPro));
}
void querie1Ven(){
	printf("Ficheiro lido para Vendas: %s --- Linhas Totais Lidas: %d ; Linhas Válidas: %d ; Linhas Incorretas: %d ; \n",getNome(leiVen),getLinhas(leiVen),getCorretas(leiVen),getIncorretas(leiVen));
	printf("Tempo demorado na leitura %f segundos \n",getTempo(leiVen));
}

void querie2(){
	char letra,aux;
	int tam = 0;
	char** array = NULL;
	printf("Insira uma letra: \n");
	aux = scanf(" %c",&letra);
	letra = toupper(letra);
	if(isalpha(letra)){
    	array = giveMe(catProdutos[letra-'A'],totVen,&tam);
    	navegador(array,tam,1);
    }
    else{
    	printf("\033c");
		printf("Letra incorreta.\n");
		querie2();
	}
    free(array);
}

void querie3(){
 	int x,y,z,mes,op;
 	char idP[7];
 	double res1[4];
	double res2[12];
 	printf("Escreva o mês (1-12):\n");
 	x=scanf(" %d", &mes);
 	printf("Escreva o código do produto(EX:GP1999):\n");
 	y=scanf(" %s", idP);
 	printf("Insira 1 para ver os valores totais;\n");
 	printf("Insira 2 para ver Filial a filial;\n");
 	z=scanf(" %d", &op);
 	if (existeFact(facturacao[idP[0]-'A'],idP) && valida_mes(mes) && (op==1 || op==2)){
 		if (op == 1){
 			conta_Fact1(facturacao[idP[0]-'A'],idP,(mes-1),res1);
 			printf("Total de compras normais do produto %s no mês %d -> %f\n", idP, mes, res1[0]);
			printf("Total de compras em promoção do produto %s no mês %d -> %f\n",idP, mes, res1[1]);
			printf("Faturação normal no mês %d com a venda do produto %s -> %f\n", mes, idP, res1[2]);
			printf("Faturação em promoção no mês %d com a venda do produto %s -> %f\n", mes, idP, res1[3]);
 		}
 		else{
 			conta_Fact2(facturacao[idP[0]-'A'],idP,(mes-1),res2);
 			printf("----------------------------FILIAL1------------------------------------------------\n");
 			printf("Total de compras normais do produto %s no mês %d -> %f\n", idP, mes, res2[0]);
			printf("Total de compras em promoção do produto %s no mês %d -> %f\n",idP, mes, res2[2]);
			printf("Faturação normal no mês %d com a venda do produto %s -> %f\n", mes, idP, res2[1]);
			printf("Faturação em promoção no mês %d com a venda do produto %s -> %f\n", mes, idP, res2[3]);
			printf("----------------------------FILIAL2------------------------------------------------\n");
 			printf("Total de compras normais do produto %s no mês %d -> %f\n", idP, mes, res2[4]);
			printf("Total de compras em promoção do produto %s no mês %d -> %f\n",idP, mes, res2[6]);
			printf("Faturação normal no mês %d com a venda do produto %s -> %f\n", mes, idP, res2[5]);
			printf("Faturação em promoção no mês %d com a venda do produto %s -> %f\n", mes, idP, res2[7]);
			printf("----------------------------FILIAL3------------------------------------------------\n");
 			printf("Total de compras normais do produto %s no mês %d -> %f\n", idP, mes, res2[8]);
			printf("Total de compras em promoção do produto %s no mês %d -> %f\n",idP, mes, res2[10]);
			printf("Faturação normal no mês %d com a venda do produto %s -> %f\n", mes, idP, res2[9]);
			printf("Faturação em promoção no mês %d com a venda do produto %s -> %f\n", mes, idP, res2[11]);

 		}
 	}
	else{
		printf("\033c");
		printf("Erro. Tente novamente.\n");
		querie3();
	}
}

void querie4(){
	int tot=0;
	char** array = NULL;
	array = criaArrayF(facturacao,totPro,&tot);
	navegador(array,tot,1);
	free(array);
}

void querie5(){
	int p=0;
	char** g = NULL;
	g = criaArrayFi(filial,totCli,&p);
	navegador(g,p,1);
	free(g);
}

void querie6_0(){
	int s=0,i;
	for (i=0; i<26; i++){ 
		s += ngmCompra(filial[i]);
	}
	printf("Houve %d clientes que não realizaram nenhuma compra.\n",s);
}

void querie6_1(){
	int s=0,i;
	char** aux=NULL;
	aux = criaArrayF(facturacao,totPro,&s);
	printf("Houve %d produtos não comprados.\n",s);
	free(aux);
}

int qDig(int n){
	int i=0;
	if (n==0) return 1;
	while(n>0){
		n/=10;
		i++;
	}
	return i;
}
void querie7(){
	int x=0;
	int i1=0,i2=0,i3=0;
	int totCF1[12];
	int totCF2[12];
	int totCF3[12];
	char cli[6];
	printf("Escreva o código do cliente por favor (EX:G1999): \n");
 	x = scanf(" %s", cli);
 	getCompras(filial[cli[0]-'A'],cli,totCF1,totCF2,totCF3);
 	if(totCF1[0]<0 && totCF2[0]<0 && totCF3[0]<0){
 		printf("Cliente não existe. Tente novamente.\n");
 		menuQueries();
 	}
 	else{
 		printf(" _______________________________________________________ \n");
 		printf(" |                                                     | \n");
 		printf(" |                     FILAL 1                         | \n");
 		printf(" |_____________________________________________________| \n");
 		printf(" |          MÊS           |          TOTAL             | \n");
 		printf(" |________________________|____________________________| \n");
 		for(i1=0; i1<12; i1++){
 		if(qDig(i1)==1){
 			if(qDig(totCF1[i1])==1)printf(" |          %d             |          %d                 | \n", i1, totCF1[i1]);
 			if(qDig(totCF1[i1])==2)printf(" |          %d             |          %d                | \n", i1, totCF1[i1]);
 			if(qDig(totCF1[i1])==3)printf(" |          %d             |          %d               | \n", i1, totCF1[i1]);}
 		if(qDig(i1)==2){
 			if(qDig(totCF1[i1])==1)printf(" |          %d            |          %d                 | \n", i1, totCF1[i1]);
 			if(qDig(totCF1[i1])==2)printf(" |          %d            |          %d                | \n", i1, totCF1[i1]);
 			if(qDig(totCF1[i1])==3)printf(" |          %d            |          %d               | \n", i1, totCF1[i1]);
 		}
 		printf(" |________________________|____________________________| \n");}
 		printf(" _______________________________________________________ \n");
 		printf(" |                                                     | \n");
 		printf(" |                     FILAL 2                         | \n");
 		printf(" |_____________________________________________________| \n");
 		printf(" |          MÊS           |          TOTAL             | \n");
 		printf(" |________________________|____________________________| \n");
 		for(i2=0; i2<12; i2++){
 		if(qDig(i2)==1){
 			if(qDig(totCF2[i2])==1)printf(" |          %d             |          %d                 | \n", i2, totCF2[i2]);
 			if(qDig(totCF2[i2])==2)printf(" |          %d             |          %d                | \n", i2, totCF2[i2]);
 			if(qDig(totCF2[i2])==3)printf(" |          %d             |          %d               | \n", i2, totCF2[i2]);}
 		if(qDig(i2)==2){
 			if(qDig(totCF2[i2])==1)printf(" |          %d            |          %d                 | \n", i2, totCF2[i2]);
 			if(qDig(totCF2[i2])==2)printf(" |          %d            |          %d                | \n", i2, totCF2[i2]);
 			if(qDig(totCF2[i2])==3)printf(" |          %d            |          %d               | \n", i2, totCF2[i2]);
 		}
 		printf(" |________________________|____________________________| \n");}
 		printf(" _______________________________________________________ \n");
 		printf(" |                                                     | \n");
 		printf(" |                     FILAL 3                         | \n");
 		printf(" |_____________________________________________________| \n");
 		printf(" |          MÊS           |          TOTAL             | \n");
 		printf(" |________________________|____________________________| \n");
 		for(i3=0; i3<12; i3++){
 		if(qDig(i3)==1){
 			if(qDig(totCF3[i3])==1)printf(" |          %d             |          %d                 | \n", i3, totCF3[i3]);
 			if(qDig(totCF3[i3])==2)printf(" |          %d             |          %d                | \n", i3, totCF3[i3]);
 			if(qDig(totCF3[i3])==3)printf(" |          %d             |          %d               | \n", i3, totCF3[i3]);}
 		if(qDig(i3)==2){
 			if(qDig(totCF3[i3])==1)printf(" |          %d            |          %d                 | \n", i3, totCF3[i3]);
 			if(qDig(totCF3[i3])==2)printf(" |          %d            |          %d                | \n", i3, totCF3[i3]);
 			if(qDig(totCF3[i3])==3)printf(" |          %d            |          %d               | \n", i3, totCF3[i3]);}
 		printf(" |________________________|____________________________| \n");}
	}
}

void querie8(){
	int i;
	int ini,fim;
	int aux1,aux2;
	int totV=0;
	double totF=0;
	printf("Escreva o mês inicial:\n");
	aux1=scanf(" %d", &ini);
	printf("Escreva o mês final:\n");
	aux2=scanf(" %d", &fim);
	if(valida_mes(ini) && valida_mes(fim) && ini<=fim){
		for(i=0; i<26; i++){
		totV += comprasEntre(facturacao[i],ini,fim);
		totF += facturacaoEntre(facturacao[i],ini,fim);
		}
		printf("Total de vendas entre o mês %d e o mês %d -> %d\n", ini, fim, totV);
		printf("Total faturado entre o mês %d e o mês %d -> %f\n", ini, fim, totF);	
	}
	else{
		printf("\033c");
		printf("Meses Inválidos.\n");
		querie8();
	}
	
}

void querie9(){
	int aux1,aux2;
	int tam1=0,tam2=0;
	int f;
	char p[7];
	char** array_cli_fi_nor=NULL;
	char** array_cli_fi_pro=NULL;
	printf("Escreva o código do produto (EX:GP1999):\n");
 	aux1=scanf(" %s", p);
 	if(existe(catProdutos[p[0]-'A'],p)){
 		printf("Escreva a filial pretendida (1-3): \n");
		aux2=scanf(" %d", &f);
		if(valida_filial(f)){
			array_cli_fi_nor = inicArrayCliN(filial,totPro,&tam1,p,f);
			array_cli_fi_pro = inicArrayCliP(filial,totPro,&tam2,p,f);
			printf("Clientes que compraram o produto %s no tipo normal na filial %d.\n", p,f);
			navegador(array_cli_fi_nor,tam1,0);
			printf("Clientes que compraram o produto %s no tipo promoção na filial %d.\n", p,f);
			navegador(array_cli_fi_pro,tam2,0);
		}
		else{
			printf("\033c");
			printf("Filial Inválida.\n");
			querie9();
		}
 	}
 	else{
 		printf("\033c");
		printf("Cliente não existe.\n");
		querie9();
 	}
}


void querie10(){
	int aux,mes;
	char c[5];
	char** listaProdutos=NULL;
 	int* listaTotal=NULL;
	printf("Insira um código de um cliente (EX:G1999): \n");
	aux = scanf("%s",c);
	if(existe(catClientes[c[0]-'A'],c)){
		printf("Insira um mês: \n");
		aux = scanf("%d",&mes);
		if(valida_mes(mes)){
			listaProdutos = inspector(filial[c[0]-'A'],c,mes,listaTotal);
			listaTotal = encontra(filial[c[0]-'A'],c,mes,listaProdutos);
			aux=0;
			if(listaProdutos!=NULL){
				printf("Os produtos mais comprados pelo cliente %s no mês %d são:\n",c,mes);
				printf("---------------------------------------------------------------------\n");
				while(listaProdutos[aux]!=NULL){
					printf("%s -> %d\n",listaProdutos[aux],listaTotal[aux]);
					aux++;
				}
			}
		}
		else{
			printf("\033c");
			printf("Mês Incorrecto\n");
			querie10();
		}
	}				      
    else{
		printf("\033c");
		printf("Cliente não existe.\n");
		querie10();
	}
	free(listaProdutos);
	free(listaTotal);
}

void printQ11(ConjProds f1, ConjProds f2, ConjProds f3, int n, int con, int ind){
	int j,i,c=0;
	int conI = con;
	int indI = ind;
	printf("############################## %d produtos mais comprados por filial  ########################################\n", n);
	printf("##############################################################################################################\n");
	printf("#            FILIAL 1               |             FILIAL 2               |             FILIAL 3              #\n");
	printf("# Código  |  Unidades  |  Clientes  |  Código  |  Unidades  |  Clientes  |  Código  |  Unidades  |  Clientes #\n");
	printf("##############################################################################################################\n");
	for(i=0;i<13 && ind < n; i++){
		printf("#   %s    |    %d   | %d  |    %s    |   %d    | %d  |    %s    |   %d    | %d #\n",getLista(f1,ind),getUnidades(f1,ind),getClientes(f1,ind),getLista(f2,ind),getUnidades(f2,ind),getClientes(f2,ind),getLista(f3,ind),getUnidades(f3,ind),getClientes(f3,ind));
		con++;
		ind++;
	}
	printf("##############################################################################################################\n");
	printf("# 1. Continuar.                                                                                              #\n");
	printf("# 2. Retroceder.                                                                                             #\n");
	printf("# 0. Sair.                                                                                                   #\n");
	printf("##############################################################################################################\n");
	printf(">>> ");
	j = scanf("%d",&c);
	if(j > 0){
		switch(c){
			case 1:
				if(con != n){
					printf("\033c");
					printQ11(f1,f2,f3,n,con,ind++);
				}
				if(con == n){
					printf("\033c");
					printf("Impossível continuar.\n");
					printQ11(f1,f2,f3,n,conI,indI);
				}
				break;
			case 2:
				if((con-26)>=0){
					printf("\033c");
					printQ11(f1,f2,f3,n,con-26,ind-26);
				}
				con=0;
				indI=0;
				if((con-26)<=0){
					printf("\033c");
					printf("Impossível retroceder.\n");
					printQ11(f1,f2,f3,n,con,indI);
				}
				break;
			case 0:
				printf("\033c");
				break;
			default:	
				printf("\033c");
				printf("Comando Inválido.\n");
				printQ11(f1,f2,f3,n,conI,indI);
				break;
		}
	}
}

void querie11(){
	int s,n=0;
	int x=0;
	int i=0;
	int y=0;
	ConjProds p1 = initCP(n);
	ConjProds p2 = initCP(n);
	ConjProds p3 = initCP(n);
	printf("Insira um número: \n");
	s = scanf("%d",&n);
	if(n<totPro){
		int uvF1[n];
		int uvF2[n];
		int uvF3[n];
		int ccF1[n];
		int ccF2[n];
		int ccF3[n];
		int ind1=0;
		int ind2=0;
		int ind3=0;
		char** aux1;
		char** aux2;
		char** aux3;
		aux1 = malloc(sizeof(char*)*n);
		aux2 = malloc(sizeof(char*)*n);
		aux3 = malloc(sizeof(char*)*n);
		for(x=0;x<n;x++){
			uvF1[x]=0;
			uvF2[x]=0;
			uvF3[x]=0;
			ccF1[x]=0;
			ccF2[x]=0;
			ccF3[x]=0;
			aux1[x]="";
			aux2[x]="";
			aux3[x]="";
		}
		for(x=0;x<26;x++){
			ind1=percorreProd1(facturacao[x],aux1,uvF1,ind1,n);
			ind2=percorreProd2(facturacao[x],aux2,uvF2,ind2,n);
			ind3=percorreProd3(facturacao[x],aux3,uvF3,ind3,n);
		}		
		for(y=0;y<n;y++){
        	ind1 = 0;
        	ind2 = 0;
       		ind3 = 0;
        for(i=0; i<26; i++){
            ind1 = percorreCli(filial[i],aux1[y],ind1,1);
            ind2 = percorreCli(filial[i],aux2[y],ind2,2);
            ind3 = percorreCli(filial[i],aux3[y],ind3,3);
        }
        ccF1[y] = ind1;
        ccF2[y] = ind2;
        ccF3[y] = ind3;
    }
		p1->clientes = ccF1;
   		p1->lista = aux1;
    	p1->unidades = uvF1; 
    	p2->lista = aux2;
    	p2->unidades = uvF2;
    	p2->clientes = ccF2;
   		p3->lista = aux3;
    	p3->unidades = uvF3;
    	p3->clientes = ccF3;
		printQ11(p1,p2,p3,n,0,0);
		free(p1);
		free(p2);
		free(p3);
	}
	else{
		printf("\033c");
		printf("Número Inválido\n");
		querie11();
	}
}

void querie12(){
	int aux,i;
	char c[6];
	char* array_prod[3];
	int array_T_prod[3];
	for (i=0; i<3; i++){
		array_prod[i]=0;
		array_T_prod[i]=0;
	}
	printf("Insira um código do um cliente (EX:G1999): \n");
	aux = scanf("%s",c);
	if(existe(catClientes[c[0]-'A'],c));
		top3C(filial[c[0]-'A'],c,array_prod,array_T_prod);
		i=0;
		if(array_prod[0] != NULL){
			printf ("Produtos mais comprados pelo cliente %s:\n", c);
 			while(i<3){
 				if (array_prod[i] != NULL){
 					printf ("     -> gastou %d no produto %s \n", array_T_prod[i], array_prod[i]);
 					i++;
				}
				else {break;}
			}
		}
	else{
		printf("\033c");
		printf("Cliente Inválido\n");
		querie12();
	}
	for(i=0;i<3;i++){
		free(array_prod[i]);
	}
}