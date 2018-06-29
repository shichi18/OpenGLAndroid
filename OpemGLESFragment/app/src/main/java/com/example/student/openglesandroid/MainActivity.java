package com.example.student.openglesandroid;

import android.opengl.GLSurfaceView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Fragment fragment;
        fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, fragment).commit();
    }


}
