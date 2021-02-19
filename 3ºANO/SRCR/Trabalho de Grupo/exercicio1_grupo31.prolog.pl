%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 	SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3 - 1ºExercicio do trabalho de grupo
%--------------------------------- - - - - - - - - - -  -  -  -  -
%
% 								  GRUPO 31
% 					 Diogo Vasconcelos - a85059
%  					 Gonçalo Pinto     - a83732
% 					 João Parente      - a84197
%                    José Costa 	   - a84829
%
%--------------------------------- - - - - - - - - - -  -  -  -  -
% TEMA: 	Programacao em logica estendida e Conhecimento imperfeito. 
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Definicoes iniciais

:- op( 900,xfy,'::' ).
:- dynamic '-'/1.
:- dynamic adjudicante/4.
:- dynamic adjudicataria/4.
:- dynamic contrato/10.
:- dynamic excecao/1.



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 								  PREDICADOS AUXILIARES
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado demo: Questao, Resposta -> { V,F,D }

demo( Questao,verdadeiro ) :- Questao.
demo( Questao,falso ) :- -Questao.
demo( Questao,desconhecido ) :- nao( Questao ), nao( -Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado demo: Questao1, Tipo, Questao2, Resposta -> { V,F,D }
% 											em que Tipo pode ser: e - conjuncao ; ou - disjuncao; impl - implicacao ; eq - equivalencia

demo( Q1,e,Q2,F )    :- demo( Q1,F1 ), demo( Q2,F2 ), conjuncao( F1,F2,F ).
demo( Q1,ou,Q2,F )   :- demo( Q1,F1 ), demo( Q2,F2 ), disjuncao( F1,F2,F ).
demo( Q1,impl,Q2,F ) :- demo( Q1,F1 ), demo( Q2,F2 ), implicacao( F1,F2,F ).
demo( Q1,eq,Q2,F )   :- demo( Q1,F1 ), demo( Q2,F2 ), equivalencia( F1,F2,F ).

conjuncao( verdadeiro,verdadeiro,verdadeiro ).
conjuncao( desconhecido,verdadeiro,desconhecido ).
conjuncao( verdadeiro,desconhecido,desconhecido ).
conjuncao( falso,_,falso ).
conjuncao( _,falso,falso ).

disjuncao( verdadeiro,X,verdadeiro ).
disjuncao( X,verdadeiro,verdadeiro ).
disjuncao( Y,desconhecido,desconhecido ) :- Y \= verdadeiro.
disjuncao( desconhecido,Y,desconhecido ) :- Y \= verdadeiro.
disjuncao( falso,falso,falso ).

implicacao( X,verdadeiro,verdadeiro ).
implicacao( falso,X,verdadeiro ).
implicacao( desconhecido,X,desconhecido ) :- X \= verdadeiro.
implicacao( verdadeiro,desconhecido,desconhecido ). 
implicacao( verdadeiro,falso,falso ).

equivalencia( X,X,verdadeiro ).
equivalencia( X,desconhecido,desconhecido ).
equivalencia( desconhecido,Y,desconhecido ).
equivalencia( X,Y,falso ) :- X \= Y.

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado demoList: Lista de Questoes, Lista de Respostas -> { V,F,D }

demoList( [],[] ).
demoList( [Q|L1],[R|L2] ) :- demo( Q,R ), demoList( L1,L2 ). 

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :- Questao, !, fail.
nao( Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que permite a evolucao do conhecimento

evolucao( Termo ) :- solucoes( Invariante,+Termo::Invariante,Lista ), insercao( Termo ), teste( Lista ).

insercao( Termo ) :- assert( Termo ).
insercao( Termo ) :- retract( Termo ),!,fail.

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que permite a involucao do conhecimento

involucao( Termo ) :- solucoes( Invariante,-Termo::Invariante,Lista ), remocao( Termo ), teste( Lista ).

remocao( Termo ) :- retract( Termo ).
remocao( Termo ) :- assert( Termo ),!,fail.

%--------------------------------- - - - - - - - - - -  -  -  -  -

teste( [] ).
teste( [X|Y] ) :- X, teste( Y ).

solucoes( X,Y,Z ) :- findall( X,Y,Z ).

comprimento( S,N ) :- length( S,N ).

pertence( X,[X | T] ).
pertence( X,[H | T] ) :- X \= H, pertence(X, T).

somaN( [],0 ). 
somaN( [First | Rest],Sum ) :- somaN( Rest,SumRest ), Sum is First + SumRest.

custo_total([X],X).
custo_total([X,Y|Z], R) :- custo_total([X+Y|Z], R1), R is R1.

apaga1( _,[],[] ).
apaga1( X,[X|Y],T ) :- apaga1( X,Y,T ).
apaga1( X,[H|Y],[H|R] ) :- apaga1( X,Y,R ).

apagaRep( [],[] ).
apagaRep( [X|Y],[X|L1] ) :- apaga1( X,Y,L ), apagaRep( L,L1 ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado data: D,M,A -> { V,F }

data( D,2,A ) :- D =< 28, A >= 0, A mod 4 =\= 0,  D >= 1.
data( D,2,A ) :- D =< 29, A >= 0, A mod 4 =:= 0,  D >= 1.
data( D,M,A ) :- D =< 30, A >= 0, D >= 1, pertence( M, [4, 6, 9, 11] ).
data( D,M,A ) :- D =< 31, A >= 0, D >= 1, pertence( M, [1,3,5,7,8,10,12] ).
data( data( D,M,A ) ) :- data( D,M,A ).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado data: D,M,A -> { V,F }

validaProcedimento( 'Ajuste Direto' ).
validaProcedimento( 'Consulta Previa' ).
validaProcedimento( 'Concurso Publico' ).





%--------------------------------- - - - - - - - - - -  -  -  -  -
%--------------------------------- - - - - - - - - - -  -  -  -  -
%--------------------------------- - - - - - - - - - -  -  -  -  -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 								  EXTENSAO DOS PREDICADOS
%--------------------------------- - - - - - - - - - -  -  -  -  -

% Predicado adjudicante: IdAd,Nome,NIF,Morada -> {V,F,D}

adjudicante(1,'Universidade do Minho',502011378,'Portugal,Braga').
adjudicante(2,'Servicos de Acao Social da Universidade do Minho',680047360,'Portugal,Braga').
adjudicante(3,'Hospital Dr. Francisco Zagalo - Ovar',501510150,'Portugal,Aveiro,Ovar').
adjudicante(4,'Municipio de Barcelos',505584760,'Portugal,Braga,Barcelos').
adjudicante(5,'Municipio de Caminha',500843139,'Portugal,Viana do Castelo,Caminha').
adjudicante(6,'DGAM',600012662,'Portugal,Setubal,Almada').
adjudicante(7,'Municipio de Alto de Basto',705330336,'Portugal,Braga,Alto de Basto').


% Predicado adjudicataria: IdAda,Nome,NIF,Morada -> {V,F,D}

adjudicataria(1,'IDUNA COMERCIO E INDUSTRIA DE MOBILIARIO,S.A',503263869,'Portugal').
adjudicataria(2,'Diversey Portugal-Sistemas de Higiene e Limpeza Unipessoal,Lda',500086753,'Portugal').
adjudicataria(3,'SIEMENS HEALTHCARE DIAGNOSTICO LDA',507925173,'Portugal').
adjudicataria(4,'Lugar do Plano, Gestao do Territorio e Cultura,Lda',506378802,'Portugal').
adjudicataria(5,'Herculano Filipe Marvao Franco de Almeida',228686750,'Portugal').
adjudicataria(6,'BASE2 - INFORMATICA E TELECOMUNICACOES,LDA',501333401,'Portugal').
adjudicataria(7,'XXX - Associados - Sociedade de Advogados,SP,RL',702675112,'Portugal').
adjudicataria(9,'Kone Portugal Elevadores,Lda',506682048,'Portugal').
adjudicataria(10,'Newcoffee - industria torrefatora de cafes,SA',508348684,'Portugal').


% Predicado contrato: IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data -> {V,F,D}

contrato(1,1,1,'Aquisicao de bens moveis','Consulta Previa', 'Aquisicao de cadeiras de escritorio para os varios servicos administrativos',28600,30,'Portugal,Braga',data(24,02,2020)).
contrato(2,2,2,'Aquisicao de bens moveis','Concurso Publico','Fornecimento de preparacoes para lavagem, por lotes, para o ano de 2020',6065.50,365,'Portugal,Braga',data(01,04,2020)).
contrato(3,3,3,'Aquisicao de bens moveis','Ajuste Direto','Reagentes de laboratorio',1950.50,347,'Portugal,Aveiro,Ovar',data(14,01,2020)).
contrato(4,4,4,'Aquisicao de servicos','Ajuste Direto','Aquisicao de servicos para elaboracao da estrategia local de habitacao em Barcelos',19200.00,120,'Portugal,Braga,Barcelos',data(13,03,2020)).
contrato(5,5,5,'Aquisicao de servicos','Consulta Previa','Prestacao de servicos de Assessoria Juridica',54000.00,1095,'Portugal,Viana do Castelo,Caminha',data(30,03,2020)).
contrato(6,6,6,'Aquisicao de servicos','Ajuste Direto','Assistencia a multifuncoes Lexmark',2016.00,5,'Portugal',data(10,03,2020)).
contrato(7,1,6,'Aquisicao de bens moveis','Concurso Publico','Aquisicao de equipamento informatico - DTSI e SCOM',3976.00,30,'Portugal,Braga,Braga',data(21,02,2020)).
contrato(8,7,7,'Aquisicao de servicos','Consulta Previa','Assessoria juridica',13599,547,'Alto de Basto',data(11,02,2020)).



%--------------------------------- - - - - - - - - - -  -  -  -  -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 						REPRESENTACAO DE CONHECIMENTO IMPERFEITO
%--------------------------------- - - - - - - - - - -  -  -  -  -

-adjudicante( IdAd,Nome,NIF,Morada ) :- nao( adjudicante( IdAd,Nome,NIF,Morada )), nao( excecao( adjudicante( IdAd,Nome,NIF,Morada ))).

-adjudicataria( IdAda,Nome,NIF,Morada ) :- nao( adjudicataria( IdAd,Nome,NIF,Morada )), nao( excecao( adjudicataria( IdAd,Nome,NIF,Morada ))).

-contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ) :-
		 nao( contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data )),
		 nao( excecao( contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ) )).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% desconhecimento da morada do adjudicante:
