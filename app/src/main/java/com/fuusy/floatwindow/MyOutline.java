package com.fuusy.floatwindow;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class MyOutline extends ViewOutlineProvider {


    private float radius = 0;

    public MyOutline(float radius){

        this.radius = radius;
    }

    @Override
    public void getOutline(View view, Outline outline) {

        outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),radius);
    }
}
