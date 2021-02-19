%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Invariantes Estruturais e Referenciais.
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Definicoes iniciais

:- op( 900,xfy,'::' ).
:- dynamic filho/2.
:- dynamic pai/2.
:- dynamic idade/2.



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :- Questao, !, fail.
nao( Questao ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado filho: Filho,Pai -> {V,F}

filho( joao,jose ).
filho( jose,manuel ).
filho( carlos,jose ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado idade: Pessoa,Idade -> {V,F}

idade( joao,21 ).
idade( jose,42 ).
idade( manuel,63 ).
idade( carlos,19 ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Invariante Estrutural : nao permitir a insercao de conhecimento repetido
%                       : nao pode existir mais do que uma ocorrencia da mesma evidencia na relacao filho:

+filho( F,P ) :: (solucoes((F,P),(filho( F,P )),S), comprimento( S,N ),  N == 1).

%                       : nao pode existir mais do que uma ocorrencia da mesma evidencia na relacao pai:

+pai( P,F )   :: (solucoes((P,F),(pai( P,F )),S),   comprimento( S,N ),  N == 1).

%                       : nao pode existir mais do que uma ocorrencia da mesma evidencia na relacao neto:

+neto( N,A )  :: (solucoes((N,A),(neto( N,A )),S),  comprimento( S,N ),  N == 1).

%                       : nao pode existir mais do que uma ocorrencia da mesma evidencia na relacao avo:

+avo( A,N )   :: (solucoes((A,N),(avo( A,N )),S),   comprimento( S,N ),  N == 1).

%                       : nao pode existir mais do que uma ocorrencia da mesma evidencia na relacao descendente:

+descendente( D,A,G ) :: (solucoes((D,A,G),(descendente( D,A,G )),S), comprimento( S,N ),  N == 1).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Invariante Referencial : nao admitir mais do que 2 progenitores para um mesmo individuo
%                        : nao pode existir mais do que 2 progenitores para um dado individuo, na relacao filho:
+filho( F,P ) :: (solucoes((Ps),(filho( F,Ps )),S), comprimento( S,N ), N =< 2).

%                        : nao pode existir mais do que 2 progenitores para um dado individuo, na relacao pai:

+pai( P,F )   :: (solucoes((Ps),(pai( P,Ps )),S), comprimento( S,N ), N =< 2).

%                        : nao pode existir mais do que 4 individuos identificados como avo na relacao neto:

+neto( N,A )  :: (solucoes((Ps),(neto( Ps,A )),S), comprimento( S,N ), N =< 4).

%                        : nao pode existir mais do que 4 individuos identificados como avo na relação avo:

+avo( N,A )  :: (solucoes((Ps),(avo( A,Ps )),S), comprimento( S,N ), N =< 4).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Invariante Referencial: nao e possivel remover filhos para os quais exista registo de idade

-filho( F,P ) :: (solucoes((F),(idade(F,I)),S), comprimento( S,N ), N == 0).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a evolucao do conhecimento

evolucao( Termo ) :- solucoes( Invariante,+Termo::Invariante,Lista ), insercao( Termo ), teste( Lista ).

insercao( Termo ) :- assert( Termo ).
insercao( Termo ) :- retract( Termo ), !, fail.
	
teste( [] ).
teste( [R|LR] ) :- R, teste( LR ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que permite a involucao do conhecimento

involucao( Termo ) :- solucoes( Invariante,-Termo::Invariante,Lista ), remocao( Termo ), teste( Lista ).

remocao( Termo ) :- retract( Termo ).
remocao( Termo ) :- assert( Termo ), !, fail.


%--------------------------------- - - - - - - - - - -  -  -  -  -

solucoes( X,Y,Z ) :- findall( X,Y,Z ).

comprimento( S,N ) :- length( S,N ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -