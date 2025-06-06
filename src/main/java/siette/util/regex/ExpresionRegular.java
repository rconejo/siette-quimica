/**
 * *****************************************************************************
 * *****************************************************************************
 * clase ExpresionRegular
 * *****************************************************************************
 * *****************************************************************************
 */

package siette.util.regex;

/**
 * Patron: Expresion Regular
 *
 * @author Ricardo Conejo
 * @version 2.10
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import siette.util.text.Strings;

// import org.apache.log4j.Logger;

public class ExpresionRegular extends Patron {

	// static Logger logger = Logger.getLogger(ExpresionRegular.class);

///////////////////////////////////////////////////////////////////
public class Procesador {


boolean MODO_NUMERICO = false; // true: Si los numeros se comprueban aparte,
                                // mediante rangos
Rango valor[];
AFN afn;

public Procesador(String patron) {
    Parser p = new Parser(patron);
    afn = p.afn();
    valor = p.valores();
    if (valor != null && valor.length > 0) {
    	// si hay expresiones de numeros que deban reconocerse aparte
        MODO_NUMERICO = true;
    }
}

public void reset() {
	for(int i=0; i<valor.length; i++) {
		valor[i].reset();
	}
}

} // class Procesador

/////////////////////////////////////////////////////////////////////////////////////////
private class Parser {

String patron; // aqui hace las veces de "fichero fuente"
int posicion; // por donde va analizando
char token; // ultimo token leido
int error = -1; // contiene la posicion en la que se detecto el error
// -1 en caso de que no haya error
AFN afn; // automata finito no determinista que reconoce la expresion
ArrayList valor; // valores de los numeros con precision que se encuentren en la expresion

Parser(String patron) {
    this.patron = patron;
    // System.out.println("patron="+patron);
    error = -1;
    yylex(0);
    token = yylex();
    afn = expresion();
    if (token != '$') {
        error();
    }
}

/**
 * El analizador lexico de la expresion va devolviendo simplemente caracter a
 * caracter
 */
private char yylex() {
    char token = '$';
    if (patron.length() > posicion) {
        token = patron.charAt(posicion++);
    }
    // System.out.println("token="+token);
    return token;
}

/**
 * El analizador lexico de la expresion va devolviendo simplemente caracter a
 * caracter
 */
private void yypushback() {
    if (posicion>0) {
        posicion--;
    }
}


/**
 * Inicializacion de la posicion de lectura
 */
private void yylex(int pos) {
    posicion = pos;
}

private AFN expresion() {
	// System.out.println("expresion() token="+token);
    AFN afn = new AFN();
    AFN afn1, afn2; // auxiliar
    switch (token) {
        case '$' :
        case '|' :
        case ']' :
        case ')' :
            error();
            break;
        default :
            ArrayList afns = lista();
            afn = afn.permutaciones(afns);
            afn1 = eprima();
            afn = afn.union(afn1);
    }
    // System.out.println(afn + "---- expresion ");
    return afn;
}

private AFN eprima() {
	// System.out.println("eprima() token="+token);
    AFN afn = new AFN();
    switch (token) {
        case '$' : // se devuelve un automata vacio
        case '}' :
        case ')' :
            afn = null;
            break;
        case '|' :
            token = yylex();
            afn = expresion();
            break;
        default :
            error();
            break;
    }
    // System.out.println(afn + " --- eprima ");
    return afn;
}

//Devuelve un ArrayList de AFN
private ArrayList lista() {
	// System.out.println("expresion() token="+token);
    ArrayList afns = new ArrayList();
    AFN afn1; // auxiliar
    switch (token) {
        case '$' :
        case '|' :
        case '+' :
        case ']' :
        case ')' :
            error();
            break;
        default :
            afn1 = termino();
            afns = listap();
             if (afn1 != null && afns!=null) {
            	afns.add(afn1);
            }
    }
    // System.out.println(afns + "---- lista ");
    return afns;
}

// Devuelve un ArrayList de AFN
private ArrayList listap() {
	// System.out.println("fprima() token="+token);
    ArrayList afns = new ArrayList();
    switch (token) {
        case '$' : // se devuelve un automata vacio
        case '|' :
        case '}' :
        case ')' :
            afns = new ArrayList();
            break;
        case '+' :
            token = yylex();
            afns = lista();
            break;
        default :
            error();
            break;
    }
    // System.out.println(afns + " --- listap ");
    return afns;
}



private AFN termino() {
	// System.out.println("termino() token="+token);
    AFN afn = new AFN();
    AFN afn1, afn2; // auxiliar
    String eInicial; // auxiliar
    String eFinal; // auxiliar
    Character s; // auxiliar
    switch (token) {
        case '|' :
        case '+' :
        case ')' :
        case '}' :
        case '$' : // automata que reconoce la cadena vacia
            eInicial = AF.nuevoEstado();
            afn.apuntaTransicion(eInicial, AF.EPSILON, eInicial);
            afn.apuntaEstadoInicial(eInicial);
            afn.apuntaEstadoFinal(eInicial);
            break;
        case ']' :
            error();
            break;
        case '*' :
            token = yylex();
            afn = termino();
            afn.apuntaTransicion(afn.estadoInicial(), AF.COMODIN, afn
                .estadoInicial());
            break;
        case '?' :
            token = yylex();
            afn = termino();
            eInicial = AFN.nuevoEstado();
            afn.apuntaTransicion(eInicial, AF.COMODIN, afn.estadoInicial());
            afn.apuntaEstadoInicial(eInicial);
            break;
        case '(' :
            token = yylex();
            afn = expresion();
            if (token == ')')
                token = yylex();
            else
                error();
            afn2 = termino();
            afn.concatena(afn2);
            break;
        case '{' :
            token = yylex();
            afn = expresion();
            afn.apuntaTransicion(afn.estadoInicial(), AF.EPSILON, afn
                .estadosFinales());
            if (token == '}')
                token = yylex();
            else
                error();
            afn2 = termino();
            afn.concatena(afn2);
            break;
        case '[' :
            token = yylex();
            afn = caracteres();
            if (token == ']')
                token = yylex();
            else
                error();
            afn2 = termino();
            afn.concatena(afn2);
            break;
        case '\\' :
            token = yylex();
            s = new Character(token);
            token = yylex();
            afn = termino();
            eInicial = AFN.nuevoEstado();
            afn.apuntaTransicion(eInicial, s, afn.estadoInicial());
            afn.apuntaEstadoInicial(eInicial);
            break;
        case '#' :
        case '<' : // OJO: Este caso debe ser el ultimo antes del default
            token = yylex();
            if (token != '!') { 
                // en el AF, se mete un #n en vez del numero mismo,
                // para poder aplicar el factor de precision y poder
                // seguir haciendo el reconocimiento de la cadena
                // para expresiones como "9.81 aprox"
                // Los numeros se reconocen luego por rangos
                String strNumero = numero();
                afn = termino();
                eInicial = AFN.nuevoEstado();
                afn.apuntaTransicion(eInicial, strNumero, afn.estadoInicial());
                afn.apuntaEstadoInicial(eInicial);
                break;
            } else { // STE-1204 Caso especial de comenatrio HTML <!--  -->
            	yypushback();
            	token = '<';
            	// contnua con el codigo escrito en default
            }
        default :
            s = new Character(token);
            token = yylex();
            afn = termino();
            eInicial = AFN.nuevoEstado();
            afn.apuntaTransicion(eInicial, s, afn.estadoInicial());
            afn.apuntaEstadoInicial(eInicial);
            break;
    }
    // System.out.println(afn +" ---- termino ");
    return afn;
}

