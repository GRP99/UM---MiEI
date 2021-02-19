#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "avl_filial.h"


struct DADOS{
    char* idP; /*ID do produto*/
    int quantT; /*Quantidade total deste produto que um dado cliente comprou*/
    double quantG; /*Quantidade total gasta neste produto por um dado cliente*/
    int prodF1N [12]; /*Total de produtos vendidos normais pela Filial1 em cada mes numa posicao do array*/
    int prodF1P [12]; /*Total de produtos vendidos em promocao pela Filial1 em cada mes numa posicao do array*/
    int prodF2N [12]; /*Total de produtos vendidos normais pela Filial2 em cada mes numa posicao do array*/
    int prodF2P [12]; /*Total de produtos vendidos em promocao pela Filial2 em cada mes numa posicao do array*/
    int prodF3N [12]; /*Total de produtos vendidos normais pela Filial3 em cada mes numa posicao do array*/
    int prodF3P [12]; /*Total de produtos vendidos em promocao pela Filial3 em cada mes numa posicao do array*/
    int prodTN [12]; /*Total de produtos vendidos normais em cada mes numa posicao do array*/
    int prodTP [12]; /*Total de produtos vendidos em promocao em cada mes numa posicao do array*/
    struct DADOS *esq;
    struct DADOS *dir;
    int alt; 
}DADOS;

struct FIL{ 
    char* idC;  /*ID do cliente*/
    int totC; /*Total de produtos que este cliente comprou*/
    int tot1C; /*Total de produtos que este cliente comprou na filial1*/
    int tot2C; /*Total de produtos que este cliente comprou na filial2*/
    int tot3C; /*Total de produtos que este cliente comprou na filial3*/
    double totG; /*Total gasto por este cliente */
    double tot1G; /*Total gasto por este cliente na filial1 */
    double tot2G; /*Total gasto por este cliente na filial2 */
    double tot3G; /*Total gasto por este cliente na filial3*/
    INFORM listprod[26]; /*Array com cada letra do alfabeto permitindo saber quais os produtos que comprou  */
    struct FIL *esq;
    struct FIL *dir;
    int alt; 
}FIL;


/*----------------------------------------------------------------------------------------------------*/
char* strdup(char*);
int maxDados(int x, int y){
    if (x > y) return x;
    else return y;
}
int alturaDados (INFORM i){
    if (i == NULL) return 0;
    return i->alt;
}
int getInclinacaoDados (INFORM i){
    if (i == NULL) return 0;
    return alturaDados(i->esq) - alturaDados(i->dir);
}
void init_normal(int normal[]){
    int i;
    for (i=0; i<12; i++){
        normal[i]=0;
    }
}
void init_promo(int promo[]){
    int j;
    for(j=0; j<12; j++){
        promo[j]=0;
    }
}

INFORM rodarDireitaDados (INFORM i){
    INFORM x;
    x = i->esq;
    i->esq = x->dir;
    x->dir = i;
    i->alt = maxDados(alturaDados(i->esq), alturaDados(i->dir))+1;
    x->alt = maxDados(alturaDados(x->esq), alturaDados(x->dir))+1;
    return x;
}

INFORM rodarEsquerdaDados (INFORM i){
    INFORM x;
    x = i->dir;
    i->dir = x->esq;
    x->esq = i;
    i->alt = maxDados(alturaDados(i->esq), alturaDados(i->dir))+1;
    x->alt = maxDados(alturaDados(x->esq), alturaDados(x->dir))+1;
    return x;
}

INFORM inic_list_prod(char* idP,double preco, int unidades,char tipo,int mes,int fil){
    INFORM newNoP = (INFORM) malloc(sizeof(DADOS));
    newNoP->idP = strdup(idP);
    newNoP->quantT += unidades;
    newNoP->quantG += preco*unidades;
    init_normal(newNoP->prodF1N);
    init_promo(newNoP->prodF1P);
    init_normal(newNoP->prodF2N);
    init_promo(newNoP->prodF2P);
    init_normal(newNoP->prodF3N);
    init_promo(newNoP->prodF3P);
    init_normal(newNoP->prodTN);
    init_promo(newNoP->prodTP);
    if (tipo=='N' && fil==1){
        newNoP->prodF1N[mes-1] += unidades;
        newNoP->prodTN [mes-1] += unidades;
    } 
    else if(tipo=='N' && fil==2){
        newNoP->prodF2N[mes-1] += unidades;
        newNoP->prodTN [mes-1] += unidades;
    }
        else if(tipo=='N' && fil==3){
            newNoP->prodF3N[mes-1] += unidades;
            newNoP->prodTN [mes-1] += unidades; 
        }
            else if (tipo=='P' && fil==1){
                newNoP->prodF1P[mes-1] += unidades;
                newNoP->prodTP [mes-1] += unidades;
            }
                else if (tipo=='P' && fil==2){
                    newNoP->prodF2P[mes-1] += unidades;
                    newNoP->prodTP [mes-1] += unidades;
                }
                    else {
                        newNoP->prodF3P[mes-1] += unidades;
                        newNoP->prodTP [mes-1] += unidades;
                    }
    newNoP->dir=NULL;
    newNoP->esq=NULL;
    newNoP->alt=1;
    return newNoP; 
}

