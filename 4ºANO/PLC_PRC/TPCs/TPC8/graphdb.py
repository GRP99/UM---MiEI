import json
import urllib.parse
import requests as reqs
from bs4 import BeautifulSoup

prefixes = '''
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        PREFIX owl: <http://www.w3.org/2002/07/owl#>
        PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
        PREFIX noInferences: <http://www.ontotext.com/explicit>
        PREFIX skos: <http://www.w3.org/2004/02/skos/core#>
        PREFIX : <http://www.di.uminho.pt/prc2021/mapa-virtual#>
    '''


def consulta_cidades() -> ():
    getLink = "http://localhost:7200/repositories/mapa?query="

    query = 'SELECT ?s ?nome ?distrito WHERE { ?s a :Cidade . ?s :nome ?nome . ?s :distrito ?distrito . } ORDER BY ?s'

    encoded = urllib.parse.quote(prefixes + query)

    resp = reqs.get(getLink + encoded)

    resp.raise_for_status()

    soup = BeautifulSoup(resp.text, "lxml")

    list_cidades = []
    line_count = 0
    for line in soup.find_all('p')[0].text.replace('\r', '').split('\n'):
        line_split = line.split(',')
        if line_count != 0 and len(line_split) == 3:
            dict_cidade = {
                'id': line_split[0].split('#')[1],
                'nome': line_split[1],
                'distrito': line_split[2]
            }
            list_cidades.append(dict_cidade)
        line_count += 1

    print(list_cidades)


def constroi_ligacoes() -> ():
    getLink = "http://localhost:7200/repositories/mapa?query="
    postLink = "http://localhost:7200/repositories/mapa/statements?update="

    query = 'CONSTRUCT { ?c1 :temLigacao ?c2 . } WHERE { ?s a :Ligacao . ?s :origem ?c1. ?s :destino ?c2. } ORDER BY ?c1'

    encoded = urllib.parse.quote(prefixes + query)

    resp = reqs.get(getLink + encoded)

    resp.raise_for_status()

    line_count = 0
    for line in resp.text.split('.\n'):
        line_split = line.split(' ')
        if len(line_split) == 4:
            querypost = 'INSERT DATA { :' + line_split[0].split('#')[1][:-1] + ' :' + line_split[1].split('#')[1][:-1] + ' :'  + line_split[2].split('#')[1][:-1] + ' . }'
            encodedpost = urllib.parse.quote(prefixes + querypost)
            resppost = reqs.post(postLink + encodedpost)
            resppost.raise_for_status()
            line_count += 1
            print('=== ' + line_count + ' ===')


def main() -> ():
    # consulta_cidades()

    constroi_ligacoes()


main()
