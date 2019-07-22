package com.bn.ZDR.setCrama;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MainActivity;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;

import static com.bn.ZDR.Util.Constant.SPAN;
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
import static com.bn.ZDR.Util.Constant.upcrama;
import static com.bn.ZDR.Util.Constant.upcramaTime;

/**
 * Created by zhuanxu on 2017/8/27.
 */

public class UpCrama//人摄像机相距为5.5
{
    GameView gameView;
    float dx;//现在相机位置到目标相机位置的距离
    float dy;
    float dz;
    public static float cx=7f;	//摄像机位置x
    public static float cy=19f;  //摄像机位置y
    public static float cz=-4.0f;  //摄像机位置z//////////////
    public static float tx=5.5f;   //摄像机目标点x
    public static float ty=0f;     //摄像机目标点y
    public static float tz=-12f;   //摄像机目标点z//////////////////////////
    public static float upx=-0.7f;  //摄像机UP向量X分量
    public static float upy=40f;      //摄像机UP向量Y分量
    public static float upz=-1.0f;  //摄像机UP向量Z分量
    public static float cramatZtoRenZ=-1.5f;//-
    public static float cramaCZtocramaTZ=cz-tz;//+
    float speedy=2.8f;//移动距离
    public UpCrama(GameView gameView)
    {
        this.gameView=gameView;
    }
    public void caculateXYZDistince()
    {
        dx=cx- Constant.Scx;
        dy=cy-Constant.Scy;
        dz=cz-Constant.Scz;
        speedy=Math.abs(dy)/10;
//        upx=tx-cx;
//        upz=tz-cz;
        //upy=toSupy(cx,cy,cz,tx,ty,tz);
        //System.out.println("cy="+upy);
    }
    public void upCrama()
    {
        Constant.Scy+=speedy;
        if (dx>0)
        {
            Constant.Scx+=speedy*(Math.abs(dx)/Math.abs(dy));
        }
        else
        {
            Constant.Scx-=speedy*(Math.abs(dx)/Math.abs(dy));
        }
        if (dz>0)
        {
            Constant.Scz+=speedy*(Math.abs(dz)/Math.abs(dy));
        }
        else
        {
            Constant.Scz-=speedy*(Math.abs(dz)/Math.abs(dy));
        }
        Stx=tx;
        Sty=ty;
        Stz=tz;
        Supx=upx;
        Supy=upy;
        Supz=upz;
        MatrixState3D.setCamera(Constant.Scx,Constant.Scy,Constant.Scz,Stx,Sty,Stz,Supx,Supy,Supz);
//        if (Scy>=cy-4)
//        {
//            gameView.TopZ=-SideLengh*(gameView.ZDRStartRow)-SPAN;
//        }
        if (Scy>=cy)
        {
            upcrama=false;
            upcramaTime=0;
            Scx=cx;
            Scy=cy;
            Scz=cz;
        }
    }
    public float toSupy(float x1,float y1,float z1,float x2,float y2,float z2)
    {
         float r=(float) Math.sqrt ((x1-x2)*(x1-x2)+(z1-z2)*(z1-z2));
        float radian=(float) Math.atan((y1-y2)/(r));
        float upy=(float)(Math.tan(Math.PI/2-radian)*r);
        return upy;
    }
}
