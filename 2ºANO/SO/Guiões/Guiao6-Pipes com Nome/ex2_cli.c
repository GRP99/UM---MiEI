#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <fcntl.h>
#include <errno.h>
#include <unistd.h>

int main (int argc, int argv[]){
	int fd = open("fifo",O_WRONLY);
	if(fd == -1){
		perror("open");
	}
	else{
		printf("client created\n");
	}
	if(write(fd,argv[0],strlen(argv[0])) == -1){
		perror("write");
	}
	close(fd);
	return 0;
}