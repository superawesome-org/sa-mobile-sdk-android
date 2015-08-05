There are two ways of including the SDK in your Android project using Android Studio.


TODO: FINISH


First, you have to add the library to your workspace. To do this, download the library from [here](https://github.com/SuperAwesomeLTD/sa-mobile-sdk-android) then go to Eclipse's 'File' > 'Import' menu, expand the 'Android' section and choose 'Existing Android Code into Workspace'.

After importing the library to your workspace, your application needs to be linked to the SDK library project. View the properties for the project, and navigate to the 'Android' tab. In the lower part of the dialog, click 'Add' and choose the 'SAMobileSDK' project from the workspace.

Next, open the AndroidManifest.xml file in the root of the project. Add a 'Uses Permission' item named android.permission.INTERNET and android.permission.ACCESS_NETWORK_STATE.

![](img/eclipse_permissions.png "Setting the permissions in Eclipse")

Finally, copy the superawesome directory from the library's assets folder to your project's assets folder.

After you've set up the library the following lines should be present in your `AndroidManifest.xml` file:
```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>
```