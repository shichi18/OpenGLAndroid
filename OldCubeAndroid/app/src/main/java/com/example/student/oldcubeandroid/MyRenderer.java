package com.example.student.oldcubeandroid;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class MyRenderer implements GLSurfaceView.Renderer {
    float aspect = 0.0f;
    int vertices = 0;
    int indices = 0;
    int indicesLength = 0;

    float lightpos[] = {0.0f, 0.0f, 4.0f, 0.0f};
    float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
    float green[] = {0.0f, 1.0f, 0.0f, 1.0f};
    float blue[] = {0.0f, 0.0f, 1.0f, 1.0f};
    float white[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float gray[] = {0.5f, 0.5f, 0.5f, 1.0f};
    float yellow[] = {1.0f, 1.0f, 0.0f, 1.0f};

    // 光源の環境光
    private float[] lightAmbient = new float[]{ 0.1f, 0.1f, 0.1f, 1.0f };

    //光源の拡散光
    private float[] lightDiffuse = new float[]{ 0.9f, 0.9f, 0.9f, 1.0f };

    //光源の位置
    private float[] lightPos  = new float[]{ 0, 0, 2, 1 };

    //ビューのOpenGL ES環境を設定するために一度呼び出し。
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        //背景色の設定
        gl10.glClearColor(0.0f,0.0f,0.0f,1.0f);

    }

    //デバイスの画面の向きが変更された場合など、
    // ビューのジオメトリが変更された場合に呼び出される。
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        aspect = (float)width/(float)height;
        gl10.glViewport(0, 0, width, height);

        //バッファを生成
        GL11 gl11 = (GL11) gl10;
        {
            int[] buffer = new int[2];
            gl11.glGenBuffers(2, buffer, 0);

            vertices = buffer[0];//頂点用
            indices = buffer[1];//インデックス用
        }

        //頂点バッファを作成する
        //頂点転送
        {
            //final float one = 1.0f;
            final float[]vertices = new float[]{
                    -1.0f, -1.0f, -1.0f,
                    1.0f, -1.0f, -1.0f,
                    1.0f, 1.0f, -1.0f,
                    -1.0f, 1.0f, -1.0f,

                    -1.0f, -1.0f, 1.0f,
                    1.0f, -1.0f, 1.0f,
                    1.0f, 1.0f, 1.0f,
                    -1.0f, 1.0f, 1.0f
            };

            //float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };

            FloatBuffer fb = ByteBuffer.allocateDirect(vertices.length*4)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            fb.put(vertices);
            fb.position(0);

            gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER,this.vertices);
            gl11.glBufferData(GL11.GL_ARRAY_BUFFER,fb.capacity()*4,fb,GL11.GL_STATIC_DRAW);
            //頂点バッファの関連付け
            gl11.glVertexPointer(3, GL10.GL_FLOAT, 0, 0);
        }

        //インデックスバッファ生成
        {
            final byte[] indices = new byte[]{
                    0, 1, 3, 3, 1, 2, // Front
                    0, 1, 4, 4, 5, 1, // Bottom
                    1, 2, 5, 5, 6, 2, // Right
                    2, 3, 6, 6, 7, 3, // Top
                    3, 7, 4, 4, 3, 0, // Left
                    4, 5, 7, 7, 6, 5, // Rear
            };
            ByteBuffer bb = ByteBuffer.allocateDirect(indices.length)
                    .order(ByteOrder.nativeOrder());
            bb.put(indices);
            indicesLength = bb.capacity();
            bb.position(0);

            gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER,this.indices);
            gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER,bb.capacity(),bb,GL11.GL_STATIC_DRAW);

        }
    }
    //毎SurFace描画処理（再描画ごと）
    public void onDrawFrame(GL10 gl10) {

        // ライティングをON
        gl10.glEnable(GL10.GL_LIGHTING);
        // 光源を有効にして位置を設定
        gl10.glEnable(GL10.GL_LIGHT0);
        //　Light0の環境光の設定
        gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
        //　Light0の拡散光の設定
        gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
        //　Light0の場所の設定
        gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);


        //背景色の描画
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        //カメラ転送(GL_PROJECTION:カメラ用行列を示す定数)
        {
            gl10.glMatrixMode(GL10.GL_PROJECTION);
            //操作前のリセット
            gl10.glLoadIdentity();
            //カメラから見て奥側を圧縮し正規化。
            GLU.gluPerspective(gl10, 45.0f,aspect, 0.01f, 100.0f);
            //被写体の注視点とカメラの位置の決定
            GLU.gluLookAt(gl10, 0, 5.0f, 5.0f, 0, 0, 0.0f, 0.0f, 1.0f, 0.0f);
        }
        {//回転描写
           // gl10.glMatrixMode(GL10.GL_MODELVIEW);//自動カメラ回転
            gl10.glRotatef(30.0f,0,1,0);
        }

        GL11 gl11 = (GL11) gl10;
        gl11.glDrawElements(GL10.GL_TRIANGLES, indicesLength, GL10.GL_UNSIGNED_BYTE, 0);
    }
}