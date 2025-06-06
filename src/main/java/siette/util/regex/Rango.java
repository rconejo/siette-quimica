package siette.util.regex;

public class Rango {

double minimo;
double maximo;
Magnitud magnitud = Magnitud.VACIO;
boolean esMagnitudOpcional = false;
boolean yaReconocido = false; // Si el rango ha sido previamente reconocido, no debe volver a encajar


Rango(double a, double b, Magnitud m, boolean opcional) {
	if (a<b) {
       minimo = a;
       maximo = b;
	} else {
       minimo = b;
       maximo = a;
	}
	this.magnitud = m;
	this.esMagnitudOpcional = opcional;
	this.yaReconocido = false;
}

Rango(double val) {
	   this(val,val,Magnitud.VACIO,false);
}

Rango(double val, Magnitud m, boolean op) {
	   this(val,val,m,op);
}

Rango(double min, double max) {
   this(min,max,Magnitud.VACIO,false);
}

Rango() {
    this(Double.MAX_VALUE); // Rango casi imposible
}

public boolean contiene(double valor, Magnitud mag) {
	boolean contiene = false;
	if (!yaReconocido) {
		double dMin = minimo;
		double dMax = maximo;
		double dValor = valor;
		if (  (magnitud!=null && magnitud!=Magnitud.VACIO)  &&  (mag!=null && mag!=Magnitud.VACIO)  ) {
			// Se han definido magnitudes en el patron y en la respuesta
			// System.out.println("mag:  (antes)     "+mag);
			mag = magnitud.magnitudEquivalente(mag);
			dValor *= mag.factor;
			dMin *= magnitud.factor;
			dMax *= magnitud.factor;
			// System.out.println("magnitud:         "+magnitud);
			// System.out.println("valor             "+valor);
			// System.out.println("mag:              "+mag);
			// System.out.println("dMin,dValor,dmaX  "+ dMin+","+dValor+","+dMax);
		    if (magnitud.unidadEquivalente.equals(mag.unidadEquivalente) && dMin <= dValor && dValor <= dMax) {
				// System.out.println("contiene  = true");
		        contiene = true;
		    }
		} else if (  (magnitud!=null && magnitud!=Magnitud.VACIO)  &&  !(mag!=null && mag!=Magnitud.VACIO)  ) {
			// Se han definido magnitudes en el patron pero no en la respuesta
			// (Se asume que la respuesta esta en el sistema internacional)
	        if (esMagnitudOpcional) {
	    		dMin *= magnitud.factor;
	    		dMax *= magnitud.factor;
	    	    if (dMin <= dValor && dValor <= dMax) {
	    	    	contiene = true;
	    	    }
	        } else {
	    		// (Se asume que hay un fallo al no especificar las unidades)
	    		contiene = false;
	        }
		} else if ( !(magnitud!=null && magnitud!=Magnitud.VACIO)  &&  (mag!=null && mag!=Magnitud.VACIO)  ) {
			// No se han definido magnitudes en el patron pero si en la respuesta
			// (No se comprueban las magnitudes, acepta cualquier magnitud de la lista )
			dValor *= mag.factor;
		    if (dMin <= dValor && dValor <= dMax) {
		    	contiene = true;
		    }
		} else if (  !(magnitud!=null && magnitud!=Magnitud.VACIO)  &&  !(mag!=null && mag!=Magnitud.VACIO)  ) {
			// No se han definido magnitudes ni en el patron y ni en la respuesta
			// (No se comprueban las magnitudes)
		    if (dMin <= dValor && dValor <= dMax) {
		    	contiene = true;
		    }
		}
		yaReconocido = contiene;
		// System.out.println("Rango.contiene():  dMin="+dMin+ " dMax="+dMax + " dValor="+dValor + " contiene="+contiene);
	}
    return contiene ;
}

public boolean contiene(double dValor) {
    return contiene(dValor,Magnitud.VACIO);
}

public String toString() {
    return   "[" + minimo + "," + maximo + "]"
           + (magnitud!=null?
        		   (esMagnitudOpcional ?
        				     " {"+ magnitud + "}"
        				   : " "+ magnitud
        		   )
                   :""
              )
           ;
}

public void reset() {
	yaReconocido = false;
}

}  // class Rango

