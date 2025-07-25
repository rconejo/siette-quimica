package siette.quimica;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import siette.util.Random;

public class Quiette {
	
	public final static int ES = Compuesto.ES;
	public final static int EN = Compuesto.EN;
	
	public final static String PROPERTIES_FILENAME = ".quiette.properties";
		
	public static boolean RANDOM = true;
	public static boolean ALTERNATIVAS = false;
	
	public static String NIVEL = "";
	public static HashSet<Integer> LISTA_COMPUESTOS;
	public static HashSet<Integer> LISTA_NOMENCLATURAS;
	static {
    	LISTA_COMPUESTOS = new HashSet<Integer>();
    	LISTA_NOMENCLATURAS = new HashSet<Integer>();
 	}
	
	public static Compuesto COMPUESTO;
	public static String FORMULA;
	public static String NOMBRE;

	
	
		public static int idioma() {
			return Compuesto.defaultLang;
		}
		
		public static void setIdioma(int idioma) {
			Compuesto.defaultLang = idioma;
		}
		
		public static Properties readProperties(String fichero, boolean onError) {
			Properties p = null;
			try {
				p = new Properties();
				p.load(new FileInputStream(fichero));
			} catch(IOException e) { 
				if (onError) e.printStackTrace(); 
			}
			return p;
		}
	
