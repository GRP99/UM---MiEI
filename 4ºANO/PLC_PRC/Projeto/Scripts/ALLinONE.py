import csv
import json
import requests


def createCSV(dict_cocktails: dict):
    with open('Datasets/cocktails.csv', mode='w', encoding='utf8', newline='') as csv_file:
        fieldnames = [
            'Cocktail Name', 
            'Category', 
            'Alcoholic', 
            'Glassware',
            'Preparation', 
            'PreparationDE', 
            'PreparationIT', 
            'DrinkThumb',
            'Ingredients',
            'Bartender',
            'Bar/Company',
            'Location',
            'Garnish',
            'Notes'
        ]
        writer = csv.DictWriter(csv_file, delimiter=";", fieldnames=fieldnames)
        writer.writeheader()
        for cocktail, info in dict_cocktails.items():
            writer.writerow(info)


def put_all_together() -> dict:
    dict_cocktails = {}
    with open('Datasets/hotaling_cocktails.csv', 'r', encoding='utf8') as hotaling_cocktails:
        csv_reader = csv.reader(hotaling_cocktails, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count != 0:
                key = row[0].strip().replace(" ", "").lower()
                dict_cocktails[key] = {
                    'Cocktail Name': row[0].strip(),
                    'Bartender': row[1].strip() if row[1] != '' else 'N/A',
                    'Bar/Company': row[2].strip() if row[2] != '' else 'N/A',
                    'Location': row[3].strip() if row[3] != '' else 'N/A',
                    'Ingredients': row[4].strip().replace('\n',' ').replace('\r',' ') if row[4] != '' else 'N/A',
                    'Garnish': row[5].strip().replace('\n',' ').replace('\r',' ') if row[5] != '' else 'N/A',
                    'Glassware': row[6].strip() if row[6] != '' else 'N/A',
                    'Preparation': row[7].strip().replace('\n',' ').replace('\r',' ') if row[7] != '' else 'N/A',
                    'Notes': row[8].strip().replace('\n',' ').replace('\r',' ') if row[8] != '' else 'N/A',
                    'Category': 'N/A',
                    'Alcoholic': 'N/A',
                    'PreparationDE': 'N/A',
                    'PreparationIT': 'N/A',
                    'DrinkThumb': 'N/A'
                }
            line_count += 1

    with open('Datasets/thecocktaildb.csv', 'r', encoding='utf8') as thecocktaildb:
        csv_reader = csv.reader(thecocktaildb, delimiter=';')
        line_count = 0
        for row in csv_reader:
            if line_count != 0:
                key = row[0].strip().replace(" ", "").lower()

                if key in dict_cocktails.keys():
                    dict_cocktails.get(key)['Category'] = row[1]
                    dict_cocktails.get(key)['Alcoholic'] = row[2]
                    dict_cocktails.get(key)['Preparation'] = row[4]
                    dict_cocktails.get(key)['PreparationDE'] = row[5]
                    dict_cocktails.get(key)['PreparationIT'] = row[6]
                    dict_cocktails.get(key)['DrinkThumb'] = row[7]
                    dict_cocktails.get(key)['Ingredients'] = row[8]
                else:
                    dict_cocktails[key] = {
                        'Cocktail Name': row[0],
                        'Category': row[1],
                        'Alcoholic': row[2],
                        'Glassware': row[3],
                        'Preparation': row[4],
                        'PreparationDE': row[5],
                        'PreparationIT': row[6],
                        'DrinkThumb': row[7],
                        'Ingredients': row[8],
                        'Bartender': 'N/A',
                        'Bar/Company': 'N/A',
                        'Location': 'N/A',
                        'Garnish': 'N/A',
                        'Notes': 'N/A'
                    }
            line_count += 1

    return dict_cocktails


def main():
    dict_cocktails = put_all_together()

    createCSV(dict_cocktails)

    print(f'File cocktails.csv created with {len(dict_cocktails)} cocktails!')


main()