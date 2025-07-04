package siette.quimica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import siette.util.Combinatoria;
import siette.util.Random;
import siette.util.Roman;
import siette.util.text.Strings;

public abstract class Compuesto {

	// IDIOMAS
	public static int NINGUNO = -1; // No necesario el idioma (formulas)
	public static int ES = 1; 
	public static int EN = 2;
	public static int defaultLang = ES;
	
	// Nivel
	public static String ESO3 = "3ESO";
	public static String ESO4 = "4ESO";
	
	// Tipos de compuestos
	public final static int ELEMENTO = 10251, PRIMERCOMPUESTO = ELEMENTO;
	public final static int OXIDO = 10252;        
	public final static int PEROXIDO = 10253;
	public final static int HIDRURO = 10254;
	public final static int HALURO = 10255;
	public final static int SALBINARIA = 10256;
	public final static int HIDROXIDO = 10257;
	public final static int OXOACIDO = 10258;
	public final static int OXOACIDOPOLIATOMICO = 10259;
	public final static int OXOSAL = 10260;
	public final static int OXOSALPOLIATOMICA = 10261;
	public final static int SALACIDA = 10262;
	public final static int TIOACIDO = 10263;
	public final static int ANIONPOLIATOMICO = 10264, ULTIMOCOMPUESTO = ANIONPOLIATOMICO;  

	// Tipos de nomenclatura
	public final static int NOM_SISTEMATICA = 10274, PRIMERANOMENCLATURA = NOM_SISTEMATICA;
	public final static int NOM_STOCK = 10275;
	public final static int NOM_DEHIDROGENO = 10276;
	public final static int NOM_DEADICION = 10277;
	public final static int NOM_TRADICIONAL = 10278, ULTIMANOMENCLATURA = NOM_TRADICIONAL;
	public final static int NOM_CLASICA = 10279;
	
	public static HashMap<String, String> nombreComunIUPAC = new HashMap<String, String>();
	public static HashMap<String, String> nombreComun = new HashMap<String, String>();
	static {
		nombreComunIUPAC.put("H2O",  "agua");
		nombreComunIUPAC.put("H2O2", "agua oxigenada");

		// Hidruros
		nombreComunIUPAC.put("NH3",  "amoniaco");      
		nombreComunIUPAC.put("PH3",  "fosfano");       
		nombreComunIUPAC.put("AsH3", "arsano");        
		nombreComunIUPAC.put("SbH3", "estibano");      
		nombreComunIUPAC.put("BiH3", "bismutano");
		nombreComunIUPAC.put("CH4",  "metano");
		nombreComunIUPAC.put("SiH4", "silano");
		nombreComunIUPAC.put("GeH4", "germano");
		nombreComunIUPAC.put("SnH4", "estannano");
		nombreComunIUPAC.put("PbH4", "plumbano");
		nombreComunIUPAC.put("BH3",  "borano");
		nombreComunIUPAC.put("AlH3", "alumano");
		nombreComunIUPAC.put("GaH3", "galano");
		nombreComunIUPAC.put("InH3", "indigano");
		nombreComunIUPAC.put("TlH3", "talano");

		nombreComunIUPAC.put("H2S",  Strings.filterHTMLtoTXT("&aacute;cido sulfh&iacute;drico"));
		nombreComunIUPAC.put("H2Se", Strings.filterHTMLtoTXT("&aacute;cido selenh&iacute;drico"));
		nombreComunIUPAC.put("H2Te", Strings.filterHTMLtoTXT("&aacute;cido telurh&iacute;drico"));
		nombreComunIUPAC.put("HF",   Strings.filterHTMLtoTXT("&aacute;cido fluorh&iacute;drico"));
		nombreComunIUPAC.put("HCl",  Strings.filterHTMLtoTXT("&aacute;cido clorh&iacute;drico"));
		nombreComunIUPAC.put("HBr",  Strings.filterHTMLtoTXT("&aacute;cido bromh&iacute;drico"));
		nombreComunIUPAC.put("HI",   Strings.filterHTMLtoTXT("&aacute;cido yodh&iacute;drico"));

		nombreComunIUPAC.put("NH4",   "amonio");
		
		nombreComunIUPAC.put("HCN",  Strings.filterHTMLtoTXT("&aacute;cido cianh&iacute;drico"));

		// Nombres comunes no admitods por IUPAC

		// Hidruros
		nombreComun.put("PH3",  "azano");
		nombreComun.put("PH3",  "fosfina");
		nombreComun.put("PH3",  "arsina");
		nombreComun.put("PH3",  "estibina");

		nombreComun.put("HF",   "fluorano");
		nombreComun.put("HCl",  "clorano");
		nombreComun.put("H2Se", "selano");
		nombreComun.put("H2S",  "sulfano");

		//Anhidridos
		nombreComun.put("N2O",  Strings.filterHTMLtoTXT("&oacute;xido nitroso"));
		nombreComun.put("NO",   Strings.filterHTMLtoTXT("&oacute;xido n&iacute;trico"));
		nombreComun.put("NO2",  Strings.filterHTMLtoTXT("di&oacute;xido n&iacute;trico"));
		nombreComun.put("N2O3", Strings.filterHTMLtoTXT("anh&iacute;drido nitroso"));
		nombreComun.put("N2O5", Strings.filterHTMLtoTXT("anh&iacute;drido n&iacute;trico"));
		nombreComun.put("SiO2", Strings.filterHTMLtoTXT("s&iacute;lice"));
		nombreComun.put("CO2",  Strings.filterHTMLtoTXT("anh&iacute;drido carb&oacute;nico"));
		nombreComun.put("SO2",  Strings.filterHTMLtoTXT("anh&iacute;drido sulfuroso"));
		nombreComun.put("SO3",  Strings.filterHTMLtoTXT("anh&iacute;drido sulfu&uacite;rico"));

		//Peroxidos
		nombreComun.put("Na2O2", "oxilita");
		
	}
	
	public static HashMap<String, String> commonNameIUPAC = new HashMap<String, String>();
	public static HashMap<String, String> commonName = new HashMap<String, String>();
	static {
		commonNameIUPAC.put("H2O",  "water");
		commonNameIUPAC.put("H2O2", "hydrogen peroxide");
		
		// Hidruros
		commonNameIUPAC.put("NH3",  "ammonia");      
		commonNameIUPAC.put("PH3",  "phosphine");       
		commonNameIUPAC.put("AsH3", "arsine");        
		commonNameIUPAC.put("SbH3", "stibine");      
		commonNameIUPAC.put("BiH3", "bismuthane");
		commonNameIUPAC.put("CH4",  "methane");
		commonNameIUPAC.put("SiH4", "silane");
		commonNameIUPAC.put("GeH4", "germanane");
		commonNameIUPAC.put("SnH4", "stannane");
		commonNameIUPAC.put("PbH4", "plumbane");
		commonNameIUPAC.put("BH3",  "borane");
		commonNameIUPAC.put("B2H6", "diborane");
		commonNameIUPAC.put("AlH3", "alane");
		// commonNameIUPAC.put("GaH3", "galane");
		commonNameIUPAC.put("InH3", "indane");
		// commonNameIUPAC.put("TlH3", "talane");

		commonNameIUPAC.put("H2S",  "hydrosulfuric acid,");
		commonNameIUPAC.put("H2Se", "hydroselenic acid");
		commonNameIUPAC.put("H2Te", "hydrotelluric acid");
		commonNameIUPAC.put("HF",   "hydrofluoric acid");
		commonNameIUPAC.put("HCl",  "hydrochloric acid");
		commonNameIUPAC.put("HBr",  "hydrobromic acid");
		commonNameIUPAC.put("HI",   "hydroiodic acid");

		commonNameIUPAC.put("NH4",   "amonio");
		
		commonNameIUPAC.put("HCN",  "hydrocyanic acid");

		// Nombres comunes no admitods por IUPAC

		//Anhidridos
		commonName.put("N2O",  "nitrous oxide");
		commonName.put("NO",   "nitric oxide");
		commonName.put("NO2",  "nitric dioxide");
		commonName.put("N2O3", "nitrous anhydride");
		commonName.put("N2O5", "nitric anhydride");
		commonName.put("SiO2", "silica");
		commonName.put("CO2",  "carbonic anhydride");
		commonName.put("SO2",  "sulfurous anhydride");
		commonName.put("SO3",  "sulfuric anhydride");
	}
	

	public static TablaPeriodica halogenos;
	static {
		halogenos = TablaPeriodica.TABLAPERIODICA; 
		// System.out.println("halogenos.size(1)="+halogenos.size());
		halogenos = halogenos.selectTable(TablaPeriodica.SUBCATEGORIA, new String[] {"Halogenos"} );
		// System.out.println("halogenos.size(2)="+halogenos.size());
		halogenos = halogenos.selectTable(TablaPeriodica.PERIODO, new int[] {1,2,3,4,5,6} );
		// System.out.println("halogenos.size(2)="+halogenos.size());
		halogenos.setNotExclusive(); 
	}

	public static TablaPeriodica metales;
	static {
		metales = TablaPeriodica.TABLAPERIODICA; 
		// System.out.println("metales.size(1)="+metales.size());
		metales = metales.selectTable(TablaPeriodica.CATEGORIA, new String[] {"Metales"} );
		// System.out.println("metales.size(2)="+metales.size());
		metales = metales.selectTable(TablaPeriodica.OXIDACION, new int[] {1,2,3,4,5,6,7,8} ); // (casi todos tienen valencia positiva, salvo los raros)		
		// System.out.println("metales.size(3)="+metales.size());
		metales.setNotExclusive();  // 30
	}