adjudicante(9,'Instituto Nacional de Emergencia Medica, I.P.',501356126,morada_desconhecida).
excecao(adjudicante( IdAd,Nome,NIF,Morada )) :- adjudicante( IdAd,Nome,NIF,morada_desconhecida ).

% desconhecimento do NIF do adjudicante mas sabendo que nao é 500745471: 
adjudicante(8,'Santa Casa da Misericordia de Lisboa',nif_desconhecido,'Portugal,Lisboa').
-adjudicante(8,'Santa Casa da Misericordia de Lisboa',500745471,'Portugal,Lisboa').
excecao(adjudicante( IdAd,Nome,NIF,Morada )) :- adjudicante( IdAd,Nome,nif_desconhecido,Morada ).

% a adjudicataria Mario Goncalves,Lda trabalha em três cidades diferentes:
excecao( adjudicataria(8,'Mario Goncalves, Lda',500183872,'Portugal,Braga,Braga')).
excecao( adjudicataria(8,'Mario Goncalves, Lda',500183872,'Portugal,Porto,Porto')).
excecao( adjudicataria(8,'Mario Goncalves, Lda',500183872,'Portugal,Lisboa,Lisboa')).

% imprecisao no valor de um contrato, mas sabendo que é aproximadamente 1000:
contrato(10,2,10,'Aquisicao de bens moveis','Concurso Publico','Aquisicao de cafe, por lotes, para os anos de 2020 e 2021',valor_desconhecido,353,'Portugal,Braga',data(13,01,2020)).
excecao(contrato(10,2,10,'Aquisicao de bens moveis','Concurso Publico','Aquisicao de cafe, por lotes, para os anos de 2020 e 2021',Valor,353,'Portugal,Braga',data(13,01,2020))) :- aproximadamente(Valor,1000).

