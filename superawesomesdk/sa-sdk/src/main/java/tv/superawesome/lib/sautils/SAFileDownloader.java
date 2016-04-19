package tv.superawesome.lib.sautils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import tv.superawesome.sdk.loader.SALoader;

/**
 * Created by gabriel.coman on 19/04/16.
 */
public class SAFileDownloader {

    /** constants */
    private final String PREFERENCES = "MyPreferences";
    private final String SA_FILE_STORE = "SA_FILE_STORE";
    private final String SA_FOLDER = "/satmofolder";

    /** private variables */
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private HashMap<String, String> fileStore;

    /** the singleton SuperAwesome instance */
    private static SAFileDownloader instance = new SAFileDownloader();
    public static SAFileDownloader getInstance(){
        return instance;
    }

    /** make the constructor private so that this class cannot be instantiated */
    private SAFileDownloader(){
        preferences = SAApplication.getSAApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();

        cleanup();
    }

    /**
     * Download a file in a sync way
     * @param videoURL
     * @return a filepath to the new file
     */
    public String downloadFileSync(String videoURL) {

        /** create the file path */
        final String key = SAUtils.generateUniqueKey();
        final String filePath = "samov_" + key + ".mp4";

        /** actually create the file */
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + SA_FOLDER);
        dir.mkdirs();
        File file = new File(dir, filePath);

        Log.d("SuperAwesome", "can read file " + file.canRead() + " " + file.canWrite());

        /** create streams */
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            /** start connection */
            URL url = new URL(videoURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int statusCode = connection.getResponseCode();
            int fileLength = connection.getContentLength();

            /** exception code != 200 */
            if (statusCode != HttpURLConnection.HTTP_OK) return null;

            /** get input stream and start writing to disk */
            input = connection.getInputStream();
            output = new FileOutputStream(file);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            /** here file is written */
            editor.putString(key, filePath);
            editor.apply();

        } catch (Exception e) {
            /** no file has beet written here */
            Log.d("SuperAwesome-FILE", "Exception: " + e.toString());
            return null;

        }

        try {
            if (output != null) output.close();
            if (input != null) input.close();
        } catch (IOException ignored) {}
        Log.d("SuperAwesome", "but does it get to here?");
        if (connection != null) connection.disconnect();

        /** return the filePath */
        return filePath;
    }

    /**
     * Cleanup function - it will remove files and reset preferences
     */
    private void cleanup() {
        Set<String> keys = preferences.getAll().keySet();
        for (String key : keys) {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath() + SA_FOLDER);
            dir.mkdirs();
            String filePath = preferences.getString(key, "");
            File file = new File(dir, filePath);
            boolean deleted = file.delete();
            Log.d("SuperAwesome", "Deleting " + deleted + ":" + file.toURI());
            editor.remove(key);
        }
        editor.apply();
    }
}
