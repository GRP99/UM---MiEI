#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

struct ART{
     int id;
     float pre;
};

char *randstring(int length) {    
    static int mySeed = 25011984;
    char *string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    size_t stringLen = strlen(string);        
    char *randomString = NULL;

    srand(time(NULL) * length + ++mySeed);

    if (length < 1) {
        length = 1;
    }

    randomString = malloc(sizeof(char) * (length +1));

    if (randomString) {
        short key = 0;

        for (int n = 0;n < length;n++) {            
            key = rand() % stringLen;          
            randomString[n] = string[key];
        }

        randomString[length] = '\0';

        return randomString;        
    }
    else {
        printf("No memory");
        exit(1);
    }
}

void testeMA(){
	FILE *f = fopen("GeradorMA.txt", "a");
    
	for(int s=0; s<=10000; s++){ //insere 10000 artigos
		char *st = randstring(5);
		fprintf(f, "i %s %d\n", st, (rand()/10000));
	}

	for(int i =1; i<=5000; i++){ //cria 5000 mudanças de preço 
		fprintf(f,"p %d %d\n", rand() % 10000, (rand()/10000));
	}

	for (int j = 0; j <= 2000; j++){//muda 2000 vezes o nome 
        char *st = randstring(5);
		fprintf(f, "n %d %s\n", rand() % 10000,st);
	}

	fclose(f);	
}

void testeCV(){
	FILE *f = fopen("GeradorCV.txt", "a");
	for (int i = 0; i < 1000; ++i){
		fprintf(f,"%d\n", rand() % 10000);
	}
	printf("Acabou mostra\n");
	for(int i = 0; i < 500; i++){ //atualiza 500 stocks para 10  
		fprintf(f, "%d 10\n", rand() % 1000);
	}
	printf("Introduzir Stocks\n");
	for(int i = 0; i < 500; i++){ //efectua 500 vendas a vender 10 
		fprintf(f, "%d -10\n", rand() % 1000);
	}
	printf("Vendas\n");
	fclose(f);
}

void testeAG(){
	FILE *f = fopen("GeradorAG.txt", "a");
	for (int i = 0; i < 1000000; ++i)
	{
		fprintf(f,"%d %d %f\n", rand() % 1000000, rand() % 100000, ((float)rand()/10000000));
	}
}

int main(){
	testeMA();
	testeCV();
	testeAG();

	return 0;
}