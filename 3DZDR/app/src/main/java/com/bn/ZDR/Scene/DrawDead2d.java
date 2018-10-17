package com.bn.ZDR.Scene;

import android.opengl.GLES30;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.LongestTime;
import static com.bn.ZDR.Util.Constant.SCREEN_HEIGHT;
import static com.bn.ZDR.Util.Constant.all2DTextureRectangle;
import static com.bn.ZDR.Util.Constant.drawDead2DTime;
import static com.bn.ZDR.Util.Constant.final_jibi_X;
import static com.bn.ZDR.Util.Constant.final_jinbi_background_X;
import static com.bn.ZDR.Util.Constant.final_jinbi_background_Y;
import static com.bn.ZDR.Util.Constant.final_jinbi_background_changdu;
import static com.bn.ZDR.Util.Constant.final_jinbi_background_kuandu;
import static com.bn.ZDR.Util.Constant.final_jinbi_changdu;
import static com.bn.ZDR.Util.Constant.final_score_X;
import static com.bn.ZDR.Util.Constant.final_score_Y;
import static com.bn.ZDR.Util.Constant.final_score_backgroundId;
import static com.bn.ZDR.Util.Constant.final_score_background_X;
import static com.bn.ZDR.Util.Constant.final_score_background_Y;
import static com.bn.ZDR.Util.Constant.final_score_background_changdu;
import static com.bn.ZDR.Util.Constant.final_score_background_kuandu;
import static com.bn.ZDR.Util.Constant.get_score;
import static com.bn.ZDR.Util.Constant.goldSum;
import static com.bn.ZDR.Util.Constant.hadDrawDead2D;
import static com.bn.ZDR.Util.Constant.jinbi_backgroundId;
import static com.bn.ZDR.Util.Constant.numberId;
import static com.bn.ZDR.Util.Constant.relivd2Id;
import static com.bn.ZDR.Util.Constant.relive3Id;
import static com.bn.ZDR.Util.Constant.reliveId;
import static com.bn.ZDR.Util.Constant.reliveTime;
import static com.bn.ZDR.Util.Constant.relive_X;
import static com.bn.ZDR.Util.Constant.relive_Y;
import static com.bn.ZDR.Util.Constant.relive_changdu;
import static com.bn.ZDR.Util.Constant.relive_kuandu;
import static com.bn.ZDR.Util.Constant.restartId;
import static com.bn.ZDR.Util.Constant.restart_X;
import static com.bn.ZDR.Util.Constant.restart_Y;
import static com.bn.ZDR.Util.Constant.restart_changdu;
import static com.bn.ZDR.Util.Constant.restart_kuandu;

/**
 * Created by zhuanxu on 2017/7/29.
 */

public class DrawDead2d
{
    MyGLSurfaceView myGLSurfaceView;
    GameView gameView;
    NumberForDraw fianl_jibi_number;//最终金币
    NumberForDraw fianl_score_number;//最终得分
    public DrawDead2d(MyGLSurfaceView myGLSurfaceView,GameView gameView)
    {
        this.myGLSurfaceView=myGLSurfaceView;
        this.gameView=gameView;
        fianl_jibi_number=new NumberForDraw(10, Constant.final_jinbi_changdu,Constant.final_jinbi_kuandu,myGLSurfaceView);
        fianl_score_number=new NumberForDraw(10,Constant.final_score_changdu,Constant.fianl_score_kuandu,myGLSurfaceView);
    }

    public void drawSelf()
    {

        GLES30.glEnable(GLES30.GL_BLEND);			//开启混合
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,	//设置混合因子
                GLES30.GL_ONE_MINUS_SRC_ALPHA);

