#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>

/*Programa que cria apenas um pipe com nome “fifo”*/
int main (int argc, char* argv[]){
	if (mkfifo("fifo",0666) == -1){
		perror("mkfifo");
	}
	return 0;
}