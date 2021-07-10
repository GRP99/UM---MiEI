import csv
import json
import string
import requests


def get_ingredients(cocktail_dict) -> str:
    if not cocktail_dict['strIngredient1'] is None:
        ingredient = cocktail_dict['strIngredient1'].strip().replace('\n', ' ').replace('\r', ' ')
    else:
        ingredient = None

    if not cocktail_dict['strMeasure1'] is None:
        measure = cocktail_dict['strMeasure1'].strip().replace('\n', ' ').replace('\r', ' ') + ' '
    else:
        measure = ''

    i = 1
    ingredients = ""

    while ingredient:
        if i == 1:
            ingredients = measure + ingredient + ingredients
        else:
            ingredients = measure + ingredient + ", " + ingredients

        i += 1

        if not cocktail_dict['strIngredient' + str(i)] is None:
            ingredient = cocktail_dict['strIngredient' + str(i)].strip().replace('\n', ' ').replace('\r', ' ')
        else:
            ingredient = None

        if not cocktail_dict['strMeasure' + str(i)] is None:
            measure = cocktail_dict['strMeasure' + str(i)].strip().replace('\n', ' ').replace('\r', ' ') + ' '
        else:
            measure = ''
    return ingredients


def get_info_api():
    total_drinks = 0
    with open('Datasets/thecocktaildb.csv', mode='w', encoding='utf8', newline='') as csv_file:
        fieldnames = ['Cocktail Name', 'Category', 'Alcoholic', 'Glassware', 'Preparation', 'PreparationDE', 'PreparationIT', 'DrinkThumb', 'Ingredients']
        writer = csv.DictWriter(csv_file, delimiter=";", fieldnames=fieldnames)
        main_url = 'https://www.thecocktaildb.com/api/json/v1/1/search.php?f='
        writer.writeheader()
        for i in string.printable[:36]:
            try:
                data = requests.get(main_url + i)
                if data.json()['drinks']:
                    for cocktail_dict in data.json()['drinks']:
                        cocktail = {
                            'Cocktail Name': cocktail_dict['strDrink'].strip(),
                            'Category': cocktail_dict['strCategory'].strip() if not cocktail_dict['strCategory'] is None else 'N/A',
                            'Alcoholic': cocktail_dict['strAlcoholic'].strip() if not cocktail_dict['strAlcoholic'] is None else 'N/A',
                            'Glassware': cocktail_dict['strGlass'].strip() if not cocktail_dict['strGlass'] is None else 'N/A',
                            'Preparation': cocktail_dict['strInstructions'].strip().replace('\n', ' ').replace('\r', ' ') if not cocktail_dict['strInstructions'] is None else 'N/A',
                            'PreparationDE': cocktail_dict['strInstructionsDE'].strip().replace('\n', ' ').replace('\r', ' ') if not cocktail_dict['strInstructionsDE'] is None else 'N/A',
                            'PreparationIT': cocktail_dict['strInstructionsIT'].strip().replace('\n', ' ').replace('\r', ' ') if not cocktail_dict['strInstructionsIT'] is None else 'N/A',
                            'DrinkThumb': cocktail_dict['strDrinkThumb'].strip(),
                            'Ingredients': get_ingredients(cocktail_dict).strip()
                        }
                        writer.writerow(cocktail)
                        total_drinks += 1

            except Exception as e:
                print("Ocorreu um erro ... " + str(e))
                raise e

    return total_drinks


def main():
    total_cocktails = get_info_api()

    print('File thecocktaildb.csv created !')

    print(f'Total of Cocktails in TheCocktailDB : {total_cocktails}')


main()