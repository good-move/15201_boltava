package ru.nsu.ccfit.boltava.car;

import ru.nsu.ccfit.boltava.IDGenerator;

public class Accessory extends Component {

    private static final IDGenerator mIDGenerator = new IDGenerator(Accessory.class.getName());

    public Accessory(String serial) {
        super(serial, mIDGenerator.getId());
    }

}
