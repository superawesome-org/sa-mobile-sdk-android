Parental gates are used in apps targeted towards kids to prevent them from engaging in commerce or following links out of an app to websites, social networks, or other apps without the knowledge of their parent or guardian. A parental gate presents an adult level task which must be completed in order to continue.


For Banner, Interstitial or Video ads:

```
myBanner.setIsParentalGateEnabled(true);
myInterstitial.setIsParentalGateEnabled(true);
myVideoAd.setIsParentalGateEnabled(true);

```

Also, for Interstitial ads, if you use the shorthand `start()` function, set to "true" the third parameter:

```
SAInterstitialActivity.start(MainActivity.this, ad, true, adListener, parentalGateListener);

```

For Video ads, set to "true" the same third parameter of the `SAVideoActivity.start()` function:

```
SAVideoActivity.start(MainActivity.this, ad, true, adListener, parentalGateListener, videoAdListener);

```

![](img/parental_gate.png "Parental Gate on Android")