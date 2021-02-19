#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>

/*Programa que crie um processo filho*/
int main(int argc, char** argv){
	pid_t childpid;
	int orphan = 0;

	/* Zombie child or not */
	if ((argc > 1) && (!strcmp("-orphan",argv[1]))) orphan = 1;

	/* Create a child */
	childpid = fork();

	if (childpid == -1) {
		perror("fork failed");
		return -1;
	}

	if (childpid > 0) { // Father
		if (!orphan) wait(NULL);
		printf("I\'m the father with the process id: %d.\n", getpid());
		printf("I created a son with the pid: %d and my father is: %d. \n",childpid, getppid());
	} 
	else { // Son
		if (orphan) sleep(1);
		printf("I\'m the son with the process pid: %d.\n", getpid());
		printf("My father has the pid: %d.\n", getppid());
	}
	return 0;
}