void free_INF(INFORM y){
    if (y==NULL) return;
    free_INF(y->esq);
    free_INF(y->dir);
    free(y->idP);
    free(y);
    return;
}

INFORM init_INF(){
    int i=0;
    INFORM nofi = (INFORM) malloc(sizeof(DADOS));
    nofi->idP = NULL;
    nofi->quantT = 0;
    nofi->quantG = 0;
    for(i=0;i<12;i++){
        nofi->prodF1N[i]=0;
        nofi->prodF1P[i]=0;
        nofi->prodF2N[i]=0;
        nofi->prodF2P[i]=0;
        nofi->prodF3N[i]=0;
        nofi->prodF3P[i]=0;
        nofi->prodTN[i]=0;
        nofi->prodTN[i]=0;
    }
    nofi->esq=NULL;
    nofi->dir=NULL;
    nofi->alt=0;
    return nofi;
}

int existeDados (INFORM i, char* idP){
    if (i == NULL) return 0;
    if (strcmp(idP,i->idP) == 0) return 1;
    else if (strcmp(idP,i->idP) < 0) return existeDados(i->esq,idP);
         else return existeDados(i->dir,idP);
}

INFORM procuraDados (INFORM i, char* idP){
    if (i == NULL) return NULL;
    if (strcmp(idP,i->idP) == 0) return i;
    else if (strcmp(idP,i->idP) < 0) return procuraDados(i->esq,idP);
         else return procuraDados(i->dir,idP);
}

INFORM insereProd(INFORM ap, char* idP, double preco, int unidades, char tipo, int mes, int fil){
    int inc = 0;
    if (ap == NULL) return inic_list_prod(idP,preco,unidades,tipo,mes,fil);
    if (strcmp(idP,ap->idP) != 0){
        if (strcmp(idP,ap->idP) < 0) ap->esq = insereProd(ap->esq,idP,preco,unidades,tipo,mes,fil);
        else ap->dir = insereProd(ap->dir,idP,preco,unidades,tipo,mes,fil);
        ap->alt = maxDados(alturaDados(ap->esq), alturaDados(ap->dir))+1;
        inc = getInclinacaoDados(ap);
        if (inc > 1 && strcmp(idP,ap->esq->idP) < 0) return rodarDireitaDados(ap);
        if (inc < -1 && strcmp(idP,ap->dir->idP) >= 0) return rodarEsquerdaDados(ap);
        if (inc > 1 && strcmp(idP,ap->esq->idP) >= 0){
            ap->esq = rodarEsquerdaDados(ap->esq);
            return rodarDireitaDados(ap);
        }
        if (inc < -1 && strcmp(idP,ap->dir->idP) < 0){
            ap->dir = rodarDireitaDados(ap->dir);
            return rodarEsquerdaDados(ap);
        }
        return ap;
    }
    else{
        ap->quantT += unidades;
        ap->quantG += preco*unidades;
        if (tipo=='N' && fil==1){
            ap->prodF1N[mes-1] += unidades;
            ap->prodTN [mes-1] += unidades;
        }    
        else if (tipo=='N' && fil==2){
            ap->prodF2N[mes-1] += unidades;
            ap->prodTN [mes-1] += unidades;
        }
            else if (tipo=='N' && fil==3){
                ap->prodF3N[mes-1] += unidades;
                ap->prodTN [mes-1] += unidades; 
            }
            else if (tipo=='P' && fil==1){
                ap->prodF1P[mes-1] += unidades;
                ap->prodTP [mes-1] += unidades;
            }
                else if (tipo=='P' && fil==2){
                    ap->prodF2P[mes-1] += unidades;
                    ap->prodTP [mes-1] += unidades;
                }
                    else {
                        ap->prodF3P[mes-1] += unidades;
                        ap->prodTP [mes-1] += unidades;
                    }
        return ap;
    }
}

/*____________________________________________________________________________________________________*/
int maxFil(int x, int y){
    if (x > y) return x;
    else return y;
}
int alturaFil (AVLFILIAL af){
    if (af == NULL) return 0;
    return af->alt;
}
int getInclinacaoFil (AVLFILIAL af){
    if (af == NULL) return 0;
    return alturaFil(af->esq) - alturaFil(af->dir);
}

AVLFILIAL rodarDireitaFil (AVLFILIAL af){
    AVLFILIAL x;
    x = af->esq;
    af->esq = x->dir;
    x->dir = af;
    af->alt = maxFil(alturaFil(af->esq), alturaFil(af->dir))+1;
    x->alt = maxFil(alturaFil(x->esq), alturaFil(x->dir))+1;
    return x;
}

AVLFILIAL rodarEsquerdaFil (AVLFILIAL af){
    AVLFILIAL x;
    x = af->dir;
    af->dir = x->esq;
    x->esq = af;
    af->alt = maxFil(alturaFil(af->esq), alturaFil(af->dir))+1;
    x->alt = maxFil(alturaFil(x->esq), alturaFil(x->dir))+1;
    return x;
}

AVLFILIAL novoNOFil (char* idC){
    int x;
    AVLFILIAL nofi = (AVLFILIAL) malloc(sizeof(FIL));
    nofi->idC = strdup(idC);
    nofi->totC = 0;
    nofi->tot1C = 0;
    nofi->tot2C = 0;
    nofi->tot3C = 0;
    nofi->totG = 0;
    nofi->tot1G = 0;
    nofi->tot2G = 0; 
    nofi->tot3G = 0;
    for(x=0; x<26; x++){
        nofi->listprod[x] = NULL;
    }
    nofi->esq=NULL;
    nofi->dir=NULL;
    nofi->alt=1;
    return nofi;
}

void free_FIAVL(AVLFILIAL x){
    int i;
    if(x==NULL) return;
    free_FIAVL(x->esq);
    free_FIAVL(x->dir);
    for(i=0; i<26; i++){
        free_INF(x->listprod[i]);
    }
    free(x->idC);
    free(x);
    return;
}

AVLFILIAL init_FIAVL(){
    int x=0;
    AVLFILIAL aux = (AVLFILIAL) malloc(sizeof(struct FIL));
    aux->idC = NULL;
    aux->totC = 0;
    aux->tot1C = 0;
    aux->tot2C = 0;
    aux->tot3C = 0;
    aux->totG = 0;
    aux->tot1G = 0;
    aux->tot2G = 0; 
    aux->tot3G = 0;
    for(x=0; x<26; x++){
        aux->listprod[x] = init_INF();
    }
    aux->esq=NULL;
    aux->dir=NULL;
    aux->alt=1;
    return aux;
}

int existeFil(AVLFILIAL afil, char* idC){
    if (afil == NULL) return 0;
    if (strcmp(idC,afil->idC) == 0) return 1;
    else if (strcmp(idC,afil->idC) < 0) return existeFil(afil->esq,idC);
         else return existeFil(afil->dir,idC);
}

AVLFILIAL procuraFil(AVLFILIAL afil, char* idC){
    if (afil == NULL) return NULL;
    if (strcmp(idC,afil->idC) == 0) return afil;
    else if (strcmp(idC,afil->idC) < 0) return procuraFil(afil->esq,idC);
         else return procuraFil(afil->dir,idC);
}

AVLFILIAL insereCli(AVLFILIAL afil, char* idP,double preco, int unidades, char tipo, char* idC, int mes, int fil){
    int inc = 0;
    if (afil == NULL) return novoNOFil(idC);
    if (strcmp(idC,afil->idC) != 0){
        if (strcmp(idC,afil->idC) < 0) afil->esq = insereCli(afil->esq,idP,preco,unidades,tipo,idC,mes,fil);
        else afil->dir = insereCli(afil->dir,idP,preco,unidades,tipo,idC,mes,fil);
        afil->alt = maxFil(alturaFil(afil->esq), alturaFil(afil->dir))+1;
        inc=getInclinacaoFil(afil);
        if (inc > 1 && strcmp(idC,afil->esq->idC) < 0) return rodarDireitaFil(afil);
        if (inc < -1 && strcmp(idC,afil->dir->idC) >= 0) return rodarEsquerdaFil(afil);
        if (inc > 1 && strcmp(idC,afil->esq->idC) >= 0){
            afil->esq = rodarEsquerdaFil(afil->esq);
            return rodarDireitaFil(afil);
        }
        if (inc < -1 && strcmp(idC,afil->dir->idC) < 0){
            afil->dir = rodarDireitaFil(afil->dir);
            return rodarEsquerdaFil(afil);
        }
        return afil;
    }
    else{
        afil->totC += unidades;
        afil->totG += preco*unidades;
        if (fil==1){
            afil->tot1C += unidades;
            afil->tot1G += preco*unidades;
        }
        if (fil==2){
            afil->tot2C += unidades;
            afil->tot2G += preco*unidades;
        }
        if (fil==3){
            afil->tot3C += unidades;
            afil->tot3G += preco*unidades;
        }
        afil->listprod[*idP-'A'] = insereProd(afil->listprod[*idP-'A'],idP,preco,unidades,tipo,mes,fil);
        return afil;
    }
}


/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>QUERIES<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<5*/
int comprador(AVLFILIAL afi,char**l,int m){
    if(afi != NULL){
        m = comprador(afi->esq,l,m);
        if((afi->tot1G != 0) && (afi->tot2G != 0) && (afi->tot3G != 0)){
            l[m]=(char*)strdup(afi->idC);
            m++;
        }
        m = comprador(afi->dir,l,m);
    }
    return m;
}
char** criaArrayFi(AVLFILIAL afi[],int to,int* ta){
    int z,w=0;
    char** l= NULL;
    if (afi != NULL){
        l = (char**) malloc(to*sizeof(char*));
        l[0]=NULL;
        for(z=0; z<26; z++){
            w=comprador(afi[z],l,w);
        }
    }
    *ta=w;
    return l;
}

/*#################################################################################################6*/
int ngmCompra(AVLFILIAL afi){
    int i,stop=0;
    int res=0;
    if (afi != NULL){
        res += ngmCompra(afi->esq);
        for (i=0; i<26 && stop!=1 ;i++){
            if (afi->listprod[i] != NULL) stop = 1;
        }
        if (stop == 0) res++;
        res += ngmCompra(afi->dir);
    }
    return res;
}

/*#################################################################################################8*/
void totalCompras(INFORM lp, int totCF1[], int totCF2[], int totCF3[]){
    int i;
    if(lp!=NULL){
        for(i=0;i<12;i++){
            totCF1[i] = totCF1[i] + (lp->prodF1N[i]) + (lp->prodF1P[i]);
            totCF2[i] = totCF2[i] + (lp->prodF2N[i]) + (lp->prodF2P[i]);
            totCF3[i] = totCF3[i] + (lp->prodF3N[i]) + (lp->prodF3P[i]);
        }
        totalCompras(lp->esq,totCF1,totCF2,totCF3);
        totalCompras(lp->dir,totCF1,totCF2,totCF3);
    }
}
void getCompras(AVLFILIAL fi,char cli[],int totCF1[],int totCF2[],int totCF3[]){
    int x,y,z;
    if(fi != NULL){
        if(strcmp(cli,fi->idC) == 0){
            for(x=0;x<12;x++){
                totCF1[x]=0;
                totCF2[x]=0;
                totCF3[x]=0;
            }
            for(y=0;y<26;y++){
                totalCompras(fi->listprod[y],totCF1,totCF2,totCF3);
            }
        }
        else{
            if (strcmp(cli,fi->idC) < 0) getCompras(fi->esq,cli,totCF1,totCF2,totCF3);
            else getCompras(fi->dir,cli,totCF1,totCF2,totCF3);
        }
    }
    else{
        for(z=0;z<12;z++){
            totCF1[z]=-1;
            totCF2[z]=-1;
            totCF3[z]=-1;
        }
    }
}

