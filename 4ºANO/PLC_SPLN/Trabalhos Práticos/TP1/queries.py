# /usr/bin/python3
import re
import sys
import readline
import requests


def queries(query):
    q = query.split("=")

    if ("Nome" in query) or ("id" in query):
        if re.search("^id", q[0]):
            query = '_' + q[0] + '=' + q[1]

    elif ("Nascimento" in query) or ("Falecimento" in query):

        if re.search("^\d", q[1]) and re.search("^(N|F)", q[0]):
            query = q[0] + '.data=' + q[1]

        elif re.search("^\w", q[1]) and re.search("^(N|F)", q[0]):
            query = q[0] + '.local=' + q[1]

    elif "Info" in query:
        query = 'q=' + q[1]

    else:
        query = '_id=none'
    # the required first parameter of the 'get' method is the 'url':
    x = requests.get('http://localhost:3000/Pessoas?' + query)

    # print the response text (the content of the requested file):
    output = x.text
    print(output)


def main():
    print("=== Procurar por nome: Nome=[A-Za-z ]+ ===")
    print("=== Procurar por id: id=[0-9]+ ===")
    print("=== Procurar por Nascimento|Falecimento: (Nascimento|Falecimento)=([A-Za-z ,]+)|((\d{1,2}\.){2}\d{4}) ===")
    print("=== Procurar por em todos os campos com: Info=.+ ===")
    print("=== Sair do programa: exit ===")
    while True:
        query = input("? ").strip()

        if query == "exit":
            sys.exit()

        else:
            readline.add_history(query)
            queries(query)


main()
