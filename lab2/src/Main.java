import org.xml.sax.SAXException;
import ru.nsu.ccfit.boltava.ConfigParser;
import ru.nsu.ccfit.boltava.EnvironmentConfiguration;
import ru.nsu.ccfit.boltava.FactoryManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ConfigParser parser = new ConfigParser();
        String xmlFile = "./src/config.xml";
        String xsdFile = "./src/config.xsd";
        try {
            EnvironmentConfiguration ec = parser.parse(xmlFile, xsdFile);

            System.out.println(ec.getOrderedCarSerials());
            System.out.println(ec.getEngineSuppliersInfo());
            System.out.println(ec.getBodySuppliersInfo());
            System.out.println(ec.getAccessorySuppliersInfo());
            FactoryManager factoryManager = new FactoryManager(ec);
            factoryManager.launchFactory();
//            factoryManager.stopFactory();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

}
