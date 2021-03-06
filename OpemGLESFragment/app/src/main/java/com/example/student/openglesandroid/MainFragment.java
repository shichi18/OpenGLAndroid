package com.example.student.openglesandroid;


import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by kumaneko on 2018/04/15.
 */

public class MainFragment extends Fragment {

    private GLSurfaceView mGLView;
    private View lview = null;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lview = inflater.inflate(R.layout.fragment_main, container, false);

        //GLSurfaceViewインスタンスを作成して設定
        mGLView = new MyGLSurfaceView(this.getActivity());
        //このActivityのContentViewとして指定
        FrameLayout frameLayout = lview.findViewById(R.id.fragment_layout);
        frameLayout.addView(mGLView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return lview;
    }
}