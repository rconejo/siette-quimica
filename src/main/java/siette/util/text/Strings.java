package siette.util.text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

	public final static ArrayList<Character> SIGNOS = new ArrayList<Character>();
	static {
		SIGNOS.add(';');
	    SIGNOS.add(':');
	    SIGNOS.add('!');
	    SIGNOS.add('\'');
	    SIGNOS.add((char)161);
	    SIGNOS.add((char)191);
	}

	
	public final static ArrayList<Character> BLANCOS = new ArrayList<Character>();
	static {
		BLANCOS.add(' ');
		BLANCOS.add('\t');
		BLANCOS.add('\n');
		BLANCOS.add('\r');
		BLANCOS.add((char)0);
		BLANCOS.add((char)160);
	}

	public final static ArrayList<Character> ACENTOS = new ArrayList<Character>();
	static {
		ACENTOS.add('\u00E1');  ACENTOS.add('\u00C1');  // A a
		ACENTOS.add('\u00E9');  ACENTOS.add('\u00C9');  // E e
		ACENTOS.add('\u00ED');  ACENTOS.add('\u00CD');  // I i
		ACENTOS.add('\u00F3');  ACENTOS.add('\u00D3');  // O o
		ACENTOS.add('\u00FA');  ACENTOS.add('\u00DA');  // U u
		ACENTOS.add('\u00FC');  ACENTOS.add('\u00DC');  // U u  (dieresis)
		ACENTOS.add('\u00F1');  ACENTOS.add('\u00D1');  // N n
	}

	
public static boolean esVocal(String in) {
	if (in.length()>1) {
		in = in.substring(0, in.length()-1);
	}
	in = cambiarHTML(in);
	in = eliminarAcentos(in);
	return (	"a".equalsIgnoreCase(in) 
		     || "e".equalsIgnoreCase(in)
		     || "i".equalsIgnoreCase(in)
		     || "o".equalsIgnoreCase(in)
		     || "u".equalsIgnoreCase(in)
	       );
}

	
public static String eliminarAcentos(String in) {
    in = cambiarHTML(in);
    StringBuffer out = new StringBuffer();
    for (int i = 0; i < in.length(); i++) {
        char ch = in.charAt(i);
        // System.out.println("ch ="+ch +" val="+(int)ch);
        if ((int)ch==8730 && i+1<in.length()) {
        	ch = in.charAt(++i);
            // System.out.println("ch ="+ch +" val="+(int)ch);
        	if (  (int)ch==176 || (int)ch==167 || (int)ch==162) {
                out.append('a');
        	} else if ((int)ch==169 || (int)ch==180 || (int)ch==8482  ) {
                out.append('e');
        	} else if ((int)ch==8800 || (int)ch==216 || (int)ch==198) {
                out.append('i');
        	} else if ((int)ch==8805 || (int)ch==8706 || (int)ch==165) {
                out.append('o');
        	} else if ((int)ch==8747 || (int)ch==186 || (int)ch==170) {
                out.append('u');
        	} else if (  (int)ch==197 || (int)ch==209 || (int)ch==199) {
                out.append('A');
        	} else if ((int)ch==226 || (int)ch==227 || (int)ch==228) {
                out.append('E');
        	} else if ((int)ch==231 || (int)ch==232 || (int)ch==233) {
                out.append('I');
        	} else if ((int)ch==236 || (int)ch==241 || (int)ch==238) {
                out.append('O');
        	} else if ((int)ch==246 || (int)ch==250 || (int)ch==245) {
                out.append('U');
        	} else if ((int)ch==177 ) {
                out.append('n');
        	} else if ((int)ch==235 ) {
                out.append('N');
        	} else {
        		 out.append(((char)8730));
        		 out.append(ch);
        	}
        } else if (    (int)ch>=224 && (int)ch<=229) {
            out.append('a');
    	} else if ((int)ch>=192 &&  (int)ch<=197) {
            out.append('A');
		} else if ((int)ch>=232 &&  (int)ch<=235) {
            out.append('e');
		} else if ((int)ch>=200 &&  (int)ch<=203) {
            out.append('E');
		} else if ((int)ch>=236 &&  (int)ch<=239) {
            out.append('i');
		} else if ((int)ch>=205 &&  (int)ch<=207) {
            out.append('I');
		} else if ((int)ch>=242 &&  (int)ch<=246) {
            out.append('o');
		} else if ((int)ch>=211 &&  (int)ch<=214) {
            out.append('O');
		} else if ((int)ch>=249 &&  (int)ch<=252) {
            out.append('u');
		} else if ((int)ch>=217 &&  (int)ch<=220) {
            out.append('U');
		} else if ((int)ch==241) {
            out.append('n');
		} else if ((int)ch==209) {
            out.append('N');
		} else {
            out.append(ch);
        }
    }
    return out.toString();
}


