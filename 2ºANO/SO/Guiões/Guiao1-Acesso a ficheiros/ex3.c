#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>

/*Le do standard input blocos de N bytes, valor passado como seu argumento*/
int main (int argc, char** argv) {
	char* buf = NULL;
	int n;
	int N;

	if (argc != 2) {
		printf("Falta o tamanho do bloco de N bytes a ler!\n");
		exit(1);
	}
	
	N = atoi(argv[1]);
	buf = malloc(N);
	
	while(1) {
		/* 0 = standard input */
		n = read (0,buf,N);
		
		// se nao ler nada.
		if (n <= 0) {
			free(buf);
			exit(0);
		}
		
		/* 1 = standard output */
		write(1,buf,n);
	}
}