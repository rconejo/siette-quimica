package siette.quimica;

import junit.framework.Test;
import junit.framework.TestSuite;
import siette.util.Random;

public class OxosalTest extends TestCase {

    public OxosalTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( OxosalTest.class );
    }

    public void testApp() {
		TablaPeriodica tablaPeriodica = new TablaPeriodica(new Random());
		// tablaPeriodica = tablaPeriodica.selectCurso("3ESO");
		
		// ConjuntoCompuestos cc = Oxosal.compuestos(TablaPeriodica.selectElemento("Rh"), TablaPeriodica.selectElemento("Mn"));

		for(int i=0; i<100; i++) {
			Compuesto c = Oxosal.random(tablaPeriodica);
			// Compuesto c = Compuesto.analizar("Mn(HCrO5)2");
			// c.idIdioma(Compuesto.EN);
			// Compuesto c = Compuesto.analizar("Bis(Hidrogeno(pentaoxidocromato)) de manganeso", Compuesto.ES);
			// System.out.println(c.nomenclaturaSistematica());
			// System.out.println(c.patronSistematica());
			test(c);
		}
   }

}