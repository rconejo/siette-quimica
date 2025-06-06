/**
 * *****************************************************************************
 * *****************************************************************************
 * clase AF
 * *****************************************************************************
 * *****************************************************************************
 */

package siette.util.regex;

/**
 * AF: Automata Finito
 *
 * @author Ricardo Conejo
 * @version 1.01
 */

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

abstract class AF {

// tablaTransiciones[i][j][k]
// i, estado
// j, simbolo
// k, posible nuevo estado
// int tablaTransiciones[][][];
protected String estadoInicial = "";
protected HashSet estadosFinales = new HashSet();
protected Hashtable tablaTransiciones = new Hashtable();

public final static Character EPSILON = new Character('\u0000');
public final static Character COMODIN = new Character('\uffff');

public void apuntaEstadoInicial(String estado) {
    if (estado != null && !estado.equals("")) {
        estadoInicial = estado;
    }
}

public void apuntaEstadoFinal(String estadoFinal) {
    if (estadoFinal == null || estadoFinal.equals(""))
        return;

    if (estadosFinales == null) {
        estadosFinales = new HashSet();
    }
    estadosFinales.add(estadoFinal);
}

public void apuntaEstadoFinal(HashSet finales) {
    if (finales == null || finales.isEmpty())
        return;

    if (estadosFinales == null) {
        estadosFinales = new HashSet();
    }
    estadosFinales.addAll(finales);
}

public void apuntaTransicion(String estado, Character simbolo,
        String nuevoEstado) {
    if (estado == null || estado.equals(""))
        return;
    if (simbolo == null)
        return;
    if (nuevoEstado == null || nuevoEstado.equals(""))
        return;

    if (tablaTransiciones == null) {
        tablaTransiciones = new Hashtable();
    }
    Hashtable transiciones = (Hashtable) tablaTransiciones.get(estado);
    if (transiciones == null) {
        HashSet nuevosEstados = new HashSet();
        nuevosEstados.add(nuevoEstado);
        transiciones = new Hashtable();
        transiciones.put(simbolo, nuevosEstados);
        tablaTransiciones.put(estado, transiciones);
        if (estadoInicial == null || estadoInicial.equals("")) {
            apuntaEstadoInicial(estado);
        }
    } else {
        HashSet nuevosEstados = (HashSet) transiciones.get(simbolo);
        if (nuevosEstados == null) {
            nuevosEstados = new HashSet();
            nuevosEstados.add(nuevoEstado);
            transiciones.put(simbolo, nuevosEstados);
        } else {
            nuevosEstados.add(nuevoEstado);
        }
    }
}

public void apuntaTransicion(String estado, String cadena, String nuevoEstado) {
    if (cadena == null || cadena.equals(""))
        return;

    String estadoInicial = estado;
    String estadoIntermedio;
    Character simbolo;
    for (int i = 0; i < cadena.length() - 1; i++) {
        simbolo = new Character(cadena.charAt(i));
        estadoIntermedio = nuevoEstado();
        // logger.debug(">"+estadoInicial+","+simbolo+","+estadoIntermedio);
        apuntaTransicion(estadoInicial, simbolo, estadoIntermedio);
        estadoInicial = estadoIntermedio;
    }
    simbolo = new Character(cadena.charAt(cadena.length() - 1));
    // logger.debug(">"+estadoInicial+","+simbolo+","+nuevoEstado);
    apuntaTransicion(estadoInicial, simbolo, nuevoEstado);
}

public void apuntaTransicion(String estado, Character simbolo,
        HashSet nuevosEstados) {
    if (nuevosEstados == null)
        return;
    Iterator i = nuevosEstados.iterator();
    while (i.hasNext()) {
        String nuevoEstado = (String) i.next();
        apuntaTransicion(estado, simbolo, nuevoEstado);
    }
}

public void apuntaTransicion(Hashtable tabla) {
    if (tabla == null || tabla.isEmpty())
        return;

    if (tablaTransiciones == null) {
        tablaTransiciones = new Hashtable();
    }
    tablaTransiciones.putAll(tabla);
}

/**
 * devuelve el conjunto de estados a que transita el automata que esta
 * actualmente en un conjunto de estados, con un simbolo de entrada
 */
public HashSet transicion(String estado, Character simbolo) {
    // logger.debug("transicion("+estado+","+simbolo+")");
    HashSet nuevosEstados = new HashSet();
    if (tablaTransiciones != null) {
        Hashtable transiciones = (Hashtable) tablaTransiciones.get(estado);
        if (transiciones != null) {
            HashSet masEstados = (HashSet) transiciones.get(simbolo);
            if (masEstados != null) {
                nuevosEstados = masEstados;
            }
            masEstados = (HashSet) transiciones.get(COMODIN);
            if (masEstados != null) {
                nuevosEstados.addAll(masEstados);
            }

        }
    }
    // logger.debug("= "+nuevosEstados);
    return nuevosEstados;
}

public String toString() {
    if (tablaTransiciones == null || tablaTransiciones.isEmpty())
        return "";

    String st = "";
    Enumeration eEstado = tablaTransiciones.keys();
    while (eEstado.hasMoreElements()) {
        String estado = (String) eEstado.nextElement();
        Hashtable transiciones = (Hashtable) tablaTransiciones.get(estado);
        Enumeration eSimbolo = transiciones.keys();
        while (eSimbolo.hasMoreElements()) {
            Character simbolo = (Character) eSimbolo.nextElement();
            st += "delta(" + estado + "," + simbolo + ") = { ";
            HashSet nuevosEstados = (HashSet) transiciones.get(simbolo);
            Iterator i = nuevosEstados.iterator();
            while (i.hasNext()) {
                String nuevoEstado = (String) i.next();
                st += nuevoEstado + " ";
            }
            st += "}\n";
        }
    }
    st += "Inicial= " + estadoInicial + "\n";
    if (estadosFinales != null) {
        st += "Finales= " + estadosFinales.toString();
    } else {
        st += "Finales= []";
    }

    return st;
}

/**
 * devuelve el estado inicial del automata
 */
public String estadoInicial() {
    return estadoInicial;
}

/**
 * devuelve el conjunto de estados finales del automata
 */
public HashSet estadosFinales() {
    return estadosFinales;
}

/**
 * return true si el estado pertenece a la lista de estados finales
 */
public boolean esFinal(String estado) {
	return estadosFinales.contains(estado);
}

/**
 * devuelve el conjunto de estados del automata
 */
public HashSet estados() {
    HashSet estados = new HashSet();
    if (tablaTransiciones != null) {
        estados = new HashSet(tablaTransiciones.keySet());
    }
    return estados;
}

/**
 * devuelve un estado que no esta en el automata
 */
private static int _numero = 0;

public static String nuevoEstado() {
    String st = "_q" + _numero++;
    return st;
}

public static Character alfaNumericoAleatorio() {
	ArrayList posibles = new ArrayList();
	for(char ch=0; ch<127; ch++) {
		if (Character.isLetterOrDigit(ch)) {
			posibles.add(new Character(ch));
		}
	}
	int n = (new Random()).nextInt(posibles.size());
	return (Character) posibles.get(n);
}


}
