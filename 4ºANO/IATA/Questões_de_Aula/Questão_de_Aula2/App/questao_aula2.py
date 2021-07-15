import csv
import matplotlib.pyplot as plt
import networkx as nx

# Open the file :
#  - FROM NODE
#  - TO NODE
#  - STRENGTH OF TIE (5=strong tie ; 1= weak tie)
#  - LEVEL TO EACH TIE HAS BEEN VERIFIED (1=confirmed close contact, 2= various record interactions, 3= potential or planned unconfirmed interaction)
in_file = csv.reader(open('lista_arestas.txt', 'r'))

# Create a graph with the information contained in the file
g = nx.Graph()
for line in in_file:
    g.add_edge(line[0], line[1], weight=line[2], conf=line[3])

print(g.nodes())
print(g.edges())

# SEE GRAPH
plt.figure(figsize=(50, 50))
nx.draw_networkx(g);
plt.show()

###################################################################################################

# I find that the network consists of several disconnected components
# I only concern with the largest component of network so
component = list(g.subgraph(c) for c in nx.connected_components(g))[0]

# SEE LARGEST COMPONENT OF NETWORK
plt.figure(figsize=(50, 50))
nx.draw_networkx(component);
plt.show()

###################################################################################################

# DEGREE DISTRIBUTION
degs = [i[1] for i in component.degree()]
fig, ax = plt.subplots(figsize=(13, 9))
ax.get_xaxis().tick_bottom()
ax.get_yaxis().tick_left()
ax.hist(degs, color="#3F5D7D", bins='auto')
plt.show()

###################################################################################################

# DIAMETER
if nx.is_connected(component):
    print('Diameter = ', nx.diameter(component))
else:
    print('Cannot calculate the diameter... i found infinite path length because the graph is not connected... ')

# DISTANCE MEASURES
center = nx.center(component)  # center of network

print('Center = ', center)

print(nx.degree(component, center))  # degree of network center

print('Shortest path between Abu Zubeida and Nabil al-Marabh is ',
      nx.shortest_path(component, 'Abu Zubeida', 'Nabil al-Marabh'))  # shortest_path
print('Length of shortest path between Abu Zubeida and Nabil al-Marabh is ',
      nx.shortest_path_length(component, 'Abu Zubeida', 'Nabil al-Marabh'))  # shortest path length

print('Eccentricity = ', nx.eccentricity(component, 'Mohamed Atta'))  # eccentricity

###################################################################################################

# CENTRALITY MEASURES
print(nx.degree_centrality(component))  # degree centrality

print(nx.eigenvector_centrality(component))  # eigenvector centrality

print(nx.closeness_centrality(component))  # closeness centrality

print(nx.betweenness_centrality(component))  # betweenness centrality

pos = nx.spring_layout(component)
betCent = nx.betweenness_centrality(component, normalized=True, endpoints=True)
node_color = [20000.0 * component.degree(v) for v in component]
node_size = [v * 10000 for v in betCent.values()]
plt.figure(figsize=(10, 10))
nx.draw_networkx(component, pos=pos, with_labels=True,
                 node_color=node_color,
                 node_size=node_size)
plt.axis('off')
plt.show()