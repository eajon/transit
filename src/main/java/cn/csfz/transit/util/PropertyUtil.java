package cn.csfz.transit.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author eajon
 */
public class PropertyUtil {

    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        props = new Properties();
        try (InputStream in = PropertyUtil.class.getClassLoader().
                getResourceAsStream("application.properties")) {
            props.load(in);
        } catch (IOException e) {

        }
        // logger.info(props);

    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }
}
