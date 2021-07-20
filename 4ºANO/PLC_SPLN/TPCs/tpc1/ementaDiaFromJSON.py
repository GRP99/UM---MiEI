# AUTHOR : Gonçalo Pinto - A83732
#!/usr/bin/python3
from jjcli import *
import json, sys

dia = ""
sysdia = sys.argv[1]
if(int(sysdia) >= 0 and int(sysdia)<=4):
    dia = str(int(sysdia) + 2) + "F"
else:
    print("Dia Inválido ! Opções: 0(2F) a 4(6F)!")
    sys.exit()

refeicao = ""
sysrefeicao = sys.argv[2]
if(sysrefeicao == "A"):
    refeicao="Almoço"
elif(sysrefeicao == "J"):
    refeicao="Jantar"
else:
    print("Refeição Inválida ! Opções: A(Almoço) ou J(Jantar)!")
    sys.exit()   

c = clfilter()

with open('ementa.json') as json_file:
    json_data = json.load(json_file)
    #print(json_data)
    #print(json_data[dia])
    #print(json_data[dia][refeicao])
    
print(">>> DIA",dia,": <<<")
print("    > ",refeicao," <")
print("        - Sopa : ",json_data[dia][refeicao]["Sopa"])
print("        - Prato : ",json_data[dia][refeicao]["Prato"])
print("        - Acompanhamento 1 : ",json_data[dia][refeicao]["Acompanhamento 1"])
print("        - Acompanhamento 2 : ",json_data[dia][refeicao]["Acompanhamento 2"])