	   public static Properties init(String argv[]) {
	        Properties p = new Properties();
	        int i=0;
	        if (argv.length>0) {
		        while (i<argv.length && argv[i]!=null) {
		        	// Fichero de propiedades
		            if (argv[i].equals("-p") ) {
		                     String fileName = PROPERTIES_FILENAME;
		                	 if (i<argv.length && argv[i]!=null && !argv[i].startsWith("-")) {
		                		 fileName = argv[i];
			                     i++;
		                	 }
		                	 init(readProperties(fileName, true));
		            // Idioma     
		            } else if (argv[i].equals("-es") ) {
	                     	p.setProperty("quiette.idioma","es");
	               	 		i++;
		            } else if (argv[i].equals("-en") ) {
                     		p.setProperty("quiette.idioma","en");
	                   	 	i++;
	                   	 	
	                // opciones
		            } else if (argv[i].equals("-ayuda") || argv[i].equals("?") ) {
		            	 setIdioma(ES);
		                 i++;
		                 return null;
		            } else if (argv[i].equals("-help") || argv[i].equals("?") ) {
                     	 setIdioma(EN);
		                 i++;
		                 return null;
		            } else if (argv[i].equals("-random") || argv[i].equals("-r")) {
	                    p.setProperty("quiette.random","true");
		                 i++;
		            } else if (argv[i].equals("-random") || argv[i].equals("-a")) {
	                    p.setProperty("quiette.alternativas","true");
		                 i++;
	                   	 	
	                // Tipo de compuesto   	 	
		            } else if (argv[i].equals("-elemento") || argv[i].equals("-element")) {
	                     p.setProperty("quiette.elemento","true");
		                 i++;
		            } else if (argv[i].equals("-oxido") || argv[i].equals("-oxide")) {
	                     p.setProperty("quiette.oxido","true");
		                 i++;
		            } else if (argv[i].equals("-peroxido") || argv[i].equals("-peroxide")) {
	                     p.setProperty("quiette.peroxido","true");
		                 i++;
		            } else if (argv[i].equals("-hidruro") || argv[i].equals("-hydride")) {
	                     p.setProperty("quiette.hidruro","true");
		                 i++;
		            } else if (argv[i].equals("-haluro") || argv[i].equals("-halide")) {
	                     p.setProperty("quiette.haluro","true");
		                 i++;
		            } else if (argv[i].equals("-salbinaria") || argv[i].equals("-binarysalt")) {
	                     p.setProperty("quiette.salbinaria","true");
	                     i++;
		            } else if (argv[i].equals("-salacida") || argv[i].equals("-salacida")) {
	                     p.setProperty("quiette.salacida","true");
	                     i++;
		            } else if (argv[i].equals("-sal") || argv[i].equals("-salt")) {
		            	 if (p.getProperty("quiette.oxoacido") !=null) {
		            		p.setProperty("quiette.oxosal","true");
		            	 } else {
		            		 p.setProperty("quiette.salbinaria","true");
		            	 }
		                 i++;
		            } else if (argv[i].equals("-hidroxido") || argv[i].equals("-hydroxide")) {
	                     p.setProperty("quiette.hidroxido","true");
		                 i++;
		            } else if (argv[i].equals("-oxoacido") || argv[i].equals("-oxoacid") || argv[i].equals("-oxocid") || argv[i].equals("-oxyacid")) {
	                     p.setProperty("quiette.oxoacido","true");
		                 i++;
		            } else if (argv[i].equals("-tioacido") || argv[i].equals("-thioacid")) {
	                     p.setProperty("quiette.tioacido","true");
		                 i++;
		            } else if (argv[i].equals("-oxosal") || argv[i].equals("-oxosalt") || argv[i].equals("-oxoacidsalt")) {
	                     p.setProperty("quiette.oxosal","true");
		                 i++;
		            } else if (argv[i].equals("-ciano") || argv[i].equals("-cyanide")) {
	                     p.setProperty("quiette.ciano","true");
		                 i++;
		            } else if (argv[i].equals("-poliatomico") || argv[i].equals("-poliatomica") || argv[i].equals("-poliatomic")) {
		            	 if (p.getProperty("quiette.oxoacido") !=null) {
		                     p.setProperty("quiette.oxoacidopoliatomico","true");
		            	 }
		            	 if (p.getProperty("quiette.oxosal") !=null) {
		                     p.setProperty("quiette.oxosalpoliatomica","true");
		            	 }
		                 i++;
		                 
		            // Nomenclaturas     
		            } else if (argv[i].startsWith("-n.s")) {
	                     p.setProperty("quiette.sistematica","true");
		                 i++;
		            } else if (argv[i].startsWith("-n.st") || argv[i].equals("-n.o")) {
	                     p.setProperty("quiette.stock","true");
		                 i++;
		            } else if (argv[i].startsWith("-n.h") ) {
	                     p.setProperty("quiette.hidrogeno","true");
		                 i++;
		            } else if (argv[i].startsWith("-n.a")) {
	                     p.setProperty("quiette.adicion","true");
		                 i++;
		            } else if (argv[i].startsWith("-n.t")) {
	                     p.setProperty("quiette.tradicional","true");
		                 i++;
		            } else if (argv[i].startsWith("-n.c")) {
	                     p.setProperty("quiette.clasica","true");
		                 i++;

		            // Nivel
		            } else if (argv[i].startsWith("-3")) {
	                     p.setProperty("quiette.nivel","3eso");
		                 i++;
		            } else if (argv[i].startsWith("-4")) {
	                     p.setProperty("quiette.nivel","4eso");
		                 i++;

		                 
		            } else {
		            	FORMULA = argv[i];
		            	i++; 
		            	while (i<argv.length && argv[i]!=null && !argv[i].startsWith("-")) {
		            		FORMULA += " " + argv[i];
		            		i++;
		            	}
		            }
		        }
		        init(p); // iniciar a partir de las propiedades leidas
	        } else {
	        	return null;
	        }
	        return p;
	    }
	   
	   
	   public static void help() {
		   if (idioma() == EN) {
	           System.out.println("quiette [formula|name] [OPTIONS] [COMPOUND TYPES] [NOMENCLATURES] [LEVEL]");
	           System.out.println("OPTIONS");
	           System.out.println("   -help                  Show this help");
	           System.out.println("   -p <properties file>   Read options from properties file");
	           System.out.println("   -r                     Randomly generate a chemical compund");
	           System.out.println("   -es                    Spanish (default)");
	           System.out.println("   -en                    English");
	           System.out.println("   -a                     Show alternatives for a given wrong formula");
	           System.out.println("COMPOUND TYPES (choose any)");
	           System.out.println("   -element               Randomly generate a single element");
	           System.out.println("   -oxide                 Randomly generate an oxide");
	           System.out.println("   -peroxide              Randomly generate a peroxide");
	           System.out.println("   -hydride               Randomly generate an hydride");
	           System.out.println("   -halide                Randomly generate an halide");
	           System.out.println("   -binarysalt            Randomly generate a binary salt");
	           System.out.println("   -acidsalt              Randomly generate an acid salt");
	           System.out.println("   -hydroxide             Randomly generate an hydroxide");
	           System.out.println("   -oxoacid               Randomly generate an oxoacid");
	           System.out.println("   -oxoacidsalt           Randomly generate an oxoacidsalt");
	           System.out.println("   -thioacid              Randomly generate a thioacid");
	           System.out.println("   -cyanide               Randomly generate a cyanide");
	           System.out.println("   -salt                  Randomly generate an oxoacidsalt if -oxoacid is declared, otherwise generate a binary salt");
	           System.out.println("   -poliatomic            Randomly generate an poliatomic oxoacid and/or a politomic oxosalt if -oxoacid and/or -oxosalt has been previously declared");
	           System.out.println("NOMENCLATURES (choose any)");
	           System.out.println("   -n.s                   Show systematic nomenclature");
	           System.out.println("   -n.st | n.o            Show Stock nomenclature, with roman oxidation numbers");
	           System.out.println("   -n.h                   Show hdrogen nomenclature");
	           System.out.println("   -n.a                   Show addition nomenclature");
	           System.out.println("   -n.t                   Show traditional nomenclature (currently accepted by IUPAC)");
	           System.out.println("   -n.c                   Show classical nomenclature (currently not accepted by IUPAC)");
	           System.out.println("LEVEL (choose one)");
	           System.out.println("   -3                     Use only elements marked for level 3 (3ESO)");
	           System.out.println("   -4                     Use only elements marked for level 4 (4ESO)");
		   } else {
			   ayuda();
		   }
	   }

