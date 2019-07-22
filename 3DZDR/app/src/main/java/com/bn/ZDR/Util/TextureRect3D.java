package com.bn.ZDR.Util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.opengl.GLES30;
import android.os.Build;

import com.bn.ZDR.View.MyGLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

@SuppressLint("NewApi")
public class TextureRect3D
{
    int vCount = 6;
    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mTextureBuffer;//顶点纹理坐标数据缓冲
    int mProgram;// 自定义渲染管线程序id
    int muMVPMatrixHandle;// 总变换矩阵引用id
    int maPositionHandle; // 顶点位置属性引用id
    int maTexCoorHandle; // 顶点纹理坐标属性引用id
    int maNormalHandle; //顶点法向量属性引用
    int muMMatrixHandle;//位置、旋转变换矩阵
    String mVertexShader;// 顶点着色器
    String mFragmentShader;// 片元着色器
    float[] vertices;//定义顶点坐标
    public TextureRect3D(MyGLSurfaceView mv)//按需求大小绘制一个2D长方形到指定位置
    {
        // 初始化顶点坐标与着色数据
        initVertexData();
        // 初始化shader
        initShader(mv);
    }

    // 初始化顶点坐标与着色数据的方法
    private void initVertexData()
    {
        vertices = new float[] {
//                -1,1,0,//0
//                1,-1,0,//2
//                1,1,0,//3
//                -1,1,0,//0
//                -1,-1,0,//1
//                1,-1,0//2

                -1,0,-1,
                1,0,1,
                1,0,-1,
                -1,0,-1,
                -1,0,1,
                1,0,1
//
        };
        ByteBuffer vbb_advance = ByteBuffer
                .allocateDirect(vertices.length * 4);
        vbb_advance.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb_advance.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        float[] textures = new float[] {
                0, 0,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1};
        ByteBuffer cbb_advance = ByteBuffer
                .allocateDirect(textures.length * 4);
        cbb_advance.order(ByteOrder.nativeOrder());
        mTextureBuffer = cbb_advance.asFloatBuffer();
        mTextureBuffer.put(textures);
        mTextureBuffer.position(0);

    }

    // 初始化着色器
    @SuppressLint("NewApi")
    public void initShader(MyGLSurfaceView mv)
    {
        // 加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex_sky.sh",
                mv.getResources());
        // 加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag_sky.sh",
                mv.getResources());
        // 基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取位置、旋转变换矩阵引用
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix");
        //获取程序中顶点颜色属性引用
        maNormalHandle= GLES30.glGetAttribLocation(mProgram, "aNormal");
        // 获取程序中顶点位置属性引用id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        // 获取程序中顶点纹理坐标属性引用id
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        // 获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2) @SuppressLint("NewApi")
    public void drawSelf(int texId)
    {

        //指定使用某套shader程序
        GLES30.glUseProgram(mProgram);

        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState3D.getFinalMatrix(), 0);
        //将顶点位置数据传送进渲染管线
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );
        //将顶点纹理坐标数据传送进渲染管线
        GLES30.glVertexAttribPointer
                (
                        maTexCoorHandle,
                        2,
                        GLES30.GL_FLOAT,
                        false,
                        2*4,
                        mTextureBuffer
                );
        //允许顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);//启用顶点位置数据
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);//启用顶点纹理坐标数据
        //绑定纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//设置使用的纹理编号
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);//绑定指定的纹理id
        //以三角形的方式填充
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}