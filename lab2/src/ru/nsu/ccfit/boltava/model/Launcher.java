package ru.nsu.ccfit.boltava.model;

import ru.nsu.ccfit.boltava.view.MainWindow;

public class Launcher {

    public Launcher(String xmlConfigFilePath) throws InterruptedException {

        try {
            EnvironmentConfiguration ec = new ConfigParser().parse(xmlConfigFilePath);
            FactoryManager factoryManager = new FactoryManager(ec);
            MainWindow window = new MainWindow(factoryManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
