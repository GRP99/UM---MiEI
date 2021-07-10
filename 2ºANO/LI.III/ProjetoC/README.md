# Projecto de C
## GestVendas

### Objetivo
Os objectivos deste trabalho, foi apresentar aos alunos de forma pragmática, os desafios que se colocam a quem concebe e programa aplicações software (em qualquer linguagem), quando passamos a realizar a designada programação em larga escala, ou seja, aplicações com grandes volumes de dados e com mais elevada complexidade algorítmica e estrutural.

### Aplicação
A aplicação possui uma arquitectura na qual, se indentifica de
forma clara as fontes de dados, a sua leitura e os módulos de dados a construir: 
* **Leitura**: função ou parte do código no qual é realizada a leitura (e tratamento) dos dados dos ficheiros de dados (Clientes, Produtos e Vendas_1M);
* **Catálogo de Produtos**: módulo de dados onde se guarda os códigos válidos de todos os produtos do ficheiro [Produtos.txt](src/Produtos.txt), organizados por índice alfabético, o que permite, de forma eficaz, saber quais são os produtos cujos códigos começam por uma dada letra do alfabeto, quantos são, etc.;
* **Catálogo de Clientes**: módulo de dados onde se guarda os códigos válidos de todos os clientes do ficheiro [Clientes.txt](src/Clientes.txt), organizados também por índice alfabético;
* **Facturação Global**: módulo de dados que contém as estruturas de dados responsáveis pela resposta eficiente a questões quantitativas que relacionam os produtos às suas vendas mensais, em modo Normal (N) ou em Promoção (P), para cada um dos casos guardando o número de vendas e o valor total de facturação de cada um destes tipos. Este módulo referencia todos os produtos, mesmo os que nunca foram vendidos. Este módulo não contém qualquer referência a clientes, mas deve é capaz de distinguir os valores obtidos em cada filial;
* **Gestão de Filial**: módulo de dados que, para uma Filial, contém as estruturas de dados adequadas à representação dos relacionamentos, fundamentais para a aplicação, entre produtos e clientes, ou seja, para cada produto, saber quais os clientes que o compraram, quantas unidades cada um comprou, em que mês, etc.