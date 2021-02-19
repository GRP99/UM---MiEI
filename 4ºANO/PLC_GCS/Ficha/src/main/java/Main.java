import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class Main {
    /*
        public static void main(String[] args) {
            try {
                listasLexer lexer = new listasLexer(CharStreams.fromFileName("C:\\Users\\Gonçalo Pinto\\Documents\\@Goncalo\\Universidade\\4ºANO\\1Semestre\\PLC_Gramáticas na Compreensão de Software\\Fichas de Exercícios para as Aulas\\Ficha01\\exemploListas.txt"));
                CommonTokenStream stream = new CommonTokenStream(lexer);
                listasParser parser = new listasParser(stream);
                parser.listas();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            try {
                notasLexer lexer = new notasLexer(CharStreams.fromFileName("C:\\Users\\Gonçalo Pinto\\Documents\\@Goncalo\\Universidade\\4ºANO\\1Semestre\\PLC_Gramáticas na Compreensão de Software\\Fichas de Exercícios para as Aulas\\Ficha01\\exemploNotas.txt"));
                CommonTokenStream stream = new CommonTokenStream(lexer);
                notasParser parser = new notasParser(stream);
                parser.turmas();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     */
    public static void main(String[] args) {
        try {
            gestaoLexer lexer = new gestaoLexer(CharStreams.fromFileName("C:\\Users\\Gonçalo Pinto\\Documents\\@Goncalo\\Universidade\\4ºANO\\1Semestre\\PLC_Gramáticas na Compreensão de Software\\Fichas de Exercícios para as Aulas\\Ficha01\\exemploGestao.txt"));
            CommonTokenStream stream = new CommonTokenStream(lexer);
            gestaoParser parser = new gestaoParser(stream);
            parser.gestao();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}