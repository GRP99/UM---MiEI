#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

/*Programa que vai contando o tempo em segundos desde que comecou e: 
se o utilizador carregar em Ctrl-C imprime o tempo passado; 
se carregar em Ctrl-\ indique quantas vezes o utilizador carregou em Ctr-C e termina*/
unsigned int time=0;
unsigned int cout=0;

void print_int(int signum){
	cout++;
	printf(" Time %d. \n",time);
}

void print_quit(int signum){
	printf(" You click %d. \n",cout);
	exit(0);
}

void print_alarm(int signum){
	time++;
	alarm(1);
}

int main (int argc, char* argv[]){
	signal(SIGALRM,print_alarm);
	signal(SIGINT,print_int);
	signal(SIGQUIT,print_quit);

	alarm(1);
	while(1){
		pause();
		printf("teste\n");
	}
	return 0;
}