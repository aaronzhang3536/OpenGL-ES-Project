package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.ScreenScaleUtil;
import com.bn.ZDR.View.MyGLSurfaceView;


import static com.bn.ZDR.Util.Constant.bombrange;
import static com.bn.ZDR.Util.Constant.drawFlyShui;
import static com.bn.ZDR.Util.Constant.drawFlyTnt;
import static com.bn.ZDR.Util.Constant.huoNum;
import static com.bn.ZDR.Util.Constant.shuiNum;
import static com.bn.ZDR.Util.Constant.shui_current_Position;
import static com.bn.ZDR.Util.Constant.shui_distinceX;
import static com.bn.ZDR.Util.Constant.shui_distinceY;
import static com.bn.ZDR.Util.Constant.shui_fly_foodId;

/**
 * Created by zhuanxu on 2017/7/23.
 */

public class Shui_FoodFly
{
    MyGLSurfaceView myGLSurfaceView;
    public int shuiflyTime;//tnt飞行刷帧
    public int timtControl;//时间控制
    public float speedy;
    float shuiNumber_Position[];
    float roateAngle;
    public Shui_FoodFly(MyGLSurfaceView myGLSurfaceView)
    {
        this.myGLSurfaceView=myGLSurfaceView;
        shuiflyTime=0;
        timtControl=60;
        speedy=0.10f;
        roateAngle=15f;
        shuiNumber_Position=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.Shui_start_X+Constant.Huo_kuandu*5/4*(shuiNum+1), Constant.Shui_start_Y);
    }
    public void drawself()
    {
        if (shui_distinceY!=0&&shui_distinceX!=0)
        {
            MatrixState2D.pushMatrix();
            MatrixState2D.translate(shui_current_Position[0]-shuiflyTime*Math.abs(shui_distinceX/shui_distinceY)*speedy,shui_current_Position[1]+shuiflyTime*speedy,0.5f);
            MatrixState2D.rotate(roateAngle*shuiflyTime,0,0,1);
            MatrixState2D.scale(0.05f,0.05f,0.05f);
            Constant.all2DTextureRectangle.drawSelf(shui_fly_foodId);
            MatrixState2D.popMatrix();
            shuiflyTime++;
            if (shui_current_Position[1]+shuiflyTime*speedy>=shuiNumber_Position[1])
            {
                drawFlyShui=false;
                shuiflyTime=0;
                if (shuiNum<4)
                {
                    shuiNum++;
                    if (huoNum>0)
                    {
                        huoNum--;
                        if (bombrange>3)
                        {
                            bombrange--;
                        }
                    }
                }

            }
        }
    }

}