public static String cambiarHTML(String sIn) {
	String sOut = filterHTMLtoTXT(sIn);
	/*
	sOut = cambiarSecuencia(sOut,"&aacute;",new String( ""+(char)225 )) ;
	sOut = cambiarSecuencia(sOut,"&eacute;",new String( ""+(char)233 )) ;
	sOut = cambiarSecuencia(sOut,"&iacute;",new String( ""+(char)237 )) ;
	sOut = cambiarSecuencia(sOut,"&oacute;",new String( ""+(char)243 )) ;
	sOut = cambiarSecuencia(sOut,"&uacute;",new String( ""+(char)250 )) ;
	sOut = cambiarSecuencia(sOut,"&uuml;",  new String( ""+(char)252 )) ;
	sOut = cambiarSecuencia(sOut,"&Aacute;",new String( ""+(char)193 )) ;
	sOut = cambiarSecuencia(sOut,"&Eacute;",new String( ""+(char)201 )) ;
	sOut = cambiarSecuencia(sOut,"&Iacute;",new String( ""+(char)205 )) ;
	sOut = cambiarSecuencia(sOut,"&Oacute;",new String( ""+(char)211 )) ;
	sOut = cambiarSecuencia(sOut,"&Uacute;",new String( ""+(char)218 )) ;
	sOut = cambiarSecuencia(sOut,"&Uuml;",  new String( ""+(char)220 )) ;
	sOut = cambiarSecuencia(sOut,"&ntilde;",new String( ""+(char)241 )) ;
	sOut = cambiarSecuencia(sOut,"&Ntilde;",new String( ""+(char)209 )) ;
	*/
	return sOut;
}


private static String cambiarSecuencia( String sInput, String sAntes, String sDespues) {
     StringBuffer sbResult = new StringBuffer();
     int iStart = 0;
     int iEnd = 0;
     sInput = sInput.trim();
     iEnd = sInput.indexOf( sAntes, iStart );
     while( iEnd != -1 ) {
             sbResult.append( sInput.substring( iStart, iEnd ) );
             sbResult.append( sDespues );
             iStart = iEnd + sAntes.length();
             iEnd = sInput.indexOf( sAntes, iStart );
     }
     if (iStart<sInput.length()) sbResult.append( sInput.substring( iStart ) );
     return sbResult.toString();
}



public static String eliminarSignosPuntuacion(String in) {
    String out = "";
    for (int i = 0; i < in.length(); i++) {
            char ch = in.charAt(i);
            if (esSignoPuntuacion(ch)) {
                ;
             } else {
                 out += ch;
             }
    }
    return out;
}

public static boolean esSeparadorPalabras(char ch) {
    if (  ch == ';'
    	       || ch == ','
    	       || ch == '.'
    	       || ch == ':'
    	       || ch == ';'
    	       || ch == '!'
    	       || ch == '?' 
    	       || ch == ' '
    	       || ch == '\t'
    	       || ch == '\n'
    	       ) {
    	    	return true;
    	    } else {
    	    	return false;
    	    }
}

