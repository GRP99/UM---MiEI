# AUTHOR : Gonçalo Pinto - A83732
#!/usr/bin/python3

# 2F = 0 ; 3F = 1 ; 4F = 2 ; 5F = 3 ; 6F = 4 ;

from jjcli import *
import sys

sysdia = sys.argv[1] 
if(int(sysdia) >= 0 and int(sysdia)<=4):
    dia = int(sysdia)
else:
    print("Dia Inválido ! Opções: 0(2F) a 4(6F)!")
    sys.exit()

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
#print(menugeral[int(dia)])


print(">>> DIA",dia+2,"F: <<<")
print("    > Almoço <")
print("        - Sopa : ",menugeral[0][1][int(dia)].strip().replace('\n',' '))
print("        - Prato : ",menugeral[1][1][int(dia)].strip().replace('\n',' '))
print("        - Acompanhamento 1 : ",menugeral[2][1][int(dia)].strip().replace('\n',' '))
print("        - Acompanhamento 2 : ",menugeral[3][1][int(dia)].strip().replace('\n',' '))
print("    > Jantar <")
print("        - Sopa : ",menugeral[4][1][int(dia)].strip().replace('\n',' '))
print("        - Prato : ",menugeral[5][1][int(dia)].strip().replace('\n',' '))
print("        - Acompanhamento 1 : ",menugeral[6][1][int(dia)].strip().replace('\n',' '))
print("        - Acompanhamento 2 : ",menugeral[7][1][int(dia)].strip().replace('\n',' '))