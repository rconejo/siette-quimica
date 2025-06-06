package siette.quimica;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import siette.util.regex.ExpresionRegular;
import siette.util.text.Strings;

public class PatronQuimica extends ExpresionRegular {

	public final static String FORMULA = "@Formula";	// patron auxiliar que contiene la formula para recomponer el compuesto
	
	// Patrones de errores de formulacion
	public final static String PATRON_FORMULA = "@PatronFormula"; // patron correcto
	public final static String PATRON_ELEMENTO_DESCONOCIDO = "@ElementoDesconocido";
	public final static String PATRON_ELEMENTO_INCORRECTO = "@ElementoIncorrecto";
	public final static String PATRON_MAYUSCULAS_MINUSCULAS = "@MayusculasMinusculas";
	public final static String PATRON_NO_SIMPLIFICAR = "@NoSimplificar";
	public final static String PATRON_SIMPLIFICAR = "@Simplificar";
	public final static String PATRON_ORDENACION = "@Ordenacion";
	public final static String PATRON_FALTA_HIDROGENO = "@FaltaHidrogeno";
	public final static String PATRON_NUMEROS_DESORDENADOS = "@NumerosDesordenados"; // H2O => HO2
	public final static String PATRON_CONFUNDIR_NUMERO_OXIDACION = "@ConfundirNumeroOxidacion"; // Confunde numero de atomos con numero de exidacion
	public final static String PATRON_NUMERO_ATOMOS = "@NumeroAtomos"; // H2O => H3O
	public final static String PATRON_NUMERO_UNO = "@NumeroUno";
	public final static String PATRON_NO_PEROXIDO = "@NoPeroxido";
	public final static String PATRON_SIN_SUBINDICES = "@SinSubindices";
	public final static String PATRON_SAL_ELECTRONEGATIVO = "@ErrorSalElectronegativo";
	
	// Patrones de errores de nomenclatura
	public final static String PATRON_NOMENCLATURA = "@Nomenclatura"; // patron correcto
	public final static String PATRON_NOMENCLATURA_INCORRECTA = "@NomenclaturaIncorrecta";
	public final static String PATRON_NOMENCLATURA_CLASICA = "@NomenclaturaClasica";
	public final static String PATRON_NOMENCLATURA_TRADICIONAL_INCORRECTA = "@NomenclaturaTradicionalIncorrecta";
	public final static String PATRON_NOMENCLATURA_PREFIJOS = "@NomenclaturaPrefijos";
	public final static String PATRON_NOMENCLATURA_FALTA_URO ="@NomenclaturaFaltaUro";
	public final static String PATRON_NOMENCLATURA_FALTA_ANO ="@NomenclaturaFaltaAno";
	public final static String PATRON_NOMENCLATURA_STOCK_CON_NUMERO_OXIDACION ="@NomenclaturaStockConNumeroOxidacion";
	public final static String PATRON_NOMENCLATURA_STOCK_SIN_NUMERO_OXIDACION ="@NomenclaturaStockSinNumeroOxidacion";
	public final static String PATRON_NOMENCLATURA_FALTA_H ="@NomenclaturaFaltaH";
	public final static String PATRON_NOMENCLATURA_FALTA_OXIDO ="@NomenclaturaFaltaOxido";
	public final static String PATRON_NOMENCLATURA_SOBRA_OXIDO ="@NomenclaturaSobraOxido";
	public final static String PATRON_NOMENCLATURA_LATINA ="@NomenclaturaLatina";
	public final static String PATRON_NOMENCLATURA_SOBRA_HIDRURO ="@NomenclaturaSobraHidruro";
		
