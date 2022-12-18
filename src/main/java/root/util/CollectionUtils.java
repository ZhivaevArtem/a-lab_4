package root.util;

import java.util.Collection;
import java.util.List;

public class CollectionUtils {
    public static boolean sameElements(List c1, List c2) {
        if (c1.size() != c2.size()) return false;
        for (Object cc1 : c1) {
            if (!c2.contains(cc1)) return false;
        }
        for (Object cc2 : c2) {
            if (!c1.contains(cc2)) return false;
        }
        return true;
    }
}
