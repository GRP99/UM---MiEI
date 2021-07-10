# /usr/bin/python3
import re
import sys
import readline
from neographviz import Graph, plot
from py2neo import Graph, Node, Relationship


def efetua_consulta_table(graph, query) -> str:
    try:
        return str(graph.run(str(query)).to_table())
    except Exception as e:
        return "ERRO : " + str(e)


def efetua_consulta_dict(graph, query) -> str:
    try:
        return str(graph.run(str(query)).data())
    except Exception as e:
        return "ERRO : " + str(e)


def cria_ficheiro(limit) -> str:
    graph = Graph("http://localhost:7474", auth=("neo4j", "12345"))
    query = "match p=()--()--() return p Limit " + limit
    x = plot(graph, query, font_size=50, node_size=30, height=900)
    return str(x.src)


def introduz_ficheiro(graph, ficheiro):
    with open(ficheiro) as scriptPovoamento:
        povoamento = scriptPovoamento.read()
    return str(graph.run(povoamento).stats())


def dataset_movies(graph):
    with open("movies.txt") as scriptPovoamento:
        povoamento = scriptPovoamento.read()
    return str(graph.run(povoamento).stats())


def dataset_hr(graph):
    with open("hr.txt") as scriptPovoamento:
        povoamento = scriptPovoamento.read()
    return str(graph.run(povoamento).stats())


def main():
    try:
        flag = True
        graph = Graph("http://localhost:7474", auth=("neo4j", "12345"))
        graph.delete_all()

        while flag:
            print("==========================================================")
            print("=====    Escolha o dataset                           =====")
            print("=====        1. Dataset sobre funcionários           =====")
            print("=====        2. Dataset sobre cinema                 =====")
            print("=====        3. Introduzir ficheiro                  =====")
            print("=====    Sair do programa: exit                      =====")
            print("==========================================================")
            query = input("? ").strip()
            if query == "exit":
                sys.exit()
            else:
                readline.add_history(query)
                if query == '1':
                    res = dataset_hr(graph)
                    print(res)
                    flag = False
                elif query == '2':
                    res = dataset_movies(graph)
                    print(res)
                    flag = False
                elif query == '3':
                    print("=====    Nome do ficheiro:                           =====")
                    ficheiro = input("$ ").strip()
                    res = introduz_ficheiro(graph, ficheiro)
                    print(res)
                    flag = False
                else:
                    print("=====    Opção inválida !                            =====")

        while True:
            print("==========================================================")
            print("=====    Operações                                   =====")
            print("=====        1. Criar ficheiro HTML do dataset       =====")
            print("=====        2. Escrever query (Resultado as dict)   =====")
            print("=====        3. Escrever query (Resultado as table)  =====")
            print("=====    Sair do programa: exit                      =====")
            print("==========================================================")
            query = input("? ").strip()
            if query == "exit":
                sys.exit()
            else:
                readline.add_history(query)
                if query == '1':
                    print("=====    Limite:                                     =====")
                    limite = input("$ ").strip()
                    if limite.isnumeric():
                        ficheiro = cria_ficheiro(limite)
                        print("=====    Ficheiro " + ficheiro + " criado   =====")
                    else:
                        print("=====    Opção inválida !                            =====")
                elif query == '2':
                    print("=====    Query:                                      =====")
                    consulta = input("$ ").strip()
                    res = efetua_consulta_dict(graph, consulta)
                    print("=====\n" + res + "\n=====")
                elif query == '3':
                    print("=====    Query:                                      =====")
                    consulta = input("$ ").strip()
                    res = efetua_consulta_table(graph, consulta)
                    print("=====\n" + res + "\n=====")
                else:
                    print("=====    Opção inválida !                            =====")

    except Exception as e:
        print('ERRO : ' + str(e))

main()