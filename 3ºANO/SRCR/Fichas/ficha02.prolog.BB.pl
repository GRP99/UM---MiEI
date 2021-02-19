%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em Logica.
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que calcule a soma de dois valores:

soma2( X,Y,R ) :- R is X + Y.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que calcula a soma de tres valores:

soma3( X,Y,Z,R ) :- R is X + Y + Z.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que calcula a soma de um conjunto de valores:

somaN( [],0 ).
somaN( [H|T],R ) :- somaN( T,RR), R is H + RR.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que aplica uma operacao aritmetica a dois valores:

operacao( X,adicao,Y,R ) :- R is X + Y.
operacao( X,subtracao,Y,R ) :- R is X - Y.
operacao( X,multiplicacao,Y,R ) :- R is X * Y.
operacao( X,divisao,Y,R ) :- R is X / Y.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que aplica uma operacao aritmetica a um conjunto de valores:

operacaoN([],adicao,0).
operacaoN([H|T],adicao,R) :-  operacaoN(T,adicao,RR), R is H + RR.
operacaoN([],multiplicacao,1). 
operacaoN([H|T],multiplicacao,R) :- operacaoN(T,multiplicacao,RR), R is H * RR.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que calcula o maior valor entre dois valores:

maior2( X,Y,R ) :- ( X>Y -> R is X; R is Y).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que calcula o maior valor entre tres valores:

maior3( X,Y,Z,R ) :- ( X>Y,X>Z -> R is X; (Y>X,Y>Z -> R is Y; (Z>X,Z>Y -> R is Z; R is Z))).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que calcula o maior de um conjunto de valores:

maiorN([R],R).
maiorN([H|T],R) :- maiorN(T,RR), (RR > H -> R = RR; R = H).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que calcula o menor valor entre dois valores:

menor2( X,Y,R ) :- ( X<Y -> R is X; R is Y).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que calcula o menor valor entre tres valores:

menor3( X,Y,Z,R ) :- ( X<Y,X<Z -> R is X; (Y<X,Y<Z -> R is Y; (Z<X,Z<Y -> R is Z; R is Z))).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que calcula o menor de um conjunto de valores:

menorN([R],R).
menorN([H|T],R) :- menorN(T,RR), (RR < H -> R = RR; R = H).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que calcule a media aritmetica de um conjunto de valores:

comprimento(0,[]).
comprimento(N,[_|R]) :- comprimento( N1,R ), N is 1 + N1.

somatorio( 0,[] ).
somatorio( X,[Y|R] ) :- somatorio( S,R ), X is S+Y.

media( L,X ) :- comprimento( N,L ), somatorio( S,L ), X is S/N.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que ordene de modo crescente uma sequencia de valores:

maior( X,Y ) :- X > Y.

modocrescente([X]).
modocrescente([X,Y|T]) :- maior(Y,X), modocrescente([Y|Z]).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que ordene de modo decrescente uma sequencia de valores:

menor( X,Y ) :- X < Y.

mododecrescente([X]).
mododecrescente([X,Y|T]) :- menor(Y,X), mododecrescente([Y|Z]).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -