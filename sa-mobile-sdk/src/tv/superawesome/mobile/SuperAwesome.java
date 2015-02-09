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
	
	private Context context;
	private int appId;
	private List<Placement> placements;
	private List<Preroll> prerolls;
	private boolean isLoadingConfiguration = true;
	private boolean useParentalGate = false;
	
	public static SuperAwesome getInstance() {
      if(instance == null) {
         instance = new SuperAwesome();
      }
      return instance;
	}
	
	public void setContext(Context context){
		if(this.context == null){
			this.context = context;
			this.appId = getAppId();
			getSettings();
		}
		this.context = context;
	}
	
	public boolean getUseParentalGate(){
		return useParentalGate;
	}
	
	public void setUseParentalgate(boolean useParentalGate){
		this.useParentalGate = useParentalGate;
	}
	
	private int getAppId(){
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
	
	public void getSettings(){
		SettingsAsyncTask task = new SettingsAsyncTask();
		task.delegate = this;
		task.execute(""+appId);
	}
	
	public boolean getIsLoadingConfiguration(){
		return isLoadingConfiguration;
	}
	
	public Placement getPlacement(String placementID){
		if(placements == null) return null;
		for(Placement p : placements){
			if(p.id.equals(placementID)){
				return p;
			}
		}
		return null;
	}
	
	public Preroll getPreroll(){
		if(prerolls == null) return null;
		if(prerolls.size() == 0) return null;
		return prerolls.get(0);
	}

	@Override
	public void receivedConfiguration(List<Placement> placements,
			List<Preroll> prerolls) {
		Log.v("SuperAwesome SDK", "receivedPlacements");
		this.placements = placements;
		this.prerolls = prerolls;
		this.isLoadingConfiguration = false;
		this.setChanged();
		this.notifyObservers();
		
	}
}
