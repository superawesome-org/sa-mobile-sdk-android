package tv.superawesome.superawesomesdk.view;

public interface InterstitialViewListener extends PlacementViewListener {
	public void onLoaded();
	
	public void onAdDismiss();

	public void onAdLeave();

	public void onAdError();
}
