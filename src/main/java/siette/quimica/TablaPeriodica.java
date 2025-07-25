package siette.quimica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import siette.util.Random;
import siette.util.RandomException;
import siette.util.corpus.TableCSV;
import siette.util.regex.ExpresionRegular;
import siette.util.text.Strings;
import siette.util.corpus.Row;

public class TablaPeriodica extends TableCSV {
	
	public final static int ES = 1;
	public final static int EN = 2;
	
	public final static String ESO3 = "3ESO";
	public final static String ESO4 = "4ESO";
	

	final static TablaPeriodica TABLAPERIODICA;	
	private static String FILENAME = "/TablaPeriodica.csv";
	static {
		TABLAPERIODICA = new TablaPeriodica(FILENAME);
		TABLAPERIODICA.setNotExclusive();
		// System.out.println("TABLAPERIODICA.size()="+TABLAPERIODICA.size());
	}
	
	// usado solamente por el objeto TABLAPERIODICA
	private TablaPeriodica(String filename) {
		super(filename, true, ";");
		setExactMatch();
	}	


	public final static int NUMERO = TABLAPERIODICA.find("NUMERO_ATOMICO");;
	public final static int SIMBOLO = TABLAPERIODICA.find("SIMBOLO");
	public final static int NOMBRE = TABLAPERIODICA.find("NOMBRE");
	public final static int PATRON = TABLAPERIODICA.find("PATRON");
	public final static int MASA = TABLAPERIODICA.find("MASA_ATOMICA");
	public final static int DENSIDAD = TABLAPERIODICA.find("DENSIDAD");
	public final static int PFUSION = TABLAPERIODICA.find("PUNTO_FUSION");
	public final static int PEBULLICION = TABLAPERIODICA.find("PUNTO_EBUILLICION");
	public final static int ELECTRONEGATIVIDAD = TABLAPERIODICA.find("ELECTRONEGATIVIDAD");
	public final static int DESCUBRIMIENTO = TABLAPERIODICA.find("DESCUBRIMIENTO");
	public final static int VAENCIA = TABLAPERIODICA.find("VALENCIA");
	public final static int GRUPO = TABLAPERIODICA.find("GRUPO");
	public final static int PERIODO = TABLAPERIODICA.find("PERIODO");
	public final static int BLOQUE = TABLAPERIODICA.find("BLOQUE");
	public final static int ESTADO = TABLAPERIODICA.find("ESTADO");
	public final static int CATEGORIA = TABLAPERIODICA.find("CATEGORIA");
	public final static int SUBCATEGORIA = TABLAPERIODICA.find("SUBCATEGORIA");
	public final static int OXIDACION = TABLAPERIODICA.find("OXIDACION");
	public final static int CURSO = TABLAPERIODICA.find("CURSO");
	
	public final static int NAME = TABLAPERIODICA.find("NAME");
	
	public final static String DESINENCIAS = "(uro|ato|ito|ico|ano)";

	private static String PATRON_NOMBRES;
	static {
		PATRON_NOMBRES = TABLAPERIODICA.get(0, TablaPeriodica.PATRON);
		for(int i=1;  i<TABLAPERIODICA.size(); i++) { // TABLAPERIODICA.size(); i++) {
			String elemento = TABLAPERIODICA.get(i, TablaPeriodica.PATRON);
			PATRON_NOMBRES += "|"+elemento;
		}
		PATRON_NOMBRES = PATRON_NOMBRES.toLowerCase();
		PATRON_NOMBRES = Strings.filterHTMLtoUTF8(PATRON_NOMBRES);
		PATRON_NOMBRES = Strings.eliminarAcentos(PATRON_NOMBRES);
		PATRON_NOMBRES = "(" + PATRON_NOMBRES + ")";
	}
	
	private static String PATRON_NAMES;
	static {
		PATRON_NAMES = TABLAPERIODICA.get(0, TablaPeriodica.PATRON);
		for(int i=1;  i<TABLAPERIODICA.size(); i++) { // TABLAPERIODICA.size(); i++) {
			String elemento = TABLAPERIODICA.get(i, TablaPeriodica.NAME);
			PATRON_NAMES += "|"+elemento;
		}
		PATRON_NAMES = PATRON_NAMES.toLowerCase();
		PATRON_NAMES = Strings.filterHTMLtoUTF8(PATRON_NAMES);
		// PATRON_NAMES = Strings.eliminarAcentos(PATRON_NAMES);
		PATRON_NAMES = "(" + PATRON_NAMES + ")";
	}
	
