#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <fcntl.h>
#include <errno.h>
#include <unistd.h>

#define MAX_ARG_SIZE 1024

int main (int argc, int argv[]){
	int fd;
	int logfile;
	int bytes_read;
	char buf[MAX_ARG_SIZE];
	while(1){
		if((fd = open("fifo",O_RDONLY)) == -1){
			perror("open");
			return -1;
		}
		else{
			printf("opened fifo for reading\n");
		}
		while((bytes_read = read(fd,buf,MAX_ARG_SIZE)) > 0){
			write(logfile,buf,bytes_read);
			printf("Write %s\n",buf);
		}

		if(bytes_read == 0){
			printf("EOF\n");
		}
		else{
			perror("read");
		}
		close(logfile);
		close(fd);
	}
}