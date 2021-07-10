#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>


struct PONTO{
    int linha;
};
struct AGR{
	int ultimalinha;
};
struct VENDA{
	int codigo;
	int quantidade;
	int facturado;
};
typedef struct{
	int codigo;
	int quantidade;
	int facturado;
} ven;


int procura(int id, ven* arr, int tam){
	int i=0;
	for(i=0; i < tam; i++){
    	if (arr[i].codigo == id) return i;
    }
  	return -1;
}


ssize_t myreadln(int fildes, void *buf, size_t nbyte){
    size_t i = 0;
    ssize_t n = 1;
    char c = ' ';
    while ((i < nbyte) && (n > 0) && (c != '\n')) {
        n = read(fildes, &c, 1);
        if (n && (c != '\n')) {
            ((char*) buf)[i] = c;
            i++;
        }
    }
    if (i < nbyte) ((char*) buf)[i] = 0;
    else {
        i--;
        ((char*) buf)[i] = 0;
    }
    if (n < 0) return n;
    if ((n == 0) && (i == 0)) return (-1);
    return i;
}



int main (){
	int flag=1, my=0, capacity=1, id=0, aux=0, qt=0, ft=0, used=0, pos=0, i=0;
	char* tok;
	char buff[1024];
	ven* arr = (ven*) malloc(sizeof(ven)*capacity);

	while(1){

		my = myreadln(STDIN_FILENO, buff, 1024);

		if (!(strcmp(buff,"exit"))) goto final;

		if (my > 0){
			buff[(strlen(buff))] = '\0';
			tok = strtok(buff," ");
			id = atoi(tok);
			aux = 1;
			while(tok!=NULL){
				tok=strtok(NULL," ");
				if(tok != NULL){
					switch(aux){
						case 1:{qt=atoi(tok);aux++;}break;
						case 2:{ft=atoi(tok);aux++;}break;
					}
				}
			}
			pos = procura(id,arr,used);
			if (pos >= 0){
        		arr[pos].quantidade = arr[pos].quantidade + qt;
        		arr[pos].facturado = arr[pos].facturado + ft;
        	}
        	else{
        		if(used==capacity) {
            		capacity = 2*capacity;
            		arr = (ven*) realloc(arr,sizeof(ven)*capacity);
       			}
        		arr[used].codigo = id;
        		arr[used].quantidade = qt;
        		arr[used].facturado = ft;
        		used++;
        	}
        }

        if (my < 0){
            goto final;  
        }
    }

    final:
 	if (flag){
        printf("-------------\n");
 		for(i=0; i<used; i++){
			sprintf(buff,"ID: %d ; Quantidade: %d ; Total facturado: %d ",arr[i].codigo,arr[i].quantidade,arr[i].facturado);
			printf("%s\n",buff);
		}
        printf("-------------\n");
        free(arr);
 	}
    
	return 0;
}