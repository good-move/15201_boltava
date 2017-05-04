package ru.nsu.ccfit.boltava.car;

public class Body extends Component {

    private static final IDGenerator mIDGenerator = new IDGenerator(Body.class.getName());

    public Body() {
        super("", mIDGenerator.getId());
    }

    public Body(String serial) {
        super(serial, mIDGenerator.getId());
    }

}
