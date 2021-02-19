/*********************************************************************************/
/** DISCLAIMER: Este cÃ³digo foi criado e alterado durante as aulas prÃ¡ticas      */
/** de POO. Representa uma soluÃ§Ã£o em construÃ§Ã£o, com base na matÃ©ria leccionada */ 
/** atÃ© ao momento da sua elaboraÃ§Ã£o, e resulta da discussÃ£o e experimentaÃ§Ã£o    */
/** durante as aulas. Como tal, nÃ£o deverÃ¡ ser visto como uma soluÃ§Ã£o canÃ³nica,  */
/** ou mesmo acabada. Ã‰ disponibilizado para auxiliar o processo de estudo.      */
/** Os alunos sÃ£o encorajados a testar adequadamente o cÃ³digo fornecido e a      */
/** procurar soluÃ§Ãµes alternativas, Ã  medida que forem adquirindo mais           */
/** conhecimentos de POO.                                                        */
/*********************************************************************************/

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
/**
 * Write a description of class TesteFBFeed here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TesteFBFeed
{
    public static void main(String[] args) {
        FBPost post0 = new FBPost(0, "User 1", LocalDateTime.of(2018,3,10,10,30,0), "Teste 1", 0, new ArrayList<>());
        FBPost post1 = new FBPost(1, "User 1", LocalDateTime.of(2018,3,12,15,20,0), "Teste 2", 0, new ArrayList<>());
        FBPost post2 = new FBPost(2, "User 2", LocalDateTime.now(), "Teste 3", 0, new ArrayList<>());
        FBPost post3 = new FBPost(3, "User 3", LocalDateTime.now(), "Teste 4", 0, new ArrayList<>());
        FBPost post4 = new FBPost(4, "User 4", LocalDateTime.now(), "Teste 5", 0, new ArrayList<>());

        FBFeed feed = new FBFeed();
        List<FBPost> tp = new ArrayList<>();
        tp.add(post0);
        tp.add(post1);
        tp.add(post2);
        tp.add(post3);
        tp.add(post4);
        feed.setPosts(tp);

        int np = feed.nrPosts("User 1");
        if(np==2) {
            System.out.println("NR posts ok");
        } else {
            System.out.println("Erro em NR posts");
        }

        FBPost p = feed.postsOf("User 2").get(0);
        if(p.getPost().equals("Teste 3")) {
            System.out.println("postsOf ok");
        } else {
            System.out.println("Erro em postsOf");
        }

        p = feed.getPost(3);
        if(p.getUsername().equals("User 3")) {
            System.out.println("getPost ok");
        } else {
            System.out.println("Erro em getPost");
        }

        feed.comment(p, "Primeiro comentario");
        if(p.getComentarios().get(0).equals("Primeiro comentario")) {
            System.out.println("comment ok");
        } else {
            System.out.println("Erro em comment");
        }

        feed.like(p);
        feed.like(p.getId());
        if(p.getLikes()==2) {
            System.out.println("like ok");
        } else {
            System.out.println("Erro em like");
        }

        List<FBPost> posts = feed.postsOf("User 1", LocalDateTime.of(2018,3,11,0,0,0),LocalDateTime.of(2018,3,15,0,0,0));
        if(posts.size()==1) {
            System.out.println("postsOf ok");
        } else {
            System.out.println("Erro em postsOf");
        }

        List<Integer> l = feed.top5Comments();
        if(l.size()==5) {
            System.out.println("Top5 ok");
        } else {
            System.out.println("Erro em Top5");
        }

        System.out.println("-- Post 3 --");
        System.out.println(p.toString());
    }
}
