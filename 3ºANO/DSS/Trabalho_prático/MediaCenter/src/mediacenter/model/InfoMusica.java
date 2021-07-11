package mediacenter.model;

public class InfoMusica {
    private int identificador;
    private String musica;
    private String artista;
    private String album;
    private String imagem;
    private String tipo;

    public InfoMusica(int i, String m, String a, String al, String im, String ti) {
        this.identificador = i;
        this.musica = m;
        this.artista = a;
        this.album = al;
        this.imagem = im;
        this.tipo = ti;
    }

    public int getIdentificador() {
        return this.identificador;
    }

    public String getMusica() {
        return this.musica;
    }

    public String getArtista() {
        return this.artista;
    }

    public String getAlbum() {
        return this.album;
    }

    public String getImagem() {
        return this.imagem;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InfoMusica:: {");
        sb.append("ID: ").append(this.identificador);
        sb.append("Nome: ").append(this.musica);
        sb.append("Artista: ").append(this.artista);
        sb.append("Album: ").append(this.album);
        sb.append("Tipo: ").append(this.tipo);
        sb.append("Imagem: ").append(this.imagem).append("} \n");;
        return sb.toString();
    }
}
