// Generated from c:\Users\Gonçalo Pinto\Documents\@Goncalo\Universidade\4ºANO\1Semestre\PLC_Gramáticas na Compreensão de Software\Fichas de Exercícios para as Aulas\Ficha01\notas.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class notasParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TURMA=1, ID=2, NOME=3, NUM=4, VIRGULA=5, PONTOVIRGULA=6, PONTO=7, LP=8, 
		RP=9, WS=10;
	public static final int
		RULE_turmas = 0, RULE_turma = 1, RULE_alunos = 2, RULE_aluno = 3, RULE_nome = 4, 
		RULE_notas = 5;
	private static String[] makeRuleNames() {
		return new String[] {
			"turmas", "turma", "alunos", "aluno", "nome", "notas"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "','", "';'", "'.'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "TURMA", "ID", "NOME", "NUM", "VIRGULA", "PONTOVIRGULA", "PONTO", 
			"LP", "RP", "WS"
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
	public String getGrammarFileName() { return "notas.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public notasParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TurmasContext extends ParserRuleContext {
		public List<TurmaContext> turma() {
			return getRuleContexts(TurmaContext.class);
		}
		public TurmaContext turma(int i) {
			return getRuleContext(TurmaContext.class,i);
		}
		public TurmasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_turmas; }
	}

	public final TurmasContext turmas() throws RecognitionException {
		TurmasContext _localctx = new TurmasContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_turmas);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(12);
				turma();
				}
				}
				setState(15); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TURMA );
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

	public static class TurmaContext extends ParserRuleContext {
		public AlunosContext alunos;
		public TerminalNode TURMA() { return getToken(notasParser.TURMA, 0); }
		public TerminalNode ID() { return getToken(notasParser.ID, 0); }
		public AlunosContext alunos() {
			return getRuleContext(AlunosContext.class,0);
		}
		public TerminalNode PONTO() { return getToken(notasParser.PONTO, 0); }
		public TurmaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_turma; }
	}

	public final TurmaContext turma() throws RecognitionException {
		TurmaContext _localctx = new TurmaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_turma);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17);
			match(TURMA);
			setState(18);
			match(ID);
			setState(19);
			((TurmaContext)_localctx).alunos = alunos();
			 System.out.println("Total Alunos:" + ((TurmaContext)_localctx).alunos.totalAlunos); 
			setState(21);
			match(PONTO);
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

	public static class AlunosContext extends ParserRuleContext {
		public int totalAlunos;
		public AlunoContext a1;
		public AlunoContext a2;
		public List<AlunoContext> aluno() {
			return getRuleContexts(AlunoContext.class);
		}
		public AlunoContext aluno(int i) {
			return getRuleContext(AlunoContext.class,i);
		}
		public List<TerminalNode> PONTOVIRGULA() { return getTokens(notasParser.PONTOVIRGULA); }
		public TerminalNode PONTOVIRGULA(int i) {
			return getToken(notasParser.PONTOVIRGULA, i);
		}
		public AlunosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alunos; }
	}

	public final AlunosContext alunos() throws RecognitionException {
		AlunosContext _localctx = new AlunosContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_alunos);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			((AlunosContext)_localctx).a1 = aluno();
			 ((AlunosContext)_localctx).totalAlunos =  1; ((AlunosContext)_localctx).a1.nomes.add(((AlunosContext)_localctx).a1.name);
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PONTOVIRGULA) {
				{
				{
				setState(25);
				match(PONTOVIRGULA);
				setState(26);
				((AlunosContext)_localctx).a2 = aluno();
				   _localctx.totalAlunos += 1;
				                                    if (((AlunosContext)_localctx).a1.nomes.contains(((AlunosContext)_localctx).a2.name)) {
				                                        System.out.println("ERRO: Nome repetido : " + ((AlunosContext)_localctx).a2.name );
				                                    }
				                                    else { ((AlunosContext)_localctx).a1.nomes.add(((AlunosContext)_localctx).a2.name); }
				                                
				}
				}
				setState(33);
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

	public static class AlunoContext extends ParserRuleContext {
		public ArrayList<String> nomes;
		public String name;
		public NomeContext nome;
		public NotasContext notas;
		public NomeContext nome() {
			return getRuleContext(NomeContext.class,0);
		}
		public NotasContext notas() {
			return getRuleContext(NotasContext.class,0);
		}
		public AlunoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aluno; }
	}

	public final AlunoContext aluno() throws RecognitionException {
		AlunoContext _localctx = new AlunoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_aluno);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			((AlunoContext)_localctx).nome = nome();
			setState(35);
			((AlunoContext)_localctx).notas = notas((((AlunoContext)_localctx).nome!=null?_input.getText(((AlunoContext)_localctx).nome.start,((AlunoContext)_localctx).nome.stop):null));

			                                                                                ((AlunoContext)_localctx).nomes =  new ArrayList<String>();
			                                                                                ((AlunoContext)_localctx).name =  (((AlunoContext)_localctx).nome!=null?_input.getText(((AlunoContext)_localctx).nome.start,((AlunoContext)_localctx).nome.stop):null);
			                                                                                float media = ((AlunoContext)_localctx).notas.soma / ((AlunoContext)_localctx).notas.totalNotas;
			                                                                                System.out.println("A media do aluno " + (((AlunoContext)_localctx).nome!=null?_input.getText(((AlunoContext)_localctx).nome.start,((AlunoContext)_localctx).nome.stop):null) + " : " + media);
			                                                                             
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
		public TerminalNode NOME() { return getToken(notasParser.NOME, 0); }
		public NomeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nome; }
	}

	public final NomeContext nome() throws RecognitionException {
		NomeContext _localctx = new NomeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_nome);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(NOME);
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

	public static class NotasContext extends ParserRuleContext {
		public String nomeA;
		public int soma;
		public int totalNotas;
		public Token n1;
		public Token n2;
		public TerminalNode LP() { return getToken(notasParser.LP, 0); }
		public TerminalNode RP() { return getToken(notasParser.RP, 0); }
		public List<TerminalNode> NUM() { return getTokens(notasParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(notasParser.NUM, i);
		}
		public List<TerminalNode> VIRGULA() { return getTokens(notasParser.VIRGULA); }
		public TerminalNode VIRGULA(int i) {
			return getToken(notasParser.VIRGULA, i);
		}
		public NotasContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public NotasContext(ParserRuleContext parent, int invokingState, String nomeA) {
			super(parent, invokingState);
			this.nomeA = nomeA;
		}
		@Override public int getRuleIndex() { return RULE_notas; }
	}

	public final NotasContext notas(String nomeA) throws RecognitionException {
		NotasContext _localctx = new NotasContext(_ctx, getState(), nomeA);
		enterRule(_localctx, 10, RULE_notas);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(LP);
			setState(41);
			((NotasContext)_localctx).n1 = match(NUM);
			   ((NotasContext)_localctx).soma =  (((NotasContext)_localctx).n1!=null?Integer.valueOf(((NotasContext)_localctx).n1.getText()):0);
			                            ((NotasContext)_localctx).totalNotas =  1;
			                            if (!((((NotasContext)_localctx).n1!=null?Integer.valueOf(((NotasContext)_localctx).n1.getText()):0) > 0 && (((NotasContext)_localctx).n1!=null?Integer.valueOf(((NotasContext)_localctx).n1.getText()):0) < 20)) System.out.println("ERRO: Nota fora de escala!");
			                        
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VIRGULA) {
				{
				{
				setState(43);
				match(VIRGULA);
				setState(44);
				((NotasContext)_localctx).n2 = match(NUM);
				   _localctx.soma += (((NotasContext)_localctx).n2!=null?Integer.valueOf(((NotasContext)_localctx).n2.getText()):0);
				                            _localctx.totalNotas += 1;
				                            if (!((((NotasContext)_localctx).n2!=null?Integer.valueOf(((NotasContext)_localctx).n2.getText()):0) > 0 && (((NotasContext)_localctx).n2!=null?Integer.valueOf(((NotasContext)_localctx).n2.getText()):0) < 20)) System.out.println("ERRO: Nota fora de escala!");
				                        
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			match(RP);
			   if (_localctx.totalNotas < 4) System.out.println("ERRO: O Aluno " + _localctx.nomeA + " tem notas a menos!");
			                            if (_localctx.totalNotas > 6) System.out.println("ERRO: O Aluno " + _localctx.nomeA + " tem notas a mais!");
			                        
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\f9\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\6\2\20\n\2\r\2\16\2\21\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4 \n\4\f\4\16\4#\13\4\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7\61\n\7\f\7\16\7\64\13\7"+
		"\3\7\3\7\3\7\3\7\2\2\b\2\4\6\b\n\f\2\2\2\65\2\17\3\2\2\2\4\23\3\2\2\2"+
		"\6\31\3\2\2\2\b$\3\2\2\2\n(\3\2\2\2\f*\3\2\2\2\16\20\5\4\3\2\17\16\3\2"+
		"\2\2\20\21\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2\2\22\3\3\2\2\2\23\24\7\3"+
		"\2\2\24\25\7\4\2\2\25\26\5\6\4\2\26\27\b\3\1\2\27\30\7\t\2\2\30\5\3\2"+
		"\2\2\31\32\5\b\5\2\32!\b\4\1\2\33\34\7\b\2\2\34\35\5\b\5\2\35\36\b\4\1"+
		"\2\36 \3\2\2\2\37\33\3\2\2\2 #\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"\7\3\2"+
		"\2\2#!\3\2\2\2$%\5\n\6\2%&\5\f\7\2&\'\b\5\1\2\'\t\3\2\2\2()\7\5\2\2)\13"+
		"\3\2\2\2*+\7\n\2\2+,\7\6\2\2,\62\b\7\1\2-.\7\7\2\2./\7\6\2\2/\61\b\7\1"+
		"\2\60-\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\65\3\2\2\2"+
		"\64\62\3\2\2\2\65\66\7\13\2\2\66\67\b\7\1\2\67\r\3\2\2\2\5\21!\62";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}