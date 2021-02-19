// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\gestao.g4 by ANTLR 4.9

    import java.util.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class gestaoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, STR=9, 
		ID=10, NUM=11, HIFEN=12, PONTO=13, WS=14;
	public static final int
		RULE_gestao = 0, RULE_stock = 1, RULE_produto = 2, RULE_faturas = 3, RULE_fatura = 4, 
		RULE_cabecalho = 5, RULE_numFat = 6, RULE_fornecedor = 7, RULE_cliente = 8, 
		RULE_nome = 9, RULE_nif = 10, RULE_morada = 11, RULE_nib = 12, RULE_corpo = 13, 
		RULE_linha = 14, RULE_refProd = 15, RULE_valUnit = 16, RULE_quant = 17, 
		RULE_descProd = 18, RULE_quantS = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"gestao", "stock", "produto", "faturas", "fatura", "cabecalho", "numFat", 
			"fornecedor", "cliente", "nome", "nif", "morada", "nib", "corpo", "linha", 
			"refProd", "valUnit", "quant", "descProd", "quantS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'STOCK'", "':'", "','", "'FATURA'", "'VENDAS'", "'/'", 
			"'|'", null, null, null, "'-'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "STR", "ID", "NUM", 
			"HIFEN", "PONTO", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "gestao.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    class Produto{
	        String descr;
	        Double prec;
	        int qtd;
	           
	        public String toString(){
	            StringBuffer sb = new StringBuffer();
	            sb.append(this.descr + "; ");
	            sb.append(this.prec + "; ");
	            sb.append(this.qtd + ". ");
	            return sb.toString();
	        }
	    }

	public gestaoParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class GestaoContext extends ParserRuleContext {
		public StockContext stock;
		public StockContext stock() {
			return getRuleContext(StockContext.class,0);
		}
		public FaturasContext faturas() {
			return getRuleContext(FaturasContext.class,0);
		}
		public GestaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gestao; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterGestao(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitGestao(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitGestao(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GestaoContext gestao() throws RecognitionException {
		GestaoContext _localctx = new GestaoContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_gestao);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			((GestaoContext)_localctx).stock = stock();
			setState(41);
			match(T__0);
			setState(42);
			match(T__0);
			setState(43);
			faturas(((GestaoContext)_localctx).stock.prodts);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StockContext extends ParserRuleContext {
		public HashMap<String, Produto> prodts;
		public ProdutoContext p1;
		public ProdutoContext p2;
		public List<ProdutoContext> produto() {
			return getRuleContexts(ProdutoContext.class);
		}
		public ProdutoContext produto(int i) {
			return getRuleContext(ProdutoContext.class,i);
		}
		public StockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterStock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitStock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitStock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StockContext stock() throws RecognitionException {
		StockContext _localctx = new StockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stock);
		((StockContext)_localctx).prodts =  new HashMap<String, Produto>(); 
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(T__1);
			setState(46);
			((StockContext)_localctx).p1 = produto(_localctx.prodts);
			 ((StockContext)_localctx).prodts = ((StockContext)_localctx).p1.prodout; 
			setState(54);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(48);
					match(T__0);
					setState(49);
					((StockContext)_localctx).p2 = produto(_localctx.prodts);
					 ((StockContext)_localctx).prodts = ((StockContext)_localctx).p2.prodout; 
					}
					} 
				}
				setState(56);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			 System.out.println("Quantidades em Stock no inicio:"); System.out.println(_localctx.prodts.toString()); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProdutoContext extends ParserRuleContext {
		public HashMap<String, Produto> prodin;
		public HashMap<String, Produto> prodout;
		public RefProdContext refProd;
		public DescProdContext descProd;
		public ValUnitContext valUnit;
		public QuantSContext quantS;
		public RefProdContext refProd() {
			return getRuleContext(RefProdContext.class,0);
		}
		public DescProdContext descProd() {
			return getRuleContext(DescProdContext.class,0);
		}
		public ValUnitContext valUnit() {
			return getRuleContext(ValUnitContext.class,0);
		}
		public QuantSContext quantS() {
			return getRuleContext(QuantSContext.class,0);
		}
		public ProdutoContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ProdutoContext(ParserRuleContext parent, int invokingState, HashMap<String, Produto> prodin) {
			super(parent, invokingState);
			this.prodin = prodin;
		}
		@Override public int getRuleIndex() { return RULE_produto; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterProduto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitProduto(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitProduto(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProdutoContext produto(HashMap<String, Produto> prodin) throws RecognitionException {
		ProdutoContext _localctx = new ProdutoContext(_ctx, getState(), prodin);
		enterRule(_localctx, 4, RULE_produto);
		((ProdutoContext)_localctx).prodout =  new HashMap<String, Produto>(); 
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			((ProdutoContext)_localctx).refProd = refProd();
			setState(60);
			match(T__2);
			setState(61);
			((ProdutoContext)_localctx).descProd = descProd();
			setState(62);
			match(T__3);
			setState(63);
			((ProdutoContext)_localctx).valUnit = valUnit();
			setState(64);
			match(T__3);
			setState(65);
			((ProdutoContext)_localctx).quantS = quantS();
			   
			                                                        Produto p = new Produto(); 
			                                                        p.descr=(((ProdutoContext)_localctx).descProd!=null?_input.getText(((ProdutoContext)_localctx).descProd.start,((ProdutoContext)_localctx).descProd.stop):null);
			                                                        p.prec=Double.parseDouble((((ProdutoContext)_localctx).valUnit!=null?_input.getText(((ProdutoContext)_localctx).valUnit.start,((ProdutoContext)_localctx).valUnit.stop):null));
			                                                        p.qtd=Integer.parseInt((((ProdutoContext)_localctx).quantS!=null?_input.getText(((ProdutoContext)_localctx).quantS.start,((ProdutoContext)_localctx).quantS.stop):null));
			                                                        _localctx.prodin.put((((ProdutoContext)_localctx).refProd!=null?_input.getText(((ProdutoContext)_localctx).refProd.start,((ProdutoContext)_localctx).refProd.stop):null), p);
			                                                        ((ProdutoContext)_localctx).prodout = _localctx.prodin;
			                                                    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FaturasContext extends ParserRuleContext {
		public HashMap<String, Produto> prodts;
		public FaturaContext f1;
		public FaturaContext f2;
		public List<FaturaContext> fatura() {
			return getRuleContexts(FaturaContext.class);
		}
		public FaturaContext fatura(int i) {
			return getRuleContext(FaturaContext.class,i);
		}
		public FaturasContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public FaturasContext(ParserRuleContext parent, int invokingState, HashMap<String, Produto> prodts) {
			super(parent, invokingState);
			this.prodts = prodts;
		}
		@Override public int getRuleIndex() { return RULE_faturas; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterFaturas(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitFaturas(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitFaturas(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FaturasContext faturas(HashMap<String, Produto> prodts) throws RecognitionException {
		FaturasContext _localctx = new FaturasContext(_ctx, getState(), prodts);
		enterRule(_localctx, 6, RULE_faturas);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

			    System.out.println("Quantidades em Stock no meio:"); System.out.println(_localctx.prodts.toString());
			    
			setState(69);
			((FaturasContext)_localctx).f1 = fatura(_localctx.prodts);
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(70);
				match(T__0);
				setState(71);
				((FaturasContext)_localctx).f2 = fatura(_localctx.prodts);
				}
				}
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 System.out.println("Quantidades em Stock no fim:"); 
			      System.out.println(_localctx.prodts.toString());
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FaturaContext extends ParserRuleContext {
		public HashMap<String, Produto> prodin;
		public CorpoContext corpo;
		public CabecalhoContext cabecalho() {
			return getRuleContext(CabecalhoContext.class,0);
		}
		public CorpoContext corpo() {
			return getRuleContext(CorpoContext.class,0);
		}
		public TerminalNode PONTO() { return getToken(gestaoParser.PONTO, 0); }
		public FaturaContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public FaturaContext(ParserRuleContext parent, int invokingState, HashMap<String, Produto> prodin) {
			super(parent, invokingState);
			this.prodin = prodin;
		}
		@Override public int getRuleIndex() { return RULE_fatura; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterFatura(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitFatura(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitFatura(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FaturaContext fatura(HashMap<String, Produto> prodin) throws RecognitionException {
		FaturaContext _localctx = new FaturaContext(_ctx, getState(), prodin);
		enterRule(_localctx, 8, RULE_fatura);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(T__4);
			setState(80);
			cabecalho();
			setState(81);
			match(T__5);
			setState(82);
			((FaturaContext)_localctx).corpo = corpo(_localctx.prodin);
			setState(83);
			match(PONTO);
			System.out.println("TOTAL da Fatura: "+ ((FaturaContext)_localctx).corpo.totF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CabecalhoContext extends ParserRuleContext {
		public NumFatContext numFat;
		public NumFatContext numFat() {
			return getRuleContext(NumFatContext.class,0);
		}
		public FornecedorContext fornecedor() {
			return getRuleContext(FornecedorContext.class,0);
		}
		public ClienteContext cliente() {
			return getRuleContext(ClienteContext.class,0);
		}
		public CabecalhoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cabecalho; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterCabecalho(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitCabecalho(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitCabecalho(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CabecalhoContext cabecalho() throws RecognitionException {
		CabecalhoContext _localctx = new CabecalhoContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_cabecalho);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			((CabecalhoContext)_localctx).numFat = numFat();
			setState(87);
			match(T__2);
			setState(88);
			fornecedor();
			setState(89);
			match(T__6);
			setState(90);
			cliente();
			 System.out.println("FATURA num: " + (((CabecalhoContext)_localctx).numFat!=null?_input.getText(((CabecalhoContext)_localctx).numFat.start,((CabecalhoContext)_localctx).numFat.stop):null));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumFatContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(gestaoParser.ID, 0); }
		public NumFatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numFat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterNumFat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitNumFat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitNumFat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumFatContext numFat() throws RecognitionException {
		NumFatContext _localctx = new NumFatContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_numFat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FornecedorContext extends ParserRuleContext {
		public NomeContext nome() {
			return getRuleContext(NomeContext.class,0);
		}
		public MoradaContext morada() {
			return getRuleContext(MoradaContext.class,0);
		}
		public NifContext nif() {
			return getRuleContext(NifContext.class,0);
		}
		public NibContext nib() {
			return getRuleContext(NibContext.class,0);
		}
		public FornecedorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fornecedor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterFornecedor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitFornecedor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitFornecedor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FornecedorContext fornecedor() throws RecognitionException {
		FornecedorContext _localctx = new FornecedorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_fornecedor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			nome();
			setState(96);
			match(T__2);
			setState(97);
			morada();
			setState(98);
			match(T__3);
			setState(99);
			nif();
			setState(100);
			match(T__3);
			setState(101);
			nib();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClienteContext extends ParserRuleContext {
		public NomeContext nome() {
			return getRuleContext(NomeContext.class,0);
		}
		public MoradaContext morada() {
			return getRuleContext(MoradaContext.class,0);
		}
		public NifContext nif() {
			return getRuleContext(NifContext.class,0);
		}
		public ClienteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cliente; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterCliente(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitCliente(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitCliente(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClienteContext cliente() throws RecognitionException {
		ClienteContext _localctx = new ClienteContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_cliente);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			nome();
			setState(104);
			match(T__2);
			setState(105);
			morada();
			setState(106);
			match(T__3);
			setState(107);
			nif();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NomeContext extends ParserRuleContext {
		public TerminalNode STR() { return getToken(gestaoParser.STR, 0); }
		public NomeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nome; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterNome(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitNome(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitNome(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NomeContext nome() throws RecognitionException {
		NomeContext _localctx = new NomeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_nome);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(STR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NifContext extends ParserRuleContext {
		public TerminalNode STR() { return getToken(gestaoParser.STR, 0); }
		public NifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nif; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterNif(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitNif(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitNif(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NifContext nif() throws RecognitionException {
		NifContext _localctx = new NifContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_nif);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(STR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MoradaContext extends ParserRuleContext {
		public TerminalNode STR() { return getToken(gestaoParser.STR, 0); }
		public MoradaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_morada; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterMorada(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitMorada(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitMorada(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MoradaContext morada() throws RecognitionException {
		MoradaContext _localctx = new MoradaContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_morada);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(STR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NibContext extends ParserRuleContext {
		public TerminalNode STR() { return getToken(gestaoParser.STR, 0); }
		public NibContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nib; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterNib(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitNib(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitNib(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NibContext nib() throws RecognitionException {
		NibContext _localctx = new NibContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_nib);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(STR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CorpoContext extends ParserRuleContext {
		public HashMap<String, Produto> prodin;
		public Double totF;
		public LinhaContext l1;
		public LinhaContext l2;
		public List<TerminalNode> HIFEN() { return getTokens(gestaoParser.HIFEN); }
		public TerminalNode HIFEN(int i) {
			return getToken(gestaoParser.HIFEN, i);
		}
		public List<LinhaContext> linha() {
			return getRuleContexts(LinhaContext.class);
		}
		public LinhaContext linha(int i) {
			return getRuleContext(LinhaContext.class,i);
		}
		public CorpoContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public CorpoContext(ParserRuleContext parent, int invokingState, HashMap<String, Produto> prodin) {
			super(parent, invokingState);
			this.prodin = prodin;
		}
		@Override public int getRuleIndex() { return RULE_corpo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterCorpo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitCorpo(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitCorpo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CorpoContext corpo(HashMap<String, Produto> prodin) throws RecognitionException {
		CorpoContext _localctx = new CorpoContext(_ctx, getState(), prodin);
		enterRule(_localctx, 26, RULE_corpo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(HIFEN);
			setState(118);
			((CorpoContext)_localctx).l1 = linha(_localctx.prodin);
			 ((CorpoContext)_localctx).totF =  ((CorpoContext)_localctx).l1.totL; 
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==HIFEN) {
				{
				{
				setState(120);
				match(HIFEN);
				setState(121);
				((CorpoContext)_localctx).l2 = linha(_localctx.prodin);
				 _localctx.totF += ((CorpoContext)_localctx).l2.totL;
				}
				}
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LinhaContext extends ParserRuleContext {
		public HashMap<String, Produto> prodin;
		public Double totL;
		public RefProdContext refProd;
		public QuantContext quant;
		public RefProdContext refProd() {
			return getRuleContext(RefProdContext.class,0);
		}
		public QuantContext quant() {
			return getRuleContext(QuantContext.class,0);
		}
		public LinhaContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public LinhaContext(ParserRuleContext parent, int invokingState, HashMap<String, Produto> prodin) {
			super(parent, invokingState);
			this.prodin = prodin;
		}
		@Override public int getRuleIndex() { return RULE_linha; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterLinha(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitLinha(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitLinha(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LinhaContext linha(HashMap<String, Produto> prodin) throws RecognitionException {
		LinhaContext _localctx = new LinhaContext(_ctx, getState(), prodin);
		enterRule(_localctx, 28, RULE_linha);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			((LinhaContext)_localctx).refProd = refProd();
			setState(130);
			match(T__7);
			setState(131);
			((LinhaContext)_localctx).quant = quant();
			   Produto p;
			                                if (_localctx.prodin.containsKey((((LinhaContext)_localctx).refProd!=null?_input.getText(((LinhaContext)_localctx).refProd.start,((LinhaContext)_localctx).refProd.stop):null))) {
			                                    p = _localctx.prodin.get((((LinhaContext)_localctx).refProd!=null?_input.getText(((LinhaContext)_localctx).refProd.start,((LinhaContext)_localctx).refProd.stop):null));
			                                    if(Integer.parseInt((((LinhaContext)_localctx).quant!=null?_input.getText(((LinhaContext)_localctx).quant.start,((LinhaContext)_localctx).quant.stop):null)) <= p.qtd) {
			                                        System.out.println((((LinhaContext)_localctx).refProd!=null?_input.getText(((LinhaContext)_localctx).refProd.start,((LinhaContext)_localctx).refProd.stop):null) +"-> "+ (p.prec * (Integer.parseInt((((LinhaContext)_localctx).quant!=null?_input.getText(((LinhaContext)_localctx).quant.start,((LinhaContext)_localctx).quant.stop):null)))));
			                                        ((LinhaContext)_localctx).totL =  (p.prec * (Integer.parseInt((((LinhaContext)_localctx).quant!=null?_input.getText(((LinhaContext)_localctx).quant.start,((LinhaContext)_localctx).quant.stop):null))));
			                                        p.qtd -= Integer.parseInt((((LinhaContext)_localctx).quant!=null?_input.getText(((LinhaContext)_localctx).quant.start,((LinhaContext)_localctx).quant.stop):null));
			                                        _localctx.prodin.put((((LinhaContext)_localctx).refProd!=null?_input.getText(((LinhaContext)_localctx).refProd.start,((LinhaContext)_localctx).refProd.stop):null),p);
			                                    }
			                                    else {System.out.println("ERRO: A quantia de venda do produto " + (((LinhaContext)_localctx).refProd!=null?_input.getText(((LinhaContext)_localctx).refProd.start,((LinhaContext)_localctx).refProd.stop):null) + " excede a quantidade em stock");
			                                           ((LinhaContext)_localctx).totL =  0.0;
			                                          }
			                                }
			                                else  { 
			                                    System.out.println("ERRO: A Referencia " + (((LinhaContext)_localctx).refProd!=null?_input.getText(((LinhaContext)_localctx).refProd.start,((LinhaContext)_localctx).refProd.stop):null) + " nao existe em Stock");
			                                    ((LinhaContext)_localctx).totL =  0.0;
			                                } 
			                            
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RefProdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(gestaoParser.ID, 0); }
		public RefProdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_refProd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterRefProd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitRefProd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitRefProd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RefProdContext refProd() throws RecognitionException {
		RefProdContext _localctx = new RefProdContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_refProd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValUnitContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(gestaoParser.NUM, 0); }
		public ValUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterValUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitValUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitValUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValUnitContext valUnit() throws RecognitionException {
		ValUnitContext _localctx = new ValUnitContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_valUnit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(gestaoParser.NUM, 0); }
		public QuantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterQuant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitQuant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitQuant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantContext quant() throws RecognitionException {
		QuantContext _localctx = new QuantContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_quant);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DescProdContext extends ParserRuleContext {
		public TerminalNode STR() { return getToken(gestaoParser.STR, 0); }
		public DescProdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descProd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterDescProd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitDescProd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitDescProd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescProdContext descProd() throws RecognitionException {
		DescProdContext _localctx = new DescProdContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_descProd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(STR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantSContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(gestaoParser.NUM, 0); }
		public QuantSContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantS; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).enterQuantS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof gestaoListener ) ((gestaoListener)listener).exitQuantS(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gestaoVisitor ) return ((gestaoVisitor<? extends T>)visitor).visitQuantS(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantSContext quantS() throws RecognitionException {
		QuantSContext _localctx = new QuantSContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_quantS);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			match(NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\20\u0093\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\7\3\67\n\3\f\3\16\3:\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\7\5K\n\5\f\5\16\5N\13\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\177\n\17\f\17\16\17\u0082"+
		"\13\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24"+
		"\3\25\3\25\3\25\2\2\26\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(\2"+
		"\2\2\u0081\2*\3\2\2\2\4/\3\2\2\2\6=\3\2\2\2\bF\3\2\2\2\nQ\3\2\2\2\fX\3"+
		"\2\2\2\16_\3\2\2\2\20a\3\2\2\2\22i\3\2\2\2\24o\3\2\2\2\26q\3\2\2\2\30"+
		"s\3\2\2\2\32u\3\2\2\2\34w\3\2\2\2\36\u0083\3\2\2\2 \u0088\3\2\2\2\"\u008a"+
		"\3\2\2\2$\u008c\3\2\2\2&\u008e\3\2\2\2(\u0090\3\2\2\2*+\5\4\3\2+,\7\3"+
		"\2\2,-\7\3\2\2-.\5\b\5\2.\3\3\2\2\2/\60\7\4\2\2\60\61\5\6\4\2\618\b\3"+
		"\1\2\62\63\7\3\2\2\63\64\5\6\4\2\64\65\b\3\1\2\65\67\3\2\2\2\66\62\3\2"+
		"\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:8\3\2\2\2;<\b\3\1\2<"+
		"\5\3\2\2\2=>\5 \21\2>?\7\5\2\2?@\5&\24\2@A\7\6\2\2AB\5\"\22\2BC\7\6\2"+
		"\2CD\5(\25\2DE\b\4\1\2E\7\3\2\2\2FG\b\5\1\2GL\5\n\6\2HI\7\3\2\2IK\5\n"+
		"\6\2JH\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2NL\3\2\2\2OP\b\5"+
		"\1\2P\t\3\2\2\2QR\7\7\2\2RS\5\f\7\2ST\7\b\2\2TU\5\34\17\2UV\7\17\2\2V"+
		"W\b\6\1\2W\13\3\2\2\2XY\5\16\b\2YZ\7\5\2\2Z[\5\20\t\2[\\\7\t\2\2\\]\5"+
		"\22\n\2]^\b\7\1\2^\r\3\2\2\2_`\7\f\2\2`\17\3\2\2\2ab\5\24\13\2bc\7\5\2"+
		"\2cd\5\30\r\2de\7\6\2\2ef\5\26\f\2fg\7\6\2\2gh\5\32\16\2h\21\3\2\2\2i"+
		"j\5\24\13\2jk\7\5\2\2kl\5\30\r\2lm\7\6\2\2mn\5\26\f\2n\23\3\2\2\2op\7"+
		"\13\2\2p\25\3\2\2\2qr\7\13\2\2r\27\3\2\2\2st\7\13\2\2t\31\3\2\2\2uv\7"+
		"\13\2\2v\33\3\2\2\2wx\7\16\2\2xy\5\36\20\2y\u0080\b\17\1\2z{\7\16\2\2"+
		"{|\5\36\20\2|}\b\17\1\2}\177\3\2\2\2~z\3\2\2\2\177\u0082\3\2\2\2\u0080"+
		"~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\35\3\2\2\2\u0082\u0080\3\2\2\2\u0083"+
		"\u0084\5 \21\2\u0084\u0085\7\n\2\2\u0085\u0086\5$\23\2\u0086\u0087\b\20"+
		"\1\2\u0087\37\3\2\2\2\u0088\u0089\7\f\2\2\u0089!\3\2\2\2\u008a\u008b\7"+
		"\r\2\2\u008b#\3\2\2\2\u008c\u008d\7\r\2\2\u008d%\3\2\2\2\u008e\u008f\7"+
		"\13\2\2\u008f\'\3\2\2\2\u0090\u0091\7\r\2\2\u0091)\3\2\2\2\58L\u0080";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}