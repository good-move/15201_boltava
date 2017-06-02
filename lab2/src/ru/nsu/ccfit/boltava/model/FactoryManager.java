package ru.nsu.ccfit.boltava.model;

import ru.nsu.ccfit.boltava.model.actors.Dealer;
import ru.nsu.ccfit.boltava.model.actors.Supplier;
import ru.nsu.ccfit.boltava.model.car.Accessory;
import ru.nsu.ccfit.boltava.model.car.Body;
import ru.nsu.ccfit.boltava.model.car.Component;
import ru.nsu.ccfit.boltava.model.car.Engine;
import ru.nsu.ccfit.boltava.model.factory.Assembly;
import ru.nsu.ccfit.boltava.model.factory.AssemblyManager;
import ru.nsu.ccfit.boltava.model.storage.CarStorageManager;
import ru.nsu.ccfit.boltava.model.storage.StorageManager;

import java.util.ArrayList;
import java.util.HashMap;

public class FactoryManager {

    private CarStorageManager mCarStorageManager;
    private StorageManager<Engine> mEngineStorageManager;
    private StorageManager<Body> mBodyStorageManager;
    private StorageManager<Accessory> mAccessoryStorageManager;
    private Assembly mAssembly;
    private AssemblyManager mAssemblyManager;
    private ArrayList<Supplier<Engine>> mEngineSuppliers = new ArrayList<>();
    private ArrayList<Supplier<Body>> mBodySuppliers = new ArrayList<>();
    private ArrayList<Supplier<Accessory>> mAccessorySuppliers = new ArrayList<>();
    private ArrayList<Dealer> mDealers = new ArrayList<>();
    private ArrayList<String> mInitialOrders = new ArrayList<>();

    private boolean isRunning = false;

    public FactoryManager(EnvironmentConfiguration ec) {
        EnvironmentConfiguration.FactoryInfo factoryInfo = ec.getFactoryInfo();

        mCarStorageManager = new CarStorageManager(
                new ArrayList<>(ec.getCarDescriptions().keySet()),
                factoryInfo.getCarStorageSize());

        mEngineStorageManager = new StorageManager<Engine>(
                new ArrayList<>(ec.getEngineSuppliersInfo().keySet()),
                factoryInfo.getEngineStorageSize());

        mBodyStorageManager = new StorageManager<Body>(
                new ArrayList<>(ec.getBodySuppliersInfo().keySet()),
                factoryInfo.getBodyStorageSize());

        mAccessoryStorageManager = new StorageManager<>(
                new ArrayList<>(ec.getAccessorySuppliersInfo().keySet()),
                factoryInfo.getAccessoryStorageSize());

        mAssembly = new Assembly(
                mEngineStorageManager,
                mBodyStorageManager,
                mAccessoryStorageManager,
                mCarStorageManager,
                10,
                factoryInfo.getWorkersCount());

        mAssemblyManager = new AssemblyManager(mAssembly, ec.getCarDescriptions());
        mCarStorageManager.addCarPurchasedListener(mAssemblyManager);

        callSuppliers(
                mEngineSuppliers,
                ec.getEngineSuppliersInfo(),
                Engine.class,
                mEngineStorageManager);
        callSuppliers(
                mBodySuppliers,
                ec.getBodySuppliersInfo(),
                Body.class,
                mBodyStorageManager);
        callSuppliers(
                mAccessorySuppliers,
                ec.getAccessorySuppliersInfo(),
                Accessory.class,
                mAccessoryStorageManager);

        System.out.println("Dealers");

        HashMap<String, Integer> dealersInfo = ec.getOrderedCarSerials();
        dealersInfo.keySet().forEach(carSerial -> {
            for (int i = 0; i < dealersInfo.get(carSerial); ++i ) {
                mDealers.add(new Dealer(mCarStorageManager, carSerial));
                mInitialOrders.add(carSerial);
            }
        });


    }

    public void launchFactory() {
        if (isRunning) throw new RuntimeException("Factory is already running");
        isRunning = true;
        mEngineSuppliers.forEach(supplier -> supplier.getThread().start());
        mBodySuppliers.forEach(supplier -> supplier.getThread().start());
        mAccessorySuppliers.forEach(supplier -> supplier.getThread().start());
        mDealers.forEach(dealer -> dealer.getThread().start());
    }

    public void stopFactory() {
        mAssembly.shutDown();
        mEngineSuppliers.forEach(supplier -> supplier.getThread().interrupt());
        mBodySuppliers.forEach(supplier -> supplier.getThread().interrupt());
        mAccessorySuppliers.forEach(supplier -> supplier.getThread().interrupt());
        mDealers.forEach(dealer -> dealer.getThread().interrupt());
        isRunning = false;
    }

    private <ItemType extends Component> void callSuppliers(
            ArrayList<Supplier<ItemType>> suppliers,
            HashMap<String, Integer> suppliersInfo,
            Class<ItemType> itemClass,
            StorageManager<ItemType> storageManager) {
        suppliersInfo.keySet().forEach(componentSerial -> {
            for (int i = 0; i < suppliersInfo.get(componentSerial); i++) {
                suppliers.add(new Supplier<>(storageManager, itemClass, componentSerial));
            }

        });
    }

}