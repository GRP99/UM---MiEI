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

/*Programa que repete para este pipe todas as linhas de texto lidas a partir do seu standard input*/
int main (int argc, char* arv[]){
	int fd;
	int bytes_read;
	char buf[MAX_LINE_SIZE];
	if((fd = open("fifo",O_RDONLY)) == -1){
		perror("open");
		return -1;
	}
	else{
		printf("opened fifo for reading\n");
	}
	while ((bytes_read = read(fd,&buf,MAX_LINE_SIZE)) > 0){
		write(1,&buf,bytes_read);
	}
	if(bytes_read == 0){
		printf("EOF\n");
	}
	else{
		perror("read");
	}
	close(fd);
	return 0;
}