public static boolean esSignoPuntuacion(char ch) {
    if (  ch == ';'
    	       || ch == ','
    	       || ch == '.'
    	       || ch == ':'
    	       || ch == '!'
    	       || ch == '\''
//    	       || ch == '!'
//    	       || ch == '?' // OJO, puede ser un metasimbolo de las expresiones regulares
//    	       || ch == '¡' || ch == (char)173 // abre exclamacion
//    	       || ch == '¿' || ch == (char)168 // abre interrogacion
    	       || (int)ch == 161
    	       || (int)ch == 191
    	       ) {
    	    	return true;
    	    } else {
    	    	return false;
    	    }
}

public static boolean esSignoOperador(char ch) {
    if (      ch == ';'
	       || ch == ','
	       || ch == '.'
	       || ch == ':'
	       || ch == '\''
	       || ch == '-'
	       || ch == '/'
	       || ch == '!'
	       || ch == '?'
	       || ch == '¡' || ch == (char)173 // abre exclamacion
	       || ch == '¿' || ch == (char)168 // abre interrogacion
	       || ch == '\\'
		   || ch == '+'
		   || ch == '*'
		   || ch == '?'
		   || ch == '#'
		   || ch == '%'
		   || ch == '$'
		   || ch == '|'
		   || ch == '('
		   || ch == ')'
		   || ch == '['
	  	   || ch == ']'
		   || ch == '{'
		   || ch == '}'
		   || ch == '<'
		   || ch == '>'
   ) {
    	return true;
    } else {
    	return false;
    }
}



public static String eliminarBlancos(String in) {
	// (char)0 puede usarse como terminador de cadena,
	// se considera como un espacio en blanco,
	// pero no hay que eliminarlo
    String out = "";
    for (int i = 0; i < in.length(); i++) {
        char ch = in.charAt(i);
        if (esBlanco(ch) && (int)ch != 0 ) {
           ;
        } else {
            out += ch;
        }
    }
    return out;
}



public static String eliminarCaracter(String in, char chExtra) {
    String out = "";
    for (int i = 0; i < in.length(); i++) {
        char ch = in.charAt(i);
        if (ch == chExtra) {
           ;
        } else {
            out += ch;
        }
    }
    return out;
}

public static boolean esBlanco(char ch) {
    if (  ch == ' '
       || ch == '\t'
       || ch == '\n'
       || ch == '\r'
       || ch == (char) 0
       || ch == (char) 160 // NO-BREAK SPACE
       // || ch == (char) 8199 // figure space
       // || ch == (char) 8239 // narrow no-break space
       // || ch == (char) 8288 // word joiner
       // || ch == (char) 65279 // zero width no-break space      
       ) {
    	return true;
    } else {
    	return false;
    }
}

public static boolean esDigito(char ch) {
    if (ch >= '0' && ch<= '9') {
    	return true;
    } else {
    	return false;
    }
}

public static boolean esNumerico(char ch) {
    if (  ch == ' '
       || ch == '+'
       || ch == ','
       || ch == '.'
       || ch == '+'
       || ch == '-'
       || ch == 'E'
       || ch == 'e'
       || (ch >= '0' && ch<= '9')
       ) {
    	return true;
    } else {
    	return false;
    }
}


public static ArrayList<String> generarVariaciones(String in) {
	return generarVariaciones(in, true, true, true, true);
}


public static ArrayList<String> generarVariaciones(String in, boolean mMayusculas, boolean mAcentos, boolean mSignos, boolean mBlancos ) {
	ArrayList<String> variaciones = new ArrayList<String>();
	if (in!=null) {
		if (!in.equals("")) {
			variaciones.addAll(menosUnCaracter(in));
			variaciones.addAll(intercambiaDosCaracteres(in));
			variaciones.addAll(masUnCaracter(in, mMayusculas, mAcentos, mSignos, mBlancos));
			variaciones.addAll(sustituyeUnCaracter(in, mMayusculas, mAcentos, mSignos, mBlancos));
		} else {
			variaciones.add("");
		}
	}
	return variaciones;
}



