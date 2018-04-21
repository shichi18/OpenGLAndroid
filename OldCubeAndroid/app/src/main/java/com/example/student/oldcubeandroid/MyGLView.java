package com.example.student.oldcubeandroid;

import android.content.Context;
import android.opengl.GLSurfaceView;

class MyGLView extends GLSurfaceView{

    MyRenderer myRenderer;

    public MyGLView(Context context) {
        super(context);
        myRenderer = new MyRenderer();
        setRenderer(myRenderer);
    }
}
