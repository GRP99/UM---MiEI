# Trabalho Prático

### Documentação
Para cumprir de forma integrada os objectivos recorreu-se a uma técnica de programação dita **“literária”**, cujo princípio base é o seguinte: *Um programa e a sua documentação devem coincidir.*
Por outras palavras, o código fonte e a documentação de um programa encontram-se no mesmo ficheiro. 

O ficheiro [cp1819t.pdf](cp1819t.pdf) é um exemplo de programação literária: foi gerado a partir
do texto fonte [cp1819t.lhs](cp1819t.lhs).
<p> <code> $ lhs2TeX cp1819t.lhs > cp1819t.tex </code>
<p> <code> $ pdflatex cp1819t </code>

**Nota:** *lhs2tex* é um pre-processador que faz “pretty printing” de código Haskell em LATEX e que deve desde instalar executando
`   $ cabal install lhs2tex `

Por outro lado, o mesmo ficheiro [cp1819t.lhs](cp1819t.lhs) é executável e contém o “kit” básico, escrito em Haskell, para realizar o trabalho. Basta executar
<p> <code> $ ghci cp1819t.lhs </code>