private AFN caracteres() {
	// System.out.println("caracteres() token="+token);
    AFN afn = new AFN();
    String eInicial = AFN.nuevoEstado();
    String eFinal = AFN.nuevoEstado();
    afn.apuntaEstadoInicial(eInicial);
    afn.apuntaEstadoFinal(eFinal);
	char tokenInicial = (char)0;
	char tokenFinal = (char)0;
    while (token != ']' && token != '$') {
        if (token == '-' && tokenInicial != (char)0 ) {
        	token = yylex();
        	if (token != ']' && token != '$') {
        		tokenFinal = token;
        		if (tokenInicial < tokenFinal) {
        			for(int tk=(int)tokenInicial+1; tk<=(int)tokenFinal; tk++) {
        	        	afn.apuntaTransicion(eInicial, (char)tk, eFinal);
        			}
        			tokenInicial = tokenFinal = (char)0;
        		}
        	} else {
        		// '-' al final 
        	}
        } else {
        	if (token!='-') {
            	tokenInicial = token;
        	}
        	afn.apuntaTransicion(eInicial, token, eFinal);
        	token = yylex();
        }
    }
    // logger.debug(afn+ "--- caracteres ");
    return afn;
}

private String numero() {
	// logger.debug("numero() token="+token);
    String sValor1 = "";
    String sValor2 = "";
	String sUnidades = "";

	// se lee el primer numero
    while (Strings.esNumerico(token) && token != '$') {
        sValor1 += token;
        token = yylex();
    }
    sValor1 = sValor1.replace(',', '.'); // como separador decimal se acepta , y .

    // se especifican las unidades por ejemplo #9,81 m/s#3%
    if (token != '#' && token != '>' && token != '|' && token != '%' && token != '$') {
    	sUnidades = "";
        while (token != '#' && token != '>' && token != '|' && token != '%' && token != '$') {
        	// System.out.println("token="+token+">");
            sUnidades += token;
            token = yylex();
        }
    }

    //  leer el separador #, | o %  o bien >
    // si #120#140#  separador #
    // si <120|140>  separador |
    // si <120%10> separador %
    // si <120> separador >
    boolean esPorcentaje = false;
    if (token != '>') {
        if (token == '%') {
        	esPorcentaje = true;
        } 
        token = yylex();

        while (Strings.esNumerico(token) && token != '$') {
            sValor2 += token;
            token = yylex();
        }
        sValor2 = sValor2.replace(',', '.'); // como separador decimal se acepta , y .

        // se especifican las unidades en segunda posicion por ejemplo #9,81#9,99 m/s2#
        if (token != '%' && token != '#' && token != '>' && token != '$') {
        	sUnidades = "";
            while (token != '%' && token != '#' && token != '>' && token != '$') {
            	// System.out.println("token="+token+">");
                sUnidades += token;
                token = yylex();
            }
        }
    } else {
    	sValor2 = sValor1;
    }

    // Componer el rango y su magnitud
    Rango r;
    try {
		// System.out.println("sUnidades=<"+sUnidades+">");
     	Magnitud magnitud = Magnitud.VACIO;
    	boolean esMagnitudOpcional = false;
    	if (sUnidades!=null) {
    		sUnidades = sUnidades.trim();
    		// System.out.println("sUnidades=<"+sUnidades+">");
    	}
    	if (!sUnidades.equals("")) {
    		// System.out.println("sUnidades=<"+sUnidades+">");
    		if (sUnidades.charAt(0) == '{' && sUnidades.charAt(sUnidades.length()-1) == '}' ) {
    			sUnidades = sUnidades.substring(1,sUnidades.length()-1);
    			esMagnitudOpcional = true;
    		}
    		int i = lookaheadToken(Magnitud.lista(), sUnidades, MODO_MAYUSCULAS);
    		// System.out.println("i=<"+i+">");
    		// System.out.println("sUnidades=<"+sUnidades+">");
    		if (i != -1 &&
    			   (     sUnidades.equals(Magnitud.lista()[i])
    			      || sUnidades.equalsIgnoreCase(Magnitud.lista()[i]) && MODO_MAYUSCULAS
    			   )
    	       ) {
    			// una de las magnitudes de la tabla
    			magnitud = Magnitud.tablaMagnitudes[i];
        		// System.out.println("sUnidades#1=<"+sUnidades+">");
        	  // System.out.println("magnitud#1=<"+magnitud+">");
    		} else {
    			// una magnitud desconocida
    			magnitud = new Magnitud(sUnidades);
        		// System.out.println("sUnidades#2=<"+sUnidades+">");
        		// System.out.println("magnitud#2=<"+magnitud+">");
    		}
    	}
        if (token == '%' || esPorcentaje) { // #Valor#Precision% o bien <Valor%Precision>
            double dValor = (new Double(sValor1)).doubleValue();
            double dPrecision = (new Double(sValor2)).doubleValue();
            r = new Rango(dValor - dValor * dPrecision / 100,
                          dValor + dValor * dPrecision / 100,
                          magnitud,
                          esMagnitudOpcional);
        } else { // #Minimo#Maximo# o bien <Minimo|Maximo>
            double dMinimo = (new Double(sValor1)).doubleValue();
            double dMaximo = (new Double(sValor2)).doubleValue();
            r = new Rango(dMinimo, dMaximo, magnitud, esMagnitudOpcional);
        }
    } catch (NumberFormatException e) {
    	r = new Rango();  // rango erroneo
    }

    // System.out.println("Rango r="+r);

    token = yylex(); // avanzar despues del separador % o #
    if (valor == null) {
        valor = new ArrayList();
    }
    int n = valor.size(); // por defecto se considera que se inserta en una nueva posicion

	// System.out.println("Rango r="+r);
    // ver si el numero existe ya
    /* STE-1204 No hace falta comprobar si ya se ha definido el rango, pueden incluso repetirse.
    for (int i = 0; i < valor.size(); i++) {
        Rango r0 = (Rango) valor.get(i);
        if (r0.minimo <= r.minimo && r.maximo <= r0.maximo && r.magnitud == r0.magnitud) {
            n = i;
            break;
        }
    }
    */
    
    valor.add(r);
    String sNumero = "#" + n;
    // System.out.println("sNumero="+sNumero);
    return sNumero;
}

