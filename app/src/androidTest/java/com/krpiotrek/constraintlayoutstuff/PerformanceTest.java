package com.krpiotrek.constraintlayoutstuff;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PerformanceTest extends InstrumentationTestCase {

    public static final String LOG_TAG = "bench";

    @SmallTest
    public void test() {
        for (int i = 0; i < 2; i++) {
            Log.i(LOG_TAG, "Test " + i);
            Log.i(LOG_TAG, "frame : " + getLayoutTime(R.layout.item_frame));
            Log.i(LOG_TAG, "linear : " + getLayoutTime(R.layout.item_old_linear));
            Log.i(LOG_TAG, "relative : " + getLayoutTime(R.layout.item_old_relative));
            Log.i(LOG_TAG, "constraint : " + getLayoutTime(R.layout.item_new));
        }
    }

    private long getLayoutTime(int layoutRes) {
        final Context targetContext = getInstrumentation().getTargetContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(targetContext);

        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            final View view = layoutInflater.inflate(layoutRes, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(0, 0));

            view.measure(View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            final int measuredHeight = view.getMeasuredHeight();
            final int measuredWidth = view.getMeasuredWidth();

            view.layout(0, 0, measuredWidth, measuredHeight);
        }
        return System.currentTimeMillis() - startTime;
    }
}
