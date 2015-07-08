package org.nexage.sourcekit.mraid;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

public interface MRAIDViewListener {

    /******************************************************************************
     * A listener for basic MRAIDView banner ad functionality.
     ******************************************************************************/

    public void mraidViewLoaded(MRAIDView mraidView);

    public void mraidViewExpand(MRAIDView mraidView);

    public void mraidViewClose(MRAIDView mraidView);

    public boolean mraidViewResize(MRAIDView mraidView, int width, int height, int offsetX, int offsetY);

    public void mraidViewPageFinished(View view);
}
