#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>

#define LINES 10
#define COLUMNS 100
#define MAXVALUE 50

/*Programa indentico ao ex6 mas  pretende-se que imprima por ordem crescente os numeros de linha onde existem ocorrencias do numero*/
int main(int argc, char** argv){
	int matrix[LINES][COLUMNS];
	int i, j, x, value, estado = 0;
	pid_t pid[LINES] = {0};

	if (argc == 2) {
		value = atoi(argv[1]);
		if (!((value > 0) && (value <= MAXVALUE))) {
			perror("Input argument invalid");
			return -1;
		}
	} 
	else {
		perror("Too much argument's");
		return -1;
	}

	for (i = 0; i < LINES; i++) {
		for (j = 0; j < COLUMNS; j++) {
			matrix[i][j] = rand() % MAXVALUE;
		}
	}
	printf(" A Matrix has built with %d lines and %d columns.\n", LINES, COLUMNS);

	
	for (x = 0; x < LINES; x++) {
		pid[x] = fork();
		i = x;
		if (pid[x] < 0) {
			perror("fork failed");
			_exit(-1);
		}
		if (pid[x] == 0) {
				for (j = 0; j < COLUMNS; j++) {
					if (matrix[i][j] == value) _exit(1);
			    }
		}
	}

	for (i = 0; i < LINES; i++) {
		waitpid(pid[i],&estado,0);
		printf(" %d;",i);
	}
	printf("\n");
	return 0;
}