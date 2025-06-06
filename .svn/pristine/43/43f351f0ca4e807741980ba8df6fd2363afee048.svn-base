package siette.quimica;

import java.util.ArrayList;
import java.util.Set;

import siette.util.Random;
import siette.util.text.Strings;

public class Oxido extends CompuestoBinario {
	
	 // Nombres comunes

	static {
	}
	
	// Constructores
	

	public Oxido(Elemento e, int valencia) {
		setAnion(OXIGENO,-2,valencia);
		setCation(e,valencia,2);
		simplificar();
	}
	
	/////////////////////////////////////////////////
	//////////////////// random /////////////////////
	/////////////////////////////////////////////////

	// Métodos para generar un compuesto al azar
	public static Oxido random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento(oxidables);
		return random(tabla,elemento);
	}
	public static Oxido random(TablaPeriodica tabla, Elemento elemento) {
		int valencia = (int) tabla.select(elemento.valenciasPositivas());
		return new Oxido(elemento, valencia);
	}

	
	/////////////////////////////////////////////////
	////////////////// analizar /////////////////////
	/////////////////////////////////////////////////

	public static Compuesto analizar(ArrayList<String> componentes, String target, int idioma ) {
		Compuesto compuesto = null;
		if (componentes.size()>1 && "O".equals(componentes.get(1))) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(0));
			ConjuntoCompuestos cc = compuestos(elemento);
			compuesto = idioma == NINGUNO ? cc.buscar(target) : cc.buscar(target, idioma);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		if (oxidables.containsElemento(e.simbolo())) {
			// solo para elemento que pueden formar oxidos
			Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
			for(Integer n: numerosOxidacion) {
				c.add(new Oxido(e,n));
			}
		}
		return c;
	}
	
	
	// Contabilidad de hidrogeno y oxigeno
	public int nOxigeno() {
		return anion.numeroAtomos;
	}
	
	public int nElemento() {
		return cation.numeroAtomos;
	}


	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String nomenclaturaClasica() {
		CompuestoUnario c = (CompuestoUnario) cation.getCompuesto();
		Elemento e = c.getElemento();
		String nombre = super.nomenclaturaClasica(); 
		if (!metales.containsElemento(e.simbolo())) {
			if ( idIdioma == ES) {
				nombre = Strings.filterHTMLtoTXT("Anh&iacute;drido") + " " + cation.nomenclaturaClasica();
			} else if (idIdioma == EN) {
				nombre = cation.nomenclaturaClasica() + " anhydride";
			}
		} else {
			if (idIdioma == Compuesto.EN) {
				nombre = cation.nomenclaturaClasica() + " oxide";
			} else {
				nombre = Strings.filterHTMLtoTXT("&Oacute;xido") + " " + cation.nomenclaturaClasica();
			}
		}
		return cap(nombre);
	}

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	

	////////////////////////////////////////

	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		tablaPeriodica.selectCurso("3ESO");
		
		Elemento elemento  = TablaPeriodica.selectElemento("C");
		Oxido ox1 = new Oxido(elemento,4);
		ox1.idIdioma(EN);
		System.out.println(ox1);
		
		
		String[] formulas = {  "H2O", "Cs2O", "SO", "SO2", "SO3", "SeO2",  "S2O3",   "NO", "NO2", "N2O3", "N2O5", "CO2" , "Cl2O7" };
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula);
			if (compuesto!=null) {
				System.out.println(compuesto);
				compuesto.idIdioma(EN);
				System.out.println(compuesto);
			}
		}

		Oxido ox = Oxido.random(tablaPeriodica, HIDROGENO);
		System.out.println(ox);
		ox = Oxido.random(tablaPeriodica,TablaPeriodica.selectElemento("Au"));
		System.out.println(ox);
		for(int i=0; i<100; i++) {
			ox = Oxido.random(tablaPeriodica);		
			System.out.println(ox);
			ox.idIdioma(EN);
			System.out.println(ox);
		}
		

	}

}
