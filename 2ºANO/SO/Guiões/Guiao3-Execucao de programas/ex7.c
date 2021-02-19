#include <unistd.h>
#include <wordexp.h>
#include <sys/wait.h>
#include <fcntl.h>		
#include <string.h>
#include "readln.h"

#define PROMPT_WORD "mybash > "
#define PROMPT_SIZE 9

/*Interpretador de comandos muito simples inspirado na bash*/
int main(){
	int exit_flag = 0, background_flag = 0, n = 0;
	char buff[1024];
	pid_t pid;
	wordexp_t p;

	while (!exit_flag) {
		background_flag = 0;

		write(STDOUT_FILENO,PROMPT_WORD,PROMPT_SIZE);

		n = myreadln(STDIN_FILENO, buff, 1024);

		while ((n > 0) && (buff[n - 1] == ' ')) {
			n--;
			buff[n] = 0;
		}

		if ((n > 1) && (buff[n - 1] == '&') && (buff[n - 2] == ' ')) {
			background_flag = 1;
			buff[n - 1] = 0;
			buff[n - 2] = 0;
		}

		if ((n > 0) && (!wordexp(buff, &p, 0))) {
			if (strcmp("exit",p.we_wordv[0])) {
				pid = fork();

				if (pid < 0) if (write(STDERR_FILENO,"error",42)){};

				if (pid == 0) {
					execvp(p.we_wordv[0], p.we_wordv);
					_exit(-1);
				}

				wordfree(&p);

				if (!background_flag) waitpid(pid,NULL,0);
	        } 
			else exit_flag = 1;
		} 
		else {
			if (n < 0) exit_flag = 1;
			if (n > 0) if (write(STDERR_FILENO,"error",63));
		}
	}

	return 0;
}