private static ArrayList<String> menosUnCaracter(String in) {
	ArrayList<String> variaciones = new ArrayList<String>();
	if (in.length()>1) { // si hay un solo caracter no se hace nada
		for(int i=1; i<= in.length(); i++) {
			if (!esDigito(in.charAt(i-1))) {  // Los numeros no se modifican
				variaciones.add(in.substring(0, i-1) + in.substring(i, in.length()));
			}
		}
	}
	return variaciones;
}



private static  ArrayList<String> sustituyeUnCaracter(String in, boolean mMayusculas, boolean mAcentos, boolean mSignos, boolean mBlancos ) {
	ArrayList<String> variaciones = new ArrayList<String>();
	for(char ch='A'; ch<='Z'; ch++) {
		for(int i=1; i<= in.length(); i++) {
			variaciones.add(in.substring(0, i-1) + ch + in.substring(i, in.length()));
		}
	}
	if (!mMayusculas) {
		for(char ch='a'; ch<='z'; ch++) {
			for(int i=1; i<= in.length(); i++) {
				variaciones.add(in.substring(0, i-1) + ch + in.substring(i, in.length()));
			}
		}
	}
	if (!mBlancos) {
		for(Character ch : BLANCOS) {
			for(int i=1; i<= in.length(); i++) {
				variaciones.add(in.substring(0, i-1) + ch + in.substring(i, in.length()));
			}
		}
	}
	if (!mAcentos) {
		for(Character ch : ACENTOS) {
			for(int i=1; i<= in.length(); i++) {
				variaciones.add(in.substring(0, i-1) + ch + in.substring(i, in.length()));
			}
		}
	}
	if (!mSignos) {
		for(Character ch : SIGNOS) {
			for(int i=1; i<= in.length(); i++) {
				variaciones.add(in.substring(0, i-1) + ch + in.substring(i, in.length()));
			}
		}
	}
	return variaciones;
}


private static  ArrayList<String> masUnCaracter(String in, boolean mMayusculas, boolean mAcentos, boolean mSignos, boolean mBlancos ) {
	ArrayList<String> variaciones = new ArrayList<String>();
	for(char ch='A'; ch<='Z'; ch++) {
		for(int i=0; i<= in.length(); i++) {
			variaciones.add(in.substring(0, i) + ch + in.substring(i, in.length()));
		}
	}
	if (!mMayusculas) {
		for(char ch='a'; ch<='z'; ch++) {
			for(int i=0; i<= in.length(); i++) {
				variaciones.add(in.substring(0, i) + ch + in.substring(i, in.length()));
			}
		}
	}
	if (!mBlancos) {
		for(Character ch : BLANCOS) {
			for(int i=0; i<= in.length(); i++) {
				variaciones.add(in.substring(0, i) + ch + in.substring(i, in.length()));
			}
		}
	}
	if (!mAcentos) {
		for(Character ch : ACENTOS) {
			for(int i=0; i<= in.length(); i++) {
				variaciones.add(in.substring(0, i) + ch + in.substring(i, in.length()));
			}
		}
	}
	if (!mSignos) {
		for(Character ch : SIGNOS) {
			for(int i=0; i<= in.length(); i++) {
				variaciones.add(in.substring(0, i) + ch + in.substring(i, in.length()));
			}
		}
	}
	return variaciones;
}


private static ArrayList<String> intercambiaDosCaracteres(String in) {
	ArrayList<String> variaciones = new ArrayList<String>();
	for(int i=1; i< in.length(); i++) {
		if (!esDigito(in.charAt(i-1)) && !!esDigito(in.charAt(i))) { // Los numero no se tocan
			variaciones.add(in.substring(0, i-1) + in.charAt(i) + in.charAt(i-1)  + in.substring(i+1, in.length()));
		}
	}
	return variaciones;
}