private void error() {
	// System.out.println("error() token="+token);
    error = posicion;
    posicion = patron.length();
    token = '$';
}

AFN afn() {
    if (error != -1)
        return null;
    return afn;
}

Rango[] valores() {
    if (valor != null) {
        return (Rango[]) valor.toArray(new Rango[0]);
    } else {
        return new Rango[0];
    }
}

} // class Parser


//////////////////////////////////////////////////////////////////////////////////
protected Procesador[] procesador; // el procesador que va a lleva a cabo el
                            // reconocimiento del patron i-esimo

public ExpresionRegular(String st[], Boolean mMayusculas, Boolean mAcentos, Boolean mSignos, Boolean mBlancos, Boolean mVariaciones) {
    super(st, mMayusculas, mAcentos, mSignos, mBlancos, mVariaciones);
    construirProcesador();
}

public ExpresionRegular(String[] patrones, Boolean mMayusculas, Boolean mAcentos, Boolean mSignos, Boolean mBlancos) {
	this(patrones, mMayusculas, mAcentos, mSignos, mBlancos, Boolean.FALSE);
}

public ExpresionRegular(String st[]) {
    this(st, Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE, Boolean.FALSE);
}

public ExpresionRegular(String st, boolean mMayusculas, boolean mAcentos, boolean mSignos, boolean mBlancos) {
    super(st, mMayusculas, mAcentos, mSignos, mBlancos);
    construirProcesador();
}

public ExpresionRegular(String st) {
    this(st, false, false, false, false);
}

protected void construirProcesador() {
    procesador = new Procesador[patron.length];
    for (int i = 0; i < patron.length; i++) {
        procesador[i] = new Procesador(normalizar(patron[i]));
    }
}

// Se reescribe para evitar la llamada a "normalizar", ya que en este patron hay
// que normalizar primero los numeros y luego los espacios en blanco, acentos, etc..
// la llamada a normalizar() se hace en el metodo pertenece(String st, int kesimo)
public int[] pertenece(String st[]) {
    int correcta[] = new int[st.length];
    for (int i = 0; i < st.length; i++) {
        correcta[i] = pertenece(st[i],nIguales(st,i));
    }
    return correcta;
}

// Devuelve el numero del primer patron que reconoce la cadena st despues de la posicion k-esima
// de manera que si hay varias respuestas los patrones que ya se han instanciado con alguna otra respuesta
// no se tengan en cuenta
public int pertenece(String st, int kesimo) {
    int iPatron = FALLO;
    int i = 0;
    int k = 0;
    while (iPatron == FALLO && i < patron.length && k<=kesimo) {
        if (reconoce(i,st)) {
        	if (k==kesimo) {
                iPatron = i;
        	}
            k++;
        }
        i++;
    }
    return iPatron;
}

protected boolean reconoce(int iPatron, String respuesta) {
	boolean reconoce = false;
    if (procesador[iPatron] != null) {
        AFN afn = procesador[iPatron].afn;
        if (respuesta != null && afn != null  && afn.reconoce(normalizar(respuesta, procesador[iPatron]))) {
        	reconoce = true;
        }
    }
    return reconoce;
}


protected void reset() {
	for (int i=0; i<patron.length; i++) {
		procesador[i].reset();
	}
}

public String[] genera(int n) {
	HashSet todas = new HashSet();
    for(int i=0; i< patron.length; i++) {
        AFN afn = procesador[i].afn;
        String[] cadenas = afn.genera(n);
        for(int j=0; j<cadenas.length; j++) {
        	todas.add(cadenas[j]);
        }
    }
    return (String[]) todas.toArray(new String[todas.size()]);
}

public String[] generaLongExacta(int n) {
	HashSet todas = new HashSet();
    for(int i=0; i< patron.length; i++) {
        AFN afn = procesador[i].afn;
        String[] cadenas = afn.genera(n);
        for(int j=0; j<cadenas.length; j++) {
        	if (cadenas[j].length() == n) {
            	todas.add(cadenas[j]);
        	}
        }
    }
    return (String[]) todas.toArray(new String[todas.size()]);
}


protected String normalizar(String st, Procesador proc) {
    if (st == null)
        return ""; // ASSERT

  // System.out.println("normalizar#1 st=<"+st+"> length=("+st.length()+")" );
    if (proc.MODO_NUMERICO) {
    	// Para evitar trampas se elimina el token de numero, si lo hubiera
    	st = Strings.eliminarCaracter(st,'#');
    	// Se transforman los numeros en tokens de numeros
        st = normalizarNumeros(st, proc.valor);
      // System.out.println("normalizar#2 st=<"+st+"> length=("+st.length()+")" );
    }
    // eliminar signos, caracteres en blanco, etc. despues de numeros, ya que los numeros tienen signos
    st = super.normalizar(st);
  // System.out.println("normalizar#3 st=<"+st+"> length=("+st.length()+")" );
    return st;
}

