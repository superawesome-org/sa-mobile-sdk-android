Banner Ads, Video Ads and Interstitial can be helped by up to three listeners in order to notify you of ad lifecycle events.

The simplest way to use them is as in the following example:

```

// just create a new video activity and setup its base parameters
// like the ad unit, is parental gate enabled, etc
SAVideoActivity vad = new SAVideoActivity(MainActivity.this);
vad.setAd(saAd);
vad.setIsParentalGateEnabled(true);
vad.setShouldShowCloseButton(true);
vad.setShouldAutomaticallyCloseAtEnd(false);

// set the normal ad Listener
// this returns callbacks for when an ad is shown or closed or when
// it's URL link is clicked
//
// this can be set for Banner Ads, Interstitial Ads and Video Ads
vad.setAdListener(new SAAdListener() {
	@Override
    public void adWasShown(int i) {

    }

	@Override
   	public void adFailedToShow(int i) {

    }

    @Override
    public void adWasClosed(int i) {

    }

    @Override
    public void adWasClicked(int i) {

    }

    @Override
    public void adHasIncorrectPlacement(int i) {

    }
});

// set the parental gate listener
// it's only active if the parental gate is enabled
//
// this can be set on Banner Ads, Interstitial Ads and Video Ads
vad.setParentalGateListener(new SAParentalGateListener() {
	@Override
    public void parentalGateWasCanceled(int i) {

    }

    @Override
    public void parentalGateWasFailed(int i) {

    }

    @Override
    public void parentalGateWasSucceded(int i) {

    }
});

// set the video ad listener
// this has callbacks for when a video starts, reached first quartile, etc
//
// can only be set on video ads
vad.setVideoAdListener(new SAVideoAdListener() {
	@Override
    public void adStarted(int i) {

    }

    @Override
    public void videoStarted(int i) {

	}

    @Override
    public void videoReachedFirstQuartile(int i) {

    }

    @Override
    public void videoReachedMidpoint(int i) {

    }

    @Override
    public void videoReachedThirdQuartile(int i) {

    }

    @Override
    public void videoEnded(int i) {

    }

    @Override
    public void adEnded(int i) {

    }

    @Override
    public void allAdsEnded(int i) {

    }
});

// finally play the ad
vad.play();

```

You can set the same listeners for the Interstitial and Banner ads shorthand method `start()`.
