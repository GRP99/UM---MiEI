%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Grafos: representacao e operacoes.
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais
 
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).
 

%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Definicoes iniciais
 
:- dynamic aresta/2.
:- dynamic grafo/2.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Representar cada aresta separadamente: 

aresta( a,b ).
aresta( c,d ).
aresta( c,f ).
aresta( d,f ).
aresta( f,g ).

% Representar todo o grafo como um objeto, que contem um conjunto de nodos e um conjunto arestas:

g(grafo([a,b,c,d,e,f,g], [aresta(a,b),aresta(c,f),aresta(c,d),aresta(d,f),aresta(f,g)])).

% Representar um grafo associando a cada nodo o conjunto dos seus nodos adjacentes:

[n(a,[b]),n(b,[a]),n(c,[d,f]),n(d,[c,f]),n(e,[]),n(f,[c,d]),n(g,[f])].


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado que verifica se os nos X e Y sao adjacentes no grafo G:

adjacente(X,Y,grafo(_,Es)) :- member(aresta(X,Y),Es).
adjacente(X,Y,grafo(_,Es)) :- member(aresta(Y,X),Es).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para encontrar um caminho aciclico P do no A para o no B no grafo G:

caminho(G,A,B,P) :- caminho1(G,A,[B],P).

caminho1(_,A,[A|P1],[A|P1]).
caminho1(G,A,[Y|P1],P) :- adjacente(X,Y,G), \+ memberchk(X,[Y|P1]), caminho1(G,A,[X,Y|P1],P).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para encontrar um caminho fechado P, que começa e acabe no no A, no grafo G:

ciclo(G,A,P) :- adjacente(B,A,G), caminho(G,A,B,P1), length(P1,L), L > 2, append(P1,[A],P).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -