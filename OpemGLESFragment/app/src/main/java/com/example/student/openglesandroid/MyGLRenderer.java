package com.example.student.openglesandroid;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by student on 2018/04/12.
 */

//親クラスGLSurfaceView.Renderer→描画されるものを制御する
class MyGLRenderer implements GLSurfaceView.Renderer{

    //一回呼び出されViewのOpenGL ESの環境設定
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        //背景のフレームの色を設定
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);
    }

    //Viewの再描画ごとに呼び出し
    @Override
    public void onDrawFrame(GL10 unused) {
        //背景色の再描画
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
    //Viewのジオメトリが変更された際に呼び出し（デバイスの向き変更など）
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }
}