	   public static void ayuda() {
		   if (idioma() == ES) {
	           System.out.println("quiette [formula|nombre] [OPCIONES] [TIPO DE COMPUESTO] [NOMENCLATURAS] [NIVEL]");
	           System.out.println("OPTIONS");
	           System.out.println("   -ayuda | ?              Mostrar esta ayuda");
	           System.out.println("   -p <properties fichero> Leer opciones desde un fichero de propiedades");
	           System.out.println("   -r                      Generar un compuesto al azar");
	           System.out.println("   -es                     Español (por defecto)");
	           System.out.println("   -en                     Ingles");
	           System.out.println("   -a                      Mostrar alternativas a una formula incorrecta");
	           System.out.println("TIPO DE COMPUESTO (elegir cualquier mnumero)");
	           System.out.println("   -elemento               Generar un elementoi quimico");
	           System.out.println("   -oxido                  Generar un oxido");
	           System.out.println("   -peroxido               Generar un proxido");
	           System.out.println("   -hidruro                Generar un hidruro");
	           System.out.println("   -haluro                 Generar un haluro");
	           System.out.println("   -salbinaria             Generar una sal binaria");
	           System.out.println("   -salacida               Generar una sal binaria acida");
	           System.out.println("   -hidroxido              Generar un hidroxido");
	           System.out.println("   -oxoacido               Generaar un oxoacido");
	           System.out.println("   -oxosal                 Genberar una oxosal");
	           System.out.println("   -tioacido               Generar un tioacido");
	           System.out.println("   -cyanide                Generar un compuesto con ion cianuro");
	           System.out.println("   -sal                    Si se ha declarado previamente -oxoacido, gerera tambien una oxosal, si no genera una sal binaria");
	           System.out.println("   -poliatomic(o|a)        Generar un oxoacido politomico y/o una sal poliatomica si las opciones -oxoacido y/o -oxosal han sido declaradas previamente");
	           System.out.println("NOMENCLATURAS (elegir cualquier mnumero)");
	           System.out.println("   -n.s                   Mostrar nomenclatura sistematica");
	           System.out.println("   -n.st | n.o            Mostrar nomenclatura de Stock, con mumeros romanos");
	           System.out.println("   -n.h                   Mostrar nomenclatura de hidrogeno");
	           System.out.println("   -n.a                   Mostrar nomenclatura de adicion");
	           System.out.println("   -n.t                   Mostrar nomenclatura tradicional (en caso de que este actualmente aceptada por IUPAC)");
	           System.out.println("   -n.c                   Mostrar nomenclatura clasica (aunque no este aceptada ya por IUPAC)");
	           System.out.println("NIVEL (elegir uno)");
	           System.out.println("   -3                     Usar solo elementos incluidos en la lista de 3ro ESO (3ESO)");
	           System.out.println("   -4                     Usar solo elementos incluidos en la lista de 3to ESO (4ESO)");
		   } else {
			   help();
		   }
	   }