	// tipo de patrones de error
	// el patron[0] es siempre el patron correcto
	public final static String[] TIPOS = {
			
			   FORMULA // patron auxiliar
			   
			// Patrones correctos
			,  PATRON_FORMULA
			,  PATRON_NOMENCLATURA
			
			// Errores de formulaas
			, PATRON_ELEMENTO_DESCONOCIDO
			, PATRON_ELEMENTO_INCORRECTO
			, PATRON_MAYUSCULAS_MINUSCULAS  
			, PATRON_NO_SIMPLIFICAR
			, PATRON_SIMPLIFICAR
			, PATRON_ORDENACION
			, PATRON_FALTA_HIDROGENO
			, PATRON_CONFUNDIR_NUMERO_OXIDACION
			, PATRON_NUMEROS_DESORDENADOS
			, PATRON_NUMERO_ATOMOS
			, PATRON_NUMERO_UNO
			, PATRON_NO_PEROXIDO
			, PATRON_SIN_SUBINDICES
			, PATRON_SAL_ELECTRONEGATIVO
			
			// Errores de nomenclatura
			, PATRON_NOMENCLATURA_INCORRECTA
			, PATRON_NOMENCLATURA_CLASICA
			, PATRON_NOMENCLATURA_TRADICIONAL_INCORRECTA
			, PATRON_NOMENCLATURA_PREFIJOS
			, PATRON_NOMENCLATURA_FALTA_URO
			, PATRON_NOMENCLATURA_FALTA_ANO
			, PATRON_NOMENCLATURA_STOCK_CON_NUMERO_OXIDACION
			, PATRON_NOMENCLATURA_STOCK_SIN_NUMERO_OXIDACION
			, PATRON_NOMENCLATURA_FALTA_H
			, PATRON_NOMENCLATURA_FALTA_OXIDO
			, PATRON_NOMENCLATURA_SOBRA_OXIDO
			, PATRON_NOMENCLATURA_LATINA
			, PATRON_NOMENCLATURA_SOBRA_HIDRURO
	};

	protected String[] tipoPatron; 
	
	public final static int REFUERZO_ELEMENTO_DESCONOCIDO         = 10300; // ids de la base de datos
	public final static int REFUERZO_MAYUSCULAS_MINUSCULAS        = 10301; 
	public final static int REFUERZO_NO_SIMPLIFICAR               = 10302; 
	public final static int REFUERZO_SIMPLIFICAR                  = 10303; 
	public final static int REFUERZO_ELEMENTO_INCORRECTO          = 10304; 
	public final static int REFUERZO_ORDENACION                   = 10305; 
	public final static int REFUERZO_FALTA_HIDROGENO              = 10306; 
	public final static int REFUERZO_NUMERO_ATOMOS                = 10307; 
	public final static int REFUERZO_NUMERO_UNO                   = 10308; 
	public final static int REFUERZO_NO_PEROXIDO                  = 10309; 
	public final static int REFUERZO_NUMERO_OXIDACION             = 10310; 
	public final static int REFUERZO_NUMEROS_DESORDENADOS         = 10311; 
	public final static int REFUERZO_CONFUNDIR_NUMERO_OXIDACION   = 10312; 
	public final static int REFUERZO_SIN_SUBINDICES               = 10313; 
	public final static int REFUERZO_SAL_ELECTRONEGATIVO          = 10314; 
	
	public final static int REFUERZO_NOMENCLATURA_INCORRECTA                 = 10320; 
	public final static int REFUERZO_NOMENCLATURA_CLASICA                    = 10321; 
	public final static int REFUERZO_NOMENCLATURA_PREFIJOS                   = 10322; 
	public final static int REFUERZO_NOMENCLATURA_FALTA_URO                  = 10323; 
	public final static int REFUERZO_NOMENCLATURA_STOCK_CON_NUMERO_OXIDACION = 10324;
	public final static int REFUERZO_NOMENCLATURA_STOCK_SIN_NUMERO_OXIDACION = 10325;
	public final static int REFUERZO_NOMENCLATURA_FALTA_ANO                  = 10326; 
	public final static int REFUERZO_NOMENCLATURA_FALTA_H                    = 10327; 
	public final static int REFUERZO_NOMENCLATURA_FALTA_OXIDO                = 10328; 
	public final static int REFUERZO_NOMENCLATURA_SOBRA_OXIDO                = 10329; 
	public final static int REFUERZO_NOMENCLATURA_LATINA                     = 10330; 
	public final static int REFUERZO_NOMENCLATURA_SOBRA_HIDRURO              = 10331; 
	public static final int REFUERZO_NOMENCLATURA_TRADICIONAL_INCORRECTA     = 10332;
	
	public final static int REFUERZO_FORMULA_RESPUESTA                       = 10333; 
	public static final int REFUERZO_NOMENCLATURA_RESPUESTA                  = 10334;

