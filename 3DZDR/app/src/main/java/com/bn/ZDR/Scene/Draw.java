package com.bn.ZDR.Scene;
import android.opengl.GLES30;
import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.UserInfoUtil;
import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.*;

/**
 * Created by zhuanxu on 2017/7/9.
 */

public class Draw
{
    MyGLSurfaceView mySurfaceView;
    GameView gameView;

    NumberForDraw moneyNumber;//画金币
    NumberForDraw tntNumber;//画tnt数量
    NumberForDraw runEatGoldNumber;//金币连吃效果绘制
    NumberForDraw getScoreNumber;//得分数量
    NumberForDraw bestNumber;//最好得分

    ShuiHuoForDraw shui_huoForDraw;//画水火图
    TNTFoodFly tnt_foodFly;
    Shui_FoodFly shui_foodFly;
    HuoFoodFly huo_foodFly;


    public Draw(MyGLSurfaceView mySurfaceView,GameView gameView)
    {
        this.mySurfaceView=mySurfaceView;
        this.gameView=gameView;
        moneyNumber=new NumberForDraw(10,Constant.Qianbi_number_changdu,Qianbi_number_kuandu,mySurfaceView);
        tntNumber=new NumberForDraw(10,Constant.tnt_number_changdu,tnt_number_kuandu,mySurfaceView);
        if(Constant.SCREEN_WIDTH<=Constant.SCREEN_HEIGHT)
        {
            runEatGoldNumber=new NumberForDraw(10,Constant.SCREEN_WIDTH/10,Constant.SCREEN_WIDTH/10,mySurfaceView);
        }
        else
        {
            runEatGoldNumber=new NumberForDraw(10,Constant.SCREEN_HEIGHT/10,Constant.SCREEN_HEIGHT/10,mySurfaceView);
        }
        getScoreNumber=new NumberForDraw(10,Constant.Get_score_changdu,Constant.Get_score_kuandu,mySurfaceView);
        bestNumber=new NumberForDraw(10,Constant.best_number_changdu,Constant.best_number_kuandu,mySurfaceView);
        shui_huoForDraw=new ShuiHuoForDraw(mySurfaceView);
        tnt_foodFly=new TNTFoodFly(mySurfaceView);
        shui_foodFly=new Shui_FoodFly(mySurfaceView);
        huo_foodFly=new HuoFoodFly(mySurfaceView);

    }
    public void drawSelf()
    {
        GLES30.glEnable(GLES30.GL_BLEND);			//开启混合
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,	//设置混合因子
                GLES30.GL_ONE_MINUS_SRC_ALPHA);

