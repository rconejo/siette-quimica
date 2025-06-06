package siette.quimica;

public class Molecula  {
   protected String simbolo;
   protected int nAtomos;
   
   public Molecula(String s, int n) {
      simbolo = new String(s);
      nAtomos = n;
   }
   
   public Molecula(Molecula m) {
	 simbolo = m.simbolo;
	 nAtomos = m.nAtomos;
   }

   public Molecula(Ion ion) {
	 simbolo = ion.getCompuesto().formula();
	 nAtomos = ion.numeroAtomos;
   }

public Elemento elemento() {
   	   return TablaPeriodica.selectElemento(simbolo);
}

public void setElemento(String string) {
	simbolo = TablaPeriodica.selectElemento(simbolo).simbolo();
}


public String simbolo() {
	   return simbolo;
}

public void setSimbolo(String string) {
	simbolo = string;
}

public String toString() {
	return simbolo+(nAtomos>1?nAtomos:"");
}


}