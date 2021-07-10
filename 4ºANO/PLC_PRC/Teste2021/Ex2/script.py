import json
import urllib.parse
import requests as reqs

prefixes = '''
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        PREFIX owl: <http://www.w3.org/2002/07/owl#>
        PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
        PREFIX noInferences: <http://www.ontotext.com/explicit>
        PREFIX skos: <http://www.w3.org/2004/02/skos/core#>
        PREFIX : <http://prc.di.uminho.pt/2021/myfamily#>
    '''

def constroi_ligacoes():
    getLink = "http://localhost:7200/repositories/family?query="
    postLink = "http://localhost:7200/repositories/family/statements?update="

    query = 'CONSTRUCT { ?a :bisavo ?b . } WHERE { ?a :ePaide ?p1 . ?p1 :ePaide ?p2 . ?p2 :eProgenitorDe ?b . FILTER ( ?a != ?b && ?a != ?p1 && ?p1 != ?p2 && ?p2 != ?b ) }'

    encoded = urllib.parse.quote(prefixes + query)

    resp = reqs.get(getLink + encoded)

    resp.raise_for_status()

    line_count = 0
    for line in resp.text.split('.\n'):
        line_split = line.split(' ')
        querypost = 'INSERT DATA { :' + line_split[0].split('#')[1][:-1] + ' :' + line_split[1].split('#')[1][:-1] + ' :'  + line_split[2].split('#')[1][:-1] + ' . }'
        encodedpost = urllib.parse.quote(prefixes + querypost)
        resppost = reqs.post(postLink + encodedpost)
        resppost.raise_for_status()
        line_count += 1
        print('=== ' + str(line_count) + ' ===')


def main():
    constroi_ligacoes()


main()