from os import altsep
import re
import json
import csv

list_cocktails = []
list_bartenders = []
list_idbartenders = []
list_locations = []
list_idlocations = []
list_bars = []
list_idbars = []
with open('../Datasets/cocktails.csv', mode='r', encoding='utf-8') as cocktails:
    csv_reader = csv.reader(cocktails, delimiter=';')
    line_count = 0        
    for row in csv_reader:
        if line_count != 0:
            id_cocktail = re.sub('[^A-Za-z0-9-]+', '', row[0].lower()).replace('-', '_')
            bartender = re.sub('[^A-Za-z0-9]+', '', row[9].lower())
            bar = re.sub('[^A-Za-z0-9]+', '', row[10].lower())
            local = re.sub('[^A-Za-z0-9]+', '', row[11].lower())
            
            list_cocktails.append({
                "_id": id_cocktail,
                "name": row[0],
                "reviews": [],
                "likes": {
                    "count": 0,
                    "authors":[],
                },
                "dislikes": {
                    "count": 0,
                    "authors":[],
                }
            })
            if not bartender in list_idbartenders:
                list_bartenders.append({
                    "_id": bartender,
                    "name": row[9],
                    "reviews": [],
                    "likes": {
                        "count": 0,
                        "authors":[],
                    },
                    "dislikes": {
                        "count": 0,
                        "authors":[],
                    }}
                )
                list_idbartenders.append(bartender)
            if not local in list_idlocations:
                list_locations.append({
                    "_id": local,
                    "name": row[11],
                    "visits": {
                        "count": 0,
                        "authors":[],
                    }
                    }
                )
                list_idlocations.append(local)
            
            if not bar in list_idbars:
                list_bars.append({
                    "_id": bar,
                    "name": row[10],
                    "visits": {
                        "count": 0,
                        "authors":[],
                    }
                    }
                )
                list_idbars.append(bar)
            
        line_count += 1

with open('../App/api-server/data/cocktails.json', mode='w', encoding='utf8') as individuos:
    json.dump(list_cocktails, individuos)

with open('../App/api-server/data/bartenders.json', mode='w', encoding='utf8') as individuos:
    json.dump(list_bartenders, individuos)

with open('../App/api-server/data/locations.json', mode='w', encoding='utf8') as individuos:
    json.dump(list_locations, individuos)

with open('../App/api-server/data/bars.json', mode='w', encoding='utf8') as individuos:
    json.dump(list_bars, individuos)