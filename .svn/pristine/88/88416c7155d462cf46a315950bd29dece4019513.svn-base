package siette.quimica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import siette.util.Random;
import siette.util.text.Strings;


public class Oxoacido extends Compuesto {
	
	

	 // Tablas de elementos
	
	protected static TablaPeriodica noMetalesSeleccion;
	static {
		noMetalesSeleccion = new TablaPeriodica(CompuestoBinario.noMetales);
		noMetalesSeleccion.delRow(TablaPeriodica.SIMBOLO, "O");
		noMetalesSeleccion.delRow(TablaPeriodica.SIMBOLO, "H");
		noMetalesSeleccion.setNotExclusive();
	}	
	
	protected static TablaPeriodica metalNoMetal;
	static {
		metalNoMetal = new TablaPeriodica(metales); 
		metalNoMetal = metalNoMetal.selectTable(TablaPeriodica.SIMBOLO, new String[] {"Cr","Mn"});
		metalNoMetal.addRow(BORO);
		metalNoMetal.addTable(noMetalesSeleccion);
	}	

	protected static TablaPeriodica orto;
	static {
		orto = TablaPeriodica.TABLAPERIODICA; 
		orto = orto.selectTable(TablaPeriodica.SIMBOLO, new String[] {"B", "V", "P", "As", "Sb", "Si" });
	}	

	protected static TablaPeriodica grupoPar;
	static {
		grupoPar = new TablaPeriodica(metalNoMetal); 
		grupoPar = grupoPar.selectTable(TablaPeriodica.GRUPO, new String[] {"6","14","16"});
	}	

	protected static TablaPeriodica grupoInpar;
	static {
		grupoInpar = new TablaPeriodica(metalNoMetal); 
		grupoInpar = grupoInpar.selectTable(TablaPeriodica.GRUPO, new String[] {"7","13","15","17"});
	}	

	protected int nHidrogeno, nElemento, nOxigeno; 
	protected Elemento elemento;
	protected String polihidratado;
	
	protected static Set<Integer> numerosOxidacionPositivos(Elemento e) {
		if (e.simbolo().equals("N")) {
			// En los oxoacidos solo se usa la valencia 1,3,5 paara el nitrogeno
			Set<Integer> set = new TreeSet<Integer>(Arrays.asList(1,3,5)); 
			return set;
		} else if (e.simbolo().equals("Mn")) {
				// En los oxoacidos solo se usa la valencia 6,7 paara el Manganeso
				Set<Integer> set = new TreeSet<Integer>(Arrays.asList(6,7)); 
				return set;
		} else if (e.simbolo().equals("Cr")) {
			// En los oxoacidos solo se usa la valencia 6 paara el cromo
			Set<Integer> set = new TreeSet<Integer>(Arrays.asList(6)); 
			return set;
		} else {
			return e.numerosOxidacionPositivos();
		}
	}
	
	
	/////////////////////////////////////////////////
	////////////// Constructores ////////////////////
	/////////////////////////////////////////////////
	
	public Oxoacido(Elemento e, int valencia, String poli) {
		super();
		init(e,valencia,poli);
	}
	
	public Oxoacido(Oxoacido ox) {
		this.nHidrogeno = ox.nHidrogeno;
		this.nElemento = ox.nElemento;
		this.nOxigeno = ox.nOxigeno;
		this.elemento = ox.elemento;
		this.polihidratado = ox.polihidratado;
	}
		
