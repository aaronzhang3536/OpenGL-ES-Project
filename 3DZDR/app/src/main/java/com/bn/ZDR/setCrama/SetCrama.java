package com.bn.ZDR.setCrama;

import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.dietoaliveUpCrama;
import static com.bn.ZDR.Util.Constant.dietoaliveUpCramaTime;
import static com.bn.ZDR.Util.Constant.downcrama;
import static com.bn.ZDR.Util.Constant.downcramaTime;
import static com.bn.ZDR.Util.Constant.recovercrama;
import static com.bn.ZDR.Util.Constant.recovercramaTime;
import static com.bn.ZDR.Util.Constant.roatecrama;
import static com.bn.ZDR.Util.Constant.roatecramaTime;
import static com.bn.ZDR.Util.Constant.upcrama;
import static com.bn.ZDR.Util.Constant.upcramaTime;

/**
 * Created by zhuanxu on 2017/8/27.
 */

public class SetCrama
{
    GameView gameView;
    MyGLSurfaceView myGLSurfaceView;

    UpCrama upCrama;//升高相机
    DownCrama downCrama;//降低相机
    RoateCrama roateCrama;//转动相机
    DieToAliveUpCrama dieToAliveUpCrama;//再次升高照相机
    RecoverCrama recoverCrama;//复原摄像机
    public SetCrama(GameView gameView, MyGLSurfaceView myGLSurfaceView)
    {
        this.gameView=gameView;
        this.myGLSurfaceView=myGLSurfaceView;

        upCrama=new UpCrama(gameView);
        downCrama=new DownCrama(gameView,myGLSurfaceView);
        roateCrama=new RoateCrama(gameView,myGLSurfaceView);
        dieToAliveUpCrama=new DieToAliveUpCrama(gameView);
        recoverCrama=new RecoverCrama(gameView);
    }

    public void setCrama()
    {
        //升高相机
        if (upcrama)
        {
            if (upcramaTime<1)
            {
                upCrama.caculateXYZDistince();
                upcramaTime++;
            }
            upCrama.upCrama();
        }

        //降低相机
        if (downcrama)
        {
            if (downcramaTime<1)
            {
                downCrama.caculateXYZDistince();
                downcramaTime++;
            }
            downCrama.downCrama();
        }

        //转相机
        if (roatecrama)
        {
            if (roatecramaTime<1)
            {
                roateCrama.getCenter();
               // System.out.println("scz="+Scz+"    ZDRZ="+gameView.ZDRZ+"  Scy"+Scy);
                roatecramaTime++;
            }
            roateCrama.roateCrama();
        }

        //再次升高摄像机
        if (dietoaliveUpCrama)
        {
            if (dietoaliveUpCramaTime<1)
            {
                dieToAliveUpCrama.caculateXYZDistince();
                dietoaliveUpCramaTime++;
            }
            dieToAliveUpCrama.upCrama();
        }

        //复原摄像机
        if (recovercrama)
        {
            if (recovercramaTime <1)
            {
                recoverCrama.caculateXYZDistince();
                recovercramaTime++;
            }
            recoverCrama.upCrama();

        }
    }
}