	private static String PATRON_RAIZ_NOMBRES;
	static {
		Elemento elemento = TABLAPERIODICA.getElemento(0);
		String raiz = Cation.raiz(elemento);
		PATRON_RAIZ_NOMBRES = raiz;
		for(int i=1; i<TABLAPERIODICA.size(); i++) { // TABLAPERIODICA.size(); i++) {
			elemento = TABLAPERIODICA.getElemento(i);
			raiz = Cation.raiz(elemento);
			PATRON_RAIZ_NOMBRES += "|"+raiz;
		}
		PATRON_RAIZ_NOMBRES = PATRON_RAIZ_NOMBRES.toLowerCase();
		PATRON_RAIZ_NOMBRES = Strings.filterHTMLtoUTF8(PATRON_RAIZ_NOMBRES);
		PATRON_RAIZ_NOMBRES = Strings.eliminarAcentos(PATRON_RAIZ_NOMBRES);
		PATRON_RAIZ_NOMBRES = "(" + PATRON_RAIZ_NOMBRES + ")"+ DESINENCIAS;
	}

	public static String[] RAIZ_NOMBRES;
	static {
		Elemento elemento = TABLAPERIODICA.getElemento(0);
		RAIZ_NOMBRES = new String[TABLAPERIODICA.size()];
		for(int i=0; i<TABLAPERIODICA.size(); i++) { // TABLAPERIODICA.size(); i++) {
			elemento = TABLAPERIODICA.getElemento(i);
			String raiz = Cation.raiz(elemento);
			raiz = raiz.toLowerCase();
			raiz = Strings.filterHTMLtoUTF8(raiz);
			raiz = Strings.eliminarAcentos(raiz);
			RAIZ_NOMBRES[i] = raiz;
		}
	}

	private static String PATRON_RAIZ_NAMES;
	static {
		Elemento elemento = TABLAPERIODICA.getElemento(0);
		String raiz = Cation.root(elemento);
		PATRON_RAIZ_NAMES = raiz;
		for(int i=1; i<TABLAPERIODICA.size(); i++) { // TABLAPERIODICA.size(); i++) {
			elemento = TABLAPERIODICA.getElemento(i);
			raiz = Cation.root(elemento);
			PATRON_RAIZ_NAMES += "|"+raiz;
		}
		PATRON_RAIZ_NAMES = PATRON_RAIZ_NAMES.toLowerCase();
		PATRON_RAIZ_NAMES = Strings.filterHTMLtoUTF8(PATRON_RAIZ_NAMES);
		// PATRON_RAIZ_NAMES = Strings.eliminarAcentos(PATRON_RAIZ_NAMES);
		PATRON_RAIZ_NAMES = "(" + PATRON_RAIZ_NAMES + ")"+ DESINENCIAS;
	}

	public static String[] RAIZ_NAMES;
	static {
		Elemento elemento = TABLAPERIODICA.getElemento(0);
		RAIZ_NOMBRES = new String[TABLAPERIODICA.size()];
		for(int i=0; i<TABLAPERIODICA.size(); i++) { // TABLAPERIODICA.size(); i++) {
			elemento = TABLAPERIODICA.getElemento(i);
			String raiz = Cation.root(elemento);
			raiz = raiz.toLowerCase();
			raiz = Strings.filterHTMLtoUTF8(raiz);
			raiz = Strings.eliminarAcentos(raiz);
			RAIZ_NOMBRES[i] = raiz;
		}
	}

	public TablaPeriodica() {
		this(TABLAPERIODICA);
		setExactMatch();
	}	

	public TablaPeriodica(Random random) {
		this();
		setRandom(random);
	}	
	
	// Constructor de copia
	public TablaPeriodica(TableCSV t) {
		super(t);
	}
		
	public void setRandom(Random random) {
		super.setRandom(random);
	}
	
	public Random getRandom() {
		return random;
	}
	
