// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\notas.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link notasParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface notasVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link notasParser#turmas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTurmas(notasParser.TurmasContext ctx);
	/**
	 * Visit a parse tree produced by {@link notasParser#turma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTurma(notasParser.TurmaContext ctx);
	/**
	 * Visit a parse tree produced by {@link notasParser#alunos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlunos(notasParser.AlunosContext ctx);
	/**
	 * Visit a parse tree produced by {@link notasParser#aluno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAluno(notasParser.AlunoContext ctx);
	/**
	 * Visit a parse tree produced by {@link notasParser#nome}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNome(notasParser.NomeContext ctx);
	/**
	 * Visit a parse tree produced by {@link notasParser#notas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotas(notasParser.NotasContext ctx);
}