package root.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FormatUtils {
    public static String formatDouble(double d) {
        return NumberFormat.getCurrencyInstance().format(d).replace(" ₽", "");
//        return Double.toString(d);
//        return new DecimalFormat("0.00").format(d);
    }
}
