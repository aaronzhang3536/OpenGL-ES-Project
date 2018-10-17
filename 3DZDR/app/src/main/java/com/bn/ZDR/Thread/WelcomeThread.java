package com.bn.ZDR.Thread;

import com.bn.ZDR.View.GameView;

import static com.bn.ZDR.Util.Constant.ButtonRangeX;
import static com.bn.ZDR.Util.Constant.SCREEN_WIDTH;
import static com.bn.ZDR.Util.Constant.Scx;
import static com.bn.ZDR.Util.Constant.Scz;
import static com.bn.ZDR.Util.Constant.SideLengh;
import static com.bn.ZDR.Util.Constant.Tiny_beijing_X;
import static com.bn.ZDR.Util.Constant.Tiny_beijing_Y;
import static com.bn.ZDR.Util.Constant.Tiny_beijing_kuandu;
import static com.bn.ZDR.Util.Constant.WelcomeThreadFlag;
import static com.bn.ZDR.Util.Constant.ZDRStartCol;
import static com.bn.ZDR.Util.Constant.ZDRStartRow;
import static com.bn.ZDR.Util.Constant.drawShouOrShouzhi;
import static com.bn.ZDR.Util.Constant.tiny_roate_angle;

/**
 * Created by zhuanxu on 2017/8/26.
 */

public class WelcomeThread extends Thread
{
    GameView gameView;
    public int time;
    //====================================摄像机==================================
    boolean growOrInCrease=true;//true + false -
    float r=5.5f;//转动半径
    float rx=SideLengh*(ZDRStartCol+0.5f);//圆心x
    float rz=-SideLengh*(ZDRStartRow+0.5f)+r+5;//圆心y
    float xMax=rx+2f;
    float xMin=rx-2f;
    //=====================================背景===================================
    boolean isdown=true;
    boolean isleft=true;
    float xspan=4f;//x补进
    float yspan=2f;//y步进
    float angle=1f;//所转角度
    int n=1;//几次抖动
    int drawShouOrShouzhiTime=0;
    public WelcomeThread(GameView gameView)
    {
        this.gameView=gameView;
        Tiny_beijing_X=SCREEN_WIDTH/2;
        Tiny_beijing_Y=0.3f*ButtonRangeX+Tiny_beijing_kuandu/2;
        tiny_roate_angle=0;
        time=0;
    }

    @Override
    public void run()
    {
        while (WelcomeThreadFlag)
        {
            //摄像机转动
           if (growOrInCrease)
           {
               Scx+=0.1f;
               Scz=(float)- Math.sqrt(r*r-(Scx-rx)*(Scx-rx))-rz;
               if (Scx>=xMax)
               {
                   growOrInCrease=false;
               }
           }
           else
           {
               Scx-=0.1f;
               Scz=(float)- Math.sqrt(r*r-(Scx-rx)*(Scx-rx))-rz;
               if (Scx<=xMin)
               {
                   growOrInCrease=true;
               }
           }

            //背景抖动
            if (time%5==0)
            {
                if (isleft)
                {
                    Tiny_beijing_X-=xspan;
                    if (Tiny_beijing_X<=SCREEN_WIDTH/2-n*xspan)
                    {
                        isleft=false;
                    }
                    if (isdown)
                    {
                        Tiny_beijing_Y+=yspan;
                        if (Tiny_beijing_Y>=0.3f*ButtonRangeX+Tiny_beijing_kuandu/2+n*yspan)
                        {
                            isdown=false;
                            tiny_roate_angle+=angle;
                        }
                    }
                    else
                    {
                        Tiny_beijing_Y-=yspan;
                        if (Tiny_beijing_Y<=0.3f*ButtonRangeX+Tiny_beijing_kuandu/2)
                        {
                            isdown=true;
                            tiny_roate_angle-=angle;
                        }
                    }
                }
                else
                {
                    Tiny_beijing_X+=xspan;
                    if (Tiny_beijing_X>=SCREEN_WIDTH/2+n*xspan)
                    {
                        isleft=true;
                    }
                    if (isdown)
                    {
                        Tiny_beijing_Y+=yspan;
                        if (Tiny_beijing_Y>=0.3f*ButtonRangeX+Tiny_beijing_kuandu/2+n*yspan)
                        {
                            isdown=false;
                            tiny_roate_angle-=angle;
                        }
                    }
                    else
                    {
                        Tiny_beijing_Y-=yspan;
                        if (Tiny_beijing_Y<=0.3f*ButtonRangeX+Tiny_beijing_kuandu/2)
                        {
                            isdown=true;
                            tiny_roate_angle+=angle;
                        }
                    }
                }
            }

            //画手指或手
            if (drawShouOrShouzhiTime==3)
            {
                drawShouOrShouzhi=1;//画手
            }
            else  if (drawShouOrShouzhiTime==5)
            {
                drawShouOrShouzhi=2;
            }
            else if (drawShouOrShouzhiTime==7)
            {
                drawShouOrShouzhi=0;
            }
            if (drawShouOrShouzhiTime>=12)
            {
                drawShouOrShouzhiTime=0;
            }


            drawShouOrShouzhiTime++;
            time++;
            try {
                Thread.sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
