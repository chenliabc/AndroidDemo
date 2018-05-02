package com.chenli.openglmodule.sample3_1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.chenli.commonlib.util.gameutil.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Lenovo on 2018/5/2.
 */

public class Sample_3_1_Render implements GLSurfaceView.Renderer {

    private Context context;
    public static float[] mProjMatrix = new float[16];//4x4矩阵 投影用
    public static float[] mVMatrix = new float[16];//摄像机位置朝向9参数矩阵
    public static float[] mMVPMatrix = new float[16];//最后起作用的总变换矩阵
    int mProgram;//自定义渲染管线程序id
    int muMVPMatrixHandle;//总变换矩阵引用id
    int maPositionHandle; //顶点位置属性引用id
    int maColorHandle; //顶点颜色属性引用id
    String mVertexShader;//顶点着色器
    String mFragmentShader;//片元着色器
    FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲
    static float[] mMMatrix = new float[16];//具体物体的移动旋转矩阵，旋转、平移

    float vertices[] = {
            -0.8f,0,0,
            0,-0.8f, 0,
            0.8f,0,0
    };
    float colors[]=new float[]{
            1,1,1,0,
            0,0,1,0,
            0,1,0,0
    };



    public Sample_3_1_Render(Context context){
        this.context = context;

        float[] position = createPosition();

        mVertexBuffer = ByteBuffer.allocateDirect(position.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexBuffer.put(position);
        mVertexBuffer.position(0);

        mColorBuffer = ByteBuffer.allocateDirect(colors.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);



    }

    private float[] createPosition() {
        LinkedList<Float> list = new LinkedList<>();
        list.add(0.0f);
        list.add(0.0f);
        list.add(0.0f);
        float radius = 0.5f;
        float angle = 360f/360;
        for (int i = 0; i < 360 + angle; i+=angle) {
            list.add((float) Math.sin(i*Math.PI/180f)*radius);
            list.add((float) Math.cos(i*Math.PI/180f)*radius);
            list.add(0.0f);
        }

        float[] floats = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            floats[i] = list.get(i);
        }
        return floats;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);

        mVertexShader = ShaderUtils.loadFromAssetsFile("sample3/vertex.glsl", context.getResources());
        mFragmentShader = ShaderUtils.loadFromAssetsFile("sample3/frag.glsl",context.getResources());
        mProgram = ShaderUtils.createProgram(mVertexShader, mFragmentShader);

        GLES20.glUseProgram(mProgram);

        maPositionHandle = GLES20.glGetAttribLocation(mProgram,"aPosition");
        GLES20.glVertexAttribPointer(maPositionHandle,3,GLES20.GL_FLOAT,false,0,mVertexBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);

        maColorHandle = GLES20.glGetAttribLocation(mProgram,"aColor");
        GLES20.glVertexAttribPointer(maColorHandle,3,GLES20.GL_FLOAT,false,0,mColorBuffer);
        GLES20.glEnableVertexAttribArray(maColorHandle);

        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        Matrix.perspectiveM(mProjMatrix,0,45,width*1.0f/height,1f,10f);

        Matrix.setIdentityM(mVMatrix,0);
        Matrix.translateM(mVMatrix,0,0,0,-3f);

        Matrix.multiplyMM(mMVPMatrix,0,mProjMatrix,0,mVMatrix,0);

        GLES20.glUniformMatrix4fv(muMVPMatrixHandle,1,false,mMVPMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,mVertexBuffer.limit()/3);

    }
}
