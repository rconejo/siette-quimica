package siette.quimica;

import java.util.ArrayList;
import java.util.HashSet;
import siette.util.Random;
import siette.util.text.Strings;

public class Poliacido extends Oxoacido {
	
	
	protected static HashSet<String> excepciones;
	static {
		// Formulas que generan la misma nomenclatura tradicional y que no he conseguido desambiguar
		excepciones = new HashSet<String>();
		excepciones.add("H2Si2O5");   // Ácido disilícico = H6Si2O7
		excepciones.add("H6Si2O7");   // Ácido disilícico = H2Si2O5
		excepciones.add("H6Si2O5");   // Ácido disilicoso = H2Si2O3
		excepciones.add("H2Si2O3");   // Ácido disilicoso = H6Si2O5
		excepciones.add("H10Si4O13"); // Ácido tetrasilícico = H2Si4O9
		excepciones.add("H2Si4O9");   // Ácido tetrasilícico = H10Si4O13
		excepciones.add("H2Si4O5");   // Ácido tetrasilicoso = H10Si4O9
		excepciones.add("H10Si4O9");  // Ácido tetrasilicoso = H2Si4O5
	}	

	
	protected int nAcido;
	protected Oxoacido oxoacido;
	
	// Constructores
	
	public Poliacido(Oxoacido ox, int nAcido) {
		super(ox);
		// nAcido * HXO - (nAcido-1)*H2O
		this.oxoacido = ox;
		this.nAcido = nAcido;
		this.nHidrogeno = nHidrogeno*nAcido - (nAcido-1)*2;
		this.nElemento = nElemento*nAcido ;
		this.nOxigeno = nOxigeno*nAcido - (nAcido-1) ;
		simplificar();
	}
	
