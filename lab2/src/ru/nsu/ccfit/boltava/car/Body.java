package ru.nsu.ccfit.boltava.car;

public class Body extends Component {

    private static final IDGenerator mIDGenerator = new IDGenerator();

    public Body(String serial) throws IDGenerator.IDGenerationException {
        super(serial, mIDGenerator.getId());
    }

}
