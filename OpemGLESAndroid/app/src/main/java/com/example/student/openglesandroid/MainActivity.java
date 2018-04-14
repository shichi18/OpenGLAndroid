package com.example.student.openglesandroid;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //GLSurfaceViewインスタンスを作成して設定
        mGLView = new MyGLSurfaceView(this);
        //このActivityのContentViewとして指定
        setContentView(mGLView);
    }
}
