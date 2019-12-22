package ua.nure.kn17.nikolenko.usermanagement.util;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Message {
    private static final String BUNDLE_NAME = "messages";
    private  static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    private Message() {}

    public static String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
