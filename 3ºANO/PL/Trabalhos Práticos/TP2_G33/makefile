CC=cc
GLIB_LIBS=`pkg-config --cflags --libs glib-2.0`

build:
	flex toml2json.l
	yacc -d -v toml2json.y 
	$(CC) -o toml2json y.tab.c $(GLIB_LIBS)

run:	
	./toml2json

example: 
	./toml2json < "./Files_toml/example.toml" >example.json

array: 
	./toml2json < "./Files_toml/array.toml" >array.json

keys: 
	./toml2json < "./Files_toml/keys.toml" >keys.json

tables:
	./toml2json < "./Files_toml/tables.toml" >tables.json

values:
	./toml2json < "./Files_toml/values.toml" >values.json

arraytables:
	./toml2json < "./Files_toml/arraytables.toml" >arraytables.json

ficheiro:
	./toml2json < "./Files_toml/ficheiro.toml" >ficheiro.json	

clean:
	rm -f lex.yy.c y.tab.c y.tab.h y.output toml2json *.dot *.json