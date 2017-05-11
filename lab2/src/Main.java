import org.xml.sax.SAXException;
import ru.nsu.ccfit.boltava.ConfigParser;
import ru.nsu.ccfit.boltava.EnvironmentConfiguration;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        ConfigParser parser = new ConfigParser();
        String xmlFile = "./src/config.xml";
        String xsdFile = "./src/config.xsd";
        try {
            EnvironmentConfiguration ec = parser.parse(xmlFile, xsdFile);

            System.out.println(ec.getOrderedCarSerials());
            System.out.println(ec.getEngineSuppliersInfo());
            System.out.println(ec.getBodySuppliersInfo());
            System.out.println(ec.getAccessorySuppliersInfo());

            // create car dealers for each ordered car serial (new threads)
            // create car storage for each car in car description
            // for each id in component suppliers info:
            //      create component storage
            //      create supplier
            // create storage mangers for each storage component



        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}
