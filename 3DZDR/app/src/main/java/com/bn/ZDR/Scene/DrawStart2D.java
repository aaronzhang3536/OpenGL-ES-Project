package com.bn.ZDR.Scene;

import android.opengl.GLES30;

import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.MapWidth;
import static com.bn.ZDR.Util.Constant.*;

import static com.bn.ZDR.Util.UserInfoUtil.GetFisrtScore;
import static com.bn.ZDR.Util.UserInfoUtil.GetPinzhi;
import static com.bn.ZDR.Util.UserInfoUtil.GetSecondScore;
import static com.bn.ZDR.Util.UserInfoUtil.GetThirdScore;
import static com.bn.ZDR.Util.UserInfoUtil.GetYinxiao;
import static com.bn.ZDR.Util.UserInfoUtil.GetYinyue;

/**
 * Created by zhuanxu on 2017/8/26.
 */

public class DrawStart2D
{
    GameView gameView;
    MyGLSurfaceView myGLSurfaceView;
    NumberForDraw score;
    public DrawStart2D(GameView gameView ,MyGLSurfaceView myGLSurfaceView)
    {
        this.gameView=gameView;
        this.myGLSurfaceView=myGLSurfaceView;
        score=new NumberForDraw(10,chartScore_changdu,chartScore_kuandu,myGLSurfaceView);
    }

    public void drawSelf()
    {
        GLES30.glEnable(GLES30.GL_BLEND);			//开启混合
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,	//设置混合因子
                GLES30.GL_ONE_MINUS_SRC_ALPHA);


        if(currPage==SetPage)//设置界面
        {

            {
                MatrixState2D.pushMatrix();//设置界面标题
                all2DTextureRectangle.drawSelf(SetPageTitleTextureId,setPageTitle_x,setPageTitle_y,setPageTitle_changdu,setPageTitle_kuandu,1);
                MatrixState2D.popMatrix();

                MatrixState2D.pushMatrix();//设置界面返回
                all2DTextureRectangle.drawSelf(SetPageBackTextureId,setPageBack_x,setPageBack_y,setPageBack_changdu,setPageBack_kuandu,1);
                MatrixState2D.popMatrix();

                MatrixState2D.pushMatrix();//设置界面面板
                MatrixState2D.translate(0,0,-0.1f);
                all2DTextureRectangle.drawSelf(SetPagePanelTextureId,setPagePanel_x,setPagePanel_y,setPagePanel_changdu,setPagePanel_kuandu,1);
                MatrixState2D.popMatrix();

                if(GetPinzhi())
                {
                    MatrixState2D.pushMatrix();//设置品质高
                    all2DTextureRectangle.drawSelf(SetPagePanelHighTextureId,setPagePinzhi_x,setPagePinzhi_y,setPagePinzhi_changdu,setPagePinzhi_kuandu,1);
                    MatrixState2D.popMatrix();
                }
                else
                {
                    MatrixState2D.pushMatrix();//设置品质低
                    all2DTextureRectangle.drawSelf(SetPagePanelLowTextureId,setPagePinzhi_x,setPagePinzhi_y,setPagePinzhi_changdu,setPagePinzhi_kuandu,1);
                    MatrixState2D.popMatrix();
                }

                if(GetYinyue())
                {
                    MatrixState2D.pushMatrix();//设置音乐对勾
                    all2DTextureRectangle.drawSelf(SetPagePanelYesTextureId,setPageUpYes_x,setPageUpYes_y,setPageUpYes_changdu,setPageUpYes_kuandu,1);
                    MatrixState2D.popMatrix();
                }

                if(GetYinxiao())
                {
                    MatrixState2D.pushMatrix();//设置音效对勾
                    all2DTextureRectangle.drawSelf(SetPagePanelYesTextureId,setPageDownYes_x,setPageDownYes_y,setPageDownYes_changdu,setPageDownYes_kuandu,1);
                    MatrixState2D.popMatrix();
                }
            }

        }

        if(currPage==ChartPage)//排行榜界面
        {
            {
                MatrixState2D.pushMatrix();//排行榜界面标题
                all2DTextureRectangle.drawSelf(ChartTitleTextureId,chartPageTitle_x,chartPageTitle_y,chartPageTitle_changdu,chartPageTitle_kuandu,1);
                MatrixState2D.popMatrix();

                MatrixState2D.pushMatrix();//排行榜界面返回
                all2DTextureRectangle.drawSelf(SetPageBackTextureId,setPageBack_x,setPageBack_y,setPageBack_changdu,setPageBack_kuandu,1);
                MatrixState2D.popMatrix();

                MatrixState2D.pushMatrix();//排行榜界面面板
                MatrixState2D.translate(0,0,-0.1f);
                all2DTextureRectangle.drawSelf(ChartPanelTextureId,chartPagePanel_x,chartPagePanel_y,chartPagePanel_changdu,chartPagePanel_kuandu,1);
                MatrixState2D.popMatrix();

                MatrixState2D.pushMatrix();//排行榜界面冠军分数
                score.drawself(GetFisrtScore()+"",RunEatGoldNumberTextureId,FirstPlace_x,FirstPlace_y);
                MatrixState2D.popMatrix();

                MatrixState2D.pushMatrix();//排行榜界面亚军分数
                score.drawself(GetSecondScore()+"",RunEatGoldNumberTextureId,SecondPlace_x,SecondPlace_y);
                MatrixState2D.popMatrix();

                MatrixState2D.pushMatrix();//排行榜界面季军分数
                score.drawself(GetThirdScore()+"",RunEatGoldNumberTextureId,ThirdPlace_x,ThirdPlace_y);
                MatrixState2D.popMatrix();

            }

        }

        if(currPage==OpenPage)//开场界面
        {


            MatrixState2D.pushMatrix();//背景
            all2DTextureRectangle.drawSelf(beijingId,Tiny_beijing_X,Tiny_beijing_Y,Tiny_beijing_changdu,Tiny_beijing_kuandu,1);
            MatrixState2D.popMatrix();



            MatrixState2D.pushMatrix();//排行榜
            all2DTextureRectangle.drawSelf(chartId,chart_X,chart_Y,chart_changdu,chart_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//设置
            all2DTextureRectangle.drawSelf(setId,set_X,set_Y,set_changdu,set_kuandu,1);
            MatrixState2D.popMatrix();


            if (drawShouOrShouzhi==1)
            {
                MatrixState2D.pushMatrix();//手
                all2DTextureRectangle.drawSelf(shouId,shou_X,shou_Y,shou_changdu,shou_kuandu,1);
                MatrixState2D.popMatrix();
            }
            else if (drawShouOrShouzhi==2)
            {
                MatrixState2D.pushMatrix();//手指
                all2DTextureRectangle.drawSelf(shouzhiId,shou_X,shou_Y,shouzhi_changdu,shouzhi_kuandu,1);
                MatrixState2D.popMatrix();

                MatrixState2D.pushMatrix();//波纹
                all2DTextureRectangle.drawSelf(bowenId,shou_X-shouzhi_kuandu*0.2f,shou_Y-shouzhi_kuandu*0.4f,shouzhi_changdu*2,shouzhi_changdu*2,1);
                MatrixState2D.popMatrix();
            }

        }


        GLES30.glDisable(GLES30.GL_BLEND);
    }
}
