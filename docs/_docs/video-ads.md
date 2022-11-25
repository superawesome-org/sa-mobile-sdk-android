---
title: Video Ads
description: Video Ads
---

# Video Ads

The following code block sets up a video ad and loads it:

{% highlight java %}
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        // to display test ads
        SAVideoAd.enableTestMode ();

        // set configuration to production
        SAVideoAd.setConfigurationProduction ();

        // lock orientation to portrait or landscape
        SAVideoAd.setOrientationLandscape ();

        // enable or disable the android back button
        SAVideoAd.enableBackButton ();

        // enable or disable a close button
        SAVideoAd.enableCloseButton ();

        // enable or disable a close button that displays without a delay. Use instead of enableCloseButton.
        // WARNING: this will allow users to close the ad before the viewable tracking event is fired
        // and should only be used if you explicitly want this behaviour over consistent tracking.
        SAVideoAd.enableCloseButtonNoDelay();
        
        // enable close button and warn user before closing
        SAVideoAd.enableCloseButtonWithWarning();

        // enable or disable auto-closing at the end
        SAVideoAd.disableCloseAtEnd ();

        // make the whole video surface area clickable
        SAVideoAd.disableSmallClick ();
        
        // mute the video on start
        SAVideoAd.enableMuteOnStart ();

        // start loading ad data for a placement
        SAVideoAd.load (30479, MainActivity.this);
    }
}
{% endhighlight %}

Once you’ve loaded an ad, you can also display it:

{% highlight objective_c %}
public void onClick (View view) {

    // check if ad is loaded
    if (SAVideoAd.hasAdAvailable (30479)) {

        // display the ad
        SAVideoAd.play (30479, MainActivity.this);
    }
}
{% endhighlight %}

These are the default values:

| Parameter | Value |
|-----|-----|
| Configuration | Production |
| Test mode | Disabled |
| Orientation | Any | 
| Closes at end | True |
| Close button | Disabled |
| Small click button | Disabled | 
| Back button | Enabled |
| Close button with no delay | Disabled |
| Close with warning | Disabled |
| Mute on start | Disabled |
