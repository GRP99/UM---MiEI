// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\listas.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link listasParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface listasVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link listasParser#listas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListas(listasParser.ListasContext ctx);
	/**
	 * Visit a parse tree produced by {@link listasParser#lista}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLista(listasParser.ListaContext ctx);
	/**
	 * Visit a parse tree produced by {@link listasParser#elementos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementos(listasParser.ElementosContext ctx);
	/**
	 * Visit a parse tree produced by {@link listasParser#elemento}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElemento(listasParser.ElementoContext ctx);
}