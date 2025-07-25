package siette.quimica;

import java.util.HashMap;

import siette.util.Roman;
import siette.util.text.Strings;

public class Ion {
	
	public static HashMap<String, String> nombreComun = new HashMap<String, String>();
	static {
		nombreComun.put("OH",  "hidr&oacute;xido");
		nombreComun.put("CN",  "cianuro");
		nombreComun.put("NH4", "amonio");
	}

	public static HashMap<String, String> commonName = new HashMap<String, String>();
	static {
		commonName.put("OH",  "hydroxide");
		commonName.put("CN",  "cyanide");
		commonName.put("NH4", "ammonium");
	}

	public static HashMap<String, String> patrones = new HashMap<String, String>();
	static {
		patrones.put("arsenuro",   "arsen{i}uro");
		patrones.put("antimonuro", "antimon{i}uro");
		patrones.put("selenuro",   "selen{i}uro");
		patrones.put("selenuro",   "selen{i}uro");
		patrones.put("telururo",   "telur{i}uro");
		patrones.put("telururo",   "telur{i}uro");
		patrones.put("arsenato",   "arsen{i}ato");
		patrones.put("antimonato", "antimon{i}ato");
		patrones.put("pentaoxido", "pent{a}oxido");
		patrones.put("peroxo",     "perox(o|i)");
	}
	
	public static String prefijo(int n) {
		switch(n) {
			case 1: 
				return "mono";
			case 2:
				return "di";
			case 3:
				return "tri";
			case 4:
				return "tetra";
			case 5:
				return "penta";
			case 6:
				return "hexa";
			case 7:
				return "hecta";
			case 8:
				return "octa";
			case 9:
				return "nona";
			case 10:
				return "deca";
			case 11:
				return "undeca";
			case 12:
				return "dodeca";
			case 13:
				return "trideca";
			case 14:
				return "tetradeca";
			case 15:
				return "pentadeca";
			case 16:
				return "hexadeca";
			case 17:
				return "heptadeca";
			case 18:
				return "octadeca";
			case 19:
				return "nonadeca";
			case 20:
				return "icosa";
			case 21:
				return "heneicosa";
			case 22:
				return "docosa";
			case 23:
				return "tricosa";
			case 24:
				return "tetracosa";
			case 25:
				return "pentacosa";
			default:
				return ""+n+"-";
		}
	}
	
	public static int prefijo(String nombre) {
		if (nombre.toLowerCase().startsWith("mon")) {
			return 1;
		} else if (nombre.toLowerCase().startsWith("di")) {
			return 2;
		} else if (nombre.toLowerCase().startsWith("tri")) {
			return 3;
		} else if (nombre.toLowerCase().startsWith("tetra")) {
			return 4;
		} else if (nombre.toLowerCase().startsWith("penta")) {
			return 5;
		} else if (nombre.toLowerCase().startsWith("hexa")) {
			return 6;
		} else if (nombre.toLowerCase().startsWith("hecta")) {
			return 7;
		} else if (nombre.toLowerCase().startsWith("octa")) {
			return 8;
		} else if (nombre.toLowerCase().startsWith("nona")) {
			return 9;
		} else if (nombre.toLowerCase().startsWith("deca")) {
			return 10;
		} else if (nombre.toLowerCase().startsWith("deca")) {
			return 11;
		} else if (nombre.toLowerCase().startsWith("undeca")) {
			return 12;
		} else if (nombre.toLowerCase().startsWith("dodeca")) {
			return 13;
		} else if (nombre.toLowerCase().startsWith("trideca")) {
			return 14;
		} else if (nombre.toLowerCase().startsWith("tetradeca")) {
			return 14;
		} else if (nombre.toLowerCase().startsWith("pentadeca")) {
			return 15;
		} else if (nombre.toLowerCase().startsWith("hexadeca")) {
			return 16;
		} else if (nombre.toLowerCase().startsWith("heptadeca")) {
			return 17;
		} else if (nombre.toLowerCase().startsWith("octadeca")) {
			return 18;
		} else if (nombre.toLowerCase().startsWith("nonadeca")) {
			return 19;
		} else if (nombre.toLowerCase().startsWith("icosa")) {
			return 20;
		} else if (nombre.toLowerCase().startsWith("heneicosa")) {
			return 21;
		} else if (nombre.toLowerCase().startsWith("docosa")) {
			return 22;
		} else if (nombre.toLowerCase().startsWith("tricosa")) {
			return 23;
		} else if (nombre.toLowerCase().startsWith("tetracosa")) {
			return 24;
		} else if (nombre.toLowerCase().startsWith("pentacosa")) {
			return 25;
		} else {
			return 1;
		}
	}
	
	protected int idIdioma = Compuesto.ES;
	public void idIdioma(int idIdioma) {
		this.idIdioma = idIdioma;
		compuesto.idIdioma(idIdioma);
	}
	public int idIdioma() {
		return this.idIdioma;
	}
	
	/* ***** ESPAÑOL ***** */

	public static String nombre(Elemento e, int n) {
		return nombre(e.nombre(),n);
	}
	
	public static String nombre(String nombre, int n) {
		String prefijo = prefijo(n);
		String st = Strings.eliminarAcentos(nombre);
		if (st.startsWith(prefijo.substring(prefijo.length()-1))) {
			prefijo = prefijo.substring(0,prefijo.length()-1);
		}		
		return prefijo + nombre.toLowerCase();
	}

