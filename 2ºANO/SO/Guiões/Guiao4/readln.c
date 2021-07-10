#include <stdlib.h>
#include "readln.h"

ssize_t myreadln(int fildes, void *buf, size_t nbyte){
	size_t i = 0;
	ssize_t n = 1;
	char c = ' ';
	while ((i < nbyte) && (n > 0) && (c != '\n')) {
		n = read(fildes, &c, 1);
		if (n && (c != '\n')) {
			((char*) buf)[i] = c;
			i++;
		}
	}
	if (i < nbyte) ((char*) buf)[i] = 0;
    else {
		i--;
		((char*) buf)[i] = 0;
	}
	if (n < 0) return n;
	if ((n == 0) && (i == 0)) return (-1);
    return i;
}

