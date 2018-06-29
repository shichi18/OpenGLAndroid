package com.example.student.openglesandroid;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by student on 2018/04/15.
 */
class MyGLSurfaceView extends GLSurfaceView {

    public View parentView = null;
    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // OpenGL ES 2.0コンテキストを作成する
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        // GLSurfaceViewで描画するためのレンダラーを設定
        setRenderer(mRenderer);

        //描画データに変更がある場合にのみ、ビューをレンダリングします。
        // 三角形が自動的に回転するようにするには、この行をコメントアウト：
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEventは、タッチスクリーンやその他の入力コントロールから入力の詳細をレポート。
        // この場合、タッチ位置が変更されたイベントのみ適応

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // 中間線より上の回転の逆方向
                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }

                // 中間線の左に回転の逆方向
                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }


}
