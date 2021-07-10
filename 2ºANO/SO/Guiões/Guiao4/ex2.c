#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "readln.h"

#define BUFF_SIZE 1024
#define MENU "redir command flags:\n\t-write\n\t-printf\n\t-fprintf\n"

/*Depois de realizar os redireccionamentos, e criado um novo processo que realiza as operacoes de leitura e escrita*/
int main (int argc, char**argv){
	char buff[BUFF_SIZE], *aux = buff;
	int ifd = 0, ofd = 1, efd = 2, n = 0, count = 0, err = 0;
	pid_t pid;

	if (argc != 2){
		printf(MENU);
		perror("Invalid argument's");
		exit(EXIT_FAILURE);
	}
	else{
		ifd = open("/etc/passwd", O_RDONLY);
		ofd = open("./saida.txt", O_CREAT | O_TRUNC | O_WRONLY, 0666);
		efd = open("./erros.txt", O_CREAT | O_APPEND | O_WRONLY, 0666);

		close(ifd);
		close(STDOUT_FILENO);
		err = dup(ofd);
		if (err < 0) {
			perror("file redirection failed");
			exit(err);
		}
		close(ofd);
		close(STDERR_FILENO);
		err = dup(efd);
		if (err < 0) {
			perror("file redirection failed");
			exit(err);
		}
		close(efd);

		if (!strcmp(argv[1],"-write")){
			pid = fork();
			if (pid < 0) {
				perror("fork failed. terminating all processes");
				_exit(EXIT_FAILURE);
			}
			if (pid == 0){
				while (n >= 0) {
					n = myreadln(STDIN_FILENO, buff, BUFF_SIZE);
					if (n > 0) {
						count = write(STDOUT_FILENO, buff, n);
						count *= write(STDOUT_FILENO, "\n", 1);
						count += write(STDERR_FILENO, buff, n);
						count *= write(STDERR_FILENO, "\n", 1);
					}
				}	
			}
		if (!strcmp(argv[1],"-printf")) {
				pid = fork();
				if (pid < 0) {
					perror("fork failed. terminating all processes");
					_exit(EXIT_FAILURE);
				}
				if (pid == 0){
					while (n >= 0) {
						n = scanf("%[^\n]%*c", buff);		
						if (n > 0) {
							count = printf("%s\n", buff);	
							perror(buff);
						}
					}
				}
	    }

		if (!strcmp(argv[1],"-fprintf")) {
			pid = fork();
			if (pid < 0) {
				perror("fork failed. terminating all processes");
				_exit(EXIT_FAILURE);
			}
			if (pid == 0){
				while ((aux != NULL) && (n >= 0)) {
					aux = fgets(buff, BUFF_SIZE, stdin);
					n = strlen(buff);
					if ((aux != NULL) && (n > 0)) {
						count = fprintf(stdout, "%s", buff);
						count += fprintf(stderr, "%s", buff);
					}
				}					
		    }
		}

		close(STDIN_FILENO);
		close(STDOUT_FILENO);
		close(STDERR_FILENO);

		exit(EXIT_SUCCESS);
	}
}
}