aproximadamente( X,Y ) :- W is 0.90 * Y, Z is 1.10 * Y, X =< Z, X >= W.

% devido a problemas na base de conhecimento perdeu-se a descricao de um contrato:
contrato(9,2,9,'Aquisicao de servicos','Concurso Publico',descricao_interdita,30024,1096,'Portugal,Braga',data(14,02,2020)).
excecao(contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data )) :- contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,descricao_interdita,Valor,Prazo,Local,Data).
nulo(descricao_interdita) :- contrato(9,2,9,'Aquisicao de servicos','Concurso Publico',Descricao,30024,1096,'Portugal,Braga',data(14,02,2020)), nao(nulo(Morada)).



%--------------------------------- - - - - - - - - - -  -  -  -  -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 								  INVARIANTES
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Nao permitir a insercao de conhecimento repetido para o adjudicante:

+adjudicante( IdAd,Nome,NIF,Morada ) :: ((solucoes(IdAd, adjudicante( IdAd,Nome,NIF,Morada ), U), comprimento(U,N), N==1), integer(IdAd), integer(NIF), IdAd>0, NIF=<999999999, NIF>=100000000).

% Nao permitir a remocao de adjudicante enquando existirem contratos associadas ao mesmo:

-adjudicante( IdAd,Nome,NIF,Morada ) :: (solucoes( IdAd,contrato( _,IdAd,_,_,_,_,_,_,_,_ ),S1 ), comprimento( S1,0 )).


