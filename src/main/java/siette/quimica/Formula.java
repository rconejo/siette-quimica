package siette.quimica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import siette.util.Combinatoria;

public class Formula extends ArrayList<Molecula> {
	
	private static final long serialVersionUID = 1L;

	public Formula() {
		super();
	}

	public Formula(String st) throws FormulaException {
		super();
		fromString(st);
	}
	
	protected Formula fromString(String st) throws FormulaException {
		int p=0; // puntero
		String estado = "q0";
		String s="";
		int n=1;
		while(p<st.length()) {
			char ch = st.charAt(p);
			if ("q0".equals(estado)) {
				if (Character.isUpperCase(ch)) {
					estado = "q1";
					s = ""+ch;
				} else if ( ch == '(') {
					s = ""+ch;
					estado = "q2";
				} else {
					throw new FormulaException();
				}
			} else if ("q1".equals(estado)) {
				if (Character.isLowerCase(ch)) {
					estado = "q1";
					s += ch;
				} else if (Character.isUpperCase(ch) ) {
					add(new Molecula(s,1));
					s=""+ch; n=1;
					estado = "q1";
				} else if (ch == '(' ) {
					add(new Molecula(s,n));
					s=""+ch; n=1;
					estado = "q2";
				} else if ('0' <= ch && ch < '9') {
					n = (int) ch - (int) '0';
					add(new Molecula(s,n));
					estado = "q0";
				}
			} else if ("q2".equals(estado)) {
				if (ch == ')') {
					s += ch;
					estado = "q1";
				} else {
					s += ch;
				}
			} else  {
				throw new FormulaException();
			}
			p++;
		}
		if ("q1".equals(estado)) {
			add(new Molecula(s,1));
		}
		return this;
	}

	public Formula(Formula formula) {
		super();
		for(Molecula m: formula) {
			Molecula mp = new Molecula(m);
			add(mp);
		}
	}

	public Formula(Compuesto compuesto) throws FormulaException {
		super();
		if (compuesto instanceof CompuestoBinario) {
			CompuestoBinario cb = (CompuestoBinario) compuesto;
			Molecula ma = new Molecula(cb.anion);
			Molecula mc = new Molecula(cb.cation);
			add(mc);
			add(ma);
		} else {
			fromString(compuesto.formula());
		}
	}

	public Formula combinar(Formula formula) {
		for(Molecula m: formula) {
			boolean found = false;
			for (Molecula tm : this) {
				if (tm.simbolo.equals(m.simbolo())) {
					tm.nAtomos += m.nAtomos;
					found = true;
				}
			} 
			if (!found) {
				add(0,m);
			}
		}
		return this;
	}


	public String toString() {
		String st="";
		for(Molecula a: this) {
			st += a.simbolo + (a.nAtomos>1 ? a.nAtomos : "");
		}
		return st;
	}

	public int nElemento(String s) throws FormulaException {
		int conta = 0;
		for(Molecula m : this) {
			if (s.equals(m.simbolo)) {
				conta += m.nAtomos;
			} else if (m.simbolo.startsWith("(")) {
				String st = m.simbolo.substring(1,m.simbolo.length()-1);
				Formula formula = new Formula(st);
				conta += formula.nElemento(s) * m.nAtomos;
			}
		}
		return conta;
	}
	
	public int nOxigeno() throws FormulaException {
		try {
			return nElemento("O");
		} catch (FormulaException e) {
			return 0;
		}
	}
	
	public int nHidrogeno()  {
		try {
			return nElemento("H");
		} catch (FormulaException e) {
			return 0;
		}
	}

	public Set<Integer> balanceCargas() {
		Set<Integer> cargas = new HashSet<Integer>();
		for(Molecula m: this) {
			Set<Integer> cargasIon = new HashSet<Integer>();
			if (m.simbolo.startsWith("(")) {
				String st = m.simbolo.substring(1,m.simbolo.length()-1);
				Formula formula;
				
				try {
					formula = new Formula(st);
					cargasIon = formula.balanceCargas();
				} catch (FormulaException e) {
					e.printStackTrace();
				}
			} else {
				Elemento e = TablaPeriodica.selectElemento(m.simbolo);
				if (e!=null) {
					cargasIon = e.numerosOxidacion();
				}
			}
			if (cargas.isEmpty()) {
				for(Integer c : cargasIon)  {
					cargas.add(c*m.nAtomos);
				}
			} else {
				Set<Integer> nuevasCargas = new HashSet<Integer>();
				for(Integer carga : cargas) {
					for(Integer cargaIon: cargasIon) {
						nuevasCargas.add(carga+cargaIon*m.nAtomos);
					}
				}
				cargas = nuevasCargas;
			}
		}
		return cargas;
	}
	
	
	
	
	public ArrayList<Formula> permutaciones() {
		  // Permutacion del orden de los elementos, por ejemplo de H2SO4 genera SO4H2, SH2O4, etc..
		  int[][] permutaciones = Combinatoria.permutaciones(size());
		  ArrayList<Formula> variaciones = new ArrayList<Formula>();
		  for(int i=0; i<permutaciones.length; i++) {
			  Formula formula = new Formula();
			  for (int j=0; j<size(); j++) {
				  formula.add(get(permutaciones[i][j]));
			  }
			  if (!equals(formula)) { // No incluir la formula original
				  variaciones.add(formula);
			  }
		  }
		  return variaciones;
	}

