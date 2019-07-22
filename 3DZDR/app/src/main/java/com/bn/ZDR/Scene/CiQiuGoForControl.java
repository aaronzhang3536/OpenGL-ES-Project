package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.Util.Quaternion;
import com.bn.ZDR.Util.Vector3f;
import com.bn.ZDR.View.GameView;

import java.util.ArrayList;

import static com.bn.ZDR.Util.Constant.OverPage;
import static com.bn.ZDR.Util.Constant.Ratio;
import static com.bn.ZDR.Util.Constant.SideLengh;

import static com.bn.ZDR.Util.Constant.currPage;
import static com.bn.ZDR.Util.Constant.mapRoateAngle;

/**
 * Created by zhuanxu on 2017/8/22.
 */

public class CiQiuGoForControl
{
    ArrayList<CiQiuGoForControl> ballAl;//所有桌球的列表
    public float xOffset;//刺球的X位置
    public float zOffset;//球的Z位置
    public float startZoffset;//球初始z位置
    float TIME_SPAN=0.05f;
    //桌球的速度（桌球是一直在桌面上运动，因此没有Y向速度）
    float vTotal;
    //滚动方向
    int roateDirection;//0 上 1下 2 左 3右
    //记录桌球旋转姿态的四元数
    Quaternion quaternionTotal = Quaternion.getIdentityQuaternion();
    GameView gameView;
    //绘制桌球时与旋转姿态相关的各种值
    float angleCurr;//当前桌球的旋转角度
    float currAxisX;//当前桌球旋转轴向量的X分量
    float currAxisZ;//当前桌球旋转轴向量的Z分量
    float currAxisY=0;//当前桌球旋转轴向量的Y分量（因为球在桌面上运动旋转轴平行于桌面，因此没有Y分量）
    //记录该刺球产生位置
    public int startI;
    public int startJ;
    public CiQiuGoForControl(GameView gameView,float xOffset,float zOffset,int startI,int startJ)
    {
        this.gameView=gameView;
        this.xOffset=xOffset;
        this.zOffset=zOffset;
        this.vTotal=0.5f;
        this.roateDirection=0;//0 上 1下 2 左 3右
        this.startI=startI;
        this.startJ=startJ;
        startZoffset=(-0.5f-startI*SideLengh);
    }

    public void drawSelf(boolean secondDraw)
    {//绘制物体自己
        if (zOffset<gameView.TopZ)
        {
            MatrixState3D.pushMatrix();
            MatrixState3D.translate(0,0,gameView.TopZ);
            MatrixState3D.rotate((float) ((mapRoateAngle)*(gameView.TopZ-zOffset)/SideLengh),1,0,0);
        }
        else
        {
            MatrixState3D.pushMatrix();
        }
        //移动到指定位置
        if (zOffset<gameView.TopZ)
        {
            MatrixState3D.translate(xOffset,0.5f*SideLengh,zOffset-gameView.TopZ);
        }
        else
        {
            MatrixState3D.translate(xOffset, 0.5f*SideLengh, zOffset);
        }
        //绕旋转轴旋转（旋转轴垂直与运动方向，平行于桌面）
        if(Math.abs(angleCurr)!=0&&(Math.abs(currAxisX)!=0||Math.abs(currAxisY)!=0||Math.abs(currAxisZ)!=0))
        {
            MatrixState3D.rotate(angleCurr, currAxisX, currAxisY, currAxisZ);
        }
        //缩放
        MatrixState3D.scale(Ratio,Ratio,Ratio);
        //绘制球
        if (secondDraw)
        {
            Constant.ci_qiu_guai.drawSelfTwo(Constant.XuHuaId);
        }
        else
        {
            Constant.ci_qiu_guai.drawSelf(Constant.ciqiuId);
        }

        MatrixState3D.popMatrix();
    }

    //球按照当前速度向前运动一步的方法
    public void go()
    {
        //记录旧位置
        float tempX=xOffset;
        float tempZ=zOffset;
        int  rh=(int) (-zOffset/SideLengh);
        int  rl=(int)(xOffset/SideLengh);
        if (gameView.al.get(rh)[rl]==Constant.iBombRange)
        {
            gameView.ciQiuGoForControlsList.remove(this);
            gameView.al.get(rh)[rl]=Constant.iBuff_gold;
            return;
        }
        //总速度
        float tempVTotal=0;
        int tempDirection=0;
        if (roateDirection==0)
        {
            tempVTotal=-vTotal;
            tempDirection=do0123(tempVTotal,tempX,tempZ);

        }
        else if (roateDirection==1)
        {
            tempVTotal=vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            tempDirection=do1230(tempVTotal,tempX,tempZ);
        }
        else if (roateDirection==2)
        {
            tempVTotal=-vTotal;
            tempDirection=do2301(tempVTotal,tempX,tempZ);
        }
        else if (roateDirection==3)
        {
            tempVTotal=vTotal;
            tempDirection=do3012(tempVTotal,tempX,tempZ);
        }
        float axisXTemp=0;//vz;
        float axisYTemp=0;//因为球在桌面上运动旋转轴平行于桌面，因此没有Y分量
        float axisZTemp=0;//-vx;
        //没有碰撞
        //计算球此步运动的距离
        float distance=(float)vTotal*TIME_SPAN;
        //计算此步滚动时绕着转的轴的向量
        if (tempDirection==0)
        {
            axisXTemp=-1;
            axisYTemp=0;
            axisZTemp=0;
        }
        else if (tempDirection==1)
        {
            axisXTemp=1;
            axisYTemp=0;
            axisZTemp=0;
        }
        else if (tempDirection==2)
        {
            axisXTemp=0;
            axisYTemp=0;
            axisZTemp=+1;
        }
        else if (tempDirection==3)
        {
            axisXTemp=0;
            axisYTemp=0;
            axisZTemp=-1;
        }
            //旋转轴向量三元组
        Vector3f tmpAxis = new Vector3f(axisXTemp, axisYTemp, axisZTemp);
        //根据运动的距离计算出球需要滚动的角度
        float tmpAngrad = distance/(SideLengh*0.5f);
        //创建临时四元数
        Quaternion tmpQuaternion = new Quaternion();
        //通过旋转轴和旋转角度设置四元数的值
        tmpQuaternion.setToRotateAboutAxis(tmpAxis, tmpAngrad);
        //将临时四元数叉乘总的四元数
        quaternionTotal = quaternionTotal.cross(tmpQuaternion);
        //获取当前总的旋转轴
        Vector3f axis = quaternionTotal.getRotationAxis();
        //获取当前总的旋转角
        float angrad = quaternionTotal.getRotationAngle();
        //将当前姿态对应的总的旋转角度与轴数据记录进成员变量
        currAxisX = axis.x;
        currAxisY = axis.y;
        currAxisZ = axis.z;
        angleCurr = (float) Math.toDegrees(angrad);
    }

