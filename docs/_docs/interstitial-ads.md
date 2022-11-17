---
title: Interstitial Ads
description: Interstitial Ads
---

# Interstitial Ads

The following code block sets up an interstitial ad and loads it:

{% highlight java %}
public class MainActivity extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        // set configuration to production
        SAInterstitialAd.setConfigurationProduction ();

        // to display test ads
        SAInterstitialAd.enableTestMode ();

        // lock orientation to portrait or landscape
        SAInterstitialAd.setOrientationPortrait ();

        // enable or disable the android back button
        SAInterstitialAd.enableBackButton ();

        // enable close button with a delay
        SAInterstitialAd.enableCloseButton ();

        // enable or disable a close button that displays without a delay. Use instead of enableCloseButton.
        // WARNING: this will allow users to close the ad before the viewable tracking event is fired
        // and should only be used if you explicitly want this behaviour over consistent tracking.
        SAInterstitialAd.enableCloseButtonNoDelay();

        // start loading ad data for a placement
        SAInterstitialAd.load (30473, MainActivity.this);
    }
}
{% endhighlight %}

Once you’ve loaded an ad, you can also display it:

{% highlight java %}
public void onClick (View view) {

    // check if ad is loaded
    if (SAInterstitialAd.hasAdAvailable (30473)) {

        // display the ad
        SAInterstitialAd.play (30473, MainActivity.this);
    }
}
{% endhighlight %}

These are the default values:

| Parameter | Value |
|-----|-----|
| Configuration | Production |
| Test mode | Disabled |
| Orientation | Any | 
| Back button | Enabled |
| Close button | Enabled |
| Close button with no delay | Disabled |
