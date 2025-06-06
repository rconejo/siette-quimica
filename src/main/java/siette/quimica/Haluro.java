package siette.quimica;

import java.util.ArrayList;
import java.util.Set;

import siette.util.Random;
import siette.util.text.Strings;

public class Haluro extends CompuestoBinario {
	
	// Nombres comunes y excepciones
	static {
		nombreComun.put("OF2", "Anhídrido hipofluoroso");
	}

	// Constructores

	public Haluro(Elemento e, int valencia) {
		super();
		int valenciaOxigeno = -2;
		int valenciaHalogeno = valencia;
		int numeroOxidacionHalogeno = valenciaHalogeno;
		if (e.simbolo().equals("F")) {
			// caso especial del fluor
			valenciaOxigeno = 2;
			valenciaHalogeno = 1;
			numeroOxidacionHalogeno = -1;
		}
		setCation(OXIGENO,valenciaOxigeno,Math.abs(valenciaHalogeno));
		setAnion(e,numeroOxidacionHalogeno,2);
		simplificar();
	}

	// Métodos para generar un compuesto al azar
	public static Haluro random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento(halogenos);
		return random(tabla,elemento);
	}
	public static Haluro random(TablaPeriodica tabla, Elemento elemento) {
		int valencia;
		if (elemento.equals(FLUOR)) {
		   valencia = (int) tabla.select(elemento.valenciasNegativas());
		} else {
		   valencia = (int) tabla.select(elemento.valenciasPositivas());
		}
		return new Haluro(elemento, Math.abs(valencia));
	}


	
	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		if (componentes.size()>1 && "O".equals(componentes.get(0))) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(1));
			ConjuntoCompuestos cc = compuestos(elemento);
			compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		if (halogenos.containsElemento(e.simbolo())) {
			// solo para elemento que pueden formar haluros
			if (e.simbolo().equals("F")) {
				c.add(new Haluro(e,-2));
			} else {
				Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
				for(Integer n: numerosOxidacion) {
					c.add(new Haluro(e,n));
				}
			}
		}
		return c;
	}
	
	
	// Contabilidad de hidrogeno y oxigeno
	@Override
	public int nOxigeno() {
		return cation.numeroAtomos;
	}
	
	@Override
	public int nElemento() {
		return anion.numeroAtomos;
	}



	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////
	
	@Override
	public String nomenclaturaStock() {
		String nombre;
		if (idIdioma == EN) {
			Cation ion = new Cation(anion);
			ion.idIdioma(idIdioma);
			nombre = ion.nomenclaturaStock() + " oxide";
		} else {
			nombre = Strings.filterHTMLtoTXT("&Oacute;xido ") + (new Cation(anion)).nomenclaturaStock();
		}
		return nombre;
	}

	
	@Override
	public String nomenclaturaClasica() {
		String nombre="";
		if (idIdioma == EN) {
			// String simbolo =  cation.simbolo();
			// Elemento elemento  = TablaPeriodica.selectElemento(simbolo);
			// Oxoacido acido = new Oxoacido(elemento, cation.numeroOxidacion, "sin");
			// acido.idIdioma(EN);
			// nombre =  acido.nomenclaturaClasica() + " anhydride";
			Cation aux = new Cation(anion);
			aux.idIdioma(EN);
			// nombre = aux.nomenclaturaClasica() + " anhydride";
			nombre = "Anhydride of "+ aux.nomenclaturaClasica() + " acid";
		} else {
			nombre = "Anhidrido " + new Cation(anion).nomenclaturaClasica();
		}
		return cap(nombre);
	}

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	
	
	public String patron() { 
		return patron(SISTEMATICA+TRADICIONAL); // opciones por defecto
	}

	@Override
	public String patronSistematica() {
		Elemento e = anion.elemento();
		Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
		String patronAnion = anion.patronSistematica(numerosOxidacion.size()>1);
		String patronCation = cation.patronSistematica(numerosOxidacion.size()>1);
		String patron;
		if (idIdioma == EN) {
			patron = patronCation  + " " + patronAnion;
		} else {
			patron = patronAnion + " " + patronCation;
		}
		return patron;
	}

	@Override
	public String patronStock() {
		String patron;
		if (idIdioma == EN) {
			Cation ion = new Cation(anion);
			ion.idIdioma(idIdioma);
			patron = ion.patronStock() + " oxide";
		} else {
			patron = "oxido " + (new Cation(anion)).patronStock();
		}
		return patron;
	}


	
	////////////////////////////////////////
	
	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");

		// Haluro OF2 = Haluro.random(tablaPeriodica,FLUOR);
		// System.out.println(OF2);
		for(int i=0; i<100; i++) {
			Haluro h = Haluro.random(tablaPeriodica);		
			System.out.println(h);
			h.idIdioma(EN);
			System.out.println(h);
		}
		
		System.out.println(compuestos(CLORO));
		System.out.println(compuestos(FLUOR));
		System.out.println(compuestos(AZUFRE));
	}

}
