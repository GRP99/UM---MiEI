#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>

#define BUFF_SIZE 512

/*Programa que cria um pipe anonimo e de seguida crie um processo filho*/
/* p2f -> comunicacao do pai com o filho*/
/* f2p -> comunicacao do filho com o pai*/
/* p2fd -> comunicacao do pai para o filho com delay*/
/* f2pd -> comunicacao do filho para o pai com delay*/
int main (int argc, char** argv){
	char buff[BUFF_SIZE];
	pid_t pid;
	ssize_t n;
	int err, pipe_desc[2]; // 0-output,1-input
	if (argc != 2) _exit(EXIT_FAILURE);
	err = pipe(pipe_desc);
	if (err < 0) _exit(EXIT_FAILURE);
	if(!strcmp("-p2f",argv[1])){
		pid = fork();
		if(pid < 0) _exit(EXIT_FAILURE);
		if (pid == 0){
			close(pipe_desc[1]);
			n = read(pipe_desc[0],buff,BUFF_SIZE);
			close(pipe_desc[0]);
			write(STDOUT_FILENO,buff,n);
		}
		else{
			close(pipe_desc[0]);
			write(pipe_desc[1],"Pai para o filho\n",18);
			close(pipe_desc[1]);
			wait(NULL);
		}
	}
	if(!strcmp("-f2p",argv[1])){
		pid = fork();
		if (pid < 0) _exit(EXIT_FAILURE);
		if (pid == 0){
			close(pipe_desc[0]);
			write(pipe_desc[1],"Filho para o pai\n",18);
			close(pipe_desc[1]);
		}
		else{
			close(pipe_desc[1]);
			n = read(pipe_desc[0],buff,BUFF_SIZE);
			close(pipe_desc[0]);
			write(STDOUT_FILENO,buff,n);
			wait(NULL);
		}
	}
	if(!strcmp("-p2fd",argv[1])){
		pid = fork();
		if (pid < 0) _exit(EXIT_FAILURE);
		if (pid == 0){
			close(pipe_desc[1]);
			n = read(pipe_desc[0],buff,BUFF_SIZE);
			close(pipe_desc[0]);
			write(STDOUT_FILENO,buff,n);
		}
		else{
			close(pipe_desc[0]);
			sleep(5);
			write(pipe_desc[1],"Pai para o filho com delay\n",28);
			close(pipe_desc[1]);
			wait(NULL);
		}
	}
	if(!strcmp("-f2pd",argv[1])){
		pid = fork();
		if (pid < 0) _exit(EXIT_FAILURE);
		if (pid == 0){
			close(pipe_desc[0]);
			sleep(5);
			write(pipe_desc[1],"Filho para o pai com delay\n",28);
			close(pipe_desc[1]);
		}
		else{
			close(pipe_desc[1]);
			n = read(pipe_desc[0],buff,BUFF_SIZE);
			close(pipe_desc[0]);
			write(STDOUT_FILENO,buff,n);
			wait(NULL);
		}
	}
	_exit(EXIT_SUCCESS);
}