	public ArrayList<Formula> faltaH() {
		ArrayList<Formula> variaciones = new ArrayList<Formula>();
		Set<Integer> cargas = balanceCargas();
		int nHidrogeno = nHidrogeno();
		if (!cargas.contains(0)) {
			for(Integer carga: cargas) {
				Formula formula = new Formula(this);
				boolean valida = false;
				if (carga<0) {  // Si esta cargado negativamente, añadir uno o varios hidrogenos
					if (nHidrogeno>0) {
						for(Molecula m: formula) {
							if (m.simbolo.equals("H")) {
								m.nAtomos += -carga;
								valida = true;
							}
						}
					} else {
						Molecula m = new Molecula("H", -carga);
						formula.add(0,m);
						valida=true;
					}
				} else if (carga>0) {
					if (nHidrogeno>1) {
						for(Molecula m: formula) {
							if (m.simbolo.equals("H")) {
								if (m.nAtomos>carga+1) {
									m.nAtomos -= carga;
									valida = true;
								}
							}
						}
					}
				}
				if (valida) {
					variaciones.add(formula);
				}
			}
		}
		return variaciones;
	}

	// No se usa, se sustituye por el siguiente 
	public ArrayList<Formula> variacionesAtomos() {
		//Aumenta o disminuye los indices de cada una de las moleculas
		ArrayList<Formula> variaciones = new ArrayList<Formula>();
		for(int i=0; i<size();i++) {
			Formula formula1 = new Formula(this);
			Formula formula2 = new Formula(this);
			(formula1.get(i)).nAtomos++;
			variaciones.add(formula1);
			if ((formula2.get(i)).nAtomos>1) {
				(formula2.get(i)).nAtomos--;
				variaciones.add(formula2);
			}
		}
		return variaciones;
	}


	public static int mcd(int a, int b) {
	    if (b == 0) return a;
	    return mcd(b, a % b);	
	}
	
	
	public void simplificar() {
		// Hallar el valor mcd
		int mcd = get(0).nAtomos;
		for(Molecula m: this) {
			mcd = mcd(mcd,m.nAtomos);
		}
		for(Molecula m: this) {
			m.nAtomos /= mcd;
		}
	}

	public ArrayList<Formula> variacionesSinSimplificar() {
		// Genera variaciones multiplicando los indices por 2,3,4,5, o 6
		ArrayList<Formula> variaciones = new ArrayList<Formula>();
		for(int i=2; i<7; i++) {
			Formula formula1 = new Formula(this);
			for(Molecula a: formula1) {
				a.nAtomos *= i;
			}
			variaciones.add(formula1);
		}
		return variaciones;
	}

	
	private static HashMap<String,String> confusionElementos = new HashMap<String,String>();
	static {
		confusionElementos.put("He", "H");
		confusionElementos.put("F", "P");
		confusionElementos.put("P", "F");
		confusionElementos.put("Mn", "Mg");
		confusionElementos.put("Mg", "Mn");
		confusionElementos.put("Pt", "Pd");
		confusionElementos.put("Pt", "Pb");
		confusionElementos.put("Pt", "Ag");
		confusionElementos.put("Ag", "Au");
		confusionElementos.put("Au", "Ag");
		confusionElementos.put("Ag", "Pt");
		confusionElementos.put("Ag", "Pd");
		confusionElementos.put("Pd", "Pt");
		confusionElementos.put("Pd", "Pb");
		confusionElementos.put("Be", "B");
		confusionElementos.put("Cu", "Co");
		confusionElementos.put("Cu", "C");
		confusionElementos.put("C", "Ca");
		confusionElementos.put("Cd", "Ca");
		confusionElementos.put("Cr", "C");
		confusionElementos.put("K", "P");
		confusionElementos.put("In", "I");
		confusionElementos.put("Ni", "N");
		confusionElementos.put("N", "Ni");
		confusionElementos.put("Si", "S");
		confusionElementos.put("Se", "S");
		confusionElementos.put("As", "At");
		confusionElementos.put("At", "As");
		confusionElementos.put("Hg", "Mg");
	}
	