	// Métodos para generar un compuesto al azar
	public static Poliacido random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento(metalNoMetal);
		return random(tabla,elemento);
	}

	public static Poliacido random(TablaPeriodica tabla, Elemento elemento) {
		ConjuntoCompuestos cc = Poliacido.compuestos(elemento);
		if (cc!=null && !cc.isEmpty()) {
			return (Poliacido) tabla.select(cc);
		} else {
			System.out.println("No hay poliacidos para el elemento: "+elemento);
		}
		return null;
	}	

	public void idIdioma(int idIdioma) {
		super.idIdioma(idIdioma);
		oxoacido.idIdioma(idIdioma);
	}

	// Analizar
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
		ConjuntoCompuestos cc = Oxoacido.compuestos(e);
		ConjuntoCompuestos poliacidos = new ConjuntoCompuestos();
		for(Compuesto c : cc) {
			Oxoacido ox = (Oxoacido) c;
			if (ox.nHidrogeno > 1) {
				for(int nAcido=2; nAcido<=4; nAcido++) {
					Poliacido p = new Poliacido(ox, nAcido);
					poliacidos.add(p);
				}
			}
		}
		return poliacidos;
	}

	
	protected void simplificar() {
		super.simplificar();
	}

	

	/////////////////////////////////////////////////
	//////////////   Formula    /////////////////////
	/////////////////////////////////////////////////


	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String nomenclaturaDeAdicion() { 
		String nombre = "";
		return nombre;
	}

	@Override
	public String nomenclaturaClasica() {
		//Excepciones (Casos que no he podido resolver)
		if (excepciones.contains(formula())) return ""; 
		String nomenclatura = oxoacido.nomenclaturaClasica().toLowerCase();
		if (nomenclatura==null || nomenclatura.equals("")) return "";

		int nMulti = nAcido;
		if (idIdioma == Compuesto.EN) {
			nomenclatura = nomenclatura.substring(0, nomenclatura.length()-5);  // Quitar la palabra " acid"
		} else {
			nomenclatura = nomenclatura.substring(6); // Quitar la palabra "Acido "
			// nomenclatura = Strings.eliminarAcentos(nomenclatura);
		}
		// para evitar prefijos dobles i.e.: H4P2O5 * 2 - H2O == H3PO3 * 4 - 3(H2O)
		if (nomenclatura.startsWith("di")) {
			nomenclatura = nomenclatura.substring(2,nomenclatura.length());
			nMulti *= 2;
		} else if (nomenclatura.startsWith("piro") || nomenclatura.startsWith("pyro")) {
			nomenclatura = nomenclatura.substring(4,nomenclatura.length());
			nMulti *= 2;
		} else if (nomenclatura.startsWith("tri")) {
			nomenclatura = nomenclatura.substring(3,nomenclatura.length());
			nMulti *= 3;
		} else if (nomenclatura.startsWith("tetra")) {
			nomenclatura = nomenclatura.substring(5,nomenclatura.length());
			nMulti *= 4;
		}
		if (idIdioma == Compuesto.EN) {
			nomenclatura = Ion.prefijo(nMulti) + nomenclatura.toLowerCase() + " acid";
		} else {
			nomenclatura = Strings.filterHTMLtoTXT("&Aacute;cido")+ " " + Ion.prefijo(nMulti) + nomenclatura;
		}
		return cap(nomenclatura);
	}

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public String patronTradicional() {
		if (elemento.equals("Cr") || elemento.equals("Mn")) {
			return "";  // No se admiten en la IUPAC "acido cromico" ni "acido manganico"
		} else {
			return patronClasico();
		}
	}

	@Override
	public String patronClasico() {
		//Excepciones (Casos que no he podido resolver)
		if (excepciones.contains(formula())) return ""; 
		String patron = oxoacido.patronClasico();
		if (patron == null || patron.equals("")) return "";

		int nMulti = nAcido;
		if (idIdioma == Compuesto.EN) {
			patron = patron.substring(0, patron.length()-5);  // Quitar la palabra " acid"
		} else {
			patron = patron.substring(6); // Quitar la palabra "Acido "
			patron = Strings.eliminarAcentos(patron);
		}
		// para evitar prefijos dobles i.e.: H4P2O5 * 2 - H2O == H3PO3 * 4 - 3(H2O)
		if (patron.startsWith("(piro|pyro|di)")) {
			patron = patron.substring(14,patron.length());
			nMulti *= 2;
		} else if (patron.startsWith("tri")) {
			patron = patron.substring(3,patron.length());
			nMulti *= 3;
		} else if (patron.startsWith("tetra")) {
			patron = patron.substring(5,patron.length());
			nMulti *= 4;
		}
		if (idIdioma == Compuesto.EN) {
			patron = Ion.prefijo(nMulti) + patron + " acid";
		} else {
			patron = Strings.filterHTMLtoTXT("&Aacute;cido")+ " " + Ion.prefijo(nMulti) + patron;
		}
		return patron;
	}

	
	/////////////////////////////////////////////////

	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");

		
		// String[] formulas = { "HClO3", "H2SO4","HBrO", "HNO2", "H2CO3" }; 
		// String[] formulas = { "HNO3", "H2S2O7","H2MnO4", "H4SiO4", "H2SO4", "H2Cr2O7","H2CrO4"  }; 
		// String[] formulas = { "H3BO3", "H2SiO3","H4SiO4", "HPO", "H4P2O3", "H3PO2", "HPO2", "H4P2O5", "H3PO3", "HPO3", "H4P2O7", "H3PO4" }; 
		String[] formulas = { "H6Si2O7", "H4P2O7", "H5P3O10", "H2S2O7", "H2S2O5"};
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula,true);
			if (compuesto!=null) {
				System.out.println(compuesto);
			}
		}
		
		for(int i=0; i<100; i++) {
			Poliacido acido = Poliacido.random(tablaPeriodica);		
			System.out.println(acido);
			Compuesto compuesto = analizar(acido.formula(),true);
			System.out.println(compuesto);
		}
		

	}


}
