package com.chenli.openglmodule.airhockeytexture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * Created by Lenovo on 2018/5/2.
 */

public class AirHockeyRender implements GLSurfaceView.Renderer {

    private final Context context;

    private static final float[] vertexTable = {
            0f,    0f, 0.5f, 0.5f,
            -0.5f, -0.8f,   0f, 0.9f,
            0.5f, -0.8f,   1f, 0.9f,
            0.5f,  0.8f,   1f, 0.1f,
            -0.5f,  0.8f,   0f, 0.1f,
            -0.5f, -0.8f,   0f, 0.9f
    };

    private static final float[] vertexMallet = {
            // Order of coordinates: X, Y, R, G, B
            0f, -0.4f, 0f, 0f, 1f,
            0f,  0.4f, 1f, 0f, 0f };

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] mMatrix = new float[16];

    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    private Table table;
    private Mallet mallet;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private FloatBuffer tableBuffer;
    private FloatBuffer malletBuffer;

    private int[] texture;
    private int program;
    private int programText;

    private int uMatrixLocation;
    private int uTextureUnitLocation;

    private int aPositionLocation;
    private int aTextureCoordinatesLocation;
    private int aColorLocation;

    public AirHockeyRender(Context context){
        this.context = context;

        tableBuffer = ByteBuffer
                .allocateDirect(vertexTable.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexTable);
        tableBuffer.position(0);

        malletBuffer = ByteBuffer
                .allocateDirect(vertexMallet.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexMallet);
        malletBuffer.position(0);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        table = new Table();
        mallet = new Mallet();

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.air_hockey_surface);
        texture = ShaderUtils.loadTexture(bitmap);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        Matrix.perspectiveM(projectionMatrix,0,45,width*1.0f/height,1f,5f);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
        Matrix.scaleM(modelMatrix,0,0.8f,1f,1f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
        Matrix.multiplyMM(mMatrix,0,projectionMatrix,0,modelMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        textureProgram.useProgram();
        textureProgram.setUniforms(mMatrix,texture[0]);
        table.bindData(textureProgram);
        table.draw();

        colorProgram.useProgram();
        colorProgram.setUniforms(mMatrix);
        mallet.bindData(colorProgram);
        mallet.draw();

    }
}
