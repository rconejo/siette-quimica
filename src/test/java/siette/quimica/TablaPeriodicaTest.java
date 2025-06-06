package siette.quimica;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import siette.util.RandomException;

public class TablaPeriodicaTest extends TestCase {

    public TablaPeriodicaTest( String testName ) {
        super( testName );
    }
    public static Test suite() {
        return new TestSuite( TablaPeriodicaTest.class );
    }

    public void testApp() throws RandomException {
    	TablaPeriodica tabla = new TablaPeriodica();
        assertTrue( tabla!=null );
    }

}
