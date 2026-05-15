package alchemy;

/**
 * An exception thrown when a device is missing from a laboratory
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class DeviceNotPresentException extends RuntimeException {

    /**
     * The device that missing when the exception was thrown.
     */
    private final Device device;

    /**********************************************************
     *                     constructor
     **********************************************************/

    /**
     * Initialize a new DeviceNotPresentException for the given device.
     *
     * @param   device
     *          the device that was absent
     *
     * @post    the device of this exception is set to the given device
     *          | new.getDevice() == device
     */
    public DeviceNotPresentException(Device device) {
        super("Device missing " + device.getClass().getSimpleName());
        this.device = device;
    }

    /**
     * Return the device that was full when this exception was thrown.
     */
    public Device getDevice() {
        return this.device;
    }
}