	public static final int REFUERZO_CASI_CORRECTA                           = 10335;
	
	protected PatronQuimica(String st) {
		super(st);
		definirTipoPatrones();
	}
	
	public PatronQuimica(String[] st, Boolean mMayusculas, Boolean mAcentos, Boolean mSignos, Boolean mBlancos) {
	    super(st, mMayusculas,  mAcentos,  mSignos,  mBlancos);
	}

	public PatronQuimica(String st, boolean mMayusculas, boolean mAcentos, boolean mSignos, boolean mBlancos) {
	    super( st, mMayusculas,  mAcentos,  mSignos,  mBlancos);
	}

	
	// Este procedimiento guarda en la variable tipoPatron[] el tipo de
	// cada uno de los patrones, eliminando la etiqueta, por ejemplo
	// el patron "@NoSimplificar H4O2|H8O4" indica que debe reconocerse el 
	// error de no haber simplificado la formula del agua
	// Lo primero es el tipo de error, que se guarda aparte,
	// El resto son los parametros de este tipo de error
	// el patron[0] corresponde siempre al patron correcto
	protected void construirProcesador() {
	    procesador = new Procesador[patron.length];
    	tipoPatron = new String[patron.length];
    	// tipoPatron[0] = PATRON_CORRECTO;
        // procesador[0] = new Procesador(normalizar(patron[0]));
	    for (int iPatron = 0; iPatron < patron.length; iPatron++) {
			for(int i=TIPOS.length-1; i>=0; i--) {
				if (patron[iPatron].startsWith(TIPOS[i])) {
	    			tipoPatron[iPatron] = TIPOS[i];
	    			String patronER = patron[iPatron];
	    			patronER = patronER.replaceAll(TIPOS[i]+" ", "");
	    	        procesador[iPatron] = new Procesador(normalizar(patronER));
	    	        break; //encontrado, no busques mas.
				}
			} 
			if (procesador[iPatron] == null) {
				procesador[iPatron] = new Procesador(normalizar(patron[iPatron]));
			}
    		if (tipoPatron[iPatron] == null) {
    			tipoPatron[iPatron] = "";  // por si acaso alguno no se instancia, que no de problemas
    		}

	    }
	}

    protected void definirTipoPatrones() {
    	tipoPatron = new String[patron.length];
    	// tipoPatron[0] = PATRON_CORRECTO;
    	for(int iPatron=0; iPatron<patron.length; iPatron++) {
    		if (patron[iPatron].startsWith(PATRON_ELEMENTO_DESCONOCIDO)) {
    			tipoPatron[iPatron] = PATRON_ELEMENTO_DESCONOCIDO;
    		} else {
    			for(int i=TIPOS.length-1; i>=0; i--) {
    				if (patron[iPatron].startsWith(TIPOS[i])) {
    	    			tipoPatron[iPatron] = TIPOS[i];
    	    			patron[iPatron] = patron[iPatron].replaceAll(TIPOS[i]+" ", ""); // eliminar el tag, para dejar la expresion regular Siette
    				}
    			}
    		}
    		if (tipoPatron[iPatron] == null) {
    			tipoPatron[iPatron] = "";  // por si acaso alguno no se instancia, que no de problemas
    		}
    	}
    }
	    
	@Override
	protected boolean reconoce(int iPatron, String respuesta) {
		boolean reconoce = false;
		if (patron[iPatron].equals(PATRON_ELEMENTO_DESCONOCIDO)) {
			ArrayList<String> componentes = Compuesto.componentes(respuesta);
			if (componentes!=null) {
				for(int i=0; i<componentes.size(); i++) {
					String simbolo = componentes.get(i);
					if (!TablaPeriodica.TABLAPERIODICA.containsElemento(simbolo)) {
						// System.out.println("El componente: "+componentes[i] + " no corresponde a ningun elemento de la tabla periodica");
						reconoce = true;
					}
				}
			}
		} else if (patron[iPatron].equals(PATRON_ELEMENTO_INCORRECTO) ) {
				for(int i=0; i<tipoPatron.length; i++) {
					if (tipoPatron[i].startsWith(FORMULA)) {
		    			String solucion = patron[i].replaceAll(FORMULA+" ", "");
		    			String formula = solucion.replaceAll("\\\\", ""); // reconvertir el patron en una formula
						Set<String> sRespuesta = Compuesto.setComponentes(respuesta);
						Set<String> sSolucion = Compuesto.setComponentes(formula);
						for(String simbolo : sRespuesta) {
							if (TablaPeriodica.TABLAPERIODICA.containsElemento(simbolo) && !sSolucion.contains(simbolo)) {
								reconoce = true;
							}
						}
						break;
					}
				}
		} else if (patron[iPatron].equals(PATRON_MAYUSCULAS_MINUSCULAS)) {
			PatronQuimica patronMayusculasMinusculas = new PatronQuimica(patron[0], true, false, false, true);
			reconoce = (patronMayusculasMinusculas.pertenece(respuesta) == 0);  // se comprueba si con la opcion may/min activada reconoceria el patron
		} else {
			reconoce = super.reconoce(iPatron,respuesta); // usar el patron de expresiones regulares en la mayoria de los casos
		}
		return reconoce;
	}


