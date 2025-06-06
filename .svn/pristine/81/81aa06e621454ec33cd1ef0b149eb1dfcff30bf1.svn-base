package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class HidroxidoTest extends TestCase {

    public HidroxidoTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( HidroxidoTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = Hidroxido.random(tablaPeriodica);
			test(c);
		}
    }

}