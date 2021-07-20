#!/usr/bin/python3
# from jjcli import *           ### Sem jjcli, leva a necessidade de utilizar operating system (os) e system (sys)
import random, os, sys

disc = sys.argv[1]              ## conhecer o ficheiro de alunos a ler
file = os.environ["HOME"] + "/Documentos/4ANO/PLC/SPLN2021/aula2/" + disc   ## construir o path para aceder ao ficheiro

# alunos = open(file).readlines()           ## Abre e le o ficheiro mas existe a necessidade de fechar

with open(file) as f:                       ## Abre e fecha o ficheiro
    alunos = f.readlines()                  ## f.readline(), read(), ... -> Funcoes para leitura

print(random.choice(alunos).strip())        ## strip remove \s (esq, dir)