	@SuppressWarnings("unused")
	private String getMessage(int idIdioma, int idMensaje, String info) {
		switch (idIdioma) {
			case EN:
				switch (idMensaje) {
				}
			default:
				switch (idMensaje) {
				}
		}
		return null;
	}
	
	
	private final static int ES = 1;
	private final static int EN = 2;


	
	// personalizar los refuerzos que se puedan personalizar
	/**
	 * @param plantilla
	 * La plantilla del refuerzo es especial, contiene instrucciones para rellenarla usando datos de la respuesta del alumno
	 * personalizandola con valores del compuesto y la respuesta. Las variables son identifiadores de la forma %XXXX
	 * Dado que a este metodo no se le pasa como parametro la instancia del compuesto que se ha elegido, hay que hacer un truco
	 * para que funcione, que consiste en usar el ULTIMO patron, como un patron especial que contiene la formula del compuesto
	 * que se ha pedido. Ya sea la formula tal cual, o la nomenclatura. El ultimo patron es la formula correcta.
	 * La plantilla tiene una estructura de texto en HTML con secciones <span></span> que son opcionales. En caso de que no se 
	 * consiga instanciar las variables que haya dentro, la seccion entera se quita. Por ejemplo, si no se encuentra un valor
	 * para la variable %ElementoAnion que esta dentro de una seccion <span></span>, esta seccion entera se elimina.
	 * Por otra parte, probablemente al final hay una sección <div></div> que contiene un refuerzo sobre el error cometido al
	 * formular. Por ejemplo si se pide una formula y la respuesta es incorrecta la plantilla incluira algo asi:
	 * <div>La formula %Respuesta corresponde al compuesto %NomenclaturaXXXX</div> en donde %Respuesta se sustituye por la
	 * respuesta del alumno y %NomenclaturaXXX por el nombre del compuesto usando la nomenclatura X. Evidentemente, si a 
	 * partir de la formula no se ha podido hallar ningun compuesto, no tiene sentido añadir este refuerzo.
	 */
	@Override
	public String refuerzo(String plantilla, String respuesta, int idioma) {
		if (idioma!=EN) {
			idioma = ES; // idioma por defecto
		}
		int iPatron = pertenece(respuesta);
		
		String tipoRespuesta = null;
		String solucion = null; // En el caso de formulas, el patron[ultimo] siempre contiene la formula correcta
		Elemento elementoRespuesta = null;
		Elemento elementoSolucion = null;
		String simboloIncorrecto = null;
		String formula = null;
		Integer[] subindicesRespuesta = null;  // subindices de la formula de la respuesta
		Integer[] subindicesSolucion = null;  // subindices de la formula de la solucion

		for(int i=0; i<tipoPatron.length; i++) {
			if (tipoPatron[i].startsWith(FORMULA)) {
    			solucion = patron[i].replaceAll(FORMULA+" ", "");
    			formula = solucion.replaceAll("\\\\", ""); // reconvertir el patron en una formula
    			subindicesRespuesta = Compuesto.subindices(respuesta);  // subindices de la formula de la respuesta
    			subindicesSolucion = Compuesto.subindices(formula);  // subindices de la formula de la solucion
			}
		}
		
		ArrayList<String> componentesRespuesta = Compuesto.componentes(respuesta);  // elementos quimicos de la respuesta
		// ArrayList<String> componentesSolucion = Compuesto.componentes(formula);  // elementos quimicos de la solucion
		Set<String> sRespuesta = Compuesto.setComponentes(respuesta);
		Set<String> sSolucion = Compuesto.setComponentes(formula);
		if (tipoPatron[0].startsWith(PATRON_FORMULA)) { // en otro caso se proporciona un patron de formulas alternativo, para mayor comodidad
			tipoRespuesta = PATRON_FORMULA;
			if (componentesRespuesta!=null) {
				for(int i=0; i<componentesRespuesta.size(); i++) {
					String simbolo = componentesRespuesta.get(i);
					if (!TablaPeriodica.TABLAPERIODICA.containsElemento(simbolo)) {
						simboloIncorrecto = simbolo;
						for(String simboloCorrecto : sSolucion) {
							if (!sRespuesta.contains(simboloCorrecto)) {
								elementoSolucion = TablaPeriodica.selectElemento(simboloCorrecto);
							}
						}
					}
				}
				// Caso de un simbolo que si pertenece a la tabla periodica
				if (simboloIncorrecto == null) {
					for(String simbolo : sRespuesta) {
						if (TablaPeriodica.TABLAPERIODICA.containsElemento(simbolo) && !sSolucion.contains(simbolo)) {
							simboloIncorrecto = simbolo;
						}
					}
					if (simboloIncorrecto!=null) {
						elementoRespuesta = TablaPeriodica.selectElemento(simboloIncorrecto);
						for(String simbolo : sSolucion) {
							if (!sRespuesta.contains(simbolo)) {
								elementoSolucion = TablaPeriodica.selectElemento(simbolo);
							}
						}
					}

					/*
					for(int i=0; i<Math.min(componentesRespuesta.length, componentesSolucion.length); i++) {
						String simbolo = componentesRespuesta[i];
						if (! componentesRespuesta[i].equals(componentesSolucion[i])  && TablaPeriodica.TABLAPERIODICA.containsElemento(simbolo)) {
							simboloIncorrecto = simbolo;
							elementoSolucion = TablaPeriodica.selectElemento(componentesSolucion[i]);
							elementoRespuesta = TablaPeriodica.selectElemento(componentesRespuesta[i]);
						}
					}
					*/
				}
			}
			
		} else if (tipoPatron[0].startsWith(PATRON_NOMENCLATURA)) { // en otro caso se proporciona un patron de formulas alternativo, para mayor comodidad
			tipoRespuesta = PATRON_NOMENCLATURA;
			patron[0].replaceAll(PATRON_NOMENCLATURA+" ", "");
			try {
				String respuestaNormalizada = respuesta.toLowerCase();
				respuestaNormalizada = Strings.eliminarAcentos(respuestaNormalizada);

				if (iPatron>=0 && iPatron<patron.length) {
					if (tipoPatron[iPatron].startsWith(PATRON_ELEMENTO_INCORRECTO)) {
		    			String patronER = patron[iPatron];
		    			patronER = patronER.replaceAll(PATRON_ELEMENTO_INCORRECTO+" ", ""); // *(<litasElementos>)*
		    			patronER = patronER.substring(1,patronER.length()-1); // pasar de ER a regex
				        Pattern pattern = Pattern.compile(patronER);
				        Matcher matcher = pattern.matcher(respuestaNormalizada);
						while (matcher.find()) { // toma el último
				        	String stElementoRespuesta = matcher.group();
				        	elementoRespuesta = TablaPeriodica.selectElementoNombre(stElementoRespuesta, idioma);
				        	if (elementoRespuesta == null) {
					        	elementoRespuesta = TablaPeriodica.selectElementoRaizNombre(stElementoRespuesta, idioma);
				        	}
				        }
				        
						for(String simbolo : sSolucion) {
							if (!sRespuesta.contains(simbolo)) {
								elementoSolucion = TablaPeriodica.selectElemento(simbolo);
							}
						}
					}
				}
		        
			} catch (Exception e) {
				respuesta = "";
			}   
		}
		
		String refuerzo = plantilla;
		Compuesto compuestoCorrecto = Compuesto.analizar(formula);
		if (compuestoCorrecto!=null) {
			refuerzo = refuerzo.replaceAll("%Formula", compuestoCorrecto.formulaHTML());
		} else {
			refuerzo = refuerzo.replaceAll("%Formula", formula);
		}

		if (iPatron>=0 && iPatron<patron.length) {
			if (tipoPatron[iPatron].startsWith(PATRON_ELEMENTO_DESCONOCIDO)) {
				if (simboloIncorrecto!=null ) {
					refuerzo = refuerzo.replaceAll("%SimboloIncorrecto", simboloIncorrecto);
					if (elementoSolucion!=null) {
						refuerzo = refuerzo.replaceAll("%ElementoCorrecto", elementoSolucion.nombre() );
						refuerzo = refuerzo.replaceAll("%SimboloCorrecto", elementoSolucion.simbolo() );
					}
					if (refuerzo.contains("%SimboloCorrecto")) { // no se ha posido instanciar
						refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");					
					}
				} else {
					refuerzo = "";
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_ELEMENTO_INCORRECTO)) {
				if (elementoRespuesta!=null) {
					refuerzo = refuerzo.replaceAll("%SimboloIncorrecto", elementoRespuesta.simbolo());
					refuerzo = refuerzo.replaceAll("%ElementoIncorrecto", elementoRespuesta.nombre() );
					if (elementoSolucion!=null) {
						refuerzo = refuerzo.replaceAll("%ElementoCorrecto", elementoSolucion.nombre() );
						refuerzo = refuerzo.replaceAll("%SimboloCorrecto", elementoSolucion.simbolo() );
					}
					if (refuerzo.contains("%SimboloCorrecto")) { // no se ha posido instanciar
						refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");					
					}
				} else {
					refuerzo = "";
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_NO_SIMPLIFICAR)) {
				if (subindicesRespuesta!=null && subindicesSolucion!=null) {
					if (subindicesRespuesta[0] != null && subindicesSolucion[0] != null) {
						int divisor = subindicesRespuesta[0] / subindicesSolucion[0];
						refuerzo = refuerzo.replaceAll("%Divisor", ""+divisor );
					}
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_SIMPLIFICAR)) {
				Compuesto c = Peroxido.analizar(formula);
				if (c==null) { //  Si no es un peroxido, quitar el mensaje de los peroxidos
					refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_ORDENACION) ) {
				Compuesto c = Compuesto.analizar(formula); // Hallar de nuevo el compuesto a partir de la formula
				if (c!=null && c instanceof CompuestoBinario) { //  Si no es binario, no se puede instanciar el refuerzo
					CompuestoBinario cb = (CompuestoBinario) c;
					Anion anion = cb.getAnion();
					Cation cation = cb.getCation();
					Compuesto compuestoCation = cation.getCompuesto();
					if (compuestoCation instanceof CompuestoUnario) {
						Elemento eCation = ((CompuestoUnario)compuestoCation).getElemento();
						refuerzo = refuerzo.replaceAll("%SimboloCation", eCation.simbolo());
						refuerzo = refuerzo.replaceAll("%ElementoCation", eCation.nombre());
						String nombreAnion = anion.nombre();
						refuerzo = refuerzo.replaceAll("%ElementoAnion", nombreAnion);
						refuerzo = refuerzo.replaceAll("%SimboloAnion", anion.getCompuesto().formulaHTML());
					} else {
						refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
						// refuerzo = refuerzo.replaceAll("\\<div.*\\</div\\>", "");
					}				
				} else {
					refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
					// refuerzo = refuerzo.replaceAll("\\<div.*\\</div\\>", "");
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_SAL_ELECTRONEGATIVO) ) {
				Compuesto c = Compuesto.analizar(formula); // Hallar de nuevo el compuesto a partir de la formula
				if (c!=null && c instanceof SalBinaria) { //  Si no es una sal binaria, no se puede instanciar el refuerzo
					SalBinaria sal = (SalBinaria) c;
					// Cation cation = sal.getCation();
					Anion anion = sal.getAnion();
					Compuesto compuestoAnion = anion.getCompuesto();
					if (compuestoAnion instanceof CompuestoUnario) {
						Elemento eAnion = ((CompuestoUnario)compuestoAnion).getElemento();
						String nombreAnion = anion.nombre();
						int numeroOxidacionAnion = anion.numeroOxidacion;
						refuerzo = refuerzo.replaceAll("%ElementoAnion", nombreAnion);
						refuerzo = refuerzo.replaceAll("%SimboloAnion", eAnion.formulaHTML());
						refuerzo = refuerzo.replaceAll("%GrupoAnion", ""+eAnion.grupo());
						refuerzo = refuerzo.replaceAll("%NumeroOxidacionAnion", ""+numeroOxidacionAnion);
					} else {
						refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
					}				
				} else {
					refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_NOMENCLATURA_STOCK_CON_NUMERO_OXIDACION) ) {
				Compuesto c = Compuesto.analizar(formula); // Hallar de nuevo el compuesto a partir de la formula
				if (c!=null && c instanceof CompuestoBinario) { //  Si no es un compuesto binario, no se puede instanciar el refuerzo
					CompuestoBinario cb = (CompuestoBinario) c;
					Cation cation = cb.getCation();
					int numeroOxidacion = cation.numeroOxidacion;
					refuerzo = refuerzo.replaceAll("%SimboloCation", cation.simbolo());
					refuerzo = refuerzo.replaceAll("%ElementoCation", cation.nombre());
					refuerzo = refuerzo.replaceAll("%NumeroOxidacionCation", "+"+numeroOxidacion);
				} else{
					refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_NOMENCLATURA_STOCK_SIN_NUMERO_OXIDACION) ) {
				Compuesto c = Compuesto.analizar(formula); // Hallar de nuevo el compuesto a partir de la formula
				if (c!=null && c instanceof CompuestoBinario) { //  Si no es un compuesto binario, no se puede instanciar el refuerzo
					CompuestoBinario cb = (CompuestoBinario) c;
					Cation cation = cb.getCation();
					int numeroOxidacion = cation.numeroOxidacion;
					String stNumerosOxidacion = "+"+numeroOxidacion;
					refuerzo = refuerzo.replaceAll("%SimboloCation", cation.simbolo());
					refuerzo = refuerzo.replaceAll("%ElementoCation", cation.nombre());
					if (cation.compuesto instanceof CompuestoUnario) {
						Elemento e = ((CompuestoUnario) cation.compuesto).getElemento();
						Set<Integer> numerosOxidacion = e.numerosOxidacionPositivos();
						stNumerosOxidacion = "";
						for(Integer n : numerosOxidacion) {
							stNumerosOxidacion += (stNumerosOxidacion.equals("")?"":", ")+ "+" + n;
						}
					} 
					refuerzo = refuerzo.replaceAll("%NumeroOxidacionCation", stNumerosOxidacion);
				} else{
					refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_NUMERO_ATOMOS) ||
					   tipoPatron[iPatron].startsWith(PATRON_NUMEROS_DESORDENADOS) ||
					   tipoPatron[iPatron].startsWith(PATRON_SIN_SUBINDICES) ||
					   tipoPatron[iPatron].startsWith(PATRON_CONFUNDIR_NUMERO_OXIDACION) ) {
				Compuesto c = Compuesto.analizar(formula); // Hallar de nuevo el compuesto a partir de la formula
				if (c!=null && c instanceof CompuestoBinario) { //  Si no es un compuesto binario, no se puede instanciar el refuerzo
					CompuestoBinario cb = (CompuestoBinario) c;
					Cation cation = cb.getCation();
					Anion anion = cb.getAnion();
					Compuesto compuestoCation = cation.getCompuesto();
					if (compuestoCation instanceof CompuestoUnario) {
						Elemento eCation = ((CompuestoUnario)compuestoCation).getElemento();
						int numeroOxidacion = cation.numeroOxidacion;
						refuerzo = refuerzo.replaceAll("%SimboloCation", eCation.simbolo());
						refuerzo = refuerzo.replaceAll("%ElementoCation", eCation.nombre());
						refuerzo = refuerzo.replaceAll("%NumeroOxidacionCation", "+"+numeroOxidacion);
						String nombreAnion = anion.nombre();
						int numeroOxidacionAnion = anion.numeroOxidacion;
						refuerzo = refuerzo.replaceAll("%ElementoAnion", nombreAnion);
						refuerzo = refuerzo.replaceAll("%SimboloAnion", anion.getCompuesto().formulaHTML());
						refuerzo = refuerzo.replaceAll("%NumeroOxidacionAnion", ""+numeroOxidacionAnion);
					} else {
							refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
					}				
				} else {
					refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
				}
			} else if (tipoPatron[iPatron].startsWith(PATRON_NOMENCLATURA_PREFIJOS) ) {
				Compuesto c = Compuesto.analizar(formula); // Hallar de nuevo el compuesto a partir de la formula
				if (c!=null) {
					String patron = Strings.eliminarAcentos(c.patronSistematica()).toLowerCase();
					Pattern pattern = Pattern.compile("(?:^| |\\(|\\{)"+Ion.patronPrefijos());
					Matcher matcher = pattern.matcher(patron);
					String prefijo1 = "";
					if (matcher.find()) {
						int nGrupos = matcher.groupCount();
					    prefijo1 = matcher.group(nGrupos);
						refuerzo = refuerzo.replaceAll("%NumeroAtomos", ""+Ion.prefijo(prefijo1));
						refuerzo = refuerzo.replaceAll("%Prefijo", prefijo1);
					} else {
						refuerzo = "";
					}
					if (matcher.find()) {
						int nGrupos = matcher.groupCount();
					    String prefijo2 = matcher.group(nGrupos);
					    if (!prefijo2.equals(prefijo1)) {
							refuerzo = refuerzo.replaceAll("%2NumeroAtomos", ""+Ion.prefijo(prefijo2));
							refuerzo = refuerzo.replaceAll("%2Prefijo", prefijo2);
						} else {
							refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
					    }
					} else {
						refuerzo = refuerzo.replaceAll("\\<span.*\\</span\\>", "");
					}
				} else {
					refuerzo = "";
				}				
			}
			
			// Segunda parte Refuerzo de la respuesta
			if (refuerzo == null || "".equals(refuerzo)) {
				// recuperar la sección <div>/div> si el refuerzo se elimino completamente
				// lo que ocurre en algunos casos.
				int ini = plantilla.indexOf("<div>");
				int fin = plantilla.indexOf("</div>");
				if (ini>=0 && fin>=0 && fin>ini) {
					refuerzo = plantilla.substring(ini,fin+6);
				}
			}
			
			if (PATRON_FORMULA.equals(tipoRespuesta)) {
				// La respuesta es una formula
				Compuesto compuesto = Compuesto.analizar(respuesta);
				if (compuesto!=null) {
					refuerzo = refuerzo.replaceAll("%Respuesta", compuesto.formulaHTML());
					refuerzo = refuerzo.replaceAll("%NomenclaturaSistematica", compuesto.nomenclaturaSistematica());
					refuerzo = refuerzo.replaceAll("%NomenclaturaStock", compuesto.nomenclaturaStock());
					refuerzo = refuerzo.replaceAll("%NomenclaturaTradicional", compuesto.nomenclaturaTradicional());
					refuerzo = refuerzo.replaceAll("%NomenclaturaDeHidrogeno", compuesto.nomenclaturaDeHidrogeno());
					refuerzo = refuerzo.replaceAll("%NomenclaturaDeAdicion", compuesto.nomenclaturaDeAdicion());
				} else {
					refuerzo = refuerzo.replaceAll("\\<div.*\\</div\\>", "");
				}
			} else if (PATRON_NOMENCLATURA.equals(tipoRespuesta)) {
				// La respuesta es un nombre
				refuerzo = refuerzo.replaceAll("%Respuesta", respuesta);
				Compuesto compuestoSolucion = Compuesto.analizar(formula);
				if (compuestoSolucion!=null) {
					Compuesto compuestoRespuesta = compuestoSolucion.compuestoSimilar(respuesta);
					if (compuestoRespuesta!=null) {
						refuerzo = refuerzo.replaceAll("%Formula", compuestoRespuesta.formulaHTML());
					} else {
						refuerzo = refuerzo.replaceAll("\\<div.*\\</div\\>", "");
					}
				} else {
					// aqui no deberia llegar...
					refuerzo = refuerzo.replaceAll("\\<div.*\\</div\\>", "");
				}
			}
		}
		return refuerzo;
	}

}
