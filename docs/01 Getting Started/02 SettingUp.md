First, you have to add the library to your workspace; it can be found at https://github.com/SuperAwesomeLTD/sa-mobile-sdk-android

To do this in Android Studio, you can either include the superawesomesdk-*.aar file in your project or download the entire 'superawesomesdk' directory and include it using Gradle by inserting the following into your 'settings.gradle' file:
```
include 'superawesomesdk'
project(':superawesomesdk').projectDir=new File('../superawesomesdk')
```

Next, open the AndroidManifest.xml file in the root of the project. Add two 'Uses Permission' items, named android.permission.INTERNET and android.permission.ACCESS_NETWORK_STATE, by copying the following into the root node of your manifest file:

```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>
```