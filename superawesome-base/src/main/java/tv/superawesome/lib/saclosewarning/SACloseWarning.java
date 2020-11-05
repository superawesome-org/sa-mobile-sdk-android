package tv.superawesome.lib.saclosewarning;

import android.app.AlertDialog;
import android.content.Context;

public class SACloseWarning {
    private static final String CloseButtonTitle = "Close Video";
    private static final String ResumeButtonTitle = "Resume Video";
    private static final String AlertTitle = "Close Video?";
    private static final String AlertDescription = "You will lose your reward";

    private static AlertDialog dialog = null;

    private static SACloseWarning.Interface listener;

    public static void show(final Context c) {
        final AlertDialog.Builder alert;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            alert = new AlertDialog.Builder(c, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        } else {
            alert = new AlertDialog.Builder(c);
        }
        alert.setTitle(AlertTitle);
        alert.setCancelable(false);
        alert.setMessage(AlertDescription);

        alert.setPositiveButton(ResumeButtonTitle, (dialog, which) -> {
            if (listener != null) {
                listener.onResumeSelected();
            }
            dialog.dismiss();
        });

        alert.setNegativeButton(CloseButtonTitle, (dialog, which) -> {
            if (listener != null) {
                listener.onCloseSelected();
            }
            dialog.dismiss();
        });

        dialog = alert.create();
        dialog.show();
    }

    public static void close() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

    public static void setListener(Interface listener) {
        SACloseWarning.listener = listener;
    }

    public interface Interface {
        void onResumeSelected();

        void onCloseSelected();
    }
}
