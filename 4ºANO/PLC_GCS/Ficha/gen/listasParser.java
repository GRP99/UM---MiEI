// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\listas.g4 by ANTLR 4.9
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class listasParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Lista=3, NUM=4, PALAVRA=5, WS=6;
	public static final int
		RULE_listas = 0, RULE_lista = 1, RULE_elementos = 2, RULE_elemento = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"listas", "lista", "elementos", "elemento"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'.'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "Lista", "NUM", "PALAVRA", "WS"
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
	public String getGrammarFileName() { return "listas.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    int contador = 1;
	    int comprimento = 0;
	    int qtNumeros = 0;
	    int somaNumeros = 0;
	    float media = 0;
	    int qtPalavras = 0;
	    int maximo = 0;
	    boolean contido = false;
	    int somaContida = 0;

	public listasParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ListasContext extends ParserRuleContext {
		public List<ListaContext> lista() {
			return getRuleContexts(ListaContext.class);
		}
		public ListaContext lista(int i) {
			return getRuleContext(ListaContext.class,i);
		}
		public ListasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listas; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof listasListener ) ((listasListener)listener).enterListas(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof listasListener ) ((listasListener)listener).exitListas(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof listasVisitor ) return ((listasVisitor<? extends T>)visitor).visitListas(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListasContext listas() throws RecognitionException {
		ListasContext _localctx = new ListasContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_listas);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(9); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(8);
				lista();
				}
				}
				setState(11); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Lista );
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

	public static class ListaContext extends ParserRuleContext {
		public TerminalNode Lista() { return getToken(listasParser.Lista, 0); }
		public ElementosContext elementos() {
			return getRuleContext(ElementosContext.class,0);
		}
		public ListaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lista; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof listasListener ) ((listasListener)listener).enterLista(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof listasListener ) ((listasListener)listener).exitLista(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof listasVisitor ) return ((listasVisitor<? extends T>)visitor).visitLista(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListaContext lista() throws RecognitionException {
		ListaContext _localctx = new ListaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_lista);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			match(Lista);
			setState(14);
			elementos();
			setState(15);
			match(T__0);
			 System.out.println("#####---LISTA " + contador + " ----#####");
			                                          System.out.println("A) Comprimento da lista: " + comprimento);
			                                          System.out.println("A) Quantidade de numeros: " + qtNumeros );
			                                          if (qtNumeros > 0) media = (float) somaNumeros / qtNumeros ;
			                                          System.out.println("B) Media de todos os numeros: " + (float) media);
			                                          if (qtNumeros == qtPalavras) System.out.println(" C) Quantidade de palavras e igual a quantidade de numeros.");
			                                          else System.out.println("C) Quantidade de palavras -> " + qtPalavras + " nao e igual a quantidade de numeros -> " + qtNumeros + ".");
			                                          System.out.println("D) Maximo dos numeros: " + maximo);
			                                          System.out.println("E) Somatorio apenas dos numeros contidos: " + somaContida);
			                                          System.out.println("###########################################");
			                                          contador++;
			                                          qtNumeros = 0;
			                                          somaNumeros = 0;
			                                          media = 0;
			                                          qtPalavras = 0;
			                                          maximo = 0;
			                                          contido = false;
			                                          somaContida = 0;
			                                        
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

	public static class ElementosContext extends ParserRuleContext {
		public List<ElementoContext> elemento() {
			return getRuleContexts(ElementoContext.class);
		}
		public ElementoContext elemento(int i) {
			return getRuleContext(ElementoContext.class,i);
		}
		public ElementosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementos; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof listasListener ) ((listasListener)listener).enterElementos(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof listasListener ) ((listasListener)listener).exitElementos(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof listasVisitor ) return ((listasVisitor<? extends T>)visitor).visitElementos(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementosContext elementos() throws RecognitionException {
		ElementosContext _localctx = new ElementosContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_elementos);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			elemento();
			 comprimento=1; 
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(20);
				match(T__1);
				setState(21);
				elemento();
				 comprimento++; 
				}
				}
				setState(28);
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

	public static class ElementoContext extends ParserRuleContext {
		public Token PALAVRA;
		public Token NUM;
		public TerminalNode PALAVRA() { return getToken(listasParser.PALAVRA, 0); }
		public TerminalNode NUM() { return getToken(listasParser.NUM, 0); }
		public ElementoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elemento; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof listasListener ) ((listasListener)listener).enterElemento(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof listasListener ) ((listasListener)listener).exitElemento(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof listasVisitor ) return ((listasVisitor<? extends T>)visitor).visitElemento(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementoContext elemento() throws RecognitionException {
		ElementoContext _localctx = new ElementoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_elemento);
		try {
			setState(33);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PALAVRA:
				enterOuterAlt(_localctx, 1);
				{
				setState(29);
				((ElementoContext)_localctx).PALAVRA = match(PALAVRA);
				 qtPalavras++;
				                                          if ( (((ElementoContext)_localctx).PALAVRA!=null?((ElementoContext)_localctx).PALAVRA.getText():null).equals("agora") ) contido = true;
				                                          if ( (((ElementoContext)_localctx).PALAVRA!=null?((ElementoContext)_localctx).PALAVRA.getText():null).equals("fim") ) contido = false;
				                                        
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(31);
				((ElementoContext)_localctx).NUM = match(NUM);
				 qtNumeros++;
				                                          somaNumeros += (((ElementoContext)_localctx).NUM!=null?Integer.valueOf(((ElementoContext)_localctx).NUM.getText()):0);
				                                          if ( maximo < (((ElementoContext)_localctx).NUM!=null?Integer.valueOf(((ElementoContext)_localctx).NUM.getText()):0)) maximo = (((ElementoContext)_localctx).NUM!=null?Integer.valueOf(((ElementoContext)_localctx).NUM.getText()):0);
				                                          if ( contido ) somaContida += (((ElementoContext)_localctx).NUM!=null?Integer.valueOf(((ElementoContext)_localctx).NUM.getText()):0);
				                                        
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\b&\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\3\2\6\2\f\n\2\r\2\16\2\r\3\3\3\3\3\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\7\4\33\n\4\f\4\16\4\36\13\4\3\5\3\5\3\5\3\5\5\5$\n"+
		"\5\3\5\2\2\6\2\4\6\b\2\2\2$\2\13\3\2\2\2\4\17\3\2\2\2\6\24\3\2\2\2\b#"+
		"\3\2\2\2\n\f\5\4\3\2\13\n\3\2\2\2\f\r\3\2\2\2\r\13\3\2\2\2\r\16\3\2\2"+
		"\2\16\3\3\2\2\2\17\20\7\5\2\2\20\21\5\6\4\2\21\22\7\3\2\2\22\23\b\3\1"+
		"\2\23\5\3\2\2\2\24\25\5\b\5\2\25\34\b\4\1\2\26\27\7\4\2\2\27\30\5\b\5"+
		"\2\30\31\b\4\1\2\31\33\3\2\2\2\32\26\3\2\2\2\33\36\3\2\2\2\34\32\3\2\2"+
		"\2\34\35\3\2\2\2\35\7\3\2\2\2\36\34\3\2\2\2\37 \7\7\2\2 $\b\5\1\2!\"\7"+
		"\6\2\2\"$\b\5\1\2#\37\3\2\2\2#!\3\2\2\2$\t\3\2\2\2\5\r\34#";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}