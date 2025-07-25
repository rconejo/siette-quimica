package siette.quimica;

import java.util.ArrayList;
import java.util.HashSet;

import siette.util.Random;

public class Oxosal extends CompuestoBinario {
	
	protected static HashSet<String> excepciones;
	static {
		// Formulas que generan la misma nomenclatura tradicional y que no he conseguido desambiguar
		excepciones = new HashSet<String>();
	}	
		
	// Constructores

	protected Oxosal() { super(); };
	
	public Oxosal(Elemento e1, int valenciaPositiva, Oxoanion anion) {
		init(e1,valenciaPositiva,anion);
	}

	public Oxosal(Elemento ePositivo, int valenciaPositiva,  
				  Elemento eNegativo, int valenciaAcido, String poli, int valenciaNegativa) {
		Oxoacido acido = new Oxoacido(eNegativo, valenciaAcido, poli);
		Oxoanion anion = new Oxoanion(acido, valenciaNegativa);
		init(ePositivo,valenciaPositiva,anion);
	}

	// Métodos para generar un compuesto al azar
	public static Oxosal random(TablaPeriodica tabla) {
		Oxoacido acido = Oxoacido.random(tabla);
		Oxoanion anion = new Oxoanion(acido, tabla.nextInt(1,acido.nHidrogeno()));
		return random(tabla, anion);
	}	

	// Métodos para generar un compuesto al azar
	public static Oxosal random(TablaPeriodica tabla, String poli) {
		Oxoacido acido = Oxoacido.random(tabla, poli);
		Oxoanion anion = new Oxoanion(acido, tabla.nextInt(1,acido.nHidrogeno()));
		return random(tabla, anion);
	}	

	// Métodos para generar un compuesto al azar
	public static Oxosal random(TablaPeriodica tabla, Oxoanion anion) {
		Elemento elemento = ((Oxoacido)anion.getCompuesto()).getElemento();
		Elemento metal = tabla.selectElemento(metales);
		while (metal.equals(elemento)) {  // evitar permanganato de magnesio, etc.
			metal = tabla.selectElemento(metales);
		}
		int valenciaPositiva = (int) tabla.select(metal.valenciasPositivas());
		return new Oxosal(metal, valenciaPositiva, anion);
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
		// String[] componentes = Compuesto.componentes(formula); // Metal {Hidrogeno} MetalNoMetal Oxigeno
		Elemento metal = null;
		Elemento metalNoMetal = null;
		if (componentes.size()==3 &&   metales.containsElemento(componentes.get(0))  && Oxoacido.metalNoMetal.containsElemento(componentes.get(1)) && "O".equals(componentes.get(2)))  {
			metal = TablaPeriodica.selectElemento(componentes.get(0));
			metalNoMetal = TablaPeriodica.selectElemento(componentes.get(1));
		} else if ( componentes.size()==4 &&  metales.containsElemento(componentes.get(0))  && "H".equals(componentes.get(1)) && Oxoacido.metalNoMetal.containsElemento(componentes.get(2)) && "O".equals(componentes.get(3))	) {
			metal = TablaPeriodica.selectElemento(componentes.get(0));
			metalNoMetal = TablaPeriodica.selectElemento(componentes.get(2));
		} 
		if (metal!=null && metalNoMetal!=null) {
			ConjuntoCompuestos cc = compuestos(metal, metalNoMetal);
			compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento metal, Elemento metalNoMetal) {
		ConjuntoCompuestos cc = new ConjuntoCompuestos();
		if (metales.containsElemento(metal.simbolo()) &&  Oxoacido.metalNoMetal.containsElemento(metalNoMetal.simbolo())) {
			for(String poli : Oxoanion.POLIHIDRATADOS) {
				// caso especial del Fluor
				if ("F".equals(metalNoMetal.simbolo())) {
					Oxoacido acido = new Oxoacido(metalNoMetal, 1, poli);
					for(int nNegativo=1; nNegativo<=acido.nHidrogeno(); nNegativo++) {
						for(int valenciaPositiva : metal.valenciasPositivas() ) {
							Oxosal oxosal = new Oxosal(metal,valenciaPositiva,   metalNoMetal, 1, poli,    nNegativo);
							cc.add(oxosal);
						}
					}
				} else {
					for(int valenciaAcido : Oxoacido.numerosOxidacionPositivos(metalNoMetal) ) {
						Oxoacido acido = new Oxoacido(metalNoMetal, valenciaAcido, poli);
						for(int nNegativo=1; nNegativo<=acido.nHidrogeno(); nNegativo++) {
							for(int valenciaPositiva : metal.valenciasPositivas() ) {
								// acido = new Oxoacido(metalNoMetal, nPositivo, poli);
								// Oxoanion oxoanion = new Oxoanion(acido, nNegativo);
								// Oxosal oxosal = new Oxosal(metal,valenciaPositiva, oxoanion);
								Oxosal oxosal = new Oxosal(metal,   valenciaPositiva,   metalNoMetal, valenciaAcido, poli,    nNegativo);
								// System.out.println(""+ metal.simbolo() + " vp="+valenciaPositiva + " " + metalNoMetal.simbolo() + " va="+ valenciaAcido + " poli="+ poli +  " nn="+ nNegativo+ " ->"+oxosal);
								cc.add(oxosal);
							}
						}
					}
				}
			}				
		}
		return cc;
	}
	
	private void init(Elemento metal, int valenciaPositiva, Oxoanion oxoanion) {
		int valenciaNegativa = oxoanion.numeroOxidacion; // la que haya salid
		int vP = valenciaPositiva;
		int vN = valenciaNegativa;
		// System.out.println("init vp="+valenciaPositiva+" vn="+valenciaNegativa);
		// simplificar
		if (valenciaNegativa % valenciaPositiva == 0) {
			vN = valenciaNegativa / valenciaPositiva;
			vP = 1;
		}
		if (valenciaPositiva % valenciaNegativa == 0) {
			vP = valenciaPositiva / valenciaNegativa;
			vN = 1;
		}
		oxoanion.setNumeroAtomos(vP);
		setAnion(oxoanion);
		// System.out.println("init vp="+valenciaPositiva+" vn="+valenciaNegativa);
		setCation(metal,valenciaPositiva,vN);
	}
	
	/////////////////////////////////////////////////
	//////////////    Formula   /////////////////////
	/////////////////////////////////////////////////
	

	public String formula() {
		return cation.formula() + ((Oxoanion)anion).formula();
	}
	
	public String formulaHTML() {
		return cation.formulaHTML() + ((Oxoanion)anion).formulaHTML();
	}


	
	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////
	

	@Override
	public String nomenclaturaSistematica() {  // de Hidrogeno
		return  nomenclaturaDeHidrogeno();
	}
		
	@Override
	public String nomenclaturaDeHidrogeno() {  
		String nombre = "";
		if (idIdioma == EN) {
			nombre = cation.nomenclaturaSistematica() + " " + ((Oxoanion)anion).nomenclaturaDeHidrogeno() ;
		} else {
			nombre = ((Oxoanion)anion).nomenclaturaDeHidrogeno() + " " + cation.nomenclaturaSistematica();
		}
		return TablaPeriodica.cap(nombre);
	}

	@Override
	public String nomenclaturaStock() {
		//Excepciones (Casos que no he podido resolver)
		if (excepciones.contains(formula())) return ""; 
		String nombre = "";
		if (idIdioma == EN) {
			nombre = cation.nomenclaturaStock() + " " + ((Oxoanion)anion).nomenclaturaStock().toLowerCase() ;
		} else {
			nombre = ((Oxoanion)anion).nomenclaturaStock() + " " + cation.nomenclaturaStock();
		}
		return TablaPeriodica.cap(nombre);
	}

	@Override
	public String nomenclaturaDeAdicion() {
		String nombre = "";
		if (idIdioma == EN) {
			nombre = cation.nomenclaturaDeAdicion() + " " + ((Oxoanion)anion).nomenclaturaDeAdicion().toLowerCase() ;
		} else {
			nombre = ((Oxoanion)anion).nomenclaturaDeAdicion() + " " + cation.nomenclaturaDeAdicion();
		}
		return TablaPeriodica.cap(nombre);
	}

	@Override
	public String nomenclaturaClasica() {
		String nombre = super.nomenclaturaClasica();
		if (nombre == null || nombre.equals("")) {
			if (idIdioma == EN) {
				nombre = cation.nomenclaturaClasica() + " " + anion.nomenclaturaClasica();
			} else {
				nombre = anion.nomenclaturaClasica() + " " + cation.nomenclaturaClasica();
			}
		}
		return TablaPeriodica.cap(nombre);
	}

	
	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////

	@Override
	public ArrayList<Integer> nomenclaturasPorDefecto() {
		ArrayList<Integer> porDefecto = new ArrayList<Integer>();
		porDefecto.add(NOM_DEHIDROGENO);
		porDefecto.add(NOM_DEADICION);
		porDefecto.add(NOM_TRADICIONAL);
		return porDefecto;
	}
	

	public String patronSistematica() {  // de Hidrogeno
		return  patronDeHidrogeno();
	}

	@Override
	public String patronStock() {
		String patron = "";
		if (idIdioma == EN) {
			patron = cation.patronStock() + " " + ((Oxoanion)anion).patronStock() ;
		} else {
			patron = ((Oxoanion)anion).patronStock() + " " + cation.patronStock();
		}
		return patron;
	}


	/////////////////////////////////////////////////

	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		tablaPeriodica.selectCurso("3ESO");
		
		Compuesto c = Compuesto.analizar("Bis(Pentaoxidomanganato) de cromo", ES);
		System.out.println(c);
		
		ConjuntoCompuestos cc = Oxosal.compuestos(TablaPeriodica.selectElemento("Na"), TablaPeriodica.selectElemento("C"));
		for(Compuesto c1 : cc) {
			Oxosal s = (Oxosal)c1;
			System.out.println(s.formula()+": "+s.nomenclaturaClasica());
			// Oxoanion ox = (Oxoanion) s.anion;
		}
		
		String[] formulas = { "Pb(HPO4)2", "PbHPO4", "Fe2(SO4)3"};
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula,true);
			if (compuesto!=null) {
				System.out.println(compuesto);
				compuesto.idIdioma(EN);
				System.out.println(compuesto.nomenclaturaClasica());
			}
		}

		for(int i=0; i<100; i++) {
			Oxosal s = Oxosal.random(tablaPeriodica,"meta");		
			System.out.println(s);
		}
	}

}
