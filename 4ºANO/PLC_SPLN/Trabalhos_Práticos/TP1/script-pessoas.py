#!/usr/bin/python3
import json
import yaml
import requests as reqs
from jjcli import *
from bs4 import BeautifulSoup

c = clfilter(opt="do:")


def extrai_pessoa(id_page: int) -> dict:
    dict_pessoa = {"_id": id_page}
    dict_nascimento = {}
    dict_falecimento = {}
    dict_casamentos = []
    dict_pais = []
    list_filhos = []
    dict_filhos_por_casamento = {}

    link = 'http://pagfam.geneall.net/3418/pessoas.php?id=' + str(id_page)

    resp = reqs.get(link)

    resp.raise_for_status()

    soup = BeautifulSoup(resp.text, 'html.parser')
    pg = str(soup)

    tags = ['Pais', 'Casamentos', 'Filhos', 'Notas']

    p1, *sec_cont = split(r'<div\s+class="marcadorP".*?>(.*?)</div>', pg)

    info = split(r'<div align=\"center\">[^\n]', p1)
    info = split(r'</?nobr>', info[1])
    info = info[:-1]
    for i in info:
        if '*' in i:
            dict_nascimento["local"] = i.replace('*', '').strip()
            dict_nascimento["data"] = info[info.index(i) + 1].strip()
        if '+' in i:
            dict_falecimento["local"] = i.replace('+', '').strip()
            dict_falecimento["data"] = info[info.index(i) + 1].strip()

    dict_pessoa["Nascimento"] = dict_nascimento
    dict_pessoa["Falecimento"] = dict_falecimento

    nome = findall(r'<title>([\w \. \-]+)</title>', p1)
    dict_pessoa["Nome"] = nome[0]

    last_tag = ''
    tit = ''
    for sec in sec_cont:
        if sec in tags:
            tit = sec
            last_tag = ''
            dict_pessoa[tit] = []
        else:
            familiares = findall(
                r'((<div.*?class="txt2".*?>\n)?((<div|<ul|<DIV).*?>)\s*(<b>(.*?)</b>)?\s*([^\<]+)?\s*(<nobr>(.*?)</nobr>)?.*?)?<a\s+href=(.*?id=(\d+)"?)>(.*?)</a>',
                sec)

            if tit == 'Notas':
                dict_notas = findall(r'<li>([^<][^/]+)</li>', sec)
                dict_pessoa["Notas"] = dict_notas

            for ignore, yet_Another_ignore, ignore_div, ignore_div, ignore_tag, tag, localC, ignore_data, dataC, url, find_id, nome in familiares:
                if tag == '':
                    if last_tag != '':
                        tag = last_tag
                else:
                    last_tag = tag

                if "<a" in tag:
                    _id = findall(r'id=(\d+)', tag)
                    tag = re.split('</?a.*?>', tag)
                    tag = tag[0] + tag[1] + " (" + _id[0] + ') :'

                if tit == 'Casamentos':
                    if tag == '':
                        temp = {"idParceiro": find_id, "parceiro": nome.strip(), "local": localC.strip(), "data": dataC.strip()}
                        dict_casamentos.append(temp)
                    else:
                        temp = {"idParceiro": find_id, "parceiro": nome.strip(), "local": localC.strip(), "data": dataC.strip()}
                        dict_por_casamentos = {tag.replace(':', ''): temp}
                        dict_casamentos.append(dict_por_casamentos)

                else:
                    if tag == '':
                        if tit == "Pais":
                            temp = {"id": find_id, "nome": nome.strip()}
                            dict_pais.append(temp)

                        elif tit == "Filhos":
                            dict_filho = {"id": find_id, "nome": nome.strip()}
                            list_filhos.append(dict_filho)
                    else:
                        if tit == "Pais":
                            temp = {"id": find_id, "nome": nome.strip()}
                            dict_pais_tag = {tag.replace(':', ''): temp}
                            dict_pais.append(dict_pais_tag)

                        elif tit == "Filhos":
                            dict_filho = {"id": find_id, "nome": nome.strip()}

                            if tag.replace(':', '') not in dict_filhos_por_casamento.keys():
                                dict_filhos_por_casamento[tag.replace(':', '')] = [dict_filho]
                            else:
                                dict_filhos_por_casamento[tag.replace(':', '')].append(dict_filho)

    dict_pessoa["Casamentos"] = dict_casamentos

    for k, v in dict_filhos_por_casamento.items():
        temp = {k: v}
        list_filhos.append(temp)

    dict_pessoa["Filhos"] = list_filhos
    dict_pessoa["Pais"] = dict_pais
    dict_pessoa["Documentos"] = {}
    return dict_pessoa


def get_ids() -> list:
    list_hyperlinks = []

    for page in range(0, 150, 30):
        url = 'http://pagfam.geneall.net/3418/pessoas_search.php?start=' + str(page) + '&orderby=&sort=&idx=0&search='

        resp = reqs.get(url)

        resp.raise_for_status()

        soup = BeautifulSoup(resp.text, 'html.parser')

        list_hyperlinks.append(soup.find_all('a')[3:len(soup.find_all('a')) - 4])

    list_ids = []

    for sublist in list_hyperlinks:
        for hyperlink in sublist:
            list_ids.append(findall(r'\?id=[0-9]+\s*\">', str(hyperlink))[0][4:11])

    return list_ids


def main() -> ():
    list_ids = get_ids()

    if len(list_ids) > 0:
        dict_family = {}
        list_family = []

        for id_page in list_ids:
            person = extrai_pessoa(id_page)
            list_family.append(person)

        dict_family["Pessoas"] = list_family

        if len(sys.argv) > 1:
            if sys.argv[1] == 'yaml':
                print(yaml.dump(dict_family, allow_unicode=True, default_flow_style=False))
            else:
                print('TRY AGAIN!')
        else:
            print(json.dumps(dict_family, ensure_ascii=False, indent=4, sort_keys=True))



main()
