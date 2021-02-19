/*  4. Gestao de Stock e Faturacao
        4.1 Definicao Sintatica
            Considere uma linguagem especifica para descrever Faturas.
            Sabe-se que uma Fatura e composta por um cabecalho e um corpo, e este e composto por um ou mais movimentos.
            No cabecalho deve aparecer o numero da Fatura, a Identificao do Fornecedor (nome, NIF, morada e NIB) e a
            Identificao do Cliente (nome, NIF, morada). Em cada linha do corpo da Fatura pretende-se, apenas incluir a
            Referencia ao produto vendido e a Quantidade vendida.
            Para ser possivel processar as faturas convenientemente e necessario que no inicio de cada frase dessa linguagem surja
            uma descricao do Stock, conjunto de produtos, sendo cada produto definido por: referencia do produto, descricao,
            preco unitario e quantidade em stock.
            Escreva entao uma Gramatica Independente de Contexto, GIC, que especifique a Linguagem pretendida.

        4.2 Definicao Semantica
            Escreva uma Gramatica de Atributos, GA, para calcular o total por linha e total geral, caso nao sejam detetados erros
            semanticos (referencias a produtos que nao existem em Stock, ou venda de quantidades superiores as existentes). Note
            que o seu processador deve atualizar o Stock de acordo com as vendas registadas em cada linha da fatura.
*/

grammar gestao;

@header {
    import java.util.*;
}

@members {
    class Produto{
        String descr;
        Double prec;
        int qtd;
           
        public String toString(){
            StringBuffer sb = new StringBuffer();
            sb.append(this.descr + "; ");
            sb.append(this.prec + "; ");
            sb.append(this.qtd + ". ");
            return sb.toString();
        }
    }
}

gestao: stock ';' ';' faturas[$stock.prodts];

stock
	returns[HashMap<String, Produto> prodts]
	@init {$prodts = new HashMap<String, Produto>(); }:
	'STOCK' p1 = produto[$prodts] { $prodts=$p1.prodout; }
    ( ';' p2 = produto[$prodts] { $prodts=$p2.prodout; } )*
    { System.out.println("Quantidades em Stock no inicio:"); System.out.println($prodts.toString()); };

produto[HashMap<String, Produto> prodin]
	returns[HashMap<String, Produto> prodout]
        @init {$prodout = new HashMap<String, Produto>(); }:
	refProd ':' descProd ',' valUnit ',' quantS     {   
                                                        Produto p = new Produto(); 
                                                        p.descr=$descProd.text;
                                                        p.prec=Double.parseDouble($valUnit.text);
                                                        p.qtd=Integer.parseInt($quantS.text);
                                                        $prodin.put($refProd.text, p);
                                                        $prodout=$prodin;
                                                    };

faturas[HashMap<String, Produto> prodts]:
    {
    System.out.println("Quantidades em Stock no meio:"); System.out.println($prodts.toString());
    }
	f1 = fatura[$prodts]
    ( ';' f2 = fatura[$prodts])*
    { System.out.println("Quantidades em Stock no fim:"); 
      System.out.println($prodts.toString());
    };

fatura[HashMap<String, Produto> prodin]:
	'FATURA' cabecalho 'VENDAS' corpo[$prodin] PONTO       {System.out.println("TOTAL da Fatura: "+ $corpo.totF);};

cabecalho: numFat ':' fornecedor '/' cliente                        { System.out.println("FATURA num: " + $numFat.text);};

numFat: ID;

fornecedor: nome ':' morada ',' nif ',' nib;

cliente: nome ':' morada ',' nif;

nome: STR;

nif: STR;

morada: STR;

nib: STR;

corpo[HashMap<String, Produto> prodin] returns [Double totF]:
	 HIFEN l1=linha[$prodin] { $totF = $l1.totL; }
    ( HIFEN l2=linha[$prodin] { $totF += $l2.totL;} )*;

linha[HashMap<String, Produto> prodin] returns [Double totL]:
	refProd '|' quant       {   Produto p;
                                if ($prodin.containsKey($refProd.text)) {
                                    p = $prodin.get($refProd.text);
                                    if(Integer.parseInt($quant.text) <= p.qtd) {
                                        System.out.println($refProd.text +"-> "+ (p.prec * (Integer.parseInt($quant.text))));
                                        $totL = (p.prec * (Integer.parseInt($quant.text)));
                                        p.qtd -= Integer.parseInt($quant.text);
                                        $prodin.put($refProd.text,p);
                                    }
                                    else {System.out.println("ERRO: A quantia de venda do produto " + $refProd.text + " excede a quantidade em stock");
                                           $totL = 0.0;
                                          }
                                }
                                else  { 
                                    System.out.println("ERRO: A Referencia " + $refProd.text + " nao existe em Stock");
                                    $totL = 0.0;
                                } 
                            };
refProd: ID;

valUnit: NUM;

quant: NUM;

descProd: STR;

quantS: NUM;

/*--------------- LEXER ---------------------------------------*/
NUM: '0' ..'9'+ ('.' ('0' ..'9')+)?;

ID: ('a' ..'z' | 'A' ..'Z' | '_') ('a' ..'z' | 'A' ..'Z' | '0' ..'9' | '_' | '-' )*;



HIFEN:'-';

PONTO:'.';

WS: [ \t\r\n] -> skip;

STR: '"' ( ~('"'))* '"';
