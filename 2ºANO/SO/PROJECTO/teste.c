#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <time.h>

struct ART{
     int id;
     float pre;
};

struct STR{
    char informacao[1024]; 
};

struct STO{
    int quantidade;
};

struct VENDA{
	int codigo;
	int quantidade;
	int facturado;
};

struct AGR{
	int ultimalinha;
};

struct PONTO{
    int linha;
};

typedef struct{
	int codigo;
	int quantidade;
	float facturado;
} ven;

/*

int procura(int id, ven* arr, int tam){
	int i=0;
	for(i=0; i < tam; i++){
    	if (arr[i].codigo == id) return i;
    }
  	return -1;
}


int main(int argc, char *argv[]){
	struct ART infA;
	int fa;
	fa = open("ARTIGOS.txt",O_RDONLY,0666);
	while (read(fa,&infA,sizeof(struct ART))){
		printf("%d\n",infA.id);
		printf("%f\n",infA.pre);
	}
	close(fa);
	return 0;
}


int main(int argc, char *argv[]){
	struct STR infS;
	int fa;
	fa = open("STRINGS.txt",O_RDONLY,0666);
	while (read(fa,&infS,sizeof(struct STR))){
		printf("%s\n",infS.informacao);
	}
	close(fa);
	return 0;
}

int main(int argc, char *argv[]){
	struct STO inf;
	int fa;
	fa = open("STOCKS.txt",O_RDONLY,0666);
	while (read(fa,&inf,sizeof(struct STO))){
		printf("%d\n",inf.quantidade);
	}
	close(fa);
	return 0;
}



int main(int argc, char *argv[]){
	struct VENDA inf;
	struct AGR l;
	int fa;
	fa = open("VENDAS.txt",O_RDONLY,0666);
	read(fa,&l,sizeof(struct AGR));
	printf("%d\n",l.ultimalinha);
	while (read(fa,&inf,sizeof(struct VENDA))){
		printf("%d\n",inf.codigo);
		printf("%d\n",inf.quantidade);
		printf("%d\n",inf.facturado);
	}
	close(fa);
	return 0;
}


int main(int argc, char *argv[]){
	char teste[7];
	char namefile[1024];
	time_t rawtime;
	struct tm *info;
    time(&rawtime);
    info = localtime(&rawtime);
    sprintf(namefile,"%d-%d-%dT%d:%d:%d.txt",(info->tm_year+1900),(info->tm_mon+1),info->tm_mday,info->tm_hour,info->tm_min,info->tm_sec);
    printf("%s\n",namefile);
    int criado = open(namefile,O_CREAT | O_RDWR, 0666);
    write(criado,"Teste\n",7);
    lseek(criado,0,SEEK_SET);
    read(criado,&teste,7);
    printf("%s\n",teste);
    close(criado);
}

int main(int argc, char *argv[]){
	int fv=0, pos2=0, used2=0, capacity2=0, conta=0, criado=0, x=0;
	time_t rawtime;
   	struct tm *info;
	struct AGR salta;
	struct VENDA vend;
    struct PONTO momento;
	char namefile[1024];
	ven* array = (ven*) malloc(sizeof(ven)*capacity2);
	fv = open("VENDAS.txt",O_RDONLY, 0666);
    printf("Mensagem chegou");
    read(fv,&salta,sizeof(struct AGR));
    printf("%d\n",salta.ultimalinha);
    lseek(fv,(momento.linha+1) * sizeof(struct VENDA),SEEK_CUR);
    while (read(fv,&vend,sizeof(struct VENDA))){
                    pos2 = procura(vend.codigo,array,used2);
                    if (pos2 >= 0){
                        array[pos2].quantidade = array[pos2].quantidade + vend.quantidade;
                        array[pos2].facturado = array[pos2].facturado + vend.facturado;
                    }
                    else{
                        if(used2==capacity2){
                            capacity2 = 2*capacity2;
                            array = (ven*) realloc(array,sizeof(ven)*capacity2);
                        }
                        array[used2].codigo = vend.codigo;
                        array[used2].quantidade = vend.quantidade;
                        array[used2].facturado = vend.facturado;
                        used2++;
                    }
                    conta++;
                }
                close(fv);
                time(&rawtime);
                info = localtime(&rawtime);
                sprintf(namefile,"%d-%d-%dT%d:%d:%d.txt",(info->tm_year+1900),(info->tm_mon+1),info->tm_mday,info->tm_hour,info->tm_min,info->tm_sec);
                printf("%s\n",namefile);
                criado = open(namefile,O_CREAT | O_RDWR, 0666);
                for(x=0; x<used2; x++){
                    vend.codigo = array[x].codigo;
                    vend.quantidade = array[x].quantidade;
                    vend.facturado = array[x].facturado;
                    write(criado,&vend,sizeof(struct VENDA));
                }
                close(criado);
                momento.linha = conta;
                free(array);

}
*/
int tradutor(){
	int count=0;
	char buf[64];
	struct PONTO momento;
	struct VENDA vend;
	int fv = open("VENDAS.txt", O_RDONLY);
	int tmp = open("tempVENDAS.txt", O_RDWR | O_CREAT, 0666);
	if (tmp < 0) return 0;
	lseek(fv,0,SEEK_SET);
	read(fv,&momento,sizeof(struct PONTO));
	lseek(fv,(momento.linha) * sizeof(struct VENDA),SEEK_CUR);
	while (read(fv,&vend,sizeof(struct VENDA))){
		sprintf(buf,"%d %d %d\n",vend.codigo,vend.quantidade,vend.facturado);
		write(tmp,buf,strlen(buf));
		count++;
	}
	close(fv);
	close(tmp);
    	return count;
}

int main(int argc, char *argv[]){
	int out, x, tmp;
	char namefile[64];
	time_t rawtime;
	struct tm *info;
	struct PONTO momento;
	char* args[] = {"./ag",NULL};
	
	int v = open("VENDAS.txt", O_RDONLY, 0666);
    lseek(v,0,SEEK_SET);
    if(read(v,&momento,sizeof(struct PONTO))){
    	x = tradutor();
        if (x == 0) return -1;
        time(&rawtime);
        info = localtime(&rawtime);
        sprintf(namefile,"%d-%d-%dT%d:%d:%d.txt",(info->tm_year+1900),(info->tm_mon+1),info->tm_mday,info->tm_hour,info->tm_min,info->tm_sec);
        tmp = open("tempVENDAS.txt",O_RDONLY);
        out = open(namefile,O_WRONLY | O_TRUNC | O_CREAT, S_IRUSR | S_IRGRP | S_IWGRP | S_IWUSR);
		dup2(tmp,0);
        dup2(out,1);
        close(tmp);
  		close(out);
  		execv(args[0],args);
  	}
  	close(v);
  	remove("tempVENDAS.txt");
  	return 0;
}

