#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>

#define NUMBEROFSONS 10

/*Programa que crie dez processos filhos que deverao executar sequencialmente*/
int main(){
	pid_t pid;
	int i, estado;

	for (i = 1; i <= NUMBEROFSONS; i++) {
		pid = fork();
		if (pid == -1) {
			perror("fork failed");
			return -1;
		}

		if (pid == 0) {
			// Child
			printf("I'm the son with pid: %d and number: %d.", getpid(), i);
			printf("My father has the pid: %d.\n", getppid());
			_exit(i);
		} 
		else {
			// Parent
			wait(&estado);	
			if (WIFEXITED(estado)) printf("Child with pid: %d terminated with the code: %d.\n", pid, WEXITSTATUS(estado));
			else perror("Child went wrong");
		}
	}
	return 0;
}