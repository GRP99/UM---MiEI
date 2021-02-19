CC:= gcc
CFLAGS:= -ansi -O2
TARGET:= SGV

all: $(TARGET)
	 rm *.o

$(TARGET): SGV.o avl_base.o avl_facturacao.o avl_filial.o interface.o leitura.o navega.o queries.o teste.o
	$(CC) $(CFLAGS) $^ -o $@ 

SGV.o: SGV.c 
	$(CC) $(CFLAGS) -c $^

avl_base.o: avl_base.c  
	$(CC) $(CFLAGS) -c $^

avl_facturacao.o: avl_facturacao.c  
	$(CC) $(CFLAGS) -c $^ 

avl_filial.o: avl_filial.c
	$(CC) $(CFLAGS) -c $^

interface.o: interface.c
	$(CC) $(CFLAGS) -c $^

leitura.o: leitura.c
	$(CC) $(CFLAGS) -c $^

navega.o: navega.c
	$(CC) $(CFLAGS) -c $^

queries.o: queries.c
	$(CC) $(CFLAGS) -c $^ 
    
teste.o: teste.c
	$(CC) $(CFLAGS) -c $^

clean: 
	@$(RM) *.o
