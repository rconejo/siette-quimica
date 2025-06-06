package siette.quimica;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import siette.util.corpus.Row;
import siette.util.text.Strings;

public class Elemento extends Row {
	
	private static final long serialVersionUID = 1L;

	public Elemento() {
		super();
	}

	public Elemento(Row row) {
		super(row);
	}
	
	public int numero() {
		return getInt(TablaPeriodica.NUMERO);
	}
	
	public String simbolo() {
		return get(TablaPeriodica.SIMBOLO);
	}

	public String nombre() {
		 String nombre = get(TablaPeriodica.NOMBRE);
		return Strings.filterHTMLtoTXT(nombre);
	}
	
	public String name() {
	    String nombre = get(TablaPeriodica.NAME);
		return Strings.filterHTMLtoTXT(nombre);
	}

	public String patron() {
		 String nombre = get(TablaPeriodica.PATRON);
		return Strings.filterHTMLtoTXT(nombre);
	}
	
	public Double electronegatiovidad() {
		return getDouble(TablaPeriodica.ELECTRONEGATIVIDAD);
	}

	public int grupo() {
		return getInt(TablaPeriodica.GRUPO);
	}

	public int periodo() {
		return getInt(TablaPeriodica.PERIODO);
	}

	public Set<Integer> numerosOxidacion() {
		return getSet(TablaPeriodica.OXIDACION);
	}
	
	public String numerosOxidacion(String st) {
		return set(TablaPeriodica.OXIDACION, st);
	}

	public Set<Integer> numerosOxidacionPositivos() {
		Set<Integer> numerosPositivos = new TreeSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8)); 
		Set<Integer> numerosOxidacion = numerosOxidacion();
		numerosOxidacion.retainAll(numerosPositivos);
		return numerosOxidacion;
	}

	public Set<Integer> numerosOxidacionNegativos() {
		Set<Integer> numerosNegativos = new TreeSet<Integer>(Arrays.asList(-1,-2,-3,-4,-5,-6,-7,-8)); 
		Set<Integer> numerosOxidacion = numerosOxidacion();
		numerosOxidacion.retainAll(numerosNegativos);
		return numerosOxidacion;
	}

	public Set<Integer> getSet(int pos) {
		Set<Integer> set = new TreeSet<Integer>();
		Double d = getDouble(pos);
		// Las casillas con un solo número no las divide bien
		// Si devuelve un valor Infinity es que no se trata de un solo numero, sino de varios (numero de oxidacion)
		if (Double.isInfinite(d) || Double.isNaN(d)) {
			String vals = get(pos);
			String[] val = vals.split("\\|");
			for(String v: val) {
				try {
					set.add(Integer.parseInt(v));
				} catch (NumberFormatException e) {
				}
			}
		} else {
			set.add(d.intValue());
		}
		return set;
	}

	/**
	 * @return Devuelve el conjunto de todas las valencias
	 */
	public Set<Integer> valencias() {
		return numerosOxidacion();
	}
	
	public Set<Integer> valenciasPositivas() {
		return numerosOxidacionPositivos();
	}
	
	public Set<Integer> valenciasNegativas() {
		return numerosOxidacionNegativos();
	}
	
/*	
	// Elige un numero de oxidacion (valencia) del elemento entre una lista de posibles valores 
	public int valencia(Set<Integer> posiblesValores) {
		Set<Integer> numerosOxidacion = numerosOxidacion();
		numerosOxidacion.retainAll(posiblesValores);
		// for(int n: numerosOxidacion) {
		//	System.out.print("n="+n+" ");
		// }
		// System.out.println();
		int n = 0;
		if (!numerosOxidacion.isEmpty()) {
			n = (int) TablaPeriodica.select(numerosOxidacion);
		}
		return n;
	}
	
	public int valencia() {
		return valencia(numerosOxidacion());
	}

	public int valenciaPositiva() {
		Set<Integer> numerosPositivos = new TreeSet<>(Arrays.asList(1,2,3,4,5,6,7,8)); 
		return valencia(numerosPositivos);
	}

	public int valenciaNegativa() {
		Set<Integer> numerosPositivos = new TreeSet<>(Arrays.asList(-1,-2,-3,-4,-5,-6,-7,-8)); 
		return valencia(numerosPositivos);
	}
*/
	
	public String formulaHTML() {
		return simbolo(); // + (numerosOxidacion()>1?"<sup>"+numeroAtomos+"</sup>":"") ;
	}

	/**
	 * Devuelve true si este elemento es mas electronegativo que el otro
	 * @param otro
	 * @return
	 */
	public boolean masElectronegativo(Elemento otro) {
		if (this.grupo()>otro.grupo()) {
			return true;
		} else if (this.grupo() == otro.grupo()) {
			if(this.periodo() < otro.periodo()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Elemento) {
			Elemento elemento = (Elemento) obj;
			return simbolo().equals(elemento.simbolo());
		} else if (obj instanceof String) {
			return simbolo().equals((String)obj);
		} else {
			return false;
		}
	}


}
