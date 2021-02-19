public class JogoFutebol{
    
    private String estado;
    private int visitado;
    private int visitante;
    
    public JogoFutebol() {
        this.estado = "por iniciar";
        this.visitado = 0;
        this.visitante = 0;
    }
    
    public JogoFutebol(String nestado,int nvisitado, int nvisitante){
        this.estado = nestado;
        this.visitado = nvisitado;
        this.visitante = nvisitante;
    }
    
    public JogoFutebol(JogoFutebol outro){
        this.estado = getEstado();
        this.visitado = getVisitado();
        this.visitante = getVisitante();
    }
    
    public String getEstado(){
        return this.estado;
    }
    
    public int getVisitado(){
        return this.visitado;
    }
    
    public int getVisitante(){
        return this.visitante;
    }
    
    public void startGame(){
        this.estado = "a decorrer";
    }
    
    public void endGame(){
        this.estado = "terminado";
    }
    
    public void goloVisitado(){
        this.visitado += 1;
    }
    
    public void goloVisitante(){
        this.visitante += 1;
    }
    
    public String resultadoActual(){
        return ("Estado do jogo: " + this.estado + "\n" + "Visitado: " + this.visitado + " vs " + "Visitante: " + this.visitante);
    }
    
    public JogoFutebol clone(){
        return new JogoFutebol(this);
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        JogoFutebol jf = (JogoFutebol) o;
        return (this.estado.equals(jf.getEstado()) && (this.visitado == jf.getVisitado()) && (this.visitante == jf.getVisitante()));
    }
}
