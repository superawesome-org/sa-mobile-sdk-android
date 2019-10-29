package tv.superawesome.lib.saadloader.testutils;

import java.io.InputStream;
import java.util.Scanner;

import okio.Buffer;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class ResourceReader {

    public static Buffer readBuffer (String name) throws Exception {
        InputStream str = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
        Buffer buffer = new Buffer();
        buffer.readFrom(str);
        return buffer;
    }

    public static String readString(String name) {
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