%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Programacao em Logica.
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado capaz de caracterizar os numeros pares:

par(X) :- 0 is mod(X, 2).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado capaz de caracterizar os numeros impares:

impar(X) :- 1 is mod(X, 2).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado capaz de caracterizar os numeros naturais:

natural(0).
natural(N) :- N>0, N1 is N - 1, natural(N1), \+(float(N)).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado capaz de caracterizar os numeros inteiros:

inteiro(0).
inteiro(N) :- ( N<0 -> N1 is N + 1, inteiro(N1), \+ (float(N)); N1 is N - 1, inteiro(N1), \+(float(N))).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que determine os divisores de um numero natural

divisores( N,D ) :- between( 1,N,D ), 0 is N mod D.

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que verifica se um numero natural e primo

edivisor( N,L ) :- N mod L =:= 0.
edivisor( N,L ) :- L*L<N, L2 is L+2, edivisor(N,L2).

is_prime( 2 ).
is_prime( 3 ).
is_prime( P ) :- integer(P), P>3, P mod 2 =\= 0, \+edivisor(P,3).  

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que calcule o m.d.c. entre 2 numeros naturais:

mdc( X,Y,R ) :- X>Y, X1 is X-Y, mdc( X1,Y,R ).
mdc( X,Y,R ) :- Y>X, Y1 is Y-X, mdc( X,Y1,R ).
mdc( X,X,X ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -