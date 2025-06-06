package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class HidruroTest extends TestCase {

    public HidruroTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( HidruroTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = Hidruro.random(tablaPeriodica);
			// c = Compuesto.analizar("GaH3");
			// c.idIdioma(Compuesto.EN);
			// System.out.println(c.patron());
			test(c);
		}
    }

}