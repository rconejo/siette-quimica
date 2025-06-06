package siette.quimica;

import java.util.ArrayList;
import siette.util.Random;
import siette.util.text.Strings;

public class Peroxoacido extends Oxoacido {
	
	// Constructores
	
	public Peroxoacido(Oxoacido ox) {
		super(ox);
		nOxigeno++;
	}
	
	public Peroxoacido(Elemento e, int n, String poli) {
		super(e,n,poli);
		nOxigeno++;
	}
	
	// Métodos para generar un compuesto al azar
	public static Peroxoacido random(TablaPeriodica tabla) {
		Oxoacido ox = Oxoacido.random(tabla);
		return new Peroxoacido(ox);
	}

	public static Peroxoacido random(TablaPeriodica tabla, String poli) {
		Oxoacido ox = Oxoacido.random(tabla, poli);
		return new Peroxoacido(ox);
	}	

	public static Peroxoacido random(TablaPeriodica tabla, Elemento elemento) {
		Oxoacido ox = Oxoacido.random(tabla, elemento);
		return new Peroxoacido(ox);
	}	

	public static Peroxoacido random(TablaPeriodica tabla, Elemento elemento, String poli) {
		Oxoacido ox = Oxoacido.random(tabla, elemento, poli);
		return new Peroxoacido(ox);
	}	

	
	// Metodos
	

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
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		ConjuntoCompuestos cox = Oxoacido.compuestos(e);
		for(Compuesto ox: cox) {
			Peroxoacido pox = new Peroxoacido((Oxoacido) ox);
			c.add(pox);
		}
		return c;
	}

	

	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////
	@Override
	public String nomenclaturaSistematica() {  
		String hidrogeno = (idIdioma==Compuesto.EN ? "hydrogen" : "hidrogeno");
		String ato = (idIdioma==Compuesto.EN ? "ate" : "ato");
		String oxo = (idIdioma==Compuesto.EN ? "oxo" : "oxo");
		String nombre = "";
		String raiz = (idIdioma==Compuesto.EN ? Oxoacido.root(elemento).toLowerCase() : Oxoacido.raiz(elemento).toLowerCase());
		String nombreOxido = (nOxigeno>1?Ion.nombre(oxo, nOxigeno-2):oxo) + "per"+oxo + (nElemento>1 ? Ion.nombre(raiz,nElemento):raiz) + ato;
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
						  + "per"+oxo
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
	public String nomenclaturaDeAdicion() { 
		String nombre = "";
		return nombre;
	}

	@Override
	public String nomenclaturaClasica() {
		Oxoacido ox = new Oxoacido(this);
		ox.nOxigeno--;
		ox.idIdioma(idIdioma);
		String nomenclatura = ox.nomenclaturaClasica();
		if (nomenclatura!=null && !nomenclatura.equals("")) {
			if (idIdioma == EN) {
				nomenclatura = "Peroxi" + nomenclatura.toLowerCase();
			} else {
				nomenclatura = nomenclatura.substring(6); // Quitar la palabra "Acido "
				nomenclatura = Strings.filterHTMLtoTXT("&Aacute;cido")+ " peroxi" + nomenclatura;
			}
		}
		return nomenclatura;
	}

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String patronClasico() {
		Oxoacido ox = new Oxoacido(this);
		ox.nOxigeno--;
		ox.idIdioma(idIdioma);
		String patron = ox.patronClasico();
		if (patron!=null && !patron.equals("")) {
			if (idIdioma == EN) {
				patron = "perox(o|i)" + patron.toLowerCase() ;
			} else {
				patron = patron.substring(6); // Quitar la palabra "Acido "
				patron = Strings.filterHTMLtoTXT("&aacute;cido")+ " " + "perox(o|i)" + patron;
			}
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
		String[] formulas = {  "HNO4", "HNO3", "H2SO5", "H3PO3", "H4P2O8" };
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula,true);
			if (compuesto!=null) {
				System.out.println(compuesto);
			}
		}
		
		for(int i=0; i<100; i++) {
			Oxoacido acido = Oxoacido.random(tablaPeriodica);		
			System.out.println(acido);
		}

	}

	
}
