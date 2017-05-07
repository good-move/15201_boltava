package ru.nsu.ccfit.boltava;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.boltava.car.CarDescription;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ConfigParser {

    private Document mDocument;

    public EnvironmentConfiguration parse(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        HashMap<String, CarDescription> carDescriptions = getCarDescriptions();
        String[] carSerials = getDealerCarSerials();
        String[] suppliedBodySerials = getSuppliedBodySerials();
        String[] suppliedEngineSerials = getSuppliedEngineSerials();
        String[] suppliedAccessorySerials = getSuppliedAccessorySerials();
        EnvironmentConfiguration.FactoryInfo finfo = getFactoryInfo();

        EnvironmentConfiguration ec = new EnvironmentConfiguration();

        ec.setCarDescriptions(carDescriptions);
        ec.setCarSerials(carSerials);
        ec.setFactoryInfo(finfo);
        ec.setSuppliedEngineSerials(suppliedEngineSerials);
        ec.setSuppliedBodySerials(suppliedBodySerials);
        ec.setSuppliedAccessorySerials(suppliedAccessorySerials);

        return ec;
    }

    private String[] getDealerCarSerials() {
        HashSet<String> carSerials = new HashSet<>();

        NodeList dealers = mDocument
                .getElementsByTagName("dealers")
                .item(0)
                .getChildNodes();

        for (int i = 0; i < dealers.getLength(); ++i) {
            Element dealer = (Element) dealers.item(i);
            String carSerial = dealer.getAttribute("car_serial");
            carSerials.add(carSerial);
        }

        return carSerials.toArray(new String[0]);
    }

    private HashMap<String, CarDescription> getCarDescriptions() {
        HashMap<String, CarDescription> carDescriptions = new HashMap<>();

        NodeList cars = mDocument
                .getElementsByTagName("cars")
                .item(0)
                .getChildNodes();

        for (int i = 0; i < cars.getLength(); ++i) {
            Element car = (Element) cars.item(i);
            Element engine =  (Element) car.getElementsByTagName("engine").item(0);
            Element body =  (Element) car.getElementsByTagName("body").item(0);
            Element accessory =  (Element) car.getElementsByTagName("accessory").item(0);

            String carSerial = car.getAttribute("serial");
            String engineSerial = engine.getAttribute("serial");
            String bodySerial = body.getAttribute("serial");
            String accessorySerial = accessory.getAttribute("serial");

            CarDescription carDescription = new CarDescription(carSerial, engineSerial, bodySerial, accessorySerial);
            carDescriptions.put(carSerial, carDescription);
        }

        return carDescriptions;
    }

    private String[] getSuppliedEngineSerials() {
        return getComponentSerials("engine_suppliers");
    }

    private String[] getSuppliedBodySerials() {
        return getComponentSerials("body_suppliers");
    }

    private String[] getSuppliedAccessorySerials() {
        return getComponentSerials("accessory_suppliers");
    }

    private String[] getComponentSerials(String supplierType) {
        Element suppliers = (Element) mDocument.getElementsByTagName("suppliers");
        NodeList componentSuppliers = suppliers.
                getElementsByTagName(supplierType)
                .item(0)
                .getChildNodes();

        HashSet<String> serials = new HashSet<>();

        for (int i = 0; i < componentSuppliers.getLength(); ++i) {
            Element componentSupplier = (Element) componentSuppliers.item(i);
            String componentSerial = componentSupplier.getAttribute("component_serial");
            serials.add(componentSerial);
        }

        return serials.toArray(new String[0]);
    }

    private EnvironmentConfiguration.FactoryInfo getFactoryInfo() {
        NodeList factoryProperties = mDocument
                .getElementsByTagName("factory")
                .item(0)
                .getChildNodes();

        int carStorageSize = 0;
        int engineStorageSize = 0;
        int bodyStorageSize = 0;
        int accessoryStorageSize = 0;
        int workersCount = 0;



        for (int i = 0; i < factoryProperties.getLength(); ++i) {
            Element property = (Element) factoryProperties.item(i);
            if (property.getTagName().equals("storage")) {
                String resource = property.getAttribute("resource");
                int size = Integer.parseInt(property.getAttribute("size"));

                switch (resource) {
                    case "car": carStorageSize = size; break;
                    case "engine": engineStorageSize = size; break;
                    case "body": bodyStorageSize = size; break;
                    case "accessory": accessoryStorageSize = size; break;
                    case "default": throw new RuntimeException("Unknown resource: " + resource);
                }

            } else if (property.getTagName().equals("workers")) {
                workersCount = Integer.parseInt(property.getAttribute("count"));
            }
        }

        return new EnvironmentConfiguration.FactoryInfo(
                carStorageSize,
                engineStorageSize,
                bodyStorageSize,
                accessoryStorageSize,
                workersCount);
    }

}
