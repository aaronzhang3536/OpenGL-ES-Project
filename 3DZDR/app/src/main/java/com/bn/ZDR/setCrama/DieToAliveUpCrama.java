package com.bn.ZDR.setCrama;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;

import static com.bn.ZDR.Util.Constant.Scx;
import static com.bn.ZDR.Util.Constant.Scy;
import static com.bn.ZDR.Util.Constant.Scz;
import static com.bn.ZDR.Util.Constant.Stx;
import static com.bn.ZDR.Util.Constant.Sty;
import static com.bn.ZDR.Util.Constant.Stz;
import static com.bn.ZDR.Util.Constant.Supx;
import static com.bn.ZDR.Util.Constant.Supy;
import static com.bn.ZDR.Util.Constant.Supz;
import static com.bn.ZDR.Util.Constant.ZDRtxTimes;
import static com.bn.ZDR.Util.Constant.dietoaliveUpCrama;
import static com.bn.ZDR.Util.Constant.dietoaliveUpCramaTime;

/**
 * Created by zhuanxu on 2017/8/29.
 */

public class DieToAliveUpCrama
{
    GameView gameView;
    float dx;//现在相机到目标相机位置的距离
    float dy;
    float dz;
    public static float cx=7f;	//摄像机位置x
    public static float cy=19f;  //摄像机位置y
    public static float cz;  //摄像机位置z
    public static float tx;   //摄像机目标点x
    public static float ty;     //摄像机目标点y
    public static float tz;   //摄像机目标点z
    public static float upx=-0.7f;  //摄像机UP向量X分量
    public static float upy=40f;       //摄像机UP向量Y分量
    public static float upz=-1.0f;  //摄像机UP向量Z分量

    float speedy=1.4f;
    public DieToAliveUpCrama(GameView gameView)
    {
        this.gameView=gameView;
    }
    public void caculateXYZDistince()
    {
        cx=cx;
        cz=(gameView.ZDRZ+UpCrama.cramatZtoRenZ)+UpCrama.cramaCZtocramaTZ;
        tx=(gameView.ZDRX-5.5f)/ZDRtxTimes+5.5f;
        ty=0f;
        tz=gameView.ZDRZ+UpCrama.cramatZtoRenZ;
        dx=cx- Constant.Scx;
        dy=cy-Constant.Scy;
        dz=cz-Constant.Scz;
        speedy=Math.abs(dy)/5;
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
//        Supx=gameView.ZDRX-Scx;
//        Supz=gameView.ZDRZ-Scz;
//        Supy=toSupy(Scx,Scy,Scz,cx,0f,cz);
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
            //System.out.println("scz="+Scz+" ga="+gameView.ZDRZ+"   cz="+cz);
            dietoaliveUpCrama=false;
            dietoaliveUpCramaTime=0;
            Supx=upx;
            Supy=upy;
            Supz=upz;
            Scy=cy;
            Scx=cx;
        }
    }
    public float toSupy(float x1,float y1,float z1,float x2,float y2,float z2)
    {
        // float y= (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2)-r*r;
        float r=Math.abs((Scz-gameView.ZDRZ)*(Scz-gameView.ZDRZ)+(Scx-gameView.ZDRX)*(Scx-gameView.ZDRX));
        float radian=(float) Math.atan((y1-y2)/(r));
        float upy=(float)(Math.tan(Math.PI/2-radian)*r);
        return upy;
    }
}
