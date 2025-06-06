package siette.quimica;

import java.util.Set;

public class CompuestoBinario extends Compuesto {
	
	
	Anion anion;  // carga negativa, ej: Cl- O- (HO)- 
	Cation cation; // carga positiva, ej: K+
		
	
	protected void setAnion(Anion a) {
		anion = a;
	}

	protected void setCation(Cation c) {
		cation = c;
	}
	
	protected void setAnion(Elemento e, int carga, int nAtomos) {
		anion = new Anion(e,carga, nAtomos);
	}

	protected void setCation(Elemento e, int carga, int nAtomos) {
		cation = new Cation(e,carga, nAtomos);
	}
	
	
	public Anion getAnion() {
		return anion;
	}

	public Cation getCation() {
		return cation;
	}

	public String patronFormula() { 
		String patron = cation.patronFormula() + anion.patronFormula();
		return patron; 
	}

	public String formula() { 
		String formula = cation.formula() + anion.formula();
		return formula;
	}

	public String formulaHTML() {
		String formulaHTML = cation.formulaHTML() + anion.formulaHTML();
		return formulaHTML;
	}

	protected void simplificar() {
		int mcd = Formula.mcd(anion.numeroAtomos, cation.numeroAtomos);
		anion.numeroAtomos /= mcd;
		cation.numeroAtomos /= mcd;
	}	
	
	public void idIdioma(int idIdioma) {
		this.idIdioma = idIdioma;
		anion.idIdioma(idIdioma);
		cation.idIdioma(idIdioma);
	}

	
	// Contabilidad de hidrogeno, oxigeno u otro elemento
	public int nOxigeno() {
		try {
			Formula formula = new Formula(formula());
			return formula.nOxigeno();
		} catch (FormulaException e) {
			return 0;
		}
	}
	

	public int nElemento() {
		return 0;
	}

	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	/* **** ESPAÑOL **** */

	@Override
	public String nomenclaturaSistematica() {
		cation.idIdioma(idIdioma); 
		anion.idIdioma(idIdioma);
		Set<Integer> numerosOxidacion = cation.numerosOxidacionPositivos();
		String nombreAnion = anion.nomenclaturaSistematica(numerosOxidacion.size()>1);
		String nombreCation = cation.nomenclaturaSistematica(numerosOxidacion.size()>1);
		String nombre = TablaPeriodica.cap(nombreAnion) + " " + nombreCation;
		if (idIdioma == EN) {
			nombre = TablaPeriodica.cap(nombreCation) + " " + nombreAnion.toLowerCase();
		}
		return nombre;
	}

	@Override
	public String nomenclaturaStock() {
		String nombre;
		if (idIdioma == EN) {
			nombre = cation.nomenclaturaStock() + " " + anion.nomenclaturaStock();
		} else {
			nombre = TablaPeriodica.cap(anion.nomenclaturaStock()) + " " + cation.nomenclaturaStock();
		}
		return nombre;
	}


	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////
	
	@Override
	public String patronSistematica() {
		Set<Integer> numerosOxidacion = cation.numerosOxidacionPositivos();
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
		// Set<Integer> numerosOxidacion = cation.numerosOxidacionPositivos();
		String nombre;
		if (idIdioma == EN) {
			nombre = cation.patronStock() + " " + anion.patronStock();
		} else {
			nombre = anion.patronStock() + " " + cation.patronStock();
		}
		return nombre;
	}

	@Override
	public ConjuntoCompuestos compuestosSimilares() {
		ConjuntoCompuestos cc = new ConjuntoCompuestos();
		if (this instanceof Haluro) 
			cc = Haluro.compuestos(anion.elemento());
		else if (this instanceof Oxido) 
			cc = Oxido.compuestos(cation.elemento());
		else if (this instanceof Peroxido) 
			cc = Peroxido.compuestos(cation.elemento());
		else if (this instanceof Hidroxido) 
			cc = Hidroxido.compuestos(cation.elemento());
		else if (this instanceof Hidruro) 
			cc = Hidruro.compuestos(((Hidruro)this).getElemento());
		else if (this instanceof Cianuro) 
			cc = Cianuro.compuestos(cation.elemento());
		else if (this instanceof SalAcida) 
			cc = SalAcida.compuestos(anion.elemento(),cation.elemento());
		else if (this instanceof SalBinaria) 
			cc = SalBinaria.compuestos(anion.elemento(),cation.elemento());
		else if (this instanceof OxosalPoliatomica) 
			cc = OxosalPoliatomica.compuestos(cation,anion.elemento());		
		else if (this instanceof Oxosal) 
			cc = Oxosal.compuestos(anion.elemento(),cation.elemento());		
		return cc;
	}

	

}
