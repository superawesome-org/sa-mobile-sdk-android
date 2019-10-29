package tv.superawesome.lib;

import java.io.InputStream;

import okio.Buffer;

public class BufferedResourceReader {

    public static Buffer readResource (String name) throws Exception {
        InputStream str = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
        Buffer buffer = new Buffer();
        buffer.readFrom(str);
        return buffer;
    }
}