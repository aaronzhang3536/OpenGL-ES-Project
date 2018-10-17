package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.View.GameView;


import static com.bn.ZDR.Util.Constant.MapWidth;
import static com.bn.ZDR.Util.Constant.Ratio;
import static com.bn.ZDR.Util.Constant.SideLengh;
import static com.bn.ZDR.Util.Constant.ZhaDanTextureId;
import static com.bn.ZDR.Util.Constant.huoNum;
import static com.bn.ZDR.Util.Constant.ifrelive;
import static com.bn.ZDR.Util.Constant.zhadan;

//代表子弹的类
public class TNTBullet
{
    //子弹的出膛位置
    float startX;
    float startZ;
    //子弹发射后的累计时间
    float timeLive=0;
    //用于绘制爆炸动画效果的纹理矩形组
    //爆炸时长控制
    int time;
    //爆炸动画是否开始标志
    boolean anmiStart=false;
    //爆炸动画帧索引
    int anmiIndex=0;
    //MySurfaceView的引用
    GameView gameView;

    int tempMap[][];
    static float TIME_SPAN=1;
    public TNTBullet(float startX, float startZ, int time, GameView gameView)
    {
        this.time=time;
        this.gameView=gameView;
        this.startX=startX;
        this.startZ=startZ;
    }

    public void go()
    {
        if(!anmiStart)
        {//动画没有开始时正常运动逻辑

            if(timeLive<21)
            {//若没有碰上则前进
                //gameView.al.get()
                timeLive++;//修改当前时间，xyz坐标
//                System.out.println("temeLive="+timeLive);
            }
            else
            {
                anmiStart=true;
                //若碰上则开始播放爆炸动画
                int tempCol=(int)((startX-0.5f)/SideLengh);
                int tempRow=(int)((startZ+0.5f)/SideLengh);
                tempMap=new int [gameView.al.size()][gameView.al.get(0).length];
                for (int i=0;i<gameView.al.size();i++)
                {
                    for (int j=0;j<gameView.al.get(i).length;j++)
                    {
                        tempMap[i][j]=gameView.al.get(i)[j];
                    }
                }
                getRange(-tempRow,tempCol);
                //播放爆炸音效
                gameView.activity.playEQ(1,0);
            }
        }
        else
        {
            //动画开始后换帧
            if(anmiIndex<time-1)
            {//动画没有播放完动画换帧
                anmiIndex=anmiIndex+1;
            }
            else
            {//动画播放完将炮弹从列表中删除
                gameView.tnt_bulletAl.remove(this);
                int tempCol=(int)((startX-0.5f)/SideLengh);
                int tempRow=(int)((startZ+0.5f)/SideLengh);
                recovered(-tempRow,tempCol);
            }
        }
    }


    public void drawSelf()
    {
        MatrixState3D.pushMatrix();//保护现场
        MatrixState3D.translate(startX,0, startZ);//移动到指定位置
        if(!anmiStart)
        {
            //若爆炸动画没有开始，正常绘制炮弹
            MatrixState3D.scale(Ratio,Ratio,Ratio);
            zhadan.drawSelf(ZhaDanTextureId,timeLive*200);
        }
        else
        {
        }
        MatrixState3D.popMatrix();
    }



    public void getRange(int rows,int cols)
    {
        //左边
        for (int i=cols;i>=cols-(huoNum+1);i--)
        {
            if (i<0)
            {
                continue;
            }
            else
            {
                if (gameView.al.get(rows)[i]!=Constant.iWall)
                {
                    gameView.al.get(rows)[i]=Constant.iBombRange;
                }
                else
                {
                    break;
                }
            }
        }
        //右边
        for (int i=cols;i<=cols+(huoNum+1);i++)
        {
            if (i>MapWidth-1)
            {
                continue;
            }
            else
            {
                if (gameView.al.get(rows)[i]!=Constant.iWall)
                {
                    gameView.al.get(rows)[i]=Constant.iBombRange;
                }
                else
                {
                    break;
                }
            }
        }
        //上边
        for (int i=rows;i<=rows+(huoNum+1);i++)
        {
            if (gameView.al.get(i)[cols]!=Constant.iWall)
            {
                gameView.al.get(i)[cols]=Constant.iBombRange;
            }
            else
            {
                break;
            }
        }

        //上边
        for (int i=rows;i>=rows-(huoNum+1);i--)
        {
            if (i<0)
            {
                continue;
            }
            else
            {
                if (gameView.al.get(i)[cols]!=Constant.iWall)
                {
                    gameView.al.get(i)[cols]=Constant.iBombRange;
                }
                else
                {
                    break;
                }
            }
        }
    }

