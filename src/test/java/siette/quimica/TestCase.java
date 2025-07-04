package siette.quimica;


public abstract class TestCase extends junit.framework.TestCase {
	
	protected static boolean DEBUG = false;
	
	public TestCase( String testName) {
		super(testName);
	}
	
	protected void test(Compuesto c) {
		if (DEBUG) System.out.println ("---");
		if (c!=null) {
			for (int idioma = Compuesto.ES; idioma<=Compuesto.EN; idioma++) {
				c.idIdioma(idioma);
				for(int j=Compuesto.PRIMERANOMENCLATURA; j<=Compuesto.ULTIMANOMENCLATURA; j++) {
					String nombre = c.nomenclatura(j);
					if (nombre!=null && !nombre.equals("")) {
						Compuesto cN = Compuesto.analizar(nombre, idioma);
						if (cN!=null) cN.idIdioma(idioma);
						if (DEBUG || cN==null || !cN.equals(c)) {
							System.out.println ("Compuesto: "+ c);
							System.out.println (nombre+": "+cN);
						}
						assertTrue(cN!=null && cN.equals(c));
					}
				}
			}
		}
	}
	
    public void testApp() throws FormulaException {} ;

}