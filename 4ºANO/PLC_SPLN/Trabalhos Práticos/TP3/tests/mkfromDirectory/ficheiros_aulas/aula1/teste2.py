#!/usr/bin/python3
from jjcli import *
c=clfilter()
i=j=w=r=0
for line in c.input():
  w += len(line.split())
  j += len(line) + 1
  i += 1
#for par in c.paragraph():
#  r += 1
print(i,w,j)