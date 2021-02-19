#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFF_SIZE 1024
#define MENU "redir command flags:\n\t-parent\n\t-child\n"

/*Program que executa o comando "wc",sem argumentos, depois do redireccionamento dos descritores de entrada e saida*/
int main (int argc, char** argv){
	int fd_in = 0, fd_out = 1, fd_err = 2, err = 0;
	pid_t pid;
	if (argc != 2) {
		printf(MENU);
		perror("invalid number of arguments");
		exit(EXIT_FAILURE);
	} 
	else {
		fd_in = open("/etc/passwd", O_RDONLY);
		fd_out = open("./saida.txt", O_CREAT | O_TRUNC | O_WRONLY, 0640);
		fd_err = open("./erros.txt", O_CREAT | O_APPEND | O_WRONLY, 0640);

		close(STDIN_FILENO);
		err = dup(fd_in);
		if (err < 0) {
			perror("file redirection failed");
			exit(err);
		}
		close(fd_in);
		close(STDOUT_FILENO);
		err = dup(fd_out);
		if (err < 0) {
			perror("file redirection failed");
			exit(err);
		}
		close(fd_out);
		close(STDERR_FILENO);
		err = dup(fd_err);
		if (err < 0) {
			perror("file redirection failed");
			exit(err);
		}
		close(fd_err);

		if (!strcmp(argv[1],"-parent")) {
			execlp("wc","wc",NULL);
			_exit(EXIT_FAILURE);
		}

		if (!strcmp(argv[1],"-child")) {
			pid = fork();
			if (pid < 0) {
				perror("error");
				_exit(EXIT_FAILURE);
		    }
			if (pid == 0) {
				execlp("wc","wc",NULL);
				_exit(EXIT_FAILURE);
			}
		}
		close(STDIN_FILENO);
		close(STDOUT_FILENO);
		close(STDERR_FILENO);
		exit(EXIT_SUCCESS);
	}
}