If you just want to display a fullscreen video ad and not to worry about the listener interface, this is the easiest way to show a video ad.


Use the following code within an activity to display the fullscreen video ad:
```

String testAd = "false";	// must be strings
String isParentalGateEnabled = "true"; // must be strings

SAVideoActivity.start(MainActivity.this, "__PlacementId__", testAd, isParentalGateEnabled, new SAVideoViewListener() {
    ....
    Same SAVideoViewListener implementation as for SAVideoFragment 
    ....                       
});

```