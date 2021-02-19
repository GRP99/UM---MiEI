// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\gestao.g4 by ANTLR 4.9

    import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link gestaoParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface gestaoVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link gestaoParser#gestao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGestao(gestaoParser.GestaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#stock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStock(gestaoParser.StockContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#produto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProduto(gestaoParser.ProdutoContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#faturas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFaturas(gestaoParser.FaturasContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#fatura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFatura(gestaoParser.FaturaContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#cabecalho}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCabecalho(gestaoParser.CabecalhoContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#numFat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumFat(gestaoParser.NumFatContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#fornecedor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFornecedor(gestaoParser.FornecedorContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#cliente}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCliente(gestaoParser.ClienteContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#nome}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNome(gestaoParser.NomeContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#nif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNif(gestaoParser.NifContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#morada}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMorada(gestaoParser.MoradaContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#nib}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNib(gestaoParser.NibContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#corpo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCorpo(gestaoParser.CorpoContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#linha}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinha(gestaoParser.LinhaContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#refProd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRefProd(gestaoParser.RefProdContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#valUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValUnit(gestaoParser.ValUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#quant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuant(gestaoParser.QuantContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#descProd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescProd(gestaoParser.DescProdContext ctx);
	/**
	 * Visit a parse tree produced by {@link gestaoParser#quantS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantS(gestaoParser.QuantSContext ctx);
}