%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 	SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3 - Componente Individual
%--------------------------------- - - - - - - - - - -  -  -  -  -
%  					 Gonçalo Pinto - a83732
%--------------------------------- - - - - - - - - - -  -  -  -  -
% TEMA:	Programacao em Logica Estendida, Metodos de Resolucao de Problemas e de Procura
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).
:- set_prolog_flag( toplevel_print_options,[quoted(true), portrayed(true),max_depth(0)] ).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Definicoes iniciais

:- op(900,xfy,'::').
:- dynamic paragem/11.
:- dynamic ligacao/3.
:- include('C:/base_conhecimento.pl').



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 								  PREDICADOS AUXILIARES
%--------------------------------- - - - - - - - - - -  -  -  -  -

solucoes(X,Y,Z) :- findall(X,Y,Z).

comprimento(S,N) :- length(S,N).

pertence(X,Y) :- member(X,Y).

naopertence(X,Y) :- nonmember(X,Y).

junta(X,Y,L) :- append(X,Y,L).

simplifica([[H|T]],[H|T]).

simples([L],L).

apaga1(_,[],[]).
apaga1(X,[X|Y],T) :- apaga1(X,Y,T).
apaga1(X,[H|Y],[H|R]) :- apaga1(X,Y,R).

apagaRep([],[]).
apagaRep([X|Y],[X|L1]) :- apaga1(X,Y,L), apagaRep(L,L1).

ultimo([H],H).
ultimo([H|T],X) :- ultimo(T,X).

inverso(Xs,Ys) :- inverso(Xs,[],Ys).
inverso([],Xs,Xs).
inverso([X|Xs],Ys,Zs) :- inverso(Xs,[X|Ys],Zs).

conc([],Y,Y).
conc([A|X],Y,[A|Z]) :- conc(X,Y,Z).

seleciona(E,[E|Xs],Xs).
seleciona(E,[X|Xs],[X|Ys]) :- seleciona(E,Xs,Ys).

teste([]).
teste([X|Y]) :- X, teste(Y).


%--------------------------------- - - - - - - - - - -  -  -  -  -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Identifica uma ligacao:
adjacente(O,D,C) :- ligacao(O,D,C).


%--------------------------------- - - - - - - - - - -  -  -  -  -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Calcular um trajeto entre dois pontos: 
%----------(pesquisa nao informada - profundidade)
percursodf( Inicial,Final,[Inicial|Trajeto] ) :- resolvedf( Final,Inicial,Trajeto ).

resolvedf( Final,Paragem,[] ) :- Final is Paragem.
resolvedf( Final,Paragem,[ProxPar|Trajeto] ) :- adjacente(Paragem,ProxPar,_), resolvedf( Final,ProxPar,Trajeto ).	


%----------(pesquisa nao informada - largura)
percursobf( Inicial,Final,Solucao ) :- resolvebf( Final,[[Inicial]],S ), inverso(S,Solucao).

resolvebf( Final,[[Estado|Percurso]|_],[Estado|Percurso] ) :- Final is Estado.
resolvebf( Final,[Percurso|Historico],Solucao ) :- atualiza( Percurso,NovoPercurso ), junta(Historico,NovoPercurso,NovoHistorico), resolvebf( Final,NovoHistorico,Solucao ).

atualiza( [Estado|Percurso],NovoPercurso )  :- bagof( [NovoEstado,Estado|Percurso],(adjacente(Estado,NovoEstado,_),naopertence(NovoEstado,[Estado|Percurso])),NovoPercurso ), !.
atualiza( Percurso,[] ).   


