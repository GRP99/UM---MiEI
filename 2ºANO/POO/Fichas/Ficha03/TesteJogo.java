public class TesteJogo{
    public static void main(String [] args){
        JogoFutebol jf1 = new JogoFutebol();
        System.out.println(jf1.resultadoActual() + "\n");
        jf1.startGame();
        jf1.goloVisitado();
        jf1.goloVisitante();
        jf1.goloVisitante();
        JogoFutebol jf2 = new JogoFutebol(jf1);
        jf1.endGame();
        System.out.println(jf1.resultadoActual() + "\n");
        jf2.goloVisitado();
        System.out.println(jf1.equals(jf2) + "\n");
        
    }
}
