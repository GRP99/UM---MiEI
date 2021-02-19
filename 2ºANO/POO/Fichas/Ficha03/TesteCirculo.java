public class TesteCirculo{
    public static void main(String [] args){
        Circulo c = new Circulo();
        c.setRaio(3);
        System.out.println(c);
        c.alteraCentro(2,3);
        Circulo c2 = c.clone();
        System.out.println(c.equals(c2));
        c2.setRaio(5);
        System.out.println(c.equals(c2) + " " + c.calculaArea() + " " + c.calculaPerimetro() + " Circulo1: " + c + " Circulo2: " + c2);
    }
}