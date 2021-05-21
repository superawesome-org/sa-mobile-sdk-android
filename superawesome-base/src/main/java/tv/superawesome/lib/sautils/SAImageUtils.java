/*
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sautils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.File;

import static android.graphics.Bitmap.createScaledBitmap;
import static android.graphics.BitmapFactory.decodeByteArray;

public class SAImageUtils {

    public static Bitmap createBitmap (Context context, String filename, int width, int height) {
        File file = new File(context.getFilesDir(), filename);
        if (file.exists()) {
            String fileUrl = file.toString();
            Bitmap bitmap = BitmapFactory.decodeFile(fileUrl);
            return createScaledBitmap(bitmap, width, height, true);
        } else {
            return null;
        }
    }

    public static Bitmap createBitmap (int width, int height, int color, float radius) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, output.getWidth(), output.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(output, rect, rect, paint);

        return output;
    }

    public static Bitmap createBitmap (int width, int height, int color) {
        return createBitmap(width, height, color, 0);
    }

    public static Bitmap createBitmap (int color) {
        return createBitmap(1, 1, color);
    }

    public static Drawable createDrawable (Context context, String fileName, int width, int height, float radius) {
        return createDrawable(createBitmap(context, fileName, width, height), radius);
    }

    public static Drawable createDrawable (Context context, String fileName, int width, int height) {
        return createDrawable(context, fileName, width, height, 0);
    }

    public static Drawable createDrawable (int width, int height, int color, float radius) {
        return new SADrawable(createBitmap(width, height, color), radius, 0);
    }

    public static Drawable createDrawable (int width, int height, int color) {
        return createDrawable(width, height, color, 0);
    }

    public static Drawable createDrawable (int color) {
        return createDrawable(1, 1, color);
    }

    public static Drawable createDrawable (Bitmap bitmap, float radius) {
        return new SADrawable(bitmap, radius, 0);
    }

    public static Drawable createDrawable (Bitmap bitmap) {
        return createDrawable(bitmap, 0);
    }

    public static Bitmap createCloseButtonBitmap () {

        String imageString = "";

        imageString += "iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAAAXNSR0IArs4c6QAA";
        imageString += "ABxpRE9UAAAAAgAAAAAAAABAAAAAKAAAAEAAAABAAAADNgS9T/UAAAMCSURBVHgB";
        imageString += "7JlLbhNREEUtkBCLADLhtwgWwAJYmj2xLcufkdeDGJIw4bOAMIgU5VGFXFGpcQd/";
        imageString += "+nXqdt1BqTuO0uV+59R9dmdUShmx8q4B4ScfAApAAfLGH7e+MmICMAGYAJmTgAnA";
        imageString += "BGACMAGSTwEFoABpt8K0N5556v29U4Dk6UcBKAC/BfhIzHbOBGACMAGyTb2/XyYA";
        imageString += "E4AJ4Cci2zkTgAnABMg29f5+mQBMACaAn4hs50wAJgATINvU+/tlAjABmAB+IrKd";
        imageString += "MwGYAEyAbFPv75cJwARgAviJyHbOBGACMAGyTb2/XyYAE4AJ4Cci2/kgEmA8Hj/t";
        imageString += "G5z0fNZ3zxr94AUQEC+kvkh9rLFA+66pvaSupN7v+z3Sa9ACCACF/1WqSN1IVZdA";
        imageString += "e+x6ac9fUtASwAogC38PfzqdKozqEkiPe/iup0rwDmnq/XuFFEAW/KXU38mfz+dl";
        imageString += "u92W9XpdVQIPX3tpz8ViYT1/okoAJ8AO/qUci8FXGFqr1cqAdLod7INvPdElgBLg";
        imageString += "IfgGpGsJHoJvPRsSvPURG/0cRoBD4BuQriQ4BL71dBL8kL+DkQBCAFnQV1J7Y98A";
        imageString += "NI/nSnAMfOuNKEF4AXbwr+T4z55vC992PFWCU+Dbe2hI8IZbwBnP2s+Bb0COleAc";
        imageString += "+NYTSYKwCSAgLqROmnwDYcdDJegCvvV0EnyX64ZNgpAC7OB/k+PRsW8Amsf/SeDg";
        imageString += "39n3/OY1jv25IcHriNtBOAFqwDdwbRLUgG89o0sQSoCa8A1IU4Ka8K1nZAnCCCAg";
        imageString += "nkh9lir6nN0Wr8axIYE+News9tve72w2s6eUurU9j7IdhBFAF0QW5oPUtVRZLpd9";
        imageString += "SVAd/mazKZPJ5E7u61bqUxT4+j5CCfAYEnT1ga9t8iPDDylA3xK0gevi9ejwwwow";
        imageString += "BAkQ4IcWoCmBfnDrYir7uAYK/PACIEqABB9CACQJ0ODDCIAgASJ8KAEiS4AKH06A";
        imageString += "iBIgw4cUIJIE6PBhBYggwRDgQwvwmBIMBT68AE6C3/oPpD4eFg0Jvq7fHwAAAP//";
        imageString += "lVBvPwAAAx9JREFU7ZlLbttQDEWDFii6iLaZ9LeILiALyNLsiW0Y/oy8nqLDfib9";
        imageString += "LCAdFCiiXhZi8SCEhi3puaR4B8RzXhBR5D28Up6vmqa5ihyz2ewW8Xs+n9/v9/vm";
        imageString += "cDhUje122yCfxE/Eu8i9k3un+D2AKSC4iw5BWADQ+FvExSa/6yxTgSAkAP9bfIVh";
        imageString += "ChCEA8CL+FOBIBQA3sSfAgRhAPAqfnQIQgDgXfzIELgHIIr4USFwDUA08SNC4BaA";
        imageString += "qOIrBJvNRk8M71CL2xNDlwBEFz8SBO4AuKT4u92ukcMcFazG6t0JXAFwafGR7x6R";
        imageString += "GgI3AECIp4gvIshyuaw6lTL5yCPi/2qjOgSLxULfCd4j5yMv3yK6AUAagsa8RHxF";
        imageString += "NOv1ugoEHfFvkEtCQKgGwWq1UvEF8Gsv4st9uAKgNgRd8VWImhB4Ft8lALUgsMSv";
        imageString += "CYF38d0C0ELwCpM5yuOgFV9sWKz+RkXvrmM6QSH+Z1z3upvLy8/uHgFlY9A4geAb";
        imageString += "ovc7wania17kGvxO0BH/hV7b4+oaAGnYEAjOFV8FGgJBJPGlXvcAtBC8hihnOUFf";
        imageString += "8YdAUIj/CffrevK1zhAAnAvBUPG1Oec4QUf853oN72sYAAoIvkMY851gLPFVuFMg";
        imageString += "iCq+1BgKgBaCNxDlQQjGFv8UCCKLHxIAC4Ja4j8EgeSSL44K8T8CyjC2rzWFBaCA";
        imageString += "4Ic8Dopz9qP/55eF9/lcPg6KnCL+sz7X8/A34R4BZdPQ+LeIvxBgrSq+5kWef+cE";
        imageString += "+BxafKkpNABSAEQQCOS0zTzhU/HGWiUX4gMi7ORrL8ID0ELwRAu61ArxH18qV808";
        imageString += "kwCgZoOmfm0CgMfI1EU+Vl/q4o81JsvvCAAdILcFZpl0q046AB2ADmBNR4Z9OgAd";
        imageString += "gA6QYdKtGukAdAA6gDUdGfbpAHQAOkCGSbdqpAPQAegA1nRk2KcD0AHoABkm3aqR";
        imageString += "DkAHoANY05Fhnw5AB6ADZJh0q0Y6AB2ADmBNR4Z9OgAdgA6QYdKtGukAdAA6gDUd";
        imageString += "GfbpAHQAOkCGSbdqpAPQAegA1nRk2KcD0AHoABkm3aqRDpDcAf4AbuAOWc2aNWwA";
        imageString += "AAAASUVORK5CYII=";

        try {
            byte [] encodeByte = Base64.decode(imageString, Base64.DEFAULT);
            return decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Drawable createCloseButtonDrawable () {
        return createDrawable(createCloseButtonBitmap());
    }

    public static Bitmap createPadlockBitmap () {

        String imageString ="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";

        try {
            byte [] encodeByte= Base64.decode(imageString, Base64.DEFAULT);
            return decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Drawable createPadlockDrawable () {
        return createDrawable(createPadlockBitmap());
    }

    public static Bitmap createVideoGradientBitmap () {

        String imageString =
                "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAICAYAAADA+m62AAAAAXNSR0IArs4c6QAA" +
                "ABxpRE9UAAAAAgAAAAAAAAAEAAAAKAAAAAQAAAAEAAAAYzTSV/QAAAAvSURBVCgV" +
                "YmBgYBAhEjOYAhXiwmZIcgyhQE4YEINodIwszlAGVADDpUhsmBiYBgAAAP//1nMT" +
                "5wAAAChJREFUY2BgYJiKhKcB2SCMLgbiM2wF4m1EYIarQEXXiMAM34CKCGIAAN0p" +
                "shJZ248AAAAASUVORK5CYII=";

        try {
            byte [] encodeByte= Base64.decode(imageString, Base64.DEFAULT);
            return decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Drawable createVideoGradientDrawable () {
        return createDrawable(createVideoGradientBitmap());
    }
}