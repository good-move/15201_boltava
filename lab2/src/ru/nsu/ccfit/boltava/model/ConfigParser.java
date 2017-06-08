package ru.nsu.ccfit.boltava.model;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.boltava.model.car.CarDescription;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigParser {

    private static Logger logger = LogManager.getLogger(ConfigParser.class.getName());

    private Document mDocument;

    public EnvironmentConfiguration parse(String xmlFile)
            throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        mDocument = builder.parse(new File(xmlFile));

        HashMap<String, CarDescription> carDescriptions = getCarDescriptions();
        HashMap<String, Integer> carSerials = getDealerCarSerials();
        HashMap<String, Integer> suppliedBodySerials = getBodySuppliersInfo();
        HashMap<String, Integer> suppliedEngineSerials = getEngineSuppliersInfo();
        HashMap<String, Integer> suppliedAccessorySerials = getAccessorySuppliersInfo();
        EnvironmentConfiguration.FactoryInfo finfo = getFactoryInfo();

        EnvironmentConfiguration ec = new EnvironmentConfiguration();

        ec.setCarDescriptions(carDescriptions);
        ec.setOrderedCarSerials(carSerials);
        ec.setFactoryInfo(finfo);
        ec.setEngineSuppliersInfo(suppliedEngineSerials);
        ec.setBodySuppliersInfo(suppliedBodySerials);
        ec.setAccessorySuppliersInfo(suppliedAccessorySerials);

        return ec;
    }

    private HashMap<String, Integer> getDealerCarSerials() {
        HashMap<String, Integer> orderedCarsSerials = new HashMap<>();

        NodeList dealers = mDocument
                .getElementsByTagName("dealers")
                .item(0)
                .getChildNodes();

        for (int i = 0; i < dealers.getLength(); ++i) {
            if (dealers.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
            Element dealer = (Element) dealers.item(i);
            String carSerial = dealer.getAttribute("car_serial");
            Integer dealersCount = Integer.parseInt(dealer.getAttribute("count"));
            if (orderedCarsSerials.put(carSerial, dealersCount) != null) {
                logger.warn("Multiple occurrence of dealers of serial " + carSerial);
            }
        }

        return orderedCarsSerials;
    }

    private HashMap<String, CarDescription> getCarDescriptions() {
        HashMap<String, CarDescription> carDescriptions = new HashMap<>();

        NodeList cars = mDocument
                .getElementsByTagName("cars")
                .item(0)
                .getChildNodes();

        for (int i = 0; i < cars.getLength(); ++i) {
            if (cars.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
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

    private HashMap<String, Integer> getEngineSuppliersInfo() {
        return getComponentSuppliersInfo("engine_suppliers");
    }

    private HashMap<String, Integer> getBodySuppliersInfo() {
        return getComponentSuppliersInfo("body_suppliers");
    }

    private HashMap<String, Integer> getAccessorySuppliersInfo() {
        return getComponentSuppliersInfo("accessory_suppliers");
    }

    private HashMap<String, Integer> getComponentSuppliersInfo(String supplierType) {
        Element suppliers = (Element) mDocument.getElementsByTagName("suppliers").item(0);
        NodeList componentSuppliers = suppliers.
                getElementsByTagName(supplierType)
                .item(0)
                .getChildNodes();

        HashMap<String, Integer> suppliersInfo = new HashMap<>();

        for (int i = 0; i < componentSuppliers.getLength(); ++i) {
            if (componentSuppliers.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
            Element componentSupplier = (Element) componentSuppliers.item(i);
            String componentSerial = componentSupplier.getAttribute("component_serial");
            Integer suppliersCount = Integer.parseInt(componentSupplier.getAttribute("count"));
            suppliersInfo.put(componentSerial, suppliersCount);
        }

        return suppliersInfo;
    }

    private EnvironmentConfiguration.FactoryInfo getFactoryInfo() {
        NodeList factoryProperties = mDocument
                .getElementsByTagName("factory")
                .item(0)
                .getChildNodes();

        EnvironmentConfiguration.FactoryInfo fi = new EnvironmentConfiguration.FactoryInfo();

        for (int i = 0; i < factoryProperties.getLength(); ++i) {
            if (factoryProperties.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
            Element property = (Element) factoryProperties.item(i);
            if (property.getTagName().equals("storage")) {
                String resource = property.getAttribute("resource");
                int size = Integer.parseInt(property.getAttribute("size"));

                switch (resource) {
                    case "car": fi.setCarStorageSize(size); break;
                    case "engine": fi.setEngineStorageSize(size); break;
                    case "body": fi.setBodyStorageSize(size); break;
                    case "accessory": fi.setAccessoryStorageSize(size); break;
                    default: throw new RuntimeException("Unknown resource: " + resource);
                }

            } else if (property.getTagName().equals("workers")) {
                fi.setWorkersCount(Integer.parseInt(property.getAttribute("count")));
            }
        }

        return fi;
    }

}
