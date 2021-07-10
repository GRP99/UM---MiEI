#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

/*Implemente um programa semelhante ao anterior que execute o mesmo comando mas agora no contexto de um processo filho*/
int main(){
	pid_t pid;

	pid = fork();

	if (pid < 0) {
		perror("fork failed");
		_exit(-1);
	}

	if (pid == 0) {
		execlp("ls","ls","-l",NULL);
		_exit(-1);
	} 
	else wait(NULL);
	return 0;
}
