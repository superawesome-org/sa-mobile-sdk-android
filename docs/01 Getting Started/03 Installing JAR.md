If you're running an environment which does not support Gradle, then you'll need to add the SDK manually.

First, download two .jar files:

 * [gson-2.4.jar](https://github.com/SuperAwesomeLTD/sa-mobile-sdk-android/blob/develop_v3/docs/res/gson-2.4.jar?raw=true)
 * [sa-sdk-3.1.4.jar](https://github.com/SuperAwesomeLTD/sa-mobile-sdk-android/blob/develop_v3/docs/res/sa-sdk-3.1.4.jar?raw=true) 

You'll need to add these two to your project's `lib` folder, usually `MyApplication/app/libs`. The `libs` folder should be located on the same level as the `src` and `build` folders.

Once they're there, in Android Studion you'll need to select each one, right-click and click on `Add as Library`.

Secondly, you'll need to add the following items in you Manifest file, under the Application tag:

```
<!-- Awesome Ads custom Manifest part -->
<activity android:name="tv.superawesome.sdk.views.SAVideoActivity" android:label="SAVideoActivity"></activity>
<activity android:name="tv.superawesome.sdk.views.SAInterstitialActivity" android:label="SAInterstitialActivity"></activity>
<service android:name="tv.superawesome.lib.sanetwork.SAGet" android:exported="false" />

```

This will register two new activities and one service for your application, all needed by the SDK.

