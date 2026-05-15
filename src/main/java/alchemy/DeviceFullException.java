package alchemy;

/**
 * An exception thrown when an ingredient is added to a device that is already
 * at full capacity (i.e. a single-slot device that already holds an ingredient).
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class DeviceFullException extends Exception {

    /**
     * The device that was full when the exception was thrown.
     */
    private final Device device;

    /**********************************************************
     * Constructor
     **********************************************************/

    /**
     * Initialize a new DeviceFullException for the given device.
     *
     * @param   device
     *          the device that was full
     *
     * @post    the device of this exception is set to the given device
     *          | new.getDevice() == device
     */
    public DeviceFullException(Device device) {
        super("Device is already full: " + device.getClass().getSimpleName());
        this.device = device;
    }

    /**
     * Return the device that was full when this exception was thrown.
     */
    public Device getDevice() {
        return this.device;
    }
}
