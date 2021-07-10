#include <unistd.h>
#include <sys/wait.h>
#include <wordexp.h>
#include <stdio.h>
#include <stdlib.h>

#define COMMAND "ls -la"

/*Passa o nome do comando ou nome do programa especificado pelo comando para o ambiente do host a ser executado 
pelo processador de comandos e retorna após o comando ter sido concluído.*/
int mysystem(const char* command){
	wordexp_t p;
	int status;
	pid_t pid;

	if (!wordexp(command, &p, 0)) {
		pid = fork();
		if (pid < 0) {
			perror("Fork fail");
			_exit(-1);
		}
		if (pid == 0) {
			execvp(p.we_wordv[0], p.we_wordv);
			_exit(-1);
		}
		wordfree(&p);
		waitpid(pid,&status,0);
		if (WIFEXITED(status)) return WEXITSTATUS(status);
	}
	else perror("ERROR!!!");
	return -1;
}


int main(){
	int res;
	res = mysystem(COMMAND);
	printf("Mysystem function returned value %d\n", res);
	return 0;
}