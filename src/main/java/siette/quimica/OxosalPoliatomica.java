package siette.quimica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import siette.util.Random;

public class OxosalPoliatomica extends Oxosal {
	
	protected static Set<Cation> cationesPoliatomicos; 
	static { 
		cationesPoliatomicos = new HashSet<Cation>();
		cationesPoliatomicos.add(new IonAmonio());
	}; 
		
	// Nombres comunes
		
	// Constructores

	public OxosalPoliatomica(Cation cation, Oxoanion anion) {
		init(cation,anion);
	}

	public OxosalPoliatomica(Cation cation, Oxoacido acido, int valenciaNegativa) {
		Oxoanion anion = new Oxoanion(acido, valenciaNegativa);
		init(cation,anion);
	}

	public OxosalPoliatomica(Cation cation, Elemento e2, int valenciaAcido, String poli, int valenciaNegativa) {
		Oxoacido acido = new Oxoacido(e2, valenciaAcido, poli);
		Oxoanion anion = new Oxoanion(acido, valenciaNegativa);
		init((Cation)cation.clone(),anion);
	}

	// Metodos
	public static OxosalPoliatomica random(TablaPeriodica tabla) {
		Cation cation = (Cation) tabla.select(cationesPoliatomicos);
		Oxoacido acido = Oxoacido.random(tabla);
		Oxoanion anion = new Oxoanion(acido, tabla.nextInt(1,acido.nHidrogeno()));
		return new OxosalPoliatomica(cation, anion);
	}
	
	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		Cation metal = null;
		ArrayList<String> componentesCation = new ArrayList<String>();
		Elemento metalNoMetal = null;
		for(Cation cation : cationesPoliatomicos) {
			if (formula.startsWith(cation.formula()) || formula.startsWith("("+cation.formula() )  || formula.toLowerCase().contains("amoni" )) {
				metal = cation;
				componentesCation = Compuesto.componentes(cation.formula());
			}
		}
		if (metal!=null && componentesCation.size()>0) {
			int n = componentesCation.size();			
			if (componentes.size()==3+(n-1) && Oxoacido.metalNoMetal.containsElemento(componentes.get(1+(n-1))) && "O".equals(componentes.get(2+(n-1))))  {
				metalNoMetal = TablaPeriodica.selectElemento(componentes.get(1+(n-1)));
			} else if ( componentes.size()==4+(n-1) && "H".equals(componentes.get(1+(n-1))) && Oxoacido.metalNoMetal.containsElemento(componentes.get(2+(n-1))) && "O".equals(componentes.get(3+(n-1)))	) {
				metalNoMetal = TablaPeriodica.selectElemento(componentes.get(2+(n-1)));
			} 
			if (metalNoMetal!=null) {
				ConjuntoCompuestos cc = compuestos(metal, metalNoMetal);
				compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
			}
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Cation metal, Elemento metalNoMetal) {
		ConjuntoCompuestos cc = new ConjuntoCompuestos();
		if (Oxoacido.metalNoMetal.containsElemento(metalNoMetal.simbolo())) {
			for(String poli : Oxoanion.POLIHIDRATADOS) {
				// caso especial del Fluor
				if ("F".equals(metalNoMetal.simbolo())) {
					Oxoacido acido = new Oxoacido(metalNoMetal, 1, poli);
					for(int nNegativo=1; nNegativo<=acido.nHidrogeno(); nNegativo++) {
						OxosalPoliatomica oxosal = new OxosalPoliatomica(metal, metalNoMetal, 1, poli, nNegativo);
						cc.add(oxosal);
					}
				} else {
					for(int nPositivo : metalNoMetal.valenciasPositivas() ) {
						Oxoacido acido = new Oxoacido(metalNoMetal, nPositivo, poli);
						for(int nNegativo=1; nNegativo<=acido.nHidrogeno(); nNegativo++) {
							OxosalPoliatomica oxosal = new OxosalPoliatomica(metal,   metalNoMetal, nPositivo, poli,    nNegativo);
							cc.add(oxosal);
						}
					}
				}
			}
				
		}
		return cc;
	}
	
	private void init(Cation cation, Oxoanion oxoanion) {
		int valenciaNegativa = oxoanion.numeroOxidacion; // la que haya salido
		int valenciaPositiva = cation.numeroOxidacion;
		// simplificar
		if (valenciaNegativa % valenciaPositiva == 0) {
			valenciaNegativa = valenciaNegativa / valenciaPositiva;
			valenciaPositiva = 1;
		}
		if (valenciaPositiva % valenciaNegativa == 0) {
			valenciaPositiva = valenciaPositiva / valenciaNegativa;
			valenciaNegativa = 1;
		}
		oxoanion.setNumeroAtomos(valenciaPositiva);
		setAnion(oxoanion);
		cation.setNumeroAtomos(valenciaNegativa);
		setCation(cation);
	}
	

////////////////////////////////////////////////////////////	
	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");

		String[] formulas = { "NH4HMnO4" }; 
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula);
			if (compuesto!=null) {
				System.out.println(compuesto);
			}
		}
		
		for(int i=0; i<100; i++) {
			OxosalPoliatomica s = OxosalPoliatomica.random(tablaPeriodica);		
			System.out.println(s);
			Compuesto c1 = Compuesto.analizar(s.formula());
			System.out.println(c1);
		}
		

	}

}
