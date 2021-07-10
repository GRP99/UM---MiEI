#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <fcntl.h>
#include <errno.h>
#include <unistd.h>

#define MAX_LINE_SIZE 1024

/*Programa repete para o seu standard output todas as linhas de texto lidas a partir deste pipe*/
int main (int argc, char* arv[]){
	char buf[MAX_LINE_SIZE];
	int bytes_read,fd;
	if((fd = open("fifo",O_WRONLY)) == -1){
		perror("open");
	}
	else{
		printf("opened fifo for writing\n");
	}
	int i = 0;

	while (i<1 && (bytes_read = read(0,buf,MAX_LINE_SIZE)) > 0){
		if(write(fd,buf,bytes_read) == -1){
			perror("write");
		}
		i++;
	}
	close(fd);
	return 0;
}