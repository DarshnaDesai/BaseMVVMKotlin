package com.basemvvmkotlin.utils;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Created by Darshna Desai on 2/1/19.
 */
public class ZoftinoCustomOutlineProvider  extends ViewOutlineProvider {

    int roundCorner;

    public ZoftinoCustomOutlineProvider(int round) {
        roundCorner = round;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), roundCorner);
    }
}
