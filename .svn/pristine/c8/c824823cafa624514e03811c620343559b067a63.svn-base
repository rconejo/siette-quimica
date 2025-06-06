package siette.quimica;

import java.util.HashMap;

import siette.util.text.Strings;


public class Anion extends Ion {
	
	public static HashMap<String, String> nombreComun = new HashMap<String, String>();
	static {
		nombreComun.put("H",   "hidruro");
		nombreComun.put("O",   Strings.filterHTMLtoTXT("&oacute;xido") );
		
		nombreComun.put("OH",  Strings.filterHTMLtoTXT("hidr&oacute;xido") );
		nombreComun.put("CN",  "cianuro");
		
		nombreComun.put("F",   "fluoruro");
		nombreComun.put("Cl",  "cloruro");
		nombreComun.put("Br",  "bromuro");
		nombreComun.put("I",   "yoduro");
		nombreComun.put("Te",  "telururo");
		nombreComun.put("Se",  "selenuro");
		nombreComun.put("S",   "sulfuro");
		nombreComun.put("P",   "fosfuro");
		nombreComun.put("C",   "carburo");
		nombreComun.put("At",  "astaturo");
		nombreComun.put("N",   "nitruro");
		nombreComun.put("P",   "fosfuro");
		nombreComun.put("As",  "arsenuro");
		nombreComun.put("B",   "boruro");
		nombreComun.put("Si",  "siliciuro");
	}
		
	public static HashMap<String, String> commonName = new HashMap<String, String>();
	static {
		commonName.put("H",   "hydride");
		commonName.put("O",   Strings.filterHTMLtoTXT("oxide") );
		
		commonName.put("OH",  Strings.filterHTMLtoTXT("hydroxide") );
		commonName.put("CN",  "cyanide");
		
		commonName.put("F",   "fluoride");
		commonName.put("Cl",  "chloride");
		commonName.put("Br",  "bromide");
		commonName.put("I",   "iodide");
		commonName.put("Te",  "telluride");
		commonName.put("Se",  "selenide");
		commonName.put("S",   "sulfide");
		commonName.put("P",   "phosphide");
		commonName.put("C",   "carbide");
		commonName.put("At",  "astatide");
		commonName.put("N",   "nitride");
		commonName.put("P",   "phosphide");
		commonName.put("As",  "arsenide");
		commonName.put("B",   "boride");
		commonName.put("Si",  "silicide");
	}
		

	public Anion() {
		super();
	}

	public Anion(Compuesto c, int carga, int nAtomos) {
		super(c,carga,nAtomos);
	}

	public Anion(Elemento e, int carga, int nAtomos) {
		super(e,carga,nAtomos);
	}

	
	public Anion setNumeroAtomos(int n) {
		numeroAtomos = n;
		return this;
	}

	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////
	
	/* **** ESPAÑOL **** */
	
	public String nomenclaturaSistematica() {
		return nomenclaturaSistematica(false);
	}
	public String nomenclaturaSistematica(boolean multiplesValencias) {
		String s = compuesto.formula();
		String nombre;
		String sufijo;
		if (idIdioma == Compuesto.EN) {
			nombre = commonName.get(s); 
			sufijo = "ide";
		} else {
			nombre = nombreComun.get(s);
			sufijo =  "uro";
		}
		if (nombre == null) {
			nombre = compuesto.nomenclaturaSistematica()+sufijo;
		}
		if (multiplesValencias || numeroAtomos>1 ) { // Se fuerza el prefijo en todos los casos de multiples valencias que no sea "mono"
			nombre = (idIdioma==Compuesto.EN ? name(nombre,numeroAtomos) : nombre(nombre,numeroAtomos));
		}
		nombre = TablaPeriodica.cap(nombre);
		return nombre;
	}
	
	public String nomenclaturaStock() {
		String s = compuesto.formula();
		String nombre = (idIdioma == Compuesto.EN ? commonName.get(s) : nombreComun.get(s));
		return nombre;
	}

	public String nomenclaturaTradicional() {
		return nomenclaturaClasica();
	}

	public String nomenclaturaClasica() {
		String s = compuesto.formula();
		String nombre = (idIdioma == Compuesto.EN ? commonName.get(s) : nombreComun.get(s));
		return nombre;
	}

	
	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////

	public String patronRaiz() {
		String formula = compuesto.formula();
		String nombre = nombreComun.get(formula);
		String patron = compuesto.nombre();
		String sufijo;
		if (idIdioma == Compuesto.EN) {
			sufijo = "ide";
		} else {
			sufijo =  "uro";
		}
		if (nombre!=null) {
			patron = (idIdioma==Compuesto.EN ? pattern(nombre) : patron(nombre));
			if (patron.endsWith(sufijo) || patron.endsWith("ato")) {
				patron = patron.substring(0,patron.length()-3);
			}
		}
		return patron;
	}
	

	public String patronSistematica(boolean multiplesValencias) {
		String s = compuesto.formula();
		String nombre, sufijo;
		if (idIdioma == Compuesto.EN) {
			nombre = commonName.get(s); 
			sufijo = "ide";
		} else {
			nombre = nombreComun.get(s);
			sufijo =  "uro";
		}
		if (nombre == null) {
			nombre = compuesto.nomenclaturaSistematica()+sufijo;
		}
		nombre = (idIdioma==Compuesto.EN ? pattern(nombre,numeroAtomos,multiplesValencias) : patron(nombre,numeroAtomos,multiplesValencias));
		return (idIdioma==Compuesto.EN ? pattern(nombre) : patron(nombre));
	}

	public String patronStock() {
		return patron(nomenclaturaStock());
	}

}
