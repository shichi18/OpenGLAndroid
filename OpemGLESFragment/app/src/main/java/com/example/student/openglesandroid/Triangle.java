package com.example.student.openglesandroid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by student on 2018/04/13.
 */

public class Triangle {
    private FloatBuffer vertexBuffer;

    // 配列の頂点あたりの座標数
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // 反時計回り
            0.0f,  0.622008459f, 0.0f, // 上
            -0.5f, -0.311004243f, 0.0f, // 左下
            0.5f, -0.311004243f, 0.0f  // 右下
    };

    // RGBAで色設定
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

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

    }
}