    public void recovered(int rows,int cols)
    {
        if (ifrelive)
        {
            return;
        }
        //左边
        for (int i=cols;i>=cols-(huoNum+1);i--)
        {
            if (i<0)
            {
                continue;
            }
            else
            {

                if (tempMap[rows][i]==Constant.iBox)
                {
                    tempMap[rows][i]=99;
                }
                else if (tempMap[rows][i]==Constant.iBoxHuo)
                {
                    tempMap[rows][i]=Constant.iHuo_food;
                }
                else if (tempMap[rows][i]==Constant.iBoxShui)
                {
                    tempMap[rows][i]=Constant.iShui_food;
                }
                else if (tempMap[rows][i]==Constant.iBoxTnt)
                {
                    tempMap[rows][i]=Constant.iTnt_food;
                }
            }
        }
        //右边
        for (int i=cols;i<=cols+(huoNum+1);i++)
        {
            if (i>MapWidth-1)
            {
                continue;
            }
            else
            {
                if (tempMap[rows][i]==Constant.iBox)
                {
                    tempMap[rows][i]=99;
                }
                else if (tempMap[rows][i]==Constant.iBoxHuo)
                {
                    tempMap[rows][i]=Constant.iHuo_food;
                }
                else if (tempMap[rows][i]==Constant.iBoxShui)
                {
                    tempMap[rows][i]=Constant.iShui_food;
                }
                else if (tempMap[rows][i]==Constant.iBoxTnt)
                {
                    tempMap[rows][i]=Constant.iTnt_food;
                }
            }
        }
        //上边
        for (int i=rows;i<=rows+(huoNum+1);i++)
        {
            {
                if (tempMap[i][cols]==Constant.iBox)
                {
                    tempMap[i][cols]=99;
                }
                else if (tempMap[i][cols]==Constant.iBoxHuo)
                {
                    tempMap[i][cols]=Constant.iHuo_food;
                }
                else if (tempMap[i][cols]==Constant.iBoxShui)
                {
                    tempMap[i][cols]=Constant.iShui_food;
                }
                else if (tempMap[i][cols]==Constant.iBoxTnt)
                {
                    tempMap[i][cols]=Constant.iTnt_food;
                }
            }
        }

        //上边
        for (int i=rows;i>=rows-(huoNum+1);i--)
        {
            if (i<0)
            {
                continue;
            }
            else
            {
                if (tempMap[i][cols]==Constant.iBox)
                {
                    tempMap[i][cols]=99;
                }
                else if (tempMap[i][cols]==Constant.iBoxHuo)
                {
                    tempMap[i][cols]=Constant.iHuo_food;
                }
                else if (tempMap[i][cols]==Constant.iBoxShui)
                {
                    tempMap[i][cols]=Constant.iShui_food;
                }
                else if (tempMap[i][cols]==Constant.iBoxTnt)
                {
                    tempMap[i][cols]=Constant.iTnt_food;
                }
            }
        }

        for (int i=0;i<gameView.al.size();i++)
        {
            for (int j=0;j<gameView.al.get(i).length;j++)
            {
                if (gameView.al.get(i)[j]==Constant.iBuff_gold)
                {
                    continue;
                }
                else
                {
                    gameView.al.get(i)[j]=tempMap[i][j];
                }
            }
        }

    }





}
