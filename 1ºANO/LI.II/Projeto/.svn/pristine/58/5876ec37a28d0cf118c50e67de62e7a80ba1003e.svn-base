#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cgi.h" 
#include "estado.h"
#include <unistd.h>
    
#define MAX_BUFFER      10240
#define GRELHA      4
#define TAM     55
#define FALSE 0
#define TRUE  1
#define PATH_TABULEIROS "/var/www/html/tabuleiros/"
#define PATH_ESTADOS "/var/www/html/estados/"
#define PATH_LINK "http://localhost/cgi-bin/GandaGalo65?"

/**
Funcao que verifica se a linha do tabuleiro esta valida
@param e Estado atual
@returns A veracidade do tabuleiro
*/
int validalinha (ESTADO e){
    int l,c,conta=0;
    char p=VAZIA;
    for(l=0;l<e.num_lins;l++){
        for(c=0;c<e.num_cols;c++){
            if((((e.grelha[l][c]==SOL_X || e.grelha[l][c]==FIXO_X) && (p==SOL_X || p==FIXO_X)) || ((e.grelha[l][c]==SOL_O || e.grelha[l][c]==FIXO_O) && (p==SOL_O || p==FIXO_O))) && (e.grelha[l][c]!=VAZIA) && (e.grelha[l][c]!=BLOQUEADA)) conta++;
            else {conta=0; p=e.grelha[l][c];}
            if(conta>=2) return TRUE;
        }
        p=VAZIA;
    }
    return FALSE;
}    

/**
Funcao que verifica se a coluna do tabuleiro esta valida
@param e Estado atual
@returns A veracidade do tabuleiro
*/
int validacoluna (ESTADO e){
    int l,c,conta=0;
    char p=VAZIA;
    for(c=0;c<e.num_cols;c++){
        for(l=0;l<e.num_lins;l++){
            if ((((e.grelha[l][c]==SOL_X || e.grelha[l][c]==FIXO_X) && (p==SOL_X || p==FIXO_X)) || ((e.grelha[l][c]==SOL_O || e.grelha[l][c]==FIXO_O) && (p==SOL_O || p==FIXO_O))) && (e.grelha[l][c]!=VAZIA) && (e.grelha[l][c]!=BLOQUEADA)) conta++;
            else {conta=0; p=e.grelha[l][c];}
            if (conta>=2) return TRUE;
        }
        p=VAZIA;
    }
    return FALSE;
}

/**
Funcao auxiliar que verifica se a diagonal do tabuleiro esta valida
@param e Estado atual
@param l Linha atual
@param c Coluna atual
@returns A veracidade do tabuleiro
*/
int vd1Aux (ESTADO e,int l,int c){
    char p=VAZIA;
    int conta=0;
    while(l<e.num_lins && c<e.num_cols){
        if ((((e.grelha[l][c]==SOL_X || e.grelha[l][c]==FIXO_X) && (p==SOL_X || p==FIXO_X)) || ((e.grelha[l][c]==SOL_O || e.grelha[l][c]==FIXO_O) && (p==SOL_O || p==FIXO_O))) && e.grelha[l][c]!=VAZIA && e.grelha[l][c]!=BLOQUEADA) conta++;
        else {conta=0; p=e.grelha[l][c];}
        l++; c++;
        if (conta>=2) return FALSE;
    }
    return TRUE;
}
/**
Funcao que verifica se a diagonal do tabuleiro esta valida
@param e Estado atual
@returns A veracidade do tabuleiro
*/
int validadiagonal1(ESTADO e){
    int l=0,c=0;
    int conta=vd1Aux(e,0,0);
    while (l<e.num_lins && c<e.num_cols && conta != 0){
        conta = vd1Aux(e,l++,0)*vd1Aux(e,0,c++)*conta;
    }
    if (conta==0) return TRUE;
    else return FALSE;
}

/**
Funcao auxiliar que verifica se a diagonal do tabuleiro esta valida
@param e Estado atual
@param l Linha atual
@param c Coluna atual
@returns A veracidade do tabuleiro
*/
int vd2Aux (ESTADO e, int l, int c){
    char p=VAZIA;
    int conta=0;
    while(l<e.num_lins && c>=0){
        if ((((e.grelha[l][c]==SOL_X || e.grelha[l][c]==FIXO_X) && (p==SOL_X || p==FIXO_X)) || ((e.grelha[l][c]==SOL_O || e.grelha[l][c]==FIXO_O) && (p==SOL_O || p==FIXO_O))) && e.grelha[l][c]!=VAZIA && e.grelha[l][c]!=BLOQUEADA) conta++;
        else {
            conta=0;
            p=e.grelha[l][c];
        }
        l++; c--;
        if (conta>=2) return FALSE;
    }
    return TRUE;
}

/**
Funcao que verifica se a diagonal do tabuleiro esta valida
@param e Estado atual
@returns A veracidade do tabuleiro
*/
int validadiagonal2(ESTADO e){
    int l=0,c=e.num_cols-1;
    int conta;
    conta=vd2Aux(e,0,c);
    while(l<e.num_lins && c>=0 && conta != 0){
        l++; c--;
        conta = vd2Aux(e,0,c)*vd2Aux(e,l,e.num_cols-1)*conta;
    }
    if (conta==0) return TRUE;
    else return FALSE;
}