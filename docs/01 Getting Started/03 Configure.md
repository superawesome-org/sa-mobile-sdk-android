To configure the SuperAwesome library you have to set your application ID in your project. Open the `AndroidManifest.xml` file in the root of the project and add a 'Meta Data' item called `tv.superawesome.sdk.ApplicationId`, with a value of your application ID that is shown on the dashboard site.

![](img/eclipse_meta.png "Setting the app ID in Eclipse")

If you haven't got an app ID go to [http://dashboard.superawesome.tv](http://dashboard.superawesome.tv) and register you app.

After you've configured the library the following line should be present in your `AndroidManifest.xml` file (with your own app ID):
```
<meta-data android:value="__APP_ID__" android:name="tv.superawesome.sdk.ApplicationId"/>
```