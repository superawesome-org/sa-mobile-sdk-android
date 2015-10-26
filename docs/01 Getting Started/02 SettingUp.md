First, you have to add the library to your workspace. The easiest way to do this is to use Gradle; just include the following in your module's `build.gradle` file (usually the file under `MyApplication/app/`):

```
repositories {
    maven {
        url  "http://dl.bintray.com/sharkofmirkwood/maven"
    }
}

dependencies {
    compile 'tv.superawesome.sdk:sa-sdk:2.1.5@aar'
    compile 'tv.superawesome.sdk:bee7androidsdkgamewall:2.1.5@aar'
}
```

![](img/android_gradle_setup.png "Setting up SA in build.gradle")

Next, open the AndroidManifest.xml file in the root of the project. Add two 'Uses Permission' items, named android.permission.INTERNET and android.permission.ACCESS_NETWORK_STATE, by copying the following into the root node of your manifest file:

```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>
```