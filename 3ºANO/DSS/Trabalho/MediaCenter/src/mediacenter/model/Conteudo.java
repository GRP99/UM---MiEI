package mediacenter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conteudo {

    private String nome;
    private String artista;
    private String album;
    private String tipo;
    private String imagem;
    private String ficheiro;
    private Map<String, List<String>> categoria;
    private List<String> donos;

    public Conteudo(String no, String ar, String al, String ti, String im, String fi, Map<String, List<String>> ca, List<String> dos) {
        this.nome = no;
        this.artista = ar;
        this.album = al;
        this.tipo = ti;
        this.imagem = im;
        this.ficheiro = fi;
        this.categoria = ca;
        this.donos = dos;
    }

    public String getNome() {
        return this.nome;
    }

    public String getArtista() {
        return this.artista;
    }

    public String getAlbum() {
        return this.album;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getImagem() {
        return this.imagem;
    }

    public String getFicheiro() {
        return this.ficheiro;
    }

    public Map<String, List<String>> getCategorias() {
        Map<String, List<String>> res = new HashMap<>();
        for (String x : categoria.keySet()) {
            List<String> re = new ArrayList<>();
            List<String> c = categoria.get(x);
            for (String k : c) {
                re.add(k);
            }
            res.put(x, re);
        }
        return res;
    }

    public List<String> getDonos() {
        List<String> res = new ArrayList<>();
        for (String c : donos) {
            res.add(c);
        }
        return donos;
    }

    public void putCategoria(String email, String ctgr) {
        List<String> aux = this.getCategorias().get(email);
        aux.add(ctgr);
        this.categoria.put(email, aux);
    }

    public void addDono(String email) {
        this.donos.add(email);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Conteudo:: {");
        sb.append("Nome: ").append(this.nome);
        sb.append("Artista: ").append(this.artista);
        sb.append("Album: ").append(this.album);
        sb.append("Tipo: ").append(this.tipo);
        sb.append("Imagem: ").append(this.imagem);
        sb.append("Categoria: ").append(this.categoria);
        sb.append("Donos: ").append(this.donos).append("} \n");
        return sb.toString();
    }
}