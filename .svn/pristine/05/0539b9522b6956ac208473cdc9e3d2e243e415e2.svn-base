package siette.quimica;

import java.util.ArrayList;

import siette.util.Random;

public class Cianuro extends SalBinaria {
	
	// Constructores


	public Cianuro(Elemento ePositivo, int nPositivo) {
		IonCiano ciano = new IonCiano();
		ciano.numeroAtomos = nPositivo;
		setAnion(ciano);
		setCation(ePositivo,nPositivo,1);
	}


	// Métodos para generar un compuesto al azar
	public static Cianuro random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento(metales);
		return random(tabla,elemento);
	}
	public static Cianuro random(TablaPeriodica tabla, Elemento elemento) {
		int valencia = (int) tabla.select(elemento.valenciasPositivas());
		return new Cianuro(elemento, valencia);
	}
	
	
	// Metodos
	
	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		if (componentes.size()==3) {
			String simbolo1 = componentes.get(0);
			String simbolo2 = componentes.get(1);
			String simbolo3 = componentes.get(2);
			if ( (metales.containsElemento(simbolo1) || simbolo1.equals("H") ) && simbolo2.equals("C")	&& simbolo3.equals("N") ) {
				Elemento elemento1 = TablaPeriodica.selectElemento(simbolo1);
				ConjuntoCompuestos cc = compuestos(elemento1);
				compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
			}
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos cc = new ConjuntoCompuestos();
		for(int nPositivo : e.valenciasPositivas() ) {
			cc.add(new Cianuro(e, nPositivo));
		}
		return cc;
	}
	
	
	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");

		Cianuro hf = Cianuro.random(tablaPeriodica, TablaPeriodica.selectElemento("K"));
		System.out.println(hf);
		for(int i=0; i<100; i++) {
			Cianuro h = Cianuro.random(tablaPeriodica);		
			System.out.println(h);
			Compuesto c1 = Compuesto.analizar(h.formula());
			System.out.println("c1="+ c1);
			Compuesto c2 = Compuesto.analizar(h.nomenclaturaSistematica(), ES);
			System.out.println("c2="+ c2);
			h.idIdioma(EN);
			System.out.println(h);
		}
		
		String[] formulas = {  "HCN",  };
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula);
			if (compuesto!=null) {
				System.out.println(compuesto);
			}
		}

	}

}
