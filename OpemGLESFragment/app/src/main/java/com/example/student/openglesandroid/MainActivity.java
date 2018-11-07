package com.example.student.openglesandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kumaneko on 2018/04/15.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.replacelayout, mainFragment).commit();

    }


}
