# /usr/bin/python3
from py2neo import Graph, Node, Relationship
from py2neo.matching import *


graph = Graph("http://localhost:7474", auth=("neo4j","12345"))

graph.delete_all()

a = Node("Person", name="Alice", age=20)
b = Node("Person", name="Bob", age=21)
graph.create(a)
graph.create(b)

nicole = Node("Person", name="Nicole", age=22)
drew = Node("Person", name="Drew", age=23)

mtdew = Node("Drink", name="Mountain Dew", calories=9000)
cokezero = Node("Drink", name="Coke Zero", calories=0)

coke = Node("Manufacturer", name="Coca Cola")
pepsi = Node("Manufacturer", name="Pepsi")

graph.create(nicole | drew | mtdew | cokezero | coke | pepsi)


graph.create(Relationship(a, "KNOWS", b))

graph.create(Relationship(nicole, "LIKES", cokezero))
graph.create(Relationship(nicole, "LIKES", mtdew))
graph.create(Relationship(drew, "LIKES", mtdew))

graph.create(Relationship(coke, "MAKES", cokezero))
graph.create(Relationship(pepsi, "MAKES", mtdew))

nodes = NodeMatcher(graph)
res = nodes.match("Person",name="Bob").first()
print(str(res) + "\n")

query = "MATCH (bob:Person{name:\"Bob\"}) RETURN bob"
res = graph.run(query).data()
bob = print( str(res) + "\n\n")


query = """
MATCH (person:Person)-[:LIKES]->(drink:Drink)
RETURN person.name AS name, drink.name AS drink
"""

data = graph.run(query).to_table()
print(data)

query = """
MATCH (p:Person)-[:LIKES]->(drink:Drink)
WHERE p.name = $name
RETURN p.name AS name, AVG(drink.calories) AS avg_calories
"""

data = graph.run(query, name="Nicole")

for d in data:
    print(d)