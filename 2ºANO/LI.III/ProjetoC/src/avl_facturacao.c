#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "avl_facturacao.h"


typedef struct INF{
    int tunid; /*Unidades de vendas vendidas num mes*/
    double tfact; /*Total facturado num mes*/
}INF;

struct FACT{
    char* idP; /*Id do produto*/
    INF normalF1[12]; /*Registo de vendas normais pela Filial1 deste produto pelos 12 meses*/
    INF promoF1[12]; /*Registo de vendas promocao pela Filial1 deste produto pelos 12 meses*/
    INF normalF2[12]; /*Registo de vendas normais pela Filial2 deste produto pelos 12 meses*/
    INF promoF2[12]; /*Registo de vendas promocao pela Filial2 deste produto pelos 12 meses*/
    INF normalF3[12]; /*Registo de vendas normais pela Filila3 deste produto pelos 12 meses*/
    INF promoF3[12]; /*Registo de vendas promocao pela Filial3 deste produto pelos 12 meses*/
    INF normal[12]; /*Registo de vendas normais deste produto pelos 12 meses*/
    INF promo[12];  /*Registo de vendas promocao deste produto pelos 12 meses*/
    int totF1UN; /*Total de unidades vendidas normalmente desse produto pela Filial1*/
    int totF1UP; /*Total de unidades vendidas em promocao desse produto pela Filila1*/
    int totF2UN; /*Total de unidades vendidas normalmente desse produto pela Filial2*/
    int totF2UP; /*Total de unidades vendidas em promocao desse produto pela Filial2*/
    int totF3UN; /*Total de unidades vendidas normalmente desse produto pela Filial3*/
    int totF3UP; /*Total de unidades vendidas em promocao desse produto pela Filial3*/
    int totUN; /*Total de unidades vendidas normalmente desse produto*/
    int totUP; /*Total de unidades vendidas em promocao desse produto*/
    double totF1FN; /*Total facturado normalmente desse produto pela Filial1*/
    double totF1FP; /*Total facturado em modo de promocao desse produto pela Filial1*/
    double totF2FN; /*Total facturado normalmente desse produto pela Filial2*/
    double totF2FP; /*Total facturado em modo de promocao desse produto pela Filial2*/
    double totF3FN; /*Total facturado normalmente desse produto pela Filial3*/
    double totF3FP; /*Total facturado em modo de promocao desse produto pela Filial3*/
    double totFN; /*Total facturado normalmente desse produto*/
    double totFP; /*Total facturado em modo de promocao desse produto*/
    struct FACT *esq;
    struct FACT *dir;
    int alt;
}FACT;


/*----------------------------------------------------------------------------------------------------*/
char* strdup(char*);
int maxFact(int x, int y){
    if (x > y) return x;
    else return y;
}
int alturaFact (AVLFACTURACAO af){
    if (af == NULL) return 0;
    return af->alt;
}
int getInclinacaoFact (AVLFACTURACAO a){
    if (a == NULL) return 0;
    return alturaFact(a->esq) - alturaFact(a->dir);
}
void inic_array_normal (INF n[]){
    int i;
    for(i=0; i<12; i++){
        n[i].tunid=0;
        n[i].tfact=0;
    }
}
void inic_array_promo (INF p[]){
    int i;
    for(i=0; i<12; i++){
        p[i].tunid=0;
        p[i].tfact=0;
    }
}

AVLFACTURACAO rodarDireitaFact (AVLFACTURACAO fa){
    AVLFACTURACAO x;
    x = fa->esq;
    fa->esq = x->dir;
    x->dir = fa;
    fa->alt = maxFact(alturaFact(fa->esq), alturaFact(fa->dir))+1;
    x->alt = maxFact(alturaFact(x->esq), alturaFact(x->dir))+1;
    return x;
}

AVLFACTURACAO rodarEsquerdaFact (AVLFACTURACAO fa){
    AVLFACTURACAO x;
    x = fa->dir;
    fa->dir = x->esq;
    x->esq = fa;
    fa->alt = maxFact(alturaFact(fa->esq), alturaFact(fa->dir))+1;
    x->alt = maxFact(alturaFact(x->esq), alturaFact(x->dir))+1;
    return x;
}

AVLFACTURACAO novoNOFact (char* id){
    AVLFACTURACAO nof = (AVLFACTURACAO) malloc(sizeof(FACT));
    nof->idP = strdup(id);
    inic_array_normal(nof->normalF1);
    inic_array_promo(nof->promoF1);
    inic_array_normal(nof->normalF2);
    inic_array_promo(nof->promoF2);
    inic_array_normal(nof->normalF3);
    inic_array_promo(nof->promoF3);
    inic_array_normal(nof->normal);
    inic_array_promo(nof->promo);
    nof->totF1UN=0;
    nof->totF1UP=0;
    nof->totF2UN=0;
    nof->totF2UP=0;
    nof->totF3UN=0;
    nof->totF3UP=0;
    nof->totUN=0;
    nof->totUP=0;
    nof->totF1FN=0;
    nof->totF1FP=0;
    nof->totF2FN=0;
    nof->totF2FP=0;
    nof->totF3FN=0;
    nof->totF3FP=0;
    nof->totFN=0;
    nof->totFP=0;
    nof->esq=NULL;
    nof->dir=NULL;
    nof->alt=1;
    return nof;
}

