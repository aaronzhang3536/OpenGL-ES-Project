package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.ScreenScaleUtil;
import com.bn.ZDR.View.MyGLSurfaceView;


import static com.bn.ZDR.Util.Constant.drawFlyTnt;
import static com.bn.ZDR.Util.Constant.tntBombId;
import static com.bn.ZDR.Util.Constant.tntNum;
import static com.bn.ZDR.Util.Constant.tnt_current_Position;
import static com.bn.ZDR.Util.Constant.tnt_distinceX;
import static com.bn.ZDR.Util.Constant.tnt_distinceY;
import static com.bn.ZDR.Util.Constant.tnt_fly_foodId;
import static com.bn.ZDR.Util.Constant.tnt_shui_huo_food;

/**
 * Created by zhuanxu on 2017/7/23.
 */

public class TNTFoodFly
{
    MyGLSurfaceView myGLSurfaceView;
    public int tntflyTime;//tnt飞行刷帧
    public int timtControl;//时间控制
    public float speedx;
    float tntNumber_Position[];
    float roateAngle;
    public TNTFoodFly(MyGLSurfaceView myGLSurfaceView)
    {
        this.myGLSurfaceView=myGLSurfaceView;
        tntflyTime=0;
        timtControl=60;
        speedx=0.10f;
        roateAngle=15f;
        tntNumber_Position= ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.tnt_number_X,Constant.tnt_number_Y);
    }
    public void drawself()
    {
        if (tnt_distinceY!=0&&tnt_distinceX!=0)
        {
            MatrixState2D.pushMatrix();
            MatrixState2D.translate(tnt_current_Position[0]+tntflyTime*speedx,tnt_current_Position[1]-tntflyTime*Math.abs(tnt_distinceY/tnt_distinceX)*speedx,0.5f);
            MatrixState2D.rotate(roateAngle*tntflyTime,0,0,1);
            MatrixState2D.scale(0.05f,0.05f,0.05f);
            Constant.all2DTextureRectangle.drawSelf(tnt_fly_foodId);

            MatrixState2D.popMatrix();
            tntflyTime++;
            if (tnt_current_Position[0]+tntflyTime*speedx>=tntNumber_Position[0])
            {
                drawFlyTnt=false;
                tntflyTime=0;
                tntNum++;
            }
        }
    }

}
