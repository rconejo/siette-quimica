package siette.quimica;

import java.util.HashMap;

import siette.util.Random;
import siette.util.text.Strings;

public class Oxoanion extends Anion {
	
	public static HashMap<String, String> nombreComunIUPAC = new HashMap<String, String>();
	public static HashMap<String, String> nombreComun = new HashMap<String, String>();
	static {
		
		nombreComun.put     ("HCO3",    "bicarbonato");
		
		// Manganeso
		// nombreComunIUPAC.put("MnO4",    "permanganato");
		nombreComunIUPAC.put("Cr2O7",   "dicromato");
		
		// Cromo
		nombreComunIUPAC.put("CrO4",    "cromato");
/*

        // Boro
		nombreComunIUPAC.put("H2BO3",   "dihidrogenoborato");
		nombreComunIUPAC.put("HBO3",    "hidrogenoborato");
		nombreComunIUPAC.put("BO3",     "borato");
        
        // Carbono
		nombreComunIUPAC.put("HCO3",    "hidrogenocarbonato");
		nombreComunIUPAC.put("CO3",     "carbonato");
        
 
 		// Nitrogeno
		nombreComunIUPAC.put("NO3",     "nitrato");
		nombreComunIUPAC.put("NO2",     "nitrito");
		
		// Fosforo
		nombreComunIUPAC.put("H2PO4",   "dihidrogenofosfato");
		nombreComunIUPAC.put("HPO4",    "hidrogenofosfato");
		nombreComunIUPAC.put("PO4",     "fosfato");
		nombreComunIUPAC.put("H2PO3",   "dihidrogenofosfito");
		nombreComunIUPAC.put("HPO3",    "hidrogenofosfito");
		nombreComunIUPAC.put("PO3",     "fosfito");
		nombreComunIUPAC.put("PHO2(OH)","hidrogenofosfonato");
		nombreComunIUPAC.put("PHO3",    "fosfonato");
		
		// Azufre
		nombreComunIUPAC.put("HSO4",    "hidrogenosulfato");
		nombreComunIUPAC.put("SO4",     "sulfato");
		nombreComunIUPAC.put("HSO3",    "hidrogenosulfito");
		nombreComunIUPAC.put("SO3",     "sulfito");
		
		// Cloro
		nombreComunIUPAC.put("ClO4",    "perclorato");
		nombreComunIUPAC.put("ClO3",    "clorato");
		nombreComunIUPAC.put("ClO2",    "clorito");
		nombreComunIUPAC.put("ClO",     "hipoclorito");
*/	
	}
		
	public static HashMap<String, String> commonNameIUPAC = new HashMap<String, String>();
	public static HashMap<String, String> commonName = new HashMap<String, String>();
	static {
		commonName.put     ("HCO3",    "bicarbonate");
		
		// Manganeso
		// commonNameIUPAC.put("MnO4",    "permanganate");
		
		// Cromo
		commonNameIUPAC.put("Cr2O7",   "dichromate");
		commonNameIUPAC.put("CrO4",    "chromate");
	}

	
    public static String[] POLIHIDRATADOS = { "meta", "orto", "di" };
    public static String traduce(String es) {
    	if (es == "orto") return "ortho";
    	if (es == "piro") return "pyro";
    	return es;
    }
	
	public static String prefijo(int n) {
		switch(n) {
			case 1: 
				return "";
			case 2:
				return "bis";
			case 3:
				return "tris";
			case 4:
				return "tetrakis";
			case 5:
				return "pentakis";
			case 6:
				return "hexakis";
			case 7:
				return "hectakis";
			case 8:
				return "octakis";
			case 9:
				return "nonakis";
			case 10:
				return "decakis";
			default:
				return ""+n+"-";
		}
	}

