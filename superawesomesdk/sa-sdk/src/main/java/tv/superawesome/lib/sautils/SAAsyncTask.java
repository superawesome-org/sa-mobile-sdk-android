package tv.superawesome.lib.sautils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

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

        /** add something more to the hash map */
        String hash = SAUtils.generateUniqueKey();
        SAPersister persister = new SAPersister();
        persister.listener = listener;
        SAPersisterStore.getInstance().persisterHashMap.put(hash, persister);

        /** create the receiver */
        receiver = new SAAsycTaskReceiver(new Handler());
        receiver.setReceiver(new SAAsyncReceiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {

                String hash = resultData.getString("hash");
                SAPersister persister = SAPersisterStore.getInstance().persisterHashMap.get(hash);

                switch (resultCode) {
                    case STATUS_RUNNING:
                        break;
                    case STATUS_FINISHED: {
                        persister.listener.onFinish(persister.result);
                        break;
                    }
                    case STATUS_ERROR: {
                        persister.listener.onError();
                        break;
                    }
                }
                SAPersisterStore.getInstance().persisterHashMap.remove(hash);
            }
        });

        /** create and launch the Intent */
        Intent intent = new Intent(Intent.ACTION_SYNC, null, context, SAAsync.class);
        intent.putExtra("receiver", receiver);
        intent.putExtra("hash", hash);
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
            String hash = intent.getStringExtra("hash");
            SAPersister persister = SAPersisterStore.getInstance().persisterHashMap.get(hash);

            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                persister.result = persister.listener.taskToExecute();
                SAPersisterStore.getInstance().persisterHashMap.put(hash, persister);
            } catch (Exception e) {
                persister.result = null;
                SAPersisterStore.getInstance().persisterHashMap.put(hash, persister);
            }

            /** send results forward */
            Bundle bundle = new Bundle();
            bundle.putString("hash", hash);
            receiver.send(STATUS_FINISHED, bundle);
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
}
