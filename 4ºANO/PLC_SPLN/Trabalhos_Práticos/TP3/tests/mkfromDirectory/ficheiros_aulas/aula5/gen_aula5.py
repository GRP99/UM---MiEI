#!/usr/bin/python3

import yaml, sys, readline

def importyamlgen(f):
   with open(f) as file:
       gen = yaml.full_load(file)
   return gen

def pai(c,gen):
   try:
       cf=gen[c]["pais"]
       cp=gen[cf]["pai"]
       return gen[cp]["nome"]
   except Exception:
       return None
       #return gen[ gen[ gen[c]["pais"] ]["pai"] ]["nome"]

def mae(c,gen):
   try:
       cf=gen[c]["pais"]
       cp=gen[cf]["mae"]
       return gen[cp]["nome"]
   except Exception:
       return None

def pppessoas(gen):
   for p in gen:
      pppessoa(p,gen)

def pppessoa(c,gen):  ## FIXME melhorar...
   inf=gen[c]
   if c[0] == "I":
      print(f"{c} -- {inf['nome']} .... {mae(c,gen)} | {pai(c,gen)}")

def searchgen(n,gen):
   for c,inf in gen.items():
       if n in inf.get("nome",""):
          pppessoa(c,gen)
          print("---")

def main():
   gen=importyamlgen(sys.argv[1])
   while True:
      q = input("? ").strip()
      readline.add_history(q)
      searchgen(q,gen)
   #print(gen)

main()
