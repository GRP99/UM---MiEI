#!/usr/bin/python3
from jjcli import *

c = clfilter()

txt = qx("pdftotext ementa.pdf -")                                          # Execute command and save

txt = sub(r'(.|\n)*?Sopa', r'===Sopa', txt, count=1)                        # apagar até "Sopa"
txt = sub(r'\n1 (.|\n)*', r'===Fim', txt, count=1)                          # apagar a partir de 1.
txt = sub(r'\n(Acompanhamento [12]|Prato|Sopa)\n', r'===\1\n', txt)         # delimitar "Acompanhamento","Prato" e "Sopa"
#print(txt)


lista = split(r'===', txt)
                                        # ig1, *lista, igu=split(...)
                                        # lista = lista[1:-1]
lista.pop()                             # tirar ultimo "FIM"
lista.pop(0)                            # tirar primeiro "" inicial
#print(lista)


menugeral = []
for g in lista:
    tit, *items = split(r'\n\n+', g)    # coloca o primeiro membro da lista em tit e o resto em items construindo um tuplo 
    menugeral.append((tit, items))
#print(menugeral)