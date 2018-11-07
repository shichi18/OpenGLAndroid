package com.example.student.openglesandroid;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by kumaneko on 2018/04/13.
 *
 */

public class Square {

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


    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private final int mProgram;

    // 配列の頂点当たりの座標数
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.5f, 0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f, 0.5f, 0.0f}; // top right

    private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // 頂点の描画命令


    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    float color[] = {0.2f, 0.709803922f, 0.898039216f, 1.0f};

    public Square() {
        // 形状座標の頂点バイトバッファを初期化
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (座標数 * フロートあたり 4 bytes)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // 描画リストのByteBufferを初期化
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (座標数 * 2 shortあたりbytes)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();

        //FloatBufferに座標追加
        drawListBuffer.put(drawOrder);
        // 最初の座標を読み取るためにバッファを設定
        drawListBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // 空のOpenGL ESプログラムを作成
        GLES20.glAttachShader(mProgram, vertexShader);  // プログラムに頂点シェーダを追加
        GLES20.glAttachShader(mProgram, fragmentShader);// プログラムにフラグメントシェーダを追加
        GLES20.glLinkProgram(mProgram);                 // OpenGL ESプログラム実行ファイルを作成

    }

    public void draw(float[] mvpMatrix) {
        // OpenGL ES環境にプログラムを追加する
        GLES20.glUseProgram(mProgram);

        // 頂点シェーダのvPositionメンバにハンドルを渡す
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 頂点へのハンドルを有効にする
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // 座標データを準備
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
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

        // 四角形を描く
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // 頂点配列を無効にする
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
