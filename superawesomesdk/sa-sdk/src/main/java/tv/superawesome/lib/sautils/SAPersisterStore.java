package tv.superawesome.lib.sautils;

import java.util.HashMap;

/**
 * Created by gabriel.coman on 12/04/16.
 */
public class SAPersisterStore {

    /**
     * The public HashMap that holds objects of
     *  key
     *  saperister { listener, object }
     */
    public HashMap<String, SAPersister> persisterHashMap;

    /**
     * private constructor (that's called just once)
     */
    private SAPersisterStore() {
        persisterHashMap = new HashMap<>();
    }

    /**
     * instance variable
     */
    private final static SAPersisterStore instance = new SAPersisterStore();

    /**
     * The public instance getter
     * @return the actual instance
     */
    public static SAPersisterStore getInstance() {
        return instance;
    }
}
