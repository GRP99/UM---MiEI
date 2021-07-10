import csv

dict_meses = { "1": "Jan", "2": "Fev", "3": "Mar", "4": "Abr", "5":"Mai","6":"Jun","7":"Jul","8":"Ago","9":"Set","10":"Out","11":"Nov","12":"Dez"}

with open('individuos.txt','w+') as individuos:

    dict_pagamentos = {}
    with open('pagamentos.txt','r') as pagamentos:
        line_count = 0
        for row in csv.reader(pagamentos, delimiter=','):
            if line_count == 0:
                field_count = 0
                for field in row:
                    if field_count != 0:
                        individuos.write('###  http://www.di.uminho.pt/prc2021/afericao#' + field  + '\n')
                        individuos.write(':' + field + ' rdf:type owl:NamedIndividual ,\n')
                        individuos.write('              :Mes . \n\n\n')
                    field_count += 1
            else:
                list_meses = []
                field_count = 0
                for field in row:
                    if field_count != 0:
                        if field == str(1):
                            list_meses.append(dict_meses.get(str(field_count)))
                    field_count += 1

                dict_pagamentos[row[0]] = list_meses
            line_count += 1

    dict_origem_receita = {}
    dict_origem_despesa = {}
    with open('livro.txt','r') as livro:
        line_count = 0
        for row in csv.reader(livro, delimiter=','):
            if line_count != 0:
                id_livro = row[1][0] + row[0].split('/')[1]
                individuos.write('###  http://www.di.uminho.pt/prc2021/afericao#' + id_livro + '\n')
                individuos.write(':' + id_livro + ' rdf:type owl:NamedIndividual ,\n')
                individuos.write('            :' + row[1] + ' ;\n')
                individuos.write('   :data "' + row[2] + '" ;\n')
                individuos.write('   :valor ' + row[3] + ' ;\n')
                individuos.write('   :descricao "' + row[5] + '" .\n\n\n')

                if row[1] == 'Receita':
                    if not row[4].replace('Âº ','') in dict_origem_receita:
                        dict_origem_receita[row[4].replace('Âº ','')] = [id_livro]
                    else:
                        list_livro = dict_origem_receita.get(row[4].replace('Âº ',''))
                        list_livro.append(id_livro)
                        dict_origem_receita[row[4].replace('Âº ','')] = list_livro
                else:
                    if not row[4] in dict_origem_despesa:
                        dict_origem_despesa[row[4]] = [id_livro]
                    else:
                        list_livro = dict_origem_despesa.get(row[4])
                        list_livro.append(id_livro)
                        dict_origem_despesa[row[4]] = list_livro
                    
            line_count += 1


    with open('fracoes.txt', 'r') as fracoes:
        line_count = 0
        for row in csv.reader(fracoes, delimiter=','):
            if line_count != 0:
                individuos.write('###  http://www.di.uminho.pt/prc2021/afericao#' + row[1] + '\n')
                individuos.write(':' + row[1] + ' rdf:type owl:NamedIndividual ,\n')
                individuos.write('            :Fracao ;\n')
                pagamento_count = 1
                for mes in dict_pagamentos.get(row[0].replace('Âº ','')):
                    if pagamento_count == 1:
                        individuos.write('   :pagamento :' + mes + ' ,\n')
                    elif pagamento_count == len(dict_pagamentos.get(row[0].replace('Âº ',''))) :
                        individuos.write('              :' + mes +' ;\n')
                    else:
                        individuos.write('              :' + mes +' ,\n')
                    pagamento_count += 1

                receita_count = 1
                list_receitas = dict_origem_receita.get(row[0].replace('Âº ',''))
                if list_receitas is not None:
                    for receita in list_receitas:
                        if receita_count == 1 :
                            individuos.write('   :originaReceita :' + receita + ' ')
                        else:
                            individuos.write(',\n              :' + receita + ' ')
                        receita_count += 1
                    individuos.write(';\n')

                individuos.write('   :descricao "' + row[0] + '" ;\n')
                individuos.write('   :permilagem ' + row[2] + ' ;\n')
                individuos.write('   :mensalidade "' + row[3] + '" .\n\n\n')
            line_count += 1
    
    for fornecedor in dict_origem_despesa.keys():
        list_despesas = dict_origem_despesa.get(fornecedor)
        individuos.write('###  http://www.di.uminho.pt/prc2021/afericao#' + fornecedor + '\n')
        individuos.write(':' + fornecedor + ' rdf:type owl:NamedIndividual ,\n')
        individuos.write('                  :Fornecedor ;\n')
        despesa_count = 1

        if list_despesas is not None:
            for despesa in dict_origem_despesa.get(fornecedor):
                if despesa_count == 1 :
                    individuos.write('         :originaDespesa :' + despesa)
                else:
                    individuos.write(',\n           :' + despesa)
                despesa_count += 1
            individuos.write('.\n\n\n')