    public boolean doCollision(float xOffset,float zOffset,int roateDirection)
    {
        //左上侧顶点
        float tempLUX=xOffset-SideLengh/2.5f;
        float tempLUZ=zOffset-SideLengh/2.5f;
        int LUi=(int)(-tempLUZ/SideLengh);
        int LUj=(int)(tempLUX/SideLengh);
        //左下
        float tempLDX=xOffset-SideLengh/2.5f;
        float tempLDZ=zOffset+SideLengh/2.5f;
        int LDi=(int)(-tempLDZ/SideLengh);
        int LDj=(int)(tempLDX/SideLengh);
        //右上
        float tempRUX=xOffset+SideLengh/2.5f;
        float tempRUZ=zOffset-SideLengh/2.5f;
        int RUi=(int)(-tempRUZ/SideLengh);
        int RUj=(int)(tempRUX/SideLengh);
        //右下
        float tempRDX=xOffset+SideLengh/2.5f;
        float tempRDZ=zOffset+SideLengh/2.5f;
        int RDi=(int)(-tempRDZ/SideLengh);
        int RDj=(int)(tempRDX/SideLengh);
        //中心点
        int Centerj=(int)(xOffset/SideLengh);
        int Centeri=(int)(-zOffset/SideLengh);
        if (doDeadTest(xOffset,zOffset))
        {
            currPage=OverPage;
            gameView.gameOver(Constant.iMonster);
            return true;
        }
        if (roateDirection==0)
        {
            if (LUi<gameView.al.size()&&gameView.al.get(LUi)[LUj]!= Constant.iWall&&gameView.al.get(LUi)[LUj]!= Constant.iBoxShui&&
                    gameView.al.get(LUi)[LUj]!= Constant.iBoxTnt&&gameView.al.get(LUi)[LUj]!= Constant.iBoxHuo
                    &&gameView.al.get(LUi)[LUj]!= Constant.iBox&&gameView.al.get(LUi)[LUj]!= Constant.iJiGuangGuai_down
                    &&gameView.al.get(LUi)[LUj]!= Constant.iJiGuangGuai_left&&gameView.al.get(LUi)[LUj]!= Constant.iJiGuangGuai_right
                    &&gameView.al.get(LUi)[LUj]!= Constant.iJiGuangGuai_up&&
                    gameView.al.get(RUi)[RUj]!= Constant.iWall&&gameView.al.get(RUi)[RUj]!= Constant.iBoxShui&&
                    gameView.al.get(RUi)[RUj]!= Constant.iBoxTnt&&gameView.al.get(RUi)[RUj]!= Constant.iBoxHuo
                    &&gameView.al.get(RUi)[RUj]!= Constant.iBox&&gameView.al.get(RUi)[RUj]!= Constant.iJiGuangGuai_down
                    &&gameView.al.get(RUi)[RUj]!= Constant.iJiGuangGuai_left&&gameView.al.get(RUi)[RUj]!= Constant.iJiGuangGuai_right
                    &&gameView.al.get(RUi)[RUj]!= Constant.iJiGuangGuai_up)
            {
                if(gameView.al.get(LUi)[LUj]== Constant.iBombRange||gameView.al.get(RUi)[RUj]== Constant.iBombRange)
                {
                    gameView.ciQiuGoForControlsList.remove(this);
                    return true;
                }
                for(int k=0;k<gameView.makeManDeadDropBrick.size();k++)
                {
                    if ((LUi==gameView.makeManDeadDropBrick.get(k)[0]&&LUj==gameView.makeManDeadDropBrick.get(k)[1])||
                            (RUi==gameView.makeManDeadDropBrick.get(k)[0]&&RUj==gameView.makeManDeadDropBrick.get(k)[1]))
                    {//砖块已掉落
                        gameView.ciQiuGoForControlsList.remove(this);
                        //gameView.gameOver(Constant.iDropedBrick);
                        return true;
                    }
                }
                return false;
            }
            else
            {

                return true;
            }
        }
        else if (roateDirection==1)
        {
            if (LDi<gameView.al.size()&&gameView.al.get(LDi)[LDj]!= Constant.iWall&&gameView.al.get(LDi)[LDj]!= Constant.iBoxShui&&
                    gameView.al.get(LDi)[LDj]!= Constant.iBoxTnt&&gameView.al.get(LDi)[LDj]!= Constant.iBoxHuo
                    &&gameView.al.get(LDi)[LDj]!= Constant.iBox&&gameView.al.get(LDi)[LDj]!= Constant.iJiGuangGuai_up&&
                    gameView.al.get(LDi)[LDj]!= Constant.iJiGuangGuai_right&&gameView.al.get(LDi)[LDj]!= Constant.iJiGuangGuai_left
                    &&gameView.al.get(LDi)[LDj]!= Constant.iJiGuangGuai_down&&
                    gameView.al.get(RDi)[RDj]!= Constant.iWall&&gameView.al.get(RDi)[RDj]!= Constant.iBoxShui&&
                    gameView.al.get(RDi)[RDj]!= Constant.iBoxTnt&&gameView.al.get(RDi)[RDj]!= Constant.iBoxHuo
                    &&gameView.al.get(RDi)[RDj]!= Constant.iBox&&gameView.al.get(RDi)[RDj]!= Constant.iJiGuangGuai_left&&
                    gameView.al.get(RDi)[RDj]!= Constant.iJiGuangGuai_down&&gameView.al.get(RDi)[RDj]!= Constant.iJiGuangGuai_right
                    &&gameView.al.get(RDi)[RDj]!= Constant.iJiGuangGuai_up)
            {

                if(gameView.al.get(LDi)[LDj]== Constant.iBombRange||gameView.al.get(RDi)[RDj]== Constant.iBombRange)
                {
                    gameView.ciQiuGoForControlsList.remove(this);
                    return true;
                }
                for(int k=0;k<gameView.makeManDeadDropBrick.size();k++)
                {
                    if ((LDi==gameView.makeManDeadDropBrick.get(k)[0]&&LDj==gameView.makeManDeadDropBrick.get(k)[1])||
                            (RDi==gameView.makeManDeadDropBrick.get(k)[0]&&RDj==gameView.makeManDeadDropBrick.get(k)[1]))
                    {//砖块已掉落
                        gameView.ciQiuGoForControlsList.remove(this);
                        //gameView.gameOver(Constant.iDropedBrick);
                        return true;
                    }
                }
                return  false;
            }
            else
            {
                return true;
            }
        }
        else if (roateDirection==2)
        {
            if (LDi<gameView.al.size()&&gameView.al.get(LDi)[LDj]!= Constant.iWall&&gameView.al.get(LDi)[LDj]!= Constant.iBoxShui&&
                    gameView.al.get(LDi)[LDj]!= Constant.iBoxTnt&&gameView.al.get(LDi)[LDj]!= Constant.iBoxHuo
                    &&gameView.al.get(LDi)[LDj]!= Constant.iBox&&gameView.al.get(LDi)[LDj]!= Constant.iJiGuangGuai_right&&
                    gameView.al.get(LDi)[LDj]!= Constant.iJiGuangGuai_up&&gameView.al.get(LDi)[LDj]!= Constant.iJiGuangGuai_left
                    &&gameView.al.get(LDi)[LDj]!= Constant.iJiGuangGuai_down&&
                    gameView.al.get(LUi)[LUj]!= Constant.iWall&&gameView.al.get(LUi)[LUj]!= Constant.iBoxShui&&
                    gameView.al.get(LUi)[LUj]!= Constant.iBoxTnt&&gameView.al.get(LUi)[LUj]!= Constant.iBoxHuo
                    &&gameView.al.get(LUi)[LUj]!= Constant.iBox&&gameView.al.get(LUi)[LUj]!= Constant.iJiGuangGuai_left&&
                    gameView.al.get(LUi)[LUj]!= Constant.iJiGuangGuai_down&&gameView.al.get(LUi)[LUj]!= Constant.iJiGuangGuai_right
                    &&gameView.al.get(LUi)[LUj]!= Constant.iJiGuangGuai_up)
            {
                if(gameView.al.get(LDi)[LDj]== Constant.iBombRange||gameView.al.get(LUi)[LUj]== Constant.iBombRange)
                {
                    gameView.ciQiuGoForControlsList.remove(this);
                    return  true;
                }
                for(int k=0;k<gameView.makeManDeadDropBrick.size();k++)
                {
                    if ((LDi==gameView.makeManDeadDropBrick.get(k)[0]&&LDj==gameView.makeManDeadDropBrick.get(k)[1])||
                            (LUi==gameView.makeManDeadDropBrick.get(k)[0]&&LUj==gameView.makeManDeadDropBrick.get(k)[1]))
                    {//砖块已掉落
                        gameView.ciQiuGoForControlsList.remove(this);
                        //gameView.gameOver(Constant.iDropedBrick);
                        return true;
                    }
                }
                return  false;
            }
            else
            {

                return true;
            }
        }
        else if (roateDirection==3)
        {
            if ( RUi<gameView.al.size()&&gameView.al.get(RUi)[RUj]!= Constant.iWall&&gameView.al.get(RUi)[RUj]!= Constant.iBoxShui&&
                    gameView.al.get(RUi)[RUj]!= Constant.iBoxTnt&&gameView.al.get(RUi)[RUj]!= Constant.iBoxHuo
                    &&gameView.al.get(RUi)[RUj]!= Constant.iBox&&gameView.al.get(RUi)[RUj]!= Constant.iJiGuangGuai_down&&
                    gameView.al.get(RUi)[RUj]!= Constant.iJiGuangGuai_left&&gameView.al.get(RUi)[RUj]!= Constant.iJiGuangGuai_right
                    &&gameView.al.get(RUi)[RUj]!= Constant.iJiGuangGuai_up&&
                    gameView.al.get(RDi)[RDj]!= Constant.iWall&&gameView.al.get(RDi)[RDj]!= Constant.iBoxShui&&
                    gameView.al.get(RDi)[RDj]!= Constant.iBoxTnt&&gameView.al.get(RDi)[RDj]!= Constant.iBoxHuo
                    &&gameView.al.get(RDi)[RDj]!= Constant.iBox&&gameView.al.get(RDi)[RDj]!= Constant.iJiGuangGuai_down&&
                    gameView.al.get(RDi)[RDj]!= Constant.iJiGuangGuai_up&&gameView.al.get(RDi)[RDj]!= Constant.iJiGuangGuai_right
                    &&gameView.al.get(RDi)[RDj]!= Constant.iJiGuangGuai_left)
            {
                if(gameView.al.get(RUi)[RUj]== Constant.iBombRange||gameView.al.get(RDi)[RDj]== Constant.iBombRange)
                {
                    gameView.ciQiuGoForControlsList.remove(this);
                    return  true;
                }
                for(int k=0;k<gameView.makeManDeadDropBrick.size();k++)
                {
                    if ((RUi==gameView.makeManDeadDropBrick.get(k)[0]&&RUj==gameView.makeManDeadDropBrick.get(k)[1])||
                            (RDi==gameView.makeManDeadDropBrick.get(k)[0]&&RDj==gameView.makeManDeadDropBrick.get(k)[1]))
                    {//砖块已掉落
                        gameView.ciQiuGoForControlsList.remove(this);
                        return true;
                    }
                }
                return  false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    //上下左右
    public int  do0123(float tempVTotal,float tempX,float tempZ)
    {
        boolean flag;
        if (roateDirection==0)
        {
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+0f;
            zOffset=zOffset+tempVTotal*TIME_SPAN;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=1;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==1)
        {
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+0f;
            zOffset=zOffset+tempVTotal*TIME_SPAN;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=2;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==2)
        {
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+tempVTotal*TIME_SPAN;
            zOffset=zOffset+0f;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=3;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==3)
        {
            tempVTotal=vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+tempVTotal*TIME_SPAN;
            zOffset=zOffset+0f;
            flag=doCollision(xOffset,zOffset,roateDirection);
        }
        return roateDirection;
    }
    //下左右上
    public int do1230(float tempVTotal,float tempX,float tempZ)
    {
        boolean flag;
        if (roateDirection==1)
        {
            tempVTotal=vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+0f;
            zOffset=zOffset+tempVTotal*TIME_SPAN;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=2;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==2)
        {
            tempVTotal=-vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+tempVTotal*TIME_SPAN;
            zOffset=zOffset+0f;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=3;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==3)
        {
            tempVTotal=vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+tempVTotal*TIME_SPAN;
            zOffset=zOffset+0f;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=0;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==0)
        {
            tempVTotal=-vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+0f;
            zOffset=zOffset+tempVTotal*TIME_SPAN;
            flag=doCollision(xOffset,zOffset,roateDirection);
        }
        return roateDirection;
    }
    //左右上下
    public int do2301(float tempVTotal,float tempX,float tempZ)
    {
        boolean flag;
        if (roateDirection==2)
        {
            tempVTotal=-vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+tempVTotal*TIME_SPAN;
            zOffset=zOffset+0f;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=3;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==3)
        {
            tempVTotal=vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+tempVTotal*TIME_SPAN;
            zOffset=zOffset+0f;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=0;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==0)
        {
            tempVTotal=-vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+0f;
            zOffset=zOffset+tempVTotal*TIME_SPAN;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=1;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==1)
        {
            tempVTotal=vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+0f;
            zOffset=zOffset+tempVTotal*TIME_SPAN;
            flag=doCollision(xOffset,zOffset,roateDirection);
        }
        return roateDirection;
    }
    //右上下左
    public int do3012(float tempVTotal,float tempX,float tempZ)
    {
        boolean flag;
        if (roateDirection==3)
        {
            tempVTotal=vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+tempVTotal*TIME_SPAN;
            zOffset=zOffset+0f;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=0;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==0)
        {
            tempVTotal=-vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+0f;
            zOffset=zOffset+tempVTotal*TIME_SPAN;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=1;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==1)
        {
            tempVTotal=vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+0f;
            zOffset=zOffset+tempVTotal*TIME_SPAN;
            flag=doCollision(xOffset,zOffset,roateDirection);
            if (flag==true)
            {
                roateDirection=2;
                zOffset=tempZ;
                xOffset=tempX;
            }
        }
        if (roateDirection==2)
        {
            tempVTotal=-vTotal;
            //若总速度不小于阈值，则计算球下一步的位置
            xOffset=xOffset+tempVTotal*TIME_SPAN;
            zOffset=zOffset+0f;
            flag=doCollision(xOffset,zOffset,roateDirection);

        }
        return roateDirection;
    }

    //判断人物是否死亡
    public boolean doDeadTest(float cx,float cz)
    {
        float rx=gameView.ZDRX;
        float rz=gameView.ZDRZ;
        float dx=Math.abs(cx-rx);
        float dz=Math.abs(cz-rz);
        float distince=(float) Math.sqrt(dx*dx+dz*dz);
        if (distince>(float)(SideLengh/2))
        {
            return  false;
        }
        else
        {
            return true;
        }
    }
}
