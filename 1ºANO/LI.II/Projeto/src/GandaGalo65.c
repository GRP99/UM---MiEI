/**
@file GandaGalo65.c
Esqueleto do programa
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cgi.h" 
#include "estado.h"
#include <unistd.h>
#include "ajudas.h"
#include "gravaFundo.h"
#include "svgRelate.h"
#include "valida.h"

#define MAX_BUFFER      10240
#define GRELHA      4
#define TAM     55
#define FALSE 0
#define TRUE  1
#define PATH_TABULEIROS "/var/www/html/tabuleiros/"
#define PATH_ESTADOS "/var/www/html/estados/"
#define PATH_LINK "http://localhost/cgi-bin/GandaGalo65?"

static const  struct {
    JOGADAS val;
    const char *str;
} 
conversion [] = {{PUZZLE1, "PUZZLE1"},{PUZZLE2, "PUZZLE2"},{PUZZLE3, "PUZZLE3"},{NADA, "NADA"},{MARCA_X, "MARCA_X"},{MARCA_O, "MARCA_O"},{MARCA_VAZIO, "MARCA_VAZIO"},{UNDO, "UNDO"},{MARCA_ANCORA, "MARCA_ANCORA"},{VOLTA_ANCORA, "VOLTA_ANCORA"},{AJUDA, "AJUDA"},{VALIDA, "VALIDA"},{COMECAR,"COMECAR"},{RESOLVER, "RESOLVER"},};

JOGADAS str2enum (const char *str){
    unsigned j;
    for (j = 0;  j < sizeof (conversion) / sizeof (conversion[0]);  ++j)
        if (!strcmp (str, conversion[j].str))
                return conversion[j].val;
    return 1;
}

/**
Funcao que testa se existe algum espaco na grelha vazio
@param e Estado atual
@returns A veracidade do que se está a testar
*/
int vaz(ESTADO e){
	int i,j;
	for (i=0;i< e.num_lins;i++){
		for (j=0;j< e.num_cols;j++){
			if (e.grelha[i][j]==VAZIA) return TRUE;
		}
	}
	return FALSE;
}

/**
Funcao que resolve o tabuleiro
@param e Estado atual
@returns e Estado atualizado 
*/
ESTADO resolve(ESTADO e){
    while (vaz(e)){
        e = help(e);
    }
    return e;
}

/**
Funcao que le os diferentes tabuleiros
@param filename Nome do tabuleiro a ser lido
@returns e Estado atualizado com o tabuleiro lido
*/
ESTADO ler_tabuleiro(char *filename){
  ESTADO e;
  int L,C;
  char linha [100],undo[100],cleanAncora[100];
  FILE *fp= fopen (filename,"r");
  if(fscanf (fp,"%d %d",&L, &C)==2){
    e.num_lins=L;
    e.num_cols=C;
    for(L=0;L<e.num_lins;L++) {
        if(fscanf (fp,"%s",linha)==1){
            for(C=0;C<e.num_cols;C++){
                switch (linha[C]){
                      case'.': e.grelha[L][C]=VAZIA;
                               break;
                      case'#': e.grelha[L][C]=BLOQUEADA;
                               break;
                      case'X': e.grelha[L][C]=FIXO_X;
                               break;
                      case'O': e.grelha[L][C]=FIXO_O;
                               break;
                      default: e.grelha[L][C]=VAZIA;
                }
            }
        }
    }
  }
  fclose(fp);
  sprintf(undo,"%sundo.txt",PATH_ESTADOS);
  FILE *fi=fopen(undo,"w");
  fclose(fi);
  sprintf(cleanAncora,"%sancora.txt",PATH_ESTADOS);
  FILE *fx=fopen(cleanAncora,"w");
  fclose(fx);
  e.ancora=0;
  return e;
}

/**
Funcao que define os diferentes tabuleiros
@param e Estado atual
@returns e Estado atualizado, isto e, adequado com o tabuleiro escolhido
*/
ESTADO def_puzzle(ESTADO e){
    char nomef_estado[100],user[100];
    strcpy(user,e.user);
    switch(e.num_tab){
        case 1:
            sprintf(nomef_estado,"%sPuzzle1.txt",PATH_TABULEIROS);
            e=ler_tabuleiro(nomef_estado);
            e.num_tab=1;
            break;
        case 2:
            sprintf(nomef_estado,"%sPuzzle2.txt",PATH_TABULEIROS);
            e=ler_tabuleiro(nomef_estado);
            e.num_tab=2;
            break;
        case 3:
            sprintf(nomef_estado,"%sPuzzle3.txt",PATH_TABULEIROS);
            e=ler_tabuleiro(nomef_estado);
            e.num_tab=3;
            break;
    }
    strcpy(e.user,user);
    return e;
}

