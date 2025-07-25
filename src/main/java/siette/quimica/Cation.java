package siette.quimica;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import siette.util.text.Strings;

public class Cation extends Ion {
	
	public static String raiz(String nombre) {
			// String ultimaLetra = nombre.substring(nombre.length()-1);
			Pattern patronConsonante = Pattern.compile("[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]");
			Matcher matcher = patronConsonante.matcher(nombre);
			int pos = 0;
	        while (matcher.find()) {
	            pos = matcher.start();
	        }
			nombre = nombre.substring(0,pos+1);
			// quitar acentos
			nombre = Strings.eliminarAcentos(nombre);
			return nombre;
	}
	
	public static String root(String name) {
		// String ultimaLetra = nombre.substring(nombre.length()-1);
		if (name.endsWith("y")) {
			name = name.substring(0,name.length()-1);
		} else if (name.endsWith("ium") || name.endsWith("ine")) {
			name = name.substring(0,name.length()-3);
		}
		return name.toLowerCase();
}

	public static String raiz(Elemento e) {
		switch(e.simbolo()) {
			case "S": 
				return "sulfur";
			case "N": 
				return "nitr";
			case "As": 
				return "arsen";
			case "Mn": 
				return "mangan";
			case "Au": 
				return "aur";
			case "Ag": 
				return "argent";
			case "Pb": 
				return "plumb";
			case "Fe": 
				return "ferr";
			case "Cu": 
				return "cupr";
			case "B": 
				return "bor";
			default:
				String nombre = e.nombre();
				return raiz(nombre);
		}
	}

	public static String root(Elemento e) {
		switch(e.simbolo()) {
			case "S": 
				return "sulfur";
			case "N": 
				return "nitr";
			case "As": 
				return "arsen";
			case "Mn": 
				return "mangan";
			case "Au": 
				return "aur";
			case "Ag": 
				return "argent";
			case "Pb": 
				return "plumb";
			case "Fe": 
				return "ferr";
			case "Cu": 
				return "cupr";
			case "P": 
				return "phosphor";
			case "Si": 
				return "silic";
			default:
				String name = e.name();
				return root(name);
		}
	}


	public static String acentuarRaiz(String nombre) {
		// quitar acentos
		nombre = Strings.eliminarAcentos(nombre);
		// poner acento en la última vocal
		for(int k : new int[] {2,3}) {
			int pos = nombre.length()-k; // prueba la penultma y la anterior
			for(char x : new char[] {'a','e','i','o','u'} ) {
				if ( x == nombre.charAt(pos)) {
					nombre = nombre.substring(0,pos) + "&"+x+"acute;" + nombre.substring(pos+1);
					return Strings.filterHTMLtoTXT(nombre); // solo pone el acento una vez
				}
			}
		}
		return Strings.filterHTMLtoTXT(nombre);
	}
	
	public static String prefijo(int valencia, Set<Integer> valencias, int idIdioma) {
		if (valencias.size() > 2) {
			switch(valencia) {
				case 1: 
				case 2: 
					return (idIdioma==Compuesto.EN ? "hypo" : "hipo");
				case 7:
					return "per";
				default:
					return "";
			}
		} else {
			return "";
		}
	}

	public static String sufijo(int valencia, Set<Integer> valencias, int idIdioma) {
		if (valencias.size() > 2) {
			switch(valencia) {
			case 1: 
			case 2: 
			case 3: 
			case 4: 
				return (idIdioma==Compuesto.EN ? "ous" : "oso");
			default:
				return (idIdioma==Compuesto.EN ? "ic" : "ico");
			}
		} else if (valencias.size() == 2) {
			switch(valencia) {
			case 1: 
			case 2: 
				return (idIdioma==Compuesto.EN ? "ous" : "oso");
			default:
				return (idIdioma==Compuesto.EN ? "ic" : "ico");
			}
		} else {
			return (idIdioma==Compuesto.EN ? "ic" : "ico");
		}

	}

	public static String prefijoSufijo(Elemento elemento, int valencia, int idIdioma) {
		String prefijo = prefijo(valencia, elemento.valenciasPositivas(), idIdioma);
		String sufijo = sufijo(valencia, elemento.valenciasPositivas(), idIdioma);
		String raiz = (idIdioma==Compuesto.EN ? root(elemento).toLowerCase() : raiz(elemento).toLowerCase() );
		if ("ico".equals(sufijo)) {
			raiz = acentuarRaiz(raiz);
		}
		String nombre = prefijo+raiz+sufijo;
		return nombre;
	}

	
	public Cation(Elemento e, int carga, int nAtomos) {
		super(e,carga,nAtomos);
	}

