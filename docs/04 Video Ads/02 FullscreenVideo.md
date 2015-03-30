If you just want to display a fullscreen video ad and not to worry about the listener interface, this is the easiest way to show a video ad.

Add the following line to you `AndroidManifest.xml` file so you can access the VideoAdActivity class from your code:

```
<activity android:name="tv.superawesome.mobile.view.VideoAdActivity" android:configChanges="orientation|screenSize"></activity>
```

Use the following code to display the fullscreen video ad ('this' refers to the current activity):
```
Intent intent = new Intent(this, VideoAdActivity.class);
startActivity(intent);
```

If you want to hide the loading dialog, you can pass the `disable_loading_dialog` flag to the activity:
```
Intent intent = new Intent(this, VideoAdActivity.class);
Bundle params = new Bundle();
params.putBoolean("disable_loading_dialog", true);
intent.putExtras(params);
startActivity(intent);
```