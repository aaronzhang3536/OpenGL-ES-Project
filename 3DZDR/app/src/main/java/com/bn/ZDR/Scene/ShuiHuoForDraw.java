package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.TextureRectangle2D;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.all2DTextureRectangle;
import static com.bn.ZDR.Util.Constant.b_huoId;
import static com.bn.ZDR.Util.Constant.b_shuiId;
import static com.bn.ZDR.Util.Constant.shui_backgroundId;
import static com.bn.ZDR.Util.Constant.w_huoId;
import static com.bn.ZDR.Util.Constant.w_shuiId;

/**
 * Created by zhuanxu on 2017/7/21.
 */

public class ShuiHuoForDraw
{
    MyGLSurfaceView myGLSurfaceView;
    float span;//每个间距
    public ShuiHuoForDraw(MyGLSurfaceView myGLSurfaceView)
    {
        this.myGLSurfaceView=myGLSurfaceView;
        span=Constant.Huo_kuandu*5/4;
    }

    public void drawself(int huoNum,int shuiNum)
    {
        drawHuo(huoNum);
        drawShui(shuiNum);
    }

    public void drawHuo(int huoNum)
    {
        for (int i=0;i<6;i++)
        {
            if (i<huoNum)
            {
                MatrixState2D.pushMatrix();
                MatrixState2D.translate(0,0,0.5f);
                all2DTextureRectangle.drawSelf(w_huoId,Constant.Huo_start_x+i*span,Constant.Huo_start_Y,Constant.Huo_changdu,Constant.Huo_kuandu,1);
                MatrixState2D.popMatrix();
            }
            else
            {
                MatrixState2D.pushMatrix();
                MatrixState2D.translate(0,0,0.5f);
                all2DTextureRectangle.drawSelf(b_huoId,Constant.Huo_start_x+i*span,Constant.Huo_start_Y,Constant.Huo_changdu,Constant.Huo_kuandu,1);
                MatrixState2D.popMatrix();
            }
        }
    }

    public void drawShui(int shuiNum)
    {
        for (int i=0;i<4;i++)
        {
            if (i<shuiNum)
            {
                MatrixState2D.pushMatrix();
                MatrixState2D.translate(0,0,0.5f);
                all2DTextureRectangle.drawSelf(w_shuiId,Constant.Shui_start_X+i*span,Constant.Shui_start_Y,Constant.Shui_changdu,Constant.Shui_kuandu,1);
                MatrixState2D.popMatrix();
            }
            else
            {
                MatrixState2D.pushMatrix();
                MatrixState2D.translate(0,0,0.5f);
                all2DTextureRectangle.drawSelf(b_shuiId,Constant.Shui_start_X+i*span,Constant.Shui_start_Y,Constant.Shui_changdu,Constant.Shui_kuandu,1);
                MatrixState2D.popMatrix();
            }
        }
    }
}
