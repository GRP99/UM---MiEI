CC=gcc
CFLAGS= -Wall

all: ag cv ma sv 

agregador: 
	$(CC) $(CFLAGS) ag.c -o ag

cliente: 
	$(CC) $(CFLAGS) cv.c -o cv

manutencao: 
	$(CC) $(CFLAGS) ma.c -o ma

servidor: 
	$(CC) $(CFLAGS) sv.c -o sv

clean:
	rm -f ag cv ma sv
