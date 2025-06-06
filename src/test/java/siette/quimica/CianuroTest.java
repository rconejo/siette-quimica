package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class CianuroTest extends TestCase {

    public CianuroTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( CianuroTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica =  tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Cianuro c = Cianuro.random(tablaPeriodica);
			// c = (Cianuro) Compuesto.analizar("Pa(CN)4"); 
			// System.out.println (c.nomenclaturaSistematica());
			// System.out.println (c.patronSistematica());
			test(c);
		}
    }

}