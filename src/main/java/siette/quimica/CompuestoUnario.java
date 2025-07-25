package siette.quimica;

import java.util.ArrayList;

public class CompuestoUnario extends Compuesto {
	
	Elemento elemento;
		
	public CompuestoUnario() {
	}

    public CompuestoUnario(Elemento e) {
		elemento = e;
	}
    public CompuestoUnario(String simbolo) {
		this(TablaPeriodica.selectElemento(simbolo));
	}

    
	/////////////////////////////////////////////////
	//////////////////// random /////////////////////
	/////////////////////////////////////////////////

	// Métodos para generar un compuesto al azar
	public static CompuestoUnario random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento();
		return random(tabla,elemento);
	}
	public static CompuestoUnario random(TablaPeriodica tabla, Elemento elemento) {
		return new CompuestoUnario(elemento);
	}

	/////////////////////////////////////////////////
	////////////////// analizar /////////////////////
	/////////////////////////////////////////////////

	/* ****** ESPAÑOL ***** */

	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma) {
		Compuesto compuesto = null;
		if (componentes.size()==1) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(0));
			compuesto = new CompuestoUnario(elemento);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		c.add(new CompuestoUnario(e));
		return c;
	}
	
	@Override
	public ConjuntoCompuestos compuestosSimilares() {
		return compuestos(elemento);
	}
	
	/////////////////////////////////////////////////
	//////////////   Nomenclatura   /////////////////
	/////////////////////////////////////////////////
	
	@Override
	public ArrayList<Integer> nomenclaturasPorDefecto() {
		ArrayList<Integer> porDefecto = new ArrayList<Integer>();
		porDefecto.add(NOM_TRADICIONAL);
		return porDefecto;
	}

	/* ****** ESPAÑOL ***** */
	
	@Override
	public String nomenclaturaSistematica() {
		return idIdioma == EN ? elemento.name() : elemento.nombre();
	}

	@Override
	public String nomenclaturaStock() {
		return idIdioma == EN ? elemento.name() : elemento.nombre();
	}

	@Override
	public String nomenclaturaTradicional() {
		return idIdioma == EN ? elemento.name() : elemento.nombre();
	}


	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	
	@Override
	public String patronSistematica() {
		return idIdioma == EN ? elemento.name() : elemento.patron();
	}

	@Override
	public String patronStock() {
		return idIdioma == EN ? elemento.name() : elemento.patron();
	}

	
	@Override
	public String formula() {
		return elemento.simbolo();
	}

	@Override
	public String formulaHTML() {
		return elemento.simbolo();
	}

	public Elemento getElemento() {
		return elemento;
	}


}
