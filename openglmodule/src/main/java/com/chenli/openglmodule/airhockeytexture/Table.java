package com.chenli.openglmodule.airhockeytexture;

import android.opengl.GLES20;

/**
 * Created by Lenovo on 2018/5/2.
 */

public class Table {

    private static final float[] vertexData = {
            0f,    0f, 0.5f, 0.5f,
            -0.5f, -0.8f,   0f, 0.9f,
            0.5f, -0.8f,   1f, 0.9f,
            0.5f,  0.8f,   1f, 0.1f,
            -0.5f,  0.8f,   0f, 0.1f,
            -0.5f, -0.8f,   0f, 0.9f
    };

    private VertexArray vertexArray;

    public Table(){
        vertexArray = new VertexArray(vertexData);
    }

    public void bindData(TextureShaderProgram shaderProgram){
        vertexArray.setVertextAttribPoniter(0,shaderProgram.getPositionAttributeLocation(),2,4*4);
        vertexArray.setVertextAttribPoniter(2,shaderProgram.getTextureCoordinatesAttributeLocation(),2,4*4);
    }

    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }

}