	protected String curso;
	public TablaPeriodica selectCurso(String curso) {
		if (curso !=null) {
			this.curso = curso;
			setPartialMatch();
			TablaPeriodica t = selectTable(TablaPeriodica.CURSO, curso);
			t.setExactMatch();
			if (curso.equals(ESO3) || curso.equals(ESO4)) {
				Row row;
				try {
					// Se elimina la valencia 1 por inusual para estos casos
					row = selectRow(SIMBOLO, "P");
					row.set(OXIDACION, "3|5|-3"); 
					row = selectRow(SIMBOLO, "As");
					row.set(OXIDACION, "3|5|-3"); 
					row = selectRow(SIMBOLO, "Sb");
					row.set(OXIDACION, "3|5|-3"); 
				} catch (Exception e) {}
				Compuesto.nombreComunIUPAC.remove("BiH3"); //, "bismutano");
				Compuesto.nombreComunIUPAC.remove("SiH4"); //, "silano");
				Compuesto.nombreComunIUPAC.remove("GeH4"); //, "germano");
				Compuesto.nombreComunIUPAC.remove("SnH4"); //, "estannano");
				Compuesto.nombreComunIUPAC.remove("PbH4"); //, "plumbano");
				Compuesto.nombreComunIUPAC.remove("AlH3"); //, "alumano");
				Compuesto.nombreComunIUPAC.remove("GaH3"); //, "galano");
				Compuesto.nombreComunIUPAC.remove("InH3"); //, "indigano");
				Compuesto.nombreComunIUPAC.remove("TlH3"); //, "talano");
			} else {
				Compuesto.nombreComunIUPAC.put("BiH3", "bismutano");
				Compuesto.nombreComunIUPAC.put("SiH4", "silano");
				Compuesto.nombreComunIUPAC.put("GeH4", "germano");
				Compuesto.nombreComunIUPAC.put("SnH4", "estannano");
				Compuesto.nombreComunIUPAC.put("PbH4", "plumbano");
				Compuesto.nombreComunIUPAC.put("AlH3", "alumano");
				Compuesto.nombreComunIUPAC.put("GaH3", "galano");
				Compuesto.nombreComunIUPAC.put("InH3", "indigano");
				Compuesto.nombreComunIUPAC.put("TlH3", "talano");
			}
			return t;
		} else {
			return new TablaPeriodica();
		}
	}
	public String getCurso() {
		return curso;
	}
	
	public TablaPeriodica selectTable(int pos, String att) {
		TableCSV t = super.selectTable(pos, att);
		return  new TablaPeriodica(t);
	}

	public TablaPeriodica selectTable(int pos, String[] att) {
		TableCSV t = super.selectTable(pos, att);
		return  new TablaPeriodica(t);
	}

	public TablaPeriodica selectTable(int pos, int[] att) {
		TablaPeriodica t = new TablaPeriodica((TableCSV) clone());
		Set<Integer> setAtt = new HashSet<Integer>();
		for(int i=0; i<att.length; i++) {
			setAtt.add(att[i]);
		}
		for(int i=0; i<rows.size(); i++) {
			Elemento elemento = new Elemento(rows.get(i));
			Set<Integer> setElem = elemento.getSet(pos);
			if (setElem!=null) {
				setElem.retainAll(setAtt);
				if (!setElem.isEmpty()) {
					t.rows.add(rows.get(i));
				}
			}
		}
		return t;
	}

	public TablaPeriodica selectTable(int pos, String[] att, int posNeg, String[] attNeg) {
		TableCSV t = super.selectTable(pos, att, posNeg, attNeg);
		return  new TablaPeriodica(t);
	}

	public Elemento getElemento(int pos) {
		Row row = getRow(pos);
		Elemento elemento = new Elemento(row);
		return elemento;
	}

	public Elemento selectElemento(int pos) {
		Elemento elemento = null;
		try {
			Row row = selectRow(pos);
			elemento = new Elemento(row);
		} catch (RandomException e) {
		}
		return elemento;
	}

	public Elemento selectElemento(int pos, String att) {
		Elemento elemento = null;
		try {
			Row row = selectRow(pos, att);
			elemento = new Elemento(row);
		} catch (RandomException e) {
		}
		return elemento;
	}

	public Elemento selectElemento() {
		Row row = selectRow();
		Elemento elemento = new Elemento(row);
		return elemento;
	}
	
	public static Elemento selectElemento(String simbolo) {
		Elemento elemento = null;
		try {
			int matchMode = TABLAPERIODICA.matchMode;
			TABLAPERIODICA.setExactMatch();
			TABLAPERIODICA.setNotExclusive();
			Row row = TABLAPERIODICA.selectRow(SIMBOLO, simbolo);
			TABLAPERIODICA.matchMode = matchMode;
			elemento = new Elemento(row);
		} catch (RandomException e) {
			e.printStackTrace();
		}
		return elemento;
	}
	
