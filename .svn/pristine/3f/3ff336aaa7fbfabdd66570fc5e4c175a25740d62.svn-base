package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class OxosalPoliatomicaTest extends TestCase {

    public OxosalPoliatomicaTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( OxosalPoliatomicaTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = OxosalPoliatomica.random(tablaPeriodica);
			c = Compuesto.analizar("(NH4)2C2O3");
			// System.out.println(c.nomenclaturaDeAdicion());
			test(c);
		}
     }

}