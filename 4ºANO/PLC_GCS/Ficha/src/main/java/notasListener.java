// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\notas.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link notasParser}.
 */
public interface notasListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link notasParser#turmas}.
	 * @param ctx the parse tree
	 */
	void enterTurmas(notasParser.TurmasContext ctx);
	/**
	 * Exit a parse tree produced by {@link notasParser#turmas}.
	 * @param ctx the parse tree
	 */
	void exitTurmas(notasParser.TurmasContext ctx);
	/**
	 * Enter a parse tree produced by {@link notasParser#turma}.
	 * @param ctx the parse tree
	 */
	void enterTurma(notasParser.TurmaContext ctx);
	/**
	 * Exit a parse tree produced by {@link notasParser#turma}.
	 * @param ctx the parse tree
	 */
	void exitTurma(notasParser.TurmaContext ctx);
	/**
	 * Enter a parse tree produced by {@link notasParser#alunos}.
	 * @param ctx the parse tree
	 */
	void enterAlunos(notasParser.AlunosContext ctx);
	/**
	 * Exit a parse tree produced by {@link notasParser#alunos}.
	 * @param ctx the parse tree
	 */
	void exitAlunos(notasParser.AlunosContext ctx);
	/**
	 * Enter a parse tree produced by {@link notasParser#aluno}.
	 * @param ctx the parse tree
	 */
	void enterAluno(notasParser.AlunoContext ctx);
	/**
	 * Exit a parse tree produced by {@link notasParser#aluno}.
	 * @param ctx the parse tree
	 */
	void exitAluno(notasParser.AlunoContext ctx);
	/**
	 * Enter a parse tree produced by {@link notasParser#nome}.
	 * @param ctx the parse tree
	 */
	void enterNome(notasParser.NomeContext ctx);
	/**
	 * Exit a parse tree produced by {@link notasParser#nome}.
	 * @param ctx the parse tree
	 */
	void exitNome(notasParser.NomeContext ctx);
	/**
	 * Enter a parse tree produced by {@link notasParser#notas}.
	 * @param ctx the parse tree
	 */
	void enterNotas(notasParser.NotasContext ctx);
	/**
	 * Exit a parse tree produced by {@link notasParser#notas}.
	 * @param ctx the parse tree
	 */
	void exitNotas(notasParser.NotasContext ctx);
}