        //飞行过程
        if (!hadDraw)
        {
            MatrixState2D.pushMatrix();//遥感大环
            all2DTextureRectangle.drawSelf(jk_bigId,-Constant.JK_big_yuanxinX+(Constant.JK_big_yuanxinX*2/LongestTime),Constant.JK_big_yuanxinY,Constant.JK_big_radius,Constant.JK_big_radius,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//遥感小环
            MatrixState2D.translate(0,0,0.5f);
            all2DTextureRectangle.drawSelf(jk_smallId, -Constant.JK_small_yuanxin_Current_X+(Constant.JK_small_yuanxin_Current_X*2/LongestTime), Constant.Jk_small_yuanxin_Current_Y, Constant.Jk_small_radius, Constant.Jk_small_radius,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//炸弹
            all2DTextureRectangle.drawSelf(ButtonDropBombTextureId,2*SCREEN_WIDTH-commonBombX-((SCREEN_WIDTH-commonBombX)*2/LongestTime*drawTime),commonBombY,commonBombChangdu,commonBombKuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//TNT
            all2DTextureRectangle.drawSelf(ButtonDropTntTextureId,2*SCREEN_WIDTH-tNtBombX-((SCREEN_WIDTH-tNtBombX)*2/LongestTime*drawTime),tNtBombY,tNtBombChangdu,tNtBombKuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//金币背景
            all2DTextureRectangle.drawSelf(jinbi_backgroundId,-Qianbi_background_X+(Qianbi_background_X*2/LongestTime*drawTime),Qianbi_background_Y,Qianbi_background_changdu,Qianbi_background_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//火背景
            all2DTextureRectangle.drawSelf(huo_backgroundId2,-Huo_background_X+(Huo_background_X*2/LongestTime*drawTime),Huo_background_Y,Huo_background_changdu,Huo_background_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//水背景
            all2DTextureRectangle.drawSelf(shui_backgroundId2,-Shui_background_X+(Shui_background_X*2/LongestTime*drawTime),Shui_background_Y,Shui_background_changdu,Shui_background_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//画金币
            moneyNumber.drawself(goldSum +"", numberId,-Constant.Qianbi_number_x2+(Qianbi_number_X1*2/LongestTime*drawTime),Qianbi_number_Y);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//得分
            getScoreNumber.drawself(Constant.get_score+"",Constant.numberId,(Constant.Get_score_X-((get_score+"").length()-1)*25),-Get_score_Y+(Get_score_Y*2/LongestTime*drawTime));
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//奖杯
            all2DTextureRectangle.drawSelf(cupId,Constant.Cup_x,-Constant.Cup_Y+(Cup_Y*2/LongestTime*drawTime),Constant.Cup_changdu,Constant.Cup_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//最好
            all2DTextureRectangle.drawSelf(bestId,best_X,-best_Y+(best_Y*2/LongestTime*drawTime),best_changdu,best_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//最高得分
            bestNumber.drawself(best_score+"",numberId,best_number_X,-best_number_Y+(best_number_Y*2/LongestTime*drawTime));
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//画暂停
            all2DTextureRectangle.drawSelf(pauseId,SCREEN_WIDTH*2-pauseX-((SCREEN_WIDTH-pauseX)*2/LongestTime*drawTime),pauseY,pauseChangdu,pauseKuandu,1);
            MatrixState2D.popMatrix();

            if (drawTime>LongestTime)
            {
                hadDraw=true;
            }

            drawTime++;
        }

        else if (currPage==PausePage)
        {
            MatrixState2D.pushMatrix();//结束
            all2DTextureRectangle.drawSelf(overId,overX,overY,overChangdu,overKuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//继续
            all2DTextureRectangle.drawSelf(resumeId,resumeX,resumeY,resumeChangdu,resumeKuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//模糊背景
            all2DTextureRectangle.drawSelf(mohuId,SCREEN_WIDTH/2,SCREEN_HEIGHT/2,SCREEN_WIDTH,SCREEN_HEIGHT,1);
            MatrixState2D.popMatrix();
        }
        else
        {

            MatrixState2D.pushMatrix();//遥感大环
            all2DTextureRectangle.drawSelf(jk_bigId,Constant.JK_big_yuanxinX,Constant.JK_big_yuanxinY,Constant.JK_big_radius,Constant.JK_big_radius,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//遥感小环
            MatrixState2D.translate(0,0,0.5f);
            all2DTextureRectangle.drawSelf(jk_smallId, Constant.JK_small_yuanxin_Current_X, Constant.Jk_small_yuanxin_Current_Y, Constant.Jk_small_radius, Constant.Jk_small_radius,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//炸弹
            all2DTextureRectangle.drawSelf(ButtonDropBombTextureId,commonBombX,commonBombY,commonBombChangdu,commonBombKuandu,1);
            MatrixState2D.popMatrix();

            if (tntNum>0)
            {
                MatrixState2D.pushMatrix();//TNT
                all2DTextureRectangle.drawSelf(ButtonDropTntTextureId2,tNtBombX,tNtBombY,tNtBombChangdu,tNtBombKuandu,1);
                MatrixState2D.popMatrix();
            }
            else
            {
                MatrixState2D.pushMatrix();//TNT
                all2DTextureRectangle.drawSelf(ButtonDropTntTextureId,tNtBombX,tNtBombY,tNtBombChangdu,tNtBombKuandu,1);
                MatrixState2D.popMatrix();
            }
            MatrixState2D.pushMatrix();//金币背景
            all2DTextureRectangle.drawSelf(jinbi_backgroundId,Qianbi_background_X,Qianbi_background_Y,Qianbi_background_changdu,Qianbi_background_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//火背景
            all2DTextureRectangle.drawSelf(huo_backgroundId,Huo_background_X,Huo_background_Y,Huo_background_changdu,Huo_background_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//水背景
            all2DTextureRectangle.drawSelf(shui_backgroundId,Shui_background_X,Shui_background_Y,Shui_background_changdu,Shui_background_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//画金币
            moneyNumber.drawSelfGold(goldSum +"", numberId);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//画水火
            shui_huoForDraw.drawself(huoNum,shuiNum);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//画暂停
            all2DTextureRectangle.drawSelf(pauseId,pauseX,pauseY,pauseChangdu,pauseKuandu,1);
            MatrixState2D.popMatrix();

            if(runGoldSum>=3&&ifShowRuneatGold)
            {//绘制金币连吃
                MatrixState2D.pushMatrix();
                runEatGoldNumber.drawSelfRunEatGold(runGoldSum+"", RunEatGoldNumberTextureId);
                MatrixState2D.popMatrix();
            }

            if (tntNum>0)
            {
                MatrixState2D.pushMatrix();//画tnt数量
                moneyNumber.drawSelfTntNum(tntNum+"", numberId);
                MatrixState2D.popMatrix();
            }

            if (drawFlyTnt)
            {
                MatrixState2D.pushMatrix();
                tnt_foodFly.drawself();//飞行tnt
                MatrixState2D.popMatrix();
            }

            if (drawFlyShui)
            {
                MatrixState2D.pushMatrix();
                shui_foodFly.drawself();//飞行水
                MatrixState2D.popMatrix();
            }

            if (drawFlyHuo)
            {
                MatrixState2D.pushMatrix();
                huo_foodFly.drawself();//飞行火
                MatrixState2D.popMatrix();
            }

            MatrixState2D.pushMatrix();//得分
            getScoreNumber.drawself(Constant.get_score+"",Constant.numberId,(Constant.Get_score_X-((get_score+"").length()-1)*25),Get_score_Y);
            MatrixState2D.popMatrix();

            //判断得分
            if (score<gameView.ZDRMoveZ)
            {
                score=gameView.ZDRMoveZ;
            }
            else {}
            get_score=(int) score*2;
            insteadOfCache();

            MatrixState2D.pushMatrix();//奖杯
            all2DTextureRectangle.drawSelf(cupId,Constant.Cup_x,Constant.Cup_Y,Constant.Cup_changdu,Constant.Cup_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//最好
            all2DTextureRectangle.drawSelf(bestId,best_X,best_Y,best_changdu,best_kuandu,1);
            MatrixState2D.popMatrix();

            MatrixState2D.pushMatrix();//最好分数
            bestNumber.drawself(best_score+"",numberId,best_number_X,best_number_Y);
            MatrixState2D.popMatrix();
        }


//        //画人脚下红框
//        if (manGoOrNot)
//        {
//            MatrixState3D.pushMatrix();
//            MatrixState3D.translate((int)(gameView.ZDRX/SideLengh)+0.5f,0.1f,(int)(gameView.ZDRZ/SideLengh)-0.5f);
//            MatrixState3D.scale(0.5f,0.5f,0.5f);
//            mandownRect.drawSelf(manDownRectId);
//            MatrixState3D.popMatrix();
//        }
//        GLES30.glDisable(GLES30.GL_BLEND);



    }
    //替换缓存
    public void insteadOfCache()
    {
        if (goldSum>UserInfoUtil.GetGold())
        {
            UserInfoUtil.WriteGold(goldSum);
        }
    }

}
