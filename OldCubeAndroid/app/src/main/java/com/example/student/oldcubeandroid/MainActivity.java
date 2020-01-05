package com.example.student.oldcubeandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    MyGLView myGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLView = new MyGLView(this);
        setContentView(myGLView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myGLView.onPause();
    }
}