/*
public static void main(String[] argv) {
	String inputFileName = null;
	String outputFileName = null;
	String fileContent = new String();
    int i=0;
    while (i<argv.length && argv[i]!=null) {
        if (argv[i].startsWith("-r") ) {
            i++;
        } else if (inputFileName == null) {
        	inputFileName = argv[i];
        	i++;
        } else if (outputFileName == null) {
			outputFileName = argv[i];
			i++;
        } else {
        	i++;
        }
	} 
    if (inputFileName!=null && outputFileName!=null) {
		fileContent = JavaExec.getFileContent(".", inputFileName);
		fileContent = Strings.eliminarAcentos(fileContent);
		JavaExec.setFileContent(".", outputFileName, fileContent);
    } else {
		System.out.println("Strings inputFileName outputFileName");
	}
}
*/

public static String codeToHTML (String code) {
	int LINEWIDTH = 80;
	String html = "";
	// html +="<font size='-1' color='red'>";
	html +="<font size='-2'>";
	html +="<code><pre>";
	String[] lines = code.split("\n");
	for (int i=0; i<lines.length; i++) {
		String line = lines[i];
		while (line.length()>LINEWIDTH) {
			int k = LINEWIDTH;
			while (k>0 && !esSeparadorPalabras(line.charAt(k)) ) {
				k--;
			}
			if (k==0) {
				k = LINEWIDTH; // si no se encuentra por donde cortar...
			}
			html += line.substring(0,k+1) + "\n";
			line = line.substring(k+1);
		} 
		html += line+ "\n";
	}
    html += "</pre></code>";
    html += "</font>";
	return html;
}

public static String serialize (String[] aString) {
	String str = "";
	if (aString.length>1) {
		str = '{' + aString[0];
	    for (int i = 1; i < aString.length; i++) {
	    	str += "," + (aString[i]!=null ? aString[i] : "''") ;
	    }
	    str += '}';
	} else if (aString.length == 1) {
		str = aString[0];
	}
    return str;
}

public static String[] deserialize (String str) {
	String[] aString;
	if (str!=null) {
		str = str.trim();
		if (str.length()>0) {
			if (str.charAt(0) == '{' && str.charAt(str.length()-1) == '}' ) {
				str = str.substring(1, str.length()-1);
			}
		}
		aString = str.split(",");
	} else {
		aString = new String[1];
		aString[0] = "";
	}
    return aString;
}

public static String escape(String str, char ch) {
	str = str.replaceAll(""+ch, "\\"+ch );
	return str;
}

/*
 * Recibe un fichero mas o menos largo en XML
 * <xml>
 *    <src="Uno dos" />
 * </xml>
 * y lo conviente en
 * '<xml>' +
 * '   <src="Uno dos" />' +
 * '</xml>' 
 */
public static String xmlToJavascriptString(String xml) {
	String str = "";
	String[] lineas = xml.split("\n|\r\n|\r");
	for(int i=0; i<lineas.length-1; i++) {
		str += "'" + escape(lineas[i],'\'') + "' +\n";
	}
	str += "'" + escape(lineas[lineas.length-1],'\'') + "'";
	return str;
}

public static String cap(String st) {
	   String capSt = st;
	   if (st!=null && st.length()>0) {
		   String st0 = ""+ st.charAt(0);
		   capSt = st0.toUpperCase() + st.substring(1,st.length()); // .toLowerCase();
	   }
	   return capSt;
}

public static String low(String st) {
	   String lowSt = st;
	   if (st!=null && st.length()>0) {
		   String st0 = ""+ st.charAt(0);
		   lowSt = st0.toLowerCase() + st.substring(1,st.length());
	   }
	   return lowSt;
}

///////////////////////////////////////////////////////////////////////////////
/**
* @author Ricardo Conejo
* Se lama a esta funcion para transformar cadenas de texto que contienen
* caracteres especiales en cadenas de texto en HTML (sustituyendo los caraceteres
* por sus correspondientes cadenas de escape). Si la cadena llega en HTML (con los
* caracteres de escape ya puestos) debe devolver la misma cadena.
* Para otros cambios usar escapeHTMLforHTML()
*/
public static String filterHTML(String sInput) {
	 String sOutput = sInput;
	 sOutput = filterHTMLAbove(sOutput);
	 sOutput = filterHTMLBelow(sOutput);
	 sOutput = filterHTMLExtra(sOutput);
	 return sOutput;
}

