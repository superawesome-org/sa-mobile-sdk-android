package tv.superawesome.lib;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by gabriel.coman on 30/04/2018.
 */
public class ResourceReader {1

    public static String readResource(String name) {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
        return convertStreamToString(in);
    }

    private static String convertStreamToString(InputStream is) {
        String kCHARSET = "UTF-8";
        String kDELIMITER = "\\A";
        Scanner s = new Scanner(is, kCHARSET).useDelimiter(kDELIMITER);
        return s.hasNext() ? s.next() : "";
    }
}