
/**
 * Write a description of class TesteGEncomendas here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.time.LocalDate;

public class TesteGEncomendas {

    public static void main(String[] args) {

        // criar linhas encomenda
        // public LinhaEncomenda(String referencia, String descricao, double preco,
        //            int quantidade, double imposto, double desconto)
        LinhaEncomenda l1 = new LinhaEncomenda("r1","parafuso", 2.5, 20, .23, 0.1);
        LinhaEncomenda l2 = new LinhaEncomenda("r2","martelo", 25, 10, .23, 0.1);
        LinhaEncomenda l3 = new LinhaEncomenda("r3","prego", 12.5, 200, .23, 0.1);
        LinhaEncomenda l4 = new LinhaEncomenda("r4","lixa", 5.5, 5, .23, 0.1);

        LinhaEncomenda l5 = new LinhaEncomenda("b1","portatil", 2500, 50, .23, 0.1);
        LinhaEncomenda l6 = new LinhaEncomenda("b2","monitor", 500, 40, .23, 0.1);
        LinhaEncomenda l7 = new LinhaEncomenda("b3","adaptador", 50, 50, .23, 0.1);
        LinhaEncomenda l8 = new LinhaEncomenda("b4","usb pen", 10, 50, .23, 0.1);

        LinhaEncomenda l9 = new LinhaEncomenda("p1","pneu", 250, 10, .23, 0.1);
        LinhaEncomenda l10 = new LinhaEncomenda("p2","porca", 2.5, 50, .23, 0.1);
        LinhaEncomenda l11 = new LinhaEncomenda("p3","tampa", 5, 10, .23, 0.1);

        // criar encomendas
        // public Encomenda(String nomeCliente, int nif, String morada, int nEnc,
        //                LocalDate data, List<LinhaEncomenda> encomendas)

        ArrayList<LinhaEncomenda> a1 = new ArrayList<>();
        a1.add(l1);
        a1.add(l2);
        a1.add(l3);
        a1.add(l4);
        Encomenda e1 = new Encomenda("Antonio", 195591, "Braga", 1, LocalDate.now(),a1);

        ArrayList<LinhaEncomenda> a2 = new ArrayList<>();
        a2.add(l5);
        a2.add(l6);
        a2.add(l7);
        a2.add(l8);
        Encomenda e2 = new Encomenda("José", 190091, "Porto", 2, LocalDate.of(2018,04,10),a2);

        ArrayList<LinhaEncomenda> a3 = new ArrayList<>();
        a3.add(l9);
        a3.add(l10);
        a3.add(l11);

        Encomenda e3 = new Encomenda("Pedro", 290097, "Lisboa", 3, LocalDate.of(2018,03,9),a3);

        GestaoEncomendas ge = new GestaoEncomendas();
        ge.addEncomenda(e1);
        ge.addEncomenda(e2);
        ge.addEncomenda(e3);

        System.out.println("Sistema de Gestão de Encomendas:");
        System.out.println(ge.toString());

        System.out.println("\n \nValores das encomendas:");
        System.out.println("E1 = :" + e1.calculaValorTotal());
        System.out.println("E2 = :" + e2.calculaValorTotal());
        System.out.println("E3 = :" + e3.calculaValorTotal());

        System.out.println("Valor ordem descrescente:");
        System.out.println(ge.encomendasValorDecrescente());

        System.out.println("Encomenda com mais produtos:");
        System.out.println(ge.encomendaComMaisProdutos());

    }
}
