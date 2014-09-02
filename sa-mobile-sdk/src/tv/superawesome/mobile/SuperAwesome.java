package tv.superawesome.mobile;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

public class SuperAwesome {
	private void getAppId(Context context){
		try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String appId = bundle.getString("tv.superawesome.sdk.ApplicationId");
        } catch (NameNotFoundException e) {
        } catch (NullPointerException e) {
        }
	}
}
