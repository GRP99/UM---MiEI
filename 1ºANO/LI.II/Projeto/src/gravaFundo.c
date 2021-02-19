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

void debug(char *teste){
    char nomeFicheiro[100];
    sprintf(nomeFicheiro,"%sdebug.txt",PATH_ESTADOS);
    FILE *fp = fopen(nomeFicheiro,"a");
    fprintf(fp,"%s\n",teste);
    fclose(fp);
}

/**
Funcao que le o estado gravado no ficheiro
@param filename Nome do ficheiro
@returns e Novo Estado
*/
ESTADO ler_estado_ficheiro(char *filename){
    char est[MAX_BUFFER];   
    FILE *fp=fopen(filename,"r");
    if(fscanf(fp,"%s",est)!=1)perror("função ler_estado_ficheiro: erro ao ler o estado;");
    fclose(fp);
    return str2estado(est);
}

/**
Funcao que grava o estado atual do ficheiro
@param e Estado atual do tabuleiro
@param user Nome do jogador
*/
void gravar_estado_ficheiro(ESTADO e, char *user){
    char *estado;
    char filename[100];
    sprintf(filename,"%s%s_estado.txt",PATH_ESTADOS,user);
    FILE *fp = fopen(filename,"w");
    estado = estado2str(e);
    fprintf(fp,"%s",estado);
    fclose(fp);
}

/**
Funcao para o auxilio da acao ancora que le o estado do ficheiro
@param e Estado
@returns e Estado atualiza ou seja foi lido o estado anterior a acao "ancora"
*/
ESTADO ler_ancora_ficheiro(ESTADO e){
    char est[MAX_BUFFER],filename[100];
    sprintf(filename,"%sancora.txt",PATH_ESTADOS);
    FILE *fp=fopen(filename,"r");
    if(fscanf(fp,"%s",est)!=1)perror("função ler_ancora_ficheiro: erro ao ler o estado;");
    if (est[0] == '\0'){return e;}
    fclose(fp);
    return str2estado(est);
}

/**
Funcao para o auxilio da acao ancora que grava o estado num ficheiro 
@param e Estado atual a ser gravado
*/
void gravar_ancora_ficheiro(ESTADO e){
    char *estado;
    char filename[100];
    sprintf(filename,"%sancora.txt",PATH_ESTADOS);
    FILE *fp = fopen(filename,"w");
    estado = estado2str(e);
    fprintf(fp,"%s",estado);
    fclose(fp);
}

/**
Funcao que grava o Undo num ficheiro
@param e Estado atual do jogo
*/
void gravar_undo_ficheiro(ESTADO e){
    char nomeFicheiro[100];
    sprintf(nomeFicheiro,"%sundo.txt",PATH_ESTADOS);
    FILE *fp = fopen(nomeFicheiro,"a");
    fprintf(fp,"%s\n",estado2str(e));
    fclose(fp);
}