	public static int prefijo(String nombre) {
		if (nombre.toLowerCase().startsWith("")) {
			return 1;
		} else if (nombre.toLowerCase().startsWith("bis")) {
			return 2;
		} else if (nombre.toLowerCase().startsWith("tris")) {
			return 3;
		} else if (nombre.toLowerCase().startsWith("tetrakis")) {
			return 4;
		} else if (nombre.toLowerCase().startsWith("pentakis")) {
			return 5;
		} else if (nombre.toLowerCase().startsWith("hexakis")) {
			return 6;
		} else if (nombre.toLowerCase().startsWith("hectakis")) {
			return 7;
		} else if (nombre.toLowerCase().startsWith("octakis")) {
			return 8;
		} else if (nombre.toLowerCase().startsWith("nonakis")) {
			return 9;
		} else if (nombre.toLowerCase().startsWith("decakis")) {
			return 10;
		} else {
			return 1;
		}
	}

	/////////////////////////////////////////////////
	//////////////   Constructores    ///////////////
	/////////////////////////////////////////////////
	
	public Oxoanion(Oxoacido acido, int valenciaNegativa) {
		super();
		int n = acido.nHidrogeno();
		numeroOxidacion = valenciaNegativa;
		acido.nHidrogeno(n-numeroOxidacion);
		setCompuesto(acido); 
	}

	protected Oxoanion() { 
		super(); 
	}
		
	public static Oxoanion random(TablaPeriodica tabla) {
		Oxoacido acido = Oxoacido.random(tabla);
		int n = acido.nHidrogeno();
		int valenciaNegativa = tabla.nextInt(1,n);
		return new Oxoanion(acido, valenciaNegativa);
	}
	
	
	public String nombre() {
		String ATO = (idIdioma==Compuesto.EN ? "ate" : "ato");
		String ITO = (idIdioma==Compuesto.EN ? "ite" : "ito");
		String ICO = (idIdioma==Compuesto.EN ? "ic" : "ico");
		String OSO = (idIdioma==Compuesto.EN ? "ous" : "oso");
		String nombre = super.nombre();
		try {
			Oxoacido acido = ((Oxoacido)compuesto);
			
			acido.nHidrogeno(acido.nHidrogeno()+numeroOxidacion);  // formar el acido otra vez
			nombre = acido.nomenclaturaClasica();
			if (idIdioma == Compuesto.EN) {
				nombre = nombre.substring(0, nombre.length()-5);  // Quitar la palabra " acid"
			} else {
				nombre = nombre.substring(6); // Quitar la palabra "Acido "
				nombre = Strings.eliminarAcentos(nombre);
			}
			String raiz;
			if (idIdioma == Compuesto.EN && nombre.endsWith("ic")) {
				raiz = nombre.substring(0,nombre.length()-2).toLowerCase();
			} else {
				raiz = nombre.substring(0,nombre.length()-3);
			}
			raiz = raiz.toLowerCase();
			if (raiz.endsWith("sulfur")) {
				raiz = raiz.substring(0,raiz.length()-2); // caso especial sulfurato/e -> sulfato/e
			} else if (raiz.endsWith("fosfor")) {
				raiz = raiz.substring(0,raiz.length()-2); // caso especial fosforato -> fosfato
			} else if (raiz.endsWith("phosphor")) {
				raiz = raiz.substring(0,raiz.length()-2); // caso especial phosphorate -> phosphate
			}
			if (nombre.endsWith(ICO)) {
				nombre =  raiz + ATO;
			} else if (nombre.endsWith(OSO)) {
				nombre = raiz + ITO;
			}
			acido.nHidrogeno(acido.nHidrogeno()-numeroOxidacion);  // dejar el ion como estaba
		} catch (Exception e) {}
		return nombre;
	}

	

	/////////////////////////////////////////////////
	//////////////   Formula    /////////////////////
	/////////////////////////////////////////////////
	
	public String patronFormula() {
		String formula = compuesto.formula();
		if (numeroAtomos>1) {
			formula = "\\(" + formula +"\\)" + numeroAtomos;
		} else {
			formula = "(" + formula + "|\\(" + formula +"\\)" + ")";
		}
		return formula;
	}
	
