package com.chenli.openglmodule;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = (GLSurfaceView) findViewById(R.id.gl_surface);
        surfaceView.setEGLContextClientVersion(2);
//        surfaceView.setRenderer(new com.chenli.openglmodule.AirHockey2.AirHockeyRender(this));
        surfaceView.setRenderer(new com.chenli.openglmodule.airhockeytexture.AirHockeyRender(this));
        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }


    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();

    }
}
