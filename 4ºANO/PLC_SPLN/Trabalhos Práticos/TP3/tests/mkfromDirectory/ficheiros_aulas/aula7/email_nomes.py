from jjcli import *
import re
import unidecode

def load_names(filename: str) -> [str]:
    pass

def load_names_and_emails(filename: str) -> [(str, str)]:
    with open(filename, 'r', encoding='utf-8') as f:
        #nomes_file = unidecode.unidecode(f.read())
        result = []
        for linha in f:
            linha = linha.strip()
            if search(r'::', linha):
                nome,email = split(r'\s*::\s*', linha)
                result.append((nome,email))
        return result
            


def avalia1(solucao: [(str,str)], calc) -> float:
    nomes = [x for x,y in solucao]
    soma = 0
    for n,e in solucao:
        r = calc(e, nomes)
        if n in r:
            i = r.index(n)
            a = 1/(i+1)
            soma += a
    return soma / len(solucao)

def calcular_nome1(email: str, nomes: [str]) -> [str]:
    stripped_email = sub("[^a-zA-Z]+", "", email)

    re_e_final = sub(r"(\w)", r"\1(.* |)", stripped_email)
    #print(re_e_final)
    resultados_email = []
    for nome in nomes:
        nome_aux = unidecode.unidecode(nome)
        if re.search(rf'{re_e_final}', nome_aux, flags=re.IGNORECASE):
            resultados_email.append(nome)
    return resultados_email


def calcular_nome2(email: str, nomes: [str]) -> [(str,float)]:
    pass
def calcular_nome3(email: str, nomes: [str]) -> str:
    pass

def main():
    s = load_names_and_emails('nomes.txt')
    nomes = [x for x,y in s]
    #print(s)

    e = calcular_nome1('jno', nomes)
    #print(e)

    a = avalia1( s , calcular_nome1)
    print(a)

main()
