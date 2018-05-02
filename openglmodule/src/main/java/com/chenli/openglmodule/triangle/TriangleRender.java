package com.chenli.openglmodule.triangle;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

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

public class TriangleRender implements GLSurfaceView.Renderer {

    private Context context;

    private float[] vMatrix = new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mViewMatrix=new float[16];

    private float[] triangleCoords = {
            0.5f,0.5f,0.0f,
            -0.5f,-0.5f,0.0f,
            0.5f,-0.5f,0.0f
    };

    //设置颜色
    float color[] = {
            0.0f, 1.0f, 0.0f, 1.0f ,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    private final FloatBuffer floatBuffer;
    private final FloatBuffer floatColor;
    private int vColor;
    private int matrixHandler;

    public TriangleRender(Context context){
        this.context = context;
        floatBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        floatBuffer.put(triangleCoords);
        floatBuffer.position(0);

        floatColor = ByteBuffer.allocateDirect(color.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        floatColor.put(color);
        floatColor.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);

        String fragmentShader = ShaderUtils.readTextFileFromResource(context, R.raw.triangle_fragment_shander);
        String vertexShader = ShaderUtils.readTextFileFromResource(context, R.raw.triangle_vertex_shander);
        int program = ShaderUtils.buildProgram(vertexShader, fragmentShader);

        GLES20.glUseProgram(program);

        matrixHandler = GLES20.glGetUniformLocation(program, "vMatrix");


        vColor = GLES20.glGetAttribLocation(program, "vColor");
        GLES20.glVertexAttribPointer(vColor,3,GLES20.GL_FLOAT,false,0,floatColor);
        GLES20.glEnableVertexAttribArray(vColor);

        int vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glVertexAttribPointer(vPosition,3,GLES20.GL_FLOAT,false,0,floatBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES20.glViewport(0,0,i,i1);

        float ratio = i*1.0f/i1;
        Matrix.perspectiveM(mProjectMatrix,0,45,ratio,-ratio,ratio);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(vMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniformMatrix4fv(matrixHandler,1,false,vMatrix,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,3);
    }
}