	public static TablaPeriodica metalesMetaloides;
	static {
		metalesMetaloides = TablaPeriodica.TABLAPERIODICA; 
		// System.out.println("metales.size(1)="+metales.size());
		metalesMetaloides = metalesMetaloides.selectTable(TablaPeriodica.CATEGORIA, new String[] {"Metales", "Metaloides"} );
		// System.out.println("metales.size(2)="+metales.size());
		metalesMetaloides = metalesMetaloides.selectTable(TablaPeriodica.OXIDACION, new int[] {1,2,3,4,5,6,7,8} ); // (casi todos tienen valencia positiva, salvo los raros)		
		// System.out.println("metales.size(3)="+metales.size());
		metalesMetaloides.setNotExclusive(); 
	}

	public static TablaPeriodica noMetales;
	static {
		noMetales = TablaPeriodica.TABLAPERIODICA; 
		// System.out.println("noMetales.size(1)="+noMetales.size());
		noMetales = noMetales.selectTable(TablaPeriodica.CATEGORIA, new String[] {"No metales","Metaloides"} );
		// System.out.println("noMetales.size(2)="+noMetales.size());
		noMetales = noMetales.selectTable(TablaPeriodica.GRUPO, new String[] {"13","14","15","16","17"} ); // grupos 13 a 17	
		// System.out.println("noMetales.size(3)="+noMetales.size());
		noMetales = noMetales.selectTable(TablaPeriodica.OXIDACION, new int[] {-1,-2,-3,-4,-5,-6,-7,-8} ); // que tienen valencia negativa		
		// System.out.println("noMetales.size(4)="+noMetales.size());
		noMetales.setNotExclusive(); // 15
	}

	public static TablaPeriodica oxidables;
	static {
		TablaPeriodica tabla = TablaPeriodica.TABLAPERIODICA; 
		// System.out.println("tabla.size(1)="+tabla.size());
		tabla = tabla.selectTable(TablaPeriodica.CATEGORIA, new String[] {"Metales","Metaloides","No metales"}, // si son
								  TablaPeriodica.SUBCATEGORIA, new String[] {"Halogenos", "Gases nobles"}  // no son
				                 );
		// System.out.println("tabla.size(3)="+tabla.size());
		tabla = tabla.selectTable(TablaPeriodica.OXIDACION, new int[] {1,2,3,4,5,6,7,8} ); // valencia positiva		
		// System.out.println("tabla.size(4)="+tabla.size());
		tabla.setNotExclusive(); 
		oxidables = tabla;
	}
	
	// Elementos singujalres
	public static Elemento HIDROGENO = TablaPeriodica.selectElemento("H");
	public static Elemento OXIGENO = TablaPeriodica.selectElemento("O");
	public static Elemento FLUOR = TablaPeriodica.selectElemento("F");
	public static Elemento CLORO = TablaPeriodica.selectElemento("Cl");
	public static Elemento BORO = TablaPeriodica.selectElemento("B");
	public static Elemento SILICIO = TablaPeriodica.selectElemento("Si");
	public static Elemento FOSFORO = TablaPeriodica.selectElemento("P");
	public static Elemento AZUFRE = TablaPeriodica.selectElemento("S");
	
	
	
	// Modos
	public static int FORMULA          = 1 << 0;
	public static int SISTEMATICA      = 1 << 1;  public static int COMPOSICION  = 1 << 1; 
	public static int STOCK            = 1 << 2;  // es la sistematica con numeros de oxidacion
	public static int DEHIDROGENO      = 1 << 3;
	public static int DEADICION        = 1 << 4;
	public static int TRADICIONAL      = 1 << 5;  // Solo incluye nombres comunes aceptados por la IUPAC
	public static int CLASICA          = 1 << 6;  // Incluye la notacion clasica, y nombres antiguos no aceptados por IUPAC 
	                                              // pero que aun son de uso corriente (usado solo para patrones de respuesta)
	                              
	public static int log2(int N) {
        return (int)(Math.log(N) / Math.log(2));
    }
	
	public static String patronEscape(String patron) {
		patron = patron.replaceAll("\\(", "\\\\(");
		patron = patron.replaceAll("\\)", "\\\\)");
		patron = patron.replaceAll("\\+", "\\\\+");
		return patron; 
	}
	
	public static String cap(String st) {
		return TablaPeriodica.cap(st);
	}
	
	public static String low(String st) {
		return TablaPeriodica.low(st);
	}
	
	protected int idIdioma = defaultLang;
	public void idIdioma(int idIdioma) {
		this.idIdioma = idIdioma;
	}
	public int idIdioma() {
		return this.idIdioma;
	}
	
	public String patronFormula() { 
		String formula = formula();
		return patronEscape(formula); 
	}
	public String patronSistematica()         { return patronEscape(nomenclaturaSistematica()); }
	public String patronStock()               { return patronEscape(nomenclaturaStock()); }
	public String patronDeHidrogeno()         { return patronEscape(nomenclaturaDeHidrogeno()); }
	public String patronDeAdicion()           { return patronEscape(nomenclaturaDeAdicion()); }
	public String patronTradicional()         { return patronEscape(nomenclaturaTradicional()); }
	public String patronClasico()             { 
		String nombre = "";
		String formula = formula();
		if (nombreComun.get(formula) !=null) {
			nombre += nombreComun.get(formula).trim();
		}
		String clasica = nomenclaturaClasica();
		if (!"".equals(clasica)) {
			if (!"".equals(nombre)) {
				nombre += "|" + clasica;
			} else {
				nombre = clasica;
			}
		}
		return patronEscape(nombre);
	}

	public ArrayList<Integer> nomenclaturasTodas() {
		ArrayList<Integer> porDefecto = new ArrayList<Integer>();
		porDefecto.add(NOM_SISTEMATICA);
		porDefecto.add(NOM_STOCK);
		porDefecto.add(NOM_DEHIDROGENO);
		porDefecto.add(NOM_DEADICION);
		porDefecto.add(NOM_TRADICIONAL);
		porDefecto.add(NOM_CLASICA);
		return porDefecto;
	}
	
	public ArrayList<Integer> nomenclaturasPorDefecto() {
		ArrayList<Integer> porDefecto = new ArrayList<Integer>();
		porDefecto.add(NOM_SISTEMATICA);
		porDefecto.add(NOM_STOCK);
		porDefecto.add(NOM_TRADICIONAL);
		return porDefecto;
	}
	
	protected int lista2Modo(List<Integer> listaNomenclaturas) {
		int modo = 0;
		for(int i:listaNomenclaturas) {
			switch (i) {
				case NOM_SISTEMATICA: 
					modo += SISTEMATICA;
					break;
				case NOM_STOCK: 
					modo += STOCK;
					break;
				case NOM_DEHIDROGENO: 
					modo += DEHIDROGENO;
					break;
				case NOM_DEADICION: 
					modo += DEADICION;
					break;
				case NOM_TRADICIONAL: 
					modo += TRADICIONAL;
					break;
				case NOM_CLASICA: 
					modo += CLASICA;
					break;
			}
		}
		return modo;
	}
	
	public String patron() { 
		return patron(lista2Modo(nomenclaturasPorDefecto())); // opciones por defecto
	}

	public String patron(List<Integer> listaNomenclaturas) {
		return patron(lista2Modo(listaNomenclaturas));
	}

	public String patron(int modo) {
		String patron = "";
		if ((modo >> log2(FORMULA)) % 2 == 1) {
			String p = patronFormula();
			if ( !p.equals("") ) patron += patron.equals("")? p : "|" + p ;
		}
		if ((modo >> log2(SISTEMATICA)) % 2 == 1) {
			String p = patronSistematica();
			if ( !p.equals("") ) patron += patron.equals("")? p : "|" + p ;
		}
		if ((modo >> log2(STOCK)) % 2 == 1) {
			String p = patronStock();
			if ( !p.equals("") ) patron += patron.equals("")? p : "|" + p ;
		}
		if ((modo >> log2(DEHIDROGENO)) % 2 == 1) {
			String p = patronDeHidrogeno();
			if ( !p.equals("") ) patron += patron.equals("")? p : "|" + p ;
		}
		if ((modo >> log2(DEADICION)) % 2 == 1) {
			String p = patronDeAdicion();
			if ( !p.equals("") ) patron += patron.equals("")? p : "|" + p ;
		}
		if ((modo >> log2(TRADICIONAL)) % 2 == 1) {
			String p = patronTradicional();
			if ( !p.equals("") ) patron += patron.equals("")? p : "|" + p ;
		}
		if ((modo >> log2(CLASICA)) % 2 == 1) {
			String p = patronClasico();
			if ( !p.equals("") ) patron += patron.equals("")? p : "|" + p ;
		}
		return patron;
	}
		
	public String nombre() { 
		return nombre(SISTEMATICA+STOCK+TRADICIONAL); // opciones por defecto
	}

	public String nombre(List<Integer> listaNomenclaturas) {
		return nombre(lista2Modo(listaNomenclaturas));
	}

