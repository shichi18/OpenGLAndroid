package com.example.student.openglesandroid;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by student on 2018/04/15.
 */
class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context){
        super(context);

        // OpenGL ES 2.0コンテキストを作成する
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        // GLSurfaceViewで描画するためのレンダラーを設定
        setRenderer(mRenderer);
    }
}
