#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <wordexp.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>

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


int main(int argc, char* argv[]){
	int exit_flag=1, in=0, out=0, n=0, i=0, aux=0, f = 0;
	int codigo;
	int quantidade;
	char* tok;
	char buff[1024];
	
    in = open("pipeI",O_WRONLY);
    out = open("pipeO",O_RDONLY);

    if (in < 0){
    	write(STDERR_FILENO,"Server to write is off.\n",25);
    	return -1;
    }

    if (out < 0){
    	write(STDERR_FILENO,"Server to read is off.\n",24);
    	return -1;
    }

	while (exit_flag) {

		n = myreadln(STDIN_FILENO, buff, 1024);

        if (n > 0) {
        	write(in,buff,1024);

        	if (!(strcmp("exit",buff))) exit_flag = 0;

        	if (exit_flag){

        		buff[(strlen(buff))] = '\0';
				tok = strtok(buff," ");
				codigo = atoi(tok);
				aux = 1;
				while(tok!=NULL){
					tok=strtok(NULL," ");
					if(tok != NULL){
						switch(aux){
							case 1:{quantidade=atoi(tok);aux++;}break;
							case 2:{write(STDERR_FILENO,"Invalid command.\n",18); return -1;}break;
						}
					}
				}
				quantidade = quantidade;
				codigo = codigo;
				
				if (aux==1){
					read(out,&i,sizeof(int));
					read(out,&f,sizeof(int));
					printf("| Stock -> %d ||| Preco -> %d |\n",i,f);
				}

				if (aux==2){
					read(out,&aux,sizeof(int));
					printf("| Novo stock -> %d |\n",aux);
				}
        	}
 		}
 		else exit_flag=0;
 	}

	close(in);
	close(out);

	return 0;
}