To integrate the gamewall placement with your app you simply need to add some content to your manifest file and instantiate the `SAGamewall` class:

First, click on `Scheme name & API key` next to the placement in the dashboard to get the XML to paste into the manifest file; it will look something like this:

```
<intent-filter>
<action android:name="android.intent.action.VIEW"/>
<category android:name="android.intent.category.DEFAULT"/>
<category android:name="android.intent.category.BROWSABLE"/>
<data android:scheme="schemeName" android:host="publisher"/>
</intent-filter>
```

Paste the code into your manifest file under the activity you wish to display the gamewall in. Then you just need to instantiate the `SAGamewall` class somewhere inside the code for that activity:

```
gamewall = new SAGamewall(this, gamewallListener, "PLACEMENT_ID", "API_KEY");
```

SAGamewall takes the following parameters:

| Argument      | Type                | Description  |
|---------------|---------------------|--------------|
| activity      | Activity            | The activity the gamewall is being called from.|
| listener      | SAGamewall.Listener | A listener to add callback events to. Described in more detail below.        |
| placementID   | String              | The placement ID of the gamewall, as found on the dashboard.        |
| apiKey        | String              | The API key for the gamewall placement. This is found when you press `Scheme name & API key` next to the placement in the dashboard.|




