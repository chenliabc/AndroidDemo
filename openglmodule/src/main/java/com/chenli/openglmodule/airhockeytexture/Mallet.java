package com.chenli.openglmodule.airhockeytexture;

import android.opengl.GLES20;

/**
 * Created by Lenovo on 2018/5/2.
 */

public class Mallet {

    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, R, G, B
            0f, -0.4f, 0f, 0f, 1f,
            0f,  0.4f, 1f, 0f, 0f };

    private VertexArray vertexArray;

    public Mallet(){
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram shaderProgram){
        vertexArray.setVertextAttribPoniter(0,shaderProgram.getPositionAttributeLocation(),2,5*4);
        vertexArray.setVertextAttribPoniter(2,shaderProgram.getColorAttributeLocation(),3,5*4);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_POINTS,0,2);
    }

}
