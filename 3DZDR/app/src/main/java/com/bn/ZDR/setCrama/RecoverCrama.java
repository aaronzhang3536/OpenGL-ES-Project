package com.bn.ZDR.setCrama;

import com.bn.ZDR.Thread.WelcomeThread;
import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;

import static com.bn.ZDR.Util.Constant.Scx;
import static com.bn.ZDR.Util.Constant.Scy;
import static com.bn.ZDR.Util.Constant.Scz;
import static com.bn.ZDR.Util.Constant.SideLengh;
import static com.bn.ZDR.Util.Constant.Stx;
import static com.bn.ZDR.Util.Constant.Sty;
import static com.bn.ZDR.Util.Constant.Stz;
import static com.bn.ZDR.Util.Constant.Supx;
import static com.bn.ZDR.Util.Constant.Supy;
import static com.bn.ZDR.Util.Constant.Supz;
import static com.bn.ZDR.Util.Constant.WelcomeThreadFlag;
import static com.bn.ZDR.Util.Constant.ZDRStartCol;
import static com.bn.ZDR.Util.Constant.recovercrama;
import static com.bn.ZDR.Util.Constant.recovercramaTime;
import static com.bn.ZDR.Util.Constant.upcrama;
import static com.bn.ZDR.Util.Constant.upcramaTime;

/**
 * Created by zhuanxu on 2017/8/30.
 */

public class RecoverCrama
{
    GameView gameView;
    float dx;//现在相机位置到目标相机位置的距离
    float dy;
    float dz;
    public static float cx=SideLengh*(ZDRStartCol+0.5f);	//摄像机位置x
    public static float cy=5f;  //摄像机位置y
    public static float cz=-3f;  //摄像机位置z//刚开始相差7.5
    public static float tx=SideLengh*(ZDRStartCol+0.5f);   //摄像机目标点x
    public static float ty=3f;     //摄像机目标点y
    public static float tz=-13.0f;   //摄像机目标点z
    public static float upx=-0f;  //摄像机UP向量X分量
    public static float upy=1.0f;      //摄像机UP向量Y分量
    public static float upz=-0f;  //摄像机UP向量Z分量
    float speedz=2.4f;//移动距离
    public RecoverCrama(GameView gameView)
    {
        this.gameView=gameView;
    }
    public void caculateXYZDistince()
    {
        dx=cx- Constant.Scx;
        dy=cy-Constant.Scy;
        dz=cz-Scz;
       // System.out.println("dz="+dz);
        speedz=Math.abs(dz)/4;
    }
    public void upCrama()
    {
        if (dz>0)
        {
            Constant.Scz=Constant.Scz+speedz;
        }
        else
        {
            Constant.Scz=Constant.Scz-speedz;
        }
        if (dx>0)
        {
            Constant.Scx+=speedz*(Math.abs(dx)/Math.abs(dz));
        }
        else
        {
            Constant.Scx-=speedz*(Math.abs(dx)/Math.abs(dz));
        }
        if (dz>0)
        {
            Constant.Scy+=speedz*(Math.abs(dy)/Math.abs(dz));
        }
        else
        {
            Constant.Scy-=speedz*(Math.abs(dy)/Math.abs(dz));
        }
        Stx=tx;
        Sty=ty;
        Stz=tz;
        Supx=upx;
        Supy=upy;
        Supz=upz;
        MatrixState3D.setCamera(Constant.Scx,Constant.Scy, Scz,Stx,Sty,Stz,Supx,Supy,Supz);
        if (Scz>=cz&&dz>0)
        {
            upcrama=false;
            upcramaTime=0;
            Scx=cx;
            Scy=cy;
            Scz=cz;
            Stx=tx;
            Sty=ty;
            Stz=tz;
            WelcomeThreadFlag=true;
            gameView.welcomeThread=null;
            gameView.welcomeThread=new WelcomeThread(gameView);
            gameView.welcomeThread.start();
            recovercrama=false;
            recovercramaTime=0;
        }
        else  if (Scz<=cz&&dz<0)
        {
            upcrama=false;
            upcramaTime=0;
            Scx=cx;
            Scy=cy;
            Scz=cz;
            WelcomeThreadFlag=true;
            gameView.welcomeThread=null;
            gameView.welcomeThread=new WelcomeThread(gameView);
            gameView.welcomeThread.start();
            recovercrama=false;
            recovercramaTime=0;
        }
    }
}
