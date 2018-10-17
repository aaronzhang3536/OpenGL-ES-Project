package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.GameView.*;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.FireTextureId;
import static com.bn.ZDR.Util.Constant.ForthLevelMap;
import static com.bn.ZDR.Util.Constant.JiGuangRangeLong;
import static com.bn.ZDR.Util.Constant.MapWidth;
import static com.bn.ZDR.Util.Constant.OneMapLength;
import static com.bn.ZDR.Util.Constant.Ratio;
import static com.bn.ZDR.Util.Constant.SPAN;
import static com.bn.ZDR.Util.Constant.SecondLevelMap;
import static com.bn.ZDR.Util.Constant.SideLengh;
import static com.bn.ZDR.Util.Constant.ThirdLevelMap;
import static com.bn.ZDR.Util.Constant.WhichMap;
import static com.bn.ZDR.Util.Constant.ZDRStepAngle;
import static com.bn.ZDR.Util.Constant.dropLength;
import static com.bn.ZDR.Util.Constant.fps;
import static com.bn.ZDR.Util.Constant.fpsJG;
import static com.bn.ZDR.Util.Constant.iJiGuangGuai_down;
import static com.bn.ZDR.Util.Constant.iJiGuangGuai_left;
import static com.bn.ZDR.Util.Constant.iJiGuangGuai_right;
import static com.bn.ZDR.Util.Constant.iJiGuangGuai_up;
import static com.bn.ZDR.Util.Constant.iJiGuangRange;
import static com.bn.ZDR.Util.Constant.mango_mapRoate;
import static com.bn.ZDR.Util.Constant.mapRoateAngle;
import static com.bn.ZDR.Util.Constant.startEndMap;
import static com.bn.ZDR.View.GameView.roateAngle;

/**
 * Created by zhuanxu on 2017/9/16.
 */

public class MyMap
{
    MyGLSurfaceView myGLSurfaceView;
    GameView gameView;
    public MyMap(GameView gameView,MyGLSurfaceView myGLSurfaceView)
    {
        this.gameView=gameView;
        this.myGLSurfaceView=myGLSurfaceView;
    }

    //绘制地图
    public void paintMap(boolean secondDraw)
    {
        getMap();
        calculatePostion(0,secondDraw);//绘制地面
        if (!secondDraw)
        {
            calculatePostion(1,secondDraw);//绘制地面
        }
        calculatePostion(2,secondDraw);//绘制地面-
    }

    //获得地图
    public void getMap()
    {
        int whichLevel =0;
        float where=gameView.ZDRZ-SPAN/SideLengh;
        int []tempMap=new int [MapWidth];
        for (int i = 0; i< Constant.startEndMap.size(); i++)
        {
            if (gameView.ZDRZ>startEndMap.get(i))
            {
                whichLevel=i;
                break;
            }
        }
        float privious=startEndMap.get(whichLevel-1);
        float next=startEndMap.get(whichLevel);
        if (gameView.ZDRZ-(SPAN+dropLength)>next&&((gameView.ZDRZ-SPAN)<=gameView.TopZ))//在一块地图中
        {
            int rows=(int)((privious-gameView.ZDRZ)/SideLengh);//人物在第几行
            int to=(int)(rows+SPAN/SideLengh+dropLength/SideLengh);//地图尾在哪
            for (int i=0;i<MapWidth;i++)
            {
                tempMap[i]=WhichMap.get(whichLevel)[to][i];
            }
//            al.add(WhichMap.get(whichLevel)[to]);
            gameView.al.add(tempMap);
            gameView.TopZ=gameView.TopZ-SideLengh;
        }
        else if (gameView.ZDRZ-(SPAN+dropLength)<next&&((gameView.ZDRZ-SPAN)<=gameView.TopZ))
        {
            if (!WhichMap.containsKey(whichLevel+1))
            {
                int randomMap=(int)Math.random()*3;
                if (randomMap<=1)
                {
                    WhichMap.put(whichLevel+1,SecondLevelMap);
                }
                else if (randomMap>1&&randomMap<=2)
                {
                    WhichMap.put(whichLevel+1,ThirdLevelMap);
                }
                else
                {
                    WhichMap.put(whichLevel+1,ForthLevelMap);
                }

                if (!startEndMap.containsKey(whichLevel+1))
                {
                    startEndMap.put(whichLevel+1,-0.5f-(whichLevel+1)*OneMapLength*SideLengh);
                }

                if (whichLevel-1>=1)
                {
                    WhichMap.remove(whichLevel-1);
                }
            }
            int rows=(int)((next-(gameView.ZDRZ-SPAN-dropLength))/SideLengh);//人物在第几行
            int to=(rows);//地图尾在哪
            for (int i=0;i<MapWidth;i++)
            {
                tempMap[i]=WhichMap.get(whichLevel+1)[to][i];
            }
            gameView.al.add(tempMap);
            gameView.TopZ=gameView.TopZ-SideLengh;

        }
        else if ((gameView.ZDRZ-SPAN-dropLength)>gameView.TopZ+SideLengh-dropLength)
        {
            gameView.al.remove(gameView.al.size()-1);
            gameView.TopZ=gameView.TopZ+SideLengh;
        }
    }

