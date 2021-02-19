
analyzer: $(FILEY) $(FILEL)
	yacc -d $(FILEY)
	flex $(FILEL)
	gcc -o analyzer y.tab.c


automato.pdf: $(FILEY)
	yacc -g $(FILEY) 
	dot -Tpdf y.dot > automato.pdf


clean:
	rm -f lex.yy.c y.tab.c y.tab.h analyzer *.dot