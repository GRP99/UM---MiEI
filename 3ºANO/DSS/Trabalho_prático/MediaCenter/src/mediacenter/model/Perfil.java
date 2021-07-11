package mediacenter.model;

public class Perfil {
    private String email;
    private String nome;
    private String password;
    private String imagem;
    private boolean admin;

    public Perfil(String e, String n, String p, String i, boolean a) {
        this.email = e;
        this.nome = n;
        this.password = p;
        this.imagem = i;
        this.admin = a;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Perfil:: {");
        sb.append("Email: ").append(this.email);
        sb.append("Nome: ").append(this.nome);
        sb.append("Password: ").append(this.password);
        sb.append("Imagem: ").append(this.imagem);
        sb.append("Admin: ").append(this.admin).append("} \n");
        return sb.toString();
    }
}
