public class TesteLinhaEncomenda{
    public static void main(String [] args){
        LinhaEncomenda le = new LinhaEncomenda("6593431", "Telemóvel", 13, 300, 0.23, 0.20);
        System.out.println(le.getDescricao());
        System.out.println(le.calculaValorLinhaEnc());
        System.out.println(le.calculaValorDesconto());
        LinhaEncomenda le2 = le.clone();
        System.out.println(le.equals(le2));
        le2.setDesconto(10);
        System.out.println("Linha de Ecomenda 2 " + le2);
        
    }
}
