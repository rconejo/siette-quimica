package siette.quimica;

import java.util.HashMap;

public class IonCiano extends Anion {
	
	public static HashMap<String, String> nombreComun = new HashMap<String, String>();
	static {
		nombreComun.put("CN", "ciano");
	}

	public IonCiano() {
		super();
		CompuestoBinario c = new CompuestoBinario();
		Elemento carbon = TablaPeriodica.selectElemento("C");
		Elemento nitrogeno = TablaPeriodica.selectElemento("N");
		c.setAnion(nitrogeno,3,1);
		c.setCation(carbon,2,1);
		setCompuesto(c);
		numeroOxidacion(1);
	}

	public String patronRaiz() {
		return "cian";
	}

	
	public Object clone() {
		IonCiano obj = new IonCiano();
		obj.clone(this);
		return obj;
	}

	// Nomenclatura

	public String nomenclaturaSistematica(boolean mono) {
		String nombre = nomenclaturaTradicional();
		if (mono || numeroAtomos>1 ) { 
			nombre = prefijo(numeroAtomos)+ nombre;
		}
		return nombre;
	}

	public String nomenclaturaSistematica(int n) {
		return nomenclaturaSistematica();
	}

	public String nomenclaturaStock() {
		return nomenclaturaTradicional();
	}

	public String nomenclaturaTradicional() {
		return (idIdioma==Compuesto.EN ? "cyanide" : "cianuro");
	}

	
	public String patronFormula() {
		if (numeroAtomos>1) {
			return "\\(CN\\)"+ numeroAtomos;
		} else {
			return "(CN|\\(CN\\))";
		}
	}

	public String formula() {
		if (numeroAtomos>1) {
			return "(CN)"+ numeroAtomos;
		} else {
			return "CN";
		}
	}

	public String formulaHTML() {
		if (numeroAtomos>1) {
			return "(CN)"+ "<sub>"+ numeroAtomos+ "</sub>";
		} else {
			return "CN";
		}
	}

	// Patrón de nomenclatura
	

}
