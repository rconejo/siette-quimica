package siette.quimica;

import java.util.HashMap;

public class IonHidroxido extends Anion {
	
	public static HashMap<String, String> nombreComun = new HashMap<String, String>();
	static {
		nombreComun.put("OH", "hidr&oacute;xido");
	}
	static {
		commonName.put("OH", "hydroxide");
	}

	
	public IonHidroxido() {
		super();
		CompuestoBinario c = new CompuestoBinario();
		Elemento hidrogeno = TablaPeriodica.selectElemento("H");
		Elemento oxigeno = TablaPeriodica.selectElemento("O");
		c.setCation(oxigeno,-2,1);
		c.setAnion(hidrogeno,1,1);
		setCompuesto(c);
		numeroOxidacion = 1;
	}

	public IonHidroxido(int n) {
		this();
		numeroAtomos = n;
	}

	// Nomenclatura
	
	public String nomenclaturaSistematica(boolean multiplesValencias) {
		String s = compuesto.formula();
		String nombre = (idIdioma == Compuesto.EN ? commonName.get(s) : nombreComun.get(s));
		if (numeroAtomos>1) {
			nombre = nombre(nombre,numeroAtomos);
		}
		nombre = TablaPeriodica.cap(nombre);
		return nombre;
	}
	
	public String nomenclaturaTradicional() {
		return (idIdioma == Compuesto.EN ? "hydroxide ion" : "i&oacute;n hidr&oacute;xido");
	}

	public String nomenclaturaClasica() {
		return (idIdioma == Compuesto.EN ? "hydroxide ion" : "i&oacute;n hidr&oacute;xido");
	}

	public String patronFormula() {
		if (numeroAtomos>1) {
			return "\\(OH\\)"+ numeroAtomos;
		} else {
			return "(OH|\\(OH\\))";
		}
	}

	public String formula() {
		if (numeroAtomos>1) {
			return "(OH)"+ numeroAtomos;
		} else {
			return "OH";
		}
	}

	public String formulaHTML() {
		if (numeroAtomos>1) {
			return "(OH)"+ "<sub>"+numeroAtomos+"</sub>";
		} else {
			return "OH";
		}
	}
	
	

	// Patrón de nomenclatura
	

}
