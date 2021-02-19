// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\gestao.g4 by ANTLR 4.9

    import java.util.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class gestaoLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, STR=9, 
		ID=10, NUM=11, HIFEN=12, PONTO=13, WS=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "STR", 
			"ID", "NUM", "HIFEN", "PONTO", "WS"
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


	public gestaoLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "gestao.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20b\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\3\t\3\t\3\n\3\n\7\n@\n\n\f\n\16\nC\13\n\3\n\3\n\3\13\3\13"+
		"\7\13I\n\13\f\13\16\13L\13\13\3\f\6\fO\n\f\r\f\16\fP\3\f\3\f\6\fU\n\f"+
		"\r\f\16\fV\5\fY\n\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\2\2\20\3\3\5"+
		"\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\3\2\6"+
		"\3\2$$\5\2C\\aac|\7\2//\62;C\\aac|\5\2\13\f\17\17\"\"\2f\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5!\3\2\2\2\7\'\3\2\2\2\t)\3\2\2"+
		"\2\13+\3\2\2\2\r\62\3\2\2\2\179\3\2\2\2\21;\3\2\2\2\23=\3\2\2\2\25F\3"+
		"\2\2\2\27N\3\2\2\2\31Z\3\2\2\2\33\\\3\2\2\2\35^\3\2\2\2\37 \7=\2\2 \4"+
		"\3\2\2\2!\"\7U\2\2\"#\7V\2\2#$\7Q\2\2$%\7E\2\2%&\7M\2\2&\6\3\2\2\2\'("+
		"\7<\2\2(\b\3\2\2\2)*\7.\2\2*\n\3\2\2\2+,\7H\2\2,-\7C\2\2-.\7V\2\2./\7"+
		"W\2\2/\60\7T\2\2\60\61\7C\2\2\61\f\3\2\2\2\62\63\7X\2\2\63\64\7G\2\2\64"+
		"\65\7P\2\2\65\66\7F\2\2\66\67\7C\2\2\678\7U\2\28\16\3\2\2\29:\7\61\2\2"+
		":\20\3\2\2\2;<\7~\2\2<\22\3\2\2\2=A\7$\2\2>@\n\2\2\2?>\3\2\2\2@C\3\2\2"+
		"\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2CA\3\2\2\2DE\7$\2\2E\24\3\2\2\2FJ\t\3"+
		"\2\2GI\t\4\2\2HG\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK\3\2\2\2K\26\3\2\2\2LJ\3"+
		"\2\2\2MO\4\62;\2NM\3\2\2\2OP\3\2\2\2PN\3\2\2\2PQ\3\2\2\2QX\3\2\2\2RT\7"+
		"\60\2\2SU\4\62;\2TS\3\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2WY\3\2\2\2XR"+
		"\3\2\2\2XY\3\2\2\2Y\30\3\2\2\2Z[\7/\2\2[\32\3\2\2\2\\]\7\60\2\2]\34\3"+
		"\2\2\2^_\t\5\2\2_`\3\2\2\2`a\b\17\2\2a\36\3\2\2\2\b\2AJPVX\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}