%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Formulacao de Problemas de Pesquisa e Resolucao de Problemas utilizando PESQUISA NAO INFORMADA
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais
 
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Definicoes iniciais

:- dynamic jarros/2.
:- dynamic inicial/1.
:- dynamic final/1.
:- dynamic transicao/3.
:- dynamic encher/1.
:- dynamic esvaziar/1.
:- dynamic transferir/2.



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Tipo de problema : ESTADO UNICO porque o agente "sabe" exatamente o estado em que estara, solucao e uma sequencia.

% Conjunto de Estados: conjunto de pares ordenados de inteiros (X,Y), X->[0,8] e Y->[0,5]
% Estado inicial: (0,0)
% Estados finais: (4,_) e (_,4)
% Operadores: encher(), esvaziar(), transferir()
% Teste objetivo: final(jarros(4,_)) ou final(jarros(_,4))
% Custo da Solucao: Cada acao custa 1 (custo total = numero de passos da solucao)


%--------------------------------- - - - - - - - - - -  -  -  -  -
% estado inicial:
inicial(jarros(0,0)).

% estado final:
final(jarros(4,_)).
final(jarros(_,4)).

% transicoes possiveis transicao: EstadoAtualxOperacaoxEstadoFinal
transicao(jarros(V1,V2), encher(1), jarros(8,V2)) :- V1 < 8.
transicao(jarros(V1,V2), encher(2), jarros(V1,5)) :- V2 < 5.

transicao(jarros(V1,V2), esvaziar(1), jarros(0,V2)) :- V1 > 0.
transicao(jarros(V1,V2), esvaziar(2), jarros(V1,0)) :- V2 > 0.

transicao(jarros(V1,V2), transferir(1,2), jarros(NV1,NV2)) :- V1>0, NV1 is max(V1-5+V2,0), NV1<V1, NV2 is V2+V1-NV1.
transicao(jarros(V1,V2), transferir(2,1), jarros(NV1,NV2)) :- V2>0, NV2 is max(V2-8+V1,0), NV2<V2, NV1 is V1+V2-NV2.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% pesquisa em arvore:


% estrategia "primeiro em profundidade"
resolvedf(Solucao):- inicial(EstadoInicial), resolvedf(EstadoInicial,[EstadoInicial],Solucao).
resolvedf(Estado,_,[]) :- final(Estado), !.
resolvedf(Estado,Historico,[Movimento|Solucao]) :- transicao(Estado, Movimento, Estado1), \+ memberchk(Estado1,Historico), resolvedf(Estado1, [Estado1|Historico], Solucao).

todasSolucoes(L):- findall((S,C),(resolvedf(S),length(S,C)),L).

melhordf(S, Custo):- findall((S,C), (resolvedf(S), length(S,C)), L), minimo(L,(S,Custo)).


% estrategia "primeiro em largura"
resolvebf(Solucao):- inicial(InicialEstado), resolvebf([(InicialEstado, [])|Xs]-Xs, [], Solucao).
resolvebf([(Estado, Vs)|_]-_, _, Rs):- final(Estado),!, inverso(Vs, Rs).
resolvebf([(Estado, _)|Xs]-Ys, Historico, Solucao):- membro(Estado, Historico),!, resolvebf(Xs-Ys, Historico, Solucao).
resolvebf([(Estado, Vs)|Xs]-Ys, Historico, Solucao):- setof((Move, Estado1), transicao(Estado, Move, Estado1), Ls), atualizar(Ls, Vs, [Estado|Historico], Ys-Zs), resolvebf(Xs-Zs, [Estado|Historico], Solucao).

atualizar([], _, _, X-X).
atualizar([(_, Estado)|Ls], Vs, Historico, Xs-Ys):- membro(Estado, Historico),!, atualizar(Ls, Vs, Historico, Xs-Ys).
atualizar([(Move, Estado)|Ls], Vs, Historico, [(Estado, [Move|Vs])|Xs]-Ys):- atualizar(Ls, Vs, Historico, Xs-Ys).

melhorbf(S, Custo):- findall((S,C), (resolvebf(S), length(S,C)), L), minimo(L,(S,Custo)).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Predicados auxiliares
membro(X, [X|_]).
membro(X, [_|Xs]):- membro(X, Xs).

membros([], _).
membros([X|Xs], Members):- membro(X, Members), membros(Xs, Members).

inverso(Xs, Ys):- inverso(Xs, [], Ys).
inverso([], Xs, Xs).
inverso([X|Xs],Ys, Zs):- inverso(Xs, [X|Ys], Zs).

nao( Questao ) :- Questao, !, fail.
nao( Questao ).	

minimo([(P,X)],(P,X)).
minimo([(Px,X)|L],(Py,Y)):- minimo(L,(Py,Y)), X>Y. 
minimo([(Px,X)|L],(Px,X)):- minimo(L,(Py,Y)), X=<Y. 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -