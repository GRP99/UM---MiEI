 #include <stdlib.h>
 #include <stdio.h>

float matriz_transicao[7][169][169];
float matriz_ganho[7][169][169];

float matriz_qk_pkfn1[7][169];



// alugados, devolvidos

    
float prob_filial1[2][13] = {
    {0.0456, 0.1340, 0.2128, 0.2156, 0.1756, 0.1140, 0.0640, 0.0216, 0.0112, 0.0036, 0.0016, 0.0004, 0.0000},
    {0.0484, 0.0792, 0.1200, 0.1360, 0.1380, 0.1060, 0.0884, 0.0820, 0.0720, 0.0572, 0.0444, 0.0212, 0.0072}};

float prob_filial2[2][13] = {
    {0.0520, 0.1092, 0.1308, 0.1272, 0.1244, 0.1096, 0.0864, 0.0824, 0.0672, 0.0504, 0.0328, 0.0196, 0.0080},
    {0.0124, 0.0532, 0.1224, 0.1888, 0.1968, 0.1756, 0.1156, 0.0720, 0.0372, 0.0148, 0.0068, 0.0020, 0.0024}};




// i alugados
//j devolvido
int combinacao(int stock_final,int stock,int i,int j,int envio, int *alugadosvdd,int *enviosvdd)
{
    
    int stock_local=stock;

    int alugo_vdd=i;
    if ( i>=stock) {alugo_vdd=stock;}

    stock_local-=alugo_vdd;
    
    int envio_vdd=envio;
    if (  enviosvdd!=NULL && -envio>=stock_local) {envio_vdd=-stock_local; *enviosvdd=envio_vdd;}

    stock_local-=envio_vdd;


    int recebo_vdd=j;
    if( stock -alugo_vdd +envio_vdd + j>12) recebo_vdd= 12-stock+alugo_vdd-envio_vdd;

    if( alugadosvdd!=NULL) *alugadosvdd=alugo_vdd;


    if (stock_final== stock -alugo_vdd+recebo_vdd +envio_vdd ) return 1;


   
    return 0;
}




// envio -2 - Filial 1 _> 2 carros pa Filial 2
void transi(float result[169][169], int envio)
{

 

    //estado anterior i

    for (int i = 0; i < 169; i++)
        //estado seguinte j
        for (int j = 0; j < 169; j++)
        {

 
            int stock_inicial_f1 = i/13; 
            int stock_inicial_f2 =i%13;

            int stock_final_f1= j/13;
            int stock_final_f2=j%13;


            float prob=0;

            for(int i=0;i<13;i++)
                for(int j=0;j<13 ; j++){

                 int envio_vd =envio;

                 if (combinacao(stock_final_f1,stock_inicial_f1,i,j,envio,NULL,&envio_vd)== 1)

                    for( int i2=0;i2<13;i2++)
                        for(int j2=0;j2<13;j2++){

                            if (combinacao(stock_final_f2,stock_inicial_f2,i2,j2,-envio_vd,NULL,NULL))
                                 prob+=prob_filial1[0][i]*prob_filial1[1][j]*prob_filial2[0][i2]*prob_filial2[1][j2];
                        }



                }

            result[i][j]=prob;
             
        }   
}


float funlucro(float pr1[2][13],float pr2[2][13],int stock_inicial_f1,int stock_inicial_f2, int stock_final_f1,int stock_final_f2,int envio){


    float lucro=0;
    float lucro_base=0;

    if( stock_final_f1> 8 ) lucro_base-=10;
    if( stock_final_f2> 8 ) lucro_base-=10;




    int i=0;
    for(; i<13  ;i++) // o que sai
        for(int j=0;j<13;j++){ // o que entra
             int alugados_f1=i;

          int envio_vdd=envio;
            if(combinacao(stock_final_f1,stock_inicial_f1,i,j,envio,&alugados_f1,&envio_vdd)) { 
                    for(int i2=0; i2<13  ;i2++){ // o que sai
                        int alugados_f2=i2;
                         for(int j2=0;j2<13;j2++) // o que entra
                         {
                            if(combinacao(stock_final_f2,stock_inicial_f2,i2,j2,envio_vdd,&alugados_f2,NULL)) {

                                

                                lucro+= pr1[0][i]*pr1[1][j]*pr2[0][i2]*pr2[1][j2]*(lucro_base-7*(abs(envio_vdd)) + 30*(alugados_f1 +alugados_f2));
                                    
                                }
                         }
                    } 
             }
        }


    return lucro;
}

void ganho(float result[169][169], int envio)
{

    //estado anterior i

    for (int i = 0; i < 169; i++)
        //estado seguinte j
        for (int j = 0; j < 169; j++)
        {
            int stock_inicial_f1 = i/13; 
            int stock_inicial_f2 =i%13;

            int stock_final_f1= j/13;
            int stock_final_f2=j%13;

            float lucro=0;

            

            result[i][j]=funlucro(prob_filial1,prob_filial2,stock_inicial_f1,stock_inicial_f2,stock_final_f1,stock_final_f2,envio);

    }
}
//decisao 0 -> -2 decisao 1 -> -1 decisao 2->0 ,etc

void funQn(float tra[169][169],float r[169][169],float q[169]){

    
    //estado anterior i
    for (int i = 0; i < 169; i++){
        
        q[i]=0;

        //estado seguinte j
        for (int j = 0; j < 169; j++)
            
            q[i]+= tra[i][j]*r[i][j];      

    }
        
}

void funPkFn_1(float tra[169][169],float fn1[169],float pkfn1[169]){

    for(int i=0;i<169;i++){
            pkfn1[i]=0;

        for(int j=0;j<169;j++){
            pkfn1[i]+=tra[i][j]*fn1[j];
        }
    }
}


void funQk_PKfn_1(float qk[169],float pkfn_1[169],float resu[169]){


    for(int i=0;i<169;i++){
        resu[i]=0;
        resu[i]=qk[i]+pkfn_1[i];
    }

}

void funFn(float QkPkFn1[7][169],float resu[169],int from_decisao[169]){

    for(int i=0;i<169;i++){
       
        int from=0;
        float r=QkPkFn1[0][i];
        
      

        for(int j=1;j<7;j++)
            if(QkPkFn1[j][i]>r) {r=QkPkFn1[j][i];from=j;}


        resu[i]=r;
        from_decisao[i]=from;
    }
}

// retorna a diferrenca entre os fn
float funDeltaFn(float fn_inicial[169],float fn_final[169],float resu[169]){

        for(int i=0;i<169;i++){
            resu[i]= fn_final[i]-fn_inicial[i];
        }

}

      

int CritParagem(float v[169],float x){

    int r=v[0];
    
    for(int i=1;i<169;i++)
        if (abs(r-v[i]) > x) return 1;

    return 0;
}

float copiafn(float fn_inicial[169],float fn_final[169]){
    
     for(int i=0;i<169;i++)
           fn_final[i]=fn_inicial[i];
        
}





void imprimeResultado(int from[169],float deltafn[169],int i){
    printf(" Decisao 1 , F1  3 unidade-> F2 \n,Decisao 2 , F1  2 unidade-> F2 \n,Decisao 3 , F1  1 unidade-> F2 \n,Decisao 4 , F1  0 unidade-> F2 \n Decisao 5 , F1  1 unidade <-- F2 \n,Decisao 6 , F1  2 unidade<-- F2 \n, Decisao 7 , F1  3 unidade <-- F2 \n");

    printf(" Política optima, calculada com %d iteracoes \n\n",i);


    for(int i=0;i<169;i++){
        printf("    Estado %d  Stock1 %d Stock2 %d | Decisão %d | Lucro esperado %f€ \n",i,i/13,i%13,from[i],deltafn[i]);
    }
}




void imprime_matriz_transicao( int filial,int envio){

    if(filial==1){

                float aux[13][13];
                
                for(int l=0;l<13;l++)
                    for(int c=0;c<13;c++){

                        float prob=0;

                    int stock=l;

                    if ( stock<-envio) envio=-stock;

                    for(int i=0;i<13;i++)
                        for(int j=0;j<13;j++)
                             if (combinacao(c,stock,i,j,envio,NULL,NULL)== 1)
                                prob+=prob_filial1[0][i]*prob_filial1[1][j];
              
                        aux[l][c]=prob;
                    }



            for(int linhas=0;linhas<13;linhas++){
                 
                // float somatorio=0;
                printf(" Stock %d & ",linhas);
                for(int colunas=0;colunas<13;colunas++){
                    
                    if (colunas==12)printf(" %f " , aux[linhas][colunas]);
                    else printf(" %f &" , aux[linhas][colunas]);

                    //somatorio+= aux[linhas][colunas];
                }
                   printf(" \\\\ \n");
                  // printf("somatorio %f\n",somatorio);
            }
                

    }
    else if(filial==2){

                float aux[13][13];
                
                for(int l=0;l<13;l++)
                    for(int c=0;c<13;c++){

                        float prob=0;

                    int stock=l;

                    if ( stock<-envio) envio=-stock;

                    for(int i=0;i<13;i++)
                        for(int j=0;j<13;j++)
                             if (combinacao(c,stock,i,j,envio,NULL,NULL)== 1)
                                prob+=prob_filial2[0][i]*prob_filial2[1][j];
              
                        aux[l][c]=prob;
                    }



            for(int linhas=0;linhas<13;linhas++){
                 
                 //float somatorio=0;
                printf(" Stock %d & ",linhas);
                for(int colunas=0;colunas<13;colunas++){
                    
                    if (colunas==12)printf(" %f " , aux[linhas][colunas]);
                    else printf(" %f &" , aux[linhas][colunas]);

                   // somatorio+= aux[linhas][colunas];
                }
                   printf(" \\\\ \n");
                   //printf("somatorio %f\n",somatorio);
            }
                
    }

}







void imprime_matriz_contribuicao( int filial,int envio){

    if(filial==1){

                float aux[13][13];
                

                  for(int l=0;l<13;l++)
                    for(int c=0;c<13;c++){
                           
                            float lucro=0;
                            float lucro_base=0;

                           if( c> 8 ) lucro_base-=10;
    



                    int i=0;
                    for(; i<13  ;i++) // o que sai
                        for(int j=0;j<13;j++){ // o que entra
                            int alugados_f1=i;

                            int envio_vdd=envio;
                        if(combinacao(c,l,i,j,envio,&alugados_f1,&envio_vdd)) {
                            if (envio_vdd<0) lucro_base-=-envio_vdd*7;
                            lucro+= prob_filial1[0][i]*prob_filial1[1][j]*(lucro_base + 30*alugados_f1);
                            }
                        }
                    aux[l][c]=lucro;
                    }





            for(int linhas=0;linhas<13;linhas++){
          
                printf(" Stock %d & ",linhas);
                for(int colunas=0;colunas<13;colunas++){
                    
                    if (colunas==12)printf(" %f " , aux[linhas][colunas]);
                    else printf(" %f &" , aux[linhas][colunas]);

                    
                }
                   printf(" \\\\ \n");
                  
            }
                

    }
    else if(filial==2){

        float aux[13][13];
                

                  for(int l=0;l<13;l++)
                    for(int c=0;c<13;c++){
                           
                            float lucro=0;
                            float lucro_base=0;

                           if( c> 8 ) lucro_base-=10;
    



                    int i=0;
                    for(; i<13  ;i++) // o que sai
                        for(int j=0;j<13;j++){ // o que entra
                            int alugados_f2=i;

                            int envio_vdd=envio;
                        if(combinacao(c,l,i,j,envio,&alugados_f2,&envio_vdd)) {
                            if (envio_vdd<0) lucro_base-=-envio_vdd*7;
                            lucro+= prob_filial2[0][i]*prob_filial2[1][j]*(lucro_base + 30*alugados_f2);
                            }
                        }
                    aux[l][c]=lucro;
                    }





            for(int linhas=0;linhas<13;linhas++){
          
                printf(" Stock %d & ",linhas);
                for(int colunas=0;colunas<13;colunas++){
                    
                    if (colunas==12)printf(" %f " , aux[linhas][colunas]);
                    else printf(" %f &" , aux[linhas][colunas]);

                    
                }
                   printf(" \\\\ \n");
                  
            }
               
  
                
    }

}








int main()
{/*
    int j=0;
    for(int i=-3;i<=3 && j<7;i++){
    
        transi(matriz_transicao[j],i);
        ganho(matriz_ganho[j],i);
        j++;
    }

    float deltaFn[169];
    float fn[169];
    int from[169];

    for(int i=0;i<169;i++){
          fn[i]=0;
          from[i]=-100;
          deltaFn[i]=-100;
    }
      
      deltaFn[0]=0;


    int iter=0;
    
    while(CritParagem(deltaFn,0.1)){

        for(int j=0;j<7;j++){

            float qn[169];
            float pkfn1[169];
            funQn(matriz_transicao[j],matriz_ganho[j],qn);

            funPkFn_1(matriz_transicao[j],fn,pkfn1);
            funQk_PKfn_1(qn,pkfn1,matriz_qk_pkfn1[j]);
        }

        float new_fn[169];
        funFn(matriz_qk_pkfn1,new_fn,from);

        funDeltaFn(fn,new_fn,deltaFn);
        copiafn(new_fn,fn);
        
        
    iter++;
    } 

*/

   /* Imprime from e o deltaFn  e o numero iteracoes
  
   */
 // imprimeResultado(from,deltaFn,iter);
   

    


    /* Imprimir Maior e o Menor Delta Final


    float menor=deltaFn[0]; 
    float maior=deltaFn[0]; 
    for(int i=1;i<169;i++){
        if (deltaFn[i]>maior) maior= deltaFn[i];
        if (deltaFn[i]<menor) menor= deltaFn[i];
    }
    printf(" Menor DeltaFn %f , Maior Delta %f\n",menor,maior);
*/

    /* Imprime matriz transicao por filial e envio*/
  
    // 1º argumento é a filial , 2º argumento é quanto recebe da outra filial
    // Exemplo Filial 1 , Filial 1 -> 2 unidades    
    // imprime_matriz_transicao(1,-2);


    /* Imprime matriz contribuicao por filial e envio*/
  
    // 1º argumento é a filial , 2º argumento é quanto recebe da outra filial
    // Exemplo Filial 1 , Filial 1 -> 2 unidades    
    imprime_matriz_contribuicao(2,-3);


}