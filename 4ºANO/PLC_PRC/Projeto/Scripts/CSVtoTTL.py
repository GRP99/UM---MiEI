import re
import csv

list_categories = []
list_glassware = []
list_bartender = []
list_bar = []
list_location = []
list_ingredient = []
list_QuantityOfIngredient = []

with open('../Ontologies/individuals.ttl', mode='w', encoding='utf8') as individuos:
    with open('../Datasets/cocktails.csv', mode='r', encoding='utf-8') as cocktails:
        csv_reader = csv.reader(cocktails, delimiter=';')
        line_count = 0        
        for row in csv_reader:
            if line_count != 0:
                cocktail = re.sub('[^A-Za-z0-9-]+', '', row[0].lower()).replace('-', '_')

                if row[1] != 'N/A':
                    category = re.sub('[^A-Za-z0-9]+', '', row[1].lower())
                    if not category in list_categories:
                        list_categories.append(category)
                        individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + category + '\n')
                        individuos.write(':' + category + ' rdf:type owl:NamedIndividual ,\n')
                        individuos.write('            :Category ;\n')
                        individuos.write('    :strCategory "' + row[1] + '" .\n\n\n')

                if row[3] != 'N/A':
                    glassware = re.sub('[^A-Za-z0-9]+', '', row[3].lower())
                    if not glassware in list_glassware:
                        list_glassware.append(glassware)
                        individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + glassware + '\n')
                        individuos.write(':' + glassware + ' rdf:type owl:NamedIndividual ,\n')
                        individuos.write('            :Glassware ;\n')
                        individuos.write('    :strGlass "' + row[3] + '" .\n\n\n')

                if row[9] != 'N/A':
                    bartender = re.sub('[^A-Za-z0-9]+', '', row[9].lower())
                    if not bartender in list_bartender:
                        list_bartender.append(bartender)
                        individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + bartender + '\n')
                        individuos.write(':' + bartender + ' rdf:type owl:NamedIndividual ,\n')
                        individuos.write('            :Bartender ;\n')
                        individuos.write('    :strBartender "' + row[9] + '" .\n\n\n')

                if row[10] != 'N/A':
                    bar = re.sub('[^A-Za-z0-9]+', '', row[10].lower())
                    if not bar in list_bar:
                        list_bar.append(bar)
                        individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + bar + '\n')
                        individuos.write(':' + bar + ' rdf:type owl:NamedIndividual ,\n')
                        individuos.write('            :Bar_Company ;\n')
                        individuos.write('    :strBar "' + row[10] + '" .\n\n\n')

                if row[11] != 'N/A':
                    local = re.sub('[^A-Za-z0-9]+', '', row[11].lower())
                    if not local in list_location:
                        list_location.append(local)
                        individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + local + '\n')
                        individuos.write(':' + local + ' rdf:type owl:NamedIndividual ,\n')
                        individuos.write('            :Location ;\n')
                        individuos.write('    :strLocation "' + row[11] + '" .\n\n\n')

                if row[12] != 'N/A':
                    for gar in row[12].split(','):
                        garnish = re.sub('[^A-Za-z0-9]+', '', gar.lower())
                        if not garnish in list_ingredient:
                            list_ingredient.append(garnish)
                            individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + garnish + '\n')
                            individuos.write(':' + garnish + ' rdf:type owl:NamedIndividual ,\n')
                            individuos.write('            :Ingredient ;\n')
                            individuos.write('    :strIngredient "' + gar.strip() + '" .\n\n\n')
                
                for r in row[8].split(','):
                    qnttofIgrdnt = re.findall(r'\s*[\d\/\.\-]+\s[^A-Z1-9]*\s*',r)
                    if len(qnttofIgrdnt) > 0:
                        qnt_msr = qnttofIgrdnt[0].strip().split(' ')
                        if len(qnt_msr) == 2:
                            quantity = qnt_msr[0]
                            measure = qnt_msr[1]
                            ingredient = r.replace(qnt_msr[0] + ' ' + qnt_msr[1], '').replace('*','').strip()
                            keyingredient = re.sub('[^A-Za-z0-9]+', '', ingredient.lower())
                            if not keyingredient in list_ingredient:
                                list_ingredient.append(keyingredient)
                                individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + keyingredient + '\n')
                                individuos.write(':' + keyingredient + ' rdf:type owl:NamedIndividual ,\n')
                                individuos.write('            :Ingredient ;\n')
                                individuos.write('    :strIngredient "' + ingredient.strip() + '" .\n\n\n')
                            keyQuantityOfIngredient = re.sub('[^A-Za-z0-9]+', '', r.lower())
                            if not keyQuantityOfIngredient in list_QuantityOfIngredient:
                                list_QuantityOfIngredient.append(keyQuantityOfIngredient)
                                individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + keyQuantityOfIngredient + '\n')
                                individuos.write(':' + keyQuantityOfIngredient + ' rdf:type owl:NamedIndividual ,\n')
                                individuos.write('            :QuantityOfIngredient ;\n')
                                individuos.write('    :useIngredient :' + keyingredient + ' ;\n')
                                individuos.write('    :measure "' + measure + '" ;\n')
                                individuos.write('    :quantity "' + quantity + '" .\n\n\n')
                        else:
                            qtd = qnt_msr[0]
                            ingredient = r.replace(qnt_msr[0] + ' ', '').replace('*','').strip()
                            keyingredient = re.sub('[^A-Za-z0-9]+', '', ingredient.lower())
                            if not keyingredient in list_ingredient:
                                list_ingredient.append(keyingredient)
                                individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + keyingredient + '\n')
                                individuos.write(':' + keyingredient + ' rdf:type owl:NamedIndividual ,\n')
                                individuos.write('            :Ingredient ;\n')
                                individuos.write('    :strIngredient "' + ingredient.strip() + '" .\n\n\n')
                            keyQuantityOfIngredient = re.sub('[^A-Za-z0-9]+', '', r.lower())
                            if not keyQuantityOfIngredient in list_QuantityOfIngredient:
                                list_QuantityOfIngredient.append(keyQuantityOfIngredient)
                                individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + keyQuantityOfIngredient + '\n')
                                individuos.write(':' + keyQuantityOfIngredient + ' rdf:type owl:NamedIndividual ,\n')
                                individuos.write('            :QuantityOfIngredient ;\n')
                                individuos.write('    :useIngredient :' + keyingredient + ' ;\n')
                                individuos.write('    :quantity "' + qtd + '" .\n\n\n')
                    else:
                        ingredient = r.strip()
                        keyingredient = re.sub('[^A-Za-z0-9]+', '', ingredient.lower())
                        if not keyingredient in list_ingredient:
                            list_ingredient.append(keyingredient)
                            individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + keyingredient + '\n')
                            individuos.write(':' + keyingredient + ' rdf:type owl:NamedIndividual ,\n')
                            individuos.write('            :Ingredient ;\n')
                            individuos.write('    :strIngredient "' + ingredient.strip() + '" .\n\n\n')
                        keyQuantityOfIngredient = re.sub('[^A-Za-z0-9]+', '', r.lower())
                        if not keyQuantityOfIngredient in list_QuantityOfIngredient:
                            list_QuantityOfIngredient.append(keyQuantityOfIngredient)
                            individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#qi_' + keyQuantityOfIngredient + '\n')
                            individuos.write(':qi_' + keyQuantityOfIngredient + ' rdf:type owl:NamedIndividual ,\n')
                            individuos.write('            :QuantityOfIngredient ;\n')
                            individuos.write('    :useIngredient :' + keyingredient + ' .\n\n\n')

                individuos.write('###  http://www.di.uminho.pt/prc2021/projeto#' + cocktail + '\n')
                individuos.write(':' + cocktail + ' rdf:type owl:NamedIndividual ,\n')
                individuos.write('            :Cocktail ;\n')
                if row[1] != 'N/A':
                    individuos.write('    :hasCategory :' + re.sub('[^A-Za-z0-9]+', '', row[1].lower()) + ' ;\n')
                if row[3] != 'N/A':
                    individuos.write('    :serveInGlassware :' + re.sub('[^A-Za-z0-9]+', '', row[3].lower()) + ' ;\n')
                if row[9] != 'N/A':
                    individuos.write('    :createdByBartender :' + re.sub('[^A-Za-z0-9]+', '', row[9].lower()) + ' ;\n')
                if row[10] != 'N/A':
                    individuos.write('    :associatedWithBar :' + re.sub('[^A-Za-z0-9]+', '', row[10].lower()) + ' ;\n')
                if row[11] != 'N/A':
                    individuos.write('    :createdInLocation :' + re.sub('[^A-Za-z0-9]+', '', row[11].lower()) + ' ;\n')
                if row[12] != 'N/A':
                    for gar in row[12].split(','):
                        individuos.write('    :garnishWithIngredient :' + re.sub('[^A-Za-z0-9]+', '', gar.lower()) + ' ;\n')
                for ing in row[8].split(','):
                    qnttof = re.findall(r'\s*[\d\/\.\-]+\s[^A-Z1-9]*\s*',ing)
                    if len(qnttof) > 0:
                        individuos.write('    :needQuantity :' + re.sub('[^A-Za-z0-9]+', '', ing.lower()) + ' ;\n')
                    else:
                        individuos.write('    :needQuantity :qi_' + re.sub('[^A-Za-z0-9]+', '', ing.lower()) + ' ;\n')
                if row[2] != 'N/A':
                    individuos.write('    :alcoholic "' + row[2] + '" ;\n')
                if row[4] != 'N/A':
                    individuos.write('    :preparationEN "' + row[4].replace('\"', '\\"') + '" ;\n')
                if row[5] != 'N/A':
                    individuos.write('    :preparationDE "' + row[5].replace('\"', '\\"') + '" ;\n')
                if row[6] != 'N/A':
                    individuos.write('    :preparationIT "' + row[6].replace('\"', '\\"') + '" ;\n')
                if row[7] != 'N/A':
                    individuos.write('    :drinkThumb "' + row[7] + '" ;\n')
                if row[13] != 'N/A':
                    individuos.write('    :notes "' + row[13].replace('\"', '\\"') + '" ;\n')
                individuos.write('    :drinkName "' + row[0] + '" .\n\n\n')
                
            line_count += 1
