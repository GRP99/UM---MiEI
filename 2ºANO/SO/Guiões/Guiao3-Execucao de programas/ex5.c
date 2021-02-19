#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

/*Programa que executa concorrentemente uma lista de executaveis especificados como argumentos da linha de comandos*/
int main(int argc, char** argv){
	int i;
	pid_t pid;

	for (i = 1; i < argc; i++) {
		pid = fork();
		if (pid < 0) {
			perror("Fork failed");
			_exit(-1);
		}

		if (pid == 0) {
			printf("Sending a child with codename: %s\n", argv[i]);
			execlp(argv[i], argv[i], NULL);
			_exit(-1);
		}
	}

	for (i = 1; i < argc; i++) {
		wait(NULL);
    }

	return 0;
}