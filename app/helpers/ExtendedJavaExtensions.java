package helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import play.templates.JavaExtensions;

public class ExtendedJavaExtensions extends JavaExtensions {
    
    public static String format(final Date date, final String pattern, final String timezone) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sdf.format(date);
    }
    

}
