package vn.sunasterisk.CustomBottomNav;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class CellImageView extends AppCompatImageView {

    private boolean isBitmap = false;
    private boolean useColor = true;
    private int resource = 0;
    private int color = 0;
    private int size = Utils.dip(getContext(), 24);
    private boolean actionBackgroundAlpha = false;
    private boolean changeSize = true;
    private boolean fitImage = false;
    private ValueAnimator colorAnimator;
    private boolean allowDraw = false;

    public CellImageView(Context context) {
        super(context);
        initView();
    }

    public CellImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CellImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        allowDraw = true;
        draw();
    }

    private void draw() {
        if (!allowDraw) return;

        if (resource == 0) return;

        if (isBitmap) {
            try {
                Drawable drawable = (color == 0) ? ContextCompat.getDrawable(getContext(), resource)
                        : Utils.DrawableHelper.getInstance()
                        .changeColorDrawableResource(getContext(), resource, color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (useColor && color == 0) return;

        int color = useColor ? this.color : -2;

        try {
            setImageDrawable(Utils.DrawableHelper.getInstance()
                    .changeColorDrawableVector(getContext(), resource, color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeColorByAnim(final int newColor, long duration) {
        if (color == 0) {
            color = newColor;
            return;
        }
        final int lastColor = color;
        colorAnimator.cancel();
        colorAnimator = ValueAnimator.ofFloat(0f, 1f);
        colorAnimator.setDuration(duration);
        colorAnimator.setInterpolator(new FastOutSlowInInterpolator());
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = animation.getAnimatedFraction();
                color = Utils.ColorHelper.getInstance().mixTwoColor(newColor, lastColor, f);
            }
        });
        colorAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (fitImage) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) Math.ceil((double) ((float) width
                        * (float) drawable.getIntrinsicHeight()
                        / (float) drawable.getIntrinsicWidth()));
                setMeasuredDimension(width, height);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
            return;
        }

        if (isBitmap || !changeSize) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int newSize = MeasureSpec.makeMeasureSpec(this.size, MeasureSpec.EXACTLY);
        super.onMeasure(newSize, newSize);
    }
}