	public static Elemento selectElementoNombre(String palabra, int idioma) {
		palabra.toLowerCase();
		Elemento elemento = null;
		for(int i=0; i<TABLAPERIODICA.size() && elemento==null; i++) {
			String nombreElemento = TABLAPERIODICA.get(i, idioma==ES ? TablaPeriodica.NOMBRE: TablaPeriodica.NAME);
			nombreElemento = Strings.filterHTMLtoUTF8(nombreElemento);
			nombreElemento = Strings.eliminarAcentos(nombreElemento);
			nombreElemento = nombreElemento.toLowerCase();
			if (palabra.contains(nombreElemento)) {
				Row row = TABLAPERIODICA.getRow(i);
				elemento = new Elemento(row);
				// Excepciones
				if (nombreElemento.contains("tin") && palabra.contains("protactin")) {
					elemento = TablaPeriodica.selectElemento("Pa");
				} else if (nombreElemento.contains("tin") && palabra.contains("actin")) {
					elemento = TablaPeriodica.selectElemento("Ac");
				} else if (nombreElemento.contains("tin") && palabra.contains("astatin")) {
					elemento = TablaPeriodica.selectElemento("At");
				} else if (nombreElemento.contains("tin") && palabra.contains("platin")) {
					elemento = TablaPeriodica.selectElemento("Pt");
				} else  if (palabra.contains("diterbi") ) {
					elemento = TablaPeriodica.selectElemento("Tb");
				} else  if (nombreElemento.contains("terbi") && (palabra.contains("iterbi") || palabra.contains("ytterbi"))) {
					elemento = TablaPeriodica.selectElemento("Yb");
				}

			}
			if (idioma == ES && elemento ==null) {
				String patron = TABLAPERIODICA.get(i, TablaPeriodica.PATRON);
				ExpresionRegular er = new ExpresionRegular(patron, true, true, true, true);
				if (er.pertenece(palabra) == 0) {
					Row row = TABLAPERIODICA.getRow(i);
					elemento = new Elemento(row);
				}
			}
		}
		return elemento;
	}
	
	
	public static Elemento selectElementoRaizNombre(String palabra, int idioma) {
		Elemento elemento = null;
		// Excepciones
		if (palabra.contains("thiodi") && !palabra.contains("thiodiod")) 
			palabra = palabra.replaceAll("thiodi", ""); // puede dar lugar a reconocer "iod" (yodo)
		if (palabra.contains("tiodi") && !palabra.contains("tiodiod")) 
			palabra = palabra.replaceAll("tiodi", "");
		if (palabra.contains("tetradeca")) // puede confundir con "rad" (radio)
			palabra = palabra.replaceAll("tetradeca", "");
		
		if (palabra.contains("protactin")) {
			elemento = TablaPeriodica.selectElemento("Pa");
		} else if (palabra.contains("actin")) {
			elemento = TablaPeriodica.selectElemento("Ac");
		} else if (   palabra.contains("astatin")  // confunde con "tin"
			       || palabra.contains("iodiastat") // confunde con "iod"
		           || palabra.contains("astat")) { 
			elemento = TablaPeriodica.selectElemento("At");
		} else if (palabra.contains("platin")) {
			elemento = TablaPeriodica.selectElemento("Pt");
		} else  if ( palabra.contains("diterbi") ) {
			elemento = TablaPeriodica.selectElemento("Tb");
		} else  if ((palabra.contains("iterbi") || palabra.contains("ytterbi"))) {
			elemento = TablaPeriodica.selectElemento("Yb");
		} else  if (palabra.contains("lawren") ) {
			elemento = TablaPeriodica.selectElemento("Lr");
		} else  if (palabra.contains("carb") ) {
			elemento = TablaPeriodica.selectElemento("C");
		} else  if (palabra.contains("aurani") ) { // tetr"aur"anio, se confunde con oro
			elemento = TablaPeriodica.selectElemento("U");
		} else  if (palabra.contains("seaborg") ) { // sea"bor"gio, se confunde con boro
			elemento = TablaPeriodica.selectElemento("Sg");
		}
		if (elemento==null) {
			for(int i=0; i<TABLAPERIODICA.size(); i++) { 
				elemento = TABLAPERIODICA.getElemento(i);
				if (elemento.simbolo().equals("H") || elemento.simbolo().equals("O")) continue; // se tratan aparte
				if (elemento.simbolo().equals("S"))  continue; // se trata al final
				if (elemento.simbolo().equals("Rh") && palabra.contains("sulfurod"))  continue; // caso raro, especial
				String raiz = idioma==ES ? Oxoacido.raiz(elemento) : Oxoacido.root(elemento);
				raiz = raiz.toLowerCase();
				if (raiz.equalsIgnoreCase(palabra)) {
					return elemento;
				} else {
					String patron = "*"+raiz +"*";
					ExpresionRegular er = new ExpresionRegular(patron, true, true, true, true);
					if (er.pertenece(palabra)>=0) {
						return elemento;
					} else {
						elemento = null;
					}
				}
			}
		}
		if (elemento == null && palabra.contains("sulf") || palabra.contains("azufr") ) {
			// se trata al final para poder reconocer los tioacidos tales como "trioxidosulfuroyodato"
			elemento = TablaPeriodica.selectElemento("S");
		}
		return elemento;
	}

