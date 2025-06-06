package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class CompuestoTest extends TestCase {

    public CompuestoTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( CompuestoTest.class );
    }

    public void testApp() throws FormulaException {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");
		
		for(int i=0; i<100; i++) {
			Compuesto c = Compuesto.random(tablaPeriodica);
			// Compuesto c = Compuesto.analizar("(NH4)3FO2");
			// c.idIdioma(Compuesto.EN);
			// System.out.println(c.nomenclaturaSistematica());
			// System.out.println(c.patronSistematica());
			test(c);
		}
    }

}