protected String normalizarNumeros(String in, Rango[] rango) {
    String out = "";
    int i = 0;
    int tmp = 0; // variable auxiliar
	final int NUMERO = 100;
	final int ALFANUMERICO = 101;
	final int OTRO = 99;
	final String[] potencia10 = { "10" };
 	in += '\u0000';
	int estado = 0;
	String yytext = "";
    while (i < in.length()) {
        String inRest = in.substring(i);
        char ch = in.charAt(i++);
        // System.out.println("in=<"+in+">"+"  i="+i+"  estado="+estado+ " ch=<"+ch+ "> int("+(int)ch+") inRest=<"+inRest+">");
		switch (estado) {
			case 0: {
				yytext = "";
				if (ch == '-' || ch == '+') {
					yytext += ch;
					estado = 1;
				} else if (ch >= '0' && ch <= '9') {
					yytext += ch;
					estado = 2;
				} else if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch == '_') {
					yytext += ch;
					estado = 10;
				} else {
					yytext += ch;
					estado = OTRO;
				}
				break;
			}
			case 1: {
				if (ch >= '0' && ch <= '9') {
					yytext += ch;
					estado = 2;
				} else {
					yytext += ch;
					estado = OTRO;
				}
				break;
			}
			case 2: {
				if (ch >= '0' && ch <= '9') {
					yytext += ch;
					estado = 2;
				} else if (ch == '.' || ch == ',') {
					yytext += '.';
					estado = 3;
				} else if (ch == 'E' || ch == 'e') {
					yytext += ch;
					estado = 5;
				} else if ((tmp=lookahead(potencia10,inRest,true))!=-1) {
					i += (tmp-1); // se avanza la cabeza de lectura
					yytext += 'E';
					estado = 5;
				} else {
					estado = NUMERO;
				}
				// System.out.println("ESTADO-2: yytext="+ yytext + " estado="+estado);
				break;
			}
			// Parte decimal
			case 3: {
				if (ch >= '0' && ch <= '9') {
					yytext += ch;
					estado = 4;
				} else if (ch == 'E' || ch == 'e') {
					// numero sin parte decimal, se annade un cero
					yytext += '0';
					yytext += ch;
					estado = 5;
				} else if ((tmp=lookahead(potencia10,inRest,true))!=-1) {
					i += (tmp-1); // se avanza la cabeza de lectura
					// numero sin parte decimal, se annade un cero
					yytext += '0';
					yytext += 'E';
					estado = 5;
				} else {
					// numero sin parte decimal, se annade un cero
					yytext += '0';
					estado = NUMERO;
				}
				break;
			}
			case 4: {
				if (ch >= '0' && ch <= '9') {
					yytext += ch;
					estado = 4;
				} else if (ch == 'E' || ch == 'e') {
					yytext += ch;
					estado = 5;
				} else if ((tmp=lookahead(potencia10,inRest,true))!=-1) {
					i += (tmp-1); // se avanza la cabeza de lectura
					yytext += 'E';
					estado = 5;
				} else {
					estado = NUMERO;
				}
				// System.out.println("ESTADO-4: yytext="+ yytext + " estado="+estado);
				break;
			}
			//Exponente
			case 5: {
				if (ch == '-' || ch == '+') {
					yytext += ch;
					estado = 6;
				} else if (ch >= '0' && ch <= '9') {
					yytext += ch;
					estado = 6;
				} else {
					// numero sin exponente, se annade un uno
					yytext += '1';
					estado = NUMERO;
				}
				// System.out.println("ESTADO-5: yytext="+ yytext + " estado="+estado);
				break;
			}
			case 6: {
				if (ch >= '0' && ch <= '9') {
					yytext += ch;
					estado = 6;
				} else {
					estado = NUMERO;
				}
				break;
			}
			// Alfanumerico
			case 10: {
				if ( // ch >= '0' && ch <= '9' || // STE-742 No permite reconocer los numeros pegados... (cuando se ignoran los espacios en blanco)
				    ch >= 'a' && ch <= 'z' ||
				    ch >= 'A' && ch <= 'Z' ||
				    ch == '_') {
					yytext += ch;
					estado = 10;
				} else {
					estado = ALFANUMERICO;
				}
				break;
			}
		}

		// comprobar los estados finales
		switch (estado) {
			// se ha reconocido un numero
			case NUMERO: {
				estado = 0;
				// descartar el ultimo caracter leido
				if (i < in.length()) {
					i--;
				}
				// comprobar la magnitud (si aparece a continuacion)
                try {
    			   // System.out.println("ESTADO-100: yytext="+ yytext + " estado="+estado);
   				   // System.out.println("ESTADO-100: inRest="+inRest+ "| i="+i+" in.substring(i-1)="+in.substring(i-1)+"|");
                   double dValor = (new Double(yytext)).doubleValue();
                   boolean encontrado = false;
                   int avanzarLectura = 0;
                   for (int j = 0; j < rango.length && !encontrado; j++) {
                       // System.out.println("ESTADO-100: j="+j+ " rango[j]="+rango[j] );
                	   Magnitud mag = Magnitud.VACIO;
                	   if (rango[j].magnitud != Magnitud.VACIO) {
                   		   // buscar la magnitud equivalente en la respuesta
                		   int lMag = -1;
                		   int n = lookaheadToken(Magnitud.lista(), inRest, MODO_MAYUSCULAS);
        				   // System.out.println ("inRest=<"+inRest+ "> n="+n );
                 		   if (n == -1) {
                 			  // en caso de que no se haya encontrado la magnitud
                 			  // en la lista de magnitudes, hacer un ultimo intento
                 			  // por si coincide con una magnitud desconocida incluida en el patron
                 			  lMag = lookahead(rango[j].magnitud.unidad, inRest, MODO_MAYUSCULAS);
           				    // System.out.println ("lMag#1=<"+lMag+ ">");
                    		   if ( lMag != -1) {
                    			   mag = rango[j].magnitud;
                     		   }
                 		   } else {
                			   mag = Magnitud.tablaMagnitudes[n] ;
                			   lMag = lookahead(mag.unidad,inRest, MODO_MAYUSCULAS);
            				   // System.out.println ("lMag#2=<"+lMag+ ">");
                 		   }
            			   // si la magnitud que se ha reconocido no completa la entrada
            			   // el siguiente caracter debe ser un espacio en blanco, o delimitador
            			   // para estar seguros de que se ha leido una magnitud y no un texto que aparece a contnuacion
            			   // ej "k granada" podria interpretarse incorrectamente como "kg"+"ranada"
            			   // ej "k g de patatas" podria interpretarse corectamente como "kg"+"de patatas"
        				   // System.out.println("lMag="+lMag+"  inRest=<"+inRest+ "> ("+inRest.length()+") ");
            			   if (lMag>0 && lMag<=inRest.length() && ! esDelimitadorUnidades(inRest.charAt(lMag-1)) ) {
            				   lMag = -1;
            			   }
                 		   if (lMag!=-1 ) {
                 			   // Eliminar de la entrada los caracteres que representan la magnitud
                 			  avanzarLectura = lMag ;
                 		   }
                	   } else {
                		   // Si no se han especificado unidades en el patron, no se comprueban en la entrada ...
                		   mag = Magnitud.VACIO;
                	   }
                     // System.out.println("ESTADO-100: j="+j+ " rango[j]="+rango[j]);
                     // System.out.println("dValor="+ dValor+" mag="+mag);
                       if (rango[j].contiene(dValor, mag)) {
                           out += "#" + j; // se ha encontrado el Numero etiquetado como #3
                           encontrado = true;
                           i += avanzarLectura;
                       }
                   }
                   if (!encontrado) {
                       out += yytext;
                   }
                } catch (Exception e) {
                	e.printStackTrace();
                  // en caso de que la secuencia reconocida no de lugar a un numero correcto en JAVA
                  out += yytext;
                }
				break;
			}
			// se ha reconocido un identificador alfanumerico
			case ALFANUMERICO: {
				estado = 0;
				if (i < in.length()) i--;
				out += yytext;
				break;
			}
			case OTRO: {
				estado = 0;
				if (i < in.length()) {
				   out += yytext;
				}
			}
		}
	}
  // System.out.println("out=<"+out+">");
    return out;
}

