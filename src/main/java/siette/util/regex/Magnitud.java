package siette.util.regex;

public class Magnitud {

	public String unidad;
	public String unidadEquivalente;
	public double factor;

	public final static String GRADO     = ""+'\u00B0';
	public final static String PUNTO_POR = ""+'\u00B7';
	public final static String OMEGA     = ""+'\u03A9';
	public final static String MU        = ""+'\u03BC';
	public final static String POR_MIL   = ""+'\u2030';


	private Magnitud(String unidad, String unidadEquivalente, double factor) {
		this.unidad  = unidad;
		this.unidadEquivalente = unidadEquivalente;
		this.factor = factor;
	}

	public Magnitud(String unidad) {
		this(unidad,unidad,1.0);
	}

	public final static Magnitud VACIO = new Magnitud("","",1.0);

	public final static Magnitud[] tablaMagnitudes = {

		/////////////////////////// Magnitud basicas /////////////////////////

		// Longitud
		new Magnitud(   "m",                 "m",             1.0          ),
		new Magnitud(   "metro",             "m",             1.0          ),
		new Magnitud(   "metros",            "m",             1.0          ),
		new Magnitud(   "km",                "m",          1000.0          ),
		new Magnitud(   "kilometro",         "m",          1000.0          ),
		new Magnitud(   "kilometros",        "m",          1000.0          ),
		new Magnitud(   "hm",                "m",           100.0          ),
		new Magnitud(   "hectometro",        "m",           100.0          ),
		new Magnitud(   "hectometros",       "m",           100.0          ),
		new Magnitud(   "dam",               "m",            10.0          ),
		new Magnitud(   "decametro",         "m",            10.0          ),
		new Magnitud(   "decametros",        "m",            10.0          ),
		new Magnitud(   "dm",                "m",             0.1          ),
		new Magnitud(   "decimetro",         "m",             0.1          ),
		new Magnitud(   "decimetros",        "m",             0.1          ),
		new Magnitud(   "cm",                "m",             0.01         ),
		new Magnitud(   "centimetro",        "m",             0.01         ),
		new Magnitud(   "centimetros",       "m",             0.01         ),
		new Magnitud(   "mm",                "m",             0.001        ),
		new Magnitud(   "milimetro",         "m",             0.001        ),
		new Magnitud(   "milimetros",        "m",             0.001        ),
		new Magnitud(   "mum",               "m",             0.000001     ),
		new Magnitud(   MU +"m",             "m",             0.000001     ),
		new Magnitud(   "micrometro",        "m",             0.000001     ),
		new Magnitud(   "micrometros",       "m",             0.000001     ),
	    new Magnitud(   "nm",                "m",             0.000000001  ), // posible confusion con "Nm"
		new Magnitud(   "nanometro",         "m",             0.000000001  ),
		new Magnitud(   "nanometros",        "m",             0.000000001  ),
		new Magnitud(   "milla nautica",     "m",          1852.0          ),
		new Magnitud(   "millas nauticas",   "m",          1852.0          ),
		new Magnitud(   "milla",             "m",          1609.0          ),
		new Magnitud(   "millas",            "m",          1609.0          ),
		new Magnitud(   "mi",                "m",          1609.0          ),
		new Magnitud(   "feet",              "m",             0.3048       ),
		new Magnitud(   "foot",              "m",             0.3048       ),
		new Magnitud(   "pies",              "m",             0.3048       ),
		new Magnitud(   "pie",               "m",             0.3048       ),
		new Magnitud(   "ft",                "m",             0.3048       ),
		new Magnitud(   "inch",              "m",             0.0254       ),
		new Magnitud(   "inches",            "m",             0.0254       ),
		new Magnitud(   "pulgada",           "m",             0.0254       ),
		new Magnitud(   "pulgadas",          "m",             0.0254       ),
		new Magnitud(   "in",                "m",             0.0254       ),
		new Magnitud(   "pc",                "m",             3.0857E16              ),  // parsec
		new Magnitud(   "ua",                "m",             1.49597870E11          ),  // unidad astronomica
		new Magnitud(   "UA",                "m",             1.49597870E11          ),  // unidad astronomica
		new Magnitud(   "AU",                "m",             1.49597870E11          ),  // unidad astronomica
		new Magnitud(   "año luz",           "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "año-luz",           "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "años luz",          "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "años-luz",          "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "a.l.",              "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "al",                "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "light year",        "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "light-year",        "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "light years",       "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "light-years",       "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "ly",                "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "l.y.",              "m",             9.4607304725808E15     ),  // light year
		new Magnitud(   "light-seconds",     "m",           299.79E6                 ),  // light-second
		new Magnitud(   "light seconds",     "m",           299.79E6                 ),  // light-second
		new Magnitud(   "light-second",      "m",           299.79E6                 ),  // light-second
		new Magnitud(   "light second",      "m",           299.79E6                 ),  // light-second
		new Magnitud(   "segundos luz",      "m",           299.79E6                 ),  // light-second
		new Magnitud(   "segundos-luz",      "m",           299.79E6                 ),  // light-second
		new Magnitud(   "sugundo luz",       "m",           299.79E6                 ),  // light-second
		new Magnitud(   "segundo-luz",       "m",           299.79E6                 ),  // light-second
		new Magnitud(   "ls",                "m",           299.79E6                 ),  // light-second
		new Magnitud(   "sl",                "m",           299.79E6                 ),  // light-second
		 // new Magnitud(   "A",                 "m",            10.0E-10      ), // A se usa para Amperios

		// Masa
		new Magnitud(   "kg",               "kg",            1.0            ),
		new Magnitud(   "kilo",             "kg",            1.0            ),
		new Magnitud(   "kilos",            "kg",            1.0            ),
		new Magnitud(   "kilogramo",        "kg",            1.0            ),
		new Magnitud(   "kilogramos",       "kg",            1.0            ),
		new Magnitud(   "g",                "kg",            0.001          ),
		new Magnitud(   "gramo",            "kg",            0.001          ),
		new Magnitud(   "gramos",           "kg",            0.001          ),
		new Magnitud(   "mg",               "kg",            0.000001       ),
		new Magnitud(   "miligramo",        "kg",            0.000001       ),
		new Magnitud(   "miligramos",       "kg",            0.000001       ),
		new Magnitud(   "mug",              "kg",            0.000000001    ),
		new Magnitud(   MU +"g",            "kg",            0.000000001    ),
		new Magnitud(   "microgramo",       "kg",            0.000000001    ),
		new Magnitud(   "microgramos",      "kg",            0.000000001    ),
		new Magnitud(   "ng",               "kg",            0.000000000001 ),
		new Magnitud(   "nanogramo",        "kg",            0.000000000001 ),
		new Magnitud(   "nanogramos",       "kg",            0.000000000001 ),
		new Magnitud(   "t",                "kg",         1000.0            ),
		new Magnitud(   "Tm",               "kg",         1000.0            ),
		new Magnitud(   "Ton",              "kg",         1000.0            ),
		new Magnitud(   "Tonelada",         "kg",         1000.0            ),
		new Magnitud(   "Toneladas",        "kg",         1000.0            ),
		new Magnitud(   "lb",               "kg",            0.4536         ),
		new Magnitud(   "libra",            "kg",            0.4536         ),
		new Magnitud(   "libras",           "kg",            0.4536         ),
		new Magnitud(   "u",                "kg",            1.6605402E-27  ),
		new Magnitud(   "uma",              "kg",            1.6605402E-27  ),
		new Magnitud(   "u.m.a.",           "kg",            1.6605402E-27  ),

		// Tiempo
		new Magnitud(   "s",                "s",             1.0          ),
		new Magnitud(   "''",               "s",             1.0          ),
		new Magnitud(   "\"",               "s",             1.0          ),
		new Magnitud(   "sg",               "s",             1.0          ),
		new Magnitud(   "seg",              "s",             1.0          ),
		new Magnitud(   "segundo",          "s",             1.0          ),
		new Magnitud(   "segundos",         "s",             1.0          ),
		new Magnitud(   "ms",               "s",             0.001        ),
		new Magnitud(   "msg",              "s",             0.001        ),
		new Magnitud(   "mseg",             "s",             0.001        ),
		new Magnitud(   "milisegundo",      "s",             0.001        ),
		new Magnitud(   "milisegundos",     "s",             0.001        ),
		new Magnitud(   "mus",              "s",             0.000001     ),
		new Magnitud(   MU +"s",            "s",             0.000001     ),
		new Magnitud(   "musg",             "s",             0.000001     ),
		new Magnitud(   MU +"sg",           "s",             0.000001     ),
		new Magnitud(   "museg",            "s",             0.000001     ),
		new Magnitud(   MU +"seg",          "s",             0.000001     ),
		new Magnitud(   "microsegundo",     "s",             0.000001     ),
		new Magnitud(   "microsegundos",    "s",             0.000001     ),
		new Magnitud(   "ns",               "s",             0.000000001  ),
		new Magnitud(   "nsg",              "s",             0.000000001  ),
		new Magnitud(   "nseg",             "s",             0.000000001  ),
		new Magnitud(   "nanosegundo",      "s",             0.000000001  ),
		new Magnitud(   "nanosegundos",     "s",             0.000000001  ),
		new Magnitud(   "mn",               "s",            60.0          ),
		new Magnitud(   "min",              "s",            60.0          ),
		new Magnitud(   "'",                "s",            60.0          ),
		new Magnitud(   "minuto",           "s",            60.0          ),
		new Magnitud(   "minutos",          "s",            60.0          ),
		new Magnitud(   "h",                "s",          3600.0          ),
		new Magnitud(   "hora",             "s",          3600.0          ),
		new Magnitud(   "horas",            "s",          3600.0          ),
		new Magnitud(   "d",                "s",         86400.0          ),
		new Magnitud(   "dia",              "s",         86400.0          ),
		new Magnitud(   "dias",             "s",         86400.0          ),

		// Temperatura -- Ojo no hay proporcionalidad ino cmbio de escala...implementar apaarte cuando haga falta
		new Magnitud(   "K",                 "K",            1.0            ),
		new Magnitud(   GRADO + "K",         "K",            1.0            ),
		new Magnitud(   GRADO + "C",         "K",            1.0            ),
		new Magnitud(   "grados Kelvin",     "K",            1.0            ),
		new Magnitud(   "grados centigrados","K",            1.0            ),

		// Intensidad de la corriente
		new Magnitud(   "A",                "A",             1.0            ),
		new Magnitud(   "amperio",          "A",             1.0            ),
		new Magnitud(   "amperios",         "A",             1.0            ),
		new Magnitud(   "KA",               "A",          1000.0            ),
		new Magnitud(   "mA",               "A",             0.001          ),
		new Magnitud(   "muA",              "A",             0.000001       ),
		new Magnitud(   MU + "A",           "A",             0.000001       ),
		new Magnitud(   "nA",               "A",             0.000000001    ),

		// Cantidad de substancia
		new Magnitud(   "mol",              "mol",           1.0            ),
		new Magnitud(   "moles",            "mol",           1.0            ),

		// Intenidad luminosa
		new Magnitud(   "cd",               "cd",           1.0             ),
		new Magnitud(   "candela",          "cd",           1.0             ),
		new Magnitud(   "candelas",         "cd",           1.0             ),

		// Angulos
		new Magnitud(   "rad",              "rad",          1.0             ),
		new Magnitud(   "radian",           "rad",          1.0             ),
		new Magnitud(   "radianes",         "rad",          1.0             ),
		new Magnitud(   GRADO,              "rad",      Math.PI/180.0       ),
		new Magnitud(   "grado",            "rad",      Math.PI/180.0       ),
		new Magnitud(   "grados",           "rad",      Math.PI/180.0       ),
		new Magnitud(   "'",                "rad",      Math.PI/10080.0     ),
		new Magnitud(   "''",               "rad",      Math.PI/64800.0     ),
		new Magnitud(   "\"",               "rad",      Math.PI/64800.0     ),
		new Magnitud(   "vuelta",           "rad",          2.0*Math.PI     ),
		new Magnitud(   "vueltas",          "rad",          2.0*Math.PI     ),


		/////////////////////////// Magnitud derivadas /////////////////////////

		// Inversa de Longitud
		new Magnitud(   "1/m",             "1/m",             1.0          ),
		new Magnitud(   "m-1",             "1/m",             1.0          ),
		new Magnitud(   "1/cm",            "1/m",           100.0          ),
		new Magnitud(   "cm-1",            "1/m",           100.0          ),
		new Magnitud(   "1/mm",            "1/m",          1000.0          ),
		new Magnitud(   "mm-1",            "1/m",          1000.0          ),
		new Magnitud(   "1/km",            "1/m",             0.001        ),
		new Magnitud(   "km-1",            "1/m",             0.001        ),

		//Superficie
		new Magnitud(   "m2",               "m2",             1.0          ),
		new Magnitud(   "metros cuadrados", "m2",             1.0          ),
		new Magnitud(   "km2",              "m2",       1000000.0          ),
		new Magnitud(   "dm2",              "m2",             0.01         ),
		new Magnitud(   "cm2",              "m2",             0.0001       ),
		new Magnitud(   "mm2",              "m2",             0.000001     ),

		// Volumen
		new Magnitud(   "m3",               "m3",             1.0          ),
		new Magnitud(   "Hm3",              "m3",         10000.0          ),
		new Magnitud(   "dm3",              "m3",             0.001        ),
		new Magnitud(   "litro",            "m3",             0.001        ),
		new Magnitud(   "litros",           "m3",             0.001        ),
		new Magnitud(   "cm3",              "m3",             0.000001     ),
		new Magnitud(   "cc",               "m3",             0.000001     ),
		new Magnitud(   "ga",               "m3",             0.0038       ),
		new Magnitud(   "galon",            "m3",             0.0038       ),
		new Magnitud(   "galones",          "m3",             0.0038       ),

	        // Densidad
		new Magnitud(   "kg/m3",            "kg/m3",          1.0          ),
		new Magnitud(   "kgm-3",            "kg/m3",          1.0          ),
		new Magnitud(   "g/cm3",            "kg/m3",          1.0          ),

		// Velocidad
		new Magnitud(   "m/s",              "m/s",            1.0          ),
		new Magnitud(   "m.s-1",            "m/s",            1.0          ),
		new Magnitud(   "m/sg",             "m/s",            1.0          ),
		new Magnitud(   "m/seg",            "m/s",            1.0          ),
		new Magnitud(   "cm/s",             "m/s",            0.01         ),
		new Magnitud(   "cm.s-1",           "m/s",            0.01         ),
		new Magnitud(   "cm/sg",            "m/s",            0.01         ),
		new Magnitud(   "cm/seg",           "m/s",            0.01         ),
		new Magnitud(   "mm/s",             "m/s",            0.001        ),
		new Magnitud(   "mm.s-1",           "m/s",            0.001        ),
		new Magnitud(   "mm/sg",            "m/s",            0.001        ),
		new Magnitud(   "mm/seg",           "m/s",            0.001        ),
		new Magnitud(   "km/s",             "m/s",         1000.0          ),
		new Magnitud(   "km.s-1",           "m/s",         1000.0          ),
		new Magnitud(   "km/sg",            "m/s",         1000.0          ),
		new Magnitud(   "km/seg",           "m/s",         1000.0          ),
		new Magnitud(   "m/min",            "m/s",            1.0/60.0     ),
		new Magnitud(   "m/mn",             "m/s",            1.0/60.0     ),
		new Magnitud(   "km/min",           "m/s",         1000.0/60.0     ),
		new Magnitud(   "km/mn",            "m/s",         1000.0/60.0     ),
		new Magnitud(   "m/h",              "m/s",            1.0/3600.0   ),
		new Magnitud(   "m/hora",           "m/s",            1.0/3600.0   ),
		new Magnitud(   "cm/h",             "m/s",           0.01/3600.0   ),
		new Magnitud(   "mm/hora",          "m/s",           0.01/3600.0   ),
		new Magnitud(   "mm/h",             "m/s",          0.001/3600.0   ),
		new Magnitud(   "cm/hora",          "m/s",          0.001/3600.0   ),
		new Magnitud(   "km/h",             "m/s",         1000.0/3600.0   ),
		new Magnitud(   "km/hora",          "m/s",         1000.0/3600.0   ),

		// Aceleracion
		new Magnitud(   "m/s2",             "m/s2",            1.0             ),
		new Magnitud(   "m.s-2",            "m/s2",            1.0             ),
		new Magnitud(   "m/sg2",            "m/s2",            1.0             ),
		new Magnitud(   "m/seg2",           "m/s2",            1.0             ),
		new Magnitud(   "km/s2",            "m/s2",         1000.0             ),
		new Magnitud(   "km.s-2",           "m/s2",         1000.0             ),
		new Magnitud(   "km/sg2",           "m/s2",         1000.0             ),
		new Magnitud(   "km/seg2",          "m/s2",         1000.0             ),
		new Magnitud(   "km/h2",            "m/s2",         1000.0/(3600*3600) ),
		new Magnitud(   "km.h-2",           "m/s2",         1000.0/(3600*3600) ),
		new Magnitud(   "km/hora2",         "m/s2",         1000.0/(3600*3600) ),
		new Magnitud(   "km.hora-2",        "m/s2",         1000.0/(3600*3600) ),
		new Magnitud(   "cm/s2",            "m/s2",            0.01            ),
		new Magnitud(   "cm.s-2",           "m/s2",            0.01            ),
		new Magnitud(   "cm/sg2",           "m/s2",            0.01            ),
		new Magnitud(   "cm/seg2",          "m/s2",            0.01            ),
		new Magnitud(   "mm/s2",            "m/s2",            0.001           ),
		new Magnitud(   "mm.s-2",           "m/s2",            0.001           ),
		new Magnitud(   "mm/sg2",           "m/s2",            0.001           ),
		new Magnitud(   "mm/seg2",          "m/s2",            0.001           ),


		// Fuerza
		new Magnitud(   "N",                "N",               1.0          ),
		new Magnitud(   "Nw",               "N",               1.0          ),
		new Magnitud(   "Newton",           "N",               1.0          ),
		new Magnitud(   "Newtons",          "N",               1.0          ),
		new Magnitud(   "kg.m/s2",          "N",               1.0          ),
		new Magnitud(   "kg.m/sg2",         "N",               1.0          ),
		new Magnitud(   "kg.m/seg2",        "N",               1.0          ),
		new Magnitud(   "m.kg/s2",          "N",               1.0          ),
		new Magnitud(   "m.kg/sg2",         "N",               1.0          ),
		new Magnitud(   "m.kg/seg2",        "N",               1.0          ),
		new Magnitud(   "m.kg.s-2",         "N",               1.0          ),
		new Magnitud(   "kp",               "N",               9.806        ),
		new Magnitud(   "kgf",              "N",               9.806        ),
		new Magnitud(   "dyn",              "N",               0.00001      ),

		// Constante gravitatoria
		new Magnitud(   "N.m2/kg2",          "N.m2/kg2",       1.0          ),
		new Magnitud(   "N.m2.kg-2",         "N.m2/kg2",       1.0          ),
		new Magnitud(   "N.m2/kg2",          "N.m2/kg2",       1.0          ),
		new Magnitud(   "N.m2.kg-2",         "N.m2/kg2",       1.0          ),
		new Magnitud(   "m3/kg.s2",          "N.m2/kg2",       1.0          ),
		new Magnitud(   "m3/s2.kg",          "N.m2/kg2",       1.0          ),
		new Magnitud(   "(m3/kg)/s2",        "N.m2/kg2",       1.0          ),
		new Magnitud(   "m3.kg-1.s-2",       "N.m2/kg2",       1.0          ),
		new Magnitud(   "m3.s-2.kg-1",       "N.m2/kg2",       1.0          ),
		new Magnitud(   "kg-1.m3.s-2",       "N.m2/kg2",       1.0          ),

		// Presion
		new Magnitud(   "Pa",               "Pa",            1.0          ),
		new Magnitud(   "Pascales",         "Pa",            1.0          ),
		new Magnitud(   "N/m2",             "Pa",            1.0          ),
		new Magnitud(   "N.m-2",            "Pa",            1.0          ),
		new Magnitud(   "kg/m.s2",          "Pa",            1.0          ),
		new Magnitud(   "m-1.kg.s-2",       "Pa",            1.0          ),
		new Magnitud(   "N/m2",             "Pa",            1.0          ),
		new Magnitud(   "atm",              "Pa",       101300.0          ),
		new Magnitud(   "bar",              "Pa",       100000.0          ),
		new Magnitud(   "mbar",             "Pa",         1000.0          ),
		new Magnitud(   "milibares",        "Pa",         1000.0          ),
		new Magnitud(   "mmHg",             "Pa",       100000.0/760.0    ),

		// Energia - Trabajo
		new Magnitud(   "J",                "J",             1.0            ),
		new Magnitud(   "Joule",            "J",             1.0            ),
		new Magnitud(   "Joule",            "J",             1.0            ),
		new Magnitud(   "Julio",            "J",             1.0            ),
		new Magnitud(   "Julios",           "J",             1.0            ),
		new Magnitud(   "kg.m/s2",          "J",             1.0            ),
		new Magnitud(   "m.kg/s2",          "J",             1.0            ),
		new Magnitud(   "kg.m/sg2",         "J",             1.0            ),
		new Magnitud(   "m.kg/sg2",         "J",             1.0            ),
		new Magnitud(   "kg.m/seg2",        "J",             1.0            ),
		new Magnitud(   "m.kg/seg2",        "J",             1.0            ),
		new Magnitud(   "m.kg.s-2",         "J",             1.0            ),
		new Magnitud(   "kg.m.s-2",         "J",             1.0            ),
		new Magnitud(   "N.m",              "J",             1.0            ),
		new Magnitud(   "kN.m",             "J",          1000.0            ),
		new Magnitud(   "kJ",               "J",          1000.0            ),
		new Magnitud(   "eV",               "J",             1.60217733E-19 ),
		new Magnitud(   "electronvoltio",   "J",             1.60217733E-19 ),
		new Magnitud(   "electronvoltios",  "J",             1.60217733E-19 ),
		new Magnitud(	"kW/h",             "J",             3.6E6	    ),
		new Magnitud(	"W/s",              "J",             1.0	    ),
		new Magnitud(	"cal",              "J",             4.184	    ),
		new Magnitud(	"kcal",             "J",          4184.0	    ),

		// Potencia
		new Magnitud(   "W",                "W",             1.0          ),
		new Magnitud(   "Wat",              "W",             1.0          ),
		new Magnitud(   "Watt",             "W",             1.0          ),
		new Magnitud(   "Watio",            "W",             1.0          ),
		new Magnitud(   "Watios",           "W",             1.0          ),
		new Magnitud(   "J/s",              "W",             1.0          ),
		new Magnitud(   "J/sg",             "W",             1.0          ),
		new Magnitud(   "J/seg",            "W",             1.0          ),
		new Magnitud(   "J.s-1",            "W",             1.0          ),
		new Magnitud(   "m2.kg/s3",         "W",             1.0          ),
		new Magnitud(   "m2.kg/s3",         "W",             1.0          ),
		new Magnitud(   "m2.kg/s3",         "W",             1.0          ),
		new Magnitud(   "kg.m2/s3",         "W",             1.0          ),
		new Magnitud(   "kg.m2/s3",         "W",             1.0          ),
		new Magnitud(   "kg.m2/s3",         "W",             1.0          ),
		new Magnitud(   "m2.kg.s-3",        "W",             1.0          ),
		new Magnitud(   "kg.m2.s-3",        "W",             1.0          ),
		new Magnitud(   "VA",               "W",             1.0          ),
		new Magnitud(   "AV",               "W",             1.0          ),
		new Magnitud(   "KW",               "W",          1000.0          ),
	//	new Magnitud(   "MW",               "W",       1000000.0          ), // posible confusion con mV, de dificil solucion
		new Magnitud(   "GW",               "W",    1000000000.0          ),
		new Magnitud(   "mW",               "W",             0.001        ),
		new Magnitud(   "muW",              "W",             0.000001     ),
		new Magnitud(   MU +"W",            "W",             0.000001     ),
		new Magnitud(   "nW",               "W",             0.000000001  ),
		new Magnitud(   "CV",               "W",           735.0          ),
		new Magnitud(   "caballo",          "W",           735.0          ),
		new Magnitud(   "caballos",         "W",           735.0          ),
		new Magnitud(   "caballos de vapor","W",           735.0          ),
		new Magnitud(   "HP",               "W",           746.0          ),
		new Magnitud(	"horse power",	    "W",           746.0          ),
		new Magnitud(	"caballo de fuerza","W",           746.0          ),
		new Magnitud(	"caballos de fuerza","W",          746.0          ),

		// Cantidad de movimiento
		new Magnitud(   "kg.m/s",	    "kg.m/s",	    1.0		),
		new Magnitud(	"g.m/s",	    "kg.m/s",	    0.001	),
		new Magnitud(	"kg.km/h",	    "kg.m/s",	 1000.0/3600.0	),
		new Magnitud(	"g.km/h",	    "kg.m/s",	    1.0/3600.0	),

		// Impulso
		new Magnitud(	"N.s",		   "N.s",	    1.0		),
		new Magnitud(	"kN.s",		   "N.s",	 1000.0		),

		// Potencia por m2 = Intensidad de onda
		new Magnitud(   "W/m2",            "W/m2",          1.0          ),
		new Magnitud(   "W/m2",            "W/m2",          1.0          ),
		new Magnitud(   "W/m-2",           "W/m2",          1.0          ),
		new Magnitud(   "Wat/m2",          "W/m2",          1.0          ),
		new Magnitud(   "Watt/m2",         "W/m2",          1.0          ),
		new Magnitud(   "W/cm2",           "W/m2",      10000.0          ),
		new Magnitud(   "W/mm2",           "W/m2",    1000000.0          ),
		new Magnitud(   "KW/m2",           "W/m2",       1000.0          ),
		new Magnitud(   "KW/cm2",          "W/m2",   10000000.0          ),
		new Magnitud(   "KW/mm2",          "W/m2", 1000000000.0          ),


		// Potencial electrico
		new Magnitud(   "V",                "V",             1.0          ),
		new Magnitud(   "voltio",           "V",             1.0          ),
		new Magnitud(   "voltios",          "V",             1.0          ),
		new Magnitud(   "W/A",              "V",             1.0          ),
		new Magnitud(   "W.A-1",            "V",             1.0          ),
		new Magnitud(   "m2.kg/s3.A",       "V",             1.0          ),
		new Magnitud(   "kg.m2/s3.A",       "V",             1.0          ),
		new Magnitud(   "kg.m2/A.s3",       "V",             1.0          ),
		new Magnitud(   "m2.kg.s-3.A-1",    "V",             1.0          ),
		new Magnitud(   "kg.m2.s-3.A-1",    "V",             1.0          ),
	//  new Magnitud(   "MV",               "V",          1000.0          ), // posible confusion con mV, de dificil solucion
		new Magnitud(   "KV",               "V",          1000.0          ),
		new Magnitud(   "mV",               "V",             0.001        ),
		new Magnitud(   "muV",              "V",             0.000001     ),
		new Magnitud(   MU +"V",            "V",             0.000001     ),
		new Magnitud(   "nV",               "V",             0.000000001  ),

		// Carga electrica
		new Magnitud(   "C",                "C",             1.0          ),
		new Magnitud(   "Culombio",         "C",             1.0          ),
		new Magnitud(   "Culombios",        "C",             1.0          ),
		new Magnitud(   "Coulomb",          "C",             1.0          ),
		new Magnitud(   "Coulombs",         "C",             1.0          ),
		new Magnitud(   "A.s",              "C",             1.0          ),
		new Magnitud(   "A.sg",             "C",             1.0          ),
		new Magnitud(   "A.seg",            "C",             1.0          ),
		new Magnitud(   "s.A",              "C",             1.0          ),
		new Magnitud(   "sg.A",             "C",             1.0          ),
		new Magnitud(   "seg.A",            "C",             1.0          ),
		new Magnitud(   "mC",               "C",             0.001        ),
		new Magnitud(   "muC",              "C",             0.000001     ),
		new Magnitud(   MU +"C",            "C",             0.000001     ),
		new Magnitud(   "nC",               "C",             0.000000001  ),

		// Resistencia electrica
		new Magnitud(   "Ohm",               "Ohm",             1.0          ),
		new Magnitud(   "Ohms",              "Ohm",             1.0          ),
		new Magnitud(   "Ohmios",            "Ohm",             1.0          ),
		new Magnitud(   "Omega",             "Ohm",             1.0          ),
		new Magnitud(   "Omegas",            "Ohm",             1.0          ),
		new Magnitud(   "V/A",               "Ohm",             1.0          ),
		new Magnitud(   "V.A-1",             "Ohm",             1.0          ),
		new Magnitud(   "m2.kg/s3.A2",       "Ohm",             1.0          ),
		new Magnitud(   "kg.m2/s3.A2",       "Ohm",             1.0          ),
		new Magnitud(   "m2.kg/A2.s3",       "Ohm",             1.0          ),
		new Magnitud(   "kg.m2/A2.s3",       "Ohm",             1.0          ),
		new Magnitud(   "m2.kg.s-3.A-2",     "Ohm",             1.0          ),
		new Magnitud(   "kg.m2.s-3.A-2",     "Ohm",             1.0          ),
	//	new Magnitud(   "mOhm",              "Ohm",             0.001        ),
		new Magnitud(   "muOhm",             "Ohm",             0.000001     ),
		new Magnitud(   MU+"Ohm",            "Ohm",             0.000001     ),
		new Magnitud(   "nOhm",              "Ohm",             0.000000001  ),
		new Magnitud(   "KOhm",              "Ohm",          1000.0          ),
		new Magnitud(   "MOhm",              "Ohm",       1000000.0          ),
		new Magnitud(   "GOhm",              "Ohm",    1000000000.0          ),
		new Magnitud(   OMEGA,               "Ohm",             1.0          ),
	//	new Magnitud(   "m"+ OMEGA,          "Ohm",             0.001        ),
		new Magnitud(   MU + OMEGA,          "Ohm",             0.000001     ),
		new Magnitud(   "n"+ OMEGA,          "Ohm",             0.000000001  ),
		new Magnitud(   "K"+ OMEGA,          "Ohm",          1000.0          ),
		new Magnitud(   "M"+ OMEGA,          "Ohm",       1000000.0          ),
		new Magnitud(   "G"+ OMEGA,          "Ohm",    1000000000.0          ),

		// Capacidad electrica
		new Magnitud(   "F",               "F",             1.0              ),
		new Magnitud(   "C/V",             "F",             1.0              ),
		new Magnitud(   "C.V-1",           "F",             1.0              ),
		new Magnitud(   "m-2.kg-1.s4.A2",  "F",             1.0              ),
		new Magnitud(   "s4.A2/m2.kg",     "F",             1.0              ),
		new Magnitud(   "s4.A2/kg.m2",     "F",             1.0              ),
		new Magnitud(   "mF",              "F",             0.001            ),
		new Magnitud(   "muF",             "F",             0.000001         ),
		new Magnitud(   MU+"F",            "F",             0.000001         ),
		new Magnitud(   "nF",              "F",             0.000000001      ),
		new Magnitud(   "pF",              "F",             0.000000000001   ),

		// Flujo magnetico
		new Magnitud(   "Wb",               "Wb",             1.0            ),
		new Magnitud(   "Weber",            "Wb",             1.0            ),
		new Magnitud(   "Webers",           "Wb",             1.0            ),
		new Magnitud(   "V.s",              "Wb",             1.0            ),
		new Magnitud(   "V.sg",             "Wb",             1.0            ),
		new Magnitud(   "V.seg",            "Wb",             1.0            ),
		new Magnitud(   "m2.kg.s-2.A-1",    "Wb",             1.0            ),
		new Magnitud(   "kg.m2.s-2.A-1",    "Wb",             1.0            ),
		new Magnitud(   "m2.kg/s2.A",       "Wb",             1.0            ),
		new Magnitud(   "kg.m2/s2.A",       "Wb",             1.0            ),
		new Magnitud(   "m2.kg/A.s2",       "Wb",             1.0            ),
		new Magnitud(   "kg.m2/A.s2",       "Wb",             1.0            ),
		new Magnitud(   "kg.m2/A.sg2",      "Wb",             1.0            ),
		new Magnitud(   "kg.m2/A.seg2",     "Wb",             1.0            ),

		// Induccion magnetico
		new Magnitud(   "T",                "T",             1.0            ),
		new Magnitud(   "Tesla",            "T",             1.0            ),
		new Magnitud(   "Teslas",           "T",             1.0            ),
		new Magnitud(   "Wb/m2",            "T",             1.0            ),
		new Magnitud(   "Wb.m-2",           "T",             1.0            ),
		new Magnitud(   "kg.s-2.A-1",       "T",             1.0            ),
		new Magnitud(   "kg/s2.A",          "T",             1.0            ),
		new Magnitud(   "kg/A.s2",          "T",             1.0            ),
		new Magnitud(   "kg/A.sg2",         "T",             1.0            ),
		new Magnitud(   "kg/A.seg2",        "T",             1.0            ),

		// Inductancia
		new Magnitud(   "H",                "H",             1.0            ),
		new Magnitud(   "Henry",            "H",             1.0            ),
		new Magnitud(   "Henrys",           "H",             1.0            ),
		new Magnitud(   "Wb/A",             "H",             1.0            ),
		new Magnitud(   "Wb.A-1",           "H",             1.0            ),
		new Magnitud(   "m2.kg.s-2.A-2",    "H",             1.0            ),
		new Magnitud(   "kg.m2.s-2.A-2",    "H",             1.0            ),
		new Magnitud(   "m2.kg/s2.A2",      "H",             1.0            ),
		new Magnitud(   "kg.m2/s2.A2",      "H",             1.0            ),
		new Magnitud(   "m2.kg/A2.s2",      "H",             1.0            ),
		new Magnitud(   "kg.m2/A2.s2",      "H",             1.0            ),

		// Frecuencia, Velocidad angular
		new Magnitud(   "Hz",              "Hz",             1.0          ),
		new Magnitud(   "Herzios",         "Hz",             1.0          ),
		new Magnitud(   "rad/s",           "Hz",             1.0          ),
		new Magnitud(   "rad/sg",          "Hz",             1.0          ),
		new Magnitud(   "rad/seg",         "Hz",             1.0          ),
		new Magnitud(   "rad.s-1",         "Hz",             1.0          ),
		new Magnitud(   "ciclos/s",        "Hz",             1.0          ),
		new Magnitud(   "ciclos/sg",       "Hz",             1.0          ),
		new Magnitud(   "ciclos/seg",      "Hz",             1.0          ),
		new Magnitud(   "ciclos/segundo",  "Hz",             1.0          ),
		new Magnitud(   "vueltas/s",       "Hz",             1.0          ),
		new Magnitud(   "vueltas/sg",      "Hz",             1.0          ),
		new Magnitud(   "vueltas/seg",     "Hz",             1.0          ),
		new Magnitud(   "vueltas/segundo", "Hz",             1.0          ),
		new Magnitud(   "1/s",             "Hz",             1.0          ),
		new Magnitud(   "1/sg",            "Hz",             1.0          ),
		new Magnitud(   "1/seg",           "Hz",             1.0          ),
		new Magnitud(   "s-1",             "Hz",             1.0          ),
		new Magnitud(   "sg^-1",           "Hz",             1.0          ),
		new Magnitud(   "seg^-1",          "Hz",             1.0          ),
		new Magnitud(   "KHz",             "Hz",          1000.0          ),
		new Magnitud(   "MHz",             "Hz",       1000000.0          ),
		new Magnitud(   "GHz",             "Hz",    1000000000.0          ),

		// rad/m
		new Magnitud(   "rad/m",           "rad/m",          1.0          ),
		new Magnitud(   "rad.m-1",         "rad/m",          1.0          ),
		new Magnitud(   "rad/cm",          "rad/m",          1.0/0.01     ),
		new Magnitud(   "rad/cm-1",        "rad/m",          1.0/0.01     ),
		new Magnitud(   "rad/mm",          "rad/m",          1.0/0.001    ),
		new Magnitud(   "rad/mm-1",        "rad/m",          1.0/0.001    ),

		// Aceleracion angular
		new Magnitud(   "rad/s2",          "rad/s2",         1.0          ),
		new Magnitud(   "rad/sg2",         "rad/s2",         1.0          ),
		new Magnitud(   "rad/seg2",        "rad/s2",         1.0          ),
		new Magnitud(   "rad.s-2",         "rad/s2",         1.0          ),
		new Magnitud(   "ciclos/s2",       "rad/s2",         1.0          ),
		new Magnitud(   "ciclos/sg2",      "rad/s2",         1.0          ),
		new Magnitud(   "ciclos/seg2",     "rad/s2",         1.0          ),
		new Magnitud(   "vueltas/s2",      "rad/s2",         1.0          ),
		new Magnitud(   "vueltas/sg2",     "rad/s2",         1.0          ),
		new Magnitud(   "vueltas/seg2",    "rad/s2",         1.0          ),

		// Viscosidad
		new Magnitud(   "Pa.s",          "Pa.s",           1.0            ),
		new Magnitud(   "m-1.kg.s-1",    "Pa.s",           1.0            ),
		new Magnitud(   "kg.m-1.s-1",    "Pa.s",           1.0            ),
		new Magnitud(   "kg/m.s",        "Pa.s",           1.0            ),
		new Magnitud(   "kg/s.m",        "Pa.s",           1.0            ),

        	// Entropia
		new Magnitud(   "J/K",            "J/K",           1.0            ),
		new Magnitud(   "m2.kg.s-2.K-1",  "J/K",           1.0            ),
		new Magnitud(   "kg.m2.s-2.K-1",  "J/K",           1.0            ),
		new Magnitud(   "m2.kg/s2.K",     "J/K",           1.0            ),
		new Magnitud(   "m2.kg/K.s2",     "J/K",           1.0            ),
		new Magnitud(   "kg.m2/s2.K",     "J/K",           1.0            ),
		new Magnitud(   "kg.m2/K.s2",     "J/K",           1.0            ),

		// Capacidad termica
		new Magnitud(   "J/Kg.K",         "J/(kg.K)",        1.0            ),
		new Magnitud(   "m2.s-2.K-1",     "J/(kg.K)",        1.0            ),
		new Magnitud(   "m2/s2.K",        "J/(kg.K)",        1.0            ),
		new Magnitud(   "m2/K.s2",        "J/(kg.K)",        1.0            ),

		// Conductividad termica
		new Magnitud(   "W/(m.K)",         "W/(m.K)",        1.0            ),
		new Magnitud(   "m.kg.s-3.K-1",    "W/(m.K)",        1.0            ),
		new Magnitud(   "kg.m.s-3.K-1",    "W/(m.K)",        1.0            ),
		new Magnitud(   "m.kg/s3.K",       "W/(m.K)",        1.0            ),
		new Magnitud(   "kg.m/s3.K",       "W/(m.K)",        1.0            ),
		new Magnitud(   "m.kg/K.s3",       "W/(m.K)",        1.0            ),
		new Magnitud(   "kg.m/K.s3",       "W/(m.K)",        1.0            ),

		// Intensidad del campo electrico
		new Magnitud(   "V/m",                 "V/m",        1.0            ),
		new Magnitud(   "N/C",                 "V/m",        1.0            ),
		new Magnitud(   "m.kg.s-3.A-1",        "V/m",        1.0            ),
		new Magnitud(   "kg.m.s-3.A-1",        "V/m",        1.0            ),
		new Magnitud(   "m.kg/s3.A",           "V/m",        1.0            ),
		new Magnitud(   "kg.m/s3.A",           "V/m",        1.0            ),
		new Magnitud(   "m.kg/A.s3",           "V/m",        1.0            ),
		new Magnitud(   "kg.m/A.s3",           "V/m",        1.0            ),
		new Magnitud(   "kV/m",                "V/m",     1000.0            ),

		// Porcentaje
		new Magnitud(   "%",                   "%",          1.0            ),
		new Magnitud(   "o/o",                 "%",          1.0            ),
		new Magnitud(   "por cien",            "%",          1.0            ),
		new Magnitud(   "por ciento",          "%",          1.0            ),
		new Magnitud(   POR_MIL,               "%",          0.1            ),
		new Magnitud(   "por mil",             "%",          0.1            ),

		// Bytes - bits
		new Magnitud(   "byte",                "bytes",      1.0            ),
		new Magnitud(   "bytes",               "bytes",      1.0            ),
		new Magnitud(   "B",                   "bytes",      1.0            ),
		new Magnitud(   "KB",                  "bytes",      1.0E3          ),
		new Magnitud(   "MB",                  "bytes",      1.0E6          ),
		new Magnitud(   "GB",                  "bytes",      1.0E9          ),
		new Magnitud(   "TB",                  "bytes",      1.0E12         ),
		new Magnitud(   "KiB",                  "bytes",     1024           ),
		new Magnitud(   "MiB",                  "bytes",     1024*1024      ),
		new Magnitud(   "GiB",                  "bytes",     1024*1024*1024       ),
		new Magnitud(   "TiB",                  "bytes",     1024*1024*1024*1024  ),
		new Magnitud(   "bit",                  "bytes",     1/8            ),
		new Magnitud(   "bits",                 "bytes",     1/8            ),

		// Otras magnitudes (para equivalencia)

		// Sin unidades
		VACIO
	};

    /* codigo  para no tener que calcular la lista dinamicamente */
	public static String[] lista;
	static {
		lista = new String[tablaMagnitudes.length];
		for(int i=0; i<tablaMagnitudes.length; i++) {
			lista[i] = tablaMagnitudes[i].unidad;
		}
	}

	public final static String[] lista() {
		return lista;
	}


	/* Para aliviar el problema de mayusculas/minusculas y
	 * de algunos casos en que pudiera haber polisemia de las
	 * unidades se crea este metodo que devuelve true si existe
	 * la posibilidad de que ambas magnitudes sean equivalentes.
	 * Por ejemplo la magnitud "N.m" (Newton.metro) se representa
	 * de la misma forma que "n.m" (nanometro). Al llamar a este
	 * metodo se puede desambiguar facilmente segun la pregunta
	 * por ejemplo esEquivalente(Magnitud("m")) devolveria true
	 * interpretando que la magnitud es "nanometro", pero si la
	 * llamada se hace con esEquivalente(Magnitud("kg.m.s-2"))
	 * tambien devolveria true, interpretando que la magnitud
	 * es equivalente a "N.m" (Julios)
	 */
	public boolean esEquivalente(Magnitud mag) {
		boolean esEquivalente = false;
		if (this.unidadEquivalente.equals(mag.unidadEquivalente)) {
			esEquivalente = true;
		} else {
			for(int i=0; i<tablaMagnitudes.length && !esEquivalente ; i++) {
				if (tablaMagnitudes[i].unidadEquivalente.equals(this.unidadEquivalente)) {
					if (mag.unidad.equalsIgnoreCase(this.unidad)) {
						esEquivalente = true;
					}
				}
			}
		}
		return esEquivalente;
	}

	/* Para aliviar el problema de mayusculas/minusculas y
	 * de algunos casos en que pudiera haber polisemia de las
	 * unidades se crea este metodo que crea una nueva magnitud
	 * interpretando como equivalente la magnitud que recibe
	 * En el caso general devuelve el propio argumento, que
	 * es tambien lo que hace si no encuentra equivalencia.
	 * Ver descripcion del metodo anterior
	 */
	public Magnitud magnitudEquivalente(Magnitud mag) {
		Magnitud magEquivalente = mag;
		if (this.unidadEquivalente.equals(mag.unidadEquivalente)) {
			magEquivalente = mag;
		} else {
			for(int i=0; i<tablaMagnitudes.length && magEquivalente == mag ; i++) {
				if (tablaMagnitudes[i].unidadEquivalente.equals(this.unidadEquivalente)) {
					if (mag.unidad.equalsIgnoreCase(this.unidad)) {
						magEquivalente = tablaMagnitudes[i];
					}
				}
			}
		}
		return magEquivalente;
	}

	public String toString() {
	    return (unidad!=null && !unidad.equals("") ? unidad+"<=>"+unidadEquivalente : "");
	}



} // class Magnitud

