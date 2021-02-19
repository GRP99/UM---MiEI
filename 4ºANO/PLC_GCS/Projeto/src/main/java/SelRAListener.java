// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/TPCs e Trabalhos Práticos/Projeto1\SelRA.g4 by ANTLR 4.9

    import java.io.*;
    import java.text.*;
    import java.util.*;
    import java.util.stream.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SelRAParser}.
 */
public interface SelRAListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SelRAParser#selRA}.
	 * @param ctx the parse tree
	 */
	void enterSelRA(SelRAParser.SelRAContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#selRA}.
	 * @param ctx the parse tree
	 */
	void exitSelRA(SelRAParser.SelRAContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#perfis}.
	 * @param ctx the parse tree
	 */
	void enterPerfis(SelRAParser.PerfisContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#perfis}.
	 * @param ctx the parse tree
	 */
	void exitPerfis(SelRAParser.PerfisContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#perfil}.
	 * @param ctx the parse tree
	 */
	void enterPerfil(SelRAParser.PerfilContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#perfil}.
	 * @param ctx the parse tree
	 */
	void exitPerfil(SelRAParser.PerfilContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#desc}.
	 * @param ctx the parse tree
	 */
	void enterDesc(SelRAParser.DescContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#desc}.
	 * @param ctx the parse tree
	 */
	void exitDesc(SelRAParser.DescContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#carateristicas}.
	 * @param ctx the parse tree
	 */
	void enterCarateristicas(SelRAParser.CarateristicasContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#carateristicas}.
	 * @param ctx the parse tree
	 */
	void exitCarateristicas(SelRAParser.CarateristicasContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#caracteristica}.
	 * @param ctx the parse tree
	 */
	void enterCaracteristica(SelRAParser.CaracteristicaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#caracteristica}.
	 * @param ctx the parse tree
	 */
	void exitCaracteristica(SelRAParser.CaracteristicaContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#recursos}.
	 * @param ctx the parse tree
	 */
	void enterRecursos(SelRAParser.RecursosContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#recursos}.
	 * @param ctx the parse tree
	 */
	void exitRecursos(SelRAParser.RecursosContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#recurso}.
	 * @param ctx the parse tree
	 */
	void enterRecurso(SelRAParser.RecursoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#recurso}.
	 * @param ctx the parse tree
	 */
	void exitRecurso(SelRAParser.RecursoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#idrecurso}.
	 * @param ctx the parse tree
	 */
	void enterIdrecurso(SelRAParser.IdrecursoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#idrecurso}.
	 * @param ctx the parse tree
	 */
	void exitIdrecurso(SelRAParser.IdrecursoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(SelRAParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(SelRAParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#intervaloidade}.
	 * @param ctx the parse tree
	 */
	void enterIntervaloidade(SelRAParser.IntervaloidadeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#intervaloidade}.
	 * @param ctx the parse tree
	 */
	void exitIntervaloidade(SelRAParser.IntervaloidadeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#idademin}.
	 * @param ctx the parse tree
	 */
	void enterIdademin(SelRAParser.IdademinContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#idademin}.
	 * @param ctx the parse tree
	 */
	void exitIdademin(SelRAParser.IdademinContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#idademax}.
	 * @param ctx the parse tree
	 */
	void enterIdademax(SelRAParser.IdademaxContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#idademax}.
	 * @param ctx the parse tree
	 */
	void exitIdademax(SelRAParser.IdademaxContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#idade}.
	 * @param ctx the parse tree
	 */
	void enterIdade(SelRAParser.IdadeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#idade}.
	 * @param ctx the parse tree
	 */
	void exitIdade(SelRAParser.IdadeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#alunos}.
	 * @param ctx the parse tree
	 */
	void enterAlunos(SelRAParser.AlunosContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#alunos}.
	 * @param ctx the parse tree
	 */
	void exitAlunos(SelRAParser.AlunosContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#aluno}.
	 * @param ctx the parse tree
	 */
	void enterAluno(SelRAParser.AlunoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#aluno}.
	 * @param ctx the parse tree
	 */
	void exitAluno(SelRAParser.AlunoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#nome}.
	 * @param ctx the parse tree
	 */
	void enterNome(SelRAParser.NomeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#nome}.
	 * @param ctx the parse tree
	 */
	void exitNome(SelRAParser.NomeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#idaluno}.
	 * @param ctx the parse tree
	 */
	void enterIdaluno(SelRAParser.IdalunoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#idaluno}.
	 * @param ctx the parse tree
	 */
	void exitIdaluno(SelRAParser.IdalunoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#conceitos}.
	 * @param ctx the parse tree
	 */
	void enterConceitos(SelRAParser.ConceitosContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#conceitos}.
	 * @param ctx the parse tree
	 */
	void exitConceitos(SelRAParser.ConceitosContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#conceito}.
	 * @param ctx the parse tree
	 */
	void enterConceito(SelRAParser.ConceitoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#conceito}.
	 * @param ctx the parse tree
	 */
	void exitConceito(SelRAParser.ConceitoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#idconceito}.
	 * @param ctx the parse tree
	 */
	void enterIdconceito(SelRAParser.IdconceitoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#idconceito}.
	 * @param ctx the parse tree
	 */
	void exitIdconceito(SelRAParser.IdconceitoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#ensinamentos}.
	 * @param ctx the parse tree
	 */
	void enterEnsinamentos(SelRAParser.EnsinamentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#ensinamentos}.
	 * @param ctx the parse tree
	 */
	void exitEnsinamentos(SelRAParser.EnsinamentosContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#ensinamento}.
	 * @param ctx the parse tree
	 */
	void enterEnsinamento(SelRAParser.EnsinamentoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#ensinamento}.
	 * @param ctx the parse tree
	 */
	void exitEnsinamento(SelRAParser.EnsinamentoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#questoes}.
	 * @param ctx the parse tree
	 */
	void enterQuestoes(SelRAParser.QuestoesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#questoes}.
	 * @param ctx the parse tree
	 */
	void exitQuestoes(SelRAParser.QuestoesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SelRAParser#questao}.
	 * @param ctx the parse tree
	 */
	void enterQuestao(SelRAParser.QuestaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SelRAParser#questao}.
	 * @param ctx the parse tree
	 */
	void exitQuestao(SelRAParser.QuestaoContext ctx);
}