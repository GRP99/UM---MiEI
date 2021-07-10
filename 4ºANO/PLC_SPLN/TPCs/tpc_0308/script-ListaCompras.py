#!/usr/bin/python3.7
from random import choice

'''
compras : "INICIO" lista "FIM"
        ;

lista : elem				
	  | elem "," cauda
	  ;

cauda : lista
      ;

elem : "(" !compras.txt "-" $geraqtd ")"
     ;

Exemplo : INICIO ( Atum - 1 ) FIM .    
'''

g = {'compras': [['INICIO', 'lista', 'FIM']],
     'lista': [['elem'], ['elem', ',', 'cauda']],
     'cauda': [['lista']],
     'elem': [['(', '!compras.txt', '-', '$geraqtd', ')']],
     }


def umalinha(file: str):
    with open(file) as f:
        linhas = f.readlines()

    return(choice(linhas).strip())


def geraqtd():
    return str(choice(range(1,13)))


def gera(gra: dict, simb: str) -> str:
    # IF terminal Then retorna o simb.
    if simb[0] == '!':
        return (umalinha(simb[1:]) + ' ')

    if simb[0] == '$':
        return globals()[simb[1:]]()

    if simb not in gra:
        return (simb + ' ')

    rhss = gra[simb]
    rhs = choice(rhss)

    ret = ''

    for si in rhs:
        ret += gera(gra, si)

    return ret


print(gera(g, 'compras'))