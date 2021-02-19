%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao a Programacao em Logica.
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais
 
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).
 


%--------------------------------- - - - - - - - - - -  -  -  -  - 
% SICStus PROLOG: Definicoes iniciais
 
:- op( 1100,xfy,'??' ).
:- dynamic '-'/1.
:- dynamic mamifero/1.
:- dynamic morcego/1.
 


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do meta-predicado si: Questao,Resposta -> {V,F}
%                            	 Resposta = { verdadeiro,falso,desconhecido }
 
si( Questao,verdadeiro ) :- Questao.
si( Questao,falso ) :- -Questao.
si( Questao,desconhecido ) :- nao( Questao ), nao( -Questao ).
 
Questao ?? verdadeiro :- Questao.
Questao ?? falso :- -Questao.
Questao ?? desconhecido :- nao( Questao ), nao( -Questao ).
 

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do meta-predicado nao: Questao -> {V,F}
 
nao( Questao ) :- Questao, !, fail.
nao( Questao ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Se a entidade X e uma ave entao a caracteristica do voo esta presente nessa entidade:

voa( X ) :- ave( X ), nao( excecao( voa( X ))).

voa( X ) :- excecao( -voa( X )).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Se X e um mamifero então e mentira que X exiba a caracteristica do voo:

-voa( X ) :- mamifero( X ), nao( excecao(-voa( X ))).

-voa( X ) :- excecao( voa( X )).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O Tweety nao voa:

-voa( tweety ).

excecao( voa( tweety ) ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O Pitigui e uma ave:

ave( pitigui ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Os canarios sao aves:

ave( X ) :- canario( X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Os periquitos sao aves:

ave( X ) :- periquito( X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O Piupiu e um canario:

canario( piupiu ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O Silvestre e um mamifero:

mamifero( silvestre ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Os caes sao mamiferos:

mamifero( X ) :- cao( X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Os gatos sao mamiferos:

mamifero( X ) :- gato( X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O Boby e um cao:

cao( boby ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% As avestruzes sao aves:

ave( X ) :- avestruz( X ).

excecao( voa( X ) ) :- avestruz( X ).

-voa( X ) :- avestruz(X).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Os pinguins sao aves:

ave( X ) :- pinguim( X ).

excecao( voa( X ) ) :- pinguim( X ).

-voa( X ) :- pinguim( X ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% A Trux e uma avestruz:

avestruz( trux ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O Pingu e um pinguim:

pinguim( pingu ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Os morcegos sao mamiferos:

mamifero( X ) :- morcego( X ).

voa(X) :- morcego(X).

excecao( -voa(X) ) :- morcego(X).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O Batemene e um morcego:

morcego( batemene ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -