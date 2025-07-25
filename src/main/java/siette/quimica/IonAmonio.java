package siette.quimica;

import java.util.HashMap;

public class IonAmonio extends Cation {
	
	public static HashMap<String, String> nombreComun = new HashMap<String, String>();
	static {
		nombreComun.put("NH4", "amonio");
	}

	public IonAmonio() {
		super();
		CompuestoBinario c = new CompuestoBinario();
		Elemento hidrogeno = TablaPeriodica.selectElemento("H");
		Elemento nitrogeno = TablaPeriodica.selectElemento("N");
		c.setCation(nitrogeno,5,1);
		c.setAnion(hidrogeno,1,4);
		setCompuesto(c);
		numeroOxidacion(1);
	}


	public Object clone() {
		IonAmonio obj = new IonAmonio();
		obj.clone(this);
		return obj;
	}

	/////////////////////////////////////////////////
	//////////////    Formula   /////////////////////
	/////////////////////////////////////////////////

	public String patronFormula() {
		if (numeroAtomos>1) {
			return "\\(NH4\\)"+ numeroAtomos;
		} else {
			return "(NH4|\\(NH4\\))";
		}
	}

	public String formula() {
		if (numeroAtomos>1) {
			return "(NH4)"+ numeroAtomos;
		} else {
			return "NH4";
		}
	}

	public String formulaHTML() {
		if (numeroAtomos>1) {
			return "(NH<sub>4</sub>)"+ "<sub>"+numeroAtomos+"</sub>";
		} else {
			return "NH<sub>4</sub>";
		}
	}

	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	public String nomenclaturaSistematica() {
		return (idIdioma == Compuesto.ES ? "de " : "") + (numeroAtomos>1?prefijo(numeroAtomos):"") + (idIdioma == Compuesto.ES ? "amonio" : "amonium") ; 
	}

	public String nomenclaturaStock() {
		return nomenclaturaSistematica();
	}

	public String nomenclaturaDeAdicion() {
		return (idIdioma == Compuesto.ES ? "de amonio(1+)" : "amonium(1+)")  ; 
	}

	public String nomenclaturaClasica() {
		return "amonico";
	}
	
	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////

	public String patronSistematica() {
		return (idIdioma == Compuesto.ES ? "de " : "") + (numeroAtomos>1?prefijo(numeroAtomos):"{mono}") + (idIdioma == Compuesto.ES ? "amonio" : "amonium") ; 
	}

	public String patronStock() {
		return nomenclaturaStock();
	}


}
