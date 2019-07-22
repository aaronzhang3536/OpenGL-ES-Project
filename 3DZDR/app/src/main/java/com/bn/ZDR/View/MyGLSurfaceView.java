package com.bn.ZDR.View;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.bn.ZDR.Util.MainActivity;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.Util.TextureManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2017/7/2.
 */

public class MyGLSurfaceView extends GLSurfaceView
{
    MainActivity activity;
    SceneRenderer mRenderer;               //场景渲染器
    public static ZDRAbstractView currview;//当前view

    public MyGLSurfaceView(MainActivity activity)
    {
        super(activity);
        this.activity=activity;
        this.setEGLContextClientVersion(3);//设置使用OpenGL 3.0
        mRenderer=new SceneRenderer();      //创建场景渲染器
        this.setRenderer(mRenderer);        //设置渲染器
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染模式
    }

    public boolean onTouchEvent(MotionEvent e)
    {
        if (currview == null)
        {
            return false;
        }
        return currview.onTouchEvent(e);
    }

    private class SceneRenderer implements Renderer
    {
        public void onSurfaceCreated(GL10 gl, EGLConfig config)
        {
            GLES30.glClearColor(1f,1f,1f,1.0f);         //设置屏幕背景色RGBA
            GLES30.glEnable(GL10.GL_DEPTH_TEST);        //打开深度检测
            GLES30.glEnable(GLES30.GL_CULL_FACE);       //打开背面剪裁
            MatrixState3D.setInitStack();               //初始化2D场景变换矩阵
            MatrixState2D.setInitStack();               //初始化2D场景变换矩阵
            MatrixState3D.setLightLocation(8, 5, -5);   //初始化光源位置
            MatrixState3D.setLightDirection(0,1f,0f);   //初始化光源方向
            TextureManager.loadingTexture(MyGLSurfaceView.this,0,73);//预加载纹理
            currview=new LoadView(MyGLSurfaceView.this,activity);//加载界面
        }

        public void onDrawFrame(GL10 gl)
        {
            if (currview != null) {
                currview.drawView(gl);// 绘制界面信息
            }
        }

        public void onSurfaceChanged(GL10 gl,int width,int height)
        {
            GLES30.glViewport(0,0,width,height);        //设置视口大小及位置
        }
    }
}
