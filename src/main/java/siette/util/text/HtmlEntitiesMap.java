package siette.util.text;

import java.util.HashMap;

public class HtmlEntitiesMap {
    private static final HashMap<String, Integer> htmlEntitiesInt = new HashMap<>();
    private static final HashMap<Integer, String> htmlIntEntities = new HashMap<>();

    static {
        htmlEntitiesInt.put("&amp;", 38);
        htmlEntitiesInt.put("&lt;", 60);
        htmlEntitiesInt.put("&gt;", 62);
        htmlEntitiesInt.put("&quot;", 34);
        htmlEntitiesInt.put("&apos;", 39);

        htmlEntitiesInt.put("&aacute;", 225);
        htmlEntitiesInt.put("&eacute;", 233);
        htmlEntitiesInt.put("&iacute;", 237);
        htmlEntitiesInt.put("&oacute;", 243);
        htmlEntitiesInt.put("&uacute;", 250);
        htmlEntitiesInt.put("&ntilde;", 241);
        htmlEntitiesInt.put("&Aacute;", 193);
        htmlEntitiesInt.put("&Eacute;", 201);
        htmlEntitiesInt.put("&Iacute;", 205);
        htmlEntitiesInt.put("&Oacute;", 211);
        htmlEntitiesInt.put("&Uacute;", 218);
        htmlEntitiesInt.put("&Ntilde;", 209);
        htmlEntitiesInt.put("&auml;", 228);
        htmlEntitiesInt.put("&euml;", 235);
        htmlEntitiesInt.put("&iuml;", 239);
        htmlEntitiesInt.put("&ouml;", 246);
        htmlEntitiesInt.put("&uuml;", 252);
        htmlEntitiesInt.put("&Auml;", 196);
        htmlEntitiesInt.put("&Euml;", 203);
        htmlEntitiesInt.put("&Iuml;", 207);
        htmlEntitiesInt.put("&Ouml;", 214);
        htmlEntitiesInt.put("&Uuml;", 220);
        htmlEntitiesInt.put("&ccedil;", 231);
        htmlEntitiesInt.put("&Ccedil;", 199);
        htmlEntitiesInt.put("&szlig;", 223);
        htmlEntitiesInt.put("&iquest;", 191);
        htmlEntitiesInt.put("&ldquo;", 8220);
        htmlEntitiesInt.put("&rdquo;", 8221);
        htmlEntitiesInt.put("&lsquo;", 8216);
        htmlEntitiesInt.put("&rsquo;", 8217);
        htmlEntitiesInt.put("&micro;", 181);
        htmlEntitiesInt.put("&pound;", 163);
        htmlEntitiesInt.put("&cent;", 162);
        htmlEntitiesInt.put("&yen;", 165);
        htmlEntitiesInt.put("&euro;", 8364);
        htmlEntitiesInt.put("&copy;", 169);
        htmlEntitiesInt.put("&reg;", 174);
        htmlEntitiesInt.put("&sect;", 167);
        htmlEntitiesInt.put("&para;", 182);
        htmlEntitiesInt.put("&middot;", 183);
        htmlEntitiesInt.put("&laquo;", 171);
        htmlEntitiesInt.put("&raquo;", 187);
        htmlEntitiesInt.put("&deg;", 176);
        htmlEntitiesInt.put("&plusmn;", 177);
        htmlEntitiesInt.put("&times;", 215);
        htmlEntitiesInt.put("&divide;", 247);
        htmlEntitiesInt.put("&sup1;", 185);
        htmlEntitiesInt.put("&sup2;", 178);
        htmlEntitiesInt.put("&sup3;", 179);
        htmlEntitiesInt.put("&frac12;", 189);
        htmlEntitiesInt.put("&frac14;", 188);
        htmlEntitiesInt.put("&frac34;", 190);
        htmlEntitiesInt.put("&forall;", 8704);
        htmlEntitiesInt.put("&exist;", 8707);
        htmlEntitiesInt.put("&empty;", 8709);
        htmlEntitiesInt.put("&nabla;", 8711);
        htmlEntitiesInt.put("&isin;", 8712);
        htmlEntitiesInt.put("&notin;", 8713);
        htmlEntitiesInt.put("&ni;", 8715);
        htmlEntitiesInt.put("&prod;", 8719);
        htmlEntitiesInt.put("&sum;", 8721);
        htmlEntitiesInt.put("&prop;", 8733);
        htmlEntitiesInt.put("&infin;", 8734);
        htmlEntitiesInt.put("&and;", 8743);
        htmlEntitiesInt.put("&or;", 8744);
        htmlEntitiesInt.put("&cap;", 8745);
        htmlEntitiesInt.put("&cup;", 8746);
        htmlEntitiesInt.put("&cong;", 8773);
        htmlEntitiesInt.put("&asymp;", 8776);
        htmlEntitiesInt.put("&ne;", 8800);
        htmlEntitiesInt.put("&equiv;", 8801);
        htmlEntitiesInt.put("&sub;", 8834);
        htmlEntitiesInt.put("&sup;", 8835);
        htmlEntitiesInt.put("&subseteq;", 8838);
        htmlEntitiesInt.put("&sube;", 8838); // duplicado
        htmlEntitiesInt.put("&supseteq;", 8839);
        htmlEntitiesInt.put("&supe;", 8839); // duplicado
        htmlEntitiesInt.put("&nsubseteq;", 8836);
        htmlEntitiesInt.put("&nsupseteq;", 8837);
        
        // Letras griegas mayúsculas y minúsculas
        String[] greekLetters = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota", "Kappa", "Lambda", "Mu", "Nu", "Xi", "Omicron", "Pi", "Rho", "Sigma", "Tau", "Upsilon", "Phi", "Chi", "Psi", "Omega"};
        for (int i = 0; i < greekLetters.length; i++) {
            htmlEntitiesInt.put("&" + greekLetters[i].toLowerCase() + ";", 945 + i);
            htmlEntitiesInt.put("&" + greekLetters[i] + ";", 913 + i);
        }
        
        for(String entity: htmlEntitiesInt.keySet()) {
        	htmlIntEntities.put(htmlEntitiesInt.get(entity), entity);
        }
    }
    
    public static Integer getInt(String entity) {
        return htmlEntitiesInt.get(entity);
    }
    
    public static String getEntity(Integer n) {
        return htmlIntEntities.get(n);
    }

    
}
