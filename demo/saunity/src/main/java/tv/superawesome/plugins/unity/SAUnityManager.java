package tv.superawesome.plugins.unity;

import java.util.HashMap;

/**
 * Created by gabriel.coman on 04/02/16.
 */
public class SAUnityManager {

    private HashMap<String, Object> adMap = null;

    /** the singleton SuperAwesome instance */
    private static SAUnityManager instance = new SAUnityManager();

    /** make the constructor private so that this class cannot be instantiated */
    private SAUnityManager(){
        adMap = new HashMap<>();
    }

    /** Get the only object available */
    public static SAUnityManager getInstance(){
        return instance;
    }

    /** setters and getters */
    public void setAdMap(String key, Object obj){
        adMap.put(key, obj);
    }

    public Object getAdMap(String key){
        return adMap.get(key);
    }

}
