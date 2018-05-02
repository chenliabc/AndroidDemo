package com.chenli.openglmodule.AirHockey1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.chenli.commonlib.util.gameutil.ShaderUtils;
import com.chenli.openglmodule.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Lenovo on 2018/5/1.
 */

public class AirHockeyRender implements GLSurfaceView.Renderer {

    private static final int BYTES_PER_FLOAT = 4;
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private int uColorLocation;
    private int aPositionLocation;
    private final FloatBuffer vertexData;
    private Context context;
    float[] tableVerticeWithTriangels = {
            -0.5f, -0.5f,
            0.5f,  0.5f,
            -0.5f,  0.5f,

            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f,  0.5f,

            -0.5f, 0f,
            0.5f, 0f,

            0f, -0.25f,
            0f,  0.25f

    };

    public AirHockeyRender(Context context){
        this.context = context;
        vertexData = ByteBuffer.allocateDirect(BYTES_PER_FLOAT * tableVerticeWithTriangels.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticeWithTriangels);
        vertexData.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        String vertexShaderSource = ShaderUtils.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = ShaderUtils.readTextFileFromResource(context,R.raw.simple_fragment_shader);
        int vertexShader = ShaderUtils.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderUtils.compileFragmentShader(fragmentShaderSource);
        int program = ShaderUtils.linkProgram(vertexShader, fragmentShader);
        GLES20.glUseProgram(program);

        uColorLocation = GLES20.glGetUniformLocation(program,U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        GLES20.glVertexAttribPointer(aPositionLocation,2,GLES20.GL_FLOAT,false,0,vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES20.glViewport(0,0,i,i1);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);

        GLES20.glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES,6,2);

        GLES20.glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,8,1);

        GLES20.glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,9,1);

    }
}
