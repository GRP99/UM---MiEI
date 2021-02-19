%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Conhecimento Imperfeito.
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais
 
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).
 


%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Definicoes iniciais
 
:- op( 900,xfy,'::' ).
:- dynamic servico/2.
:- dynamic ato/4.



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Aplicacao do PMF ao predicado Servico e Ato

-servico( Servico,Nome ) :- nao(servico( Servico,Nome )),  nao(excecao(servico( Servico,Nome ))).
	
-ato( Ato,Prestador,Utente,Dia ) :- nao(ato( Ato,Prestador,Utente,Dia )), nao(excecao(ato( Ato,Prestador,Utente,Dia ))).


%--------------------------------- - - - - - - - - - -  -  -  -  -
% Predicado(s) a utilizar para a representação do conhecimento caracterizado na Tabela 1 da ficha 08
servico( ortopedia,amelia ).

servico( obstetricia,ana ).

servico( obstetricia,maria ).

servico( obstetricia,mariana ).

servico( geriatria,sofia ).

servico( geriatria,susana ).

servico( x007,teodora ).
excecao( servico( Servico,Nome )) :- servico( x007,Nome ).

servico( np9,zulmira ).
excecao( servico( Servico,Nome )) :- servico( np9,Nome ).
nulo( np9 ) :- servico( Servico,zulmira ), nao(nulo( Servico )).


ato( penso,ana,joana,sabado ).

ato( gesso,amelia,jose,domingo ).

ato( x007,mariana,joaquina,domingo ).
excecao( ato( Ato,Prestador,Utente,Dia )) :- ato( x007,Prestador,Utente,Dia ).

ato( domicilio,maria,x121,x251 ).
excecao( ato( Ato,Prestador,Utente,Dia )) :- ato( Ato,Prestador,x121,x251 ).

excecao( ato( domicilio,susana,joao,segunda )).
excecao( ato( domicilio,susana,jose,segunda )).

ato( sutura,x313,josue,segunda ).
excecao( ato( Ato,Prestador,Utente,Dia )) :- ato( Ato,x313,Utente,Dia ).

excecao( ato( sutura,maria,josefa,terca )).
excecao( ato( sutura,maria,josefa,sexta )).
excecao( ato( sutura,mariana,josefa,terca )).
excecao( ato( sutura,mariana,josefa,sexta )).

intervalo(segunda,segunda,sexta).
intervalo(terca,segunda,sexta).
intervalo(quarta,segunda,sexta).
intervalo(quinta,segunda,sexta).
intervalo(sexta,segunda,sexta).
excecao( ato( penso,ana,jacinta,Dia )) :- intervalo(Dia,segunda,sexta).


%--------------------------------- - - - - - - - - - -  -  -  -  -
%Invariante que impede o registo de atos medicos em dias feriado:


%--------------------------------- - - - - - - - - - -  -  -  -  -
%Invariante que impede a remocao de profissionais com atos registados:

-servico( S,N ) :: (solucoes(( N ), (ato( A,N,U,D )), S), comprimento( S,N ), N == 0).


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