/** Esta funcion localiza un pivote como parte inicial de una cadena
 * de entrada, y devuelve el numero de caracteres que hay que saltar para
 * obtener ese pivote, o bien -1 si el pivote no se encuetra
 * Por ejemplo lookahead("kgm"," Kg . m = solucion") debe devolver 7
 * ya que suprimiendo los espacios en blanco y los caracteres de puntuacion
 * la cadena kgm aparece en los 8 primeros caracteres.
 * Por ejemplo lookahead("kgm"," kg = solucion") debe devolver -1
 *
 */
public int lookahead(String prefijo, String in, boolean modoMayusculas) {
	// se normaliza el prefijo
	prefijo = eliminarBlancos(prefijo);
	prefijo = eliminarSignos(prefijo);
	prefijo = eliminarSimbolosMudos(prefijo);
	if (modoMayusculas) {
		prefijo = prefijo.toUpperCase();
		in = in.toUpperCase();
	}
	// System.out.println("prefijo="+prefijo+" in="+in);
	int i=0; // indice del prefijo
	int j=0; // indice de la cadena de entrada
	boolean concuerdan = true;
	while ( concuerdan && i<prefijo.length() && j<in.length() ) {
        char ch = prefijo.charAt(i);
	    while (j<in.length() && (Strings.esBlanco(in.charAt(j)) || Strings.esSignoPuntuacion(in.charAt(j)) || esSimboloMudo(in.charAt(j)) ) ) {
	    	j++;
	    }
	    if (j<in.length() && ch == in.charAt(j)) {
	    	i++;
	    	j++;
	    } else {
	    	concuerdan = false;
	    }
    }
	// System.out.println("i="+i+" j="+j+" concuerdan="+concuerdan);
	if (concuerdan && i==prefijo.length()) {
		// detectar si el ultimo simbolo esta ligado a una palabra siguiente o no
		// ej "10  kg . m  anzanas" --> "10 kg . m  " + "anzanas"
		// ej "10  kg . manzanas" --> "10 kg . " + "manzanas"
		// en este segundo caso el prefijo "kg . m" no encaja
        if (j<in.length()) {
    		concuerdan = false;
    		while (j<in.length() && (Strings.esBlanco(in.charAt(j)) || Strings.esSignoPuntuacion(in.charAt(j)) || esSimboloMudo(in.charAt(j)) ) ) {
    	    	// elimina los caracteres superfluos de la entrada detras del prefijo
    			concuerdan = true;
    	    	j++;
    	    }
        }
	    if (concuerdan && i>0) {
	       // si al menos se ha encajado un caracter
		   return j;
	    }
	}
	return -1;

}

/** Igaul que el anterior pero busca la existencia de alguno
 * de los prefijos incluidos en el array y devuelve el indice mayor
 * de los encontrados (-1 si no coincide ninguno)
 */
public int lookahead(String[] prefijo, String in, boolean modoMayusculas) {
	if (prefijo==null) return -1;
	int max = -1;
	for(int i=0; i<prefijo.length; i++) {
		int val = lookahead(prefijo[i],in, modoMayusculas);
		// System.out.println("in="+in+"|  prefijo[i]="+prefijo[i]+"|  val="+val);
		if( val > max) {
			// System.out.println("lookahead    in="+in+"|  prefijo[i]="+prefijo[i]+"|  val="+val);
			max = val;
		}
	}
	return max;
}

/** Igual que el anterior pero devuelve el indice del prefijo (-1 si no coincide ninguno)
 */
public int lookaheadToken(String[] prefijo, String in, boolean modoMayusculas) {
	if (prefijo==null) return -1;
	int max = -1;
	int indice = -1;
	for(int i=0; i<prefijo.length; i++) {
		int val = lookahead(prefijo[i],in,modoMayusculas);
		// System.out.println("in="+in+"|  prefijo[i]="+prefijo[i]+"|  val="+val);
		if( val > max) {
			// System.out.println("lookaheadToken   in="+in+"|  prefijo[i]="+prefijo[i]+"|  val="+val);
			max = val;
			indice = i;
		}
	}
	return indice;
}

public static String eliminarCaracteresEspeciales(String in) {
	return eliminarCaracteresEspeciales(in, false);
}
public static String eliminarCaracteresEspeciales(String in, boolean escapar) {
    StringBuffer out = new StringBuffer();
    for (int i = 0; i < in.length(); i++) {
        char ch = in.charAt(i);
        if (   ch!='+' && ch!='*' && ch!='?' && ch!='#' && ch!='%' && ch!='$' && ch!='|'
        		&& ch!='(' && ch!=')' && ch!='[' && ch!=']' && ch!='{' && ch!='}' && ch!='<' && ch!='>' 
        	   ) {
        		out.append(ch);
        } else if (escapar) {
    			out.append('\\');
    			out.append(ch);
        }
    }
	return out.toString();
}

@Override
public String eliminarSignos(String in) {
    String out = "";
    int fuera = 0; // para evitar eliminar signos de los patrones numericos o comentarios HTML usados en preguntas de fichero
    for (int i = 0; i < in.length(); i++) {
        char ch = in.charAt(i);
        // No eliminar signos dentro de las expresiones regulares Siette de rangos de numeros, en cualquiera de las fos formas en que se aceptan: #3.14#1% <3.14|3.15> <3.14%1>
        if (fuera==0 && (ch == '#' || ch == '<')) {
                fuera = 1; // primer numero
        } else if (fuera==1 && (ch == '#' || ch == '|' || ch == '%')) { 
            	fuera = 2;  // segundo numero
        } else if (fuera==2 && (ch == '#' || ch == '>' || ch == '%')) { 
        		fuera = 0; // ya hemos terminado...
        }

        if (fuera == 0) {
            if (Strings.esSignoPuntuacion(ch)) {
                ;
             } else {
                 out += ch;
             }
        } else {
            out += ch;
        }
    }
    return out;
}


public static String eliminarSimbolosMudos(String in) {
    String out = "";
    for (int i = 0; i < in.length(); i++) {
        char ch = in.charAt(i);
        if (esSimboloMudo(ch)) {
           ;
        } else {
            out += ch;
        }
    }
    return out;
}

// Para implificar la notacion en las unidades hciendo "kg*m/s^2" equivaente a "kgm/s2"
public static boolean esSimboloMudo(char ch) {
	boolean esSimboloMudo = false;
    if (  ch == '*' // producto
       || ch == 'X' // producto
       || ch == 'x' // producto
       || ch == '\u00B7' // producto
       || ch == '.' // producto
       || ch == '^' // exponente
       || ch == '(' // parentesis
       || ch == ')' // parentesis
       ) {
    	esSimboloMudo = true;
    }
    return esSimboloMudo;
}

//Para implificar la notacion en las unidades hciendo "kg*m/s^2" equivaente a "kgm/s2"
public static boolean esDelimitadorUnidades(char ch) {
	boolean esDelimitador = false;
	esDelimitador = Strings.esBlanco(ch)
	              || Strings.esSignoPuntuacion(ch)
	              || ch == '(' // parentesis
                  || ch == ')' // parentesis
                   ;
	return esDelimitador;
}


///////////////////////////////////////////////////////////////////////////
// Funciones estaticas auxiliares para crear Expresiones Regulares  ///////
///////////////////////////////////////////////////////////////////////////

public final static int ALL = -1;
public final static int ANY = -2;

public final static int LEFT = -1;
public final static int NONE = 0;
public final static int RIGHT = 1;


/////////////////////
// toPattern  ///////
/////////////////////


/**
 * Elimina los caracteres que no son compatibles con las expresiones regulares Siette 
 * @param in
 * @return
 */
public static String toPattern(String in) {
	   return toPattern(in, false);
}

/**
 * Pone una barra de escape a los caracteres especiales de las expresiones regulares Siette
 * @param in
 * @param escape
 * @return
 */
public static String toPattern(String in, boolean escape) {
	   return toPattern(in, escape, NONE);
}

/**
 * Crea un patron Siete para una frase con varios nombres separador por espacios
 * por ejemplo {{Miguel}de}Cervantes o Aguila{de{cabeza{blanca}}}
 * @param in
 * @param escape
 * @return
 */
public static String toPattern(String in, boolean escape, int patternAlign) {
	  String pattern = ExpresionRegular.eliminarCaracteresEspeciales(in, escape);
	  if (in!=null && !in.trim().equals("")) {
		  if (patternAlign == LEFT) {
			   String[] split = pattern.split(" ");
			   pattern = "";
			   for(int i=0; i<split.length-1; i++) {
				   pattern = "{" + pattern + split[i] + "}"  ;
			   }
			   pattern +=  split[split.length-1] ;
		  } else if (patternAlign == RIGHT) {
			   String[] split = pattern.split(" ");
			   pattern = "";
			   for(int i=split.length-1; i>0; i--) {
				   pattern = "{" + split[i] + pattern + "}"  ;
			   }
			   pattern =  split[0] + pattern ;
		  }
	  }
	  return pattern;
}

/**
 * A partir de un conjunto genera una expresion regular Siette
 * @param att Lista de atributos
 * @return El patron Siette que reconoce una cadena que contenga alguna de estas palabras
 */
public static String toPattern(Set set, int mode) {
	   String pattern = "";
	   if (set!=null && !set.isEmpty()) {
		   ArrayList aList = new ArrayList();
		   for(Iterator i=set.iterator(); i.hasNext(); ) {
			   aList.add(i.next());
		   }
		   pattern = toPattern(aList, mode);
	   }
	   return pattern;
}

public static String toPatternAll(Set set) {
	   return toPattern(set, ALL);
}

public static String toPatternAny(Set set) {
	   return toPattern(set, ANY);
}

