package cn.csfz.transit.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author eajon
 *
 */

public class FileUtils {

    public static String  makeTimeDir(String prefix)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String monthPackage = simpleDateFormat.format(new Date());
        simpleDateFormat = new SimpleDateFormat("dd");
        String dayPackage = simpleDateFormat.format(new Date());
        File file = new File(prefix+ "\\" + monthPackage + "\\" + dayPackage);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