    //计算物体位置并实行绘制
    public void calculatePostion(int type,boolean secondDraw)//绘制类型，0绘制地面,1粒子系统，2为绘制地面其他物体
    {
        for (int i=0;i<gameView.al.size();i++)
        {
            if(i<gameView.curr_drop_row-2)
            {
                continue;
            }
            else
                for(int j=0;j<gameView.al.get(i).length;j++)
                {
                    float x=0;
                    float y=0;
                    float z=0;
                    int value=0;
                    synchronized (gameView.lock)
                    {
                        for(int k=0;k<gameView.dropBrickLList.size();k++)
                        {
                            if (gameView.dropBrickLList.get(k)[0] == i && gameView.dropBrickLList.get(k)[1] == j)
                            {
                                gameView.ifDrop = true;
                                value = gameView.dropBrickLList.get(k)[2];
                                break;
                            }
                        }
                    }
                    if(gameView.ifDrop)
                    {
                        if(value<gameView.shakeNum)//判断是否还处于抖动状态
                        {
                            y=0;
                            switch (value%4)//判断抖动方向
                            {
                                case 0://前z减
                                    x=SideLengh*(j+0.5f);
                                    z=-SideLengh*(i+0.5f)-gameView.shakeStep;
                                    break;
                                case 1://后z加
                                    x=SideLengh*(j+0.5f);
                                    z=-SideLengh*(i+0.5f)+gameView.shakeStep;
                                    break;
                                case 2://左x减
                                    x=SideLengh*(j+0.5f)-gameView.shakeStep;
                                    z=-SideLengh*(i+0.5f);
                                    break;
                                case 3://右x加
                                    x=SideLengh*(j+0.5f)+gameView.shakeStep;
                                    z=-SideLengh*(i+0.5f);
                                    break;
                            }
                        }
                        else//处于掉落状态
                        {
                            boolean ifAdd=true;
                            for(int k=0;k<gameView.makeManDeadDropBrick.size();k++)
                            {
                                if (i==gameView.makeManDeadDropBrick.get(k)[0]&&j==gameView.makeManDeadDropBrick.get(k)[1])
                                {//尚未添加
                                    ifAdd=false;
                                    break;
                                }
                            }
                            if (ifAdd)
                            {
                                gameView.makeManDeadDropBrick.add(new int[]{i,j});
                            }

                            x=SideLengh*(j+0.5f);
                            y=-0.5f*9.8f*(value-gameView.shakeNum)*(value-gameView.shakeNum)/2500*7;//自由落体运动，y=1/2*g*t^2
                            z=-SideLengh*(i+0.5f);
                        }
                    }
                    else
                    {
                        x=SideLengh*(j+0.5f);
                        y=0;
                        z=-SideLengh*(i+0.5f);
                    }
                    float xAngle=0;
                    float roate=0;
                    if(i>(-gameView.TopZ)/SideLengh)
                    {
                        z=-(i*SideLengh+gameView.TopZ)-0.5f;
                        xAngle=(mapRoateAngle)*(i*SideLengh+gameView.TopZ)/SideLengh;
                        roate=(SideLengh-(gameView.ZDRZ-gameView.TopZ-SPAN))/ZDRStepAngle*mango_mapRoate;
                        if (roate>=0)
                        {
                            roate=0;
                        }
                        if (roate<=mapRoateAngle)
                        {
                            roate=mapRoateAngle;
                        }
                        xAngle-=roate;
                    }
                    getLevel(-0.5f-i*SideLengh);
                    if (type==0)
                    {
                        if (gameView.al.get(i)[j]!=Constant.iKongBai)
                        {
                            paintLand(i,j,x,y,z,xAngle,secondDraw);
                        }
                    }
                    else if (type==1)
                    {
                        paintParticle(i,j,x,y,z,xAngle,gameView.ifDrop);
                    }
                    else if (type==2)
                    {
                        paintOtherThing(i,j,x,y,z,xAngle,secondDraw);
                    }
                    gameView.ifDrop=false;//重置是否掉落状态
                }
        }
    }

