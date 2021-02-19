/*  1. Listas Mistas sensiveis ao contexto
        1.1 Definicao Sintatica
            Desenvolva uma GIC para definir uma linguagem que permita
            escrever listas mistas de numeros e palavras
        1.2 Definicao Semantica
            Transforme a GIC numa GA de modo calcular os resultados pedidos nas alineas seguintes. Comece por definir uma
            GT recorrendo a variaveis globais e acoes semanticas para resolver a alinea a).
                a) contar o comprimento da lista e a quantidade de numeros;
                b) acrescente aos resultados anteriores a media de todos os numeros que aparecam na lista;
                c) obrigar a que quantidade de palavras seja igual a quantidade de numeros;
                d) calcular o maximo dos numeros.
                e) calcular o somatorio apenas dos numeros contidos entre 'agora' e 'fim'.

Autoria : Goncalo Rodrigues Pinto, a83732, MiEI
 */

grammar listas;

@members {
    int contador = 1;
    int comprimento = 0;
    int qtNumeros = 0;
    int somaNumeros = 0;
    float media = 0;
    int qtPalavras = 0;
    int maximo = 0;
    boolean contido = false;
    int somaContida = 0;
}

listas: lista+
      ;

lista: Lista elementos '.'              { System.out.println("#####---LISTA " + contador + " ----#####");
                                          System.out.println("A) Comprimento da lista: " + comprimento);
                                          System.out.println("A) Quantidade de numeros: " + qtNumeros );
                                          if (qtNumeros > 0) media = (float) somaNumeros / qtNumeros ;
                                          System.out.println("B) Media de todos os numeros: " + (float) media);
                                          if (qtNumeros == qtPalavras) System.out.println(" C) Quantidade de palavras e igual a quantidade de numeros.");
                                          else System.out.println("C) Quantidade de palavras -> " + qtPalavras + " nao e igual a quantidade de numeros -> " + qtNumeros + ".");
                                          System.out.println("D) Maximo dos numeros: " + maximo);
                                          System.out.println("E) Somatorio apenas dos numeros contidos: " + somaContida);
                                          System.out.println("###########################################");
                                          contador++;
                                          qtNumeros = 0;
                                          somaNumeros = 0;
                                          media = 0;
                                          qtPalavras = 0;
                                          maximo = 0;
                                          contido = false;
                                          somaContida = 0;
                                        }
     ;

elementos: elemento                     { comprimento=1; } ( ',' elemento { comprimento++; } )*
         ;

elemento: PALAVRA                       { qtPalavras++;
                                          if ( $PALAVRA.text.equals("agora") ) contido = true;
                                          if ( $PALAVRA.text.equals("fim") ) contido = false;
                                        }
        | NUM                           { qtNumeros++;
                                          somaNumeros += $NUM.int;
                                          if ( maximo < $NUM.int) maximo = $NUM.int;
                                          if ( contido ) somaContida += $NUM.int;
                                        }
        ;


//LEXER
Lista: [lL][iI][sS][tT][aA]
     ;

NUM: ('0'..'9')+ //[0-9]+
   ;

PALAVRA: [a-zA-Z][a-zA-Z0-9]*
       ;

WS: ('\r'? '\n' | ' ' | '\t')+ -> skip;