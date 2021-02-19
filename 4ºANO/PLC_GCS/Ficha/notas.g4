/*  2. Notas Medias dos Alunos de uma Turma
        2.1 Definicao Sintatica
            Desenvolva uma GIC para definir uma linguagem que permita descrever os alunos
            (identificados pelo seu nome) de uma turma especifica.
        2.2 Defnicao Semantica
            Transforme a GIC numa GA de modo resolver os seguintes pedidos
                a) contar o total de alunos
                b) calcular a nota media de cada aluno
                c) garantir que todos os alunos tem entre 4 e 6 notas e que estas estao na escala '0' a '20'
                d) garantir que nao ha nomes repetidos
Autoria : Goncalo Rodrigues Pinto, a83732, MiEI
*/

grammar notas;

turmas: turma+;

turma   /* returns [int totalAlunos] */
        /* @after { System.out.println("Total Alunos:" + $totalAlunos);}
           alunos { $totalAlunos = $alunos.totalAlunos; }
        */
        :TURMA ID
        alunos { System.out.println("Total Alunos:" + $alunos.totalAlunos); }
        PONTO;


alunos returns [int totalAlunos]:
        a1=aluno                { $totalAlunos = 1; $a1.nomes.add($a1.name);}
        (
        PONTOVIRGULA a2=aluno   {   $totalAlunos += 1;
                                    if ($a1.nomes.contains($a2.name)) {
                                        System.out.println("ERRO: Nome repetido : " + $a2.name );
                                    }
                                    else { $a1.nomes.add($a2.name); }
                                }
        )*;

/* notas herdar nome : nome notas[$nome.text] e depois .. notas[String n] returns .. */
aluno returns [ArrayList<String> nomes, String name]: nome notas[$nome.text] {
                                                                                $nomes = new ArrayList<String>();
                                                                                $name = $nome.text;
                                                                                float media = $notas.soma / $notas.totalNotas;
                                                                                System.out.println("A media do aluno " + $nome.text + " : " + media);
                                                                             }
                                                    ;

nome: NOME
    ;

notas[String nomeA] returns [int soma, int totalNotas]:
     LP n1=NUM          {   $soma = $n1.int;
                            $totalNotas = 1;
                            if (!($n1.int > 0 && $n1.int < 20)) System.out.println("ERRO: Nota fora de escala!");
                        }
     (VIRGULA n2=NUM    {   $soma += $n2.int;
                            $totalNotas += 1;
                            if (!($n2.int > 0 && $n2.int < 20)) System.out.println("ERRO: Nota fora de escala!");
                        }
     )* RP              {   if ($totalNotas < 4) System.out.println("ERRO: O Aluno " + $nomeA + " tem notas a menos!");
                            if ($totalNotas > 6) System.out.println("ERRO: O Aluno " + $nomeA + " tem notas a mais!");
                        }
     ;

//LEXER
TURMA: [tT][uU][rR][mM][aA];
ID: [a-zA-Z];
NOME: [a-zA-Z]+;
NUM: [0-9]+;
VIRGULA: ',';
PONTOVIRGULA: ';';
PONTO: '.';
LP: '(';
RP: ')';
WS: ('\r'? '\n' | ' ' | '\t')+ -> skip;