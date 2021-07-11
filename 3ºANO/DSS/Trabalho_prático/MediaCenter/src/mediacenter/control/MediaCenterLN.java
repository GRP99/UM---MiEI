package mediacenter.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mediacenter.model.Conteudo;
import mediacenter.model.InfoMusica;
import mediacenter.model.Perfil;
import mediacenter.model.PlayConteudo;

public class MediaCenterLN {
    private BGDAO bibliotecageral;
    private PlayConteudo player;
    private PerfilDAO perfis;

    public MediaCenterLN() {
        this.bibliotecageral = new BGDAO();
        this.player = new PlayConteudo();
        this.perfis = new PerfilDAO();
    }

    public int login(String email, String password) { // 2 ou maior que 2 utilizador , 1 incorreto login
        if (this.perfis.containsKey(email)) {
            Perfil p = this.perfis.get(email);
            String pass = p.getPassword();
            if (pass.equals(password)) {
                if (p.getAdmin()) {
                    return 3;
                } else {
                    return 2;
                }
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public void reproduzirConteudo(int idConteudo) {
        Conteudo c = this.bibliotecageral.get(idConteudo);
        this.player.reproduzir(c);
    }

    public Map<String, List<String>> getConteudoCategoria(int idConteudo) {
        return this.bibliotecageral.getCategorias(idConteudo);
    }

    public void addCategoria(String categoria, int idConteudo, String email) {
        this.bibliotecageral.putCategoria(categoria, idConteudo, email);
    }

    public void criaConteudo(String email, String nome, String local, String artista, String album, String tipo, String imagem) {
        int idConteudo = this.bibliotecageral.getIDConteudo(nome, artista, album, tipo);
        if (idConteudo != -1) {
            this.bibliotecageral.novoDono(idConteudo, email);
        } else {
            List<String> donos = new ArrayList<>();
            donos.add(email);
            Conteudo c = new Conteudo(nome, artista, album, tipo, imagem, local, new HashMap<>(), donos);
            this.bibliotecageral.putConteudo(c);
        }
    }

    public List<InfoMusica> mostraBiblioteca_geral() {
        return this.bibliotecageral.mostraBiblioteca_geral();
    }

    public List<InfoMusica> mostraBiblioteca_individual(String email) {
        return this.bibliotecageral.mostraBiblioteca_individual(email);
    }

    public void removeCategoria(String cat, String email, int idCounteudo) {
        this.bibliotecageral.removeCategoria(cat, email, idCounteudo);
    }

}
