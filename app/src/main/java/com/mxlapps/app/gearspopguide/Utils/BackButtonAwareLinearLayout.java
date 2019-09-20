package com.mxlapps.app.gearspopguide.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class BackButtonAwareLinearLayout extends FrameLayout {

    public interface BackButtonListener {
        void onBackButtonPressed();
    }

    @Nullable
    private BackButtonListener mListener;

    public BackButtonAwareLinearLayout(Context context) {
        super(context);
    }

    public BackButtonAwareLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackButtonAwareLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBackButtonListener(@Nullable BackButtonListener listener) {
        mListener = listener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && mListener != null) {
            mListener.onBackButtonPressed();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
