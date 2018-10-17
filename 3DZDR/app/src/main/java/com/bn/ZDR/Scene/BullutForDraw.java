package com.bn.ZDR.Scene;

import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.SideLengh;

/**
 * Created by zhuanxu on 2017/7/17.
 */

public class BullutForDraw
{
    MyGLSurfaceView myGLSurfaceView;
    GameView gameView;
    float r=2;
    public BullutForDraw(MyGLSurfaceView myGLSurfaceView,GameView gameView)
    {
        this.gameView=gameView;
        this.myGLSurfaceView=myGLSurfaceView;
    }
    //发射炮弹的方法
    public void fire(float zdrX,float zdrZ)
    {
        int tempCol=(int)(zdrX/SideLengh);
        int tempRow=(int)(zdrZ/SideLengh);
        boolean ishad=false;
        float x=0.5f+tempCol*SideLengh;
        float z=-0.5f+tempRow*SideLengh;
        for (Bullet b:gameView.bulletAl)
        {
            if (b.startX==x&&b.startZ==z)
            {
                ishad=true;
                break;
            }
        }
        //生成新的炮弹并加入炮弹列表
        if (!ishad)
        {
            gameView.bulletAl.add
                    (
                            new Bullet(x,z,3,gameView)
                    );
        }

    }

    public void tnt_fire(float zdrX,float zdrZ)
    {
        int tempCol=(int)(zdrX/SideLengh);//左上侧点行列
        int tempRow=(int)(zdrZ/SideLengh);
        boolean ishad=false;
        float x=0.5f+tempCol*SideLengh;
        float z=-0.5f+tempRow*SideLengh;
        for (TNTBullet b:gameView.tnt_bulletAl)
        {
            if (b.startX==x&&b.startZ==z)
            {
                ishad=true;
                break;
            }
        }
        //生成新的炮弹并加入炮弹列表
        if (!ishad)
        {
            gameView.tnt_bulletAl.add
                    (
                            new TNTBullet(x,z,3,gameView)
                    );
        }

    }
}
