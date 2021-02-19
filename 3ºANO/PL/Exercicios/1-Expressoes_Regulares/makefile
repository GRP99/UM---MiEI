CC=gcc

all: filter

filter:
	flex $(FILEL)
	$(CC) lex.yy.c -o filter
	./filter < $(FILETXT)

clean:
	rm -f filter lex.yy.c
