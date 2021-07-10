#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>

/*Programa que imprima o seu identificador de processo e o do seu pai.*/
int main(){

	printf("My process id is %d and my father's id is: %d.\n", getpid(), getppid());
	
	return 0;
}
