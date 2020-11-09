package tv.superawesome.sdk.publisher.ui.video;

import android.content.Context;

public interface ComponentCreator<T> {
    T createComponent(int id, Context context);
}
