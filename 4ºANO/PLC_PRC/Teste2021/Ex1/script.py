import re
import json

list_clubes = []
list_modalidades = []
dict_emds = {}
with open('individuos.ttl','w+', encoding='utf-8') as individuos:
    with open('emd.json', 'r', encoding='utf-8') as dataset:
        for emd in json.load(dataset):
            modalidade = emd.get('modalidade')
            id_modalidade = re.sub('[^A-Za-z0-9-]+', '', modalidade.lower())
            if not modalidade in list_modalidades:                
                individuos.write('###  http://www.di.uminho.pt/prc2021/exames_medicos#' + id_modalidade  + '\n')
                individuos.write(':' + id_modalidade + ' rdf:type owl:NamedIndividual ,\n')
                individuos.write('            :Modalidade ;\n')
                individuos.write('   :designacao "' + modalidade + '" .\n\n\n')
                list_modalidades.append(modalidade)
            
            clube = emd.get('clube')
            id_clube = re.sub('[^A-Za-z0-9-]+', '', clube.lower())
            if not clube in list_clubes:                
                individuos.write('###  http://www.di.uminho.pt/prc2021/exames_medicos#' + id_clube  + '\n')
                individuos.write(':' + id_clube + ' rdf:type owl:NamedIndividual ,\n')
                individuos.write('            :Clube ;\n')
                individuos.write('   :designacao "' + clube + '" .\n\n\n')
                list_clubes.append(clube)

            individuos.write('###  http://www.di.uminho.pt/prc2021/exames_medicos#emd' + str(emd.get('_id'))  + '\n')
            individuos.write(':emd' + str(emd.get('_id')) + ' rdf:type owl:NamedIndividual ,\n')
            individuos.write('            :EMD ;\n')
            individuos.write('   :id "' + str(emd.get('index')) + '" ;\n')
            individuos.write('   :resultado "' + str(emd.get('resultado')) + '" ;\n')
            individuos.write('   :data "' + emd.get('dataEMD') + '" .\n\n\n')


            individuos.write('###  http://www.di.uminho.pt/prc2021/exames_medicos#a' + str(emd.get('_id'))  + '\n')
            individuos.write(':a' + str(emd.get('_id')) + ' rdf:type owl:NamedIndividual ,\n')
            individuos.write('            :Atleta ;\n')
            individuos.write('   :efetuaEMD :emd' + str(emd.get('_id')) + ' ;\n')
            individuos.write('   :praticaModalidade :' + id_modalidade + ' ;\n')
            individuos.write('   :temClube :' + id_clube + ' ;\n')
            individuos.write('   :primeiro_nome "' + emd.get('nome').get('primeiro') + '" ;\n')
            individuos.write('   :ultimo_nome "' + emd.get('nome').get('último') + '" ;\n')
            individuos.write('   :idade "' + str(emd.get('idade')) + '" ;\n')
            individuos.write('   :genero "' + emd.get('género') + '" ;\n')
            individuos.write('   :morada "' + emd.get('morada') + '" ;\n')
            individuos.write('   :email "' + emd.get('email') + '" ;\n')
            individuos.write('   :federado "' + str(emd.get('federado')) + '" .\n\n\n')