        if (!hadDrawDead2D)
        {
            MatrixState2D.pushMatrix();//画最终金币背景
            all2DTextureRectangle.drawSelf(jinbi_backgroundId,-final_jinbi_background_X+(final_jinbi_background_X*2/LongestTime*drawDead2DTime),final_jinbi_background_Y,final_jinbi_background_changdu,final_jinbi_background_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//画最终得分背景
            all2DTextureRectangle.drawSelf(final_score_backgroundId,-final_score_background_X+(final_score_background_X*2/LongestTime*drawDead2DTime),final_score_background_Y,final_score_background_changdu,final_score_background_kuandu,1);
            MatrixState2D.popMatrix();

            if (reliveTime==0)
            {
                MatrixState2D.pushMatrix();//复活
                all2DTextureRectangle.drawSelf(reliveId,relive_X,SCREEN_HEIGHT*2-relive_Y-((SCREEN_HEIGHT-relive_Y)*2/LongestTime*drawDead2DTime),relive_changdu,relive_kuandu,1);
                MatrixState2D.popMatrix();
            }
            else if (reliveTime==1)
            {
                MatrixState2D.pushMatrix();//复活
                all2DTextureRectangle.drawSelf(relivd2Id,relive_X,SCREEN_HEIGHT*2-relive_Y-((SCREEN_HEIGHT-relive_Y)*2/LongestTime*drawDead2DTime),relive_changdu,relive_kuandu,1);
                MatrixState2D.popMatrix();
            }
            else if (reliveTime>=2)
            {
                MatrixState2D.pushMatrix();//复活
                all2DTextureRectangle.drawSelf(relive3Id,relive_X,SCREEN_HEIGHT*2-relive_Y-((SCREEN_HEIGHT-relive_Y)*2/LongestTime*drawDead2DTime),relive_changdu,relive_kuandu,1);
                MatrixState2D.popMatrix();
            }


            MatrixState2D.pushMatrix();//画重新开始
            all2DTextureRectangle.drawSelf(restartId,restart_X,SCREEN_HEIGHT*2-restart_Y-((SCREEN_HEIGHT-restart_Y)*2/LongestTime*drawDead2DTime),restart_changdu,restart_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//最终金币
            fianl_jibi_number.drawself(goldSum+"",numberId,-(Constant.final_jibi_X-((goldSum+"").length()-1)*0)+((Constant.final_jibi_X-((goldSum+"").length()-1)*0)*2/LongestTime*drawDead2DTime),Constant.final_jibi_Y);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//最终得分
            fianl_score_number.drawself(get_score+"",numberId,-Constant.final_score_X+(final_score_X*2/LongestTime*drawDead2DTime),final_score_Y);
            MatrixState2D.popMatrix();
            if (drawDead2DTime>LongestTime)
            {
                hadDrawDead2D=true;
            }
            drawDead2DTime++;
        }
        else
        {
            MatrixState2D.pushMatrix();//画最终金币背景
            all2DTextureRectangle.drawSelf(jinbi_backgroundId,final_jinbi_background_X,final_jinbi_background_Y,final_jinbi_background_changdu,final_jinbi_background_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//画最终得分背景
            all2DTextureRectangle.drawSelf(final_score_backgroundId,final_score_background_X,final_score_background_Y,final_score_background_changdu,final_score_background_kuandu,1);
            MatrixState2D.popMatrix();

            if (reliveTime==0)
            {
                MatrixState2D.pushMatrix();//复活
                all2DTextureRectangle.drawSelf(reliveId,relive_X,relive_Y,relive_changdu,relive_kuandu,1);
                MatrixState2D.popMatrix();
            }
            else if (reliveTime==1)
            {
                MatrixState2D.pushMatrix();//复活
                all2DTextureRectangle.drawSelf(relivd2Id,relive_X,relive_Y,relive_changdu,relive_kuandu,1);
                MatrixState2D.popMatrix();
            }
            else if (reliveTime>=2)
            {
                MatrixState2D.pushMatrix();//复活
                all2DTextureRectangle.drawSelf(relive3Id,relive_X,relive_Y,relive_changdu,relive_kuandu,1);
                MatrixState2D.popMatrix();
            }


            MatrixState2D.pushMatrix();//画重新开始
            all2DTextureRectangle.drawSelf(restartId,restart_X,restart_Y,restart_changdu,restart_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//最终金币
            fianl_jibi_number.drawself(goldSum+"",numberId,(Constant.final_jibi_X-((goldSum+"").length()-1)*0),Constant.final_jibi_Y);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//最终得分
            fianl_score_number.drawself(get_score+"",numberId,Constant.final_score_X,final_score_Y);
            MatrixState2D.popMatrix();
        }


        GLES30.glDisable(GLES30.GL_BLEND);
    }


}
