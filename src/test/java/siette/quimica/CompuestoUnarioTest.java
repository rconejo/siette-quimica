package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class CompuestoUnarioTest extends TestCase {

    public CompuestoUnarioTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( CompuestoUnarioTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = CompuestoUnario.random(tablaPeriodica);
			// c = new CompuestoUnario("Sg");
			test(c);
		}
    }

}