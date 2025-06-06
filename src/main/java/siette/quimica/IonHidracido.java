package siette.quimica;

import siette.util.Random;
import siette.util.text.Strings;

public class IonHidracido extends Oxoanion {
	

	public IonHidracido(Hidruro acido, int valenciaNegativa) {
		super();
		int n = acido.nHidrogeno();
		acido.nHidrogeno(n-valenciaNegativa);
		setCompuesto(acido); 
	}


	public static IonHidracido random(TablaPeriodica tabla) {
		Hidruro acido = Hidruro.random(tabla); 
		int valenciaNegativa = tabla.nextInt(1,acido.nHidrogeno());
		return new IonHidracido(acido, valenciaNegativa);
	}
	
	
	public String formula() {
		String formula = compuesto.formula();
		if (numeroAtomos>1) {
			formula = "(" + formula +")" + numeroAtomos;
		}
		return formula;
	}


	/////////////////////////////////////////////////
	////////////// Nomenclatura /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String nomenclaturaSistematica(boolean multiplesValencias) {
		return nomenclaturaSistematica();
	}

	@Override
	public String nomenclaturaSistematica() {
		String hidrogeno = (idIdioma==Compuesto.EN ? "hydrogen" : "hidrogeno");
		Hidruro acido = ((Hidruro)compuesto);
		String nobreAnion = acido.nomenclaturaSistematica();
		if (idIdioma==Compuesto.EN) {
			nobreAnion = hidrogeno + (nobreAnion.substring(nobreAnion.indexOf(" ")+1, nobreAnion.length())).toLowerCase();
		} else {
			nobreAnion = hidrogeno + (nobreAnion.substring(0, nobreAnion.indexOf(" "))).toLowerCase();
		}
		if (numeroAtomos>1) {
			nobreAnion = prefijo(numeroAtomos) + "(" + nobreAnion +")" ;
		}
		return nobreAnion;
	}

	@Override
	public String nomenclaturaStock() {
		String hidrogeno = (idIdioma==Compuesto.EN ? "hydrogen" : "hidrogeno");
		Hidruro acido = ((Hidruro)compuesto);
		String nobreAnion = acido.nomenclaturaSistematica();
		if (idIdioma==Compuesto.EN) {
			nobreAnion = hidrogeno + (nobreAnion.substring(nobreAnion.indexOf(" ")+1, nobreAnion.length())).toLowerCase();
		} else {
			nobreAnion = hidrogeno + (nobreAnion.substring(0, nobreAnion.indexOf(" "))).toLowerCase();
		}
		return nobreAnion;
	}


	@Override
	public String nomenclaturaClasica() {
		Hidruro acido = ((Hidruro)compuesto);
		String nobreAnion = acido.anion.nomenclaturaClasica();
		return nobreAnion + " " + Strings.filterHTMLtoTXT("&aacute;cido");
	}

	/////////////////////////////////////////////////
	//////////////   Patrones   /////////////////////
	/////////////////////////////////////////////////

	@Override
	public String patronSistematica(boolean multiplesValencias) {
		String patron = nomenclaturaSistematica();
		patron = Compuesto.patronEscape(patron);
		return patron;
	}

	
	/////////////////////////////////////////////////

	public static void main(String[] argv) {		
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random(1));
		tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			IonHidracido ox = IonHidracido.random(tablaPeriodica);		
			System.out.println(ox.formula() + " : " + ox.nomenclaturaTradicional() + " : "+ox.nomenclaturaSistematica());
		}
	}

	
}
