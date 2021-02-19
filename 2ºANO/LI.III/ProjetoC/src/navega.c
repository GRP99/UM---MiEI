#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#include "navega.h"

int introNav(int tam){
    int pagiT=0;
    printf("Total de resultados: %d\n",tam);
    if (tam%13 != 0) pagiT = (tam/13)+1;
    else pagiT = tam/13;
    printf("Total de páginas: %d\n",pagiT);
    return pagiT;
}

void navegador(char** array,int tam,int z){
    if(tam==0){
        printf("Nenhum Cliente comprou o Produto com esta condição.\n");
        return;
    }
    int x=0,opVal=0,y=0,pagA=1,socorro=0;
    char op;
    if(z==1) printf("\033c");
    int pagT=introNav(tam);
    if(array!=NULL){
        if(tam<=13){
            printf("A mostrar página 1 de 1.\n");
            while(array!=NULL){
                printf("%s\n",array[x]);
                x++;
                if(tam==x){
                    printf("Pressione P para sair.\n");
                    socorro=scanf("%c",&op); op=toupper(op);
                    while(!opVal){
                        switch(op){
                            case 'P':{opVal=1;printf("\033c");} return;
                            default:{printf("Insira uma opção correta.\n"); socorro=scanf(" %c",&op);op=toupper(op);}
                        }
                    }
                }
            }
        }
        if(tam>13){
            while(x<tam){
                if(pagT==pagA){
                    printf("A mostrar página %d de %d \n",pagA,pagT);
                    while(y<=(tam%13)){
                        printf("%s\n",array[x]);
                        x++; y++;
                        if(y=tam%13){
                            printf("Página anterior pressione R.\n");
                            printf("Pressione P para sair.\n");
                            socorro=scanf("%c",&op); op=toupper(op);
                            while(!opVal){
                                switch(op){
                                    case 'R':{x=x-26;
                                                y++;
                                                if(x<0)x=0;
                                                opVal=1;
                                                pagA--;
                                              if(pagA<1) pagA = 1;
                                              printf("\033c");}
                                              break;
                                    case 'P':{printf("\033c");} return;
                                    default:{printf("Insira uma opção correta.\n"); socorro=scanf(" %c",&op);op=toupper(op);}
                                }
                            }
                        }
                    opVal=0;
                    }
                    y=0;
                }else{
                    while(y<13){
                        if(y==0)printf("A mostrar página %d de %d \n",pagA,pagT);
                        printf("%s\n",array[x]);
                        x++; y++;
                        if(y==13){
                            printf("Página Seguinte pressione G.\n");
                            if(pagA!=1)printf("Página Anterior pressione R.\n");
                            printf("Pressione P para sair.\n");
                            socorro=scanf("%c",&op); op=toupper(op);
                            while(!opVal){
                                switch(op){
                                    case 'G':{opVal=1;pagA++;printf("\033c");} break;
                                    case 'R':{if(pagA!=1){x=x-26;
                                              if(x<0)x=0;
                                              opVal=1;
                                              pagA--;
                                              if(pagA<1) pagA = 1;
                                              printf("\033c");
                                              break;}else{printf("Insira uma opção correta.\n"); socorro=scanf(" %c",&op);op=toupper(op);}}
                                    case 'P':{opVal=1;printf("\033c");} return;
                                    default:{printf("Insira uma opção correta.\n"); socorro=scanf(" %c",&op);op=toupper(op);}
                                }
                            }
                        }
                    opVal=0;
                    }
                    y=0;
                }
            }
        }
    }
}