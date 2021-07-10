#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

#define NUMBEROFSONS 10

/*Implemente um programa que crie dez processos filhos que deverao executar (potencialmente) em concorrencia*/
int main(){
	pid_t pid;
	int i, status;

	for (i = 1; i <= NUMBEROFSONS; i++) {
		pid = fork();
		if (pid == -1) {
			perror("fork failed");
			_exit(-1);
		}
		if (pid == 0) { // Children
			printf("I'm a children with PID: %d - code: %d. \n", getpid(), i);
			_exit(i);
		}
	}

	for (i = 0; i < NUMBEROFSONS; i++) {
		pid = wait(&status); // Father
		if ((pid > 0) && (WIFEXITED(status))) {
			printf("The child with PID: %d and with the code: %d.\n", pid, WEXITSTATUS(status));
		} 
		else {
			perror("Error with one of the son's");
			_exit(-1);
		}
	}

	return 0;
}