	/*
	 * Devuele el nombre del compuesto 
	 * Si existe la tradicional, esa, si no prueba la sistematica, si no la de stock, etc..
	 */
	public String nombre(int modo) {
		String nombre = "";
		if ((modo >> log2(TRADICIONAL)) % 2 == 1 && "".equals(nombre)) {
			String p = nomenclaturaTradicional();
			if ( !p.equals("") ) nombre = TablaPeriodica.cap(p) ;
		}
		if ((modo >> log2(SISTEMATICA)) % 2 == 1 && "".equals(nombre)) {
			String p = nomenclaturaSistematica();
			if ( !p.equals("") ) nombre = TablaPeriodica.cap(p) ;
		}
		if ((modo >> log2(STOCK)) % 2 == 1 && "".equals(nombre)) {
			String p = nomenclaturaStock();
			if ( !p.equals("") ) nombre = p ;
		}
		if ((modo >> log2(DEHIDROGENO)) % 2 == 1 && "".equals(nombre)) {
			String p = nomenclaturaDeHidrogeno();
			if ( !p.equals("") ) nombre = TablaPeriodica.cap(p) ;
		}
		if ((modo >> log2(DEADICION)) % 2 == 1 && "".equals(nombre)) {
			String p = nomenclaturaDeAdicion();
			if ( !p.equals("") ) nombre = TablaPeriodica.cap(p) ;
		}
		return nombre; 
	}

	public String nombreCompleto(List<Integer> listaNomenclaturas) {
		return nombreCompleto(lista2Modo(listaNomenclaturas));
	}

	/*
	 * Devuele el nombre del compuesto en todas las posibles nomenclaturas
	 */
	public String nombreCompleto(int modo) {
		String nombre = "";
		if ((modo >> log2(SISTEMATICA)) % 2 == 1 ) {
			String p = nomenclaturaSistematica();
			if ( !p.equals("") ) nombre += (nombre.equals("")? "" : "; ") + TablaPeriodica.cap(p) ;
		}
		if ((modo >> log2(STOCK)) % 2 == 1) {
			String p = nomenclaturaStock();
			if ( !p.equals("") ) nombre += (nombre.equals("")? "" : "; ") + p ;
		}
		if ((modo >> log2(DEHIDROGENO)) % 2 == 1) {
			String p = nomenclaturaDeHidrogeno();
			if ( !p.equals("") ) nombre += (nombre.equals("")? "" : "; ") + TablaPeriodica.cap(p) ;
		}
		if ((modo >> log2(DEADICION)) % 2 == 1) {
			String p = nomenclaturaDeAdicion();
			if ( !p.equals("") ) nombre += (nombre.equals("")? "" : "; ") + TablaPeriodica.cap(p) ;
		}
		if ((modo >> log2(TRADICIONAL)) % 2 == 1 ) {
			String p = nomenclaturaTradicional();
			if ( !p.equals("") ) nombre += (nombre.equals("")? "" : "; ") + TablaPeriodica.cap(p) ;
		}
		if ((modo >> log2(CLASICA)) % 2 == 1 ) {
			String p = nomenclaturaClasica();
			if ( !p.equals("") ) nombre += (nombre.equals("")? "" : "; ") + TablaPeriodica.cap(p) ;
		}
		return nombre; 
	}

	/*
	 * Devuelev exactamente la nomenclatura que se diga
	 */
	public String nomenclatura(int tipo) {
		switch(tipo) {
			case NOM_SISTEMATICA:
				return nomenclaturaSistematica();
			case NOM_STOCK:
				return nomenclaturaStock();
			case NOM_DEHIDROGENO:
				return nomenclaturaDeHidrogeno();
			case NOM_DEADICION:
				return nomenclaturaDeAdicion(); 
			case NOM_TRADICIONAL:
				return nomenclaturaTradicional(); 
		}
		return nombre(idIdioma);
	}
	
	/* **** ESPAÑOL **** */
	
	public String nomenclaturaSistematica()   { return ""; } 
	public String nomenclaturaStock()         { return ""; } 
	public String nomenclaturaDeComposicion() { return nomenclaturaSistematica(); } 
	public String nomenclaturaDeHidrogeno()   { return ""; } 
	public String nomenclaturaDeAdicion()     { return ""; } 
	public String nomenclaturaTradicional()   { 
		String nombre = "";
		String formula = formula();
		if (idIdioma==EN) {
			if (commonNameIUPAC.get(formula) !=null) {
				nombre += commonNameIUPAC.get(formula);
			}
		} else {
			if (nombreComunIUPAC.get(formula) !=null) {
				nombre += nombreComunIUPAC.get(formula);
			}
		}
		return nombre;
	}
	public String nomenclaturaClasica()     { 
		String nombre = "";
		String formula = formula();
		if (idIdioma==EN) {
			if (commonName.get(formula) !=null) {
				nombre += commonName.get(formula);
			}
		} else {
			if (nombreComun.get(formula) !=null) {
				nombre += nombreComun.get(formula);
			}
		}
		return nombre.trim();
	}  

	/* **************** */
	
	public static Compuesto random(TablaPeriodica tablaPeriodica, List<Integer> listaCompuestos) {
		Compuesto compuesto = null;
		int n = (Integer) tablaPeriodica.select(listaCompuestos);
		switch(n) {
			case ELEMENTO:
				return CompuestoUnario.random(tablaPeriodica);
			case OXIDO:
				return Oxido.random(tablaPeriodica);
			case HALURO:
				return Haluro.random(tablaPeriodica);
			case HIDRURO:
				return Hidruro.random(tablaPeriodica); 
			case PEROXIDO:
				return Peroxido.random(tablaPeriodica); 
			case SALBINARIA:
				return SalBinaria.random(tablaPeriodica); 
			case HIDROXIDO:
				return Hidroxido.random(tablaPeriodica); 
			case OXOACIDO:
				return Oxoacido.random(tablaPeriodica, "sin"); 
			case OXOACIDOPOLIATOMICO:
				return Oxoacido.random(tablaPeriodica); 
			case OXOSAL:
				return Oxosal.random(tablaPeriodica); 
			case OXOSALPOLIATOMICA:
				return OxosalPoliatomica.random(tablaPeriodica); 
			case ANIONPOLIATOMICO:
				return Cianuro.random(tablaPeriodica); 
			case SALACIDA:
				return SalAcida.random(tablaPeriodica); 
			case TIOACIDO:
				return Tioacido.random(tablaPeriodica); 
		}
		return compuesto;
	}

	
	public static Compuesto random(TablaPeriodica tablaPeriodica) {
		ArrayList<Integer> todos = new ArrayList<Integer>();
		for(int i=PRIMERCOMPUESTO; i<=ULTIMOCOMPUESTO; i++) {
			todos.add(i);
		}
		return random(tablaPeriodica, todos);
	}
	
	public abstract String formula() ;
	public abstract String formulaHTML() ;
	public abstract ConjuntoCompuestos compuestosSimilares();
	
	public static String normalizar(String in) {
		String out = in;
		if (out!=null) {
			out = Strings.eliminarAcentos(out);
			out = Strings.eliminarBlancos(out);
			out = Strings.eliminarSignosPuntuacion(out);
			out = out.toLowerCase();
		}
		return out;
	}
	
	public static ArrayList<String> componentes(String formula) {
		ArrayList<String> componentes = new ArrayList<String>();
		if (formula!=null) {
		    Pattern pattern = Pattern.compile("([A-Z][a-z]*)([0-9]*)");
		    Matcher matcher = pattern.matcher(formula);
		    while (matcher.find()) {
		    	String simbolo = matcher.group(1);
		    	componentes.add(simbolo);
		    }
		}
		//return componentes.toArray(new String[0]);
		return componentes;
	}
	
	// Intenta hallar los elementois a partir del nombre
	public static TreeSet<String> componentes(String nombre, int idioma) {
		nombre = nombre.toLowerCase();
		nombre = Strings.eliminarAcentos(nombre);
		TreeSet<String> componentes = new TreeSet<String>();
		if (nombre!=null) {
			String[] palabras = nombre.split(" ()");
			for(int i=0; i< palabras.length; i++) {
				Elemento elemento = TablaPeriodica.selectElementoNombre(palabras[i], idioma); // por nombre completo
				String simbolo = null; 
				if (elemento!=null) {	
					simbolo = elemento.simbolo();
					componentes.add(simbolo);
				} 
				elemento = TablaPeriodica.selectElementoRaizNombre(palabras[i], idioma);  // por raiz
				if (elemento!=null) {
					ArrayList<String> raices = new ArrayList<String>();
					// if (!elemento.simbolo().equals(simbolo)) { // si es distinto al obtenido por nombre completo
						componentes.add(elemento.simbolo());
						String raiz0 = idioma==ES ? Oxoacido.raiz(elemento) : Oxoacido.root(elemento);
						raices.add(raiz0);
						if (raiz0.contains("yod")) {
							componentes.add("I");
							raices.add("iod");
						}
						if (raiz0.contains("fosf") || raiz0.contains("phosph")) {
							componentes.add("P");
							raices.add("fosfor");
							raices.add("phosphor");
						}
					// }
					// Casos especiales
					if (palabras[i].contains("sulf") || palabras[i].contains("azufr")) {
						componentes.add("S");
						raices.add("sulf");
						raices.add("sulfur");
					}
					for (String raiz:raices) {
						raiz = raiz.toLowerCase();
						if (nombre.contains(raiz+"ito") || nombre.contains(raiz+"ato") || nombre.contains(raiz+"ite") || nombre.contains(raiz+"ate")) {
							// para las oxosales
							componentes.add("O");
						}
						if (nombre.contains(raiz+"ic") || nombre.contains(raiz+"oso") || nombre.contains(raiz+"ous") ) {
							// para los oxoacidos
							if (raiz.equals("arsen") && !nombre.contains("acid") ) {
								// System.out.println("raiz="+raiz+ " nombre="+nombre);
							} else {
								componentes.add("O");
							}
						}
					}
				}
				if (palabras[i].contains("anhidrido") || palabras[i].contains("anhydride")) {
					componentes.add("O");
				} else {
					if (palabras[i].contains("oxid") || palabras[i].contains("oxo")) { 
						componentes.add("O");
					}
					if (palabras[i].contains("hidr") || palabras[i].contains("hydr") || palabras[i].contains("acid") ) { 
						componentes.add("H");
					}
				}
			}
		}
		return componentes;
	}

	
	public static Set<String> setComponentes(String formula) {
		HashSet<String> setComponentes = new HashSet<String>();
		ArrayList<String> componentes = componentes(formula);
		if (componentes!=null) {
			for(int i=0; i<componentes.size(); i++) {
				setComponentes.add(componentes.get(i));
			}
		}
		return setComponentes;
	}
	