/*#################################################################################################9*/
int compraFN(AVLFILIAL fix,char** array,char* produto,int f,int tam){
    int i,stop=0;
    INFORM ajuda = NULL;
    if (fix != NULL){
        tam = compraFN(fix->esq,array,produto,f,tam);
        ajuda = procuraDados(fix->listprod[produto[0]-'A'],produto);
        if(ajuda != NULL){
            for(i=0;i<12 && stop != 1;i++){
                if(f == 1){
                    if(ajuda->prodF1N[i] != 0){
                        (array[tam]) = (char*)strdup(fix->idC);
                        tam++;
                        stop=1;
                    }
                }
                else{
                    if(f ==2){
                        if(ajuda->prodF2N[i] != 0){
                            (array[tam]) = (char*)strdup(fix->idC);
                            tam++;
                            stop=1;
                        }
                    }
                    else{
                        if(ajuda->prodF3N[i] != 0){
                            (array[tam]) = (char*)strdup(fix->idC);
                            tam++;
                            stop=1;
                        }   
                    }
                }
            }
        }
        tam=compraFN(fix->dir,array,produto,f,tam);
    }
    return tam;
}
char** inicArrayCliN(AVLFILIAL fi[],int max,int* tam,char*produto,int f){
    int x,aux=0;
    char** array = NULL;
    if(fi != NULL){
        array=(char**) malloc(max*sizeof(char*));
        array[0]=NULL;
        for(x=0;x<26;x++){
            aux=compraFN(fi[x],array,produto,f,aux);
        }
    }
    *tam=aux;
    return array;
}
int compraFP(AVLFILIAL fix,char** array,char* produto,int f,int tam){
    int i,stop=0;
    INFORM ajuda = NULL;
    if (fix != NULL){
        tam = compraFP(fix->esq,array,produto,f,tam);
        ajuda = procuraDados(fix->listprod[produto[0]-'A'],produto);
        if(ajuda != NULL){
            for(i=0;i<12 && stop != 1;i++){
                if(f == 1){
                    if(ajuda->prodF1P[i] != 0){
                        (array[tam]) = (char*)strdup(fix->idC);
                        tam++;
                        stop=1;
                    }
                }
                else{
                    if(f ==2){
                        if(ajuda->prodF2P[i] != 0){
                            (array[tam]) = (char*)strdup(fix->idC);
                            tam++;
                            stop=1;
                        }
                    }
                    else{
                        if(ajuda->prodF3P[i] != 0){
                            (array[tam]) = (char*)strdup(fix->idC);
                            tam++;
                            stop=1;
                        }   
                    }
                }
            }
        }
        tam=compraFP(fix->dir,array,produto,f,tam);
    }
    return tam;
}
char** inicArrayCliP(AVLFILIAL fi[],int max,int* tam,char*produto,int f){
    int x,aux=0;
    char** array = NULL;
    if(fi != NULL){
        array=(char**) malloc(max*sizeof(char*));
        array[0]=NULL;
        for(x=0;x<26;x++){
            aux=compraFP(fi[x],array,produto,f,aux);
        }
    }
    *tam=aux;
    return array;
}

/*#################################################################################################10*/
void trocaC(char** ch,int i,int j){
    char* x;
    if(ch[j] != NULL){
        x=strdup(ch[i]);
        ch[i]=strdup(ch[j]);
        ch[j]=strdup(x);
    }
    else{
        ch[j]=strdup(ch[i]);
        ch[i]=NULL;
    }
}
void trocaI(int* in,int i,int j){
    int x;
    x = in[i];
    in[i] = in[j];
    in[j] = x;
}
void meteCerto(int total,char** lProd, int* lTot, int maxC){
    int aux = maxC-1;
    while(aux > 0){
        if(total > lTot[aux-1]){
            trocaI(lTot,aux,aux-1);
            trocaC(lProd,aux,aux-1);
            aux--;
        }
        else{
            aux = 0;
        }
    }
}
void ordena (INFORM i, int m, char**lProd, int*lTot, int maxC){
    int aux;
    if(i != NULL){
        aux = (i->prodTN[m-1]) + (i->prodTP[m-1]);
        if (aux > lTot[maxC-1]){
            lTot[maxC-1] = aux;
            lProd[maxC-1] = strdup(i->idP);
            meteCerto(aux,lProd,lTot,maxC);
        }
        ordena(i->esq,m,lProd,lTot,maxC);
        ordena(i->dir,m,lProd,lTot,maxC);
    }

}
void percorreProdutos (AVLFILIAL afi, char c[], int m, char** lProd, int* lTot, int maxC){
    int x;
    if (afi != NULL){
        if(strcmp(c,afi->idC) == 0){
            for(x=0; x<maxC; x++){
                lProd[x] = NULL;
                lTot[x] = 0;
            }
            for(x=0; x<26; x++){
                ordena(afi->listprod[x],m,lProd,lTot,maxC);
            }
        }
        else{
          if (strcmp(c,afi->idC)<0) percorreProdutos(afi->esq,c,m,lProd,lTot,maxC);  
          else percorreProdutos(afi->dir,c,m,lProd,lTot,maxC);
        } 
    }
}
char** inspector(AVLFILIAL afi,char c[],int m,int* lTot){
    int maxC=0;
    char** lista=NULL;
    if(afi!=NULL){
        AVLFILIAL aux = procuraFil(afi,c);
        maxC = aux->totC;
        lTot = (int*) malloc(maxC*sizeof(int));
        lista = (char**) malloc(maxC*sizeof(char*));
        lista[0]=NULL;
        percorreProdutos(afi,c,m,lista,lTot,maxC);
    }
    return lista;
}
int meteValor(AVLFILIAL a,char c[],int m, char* prod){
    int valor = 0;
    INFORM aux = a->listprod[prod[0]-'A'];
    INFORM v = procuraDados(aux,prod);
    valor = v->prodTN[m-1] + v->prodTP[m-1];
    return valor;
}

