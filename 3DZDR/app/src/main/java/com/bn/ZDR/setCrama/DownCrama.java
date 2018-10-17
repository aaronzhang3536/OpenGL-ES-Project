package com.bn.ZDR.setCrama;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import java.security.cert.CertificateParsingException;

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
import static com.bn.ZDR.Util.Constant.downcrama;
import static com.bn.ZDR.Util.Constant.downcramaTime;
import static com.bn.ZDR.Util.Constant.roatecrama;

/**
 * Created by zhuanxu on 2017/8/28.
 */

public class DownCrama//人和摄像机相差5.5
{
    GameView gameView;
    MyGLSurfaceView myGLSurfaceView;

    float dx;//现在相机到目标相机的位置的距离
    float dy;
    float dz;
    float dtx;//炸弹人x与摄像机目标点的位置
    float cx;	//摄像机位置x
    float cy;  //摄像机位置y
    float cz;  //摄像机位置z
    float tx;   //摄像机目标点x
    float ty;     //摄像机目标点y
    float tz;   //摄像机目标点z
    float upx=-0.0f;  //摄像机UP向量X分量
    float upy=5.0f;      //摄像机UP向量Y分量
    float upz=-1.0f;  //摄像机UP向量Z分量
    float speedy=0.5f;
    float rz=6f;//目标相机z到目标点Z的距离
    float yh=4f;//目标相机高度
    float r;
    public DownCrama(GameView gameView,MyGLSurfaceView myGLSurfaceView)
    {
        this.gameView=gameView;
        this.myGLSurfaceView=myGLSurfaceView;
    }
    public void caculateXYZDistince()
    {
//        cy=(Scy)/(Scz-(gameView.ZDRZ))*rz;
//        cz=gameView.ZDRZ+rz;

//        dy=cy-Scy;
//        dz=cz-Scz;
        cy=yh;
        dy=cy-Scy;
        dz=gameView.ZDRZ+(rz+UpCrama.cramatZtoRenZ)-Scz;
        dx=gameView.ZDRX-Scx;
        dtx=gameView.ZDRX-Stx;
        speedy=Math.abs(dy)/10;
        upy=toSupy(Scx,Scy,Scz,cx,1f,cz);
    }
    public void downCrama()
    {
        Scy-=speedy;
        if (dz>0)
        {
            Scz+=speedy*(Math.abs(dz)/Math.abs(dy));
        }
        else
        {
            Scz-=speedy*(Math.abs(dz)/Math.abs(dy));
        }
        if (dx>0)
        {
            Scx+=speedy*(Math.abs(dx)/Math.abs(dy));
           // Stx+=speedy*(Math.abs(dx)/Math.abs(dy));
        }
        else
        {
            Scx-=speedy*(Math.abs(dx)/Math.abs(dy));
            //Stx-=speedy*(Math.abs(dx)/Math.abs(dy));
        }
        if (dtx>0)
        {
            Stx+=speedy*(Math.abs(dtx)/Math.abs(dy));
        }
        else
        {
           Stx-=speedy*(Math.abs(dtx)/Math.abs(dy));
        }

        //Stx=gameView.ZDRX;
        Sty=1f;
        Stz=gameView.ZDRZ+UpCrama.cramatZtoRenZ;

        Supx=upx;//gameView.ZDRX-Scx;
        Supy=upy;
        Supz=upz;//gameView.ZDRZ-Scz;
        MatrixState3D.setCamera(Scx,Scy,Scz,Stx,Sty,Stz,Supx,Supy,Supz);
       // System.out.println("y坐标"+Scy+"    Scz="+Scz+ "   Scx="+Scx+"   Stx="+Stx+"  Sty="+Sty+"  Stz="+(Stz)+ " upx="+upx+"  upy="+upy+"   upz="+upz+"  cy="+cy);
        if (Scy<=cy)
        {
            downcrama=false;
            roatecrama=true;
            downcramaTime=0;
        }
    }
    public float toSupy(float x1,float y1,float z1,float x2,float y2,float z2)
    {
       // float y= (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2)-r*r;
        float radian=(float) Math.atan((y1-y2)/(r));
        float upy=(float)(Math.tan(Math.PI/2-radian)*r);
        return upy;
    }
}
