# AUTHOR : Gonçalo Pinto - A83732
#!/usr/bin/python3
from jjcli import *
import json

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

#json_menugeral = json.dumps(menugeral, ensure_ascii=False, sort_keys=True)
#print(json_menugeral)

ementaJSON = {}
diaJSON = {}
almocoJSON = {}
jantarJSON = {}

for d in range(0, 5):
    almocoJSON["Sopa"] = menugeral[0][1][d].strip().replace('\n',' ')
    almocoJSON["Prato"] = menugeral[1][1][d].strip().replace('\n',' ')
    almocoJSON["Acompanhamento 1"] = menugeral[2][1][d].strip().replace('\n',' ')
    almocoJSON["Acompanhamento 2"] = menugeral[3][1][d].strip().replace('\n',' ')

    jantarJSON["Sopa"] = menugeral[4][1][d].strip().replace('\n',' ')
    jantarJSON["Prato"] = menugeral[5][1][d].strip().replace('\n',' ')
    jantarJSON["Acompanhamento 1"] = menugeral[6][1][d].strip().replace('\n',' ')
    jantarJSON["Acompanhamento 2"] = menugeral[7][1][d].strip().replace('\n',' ')

    diaJSON["Almoço"] = almocoJSON
    diaJSON["Jantar"] = jantarJSON

    ementaJSON[str(d + 2) + "F"] =  diaJSON

json_ementa = json.dumps(ementaJSON, ensure_ascii=False, sort_keys=True)
print(json_ementa)