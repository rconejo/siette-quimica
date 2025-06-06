/**
 * *****************************************************************************
 * *****************************************************************************
 * clase ExpresionRegular
 * *****************************************************************************
 * *****************************************************************************
 */

package siette.util.regex;

/**
 * Patron: Correspondencia
 *
 * @author Ricardo Conejo
 * @version 1.04
 */

public class Correspondencia extends Patron {

public Correspondencia(String[] st, Boolean mMayusculas, Boolean mAcentos, Boolean mSignos, Boolean mBlancos, Boolean mVariaciones) {
    super(st, mMayusculas, mAcentos, mSignos, mBlancos, mVariaciones);
}

public Correspondencia(String[] patrones, Boolean mMayusculas, Boolean mAcentos, Boolean mSignos, Boolean mBlancos) {
	this(patrones, mMayusculas, mAcentos, mSignos, mBlancos, Boolean.FALSE);
}

public Correspondencia(String st, boolean mMayusculas, boolean mAcentos, boolean mSignos, boolean mBlancos,boolean mVariaciones) {
    super(st, mMayusculas, mAcentos, mSignos, mBlancos);
}

public Correspondencia(String st, boolean mMayusculas, boolean mAcentos, boolean mSignos, boolean mBlancos) {
    super(st, mMayusculas, mAcentos, mSignos, mBlancos);
}

public Correspondencia(String st) {
    super(st);
}


public static String normalizar(String st, boolean mMayusculas, boolean mAcentos, boolean mSignos, boolean mBlancos) {
	Correspondencia patron = new Correspondencia(st,  mMayusculas,  mAcentos,  mSignos,  mBlancos);
	return patron.normalizar(st);
}

//Devuelve el indice del k-esimo patron al que pertenece
public int pertenece(String st, int kesimo) {
	int respuesta = FALLO;
	int i = 0;
	int k = 0;
	while (respuesta == FALLO && i < patron.length && k<=kesimo) {
	    if (st != null && patron[i] != null && normalizar(patron[i]).equals(normalizar(st))) {
        	if (k==kesimo) {
                respuesta = i;
        	}
	        k++;
	    }
	    i++;
	}
	return respuesta;
}

public static void main(String argv[]) {

    Correspondencia p;

    // Prueba 0
    p = new Correspondencia("patata");
    System.out.println("P0 p=" + p + " patata >> " + p.pertenece("patata"));
    System.out.println("P0 p=" + p + " potato >> " + p.pertenece("potato"));

    // Prueba 1
    String[] patrones = { "Italia", "Francia", "Alemania", "Belgica" };
    p = new Correspondencia(patrones, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
    String respuestas[] = { "Italia", "Francia", "Italia", "Alemania" };
    System.out.println("P1=" + p);
    System.out.println("respuesta multiple =");
    int[] eval = p.pertenece(respuestas);
    for (int i = 0; i < eval.length; i++) {
        System.out.println(respuestas[i] + " >> " + eval[i]);
    }

}

}