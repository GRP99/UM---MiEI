
#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>


#define LINES 10
#define COLUMNS 100
#define MAXVALUE 50

/*Pretende-se determinar a existencia de um determinado numero inteiro nas linhas de numa matriz de ´
numeros inteiros, em que o numero de colunas e muito maior do que o numero de linhas*/
int main(int argc, char** argv){
	int matrix[LINES][COLUMNS];
	int i, j, x, value = 0;
	pid_t pid = 1;

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

	
	for (x = 0; (pid > 0) && (x < LINES); x++) {
		pid = fork();
		i = x;
		if (pid < 0) {
			perror("fork failed");
			_exit(-1);
		}
		if (pid == 0) {
				for (j = 0; j < COLUMNS; j++) {
					if (matrix[i][j] == value) {
						printf("Find at position Matrix[%d][%d]\n", i, j);
					}
			    }
		}
	}

	for (i = 0; i < LINES; i++) {
		wait(NULL);
	}

	return 0;
}