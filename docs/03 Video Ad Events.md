The SAVideoActivity is helped by three listeners in order to notify users of events.
In order to use them, your activity must implement one or all of the Listeners:

```
public class MyActivity extends Activity implements SAAdListener, SAParentalGateListener, SAVideoAdListener {
	// private listener objects
	private SAAdListener adListener = this;
	private SAParentalGateListener parentalGateListener = this;
	private SAVideoAdListener videoAdListener = this;

	// rest of the activity implementation
	// ....
}


```

And in this case the activity must override all defined functions for SAAdListener:

```
	@Override
	public void adWasShown(int placementId) {
		System.out.println("adWasShown");
	}

    @Override
    public void adFailedToShow(int placementId) {
        System.out.println("adFailedToShow");
    }

    @Override
    public void adWasClosed(int placementId) {
        System.out.println("adWasClosed");
    }

    @Override
    public void adWasClicked(int placementId) {
        System.out.println("adWasClicked");
    }

    @Override
    public void adHasIncorrectPlacement(int placementId) {
        System.out.println("adHasIncorrectPlacement");
    }

```

And for SAParentalGateListener

```
@Override
    public void parentalGateWasCanceled(int placementId) {
        System.out.println("parentalGateWasCanceled");
    }

    @Override
    public void parentalGateWasFailed(int placementId) {
        System.out.println("parentalGateWasFailed");
    }

    @Override
    public void parentalGateWasSucceded(int placementId) {
        System.out.println("parentalGateWasSucceded");
    }

```

And for SAVideoAdListener

```
@Override
    public void adStarted(int placementId) {
        System.out.println("adStarted");
    }

    @Override
    public void videoStarted(int placementId) {
        System.out.println("videoStarted");
    }

    @Override
    public void videoReachedFirstQuartile(int placementId) {
        System.out.println("videoReachedFirstQuartile");
    }

    @Override
    public void videoReachedMidpoint(int placementId) {
        System.out.println("videoReachedMidpoint");
    }

    @Override
    public void videoReachedThirdQuartile(int placementId) {
        System.out.println("videoReachedThirdQuartile");
    }

    @Override
    public void videoEnded(int placementId) {
        System.out.println("videoEnded");
    }

    @Override
    public void adEnded(int placementId) {
        System.out.println("adEnded");
    }

    @Override
    public void allAdsEnded(int placementId) {
        System.out.println("allAdsEnded");
    }

```