	// Metodos
	private void init(Elemento elemento, int valencia, String poli) {
		this.elemento = elemento;
		CompuestoBinario base;
		if (halogenos.containsElemento(elemento.simbolo())) {
			base = new Haluro(elemento,valencia);
		} else {
			base = new Oxido(elemento,valencia);
		}
		polihidratado = poli;
		if ("sin".equals(poli)) {
			if (orto.containsElemento(elemento.simbolo())) {
				polihidratado = "orto";
			} else {
				polihidratado = "meta";
			}
		}
		if ("meta".equals(polihidratado)) {
			// META
			// base + H2O
			nOxigeno = base.nOxigeno() + 1;
			nElemento = base.nElemento();
			nHidrogeno = 2;
		} else if ("orto".equals(polihidratado)) {
			// ORTO
			if (valencia % 2 == 0) { // (grupoPar.containsElemento(elemento.simbolo())) {
				// base + 2*H2O
				nOxigeno = base.nOxigeno() + 2;
				nElemento = base.nElemento();
				nHidrogeno = 4;
			} else { // if (grupoInpar.containsElemento(elemento.simbolo())) {
				// base + 3*H2O
				nOxigeno = base.nOxigeno() + 3;
				nElemento = base.nElemento();
				nHidrogeno = 6;
			}
		} else if ("di".equals(polihidratado)) {
			// PIRO
			if (valencia % 2 == 0) { // (grupoPar.containsElemento(elemento.simbolo())) {
				// 2*base + H2O
				nOxigeno = 2*base.nOxigeno() + 1;
				nElemento = 2*base.nElemento();
				nHidrogeno = 2;
			} else { // if (grupoInpar.containsElemento(elemento.simbolo())) {
				// base + 2*H2O
				nOxigeno = base.nOxigeno() + 2;
				nElemento = base.nElemento();
				nHidrogeno = 4;
			}
		}
		simplificar();
	}
	
	
	protected void simplificar() {
		if (nHidrogeno == 6) {
			if (nOxigeno % 3 == 0 && nElemento % 3 == 0) {
				nHidrogeno = nHidrogeno / 3; 
				nElemento = nElemento / 3;
				nOxigeno = nOxigeno / 3;
			} else if (nOxigeno % 2 == 0 && nElemento % 2 == 0) {
				nHidrogeno = nHidrogeno / 2; 
				nElemento = nElemento / 2;
				nOxigeno = nOxigeno / 2;
			}
		} 
		if (nHidrogeno == 4) {
			if (nOxigeno % 2 == 0 && nElemento % 2 == 0) {
				nHidrogeno = nHidrogeno / 2; 
				nElemento = nElemento / 2;
				nOxigeno = nOxigeno / 2;
			}
		}
		if (nHidrogeno == 2) {
			if (nOxigeno %2 == 0 && nElemento % 2 == 0) {
				nHidrogeno = nHidrogeno / 2; 
				nElemento = nElemento / 2;
				nOxigeno = nOxigeno / 2;
			}
		}
	}


	public Elemento getElemento() {
		return elemento;
	}

	public int nHidrogeno() {
		return nHidrogeno;
	}
	
	public void nHidrogeno(int n) {
		nHidrogeno = n;
	}
	

	public boolean simple() {
		return "meta".equals(polihidratado);
	}
	
	protected static String raiz(Elemento elemento) {
		String raiz = Cation.raiz(elemento);
		if ("sulfur".equalsIgnoreCase(raiz)) {
			raiz = "sulf"; // caso especial sulfurato -> sulfato
		} else if ("fosfor".equalsIgnoreCase(raiz)) {
			raiz = "fosf"; // caso especial fosforato -> fosfato
		}
		return raiz;
	}
	
	protected static String root(Elemento elemento) {
		String raiz = Cation.root(elemento);
		if ("sulfur".equalsIgnoreCase(raiz)) {
			raiz = "sulf"; // caso especial sulfurate -> sulfate
		} else if ("phosphor".equalsIgnoreCase(raiz)) {
			raiz = "phosph"; // caso especial phosphorate -> phosphate
		}
		return raiz;
	}
	
	protected int valencia() {
		return (nOxigeno*2 - nHidrogeno) / nElemento;
	}


