import requests as reqs
import yaml
from bs4 import BeautifulSoup
from jjcli import *

# Idealmente seria imprimir os vários indivíduos, seus pais e seus casamentos a partir de um determinado idenficador mas obteve-se sempre RecursionError: maximum recursion depth exceeded in comparison

c = clfilter(opt="do:")


def constroi_info(id_pessoa: str, lista_pessoas: list, lista_info_pessoa: list) -> list:
    for pessoa in lista_pessoas:
        if pessoa["_id"] == id_pessoa:
            dict_info_pessoa = {"nome": pessoa["Nome"]}

            if len(pessoa["Nascimento"]) > 0:
                dict_info_pessoa["nasc"] = pessoa["Nascimento"]["data"]

            if len(pessoa["Falecimento"]) > 0:
                dict_info_pessoa["fale"] = pessoa["Falecimento"]["data"]

            if len(pessoa["Pais"]) > 0:
                if (not "Fp" + pessoa["Pais"][0]["Pai"]["id"] + pessoa["Pais"][1]["Mãe"]["id"] in set().union(
                        *lista_info_pessoa)) and (
                        not "Fp" + pessoa["Pais"][1]["Mãe"]["id"] + pessoa["Pais"][0]["Pai"]["id"] in set().union(
                            *lista_info_pessoa)):
                    lista_info_pessoa.append({"Fp" + pessoa["Pais"][0]["Pai"]["id"] + pessoa["Pais"][1]["Mãe"]["id"]: {
                        "pai": "I" + pessoa["Pais"][0]["Pai"]["id"],
                        "mae": "I" + pessoa["Pais"][1]["Mãe"]["id"],
                        "filhos": ["I" + pessoa["_id"]]}})

                    dict_info_pessoa["pais"] = "Fp" + pessoa["Pais"][0]["Pai"]["id"] + pessoa["Pais"][1]["Mãe"]["id"]

                else:
                    dict_info_pessoa["pais"] = "Fp" + pessoa["Pais"][1]["Mãe"]["id"] + pessoa["Pais"][0]["Pai"]["id"]
                    for info in lista_info_pessoa:
                        if "Fp" + pessoa["Pais"][1]["Mãe"]["id"] + pessoa["Pais"][0]["Pai"]["id"] in info.keys():
                            info["Fp" + pessoa["Pais"][1]["Mãe"]["id"] + pessoa["Pais"][0]["Pai"]["id"]][
                                "filhos"].append("I" + pessoa["_id"])
                        elif "Fp" + pessoa["Pais"][0]["Pai"]["id"] + pessoa["Pais"][1]["Mãe"]["id"] in info.keys():
                            info["Fp" + pessoa["Pais"][0]["Pai"]["id"] + pessoa["Pais"][1]["Mãe"]["id"]][
                                "filhos"].append("I" + pessoa["_id"])

                lista_info_pessoa = constroi_info(pessoa["Pais"][0]["Pai"]["id"], lista_pessoas, lista_info_pessoa)
                lista_info_pessoa = constroi_info(pessoa["Pais"][1]["Mãe"]["id"], lista_pessoas, lista_info_pessoa)

            if len(pessoa["Casamentos"]) > 0:
                list_casa = []

                if len(pessoa["Casamentos"]) > 1:
                    ncasamento = "Casamento I"
                    i = 0
                    for casamento in pessoa["Casamentos"]:
                        dict_casamento = {"parceiro(a)1": "I" + pessoa["_id"],
                                          "parceiro(a)2": "I" + casamento[ncasamento]["idParceiro"]}

                        if len(pessoa["Filhos"]) > 0:
                            list_filhos = []
                            for filho in pessoa["Filhos"][i]["Filhos do " + ncasamento]:
                                list_filhos.append("I" + filho["id"])
                                lista_info_pessoa = constroi_info(filho["id"], lista_pessoas, lista_info_pessoa)

                            dict_casamento["filhos"] = list_filhos

                        if (not "F" + pessoa["_id"] + casamento[ncasamento]["idParceiro"] in set().union(
                                *lista_info_pessoa)) and (
                                not "F" + casamento[ncasamento]["idParceiro"] + pessoa["_id"] in set().union(
                                    *lista_info_pessoa)):
                            lista_info_pessoa.append(
                                {"F" + pessoa["_id"] + casamento[ncasamento]["idParceiro"]: dict_casamento})
                            list_casa.append("F" + pessoa["_id"] + casamento[ncasamento]["idParceiro"])
                        else:
                            list_casa.append("F" + casamento[ncasamento]["idParceiro"] + pessoa["_id"])

                        lista_info_pessoa = constroi_info(casamento[ncasamento]["idParceiro"], lista_pessoas,
                                                          lista_info_pessoa)

                        ncasamento = "Casamento II"
                        i = 1

                    dict_info_pessoa["casa"] = list_casa

                else:
                    dict_casamento = {"parceiro(a)1": "I" + pessoa["_id"],
                                      "parceiro(a)2": "I" + pessoa["Casamentos"][0]["idParceiro"]}

                    if len(pessoa["Filhos"]) > 0:
                        list_filhos = []
                        for filho in pessoa["Filhos"]:
                            list_filhos.append("I" + filho["id"])
                            lista_info_pessoa = constroi_info(filho["id"], lista_pessoas, lista_info_pessoa)

                        dict_casamento["filhos"] = list_filhos

                    if (not "F" + pessoa["_id"] + pessoa["Casamentos"][0]["idParceiro"] in set().union(
                            *lista_info_pessoa)) and (
                            not "F" + pessoa["Casamentos"][0]["idParceiro"] + pessoa["_id"] in set().union(
                                *lista_info_pessoa)):
                        lista_info_pessoa.append(
                            {"F" + pessoa["_id"] + pessoa["Casamentos"][0]["idParceiro"]: dict_casamento})
                        dict_info_pessoa["casa"] = "F" + pessoa["_id"] + pessoa["Casamentos"][0]["idParceiro"]
                    else:
                        dict_info_pessoa["casa"] = "F" + pessoa["Casamentos"][0]["idParceiro"] + pessoa["_id"]

                    lista_info_pessoa = constroi_info(pessoa["Casamentos"][0]["idParceiro"], lista_pessoas,
                                                      lista_info_pessoa)

            if not "I" + pessoa["_id"] in set().union(*lista_info_pessoa):
                lista_info_pessoa.append({"I" + pessoa["_id"]: dict_info_pessoa})

    return lista_info_pessoa


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
                        temp = {"idParceiro": find_id, "parceiro": nome.strip(), "local": localC.strip(),
                                "data": dataC.strip()}
                        dict_casamentos.append(temp)
                    else:
                        temp = {"idParceiro": find_id, "parceiro": nome.strip(), "local": localC.strip(),
                                "data": dataC.strip()}
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
        list_pessoas = []

        for id_page in list_ids:
            pessoa = extrai_pessoa(id_page)
            list_pessoas.append(pessoa)

        if len(sys.argv) > 1:
            print(yaml.dump(constroi_info(sys.argv[1], list_pessoas, []), allow_unicode=True, default_flow_style=False))
        else:
            print('Apenas funciona com um identificador!')


main()
