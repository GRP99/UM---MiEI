# TPC 4

## 1º Tarefa -> WEBSITE
Partindo do [XML] sobre os Arqueossítios do Nordeste Português realizou-se um XSLT (*eXtensible Stylesheet Language*) que permitisse processar este XML num website gerando para o efeito um ficheiro por cada arqueossítio registado, estes ficheiros encontram-se numerados sequencialmente. Este website apresenta um índice agrupado e ordenado inicialmente por concelhos no qual é listado os arqueossítios, em que cada um tem associado um link que remete à página de cada arqueossítio com a informação fornecida.


## 2º Tarefa -> SERVIDOR
Assente no website gerado na tarefa anterior pretendeu-se implementar um servidor desenvolvido em *Node.js* que permita devolver ao utilizador a página *html* sobre um determinado arqueossítio para tal este tem de efectuar o pedido através do browser . Este servidor foi construído de forma a responder a pedidos na porta 7777. Considerou-se um pedido válido : **localhost:7777/arqs/XXX** , onde XXX corresponde ao número do ficheiro arqXXX.html criado na tarefa anterior. Tem-se em consideração como correcto também o seguinte pedido : **localhost:7777/arqs/** , correspondendo a apresentar o índice ao utilizador. Qualquer outro pedido além dos referidos é considerado inválido.

[XML]:http://www4.di.uminho.pt/~jcr/XML/didac/xmldocs/arq.xml