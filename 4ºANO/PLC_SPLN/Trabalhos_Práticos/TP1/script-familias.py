import json
from jjcli import *
import requests as reqs
from bs4 import BeautifulSoup


def extrai_individuos_familia(list_familias: list) -> list:
    nova_list_familias = []

    for familia in list_familias:
        list_individuos = []

        for linha in BeautifulSoup(reqs.get('http://pagfam.geneall.net/3418/fam_names.php?id=' + familia["id"]).text, 'html.parser').find_all('td', {'class': 'txt2'}):
            if len(linha.find_all('a')) > 0:
                for individuo in linha.find_all('a', href=True):
                    individuo = {"id": findall(r'\?id=[0-9]+', str(individuo))[0][4:], "nome": individuo.text}
                    list_individuos.append(individuo)

        familia["individuos"] = list_individuos
        nova_list_familias.append(familia)

    return nova_list_familias


def descobre_familias() -> list:
    list_familias = []

    for hyperlink in BeautifulSoup(reqs.get('http://pagfam.geneall.net/3418/familias_search.php').text, 'html.parser').find_all('a')[3:]:
        list_familias.append({"id": findall(r'\?id=[0-9]+\s*\">', str(hyperlink))[0][4:8], "nome": hyperlink.text})

    return list_familias


def main() -> ():
    dict_familias = {"Familias": extrai_individuos_familia(descobre_familias())}
    print(json.dumps(dict_familias, ensure_ascii=False, indent=4, sort_keys=True))


main()
