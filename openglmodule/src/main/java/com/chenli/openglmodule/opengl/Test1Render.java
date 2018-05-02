package com.chenli.openglmodule.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Lenovo on 2018/4/28.
 */

public class Test1Render implements GLSurfaceView.Renderer {

    private int program;
    private int vPosition;
    private int uColor;

    // 顶点着色器的脚本
    private static final String verticesShader
            = "attribute vec2 vPosition;            \n" // 顶点位置属性vPosition
            + "void main(){                         \n"
            + "   gl_Position = vec4(vPosition,0,1);\n" // 确定顶点位置
            + "}";

    // 片元着色器的脚本
    private static final String fragmentShader
            = "precision mediump float;         \n" // 声明float类型的精度为中等(精度越高越耗资源)
            + "uniform vec4 uColor;             \n" // uniform的属性uColor
            + "void main(){                     \n"
            + "   gl_FragColor = uColor;        \n" // 给此片元的填充色
            + "}";

    private int loadShader(int shaderType,String sourceCode){
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0){
            GLES20.glShaderSource(shader,sourceCode);
            GLES20.glCompileShader(shader);
        }
        return shader;
    }

    private int createProgram(String vertexSource,String fragmentSource){
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, fragmentSource);
        if (vertexShader == 0){
            return 0;
        }
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentSource);
        if (pixelShader == 0){
            return 0;
        }
        int program = GLES20.glCreateProgram();
        if (program != 0){
            GLES20.glAttachShader(program,vertexShader);
            GLES20.glAttachShader(program,pixelShader);
            GLES20.glLinkProgram(program);
        }
        return program;
    }

    private FloatBuffer getVertices(){
        float vertices[] = {
                0.0f,0.5f,
                -0.5f,-0.5f,
                0.5f,-0.5f,
        };
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(vertices);
        floatBuffer.position(0);
        return floatBuffer;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        program = createProgram(verticesShader,fragmentShader);
        vPosition = GLES20.glGetAttribLocation(program,"vPosition");
        uColor = GLES20.glGetAttribLocation(program,"uColor");
        GLES20.glClearColor(1.0f,0,0,1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES20.glViewport(0,0,i,i1);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        FloatBuffer vertices = getVertices();
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(program);
        GLES20.glVertexAttribPointer(vPosition,2,GLES20.GL_FLOAT,false,0,vertices);
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glUniform4f(uColor,0.0f,1.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,3);
    }
}
