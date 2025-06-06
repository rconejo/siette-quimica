package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class PoliacidoTest extends TestCase {

    public PoliacidoTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( PoliacidoTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = Poliacido.random(tablaPeriodica);
			// c = Oxoacido.analizar("H2Te3O10"); 
			// c.idIdioma(Compuesto.EN);
			// System.out.println(c.nomenclaturaTradicional());
			// System.out.println(c.patronTradicional());
			test(c);
		}
    }

}