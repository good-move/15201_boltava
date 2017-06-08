package ru.nsu.ccfit.boltava;

import ru.nsu.ccfit.boltava.model.Launcher;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Launcher launcher = new Launcher("config.xml", "config.xsd");
    }

}
