package org.anc.lapps.serialization
import org.anc.io.FileUtils
import org.anc.resource.ResourceLoader
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class ContainerTest {

    private static final String INPUT_FILE_NAME = "Bartok.txt"

    private File TEST_FILE

    @Before
    public void setup()
    {
        TEST_FILE = File.createTempFile("containerTest", ".json")
    }

    @After
    public void tearDown()
    {
        if (!TEST_FILE.delete())
        {
            TEST_FILE.deleteOnExit()
        }
        TEST_FILE = null
    }

    @Ignore
    public void testTempFile()
    {
        println "Temp file is ${TEST_FILE.path}"
        assertTrue("Temp file not found", TEST_FILE.exists())
    }

    @Test
    public void testPrettyJson()
    {
        final Container original = new Container();
        original.text = ResourceLoader.loadString(INPUT_FILE_NAME);
        FileUtils.write(TEST_FILE, original.toPrettyJson());

        final String json = FileUtils.read(TEST_FILE);
        println json
        Container copy = new Container(json);
        //println container.text
        assertTrue(original.text == copy.text)
    }

    @Test
    public void testJson()
    {
        final Container original = new Container()
        original.text = ResourceLoader.loadString(INPUT_FILE_NAME)
        FileUtils.write(TEST_FILE, original.toJson());

        final String json = FileUtils.read(TEST_FILE)
        println json
        Container copy = new Container(json)
        assertTrue(original.text == copy.text)
    }
}
