#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

#define NAMEPROGRAM "different"

/*Programa que altera o primeiro elemento da lista de argumentos*/
int main(int argc, char ** argv){
	pid_t pid;
	int i=0;
	if (argc < 2) {
		perror("No arguments passed");
		return (-1);
	}

	pid = fork();

	if (pid < 0) {
		perror("Fork failed");
		_exit(-1);
	}

	if (pid == 0) {
		argv[0] = NAMEPROGRAM;
		printf("Argument %d is now %s \n", i, argv[i]);
		execvp(argv[0], argv);
		_exit(-1);
	}
	
	wait(NULL);
	return 0;
}
