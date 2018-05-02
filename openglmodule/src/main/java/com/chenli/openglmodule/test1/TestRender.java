package com.chenli.openglmodule.test1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.chenli.commonlib.util.gameutil.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Lenovo on 2018/5/1.
 */

public class TestRender implements GLSurfaceView.Renderer {

    private Context context;

    private float[] triangleCoords = {
            0.0f,0.6f,0.0f,
            -0.5f,-0.3f,0.0f,
            0.5f,-0.3f,0.0f
    };

    //顶点着色器
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";
    //片元着色器
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private final FloatBuffer floatBuffer;

    public TestRender(Context context){
        this.context = context;
        floatBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        floatBuffer.put(triangleCoords);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);

        int program = ShaderUtils.buildProgram(vertexShaderCode, fragmentShaderCode);
        GLES20.glUseProgram(program);

        int vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition,3,GLES20.GL_FLOAT,false,0,floatBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);

        int vColor = GLES20.glGetUniformLocation(program, "vColor");
        GLES20.glUniform4fv(vColor,1,color,0);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0, width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,3);

    }
}