	public Cation(Anion a) {
		clone(a);
	}

	public Cation(Cation c) {
		clone(c);
	}

	public Cation() {
		super();
	}

	
	public Object clone() {
		Cation obj = new Cation();
		obj.clone(this);
		return obj;
	}

	public Cation setNumeroAtomos(int n) {
		numeroAtomos = n;
		return this;
	}

	
	public Set<Integer> numerosOxidacionPositivos() {
		Set<Integer> numerosOxidacionPositivos = new HashSet<Integer>();
		if (compuesto instanceof CompuestoUnario) {
			Elemento e = ((CompuestoUnario) compuesto).getElemento();
			numerosOxidacionPositivos = e.numerosOxidacionPositivos();
		}
		return numerosOxidacionPositivos;
	}

	
	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////
	
	public String nomenclaturaSistematica() {
		return (idIdioma == Compuesto.EN ? "" : "de ") 
				+ (numeroAtomos>1?prefijo(numeroAtomos):"") 
				+ compuesto.nomenclaturaSistematica().toLowerCase();
	}

	public String nomenclaturaSistematica(boolean multiplesValencias) {
		return (idIdioma == Compuesto.EN ? "" : "de ") 
				+ (multiplesValencias || numeroAtomos>1?prefijo(numeroAtomos):"") 
				+ compuesto.nomenclaturaSistematica().toLowerCase();
	}

	public String nomenclaturaStock() {
		String nombre = compuesto.nomenclaturaStock().toLowerCase();
		if (compuesto instanceof CompuestoUnario) {
			Elemento e = ((CompuestoUnario) compuesto).getElemento();
			Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
			if (idIdioma == Compuesto.EN) {
				nombre = TablaPeriodica.cap(nombre);
			}
			if (numerosOxidacion.size()>1) {
				nombre += "(" + numeroOxidacionRomano() + ")";
			}
		}
		return (idIdioma == Compuesto.EN ? "" : "de ") + nombre;
	}

	public String nomenclaturaDeAdicion() {
		String nombre = compuesto.nomenclaturaStock() +"(" + numeroOxidacion + "+" + ")";
		return (idIdioma == Compuesto.EN ? "" : "de ") + nombre;
	}

	public String nomenclaturaTradicional() {
		String nombre = compuesto.nomenclaturaSistematica().toLowerCase();
		return (idIdioma == Compuesto.EN ? "" : "de ") + nombre;
	}

	public String nomenclaturaClasica() {
		String nombre = compuesto.nomenclaturaSistematica().toLowerCase();
		if (compuesto instanceof CompuestoUnario) {
			Elemento e = ((CompuestoUnario) compuesto).getElemento();
			nombre = prefijoSufijo(e,numeroOxidacion, idIdioma);
		}
		return nombre;
	}


	
	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	
	public String patronSistematica(boolean multiplesValencias) {
		String patron = (idIdioma == Compuesto.EN ? "" : "de ");
		if (compuesto instanceof CompuestoUnario) {
			if (idIdioma == Compuesto.EN) {
				patron += pattern(compuesto.patronSistematica().toLowerCase(), numeroAtomos, multiplesValencias);
			} else {
				patron += patron(compuesto.patronSistematica().toLowerCase(), numeroAtomos, multiplesValencias);
			}
		} else {
			patron += compuesto.patronSistematica().toLowerCase();
		}
		return patron;
	}

	public String patronStock() {
		String nombre = compuesto.patronStock().toLowerCase();
		if (compuesto instanceof CompuestoUnario) {
			Elemento e = ((CompuestoUnario) compuesto).getElemento();
			Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
			if (numerosOxidacion.size()>1) {
				nombre += "\\(" + numeroOxidacionRomano() + "\\)";
			//} else {
			//	nombre += "{\\(" + numeroOxidacionRomano() + "\\)}";
			}
		}
		return (idIdioma == Compuesto.EN ? "" : "de ") + nombre;
	}

	public String errorNumeroOxidacionPatronStock() {
		String nombre = compuesto.patronStock().toLowerCase();
		if (compuesto instanceof CompuestoUnario) {
			Elemento e = ((CompuestoUnario) compuesto).getElemento();
			Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
			if (numerosOxidacion.size()==1) {
				nombre += "\\(" + numeroOxidacionRomano() + "\\)";
			} else if (e.simbolo().equals("O")){
				//Excepcion del oxigeno
				nombre += "\\(II\\)";
			}

		}
		return " " + nombre;
	}

	
	public String patronClasica() {
		return nomenclaturaClasica();
	}
	
	
	
	
}