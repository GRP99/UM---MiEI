import csv
import json


def createARFF():
    with open('./Regression/winequality-regression.arff', mode='w+') as arff_file:
        with open('./Regression/winequality-regression.csv', 'r') as csv_file:
            line_count = 0
            for row in csv.reader(csv_file, delimiter=','):
                if line_count == 0:
                    arff_file.write("@relation winequality\n")
                    for field in row:
                        arff_file.write("@attribute \'" + field + "\' real \n")
                    arff_file.write("@data")
                else:
                    i = 0
                    for field in row:
                        if i == len(row) - 1:
                            arff_file.write(field)
                        else:
                            arff_file.write(field + ',')
                        i += 1
                arff_file.write('\n')
                line_count += 1


def main():
    createARFF()

    print(f'File winequality-regression.arff created !')


main()