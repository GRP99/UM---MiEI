import java.util.ArrayList;
import java.util.List;

public class StackTeste{
   public static void main (String [] args){
       Stack s = new Stack();
       System.out.println(s.length() + " " + s.empty());
       for (int i = 0; i < 10; i++){
           s.push("String".concat(Integer.toString(i)));
       }
       Stack s2 = s.clone();
       System.out.println(s2.equals(s) + " " + s.equals(s));
       for (int i = 0; i < 5; i++){
           s.pop();
       }
       System.out.println(s.top() + " " + s2.top() + "\n" + s + "\n" + s2);
   }
}