	public static Integer[] subindices(String formula) {
		ArrayList<Integer> subindices = new ArrayList<Integer>();
		if (formula!=null) {
		    Pattern pattern = Pattern.compile("([A-Z][a-z]*)([0-9]*)");
		    Matcher matcher = pattern.matcher(formula);
		    while (matcher.find()) {
		    	String stSubindice = matcher.group(2);
		    	if (stSubindice==null || stSubindice.equals("")) {
		    		subindices.add(1);
		    	} else {
			    	subindices.add(Integer.parseInt(stSubindice));
		    	}
		    }
		}
		return subindices.toArray(new Integer[0]);
	}

	
	public static Compuesto analizar(String nombre, int idioma) {
		return analizar(nombre, idioma, false);
	}

	public static Compuesto analizar(String nombre, int idioma, boolean onError) {
		nombre = nombre.toLowerCase();
		nombre = Strings.filterHTMLtoTXT(nombre);
		nombre = Strings.eliminarAcentos(nombre);
		Compuesto compuesto = buscarPorNombre(nombre, idioma);
		if (compuesto == null) {
			TreeSet<String> ccomponentes = componentes(nombre, idioma);
			if (ccomponentes!=null) {
				Iterator<String> it = ccomponentes.iterator();
				boolean H = false, O = false; // Hay componentes H, O
				String X = null, Y = null; // Hay componentes X e Y
				while(it.hasNext()) {
					String componente = it.next();
					if (componente.equals("H")) {
						H = true;
					} else if (componente.equals("O")) {
						O = true;
					} else if (X == null) {
						X = componente;
					} else if (Y == null) {
						Y = componente;
					}
				}
				// Se prueba varios tipos de compuestos segun los componentes
				ArrayList<String> componentes = new ArrayList<String>();
				if (compuesto == null) {
					if (nombre.toLowerCase().contains("cian") || nombre.toLowerCase().contains("cyan")) {
						// IonCiano
						componentes = new ArrayList<String>();
						if (H) {
							componentes.add("H");componentes.add("C");componentes.add("N");
						} else if (X!=null) {
							componentes.add(X);componentes.add("C");componentes.add("N");
						}
						compuesto = Cianuro.analizar(componentes, nombre, idioma);
					}
				}
				if (compuesto == null) {
					if (nombre.toLowerCase().contains("amoni")) {
						// IonAmonio
						componentes = new ArrayList<String>();
						if (H && X!=null && O) {
							componentes.add("N");componentes.add("H");componentes.add("H");componentes.add(X);componentes.add("O");
						} else if (X!=null && O) {
							componentes.add("N");componentes.add("H");componentes.add(X);componentes.add("O");
						}
						compuesto = OxosalPoliatomica.analizar(componentes, nombre, idioma);
					}
				}
				if (compuesto == null) {
					if (nombre.toLowerCase().contains("hidroxido") || nombre.toLowerCase().contains("hydroxide")) {
						// Hidroxidos
						componentes = new ArrayList<String>();
						if (H && O && X!=null && Y==null) {
							componentes.add(X);componentes.add("O");componentes.add("H");
						}
						compuesto = Hidroxido.analizar(componentes, nombre, idioma);
					}
				}
				if (compuesto == null) {
					if (H && O && X!=null && Y==null) {
						// Oxoacidos (incluye Poliacidos y Peroxoacidos)
						componentes = new ArrayList<String>();
						if (H && O && X!=null && Y==null) {
							componentes.add("H");componentes.add(X);componentes.add("O");
						}
						if (compuesto == null) compuesto = Oxoacido.analizar(componentes, nombre, idioma);
						if (compuesto == null) compuesto = Poliacido.analizar(componentes, nombre, idioma);
						if (compuesto == null) compuesto = Peroxoacido.analizar(componentes, nombre, idioma);
					}
				}
				if (compuesto == null) {
					if (nombre.toLowerCase().contains("tio") || nombre.toLowerCase().contains("thio") 
							|| H && O && X!=null && X.equals("S") 
							|| H && O && Y!=null && Y.equals("S")
							|| H && O && Y!=null && X.equals("S")
						) {
						// Tioacidos
						componentes = new ArrayList<String>();
						if (H && Y!=null && !Y.equals("S") && O) {
							componentes.add("H");componentes.add(Y);componentes.add("S");componentes.add("O");
						} else if (H && X!=null && !X.equals("S") && O) {
							componentes.add("H");componentes.add(X);componentes.add("S");componentes.add("O");
						} else if (H && O) {
							componentes.add("H");componentes.add("S");componentes.add("O");
						}
						if (compuesto == null) compuesto = Tioacido.analizar(componentes, nombre, idioma);
					}
				}
				if (compuesto == null) {
					if (    H && O && X!=null && Y!=null 
						|| !H && O && X!=null && Y!=null
						) {
						// Oxosales
						componentes = new ArrayList<String>();
						if (   !metales.containsElemento(X) && metales.containsElemento(Y) 
							|| !Oxoacido.metalNoMetal.containsElemento(Y) && Oxoacido.metalNoMetal.containsElemento(X)
						   ) {
							String Z = X; X = Y; Y = Z; // intercambiar X/Y
						}
						if        (!H && O && X!=null && Y!=null) {
							componentes.add(X);componentes.add(Y);componentes.add("O");
						} else if (H && O && X!=null && Y!=null) {
							componentes.add(X);componentes.add("H");componentes.add(Y);componentes.add("O");
						}
						if (compuesto == null) compuesto = Oxosal.analizar(componentes, nombre, idioma);
						// Caso especial del Cr y el Mn
						if (compuesto == null && X.equals("Cr") && Y.equals("Mn")) {
							componentes = new ArrayList<String>();
							String Z = X; X = Y; Y = Z;
							if        (!H && O && X!=null && Y!=null) {
								componentes.add(X);componentes.add(Y);componentes.add("O");
							} else if (H && O && X!=null && Y!=null) {
								componentes.add(X);componentes.add("H");componentes.add(Y);componentes.add("O");
							}
							compuesto = Oxosal.analizar(componentes, nombre, idioma);
						}
					}
					if (compuesto == null) {
						if ( !H && !O && X!=null && Y!=null ) {
							// SalBinaria
							componentes = new ArrayList<String>();
							componentes.add(X);componentes.add(Y);
							if (compuesto == null) compuesto = SalBinaria.analizar(componentes, nombre, idioma);
							componentes = new ArrayList<String>();
							componentes.add(Y);componentes.add(X);
							if (compuesto == null) compuesto = SalBinaria.analizar(componentes, nombre, idioma);
						}
					}
					if (compuesto == null) {
						if ( H && !O && X!=null && Y!=null ) {
							// SalAcida
							if (   !SalAcida.noMetalesSeleccion.containsElemento(X) && !metales.containsElemento(Y)
								&&	SalAcida.noMetalesSeleccion.containsElemento(Y) && metales.containsElemento(X)
							   ) {
								String Z = X; X = Y; Y = Z; // intercambiar X/Y
							}
							componentes = new ArrayList<String>();
							componentes.add(Y);componentes.add("H"); componentes.add(X);
							if (compuesto == null) compuesto = SalAcida.analizar(componentes, nombre, idioma);
						}
					}
					if (compuesto == null) {
						if ( H && !O && X!=null && Y==null ) {
							// Hidruro
							componentes = new ArrayList<String>();
							componentes.add(X);componentes.add("H");
							if (compuesto == null) compuesto = Hidruro.analizar(componentes, nombre, idioma);
						}
					}
					if (compuesto == null) {
						if (    !H && O && X!=null && Y==null 
							|| H && O && X==null && Y==null  // agua
						   ) {
							if (H && O) {
								X = "H";
							}
							// Oxidos, peroxidos y haluros
							componentes = new ArrayList<String>();
							componentes.add(X);componentes.add("O");
							if (compuesto == null) compuesto = Oxido.analizar(componentes, nombre, idioma);
							if (compuesto == null) compuesto = Peroxido.analizar(componentes, nombre, idioma);
							if (compuesto == null) {
								componentes = new ArrayList<String>();
								componentes.add("O");componentes.add(X);
								compuesto = Haluro.analizar(componentes, nombre, idioma);
							}
						}
					}
					// Si solo hay un componente, se busca directamente en la tabla periodica el compuesto unario
					if (compuesto == null) {
						if        (H && !O && X==null && Y==null) {
							compuesto = new CompuestoUnario("H");
						} else if (!H && O && X==null && Y==null) {
							compuesto = new CompuestoUnario("O");
						} else if (!H && !O && X!=null && Y==null) {
							compuesto = new CompuestoUnario(X);
						}
					}
				}
			}
		}
		if (compuesto == null) {
	        if(onError) error(nombre);
		}
		return compuesto;
	}

	private static Compuesto buscarPorNombre(String nombre, int idioma) {
		if (idioma == EN) {
			for( String formula : commonNameIUPAC.keySet()) {
				String valor = commonNameIUPAC.get(formula);
				if (nombre.equals(valor)) {
					return analizar(formula);
				}
			}
		} else {
			for( String formula : nombreComunIUPAC.keySet()) {
				String valor = nombreComunIUPAC.get(formula);
				valor = Strings.eliminarAcentos(valor);
				if (nombre.equals(valor)) {
					return analizar(formula);
				}
			}
		}
		return null;
	}

