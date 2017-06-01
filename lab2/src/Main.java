import org.xml.sax.SAXException;
import ru.nsu.ccfit.boltava.model.ConfigParser;
import ru.nsu.ccfit.boltava.model.EnvironmentConfiguration;
import ru.nsu.ccfit.boltava.model.FactoryManager;
import ru.nsu.ccfit.boltava.view.Window;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        if (true) {
            ConfigParser parser = new ConfigParser();
            String xmlFile = "./src/config.xml";
            try {
                EnvironmentConfiguration ec = parser.parse(xmlFile);

                System.out.println(ec.getOrderedCarSerials());
                System.out.println(ec.getEngineSuppliersInfo());
                System.out.println(ec.getBodySuppliersInfo());
                System.out.println(ec.getAccessorySuppliersInfo());
                FactoryManager factoryManager = new FactoryManager(ec);
                factoryManager.launchFactory();
                Thread.sleep(2000);
                factoryManager.stopFactory();
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

//        Window w = new Window();

    }

}
