package siette.quimica;

import java.util.ArrayList;
import java.util.Set;

import siette.util.Random;

public class Hidroxido extends CompuestoBinario {
	
	 // Nombres comunes
	
	static {
		nombreComun.put("NaOH", "hidróxido sódico|sosa");
		nombreComun.put("KOH",  "hidróxido potásico|potasa");		
	}

	
	// Constructores
	
	public Hidroxido(Elemento e, int n) {
		super();
		setAnion(new IonHidroxido().setNumeroAtomos(n));
		setCation(e,n,1);
	}


	// Métodos para generar un compuesto al azar
	public static Hidroxido random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento(metales);
		return random(tabla,elemento);
	}
	public static Hidroxido random(TablaPeriodica tabla, Elemento elemento) {
		int valencia = (int) tabla.select(elemento.valenciasPositivas());
		return new Hidroxido(elemento, valencia);
	}
	
	// Metodos
	
	public static ConjuntoCompuestos compuestos(String simbolo) {
		Elemento elemento = TablaPeriodica.selectElemento(simbolo);
		return compuestos(elemento);
	}

	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		if (componentes.size()>2 && "O".equals(componentes.get(1)) && "H".equals(componentes.get(2))) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(0));
			ConjuntoCompuestos cc = compuestos(elemento);
			compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		if (metales.containsElemento(e.simbolo())) {
			Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
			for(Integer n: numerosOxidacion) {
				c.add(new Hidroxido(e,n));
			}
		}
		return c;
	}
		
	
	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////
	

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	
	public String patron() { 
		return patron(SISTEMATICA+STOCK); // opciones por defecto
	}



	/////////////////////////////////////////////////

	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");

		Hidroxido sosa = Hidroxido.random(tablaPeriodica,TablaPeriodica.selectElemento("Na"));
		System.out.println(sosa);
		for(int i=0; i<100; i++) {
			Hidroxido h = Hidroxido.random(tablaPeriodica);		
			System.out.println(h);
		}
	}

}
