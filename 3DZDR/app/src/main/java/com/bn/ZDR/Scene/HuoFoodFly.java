package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.ScreenScaleUtil;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.bombrange;
import static com.bn.ZDR.Util.Constant.drawFlyHuo;
import static com.bn.ZDR.Util.Constant.drawFlyShui;
import static com.bn.ZDR.Util.Constant.drawFlyTnt;
import static com.bn.ZDR.Util.Constant.huoNum;
import static com.bn.ZDR.Util.Constant.huo_current_Postion;
import static com.bn.ZDR.Util.Constant.huo_distinceX;
import static com.bn.ZDR.Util.Constant.huo_distinceY;
import static com.bn.ZDR.Util.Constant.huo_fly_foodId;
import static com.bn.ZDR.Util.Constant.shuiNum;
import static com.bn.ZDR.Util.Constant.shui_current_Position;
import static com.bn.ZDR.Util.Constant.shui_distinceX;
import static com.bn.ZDR.Util.Constant.shui_distinceY;
import static com.bn.ZDR.Util.Constant.tntNum;
import static com.bn.ZDR.Util.Constant.tnt_current_Position;
import static com.bn.ZDR.Util.Constant.tnt_distinceX;
import static com.bn.ZDR.Util.Constant.tnt_distinceY;

/**
 * Created by zhuanxu on 2017/7/23.
 */

public class HuoFoodFly
{
    MyGLSurfaceView myGLSurfaceView;
    public int huoflyTime;//tnt飞行刷帧
    public int timtControl;//时间控制
    public float speedy;
    float huoNumber_Position[];
    float roateAngle;
    public HuoFoodFly(MyGLSurfaceView myGLSurfaceView)
    {
        this.myGLSurfaceView=myGLSurfaceView;
        huoflyTime=0;
        timtControl=60;
        speedy=0.10f;
        roateAngle=15f;
        huoNumber_Position=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.Huo_start_x+Constant.Huo_kuandu*5/4*(huoNum+1), Constant.Huo_start_Y);
    }
    public void drawself()
    {
        if (huo_distinceY!=0&&huo_distinceX!=0)
        {
            MatrixState2D.pushMatrix();
            MatrixState2D.translate(huo_current_Postion[0]-huoflyTime*Math.abs(huo_distinceX/huo_distinceY)*speedy,huo_current_Postion[1]+huoflyTime*speedy,0.5f);
            MatrixState2D.rotate(roateAngle*huoflyTime,0,0,1);
            MatrixState2D.scale(0.05f,0.05f,0.05f);
            Constant.all2DTextureRectangle.drawSelf(huo_fly_foodId);
            MatrixState2D.popMatrix();
            huoflyTime++;
            if (shui_current_Position[1]+huoflyTime*speedy>=huoNumber_Position[1])
            {
                drawFlyHuo=false;
                huoflyTime=0;
                if (huoNum<6)
                {
                    huoNum++;
                    if (bombrange<huoNum+1)
                    {
                        bombrange++;
                    }
                    if (shuiNum>0)
                    {
                        shuiNum--;
                    }
                }

            }
        }
    }

}