	/// Selecciona un elemento al azar de enre los que estan preseleccionados en la subtabla 
	public Elemento selectElemento(TablaPeriodica subtabla) {
		TablaPeriodica tabla = new TablaPeriodica(subtabla); // subtabla suele ser un objeto estatico, hay que crear una nueva tabla
		tabla.setRandom(random);  // y asignature el mismo objeto random;
		if (curso!=null && !curso.equals("")) {
			tabla = tabla.selectCurso(curso);
		}
		Elemento elemento = new Elemento(tabla.selectRow());
		return elemento;
	}

	
	public boolean containsElemento(String simbolo) {
		Row row=null;;
		try {
			row = selectRow(SIMBOLO, simbolo);
		} catch (RandomException e) {
		}
		return row!=null;
	}

	
	public Object select(Set<?> set) {
		   return random.select(set);
	}

	public Object select(List<?> list) {
		   return random.select(list);
	}

	// acceso a metodos de la clase siette.Random
	
	public String randomSelect(String[] set) {
		return random.select(set);
	}

	public boolean nextBoolean() {
		return random.nextBoolean();
	}

	public int nextInt(int n1, int n2) {
		return random.nextInt(n1,n2);
	}
		
	public static String patronNombres() {
		return PATRON_NOMBRES;
	}
		
	public static String patronNombres(int idIdioma, ArrayList<String> componentes) {
		String patronNombres = (idIdioma == EN ? new String(PATRON_NAMES) : new String(PATRON_NOMBRES));
		if (componentes!=null) {
			for(int i=0; i<componentes.size(); i++) {
				Elemento elemento = selectElemento(componentes.get(i));
				if (elemento!=null) {
	        		String stElemento = Strings.eliminarAcentos(elemento.patron()).toLowerCase();
	        		stElemento = stElemento.replaceAll("\\|", "\\\\|");
	        		stElemento = stElemento.replaceAll("\\(", "\\\\(");
	        		stElemento = stElemento.replaceAll("\\)", "\\\\)");
					patronNombres = patronNombres.replaceAll(stElemento+"\\|", ""); // quitar el patron del elemento 
					// Excepcion
					if (stElemento.equals("fosforo")) {
						patronNombres = patronNombres.replaceAll("oro"+"\\|", "");
					}
				}
			}
		}
		return patronNombres;
	}
		
	public static String patronRaizNombres() {
		return PATRON_RAIZ_NOMBRES;
	}

	// Salvo las secuencias correspondientes a los componentes
	public static String patronRaizNombres(int idIdioma, ArrayList<String> componentes) {
		String patronRaices = (idIdioma == EN ? new String(PATRON_RAIZ_NAMES) : new String(PATRON_RAIZ_NOMBRES));
		if (componentes!=null) {
			for(int i=0; i<componentes.size(); i++) {
				Elemento elemento = selectElemento(componentes.get(i));
				if (elemento!=null) {
					String raiz = Cation.raiz(elemento).toLowerCase();
					patronRaices = patronRaices.replaceAll(raiz+"\\|", ""); // quitar la raiz del elemento 
				}
			}
		}
		return patronRaices;
	}


	
	public static void main(String[] argv) {
			Random $random = new Random();
			TablaPeriodica tabla = new TablaPeriodica($random); 
			// System.out.println("tabla=\n"+tabla);
			Row row = tabla.selectRow();
			for(String st: row) {
				System.out.println("row="+st);
			}
			Elemento elemento = tabla.selectElemento();
			System.out.println(elemento.nombre() + " "+elemento.simbolo()+" "+elemento.numero());
	}


	
}
