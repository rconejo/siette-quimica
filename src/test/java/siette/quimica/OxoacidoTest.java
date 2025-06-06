package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class OxoacidoTest extends TestCase {

    public OxoacidoTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( OxoacidoTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = Oxoacido.random(tablaPeriodica);
			// c = Oxoacido.analizar("H2SiO3");
			// c = Oxoacido.analizar("Ácido metasilícico",1);
			// System.out.println(c);
			test(c);
		}
    }

}