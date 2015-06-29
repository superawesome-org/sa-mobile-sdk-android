package tv.superawesome.superawesomesdk.view;

public interface InterstitialViewListener {
	public void onLoaded();
	
	public void onAdDismiss();

	public void onAdLeave();

	public void onAdError();
}