///////////////////////////////////////////////////////////////////////////////
/**
* @author Ricardo Conejo
* Filtra los caracteres HTML que estan por encima del 127, de manera que
* no haya problemas al pasarlos atraves de ningun canal de datos.
*
* Otros caracteres como < y > no se modifican
* Vease la funcion filterHTML() para realizar el filtrado completo
*/
public static String filterHTMLAbove(String sInput) {
    StringBuffer sb = new StringBuffer();
	if (sInput!=null) {
	    for (int i = 0; i < sInput.length(); i++) {
	        char ch = sInput.charAt(i);
	        int n = (char) ch;
	        String entity = null;
	        if ( (n > 127) && (n <= 159) ) {
	        	// conejo: chapucilla para salir del paso....
	        	// Compatibilidad con Mac, estos caracteres se interpretan como si fueran del "Mac OS Roman" (charset="x-MacRoman")
	        	// Esta chapucilla hace que se muestren bien la mayoria de los caracteres de los ficheros "Mac OS Roman"
		        if (        (n == 135)) {
		            sb.append("&aacute;");
		        } else if ( (n == 142)) {
		            sb.append("&eacute;");
		        } else if ( (n == 146)) {
		            sb.append("&iacute;");
		        } else if ( (n == 151)) {
		            sb.append("&oacute;");
		        } else if ( (n == 156)) {
		            sb.append("&uacute;");
		            
		        } else if ( (n == 150)) {
		            sb.append("&ntilde;");
		        } else if ( (n == 132)) {
		            sb.append("&Ntilde;");
		        }
	        } else if (n > 127 && (entity = HtmlEntitiesMap.getEntity((int)ch))!=null ) {
	            sb.append(entity); // Se usa una tabla para editarla major y para mayor eficiencia
	        } else  if ( (n > 159) && (n <= 8482) ) {
	            sb.append("&#"+n+";");
	        } else  if ( (n < 32 && ! (n == 9 || n == 10 || n == 13) )) {
	            sb.append("&#"+n+";");
	        } else {
	            sb.append(ch);
	        }
	    }
	}
    return sb.toString();
}

///////////////////////////////////////////////////////////////////////////////
/**
* @author Ricardo Conejo
* Filtra los caracteres HTML que estan por debajo del 127, de manera que
* no haya problemas al pasarlos atraves de ningun canal de datos.
*
* Otros caracteres como < y > no se modifican
* Vease la funcion filterHTML() para realizar el filtrado completo
*/
public static String filterHTMLBelow(String sInput) {
    StringBuffer sb = new StringBuffer();
	if (sInput!=null) {
	    for (int i = 0; i < sInput.length(); i++) {
	        char ch = sInput.charAt(i);

	        // Signos de puntuacion
	        if ( ((int) ch == '<')) {
	            sb.append("&lt;");
	        } else if ( ((int) ch == '>')) {
	            sb.append("&gt;");
	        } else if ( ch == '"' ) {
	            sb.append("&quot;");
	        } else if ( ch == '\'' ) {
	            sb.append("&#39;");
	        } else if ( ch == '&' ) {
	            char chSig = (i+1 < sInput.length()) ? sInput.charAt(i+1) : (char) ' ';
	            if (chSig == ' ') {
	                sb.append("&amp;");
	            } else {
	                sb.append(ch);
	            }
	        } else {
	            sb.append(ch);
	        }
	    }
	}
    return sb.toString();
}

