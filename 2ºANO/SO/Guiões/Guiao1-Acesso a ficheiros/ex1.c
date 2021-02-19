#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

#define DEZMEGA 10*1024*1024

/*Programa que cria um ficheiro com 10MB de tamanho cujo nome e passado como argumento*/
int main(int argc, char** argv){
	int fd, i;

	if (argc != 2) {
		if (argc < 2) {
			printf("File name!\n");
        } else {
			printf("One file only!\n");
        }
		exit(1);
	}

	/* open(<filename>, <options>, <restrictions>) */
	fd = open(argv[1], O_CREAT | O_TRUNC | O_WRONLY, 0640);

	for (i = 0; i < DEZMEGA; i++) {
		write(fd, "a", 1);
    }

	close(fd);
	
	exit(0);

}