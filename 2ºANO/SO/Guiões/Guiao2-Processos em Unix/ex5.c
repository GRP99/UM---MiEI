#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

#define NOFDESCENDANTES 10

/*. Implemente um programa que crie uma descendencia em profundidade de dez processos, ou seja, o
processo cria um filho, este filho cria outro, e assim por diante ate ao decimo nıvel de descendencia*/
int main(){
	pid_t pid = 0;
	int i;

	for (i = 1; (i <= NOFDESCENDANTES) && (!pid); i++) {
		pid = fork();
		if (pid < 0) {
			perror("fork failed");
			_exit(-1);
		}
		if (pid > 0) { // Parent
			wait(NULL);
			printf("I'm the father now, PID: %d and my father is PID: %d. My child is PID: %d.\n",getpid(), getppid(), pid);
		} 
		else printf("I'm the child now, PID: %d and my father is PID: %d.\n", getpid(), getppid()); // Child
	}
	return 0;
}