int* encontra(AVLFILIAL afi,char c[],int m,char** lProd){
    int maxC=0;
    int x = 0;
    int* lista = NULL;
    if(afi != NULL){
        AVLFILIAL aux = procuraFil(afi,c);
        maxC = aux->totC;
        lista = (int*) malloc(maxC*sizeof(int));
        while(lProd[x] != NULL){
            lista[x] = meteValor(aux,c,m,lProd[x]);
            x++;
        }
    }
    return lista;
}

/*#################################################################################################11*/
int contaClientes(AVLFILIAL afi, char* codigo, int ind, int fil){
    int j = 0;
    int flag=0;
    int res =0;
    INFORM tmp = procuraDados(afi->listprod[codigo[0]-'A'],codigo);
    if(tmp!=NULL){
        switch(fil){
            case 1:
                for(j=0; j<12 && flag!=1; j++){
                    if(tmp->prodF1N[j]!=0 || tmp->prodF1P[j]!=0) flag=1;
                }
                if (flag != 0){
                    ind++;
                    return ind;
                }
                break;
            case 2:
                for(j=0; j<12 && flag!=1; j++){
                    if(tmp->prodF2N[j]!=0 || tmp->prodF2P[j]!=0) flag=1;
                }
                if (flag != 0){
                    ind++;
                    return ind;
                }
                break;
            case 3:
                for(j=0; j<12 && flag!=1; j++){
                    if(tmp->prodF3N[j]!=0 || tmp->prodF3P[j]!=0) flag=1;
                }
                if (flag != 0){
                    ind++;
                    return ind;
                }
                break;
        }
    }
    return ind;
}
int percorreCli(AVLFILIAL afi, char* codigo, int ind, int fil){
    if(afi == NULL) return ind;
    if (afi->esq) ind = percorreCli(afi->esq,codigo,ind,fil);
    ind = contaClientes(afi,codigo,ind,fil);
    if (afi->dir) ind = percorreCli(afi->dir,codigo,ind,fil);
    return ind;  
}
/*#################################################################################################12*/
void meteOrdem(int gas,char** aPr,int* aTPr,int tam){
    int a=tam-1;
    while(a>0){
        if(gas>aTPr[a-1]){
            trocaI(aTPr,a,a-1);
            trocaC(aPr,a,a-1);
            a--;
        }
        else{
            a=0;
        }
    }
}
void top3P(AVLFILIAL fi,char* aPr[],int aTPr[]){
    int x,y;
    int tot=0;
    if(fi != NULL){
        for(x=0;x<26;x++){
            if(fi->listprod[x] != NULL){
                tot=0;
                tot = fi->listprod[x]->quantG;
                if(tot > aTPr[2]){
                    aPr[2] = strdup(fi->listprod[x]->idP);
                    aTPr[2] = tot;
                    meteOrdem(tot,aPr,aTPr,3);
                }
                top3P(fi->esq,aPr,aTPr);
                top3P(fi->dir,aPr,aTPr);
            }
        }
    }
}
void top3C(AVLFILIAL fi,char* cl,char* aPr[],int aTPr[]){
    AVLFILIAL ajuda;
    ajuda= procuraFil(fi,cl);
    if (ajuda != NULL){
        top3P(ajuda,aPr,aTPr);
    }
}

/*#################################################################################################*/