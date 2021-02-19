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
#define PATH_LINK "http://localhost/cgi-bin/GandaGalo65?"

const char *ficheiro [] = {"bloq.png", "X.png", "O.png", "vazio.png", "X.png", "O.png"};

/**
Funcao para as casas fixas na grelha
@param e Estado do tabuleiro
@param l Linha onde se vai imprimir a casa
@param c Coluna onde se vai imprimir a casa
*/
void imprimeCasa (ESTADO e, int l, int c){
  VALOR v = e.grelha[l][c];
  IMAGEM(c,l,TAM,ficheiro[v]);
}

/**
Funcao que atualiza a casa com os varios links na grelha
@param e Estado do tabuleiro
@param l Linha onde se vai imprimir a casa
@param c Coluna onde se vai imprimir a casa
*/

void imprimeCasa_link(ESTADO e, int l , int c){
  char link[MAX_BUFFER];
  VALOR v = e.grelha[l][c];
  switch (v){
    case VAZIA:
      sprintf(link,"%s%s,MARCA_X,%d,%d,%d,%d",PATH_LINK,e.user,l,c,e.num_tab,e.num_jog+1);
      ABRIR_LINK(link);
      IMAGEM(c,l,TAM,ficheiro[v]);
      FECHAR_LINK;
      break;
    case SOL_X:
      sprintf(link,"%s%s,MARCA_O,%d,%d,%d,%d",PATH_LINK,e.user,l,c,e.num_tab,e.num_jog+1);
      ABRIR_LINK(link);
      IMAGEM(c,l,TAM,ficheiro[v]);
      FECHAR_LINK;
      break;
    case SOL_O:
      sprintf(link,"%s%s,MARCA_VAZIO,%d,%d,%d,%d",PATH_LINK,e.user,l,c,e.num_tab,e.num_jog+1);
      ABRIR_LINK(link);
      IMAGEM(c,l,TAM,ficheiro[v]);
      FECHAR_LINK;
      break;
    default:
      e.grelha[l][c]= v;
  }
}
/**
Funcao que imprime os diferentes botoes
@param e Estado atual
@param user Nome do jogador 
*/
void imprimeBotoes(ESTADO e,char *user){
  char link [MAX_BUFFER];
  sprintf(link,"%s%s,COMECAR,0,0,%d,0",PATH_LINK,user,e.num_tab);
  ABRIR_LINK(link);
    IMAGEM(0, 3, 118, "novo.png");
  FECHAR_LINK;
  if(e.num_jog-1>(-1)){
    sprintf(link,"%s%s,UNDO,0,0,%d,%d",PATH_LINK,user,e.num_tab,e.num_jog-1);
    ABRIR_LINK(link);
     IMAGEM(1, 3, 118, "undo.png");
    FECHAR_LINK;}
  else{IMAGEM(1, 3, 118, "undo.png");}
  sprintf(link,"%s%s,AJUDA,0,0,%d,%d",PATH_LINK,user,e.num_tab,e.num_jog+1);
  ABRIR_LINK(link);
    IMAGEM(2, 3, 118, "ajuda.png");
  FECHAR_LINK;
  sprintf(link,"%s%s,MARCA_ANCORA,0,0,%d,%d",PATH_LINK,user,e.num_tab,e.num_jog+1);
  ABRIR_LINK(link);
    IMAGEM(3, 3, 118, "ancora.png");
  FECHAR_LINK;
  if(e.ancora==1){
    sprintf(link,"%s%s,VOLTA_ANCORA,0,0,%d,%d",PATH_LINK,user,e.num_tab,e.num_jog+1);
    ABRIR_LINK(link);
     IMAGEM(4, 3, 118, "desfazer_ancora.png");
    FECHAR_LINK;}
  else{IMAGEM(4, 3, 118, "desfazer_ancora.png");}
  sprintf(link,"%s%s,PUZZLE1,0,0,1,0",PATH_LINK,user);
  ABRIR_LINK(link);
    IMAGEM(0, 4, 118, "tabuleiro_1.png");
  FECHAR_LINK;
  sprintf(link,"%s%s,PUZZLE2,0,0,2,0",PATH_LINK,user);
  ABRIR_LINK(link);
    IMAGEM(1, 4, 118, "tabuleiro_2.png");
  FECHAR_LINK;
  sprintf(link,"%s%s,PUZZLE3,0,0,3,0",PATH_LINK,user);
  ABRIR_LINK(link);
    IMAGEM(2, 4, 118, "tabuleiro_3.png");
  FECHAR_LINK;
  sprintf(link,"%s%s,RESOLVER,0,0,%d,%d",PATH_LINK,user,e.num_tab,e.num_jog+1);
  ABRIR_LINK(link);
    IMAGEM(4, 4, 118, "resolve.png");
  FECHAR_LINK;
}

/**
Funcao que imprime a grelha do estado
@param e Estado atual
*/
void imprimeGrelha(ESTADO e){
  int lin, col;
  VALOR v;
  for (lin=0; lin<e.num_lins; lin++){
       for (col=0; col<e.num_cols; col++){
          v = e.grelha[lin][col];
        switch (v) {
                case VAZIA: 
                case SOL_X:
                case SOL_O:
                    imprimeCasa_link(e,lin,col);
                    break;
                case FIXO_O:
                case FIXO_X:
                case BLOQUEADA:
                    imprimeCasa(e,lin,col);
                    break;
        }
       }
    }
}