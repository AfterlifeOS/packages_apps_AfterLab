package com.afterlife.preferences.ui;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.settings.R;

public class LockscreenWidgetsPreview extends LinearLayout {

    public LockscreenWidgetsPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int previewHeight = (int) getResources().getDimension(R.dimen.lockscreen_preview_height_with_no_widgets);
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(previewHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }
}
