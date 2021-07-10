#!/usr/bin/python3
from random import choice
import sys
import re           # expressões regulares - algumas funções utilizadas split, findall, search

# Função invocada pela gramática
def num():
    return str(choice(range(1, 13)))

# Selecciona uma linha do ficheiro input
def umalinha(file: str):
    with open(file) as f:
        linhas = f.readlines()

    return(choice(linhas).strip())

# Constrói uma frase a partir da gramática fornecida, recursivamente
def gera(gra: dict, simb: str) -> str:
    # seleccionar uma linha do ficheiro
    if simb[0] == '!':
        return (umalinha(simb[1:]) + ' ')

    # invocar uma função
    if simb[0] == '$':
        if simb[1:] not in globals():
            print('### ERRO: Função não está definida! ###')
        else:
            return globals()[simb[1:]]()

    # processar os símbolos terminais
    if simb not in gra:
        return (simb.strip('"') + ' ')

    # processar os símbolos não terminais
    rhss = gra[simb]
    rhs = choice(rhss)

    ret = ''

    for si in rhs:
        ret += gera(gra, si)

    return ret

# Processa uma gramática a partir de um ficheiro
def parser(ficheiro: str) -> (dict, str):
    g = {}
    axioma = None

    with open(ficheiro) as f:
        txt = f.read().strip()

    ntdefs = re.split(r'\n{2,}', txt)

    for ntdef in ntdefs:
        nt, rhss = re.split(r'\s*:\s*', ntdef)

        if not axioma:
            axioma = nt

        g[nt] = []

        prods = re.split(r'\s*\|\s*', rhss)

        for prod in prods:
            rhs = re.split(r'\s+', prod)

            g[nt].append(rhs)

    return (g, axioma)

def main():
    # processar a gramática
    g, axioma = parser(sys.argv[1])

    # construir uma frase exemplo
    print(gera(g, axioma))

main()
