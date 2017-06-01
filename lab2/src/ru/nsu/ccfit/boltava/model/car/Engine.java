package ru.nsu.ccfit.boltava.model.car;

import ru.nsu.ccfit.boltava.model.IDGenerator;

public class Engine extends Component {

    private static final IDGenerator mIDGenerator = new IDGenerator(Engine.class.getName());

    public Engine(String serial) {
        super(serial, mIDGenerator.getId());
    }

}
