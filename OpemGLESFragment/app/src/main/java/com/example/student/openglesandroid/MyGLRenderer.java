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

    private Triangle mTriangle;
    private Square   mSquare;

    //一回呼び出されViewのOpenGL ESの環境設定
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        //背景のフレームの色を設定
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);

        // 三角形の初期化
        mTriangle = new Triangle();
        // 四角形の初期化
        mSquare = new Square();
    }

    //Viewの再描画ごとに呼び出し
    @Override
    public void onDrawFrame(GL10 unused) {
        //背景色の再描画
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }
    //Viewのジオメトリが変更された際に呼び出し（デバイスの向き変更など）
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    public static int loadShader(int type, String shaderCode){

        // 頂点シェーダタイプを作成 (GLES20.GL_VERTEX_SHADER)
        //  //またはフラグメントシェーダタイプ (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        //ソースコードをシェーダに追加してコンパイルする
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }




}
