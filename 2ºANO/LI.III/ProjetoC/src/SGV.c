/**
@file SGV.c
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "avl_base.h"
#include "avl_facturacao.h"
#include "avl_filial.h"
#include "interface.h"

BASICAVL catClientes[26]; 
BASICAVL catProdutos[26];
AVLFACTURACAO facturacao[26]; /*Insercao pela ordem alfabetico do codigo de produto*/
AVLFILIAL filial[26]; /*Insercao pela ordem alfabetica do codigo do cliente*/
/*----------------------------------------------------------------------------------------------------*/


int main(){
	int x = 0;
	int r = 1;
	
	printf("\033c");

	while (r != 0){
		r = menu();
	}
	
	/*
	for (x=0; x<26; x++){
		free_BAVL(catClientes[x]);
    	free_BAVL(catProdutos[x]);
    	free_FAAVL(facturacao[x]);
    	free_FIAVL(filial[x]);
	}
	*/
	
	return 0;
}