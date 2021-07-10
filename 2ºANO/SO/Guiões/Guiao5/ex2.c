#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>

/*Programa identico ao ex1.c de modo a ler continuamente ate um EOF ser lido no descritor do pipe*/

#define BUFF_SIZE 512

int main (int argc, char** argv){
	char buff[BUFF_SIZE];
	pid_t pid;
	ssize_t n;
	int err, pipe_desc[2];
	if (argc != 2) _exit(EXIT_FAILURE);
	err = pipe(pipe_desc);
	if (err < 0) _exit(EXIT_FAILURE);
	if (!strcmp("-p2f",argv[1])) {
		pid = fork();
		if (pid < 0)_exit(EXIT_FAILURE);
		if (pid == 0) {
			close(pipe_desc[1]);
			write(STDOUT_FILENO, "Mensagens do pai:\n", 19);
			do {
				n = read(pipe_desc[0],buff,BUFF_SIZE);
				write(STDOUT_FILENO, buff, n);
			} while (n > 0);
			close(pipe_desc[0]);
		}
		else {
			close(pipe_desc[0]);
			write(STDOUT_FILENO, "Mandei Mensagens porque sou o pai! \n", 37);
			write(pipe_desc[1], "Mensagem1\n",11);
			sleep(1);
			write(pipe_desc[1], "Mensagem2\n",11);
			sleep(1);
			write(pipe_desc[1], "Mensagem3\n",11);
			sleep(1);
			write(pipe_desc[1], "Mensagem4\n",11);
			sleep(1);
			write(pipe_desc[1], "Mensagem5\n",11);
			sleep(1);
			close(pipe_desc[1]);
			wait(NULL);
		}
	}
	if (!strcmp("-f2p",argv[1])) {
		pid = fork();
		if (pid < 0) _exit(EXIT_FAILURE);
		if (pid == 0) {
			close(pipe_desc[0]);
			write(STDOUT_FILENO, "Mandei Mensagens porque sou o filho! \n", 37);
			write(pipe_desc[1], "Mensagem1\n",11);
			sleep(1);
			write(pipe_desc[1], "Mensagem2\n",11);
			sleep(1);
			write(pipe_desc[1], "Mensagem3\n",11);
			sleep(1);
			write(pipe_desc[1], "Mensagem4\n",11);
			sleep(1);
			write(pipe_desc[1], "Mensagem5\n",11);
			sleep(1);
			close(pipe_desc[1]);
		} else {
			close(pipe_desc[1]);
			write(STDOUT_FILENO, "Mensagens do filho:\n", 19);
			do {
				n = read(pipe_desc[0],buff,BUFF_SIZE);
				if (n > 0) {
					write(STDOUT_FILENO, buff, n);
				}
			} while (n > 0);
			close(pipe_desc[0]);
			wait(NULL);
		}
	}
	exit(EXIT_SUCCESS);
}