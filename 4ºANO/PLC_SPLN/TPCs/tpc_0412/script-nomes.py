import re
import sys


def criarer(email: str) -> list:
    list_er = []

    # NP
    list_er.append('(?i)^' + email + '\s')

    # 1 d cada N
    if len(email) > 3:
        list_er.append('(?i)^' + email[0] + '\w+\s' + email[1] + '\w+\s' + email[2] + '\w+\s(de\s)?' + email[3:])

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
        list_er.append('(?i)^' + email[0] + '.*\s' + email[1] + '.*\s' + email[2] + '\w+')

    # 1 d NP + 1 d N + 1 AP
    if len(email) == 3:
        list_er.append('(?i)^' + email[0] + '[\w|\s]+' + email[1] + '[\w|\s\.]+' + email[2] + '\w+')

    return list_er


def main() -> ():
    if len(sys.argv) == 3:
        try:
            dict_nomes = {}
            with open(sys.argv[2]) as ficheiro:
                for nome in ficheiro.readlines():
                    dict_nomes[nome.strip().replace('.', '').lower()] = nome.strip()

            if len(dict_nomes) > 0:
                dict_corresp = {}
                list_ER = criarer(sys.argv[1])
                for er in list_ER:
                    for nome in dict_nomes.keys():
                        if re.search(er, nome):
                            if nome in dict_corresp.keys():
                                dict_corresp[nome] = dict_corresp.get(nome) + 1
                            else:
                                dict_corresp[nome] = 1

                if len(dict_corresp) > 0:
                    key, value = max(dict_corresp.items(), key=lambda k: k[1])
                    perc = round((value / sum(dict_corresp.values()) * 100), 2)

                    print('>>> Para o email ' + sys.argv[1] + ' o nome mais provável será ' + dict_nomes.get(key) + ' com a probabilidade de ' + str(perc) + ' %')
                else:
                    print('>>> Não foi possível encontrar nenhum nome para o email ' + sys.argv[1] + ' que forneceu. Tente com outro!')

        except FileNotFoundError:
            print('Ficheiro de nomes não encontrado!')

        except Exception as e:
            print("Ocorreu um erro ... " + str(e))

    else:
        print("Comando errado. Sintaxe do comando: > script.py $email $ficheiro")


main()