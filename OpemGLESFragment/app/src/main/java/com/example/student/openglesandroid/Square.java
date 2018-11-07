package com.example.student.openglesandroid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by student on 2018/04/13.
 */

public class Square {


    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    // 配列の頂点当たりの座標数
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.5f, 0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f, 0.5f, 0.0f}; // top right

    private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // 頂点の描画命令

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
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }
}
