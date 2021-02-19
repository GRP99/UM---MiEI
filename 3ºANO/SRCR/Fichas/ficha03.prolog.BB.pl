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
% Extensao do predicado «pertence» que verifica se um elemento existe dentro de uma lista de elementos:

pertence( R,[R|_] ).
pertence( R,[_|T] ) :- pertence( R,T ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado «comprimento» que calcula o numero de elementos existentes numa lista:

comprimento( [],0 ).
comprimento( [H|T],R ) :- comprimento( Y,RR ), N is RR+1.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado «diferentes» que calcula a quantidade de elementos diferentes existentes numa lista:: diferentes(a,[a,b,c,d,a],L).

diferentes( _,[],0 ).
diferentes( X,[X|T],R ) :- !, diferentes( X,T,RR ), R is RR + 1.
diferentes( X, [_|T],R ) :- diferentes( X,T,R ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado «apaga1» que apaga a primeira ocorrencia de um elemento de uma lista:: apaga1(a,[a,b,c,d,a],L).

apaga1( _,[],[] ).	
apaga1( X,[X|T],T ) :- !.
apaga1( X,[Y|T],[Y|TT] ) :- apaga1( X,T,TT ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado «apagaT» que apaga todas as ocorrencias de um dado elemento numa lista:: apagaT(a,[a,b,c,d,a],L).

apagaT( _,[],[]).	
apagaT( X,[X|T],TT ) :- !, apagaT( X,T,TT ).
apagaT( X,[Y|T],[Y|TT] ) :- apagaT( X,T,TT ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado «adicionar» que insere um elemento numa lista, sem o repetir:

listaitems( _,[],[] ).
listaitems( List, [H|T],R ) :- ( member(H,List) -> R = Res;  R = [H|Res]), listaitems(List, T, Res).

adicionar( X,Y,L ) :- listaitems( X,Y,R ), append( X,R,L ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado «concatenar», que resulta na concatenação dos elementos da lista L1 com os elementos da lista L2:

concatenar([],L,L).
concatenar( [X|L1],L2,[X|L3] ) :- concatenar(L1,L2,L3).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -