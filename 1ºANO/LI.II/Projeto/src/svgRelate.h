#ifndef ___SVGRELATE_H___
#define ___SVGRELATE_H___
/**
@file svgRelate.h
Funcoes para imprimir a Grelha e os Botoes no SVG
*/

void imprimeCasa (ESTADO e, int l, int c);

void imprimeCasa_link(ESTADO e, int l , int c);

void imprimeBotoes(ESTADO e,char *user);

void imprimeGrelha(ESTADO e);

#endif