///////////////////////////////////////////////////////////////////////////////
/**
* @author Ricardo Conejo
* Filtra los caracteres HTML que estan por encima del 8192, de manera que
* no haya problemas al pasarlos atraves de ningun canal de datos.
*
* Otros caracteres como < y > no se modifican
* Vease la funcion filterHTML() para realizar el filtrado completo
*/
public static String filterHTMLExtra(String sInput) {
	StringBuffer sb = new StringBuffer();
	if (sInput!=null) {
		for (int i = 0; i < sInput.length(); i++) {
			char ch = sInput.charAt(i);
			if ((int) ch > 8192) {
				sb.append("&#"+((int)ch)+";");
			} else {
				sb.append(ch);
			}
		}
	}
	return sb.toString();
}

public static String filterHTMLEOLN(String sInput) {
    StringBuffer sb = new StringBuffer();
    char ch;
    for (int i = 0; i < sInput.length(); i++) {
        ch = sInput.charAt(i);

        // Fin de linea
        if ( ((int) ch == '\n')) {
            sb.append("<br/>");
        } else if ( ((int) ch == '\r')) {
        	// ignorar
        } else {
            sb.append(ch);
        }
    }
    return sb.toString();
}


///////////////////////////////////////////////////////////////////////////////
/**
* @author Ricardo Conejo
* Se lama a esta funcion para transformar cadenas de texto formateadas por Moodle en HTML
* representando falsamente los caracteres dobles de UTF8, es un apaño para poder importar
* ficheros que genera Moodle si no esta configurado adecuadamente
*/
public static String filterHTMLtoUTF8(String sIn) {
	if (sIn==null || sIn.equals("")) return "";

	String sOut = sIn;

	String st = "";
	for (int i=0; i<sIn.length(); i++) {
		st += " "+ (int)sIn.charAt(i) + sIn.charAt(i);
	}
	
	
	sOut = replaceString(sOut,"<pre><p>","");
	sOut = replaceString(sOut,"</p></pre>","");
	
	// Secuencias desconocidas
	sOut = replaceString(sOut,"&iuml;&raquo;&iquest;","");
	sOut = replaceString(sOut,"&acirc;"+(char)196+(char)236,"\"");
	sOut = replaceString(sOut,"&acirc;"+(char)196+(char)249,"\"");
	sOut = replaceString(sOut,"&acirc;"+(char)196+(char)250,"\"");
	sOut = replaceString(sOut,"&acirc;"+(char)196+"&brvbar;","\"");
	sOut = replaceString(sOut,"&Acirc;&shy;","");
	sOut = replaceString(sOut,"&Acirc;&not;","");
	sOut = replaceString(sOut,"&Acirc;&raquo;","\"");
	sOut = replaceString(sOut,"&Acirc;&laquo;","\"");
	sOut = replaceString(sOut,"&Acirc;&nbsp;"," ");
	sOut = replaceString(sOut,"&Acirc;&ordf;",""+(char)176);
	
	// Letras
	sOut = replaceString(sOut,"&Atilde;&iexcl;",""+(char)225);
	sOut = replaceString(sOut,"&Atilde;&copy;",""+(char)233);
	sOut = replaceString(sOut,"&Atilde;&shy;",""+(char)237);
	sOut = replaceString(sOut,"&Atilde;&sup3;",""+(char)243);
	sOut = replaceString(sOut,"&Atilde;&ordm;",""+(char)250);

	sOut = replaceString(sOut,"&Atilde;"+(char)197,""+(char)193);
	sOut = replaceString(sOut,"&Atilde;"+(char)226,""+(char)201);
	sOut = replaceString(sOut,"&Atilde;"+(char)231,""+(char)205);
	sOut = replaceString(sOut,"&Atilde;"+(char)236,""+(char)211);
	sOut = replaceString(sOut,"&Atilde;"+(char)246,""+(char)218);

	sOut = replaceString(sOut,"&Atilde;&curren;"  ,""+(char)228);
	sOut = replaceString(sOut,"&Atilde;&laquo;"  ,""+(char)235);
	sOut = replaceString(sOut,"&Atilde;&macr;"  ,""+(char)239);
	sOut = replaceString(sOut,"&Atilde;&para;"  ,""+(char)246);
	sOut = replaceString(sOut,"&Atilde;&frac14;"  ,""+(char)252);

	sOut = replaceString(sOut,"&Atilde;"+(char)209  ,""+(char)196);
    sOut = replaceString(sOut,"&Atilde;"+(char)227  ,""+(char)203);
    sOut = replaceString(sOut,"&Atilde;"+(char)232  ,""+(char)207);
    sOut = replaceString(sOut,"&Atilde;"+(char)241  ,""+(char)214);
    sOut = replaceString(sOut,"&Atilde;"+(char)250  ,""+(char)220);

	sOut = replaceString(sOut,"&Atilde;&plusmn;",""+(char)241);
	sOut = replaceString(sOut,"&Atilde;"+(char)235,""+(char)209);

	sOut = replaceString(sOut,"&Atilde;&sect;",""+(char)231);
	sOut = replaceString(sOut,"&Atilde;"+(char)225,""+(char)199);

	// Signos de puntuacion
	sOut = replaceString(sOut,"&Acirc;&iquest;",""+(char)191);
	sOut = replaceString(sOut,"&Acirc;&iexcl;",""+(char)161);
	  
	return sOut;
}

