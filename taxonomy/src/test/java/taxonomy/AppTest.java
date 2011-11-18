package taxonomy;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
       try {
          String dbDir = "target";
          Class.forName("org.sqlite.JDBC");
          Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbDir + "taxonomy.db", "sa", "");
       } catch(Exception e) {
          throw new RuntimeException(e);
       }
    }
    
    public void testDownload()
    {
       
    }
}
