package siette.quimica;

import java.util.ArrayList;
import java.util.Set;

import siette.util.Random;
import siette.util.text.Strings;

public class Peroxido extends CompuestoBinario {
		
	// Constructores
	
	public Peroxido(Elemento e, int n) {
		super();
		setAnion(OXIGENO,-1,n*2);
		setCation(e,n,2);
		simplificar();
	}
	
	// Métodos para generar un compuesto al azar
	public static Peroxido random(TablaPeriodica tabla) {
		Elemento elemento = HIDROGENO; 
		if (tabla.nextInt(0, 9) != 0) { // 9 de cada diez son distintos 
			elemento = tabla.selectElemento(metales);
		}
		return random(tabla,elemento);
	}
	public static Peroxido random(TablaPeriodica tabla, Elemento elemento) {
		int valencia = (int) tabla.select(elemento.valenciasPositivas());
		return new Peroxido(elemento, valencia);
	}


	// Metodos
	
	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		if (componentes.size()>1 && "O".equals(componentes.get(1))) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(0));
			ConjuntoCompuestos cc = compuestos(elemento);
			compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		if (oxidables.containsElemento(e.simbolo())) {
			// solo para elemento que pueden formar peroxidos
			Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
			for(Integer n: numerosOxidacion) {
				c.add(new Peroxido(e,n));
			}
		}
		return c;
	}
	
	
	protected void simplificar() {
		super.simplificar();
		if (anion.numeroAtomos%2 != 0) {
			anion.numeroAtomos *= 2;
			cation.numeroAtomos *= 2;
		}
	}	

	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	
	public String nomenclaturaStock() {
		String nombre;
		if (idIdioma == Compuesto.EN) {
			nombre = cation.nomenclaturaStock() + " peroxide";
		} else {
			nombre = Strings.filterHTMLtoTXT("Per&oacute;xido") + " " + cation.nomenclaturaStock();
		}
		return nombre;
	}
	
		
	
	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String patronSistematica() {
		// Set<Integer> numerosOxidacion = cation.numerosOxidacionPositivos();
		String nombreAnion = anion.nomenclaturaSistematica(true).toLowerCase(); // forzar a que el prefijo del anion se ponga siempre
		String nombre;
		if (idIdioma == Compuesto.EN) {
			nombre = cation.patronSistematica(false) + " " + nombreAnion;
		} else {
			nombre = nombreAnion + " " + cation.patronSistematica(false);
		}
		return nombre;
	}

	public String patronStock() {
		String nombre;
		if (idIdioma == Compuesto.EN) {
			nombre = cation.patronStock() + " peroxide";
		} else {
			nombre = Strings.filterHTMLtoTXT("per&oacute;xido") + " " + cation.patronStock();
		}
		return nombre;
	}
	
	@Override
	public String nomenclaturaClasica() {
		String nombre = super.nomenclaturaClasica();
		if ("".equals(nombre)) {
			if (idIdioma == Compuesto.EN) {
				nombre = cation.nomenclaturaClasica() + " peroxide";
			} else {
				nombre = Strings.filterHTMLtoTXT("per&oacute;xido") + " " + cation.nomenclaturaClasica();
			}
		}
		return cap(nombre);
	}

	/////////////////////////////////////////////////

	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		tablaPeriodica.selectCurso("3ESO");
		
		Peroxido H2O2 = Peroxido.random(tablaPeriodica, HIDROGENO);
		System.out.println(H2O2);
		for(int i=0; i<100; i++) {
			Peroxido ox = Peroxido.random(tablaPeriodica);		
			System.out.println(ox);
			ox.idIdioma(EN);
			System.out.println(ox);
		}
	}

}
