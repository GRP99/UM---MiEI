#ifndef __READLN__
#define __READLN__

#include <unistd.h>

ssize_t myreadln(int fildes, void *buf, size_t nbyte);

#endif