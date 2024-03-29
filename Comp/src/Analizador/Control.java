package Analizador;

/* Generated By:JavaCC: Do not edit this line. Control.java */
 import java.util.*;
 public class Control implements ControlConstants {

 private static class BloqueCondicion{
 String etqVerdad, etqFalso;
 }
 public static void main(String args[]) throws ParseException {
 ControlTokenManager tm =
 new ControlTokenManager(new SimpleCharStream(System.in));
 tm.SwitchTo(tm.INICIO_LINEA);
 new Control(tm).gramatica();
 }
 private static int actualTmp=0, actualEtq=0;
 private static String nuevaTmp(){
 return "tmp"+(++actualTmp);
 }
 private static String nuevaEtq(){
 return "etq"+(++actualEtq);
 }
 private static void usarASIG(String s, String e){
 System.out.println(s+"="+e);
 }
 private static String usarOpAritmetico(String e1, String e2, String op){
 String tmp = nuevaTmp();
 System.out.println("\u005ct"+tmp+"="+e1+op+e2);
 return tmp;
 }
 private static void usarLabel(String label){
 System.out.println("label "+ label);
 }
 private static void usarGoto(String label){
 System.out.println("\u005ctgoto "+ label);
 }
 private static BloqueCondicion usarOpRelacional(String e1,
 String e2,
 String op){
 BloqueCondicion blq = new BloqueCondicion();
 blq.etqVerdad = nuevaEtq();
 blq.etqFalso = nuevaEtq();
 System.out.println("\u005ctif "+ e1+op+e2 +" goto "+ blq.etqVerdad);
 usarGoto(blq.etqFalso);
 return blq;
 }
 private static void intercambiarCondicion(BloqueCondicion blq){
 String aux = blq.etqVerdad;
 blq.etqVerdad = blq.etqFalso;
 blq.etqFalso = blq.etqVerdad;
 }

 /*
 gramatica ::= ( sentFinalizada )*
 */
  static final public void gramatica() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LLLAVE:
      case IF:
      case WHILE:
      case REPEAT:
      case CASE:
      case ID:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      sentFinalizada();
    }
  }

 /*
 sentFinalizada ::= ID ASIG expr ';' 
 | IF cond THEN sentFinalizada [ ELSE sentFinalizada ] FIN IF ';'
 | WHILE cond DO sentFinalizada FIN WHILE ';'
 | REPEAT sentFinalizada UNTIL cond ';'
 | '{' ( sentFinalizada )* '}'
 | CASE expr OF ( CASO expr ':' sentFinalizada )* 
 [ OTHERWISE sentFinalizada ] FIN CASE ';'
 | error ';'
 */
  static final public void sentFinalizada() throws ParseException {
 String s;
 String e, ei;
 BloqueCondicion c;
 String etqFinIf, etqInicioWhile, etqInicioRepeat, etqFinalCaso, etqFinCase;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        s = id();
        jj_consume_token(ASIG);
        e = expr();
                          System.out.println("\u005ct"+s+"="+e);
        break;
      case IF:
        jj_consume_token(IF);
        c = cond();
        jj_consume_token(THEN);
                          usarLabel(c.etqVerdad);
        sentFinalizada();
 usarGoto(etqFinIf=nuevaEtq());
 usarLabel(c.etqFalso);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ELSE:
          jj_consume_token(ELSE);
          sentFinalizada();
          break;
        default:
          jj_la1[1] = jj_gen;
          ;
        }
        jj_consume_token(FIN);
        jj_consume_token(IF);
              usarLabel(etqFinIf);
        break;
      case WHILE:
        jj_consume_token(WHILE);
             usarLabel(etqInicioWhile=nuevaEtq());
        c = cond();
        jj_consume_token(DO);
                 usarLabel(c.etqVerdad);
        sentFinalizada();
        jj_consume_token(FIN);
        jj_consume_token(WHILE);
 usarGoto(etqInicioWhile);
 usarLabel(c.etqFalso);
        break;
      case REPEAT:
        jj_consume_token(REPEAT);
              usarLabel(etqInicioRepeat=nuevaEtq());
        sentFinalizada();
        jj_consume_token(UNTIL);
        c = cond();
 usarLabel(c.etqFalso);
 usarGoto(etqInicioRepeat);
 usarLabel(c.etqVerdad);
        break;
      case LLLAVE:
        jj_consume_token(LLLAVE);
        gramatica();
        jj_consume_token(RLLAVE);
        break;
      case CASE:
        jj_consume_token(CASE);
        e = expr();
        jj_consume_token(OF);
                          etqFinCase = nuevaEtq();
        label_2:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case CASO:
            ;
            break;
          default:
            jj_la1[2] = jj_gen;
            break label_2;
          }
          jj_consume_token(CASO);
          ei = expr();
          jj_consume_token(DOSPUNTOS);
 System.out.println("\u005ctif "+ e+"!="+ei
 +"goto "+ (etqFinalCaso=nuevaEtq()));
          sentFinalizada();
 usarGoto(etqFinCase);
 usarLabel(etqFinalCaso);
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OTHERWISE:
          jj_consume_token(OTHERWISE);
          sentFinalizada();
          break;
        default:
          jj_la1[3] = jj_gen;
          ;
        }
        jj_consume_token(FIN);
        jj_consume_token(CASE);
                usarLabel(etqFinCase);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(PUNTOYCOMA);
    } catch (ParseException x) {
 System.out.println(x.toString());
 Token t;
 do {
 t = getNextToken();
 } while (t.kind != PUNTOYCOMA);
    }
  }

 /*
 expr ::= term (('+'|'-') term)*
 */
  static final public String expr() throws ParseException {
 String t1, t2;
    t1 = term();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MAS:
      case MENOS:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MAS:
        jj_consume_token(MAS);
        t2 = term();
                               t1=usarOpAritmetico(t1, t2, "+");
        break;
      case MENOS:
        jj_consume_token(MENOS);
        t2 = term();
                       t1=usarOpAritmetico(t1, t2, "-");
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
      {if (true) return t1;}
    throw new Error("Missing return statement in function");
  }

 /*
 term ::= fact (('*'|'/') fact)*
 */
  static final public String term() throws ParseException {
 String f1, f2;
    f1 = fact();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case POR:
      case ENTRE:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case POR:
        jj_consume_token(POR);
        f2 = fact();
                               f1=usarOpAritmetico(f1, f2, "*");
        break;
      case ENTRE:
        jj_consume_token(ENTRE);
        f2 = fact();
                       f1=usarOpAritmetico(f1, f2, "/");
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
      {if (true) return f1;}
    throw new Error("Missing return statement in function");
  }

 /*
 fact ::= ('-')* ( ID | NUMERO | '(' expr ')' ) 
 */
  static final public String fact() throws ParseException {
 String s, e, temporal;
 boolean negado = false;
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MENOS:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_5;
      }
      jj_consume_token(MENOS);
              negado = !negado;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      s = id();
          temporal = s;
      break;
    case NUMERO:
      s = numero();
                temporal = s;
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      e = expr();
      jj_consume_token(RPAREN);
                                temporal = e;
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     if (negado) temporal=usarOpAritmetico("", temporal, "-");
 {if (true) return temporal;}
    throw new Error("Missing return statement in function");
  }

 /*
 cond ::= condTerm (OR condTerm)*
 */
  static final public BloqueCondicion cond() throws ParseException {
 BloqueCondicion c1, c2;
    c1 = condTerm();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_6;
      }
      jj_consume_token(OR);
                        System.out.println("label "+ c1.etqFalso);
      c2 = condTerm();
 System.out.println("label "+ c1.etqVerdad);
 System.out.println("\u005ctgoto "+ c2.etqVerdad);
 c1 = c2;
    }
      {if (true) return c1;}
    throw new Error("Missing return statement in function");
  }

 /*
 condTerm ::= condFact (AND condFact)*
 */
  static final public BloqueCondicion condTerm() throws ParseException {
 BloqueCondicion c1, c2;
    c1 = condFact();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_7;
      }
      jj_consume_token(AND);
                         System.out.println("label "+ c1.etqVerdad);
      c2 = condFact();
 System.out.println("label "+ c1.etqFalso);
 System.out.println("\u005ctgoto "+ c2.etqFalso);
 c1 = c2;
    }
      {if (true) return c1;}
    throw new Error("Missing return statement in function");
  }

 /*
 condFact ::= (NOT)* ( condSimple | '[' cond ']' )
 */
  static final public BloqueCondicion condFact() throws ParseException {
 BloqueCondicion c1;
 boolean negado = false;
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NOT:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_8;
      }
      jj_consume_token(NOT);
            negado = !negado;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMERO:
    case MENOS:
    case LPAREN:
    case ID:
      c1 = condSimple();
      break;
    case LCOR:
      jj_consume_token(LCOR);
      c1 = cond();
      jj_consume_token(RCOR);
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
     if (negado) intercambiarCondicion(c1);
 {if (true) return c1;}
    throw new Error("Missing return statement in function");
  }

 /*
 condSimple ::= expr (('>'|'<'|'='|'>='|'<='|'!=') expr)*
 */
  static final public BloqueCondicion condSimple() throws ParseException {
 String e1, e2;
    e1 = expr();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MAYOR:
      jj_consume_token(MAYOR);
      e2 = expr();
                     {if (true) return usarOpRelacional(e1, e2, ">");}
      break;
    case MENOR:
      jj_consume_token(MENOR);
      e2 = expr();
                       {if (true) return usarOpRelacional(e1, e2, "<");}
      break;
    case IGUAL:
      jj_consume_token(IGUAL);
      e2 = expr();
                       {if (true) return usarOpRelacional(e1, e2, "=");}
      break;
    case MAI:
      jj_consume_token(MAI);
      e2 = expr();
                     {if (true) return usarOpRelacional(e1, e2, ">=");}
      break;
    case MEI:
      jj_consume_token(MEI);
      e2 = expr();
                     {if (true) return usarOpRelacional(e1, e2, "<=");}
      break;
    case DIF:
      jj_consume_token(DIF);
      e2 = expr();
                     {if (true) return usarOpRelacional(e1, e2, "!=");}
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String id() throws ParseException {
    jj_consume_token(ID);
        {if (true) return token.image;}
    throw new Error("Missing return statement in function");
  }

  static final public String numero() throws ParseException {
    jj_consume_token(NUMERO);
            {if (true) return usarOpAritmetico(token.image, "", "");}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ControlTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xe0010000,0x0,0x0,0x0,0xe0010000,0x2400,0x2400,0x1800,0x1800,0x2000,0x4040,0x8000000,0x4000000,0x10000000,0x46040,0x3f00000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x240,0x4,0x1,0x100,0x240,0x0,0x0,0x0,0x0,0x0,0x200,0x0,0x0,0x0,0x200,0x0,};
   }

  /** Constructor with InputStream. */
  public Control(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Control(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ControlTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Control(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ControlTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Control(ControlTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ControlTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[43];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 16; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 43; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
