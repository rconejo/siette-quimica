package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class PeroxoacidoTest extends TestCase {

    public PeroxoacidoTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( PeroxoacidoTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = Peroxoacido.random(tablaPeriodica);
			test(c);
		}
    }

}