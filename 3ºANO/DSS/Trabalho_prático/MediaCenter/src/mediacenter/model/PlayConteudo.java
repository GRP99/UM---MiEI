package mediacenter.model;

public class PlayConteudo {

    public PlayConteudo() {
    }

    public void reproduzir(Conteudo c) {
        System.out.println("file" + c.toString());
        try {
            System.out.println("file" + c.getFicheiro());
            Runtime.getRuntime().exec("vlc --one-instance " + c.getFicheiro());
        } catch (Exception e) {
            System.out.println("Erro a reproduzir!!!");
        }
    }
}
