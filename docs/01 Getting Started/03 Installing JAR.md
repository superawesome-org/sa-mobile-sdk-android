If you're running an environment which does not support Gradle, then you'll need to add the SDK manually.

First, download the JAR file [sa-sdk-3.5.2.jar](https://github.com/SuperAwesomeLTD/sa-mobile-sdk-android/blob/develop_v3/docs/res/sa-sdk-3.5.2.jar?raw=true)

You'll need to add it to your project's `lib` folder, usually `MyApplication/app/libs`. The `libs` folder should be located on the same level as the `src` and `build` folders.

Once they're there, in Android Studio you'll need to select it, right-click it and then `Add as Library`.

Secondly, you'll need to add the following items in you Manifest file, under the Application tag:

```
<!-- Awesome Ads custom Manifest part -->
<activity android:name="tv.superawesome.sdk.views.SAVideoActivity$SAVideoActivityInner"
		  android:label="SAVideoActivity"
		  android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen"
		  android:configChanges="keyboardHidden|orientation|screenSize"></activity>

<activity android:name="tv.superawesome.sdk.views.SAInterstitialActivity$SAInterstitialActivityInner"
		  android:label="SAInterstitialActivity"
		  android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen"
		  android:configChanges="keyboardHidden|orientation|screenSize"></activity>

<service android:name="tv.superawesome.lib.sanetwork.SAGet" android:exported="false" />

```

![](img/manifest.png "The new manifest structure")

This will register two new activities and one service for your application, all needed by the SDK.

Finally, the .jar library will depend on some external resources, such as assets, layouts, etc, to correctly display Ads.

Download [sa-sdk-res.zip](https://github.com/SuperAwesomeLTD/sa-mobile-sdk-android/blob/develop_v3/docs/res/sa-sdk-res.zip?raw=true)

and unzip it. You'll find three folders inside:
 * layout - containing a buch of XML files; copy the XML files inside your projects' layout folder
 * drawable - containing a bunch of PNG files; copy the PNG files inside your projects' drawable folder
 * assets - containing a folder named html; copy the whole folder inside your projects' assets folder.

In the end you should have the following folder structure (or something similar):

![](img/resources.png "The new folder structure")
