#!/usr/bin/python3.7
# https://viva-porto.pt/alguns-dos-melhores-proverbios-portugueses/ && https://web.fe.up.pt/~fsilva/port/proverbios.htm
from random import choice

'''
s : "Como se costuma dizer..." !prov.txt
  | !prov.txt
  ;

Exemplo: Como se costuma dizer... Mais vale tarde do que nunca.
'''

g = {
    's': [['!prov.txt'], ['Como se costuma dizer...', '!prov.txt']]
}


def umalinha(file: str):
    with open(file) as f:
        linhas = f.readlines()
        
    return(choice(linhas).strip())


def gera(gra: dict, simb: str) -> str:
    # IF terminal Then retorna o simb.
    if simb[0] == '!':
        return (umalinha(simb[1:]) + ' ')

    if simb not in gra:
        return (simb + ' ')

    rhss = gra[simb]
    rhs = choice(rhss)

    ret = ''

    for si in rhs:
        ret += gera(gra, si)

    return ret
    # return str.join(' ', [gera(gra, si) for si in choice(gra[simb])])


print(gera(g, 's'))
