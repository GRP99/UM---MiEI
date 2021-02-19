public class TesteTriangulo{
    public static void main(String [] args){
        Ponto p1 = new Ponto (3,3);
        Ponto p2 = new Ponto (6,3);
        Ponto p3 = new Ponto (3,5);
        Triangulo t1 = new Triangulo (p1,p2,p3);
        System.out.println("\n" + t1.calculaAreaTriangulo());
        Triangulo t2 = t1.clone();
        System.out.println(t1.equals(t2));
        Ponto novo = new Ponto (4,10);
        t1.setP2(novo);
        System.out.println("Perimetro : " + t1.calculaPerimetroTriangulo() + "\n" + "Altura" + t1.alturaTriangulo());
        System.out.println("Ponto 3: " + t2.getP3());
    }
}
