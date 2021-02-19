%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Conhecimento Imperfeito..
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais
 
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).
 


%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Definicoes iniciais
 
:- op( 900,xfy,'::' ).
:- dynamic jogo/3.



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Aplicacao do PMF ao predicado Jogo

-jogo( Jogo,Arbitro,Ajudas ) :- nao( jogo( Jogo,Arbitro,Ajudas ) ), nao( excecao( jogo( Jogo,Arbitro,Ajudas ) ) ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O arbitro Almeida Antunes apitou o primeiro jogo do campeonato, no qual recebeu 500€ como ajudas de custo:

jogo( 1,aa,500 ). 


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O arbitro Baltazar Borges apitou o segundo jogo, tendo recebido a titulo de ajudas de custo um valor que ainda ninguem conhece:

jogo( 2,bb,xpto0123 ).

excecao( jogo( Jogo,Arbitro,Ajudas )) :- jogo( Jogo,Arbitro,xpto0123 ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Consta na ficha de jogo da terceira partida, que o arbitro Costa Carvalho recebeu 500€, mas a comunicacao social
% 	alega ter-lhe sido pago mais 2.000€ (como compensação por danos no seu veiculo);
%	instado a pronunciar-se sobre o assunto, o arbitro não confirma nem desmente nenhum dos valores noticiados: 

excecao( jogo( 3,cc, 500 ) ).

excecao( jogo( 3,cc, 2500 ) ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O arbitro Duarte Durao apitou o quarto jogo, tendo recebido como ajudas de custo um valor que ronda os 250€ a 750€,
%	 desconhecendo-se qual a quantia exata:

excecao( jogo( 4,dd,Ajudas ) ) :- Ajudas >= 250, Ajudas =< 750.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% No quinto jogo apitado pelo arbitro Edgar Esteves, ocorreram tumultos no final do encontro tendo desaparecido
% 	as ajudas de custo da carteira do árbitro, pelo que se torna impossível vir a conhecer-se esse valor:

jogo( 5,ee,xpto765 ).

excecao( jogo( Jogo,Arbitro,Ajudas ) ) :- jogo( Jogo,Arbitro,xpto765 ).

nulo( xpto765 ).

+jogo( J,A,C ) :: ( solucoes( Ajudas, (jogo( 5,ee,Ajudas ), nao( nulo( Ajudas ) ) ), S ), comprimento( S,N ), N == 0 ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O arbitro do sexto jogo, Francisco França recebeu, como ajudas de custo, o valor de 250€;
%	no entanto (entre amigos) refere ter “encaixado” nesse jogo para cima de 5.000€:

jogo( 6,ff,250 ).

excecao( jogo( 6,ff,Ajudas )) :- Ajudas >= 5000.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O arbitro Guerra Godinho, que apitou o setimo jogo, declara ser falso que alguma vez tenha
% 	recebido os 2.500€ que a comunicacao social refere como tendo entrado na sua conta
%		bancaria; contudo, este arbitro nunca confirmou o valor exato das ajudas de custo que recebeu:

excecao( jogo( 7,gg,2500 )).

jogo( 7,gg,xpto0123 ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Nao se conhecendo com exatidao o valor das ajudas de custo entregues ao arbitro Helder Heitor
%	no oitavo jogo, aceita-se ter sido um valor cerca dos 1.000€:

cerca( X,Sup,Inf ) :- Sup is X * 1.25, Inf is X * 0.75.

excecao( jogo( 8,hh,Ajudas )) :- cerca( 1000,Sup,Inf ), Ajudas >= Inf, Ajudas =< Sup.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Apesar de nao se conhecer o valor exato das ajudas de custo pagas ao arbitro do nono jogo, Ivo Inocencio,
%	este tera recebido uma quantia muito proxima dos 3.000:

excecao( jogo( 9,ii,Ajudas ) ) :- mproximo( 3000,Csup,Cinf ), Ajudas >= Cinf, Ajudas =< Csup.

mproximo( X,Sup,Inf ) :- Sup is X * 1.1, Inf is X * 0.9.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Num mesmo jogo nao pode existir mais do que um arbitro nomeado:

+jogo( J,A,C ) :: (solucoes(Ab,(jogo( J,Ab,C )),S), comprimento( S,N ),  N == 1).

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Um arbitro nao pode apitar mais do que 3 partidas do campeonato:

+jogo( J,A,C ) :: (solucoes((Pa,A,C),(jogo( Pa,A,C )),S), comprimento( S,N ),  N =< 3).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% O mesmo arbitro nao pode apitar duas partidas consecutivas:

+jogo(J,A,C) :: (solucoes(JJ, (jogo(JJ,A,_) , (JJ is J + 1 ; JJ is J - 1)), S), comprimento(S,N), N == 0).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que permite a evolucao do conhecimento

evolucao( Termo ) :- solucoes( Invariante,+Termo::Invariante,Lista ), insercao( Termo ), teste( Lista ).

insercao( Termo ) :- assert( Termo ).
insercao( Termo ) :- retract( Termo ),!,fail.

teste( [] ).
teste( [R|LR] ) :- R, teste( LR ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do predicado que permite a involucao do conhecimento

involucao( Termo ) :- solucoes( Invariante,-Termo::Invariante,Lista ), remocao( Termo ), teste( Lista ).

remocao( Termo ) :- retract( Termo ).
remocao( Termo ) :- assert( Termo ),!,fail.


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do meta-predicado demo: Questao,Resposta -> {V,F}
%                            Resposta = { verdadeiro,falso,desconhecido }

demo( Questao,verdadeiro ) :- Questao.
demo( Questao,falso ) :- -Questao.
demo( Questao,desconhecido ) :- nao( Questao ), nao( -Questao ).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :- Questao, !, fail.
nao( Questao ).


%--------------------------------- - - - - - - - - - -  -  -  -  -

solucoes( X,Y,Z ) :- findall( X,Y,Z ).

comprimento( S,N ) :- length( S,N ).

pertence( X,[X|L] ).
pertence( X,[Y|L] ) :- X \= Y, pertence( X,L ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -