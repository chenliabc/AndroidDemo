package com.chenli.openglmodule.AirHockey2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.chenli.commonlib.util.gameutil.MatrixHelper;
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
    private static final String A_COLOR = "a_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] vMatrix = new float[16];
    private int uMatrixLocation;

    private int uColorLocation;
    private int aPositionLocation;
    private final FloatBuffer vertexData;
    private Context context;
//    float[] tableVerticeWithTriangels = {
//            -0.5f, -0.5f,
//            0.5f,  0.5f,
//            -0.5f,  0.5f,
//
//            -0.5f, -0.5f,
//            0.5f, -0.5f,
//            0.5f,  0.5f,
//
//            -0.5f, 0f,
//            0.5f, 0f,
//
//            0f, -0.25f,
//            0f,  0.25f
//
//    };

    float[] tableVerticeWithTriangels = {
            0f,0f,1f,1f,1f,
            -0.5f,-0.8f,0.7f,0.7f,0.7f,
            0.5f,-0.8f,0.7f,0.7f,0.7f,
            0.5f,0.8f,0.7f,0.7f,0.7f,
            -0.5f,0.8f,0.7f,0.7f,0.7f,
            -0.5f,-0.8f,0.7f,0.7f,0.7f,

            -0.5f, 0f,1f,0f,0f,
            0.5f, 0f,1f,0f,0f,

            0f, -0.4f,0f,0f,1f,
            0f,  0.4f,1f,0f,0f

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

        uMatrixLocation = GLES20.glGetUniformLocation(program,U_MATRIX);

        uColorLocation = GLES20.glGetAttribLocation(program,A_COLOR);
        vertexData.position(2);
        GLES20.glVertexAttribPointer(uColorLocation,3,GLES20.GL_FLOAT,false,5*4,vertexData);
        GLES20.glEnableVertexAttribArray(uColorLocation);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation,2,GLES20.GL_FLOAT,false,5*4,vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        Matrix.perspectiveM(projectionMatrix,0,45,width*1.0f/height,1f,5f);
        Matrix.setIdentityM(modelMatrix,0);
        Matrix.translateM(modelMatrix,0,0f,0f,-2f);
        Matrix.scaleM(modelMatrix,0,0.5f,1f,1f);
        Matrix.rotateM(modelMatrix,0,-60f,1f,0f,0f);
        Matrix.multiplyMM(vMatrix,0,projectionMatrix,0,modelMatrix,0);

//        float aspectRatio = width>height?
//                width*1.0f/height:height*1.0f/width;
//        if (width>height){
//            Matrix.orthoM(projectionMatrix,0,-aspectRatio,aspectRatio,-1f,1f,-1f,1f);
//        }else {
//            Matrix.orthoM(projectionMatrix,0,-1f,1f,-aspectRatio,aspectRatio,-1f,1f);
//        }

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,vMatrix,0);

        GLES20.glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,6);

        GLES20.glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES,6,2);

        GLES20.glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,8,1);

        GLES20.glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,9,1);



    }
}
