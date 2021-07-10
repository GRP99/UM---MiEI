import json

with open('individuos.txt', mode='w', encoding='utf8') as individuos:
    with open('cidades.json', mode='r', encoding='utf8') as cidades:
        data = json.load(cidades)

        for c in data['cidades']:
            individuos.write('###  http://www.di.uminho.pt/prc2021/cidades#' + c['id'] + '\n')
            individuos.write(':' + c['id'] + ' rdf:type owl:NamedIndividual ,\n')
            individuos.write('            :Cidade ;\n')
            individuos.write('    :nome "' + c['nome'] + '" ;\n')
            individuos.write('    :populacao ' + c['população'] + ' ;\n')
            individuos.write('    :descricao "' + c['descrição'] + '" ;\n')
            individuos.write('    :distrito "' + c['distrito'] + '" .\n\n\n')
            
        for l in data['ligações']:
            individuos.write('###  http://www.di.uminho.pt/prc2021/cidades#' + l['id'] + '\n')
            individuos.write(':' + l['id'] + ' rdf:type owl:NamedIndividual ,\n')
            individuos.write('            :Ligacao ;\n')
            individuos.write('    :temOrigem :' + l['origem'] + ' ;\n')
            individuos.write('    :temDestino :' + l['destino'] + ' ;\n')
            individuos.write('    :distancia ' + str(l['distância']) + ' .\n\n\n')
