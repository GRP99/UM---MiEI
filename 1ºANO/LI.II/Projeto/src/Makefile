CFLAGS=-std=c11 -Wall -Wextra -pedantic -O2
FICHEIROS=cgi.h estado.c estado.h ajudas.c ajudas.h gravaFundo.c gravaFundo.h GandaGalo65.c valida.c valida.h svgRelate.c svgRelate.h Makefile
EXECUTAVEL=GandaGalo65
install: $(EXECUTAVEL)
	sudo cp $(EXECUTAVEL) /usr/lib/cgi-bin
	touch install

$(EXECUTAVEL): GandaGalo65.o estado.o ajudas.o gravaFundo.o valida.o svgRelate.o 
	cc -o $(EXECUTAVEL) GandaGalo65.o estado.o ajudas.o gravaFundo.o valida.o svgRelate.o 

imagens:
	sudo mkdir -p /var/www/html/images
	sudo cp *.png /var/www/html/images

estados:
	sudo mkdir -p /var/www/html/estados
	sudo cp *.txt /var/www/html/estados

tabuleiros:
	sudo mkdir -p /var/www/html/tabuleiros
	sudo cp *.txt /var/www/html/tabuleiros

GandaGalo65.zip: $(FICHEIROS)
	zip -9 GandaGalo65.zip $(FICHEIROS)

doc:
	doxygen -g
	doxygen

clean:
	rm -rf *.o $(EXECUTAVEL) Doxyfile latex html install

estado.o: estado.c estado.h

ajudas.o: ajudas.c ajudas.h

gravaFundo.o: gravaFundo.c gravaFundo.h

valida.o: valida.c valida.h 

svgRelate.o: svgRelate.c svgRelate.h

GandaGalo65.o: GandaGalo65.c cgi.h estado.h ajudas.h gravaFundo.h valida.h svgRelate.h