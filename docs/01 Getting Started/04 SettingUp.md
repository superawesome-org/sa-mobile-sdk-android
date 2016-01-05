Once the SDK is added to your project (either through Gradle or as a .JAR + Resource files) you'll need to do some final setting up:

Open the AndroidManifest.xml file in the root of the project. Add two 'Uses Permission' items, named android.permission.INTERNET and android.permission.ACCESS_NETWORK_STATE, by copying the following into the root node of your manifest file:

```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>

```

Last, in the Activity or Fragment you'll want to display an Ad:

```
SuperAwesome.getInstance().setApplicationContext(getApplicationContext());
SuperAwesome.getInstance().setConfigurationProduction();
SuperAwesome.getInstance().enableTestMode();
// or SuperAwesome.getInstance().disableTestMode() once you're running real production

```

Congratulations, you've setup AwesomeAds SDK for Android.