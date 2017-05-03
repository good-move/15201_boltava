package ru.nsu.ccfit.boltava.car;

public class Engine extends Component {

    private static final IDGenerator mIDGenerator = new IDGenerator();

    public Engine(String serial) throws IDGenerator.IDGenerationException {
        super(serial, mIDGenerator.getId());
    }

}
