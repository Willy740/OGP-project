package alchemy;

/**
 * An exception thrown when a device type is already present in a laboratory
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class DeviceAlreadyPresentException extends RuntimeException {

    /**
     * The device that was already there when the exception was thrown.
     */
    private final Device device;

    /**********************************************************
     *                     constructor
     **********************************************************/

    /**
     * Initialize a new Exception for the given device.
     *
     * @param   device
     *          the device
     *
     * @post    the device of this exception is set to the given device
     *          | new.getDevice() == device
     */
    public DeviceAlreadyPresentException(Laboratory laboratory, Device device) {
        super("Device is already present in "+laboratory+": "+device.getClass().getSimpleName());
        this.device = device;
    }

    /**
     * Return the device that was full when this exception was thrown.
     */
    public Device getDevice() {
        return this.device;
    }
}
