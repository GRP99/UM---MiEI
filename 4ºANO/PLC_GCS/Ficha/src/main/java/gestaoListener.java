// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\gestao.g4 by ANTLR 4.9

    import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link gestaoParser}.
 */
public interface gestaoListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link gestaoParser#gestao}.
	 * @param ctx the parse tree
	 */
	void enterGestao(gestaoParser.GestaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#gestao}.
	 * @param ctx the parse tree
	 */
	void exitGestao(gestaoParser.GestaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#stock}.
	 * @param ctx the parse tree
	 */
	void enterStock(gestaoParser.StockContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#stock}.
	 * @param ctx the parse tree
	 */
	void exitStock(gestaoParser.StockContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#produto}.
	 * @param ctx the parse tree
	 */
	void enterProduto(gestaoParser.ProdutoContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#produto}.
	 * @param ctx the parse tree
	 */
	void exitProduto(gestaoParser.ProdutoContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#faturas}.
	 * @param ctx the parse tree
	 */
	void enterFaturas(gestaoParser.FaturasContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#faturas}.
	 * @param ctx the parse tree
	 */
	void exitFaturas(gestaoParser.FaturasContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#fatura}.
	 * @param ctx the parse tree
	 */
	void enterFatura(gestaoParser.FaturaContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#fatura}.
	 * @param ctx the parse tree
	 */
	void exitFatura(gestaoParser.FaturaContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#cabecalho}.
	 * @param ctx the parse tree
	 */
	void enterCabecalho(gestaoParser.CabecalhoContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#cabecalho}.
	 * @param ctx the parse tree
	 */
	void exitCabecalho(gestaoParser.CabecalhoContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#numFat}.
	 * @param ctx the parse tree
	 */
	void enterNumFat(gestaoParser.NumFatContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#numFat}.
	 * @param ctx the parse tree
	 */
	void exitNumFat(gestaoParser.NumFatContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#fornecedor}.
	 * @param ctx the parse tree
	 */
	void enterFornecedor(gestaoParser.FornecedorContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#fornecedor}.
	 * @param ctx the parse tree
	 */
	void exitFornecedor(gestaoParser.FornecedorContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#cliente}.
	 * @param ctx the parse tree
	 */
	void enterCliente(gestaoParser.ClienteContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#cliente}.
	 * @param ctx the parse tree
	 */
	void exitCliente(gestaoParser.ClienteContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#nome}.
	 * @param ctx the parse tree
	 */
	void enterNome(gestaoParser.NomeContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#nome}.
	 * @param ctx the parse tree
	 */
	void exitNome(gestaoParser.NomeContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#nif}.
	 * @param ctx the parse tree
	 */
	void enterNif(gestaoParser.NifContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#nif}.
	 * @param ctx the parse tree
	 */
	void exitNif(gestaoParser.NifContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#morada}.
	 * @param ctx the parse tree
	 */
	void enterMorada(gestaoParser.MoradaContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#morada}.
	 * @param ctx the parse tree
	 */
	void exitMorada(gestaoParser.MoradaContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#nib}.
	 * @param ctx the parse tree
	 */
	void enterNib(gestaoParser.NibContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#nib}.
	 * @param ctx the parse tree
	 */
	void exitNib(gestaoParser.NibContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#corpo}.
	 * @param ctx the parse tree
	 */
	void enterCorpo(gestaoParser.CorpoContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#corpo}.
	 * @param ctx the parse tree
	 */
	void exitCorpo(gestaoParser.CorpoContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#linha}.
	 * @param ctx the parse tree
	 */
	void enterLinha(gestaoParser.LinhaContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#linha}.
	 * @param ctx the parse tree
	 */
	void exitLinha(gestaoParser.LinhaContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#refProd}.
	 * @param ctx the parse tree
	 */
	void enterRefProd(gestaoParser.RefProdContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#refProd}.
	 * @param ctx the parse tree
	 */
	void exitRefProd(gestaoParser.RefProdContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#valUnit}.
	 * @param ctx the parse tree
	 */
	void enterValUnit(gestaoParser.ValUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#valUnit}.
	 * @param ctx the parse tree
	 */
	void exitValUnit(gestaoParser.ValUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#quant}.
	 * @param ctx the parse tree
	 */
	void enterQuant(gestaoParser.QuantContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#quant}.
	 * @param ctx the parse tree
	 */
	void exitQuant(gestaoParser.QuantContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#descProd}.
	 * @param ctx the parse tree
	 */
	void enterDescProd(gestaoParser.DescProdContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#descProd}.
	 * @param ctx the parse tree
	 */
	void exitDescProd(gestaoParser.DescProdContext ctx);
	/**
	 * Enter a parse tree produced by {@link gestaoParser#quantS}.
	 * @param ctx the parse tree
	 */
	void enterQuantS(gestaoParser.QuantSContext ctx);
	/**
	 * Exit a parse tree produced by {@link gestaoParser#quantS}.
	 * @param ctx the parse tree
	 */
	void exitQuantS(gestaoParser.QuantSContext ctx);
}