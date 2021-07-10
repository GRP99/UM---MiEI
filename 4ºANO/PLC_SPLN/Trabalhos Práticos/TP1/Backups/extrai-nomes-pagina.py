#!/usr/bin/python3

from jjcli import *

c = clfilter(opt="do:")

for pg in c.slurp():  # process one striped text at time
    dic = {}
    tit = ''

    tags = ['Filhos', 'Casamentos', 'Pais', 'Notas']

    # p1 vai ser igual a tudo ate ao primeiro <div class="marcadorP">
    p1, *sec_cont = split(r'<div\s+class="marcadorP".*?>(.*?)</div>', pg)
    # o split como tem parenteses, captura não só split mas como a ER definida entre parenteses

    nome = findall(r'<title>([\w \-]+)</title>', p1)
    print("NOME:\n\t", *nome)

    for sec in sec_cont:
        if sec in tags:
            tit = sec
            dic[tit] = []
        else:
            familiares = findall(r'(<b>(.*?)</b>.*?)?<a\s+href=(.*?id=(\d+)"?)>(.*?)</a>', sec)

            for ignore, tag, url, find_id, nome in familiares:
                # variavel ignore vai ser descartada
                w = f"\t{tag} {nome.strip()} ({find_id})"
                dic[tit].append(w)

    for t in dic:
        print(f"{t.upper()}:")

        for i in dic[t]:
            print(f"{i}")