	/* ***** INGLES ***** */
	
	public static String name(Elemento e, int n) {
		return name(e.name(),n);
	}
	
	public static String name(String nombre, int n) {
		return nombre(nombre, n);  // lo mismo en los dos idiomas
	}


		
	public static String patron(String nombre, int nAtomos, boolean multiplesValencias) {
		String patron = "";
		String prefijo = prefijo(nAtomos);
		String resto = "";
		String st = Strings.eliminarAcentos(nombre);
		if (st.startsWith(prefijo.substring(prefijo.length()-1))) {
			resto = "{"+prefijo.substring(prefijo.length()-1)+"}";
			prefijo = prefijo.substring(0,prefijo.length()-1);
		}
		if (multiplesValencias && nAtomos>1) {
			patron = prefijo + resto + nombre;
		} else {
			patron = "{"+ prefijo + resto +"}" + nombre;
		}
		return patron.toLowerCase();
	}

	public static String patron(String nombre) {
		for(String secuencia: patrones.keySet()) {
			nombre = nombre.replaceAll(secuencia, patrones.get(secuencia));
		}
		return nombre;
	}
	
	public static String pattern(String name, int nAtomos, boolean multiplesValencias) {
		String patron = "";
		String prefijo = prefijo(nAtomos);
		String resto = "";
		if (name.startsWith(prefijo.substring(prefijo.length()-1))) {
			resto = "{"+prefijo.substring(prefijo.length()-1)+"}";
			prefijo = prefijo.substring(0,prefijo.length()-1);
		}		
		if (multiplesValencias && nAtomos>1) {
			patron = prefijo + resto + name;
		} else {
			patron = "{"+ prefijo + resto +"}" + name;
		}
		return patron.toLowerCase();
	}

	public static String pattern(String name) {
		// for(String secuencia: patrones.keySet()) {
		//	name = name.replaceAll(secuencia, patrones.get(secuencia));
		// }
		return name;
	}


	protected Compuesto compuesto;
	protected int numeroOxidacion; 
	protected int numeroAtomos;
	
	public Ion() {
		
	}
	
	public Ion(Elemento e, int valencia, int numeroAtomos) {
		compuesto = new CompuestoUnario(e);
		this.numeroOxidacion = valencia;
		this.numeroAtomos = numeroAtomos;
	}
	
	public Ion(Compuesto c, int valencia, int numeroAtomos) {
		compuesto = c;
		this.numeroOxidacion = valencia;
		this.numeroAtomos = numeroAtomos;
	}

	public Object clone(Ion c) {
		Ion obj = new Ion();
		this.compuesto = c.compuesto;
		this.numeroAtomos = c.numeroAtomos;
		this.numeroOxidacion = c.numeroOxidacion;
		return obj;
	}

	public Compuesto getCompuesto() {
		return compuesto;
	}
	
	public void setCompuesto(Compuesto c) {
		compuesto = c;
	}
	
	public void numeroOxidacion(int n) {
		numeroOxidacion = n;
	}

	public int numeroOxidacion() {
		return numeroOxidacion;
	}

	public void numeroAtomos(int n) {
		numeroAtomos = n;
	}

	public int numeroAtomos() {
		return numeroAtomos;
	}

	public static String numeroOxidacionRomano(int n) {
		return ""+new Roman(n);
	}

	public String numeroOxidacionRomano() {
		return ""+new Roman(numeroOxidacion);
	}

	public String toString() {
		String st = "";
		st += compuesto.formula() + " n.oxidacion=" + numeroOxidacion + " n.atomos="+ numeroAtomos;
		return st;
	}

	/////////////////////////////////////////////////
	//////////////   Formula    /////////////////////
	/////////////////////////////////////////////////

	public String nombre() {
		String nombre = "i&oacute;n";
		if (compuesto instanceof CompuestoUnario) {
			Elemento elemento = ((CompuestoUnario)compuesto).getElemento();
			nombre = elemento.nombre();
		} else {
			String s = compuesto.formula();
			nombre += " " + nombreComun.get(s);
		}
		return nombre;
	}

	public Elemento elemento() {
		Elemento elemento = null;
		if (compuesto instanceof CompuestoUnario) {
			elemento =((CompuestoUnario)compuesto).getElemento();
		}
		return elemento;
	}

	public String simbolo() {
		return compuesto.formula();
	}

	public String patronFormula() {
		return Compuesto.patronEscape(formula());
	}

	public String formula() {
		String formula = compuesto.formula()+ (numeroAtomos>1?""+numeroAtomos:"");
		return formula;
	}

	public String formulaHTML() {
		String formulaHTML = compuesto.formulaHTML()+ (numeroAtomos>1?"<sub>"+numeroAtomos+"</sub>":"") ;
		return formulaHTML;
	}

	public static String patronPrefijos() {
		String patron = "mono|mon";
		for(int i=2; i<20; i++) {
			patron += "|" +prefijo(i);
		}
		return "("+ patron +")";
	}

	public static String patronRomanos() {
		String patron = numeroOxidacionRomano(1);
		for(int i=2; i<20; i++) {
			patron += "|" +numeroOxidacionRomano(i);
		}
		return "("+ patron +")";
	}
	

	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////



	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////



}