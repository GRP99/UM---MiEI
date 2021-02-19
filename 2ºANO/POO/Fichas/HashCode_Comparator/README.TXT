------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

Este projecto tem como objectivo mostrar a utilização de estruturas baseadas em 
Hash e em Tree.
Em termos de Hash, utiliza-se o HashSet. 
A utilização de Tree reflecte-se nas classes TreeSet e TreeMap.

Para a correcta utilização destas colecções é necessário:
- definir a função de Hash, hashcode(), nos objectos contidos na colecção
- definir a função de comparação, para a correcta utilização
de estruturas ordenadas. Isso pode ser feito de duas formas:
a) definir um método compareTo, na classe e que corresponderá à ordem natural
b) definir uma classe comparadora, com a implementação de um método compare, que permite ter a
possibilidade de parametrizar as estruturas de dados pela ordem de comparação desejada.


ConjuntoPontos_Hash, é uma classe que guarda os objectos Ponto2D numa colecção 
implementada em HashSet.

ConjuntoPontos_Tree, é uma classe que guarda os objectos Ponto2D numa colecção
ordenada implementada em TreeSet.

TabuleiroApostas é uma classe que mantém uma TreeMap em que as chaves são objectos
complexos (normalmente utilizámos apenas tipos simples ou String para as chaves)..
Dessa forma é necessário parametrizar o TreeMap pela método de comparação que
determina a ordem relativa de duas instâncias de Ponto2D.

Nota: complete as classes com os métodos que falta desenvolver.