package HashCode_Comparator;


/**
 * Write a description of class Código here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.TreeSet;
import java.util.Comparator;

public class Código {

  public static void main(String[] args) {
      
  TreeSet<Aluno> alunos = new TreeSet<>(new ComparatorAlunoNome());
    
  TreeSet<Aluno> turma = new TreeSet<>();
  
  TreeSet<Aluno> teóricas = new TreeSet<>(
                            new Comparator<Aluno>() {
                               public int compare(Aluno a1, Aluno a2) {
                                 return a1.getNome().compareTo(a2.getNome());
                               }
                            });
                       
  TreeSet<Aluno> praticas = new TreeSet<>((a1,a2) -> 
                                           a1.getNome().compareTo(a2.getNome()));
                                           
  
  Comparator<Aluno> comparador = (a1, a2) -> a1.getNome().compareTo(a2.getNome());
  
  TreeSet<Aluno> tutorias = new TreeSet<>(comparador);
  
  
  
                                          
                                        
}
}