%----------(pesquisa informada - a estrela)
percursoaestrela( Inicial,Final,Caminho/Custo ) :- estima(Inicial,Estima), resolveaestrela( Final,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

resolveaestrela( Final,Caminhos,Caminho ) :- obtem_melhor( Caminhos,Caminho ), Caminho=[Paragem|_]/_/_, Paragem is Final.
resolveaestrela( Final,Caminhos,SolucaoCaminho ) :- obtem_melhor( Caminhos,MelhorCaminho ), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_aestrela( MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												resolveaestrela( Final,NovoCaminhos,SolucaoCaminho ).		

obtem_melhor( [Caminho],Caminho ) :- !.
obtem_melhor( [Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos],MelhorCaminho ) :- Custo1 + Est1 =< Custo2 + Est2, !, obtem_melhor( [Caminho1/Custo1/Est1|Caminhos],MelhorCaminho ). %>
obtem_melhor( [_|Caminhos],MelhorCaminho ) :- obtem_melhor( Caminhos,MelhorCaminho ).

expande_aestrela( Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,seguinte( Caminho,NovoCaminho ),ExpCaminhos).

seguinte( [Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/Est ) :- adjacente(Nodo,ProxNodo,_), \+member(ProxNodo,Caminho), NovoCusto is Custo + 1, estima(ProxNodo,Est).


%----------(pesquisa informada - gulosa)
percursogulosa( Inicial,Final,Caminho/Custo ) :- estima(Inicial,Estima), resolvegulosa( Final,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

resolvegulosa( Final,Caminhos,Caminho ) :- obtem_melhor_g( Caminhos,Caminho ), Caminho=[Paragem|_]/_/_, Paragem is Final.
resolvegulosa( Final,Caminhos,SolucaoCaminho ) :- obtem_melhor_g( Caminhos,MelhorCaminho ), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_gulosa( MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												resolvegulosa( Final,NovoCaminhos,SolucaoCaminho ).		

obtem_melhor_g( [Caminho],Caminho ) :- !.
obtem_melhor_g( [Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos],MelhorCaminho ) :- Est1 =< Est2, !, obtem_melhor_g( [Caminho1/Custo1/Est1|Caminhos],MelhorCaminho ). %>
obtem_melhor_g( [_|Caminhos],MelhorCaminho ) :- obtem_melhor_g( Caminhos,MelhorCaminho ).

expande_gulosa( Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,seguinte(Caminho,NovoCaminho),ExpCaminhos).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Selecionar apenas algumas das operadoras de transporte para um determinado percurso:
%----------(pesquisa nao informada - profundidade)
percursoOperadorasdf( Inicial,Final,Operadoras,[Inicial|Trajeto] ) :- paragem(Inicial,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), percursoOpdf( Final,Inicial,Operadoras,Trajeto ).

percursoOpdf( Final,Paragem,Operadoras,[] ) :-  paragem(Paragem,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), Final is Paragem.
percursoOpdf( Final,Paragem,Operadoras,[ProxPar|Trajeto] ) :- adjacente(Paragem,ProxPar,_), paragem(ProxPar,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), percursoOpdf( Final,ProxPar,Operadoras,Trajeto ).	


%----------(pesquisa nao informada - largura)
percursoOperadorasbf( Inicial,Final,Operadoras,Solucao ) :- paragem(Inicial,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), percursoOpbf( Final,Operadoras,[[Inicial]],S ), inverso(S,Solucao).

percursoOpbf( Final,Operadoras,[[Estado|Percurso]|_],[Estado|Percurso] ) :- paragem(Estado,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), Final is Estado.
percursoOpbf( Final,Operadoras,[Percurso|Historico],Solucao ) :- atualizaOp( Operadoras,Percurso,NovoPercurso ), junta(Historico,NovoPercurso,NovoHistorico), percursoOpbf( Final,Operadoras,NovoHistorico,Solucao ).

atualizaOp( Operadoras,[Estado|Percurso],NovoPercurso ) :- bagof( [NovoEstado,Estado|Percurso],(adjacente(Estado,NovoEstado,_),naopertence(NovoEstado,[Estado|Percurso]),paragem(NovoEstado,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras)),NovoPercurso ), !.
atualizaOp( Operadoras,Percurso,[] ).  


%----------(pesquisa informada - a estrela)
percursoOperadorasaestrela( Inicial,Final,Operadoras,Caminho/Custo ) :- paragem(Inicial,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), estima(Inicial,Estima), percursoOpaestrela( Final,Operadoras,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

percursoOpaestrela( Final,Operadoras,Caminhos,Caminho ) :- obtem_melhor(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, paragem(Paragem,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), Final is Paragem.
percursoOpaestrela( Final,Operadoras,Caminhos,SolucaoCaminho ) :- obtem_melhor(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_op( Operadoras,MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												percursoOpaestrela( Final,Operadoras,NovoCaminhos,SolucaoCaminho ).		

expande_op( Operadoras,Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,proximo_op( Operadoras,Caminho,NovoCaminho ),ExpCaminhos).

proximo_op( Operadoras,[Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/Est ) :- adjacente(Nodo,ProxNodo,_), paragem(ProxNodo,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), \+member(ProxNodo,Caminho), NovoCusto is Custo + 1, estima(ProxNodo,Est).


%----------(pesquisa informada - gulosa)
percursoOperadorasgulosa( Inicial,Final,Operadoras,Caminho/Custo ) :- paragem(Inicial,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), estima(Inicial,Estima), percursoOpgulosa( Final,Operadoras,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

percursoOpgulosa( Final,Operadoras,Caminhos,Caminho ) :- obtem_melhor_g(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, paragem(Paragem,_,_,_,_,_,Op,_,_,_,_), pertence(Op,Operadoras), Final is Paragem.
percursoOpgulosa( Final,Operadoras,Caminhos,SolucaoCaminho ) :- obtem_melhor_g(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_op_g( Operadoras,MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												percursoOpgulosa( Final,Operadoras,NovoCaminhos,SolucaoCaminho ).		

expande_op_g( Operadoras,Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,proximo_op(Operadoras,Caminho,NovoCaminho),ExpCaminhos).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Excluir um ou mais operadores de transporte para o percurso:
%----------(pesquisa nao informada - profundidade)
percursoNaoOperadorasdf( Inicial,Final,Operadoras,[Inicial|Trajeto] ) :- paragem(Inicial,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), percursoNOpdf( Final,Inicial,Operadoras,Trajeto ).

percursoNOpdf( Final,Paragem,Operadoras,[] ) :- paragem(Paragem,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), Final is Paragem. 
percursoNOpdf( Final,Paragem,Operadoras,[ProxPar|Trajeto] ) :- adjacente(Paragem,ProxPar,_), paragem(ProxPar,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), percursoNOpdf( Final,ProxPar,Operadoras,Trajeto ).


%----------(pesquisa nao informada - largura)
percursoNaoOperadorasbf( Inicial,Final,Operadoras,Solucao ) :- paragem(Inicial,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), percursoNOpbf( Final,Operadoras,[[Inicial]],S ), inverso(S,Solucao).

percursoNOpbf( Final,Operadoras,[[Estado|Percurso]|_],[Estado|Percurso] ) :- paragem(Estado,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), Final is Estado.
percursoNOpbf( Final,Operadoras,[Percurso|Historico],Solucao ) :- atualizaNOp( Operadoras,Percurso,NovoPercurso ), junta(Historico,NovoPercurso,NovoHistorico), percursoNOpbf( Final,Operadoras,NovoHistorico,Solucao ).

atualizaNOp( Operadoras,[Estado|Percurso],NovoPercurso ) :- bagof( [NovoEstado,Estado|Percurso],(adjacente(Estado,NovoEstado,_),naopertence(NovoEstado,[Estado|Percurso]),paragem(NovoEstado,_,_,_,_,_,Op,_,_,_,_),naopertence(Op,Operadoras)),NovoPercurso ), !.
atualizaNOp( Operadoras,Percurso,[] ).  


%----------(pesquisa informada - a estrela)
percursoNaoOperadorasaestrela( Inicial,Final,Operadoras,Caminho/Custo ) :- paragem(Inicial,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), estima(Inicial,Estima), percursoNOpaestrela( Final,Operadoras,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

percursoNOpaestrela( Final,Operadoras,Caminhos,Caminho ) :- obtem_melhor(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, paragem(Paragem,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), Final is Paragem.
percursoNOpaestrela( Final,Operadoras,Caminhos,SolucaoCaminho ) :- obtem_melhor(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_nop( Operadoras,MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												percursoNOpaestrela( Final,Operadoras,NovoCaminhos,SolucaoCaminho ).		

expande_nop( Operadoras,Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,proximo_nop( Operadoras,Caminho,NovoCaminho ),ExpCaminhos).


proximo_nop( Operadoras,[Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/Est ) :- adjacente(Nodo,ProxNodo,_), paragem(ProxNodo,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), \+member(ProxNodo,Caminho), NovoCusto is Custo + 1, estima(ProxNodo,Est).


%----------(pesquisa informada - gulosa)
percursoNaoOperadorasgulosa( Inicial,Final,Operadoras,Caminho/Custo ) :- paragem(Inicial,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), estima(Inicial,Estima), percursoNOgulosa( Final,Operadoras,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

percursoNOgulosa( Final,Operadoras,Caminhos,Caminho ) :- obtem_melhor_g(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, paragem(Paragem,_,_,_,_,_,Op,_,_,_,_), naopertence(Op,Operadoras), Final is Paragem.
percursoNOgulosa( Final,Operadoras,Caminhos,SolucaoCaminho ) :- obtem_melhor_g(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_nop_g( Operadoras,MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												percursoNOgulosa( Final,Operadoras,NovoCaminhos,SolucaoCaminho ).		

expande_nop_g( Operadoras,Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,proximo_nop(Operadoras,Caminho,NovoCaminho),ExpCaminhos).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Identificar quais as paragens com o maior numero de carreiras num determinado percurso:
getCarreira( GID,C ) :- solucoes( Carreira,paragem(GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia),C ).

maiorCarreiras( [Paragem],PP,TT ) :- PP is Paragem, getCarreira( Paragem,C ), simplifica( C,L ), comprimento( L,TT ).
maiorCarreiras( [Paragem|Percurso],P,TAM ) :- getCarreira( Paragem,C ), simplifica(C,L), comprimento(L,T), maiorCarreiras( Percurso,PP,TT ), (T >= TT -> TAM is T, P is Paragem ; TAM is TT, P is PP).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Escolher o menor percurso (usando criterio menor numero de paragens) :
minimo([(P,X)],(P,X)).
minimo([(PX,X)|L],(PY,Y)) :- minimo(L,(PY,Y)),X>Y.
minimo([(PX,X)|L],(PX,X)) :- minimo(L,(PY,Y)),X=<Y. %>

%----------(pesquisa nao informada - profundidade)
percursoMenordf( A,B,P ) :- solucoes( (S,C),(percursodf(A,B,S),comprimento(S,C)),L ), minimo(L,P).


%----------(pesquisa nao informada - largura)
percursoMenorbf( A,B,P ) :- solucoes( (S,C),(percursobf(A,B,S),comprimento(S,C)),L ), minimo(L,P).


%----------(pesquisa informada - a estrela)
%	Funcao percursoaestrela ja devolve o menor percurso mediante a estima definida


%----------(pesquisa informada - gulosa)
%	Funcao percursogulosa ja devolve o menor percurso mediante a estima definida



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Escolher o percurso mais rapido (usando criterio da distancia) :
getLat( GID,L ) :- solucoes( Latitude,paragem(GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia),LL ), simples(LL,L).

getLon( GID,L ) :- solucoes( Longitude,paragem(GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia),LL ), simples(LL,L).

getDistancia( A,B,D ) :- getLat(A,LAT1), getLat(B,LAT2), getLon(A,LON1), getLon(B,LON2), D is sqrt((LAT2-LAT1)*(LAT2-LAT1) + (LON2-LON1)*(LON2-LON1)).

distanciaPercurso( [A,B],L ) :- getDistancia(A,B,L).
distanciaPercurso( [A,B|T],L ) :- getDistancia(A,B,LL), distanciaPercurso([B|T],R), L is R + LL.

%----------(pesquisa nao informada - profundidade)
percursoRapidodf( A,B,P ) :- solucoes( (S,C),(percursodf(A,B,S),distanciaPercurso(S,C)),L ), minimo(L,P).


%----------(pesquisa nao informada - largura)
percursoRapidobf( A,B,P ) :- solucoes( (S,C),(percursobf(A,B,S),distanciaPercurso(S,C)),L ), minimo(L,P).


%----------(pesquisa informada - a estrela)
percursoRapidoaestrela( Inicial,Final,Caminho/Custo ) :- estima(Inicial,Estima), rapidoaestrela( Final,[[Inicial]/0/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

rapidoaestrela( Final,Caminhos,Caminho ) :- obtem_melhor(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, Final is Paragem.
rapidoaestrela( Final,Caminhos,SolucaoCaminho ) :- obtem_melhor(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_rapido( MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												rapidoaestrela( Final,NovoCaminhos,SolucaoCaminho ).		

expande_rapido( Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,rapido( Caminho,NovoCaminho ),ExpCaminhos).

rapido( [Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/Est ) :- adjacente(Nodo,ProxNodo,_), \+member(ProxNodo,Caminho), getDistancia(Nodo,ProxNodo,Dist), NovoCusto is Custo + Dist, estima(ProxNodo,Est).


%----------(pesquisa informada - gulosa)
percursoRapidogulosa( Inicial,Final,Caminho/Custo ) :- estima(Inicial,Estima), rapidogulosa( Final,[[Inicial]/0/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

rapidogulosa( Final,Caminhos,Caminho ) :- obtem_melhor_g(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, Final is Paragem.
rapidogulosa( Final,Caminhos,SolucaoCaminho ) :- (Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_rapido_g( MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												rapidogulosa( Final,NovoCaminhos,SolucaoCaminho ).		

expande_rapido_g( Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,rapido(Caminho,NovoCaminho),ExpCaminhos).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Escolher o percurso que passe apenas por abrigos com publicidade:
%----------(pesquisa nao informada - profundidade)
percursoPublicidadedf( Inicial,Final,[Inicial|Trajeto] ) :- paragem(Inicial,_,_,_,_,"Yes",_,_,_,_,_), percursoPubdf( Final,Inicial,Trajeto ).

percursoPubdf( Final,Paragem,[] ) :- paragem(Paragem,_,_,_,_,"Yes",_,_,_,_,_), Final is Paragem.
percursoPubdf( Final,Paragem,[ProxPar|Trajeto] ) :- adjacente(Paragem,ProxPar,_), paragem(ProxPar,_,_,_,_,"Yes",_,_,_,_,_), percursoPubdf( Final,ProxPar,Trajeto ).	


%----------(pesquisa nao informada - largura)
percursoPublicidadebf( Inicial,Final,Solucao ) :- paragem(Inicial,_,_,_,_,"Yes",_,_,_,_,_), percursoPubbf( Final,[[Inicial]],S ), inverso(S,Solucao).

percursoPubbf( Final,[[Estado|Percurso]|_],[Estado|Percurso] ) :- paragem(Estado,_,_,_,_,"Yes",_,_,_,_,_), Final is Estado.
percursoPubbf( Final,[Percurso|Historico],Solucao ) :- atualizaPub( Percurso,NovoPercurso ), junta(Historico,NovoPercurso,NovoHistorico), percursoPubbf( Final,NovoHistorico,Solucao ).

atualizaPub( [Estado|Percurso],NovoPercurso ) :- bagof( [NovoEstado,Estado|Percurso],(adjacente(Estado,NovoEstado,_),naopertence(NovoEstado,[Estado|Percurso]),paragem(NovoEstado,_,_,_,_,"Yes",_,_,_,_,_)),NovoPercurso ), !.
atualizaPub( Percurso,[] ).   


%----------(pesquisa informada - a estrela)
percursoPublicidadeaestrela( Inicial,Final,Caminho/Custo ) :- paragem(Inicial,_,_,_,_,"Yes",_,_,_,_,_), estima(Inicial,Estima), percursoPubaestrela( Final,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

percursoPubaestrela( Final,Caminhos,Caminho ) :- obtem_melhor(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, paragem(Paragem,_,_,_,_,"Yes",_,_,_,_,_), Final is Paragem.
percursoPubaestrela( Final,Caminhos,SolucaoCaminho ) :- obtem_melhor(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_pub( MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												percursoPubaestrela( Final,NovoCaminhos,SolucaoCaminho ).		

expande_pub( Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,pub( Caminho,NovoCaminho ),ExpCaminhos).

pub( [Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/Est ) :- adjacente(Nodo,ProxNodo,_), \+member(ProxNodo,Caminho), paragem(ProxNodo,_,_,_,_,"Yes",_,_,_,_,_), NovoCusto is Custo + 1, estima(ProxNodo,Est).


%----------(pesquisa informada - gulosa)
percursoPublicidadegulosa( Inicial,Final,Caminho/Custo ) :- paragem(Inicial,_,_,_,_,"Yes",_,_,_,_,_), estima(Inicial,Estima), percursoPubgulosa( Final,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

percursoPubgulosa( Final,Caminhos,Caminho ) :- obtem_melhor_g(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, paragem(Paragem,_,_,_,_,"Yes",_,_,_,_,_), Final is Paragem.
percursoPubgulosa( Final,Caminhos,SolucaoCaminho ) :- obtem_melhor_g(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_pub_g( MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												percursoPubgulosa( Final,NovoCaminhos,SolucaoCaminho ).		

expande_pub_g( Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,pub(Caminho,NovoCaminho),ExpCaminhos).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Escolher o percurso que passe apenas por paragens abrigadas:
%----------(pesquisa nao informada - profundidade)
percursoAbrigadodf( Inicial,Final,[Inicial|Trajeto] ) :- paragem(Inicial,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), percursoAbrdf( Final,Inicial,Trajeto ).

percursoAbrdf( Final,Paragem,[] ) :- paragem(Paragem,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), Final is Paragem.
percursoAbrdf( Final,Paragem,[ProxPar|Trajeto] ) :- adjacente(Paragem,ProxPar,_), paragem(ProxPar,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), percursoAbrdf( Final,ProxPar,Trajeto ).	


%----------(pesquisa nao informada - largura)
percursoAbrigadobf( Inicial,Final,Solucao ) :- paragem(Inicial,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), percursoAbrbf( Final,[[Inicial]],S ), inverso(S,Solucao).

percursoAbrbf( Final,[[Estado|Percurso]|_],[Estado|Percurso] ) :- paragem(Estado,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), Final is Estado.
percursoAbrbf( Final,[Percurso|Historico],Solucao ) :- atualizaAbr( Percurso,NovoPercurso ), junta(Historico,NovoPercurso,NovoHistorico), percursoAbrbf( Final,NovoHistorico,Solucao ).

atualizaAbr( [Estado|Percurso],NovoPercurso ) :- bagof( [NovoEstado,Estado|Percurso],(adjacente(Estado,NovoEstado,_),naopertence(NovoEstado,[Estado|Percurso]),paragem(NovoEstado,_,_,_,"Fechado dos Lados",_,_,_,_,_,_)),NovoPercurso ), !.
atualizaAbr( Percurso,[] ).  

%----------(pesquisa informada - a estrela)
percursoAbrigadoaestrela( Inicial,Final,Caminho/Custo ) :- paragem(Inicial,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), estima(Inicial,Estima), percursoAbraestrela( Final,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

percursoAbraestrela( Final,Caminhos,Caminho ) :- obtem_melhor(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, paragem(Paragem,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), Final is Paragem.
percursoAbraestrela( Final,Caminhos,SolucaoCaminho ) :- obtem_melhor(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_abr( MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												percursoAbraestrela( Final,NovoCaminhos,SolucaoCaminho ).		

expande_abr( Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,abr( Caminho,NovoCaminho ),ExpCaminhos).

abr( [Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/Est ) :- adjacente(Nodo,ProxNodo,_), \+member(ProxNodo,Caminho), paragem(ProxNodo,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), NovoCusto is Custo + 1, estima(ProxNodo,Est).


%----------(pesquisa informada - gulosa)
percursoAbrigadogulosa( Inicial,Final,Caminho/Custo ) :- paragem(Inicial,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), estima(Inicial,Estima), percursoAbrgulosa( Final,[[Inicial]/1/Estima],InvCaminho/Custo/_ ), inverso(InvCaminho,Caminho).

percursoAbrgulosa( Final,Caminhos,Caminho ) :- obtem_melhor_g(Caminhos,Caminho), Caminho=[Paragem|_]/_/_, paragem(Paragem,_,_,_,"Fechado dos Lados",_,_,_,_,_,_), Final is Paragem.
percursoAbrgulosa( Final,Caminhos,SolucaoCaminho ) :- obtem_melhor_g(Caminhos,MelhorCaminho), seleciona(MelhorCaminho,Caminhos,OutrosCaminhos), 
												expande_abr_g( MelhorCaminho,ExpCaminhos ), junta(OutrosCaminhos,ExpCaminhos,NovoCaminhos), 
												percursoAbrgulosa( Final,NovoCaminhos,SolucaoCaminho ).		

expande_abr_g( Caminho,ExpCaminhos ) :- solucoes(NovoCaminho,abr(Caminho,NovoCaminho),ExpCaminhos).



%--------------------------------- - - - - - - - - - -  -  -  -  -
% Escolher um ou mais pontos intermedios por onde o percurso devera passar:
%----------(pesquisa nao informada - profundidade)
percursoIntermediodf( Inicial,Final,[PI|T],P ) :- percursodf(Inicial,PI,A), percursoIntdf([PI|T],B), junta(A,B,C), ultimo([PI|T],D), percursodf(D,Final,E), junta(C,E,F), apagaRep(F,P).

percursoIntdf([H],[H]).
percursoIntdf([A,B],L) :- percursodf(A,B,L).
percursoIntdf([A,B|T],L) :- percursodf(A,B,LL), percursoIntdf([B|T],R), junta(LL,R,L).


%----------(pesquisa nao informada - largura) 
percursoIntermediobf( Inicial,Final,[PI|T],P ) :- percursobf(Inicial,PI,A), percursoIntbf([PI|T],B), junta(A,B,C), ultimo([PI|T],D), percursobf(D,Final,E), junta(C,E,F), apagaRep(F,P).

percursoIntbf([H],[H]).
percursoIntbf([A,B],L) :- percursobf(A,B,L).
percursoIntbf([A,B|T],L) :- percursobf(A,B,LL), percursoIntbf([B|T],R), junta(LL,R,L).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -
% 								  PREDICADOS
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que permite a evolucao do conhecimento :
evolucao( Termo ) :- solucoes( Invariante,+Termo::Invariante,Lista ), insercao( Termo ), teste(Lista).

insercao( Termo ) :- assert(Termo).
insercao( Termo ) :- retract(Termo),!,fail.


% Extensao do predicado que permite a involucao do conhecimento :

involucao( Termo ) :- solucoes( Invariante,-Termo::Invariante,Lista ), remocao( Termo ), teste(Lista).

remocao( Termo ) :- retract(Termo).
remocao( Termo ) :- assert(Termo),!,fail.



%--------------------------------- - - - - - - - - - -  -  -  -  -
%----------INVARIANTES
% Garantir que o id de cada paragem e unico
+paragem(GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia) :: (solucoes(GID,paragem(GID,_,_,_,_,_,_,_,_,_,_),R), comprimento(R,1)).

% Garantir que paragens com ids diferentes tem diferente informacao
+paragem(GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia) :: (solucoes((Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia),paragem(_,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia),R), comprimento(R,1)).

% Garantir que nao e possivel remover uma paragem com ligacoes
-paragem(GID,_,_,_,_,_,_,_,_,_,_) :: (solucoes(GID,ligacao(GID,_,_),R), comprimento(R,0), solucoes(GID,ligacao(_,GID,_),S), comprimento(S,0)).

% Garantir que o id das paragens associado a ligacao existe
+ligacao(GID1,GID2,_) :: (solucoes(GID1, paragem(GID1,_,_,_,_,_,_,_,_,_,_),R), comprimento(R,1), solucoes(GID2,paragem(GID2,_,_,_,_,_,_,_,_,_,_),S), comprimento(S,1)).


%-----Introducao de conhecimento
registaParagem( GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia ) :- evolucao(paragem(GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia)).

registaLigacao( Origem,Destino,Carreira ) :- evolucao(ligacao(Origem,Destino,Carreira)).

%-----Remocao de conhecimento

removeParagem( GID ) :- involucao(paragem(GID,_,_,_,_,_,_,_,_,_,_)).

removeLigacao( Origem,Destino,Carreira ) :- involucao(ligacao(Origem,Destino,Carreira)).


%--------------------------------- - - - - - - - - - -  -  -  -  -
%----------METODOS
%-----Identificar uma paragem pelo seu GID
paragemGID( GID,R ) :- solucoes((GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia), paragem(GID,Latitude,Longitude,EstadoConservacao,TipoAbrigo,AbrigoPublicidade,Operadora,Carreira,CodigoRua,NomeRua,Freguesia),R).


%-----Identificar as ligacoes que uma determinada paragem possui como origem
ligacoes_da_paragemOR( GID,R ) :- solucoes(ligacao(GID,Destino,Carreira),ligacao(GID,Destino,Carreira),R).


%-----Identificar as ligacoes que uma determinada paragem possui como destino
ligacoes_da_paragemDE( GID,R ) :- solucoes(ligacao(Origem,GID,Carreira),ligacao(Origem,GID,Carreira),R).


%-----Identificar as ligacoes que uma determinada carreira como o seu tamanho
getLigacoes( Carreira,R,C ) :- solucoes(ligacao(Origem,Destino,Carreira),ligacao(Origem,Destino,Carreira),R), comprimento(R,C).


%-----Devolve o numero total de ligacoes:
total_ligacoes(R) :- solucoes(C,ligacao(_,_,C),L), comprimento(L,R).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -