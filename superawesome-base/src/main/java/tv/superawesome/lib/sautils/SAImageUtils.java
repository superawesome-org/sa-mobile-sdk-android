/**
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

        String imageString = "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";

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