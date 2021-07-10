# Trabalho prático
## Gestão de Vendas

### Descrição
Neste trabalho pretendeu-se construir um protótipo de um sistema de gestão de inventário e vendas. O sistema é constituído por vários programas: manutenção de artigos, servidor de vendas, cliente de vendas, e agregador de dados.

1. [**Manutenção de artigos**](ma.c) - Permite a inserção de novos artigos (especificando o nome e preço de venda), ou alterar atributos de um dado artigo (nome ou preço). Cada artigo tem um código numérico, atribuído na criação como o próximo de uma sequência (1, 2, 3, . . . ). Este programa recebe todo o seu input pelo seu *stdin*, lendo linhas de texto com o formato do exemplo seguinte:
```
$ ma
i <nome> <preço> --> insere novo artigo, mostra o código
n <código> <novo nome> --> altera nome do artigo
p <código> <novo preço> --> altera preço do artigo
...
<EOF>
```

2. [**Servidor de vendas**](sv.c) - Controla stocks, recebe pedidos do cliente de vendas, e regista as vendas efectuadas. A quantidade em stock de cada artigo é mantida num único ficheiro STOCKS, para todos os artigos. Cada venda efectuada é registada, acrescentando uma entrada a um ficheiro VENDAS, contendo código, quantidade e montante total da venda.

3. [**Cliente de vendas**](cv.c) - Interage com o servidor de vendas, solicitando-lhe a execução de operações que se distinguem facilmente pelo número de parâmetros introduzidos. Uma das operações retorna a quantidade em stock e o preço de um artigo (identificado pelo código). A outra operação permite efectuar vendas ou entrada em stock, especificando o código e quantidade (negativa ou positiva, respectivamente):
```
$ cv
<código_numérico> --> mostra no stdout stock e preço
<código_numérico> <quantidade> --> actualiza stock e mostra novo stock
...
<EOF>
```

4. [**Agregador**](ag.c) - Funciona como filtro. Recebe pelo *stdin* entradas no formato do ficheiro de vendas, até end-of-file. Nessa altura produz para o stdout os dados agregados de cada artigo com vendas efectuadas, contendo o código do artigo, a quantidade total e o montante total de vendas do artigo respectivo (mantendo o formato do ficheiro de vendas).