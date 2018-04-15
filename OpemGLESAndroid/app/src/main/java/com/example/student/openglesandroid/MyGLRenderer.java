package com.example.student.openglesandroid;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

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
        GLES20.glClearColor(0.0f,0.0f,256.0f,1.0f);

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

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shape
        mTriangle.draw(mMVPMatrix);
    }

    private final float[] mMVPMatrix = new float[16];// "Model View Projection Matrix"の略
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    //Viewのジオメトリが変更された際に呼び出し（デバイスの向き変更など）。
    //投影の定義
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        float ratio =(float) width / height;

        ///この投影行列はオブジェクトの座標に適用。onDrawFame（）メソッド内。
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
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