///////////////////////////////////////////////////////////////////////////////
/**
* @author Ricardo Conejo
*/
public static String filterHTMLtoTXT(String sIn) {
	if (sIn==null || sIn.equals("")) return "";

	String sOut = sIn;
	
	// Reemplazar &aacute; por ""+(char)225
	Pattern patt = Pattern.compile("&[A-Za-z]+;");
	Matcher m = patt.matcher(sOut);
	StringBuffer sb = new StringBuffer(sOut.length());
	while (m.find()) {
	    String entity = m.group();
	    Integer n = HtmlEntitiesMap.getInt(entity);
	    if (n!=null) {
		    String ch = ""+ (char) (int) n;
		    m.appendReplacement(sb, Matcher.quoteReplacement(ch));
	    } else {
	    	m.appendReplacement(sb, entity); // no reemplazar, porque no se ha encontrado
	    }
	}
	m.appendTail(sb);
	sOut = sb.toString();

	// Reemplazar &#123 por ""+(char)123
	patt = Pattern.compile("(&#)([0-9]+)(;)");
	m = patt.matcher(sOut);
	sb = new StringBuffer(sOut.length());
	while (m.find()) {
	    String text = m.group(2);
	    String ch = ""+ (char) Integer.parseInt(text);
	    m.appendReplacement(sb, Matcher.quoteReplacement(ch));
	}
	m.appendTail(sb);
	sOut = sb.toString();
	  
	return sOut;
}

///////////////////////////////////////////////////////////////////////////////
/**
* @author Ricardo Conejo
*/
public static String replaceString(String sInput, String sString, String sNewString) {
	return replaceString(sInput, sString, sNewString, true, false, false);
}

public static String replaceString(String sInput, String sString, String sNewString, boolean trim, boolean mayusculas, boolean acentos) {
	// ASSERT 
	if (sString==null || sString.equals("")) {
		return sInput;
	}
	
	 sInput = trim ? sInput.trim() : sInput;
	 String sInputModif = sInput;
	 String sStringModif = sString;
	 if (mayusculas) {
	 		sInputModif = sInput.toUpperCase();
	 		sStringModif = sString.toUpperCase();
	 }
	 if (acentos) {
			sInputModif = Strings.eliminarAcentos(sInput);
			sStringModif = Strings.eliminarAcentos(sString);
	 }
	 
	 StringBuffer sbResult = new StringBuffer();
	 int iStart = 0;
	 int iEnd = sInput.indexOf(sString, iStart);
	 while (iEnd != -1) {
	     String line = sInput.substring(iStart, iEnd);
	     sbResult.append(line);
	     sbResult.append(sNewString);
	     iStart = iEnd + sString.length();
	     iEnd = sInputModif.indexOf(sStringModif, iStart);
	 }
	 if (iStart < sInput.length())
	     sbResult.append(sInput.substring(iStart));
	 return sbResult.toString();
}


}