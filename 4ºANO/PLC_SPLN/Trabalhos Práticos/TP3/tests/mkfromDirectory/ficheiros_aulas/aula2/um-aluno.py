#!/usr/bin/python3
from jjcli import * ### re + getopt + fileinputs + sys + os + subprocess
import random

c = clfilter()

aluno = []

for line in c.input():
    aluno.append(line)          ## Adicionar a lista de alunos

print(random.choice(aluno))