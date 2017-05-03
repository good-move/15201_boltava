package ru.nsu.ccfit.boltava.car;

public class Accessory extends Component {

    private static final IDGenerator mIDGenerator = new IDGenerator();

    public Accessory(String serial) throws IDGenerator.IDGenerationException {
        super(serial, mIDGenerator.getId());
    }

}
