package com.example.student.cubeandroid;

import android.content.Context;
import android.opengl.GLSurfaceView;

class MyGLSurfaceView extends GLSurfaceView {

    MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        setRenderer(mRenderer);

        //ダーティになったときだけ再描画するというモード
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
