#ifndef ___VALIDA_H___
#define ___VALIDA_H___
/**
@file valida.h
Funcoes para validar um tabuleiro
*/

int validalinha (ESTADO e);

int validacoluna (ESTADO e);

int vd1Aux (ESTADO e,int l,int c);

int validadiagonal1(ESTADO e);

int vd2Aux (ESTADO e, int l, int c);

int validadiagonal2(ESTADO e);

#endif