package config;

import java.util.MissingResourceException;
import java.util.logging.Logger;

public class VigLog extends Logger {
    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers true.
     *
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     * @throws MissingResourceException if the ResourceBundleName is non-null and
     *                                  no corresponding resource can be found.
     * @param    name    A name for the logger.  This should
     * be a dot-separated name and should normally
     * be based on the package name or class name
     * of the subsystem, such as java.net
     * or javax.swing.  It may be null for anonymous Loggers.
     */
    protected VigLog(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }




}
