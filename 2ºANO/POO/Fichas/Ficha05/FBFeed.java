import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;

public class FBFeed{
    private List<FBPost> feed;

    public FBFeed(){
        this.feed = new ArrayList<>();
    }

    public FBFeed(FBFeed fbf){
        this.feed = fbf.getPosts();
    }

    public FBFeed(List<FBPost> fbf){
        setPosts(fbf);
    }

    public List<FBPost> getPosts() {
        return this.feed.stream().map(FBPost::clone).collect(Collectors.toList());        
    }

    public void setPosts(List<FBPost> fbf) {
        this.feed = new ArrayList<>();
        for(FBPost p : fbf) {
            this.feed.add(p.clone());
        }
    }

    public FBFeed clone(){
        return new FBFeed(this);
    }

    public boolean equals(Object o) {
        if(o==this) return true;
        if(o==null || o.getClass() != this.getClass()) return false;
        FBFeed fbf = (FBFeed) o;
        return this.feed.equals(fbf.getPosts()); 
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.feed.toString());
        return sb.toString();
    }

    public int nrPosts(String user){
        int nr=0;
        for(FBPost f: this.feed){
            if(f.getUsername().equals(user)) nr++;
        }
        return nr;
    }

    public List<FBPost> postsOf(String user){
        List<FBPost> posts = new ArrayList<>();
        for(FBPost f: this.feed){
            if (f.getUsername().equals(user)){
                posts.add(f.clone());
            }
        }
        return posts;
    }

    public List<FBPost> postsOf(String user,LocalDateTime inicio, LocalDateTime fim){
        return feed.stream().filter(p->p.getUsername() == user && p.getData().compareTo(inicio)>0 && p.getData().compareTo(fim)<0).collect(Collectors.toList());
    }

    public FBPost getPost(int id){
        FBPost post = new FBPost();
        for(FBPost f: this.feed){
            if(f.getId() == id){
                post = f.clone();
            }
        }
        return post;
    }

    public void comment(FBPost post, String comentario){
        boolean flag = true;
        FBPost aux = new FBPost();
        List<String> old = new ArrayList<>();
        Iterator<FBPost> it = this.feed.iterator();
        while(it.hasNext() && flag){
            aux = it.next();
            if (aux.getId() == post.getId()){
                int i = this.feed.indexOf(post);
                old = post.getComentarios();
                this.feed.remove(post);
                old.add(comentario);
                post.setComentarios(old);
                this.feed.add(i,post);
                flag = false;
            }
        }
    }

    public void comment(int postId, String comentario){
        boolean flag = true;
        FBPost aux = new FBPost();
        List<String> old = new ArrayList<>();
        Iterator<FBPost> it = this.feed.iterator();
        while(it.hasNext() && flag){
            aux = it.next();
            if (aux.getId() == postId){
                int i = this.feed.indexOf(aux);
                old = aux.getComentarios();
                this.feed.remove(aux);
                old.add(comentario);
                aux.setComentarios(old);
                this.feed.add(i,aux);
                flag = false;
            }
        }
    }

    public void like(FBPost post){
        int aux = 0;
        boolean flag = true;
        FBPost a = new FBPost();
        Iterator<FBPost> it = this.feed.iterator();
        while(it.hasNext() && flag){
            a = it.next();
            if (a.getId() == post.getId()){
                int i = this.feed.indexOf(post);
                aux = post.getLikes();
                this.feed.remove(post);
                aux++;
                post.setLikes(aux);
                this.feed.add(i,post);
                flag = false;
            }
        }
    }

    public void like (int postid){
        int aux = 0;
        boolean flag = true;
        FBPost a = new FBPost();
        Iterator<FBPost> it = this.feed.iterator();
        while(it.hasNext() && flag){
            a = it.next();
            if (a.getId() == postid){
                int i = this.feed.indexOf(a);
                aux = a.getLikes();
                this.feed.remove(a);
                aux++;
                a.setLikes(aux);
                this.feed.add(i,a);
                flag = false;
            }
        }
    }

    public List<Integer> top5Comments(){
        List<Integer> lista = new ArrayList<Integer>();
        if (this.feed.size() == 0) return lista;
        else{
            for(FBPost f: this.feed){
                if(lista.size() <= 5){
                    lista.add(f.getComentarios().size());
                    Collections.sort (lista, new Comparator() {
                            public int compare(Object o1, Object o2) {
                                Integer i1 = (Integer) o1;
                                Integer i2 = (Integer) o2;
                                if (i1 == i2) return 0;
                                if (i1 > i2) return 1;
                                return (-1);
                            }
                        });
                }
                else{
                    if (getPost(lista.get(0)).getComentarios().size() <= f.getComentarios().size()){
                        lista.remove(0);
                        lista.add(0,f.getComentarios().size());
                        Collections.sort (lista, new Comparator() {
                                public int compare(Object o1, Object o2) {
                                    Integer i1 = (Integer) o1;
                                    Integer i2 = (Integer) o2;
                                    if (i1 == i2) return 0;
                                    if (i1 > i2) return 1;
                                    return (-1);
                                }
                            });
                    }
                }
            }
        }
        return lista;
    }
}