    //获的第几关
    public void getLevel(float where)
    {
        for (int i=0;i<Constant.startEndMap.size();i++)
        {
            if (where>startEndMap.get(i))
            {
                gameView.level=i;
                break;
            }
        }
    }

    //绘制地面
    public void paintLand(int i,int j,float x,float y,float z,float xAngle,boolean secondDraw)
    {
        //绘制地面层
        {
            if(i>(-gameView.TopZ)/SideLengh)
            {
                MatrixState3D.pushMatrix();
                MatrixState3D.translate(0,0,gameView.TopZ);
                MatrixState3D.rotate(xAngle,1,0,0);
                MatrixState3D.translate(x,y,z);
                MatrixState3D.rotate(xAngle,1,0,0);
            }
            else
            {
                MatrixState3D.pushMatrix();
                MatrixState3D.translate(x,y,z);
            }
            MatrixState3D.scale(Ratio,Ratio,Ratio);
            switch (gameView.level%3)
            {
                case 1:
                    if (secondDraw)
                    {
                        Constant.land.drawSelfTwo(Constant.XuHuaId);
                    }
                    else
                    {
                        Constant.land.drawSelf(Constant.Game1stLevelLandTextureId);
                    }
                    break;
                case 2:
                    if (secondDraw)
                    {
                        Constant.land.drawSelfTwo(Constant.XuHuaId);
                    }
                    else
                    {
                        Constant.land.drawSelf(Constant.Game2ndLevelLandTextureId);
                    }
                    break;
                case 0:
                    if (secondDraw)
                    {
                        Constant.land.drawSelfTwo(Constant.XuHuaId);
                    }
                    else
                    {
                        Constant.land.drawSelf(Constant.Game3ndLevelLandTextureId);
                    }
                    break;
            }
            MatrixState3D.popMatrix();
        }
    }

