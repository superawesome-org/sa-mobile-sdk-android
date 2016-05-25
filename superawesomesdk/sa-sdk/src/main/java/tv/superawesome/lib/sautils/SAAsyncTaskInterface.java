package tv.superawesome.lib.sautils;

/**
 * Created by gabriel.coman on 25/05/16.
 */
public interface SAAsyncTaskInterface {
    Object taskToExecute() throws Exception;
    void onFinish(Object result);
    void onError();
}
