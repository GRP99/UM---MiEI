// Generated from C:/Users/Gon�alo Pinto/Documents/@Goncalo/Universidade/4�ANO/1Semestre/PLC_Gram�ticas na Compreens�o de Software/Fichas de Exerc�cios para as Aulas/Ficha01\listas.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link listasParser}.
 */
public interface listasListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link listasParser#listas}.
	 * @param ctx the parse tree
	 */
	void enterListas(listasParser.ListasContext ctx);
	/**
	 * Exit a parse tree produced by {@link listasParser#listas}.
	 * @param ctx the parse tree
	 */
	void exitListas(listasParser.ListasContext ctx);
	/**
	 * Enter a parse tree produced by {@link listasParser#lista}.
	 * @param ctx the parse tree
	 */
	void enterLista(listasParser.ListaContext ctx);
	/**
	 * Exit a parse tree produced by {@link listasParser#lista}.
	 * @param ctx the parse tree
	 */
	void exitLista(listasParser.ListaContext ctx);
	/**
	 * Enter a parse tree produced by {@link listasParser#elementos}.
	 * @param ctx the parse tree
	 */
	void enterElementos(listasParser.ElementosContext ctx);
	/**
	 * Exit a parse tree produced by {@link listasParser#elementos}.
	 * @param ctx the parse tree
	 */
	void exitElementos(listasParser.ElementosContext ctx);
	/**
	 * Enter a parse tree produced by {@link listasParser#elemento}.
	 * @param ctx the parse tree
	 */
	void enterElemento(listasParser.ElementoContext ctx);
	/**
	 * Exit a parse tree produced by {@link listasParser#elemento}.
	 * @param ctx the parse tree
	 */
	void exitElemento(listasParser.ElementoContext ctx);
}