void free_FAAVL(AVLFACTURACAO x){
    if (x==NULL) return;
    free_FAAVL(x->esq);
    free_FAAVL(x->dir);
    free(x->idP);
    free(x);
    return;
}

AVLFACTURACAO init_FAAVL(){
    int i=0;
    AVLFACTURACAO aux = (AVLFACTURACAO) malloc(sizeof(struct FACT));
    aux->idP = NULL;
    for(i=0;i<12;i++){
        aux->normalF1[i].tunid = 0;
        aux->normalF1[i].tfact = 0;
        aux->normalF2[i].tunid = 0;
        aux->normalF2[i].tfact = 0;
        aux->normalF3[i].tunid = 0;
        aux->normalF3[i].tfact = 0;
        aux->promoF1[i].tunid = 0;
        aux->promoF1[i].tfact = 0;
        aux->promoF2[i].tunid = 0;
        aux->promoF2[i].tfact = 0;
        aux->promoF3[i].tunid = 0;
        aux->promoF3[i].tfact = 0;
        aux->normal[i].tunid=0;
        aux->normal[i].tfact=0;
        aux->promo[i].tunid=0;
        aux->promo[i].tfact=0;
    }
    aux->totF1UN=0;
    aux->totF1UP=0;
    aux->totF2UN=0;
    aux->totF2UP=0;
    aux->totF3UN=0;
    aux->totF3UP=0;
    aux->totUN=0;
    aux->totUP=0;
    aux->totF1FN=0;
    aux->totF1FP=0;
    aux->totF2FN=0;
    aux->totF2FP=0;
    aux->totF3FN=0;
    aux->totF3FP=0;
    aux->totFN=0;
    aux->totFP=0;
    aux->esq=NULL;
    aux->dir=NULL;
    aux->alt=0;
    return aux;
}

int existeFact (AVLFACTURACAO af, char* id){
    if (af == NULL) return 0;
    if (strcmp(id,af->idP) == 0) return 1;
    else if (strcmp(id,af->idP) < 0) return existeFact(af->esq,id);
         else return existeFact(af->dir,id);
}

AVLFACTURACAO procuraFact (AVLFACTURACAO af, char* id){
    if (af == NULL) return NULL;
    if (strcmp(id,af->idP) == 0) return af;
    else if (strcmp(id,af->idP) < 0) return procuraFact(af->esq,id);
         else return procuraFact(af->dir,id);
}

