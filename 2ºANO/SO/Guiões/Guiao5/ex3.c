#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>

/* Programa que executa o comando wc num processo filho. O processo pai deve enviar
ao filho atraves dum pipe anónimo uma sequência de linhas de texto introduzidas pelo utilizador no stdin. */

#define BUFF_SIZE 512

int main (int argc, char** argv)
{
	char buff[BUFF_SIZE];
	pid_t pid;
	ssize_t n;
	int err, pipe_desc[2];
	if ((argc != 1) && (argv != NULL)) _exit(EXIT_FAILURE);
	err = pipe(pipe_desc);
	if (err < 0) _exit(EXIT_FAILURE);
	pid = fork();
	if (pid < 0) _exit(EXIT_FAILURE);
	if (pid == 0) {
		close(pipe_desc[1]);
		err = dup2(pipe_desc[0], STDIN_FILENO);
		if (err < 0) _exit(EXIT_FAILURE);
		else close(pipe_desc[0]);
		execlp("wc", "wc", NULL);
		_exit(EXIT_FAILURE);
	} 
	else {
		close(pipe_desc[0]);
		do {
			n = read(STDIN_FILENO, buff, BUFF_SIZE);
			write(pipe_desc[1], buff, n);
		} while (n > 0);
		close(pipe_desc[1]);
		wait(NULL);
	}
	exit(EXIT_SUCCESS);
}