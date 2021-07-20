## Trabalho Prático 1

No âmbito da UC de SPLN, pretende-se desenvolver um ou mais *scripts*, que permitam através de bibliotecas
disponibilizadas na linguagem **Python** maximizar a eficácia do processo de extração de informação de um [Website](http://pagfam.geneall.net/3418/) sobre pessoas de uma determinada família e as suas relações familiares. Pretendeu-se que a informação extraída fosse o mais clara possível.


### Extração das Pessoas do Website
1. Script que extrai todas as pessoas e a informação disponibilizada de cada uma, devolve um *dataset* de pessoas em JSON.
```sh
$ python3 script-pessoas.py (yaml)?
```
**Nota**: Existe a possibilidade de indicar que o *dataset* de pessoas seja em YAML apenas tem-se que indicar ao correr o script com a tag *yaml*.

### Extração das Famílias do Website
2. Script que extrai todas as famílias e as pessoas que fazem parte destas, devolve um *dataset* de famílias em JSON.
```sh
$ python3 script-familias.py
```
### Extração de informação de uma pessoa do Website
3. Script que extrai a informação de uma determinada pessoa, devolve a informação em YAML.
```sh
$ python3 script-pessoa-simples.py $idPessoa
```

### Obter informação do Dataset
4. Script que permite efetuar *queries* ao *dataset* de pessoas criado previamente, com auxílio da *framework* json-server.
```sh
$ cd ${Path_to_dataset}
$ json-server --watch db-pessoas.json
```
```sh
$ python3 queries.py
```

---
## Dependências
* **json** - conversão da informação em JSON ;
* **yaml** - conversão da informação em YAML ;
* **requests** - efetuar os pedidos ao Website e JSON-Server ;
* **jjcli** - *parsing* e encontrar informação específica com expressões regulares ;
* **bs4 -> BeautifulSoup** - *parsing* HTML ;
* **sys** - fornece o acesso a algumas variáveis passadas como *input* e a funções que permitem terminar a execução dos scripts ;
* **re** - operação de correspondências com expressões regulares ;
* **readline** - facilita a leitura como guardar o histórico de interação com o utilizador ;

* <ins>Nota</ins> - para executar o Script 4 é necessário instalar o *Node.js*, e posteriormente instalar o json-server `npm i json-server` ;
