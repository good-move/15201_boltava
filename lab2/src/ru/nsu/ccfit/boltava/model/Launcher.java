package ru.nsu.ccfit.boltava.model;

import org.xml.sax.SAXException;
import ru.nsu.ccfit.boltava.view.MainWindow;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Launcher {

    public Launcher(String xmlConfigFilePath) throws InterruptedException {
        ConfigParser parser = new ConfigParser();
        try {
            EnvironmentConfiguration ec = parser.parse(xmlConfigFilePath);

//            System.out.println(ec.getOrderedCarSerials());
//            System.out.println(ec.getEngineSuppliersInfo());
//            System.out.println(ec.getBodySuppliersInfo());
//            System.out.println(ec.getAccessorySuppliersInfo());

            FactoryManager factoryManager = new FactoryManager(ec);
//            factoryManager.launchFactory();
//            Thread.sleep(2000);
//            factoryManager.stopFactory();

            MainWindow window = new MainWindow(factoryManager);


        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}
