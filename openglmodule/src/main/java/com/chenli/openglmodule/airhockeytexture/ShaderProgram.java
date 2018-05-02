package com.chenli.openglmodule.airhockeytexture;

import android.content.Context;
import android.opengl.GLES20;

import com.chenli.commonlib.util.gameutil.ShaderUtils;

/**
 * Created by Lenovo on 2018/5/2.
 */

public class ShaderProgram {

    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;

    protected ShaderProgram(Context context,int vertexShaderResourceId,
                            int fragmentShaderResourceId){
        String vertexShaderSource = ShaderUtils.readTextFileFromResource(context, vertexShaderResourceId);
        String fragmentShaderSource = ShaderUtils.readTextFileFromResource(context,fragmentShaderResourceId);
        int vertexShader = ShaderUtils.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderUtils.compileFragmentShader(fragmentShaderSource);
        program = ShaderUtils.linkProgram(vertexShader,fragmentShader);
    }

    public void useProgram(){
        GLES20.glUseProgram(program);
    }

}
