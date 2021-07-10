#!/usr/bin/python3
import requests as reqs
from bs4 import BeautifulSoup
from jjcli import *

array = []
array_ids = []

for page in range(0, 150, 30):
    url = 'http://pagfam.geneall.net/3418/pessoas_search.php?start=' + str(page) + '&orderby=&sort=&idx=0&search='
    resp = reqs.get(url)

    resp.raise_for_status()

    soup = BeautifulSoup(resp.text, 'html.parser')

    array.append(soup.find_all('a')[3:len(soup.find_all('a')) - 4])

print('---')
for i in array:
    for link in i:
        print(findall(r'\?id=[0-9]+\s*\">', str(link))[0][4:11])
        array_ids.append(findall(r'\?id=[0-9]+\s*\">', str(link))[0][4:11])
print('---')
print('Total de páginas:', len(array))
print('Total de indivíduos:', len(array_ids))
