package tv.superawesome.sdk.publisher.spies;

import android.view.View;

public class OnClickListenerSpy implements View.OnClickListener {

    private int noClicks = 0;

    @Override
    public void onClick(View view) {
        noClicks++;
    }

    public int getNoClicks() {
        return noClicks;
    }
}
