import csv
import json


def createCSV(dict_wine: dict):
    with open('./Classification/winequality-classification.csv', mode='w', encoding='utf8', newline='') as csv_file:
        writer = csv.DictWriter(csv_file, delimiter=",", fieldnames=['fixed acidity', 'volatile acidity', 'citric acid', 'residual sugar', 'chlorides', 'free sulfur dioxide', 'total sulfur dioxide', 'density', 'pH', 'sulphates', 'alcohol', 'quality'])
        writer.writeheader()

        for wine, info in dict_wine.items():
            writer.writerow(info)


def put_all_together() -> dict:
    dict_wine = {}
    with open('./Datasets/winequality-red.csv', 'r', encoding='utf8') as red_wine:
        line_count = 0
        for row in csv.reader(red_wine, delimiter=';'):
            if line_count != 0:
                dict_wine['red' + str(line_count)] = {
                    'fixed acidity': row[0].strip(),
                    'volatile acidity': row[1].strip(),
                    'citric acid': row[2].strip(),
                    'residual sugar': row[3].strip(),
                    'chlorides': row[4].strip(),
                    'free sulfur dioxide': row[5].strip(),
                    'total sulfur dioxide': row[6].strip(),
                    'density': row[7].strip(),
                    'pH': row[8].strip(),
                    'sulphates': row[9].strip(),
                    'alcohol': row[10].strip(),
                    'quality': 'good' if int(row[11]) >= 7 else 'bad'
                }
            line_count += 1

    with open('./Datasets/winequality-white.csv', 'r', encoding='utf8') as white_wine:
        line_count = 0
        for row in csv.reader(white_wine, delimiter=';'):
            if line_count != 0:
                dict_wine['white' + str(line_count)] = {
                    'fixed acidity': row[0].strip(),
                    'volatile acidity': row[1].strip(),
                    'citric acid': row[2].strip(),
                    'residual sugar': row[3].strip(),
                    'chlorides': row[4].strip(),
                    'free sulfur dioxide': row[5].strip(),
                    'total sulfur dioxide': row[6].strip(),
                    'density': row[7].strip(),
                    'pH': row[8].strip(),
                    'sulphates': row[9].strip(),
                    'alcohol': row[10].strip(),
                    'quality': 'good' if int(row[11]) >= 7 else 'bad'
                }
            line_count += 1
    return dict_wine


def main():
    dict_wine = put_all_together()

    createCSV(dict_wine)

    print(f'File winequality-classification.csv created with {len(dict_wine)} instances!')


main()