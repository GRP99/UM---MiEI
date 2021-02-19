// Generated from C:/Users/Gonçalo Pinto/Documents/@Goncalo/Universidade/4ºANO/1Semestre/PLC_Gramáticas na Compreensão de Software/Fichas de Exercícios para as Aulas/Ficha01\notas.g4 by ANTLR 4.9
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class notasLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TURMA=1, ID=2, NOME=3, NUM=4, VIRGULA=5, PONTOVIRGULA=6, PONTO=7, LP=8, 
		RP=9, WS=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"TURMA", "ID", "NOME", "NUM", "VIRGULA", "PONTOVIRGULA", "PONTO", "LP", 
			"RP", "WS"
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


	public notasLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "notas.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\f>\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\6\4!\n\4\r\4\16\4\"\3\5\6\5&\n"+
		"\5\r\5\16\5\'\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\5\13\65\n\13"+
		"\3\13\3\13\6\139\n\13\r\13\16\13:\3\13\3\13\2\2\f\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\3\2\n\4\2VVvv\4\2WWww\4\2TTtt\4\2OOoo\4\2CC"+
		"cc\4\2C\\c|\3\2\62;\4\2\13\13\"\"\2B\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\3\27\3\2\2\2\5\35\3\2\2\2\7 \3\2\2\2\t%\3\2\2\2"+
		"\13)\3\2\2\2\r+\3\2\2\2\17-\3\2\2\2\21/\3\2\2\2\23\61\3\2\2\2\258\3\2"+
		"\2\2\27\30\t\2\2\2\30\31\t\3\2\2\31\32\t\4\2\2\32\33\t\5\2\2\33\34\t\6"+
		"\2\2\34\4\3\2\2\2\35\36\t\7\2\2\36\6\3\2\2\2\37!\t\7\2\2 \37\3\2\2\2!"+
		"\"\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#\b\3\2\2\2$&\t\b\2\2%$\3\2\2\2&\'\3\2"+
		"\2\2\'%\3\2\2\2\'(\3\2\2\2(\n\3\2\2\2)*\7.\2\2*\f\3\2\2\2+,\7=\2\2,\16"+
		"\3\2\2\2-.\7\60\2\2.\20\3\2\2\2/\60\7*\2\2\60\22\3\2\2\2\61\62\7+\2\2"+
		"\62\24\3\2\2\2\63\65\7\17\2\2\64\63\3\2\2\2\64\65\3\2\2\2\65\66\3\2\2"+
		"\2\669\7\f\2\2\679\t\t\2\28\64\3\2\2\28\67\3\2\2\29:\3\2\2\2:8\3\2\2\2"+
		":;\3\2\2\2;<\3\2\2\2<=\b\13\2\2=\26\3\2\2\2\b\2\"\'\648:\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}