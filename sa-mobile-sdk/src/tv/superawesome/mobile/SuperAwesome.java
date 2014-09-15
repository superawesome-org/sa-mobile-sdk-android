package tv.superawesome.mobile;

import java.util.List;
import java.util.Observable;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

public class SuperAwesome extends Observable implements ISettingsResponse{
	
	private static SuperAwesome instance = null;
	private List<Placement> placements;
	
	public static SuperAwesome getInstance() {
      if(instance == null) {
         instance = new SuperAwesome();
      }
      return instance;
   }
	
	public int getAppId(Context context){
		try {
			Log.d("SuperAwesome SDK", "Starting SDK");
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            Integer appId = bundle.getInt("tv.superawesome.sdk.ApplicationId");
            Log.d("SuperAwesome SDK", "appID=" + appId);
            return appId;
        } catch (NameNotFoundException e) {
        	Log.d("SuperAwesome SDK", "tv.superawesome.sdk.ApplicationId is not set");
        } catch (NullPointerException e) {
        	Log.d("SuperAwesome SDK", "tv.superawesome.sdk.ApplicationId is null");
        }
		return 0;
	}
	
	public void getSettings(int appId){
		SettingsAsyncTask task = new SettingsAsyncTask();
		task.delegate = this;
		task.execute(""+appId);
	}

	@Override
	public void receivedPlacements(List<Placement> placements) {
		Log.v("SuperAwesome SDK", "receivedPlacements");
		this.placements = placements;
		this.setChanged();
		this.notifyObservers();
	}

	public Placement getPlacement(int width, int height) {
		if(placements == null) return null;
		for(Placement p : placements){
			if(p.width == width && p.height == height){
				Log.v("SuperAwesome SDK", "found placement for size");
				return p;
			}
		}
		return null;
	}
}
