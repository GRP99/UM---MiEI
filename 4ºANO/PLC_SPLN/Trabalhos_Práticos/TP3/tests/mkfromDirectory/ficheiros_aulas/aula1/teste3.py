#!/usr/bin/python3
from jjcli import *
c=clfilter(opt="a")
i=j=w=r=0
ocr = {}
#for line in c.input():
for par in c.paragraph():
    #all = findall(r"[a-zA-Z]+", par)
    for w in findall(r"\w+", par):
        ocr[w] =1 if w not in ocr else ocr[w] + 1
    #print(all)

def dict_word(d):
    for k,v in sorted(d.items(), key = lambda x: x[0]):
        print(f"{k}: {v}")

def dict_num(d):
    for k,v in sorted(d.items(), key = lambda x: x[1]):
        print(f"{k}: {v}")

if("-a" in c.opt):
    dict_word(ocr)
else:
    dict_num(ocr)
