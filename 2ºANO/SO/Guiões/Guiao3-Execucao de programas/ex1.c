#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

/*Programa que execute o comando ls -l.*/
int main(){
	
	execlp("ls","ls","-l",NULL);
	
}