%--------------------------------- - - - - - - - - - -  - -  -  -
% Nao permitir a insercao de conhecimento repetido para o adjudicataria:

+adjudicataria( IdAda,Nome,NIF,Morada ) :: ((solucoes(IdAda, adjudicataria( IdAda,Nome,NIF,Morada ), U), comprimento(U,N), N==1), integer(IdAda), integer(NIF), IdAda>0, NIF=<999999999, NIF>=100000000).

% Nao permitir a remocao de adjudicataria enquando existirem contratos associadas ao mesmo:

-adjudicataria( IdAda,Nome,NIF,Morada ) :: (solucoes( IdAda,contrato( _,_,IdAda,_,_,_,_,_,_,_ ),S1 ), comprimento( S1,0 )).


%--------------------------------- - - - - - - - - - -  - -  -  -
% Condicoes obrigatorias do contrato por ajuste direto:

validaContratoAjusteDireto( 'Contrato de aquisicao' ).
validaContratoAjusteDireto( 'Locacao de Bens moveis' ).
validaContratoAjusteDireto( 'Aquisicao de servicos' ).

validaPrazoVigencia( D,M,A,P ) :- A mod 4 =:= 0, P=<365. %>
validaPrazoVigencia( D,M,A,P ) :- A mod 4 =\= 0, P=<364. %>
validaPrazoVigencia( data( D,M,A ),P ) :- validaPrazoVigencia( D,M,A,P ).

