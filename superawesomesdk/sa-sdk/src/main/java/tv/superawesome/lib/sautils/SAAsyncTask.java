package tv.superawesome.lib.sautils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by gabriel.coman on 18/03/16.
 */
public class SAAsyncTask {

    private SAAsycTaskReceiver receiver;
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    /**
     * Creates a new SAAsync Task
     */
    public SAAsyncTask(Context context, final SAAsyncTaskListener listener) {

        Persister.getInstance().listener = listener;

        /** create the receiver */
        receiver = new SAAsycTaskReceiver(new Handler());
        receiver.setReceiver(new SAAsyncReceiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                switch (resultCode) {
                    case STATUS_RUNNING:
                        break;
                    case STATUS_FINISHED: {
                        Persister.getInstance().listener.onFinish(Persister.getInstance().result);
                        break;
                    }
                    case STATUS_ERROR: {
                        Persister.getInstance().listener.onError();
                        break;
                    }
                }
            }
        });

        /** create and launch the Intent */
        Intent intent = new Intent(Intent.ACTION_SYNC, null, context, SAAsync.class);
        intent.putExtra("receiver", receiver);
        context.startService(intent);
    }

    /**
     * *********************************************************************************************
     * The Actual async service - as an IntentService
     * *********************************************************************************************
     */
    public static class SAAsync extends IntentService {

        /**
         * Creates an IntentService.  Invoked by your subclass's constructor.
         */
        public SAAsync() {
            super(SAAsync.class.getName());
        }

        /**
         * Implement main function
         * @param intent
         */
        @Override
        protected void onHandleIntent(Intent intent) {
            final ResultReceiver receiver = intent.getParcelableExtra("receiver");
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                Persister.getInstance().result = Persister.getInstance().listener.taskToExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Persister.getInstance().result == null) {
                receiver.send(STATUS_ERROR, Bundle.EMPTY);
            } else {
                receiver.send(STATUS_FINISHED, Bundle.EMPTY);
            }
        }
    }

    /**
     * *********************************************************************************************
     * The results receiver for the AsyncTask
     * *********************************************************************************************
     */
    public class SAAsycTaskReceiver extends ResultReceiver {
        /** the SAAsyncReceiver interface */
        private SAAsyncReceiver mReceiver;

        /**
         * Constructor
         */
        public SAAsycTaskReceiver (Handler handler) {
            super(handler);
        }

        /** setter and */
        public void setReceiver(SAAsyncReceiver receiver) {
            mReceiver = receiver;
        }

        /**
         * Results receiver overidden method
         * @param resultCode
         * @param resultData
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (mReceiver != null) {
                mReceiver.onReceiveResult(resultCode, resultData);
            }
        }
    }

    /**
     * *********************************************************************************************
     * Main public & private interfaces
     * *********************************************************************************************
     */
    private interface SAAsyncReceiver {
        /**
         * Used by The InternService receiver to pass data when finished
         * @param resultCode the result code (finished, error, running, etc)
         * @param resultData the result data
         */
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    public interface SAAsyncTaskListener {
        /**
         * This method basically tells the AsyncTask "what" piece of code to execute async
         * @return it returns a generic "Object", that can be anything from a String to an Array to null
         * @throws Exception - a generic Exception
         */
        Object taskToExecute() throws Exception;

        /**
         * On finish callback
         * @param result - a callback "return" parameter
         */
        void onFinish(Object result);

        /**
         * On Error callback
         */
        void onError();
    }

    /**
     * *********************************************************************************************
     * The Persister is a singleton private class that makes it easy to transfer complex objects
     * between the different internal objects in this super-class
     * *********************************************************************************************
     */
    private static class Persister {
        /**
         * Private objects - a reference to the Async Task listener and the result object
         */
        public SAAsyncTaskListener listener;
        public Object result;

        /**
         * Static methods to make it a singleton
         */
        private static final Persister instance = new Persister ();
        public static Persister  getInstance() { return instance; }
    }
}