AVLFACTURACAO insereFact (AVLFACTURACAO af, char* id, double preco, int unidades, char tipo, int mes, int fil){
    int inc = 0;
    if (af == NULL) return novoNOFact(id);
    if (strcmp(id,af->idP) != 0){
        if (strcmp(id,af->idP) < 0) af->esq = insereFact(af->esq,id,preco,unidades,tipo,mes,fil);
        else af->dir = insereFact(af->dir,id,preco,unidades,tipo,mes,fil);
        af->alt = maxFact(alturaFact(af->esq), alturaFact(af->dir))+1;
        inc=getInclinacaoFact(af);
        if (inc > 1 && strcmp(id,af->esq->idP) < 0) return rodarDireitaFact(af);
        if (inc < -1 && strcmp(id,af->dir->idP) >= 0) return rodarEsquerdaFact(af);
        if (inc > 1 && strcmp(id,af->esq->idP) >= 0){
            af->esq = rodarEsquerdaFact(af->esq);
            return rodarDireitaFact(af);
        }
        if (inc < -1 && strcmp(id,af->dir->idP) < 0){
            af->dir = rodarDireitaFact(af->dir);
            return rodarEsquerdaFact(af);
        }
        return af;
    }
    else{
        if(tipo == 'N' && fil == 1){
            af->normalF1[mes-1].tunid +=unidades;
            af->normalF1[mes-1].tfact += preco*unidades;
            af->normal[mes-1].tunid += unidades;
            af->normal[mes-1].tfact += preco*unidades;
            af->totF1UN += unidades;
            af->totUN += unidades;
            af->totF1FN += preco*unidades;
            af->totFN += preco*unidades;
        }
        else if(tipo == 'N' && fil == 2){
            af->normalF2[mes-1].tunid += unidades;
            af->normalF2[mes-1].tfact += preco*unidades;
            af->normal[mes-1].tunid += unidades;
            af->normal[mes-1].tfact += preco*unidades;
            af->totF2UN += unidades;
            af->totUN += unidades;
            af->totF2FN += preco*unidades;
            af->totFN += preco*unidades;
        }
            else if (tipo == 'N' && fil == 3){
                af->normalF3[mes-1].tunid += unidades;
                af->normalF3[mes-1].tfact += preco*unidades;
                af->normal[mes-1].tunid += unidades;
                af->normal[mes-1].tfact += preco*unidades;
                af->totF3UN += unidades;
                af->totUN += unidades;
                af->totF3FN += preco*unidades;
                af->totFN += preco*unidades;
            }   
                else if(tipo == 'P' && fil == 1){
                    af->promoF1[mes-1].tunid += unidades;
                    af->promoF1[mes-1].tfact += preco*unidades;
                    af->promo[mes-1].tunid += unidades;
                    af->promo[mes-1].tfact += preco*unidades;
                    af->totF1UP += unidades;
                    af->totUP += unidades;
                    af->totF1FP += preco*unidades;
                    af->totFP += preco*unidades;
                }
                    else if (tipo == 'P' && fil == 2){
                        af->promoF2[mes-1].tunid += unidades;
                        af->promoF2[mes-1].tfact += preco*unidades;
                        af->promo[mes-1].tunid += unidades;
                        af->promo[mes-1].tfact += preco*unidades;
                        af->totF2UP += unidades;
                        af->totUP += unidades;
                        af->totF2FP += preco*unidades;
                        af->totFP += preco*unidades;
                    }
                        else{
                            af->promoF3[mes-1].tunid += unidades;
                            af->promoF3[mes-1].tfact += preco*unidades;
                            af->promo[mes-1].tunid += unidades;
                            af->promo[mes-1].tfact += preco*unidades;
                            af->totF3UP += unidades;
                            af->totUP += unidades;
                            af->totF3FP += preco*unidades;
                            af->totFP += preco*unidades;
                        }
        return af;      
    }
}


/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>QUERIES<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<3*/
void conta_Fact1(AVLFACTURACAO af,char* i, int m,double res[]){
    int totN=0;
    int totP=0;
    double totFactN=0;
    double totFactP=0;
    AVLFACTURACAO aux = NULL;
    if(procuraFact(af,i) != NULL){
        aux = procuraFact(af,i);
        totN = aux->normal[m].tunid;
        totP = aux->promo[m].tunid;
        totFactN = aux->normal[m].tfact;
        totFactP = aux->promo[m].tfact;
        res[0]=totN;
        res[1]=totP;
        res[2]=totFactN;
        res[3]=totFactP;
    }
    else{
        res[0]=0;
        res[1]=0;
        res[2]=0;
        res[3]=0;
    }
    free_FAAVL(aux);
}
void conta_Fact2(AVLFACTURACAO af,char* i, int m,double res[]){
    AVLFACTURACAO aux = NULL;
    int x=0;
    if(procuraFact(af,i) != NULL){
        aux = procuraFact(af,i);
        res[0]=aux->normalF1[m].tunid;
        res[1]=aux->normalF1[m].tfact;
        res[2]=aux->promoF1[m].tunid;
        res[3]=aux->promoF1[m].tfact;;
        res[4]=aux->normalF2[m].tunid;
        res[5]=aux->normalF2[m].tfact;
        res[6]=aux->promoF2[m].tunid;
        res[7]=aux->promoF2[m].tfact;
        res[8]=aux->normalF3[m].tunid;
        res[9]=aux->normalF3[m].tfact;
        res[10]=aux->promoF3[m].tunid;
        res[11]=aux->promoF3[m].tfact;
    }
    else{
        for(x=0;x<12;x++){
            res[x]=0;
        }
    }
    free_FAAVL(aux);
}

/*################################################################################################4*/
int souEspecial(AVLFACTURACAO af,char**array,int t){
    if(af != NULL){
        t = souEspecial(af->esq,array,t);
        if((af->totUN==0) && (af->totUP==0)){
            array[t]=(char*)strdup(af->idP);
            t++;
        }
        t = souEspecial(af->dir,array,t);
    }
    return t;
}
char** criaArrayF(AVLFACTURACAO af[],int total,int* tam){
    int x,y=0;
    char** array= NULL;
    if (af != NULL){
        array = (char**) malloc(total*sizeof(char*));
        array[0]=NULL;
        for(x=0; x<26; x++){
            y=souEspecial(af[x],array,y);
        }
    }
    *tam=y;
    return array;
}

