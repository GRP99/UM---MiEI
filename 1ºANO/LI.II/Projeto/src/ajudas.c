#include <stdio.h>
#include "cgi.h" 
#include "estado.h"
/**
Função que efetua a ajuda
@param e Estado atual do tabuleiro
@returns e Estado atualizado, ou seja, o tabuleiro ja possui a ajuda dada
*/
ESTADO help (ESTADO e) {
  int l,c;
  // linha XX_
  for(l=0;l<e.num_lins;l++){
    for(c=0;c<e.num_cols;c++){
      if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l][c+1]==SOL_X) || (e.grelha[l][c+1]==FIXO_X))) && (e.grelha[l][c+2]==VAZIA)){
        (e.grelha[l][c+2]=SOL_O);
        return e;
      } else {
        if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l][c+1]==SOL_X) || (e.grelha[l][c+1]==FIXO_X))) && (e.grelha[l][c-2]==VAZIA)){
        (e.grelha[l][c-2]=SOL_O);
        return e;
        }
      }
    }
  }
  //linha OO_
  for(l=0;l<e.num_lins;l++){
    for(c=0;c<e.num_cols;c++){
      if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l][c+1]==SOL_O) || (e.grelha[l][c+1]==FIXO_O))) && (e.grelha[l][c+2]==VAZIA)){
        (e.grelha[l][c+2]=SOL_X);
        return e;
      } else {
        if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l][c+1]==SOL_O) || (e.grelha[l][c+1]==FIXO_O))) && (e.grelha[l][c-2]==VAZIA)){
        (e.grelha[l][c-2]=SOL_X);
        return e;
        }
      }
    }
  }
  // linha X_X
  for(l=0;l<e.num_lins;l++){
    for(c=0;c<e.num_cols;c++){
      if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l][c+2]==SOL_X) || (e.grelha[l][c+2]==FIXO_X))) && (e.grelha[l][c+1]==VAZIA)){
        (e.grelha[l][c+1]=SOL_O);
        return e;
      } 
      }
    }
  // linha O_O
  for(l=0;l<e.num_lins;l++){
    for(c=0;c<e.num_cols;c++){
      if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l][c+2]==SOL_O) || (e.grelha[l][c+2]==FIXO_O))) && (e.grelha[l][c+1]==VAZIA)){
        (e.grelha[l][c+1]=SOL_X);
        return e;
      } 
      }
    }
  //coluna XX_
  for(c=0;c<e.num_lins;c++){
    for(l=0;l<e.num_cols;l++){
      if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+1][c]==SOL_X) || (e.grelha[l+1][c]==FIXO_X))) && (e.grelha[l+2][c]==VAZIA)){
        (e.grelha[l+2][c]=SOL_O);
        return e;
      } else {
        if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+1][c]==SOL_X) || (e.grelha[l+1][c]==FIXO_X))) && (e.grelha[l-2][c]==VAZIA)){
        (e.grelha[l-2][c]=SOL_O);
        return e;
        }
      }
    }
  }
  //coluna OO_
  for(c=0;c<e.num_lins;c++){
    for(l=0;l<e.num_cols;l++){
      if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+1][c]==SOL_O) || (e.grelha[l+1][c]==FIXO_O))) && (e.grelha[l+2][c]==VAZIA)){
        (e.grelha[l][c+2]=SOL_X);
        return e;
      } else {
        if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+1][c]==SOL_O) || (e.grelha[l+1][c]==FIXO_O))) && (e.grelha[l-2][c]==VAZIA)){
        (e.grelha[l-2][c]=SOL_X);
        return e;
        }
      }
    }
  }
  //coluna X_X
  for(c=0;c<e.num_lins;c++){
    for(l=0;l<e.num_cols;l++){
      if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+2][c]==SOL_X) || (e.grelha[l+2][c]==FIXO_X))) && (e.grelha[l+1][c]==VAZIA)){
        (e.grelha[l+1][c]=SOL_O);
        return e;
      } 
      }
    }
  //colunac O_O
  for(c=0;c<e.num_lins;c++){
    for(l=0;l<e.num_cols;l++){
      if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+2][c]==SOL_O) || (e.grelha[l+2][c]==FIXO_O))) && (e.grelha[l+1][c]==VAZIA)){
        (e.grelha[l+1][c]=SOL_X);
        return e;
      } 
    }
  }
  // Diagonal 1 XX_
  for(l=0,c=0;l<e.num_lins && c<e.num_cols;l++,c++){
    if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+1][c+1]==SOL_X) || (e.grelha[l+1][c+1]==FIXO_X))) && (e.grelha[l+2][c+2]==VAZIA)){
      (e.grelha[l+2][c+2]=SOL_O);
      return e;
    }
  }
  // Diagonal 1 X_X
  for(l=0,c=0;l<e.num_lins && c<e.num_cols;l++,c++){
    if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+2][c+2]==SOL_X) || (e.grelha[l+2][c+2]==FIXO_X))) && (e.grelha[l+1][c+1]==VAZIA)){
      (e.grelha[l+1][c+1]=SOL_O);
      return e;
    }
  }
  // Diagonal 1 OO_
  for(l=0,c=0;l<e.num_lins && c<e.num_cols;l++,c++){
    if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+1][c+1]==SOL_O) || (e.grelha[l+1][c+1]==FIXO_O))) && (e.grelha[l+2][c+2]==VAZIA)){
      (e.grelha[l+2][c+2]=SOL_X);
      return e;
    }
  }
  // Diagonal 1 O_O
  for(l=0,c=0;l<e.num_lins && c<e.num_cols;l++,c++){
    if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+2][c+2]==SOL_O) || (e.grelha[l+2][c+2]==FIXO_O))) && (e.grelha[l+1][c+1]==VAZIA)){
      (e.grelha[l+1][c+1]=SOL_X);
      return e;
    }
  }
  // Diagonal 2 XX_
  for(l=0,c=e.num_cols;l<e.num_lins && c>0;l++,c--){
    if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+1][c-1]==SOL_X) || (e.grelha[l+1][c-1]==FIXO_X))) && (e.grelha[l+2][c-2]==VAZIA)){
      (e.grelha[l+2][c-2]=SOL_O);
      return e;
    }
  }
  // Diagonal 2 X_X
  for(l=0,c=e.num_cols;l<e.num_lins && c>0;l++,c--){
    if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+2][c-2]==SOL_X) || (e.grelha[l+2][c-2]==FIXO_X))) && (e.grelha[l+1][c-1]==VAZIA)){
      (e.grelha[l+1][c-1]=SOL_O);
      return e;
    }
  }
  // Diagonal 2 OO_
  for(l=0,c=e.num_cols;l<e.num_lins && c>0;l++,c--){
    if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+1][c-1]==SOL_O) || (e.grelha[l+1][c-1]==FIXO_O))) && (e.grelha[l+2][c-2]==VAZIA)){
      (e.grelha[l+2][c-2]=SOL_X);
      return e;
    }
  }
  // Diagonal 2 O_O
  for(l=0,c=e.num_cols;l<e.num_lins && c>0;l++,c--){
    if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+2][c-2]==SOL_O) || (e.grelha[l+2][c-2]==FIXO_O))) && (e.grelha[l+1][c-1]==VAZIA)){
      (e.grelha[l+1][c-1]=SOL_X);
      return e;
    }
  }
  // Diagonais XX_
  for(l=1;l<e.num_lins;l++){
    for (c=0;c<e.num_cols;c++){
      if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+1][c+1]==SOL_X) || (e.grelha[l+1][c+1]==FIXO_X))) && (e.grelha[l+2][c+2]==VAZIA)){
      (e.grelha[l+2][c+2]=SOL_O);
      return e;
      }
    l++;
    }
  }
  // Diagonais X_X
  for(l=1;l<e.num_lins;l++){
    for (c=0;c<e.num_cols;c++){
      if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+2][c+2]==SOL_X) || (e.grelha[l+2][c+2]==FIXO_X))) && (e.grelha[l+1][c+1]==VAZIA)){
      (e.grelha[l+1][c+1]=SOL_O);
      return e;
      }
    l++;
    }
  }
  // Diagonais OO_
  for(l=1;l<e.num_lins;l++){
    for (c=0;c<e.num_cols;c++){
      if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+1][c+1]==SOL_O) || (e.grelha[l+1][c+1]==FIXO_O))) && (e.grelha[l+2][c+2]==VAZIA)){
      (e.grelha[l+2][c+2]=SOL_X);
      return e;
      }
    l++;
    }
  }
  // Diagonais O_O
  for(l=1;l<e.num_lins;l++){
    for (c=0;c<e.num_cols;c++){
      if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+2][c+2]==SOL_O) || (e.grelha[l+2][c+2]==FIXO_O))) && (e.grelha[l+1][c+1]==VAZIA)){
      (e.grelha[l+1][c+1]=SOL_X);
      return e;
      }
    l++;
    }
  }
    // Diagonais 2 XX_
  for(l=1;l<e.num_lins;l++){
    for (c=e.num_cols;c>0;c--){
      if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+1][c+1]==SOL_X) || (e.grelha[l+1][c+1]==FIXO_X))) && (e.grelha[l+2][c+2]==VAZIA)){
      (e.grelha[l+2][c+2]=SOL_O);
      return e;
      }
    l++;
    }
  }
  // Diagonais 2 X_X
  for(l=1;l<e.num_lins;l++){
    for (c=e.num_cols;c>0;c--){
      if ((((e.grelha[l][c]==SOL_X) || (e.grelha[l][c]==FIXO_X)) && ((e.grelha[l+2][c+2]==SOL_X) || (e.grelha[l+2][c+2]==FIXO_X))) && (e.grelha[l+1][c+1]==VAZIA)){
      (e.grelha[l+1][c+1]=SOL_O);
      return e;
      }
    l++;
    }
  }
  // Diagonais 2 OO_
  for(l=1;l<e.num_lins;l++){
    for (c=e.num_cols;c>0;c--){
      if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+1][c+1]==SOL_O) || (e.grelha[l+1][c+1]==FIXO_O))) && (e.grelha[l+2][c+2]==VAZIA)){
      (e.grelha[l+2][c+2]=SOL_X);
      return e;
      }
    l++;
    }
  }
  // Diagonais 2 O_O
  for(l=1;l<e.num_lins;l++){
    for (c=e.num_cols;c>0;c--){
      if ((((e.grelha[l][c]==SOL_O) || (e.grelha[l][c]==FIXO_O)) && ((e.grelha[l+2][c+2]==SOL_O) || (e.grelha[l+2][c+2]==FIXO_O))) && (e.grelha[l+1][c+1]==VAZIA)){
      (e.grelha[l+1][c+1]=SOL_X);
      return e;
      }
    l++;
    }
  }
  return e;
}