	public String formula() {
		String formula = compuesto.formula();
		if (numeroAtomos>1) {
			formula = "(" + formula +")" + numeroAtomos;
		}
		return formula;
	}

	public String formulaHTML() {
		String formula = compuesto.formulaHTML();
		if (numeroAtomos>1) {
			formula = "(" + formula +")" + "<sub>" + numeroAtomos + "</sub>";
		}
		return formula;
	}

	@Override
	public Elemento elemento() {
		Elemento elemento = null;
		if (compuesto instanceof Oxoacido) {
			Oxoacido ox = ((Oxoacido)compuesto);
			elemento = ox.getElemento();
		} else if (compuesto instanceof Hidruro) {
			Hidruro h = ((Hidruro)compuesto);
			elemento = h.getElemento();
		}
		return elemento;
	}
	
	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	public String nomenclaturaDeHidrogeno() {
		Oxoacido acido = ((Oxoacido)compuesto);
		String nobreAnion = acido.nomenclaturaDeHidrogeno();
		if (numeroAtomos>1) {
			nobreAnion = prefijo(numeroAtomos) + "(" + nobreAnion +")" ;
		}
		return nobreAnion;
	}

	public String nomenclaturaDeAdicion() {
		Oxoacido acido = ((Oxoacido)compuesto);
		String nobreAnion = acido.nomenclaturaDeAdicion() + "(" + numeroOxidacion + "-)";
		return nobreAnion;
	}

	public String nomenclaturaStock() {
		return nomenclaturaTradicional();
	}

	public String nomenclaturaTradicional() {
		String hidrogeno = (idIdioma==Compuesto.EN ? "hydrogen" : "hidrogeno");
		String s = compuesto.formula();
		String nombre = (idIdioma==Compuesto.EN ? commonNameIUPAC.get(s) : nombreComunIUPAC.get(s));
		if (nombre==null) {
			Oxoacido acido = ((Oxoacido)compuesto);
			nombre = nombre();
			if (acido.nHidrogeno() > 1) {
				nombre = Ion.nombre(hidrogeno, acido.nHidrogeno()) + nombre;
			} else if (acido.nHidrogeno() > 0) {	
				nombre = hidrogeno + nombre;
			}
		} else {
			String ORTO = (idIdioma==Compuesto.EN ? "ortho" : "orto");
			Oxoacido acido = ((Oxoacido)compuesto);
			acido.nHidrogeno(acido.nHidrogeno()+numeroOxidacion);  // formar el acido otra vez
			if (acido.polihidratado == "orto") {
				nombre = ORTO + nombre;
			}
			acido.nHidrogeno(acido.nHidrogeno()-numeroOxidacion);  // dejarlo como estaba
		}
		return nombre;
	}

	public String nomenclaturaClasica() {
		String s = compuesto.formula();
		String nombre = (idIdioma==Compuesto.EN ? commonName.get(s) : nombreComun.get(s));
		if (nombre==null) {
			nombre = nombre();
			Oxoacido acido = ((Oxoacido)compuesto);
			// acido.nHidrogeno(acido.nHidrogeno()-numeroOxidacion);  // volver a formar el ion
			if (acido.nHidrogeno() == 1) {
				nombre = "bi" + nombre; // ej: bicarbonato
			// } else if (acido.nHidrogeno() > 1) {
			//	 nombre = Ion.prefijo(acido.nHidrogeno()+1) + nombre;  
			}
		}
		// acido.nHidrogeno(acido.nHidrogeno()+numeroOxidacion);  // dejar el acido como estaba
		return nombre;
	}

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////


	/////////////////////////////////////////////////
	
	
	public static void main(String[] argv) {		
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Oxoanion ox = Oxoanion.random(tablaPeriodica);		
			System.out.println(ox.formula() + " : " + ox.nomenclaturaTradicional() + " : "+ox.nomenclaturaSistematica());
		}
	}

	
}
