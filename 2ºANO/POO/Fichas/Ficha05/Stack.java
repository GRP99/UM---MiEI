import java.util.ArrayList;
import java.util.List;

public class Stack{
    private List<String> stack;

    public Stack(){
        this.stack = new ArrayList<>();
    }

    public Stack(Stack o){
        this.stack = o.getStack();
    }

    public Stack (List<String> stack){
        this.setStack(stack);
    }

    public List<String> getStack(){
        List<String> copia = new ArrayList <> (stack.size());
        for (String s : stack){
            copia.add(s);
        }
        return copia;
    }

    public void setStack(List <String> stk){
        this.stack = new ArrayList <>(stk.size());
        for(String s: stk){
            this.stack.add(s);
        }
    }

    public String top(){
        return stack.get(0);
    }

    public void push (String s){
        this.stack.add(0,s);
    }

    public void pop (){
        if (!(stack.isEmpty())) stack.remove(0);
    }

    public boolean empty(){
        return stack.isEmpty();
    }

    public int length(){
        return stack.size();
    }

    public Stack clone(){
        return new Stack(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(stack.toString());
        return sb.toString();
    }

    public boolean equals (Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Stack s = (Stack) o;
        return s.getStack().equals(stack);
    }
}
