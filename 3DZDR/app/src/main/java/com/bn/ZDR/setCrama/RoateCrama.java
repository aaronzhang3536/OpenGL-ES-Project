package com.bn.ZDR.setCrama;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.MapWidth;
import static com.bn.ZDR.Util.Constant.Scx;
import static com.bn.ZDR.Util.Constant.Scy;
import static com.bn.ZDR.Util.Constant.Scz;
import static com.bn.ZDR.Util.Constant.Stx;
import static com.bn.ZDR.Util.Constant.Sty;
import static com.bn.ZDR.Util.Constant.Stz;
import static com.bn.ZDR.Util.Constant.Supx;
import static com.bn.ZDR.Util.Constant.Supy;
import static com.bn.ZDR.Util.Constant.Supz;

/**
 * Created by zhuanxu on 2017/8/28.
 */

public class RoateCrama//圆半径为5.5
{
    GameView gameView;
    MyGLSurfaceView myGLSurfaceView;
    float cx;//目标相机的位置
    float cz;
    float r=6f;//转动半径 目标相机z到目标点Z的距离与downCrama中的rz一样
    float roateAngle=270;
    float upx=0f;  //摄像机UP向量X分量
    float upy=0f;      //摄像机UP向量Y分量
    float upz=0f;  //摄像机UP向量Z分量
    int time=0;//刷帧
    int roateWait=1;
    float yMax;
    float yMin;
    float span=0.5f;
    boolean isup=true;
    public RoateCrama(GameView gameView, MyGLSurfaceView myGLSurfaceView)
    {
        this.gameView=gameView;
        this.myGLSurfaceView=myGLSurfaceView;
    }
    public void getCenter()
    {
        cx=gameView.ZDRX;
        cz=gameView.ZDRZ-1.5f;
        Scy=4.0f;
        upy=toSupy(Scx,Scy,Scz,cx,1f,cz);
        roateAngle=270;
        yMax=Scy+span;
        yMin=Scy-span;
    }
    public void roateCrama()
    {
        //System.out.println("cx="+cx+"  cz="+cz+"   ZDRX="+gameView.ZDRX+"    ZDRZ="+gameView.ZDRZ+"  Scx="+Scx+"   Scz="+Scz);
        Constant.Scx=(float) (cx+r*Math.cos(toRadian(roateAngle)));
        Constant.Scz=(float)(cz-r*Math.sin(toRadian(roateAngle)));
        if (isup)
        {
           if (Scy<=yMax)
           {
               Scy+=span/10;
           }
            else
           {
               isup=false;
           }
        }
        else
        {
            if (Scy>=yMin)
            {
                Scy-=span/10;
            }
            else
            {
                isup=true;
            }
        }
        Sty=1f;
        Stz=gameView.ZDRZ-1.5f;
        Supx=gameView.ZDRX-Scx;
        Supy=upy;
        Supz=gameView.ZDRZ-Scz;
        MatrixState3D.setCamera(Scx,Scy,Scz,Stx,Sty,Stz,Supx,Supy,Supz);
        if (time%roateWait==0)
        {
            roateAngle+=2;
            if (roateAngle>=270+360)
            {
                roateAngle=270;
            }
        }
        time++;
    }

    public float toRadian(float degree)
    {
        return (float)( (degree/360)*Math.PI*2);
    }
    public float toSupy(float x1,float y1,float z1,float x2,float y2,float z2)
    {
        // float y= (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2)-r*r;
        float radian=(float) Math.atan((y1-y2)/(r));
        float upy=(float)(Math.tan(Math.PI/2-radian)*r);
        return upy;
    }


}
