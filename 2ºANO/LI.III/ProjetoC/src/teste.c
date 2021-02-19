#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#include "teste.h"


int valida_filial(int filial){
	if((filial >= 1) && (filial <= 3)) return 1;
	return 0;
}

int valida_mes(int mes){
	if((mes >= 1) && (mes<=12)) return 1;
	return 0;
}

int valida_cliente(char* cliente){
	int j=0;
	if (strlen(cliente)>5) return 0;
	if(!(isalpha(cliente[0])))return 0;
	if (!(isdigit(cliente[1])) && (cliente[1]>=49) && (cliente[1]<=53)) return 0;
	for(j=2; j<5; j++){
		if (!(isdigit(cliente[j])) && (cliente[j]>=48) && (cliente[j]<=57)) return 0;
	}
	return 1;
}

int valida_tipo(char tipo){
	if ((isalpha(tipo)) && ((tipo == 78) || (tipo == 80))) return 1;
	else return 0;
}

int valida_unidades(int unidades){
	if ((unidades>= 0) && (unidades<=200)) return 1;
	else return 0;
}

int valida_preco(double preco){
	if ((preco >= 0.0) && (preco <= 999.99)) return 1;
	else return 0;
}

int valida_produto(char* produto){
	int i=0;
	if (strlen(produto)>6) return 0;
	for (i=0; i<=1; i++){
		if(!(isalpha(produto[i]))) return 0;
	}
	if ((!(isdigit(produto[2]))) && (produto[2])>=49 && (produto[2])<=57) return 0;
	for(i=3; i<6; i++){
		if ((!(isdigit(produto[i]))) && (produto[i]>=48) && (produto[i]<=57)) return 0;
	}
	return 1;
}

int testeVenda(char* produto, double preco, int unidades, char tipo, char* cliente, int mes, int filial){
	int logic = 0;
	if (valida_produto(produto) && valida_preco(preco) && valida_unidades(unidades) && valida_tipo(tipo) && valida_cliente(cliente) && valida_mes(mes) && valida_filial(filial)) logic = 1;
	return logic;
}