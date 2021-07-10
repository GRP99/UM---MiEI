CC = gcc
CFLAGS = -Wall -g

program: person
	$(CC) $(CFLAGS) program.c -o program person.o

person:
	$(CC) $(CFLAGS) -c person.c

clean:
	rm program person.o
