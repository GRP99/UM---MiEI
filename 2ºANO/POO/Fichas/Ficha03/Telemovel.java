/**
 * Classe que implementa um Telemovel 
 */
public class Telemovel{
    // VARIÁVEIS DE INSTÂNCIA
    private String marca;
    private String modelo;
    private int x;
    private int y;
    private String [] armazSMS;
    private double armazTotal;
    private double armazFotos;
    private double armazApps;
    private double espacoTotalocu;
    private int nfotos;
    private int napp;
    private String [] apps;

    public Telemovel(){
        this.marca = " ";
        this.modelo = " ";
        this.x = 0;
        this.y = 0;
        this.armazSMS = null;
        this.armazFotos = 0;
        this.armazApps = 0;
        this.armazTotal = this.armazFotos + this.armazApps;
        this.espacoTotalocu = 0;
        this.nfotos = 0;
        this.napp = 0;
        this.apps = new String [napp];
    }

    public Telemovel(String marca, String modelo, int x, int y, String [] armazSMS, double armzTotal, double armazFotos, double armazApps, double espacoTotalocu, int nfotos, int napp, String [] apps){
        this.marca = marca;
        this.modelo = modelo;
        this.x = x;
        this.y = y;
        this.armazSMS = armazSMS;
        this.armazTotal = armazTotal;
        this.armazFotos = armazFotos;
        this.armazApps = armazApps;
        this.espacoTotalocu = espacoTotalocu;
        this.nfotos = nfotos;
        this.napp = napp;
        this.apps = apps;
    }

    public Telemovel (Telemovel outro){
        this.marca = outro.getMarca();
        this.modelo = outro.getModelo();
        this.x = outro.getX();
        this.y = outro.getY();
        this.armazSMS = outro.getArmazSMS();
        this.armazTotal = outro.getArmazTotal();
        this.armazFotos = outro.getArmazFotos();
        this.armazApps = outro.getArmazApps();
        this.espacoTotalocu = outro.getEspacoTotalocu();
        this.nfotos = outro.getNfotos();
        this.napp = outro.getNapp();
        this.apps = outro.getApps();
    }

    public String getMarca(){
        return this.marca;
    }

    public String getModelo(){
        return this.modelo;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    } 

    public String [] getArmazSMS(){
        return this.armazSMS;
    }  

    public double getArmazTotal(){
        return this.armazTotal;
    }  

    public double getArmazFotos(){
        return this.armazFotos;
    }

    public double getArmazApps(){
        return this.armazApps;
    }

    public double getEspacoTotalocu(){
        return this.espacoTotalocu;
    }

    public int getNfotos(){
        return this.nfotos;
    }

    public int getNapp(){
        return this.napp;
    } 

    public String [] getApps(){
        return this.apps;
    }

    public void setMarca(String newMarca){
        this.marca = newMarca;
    }

    public void setModelo(String newModelo){
        this.modelo = newModelo;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public void setY(int newY){
        this.y = newY;
    }

    public void setArmazSMS(String [] newArmazSMS){
        this.armazSMS = newArmazSMS;
    }

    public void setArmazTotal(double newArmazTotal){
        this.armazTotal = newArmazTotal;
    }

    public void setArmazFotos(double newArmazFotos){
        this.armazFotos = newArmazFotos;
    }

    public void setArmazApps(double newArmazApps){
        this.armazApps = newArmazApps;
    }

    public void setEspacoTotalocu(double newEspacoTotalocu){
        this.espacoTotalocu = newEspacoTotalocu;
    }

    public void setNfotos(int newNfotos){
        this.nfotos = newNfotos;
    }

    public void setNapp(int newNapp){
        this.napp=newNapp;
    }

    public void setApps(String [] newApps){
        this.apps = newApps;
    }

    public boolean existeEspaco(int numeroBytes){
        return (numeroBytes < ((this.armazTotal)-(this.espacoTotalocu)));
    }

    public void instalaApp(String nome, int tamanho){
        int flag = 0;
        if (tamanho <= (this.armazApps - this.espacoTotalocu)){
            espacoTotalocu += tamanho;
            if (this.napp == 0){
                this.napp ++;
                String [] nova = new String[this.napp];
                nova[0] = nome;
                setApps(nova);
            }
            else {
                if (this.napp < (this.apps).length){
                    this.apps[this.napp] = nome;
                    this.napp++;
                }
                else{
                    String [] copy = new String[this.napp*2];
                    for(int i = 0; i < (this.apps).length; i++){
                        copy[i] = this.apps[i];
                    }
                    copy[this.napp] = nome;
                    this.napp++;
                    setApps(copy);
                }
            }
        }  
    }

    public void recebeMsg(String msg){
        if (((this.armazSMS).length)==0){
            String [] nova = new String [10];
            nova[0] = msg;
            setArmazSMS(nova);
        }
        else {
            int x = ((this.armazSMS).length);
            if ((this.armazSMS)[x-1] == null){
                for(int i=0; i < ((this.armazSMS).length); i++){
                    if ((this.armazSMS)[i] == null) this.armazSMS[i] = msg;
                }
            }
            else {
                String [] copy = new String[((this.armazSMS).length)*2];
                for(int i = 0; i < ((this.armazSMS).length); i++){
                    copy[i] = this.armazSMS[i];
                }
                copy[((this.armazSMS).length)]=msg;
                setArmazSMS(copy);
            }
        }
    }

    public double tamMedioApps(){
        double media;
        media = this.espacoTotalocu / this.napp;
        return media;
    }

    public String maiorMsg(){
        int max = 0;
        String mas = " ";
        String atual = " ";
        for(int j=0; j<(this.armazSMS).length; j++){
            atual = this.armazSMS[j];
            int tam = (atual).length();
            if (tam > max){
                max = tam; mas = atual;
            }
        }
        return atual;
    }

    public void removeApp(String nome, int tamanho){
        for (int i = 0; i<(this.apps).length; i++){
            if (nome == this.apps[i]){
                this.espacoTotalocu-=tamanho;
                this.apps[i] = null;
            }
        }
    }

    public String toString(){
        return "Marca:" + marca + "    Modelo:" + modelo + "    Dimensão:" + x + "x" + y + "    Armazenamento Total:" + armazTotal + "  Armazenamento Fotos:" + armazFotos + "  Armazenamento Apps:" + armazApps + "    Espaço Total ocupado:" + espacoTotalocu + " Numero de fotos:" + nfotos + "Número de apps: " + napp + " SMS " +  armazSMS + "APPS" + apps;
    }

    public Telemovel clone (){
        return new Telemovel(this);
    }

    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Telemovel c = (Telemovel) o;
        boolean flag1 = true;
        boolean flag2 = true;
        //Falta comparar os arrays de SMS E APPS
        return this.marca==c.getMarca() && this.modelo==getModelo() && this.x==c.getX() && this.y==c.getY() && this.armazTotal==c.getArmazTotal() && this.armazFotos==c.getArmazFotos() && this.armazApps==c.getArmazApps() && this.espacoTotalocu==c.getEspacoTotalocu() && this.nfotos==c.getNfotos() && this.napp==c.getNapp() && flag1 && flag2;
    }
}