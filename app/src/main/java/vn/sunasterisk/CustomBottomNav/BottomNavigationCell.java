package vn.sunasterisk.CustomBottomNav;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import org.jetbrains.annotations.Nullable;

import kotlinx.android.extensions.LayoutContainer;
import vn.sunasterisk.curvedbottomnavigation.R;

public class BottomNavigationCell extends RelativeLayout implements LayoutContainer {

    private int defaultIconColor;
    private int selectedIconColor;
    private int circleColor;

    public void setIcon(int icon) {
        this.icon = icon;
        if(allowDraw) {

        }
    }

    private int icon;

    private String count;
    private int iconSize;
    private int countTextColor;
    private int countBackgroundColor;

    private Typeface countTypeface;
    private int rippleColor;
    private boolean isFromLeft;
    private long duration;
    private float progress;
    private boolean isEnabledCell;

    public View containerView;
    private boolean allowDraw;

    public static final String EMPTY_VALUE = "empty";

    public BottomNavigationCell(Context context) {
        super(context);
        initView();
    }

    public BottomNavigationCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BottomNavigationCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public BottomNavigationCell(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        allowDraw = true;
        containerView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, this);
    }

    private void animateProgress(final boolean enableCell, boolean isAnimate) {
        long duration = enableCell ? this.duration : 250;
        final ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        if (isEnabledCell)
            anim.setStartDelay(duration / 4);
        else
            anim.setStartDelay(0L);
        if (isAnimate)
            anim.setDuration(duration);
        else
            anim.setDuration(1L);
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = animation.getAnimatedFraction();
                if (enableCell)
                    progress = f;
                else
                    progress = 1f - f;
            }
        });
        anim.start();
    }

    @Nullable
    @Override
    public View getContainerView() {
        return null;
    }
}
