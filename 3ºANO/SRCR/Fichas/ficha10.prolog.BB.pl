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

:- dynamic estrada/4.
:- dynamic grafo/2.
:- dynamic g/1.


%--------------------------------- - - - - - - - - - -  -  -  -  -
g( grafo([madrid, cordoba, sevilla, jaen, granada, huelva, cadiz], [estrada(huelva, sevilla, a49, 94), estrada(sevilla, cadiz,ap4, 125), estrada(sevilla, granada, a92, 256), estrada(granada, jaen,a44, 97), estrada(sevilla, cordoba, a4, 138), estrada(jaen,madrid, a4, 335), estrada(cordoba, madrid, a4, 400)])).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para encontrar um caminho aciclico P do no A para o no B no grafo G, devolvendo, ainda, os quilometros (Km) e as estradas percorridas (Es):

info( X,Y,grafo( N,LA ),Es,D ) :- member(estrada( X,Y,Es,D ),LA ).
info( X,Y,grafo( N,LA ),Es,D ) :- member(estrada( Y,X,Es,D ),LA ). 

caminho( G,A,B,P,Km,Es ) :- caminho1( G,A,[B],P,Km,Es ).

caminho1( _,A,[A|P1],[A|P1],0,[] ).
caminho1( G,A,[Y|P1],P,Km,Es ) :-  info( X,Y,G,E,D ), \+ memberchk( X,[Y|P1] ), caminho1( G,A,[X,Y|P1],P,Km2,Es2 ), Km is Km2 + D, append( Es2,[E],Es ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para encontrar um caminho fechado P, que começa e acabe no no A, no grafo G, devolvendo, ainda, os quilometros (Km) e as estradas percorridas (Es):

ciclo( G,A,P,Km,Es ) :- info( B,A,G,E,D ), caminho( G,A,B,P1,Km2,Es2 ), append( P1,[A],P ), Km is D + Km2, append( Es2,[E],Es), length( P,L ), L > 3.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -