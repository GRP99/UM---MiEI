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
% Extensao do predicado filho: Filho,Pai -> {V,F}

filho( joao,jose ).
filho( jose,manuel ).	
filho( carlos,jose ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado pai: Pai,Filho -> {V,F}

pai( paulo,filipe ).
pai( paulo,maria ).

pai( P,F ) :- filho( F,P ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado avo: Avo,Neto -> {V,F}

avo( antonio,nadia ).

avo( A,N ) :- filho( N,X ), pai( A,X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado neto: Neto,Avo -> {V,F}

neto( nuno,ana ).

neto( N,A ) :- avo( A,N ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado masculino: Homem -> {V,F}

masculino( joao ).
masculino( jose ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado feminino: Mulher -> {V,F}

feminino( maria ).
feminino( joana ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que permita determinar se uma pessoa descende de outra pessoa: Descendente,Ascendente -> {V,F}

descende( D,A ) :- filho( D,A ).
descende( D,A ) :- filho( D,X ), descende( X,A ).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que permita determinar o grau de descendencia entre duas pessoas: Descendente,Ascendente,Grau -> {V,F}

graudescendencia( D,A,1 ):- filho( D,A ).
graudescendencia( D,A,G ):- filho( D,X ), graudescendencia( X,A,N ), G is N+1.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado que determina se o indivíduo A é avô de N pela utilizacao do predicado que determina o grau de descendencia entre dois individuos:

avoGD( A,N ) :- graudescendencia( N,A,2 ), descende( N,A ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado capaz de determinar se o individuo X é bisavo de Y:

bisavo( X,Y ) :- graudescendencia( Y,X,3 ), descende( Y,X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado capaz de determinar se o individuo X é trisavo de Y:

trisavo( X,Y ) :- graudescendencia( Y,X,4 ), descende( Y,X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao de um predicado capaz de determinar se o individuo X é tetraneto de Y.

tetraneto( X,Y ) :- graudescendencia( X,Y,5 ), descende( X,Y ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -