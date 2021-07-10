#!/usr/bin/python3
from jjcli import * 
import glob, yaml, os
import jinja2 as j2
c=clfilter(opt="do:")     ## option values in c.opt dictionary

base="../SPLN2021/Colecoes/Arquivo"
c.args=glob.iglob(base + "/**/*.md", recursive=True)



def html1( c: dict) :
    os.makedirs("HTML",exist_ok=True)
    with open(f'HTML/{c["id"]}.html',"w") as f:
        txt =  j2.Template("""<h1>{{from}} → {{to}}</h1>
<pre>{{body}}</pre>
""").render(c)
        print(txt, file=f)
        

def html2(c: dict) :
    os.makedirs("HTML2",exist_ok=True)
    with open(f'HTML2/{c["from"]}.html',"a") as f:
        txt =  j2.Template('''<a href="../HTML/{{id}}.html">{{id}} : {{from}} → {{to}}</a>
''').render(c)
        print(txt, file=f)

tabela_visitas = [{'pre' : lambda c : "from" in c , 'v' : html2},
                  {'pre' : lambda c : "id" in c , 'v' : html1}]
## 
for txt in c.slurp(): 
    
    ex=match(r'\s*---(.*?)---(.*)',txt,flags=re.S)
    if not ex:
        print("#### Erro no formato de", c.filename())
        continue 
    meta,body=(ex[1] , ex[2])
    try:
        carta= yaml.safe_load(meta)
    except Exception as e:
        print("#### Erro nos metadados de", c.filename(),e)
        continue 
    carta["body"]=body

    if "id" not in carta: 
        print('#### Erro no id')
        continue

    if "from" not in carta: 
        print('#### Erro no from')
        continue
    
    
    for i in tabela_visitas : 
        if i["pre"](carta) :
            i["v"](carta)
    ## html1(carta)
    ## html2(carta)