/**
Funcao que efetua a acao "Undo"
@param e Estado atual do jogo
@returns e Estado atualizado, ou seja, com a acao "Undo" executada
*/
ESTADO undo(ESTADO e){
    int i=0,j=(e.num_jog+1);
    if (j==0){return e;}
    else{
        char nomef_estado[100],nomef_estadoNW[100],linha[MAX_BUFFER];
        sprintf(nomef_estado,"%sundo.txt",PATH_ESTADOS);
        sprintf(nomef_estadoNW,"%snew.txt",PATH_ESTADOS);
        FILE *fp, *new;
        fp=fopen(nomef_estado,"r");
        new=fopen(nomef_estadoNW, "w");
        while (i<j){
            if(fscanf(fp,"%s",linha)==1){
                fprintf(new,"%s\n",linha);
                i++;
            }
        }
        e = str2estado(linha);
        e.num_jog=(j-1);
        gravar_estado_ficheiro(e,e.user);
        fclose(fp);
        fclose(new);
        unlink(nomef_estado);
        rename(nomef_estadoNW, nomef_estado);
        return e;
    }
}

/**
Função que inicializa o jogo
@param e Estado atual do jogo
@returns e Estado atualizado
*/
ESTADO inicializar(ESTADO e){
    char user[100],nomef_estado[100];
    sprintf(nomef_estado,"%sPuzzle1.txt",PATH_TABULEIROS);
    e=ler_tabuleiro(nomef_estado);
    e.acao.decisao = NADA;
    e.num_tab=1;
    sprintf(user,"convidado");
    strcpy(e.user,user);
    gravar_estado_ficheiro(e,e.user);
    return e;
}

/**
Funcao que processa as diferentes acoes a tomar
@param e Estado atual do jogo
@param acao Acao a processar
@param user Nome do jogador
@returns e Estado atualizado
*/
ESTADO processa (ESTADO e, ACAO acao,char *user){
    char nomef_tab[100];
    int j=e.num_jog;
    if(acao.decisao==UNDO){e=undo(e);}
        else{
            switch (acao.decisao){
                case MARCA_X: e.grelha[acao.dec_lin][acao.dec_col]=SOL_X;
                    break;
                case MARCA_O: e.grelha[acao.dec_lin][acao.dec_col]=SOL_O;
                    break;
                case MARCA_VAZIO: e.grelha[acao.dec_lin][acao.dec_col]=VAZIA;
                    break;
                case MARCA_ANCORA:
                    gravar_ancora_ficheiro(e);
                    e.ancora=1;
                    break;
                case VOLTA_ANCORA:
                    e=ler_ancora_ficheiro(e);
                    e.ancora=0;
                    break;
                case PUZZLE1:
                    sprintf(nomef_tab,"%sPuzzle1.txt",PATH_TABULEIROS);
                    e = ler_tabuleiro(nomef_tab);
                    strcpy(e.user,user);
                    e.num_jog=0;
                    e.num_tab=1;
                    break;
                case PUZZLE2:
                    sprintf(nomef_tab,"%sPuzzle2.txt",PATH_TABULEIROS);
                    e = ler_tabuleiro(nomef_tab);
                    strcpy(e.user,user);
                    e.num_jog=0;                    
                    e.num_tab=2;
                    break;
                case PUZZLE3:
                    sprintf(nomef_tab,"%sPuzzle3.txt",PATH_TABULEIROS);
                    e = ler_tabuleiro(nomef_tab);
                    strcpy(e.user,user);
                    e.num_jog=0;
                    e.num_tab=3;
                    break;
                case AJUDA:
                    e=help(e);
                    strcpy(e.user,user);
                    e.num_jog=j;
                    break;
                case RESOLVER:
                    e=resolve(e);
                    strcpy(e.user,user);
                    e.num_jog=j;
                    break;
                case NADA:
                    break;
                case COMECAR:
                    e=def_puzzle(e);
                    break;
                default:
                    break;
                }
            gravar_undo_ficheiro(e);
            gravar_estado_ficheiro(e,user);
        }
    return e;
}

/**
Funcao que le o estado atual
@returns e Estado atualizado
*/
ESTADO ler_estado (){
    ESTADO e={0};
    int x,y,z,w;
    char user[100];
    char dec[100];
    char nomef_estado[100];
    char *args = getenv("QUERY_STRING");
    if (args[0] == '\0'){
        e=inicializar(e);
    }
    else {
        sscanf(args,"%[^,] , %[^,] , %d , %d , %d , %d",user,dec,&x,&y,&z,&w);  
        sprintf(nomef_estado,"%s%s_estado.txt",PATH_ESTADOS,user);
        e=ler_estado_ficheiro(nomef_estado);
        strcpy(e.user,user);
        e.acao.decisao = str2enum(dec);
        e.acao.dec_lin = x;
        e.acao.dec_col = y;
        e.num_jog=w;
        e.num_tab=z;
    }
    return e;
}


/**
FUNCAO PRINCIPAL DO PROGRAMA
@returns 0 se tudo correr bem
*/
int main() {
  ESTADO e = ler_estado();
  e = processa(e,e.acao,e.user);
  COMECAR_HTML;
  ABRIR_SVG(620,900);
    imprimeGrelha(e);
    imprimeBotoes(e,e.user);
    if (validalinha(e)==0 && validacoluna(e)==0 && validadiagonal1(e)==0 && validadiagonal2(e)==0) IMAGEM(4,1,100, "valido.png");
    else IMAGEM(4,1,100,"invalido.png");
  FECHAR_SVG;
  FECHAR_HTML;
  return 0;     
}