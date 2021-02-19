#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

/*Le do standard input caractere a caractere*/
int main (int argc, char *argv[]){
	char byte;
	ssize_t res;

	while ((res = read(0,&byte,1))>0){
		write(1,&byte,res);
	}

	return 0;
}