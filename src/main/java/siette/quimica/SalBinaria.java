package siette.quimica;

import java.util.ArrayList;

import siette.util.Random;

public class SalBinaria extends CompuestoBinario {
	
	 // Nombres comunes

	static {
		nombreComun.put("NaCl",  "sal");
	}
	
	 // Tablas de elementos
	
	protected static TablaPeriodica noMetalesSeleccion;
	static {
		noMetalesSeleccion = new TablaPeriodica(CompuestoBinario.noMetales);
		noMetalesSeleccion.delRow(TablaPeriodica.SIMBOLO, "O");
		// noMetalesSeleccion.delRow(TablaPeriodica.SIMBOLO, "Po"); // no tiene valencia negativa
		noMetalesSeleccion.setNotExclusive();
	}	
	
	protected static TablaPeriodica metalNoMetal;
	static {
		metalNoMetal = new TablaPeriodica(metalesMetaloides);
		metalNoMetal.addTable(noMetalesSeleccion);
		metalNoMetal.addRow(BORO); // no tiene valencia negativa
	}	
	
	
	// Constructores

	protected SalBinaria() {}
	
	public SalBinaria(Elemento eNegativo, int nNegativo, Elemento ePositivo, int nPositivo) {
		setAnion(eNegativo,nNegativo,nPositivo);
		setCation(ePositivo,nPositivo,-nNegativo);
		simplificar();
	}
	

	// Métodos para generar un compuesto al azar
	public static SalBinaria random(TablaPeriodica tabla) {
		Elemento e1 = tabla.selectElemento(noMetalesSeleccion);
		Elemento e2 = tabla.selectElemento(metalNoMetal);
		while (e2.equals(e1) || e2.simbolo().equals("O")) { 
			e2 = tabla.selectElemento(metalNoMetal);
		}
		Elemento ePositivo=e1, eNegativo=e2;
		if (noMetalesSeleccion.containsElemento(e1.simbolo()) && noMetalesSeleccion.containsElemento(e2.simbolo())) {
			eNegativo = e1;
			ePositivo = e2;
			if (!e1.masElectronegativo(e2)) {
				eNegativo = e2;
				ePositivo = e1;
			}
		} else if (metalesMetaloides.containsElemento(e1.simbolo()) && noMetalesSeleccion.containsElemento(e2.simbolo()) ) {
			eNegativo = e2;
			ePositivo = e1;
		} else if (noMetalesSeleccion.containsElemento(e1.simbolo()) && metalesMetaloides.containsElemento(e2.simbolo()) ) {
			eNegativo = e1;
			ePositivo = e2;
		}
		int nPositivo = (int) tabla.select(ePositivo.valenciasPositivas());
		int nNegativo = (int) tabla.select(eNegativo.valenciasNegativas());

		return new SalBinaria(eNegativo, nNegativo, ePositivo, nPositivo);
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
		if (componentes.size()>1) {
			String simbolo1 = componentes.get(0);
			String simbolo2 = componentes.get(1);
			if (!simbolo1.equals(simbolo2)) {
				if (       noMetalesSeleccion.containsElemento(simbolo1) && noMetalesSeleccion.containsElemento(simbolo2)
						|| noMetalesSeleccion.containsElemento(simbolo1) && metalesMetaloides.containsElemento(simbolo2)	
						|| metalesMetaloides.containsElemento(simbolo1) && noMetalesSeleccion.containsElemento(simbolo2)	
					) {
						Elemento elemento1 = TablaPeriodica.selectElemento(simbolo1);
						Elemento elemento2 = TablaPeriodica.selectElemento(simbolo2);
						ConjuntoCompuestos cc = compuestos(elemento1, elemento2);
						compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
					}
			}
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e1, Elemento e2) {
		ConjuntoCompuestos cc = new ConjuntoCompuestos();
		if (noMetalesSeleccion.containsElemento(e1.simbolo()) && noMetalesSeleccion.containsElemento(e2.simbolo())) {
			Elemento eNegativo = e1;
			Elemento ePositivo = e2;
			if (!e1.masElectronegativo(e2)) {
				eNegativo = e2;
				ePositivo = e1;
			}
			for(int nPositivo : ePositivo.valenciasPositivas() ) {
				for (int nNegativo : eNegativo.valenciasNegativas() ) {
					cc.add(new SalBinaria(eNegativo, nNegativo, ePositivo, nPositivo));
				}
			}
		} else if (metalesMetaloides.containsElemento(e1.simbolo()) && noMetalesSeleccion.containsElemento(e2.simbolo()) ) {
			for(int nPositivo : e1.valenciasPositivas() ) {
				for (int nNegativo : e2.valenciasNegativas() ) {
					cc.add(new SalBinaria(e2, nNegativo, e1, nPositivo));
				}
			}
		} else if (noMetalesSeleccion.containsElemento(e1.simbolo()) && metalesMetaloides.containsElemento(e2.simbolo()) ) {
			for(int nPositivo : e2.valenciasPositivas() ) {
				for (int nNegativo : e1.valenciasNegativas() ) {
					cc.add(new SalBinaria(e1, nNegativo, e2, nPositivo));
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

	@Override
	public ArrayList<Integer> nomenclaturasPorDefecto() {
		ArrayList<Integer> porDefecto = new ArrayList<Integer>();
		porDefecto.add(NOM_SISTEMATICA);
		porDefecto.add(NOM_STOCK);
		return porDefecto;
	}
	

	/////////////////////////////////////////////////
	
	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica.selectCurso("3ESO");
		/*
		String[] formulas = { "Mg3P2", "Po3P2", "Zn3P2", "BP", "BrF3", "CS2","PCl5", "As2Se3" , "BAs"}; 
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula,true);
			if (compuesto!=null) {
				System.out.println(compuesto);
			}
		}
		*/
		for(int i=0; i<100; i++) {
			SalBinaria s = SalBinaria.random(tablaPeriodica);		
			System.out.println(s);
		}
		


	}

}
