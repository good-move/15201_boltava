package ru.nsu.ccfit.boltava.model;

import ru.nsu.ccfit.boltava.view.MainWindow;

public class Launcher {

    public Launcher(String xmlConfigFilePath, String xmlXchemaFilePath) throws InterruptedException {

        FactoryManager factoryManager = null;
        try {
            EnvironmentConfiguration ec = new ConfigParser().parse(xmlConfigFilePath, xmlXchemaFilePath);
            factoryManager = new FactoryManager(ec);
            MainWindow window = new MainWindow(factoryManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (factoryManager != null) {
                factoryManager.stopFactory();
            }
        }

    }

}
