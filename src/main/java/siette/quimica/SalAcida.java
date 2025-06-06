package siette.quimica;

import java.util.ArrayList;

import siette.util.Random;

public class SalAcida extends SalBinaria {
	
	 // Nombres comunes

	
	 // Tablas de elementos
	protected static TablaPeriodica noMetalesSeleccion;
	static {
		noMetalesSeleccion = new TablaPeriodica(SalBinaria.noMetalesSeleccion);
		noMetalesSeleccion = noMetalesSeleccion.selectTable(TablaPeriodica.GRUPO, new int[] {16} );
		noMetalesSeleccion = noMetalesSeleccion.selectTable(TablaPeriodica.OXIDACION, new int[] {-2,-3,-4,-5,-6,-7,-8} );
	}
	
	// Constructores
	public SalAcida(Elemento eNegativo, int nNegativo, Elemento ePositivo, int nPositivo, int nMenos) {
		super();
		Hidruro hidruro = new Hidruro(eNegativo, nNegativo);
		IonHidracido anion = new IonHidracido(hidruro, nMenos);
		anion.setNumeroAtomos(nPositivo);
		setAnion(anion);
		setCation(ePositivo,nPositivo,nMenos);
	}

	// Métodos para generar un compuesto al azar
	public static SalAcida random(TablaPeriodica tabla) {
		Elemento eNegativo = tabla.selectElemento(noMetalesSeleccion);
		int nNegativo = (int) tabla.select(eNegativo.valenciasNegativas());
		Elemento ePositivo = tabla.selectElemento(metales);
		int nPositivo = (int) tabla.select(ePositivo.valenciasPositivas());
		int nMenos = tabla.nextInt(1,-nNegativo-1);
		return new SalAcida(eNegativo,  nNegativo,  ePositivo,  nPositivo,  nMenos);
	}

	
	// Metodos
	
	public static ConjuntoCompuestos compuestos(String simbolo1, String simbolo2) {
		Elemento elemento1 = TablaPeriodica.selectElemento(simbolo1);
		Elemento elemento2 = TablaPeriodica.selectElemento(simbolo2);
		if (!simbolo1.equals(simbolo2)) {
			return compuestos(elemento1, elemento2);
		} else {
			return null;
		}
	}

	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		if (componentes.size()==3) {
			String simbolo1 = componentes.get(0);
			String simbolo2 = componentes.get(1);
			String simbolo3 = componentes.get(2);
			if (!simbolo1.equals(simbolo2)) {
				if ( "H".equals(simbolo2) && noMetalesSeleccion.containsElemento(simbolo3) && metales.containsElemento(simbolo1) ) {
						Elemento elemento1 = TablaPeriodica.selectElemento(simbolo1);
						Elemento elemento2 = TablaPeriodica.selectElemento(simbolo3);
						ConjuntoCompuestos cc = compuestos(elemento1, elemento2);
						compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
					}
			}
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e1, Elemento e2) {
		ConjuntoCompuestos cc = new ConjuntoCompuestos();
		if (noMetalesSeleccion.containsElemento(e2.simbolo()) && metales.containsElemento(e1.simbolo())) {
			Elemento eNegativo = e2;
			Elemento ePositivo = e1;
			for(int nPositivo : ePositivo.valenciasPositivas() ) {
				for (int nNegativo : eNegativo.valenciasNegativas() ) {
					for (int nMenos=1; nMenos<-nNegativo; nMenos++) // nMenos = 1
						cc.add(new SalAcida(eNegativo, nNegativo, ePositivo, nPositivo, nMenos));
				}
			}
		}
		return cc;
	}
	
	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	
	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////

	
	/////////////////////////////////////////////////

	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");
		
		for(int i=0; i<100; i++) {
			SalAcida s = SalAcida.random(tablaPeriodica);		
			System.out.println(s);
		}
		
	}

}
