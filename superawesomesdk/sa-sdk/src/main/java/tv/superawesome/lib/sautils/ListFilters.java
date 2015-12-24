package tv.superawesome.lib.sautils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel.coman on 23/12/15.
 */
public class ListFilters {

    public static List removeAllButFirstElement(List original) {
        if (original.size() >= 1) {
            List finalList = new ArrayList<>();
            finalList.add(original.get(0));
            return finalList;
        } else  {
            return original;
        }
    }
}
