package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.Ratio;
import static com.bn.ZDR.Util.Constant.SideLengh;
import static com.bn.ZDR.Util.Constant.XuHuaId;
import static com.bn.ZDR.Util.Constant.ZhaDanTextureId;
import static com.bn.ZDR.Util.Constant.bothSideCubetexture1;
import static com.bn.ZDR.Util.Constant.bothSideCubetexture2;
import static com.bn.ZDR.Util.Constant.bothSideCubetexture3;
import static com.bn.ZDR.Util.Constant.final_score_X;
import static com.bn.ZDR.Util.Constant.tnt_shui_huo_food;
import static com.bn.ZDR.Util.Constant.zhadan;

/**
 * Created by zhuanxu on 2017/9/2.
 */

public class BothSideCube
{
    GameView gameView;
    MyGLSurfaceView myGLSurfaceView;
    float cubeSideLength=SideLengh*3;
    int number=40;
    public BothSideCube(MyGLSurfaceView myGLSurfaceView, GameView gameView)
    {
        this.myGLSurfaceView=myGLSurfaceView;
        this.gameView=gameView;
    }

    public void drawSelf(boolean secondDraw)
    {
        //for (int i=(int)gameView.curr_drop_row-5;i<-(int)gameView.TopZ+5;i++)
        for (int i=0;i<-(int)gameView.TopZ+10;i++)
        {
            if((number*SideLengh-i*cubeSideLength*1.5f)>(-gameView.curr_drop_row+number*SideLengh))
            {
               // System.out.println("i="+i+"   gameView.TopZ+5="+(-gameView.TopZ+5)+ " "+ (number*SideLengh-i*cubeSideLength*1.5f) +"  "+(-gameView.curr_drop_row+number*SideLengh));
                continue;
            }
            //左
            MatrixState3D.pushMatrix();
            MatrixState3D.translate(-0.5f*cubeSideLength,0.4f*cubeSideLength,number*SideLengh-i*cubeSideLength*1.5f);
            if ((number*SideLengh-i*cubeSideLength*1.5f)<gameView.TopZ-7)
            {

                if (i%2==0)
                {
                    MatrixState3D.translate(-1f*cubeSideLength,0.5f*cubeSideLength,0);
                }
                else {
                    MatrixState3D.translate(-0.5f*cubeSideLength,-0.3f*cubeSideLength,0);
                }

            }
            if ((number*SideLengh-i*cubeSideLength*1.5f)>-gameView.curr_drop_row+5)
            {

                if (i%2==0)
                {
                    MatrixState3D.translate(2f*cubeSideLength,0.5f*cubeSideLength,0);
                }
                else {
                    MatrixState3D.translate(1f*cubeSideLength,-0.3f*cubeSideLength,0);
                }

            }

          //  if ()
            if (Math.abs(i%4)==0)
            {
                MatrixState3D.rotate(20,1,0,0);
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.scale(Ratio*2,Ratio*2,Ratio*2);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture1);
                }

                //zhadan.drawSelf(ZhaDanTextureId,2*200);
            }
            if (Math.abs(i%4)==1)
            {
                MatrixState3D.rotate(20,0,0,1);
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.scale(Ratio*3,Ratio*3,Ratio*3);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture2);
                }
            }
            if (Math.abs(i%4)==2)
            {
                MatrixState3D.rotate(10,1,1,0);
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.scale(Ratio*1,Ratio*1,Ratio*1);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture2);
                }
            }
            if (Math.abs(i%4)==3)
            {
                MatrixState3D.rotate(15,1,0,1);
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.scale(Ratio*3,Ratio*3,Ratio*3);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture3);
                }
            }
            MatrixState3D.popMatrix();






            //右
            MatrixState3D.pushMatrix();
            MatrixState3D.translate(11*SideLengh+0.5f*cubeSideLength,0.4f*cubeSideLength,number*SideLengh-i*cubeSideLength*1.5f);

            if ((number*SideLengh-i*cubeSideLength*1.5f)<gameView.TopZ-7)
            {

                if (i%2==0)
                {
                    MatrixState3D.translate(1f*cubeSideLength,0.5f*cubeSideLength,0);
                }
                else {
                    MatrixState3D.translate(0.5f*cubeSideLength,-0.3f*cubeSideLength,0);
                }

            }
            if ((number*SideLengh-i*cubeSideLength*1.5f)>-gameView.curr_drop_row+5)
            {

                if (i%2==0)
                {
                    MatrixState3D.translate(-1.5f*cubeSideLength,0.5f*cubeSideLength,0);
                }
                else {
                    MatrixState3D.translate(-1f*cubeSideLength,-0.3f*cubeSideLength,0);
                }

            }
            if (Math.abs(i%4)==0)
            {
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.rotate(20,1,0,0);
                MatrixState3D.scale(Ratio*2,Ratio*2,Ratio*2);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture1);
                }
            }
            if (Math.abs(i%4)==1)
            {
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.rotate(20,0,0,1);
                MatrixState3D.scale(Ratio*3,Ratio*3,Ratio*3);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture3);
                }
            }
            if (Math.abs(i%4)==2)
            {
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.rotate(10,1,1,0);
                MatrixState3D.scale(Ratio*1,Ratio*1,Ratio*1);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture2);
                }
            }
            if (Math.abs(i%4)==3)
            {
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.rotate(15,1,0,1);
                MatrixState3D.scale(Ratio*3,Ratio*3,Ratio*3);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture3);
                }
            }
            MatrixState3D.popMatrix();



            //下
            MatrixState3D.pushMatrix();
            MatrixState3D.translate(5f*SideLengh,-7f*cubeSideLength,number*SideLengh-i*cubeSideLength*1.5f);

            if (i%2==0)
            {
                MatrixState3D.translate(-1f*cubeSideLength,0.1f*cubeSideLength,0);
            }
            else {
                MatrixState3D.translate(1f*cubeSideLength,0.1f*cubeSideLength,0);
            }

            if (Math.abs(i%4)==0)
            {
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.rotate(20,1,0,0);
                MatrixState3D.scale(Ratio*2,Ratio*2,Ratio*2);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture1);
                }
            }
            if (Math.abs(i%4)==1)
            {
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.rotate(20,0,0,1);
                MatrixState3D.scale(Ratio*3,Ratio*3,Ratio*3);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture2);
                }
            }
            if (Math.abs(i%4)==2)
            {
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.rotate(10,1,1,0);
                MatrixState3D.scale(Ratio*4,Ratio*4,Ratio*4);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture2);
                }
            }
            if (Math.abs(i%4)==3)
            {
                MatrixState3D.rotate(15,0,1,0);
                MatrixState3D.rotate(15,1,0,1);
                MatrixState3D.scale(Ratio*1f,Ratio*1f,Ratio*1f);
                if (secondDraw)
                {
                    tnt_shui_huo_food.drawSelfTwo(XuHuaId);
                }
                else
                {
                    tnt_shui_huo_food.drawSelf(bothSideCubetexture3);
                }
            }
            MatrixState3D.popMatrix();
        }
//        System.out.println("是否结算了="+((int)gameView.curr_drop_row-5)+"   盛世嫡妃= "+(-(int)gameView.TopZ+5)+"   0.5f-i="+(0.5f-(((int)gameView.curr_drop_row-5))*cubeSideLength*1.5f)+"  z="+gameView.ZDRZ);
    }

}