+contrato(IdC,IdAd,IdAda,TipoDeContrato,'Ajuste Direto',Descricao,Valor,Prazo,Local,Data) :: (number(Valor), Valor =< 5000, Valor >= 0, number(Prazo),
																								solucoes( IdC, contrato( IdC,_,_,_,_,_,_,_,_,_ ), S0), comprimento(S0,N0), N0==1,
																								solucoes( IdAd, adjudicante( IdAd,_,_,_ ), S1), comprimento( S1,N1 ), N1==1,
																								solucoes( IdAda, adjudicataria( IdAda,_,_,_ ), S2), comprimento( S2,N2 ), N2==1,
																								validaContratoAjusteDireto( TipoDeContrato ), validaPrazoVigencia( Data,Prazo )).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Regra dos 3 anos valida para todos os contratos

doisanteriores( A,B ) :- A>=B , B>=A-2.

+contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,data( D,M,A ) )  :: (
										solucoes( V,(contrato( IdC,IdAd,IdAda,_,TipoDeProcedimento,_,V,_,_,data( D2,M2,A2 )), doisanteriores( A,A2 )),S1), 
										somaN(S1,Total), Total-Valor =< 75000). %>


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Nao permitir a insercao de um contrato ja existente ou que esteja associado a um adjudicante e a uma adjudicataria que nao existam:

+contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ) :: ( number(Valor), Valor >= 0, number(Prazo), data(Data),
   																										solucoes( IdAd, adjudicante( IdAd,_,_,_ ), S1 ), comprimento( S1,1 ),
    																									solucoes( IdAda, adjudicataria(IdAda,_,_,_ ), S2 ), comprimento( S2,1 ),
																										solucoes( IdC, contrato( IdC,_,_,_,_,_,_,_,_,_ ), S3 ), comprimento( S3,0 )).
% Remocao de um contrato
-contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ) :: ( solucoes(IdC, contrato( IdC,_,_,_,_,_,_,_,_,_ ), S), comprimento( S,1 )).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Introducao de conhecimento

registaAdjudicante( IdAd,Nome,NIF,Morada ) :- evolucao( adjudicante( IdAd,Nome,NIF,Morada )).

registaAdjudicataria( IdAda,Nome,NIF,Morada ) :- evolucao( adjudicataria( IdAda,Nome,NIF,Morada )).

registaContrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ) :- evolucao( contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data )).


% Remocao de conhecimento

removeAdjudicante( IdAd ) :- involucao( adjudicante( IdAd,_,_,_ )).

removeAdjudicataria( IdAda ) :- involucao( adjudicataria( IdAda,_,_,_ )).

removeContrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ) :- involucao( contrato( IdC,_,_,_,_,_,_,_,_,_ )).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% 								  METODOS
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Identificar um adjudicante ou adjudicataria pelos seus parametros:

adjudicanteID( IdAd,R ) :- solucoes( adjudicante( IdAd,Nome,NIF,Morada), adjudicante( IdAd,Nome,NIF,Morada),[R|_]).
adjudicanteNOME( Nome,R ) :- solucoes( adjudicante( IdAd,Nome,NIF,Morada), adjudicante( IdAd,Nome,NIF,Morada),R).
adjudicanteNIF( NIF,R ) :- solucoes( adjudicante( IdAd,Nome,NIF,Morada), adjudicante( IdAd,Nome,NIF,Morada),R).
adjudicanteMORADA( Morada,R ) :- solucoes( adjudicante( IdAd,Nome,NIF,Morada), adjudicante( IdAd,Nome,NIF,Morada),R).

adjudicatariaID( IdAda,R ) :- solucoes( adjudicataria( IdAda,Nome,NIF,Morada), adjudicataria( IdAda,Nome,NIF,Morada),[R|_]).
adjudicatariaNOME( Nome,R ) :- solucoes( adjudicataria( IdAda,Nome,NIF,Morada), adjudicataria( IdAda,Nome,NIF,Morada),R).
adjudicatariaNIF( NIF,R ) :- solucoes( adjudicataria( IdAda,Nome,NIF,Morada), adjudicataria( IdAda,Nome,NIF,Morada),R).
adjudicatariaMORADA( Morada,R ) :- solucoes( adjudicataria( IdAda,Nome,NIF,Morada), adjudicataria( IdAda,Nome,NIF,Morada),R).