	    public static boolean init(Properties properties) {
	    	boolean status = false;
	    	if (properties!=null) {
		    	status = true;
		        String st; // aux;
		        st = properties.getProperty("quiette.idioma");
		        if (st==null) st = properties.getProperty("quiette.lang");
		        if (st!=null && st.equalsIgnoreCase("en")) {
		        	setIdioma(EN);
		        } else if ( st!=null && st.equalsIgnoreCase("es")) {
		        	setIdioma(ES);
		        }
		        st = properties.getProperty("quiette.aleatorio");
		        if (st==null) st = properties.getProperty("quiette.random");
		        if (st!=null && st.equals("true")) {
		        	RANDOM = true;
		        }
		        st = properties.getProperty("quiette.alternativas");
		        if (st==null) st = properties.getProperty("quiette.alternatives");
		        if (st!=null && st.equals("true")) {
		        	ALTERNATIVAS = true;
		        }
		        st = properties.getProperty("quiette.elemento");
		        if (st==null) st = properties.getProperty("quiette.element");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.ELEMENTO);
		        }
		        st = properties.getProperty("quiette.oxido");
		        if (st==null) st = properties.getProperty("quiette.oxide");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.OXIDO);
		        }
		        st = properties.getProperty("quiette.peroxido");
		        if (st==null) st = properties.getProperty("quiette.peroxide");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.PEROXIDO);
		        }
		        st = properties.getProperty("quiette.hidruro");
		        if (st==null) st = properties.getProperty("quiette.hydride");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.HIDRURO);
		        }
		        st = properties.getProperty("quiette.haluro");
		        if (st==null) st = properties.getProperty("quiette.halide");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.HALURO);
		        }
		        st = properties.getProperty("quiette.salbinaria");
		        if (st==null) st = properties.getProperty("quiette.binarysalt");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.SALBINARIA);
		        }
		        st = properties.getProperty("quiette.hidroxido");
		        if (st==null) st = properties.getProperty("quiette.hydroxide");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.HIDROXIDO);
		        }
		        st = properties.getProperty("quiette.oxoacido");
		        if (st==null) st = properties.getProperty("quiette.oxoacid");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.OXOACIDO);
		        }
		        st = properties.getProperty("quiette.oxoacidopoliatomico");
		        if (st==null) st = properties.getProperty("quiette.poliatoticoxoacid");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.OXOACIDOPOLIATOMICO);
		        }
		        st = properties.getProperty("quiette.oxosal");
		        if (st==null) st = properties.getProperty("quiette.oxosalt");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.OXOSAL);
		        }
		        st = properties.getProperty("quiette.oxosalpoliatomica");
		        if (st==null) st = properties.getProperty("quiette.poliatomicoxosalt");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.OXOSALPOLIATOMICA);
		        }
		        st = properties.getProperty("quiette.salacida");
		        if (st==null) st = properties.getProperty("quiette.acdisalt");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.SALACIDA);
		        }
		        st = properties.getProperty("quiette.tioacido");
		        if (st==null) st = properties.getProperty("quiette.thioacid");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.TIOACIDO);
		        }
		        st = properties.getProperty("quiette.ciano");
		        if (st==null) st = properties.getProperty("quiette.cyanide");
		        if (st!=null && st.equals("true")) {
		        	LISTA_COMPUESTOS.add(Compuesto.ANIONPOLIATOMICO);
		        }

		        st = properties.getProperty("quiette.sistematica");
		        if (st==null) st = properties.getProperty("quiette.systematic");
		        if (st!=null && st.equals("true")) {
		        	LISTA_NOMENCLATURAS.add(Compuesto.NOM_SISTEMATICA);
		        }
		        st = properties.getProperty("quiette.stock");
		        if (st!=null && st.equals("true")) {
		        	LISTA_NOMENCLATURAS.add(Compuesto.NOM_STOCK);
		        }
		        st = properties.getProperty("quiette.hidrogeno");
		        if (st==null) st = properties.getProperty("quiette.hydrogen");
		        if (st!=null && st.equals("true")) {
		        	LISTA_NOMENCLATURAS.add(Compuesto.NOM_DEHIDROGENO);
		        }
		        st = properties.getProperty("quiette.adicion");
		        if (st==null) st = properties.getProperty("quiette.addition");
		        if (st!=null && st.equals("true")) {
		        	LISTA_NOMENCLATURAS.add(Compuesto.NOM_DEADICION);
		        }
		        st = properties.getProperty("quiette.tradicional");
		        if (st==null) st = properties.getProperty("quiette.traditional");
		        if (st!=null && st.equals("true")) {
		        	LISTA_NOMENCLATURAS.add(Compuesto.NOM_TRADICIONAL);
		        }
		        st = properties.getProperty("quiette.clasica");
		        if (st==null) st = properties.getProperty("quiette.classic");
		        if (st!=null && st.equals("true")) {
		        	LISTA_NOMENCLATURAS.add(Compuesto.NOM_CLASICA);
		        }

		        st = properties.getProperty("quiette.nivel");
		        if (st==null) st = properties.getProperty("quiette.level");
		        if (st!=null && st.startsWith("3")) {
		        	NIVEL= Compuesto.ESO3;
		        }
		        st = properties.getProperty("quiette.nivel");
		        if (st==null) st = properties.getProperty("quiette.level");
		        if (st!=null && st.startsWith("4")) {
		        	NIVEL= Compuesto.ESO4;
		        }
	    	}
	        // System.out.println("properties="+properties);
	        return status;
	    }

	    public static void exec() {
	    	if (FORMULA!=null && !FORMULA.equals("")) {
	    		COMPUESTO = Compuesto.analizar(FORMULA, idioma(), false);
	    		if (COMPUESTO == null) {
		    		COMPUESTO = Compuesto.analizar(FORMULA, ALTERNATIVAS);
	    		}
	    	} else if (RANDOM) {
	    		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
	    		if (NIVEL!=null && !NIVEL.equals("")) {
	    			tablaPeriodica = tablaPeriodica.selectCurso(NIVEL);
	    		}
	    		if (LISTA_COMPUESTOS.isEmpty()) {
	    			COMPUESTO = Compuesto.random(tablaPeriodica);
	    		} else {
		    		COMPUESTO = Compuesto.random(tablaPeriodica, toArrayList(LISTA_COMPUESTOS));
	    		}
	    	}
    		if (COMPUESTO!=null) {
        		COMPUESTO.idIdioma(idioma());
    			// System.out.println(COMPUESTO.formula() + ": " +COMPUESTO.nombre());
	    		if (LISTA_NOMENCLATURAS.isEmpty()) {
	    			System.out.println(COMPUESTO);
	    		} else {
	    			System.out.println(COMPUESTO.formula()+": "+ COMPUESTO.nombreCompleto(toArrayList(LISTA_NOMENCLATURAS)));
	    		}
    		}

	    }
	    
	    private static List<Integer> toArrayList(HashSet<Integer> set) {
	    	ArrayList<Integer> list = new ArrayList<Integer>();
	    	for(Integer i : set) {
	    		list.add(i);
	    	}
			return list;
		}

		public static void main(String argv[]) {
	    	init(readProperties(System.getProperty("user.home")+File.separator+PROPERTIES_FILENAME,false));  // Propiedades generales
	    	init(readProperties("."+File.separator+PROPERTIES_FILENAME,false));  // Propiedades del directorio actual
	    	Properties p = init(argv); // Propiedades a traves de parametros
	    	if (p!=null) {
	    		init(p);
		    	exec();
	    	} else {
	    		help();
	    	}
	    }
}
