#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <readline/readline.h>
#include <readline/history.h>

#define MAX_SIZE 1024

/*Implemente, utilizando a funcao readln, um programa com funcionalidade similar ao comando nl.
Este comando repete para o standard output as linhas recebidas do standard input ou de um (so) ficheiro ´
especificado na sua linha de comando*/
int main(int argc,char** argv){
	char buffer[MAX_SIZE];
	char txt[22];
	int i = 0, k, j, n = 0, m = 0, fd;

	// Só aceita um argumento além do comando
	if (argc <= 2) {
	    // Le o input
		if (argc == 1) {
			// enquanto conseguir ler alguma coisa. mesmo que seja a linha vazia
			while (n >= 0) {
				n = readline(STDIN_FILENO,buffer,MAX_SIZE); // lê uma linha até tamanho MAX_SIZE
				if (n > 0) {
					buffer[n] = '\n';	// adiciona uma mudança de linha no fim da frase.
					i++; 				// acresce ao contador de linhas
					/* cria um texto pre-linha com as tabulaçoes e o numero da linha */
					txt[0] = '\t';
					sprintf(&txt[1],"%d",i);
					for(k = i, j = 0; k > 0; j++)
						k = k / 10;
					j++;
					txt[j] = '\t';
					txt[j+1] = EOF;

					m = write(STDOUT_FILENO,txt,j+1);		// escreve o pre-texto
					m = m + write(STDOUT_FILENO,buffer,n+1);	// escreve a linha
					// no caso de erro ao escrever
					if (m < 0) {
						exit(-3);
					}
				}
			}
			exit(0);
		} else {
			// argc == 2 - no caso de um ficheiro ser passado como argumento
			if ((fd = open(argv[1],O_RDONLY)) > 0) {
				while (n >= 0) {
					n = readline(fd,buffer,MAX_SIZE);
					if (n > 0) {
						buffer[n] = '\n';
						i++;
						txt[0] = '\t';
						sprintf(&txt[1],"%d",i);
						for (k = i, j = 0; k > 0; j++) {
							k = k / 10;
                        }
						j++;
						txt[j] = '\t';
						txt[j+1] = EOF;
						m = write(STDOUT_FILENO,txt,j+1);
						m = m + write(STDOUT_FILENO,buffer,n+1);
						if (m < 0) {
							exit(-3);
						}
					} else {
						// Serve só para imprimir uma mudança de linha no caso de a linha estar vazia ou encontrar EOF.
						if (write(STDOUT_FILENO,"\n",1) < 0) {
							exit(-3);
						}
					}
				}
				exit(0);
			} else {
				exit(-2);
			}

		}
	} else {
		exit(-1);
	}
}
