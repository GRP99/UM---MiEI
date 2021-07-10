#!/usr/bin/python3

import json
from jjcli import *
c=clfilter(opt="do:")

for pg in c.slurp(): # process one striped text at time

  dic = {}

  # Versão Inicial:
    # nome = findall(r'<title>([\w \-]+)</title>', pg) # Buscar o nome que está dentro da tag <title>
    # print("NOME: ",*nome) # Para não imprimir em forma de lista

    # familiares = findall(r'<a\s+href=(.*?id=(\d+)\"?)>(.*?)</a>',pg) # Aqui são capturados 3 grupos
    # print("FAMILIARES: ")
    # for url,_id,nome in familiares:
      # print(f'\t==> {_id} → {nome.strip()}')

  dicJSON = {}
  nascimentoJSON={}
  falecimentoJSON={}
  paisJSON=[]
  casamentosJSON=[]
  filhosJSON=[]

  # Dicts Temps para guardar informação
  dict_filhos_por_casamento = {}

  # Verificação de tags
  tags = ['Filhos','Casamentos','Pais','Notas']

  # p1 vai ser igual a tudo até ao primeiro <div class="marcadorP">.
  p1, *sec_cont =split(r'<div\s+class="marcadorP".*?>(.*?)</div>',pg)
  # O split como tem parênteses, captura não só split mas como a ER definida entre parênteses.

  info = split(r'<div align=\"center\">[^\n]',p1)
  # print(info[1])
  info = split(r'</?nobr>',info[1])
  # ignore last </div>
  info = info[:-1]

  # Tratamento das datas
  for i in info:
    if '*' in i:
      s = "Nascimento:" + i.replace('*','') + "- " + info[info.index(i)+1]
      nascimentoJSON["local"] = i.replace('*','').strip()
      nascimentoJSON["dia"] = info[info.index(i)+1].strip()
      # print(s.replace(r'\s+','\s'))
    if '+' in i:
      s = "Falecimento:" + i.replace('+','') + "- " + info[info.index(i)+1]
      falecimentoJSON["local"] = i.replace('+','').strip()
      falecimentoJSON["dia"] = info[info.index(i)+1].strip()
      # print(s.replace(r'\s+','\s'))

  dicJSON["Nascimento"]  = nascimentoJSON
  dicJSON["Falecimento"] = falecimentoJSON

  nome = findall(r'<title>([\w \-]+)</title>', p1)

  dicJSON["nome"] = nome[0]
  #dicJSON["id"] = id

  lastTag = ''
  lastTitle = ''

  for sec in sec_cont:
    if sec in tags:
      tit=sec
      lastTag = ''
    else:
      # tag = findall(r'<b>(.*?)</b>', sec)
      familiares = findall(r'(<b>(.*?)</b>\s*([^\<]+)?\s*(<nobr>(.*?)</nobr>)?.*?)?<a\s+href=(.*?id=(\d+)"?)>(.*?)</a>', sec)

      if tit == "Notas":
          notas = findall(r'<li>([^<][^/]+)</li>',sec)
          dicJSON["Notas"] = notas

      for ignore,tag,localC,ignoreData,dataC,url,id,nome in familiares:
        # o ignore vai ser ignorado, tal como o ignoreData.

        if tag == '':
          if lastTag != '':
            tag = lastTag
        else:
          lastTag = tag

        if "<a" in tag:
          _id = findall(r'id=(\d+)',tag)
          tag = re.split('</?a.*?>', tag)
          tag = tag[0] + tag[1] + " (" + _id[0] + ') :'

        if tit == 'Casamentos':
          if tag == '':
            temp = {"id_parceiro": id, "parceiro": nome.strip(), "local": localC.strip(), "data": dataC.strip()}
            casamentosJSON.append(temp)
          else:
            temp = {"id_parceiro": id, "parceiro": nome.strip(), "local": localC.strip(), "data": dataC.strip()}
            dict_por_casamentos = {}

            if tag.replace(':','') not in dict_por_casamentos.keys():
              dict_por_casamentos[tag.replace(':','')] = temp
            else:
              dict_por_casamentos[tag.replace(':','')].append(temp)
            casamentosJSON.append(dict_por_casamentos)

        else:
          if tag == '':
            if tit == "Pais":
              temp = {"id":id, "nome":nome.strip()}
              paisJSON.append(temp)
            elif tit == "Filhos":
              dict_filho = {"id": id, "nome": nome.strip()}
              filhosJSON.append(dict_filho)
          else:
            if tit == "Pais":
              temp = {"id":id, "nome":nome.strip()}
              dict_pais_tag = {}

              if tag.replace(':','') not in dict_pais_tag.keys():
                dict_pais_tag[tag.replace(':','')] = temp
              else:
                print("ERRO: Mais que um(a) ", tag.replace(':',''))
              paisJSON.append(dict_pais_tag)

            elif tit == "Filhos":
              dict_filho = {"id": id, "nome": nome.strip()}
              if tag.replace(':','') not in dict_filhos_por_casamento.keys():
                dict_filhos_por_casamento[tag.replace(':','')] = [dict_filho]
              else:
                dict_filhos_por_casamento[tag.replace(':','')].append(dict_filho)

# Casamentos
dicJSON["Casamentos"] = casamentosJSON

# Tratamento dos filhos
for k,v in dict_filhos_por_casamento.items():
  temp = {}
  temp[k] = v
  filhosJSON.append(temp)

dicJSON["Filhos"] = filhosJSON
dicJSON["Pais"] = paisJSON

# dicJSON["Pais"] = [k,v for (k,v) in dict_pais_tag.items()]
# for v in dict_pais_tag:
    # paisJSON[v] = [] if v not in paisJSON.keys()
    # paisJSON[v] = dict_pais_tag[v]
    # print(paisJSON)
    # temp = "'"+str(key)+"':"+str(value)
    # dicJSON["Pais"].append(dict(temp))
# paisJSON.append(dict_pais_tag) if paisJSON != [] else [dict_pais_tag]


# ==> Ir buscar todos!!!!
# ===> import requests -→ pg = reguests.get(URL)
#print(dicJSON)
print(json.dumps(dicJSON, indent=4, ensure_ascii=False, sort_keys=False))
# \w = [a-zA-Z0-9_áéíó...]
# (...) -→ captura
# *?