public static String toPattern(String[] aString, int mode) {
	   String pattern = "";
	   if (aString!=null && aString.length>0) {
		   // pattern = "(" + aString[0];
		   pattern =  aString[0];
		   for(int i=1; i<aString.length; i++) {
			   pattern += (mode == ALL ? "+" : "|") + aString[i] ;
		   }
		   // pattern += ")";
	   }
	   return pattern;
}

public static String toPatternAll(String[] aString) {
	   return toPattern(aString, ALL);
}

public static String toPatternAny(String[] aString) {
	   return toPattern(aString, ANY);
}

public static String toPattern(List<?> aList, int mode) {
	   String pattern = "";
	   if (aList!=null && aList.size()>0) {
		   String[] aString = new String[aList.size()];
		   for(int i=0; i<aList.size(); i++) {
			   aString[i] = (String) aList.get(i) ;
		   }
		   pattern = toPattern(aString, mode);
	   }
	   return pattern;
}

public static String toPatternAll(List<?> aList) {
	   return toPattern(aList, ALL);
}

public static String toPatternAny(List<?> aList) {
	   return toPattern(aList, ANY);
}

/////////////////////
///  toText  ////////
/////////////////////

/**
 * A partir de un conjunto genera una expresion en lenguaje natural que puede aparecer como un ejemplo
 * @param att Lista de atributos
 * @return El patron Siette que reconoce una cadena que contenga alguna de estas palabras
 */
public static String toText(Set<String> set, int mode, String lang) {
	   String text = "";
	   lang = (lang==null ? "es" : lang); 
	   String AND = (lang.equals("fr") ? " et " : lang.equals("de") ? " und " : lang.equals("en") ? " and " : " y ");
	   String OR = (lang.equals("fr") ? " ou " : lang.equals("de") ? " oder " : lang.equals("en") ? " or " : " o ");
	   if (set!=null && !set.isEmpty()) {
		   Iterator<String> i = set.iterator();
		   text = i.next().toString() ;
		   while (i.hasNext()) {
			   String sig = i.next().toString();
			   text += (i.hasNext() ?  ", " : (mode == ALL ? AND : OR) ) + sig ;
		   }
	   }
	   return text;
}

public static String toTextAll(Set<String> set, String lang) {
	   return toText(set, ALL,lang);
}

public static String toTextAny(Set<String> set, String lang) {
	   return toText(set, ANY, lang);
}

public static String toText(String[] aString, int mode, String lang) {
    HashSet<String> hs = new HashSet<String>();
    for(int i=0; i<aString.length; i++) {
 	   hs.add(aString[i]);
    }
    return toText(hs, mode, lang);
}

public static String toTextAll(String[] aString, String lang) {
	   return toText(aString, ALL, lang);
}

public static String toTextAny(String[] aString, String lang) {
	   return toText(aString, ANY, lang);
}



