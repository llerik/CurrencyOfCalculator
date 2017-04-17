package kirill.sorokin.ru.calculatorcurrency;

import android.content.res.AssetManager;
import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kirill.sorokin.ru.calculatorcurrency.xml.XMLParser;
import kirill.sorokin.ru.calculatorcurrency.xml.elements.ValCurs;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class XMLParserTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public XMLParserTest() {
        super(MainActivity.class);
    }

    public void test() throws Exception {
        InputStream stream = null;
        try {
            AssetManager assetManager = getInstrumentation().getContext().getAssets();
            stream = assetManager.open("XML_daily.asp.xml");
            String text = readStream(stream);
            XMLParser xmlParser = new XMLParser(getActivity(), text);
            ValCurs valCurs = xmlParser.parse();
            assertNotNull(valCurs);
            assertTrue(valCurs.getList().size() > 0);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(e.getMessage(), e);
                }
            }
        }
    }

    private String readStream(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader input = null;
        try {
            input = new InputStreamReader(stream, "Cp1251");
            char[] buf = new char[1000];
            int size = input.read(buf);
            while (size > 0) {
                sb.append(buf, 0, size);
                size = input.read(buf);
            }
        } catch (IOException e) {
            Log.e(e.getMessage(), e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                Log.e(e.getMessage(), e);
            }
        }
        return sb.toString();
    }
}