# GandaGalo

## Etapas
### Etapa 1 - Jogo básico
* Desenhar a grelha;
* Permitir colocar um X ou um O numa casa;
### Etapa 2 - Jogo melhorado
* Apresentar uma lista de puzzles e permite jogá-los;
* Ler os puzzles a partir de ficheiros;
* Verificar se o puzzle é válido;
* Dar ajudas;
* Permitir anular movimentos e colocar âncoras.
### Etapa 3 - Avaliação final com pontuação
* Resolver puzzles;
* Gerar puzzles com várias diculdades;
* Compilar com as opções `gcc -std=c11 -Wall -Wextra -pedantic -O2` sem warnings nem erros;
* Avaliação da documentação;
* Avaliação de modularidade e legibilidade;
* Estado em ficheiro e multi-utilizador;
* Utilização de estruturas dinâmicas;
* Tarefa de análise de código (gdb);
* Interface com o utilizador;

## Geração
* Entregar numa diretoria à parte chamada gerar;
* O comando make deve criar um executável chamado gerar;
* O programa deve receber os seguintes argumentos: `gerar <dificuldade> <no de linhas> <no de colunas>`
* O programa deve imprimir o tabuleiro gerado segundo a estrutura dos ficheiros;
* A dificuldade deve ser 1 para fácil e 2 para difícil;
* O tabuleiro impresso (no stdout) deve poder ser consultado pelo programa e deve ter uma única solução e a dificuldade esperada.