    //绘制粒子系统
    public void paintParticle(int i,int j,float x,float y,float z,float xAngle,boolean ifDrop)
    {
        if(ifDrop)
        {
            return;
        }
        switch(gameView.al.get(i)[j])
        {
            case Constant.iBombRange:
                MatrixState3D.pushMatrix();
                MatrixState3D.translate(x,SideLengh*0.5f,z);
                MatrixState3D.scale(SideLengh*0.5f,SideLengh*0.5f,SideLengh*0.5f);
                fps.calculateBillboardDirection(x,SideLengh*0.5f,z);
                fps.drawSelf(FireTextureId);
                MatrixState3D.popMatrix();
                break;
            case iJiGuangGuai_down:
                if(!gameView.ifJiGuang) return;
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                    MatrixState3D.translate(x,0+y,z);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(x,0+y,z);
                }
                for(int k=1;k<=JiGuangRangeLong;k++)
                {
                    if(i-k>=0)
                    {
                        if(gameView.al.get(i-k)[j]==99||gameView.al.get(i-k)[j]==iJiGuangRange)
                        {
                            gameView.al.get(i-k)[j]=iJiGuangRange;
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(0,0.5f,SideLengh*k-SideLengh*0.2f);
                            MatrixState3D.rotate(90,0,1,0);
//                            fpsJG.calculateBillboardDirection();
                            fpsJG.drawSelf(Constant.FireTextureId);
                            MatrixState3D.popMatrix();
                        }
                        else
                            break;
                    }
                }
                MatrixState3D.popMatrix();
                break;
            case iJiGuangGuai_right:
                if(!gameView.ifJiGuang) return;
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                    MatrixState3D.translate(x,0+y,z);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(x,0+y,z);
                }
                for(int k=1;k<=JiGuangRangeLong;k++)
                {
                    if(j+k<=MapWidth)
                    {
                        if(gameView.al.get(i)[j+k]==99||gameView.al.get(i)[j+k]==iJiGuangRange)
                        {
                            gameView.al.get(i)[j+k]=iJiGuangRange;
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(SideLengh*k-SideLengh*0.2f,0.5f,0);
//                            fpsJG.calculateBillboardDirection();
                            fpsJG.drawSelf(Constant.FireTextureId);
                            MatrixState3D.popMatrix();
                        }
                        else
                            break;
                    }
                }
                MatrixState3D.popMatrix();
                break;
            case iJiGuangGuai_up:
                if(!gameView.ifJiGuang) return;
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                    MatrixState3D.translate(x,0+y,z);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(x,0+y,z);
                }
                for(int k=1;k<=JiGuangRangeLong;k++)
                {
                    if(i+k<gameView.al.size())
                    {
                        if(gameView.al.get(i+k)[j]==99||gameView.al.get(i+k)[j]==iJiGuangRange)
                        {
                            gameView.al.get(i+k)[j]=iJiGuangRange;
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(0,0.5f,SideLengh*0.2f-SideLengh*k);
                            MatrixState3D.rotate(90,0,1,0);
//                            fpsJG.calculateBillboardDirection();
                            fpsJG.drawSelf(Constant.FireTextureId);
                            MatrixState3D.popMatrix();
                        }
                        else
                            break;
                    }
                }
                MatrixState3D.popMatrix();
                break;
            case iJiGuangGuai_left:
                if(!gameView.ifJiGuang) return;
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                    MatrixState3D.translate(x,0+y,z);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(x,0+y,z);
                }
                for(int k=1;k<=JiGuangRangeLong;k++)
                {
                    if(j-k>=0)
                    {
                        if(gameView.al.get(i)[j-k]==99||gameView.al.get(i)[j-k]==iJiGuangRange)
                        {
                            gameView.al.get(i)[j-k]=iJiGuangRange;
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(SideLengh*0.2f-SideLengh*k,0.5f,0);
//                            fpsJG.calculateBillboardDirection();
                            fpsJG.drawSelf(Constant.FireTextureId);
                            MatrixState3D.popMatrix();
                        }
                        else
                            break;
                    }
                }
                MatrixState3D.popMatrix();
                break;
        }
    }

    //绘制地面其他物体
    public void paintOtherThing(int i,int j,float x,float y,float z,float xAngle,boolean secondDraw)
    {
        switch (gameView.al.get(i)[j])
        {
            case Constant.iWall://画墙体
                switch (gameView.level%3)//判断关数
                {
                    case 1:
                        if(i>(-gameView.TopZ)/SideLengh)
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(0,0,gameView.TopZ);
                            MatrixState3D.rotate(xAngle,1,0,0);
                            MatrixState3D.translate(x,0+y,z);
                            MatrixState3D.rotate(xAngle,1,0,0);
                        }
                        else
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(x,0+y,z);
                        }
                        MatrixState3D.scale(Ratio,Ratio,Ratio);
                        if (secondDraw)
                        {
                            Constant.wall1.drawSelfTwo(Constant.XuHuaId);
                        }
                        else
                        {
                            Constant.wall1.drawSelf(Constant.Game1stLevelWallTextureId);
                        }
                        MatrixState3D.popMatrix();
                        break;
                    case 2:
                        if(i>(-gameView.TopZ)/SideLengh)
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(0,0,gameView.TopZ);
                            MatrixState3D.rotate(xAngle,1,0,0);
                            MatrixState3D.translate(x,0+y,z);
                            MatrixState3D.rotate(xAngle,1,0,0);
                        }
                        else
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(x,0+y,z);
                        }
                        MatrixState3D.scale(Ratio,Ratio,Ratio);
                        if (secondDraw)
                        {
                            Constant.wall2.drawSelfTwo(Constant.XuHuaId);
                        }
                        else
                        {
                            Constant.wall2.drawSelf(Constant.Game2ndLevelWallTextureId);
                        }
                        MatrixState3D.popMatrix();
                        break;
                    case 0:
                        if(i>(-gameView.TopZ)/SideLengh)
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(0,0,gameView.TopZ);
                            MatrixState3D.rotate(xAngle,1,0,0);
                            MatrixState3D.translate(x,0+y,z);
                            MatrixState3D.rotate(xAngle,1,0,0);
                        }
                        else
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(x,0+y,z);
                        }
                        MatrixState3D.scale(Ratio,Ratio,Ratio);
                        if (secondDraw)
                        {
                            Constant.wall3.drawSelfTwo(Constant.XuHuaId);
                        }
                        else
                        {
                            Constant.wall3.drawSelf(Constant.Game3ndLevelWallTextureId);
                        }
                        MatrixState3D.popMatrix();
                        break;
                }
                break;
            case Constant.iBoxTnt:
            case Constant.iBoxShui:
            case Constant.iBoxHuo:
            case Constant.iBox://画箱子，可炸部分
                switch (gameView.level%3)//判断关数
                {
                    case 1:
                        if(i>(-gameView.TopZ)/SideLengh)
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(0,0,gameView.TopZ);
                            MatrixState3D.rotate(xAngle,1,0,0);
                            MatrixState3D.translate(x,0+y,z);
                            MatrixState3D.rotate(xAngle,1,0,0);
                        }
                        else
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(x,0+y,z);
                        }
                        MatrixState3D.scale(Ratio,Ratio,Ratio);
                        if (secondDraw)
                        {
                            Constant.box1.drawSelfTwo(Constant.XuHuaId);
                        }
                        else
                        {
                            Constant.box1.drawSelf(Constant.Game1stLevelBoxTextureId);
                        }
                        MatrixState3D.popMatrix();
                        break;
                    case 2:
                        if(i>(-gameView.TopZ)/SideLengh)
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(0,0,gameView.TopZ);
                            MatrixState3D.rotate(xAngle,1,0,0);
                            MatrixState3D.translate(x,0+y,z);
                            MatrixState3D.rotate(xAngle,1,0,0);
                        }
                        else
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(x,0+y,z);
                        }
                        MatrixState3D.scale(Ratio,Ratio,Ratio);
                        if (secondDraw)
                        {
                            Constant.box2.drawSelfTwo(Constant.XuHuaId);
                        }
                        else
                        {
                            Constant.box2.drawSelf(Constant.Game2ndLevelBoxTextureId);
                        }
                        MatrixState3D.popMatrix();
                        break;
                    case 0:
                        if(i>(-gameView.TopZ)/SideLengh)
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(0,0,gameView.TopZ);
                            MatrixState3D.rotate(xAngle,1,0,0);
                            MatrixState3D.translate(x,0+y,z);
                            MatrixState3D.rotate(xAngle,1,0,0);
                        }
                        else
                        {
                            MatrixState3D.pushMatrix();
                            MatrixState3D.translate(x,0+y,z);
                        }
                        MatrixState3D.scale(Ratio,Ratio,Ratio);
                        if (secondDraw)
                        {
                            Constant.box3.drawSelfTwo(Constant.XuHuaId);
                        }
                        else
                        {
                            Constant.box3.drawSelf(Constant.Game3ndLevelBoxTextureId);
                        }
                        MatrixState3D.popMatrix();
                        break;
                }
                break;
            case Constant.iBuff_gold://画金币buff
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                }
                MatrixState3D.translate(x,0+y,z);
                MatrixState3D.rotate(roateAngle,0,1,0);
                MatrixState3D.scale(Ratio,Ratio,Ratio);
                if (secondDraw)
                {
                    Constant.buff_gold.drawSelfTwo(Constant.XuHuaId);
                }
                else
                {
                    Constant.buff_gold.drawSelf(Constant.GameGoldTextureId);
                }
                MatrixState3D.popMatrix();
                break;
            case Constant.iTnt_food://tnt食物
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                }
                MatrixState3D.translate(x,0+y+0.2f,z);
                MatrixState3D.rotate(roateAngle,0,1,0);
                MatrixState3D.scale(2*Ratio/3,2*Ratio/3,2*Ratio/3);
                if (secondDraw)
                {
                    Constant.tnt_shui_huo_food.drawSelfTwo(Constant.XuHuaId);
                }
                else
                {
                    Constant.tnt_shui_huo_food.drawSelf(Constant.tntBombId);
                }
                MatrixState3D.popMatrix();
                break;
            case Constant.iHuo_food://火食物
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                }
                MatrixState3D.translate(x,0+y+0.2f,z);
                MatrixState3D.rotate(roateAngle,0,1,0);
                MatrixState3D.scale(2*Ratio/3,2*Ratio/3,2*Ratio/3);
                if (secondDraw)
                {
                    Constant.tnt_shui_huo_food.drawSelfTwo(Constant.XuHuaId);
                }
                else
                {
                    Constant.tnt_shui_huo_food.drawSelf(Constant.huo_foodId);
                }
                MatrixState3D.popMatrix();
                break;
            case Constant.iShui_food:
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                }
                MatrixState3D.translate(x,0+y+0.2f,z);
                MatrixState3D.rotate(roateAngle,0,1,0);
                MatrixState3D.scale(2*Ratio/3,2*Ratio/3,2*Ratio/3);
                if (secondDraw)
                {
                    Constant.tnt_shui_huo_food.drawSelfTwo(Constant.XuHuaId);
                }
                else
                {
                    Constant.tnt_shui_huo_food.drawSelf(Constant.shui_foodId);
                }
                MatrixState3D.popMatrix();
                break;
            case Constant.iCiQiu:
                boolean drawCiQiu=true;
                for (CiQiuGoForControl ciQiuGoForControl:gameView.ciQiuGoForControlsList)
                {
                    if (ciQiuGoForControl.startJ==j&&ciQiuGoForControl.startI==i)
                    {
                        drawCiQiu=false;
                        break;
                    }
                }
                if (drawCiQiu)
                {
                    gameView.ciQiuGoForControlsList.add(new CiQiuGoForControl(gameView,(0.5f+j*SideLengh),(-0.5f-i*SideLengh),i,j));
                    gameView.al.get(i)[j]=99;
                }
                break;
            case Constant.iJiGuangGuai_down:
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                    MatrixState3D.translate(x,0+y,z);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(x,0+y,z);
                }
                MatrixState3D.scale(Ratio,Ratio,Ratio);
                if (secondDraw)
                {
                    Constant.ji_guang_guai.drawSelfTwo(Constant.XuHuaId);
                }
                else
                {
                    Constant.ji_guang_guai.drawSelf(Constant.JiGuangGuaiTextureId);
                }
                MatrixState3D.popMatrix();
                break;
            case Constant.iJiGuangGuai_right:
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                    MatrixState3D.translate(x,0+y,z);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(x,0+y,z);
                }
                MatrixState3D.rotate(90,0,1,0);
                MatrixState3D.scale(Ratio,Ratio,Ratio);
                if (secondDraw)
                {
                    Constant.ji_guang_guai.drawSelfTwo(Constant.XuHuaId);
                }
                else
                {
                    Constant.ji_guang_guai.drawSelf(Constant.JiGuangGuaiTextureId);
                }
                MatrixState3D.popMatrix();
                break;
            case Constant.iJiGuangGuai_up:
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                    MatrixState3D.translate(x,0+y,z);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(x,0+y,z);
                }
                MatrixState3D.rotate(180,0,1,0);
                MatrixState3D.scale(Ratio,Ratio,Ratio);
                if(secondDraw)
                {
                    Constant.ji_guang_guai.drawSelfTwo(Constant.XuHuaId);
                }
                else
                {
                    Constant.ji_guang_guai.drawSelf(Constant.JiGuangGuaiTextureId);
                }
                MatrixState3D.popMatrix();
                break;
            case Constant.iJiGuangGuai_left:
                if(i>(-gameView.TopZ)/SideLengh)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(0,0,gameView.TopZ);
                    MatrixState3D.rotate(xAngle,1,0,0);
                    MatrixState3D.translate(x,0+y,z);
                    MatrixState3D.rotate(xAngle,1,0,0);
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate(x,0+y,z);
                }
                MatrixState3D.rotate(270,0,1,0);
                MatrixState3D.scale(Ratio,Ratio,Ratio);
                if (secondDraw)
                {
                    Constant.ji_guang_guai.drawSelfTwo(Constant.XuHuaId);
                }
                else
                {
                    Constant.ji_guang_guai.drawSelf(Constant.JiGuangGuaiTextureId);
                }
                MatrixState3D.popMatrix();
                break;
        }
    }

}