	private Formula cambiarElemento(String simbolo, String nuevoSimbolo) {
		// Devuelve una nueva formula cambiando un elemento por otro
		Formula formula1 = new Formula(this);
		for(Molecula m1: formula1) {
			String simbolo1 = m1.simbolo();
			if (simbolo1.equals(simbolo)) {
				m1.setSimbolo(nuevoSimbolo);
			}
		}
		return formula1;
	}
	
	
	// Se cambia por un patron mas generico que permite la confusion de dos elementos cualesquiera
	public ArrayList<Formula> variacionesElementos() {
		// Genera variaciones cambiando el simbolo de un elemento por otros con los que pudiera confundirse
		ArrayList<Formula> variaciones = new ArrayList<Formula>();
		for(Molecula m: this) {
			String simbolo = m.simbolo();
			for(String s: confusionElementos.keySet() ) {
				if (s.equals(simbolo)) {
					variaciones.add(cambiarElemento(s, confusionElementos.get(s)));
				}
			}
		}
		if (variaciones.isEmpty()) {
			variaciones.add(this);
		}
		return variaciones;
	}

	public String patronNumeroUno() {
		String patron = "";
		for(Molecula m : this) {
			if (m.nAtomos==1) {
				patron += m.simbolo() + "1";
			} else {
				patron += m.toString();
			}
			;
		}
		return patron;
	}

	public String patronSubindicesOxidacion(int nOxidacion) {
		String patron = toString();
		if (size()==2) {
			try {
				HashSet<String> formulas = new HashSet<String>();
				Integer subindice0 = (get(0)).nAtomos;
				Integer subindice1 = (get(1)).nAtomos;
				String formula1 = (get(0)).simbolo + (nOxidacion!=1?nOxidacion:"") + (get(1)).simbolo + (subindice0!=1?subindice0:"");
				Formula f1 = new Formula(formula1);
				formulas.add(f1.toString());
				f1.simplificar();
				formulas.add(f1.toString());
				String formula2 =  (get(0)).simbolo + (subindice1!=1?subindice1:"") + (get(1)).simbolo + (nOxidacion!=1?nOxidacion:"");
				Formula f2 = new Formula(formula2);
				formulas.add(f2.toString());
				f2.simplificar();
				formulas.add(f2.toString());
				for(String st : formulas) {
					patron += "|" + st;
				}
			} catch (FormulaException e) {}
		}
		return patron;
	}

	public String patronNoSubindices() {
		String patron = "";
		for(Molecula m : this) {
			patron += m.simbolo();
		}
		return patron;
	}

	
	public String patronSubindicesDesordenados() throws FormulaException {
		String patron = "";
		if (size()==2) {
			Integer subindice0 = (get(0)).nAtomos;
			Integer subindice1 = (get(1)).nAtomos;
			patron = (get(0)).simbolo + (subindice1!=1?subindice1:"") + (get(1)).simbolo + (subindice0!=1?subindice0:"");
		}
		return patron;
	}
	

	public String patronSubindices() throws FormulaException {
		String patron = "";
		for(Molecula m : this) {
			if (m.simbolo.startsWith("(")) {
				String st = m.simbolo.substring(1,m.simbolo.length()-1);
				Formula formula = new Formula(st);
				patron += "(" +"\\(" + formula.patronSubindices() + "\\)" + "{[1-9]}" + "|" + formula.patronSubindices() + ")";
			} else {
				patron += m.simbolo() + "{[1-9]}";
			}
		}
		return patron;
	}
	

	public String patronSubindices(String simbolo) throws FormulaException {
		String patron = "";
		for(Molecula m : this) {
			if (m.simbolo.startsWith("(")) {
				String st = m.simbolo.substring(1,m.simbolo.length()-1);
				Formula formula = new Formula(st);
				patron += "(" + formula.patronSubindices(simbolo) + ")" + (m.nAtomos!=1?m.nAtomos:"");
			} else {
				if (m.simbolo.equals(simbolo)) {
					patron += m.simbolo() + "{[1-9]}";
				} else {
					patron += m.toString();
				}
			}
		}
		return patron;
	}

	public String patronElementos() throws FormulaException {
		String patron = "";
		for(Molecula m1 : this) {
			String subpatron = "";
			for(Molecula m2 : this) {
				if (m1.simbolo.equals(m2.simbolo)) {
					if (m1.simbolo.startsWith("(")) {
						String st = m1.simbolo.substring(1,m1.simbolo.length()-1);
						Formula formula = new Formula(st);
						subpatron += "(" + formula.patronElementos() + ")" + (m1.nAtomos!=1?m1.nAtomos:"");
					} else {
						subpatron += "[A-Z]{[a-z]}" + (m2.nAtomos!=1?m2.nAtomos:"");
					}
				} else {
					subpatron += m2.toString();
				}
			}
			patron += (patron.equals("") ? "" : "|") + subpatron;
		}
		return patron;
	}

}
