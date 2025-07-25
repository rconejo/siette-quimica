package siette.quimica;

import java.util.HashSet;

import siette.util.regex.ExpresionRegular;

public class ConjuntoCompuestos extends HashSet<Compuesto> {

	private static final long serialVersionUID = 1L;

	public Compuesto buscar(String formula) {
		for(Compuesto c : this) {
			if (c.formula().equals(formula)) {
				return c;
			}
		}
		return null;
	}
	
	public Compuesto buscar(String nombre, int idioma) {
		String[] patrones = new String[size()];
		Compuesto[] compuestos = new Compuesto[size()];
		int i = 0;
		for(Compuesto c : this) {
			c.idIdioma(idioma);
			compuestos[i] = c;
			patrones[i] = c.patron(c.nomenclaturasTodas());
			i++;
		}
		ExpresionRegular er = new ExpresionRegular(patrones, true, true, true, true, false);
		i = er.pertenece(nombre);
		if (i>=0) {
			return compuestos[i];
		}
		return null;
	}

	public String toString() {
		String st = "";
		for(Compuesto c : this) {
			st += c + "\n";
		}
		return st;
	}

}
