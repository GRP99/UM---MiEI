import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;

public class TesteHoteisInc {

    public static void main(String[] args) {

        HoteisInc osHoteis = new HoteisInc();
        osHoteis.setNome("Cama e Pequeno Almoço");

        HotelStandard hs1 = new HotelStandard("ha", "BragaCentro", "Braga", 45.0, false, 100, 4);
        HotelStandard hs2 = new HotelStandard("hb", "BragaUM", "Braga", 25.0, false, 150, 3);

        HotelPremium hp1 = new HotelPremium("hc", "Falperra", "Braga", 75.0, 60.0, 120, 4);
        HotelPremium hp2 = new HotelPremium("hd", "RioEste", "Braga", 95.0, 83.0, 90, 5);

        HotelDiscount hd1 = new HotelDiscount("he", "Avenida", "Porto", 50.0, 50, 4, 50.0);
        HotelDiscount hd2 = new HotelDiscount("hf", "Alameda", "Porto", 60.0, 75, 4, 50.0);

        // adicionar hotéis
        osHoteis.adiciona(hs1);
        osHoteis.adiciona(hp1);
        osHoteis.adiciona(hd1);
        osHoteis.adiciona(hs2);
        osHoteis.adiciona(hp2);
        osHoteis.adiciona(hd2);

        //ver informação de um hotel
        try {
            Hotel h = osHoteis.getHotel("bla");
            System.out.println(h);
            // mais codigo que liberta excepções
            //
        }

        catch (HotelInexistenteException e) {
            System.out.println("Hotel " + e.getMessage() + " não existe!!!");}

        /*
        catch (HotelXXXX e) {...}
        catch (HotelXXX e) {...}
        catch (IOException e) {...}
        catch (Exception e) {...}
         */

        //acrescentar um comparador (o ComparadorNome) ao 
        //map de comparadores. Atenção: é uma variável de classe, logo devemos
        //utilizar o método de classe existente para o efeito

        HoteisInc.juntaOrdenacao("Por Nome", new ComparadorNome());
        //acrescentar outro comparador
        HoteisInc.juntaOrdenacao("Por Num Quartos", new ComparadorQuartos());

        //criar uma ordenação por nome

        Iterator<Hotel> it = osHoteis.ordenarHoteis("Por Nome");
        System.out.println("Iterar o resultado da ordenação");
        while (it.hasNext())
            System.out.println(it.next());

        //Utilização do List.sort
        List<Hotel> listOrd = osHoteis.ordenarHoteisListStream(new ComparadorQuartos());
        System.out.println("----- Lista ordenada por quartos -----");
        System.out.println(listOrd);
        System.out.println("  ");

        //Invocar o toString()
        System.out.println("Invocar o toString()");
        System.out.println("---- HOTÉIS ----");
        System.out.println(osHoteis.toString());

        //Gravar em ficheiro de texto

        try {
            osHoteis.escreveEmFicheiroTxt("estadoHoteisTXT.txt");
        }
        catch (FileNotFoundException e) {
            System.out.println("Erro a aceder a ficheiro!");}

        //Gravar em ficheiro de objectos
        try {
            osHoteis.guardaEstado("estadoHoteis.obj");
        }
        catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");}
        catch (IOException e) {
            System.out.println("Erro a aceder a ficheiro!");}

        //recuperar de ficheiro de texto (no formato de linhas com os campos separados por virgulas)     
        try {
            HoteisInc novoHoteis = HoteisInc.importaCSV("NOVOHOTEL", "estadoHoteisTXT.txt");
            System.out.println("Dados recuperados do CSV: ");
            System.out.println(novoHoteis.toString());
        }
        catch (IOException e) {System.out.println("Não foi possível ler o ficheiro CSV.");}
    }
}
