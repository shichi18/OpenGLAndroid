package com.example.student.openglesandroid;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by student on 2018/04/13.
 */

public class Triangle {

    private final String vertexShaderCode =
            //この行列メンバー変数は、操作するためのフックを提供
            // この頂点シェーダを使用するオブジェクトの座標
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    //マトリックスはgl_Positionの修飾子として含める必要がある
                    // uMVPMatrix係数*が最初に*必要であることに注意
                    // 行列乗算プロダクトが正しくなるようにする。
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    // ビュー変換にアクセスして設定するために使用
    private int mMVPMatrixHandle;

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;

    // 配列の頂点あたりの座標数
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // 反時計回り
            0.0f,  0.622008459f, 0.0f, // 上
            -0.5f, -0.311004243f, 0.0f, // 左下
            0.5f, -0.311004243f, 0.0f  // 右下
    };

    // RGBAで色設定
    //float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    float color[] = { 0.0f, 200.0f, 256.0f, 1.0f };

    private final int mProgram;

    public Triangle() {
        // 経常座標の頂点のバイトバッファを初期化
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (座標の数 * フロートあたり 4 bytes )
                triangleCoords.length * 4);

        // device hardwareのnative byte orderを使用
        bb.order(ByteOrder.nativeOrder());

        // ByteBufferFloatBufferからを作成
        vertexBuffer = bb.asFloatBuffer();

        //FloatBufferに座標追加
        vertexBuffer.put(triangleCoords);

        // 最初の座標を読み取るためにバッファを設定
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // 空のOpenGL ESプログラムを作成
        mProgram = GLES20.glCreateProgram();

        // プログラムに頂点シェーダを追加
        GLES20.glAttachShader(mProgram, vertexShader);

        // プログラムにフラグメントシェーダを追加
        GLES20.glAttachShader(mProgram, fragmentShader);

        // OpenGL ESプログラム実行ファイルを作成
        GLES20.glLinkProgram(mProgram);

    }

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    public void draw(float[] mvpMatrix) { // pass in the calculated transformation matrix

        // OpenGL ES環境にプログラムを追加する
        GLES20.glUseProgram(mProgram);

        // 頂点シェーダのvPositionメンバにハンドルを渡す
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 三角形の頂点へのハンドルを有効にする
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // 三角座標データを準備
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // フラグメントシェーダのハンドルを取得するColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // 三角形を描画するための色を設定
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // 形状の変換行列にハンドルを渡す
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // 投影とビュー変換をシェーダに渡す
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        //三角形を描く
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 頂点配列を無効にする
        GLES20.glDisableVertexAttribArray(mPositionHandle);


    }
}
