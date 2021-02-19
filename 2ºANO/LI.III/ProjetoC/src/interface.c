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

extern BASICAVL catClientes[26];
extern BASICAVL catProdutos[26];
extern AVLFACTURACAO facturacao[26];
extern AVLFILIAL filial[26];
Leitura leiCli;
Leitura leiPro;
Leitura leiVen;
int foilido = 0;
int totCli = 0;
int totPro = 0;
int totVen = 0;

/*----------------------------------------------------------------------------------------------------*/
void printOpcoesQueries(){
	printf("######################################## S.G.V ##########################################################################################\n");
	printf("#                                                                                                                                       #\n");
	printf("# 1.  Obter detalhes sobre a leitura dos ficheiros.                                                                                     #\n");
	printf("# 2.  Obter a lista de produtos iniciados por uma letra e o número total de produtos dessa dada letra.                                  #\n");
	printf("# 3.  Determinar o total de compras e o total faturado num mês de um determinado produto distinguido por N e P.                        #\n");
	printf("# 4.  Obter a lista dos códigos dos produtos que ninguém comprou.                                                                       #\n");
	printf("# 5.  Obter a lista de códigos de clientes que realizaram compras em todas as filiais.                                                  #\n");
	printf("# 6.  Determinar o número de clientes registados que não realizaram compras e o número de produtos registados que ninguém comprou.      #\n");
	printf("# 7.  Obter tabela com o número total de produtos comprados de um cliente, separados por mês.                                           #\n");
	printf("# 8.  Determinar o total de vendas registadas e o total faturado num intervalo.                                                        #\n");
	printf("# 9.  Obter os clientes que compraram um produto distinguido entre normal e promocao.                                                   #\n");
	printf("# 10. Determinar a lista de códigos de produtos que um dado cliente comprou num mês.                                                    #\n");
	printf("# 11. Determinar a lista dos N produtos mais vendidos em todo o ano.                                                                    #\n");
	printf("# 12. Obter quais os códigos dos 3 produtos que um dado cliente mais gastou durante o ano.                                              #\n");
	printf("# 0.  VOLTAR.                                                                                                                           #\n");
	printf("#                                                                                                                                       #\n");
	printf("#########################################################################################################################################\n");
	printf("# >>> ESCOLHA UMA OPÇÃO:                                                                                                                #\n");
}

void menuInicial(){
	int r = 0;
	int n = 0;
	char c = '0';
	printf("\n\n\n");
	printf("######################################## S.G.V ########################################\n");
	printf("#                                                                                     #\n");
	printf("#   1. Inicializar estrututuras com dados predefinidos.                               #\n");
	printf("#   2. Inicializar estrututuras com dados personalizados.                             #\n");
	printf("#   0. VOlTAR.                                                                        #\n");
	printf("#                                                                                     #\n");
	printf("#######################################################################################\n");
	printf("# >>> ESCOLHA UMA OPÇÃO:                                                              #\n");
	n = scanf("%d",&r);
	c = getchar();
	if (n > 0){
		switch(r){
			case 0:
				r = 0;
				printf("\033c");
				menu();
				break;
			case 1:
				printf("\033c");
				lerFpred();
				querie0Cli();
				querie0Pro();
				querie0Ven();
				foilido = 1;
				menuQueries();
				break;
			case 2:
				printf("\033c");
				lerFpers();
				querie0Cli();
				querie0Pro();
				querie0Ven();
				foilido = 1;
				menuQueries();
				break;
			
			default:
				printf("\033c");
				printf("Opção Inválida.\n");
				menuInicial();
				break;
		}
	}
	else{
		printf("\033c");
		printf("Opção Inválida.\n");
		menuInicial();
	}
}

void menuQueries(){
	int r = 0;
	int n = 0;
	char c = '0';
	printf("\n\n\n");
	printOpcoesQueries();
	n = scanf("%d",&r);
	c = getchar();
	if (n>0){
		switch(r){
			case 0:
				printf("\033c");
				menu();
				break;
			case 1:
				printf("\033c");
				querie1Cli();
				querie1Pro();
				querie1Ven();
				menuQueries();
				break;
			case 2:
				printf("\033c");
				querie2();
				menuQueries();
				break;
			case 3:
				printf("\033c");
				querie3();
				menuQueries();
				break;
			case 4:
				printf("\033c");
				querie4();
				menuQueries();
				break;
			case 5:
				printf("\033c");
				querie5();
				menuQueries();
				break;
			case 6:
				printf("\033c");
				querie6_0();
				querie6_1();
				menuQueries();
				break;
			case 7:
				printf("\033c");
				querie7();
				menuQueries();
				break;
			case 8:
				printf("\033c");
				querie8();
				menuQueries();
				break;
			case 9:
				printf("\033c");
				querie9();
				menuQueries();
				break;
			case 10:
				printf("\033c");
				querie10();
				menuQueries();
				break;
			case 11:
				printf("\033c");
				querie11();
				menuQueries();
				break;
			case 12:
				printf("\033c");
				querie12();
				menuQueries();
				break;
		}
	}

	else{
		printf("%c\n", c);
		printf("\033c");
		printf("Opção Inválida.\n");
		menuQueries();
	}
}

int menu(){
	int r = 0;
	int n = 0;
	char c = '0';
	printf("\n\n\n");
	printf("####################################################################################################\n");
	printf("#            SEJA BEM-VINDO AO SISTEMA DE GESTÃO DE VENDAS DE LI3 18/19 PELO GRUPO 37              #\n");
	printf("#                                                                                                  #\n");
	printf("#  1 - Inicializar estrututuras (Clientes,Produtos e Vendas);                                      #\n");
	printf("#  2 - Ver Queries;                                                                                #\n");
	printf("#  0 - SAIR;                                                                                       #\n");
	printf("#                                                                                                  #\n");
	printf("####################################################################################################\n");
	printf("# >>> Escolha uma opção:                                                                           #\n");
	n = scanf("%d",&r);
	c = getchar();
	if (n > 0){
		switch(r){
			case 0:
				printf("\n");
				printf("####################################################################################################\n");
				printf("#              Programa terminado com sucesso. Foi um prazer. Volte Sempre!!                       #\n");
				printf("####################################################################################################\n");
				return r;
				break;
			case 1:
				printf("\033c");
				menuInicial();
				break;
			case 2:
				if(foilido == 0){
					printf("\033c");
					printf("Tem que inicializar as estruturas!\n");
					menuInicial();
				}
				else{
					printf("\033c");
					menuQueries();
				}
				break;
			default:
				printf("\033c");
				printf("Opção Inválida.\n");
				menu();
		}
	}
	else{
		printf("\033c");
		printf("Opção Inválida.\n");
		menu();
	}
	return 0;
}