/*################################################################################################8*/
int comprasEntre(AVLFACTURACAO af, int ini, int fim){
    int j, t=0;
    if (af != NULL){    
        for (j=ini-1; j < fim; j++){
            t += (af->normal[j].tunid) + (af->promo[j].tunid);
        }
        t+=comprasEntre(af->esq, ini, fim);
        t+=comprasEntre(af->dir, fim, fim);
    }
    return t;
}
int facturacaoEntre(AVLFACTURACAO af, int ini, int fim){
    int j, t=0;
    if (af != NULL){
        for(j=ini-1; j<fim; j++){
            t += (af->normal[j].tfact) + (af->normal[j].tfact);
        }
        t+=facturacaoEntre(af->esq,ini,fim);
        t+=facturacaoEntre(af->dir,ini,fim);
    }
    return t;
}

/*################################################################################################11*/
void insereproduto(char* codigo, char** li, int un[], int uni, int n){
    int aux = n-1,old;
    char* auxS = (char*) malloc(strlen(codigo)+1*sizeof(char));
    while(aux-1>=0){
    if(uni>un[aux-1]){
        strcpy(auxS, li[aux-1]);
        li[aux-1] = (char*)malloc(strlen(codigo)+1*sizeof(char));
        strcpy(li[aux-1],codigo);
        li[aux] = (char*)malloc(strlen(codigo)+1*sizeof(char));
        strcpy(li[aux],auxS);
        old = un[aux-1];
        un[aux-1] = uni;
        un[aux] = old;
    }
        aux--;
    }
    free(auxS);
}
int prodmaisvend3(AVLFACTURACAO f, char** li, int un[], int ind, int n){
  int j,i,aux,flag=0;
  for(j=0; j<12; j++){
      aux = aux + f->normalF3[j].tunid + f->promoF3[j].tunid;
  }
  for(i=0; i<ind && ind!=n; i++){
    if((strcmp(li[i],f->idP)) == 0){
      flag = 1;
      break;
    }
  }
  if(flag==0 && ind==n) insereproduto(f->idP,li,un,aux,n);  
  else{
    li[ind] = (char*)malloc(strlen(f->idP)+1*sizeof(char));
    li[ind] = strcpy(li[ind],f->idP);
    un[ind] = aux;
    ind++;
  }
  return ind;
}
int prodmaisvend2(AVLFACTURACAO f, char** li, int un[], int ind, int n){
  int j,i,aux,flag=0;
  for(j=0; j<12; j++){
      aux = aux + f->normalF2[j].tunid + f->promoF2[j].tunid;
  }
  for(i=0; i<ind && ind!=n; i++){
    if((strcmp(li[i],f->idP)) == 0){
      flag = 1;
      break;
    }
  }
  if(flag==0 && ind==n) insereproduto(f->idP,li,un,aux,n);  
  else{
    li[ind] = (char*)malloc(strlen(f->idP)+1*sizeof(char));
    li[ind] = strcpy(li[ind],f->idP);
    un[ind] = aux;
    ind++;
  }
  return ind;
}
int prodmaisvend1(AVLFACTURACAO f, char** li, int un[], int ind, int n){
  int j,i,aux,flag=0;
  for(j=0; j<12; j++){
      aux = aux + f->normalF1[j].tunid + f->promoF1[j].tunid;
  }
  for(i=0; i<ind && ind!=n; i++){
    if((strcmp(li[i],f->idP)) == 0){
      flag = 1;
      break;
    }
  }
  if(flag==0 && ind==n) insereproduto(f->idP,li,un,aux,n);  
  else{
    li[ind] = (char*)malloc(strlen(f->idP)+1*sizeof(char));
    li[ind] = strcpy(li[ind],f->idP);
    un[ind] = aux;
    ind++;
  }
  return ind;
}
int percorreProd3(AVLFACTURACAO a, char** li, int un[], int ind, int n){
  if(a == NULL)return ind;
  if (a->esq) ind = percorreProd3(a->esq,li,un,ind,n);
  ind = prodmaisvend3(a,li,un,ind,n);
  if (a->dir) ind = percorreProd3(a->dir,li,un,ind,n);
  return ind;  
}
int percorreProd2(AVLFACTURACAO a, char** li, int un[], int ind, int n){
  if(a == NULL)return ind;
  if (a->esq) ind = percorreProd2(a->esq,li,un,ind,n);
  ind = prodmaisvend2(a,li,un,ind,n);
  if (a->dir) ind = percorreProd2(a->dir,li,un,ind,n);
  return ind;
}
int percorreProd1(AVLFACTURACAO a, char** li, int un[], int ind, int n){
  if(a == NULL)return ind;
  if (a->esq) ind = percorreProd1(a->esq,li,un,ind,n);
  ind = prodmaisvend1(a,li,un,ind,n);
  if (a->dir) ind = percorreProd1(a->dir,li,un,ind,n);
  return ind;  
}

/*#################################################################################################*/