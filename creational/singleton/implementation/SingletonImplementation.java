package creational.singleton.implementation;

import java.io.Serial;
import java.io.Serializable;

/**
 * The instance is created at the time of class loading.
 * It is thread-safe without synchronisation, but it may result in resource wastage if the instance is never used.
 */
class EagerInitializationSingleton {

    private static final EagerInitializationSingleton INSTANCE = new EagerInitializationSingleton();

    /**
     * Only can be accessed from within the class, there is no way to create an object from outside.
     */
    private EagerInitializationSingleton() {
    }

    public static EagerInitializationSingleton getInstance() {
        return INSTANCE;
    }
}

/**
 * The instance is created only when it's needed, delaying the instantiation until the first request.
 * It's not thread-safe unless additional synchronisation is used.
 */
class LazyInitializationSingleton {

    private static LazyInitializationSingleton instance;

    /**
     * Only can be accessed from within the class, there is no way to create an object from outside.
     */
    private LazyInitializationSingleton() {
    }

    /**
     * The instance will be created once, the first time when the method is called.
     * It is not synchronised, therefor multiple thread can enter the same time and re-instantiate the instance multiple times.
     */
    public static LazyInitializationSingleton getInstance() {
        if (instance == null) {
            instance = new LazyInitializationSingleton();
        }
        return instance;
    }
}

/**
 * All the benefits of the {@link LazyInitializationSingleton} but it also provides thread safety.
 * The downside is that id adds overhead due to synchronisation on each call.
 */
class LazyInitializationSingletonThreadSafe {

    private static LazyInitializationSingletonThreadSafe instance;

    /**
     * Only can be accessed from within the class, there is no way to create an object from outside.
     */
    private LazyInitializationSingletonThreadSafe() {
    }

    /**
     * The instance will be created once, the first time when the method is called.
     * It is synchronised, therefor multiple thread cannot enter the same time and re-instantiate the instance.
     */
    public static synchronized LazyInitializationSingletonThreadSafe getInstance() {
        if (instance == null) {
            instance = new LazyInitializationSingletonThreadSafe();
        }
        return instance;
    }
}

/**
 * Uses a static inner class to hold the instance, leveraging the JVM's class-loading mechanism to ensure thread safety.
 */
class BillPughSingleton {

    /**
     * Only can be accessed from within the class, there is no way to create an object from outside.
     */
    private BillPughSingleton() {
    }

    /**
     * The static inner class is loaded only when it is referenced, therefor the {@link BillPughSingletonHelper#INSTANCE}
     * is not created until the getInstance method is called, providing lazy initialisation.
     * The class loading mechanism of Java ensures that the static inner class is loaded only once, and it's thread safe.
     */
    private static class BillPughSingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return BillPughSingletonHelper.INSTANCE;
    }
}

/**
 * The Java runtime ensures that there will be only one instance of the enum, regardless of how many times it is accessed.
 * Enums by default handle serialisation and deserialization properly, preserving the Singleton property.
 * Enums instances are created when the class is loaded, ensuring that the instance is thread safe without needing additional synchronisation.
 * The con - it is not lazy-loaded.
 */
enum EnumSingleton {

    INSTANCE;
}

/**
 * Serialisation is the process of converting an object into a byte stream, which can be saved to a file, transmitted over a network, or stored in a database.
 * Deserialization is the reverse of serialisation. It converts a byte stream back into an object.
 * When an object is serialised, the current state of the object is saved, and upon deserialization, Java tries to recreate the state, but during that process it creates a new instance.
 * If we want to preserve the singleton instance and apply the changes from the deserialization state to it, we need to implement
 * {@link SerialisationProofSingleton#readResolve()} which will return the existing singleton object, and the state will be applied to it.
 * If the Singleton class does not implement a mechanism to return existing instance, deserialization will produce a new instance instead of returning the single instance.
 */
class SerialisationProofSingleton implements Serializable {

    private static final SerialisationProofSingleton INSTANCE = new SerialisationProofSingleton();

    /**
     * Only can be accessed from within the class, there is no way to create an object from outside.
     */
    private SerialisationProofSingleton() {
    }

    public static SerialisationProofSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * During the deserialization process Java calls readResolve method if it exists.
     * By returning the result of {@link SerialisationProofSingleton#getInstance()} we make sure that the deserialization will
     * apply the changes on the {@link SerialisationProofSingleton#INSTANCE} rather than creating a new instance.
     */
    @Serial
    protected Object readResolve() {
        return getInstance();
    }
}

/**
 * Reflection can break the Singleton by accessing the private constructor.
 * To prevent this, we can throw an exception if the constructor is invoked a second time.
 */
class ReflectionProofSingleton {

    private static final ReflectionProofSingleton INSTANCE = new ReflectionProofSingleton();

    private ReflectionProofSingleton() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Singleton instance already created");
        }
    }

    public static ReflectionProofSingleton getInstance() {
        return INSTANCE;
    }
}