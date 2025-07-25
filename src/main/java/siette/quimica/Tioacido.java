package siette.quimica;

import java.util.ArrayList;

import siette.util.Random;
import siette.util.text.Strings;

public class Tioacido extends Oxoacido {
	
	protected int nAzufre;
	protected Oxoacido oxoacido;
	// Constructores
	
	public Tioacido(Oxoacido ox, int nS) {
		super(ox);
		init(ox,nS);
	}

	public Tioacido(Elemento e, int n, String poli, int nS) {
		super(e,n,poli);
		init((Oxoacido)this,nS);
	}
	
	protected void init(Oxoacido ox, int nS) {
		oxoacido = ox;
		nOxigeno-=nS;
		if (elemento.equals(AZUFRE)) {
			nElemento+=nS;
			nAzufre = nElemento;
		} else {
			nAzufre = nS;
		}
	}
	
	
	// Métodos para generar un compuesto al azar
	public static Tioacido random(TablaPeriodica tabla) {
		Elemento elemento = tabla.selectElemento(metalNoMetal);
		return random(tabla,elemento);
	}


	public static Tioacido random(TablaPeriodica tabla, Elemento elemento) {
		ConjuntoCompuestos cc = compuestos(elemento);
		if (cc!=null && !cc.isEmpty()) {
			return (Tioacido) tabla.select(cc);
		} else {
			System.out.println("No hay tioacidos para el elemento: "+elemento);
		}
		return null;
	}	


	
	
	public int nAzufre() {
		return nAzufre;
	}
	
	
	// Analizar
	
	public static Compuesto analizar(ArrayList<String> componentes, String formula, int idioma ) {
		Compuesto compuesto = null;
		if (     componentes.size()==3 && "H".equals(componentes.get(0)) && "S".equals(componentes.get(1)) && "O".equals(componentes.get(2)) 
			  || componentes.size()==4 && "H".equals(componentes.get(0)) && "S".equals(componentes.get(2)) && "O".equals(componentes.get(3)) 
			) {
			Elemento elemento = TablaPeriodica.selectElemento(componentes.get(1));
			ConjuntoCompuestos cc = compuestos(elemento);
			compuesto = idioma == NINGUNO ? cc.buscar(formula) : cc.buscar(formula, idioma);
		}
		return compuesto;
	}

	public static ConjuntoCompuestos compuestos(Elemento e) {
		ConjuntoCompuestos c = new ConjuntoCompuestos();
		ConjuntoCompuestos cox = Oxoacido.compuestos(e);
		for(Compuesto ox : cox) {
			for(int iAzufre=1; iAzufre<((Oxoacido)ox).nOxigeno; iAzufre++) {
				Tioacido tiox = new Tioacido( (Oxoacido)ox, iAzufre);
				c.add(tiox);
			}
		}
		return c;
	}

	
	
	
	/////////////////////////////////////////////////
	//////////////   Formula    /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String formula() {
		return (nHidrogeno>0?"H":"") + (nHidrogeno>1?nHidrogeno:"") 
				 + elemento.simbolo() + (nElemento>1?nElemento:"") 
				 + (elemento.equals(AZUFRE) ? "" :"S" + (nAzufre>1?nAzufre:""))
				 + "O" + (nOxigeno>1?nOxigeno:"");
	}

	@Override
	public String formulaHTML() {
		return (nHidrogeno>0?"H":"") + (nHidrogeno>1?"<sub>"+nHidrogeno+"</sub>":"") 
				  + elemento.simbolo() + (nElemento>1?"<sub>"+nElemento+"</sub>":"") 
			      + (elemento.equals(AZUFRE) ? "" :"S" + (nAzufre>1?"<sub>"+nAzufre+"</sub>":""))
				  + "O" + (nOxigeno>1?"<sub>"+nOxigeno+"</sub>":"");
	}

	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////
		
	@Override
	public String nomenclaturaSistematica() {  
		String hidrogeno = (idIdioma==Compuesto.EN ? "hydrogen" : "hidrogeno");
		String ato = (idIdioma==Compuesto.EN ? "ate" : "ato");
		String tio = (idIdioma==Compuesto.EN ? "thio" : "tio");
		String oxido = (idIdioma==Compuesto.EN ? "oxide" : "oxido");
		String nombre = "";
		String raiz = (idIdioma==Compuesto.EN ? root(elemento).toLowerCase() : raiz(elemento).toLowerCase());
		int nSulfuro = elemento.equals(AZUFRE) ? nAzufre - oxoacido.nElemento : nAzufre;
		int nElemento = elemento.equals(AZUFRE) ? oxoacido.nElemento : this.nElemento;
		String nombreOxido = (nOxigeno>1?Ion.nombre(oxido, nOxigeno):oxido) 
				           + (nSulfuro>1?Ion.nombre(tio, nSulfuro):tio) 
				           + (nElemento>1 ? Ion.nombre(raiz,nElemento):raiz) + ato
				           ;
		nombre = nombreOxido + (idIdioma==Compuesto.EN ? " " : " de ") + (nHidrogeno>1 ? Ion.nombre(hidrogeno, nHidrogeno) : hidrogeno );
		return TablaPeriodica.cap(nombre);
	}

	@Override
	public String nomenclaturaStock() {  
		String ico = (idIdioma==Compuesto.EN ? "ic" : "ico");
		String tio = (idIdioma==Compuesto.EN ? "thio" : "tio");
		String oxo = (idIdioma==Compuesto.EN ? "oxo" : "oxo");
		String nomenclatura = "";
		String raiz = (idIdioma==Compuesto.EN ? root(elemento).toLowerCase() : raiz(elemento).toLowerCase());
		if (nElemento>0) {
			String nombreAcido = (idIdioma==Compuesto.EN ? raiz : Cation.acentuarRaiz(raiz)) + ico ;
			int nSulfuro = elemento.equals(AZUFRE) ? nAzufre - oxoacido.nElemento : nAzufre;
			String nombre = (nOxigeno>1?Ion.nombre(oxo, nOxigeno):oxo) 
					      + (nSulfuro>1?Ion.nombre(tio, nSulfuro):tio)
						  + (nElemento>1?Ion.nombre(nombreAcido, nElemento):nombreAcido);
			int valencia = oxoacido.valencia();
			if (valencia>1) {
				nombre += "(" + Ion.numeroOxidacionRomano(valencia) + ")" ;
			}
			if (idIdioma == EN) {
				nomenclatura =  nombre + " acid";
			} else {
				nomenclatura = Strings.filterHTMLtoTXT("&Aacute;cido")+ " " + nombre;
			}
		}
		return nomenclatura;
	}
		

	@Override
	public String nomenclaturaDeHidrogeno() {  
		String hidrogeno = (idIdioma==Compuesto.EN ? "hydrogen" : "hidrogeno");
		String ato = (idIdioma==Compuesto.EN ? "ate" : "ato");
		String sulfuro = (idIdioma==Compuesto.EN ? "sulfide" : "sulfuro");
		String oxido = (idIdioma==Compuesto.EN ? "oxide" : "oxido");
		String nombre = "";
		if(nHidrogeno>1) {
			nombre = Ion.nombre(hidrogeno, nHidrogeno);
		} else if(nHidrogeno>0) {
			nombre = hidrogeno;
		}
		String raiz = (idIdioma==Compuesto.EN ? root(elemento).toLowerCase() : raiz(elemento).toLowerCase());
		int nSulfuro = elemento.equals(AZUFRE) ? this.nAzufre-oxoacido.nElemento : this.nAzufre;
		int nElemento = elemento.equals(AZUFRE) ? oxoacido.nElemento : this.nElemento;
		String nombreOxido = (nOxigeno>1?Ion.nombre(oxido, nOxigeno):oxido);
		String nombreSulfuro = (nSulfuro>1 ? Ion.nombre(sulfuro,nSulfuro):sulfuro).toLowerCase() ;		
		String nombreElemento = (nElemento>1 ? Ion.nombre(raiz,nElemento):raiz) + ato;
		if (nombre.equals("")) {
			nombre = nombreOxido;
		} else {
			nombre += "(" + nombreOxido + nombreSulfuro + nombreElemento + ")";
		}
		return nombre;
	}


	@Override
	public String nomenclaturaDeAdicion() { 
		String sulfuro = (idIdioma==Compuesto.EN ? "sulfide" : "sulfuro");
		String oxido = (idIdioma==Compuesto.EN ? "oxide" : "oxido");
		String nombre = "";
		int nSulfuro = elemento.equals(AZUFRE) ? this.nAzufre-oxoacido.nElemento : this.nAzufre;
		int nElemento = elemento.equals(AZUFRE) ? oxoacido.nElemento : this.nElemento;
		String nombreSulfuro;
		String nombreElemento;
		if (idIdioma == EN) {
			nombreSulfuro = (nSulfuro>1 ? Ion.name(sulfuro,nSulfuro):sulfuro).toLowerCase() ;	
			nombreElemento = (nElemento>1 ? Ion.name(elemento,nElemento):elemento.name()).toLowerCase() ;	
		} else {	
			nombreSulfuro = (nSulfuro>1 ? Ion.nombre(sulfuro,nSulfuro):sulfuro).toLowerCase() ;	
			nombreElemento = (nElemento>1 ? Ion.nombre(elemento,nElemento):elemento.nombre()).toLowerCase() ;	
	    }
		// String nombreElemento = elemento.nombre().toLowerCase() ;		
		IonHidroxido ion = new IonHidroxido(nHidrogeno);
		String nombreHidroxido = Strings.filterHTMLtoTXT(ion.nomenclaturaSistematica());
		if (nOxigeno>nHidrogeno) {
			// resto
			int nOxigeno = this.nOxigeno-nHidrogeno;
			String nombreOxido = (nOxigeno>1?Ion.nombre(oxido, nOxigeno):oxido); 
			nombre = (nHidrogeno>0?nombreHidroxido:"") + nombreOxido + nombreSulfuro + nombreElemento;
		} else if (nOxigeno == nHidrogeno){
			nombre = nombreHidroxido + nombreSulfuro + nombreElemento;
		} else {
			// No hay moleculas de oxigeno suficientes...
		}
		return nombre;
	}


	@Override
	public String nomenclaturaClasica() {
		String tio = (idIdioma==Compuesto.EN ? "thio" : "tio");
		oxoacido.idIdioma(idIdioma);
		String nombre = oxoacido.nomenclaturaClasica().toLowerCase();
		int nAzufre = elemento.equals(AZUFRE) ? this.nAzufre-oxoacido.nElemento : this.nAzufre;
		if (idIdioma == EN) {
			nombre = nombre.substring(0,nombre.length()-5); //quitar la palabra acid
			nombre = (nAzufre>1 ? Ion.nombre(tio,nAzufre):tio) + nombre + " acid";
		} else {
			nombre = nombre.substring(6); //quitar la palabra acido
			nombre = Strings.filterHTMLtoTXT("&Aacute;cido") + " "+ (nAzufre>1 ? Ion.nombre(tio,nAzufre):tio) + nombre;
		}
		return nombre;
	}

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	

	@Override
	public String patronTradicional() {
		return patronClasico();
	}
	
	@Override
	public String patronClasico() {
		String tio = (idIdioma==Compuesto.EN ? "thio" : "tio");
		oxoacido.idIdioma(idIdioma);
		String patron = oxoacido.patronClasico().toLowerCase();
		int nAzufre = elemento.equals(AZUFRE) ? this.nAzufre-oxoacido.nElemento : this.nAzufre;
		if (idIdioma == EN) {
			patron = patron.substring(0,patron.length()-5); //quitar la palabra acid
			patron = (nAzufre>1 ? Ion.nombre(tio,nAzufre):tio) + patron + " acid";
		} else {
			patron = patron.substring(6); //quitar la palabra acido
			patron = Strings.filterHTMLtoTXT("&Aacute;cido") + " "+ (nAzufre>1 ? Ion.nombre(tio,nAzufre):tio) + patron;
		}
		return patron;
	}

	
	/////////////////////////////////////////////////

	
	public static void main(String[] argv) {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");

		String[] formulas = { "H2S2O3", "HNSO2", "H3PS2O2", "H2S2O2" };
		for (String formula : formulas) {
			Compuesto compuesto = analizar(formula,true);
			if (compuesto!=null) {
				System.out.println(compuesto);
			}
		}

		for(int i=0; i<100; i++) {
			Tioacido acido = Tioacido.random(tablaPeriodica);		
			System.out.println(acido);
			Compuesto c1 = Compuesto.analizar(acido.formula());
			System.out.println(c1);
			Compuesto c2 = Compuesto.analizar(acido.nomenclaturaSistematica(), acido.idIdioma());
			System.out.println(c2);
		}		

	}


}
