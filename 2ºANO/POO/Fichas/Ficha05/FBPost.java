import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
/**
 * Classe que representa um post
 *
 */
public class FBPost {
    private int id;
    private String username;
    private LocalDateTime data;
    private String post;
    private int likes;
    private List<String> comentarios;

    public FBPost() {
        this.id = 0;
        this.username = "n/a";
        this.data = LocalDateTime.now();
        this.post = "n/a";
        this.likes = 0;
        this.comentarios = new ArrayList<>();
    }

    public FBPost(FBPost post) {
        this.id = post.getId();
        this.username = post.getUsername();
        this.data = post.getData();
        this.post = post.getPost();
        this.likes = post.getLikes();
        this.comentarios = post.getComentarios();
    }

    public FBPost(int id, String username, LocalDateTime data, String post, int likes, List<String> comentarios) {
        this.id = id;
        this.username = username;
        this.data = data;
        this.post = post;
        this.likes = likes;
        setComentarios(comentarios);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getData() {
        return this.data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getComentarios() {
        List<String> res = new ArrayList<>();
        for(String s : comentarios) {
            res.add(s);
        }
        return res;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = new ArrayList<>();
        comentarios.forEach(s -> {this.comentarios.add(s);});
    }

    protected FBPost clone() {
        return new FBPost(this);
    }

    public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj==null || obj.getClass()!=this.getClass()) return false;
        FBPost p = (FBPost) obj;
        return this.id == p.getId() && this.username.equals(p.getUsername()) && 
        this.getPost().equals(p.getPost()) && this.likes == likes &&
        this.comentarios.equals(p.getComentarios()) && this.data.equals(p.getData());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\nUsername: ").append(username)
        .append("\nData: ").append(data).append("\nPost: ").append(post)
        .append("\nLikes: ").append(likes)
        .append("\nComentarios: ").append(comentarios.toString());
        return sb.toString();
    }

}