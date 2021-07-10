#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

/* Implemente um programa que imprima a lista de argumentos recebidos na sua linha de comando*/
int main(int argc, char* argv[]){
	int i = 0;

	printf("Value of argc = %d \n", argc);

	printf("Listing the arguments:\n");
	while (argv[i] != NULL){
		printf("Index %d - %s \n", i, argv[i]);
		i++;
	}

	// Printing the NULL the "last" argument
	printf("Index %d - %s\n", i, argv[i]);
	
	return 0;
}