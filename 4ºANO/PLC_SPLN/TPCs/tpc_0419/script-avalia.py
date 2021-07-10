import re
import sys
import unidecode
from jjcli import *


def rate(solution: [(str, str)], names: [str], calc) -> float:
    tot = 0
    for n, e in solution:
        r = calc(e, names)
        if n in r:
            a = 1 / (r.index(n) + 1)          
            tot += a
            # print(f'{n} - {r} = {a}')
    return round((tot / len(solution) * 100), 2)


def create_re(email: str) -> list:
    list_er = []

    # NP
    list_er.append('(?i)^' + email + '\s')

    # 1 d cada N
    if len(email) > 3:
        list_er.append('(?i)^' + email[0] + '\w+\s' + email[1] + '\w+\s' +
                       email[2] + '\w+\s(de\s)?' + email[3:])

    # 1 d NP + AP
    if len(email) > 3:
        list_er.append('(?i)^' + email[0] + '.+' + email[1:])

    # 1 parte d NP
    list_er.append('(?i)^' + email + '\w')

    # AP
    list_er.append('(?i)' + email)

    # 1 d NP + 1 d AP
    if len(email) == 2:
        list_er.append('(?i)^' + email[0] + '.*' + email[1] + '[^\s]\w+')

    # 1 d NP + 1 d 2 NP + 1 d AP
    if len(email) == 3:
        list_er.append('(?i)^' + email[0] + '.*\s' + email[1] + '.*\s' +
                       email[2] + '\w+')

    # 1 d NP + 1 d N + 1 AP
    if len(email) == 3:
        list_er.append('(?i)^' + email[0] + '[\w|\s]+' + email[1] +
                       '[\w|\s\.]+' + email[2] + '\w+')

    return list_er


def find_name(email: str, names: [str]) -> [str]:
    results = []
    list_re = create_re(email)

    dict_match = {}
    for re in list_re:
        for name in names:
            name_aux = unidecode.unidecode(name)
            if search(re, name_aux):
                if name in dict_match.keys():
                    dict_match[name] = dict_match.get(name) + 1
                else:
                    dict_match[name] = 1

    if len(dict_match) > 0:
        key, value = max(dict_match.items(), key=lambda k: k[1])
        if value == 1:
            for n in dict_match:
                results.append(n)
        else:
            results.append(key)

    return results


def load_names_and_emails(filename: str) -> [(str, str)]:
    with open(filename, 'r', encoding='utf-8') as f:
        result = []
        for line in f:
            line = line.strip()
            if search(r'::', line):
                name, email = split(r'\s*::\s*', line)
                result.append((name, email))
        return result


def main() -> ():
    if len(sys.argv) == 2:
        try:
            solution = load_names_and_emails(sys.argv[1])

            names = [n for n, e in solution]

            precison = rate(solution, names, find_name)

            print(f'Precision of {precison} %')

        except FileNotFoundError:
            print('Ficheiro de nomes não encontrado!')

        except Exception as e:
            print("Ocorreu um erro ... ")
            raise e

    else:
        print("Comando errado. Sintaxe do comando: > script.py $ficheiro")


main()