	public static Compuesto analizar(String formula) {
		return analizar(formula, false);
	}

	public static Compuesto analizar(String formula, boolean onError) {
		Compuesto compuesto = null;
		ArrayList<String> componentes = componentes(formula);
		boolean error = false;
		if (componentes!=null) {
			for(int i=0; i<componentes.size(); i++) {
				String simbolo = componentes.get(i);
				if (!TablaPeriodica.TABLAPERIODICA.containsElemento(simbolo)) {
					error = true;
				}
			}
			if (!error) {
				if (compuesto == null) compuesto = CompuestoUnario.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Oxido.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Peroxido.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Hidruro.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Cianuro.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Haluro.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = SalBinaria.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Hidroxido.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Oxoacido.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Poliacido.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Peroxoacido.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Oxosal.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = OxosalPoliatomica.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = SalAcida.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) compuesto = Tioacido.analizar(componentes, formula, NINGUNO);
				if (compuesto == null) {
			        if(onError) error(formula);
				}
 			}
		}
		return compuesto;
	}
	
	/*
	 * Devuelve el compuesto similar a partir del nombre en cualquier nomenclatura
	 */
	public Compuesto compuestoSimilar(String nombre) {
		return compuestoSimilar(nombre, false);
	}
	
	public Compuesto compuestoSimilar(String nombre, boolean onError) {
		if (nombre == null || "".equals(nombre)) return null;  // ASSERT
		Compuesto compuesto = null;
		ConjuntoCompuestos ccs = compuestosSimilares();
		nombre = normalizar(nombre);
		for(Compuesto c : ccs) {
			if (     nombre.equals(normalizar(c.nomenclaturaSistematica()))
				  || nombre.equals(normalizar(c.nomenclaturaStock()))
				  || nombre.equals(normalizar(c.nomenclaturaTradicional()))
				  || nombre.equals(normalizar(c.nomenclaturaDeHidrogeno()))
				  || nombre.equals(normalizar(c.nomenclaturaDeAdicion()))
				  || nombre.equals(normalizar(c.nomenclaturaClasica()))
			  ) {
				return c;
			}
		}
		return compuesto;
	}

	/////////////////////////////////////////////////
	//////////////   Errores    /////////////////////
	/////////////////////////////////////////////////

	// Construye un patron combinando posibles errores en cada uno de los componentes
	// Por ejemplo de H2O construiria el patron [A-Z]{[a-z]}2O|H[A-Z]{[a-z]}2
	// capaz de recocer respuestas como H2Ox o Hi2O (solo un error en un simbolo quimico)
	// Si se usa detras del errorElementoDesconocido, indicaria confusion de dos elementos 
	// validos de la tabla periodica
	public String errorElemento() {
		if (! (this instanceof CompuestoUnario) ) {
			try {
				Formula formula = new Formula(formula());
				String patron =  formula.patronElementos();
				return patronEscape(patron);
			} catch (FormulaException e) {
			}
		}
		return "";
	}

	public String errorSimplificar() {
		try {
			Formula formula = new Formula(formula());
			formula.simplificar();
			String patron = formula.toString();
			return patronEscape(patron); // Algunos compuestos no se pueden simplificar
		} catch (FormulaException e) {
		}
		return "";
	} 

	public String errorNoSimplificar() {
		if (! (this instanceof CompuestoUnario) ) {
			try {
				Formula formula = new Formula(formula());
				ArrayList<Formula> variaciones = formula.variacionesSinSimplificar();
				String patron = formula();
				for(Formula variacion : variaciones) {
					patron += "|" + variacion;
				}
				return patronEscape(patron);
			} catch (FormulaException e) {
			}
		}
		return "";
	}
	
	public String errorOrdenElementos() {
		try {
			Formula formula = new Formula(formula());
			ArrayList<Formula> variaciones = formula.permutaciones();
			String patron = formula(); // la primera variación contiene la formula correcta
			for(Formula variacion : variaciones) {
				patron += "|" + variacion;
			}
			return patronEscape(patron);
		} catch (FormulaException e) {
		}
		return "";
	}

	public String errorOrdenElementosGeneral() {
		String patron = "";
		ArrayList<String> componentesSolucion = Compuesto.componentes(formula());  // elementos quimicos de la solucion
		ArrayList<ArrayList<String>> permutaciones = Combinatoria.permutaciones(componentesSolucion);
		// i=permutaciones.length-1 contiene la primera permutacion, que corresponde al orden correcto
		for(int i=0; i<permutaciones.size()-1; i++) {
			String valencia = "{[1-9]}";
			String variacion = (String) permutaciones.get(i).get(0) + valencia;
			for(int j=1; j<permutaciones.get(i).size(); j++) {
				String elemento = (String) permutaciones.get(i).get(j);
				variacion += elemento + valencia;
			}
			patron += (patron.equals("") ? "" : "|") + variacion;
		}
		return patronEscape(patron);
	}

	public String errorNumerosDesordenados() {
		if (this instanceof CompuestoBinario)  {
			try {
				Formula formula = new Formula(formula());
				String patron = formula.patronSubindicesDesordenados();  
				return patron; // no usar patronEscape en este caso
			} catch (FormulaException e) {
			}
		}
		return "";
	}

	public String errorNumeroOxidacion() {
		if (this instanceof CompuestoBinario)  {
			try {
				CompuestoBinario compuesto = (CompuestoBinario)this;
				int nOxidacion = compuesto.cation.numeroOxidacion();
				Formula formula = new Formula(formula());
				String patron =  formula.patronSubindicesOxidacion(Math.abs(nOxidacion));  
				return patronEscape(patron);
			} catch (FormulaException e) {
			}
		}
		return "";
	}

	public String errorSinSubindices(String nombre) {
		Pattern pattern = Pattern.compile("\\("+ Ion.patronRomanos() + "\\)|(?:^| |\\()"+Ion.patronPrefijos());
		Matcher matcher = pattern.matcher(Strings.eliminarAcentos(nombre.toLowerCase()));
		if (!matcher.find()) {
			// Este patron de error solo se aplica si el nombre no tiene prefijos ni numeros romanos
			try {
				Formula formula = new Formula(formula());
				String patron =  formula.patronNoSubindices();  
				if (patron.contains("(")) {
					String altPatron = patron.replaceAll("\\(|\\)", "");
					patron = patronEscape(patron) + "|" + altPatron;
				}
				return patron;
			} catch (FormulaException e) {
			}
		}
		return "";
	}

	public String errorNumeroAtomos() {
		if (! (this instanceof CompuestoUnario) ) {
			try {
				Formula formula = new Formula(formula());
				String patron =  formula.patronSubindices();  
				return patron; // no usar patronEscape en este caso
			} catch (FormulaException e) {
			}
		}
		return "";
	}

	public String errorNumeroUno() {
		if (!(this instanceof CompuestoUnario)) {
			try {
				Formula formula = new Formula(formula());
				String patron =  formula.patronNumeroUno();
				return patronEscape(patron);
			} catch (FormulaException e) {
			}
		}
		return "";
	}

	public String errorFaltaH() {
		if (nHidrogeno()>0 && !(this instanceof CompuestoUnario)) {
			try {
				Formula formula = new Formula(formula());
				String patron =  formula.patronSubindices("H");
				return patronEscape(patron);
			} catch (FormulaException e) {
			}
		}
		return "";
	}

	public String errorNoPeroxido() {
		if (this instanceof Peroxido) {
			Peroxido pox = (Peroxido) this;
			Elemento e = ((CompuestoUnario)(pox.getCation().getCompuesto())).getElemento();
			int numeroOxidacion = pox.getCation().numeroOxidacion;
			Oxido ox = new Oxido(e, numeroOxidacion);
			return patronEscape(ox.formula());
		}
		return "";
	}

	public String errorElectronegativoSalBinaria() {
		if (this instanceof SalBinaria) {
			SalBinaria sal = (SalBinaria) this;
			try {
				// Elemento e = ((CompuestoUnario)(sal.getAnion().getCompuesto())).getElemento(); // debe ser un elemento simple
				// int numeroOxidacion = sal.getAnion().numeroOxidacion;
				Compuesto ePositivo = sal.getCation().getCompuesto();
				Formula formula = new Formula(formula());
				String patron =  formula.patronSubindices(ePositivo.formula());
				return patronEscape(patron);
			} catch (Exception ex) {
			}
		}
		return "";
	}

	/////////////////////////////////////////////////
	////////   Errores de nomenclatura    ///////////
	/////////////////////////////////////////////////

	// devuelve un patron con nombres alternatuvos para el elemento
	// i.e. cloruro de sodio => cloruro de (sodio|potasio|plata|oro|...)
	public String errorNombreElemento(List<Integer> listaNomenclaturas) {
		
		ArrayList<String> componentes = componentes(formula());
		String patronNombres = TablaPeriodica.patronNombres(idIdioma, componentes);
		String patronRaices = TablaPeriodica.patronRaizNombres(idIdioma, componentes);
		
		/*
		String nombre = nombreCompleto(listaNomenclaturas);
		nombre = Strings.eliminarAcentos(nombre).toLowerCase();
		
		// Priermo busca si en las respuesta aparece el nombre de algun elemento
		String patronNombres = TablaPeriodica.patronNombres();
        Pattern pattern = Pattern.compile(patronNombres);
        Matcher matcher = pattern.matcher(nombre);
        ArrayList<String> stElementos = new ArrayList<String>();
        String patronError = ""; 
        String stElemento = "";
       
		while (matcher.find()) {
        	stElemento = matcher.group();
        	Elemento e = TablaPeriodica.selectElementoNombre(stElemento);
        	if (e!=null) {
        		stElemento = Strings.eliminarAcentos(e.patron()).toLowerCase();
        		stElemento = stElemento.replaceAll("\\|", "\\\\|");
        		stElemento = stElemento.replaceAll("\\(", "\\\\(");
        		stElemento = stElemento.replaceAll("\\)", "\\\\)");
        	}
        	stElementos.add(stElemento);
        	patronNombres = patronNombres.replaceAll(stElemento+"\\|", ""); // quitar el elemento correcto
        }
		
        
		// Despues buca si en las respuestas aparece la raíz de algun elemento, disntinto del encontrado anteriormente.
		String patronRaices = TablaPeriodica.patronRaizNombres();
        pattern = Pattern.compile(patronRaices);
        matcher = pattern.matcher(nombre);
		while (matcher.find()) {
        	stElemento = matcher.group(1);
        	Elemento e = TablaPeriodica.selectElementoRaizNombre(stElemento);
        	if (e!=null) {
        		stElemento = Strings.eliminarAcentos(e.patron()).toLowerCase();
        	}
        	stElementos.add(stElemento);
        	patronRaices = patronRaices.replaceAll(stElemento+"\\|", ""); // quitar el elemento correcto
        }
		*/
		
		String patronError =  "*(" + patronNombres+"|"+patronRaices + ")*"; // si hay varios se coge solo el último
		
		return patronError;
	}
	
	// devuelve un patron en el que admite cualquier prefijo, incluso no valido
	// i.e. Pentafosfuro de triarsénico => *fosfuro de *arsenico
	protected String cualquierPrefijo(String patron) {
		String patronPrefijos = patron;
		Pattern pattern = Pattern.compile("(?:^| )[{]?"+Ion.patronPrefijos()+"[}]?");
		Matcher matcher = pattern.matcher(patron);
		while (matcher.find()) {
		    String prefijo = matcher.group();
		    prefijo = prefijo.replaceAll("\\{", "\\\\{");
		    prefijo = prefijo.replaceAll("\\}", "\\\\}");
		    patronPrefijos = patronPrefijos.replaceAll(prefijo, "*" );
		}
		return patronPrefijos;
	}
	
	// devuelve un patron en el que admite cualquier numero romano, incluso no valido
	// i.e. Oxido de polonio\(IV\) => Oxido de polonio*
	protected String cualquierNumeroRomano(String patron) {
		String numeroRomano = patron;
		Pattern pattern = Pattern.compile("[{]?[\\\\]?[(]?"+Ion.patronRomanos()+"[\\\\]?[)]?[}]?");
		Matcher matcher = pattern.matcher(patron);
		while (matcher.find()) {
		    String numero = matcher.group();
		    numero = numero.replaceAll("\\{", "\\\\{");
		    numero = numero.replaceAll("\\}", "\\\\}");
		    numero = numero.replaceAll("\\(", "\\\\\\\\(");
		    numero = numero.replaceAll("\\)", "\\\\\\\\)");
		    // numero = numero.replaceAll("\\\\", "\\\\\\\\");
		    numeroRomano = numeroRomano.replaceAll(numero, "*" );
		}
		return numeroRomano;
	}

	// devuelve un patron con todos los posibles prefijos
	// i.e. Pentafosfuro de triarsénico => (mono|di|tri|tetra...)fosfuro de (mono|di|tri|tetra|..)arsenico
	public String errorPrefijosNumeroAtomos() {
		String patron = Strings.eliminarAcentos(patronSistematica()).toLowerCase();
		Pattern pattern = Pattern.compile("(?:^| )"+Ion.patronPrefijos()+"|\\{"+Ion.patronPrefijos()+"\\}");
		Matcher matcher = pattern.matcher(patron);
		StringBuffer patronPrefijos = new StringBuffer();
		while (matcher.find()) {
		    matcher.appendReplacement(patronPrefijos, "{" + Ion.patronPrefijos()+ "}");
		}
		matcher.appendTail(patronPrefijos);
	    // patronPrefijos = patronPrefijos.replaceAll("(?:^| |\\{)"+"("+prefijos+")", Ion.patronPrefijos() );
		return patronPrefijos.toString();
	}
	
	// devuelve un patron con todos los posibles prefijos
	// i.e. Pentafosfuro de triarsénico => (mono|di|tri|tetra...)fosfuro de (mono|di|tri|tetra|..)arsenico
	public String errorPrefijosDesconocidos() {
		String patron = Strings.eliminarAcentos(patronSistematica()).toLowerCase();
		Pattern pattern = Pattern.compile("(?:^| )"+Ion.patronPrefijos()+"|\\{"+Ion.patronPrefijos()+"\\}");
		Matcher matcher = pattern.matcher(patron);
		StringBuffer patronPrefijos = new StringBuffer();
		while (matcher.find()) {
		    matcher.appendReplacement(patronPrefijos, "*");
		}
		matcher.appendTail(patronPrefijos);
	    // patronPrefijos = patronPrefijos.replaceAll("(?:^| |\\{)"+"("+prefijos+")", Ion.patronPrefijos() );
		return patronPrefijos.toString();
	}

	// devuelve un patron con todos los posibles prefijos
	// i.e. 
	public String errorRomanosNumeroAtomos() {
		String patron = patronStock();
		String numeroRomano = patron;
		Pattern pattern = Pattern.compile("\\{\\\\\\("+ Ion.patronRomanos() + "\\\\\\)\\}");
		Matcher matcher = pattern.matcher(patron);
		if (matcher.find()) {
		    String numero = matcher.group();
		    numero = numero.replaceAll("\\(", "\\\\\\\\(");
		    numero = numero.replaceAll("\\)", "\\\\\\\\)");
		    numero = numero.replaceAll("\\{", "\\\\{");
		    numero = numero.replaceAll("\\}", "\\\\}");
		    numeroRomano = numeroRomano.replaceAll(numero, "{\\\\(" + Ion.patronRomanos() + "\\\\)}" );
		} else {
			pattern = Pattern.compile("\\\\\\("+ Ion.patronRomanos() + "\\\\\\)");
			matcher = pattern.matcher(patron);
			if (matcher.find()) {
			    String numero = matcher.group();
			    numero = numero.replaceAll("\\(", "\\\\\\\\(");
			    numero = numero.replaceAll("\\)", "\\\\\\\\)");
			    numeroRomano = numeroRomano.replaceAll(numero, "{\\\\(" + Ion.patronRomanos() + "\\\\)}" );
			} else {
				numeroRomano += "{\\(" + Ion.patronRomanos() + "\\)}";
			}
		}
		return numeroRomano;
	}

	protected String primerPrefijo() {
		String prefijo1 = "";
		String patron = patronSistematica();
		if (this instanceof CompuestoBinario)  {
			Pattern pattern = Pattern.compile("(?:^| )"+Ion.patronPrefijos()+"|\\{"+Ion.patronPrefijos()+"\\}");
			Matcher matcher = pattern.matcher(patron);
			if (matcher.find()) {
			    prefijo1 = matcher.group();
			    prefijo1 = "mon".equals(prefijo1) || "mono".equals(prefijo1) ? "(mon|mono)" : prefijo1;
			    prefijo1 = "{mon}".equals(prefijo1) || "{mono}".equals(prefijo1) ? "{mon|mono}" : prefijo1;
			}
		}
		return prefijo1;
	}
	
	protected String segundoPrefijo() {
		String prefijo2 = "";
		String patron = patronSistematica();
		if (this instanceof CompuestoBinario)  {
			Pattern pattern = Pattern.compile("(?:^| )"+Ion.patronPrefijos()+"|\\{"+Ion.patronPrefijos()+"\\}");
			Matcher matcher = pattern.matcher(patron);
			if (matcher.find()) {
				    String prefijo1 = matcher.group();
				    prefijo1 = "mon".equals(prefijo1) || "mono".equals(prefijo1) ? "(mon|mono)" : prefijo1;
				    prefijo1 = "{mon}".equals(prefijo1) || "{mono}".equals(prefijo1) ? "{mon|mono}" : prefijo1;
					if (matcher.find()) {
					    prefijo2 = matcher.group();
					    prefijo2 = "mon".equals(prefijo2) || "mono".equals(prefijo2) ? "(mon|mono)" : prefijo2;
					    prefijo2 = "{mon}".equals(prefijo2) || "{mono}".equals(prefijo2) ? "{mon|mono}" : prefijo2;
					}
			}
		}
		return prefijo2;
	}
	
	public String errorPrefijosDesordenados() {
		String patron = patronSistematica();
		StringBuffer patronPrefijos = new StringBuffer();
		if (this instanceof CompuestoBinario)  {
			Pattern pattern = Pattern.compile("(?:^| )"+Ion.patronPrefijos()+"|\\{"+Ion.patronPrefijos()+"\\}");
			Matcher matcher = pattern.matcher(patron);
			if (matcher.find()) {
			    String prefijo1 = matcher.group();
			    prefijo1 = "mon".equals(prefijo1) || "mono".equals(prefijo1) ? "(mon|mono)" : prefijo1;
			    prefijo1 = "{mon}".equals(prefijo1) || "{mono}".equals(prefijo1) ? "{mon|mono}" : prefijo1;
				if (matcher.find()) {
				    String prefijo2 = matcher.group();
				    prefijo2 = "mon".equals(prefijo2) || "mono".equals(prefijo2) ? "(mon|mono)" : prefijo2;
				    prefijo2 = "{mon}".equals(prefijo2) || "{mono}".equals(prefijo2) ? "{mon|mono}" : prefijo2;
				    if (!prefijo1.equals(prefijo2)) {
						matcher = pattern.matcher(patron);
						if (matcher.find()) {
							matcher.appendReplacement(patronPrefijos, prefijo2);
							if (matcher.find()) {
								matcher.appendReplacement(patronPrefijos, prefijo1);
							}
						}
						matcher.appendTail(patronPrefijos);
				    }
				}
			}
		}
		return patronPrefijos.toString();
	}

	// pone a la izquierda el elemento que deberia ir a la derecha
	public String errorNomenclaturaOrdenacion() {
		String patronError = "";
		if (this instanceof CompuestoBinario) {
			CompuestoBinario c = (CompuestoBinario) this;
			Anion a = c.getAnion();
			if (a.getCompuesto() instanceof CompuestoUnario) {
				Elemento e = ((CompuestoUnario)a.getCompuesto()).getElemento();
				patronError = "*de *"+e.nombre().toLowerCase() + "{\\\\(" + Ion.patronRomanos() + "\\\\)}";
			}
		}
		return patronError; // cloruro de sodio  => *de*cloro => sodio de cloro
	}

	public String errorNomenclaturaFaltaUro() {
		String URO = (idIdioma==EN ? "ide" : "uro");
		String patronError = "";
		if (this instanceof CompuestoBinario) {
			CompuestoBinario cb = (CompuestoBinario) this;
			String nombre = patronSistematica();
			nombre = Strings.eliminarAcentos(nombre).toLowerCase();
			StringTokenizer tokens = new StringTokenizer(nombre);
			String primeraPalabra = tokens.nextToken();
			if (primeraPalabra.endsWith(URO) || primeraPalabra.endsWith(URO+"\\)") ) {
				
				// Implementacion manteniendo los prefijos y el cation
				/*
				int l = primeraPalabra.endsWith("uro") ? 3 : 5;
				String raiz = primeraPalabra.substring(0,primeraPalabra.length()-l);
				String resto = nombre.substring(primeraPalabra.length());
				String prefijo = primerPrefijo();
				patronError = raiz + "(ato|ito|ano)" ;
				Anion a = cb.getAnion();
				if (a.getCompuesto() instanceof CompuestoUnario) {
					Elemento e = ((CompuestoUnario)a.getCompuesto()).getElemento();
					String nombreElemento = Strings.eliminarAcentos(e.nombre()).toLowerCase();
					patronError += "|" + prefijo + nombreElemento + " ";
					patronError = "(" + patronError + ")";
				}
				patronError += (l==5?"\\)":"") + resto;
				*/
				
				// Implementacion que solo atiende a la terminación
				Anion a = cb.getAnion();
				// Compuesto ca = a.getCompuesto();
				patronError = a.patronRaiz() + "(ato|ito|ano)" ;
				if (a.getCompuesto() instanceof CompuestoUnario) {
					Elemento e = ((CompuestoUnario)a.getCompuesto()).getElemento();
					String nombreElemento = Strings.eliminarAcentos(e.nombre()).toLowerCase();
					patronError += "|" + nombreElemento + " ";
					patronError = "*(" + patronError + ")*";
				}
				
			}
		}
 		return patronError;
	}
	
	
	public String errorNomenclaturaFaltaAno() {
		String patronError = "";
		String nombre = patronTradicional();
		nombre = Strings.eliminarAcentos(nombre).toLowerCase();
		if (!"".equals(nombre)) {
			StringTokenizer tokens = new StringTokenizer(nombre);
			String primeraPalabra = tokens.nextToken();
			if (primeraPalabra.endsWith("ano") || primeraPalabra.endsWith("ano\\)") ) {
				
				
				// Implementacion manteniendo los prefijos y el cation
 				int l = primeraPalabra.endsWith("ano") ? 3 : 5;
				String raiz = primeraPalabra.substring(0,primeraPalabra.length()-l);
				String resto = nombre.substring(primeraPalabra.length());
				patronError = raiz + "(ato|ito|uro)" ;
				patronError += (l==5?"\\)":"") + resto;
				
				/*
				// Implementacion que solo atiende a la terminación
				Anion a = cb.getAnion();
				Compuesto ca = a.getCompuesto();
				patronError = a.patronRaiz() + "(ato|ito|ano)" ;
				if (a.getCompuesto() instanceof CompuestoUnario) {
					Elemento e = ((CompuestoUnario)a.getCompuesto()).getElemento();
					String nombreElemento = Strings.eliminarAcentos(e.nombre()).toLowerCase();
					patronError += "|" + nombreElemento + " ";
					patronError = "*(" + patronError + ")*";
				}
				*/

			}
		}
 		return patronError;
	}
	
	public String errorNomenclaturaFaltaH() {
		String patronError = "";
		String patron = patron();
		patron = Strings.eliminarAcentos(patron).toLowerCase();
		if (!"".equals(patron)) {
			if (patron.contains("hidrico")) {
				patronError = patron.replaceAll("hidrico", "idrico");
			}
		}
 		return patronError;
	}
	
	public String errorNomenclaturaFaltaOxido() {
		String patronError = "";
		String patron = patron();
		patron = Strings.eliminarAcentos(patron).toLowerCase();
		if (!"".equals(patron)) {
			if (patron.contains("oxido")) {
				patronError = patron.replaceAll("oxido", "(oxigeno|oxigenuro|oxiduro)");
			}
		}
 		return patronError;
	}
	
	public String errorNomenclaturaSobraOxido() {
		String patronError = "";
		String patron = patron();
		patron = Strings.eliminarAcentos(patron).toLowerCase();
		if (!"".equals(patron)) {
			if (patron.contains("oxigeno")) {
				patronError = "*oxido*" ;
			}
		}
 		return patronError;
	}

	public String errorNomenclaturaSobraHidruro() {
		String patronError = "";
		String patron = patron();
		patron = Strings.eliminarAcentos(patron).toLowerCase();
		if (!"".equals(patron)) {
			if (idIdioma == EN) {
				if (patron.contains("hydrogen")) {
					patronError = "*hydride*" ;
				}
			} else {
				if (patron.contains("hidrogeno")) {
					patronError = "*hidruro*" ;
				}
			}
		}
 		return patronError;
	}

	/** Uso incorrecto del numero de oxidacion cuando en realidad no hace falta
	 * porque hay un solo valor
	 */
	public String errorNomenclaturaStockConNumeroOxidacion() {
		String patronError = "";
		String patron = patronStock();
		int p = patron.lastIndexOf("\\("); // si hay numero entre parentesis
		if (p<0 && this instanceof CompuestoBinario) {
			p = patron.lastIndexOf(" ");
			patronError = patron.substring(0,p); // se elimina el cation
			Cation c = ((CompuestoBinario)this).getCation();
			patronError += c.errorNumeroOxidacionPatronStock();
		}
 		return patronError;
	}

	/** Uso incorrecto del numero de oxidacion cuando en realidad no hace falta
	 * porque hay un solo valor
	 */
	public String errorNomenclaturaTradicional() {
		String patronError = "";
		String patron = patronTradicional();
		if (this instanceof Oxoacido) {
			int p = patron.lastIndexOf(" "); 
			patronError = patron.substring(0,p)+" "; // se elimina el nombre del acido
			Oxoacido ox = ((Oxoacido)this);
			patronError += ox.errorNomenclaturaClasica(); // se sustituye por un patron de error
		}
 		return patronError;
	}

	/** Falta poner el numero de oxidacion
	 * necesario porque hay mas de un posible valor
	 */
	public String errorNomenclaturaStockSinNumeroOxidacion() {
		String patronError = "";
		String patron = patronStock();
		int p = patron.lastIndexOf("\\("); // si hay numero entre parentesis
		if (p>0) {
			patronError = patron.substring(0,p); // se elimina el numero
		}
 		return patronError;
	}
	
	/** Confunde el numero de átomos con el número de oxidacion 
	 * en la notacion de Stock
	 */
	public String errorNomenclaturaNumeroOxidacion() {
		String patronError = "";
		String patron = patronStock();
		if (this instanceof CompuestoBinario && !"".equals(patron))  {
				CompuestoBinario compuesto = (CompuestoBinario)this;
				int nAtomos = compuesto.anion.numeroAtomos();
				int p = patron.lastIndexOf("\\("); // si hay numero entre parentesis
				if (p>0) {
					patronError = patron.substring(0,p); // se elimina el numero
				} else {
					patronError = patron;
				}
				patronError += "\\("+ new Roman(nAtomos) + "\\)";
		}
 		return patronError;
	}
	
	public String errorNomenclaturaLatina(List<Integer> listaNomenclaturas) {
		String patron = patron(listaNomenclaturas);
		patron = Strings.eliminarAcentos(patron).toLowerCase();
		String patronError = patron;
		ArrayList<String> componentes = componentes(formula());
		if (componentes!=null) {
			for(int i=0; i<componentes.size(); i++) {
				String simbolo = componentes.get(i);
				Elemento elemento = TablaPeriodica.selectElemento(simbolo);
				switch(simbolo) {
					case "S": 
						patronError = patronError.replaceAll("sulfur","azufrur"); 
						patronError = patronError.replaceAll("sulf","azufr"); 
						break;
					case "N": 
					case "As": 
					case "Mn": 
					case "Au": 
					case "Ag": 
					case "Pb": 
					case "Fe": 
					case "Cu": 
						String raizLatina = Cation.raiz(elemento);
						String raiz = Cation.raiz(elemento.nombre());
						patronError = patronError.replaceAll(raizLatina, raiz.toLowerCase());
						break;
					default:
				}
			}
		}
		if (patronError.equals(patron)) {
			patronError = "";
		}
 		return patronError;
	}
	
	/* *********************************************** */

	public static void error(String stFormula) {
		String NO_ENCONTRADO = Strings.filterHTMLtoTXT("No se ha encontrado ning&uacute;n compuesto");
		String QUISO_DECIR = Strings.filterHTMLtoTXT("&iquest;Quiso decir? :");
		if (defaultLang == EN) {
			NO_ENCONTRADO = "No compound found";
			QUISO_DECIR = "Did you mean? :";
		}
		System.out.println(NO_ENCONTRADO);
		System.out.println(QUISO_DECIR);
		Formula formula;
		try {
			formula = new Formula(stFormula);
			HashSet<String> cc = new HashSet<String>();
			// Intentar simplificar
			Formula formulaSimplificada = new Formula(formula);
			formulaSimplificada.simplificar();
			if (!formula.equals(formulaSimplificada)) {
				Compuesto compuesto = analizar(formulaSimplificada.toString());
				if (compuesto!=null) {
					System.out.println(compuesto);
					cc.add(compuesto.formula());
					// return;
				}
			}
			// Intentar permutaciones
			ArrayList<Formula> variaciones = formula.permutaciones();
			for(Formula variacion : variaciones) {
				Compuesto compuesto = analizar(variacion.toString());
				if (compuesto!=null && !cc.contains(compuesto.formula())) {
					System.out.println(compuesto);
					cc.add(compuesto.formula());
					// return;
				}
			}
			variaciones = formula.variacionesAtomos();
			for(Formula variacion : variaciones) {
				// System.out.println(variacion);
				Compuesto compuesto = analizar(variacion.toString());
				if (compuesto!=null && !cc.contains(compuesto.formula())) {
					System.out.println(compuesto);
					cc.add(compuesto.formula());
					// return;
				}
			}
			variaciones = formula.faltaH();
			for(Formula variacion : variaciones) {
				// System.out.println(variacion);
				Compuesto compuesto = analizar(variacion.toString());
				if (compuesto!=null && !cc.contains(compuesto.formula())) {
					System.out.println(compuesto);
					cc.add(compuesto.formula());
					// return;
				}
			}
		} catch (FormulaException e) {
		}
	}
	
	
	/////////////////////////////////////////////////
	//////////////   toString   /////////////////////
	/////////////////////////////////////////////////

	
	public String toString() {
		/*
		String nSistematica = nomenclaturaSistematica();
		String nStock = nomenclaturaStock();;
		String nHidrogeno = nomenclaturaDeHidrogeno();
		String nAdicion = nomenclaturaDeAdicion();
		String nTradicional = nomenclaturaTradicional(); // admitida por IUPAC
		String nClasica = nomenclaturaClasica(); // ya no admitida por IUPAC
		String nombre = nSistematica 
					  + (!nStock.equals("") ? "; "+nStock : "")
					  + (!nHidrogeno.equals("") ? "; "+nHidrogeno : "")
					  + (!nAdicion.equals("") ? "; "+nAdicion : "")
					  + (!nTradicional.equals("") ? "; "+nTradicional : "")
					  ;
		// String patron = patron();
		nombre = Strings.filterHTMLtoTXT(nombre);
		// patron = Strings.filterHTMLtoTXT(patron);
		 */
		List<Integer> nomenclaturas = nomenclaturasPorDefecto();
		String nombre = nombreCompleto(nomenclaturas);
		return    formula() 
				+ ": " + nombre  
				// + " ; "+ patron
				;
	}
	
	/**
	 * Dos compuestos son iguales si tienen la misma formula
	 */
	@Override
	public boolean equals(Object obj) {
		Compuesto c = (Compuesto) obj;
		return (formula().equals(c.formula()));
	}
	
	public int nHidrogeno() {
		try {
			Formula formula = new Formula(formula());
			return formula.nHidrogeno();
		} catch (FormulaException e) {
			return 0;
		}
	}

	public static void main(String[] args) throws FormulaException {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica.selectCurso("3ESO");

		String[] formulas = {  "P3N", "CO2", "SO2", "SO3", "In2O6", "ZnO","P2O5", "O3Cl2", "CrP2", "H3BO3", "CTe2"
				              , "NH4NO3", "H3BO3", "RaHBrO2", "BH3", "SbH3"
				              , "PbO2", "AsMn", "PtS", "PtS2"
				              , "O3I2", "Ge(HTe)2", "HAtO3", "ZnO2", "HF",  "Pb(OH)2", "H4SO4", "O7At2", "PH3", "In(OH)3"
				              , "NaHS", "HCN", "KCN", "OF2", "HgFO", "K2SO4", "H2F4O3", "H2O", "CO2", "O3Cl2", "Cl2O3", "SO2", "Fe2(SO4)3", "NaHCr2O7", "Fe(NO3)2", "NaClO" 
		                      , "SO2" , "O5I2", "O4I2", "FeH3", "BaH2", "H2O2", "NaOH", "Pb(OH)4" 
		                      , "H4O2", "H2SO4", "H4S2O8", "SO4H2", "H2O4S", "HSO4", "SO4","H3SO4", "H2S2O2", "H2OS4" 
			                  , "KMnO4", "K2Cr2O7", "K2CrO4" 
							  , "HNO4" 
		                      , "HPO2" , "H3BO3" 
		                      , "HBO2" , "HBO3", "H2PHO3" 
		                      , "H2S2O3", "H4P2O7", "H3PO4" 
		                      , "Fe(ClO3)2", "Au2(SO4)3", "Pb(NO3)4" , "Fe(HCO3)2", "Na2SO4", "NaHSO4", "Na2SO3", "NaHSO3"
		                      , "H2SO3", "Na2SO3"
		                      , "HF", "HCN","H2S", "H2Se", "HCl" ,"HI"
		                      ,  "HBO2", "H3BO3","H2B4O7", "H2MnO3", "H2MnO4", "HMnO4", "H2CrO4", "H2Cr2O7"  // especiales
		                      , "H3NO", "H3NO3", "H2N2O2" // especiales
		}; 
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula,true);
			if (compuesto!=null) {
				compuesto.idIdioma(ES);
				System.out.println(compuesto.formula() + " - " + compuesto.nombre());
				System.out.println(compuesto.nombre());
				System.out.println(compuesto.nomenclaturaClasica());
				ConjuntoCompuestos ccs = compuesto.compuestosSimilares();
				for(Compuesto c : ccs) {
					System.out.println("    "+c);
					c.idIdioma(EN);
					System.out.println("    "+c);
					c.idIdioma(ES);
				}
				
				System.out.println("1." + compuesto.patron());
				System.out.println("2." + compuesto.nomenclaturaStock());
				System.out.println("3." + compuesto.patronClasico());
				// System.out.println("4." + compuesto.errorOrdenElementosGeneral());
				// System.out.println("5." + compuesto.errorOrdenElementos());
				System.out.println("5a." + compuesto.nombre( compuesto.nomenclaturasPorDefecto() ));
				System.out.println("5b." + compuesto.nombreCompleto( compuesto.nomenclaturasPorDefecto() ));
				// System.out.println("6."  + compuesto.errorNomenclaturaStockConNumeroOxidacion());
				
				// System.out.println("7."+compuesto.errorNomenclaturaStockSinNumeroOxidacion());
				// System.out.println("8."+compuesto.errorNomenclaturaNumeroOxidacion());
				// System.out.println(compuesto.errorElectronegativoSalBinaria());
				// System.out.println(compuesto.formulaHTML());
				// System.out.println(compuesto.errorPrefijosNumeroAtomos());
				// System.out.println(compuesto.errorRomanosNumeroAtomos());
				// System.out.println(compuesto.errorPrefijosDesordenados());
				// System.out.println(compuesto.nombre(Arrays.asList(10275)));
				// System.out.println(compuesto.errorSinSubindices(compuesto.nombre(Arrays.asList(10275))) );
				// System.out.println("16." + compuesto.errorNomenclaturaOrdenacion());
				// System.out.println("17." + compuesto.errorNomenclaturaFaltaUro());
				// System.out.println("18." + compuesto.errorNomenclaturaFaltaAno());
				// System.out.println("19." + compuesto.errorPrefijosDesconocidos());
				// System.out.println("20." + compuesto.errorNomenclaturaLatina(Arrays.asList(10274,10275,10276,10277,10278)));
				// System.out.println(compuesto.errorNumeroAtomos());
				// System.out.println(compuesto.errorFaltaH());
				System.out.println("21." + compuesto.errorNombreElemento(Arrays.asList(10274,10275,10276,10277,10278)));
				
				// String patron = compuesto.errorNomenclaturaFaltaUro();
				// String patron = "*(clor(ato|ito|ano)|cloro )*";
				// ExpresionRegular er = new ExpresionRegular(patron, true,true,true,true);
				// System.out.println(er.pertenece("clorato de potasa"));
				
				Integer[] subindices = subindices(formula);
				for(int i=0; i<subindices.length; i++) {
					System.out.print(subindices[i]+" ");
				}
				System.out.println();

			}
		}
		
		tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<1000; i++) {
			Compuesto compuesto = Compuesto.random(tablaPeriodica);		
			System.out.println(compuesto.formula() + ": " +compuesto.nombre());
			ConjuntoCompuestos ccs = compuesto.compuestosSimilares();
			for(Compuesto c : ccs) {
				System.out.println("    "+c);
				c.idIdioma(EN);
				System.out.println("    "+c);
				c.idIdioma(ES);
			}
		}
		
	}


	
}
