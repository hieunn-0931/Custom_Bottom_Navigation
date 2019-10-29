package vn.sunasterisk.CustomBottomNav;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class Utils {
    public static float getDP(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    protected static float dipf(Context context, Float f) {
        return f * getDP(context);
    }

    public static float dipf(Context context, int i) {
        return i * getDP(context);
    }

    public static int dip(Context context, int i) {
        return Integer.parseInt(String.valueOf(i * getDP(context)));
    }

    public static class DrawableHelper {
        private DrawableHelper() {
        }

        private static DrawableHelper instance;

        public static DrawableHelper getInstance() {
            if (instance == null) {
                instance = new DrawableHelper();
            }
            return instance;
        }

        Drawable changeColorDrawableVector(Context context, int resDrawable, int color) {
            if (context == null) return null;
            Drawable drawable = VectorDrawableCompat
                    .create(context.getResources(), resDrawable, null);
            if (drawable != null) {
                drawable.mutate();
                if (color != -2) drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
            return drawable;
        }

        Drawable changeColorDrawableResource(Context context, int resDrawable, int color) {
            if (context == null) return null;
            Drawable drawable = ContextCompat.getDrawable(context, resDrawable);
            if (drawable != null) {
                drawable.mutate();
                if (color != -2) drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
            return drawable;
        }
    }

    public static class ColorHelper {
        private ColorHelper() {
        }

        private static ColorHelper instance;

        public static ColorHelper getInstance() {
            if (instance == null) {
                instance = new ColorHelper();
            }
            return instance;
        }

        int mixTwoColor(int color1, int color2, float amount) {
            int alphaChannel = 24;
            int redChannel = 16;
            int greenChannel = 8;
            float inverseAmount = 1.0f - amount;

            int a = (int) ((float) (color1 >> alphaChannel & 255) * amount + (float) (color2 >> alphaChannel & 255) * inverseAmount) & 255;
            int r = (int) ((float) (color1 >> redChannel & 255) * amount + (float) (color2 >> redChannel & 255) * inverseAmount) & 255;
            int g = (int) ((float) (color1 >> greenChannel & 255) * amount + (float) (color2 >> greenChannel & 255) * inverseAmount) & 255;
            int b = (int) ((float) (color1 & 255) * amount + (float) (color2 & 255) * inverseAmount) & 255;

            return a << alphaChannel | r << redChannel | g << greenChannel | b;
        }
    }
}
