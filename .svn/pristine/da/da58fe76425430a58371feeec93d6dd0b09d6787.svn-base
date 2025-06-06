/**
 * *****************************************************************************
 * *****************************************************************************
 * clase AFN
 * *****************************************************************************
 * *****************************************************************************
 */

package siette.util.regex;

/**
 * AFN: Automata Finito No determinista
 *
 * @author Ricardo Conejo
 * @version 1.01
 */
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

import siette.util.Combinatoria;


class AFN extends AF {

/**
 * concatena a este automata otro mas, mediante transiciones epsilon desde los
 * estados finales al estado inicial se requiere que los nombres de los estados
 * sean diferentes. En caso conrario, llamar antes de concatenar al metodo clone()
 */
public AFN concatena(AFN afn) {
    if (afn == null || afn.tablaTransiciones == null || afn.tablaTransiciones.isEmpty())
        return this;

    if (tablaTransiciones == null || tablaTransiciones.isEmpty()) {
        apuntaEstadoInicial(afn.estadoInicial);
    } else {
        Iterator i = estadosFinales.iterator();
        while (i.hasNext()) {
            String estadoFinal = (String) i.next();
            apuntaTransicion(estadoFinal, EPSILON, afn.estadoInicial);
        }
    }
    apuntaTransicion(afn.tablaTransiciones);
    estadosFinales = (HashSet) afn.estadosFinales.clone();
    return this;
}

public AFN union(AFN afn) {
    if (afn == null || afn.tablaTransiciones == null
            || afn.tablaTransiciones.isEmpty())
        return this;

    if (tablaTransiciones == null || tablaTransiciones.isEmpty()) {
        apuntaEstadoInicial(afn.estadoInicial);
        apuntaTransicion(afn.tablaTransiciones);
        apuntaEstadoFinal(afn.estadosFinales);
    } else {
        apuntaTransicion(estadoInicial, AF.EPSILON, afn.estadoInicial());
        apuntaTransicion(afn.tablaTransiciones);
        apuntaEstadoFinal(afn.estadosFinales);
    }
    return this;
}

// Concatena al automata (this) una union de todos los
// posibles automatas que se obtienen concatenando los
// automatas que se pasan en el parametro afns
public AFN permutaciones(AFN[] afns) {
	AFN afn = new AFN();
	if (afns == null || afns.length == 0) {
		return this;
	} else if ( afns.length == 1 ) {
		afn = afns[0];
	} else {
		Object[][] permutaciones = Combinatoria.permutaciones(afns);
		for(int i=0; i<permutaciones.length; i++) {
			AFN concatenacion = new AFN();
			for(int j=0; j<permutaciones[i].length; j++) {
				AFN elemento = (AFN) permutaciones[i][j];
				concatenacion.concatena((AFN) elemento.clone());
			}
			afn = afn.union(concatenacion);
		}
	}
    return concatena(afn);
}

public AFN permutaciones(ArrayList lista) {
	AFN[] afns = (AFN[]) lista.toArray(new AFN[lista.size()]);
	return permutaciones(afns);
}

// Devuelve el conunto de todos os estados a los cuales
// se puede llegar mediante una transicion por la cadena vacia
// @param estados Estados de artida
// @return estados a los que se puede llegar
public HashSet cierreEpsilon(HashSet estados) {
    if (estados == null)
        return null;

    HashSet estadosIniciales;
    HashSet nuevosEstados = (HashSet) estados.clone();
    do {
        estadosIniciales = (HashSet) nuevosEstados.clone();
        Iterator eEstados = estadosIniciales.iterator();
        while (eEstados.hasNext()) {
            String estado = (String) eEstados.next();
            HashSet masEstados = transicion(estado, EPSILON);
            if (masEstados != null) {
                nuevosEstados.addAll(masEstados);
            }
        }
    } while (!estadosIniciales.containsAll(nuevosEstados));
    return nuevosEstados;
}

// Crea una copia del automata con nombres distintos para los estados
public Object clone() {
	AFN afn = new AFN();
	Hashtable tablaConversion = new Hashtable();

	// copiar la tabla de transiciones (se van creando copias de los estados)
    Enumeration eEstado = tablaTransiciones.keys();
    while (eEstado.hasMoreElements()) {
        String estado = (String) eEstado.nextElement();
        String estado_clone = (String) tablaConversion.get(estado);
        if (estado_clone==null || estado_clone.equals("")) {
        	estado_clone = afn.nuevoEstado();
        	tablaConversion.put(estado,estado_clone);
        }
        Hashtable transiciones = (Hashtable) tablaTransiciones.get(estado);
        Enumeration eSimbolo = transiciones.keys();
        while (eSimbolo.hasMoreElements()) {
            Character simbolo = (Character) eSimbolo.nextElement();
            HashSet nuevosEstados = (HashSet) transiciones.get(simbolo);
            Iterator i = nuevosEstados.iterator();
            while (i.hasNext()) {
                String nuevoEstado = (String) i.next();
                String nuevoEstado_clone = (String) tablaConversion.get(nuevoEstado);
                if (nuevoEstado_clone==null || nuevoEstado_clone.equals("")) {
                	nuevoEstado_clone = afn.nuevoEstado();
                	tablaConversion.put(nuevoEstado,nuevoEstado_clone);
                }
                afn.apuntaTransicion(estado_clone, simbolo, nuevoEstado_clone);
            }
        }
    }

    // Copiar el estado inicial
    afn.estadoInicial = (String) tablaConversion.get(estadoInicial);

    // Copiar los estados finales
    Iterator eEstados = estadosFinales.iterator();
    while (eEstados.hasNext()) {
        String estado = (String) eEstados.next();
        String estado_clone = (String) tablaConversion.get(estado);
        if (estado_clone==null || estado_clone.equals("")) {
        	// Ya deberian estar credos los estados
        	estado_clone = afn.nuevoEstado();
        	tablaConversion.put(estado,estado_clone);
        }
        afn.apuntaEstadoFinal(estado_clone);
    }

	return afn;
}




public boolean reconoce(String cadena) {
    HashSet estados = new HashSet();
    HashSet deltaEstados = new HashSet();
    estados.add(estadoInicial);
    estados = cierreEpsilon(estados);
    for (int i = 0; i < cadena.length(); i++) {
        deltaEstados = new HashSet();
        Character simbolo = new Character(cadena.charAt(i));
        // System.out.println("estados ="+estados);
        // System.out.println("simbolo =<"+simbolo+">");
        Iterator eEstados = estados.iterator();
        while (eEstados.hasNext()) {
            String estado = (String) eEstados.next();
            HashSet nuevoEstado = transicion(estado, simbolo);
            if (nuevoEstado != null) {
                deltaEstados.addAll(nuevoEstado);
            }
        }
        deltaEstados = cierreEpsilon(deltaEstados);
        estados = deltaEstados;
        // logger.debug("estados'="+estados);
    }
    deltaEstados.retainAll(estadosFinales); // interseccion con estados finales
    return !deltaEstados.isEmpty(); // no vacia.
}

public String[] genera(int longitud) {

	// ASSERT casos particulares
	if (tablaTransiciones==null || tablaTransiciones.isEmpty() ) {
		 if (!esFinal(estadoInicial)) {
			 return new String[0];
		 } else {
			 String[] st = { "" };
			 return st;
		 }
	}

	// Clase auxilar, ara agrupar dos valores
	class EstadoCadena {
		String estado;
		String cadena;
		public EstadoCadena(String estado, String cadena) {
			this.estado=estado;
			this.cadena=cadena;
		}
	}

	// Variables principales
	HashSet resultado = new HashSet();
	ArrayList viejo = new ArrayList();
	ArrayList nuevo = new ArrayList();

	// Se inicia con los estados iniciales, y la cadena vacia
	HashSet estados = new HashSet(); // auxiliar
	estados.add(estadoInicial);
	estados = cierreEpsilon(estados);
	for(Iterator i=estados.iterator(); i.hasNext(); ) {
		viejo.add(new EstadoCadena((String) i.next(),""));
	}

	for (int l=0; l<longitud; l++) {
		for(Iterator i=viejo.iterator(); i.hasNext(); ) {
			EstadoCadena e = (EstadoCadena) i.next();
	        Hashtable transiciones = (Hashtable) tablaTransiciones.get(e.estado);
	        Enumeration eSimbolo = transiciones.keys();
	        while (eSimbolo.hasMoreElements()) {
	            Character simbolo = (Character) eSimbolo.nextElement();
				if (simbolo==AF.EPSILON) {
					// ignorar las transiciones epsilon, porque ya han sido incluidas
					continue;
				} else if (simbolo==AF.COMODIN) {
					// generar un caracter aleatorio
					simbolo = alfaNumericoAleatorio();
				}
				// Hallar los nuevos estados a los que transita
				HashSet nuevosEstados = transicion(e.estado, simbolo);
	            if (nuevosEstados != null) {
	            	// y su cierreEpilon
	            	nuevosEstados = cierreEpsilon(nuevosEstados);
	            	// meter todas las nuvas posibles transiciones en el nuevo conjunto
	            	for(Iterator q=nuevosEstados.iterator(); q.hasNext(); ) {
	            		String estadoNuevo = (String) q.next();
	            		nuevo.add(new EstadoCadena(estadoNuevo, e.cadena+simbolo.toString() ));
	            		// Si el estado es final, apuntar la cadena
	        			if ( esFinal(estadoNuevo) ) {
	        				// System.out.println(">>>>>>>>>"+e.cadena+simbolo.toString());
	        				resultado.add(e.cadena+simbolo.toString());
	        			}
	            	}
	            }

			} // para cada simbolo con el que hay una transicion
		} // para cada estado en el viejo conjunto
        viejo = nuevo;
		nuevo = new ArrayList();
	} // bucle principal, longitud de la cadena

	return (String[]) resultado.toArray(new String[resultado.size()]);
}


}