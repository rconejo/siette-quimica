package siette.quimica;

import java.util.ArrayList;
import java.util.Set;

import siette.util.Random;

public class Hidruro extends CompuestoBinario {
	

	 // Tablas de elementos
	
	protected static TablaPeriodica cationesNoMetalicos;
	static {
		cationesNoMetalicos = TablaPeriodica.TABLAPERIODICA; 
		// System.out.println("tabla.size(1)="+cationesNoMetalicos.size());
		cationesNoMetalicos = cationesNoMetalicos.selectTable(TablaPeriodica.CATEGORIA, new String[] {"No metales","Metaloides"} );
		// System.out.println("tabla.size(3)="+cationesNoMetalicos.size());
		cationesNoMetalicos = cationesNoMetalicos.selectTable(TablaPeriodica.GRUPO, new String[] {"13","14","15"} ); // grupos 13, 14 o 15		
		// System.out.println("tabla.size(4)="+cationesNoMetalicos.size());
		cationesNoMetalicos = cationesNoMetalicos.selectTable(TablaPeriodica.OXIDACION, new int[] {1,2,3,4,5,6,7,8} ); // valencia positiva		
		// System.out.println("tabla.size(4)="+cationesNoMetalicos.size());
		cationesNoMetalicos.setNotExclusive(); // 8 
	}

	protected static TablaPeriodica anionesNoMetalicos;
	static {
		TablaPeriodica tabla = TablaPeriodica.TABLAPERIODICA; 
		// System.out.println("tabla.size(1)="+cationesNoMetalicos.size());
		tabla = tabla.selectTable(TablaPeriodica.CATEGORIA, new String[] {"No metales","Metaloides"} );
		// System.out.println("tabla.size(3)="+cationesNoMetalicos.size());
		tabla = tabla.selectTable(TablaPeriodica.GRUPO, new String[] {"16","17"} ); // grupos 16, y 17	
		// System.out.println("tabla.size(4)="+cationesNoMetalicos.size());
		tabla = tabla.selectTable(TablaPeriodica.OXIDACION, new int[] {-1,-2,-3,-4,-5,-6,-7,-8} ); // valencia negativa		
		// System.out.println("tabla.size(4)="+cationesNoMetalicos.size());
		tabla.delRow(TablaPeriodica.SIMBOLO, "O");
		// System.out.println("tabla.size(4)="+cationesNoMetalicos.size());
		tabla.setNotExclusive(); // 8 
		anionesNoMetalicos = tabla;
	}

	protected static TablaPeriodica iones;
	static {
		iones = new TablaPeriodica(metales); 
		iones.addTable(cationesNoMetalicos);
		iones.addTable(anionesNoMetalicos);
	}	
	
	
	
	
	// Constructores
	
	public Hidruro(Elemento e, int n) {
		super();
		if (anionesNoMetalicos.containsElemento(e.simbolo())) {
			setCation(HIDROGENO,+1,-n);
			setAnion(e,n,1);
		} else if (cationesNoMetalicos.containsElemento(e.simbolo())) {
			setAnion(HIDROGENO,-1,n);
			setCation(e,n,1);
		} else {
			setAnion(HIDROGENO,-1,n);
			setCation(e,n,1);
		}
	}

	// Métodos para generar un compuesto al azar
	public static Hidruro random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento(iones);
		return random(tabla,elemento);
	}
	public static Hidruro random(TablaPeriodica tabla, Elemento elemento) {
		int n;
		if (anionesNoMetalicos.containsElemento(elemento.simbolo()) ) {
			n = (Integer) tabla.select(elemento.valenciasNegativas());
		} else {
			n = (Integer) tabla.select(elemento.valenciasPositivas());			
		}
		return new Hidruro(elemento,n);
	}

	
	// Metodos
	
	public static ConjuntoCompuestos compuestos(String simbolo) {
		Elemento elemento = TablaPeriodica.selectElemento(simbolo);
		return compuestos(elemento);
	}

	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		if (componentes.size()==2 && "H".equals(componentes.get(1))) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(0));
			ConjuntoCompuestos cc = compuestos(elemento);
			compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
		} else if (componentes.size()==2 && "H".equals(componentes.get(0))) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(1));
			ConjuntoCompuestos cc = compuestos(elemento);
			compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		if (iones.containsElemento(e.simbolo())) {
			// solo para elemento que pueden formar hidruros
			if (anionesNoMetalicos.containsElemento(e.simbolo())  ) {
				Set<Integer> numerosOxidacion = e.numerosOxidacionNegativos();
				for(Integer n: numerosOxidacion) {
					c.add(new Hidruro(e,n));
				}
			} else {
				Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
				for(Integer n: numerosOxidacion) {
					c.add(new Hidruro(e,n));
				}
			}
		}
		return c;
	}
	
	public int nHidrogeno() {
		ArrayList<String> componentes = Compuesto.componentes(formula());
		int n = 0;
		if (componentes.size()==2 && "H".equals(componentes.get(1))) {
			n = cation.numeroAtomos;
		} else if (componentes.size()==2 && "H".equals(componentes.get(0))) {
			n = anion.numeroAtomos;
		}
		return n;
	}

	public void nHidrogeno(int n) {
		ArrayList<String> componentes = Compuesto.componentes(formula());
		if (componentes.size()==2 && "H".equals(componentes.get(1))) {
			anion.numeroAtomos = n;
		} else if (componentes.size()==2 && "H".equals(componentes.get(0))) {
			cation.numeroAtomos = n;
		}
	}	
	

	public Elemento getElemento() {
		if ("H".equals(anion.simbolo())) {
			return cation.elemento();
		} else {
			return anion.elemento();
		}
	}

	
	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String nomenclaturaClasica() {
		String nombre = nomenclaturaTradicional();
		if ("".equals(nombre)) {
			nombre = super.nombre();
		}
		return TablaPeriodica.cap(nombre);
	}

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////

	public String patronFormula() { 
		String patron = super.patronFormula();
		return patron + "{\\(aq\\)}"; 
	}
	
	/////////////////////////////////////////////////

	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		tablaPeriodica.selectCurso("3ESO");

		String[] formulas = { "FeH2" };

		// String[] formulas = { "B2H6", "NH3", "PH3", "AsH3",  "BH3",   "SbH3", "CH4", "SiH4", "HF", "HCl" };
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula);
			if (compuesto!=null) {
				System.out.println(compuesto);
			}
		}

		/*
		Hidruro hf = Hidruro.random(tablaPeriodica, FLUOR);
		System.out.println(hf);
		for(int i=0; i<100; i++) {
			Hidruro h = Hidruro.random(tablaPeriodica);		
			System.out.println(h);
		}
		*/
		

	}

}
