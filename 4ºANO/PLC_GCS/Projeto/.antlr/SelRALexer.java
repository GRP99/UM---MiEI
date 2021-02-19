// Generated from c:\Users\Gonçalo Pinto\Documents\@Goncalo\Universidade\4ºANO\1Semestre\PLC_Gramáticas na Compreensão de Software\TPCs e Trabalhos Práticos\Projeto1\SelRA.g4 by ANTLR 4.8

    import java.io.*;
    import java.text.*;
    import java.util.*;
    import java.util.stream.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SelRALexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AO=1, IDR=2, IDA=3, IDC=4, NUM=5, ENSINAR=6, ENSINA=7, HIFEN=8, PONTOVIRGULA=9, 
		VIRGULA=10, PONTO=11, LPAR=12, RPAR=13, PONTOINTERROGACAO=14, DOISPONTOS=15, 
		STR=16, WS=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"AO", "IDR", "IDA", "IDC", "NUM", "ENSINAR", "ENSINA", "HIFEN", "PONTOVIRGULA", 
			"VIRGULA", "PONTO", "LPAR", "RPAR", "PONTOINTERROGACAO", "DOISPONTOS", 
			"STR", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "'-'", "';'", "','", 
			"'.'", "'['", "']'", "'?'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "AO", "IDR", "IDA", "IDC", "NUM", "ENSINAR", "ENSINA", "HIFEN", 
			"PONTOVIRGULA", "VIRGULA", "PONTO", "LPAR", "RPAR", "PONTOINTERROGACAO", 
			"DOISPONTOS", "STR", "WS"
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


	    public File myFile;
	    public File fileError;

	    class Perfil implements java.io.Serializable {
	        String nome;
	        ArrayList<String> carateristicas;

	        public String toString() {
	            return "{ \"perfil\": " + nome + ", \"carateristicas\": " + carateristicas + "}";
	        }
	    }

	    class Recurso {
	        String id;
	        String tipo;
	        String descricao;
	        int idademin;
	        int idademax;
	        ArrayList<String> perfis;

	        public String toString() {
	            return "{ \"id\": \"" + id + "\", \"tipo\": " + tipo + ", \"descricao\": " + descricao + ", \"idade_ideal\": [" + idademin + "," + idademax + "], \"perfis\": " + perfis + "}";
	        }

	        public String toS() {
	            return "Recurso " + id + ", Tipo= " + tipo + ", DescriÃ§Ã£o= " + descricao + ", Idade ideal=[" + idademin + "," + idademax + "], Perfis vocacionados= " + perfis + ";";
	        }
	    }

	    class Aluno {
	        String id;
	        String nome;
	        int idade;
	        ArrayList<String> carateristicas;

	        public String toString() {
	            return "{ \"id\": \"" + id + "\", \"nome\": " + nome + ", \"idade\": " + idade + ", \"carateristicas\": " + carateristicas + "}";
	        }
	    }

	    class Conceito {
	        String id;
	        String descricao;

	        public String toString() {
	            return "{ \"id\": \"" + id + "\", \"descricao\": " + descricao + "}";
	        }
	    }

	    class Ensinamento {
	        String idRecurso;
	        String idConceito;

	        public String toString() {
	            return "{ \"recurso\": \"" + idRecurso + "\", \"conceito\": \"" + idConceito + "\"}";
	        }
	    }

	    public void criaFicheiro() {
	        try {
	            String namefile = "___server___/output.html";
	            myFile = new File(namefile);
	            String errorfile = "___server___/error.html";
	            fileError = new File(errorfile);
	            if (myFile.createNewFile() && fileError.createNewFile()) {
	                System.out.println("Files created: " + myFile.getName() + " and " + fileError.getName() + "\n");
	            } else {
	                System.out.println("File already exists.\n");
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public void escreveFicheiro(File f, String s) {
	            try {
	                FileWriter fr = new FileWriter(f, true);
	                fr.write(s);
	                fr.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	    }

	    public void criaEstrutura() {
	        String header = "<h2 style=\"text-align:center\"> Erros encontrados </h2>";
	        try {
	            FileWriter fr = new FileWriter(myFile, false);
	            fr.write("");
	            fr.close();
	            FileWriter ferror = new FileWriter(fileError, false);
	            ferror.write(header);
	            ferror.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public void fechaEstrutura(){
	        String s = "";
	        try{
	            FileWriter fr = new FileWriter(myFile, true);
	            fr.write("");
	            fr.close();
	            FileWriter ferror = new FileWriter(fileError, true);
	            ferror.write("");
	            ferror.close();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	   class Escreve implements java.io.Serializable {
	        HashMap<String, Aluno> alunos;
	        HashMap<String, Recurso> recursos;
	        HashMap<String, Conceito> conceitos;
	        ArrayList<Ensinamento> ensinamentos;
	        HashMap<String, Perfil> perfis;

	        public String toString() {
	            return "{ \"perfis\": " + perfis.values().toString() + ","
	            + "\"recursos\": " + recursos.values().toString() + ","
	            + "\n" +
	            "\"conceitos\": " + conceitos.values().toString() + ","
	            + "\n" +
	            "\"alunos\": " + alunos.values().toString() + ","
	            + "\n" +
	            "\"ensinamentos\": " + ensinamentos.toString()
	            + "\n }" ;
	        }
	    }


	   public void escreve(Escreve e) {
	        try {
	            FileWriter fr = new FileWriter("___server___/db.json", false);
	            fr.write(e.toString());
	            fr.close();
	        } catch (IOException err) {
	            err.printStackTrace();
	        }
	    }


	public SelRALexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SelRA.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23v\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\2\3\3\3\3\6\3+\n\3\r\3\16\3,\3\4\3\4\3\4\5\4\62\n\4\3\4\6\4"+
		"\65\n\4\r\4\16\4\66\3\5\3\5\6\5;\n\5\r\5\16\5<\3\6\6\6@\n\6\r\6\16\6A"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3"+
		"\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3"+
		"\21\7\21e\n\21\f\21\16\21h\13\21\3\21\3\21\3\22\5\22m\n\22\3\22\3\22\6"+
		"\22q\n\22\r\22\16\22r\3\22\3\22\2\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21"+
		"\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23\3\2\f\4\2CCcc\4\2Q"+
		"Qqq\3\2\62;\4\2GGgg\4\2PPpp\4\2UUuu\4\2KKkk\4\2TTtt\3\2$$\4\2\13\13\""+
		"\"\2~\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r"+
		"\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2"+
		"\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2"+
		"#\3\2\2\2\3%\3\2\2\2\5(\3\2\2\2\7\61\3\2\2\2\t8\3\2\2\2\13?\3\2\2\2\r"+
		"C\3\2\2\2\17K\3\2\2\2\21R\3\2\2\2\23T\3\2\2\2\25V\3\2\2\2\27X\3\2\2\2"+
		"\31Z\3\2\2\2\33\\\3\2\2\2\35^\3\2\2\2\37`\3\2\2\2!b\3\2\2\2#p\3\2\2\2"+
		"%&\t\2\2\2&\'\t\3\2\2\'\4\3\2\2\2(*\7T\2\2)+\t\4\2\2*)\3\2\2\2+,\3\2\2"+
		"\2,*\3\2\2\2,-\3\2\2\2-\6\3\2\2\2.\62\7C\2\2/\60\7R\2\2\60\62\7I\2\2\61"+
		".\3\2\2\2\61/\3\2\2\2\62\64\3\2\2\2\63\65\t\4\2\2\64\63\3\2\2\2\65\66"+
		"\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\67\b\3\2\2\28:\7E\2\29;\t\4\2\2:"+
		"9\3\2\2\2;<\3\2\2\2<:\3\2\2\2<=\3\2\2\2=\n\3\2\2\2>@\t\4\2\2?>\3\2\2\2"+
		"@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\f\3\2\2\2CD\t\5\2\2DE\t\6\2\2EF\t\7\2"+
		"\2FG\t\b\2\2GH\t\6\2\2HI\t\2\2\2IJ\t\t\2\2J\16\3\2\2\2KL\t\5\2\2LM\t\6"+
		"\2\2MN\t\7\2\2NO\t\b\2\2OP\t\6\2\2PQ\t\2\2\2Q\20\3\2\2\2RS\7/\2\2S\22"+
		"\3\2\2\2TU\7=\2\2U\24\3\2\2\2VW\7.\2\2W\26\3\2\2\2XY\7\60\2\2Y\30\3\2"+
		"\2\2Z[\7]\2\2[\32\3\2\2\2\\]\7_\2\2]\34\3\2\2\2^_\7A\2\2_\36\3\2\2\2`"+
		"a\7<\2\2a \3\2\2\2bf\7$\2\2ce\n\n\2\2dc\3\2\2\2eh\3\2\2\2fd\3\2\2\2fg"+
		"\3\2\2\2gi\3\2\2\2hf\3\2\2\2ij\7$\2\2j\"\3\2\2\2km\7\17\2\2lk\3\2\2\2"+
		"lm\3\2\2\2mn\3\2\2\2nq\7\f\2\2oq\t\13\2\2pl\3\2\2\2po\3\2\2\2qr\3\2\2"+
		"\2rp\3\2\2\2rs\3\2\2\2st\3\2\2\2tu\b\22\2\2u$\3\2\2\2\f\2,\61\66<Aflp"+
		"r\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}