% Identificar contrato pelo seu IdC:

contratoID( IdC,R ) :- solucoes( contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ), contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data),[R|_]).


% Identificar os adjudicantes ou adjudicatarias registados que tem contratos associados:

adjudicante_contrato( R1 ) :- solucoes(adjudicante( IdAd,Nome,NIF,Morada ), (adjudicante( IdAd,Nome,NIF,Morada ),contrato( _,IdAd,_,_,_,_,_,_,_,_)),R ), apagaRep(R,R1).

adjudicataria_contrato( R1 ) :- solucoes(adjudicataria( IdAda,Nome,NIF,Morada ), (adjudicataria( IdAda,Nome,NIF,Morada ),contrato( _,_,IdAda,_,_,_,_,_,_,_)),R ), apagaRep(R,R1).


% Identificar contratos que um adjudicante ou adjudicataria encontra-se envolvido:

contrato_adjudicante( IdAd,R ) :- solucoes( contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ), (adjudicante(IdAd,Nome,NIF,Morada ), contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data)),R).

contrato_adjudicataria( IdAda,R ) :- solucoes( contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ), (adjudicataria(IdAda,Nome,NIF,Morada ), contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data)),R).


% Identificar os adjudicantes de uma adjudicataria, e vice-versa:

adjudicante_de_adjudicataria(IdAda,R) :- solucoes(adjudicante(IdAd,Nome,NIF,Morada), (contrato( _,IdAd,IdAda,_,_,_,_,_,_,_ ), adjudicataria( IdAda,_,_,_),adjudicante(IdAd,Nome,NIF,Morada)),R1), apagaRep(R1,R).

adjudicataria_de_adjudicante(IdAd,R) :- solucoes(adjudicataria(IdAda,Nome,NIF,Morada), (contrato( _,IdAd,IdAda,_,_,_,_,_,_,_ ), adjudicataria( IdAda,Nome,NIF,Morada ),adjudicante(IdAd,_,_,_)),R1), apagaRep(R1,R).


% Identificar os contratos realizados num determinado local :

contratos_por_local( Local,R ) :- solucoes(contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data), contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ), R).


% Identificar os contratos com determinado tipo de contrato:

contratos_por_tipo( TipoDeContrato,R ) :- solucoes(contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data), contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ), R).


% Identificar os contratos com determinado tipo de procedimento:

contratos_por_procedimento( TipoDeProcedimento,R ) :- solucoes(contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data), contrato( IdC,IdAd,IdAda,TipoDeContrato,TipoDeProcedimento,Descricao,Valor,Prazo,Local,Data ), R).


% Calcular o valor pago ou facturado por um adjudicante ou adjudicataria:

pago_adjudicante( IdAd,R ) :- solucoes(Valor, contrato( _,IdAd,_,_,_,_,Valor,_,_,_ ),R1), custo_total(R1,R).

facturado_adjudicataria( IdAda,R ) :- solucoes(Valor, contrato( _,_,IdAda,_,_,_,Valor,_,_,_ ),R1), custo_total(R1,R).


% Devolve o numero total de adjudicantes:

total_adjudicante(R) :- solucoes(IdAd, adjudicante(IdAd,_,_,_), L), comprimento(L,R).


% Devolve o numero total de adjudicatarias:

total_adjudicataria(R) :- solucoes(IdAda, adjudicataria(IdAda,_,_,_), L), comprimento(L,R).


% Devolve o numero total de contratos:

total_contratos(R) :- solucoes(IdC,contrato( IdC,_,_,_,_,_,_,_,_,_ ),L), comprimento(L,R).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -