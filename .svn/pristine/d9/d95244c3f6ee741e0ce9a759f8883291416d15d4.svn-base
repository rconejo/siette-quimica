package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class SalAcidaTest extends TestCase {

    public SalAcidaTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( SalAcidaTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");

		for(int i=0; i<100; i++) {
			Compuesto c = SalAcida.random(tablaPeriodica);
			// c = SalAcida.analizar("Th(HSe)4"); 
			// c.idIdioma(Compuesto.EN);
			// System.out.println(c.nomenclaturaSistematica());
			// System.out.println(c.patronSistematica());
			test(c);
		}
    }

}