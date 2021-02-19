// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\listas.g4 by ANTLR 4.9
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