	/////////////////////////////////////////////////
	///////////////// Random ////////////////////////
	/////////////////////////////////////////////////

	
	// Métodos para generar un compuesto al azar
	public static Oxoacido random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento(metalNoMetal);
		return random(tabla,elemento);
	}

	public static Oxoacido random(TablaPeriodica tabla, String poli) {
		Elemento elemento = tabla.selectElemento(metalNoMetal);
		return random(tabla, elemento,  poli);
	}	

	public static Oxoacido random(TablaPeriodica tabla, Elemento elemento) {
		String poli = tabla.randomSelect(Oxoanion.POLIHIDRATADOS);
		return random(tabla, elemento,  poli);
	}	

	public static Oxoacido random(TablaPeriodica tabla, Elemento elemento, String poli) {
		int valencia = 1;
		if ( ! "F".equals(elemento.simbolo())) {
			valencia = (int) tabla.select(numerosOxidacionPositivos(elemento));
		}
		return new Oxoacido(elemento, valencia, poli);
	}	


	/////////////////////////////////////////////////
	//////////////   Analizar  //////////////////////
	/////////////////////////////////////////////////
	
	
	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		if (componentes.size()>2 && "H".equals(componentes.get(0)) && "O".equals(componentes.get(2)) ) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(1));
			ConjuntoCompuestos cc = compuestos(elemento);
			compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		if (metalNoMetal.containsElemento(e.simbolo())) {
			if ("F".equals(e.simbolo())) {
				for(String poli: Oxoanion.POLIHIDRATADOS) {
					Oxoacido ox = new Oxoacido(e,1,poli);
					c.add(ox);
				}
			} else {
				Set<Integer> numerosOxidacion = numerosOxidacionPositivos(e);
				for(Integer n: numerosOxidacion) {
					for(String poli: Oxoanion.POLIHIDRATADOS) {
						Oxoacido ox = new Oxoacido(e,n,poli);
						c.add(ox);
					}
				}
			}
		}
		return c;
	}

	@Override
	public ConjuntoCompuestos compuestosSimilares() {
		return compuestos(elemento);
	}



	/////////////////////////////////////////////////
	//////////////   Formula    /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String formula() {
		return (nHidrogeno>0?"H":"") + (nHidrogeno>1?nHidrogeno:"") + elemento.simbolo() + (nElemento>1?nElemento:"") + "O" + (nOxigeno>1?nOxigeno:"");
	}

	@Override
	public String formulaHTML() {
		return (nHidrogeno>0?"H":"") + (nHidrogeno>1?"<sub>"+nHidrogeno+"</sub>":"") + elemento.simbolo() + (nElemento>1?"<sub>"+nElemento+"</sub>":"") + "O" + (nOxigeno>1?"<sub>"+nOxigeno+"</sub>":"");
	}

	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////
	
	@Override
	public String nombre() { 
		return nombre(TRADICIONAL + DEHIDROGENO + STOCK); // opciones por defecto
	}


	@Override
	public String nomenclaturaSistematica() {  
		String hidrogeno = (idIdioma==Compuesto.EN ? "hydrogen" : "hidrogeno");
		String ato = (idIdioma==Compuesto.EN ? "ate" : "ato");
		String oxo = (idIdioma==Compuesto.EN ? "oxo" : "oxo");
		String nombre = "";
		String raiz = (idIdioma==Compuesto.EN ? Oxoacido.root(elemento).toLowerCase() : Oxoacido.raiz(elemento).toLowerCase());
		String nombreOxido = (nOxigeno>1?Ion.nombre(oxo, nOxigeno):oxo) + (nElemento>1 ? Ion.nombre(raiz,nElemento):raiz) + ato;
		nombre = nombreOxido + (idIdioma==Compuesto.EN ? "" : " de ") + (nHidrogeno>1 ? Ion.nombre(hidrogeno, nHidrogeno) : hidrogeno );
		return cap(nombre);
	}
		
	@Override
	public String nomenclaturaStock() {  
		String ico = (idIdioma==Compuesto.EN ? "ic" : "ico");
		String oxo = (idIdioma==Compuesto.EN ? "oxo" : "oxo");
		String raiz = (idIdioma==Compuesto.EN ? Cation.root(elemento).toLowerCase() : Cation.raiz(elemento).toLowerCase());
		String nomenclatura = "";
		if (nElemento>0) {
			String nombreAcido = (idIdioma==Compuesto.EN ? raiz : Cation.acentuarRaiz(raiz)) + ico ;
			String nombre = (nOxigeno>1?Ion.nombre(oxo, nOxigeno):oxo)
						  + (nElemento>1?Ion.nombre(nombreAcido, nElemento):nombreAcido);
			int valencia = (nOxigeno*2 - nHidrogeno) / nElemento;
			if (valencia>1) {
				nombre += "(" + Ion.numeroOxidacionRomano(valencia) + ")" ;
			}
			if (idIdioma==Compuesto.EN) {
				nomenclatura = nombre + " acid";
			} else {
				nomenclatura = Strings.filterHTMLtoTXT("&Aacute;cido")+ " " + nombre;
			}
		}
		return cap(nomenclatura);
	}
		
	@Override
	public String nomenclaturaDeHidrogeno() {  
		String nombre = "";
		String hidrogeno = (idIdioma==Compuesto.EN ? "hydrogen" : "hidrogeno");
		String ato = (idIdioma==Compuesto.EN ? "ate" : "ato");
		String oxido = (idIdioma==Compuesto.EN ? "oxide" : "oxido");
		if(nHidrogeno>1) {
			nombre = Ion.nombre(hidrogeno, nHidrogeno);
		} else if(nHidrogeno>0) {
			nombre = hidrogeno;
		}
		String raiz = (idIdioma==Compuesto.EN ? root(elemento).toLowerCase() : raiz(elemento).toLowerCase() );
		String nombreOxido = (nOxigeno>1?Ion.nombre(oxido, nOxigeno):oxido) + (nElemento>1 ? Ion.nombre(raiz,nElemento):raiz) + ato;
		if (nombre.equals("")) {
			nombre = nombreOxido;
		} else {
			nombre += "(" + nombreOxido + ")";
		}
		return cap(nombre);
	}

	@Override
	public String nomenclaturaDeAdicion() { 
		String oxido = (idIdioma==Compuesto.EN ? "oxide" : "oxido");
		String nombre = "";
		if (nOxigeno>nHidrogeno) {
			IonHidroxido ion = new IonHidroxido(nHidrogeno);
			ion.idIdioma(idIdioma);
			String nombreHidroxido = ion.nomenclaturaSistematica();
			nombreHidroxido = Strings.filterHTMLtoTXT(nombreHidroxido);
			// resto
			int nOxigeno = this.nOxigeno-nHidrogeno;
			// String raiz = raiz(elemento).toLowerCase();
			String nombreOxido;
			if (idIdioma == EN) {
				nombreOxido = (nElemento>1 ? Ion.name(elemento,nElemento):elemento.name()).toLowerCase() + (nOxigeno>1?Ion.nombre("oxide", nOxigeno):"oxide") ;	
			} else {
				nombreOxido = (nOxigeno>1?Ion.nombre(oxido, nOxigeno):oxido) + (nElemento>1 ? Ion.nombre(elemento,nElemento):elemento.nombre()).toLowerCase() ;	
			}
			nombre = (nHidrogeno>0?nombreHidroxido:"") + nombreOxido;
		} else if (nOxigeno == nHidrogeno){
			// Igual numero de Oxigeno que de Hidrogeno...
			IonHidroxido ion = new IonHidroxido(nHidrogeno);
			ion.idIdioma(idIdioma);
			String nombreHidroxido = ion.nomenclaturaSistematica();
			// resto
			String nombreElemento;
			if (idIdioma == EN) {
				nombreElemento = (nElemento >1? Ion.name(elemento,nElemento):elemento.name()).toLowerCase() ;		
			} else {
				nombreElemento = (nElemento >1? Ion.nombre(elemento,nElemento):elemento.nombre()).toLowerCase() ;		
			}
			nombre = nombreHidroxido + nombreElemento;
		} else {
			// No hay moleculas de oxigeno suficientes...
		}
		return cap(nombre);
	}


	@SuppressWarnings("unlikely-arg-type")
	@Override
	public String nomenclaturaTradicional() {
		if (elemento.equals("Cr") || elemento.equals("Mn")) {
			return "";  // No se admiten en la IUPAC "acido cromico" ni "acido manganico"
		} else {
			return nomenclaturaClasica();
		}
	}

	@Override
	public String nomenclaturaClasica() {
		String nomenclatura = "";
		if (nElemento>0) {
			String nombre = Cation.prefijoSufijo(elemento, valencia(), idIdioma);
			String poli = polihidratado;
			if ("orto".equals(poli) && orto.containsElemento(elemento.simbolo())) {
				poli = ""; // no poner orto
			} else if ("meta".equals(poli) && !orto.containsElemento(elemento.simbolo()) ) {
				poli = ""; // no poner meta
			} else if ("di".equals(poli) ) {
				// poli = "piro"; 
				poli = "di"; 
			}
			if (idIdioma == EN) {
				nomenclatura = TablaPeriodica.cap(Oxoanion.traduce(poli) + nombre) + " acid";
			} else {
				nomenclatura = Strings.filterHTMLtoTXT("&Aacute;cido")+ " " + poli + nombre;
			}
		}
		return cap(nomenclatura);
	}
	
	
	public String errorNomenclaturaClasica() {
		String patronError = "";
		if (elemento!=null) {
			String raiz = raiz(elemento).toLowerCase();
			patronError =      "{meta|orto|ortho|di|piro|pyro}{hipo|hypo|per}"+raiz+"{ico|oso|ic|ous}" 
					    + "|"+ "{hipo|hypo|per}{meta|orto|ortho|di|piro|pyro}"+raiz+"{ico|oso|ic|ous}"
					    ;
		}
		return patronError;
	}


	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	
	@Override
	public ArrayList<Integer> nomenclaturasPorDefecto() {
		ArrayList<Integer> porDefecto = new ArrayList<Integer>();
		porDefecto.add(NOM_DEHIDROGENO);
		porDefecto.add(NOM_DEADICION);
		porDefecto.add(NOM_TRADICIONAL);
		return porDefecto;
	}
	

	@Override
	public String patronTradicional() {
		String patron=patronClasico();
		if (patron!=null && !patron.equals("")) {
			// La notacion admitida IUPAC incluye solo el prefijo "di"
			patron = patron.replace("(piro|pyro|di)", "di");
		}
		return patron;
	}

	@Override
	public String patronClasico() {
		String patron="";
		if (nElemento>0) {
			int valencia = (nOxigeno*2 - nHidrogeno) / nElemento;
			String nombre = Cation.prefijoSufijo(elemento, valencia, idIdioma);
			String poli = polihidratado;
			if ("orto".equals(poli) && orto.containsElemento(elemento.simbolo())) {
				poli = "{orto}"; // opcional
			} else if ("meta".equals(poli) && !orto.containsElemento(elemento.simbolo()) ) {
				poli = "{meta}"; // opcional
			} else if ("di".equals(poli) ) {
				poli = "(piro|pyro|di)"; 
			}
			if (idIdioma == EN) {
				patron = TablaPeriodica.cap(Oxoanion.traduce(poli) + nombre) + " acid";
			} else {
				patron = Strings.filterHTMLtoTXT("acido")+ " " + poli + nombre;
			}
		}
		return patron;
	}

	
	/////////////////////////////////////////////////

	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		tablaPeriodica.selectCurso("3ESO");

		// String[] formulas = { "HClO3", "H2SO4","HBrO", "HNO2", "H2CO3" }; 
		// String[] formulas = { "HNO3", "H2S2O7","H2MnO4", "H4SiO4", "H2SO4", "H2Cr2O7","H2CrO4"  }; 
		// String[] formulas = { "H3BO3", "H2SiO3","H4SiO4", "HPO", "H4P2O3", "H3PO2", "HPO2", "H4P2O5", "H3PO3", "HPO3", "H4P2O7", "H3PO4" }; 
		// String[] formulas = { "H3PO4", "H5P3O10", "H6Si2O7"};
		String[] formulas = { "H3BO3", "HClO4"};
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula,true);
			if (compuesto!=null) {
				System.out.println(compuesto);
			}
		}
		
		Elemento fluor = TablaPeriodica.selectElemento("F");
		Oxoacido HFO = Oxoacido.random(tablaPeriodica,fluor);
		System.out.println(HFO);
		
		Elemento cromo = TablaPeriodica.selectElemento("Cr");
		Oxoacido HCrO = new Oxoacido(cromo,6,"di");
		System.out.println(HCrO);
		
		
		for(int i=0; i<100; i++) {
			Oxoacido acido = Oxoacido.random(tablaPeriodica,"sin");		
			System.out.println(acido);
		}
		
		System.out.println("----------");
		
		for(int i=0; i<100; i++) {
			Oxoacido acido = Oxoacido.random(tablaPeriodica);		
			System.out.println(acido);
		}
		

	}

	
}
