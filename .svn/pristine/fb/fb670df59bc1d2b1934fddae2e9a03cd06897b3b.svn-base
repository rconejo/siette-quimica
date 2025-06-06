package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class TioacidoTest extends TestCase {

    public TioacidoTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( TioacidoTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = Tioacido.random(tablaPeriodica);
			// c = Compuesto.analizar("H4I2S3O2");
			// c = Compuesto.analizar("Tetraoxidepentathiodiodate tetrahydrogen",2);
			// System.out.println(c);
			test(c);
		}
     }

}