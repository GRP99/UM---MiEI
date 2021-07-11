# Trabalho Prático nº 2
## YACC

### Objetivos
Este trabalho prático teve como principais objectivos:
* aumentar a experiência de uso do ambiente Linux, da linguagem imperativa C (para codificação das estruturas
de dados e respectivos algoritmos de manipulação), e de algumas ferramentas de apoio à programação;
* rever e aumentar a capacidade de escrever gramáticas independentes de contexto (GIC), que satisfaçam a condição LR(), para criar Linguagens de Domínio Específico (DSL);
* desenvolver processadores de linguagens segundo o método da tradução dirigida pela sintaxe, suportado numa gramática tradutora (GT);
* utilizar geradores de compiladores como o par flex/yacc.

## Conversor toml2json
Tarefas realizadas:
1. Especificação da gramática concreta da linguagem de entrada.
2. Desenvolviemnto de um reconhecedor léxico e sintáctico para essa linguagem com recurso ao par de ferramentas geradoras
Flex/Yacc.
3. Construção de um gerador de código que produza a resposta solicitada. Esse gerador de código foi construído associando
acções semânticas de tradução às produções da gramática, recorrendo uma vez mais ao gerador Yacc.