public static void main(String argv[]) {

    ExpresionRegular p;

/*
    // Prueba PB1
    p = new ExpresionRegular("select (prod_name, prod_price|prod_price, prod_name) {from products}{;}",true,true,true,true);
    System.out.println("PB1 p=" + p + " select prod_name, prod_price >> " + p.pertenece("select prod_name, prod_price"));
    System.out.println("PB1 p=" + p + " select prod_name, prod_price from products >> " + p.pertenece("select prod_name, prod_price from products"));
    System.out.println("PB1 p=" + p + " select prod_price, prod_name from products >> " + p.pertenece("select prod_price, prod_name from products"));
    System.out.println("PB1 p=" + p + " select prod_price, prod_name from products; >> " + p.pertenece("select prod_price, prod_name from products;"));

    // Prueba 0
    p = new ExpresionRegular("patata");
    System.out.println("P0 p=" + p + " potato >> " + p.pertenece("potato"));
    System.out.println("P0 p=" + p + " patata >> " + p.pertenece("patata"));


    // Prueba PM1
    p = new ExpresionRegular("aa+bb",true,true,true,true);
    System.out.println("PM1 p=" + p + " aabb >> " + p.pertenece("aabb"));
    System.out.println("PM1 p=" + p + " bbaa >> " + p.pertenece("bbaa"));
    System.out.println("PM1 p=" + p + " abab >> " + p.pertenece("abab"));
    System.out.println("PM1 p=" + p + " baba >> " + p.pertenece("baba"));


    // Prueba PM2
    p = new ExpresionRegular("(aa+bb)+(cc+dd)",true,true,true,true);
    System.out.println("PM2 p=" + p + " aabbccdd >> " + p.pertenece("aabbccdd"));
    System.out.println("PM2 p=" + p + " bbaaddcc >> " + p.pertenece("bbaaddcc"));
    System.out.println("PM2 p=" + p + " bbaaccdd >> " + p.pertenece("bbaaccdd"));
    System.out.println("PM2 p=" + p + " aabbaabb >> " + p.pertenece("aabbaabb"));
    System.out.println("PM2 p=" + p + " ccddaabb >> " + p.pertenece("ccddaabb"));

    // Prueba PM3
    p = new ExpresionRegular("a+b+c",true,true,true,true);
    System.out.println("PM3 p=" + p + " aaa >> " + p.pertenece("aaa"));
    System.out.println("PM3 p=" + p + " abc >> " + p.pertenece("abc"));
    System.out.println("PM3 p=" + p + " cba >> " + p.pertenece("cba"));
    System.out.println("PM3 p=" + p + " bac >> " + p.pertenece("bac"));
    System.out.println("PM3 p=" + p + " bba >> " + p.pertenece("bba"));

    // Prueba PM4
    p = new ExpresionRegular("alfa+beta|ganma+delta",true,true,true,true);
    System.out.println("PM4 p=" + p + " alfa beta ganma delta >> " + p.pertenece("alfa beta ganma delta"));
    System.out.println("PM4 p=" + p + " beta alfa delta ganma >> " + p.pertenece("beta alfa delta ganma"));
    System.out.println("PM4 p=" + p + " beta alfa ganma delta >> " + p.pertenece("beta alfa ganma delta"));
    System.out.println("PM4 p=" + p + " alfa beta alfa beta >> " + p.pertenece("alfa beta alfa beta"));

    // Prueba PM5
    p = new ExpresionRegular("azul+rojo+{y}+blanco",true,true,true,true);
    System.out.println("PM5 p=" + p + " azul, rojo y blanco >> " + p.pertenece("azul, rojo y blanco"));
    System.out.println("PM5 p=" + p + " azul,blanco y rojo >> " + p.pertenece("azul,blanco y rojo"));
    System.out.println("PM5 p=" + p + " azul, rojo, blanco >> " + p.pertenece("azul, rojo, blanco"));
    System.out.println("PM5 p=" + p + " azul y rojo y blanco >> " + p.pertenece("azul y rojo y blanco "));

    // Prueba 1
    p = new ExpresionRegular("#12#1%");
    System.out.println("P1 p=" + p + " 12.1 >> " + p.pertenece("12.1"));
    System.out.println("P1 p=" + p + " 12.4 >> " + p.pertenece("12.4"));
    // Prueba 2
    p = new ExpresionRegular("#-12#1%");
    System.out.println("P2 p=" + p + " -12.1 >> " + p.pertenece("-12.1"));
    System.out.println("P2 p=" + p + " -12.4 >> " + p.pertenece("-12.4"));
    // Prueba 3
    p = new ExpresionRegular("#-12,5E2#1%");
    System.out.println("P3 p=" + p + " -1250 >> " + p.pertenece("-1250"));
    System.out.println("P3 p=" + p + " -1,25E3 >> " + p.pertenece("-1,25E3"));
    System.out.println("P3 p=" + p + " -1,25*10^3 >> " + p.pertenece("-1,25*10^3"));
    System.out.println("P3 p=" + p + " -1,25 *10 ^3 >> " + p.pertenece("-1,25 *10 ^3"));
    System.out.println("P3 p=" + p + " -1,24.10 3 >> " + p.pertenece("-1,25.10 3"));
    // Prueba 4
    p = new ExpresionRegular("#10 km/h#1%");
    System.out.println("P4 p=" + p + " 10 km/h >> " + p.pertenece("10 km/h"));
    System.out.println("P4 p=" + p + " 2,77 m/s >> " + p.pertenece("2,77 m/s"));
    System.out.println("P4 p=" + p + " 2,77 km/h >> " + p.pertenece("2,77 km/h"));
    System.out.println("P4 p=" + p + " 1 x 10^1 km/h >> " + p.pertenece(" 1 x 10^1 km/h"));
    System.out.println("P4 p=" + p + " 1 x 10^1 km/h. >> " + p.pertenece(" 1 x 10^1 km/h."));
    System.out.println("P4 p=" + p + " 1 x 10 km/h. >> " + p.pertenece(" 1 x 10 km/h."));
    System.out.println("P4 p=" + p + " 2 x 10 km/h. >> " + p.pertenece(" 2 x 10 km/h."));

    // Prueba 5
    p = new ExpresionRegular("#300000 km/s#1% en el vacio",true,true,true,true);
    System.out.println("P5 p=" + p + "3x10^8 m/s en el vacio >> " + p.pertenece("3x10^8 m/s en el vacio"));
    System.out.println("P5 p=" + p + "3x10^8 m/s  >> " + p.pertenece("3x10^8 m/s"));

    // Prueba 6
    p = new ExpresionRegular("#3E5 km/s#1% en el vacio",true,true,true,true);
    System.out.println("P6 p=" + p + "300000 km/s en el vacio >> " + p.pertenece("300000 km/s en el vacio"));
    System.out.println("P6 p=" + p + "300000 km/s  >> " + p.pertenece("300000 km/s"));

    // Prueba 7
    p = new ExpresionRegular("#1.25E-3 m/s#1%",true,true,true,true);
    System.out.println("P7 p=" + p + "1,25x10^-3 >> " + p.pertenece("1,25x10^-3"));
    System.out.println("P7 p=" + p + "12,5x10^-2 >> " + p.pertenece("12,5x10^-2"));

    // Prueba 8
    p = new ExpresionRegular("(2|dos) {tipos}", true, true, true, true);
    System.out.println("P8=" + p + " 2 >> " + p.pertenece("2"));
    System.out.println("P8=" + p + " 2 tipos >> " + p.pertenece("2 tipos"));
    System.out.println("P8=" + p + " dos tipos>> " + p.pertenece("dos tipos"));
    System.out.println("P8r=" + p + " dos tipo>> " + p.pertenece("dos tipos"));
    System.out.println("P8=" + p + " dos x>> " + p.pertenece("dos x"));

    // Prueba 9
    p = new ExpresionRegular("Italia|Francia|Alemania|Belgica", true, true,
            true, true);
    String respuestas[] = { "Italia", "Francia", "Italia", "Alemania" };
    System.out.println("P8=" + p);
    System.out.println("respuesta multiple =");
    int[] eval = p.pertenece(respuestas);
    for (int i = 0; i < eval.length; i++) {
        System.out.println(respuestas[i] + " >> " + eval[i]);
    }

    // Prueba 10
    String[] patrones = { "Italia", "Francia", "Alemania", "Belgica" };
    p = new ExpresionRegular(patrones, true, true, true, true);
    // String respuestas[] = { "Italia","Francia", "Italia", "Alemania" };
    System.out.println("P10=" + p);
    System.out.println("respuesta multiple =");
    eval = p.pertenece(respuestas);
    for (int i = 0; i < eval.length; i++) {
        System.out.println(respuestas[i] + " >> " + eval[i]);
    }

    // Prueba PG1
    // String[] patrones = { "b[rl][aeiou]","p[aeiou]","s[aeiou][dl][aeiou]" };
    // p = new ExpresionRegular(patrones);
    p = new ExpresionRegular( "s[aeiou]{[dl]}{[aeiou]}");
    // p = new ExpresionRegular( "[aeiou]+[ln]");
    System.out.println("PG1=" + p);
    String[] cadenas = p.genera(3);
	System.out.print("{ ");
    for(int j=0; j<cadenas.length; j++) {
    	System.out.print(cadenas[j]+" ");
    }
	System.out.println("}");

*/


}  // main()



}

