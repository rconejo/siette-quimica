package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class PeroxidoTest extends TestCase {

    public PeroxidoTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( PeroxidoTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = Peroxido.random(tablaPeriodica);
			// c = Compuesto.analizar("H2O2");
			// c.idIdioma(Compuesto.EN);
			// c = Compuesto.analizar("Tetradecaóxido de diosmio",1);
			// System.out.println(c);
			test(c);
		}
    }

}