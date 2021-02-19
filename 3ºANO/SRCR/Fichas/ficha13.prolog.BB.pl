%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -
% Conhecimento nao simbolico: Redes Neuronais Artificias.
%--------------------------------- - - - - - - - - - -  -  -  -  -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Problema 1

emprestimo( VLM,Renda,Automovel,Credito ) :- Renda<0.3*VLM, Renda+Automovel+Credito<0.4*VLM.

%--------------------------------- - - - - - - - - - -  -  -  -  -
% Problema 2



%--------------------------------- - - - - - - - - - -  -  -  -  -
solucoes( X,Y,Z ) :- findall( X,Y,Z ).

comprimento( S,N ) :- length( S,N ).

pertence( X,[X|L] ).
pertence( X,[Y|L] ) :- X \= Y, pertence( X,L ).