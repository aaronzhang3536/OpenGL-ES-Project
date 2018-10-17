package com.bn.ZDR.View;

import android.opengl.GLES30;
import android.view.MotionEvent;

import com.bn.ZDR.Scene.BothSideCube;
import com.bn.ZDR.Scene.Bullet;
import com.bn.ZDR.Scene.BullutForDraw;
import com.bn.ZDR.Scene.CiQiuGoForControl;
import com.bn.ZDR.Scene.Draw;
import com.bn.ZDR.Scene.DrawDead2d;
import com.bn.ZDR.Scene.DrawStart2D;
import com.bn.ZDR.Scene.MyMap;
import com.bn.ZDR.Scene.Sky;
import com.bn.ZDR.Scene.TNTBullet;
import com.bn.ZDR.Thread.BulletGoThread;
import com.bn.ZDR.Thread.CiQiuGoThread;
import com.bn.ZDR.Thread.WelcomeThread;
import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.MainActivity;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.Util.ScreenScaleUtil;
import com.bn.ZDR.Util.ScreenUtil;
import com.bn.ZDR.Util.UserInfoUtil;
import com.bn.ZDR.setCrama.SetCrama;
import com.bn.ZDR.setCrama.UpCrama;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import static com.bn.ZDR.Util.Constant.*;
import static com.bn.ZDR.Util.UserInfoUtil.WriteGamePinzhi;
import static com.bn.ZDR.Util.UserInfoUtil.WriteGameYinxiao;
import static com.bn.ZDR.Util.UserInfoUtil.WriteGameYinyue;

public class GameView extends ZDRAbstractView
{
    MyGLSurfaceView mglview;
    public MainActivity activity;
    float ratio;
    //***************炸弹人移动********************
    int keyState=0;//滑动摇杆状态值
    public float ZDRMoveX=0;//炸弹人X向移动
    public float ZDRMoveZ=0;//炸弹人Z向移动
    float ZDRLastX=SideLengh*(ZDRStartCol+ZDRMoveX+0.5f);//炸弹人移动之前的列坐标
    float ZDRLastZ=-SideLengh*(ZDRStartRow-ZDRMoveZ+0.5f);//炸弹人移动之前的行坐标
    float ZDRLastXMove;//相对于初始位置，炸弹人上一次的X向移动坐标
    float ZDRLastZMove;//相对于初始位置，炸弹人上一次的Z向移动坐标
    public float ZDRX=SideLengh*(ZDRStartCol+0.5f); //炸弹人X坐标
    public float ZDRZ=-SideLengh*(ZDRStartRow+0.5f);//炸弹人Z坐标

    //*****************地图设置***************************
    public float TopZ=-SideLengh*(ZDRStartRow-ZDRMoveZ)-SPAN;
    public List<int[]> al=new ArrayList<int[]>();
    Object lockMap=new Object();
    public int level=1;//第几关地图

    //******************炸弹*************************
    public ArrayList<Bullet> bulletAl=new ArrayList<Bullet>();//炮弹列表
    public ArrayList<TNTBullet> tnt_bulletAl=new ArrayList<TNTBullet>();
    BulletGoThread bulletGoThread;
    BullutForDraw bullutForDraw;

    //*******************刺球***************************
    public ArrayList<CiQiuGoForControl> ciQiuGoForControlsList =new ArrayList<CiQiuGoForControl>();
    CiQiuGoThread ciQiuGoThread;

    //******************2d的内容************************
    Draw draw;//绘制游戏界面2D内容
    DrawDead2d drawDead2d;
    DrawStart2D drawStart2D;
    Sky sky;

    //*****************转动金币************************
    ManControlThread goldRoateThread=new ManControlThread();
    public static float roateAngle=0;


    DropBrickThread dropBrickThread=new DropBrickThread();
    public  Object lock=new Object();//砖块掉落数组的同步锁
    int dropThreadTime=0;
    public int curr_drop_row=0;//当前掉落的第几行
    int curr_drop_sum=0;//当前行已掉落数目
    int startSecondDropNum=5;//开始掉落第二行时第一行的掉落数阀值
    public int shakeNum=24;//砖块掉落前的抖动次数，一个方向算一次
    public float shakeStep=0.025f*SideLengh;//抖动幅度
    public boolean ifDrop=false;//某块是否掉落
    //表示正在掉落的砖块<掉落砖块的位置坐标[i,j],掉落砖块的状态[抖动方向/抖动次数/掉落高度]>
    //抖动方向%4=0,1,2,3上下左右，抖动次数>5次后开始掉落，后面的掉落高度表示其落下的
    public ArrayList<int []> dropBrickLList=new ArrayList<int []>();//正在掉落的砖块列表
    public ArrayList<int []> needDeleteDropBrickList = new ArrayList<int[]>();//暂存需要删除的掉落砖块
    public ArrayList<int []> makeManDeadDropBrick=new ArrayList<int[]>();//可以使人物死亡的掉落砖块的位置索引
    ArrayList<int []> deletemakeManDeadDropBrickList=new ArrayList<int[]>();

    //*******************欢迎界面**********************/
    public WelcomeThread welcomeThread;

    //********************关于相机*********************
    SetCrama setCrama;

    //**********************骨骼动画********************/
    float ggdhRoateAngle=0;

    //***********************金币连吃******************/
    long lastEatGoldTime=0;
    Timer controlRunEatGoldT=new Timer();

    //*********************激光发射控制*****************/
    JiGuangThread jiGuangThread=new JiGuangThread();
    public boolean ifJiGuang=true;//是否发射激光

    //***********************两边摄像机***************/
    BothSideCube bothSideCube;
    //************************地图绘制**********************/
    MyMap myMap;

    /*炸弹人停止走动时时刻*/
    public long LastRunTime=System.nanoTime();


    public  GameView(MyGLSurfaceView mglview, MainActivity activity)
    {
        this.mglview=mglview;
        this.activity=activity;
        initView();
    }


    //**********************************************************初始化各种数据***********************************
    public void initView()
    {
        /***地图*/
        initMap();
        /***2d*/
        draw=new Draw(mglview,this);//画活着的2d
        drawDead2d=new DrawDead2d(mglview,this);//画死了的2d
        drawStart2D=new DrawStart2D(this,mglview);//最新开始2d
        /**炸弹*/
        bullutForDraw=new BullutForDraw(mglview,this);//画炸弹
        /***线程*/
        bulletGoThread=new BulletGoThread(bulletAl,tnt_bulletAl);
        ciQiuGoThread=new CiQiuGoThread(ciQiuGoForControlsList,this);
        welcomeThread=new WelcomeThread(this);
        //线程启动
        welcomeThread.start();
        jiGuangThread.start();
        goldRoateThread.start();
        dropBrickThread.start();
        bulletGoThread.start();//炮弹检测线程
        ciQiuGoThread.start();
        /***天空盒*/
        sky=new Sky(mglview,this);
        //关于相机
        setCrama=new SetCrama(this,mglview);
        /**两边正方形*/
        bothSideCube=new BothSideCube(mglview,this);
        activity.resumeBGM();//背景音乐
        //drawTexiao=new DrawTexiao(mglview,this);
        myMap=new MyMap(this,mglview);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        float y = e.getY();
        float x = e.getX();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN://按下动作
                /***炸弹*/
                if (x>commonBomb_left&&x<commonBomb_right&&y<commonBomb_bottom&&y>commonBomb_top&&currPage==GamePage)
                {
                    activity.playEQ(2,0);
                    this.bullutForDraw.fire(ZDRX,ZDRZ);
                }
                else if (x>tNtBomb_left&&x<tNtBomb_right&&y>tNtBomb_top&&y<tNtBomb_bottom&&tntNum>0&&currPage==GamePage)
                {
                    tntNum--;
                    activity.playEQ(2,0);
                    this.bullutForDraw.tnt_fire(ZDRX,ZDRZ);
                }
                /****复活*/
                else if(x>relive_left&&x<relive_right&&y>relive_top&&y<relive_bottom&&currPage==OverPage)
                {
                    if (roatecrama)
                    {
                        relive();//复活
                    }
                }
                /***重新开始*/
                else if(x>restart_left&&x<restart_right&&y>restart_top&&y<restart_bottom&&currPage==OverPage)
                {
                    if (roatecrama)
                    {
                        activity.pauseBGM();
                        activity.nowBGM=activity.openBGM;
                        activity.resumeBGM();
                        restart();
                    }
                }
                /**点击开始*/
                else if (x>SCREEN_WIDTH/2-SCREEN_WIDTH/4&&x<SCREEN_WIDTH/2+SCREEN_WIDTH/4&&y>SCREEN_HEIGHT/2-SCREEN_HEIGHT/4&&y<SCREEN_HEIGHT/2+SCREEN_HEIGHT/4&&currPage==OpenPage)
                {
                    currPage=GamePage;
                    WelcomeThreadFlag=false;
                    upcrama=true;
                    SPAN=GameSPAN;//10;
                    dropLength=GameDropLength;//10;
                    BulletGoThreadFlag=true;
                    GoldRoateThreadFlag=true;
                    CiQiuGoThreadFlag=true;
                    DropBrickThreadFlag=true;
                    JiGuangThreadFlag=true;
                    blurPosition=UPBlurPositon;
                    activity.pauseBGM();
                    activity.nowBGM=activity.gameBGM;
                    activity.resumeBGM();
                }
                else if(x>chart_left&&x<chart_right&&y>chart_top&&y<chart_bottom&&currPage==OpenPage)//排行榜
                {
                    currPage=ChartPage;//进入排行榜界面
                    activity.playEQ(3,0);//按钮声音
                }
                /**设置*/
                else if(x>set_left&&x<set_right&&y>set_top&&y<set_bottom&&currPage==OpenPage)
                {
                    currPage=SetPage;//进入设置界面
                    activity.playEQ(3,0);//按钮声音
                }
                else if(currPage==SetPage&&x>setPagePinZhi_left&&x<setPagePinZhi_right&&y>setPagePinZhi_up&&y<setPagePinZhi_down)//设置游戏品质
                {
                    if (ifXuHua)
                    {
                        ifXuHua=false;
                    }
                    else
                    {
                        ifXuHua=true;
                    }
                    WriteGamePinzhi(ifXuHua);
                    activity.playEQ(3,0);//按钮声音
                }
                else if(currPage==SetPage&&x>setPageUpYes_left&&x<setPageUpYes_right&&y>setPageUpYes_up&&y<setPageUpYes_down)//设置游戏音乐
                {
                    WriteGameYinyue();
                    activity.resumeBGM();//背景音乐
                    activity.playEQ(3,0);//按钮声音
                }
                else if(currPage==SetPage&&x>setPageDownYes_left&&x<setPageDownYes_right&&y>setPageDownYes_up&&y<setPageDownYes_down)//设置游戏音效
                {
                    WriteGameYinxiao();
                    activity.playEQ(3,0);//按钮声音
                }
                else if((currPage==ChartPage||currPage==SetPage)&&x>setPageBack_left&&x<setPageBack_right&&y>setPageBack_up&&y<setPageBack_down)//返回按钮
                {
                    currPage=OpenPage;
                    hadDrawStart2D=false;
                    hadDrawChart2D=false;
                    hadDrawSet2D=false;
                    hadDrawStart2DTime=0;
                    hadDrawChart2DTiem=0;
                    hadDrawSet2DTime=0;
                    activity.playEQ(3,0);//按钮声音
                }
                else if(x>pauseleft&&x<pauseright&&y>pauseup&&y<pausedown&&currPage==GamePage)//暂停
                {
                    GoldRoateThreadFlag =false;
                    BulletGoThreadFlag=false;
                    DropBrickThreadFlag=false;
                    CiQiuGoThreadFlag =false;
                    JiGuangThreadFlag =false;
                    currPage=PausePage;
                }
                else  if (x>resumeleft&&x<resumeright&&y>resumeup&&y<resumedown&&currPage==PausePage)//继续
                {
                    GoldRoateThreadFlag =true;
                    BulletGoThreadFlag=true;
                    DropBrickThreadFlag=true;
                    CiQiuGoThreadFlag =true;
                    JiGuangThreadFlag =true;
                    currPage=GamePage;
                }
                else if (x>overleft&&x<overright&&y>overup&&y<overdown&&currPage==PausePage)//重新开始
                {
                    restart();
                }

            case MotionEvent.ACTION_MOVE:
                if(y> Constant.Jk_big_top&&y<Constant.JK_big_yuanxinY&&x>(Constant.JK_big_radius/2-(JK_big_yuanxinY-y))&&x<(Constant.JK_big_radius/2+(JK_big_yuanxinY-y))&&CanTouch&&currPage==GamePage)
                //按下前进虚拟按钮
                {
                    keyState=1;
                    Constant.JK_small_yuanxin_Current_X=Constant.JK_small_yuanxin_top_X;
                    Constant.Jk_small_yuanxin_Current_Y=Constant.JK_small_yuanxin_top_Y;
                }
                else if(y>JK_big_yuanxinY&&y<Jk_big_bottom&&x>(Constant.JK_big_radius/2-(-JK_big_yuanxinY+y))&&x<(Constant.JK_big_radius/2+(-JK_big_yuanxinY+y))&&CanTouch&&currPage==GamePage)
                {//按下后退虚拟按钮;
                    keyState=2;
                    Constant.JK_small_yuanxin_Current_X=Constant.JK_small_yuanxin_bottom_X;
                    Constant.Jk_small_yuanxin_Current_Y=Constant.JK_small_yuanxin_bottom_Y;
                }
                else if (x>Constant.JK_big_left&&x<JK_big_yuanxinX&&y>(Constant.JK_big_yuanxinY-(JK_big_radius/2-x))&&y<(Constant.JK_big_yuanxinY+(JK_big_radius/2-x))&&CanTouch&&currPage==GamePage)
                {//向左
                    keyState=3;
                    Constant.JK_small_yuanxin_Current_X=Constant.JK_small_yuanxin_left_X;
                    Constant.Jk_small_yuanxin_Current_Y=Constant.JK_small_yuanxin_left_Y;
                }
                else if (x>JK_big_yuanxinX&&x<JK_big_right&&y>(Constant.JK_big_yuanxinY-(-JK_big_radius/2+x))&&y<(Constant.JK_big_yuanxinY+(-JK_big_radius/2+x))&&CanTouch&&currPage==GamePage)
                {//向右
                    keyState=4;
                    Constant.JK_small_yuanxin_Current_X=Constant.JK_small_yuanxin_right_X;
                    Constant.Jk_small_yuanxin_Current_Y=Constant.JK_small_yuanxin_right_Y;
                }
                else
                {
                    keyState=5;
                    Constant.JK_small_yuanxin_Current_X=JK_big_yuanxinX;
                    Constant.Jk_small_yuanxin_Current_Y=JK_big_yuanxinY;
                }
                break;
            case MotionEvent.ACTION_UP://抬起动作
                keyState=6;
                Constant.JK_small_yuanxin_Current_X=JK_big_yuanxinX;
                Constant.Jk_small_yuanxin_Current_Y=JK_big_yuanxinY;
                break;
        }
        return true;
    }
    @Override
    public void drawView(GL10 gl)
    {
        if (ifXuHua)
        {
            generateTextImage();
            drawBlur();
        }
        else
        {
            setMatrixAndCamera();//设置投影矩阵及摄像机位置
            synchronized (lockMap)
            {
               // paintMap(false);//绘制地图
                myMap.paintMap(false);
            }
            if (drawHuman)
            {
                paintZhaDanRen(false);//绘制人物
                CanTouch=true;
            }
            else
            {
               reliveSetMap();
            }
            drawBullet();//画炮弹
            drawTnt();//画tnt
            drawCiQiu(false);//画刺球
            drawCube(false);
            drawDownRect();
            drawSky();//天空盒
            setCrama.setCrama();//设置相机
        }

        if (currPage==OverPage)
        {
            zdrDead();
            drawDead2d.drawSelf();
        }
        else if (currPage==GamePage||currPage==PausePage)
        {
            draw.drawSelf();//绘制2D
        }
        else if (currPage==OpenPage||currPage==ChartPage||currPage==SetPage)
        {
            drawStart2D.drawSelf();
        }

    }

    public void generateTextImage()//通过绘制产生纹理
    {
        //设置视窗大小及位置
        GLES30.glViewport(0, 0, GEN_TEX_WIDTH, GEN_TEX_HEIGHT);
        //绑定帧缓冲id
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBufferId);
        //清除深度缓冲与颜色缓冲
        GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        setMatrixAndCamera();//设置投影矩阵及摄像机位置
        synchronized (lockMap)
        {
            myMap.paintMap(false);//绘制地图
        }
        drawCiQiu(false);//画刺球
        drawCube(false);
        //drawDownRect();
        drawSky();//天空盒
        //关于相机
        setCrama.setCrama();
    }

    public void drawBlur()
    {
        //设置视窗大小及位置
        GLES30.glViewport(0,0,(int)SCREEN_WIDTH,(int)SCREEN_HEIGHT);
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0);//绑定帧缓冲id
        //清除深度缓冲与颜色缓冲
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT |GLES30.GL_COLOR_BUFFER_BIT);
        setMatrixAndCamera();//设置投影矩阵及摄像机位置
        synchronized (lockMap)
        {
            myMap.paintMap(true);//绘制地图
        }
        if (drawHuman)
        {
            paintZhaDanRen(false);//绘制人物
            CanTouch=true;
        }
        else
        {
           reliveSetMap();
        }
        drawBullet();//画炮弹
        drawTnt();//画tnt
        drawCiQiu(true);//画刺球
        drawCube(true);
        drawDownRect();
        drawSky();//天空盒
        setCrama.setCrama();//关于相机
    }

    //*******************************************初始化地图和复原地图**********************************************************************************
    //初始化地图
    public void initMap()
    {
        for (int i=0;i<(int)(SPAN*SideLengh+ZDRStartRow*SideLengh+dropLength*SideLengh)/SideLengh;i++)
        {
            int tempMap[]=new int[MapWidth];
            if (i<OneMapLength)
            {
                for (int j=0;j<Constant.MapWidth;j++)
                {
                    tempMap[j]=WhichMap.get(1)[i][j];
                }
                al.add(tempMap);
            }
            else
            {
                for (int j=0;j<MapWidth;j++)
                {
                    tempMap[j]=WhichMap.get(2)[i-OneMapLength][j];
                }
                al.add(tempMap);
            }
        }
    }
    //恢复地图
    public void recoverMap()
    {
        synchronized (lockMap)
        {
            while (al.size()>(int)(SPAN*SideLengh+ZDRStartRow*SideLengh+dropLength*SideLengh)/SideLengh)
            {
                al.remove(al.size()-1);
            }
        }
        WhichMap.clear();
        WhichMap.put(1,FirstLevelMap);
        WhichMap.put(2,SecondLevelMap);
        for (int i=0;i<(int)(SPAN*SideLengh+ZDRStartRow*SideLengh+dropLength*SideLengh)/SideLengh;i++)
        {
            int tempMap[]=new int[MapWidth];
            if (i<OneMapLength)
            {
                for (int j=0;j<MapWidth;j++)
                {
                    tempMap[j]=WhichMap.get(1)[i][j];
                }
                if (i>=al.size())
                {
                    al.add(tempMap);
                }
                else
                {
                    for (int z=0;z<MapWidth;z++)
                    {
                        al.get(i)[z]=tempMap[z];
                    }
                }
            }
            else
            {
                for (int j=0;j<MapWidth;j++)
                {
                    tempMap[j]=WhichMap.get(2)[i-OneMapLength][j];
                }
                if (i>=al.size())
                {
                    al.add(tempMap);
                }
                else
                {
                    for (int z=0;z<MapWidth;z++)
                    {
                        al.get(i)[z]=tempMap[z];
                    }
                }
            }
        }
    }
    //复活对地图操作
    public void reliveSetMap()
    {
        if(System.nanoTime()-lastDrawHumanTime>1000000000l)
        {
            int tempRow=(int)(ZDRZ/SideLengh);
            for (int i=-tempRow-bombrange;i<=-tempRow+bombrange;i++)
            {
                for (int j=0;j<MapWidth;j++)
                {
                    if (i<0)
                    {
                        continue;
                    }
                    else
                    {
                        if (al.get(i)[j]!=Constant.iWall&&al.get(i)[j]!=Constant.iBuff_gold&&al.get(i)[j]!=Constant.iTnt_food&&
                                al.get(i)[j]!=Constant.iShui_food&&al.get(i)[j]!=Constant.iHuo_food&&al.get(i)[j]!=iKongBai)
                        {
                            al.get(i)[j]=99;
                        }
                    }
                }
            }
            MatrixState3D.pushMatrix();
            MatrixState3D.translate(ZDRX,SideLengh*1.2f,ZDRZ);
            MatrixState3D.rotate(-90,0,1,0);
            MatrixState3D.scale(Ratio,Ratio,Ratio);
            Constant.relive.draw();
            MatrixState3D.popMatrix();
            if (System.nanoTime()-lastDrawHumanTime>2000000000l)
            {
                lastDrawHumanTime=0;
                drawHuman=true;
                ifrelive=false;
            }

        }
    }


    //*********************************************死亡复活和重新开始*****************************************************************************
    //死亡
    public void zdrDead()
    {
        if(currPage==OverPage)
        {
            GoldRoateThreadFlag =false;
            //BulletGoThreadFlag=false;
            DropBrickThreadFlag=false;
            CiQiuGoThreadFlag =false;
            JiGuangThreadFlag =false;
            blurPosition=RoateBlurPosition;
        }
        if (updateChart)
        {
            UserInfoUtil.CompareSorce(get_score);
            updateChart=false;
        }
        manGoOrNot=false;
    }
    //炸弹人复活
    public void relive()
    {
        if (reliveTime==0&&goldSum>40)
        {
            goldSum-=40;
            reliveTime++;
            reliveSet();
        }
        else if (reliveTime==1&&goldSum>200)
        {
            goldSum-=200;
            reliveTime++;
            reliveSet();
        }
        else if (reliveTime>=2&&goldSum>1000)
        {
            goldSum-=1000;
            reliveTime++;
            reliveSet();
        }
        blurPosition=UPBlurPositon;
        CanTouch=false;
        updateChart=true;
    }
    //复活设置
    public void reliveSet()
    {
        UserInfoUtil.WriteGold(goldSum);
        BulletGoThreadFlag=true;
        DropBrickThreadFlag=true;
        GoldRoateThreadFlag =true;
        JiGuangThreadFlag =true;
        CiQiuGoThreadFlag =true;
        currPage=GamePage;
        drawHuman=false;
        lastDrawHumanTime=System.nanoTime();
        if (humanDeadWay==Constant.iDropedBrick)
        {
            ZDRZ=ZDRZ-3*SideLengh;
            ZDRMoveZ+=3*SideLengh;
            int rows=(int)-(ZDRZ/SideLengh);
            int cols=(int)(ZDRX/SideLengh);
            al.get(rows)[cols]=99;
        }
        humanDeadWay=0;
        int tempRow=(int)(ZDRZ/SideLengh);
        for (int i=-tempRow-bombrange;i<=-tempRow+bombrange;i++)
        {
            for (int j=0;j<MapWidth;j++)
            {
                if (i<0)
                {
                    continue;
                }
                else
                {
                    if (al.get(i)[j]!=Constant.iWall&&al.get(i)[j]!=Constant.iBuff_gold&&al.get(i)[j]!=Constant.iTnt_food&&
                            al.get(i)[j]!=Constant.iShui_food&&al.get(i)[j]!=Constant.iHuo_food&&al.get(i)[j]!=iKongBai)
                    {
                        al.get(i)[j]=Constant.iBombRange;
                    }
                }
            }
        }
        ifrelive=true;
        //设置相机
        roatecrama=false;
        roatecramaTime=0;
        dietoaliveUpCrama=true;
        hasdowncrama=false;
        //画2d
        hadDraw=false;
        drawTime=0;
        hadDrawDead2D=false;
        drawDead2DTime=0;
    }
    //重新开始
    public void restart()
    {
        reliveTime=0;
//        drawMap=false;
//        ifrestart=true;
        get_score=0;
        score=0;
        ZDRX=SideLengh*(ZDRStartCol+0.5f); //炸弹人X坐标
        ZDRZ=-SideLengh*(ZDRStartRow+0.5f);//炸弹人Z坐标
        ZDRMoveZ=0;
        ZDRMoveX=0;
        huoNum=0;
        shuiNum=0;
        tntNum=0;//12
        SPAN=OriginSPAN;
        dropLength=OriginDropLength;
        TopZ=-SideLengh*(ZDRStartRow-ZDRMoveZ)-SPAN;
        curr_drop_row=0;
        curr_drop_sum=0;
        bulletAl.clear();
        tnt_bulletAl.clear();
        synchronized (lock) {
            dropBrickLList=null;
            dropBrickLList=new ArrayList<int[]>();
        }
        makeManDeadDropBrick.clear();
        ciQiuGoForControlsList.clear();
        /**2d*/
        hadDraw=false;
        drawTime=0;
        hadDrawDead2D=false;
        drawDead2DTime=0;
        /**初始化地图*/
        blurPosition=DownBlurPosition;
        drawMap=false;
        recoverMap();
        currPage=OpenPage;
        keyState=0;
        roatecrama=false;
        roatecramaTime=0;
        recovercrama=true;
        recovercramaTime=0;
        hasdowncrama=false;
        updateChart=true;
    }

    //****************************************************************设置相机********************************************
    //设置投影矩阵及摄像机位置
    //设置投影矩阵及摄像机位置
    public void setMatrixAndCamera()
    {
        ratio = SCREEN_HEIGHT/ SCREEN_WIDTH;         //计算GLSurfaceView的宽高比
        if (currPage==OpenPage||currPage==ChartPage||currPage==SetPage)
        {
            MatrixState3D.setProjectFrustum(-0.4f,0.4f,-0.4f*ratio,0.4f*ratio,1,100);  //调用此方法计算产生透视投影矩阵
            MatrixState2D.setProjectOrtho(-1,1,-ratio,ratio,1,100);//调用此方法计算产生正视投影矩阵
            MatrixState3D.setCamera(Scx,Scy,Scz,Stx,Sty,Stz,Supx,Supy,Supz);   //设置摄像机位置
            MatrixState2D.setCamera(0.0f, 0.0f,3f, 0.0f, 0.0f, 0.0f, 0.0f,1.0f, 0.0f);
        }
        else
        {
            MatrixState3D.setProjectFrustum(-0.3f,0.3f,-0.3f*ratio,0.3f*ratio,1,100);  //调用此方法计算产生透视投影矩阵
            MatrixState2D.setProjectOrtho(-1,1,-ratio,ratio,1,100);//调用此方法计算产生正视投影矩阵
            MatrixState3D.setCamera(Scx,Scy,Scz,Stx,Sty,Stz,Supx,Supy,Supz);   //设置摄像机位置
            MatrixState2D.setCamera(0.0f, 0.0f,3f, 0.0f, 0.0f, 0.0f, 0.0f,1.0f, 0.0f);
        }
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT|GLES30.GL_COLOR_BUFFER_BIT);//清除深度缓存及颜色缓存
    }


    //****************************************************************各种需要画的实物*************************************
    //画刺球
    public void drawCiQiu(boolean secondDraw)
    {
        MatrixState3D.pushMatrix();
        try
        {
            //绘制炮弹
            for(CiQiuGoForControl ciQiuGoForControl: ciQiuGoForControlsList)
            {
                ciQiuGoForControl.drawSelf(secondDraw);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        MatrixState3D.popMatrix();
    }
    //画天空盒
    public void drawSky()
    {
        GLES30.glDisable(GLES30.GL_CULL_FACE);       //打开背面剪裁
        GLES30.glEnable(GLES30.GL_BLEND);			//开启混合
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,	//设置混合因子
                GLES30.GL_ONE_MINUS_SRC_ALPHA);
        sky.drawSelf();
        GLES30.glDisable(GLES30.GL_BLEND);
        GLES30.glEnable(GLES30.GL_CULL_FACE);       //打开背面剪裁
    }
    //画普通炸弹
    public void drawBullet()
    {
        MatrixState3D.pushMatrix();
        try
        {
            //绘制炮弹
            for(Bullet b:bulletAl)
            {
                b.drawSelf();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        MatrixState3D.popMatrix();
    }
    //画tnt炸弹
    public void drawTnt()
    {
        MatrixState3D.pushMatrix();
        try
        {
            //绘制炮弹
            for(TNTBullet tnt_bullet:tnt_bulletAl)
            {
                tnt_bullet.drawSelf();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        MatrixState3D.popMatrix();
    }
    //画人脚下红框
    public void drawDownRect()
    {
        //画人脚下红框
        if (manGoOrNot)
        {
            GLES30.glEnable(GLES30.GL_BLEND);			//开启混合
            GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,	//设置混合因子
                    GLES30.GL_ONE_MINUS_SRC_ALPHA);
            MatrixState3D.pushMatrix();
            MatrixState3D.translate((int)(ZDRX/SideLengh)+0.5f,0.1f,(int)(ZDRZ/SideLengh)-0.5f);
            MatrixState3D.scale(0.5f,0.5f,0.5f);
            mandownRect.drawSelf(manDownRectId);
            MatrixState3D.popMatrix();
            GLES30.glDisable(GLES30.GL_BLEND);
        }
    }
    //绘制炸弹人
    void paintZhaDanRen(boolean secondDraw)
    {
        if (currPage==OpenPage||currPage==ChartPage||currPage==SetPage)//开场动画
        {
            MatrixState3D.pushMatrix();
            MatrixState3D.translate(ZDRX,0,ZDRZ);
            MatrixState3D.rotate(-90,0,1,0);
            MatrixState3D.scale(Ratio,Ratio,Ratio);
            if (!secondDraw)
            {
                Constant.OpenAction.draw();
            }
            MatrixState3D.popMatrix();
        }
        else
        {
            if(humanDeadWay!=0&&currPage==OverPage)//炸弹人死亡
            {
                switch (humanDeadWay) {
                    case iBombRange:    //被炸弹炸死
                    case iMonster:      //被怪物打死
                    case iJiGuangRange://激光射死
                        MatrixState3D.pushMatrix();
                        MatrixState3D.translate(ZDRX, 0, ZDRZ);
                        MatrixState3D.rotate(-90, 0, 1, 0);
                        MatrixState3D.scale(Ratio, Ratio, Ratio);
                        if (Constant.dead.getTime() > Constant.dead.getOnceTime() * 0.5f) {
                            Constant.dead.setTime(Constant.dead.getOnceTime() * 0.5f);
                        }
                        Constant.dead.draw();
                        MatrixState3D.popMatrix();
                        break;
                    case iDropedBrick:  //已经掉落的砖块
                        MatrixState3D.pushMatrix();
                        MatrixState3D.translate(ZDRX, 0, ZDRZ);
                        MatrixState3D.rotate(-90, 0, 1, 0);
                        MatrixState3D.scale(Ratio, Ratio, Ratio);
                        if (Constant.dead.getTime() > Constant.dead.getOnceTime() * 0.9f) {
                            Constant.dead.setTime(Constant.dead.getOnceTime() * 0.9f);
                        }
                        Constant.dead.draw();
                        MatrixState3D.popMatrix();
                        break;
                }
                return;
            }

                //绘制人物
            if(keyState==1||keyState==2||keyState==4||keyState==3)
            {
               // activity.playEQ(5,1);
                MatrixState3D.pushMatrix();
                MatrixState3D.translate(ZDRX,0,ZDRZ);
                MatrixState3D.rotate(ggdhRoateAngle,0,1,0);
                //MatrixState3D.scale(Ratio*0.887f,Ratio*1.008f,Ratio*0.7718f);
                MatrixState3D.scale(Ratio*0.85f,Ratio*0.9f,Ratio*0.7718f);
                if (!secondDraw)
                {
                    Constant.zhadanrenModel.draw();
                }
                MatrixState3D.popMatrix();
                manGoOrNot=false;
                LastRunTime=System.nanoTime();
            }
            else
            {
                manGoOrNot=true;
                if (System.nanoTime()-LastRunTime>=1000000000l)
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate((int)ZDRX/SideLengh+0.5f,0,(int)ZDRZ/SideLengh-0.5f);
                    MatrixState3D.rotate(-90,0,1,0);
                    MatrixState3D.scale(Ratio,Ratio,Ratio);
                    Constant.UpHead.draw();
                    MatrixState3D.popMatrix();
                }
                else
                {
                    MatrixState3D.pushMatrix();
                    MatrixState3D.translate((int)ZDRX/SideLengh+0.5f,0,(int)ZDRZ/SideLengh-0.5f);
                    MatrixState3D.rotate(ggdhRoateAngle,0,1,0);
                    MatrixState3D.scale(Ratio,Ratio,Ratio);
                    if (secondDraw)
                    {
                        Constant.zhadanren.drawSelfTwo(Constant.XuHuaId);
                    }
                    else
                    {
                        Constant.zhadanren.drawSelf(Constant.ZhaDanRenTextureId);
                    }

                    MatrixState3D.popMatrix();
                }
                ZDRX=(int)ZDRX/SideLengh+0.5f;
                ZDRZ=(int)ZDRZ/SideLengh-0.5f;
                ZDRMoveX=(int)ZDRX/SideLengh-ZDRStartCol;
                ZDRMoveZ=-(int)ZDRZ/SideLengh-ZDRStartRow;
            }
        }
        collisionTest(1);
        ZDRLastX=ZDRX;
        ZDRLastZ=ZDRZ;
        ZDRLastXMove=ZDRMoveX;
        ZDRLastZMove=ZDRMoveZ;
    }

    //两边正方形
    void drawCube(boolean secondDraw)
    {
        bothSideCube.drawSelf(secondDraw);
    }

    //********************************************************************碰撞和逻辑********************************************
    //碰撞检测
    void collisionTest(int testType)//碰撞检测类型，0为提前检测是否可以通过，1为检测人物所在位置是否有可碰撞可吃东西
    {
        if(testType==0)
        {
            ZDRX=SideLengh*(ZDRStartCol+ZDRMoveX+0.5f);
            ZDRZ=-SideLengh*(ZDRStartRow+ZDRMoveZ+0.5f);
        }
        /***碰撞点行列值***/
        float tempx;
        float tempz;
        int i;
        int j;
        //人物左上侧点是否碰撞
        tempx=ZDRX-SideLengh/4;
        tempz=-(ZDRZ-SideLengh/4);
        i=(int)(tempz/SideLengh);
        j=(int)(tempx/SideLengh);
        if((int)(tempz/SideLengh)-(tempz/SideLengh)==0)
        {
            i=(int)(tempz/SideLengh)-1;
        }
        if((int)(tempx/SideLengh)-(tempx/SideLengh)==0)
        {
            j=(int)(tempx/SideLengh);
        }
        doCollision(i,j,testType);

        //人物左下侧点是否碰撞
        tempx=ZDRX-SideLengh/4;
        tempz=-(ZDRZ+SideLengh/4);
        i=(int)(tempz/SideLengh);
        j=(int)(tempx/SideLengh);
        if((int)(tempz/SideLengh)-(tempz/SideLengh)==0)
        {
            i=(int)(tempz/SideLengh);
        }
        if((int)(tempx/SideLengh)-(tempx/SideLengh)==0)
        {
            j=(int)(tempx/SideLengh);
        }
        doCollision(i,j,testType);

        //人物右下侧点是否碰撞
        tempx=ZDRX+SideLengh/4;
        tempz=-(ZDRZ+SideLengh/4);
        i=(int)(tempz/SideLengh);
        j=(int)(tempx/SideLengh);
        if((int)(tempz/SideLengh)-(tempz/SideLengh)==0)
        {
            i=(int)(tempz/SideLengh);
        }
        if((int)(tempx/SideLengh)-(tempx/SideLengh)==0)
        {
            j=(int)(tempx/SideLengh)-1;
        }
        doCollision(i,j,testType);
        //人物右上侧点是否碰撞
        tempx=ZDRX+SideLengh/4;
        tempz=-(ZDRZ-SideLengh/4);
        i=(int)(tempz/SideLengh);
        j=(int)(tempx/SideLengh);
        if((int)(tempz/SideLengh)-(tempz/SideLengh)==0)
        {
            i=(int)(tempz/SideLengh)-1;
        }
        if((int)(tempx/SideLengh)-(tempx/SideLengh)==0)
        {
            j=(int)(tempx/SideLengh)-1;
        }
        doCollision(i,j,testType);
    }

    //处理碰撞逻辑
    void doCollision(int i, int j, int testType)
    {
        if(testType==0)
        {
            switch(al.get(i)[j])
            {
                case Constant.iWall://墙体
                case Constant.iBoxTnt:
                case Constant.iBoxShui:
                case Constant.iBoxHuo:
                case Constant.iBox://箱子
                case Constant.iJiGuangGuai_down:
                case Constant.iJiGuangGuai_right:
                case Constant.iJiGuangGuai_up:
                case Constant.iJiGuangGuai_left://激光怪发射体
                    ZDRX=ZDRLastX;
                    ZDRZ=ZDRLastZ;
                    ZDRMoveX=ZDRLastXMove;
                    ZDRMoveZ=ZDRLastZMove;
                    break;
            }
        }
        else
        {
            //System.out.println("al="+al.size()+"   dropRow="+curr_drop_row+"  make="+makeManDeadDropBrick.size()+"  drop="+dropBrickLList.size()
           // +"");
            while (true)
            {
                if(makeManDeadDropBrick.size()>DropOrder.length*2)
                {
                    makeManDeadDropBrick.remove(0);
                }
                else
                {
                    break;
                }
            }
            for(int k=0;k<makeManDeadDropBrick.size();k++)
            {
                if (i==makeManDeadDropBrick.get(k)[0]&&j==makeManDeadDropBrick.get(k)[1])
                {//砖块已掉落
                    gameOver(Constant.iDropedBrick);
                    return;
                }
            }
            switch(al.get(i)[j])
            {
                case Constant.iBuff_gold://金币buff
                    activity.playEQ(4,0);
                    al.get(i)[j]=99;
                    goldSum++;
                    if(System.nanoTime()-lastEatGoldTime<3000000000l)
                    {
                        runGoldSum++;
                        ifShowRuneatGold=true;
                        if (controlRunEatGoldT!=null)
                        {
                            controlRunEatGoldT.cancel();
                            controlRunEatGoldT.purge();
                        }
                        controlRunEatGoldT=new Timer();
                        controlRunEatGoldT.schedule(new ControlRunEatGoldTT(),1000);
                    }
                    else
                    {
                        runGoldSum=1;
                    }
                    lastEatGoldTime=System.nanoTime();
                    break;
                case  Constant.iTnt_food:
                    al.get(i)[j]=99;
                    drawFlyTnt=true;
                    float tnt_xyz[]= ScreenScaleUtil.fromRowColToXYZ(i,j,0.2f);
                    float tnt_number[]=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.tnt_number_X, Constant.tnt_number_Y);
                    tnt_current_Position= ScreenUtil.getscreen(ratio,tnt_xyz[0],tnt_xyz[1],tnt_xyz[2]);
                    tnt_distinceX=tnt_number[0]-tnt_current_Position[0];
                    tnt_distinceY=tnt_number[1]-tnt_current_Position[1];
                    break;
                case Constant.iShui_food:
                    al.get(i)[j]=99;
                    drawFlyShui=true;
                    float shui_xyz[]= ScreenScaleUtil.fromRowColToXYZ(i,j,0.2f);
                    float shui_number[]=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.Shui_start_X+Constant.Shui_kuandu*5/4*(shuiNum+1), Constant.Shui_start_Y);
                    shui_current_Position= ScreenUtil.getscreen(ratio,shui_xyz[0],shui_xyz[1],shui_xyz[2]);
                    shui_distinceX=shui_number[0]-shui_current_Position[0];
                    shui_distinceY=shui_number[1]-shui_current_Position[1];
                    break;
                case Constant.iHuo_food:
                    al.get(i)[j]=99;
                    drawFlyHuo=true;
                    float huo_xyz[]= ScreenScaleUtil.fromRowColToXYZ(i,j,0.2f);
                    float huo_number[]=ScreenScaleUtil.fromPixPositionToScreenPosition(Constant.Huo_start_x+Constant.Huo_kuandu*5/4*(huoNum+1), Constant.Huo_start_Y);
                    huo_current_Postion= ScreenUtil.getscreen(ratio,huo_xyz[0],huo_xyz[1],huo_xyz[2]);
                    huo_distinceX=huo_number[0]-huo_current_Postion[0];
                    huo_distinceY=huo_number[1]-huo_current_Postion[1];
                    break;
                case Constant.iBombRange:
                    gameOver(Constant.iBombRange);
                    break;
                case Constant.iJiGuangRange://激光波及范围
                    if (ifJiGuang)
                    {
                        gameOver(Constant.iJiGuangRange);
                    }
            }
        }

    }

    ///*********************************************************************游戏结束*************************************************
    //游戏结束方法
    //游戏结束方法
    public void gameOver(int overType)
    {
        currPage=OverPage;
        if (!hasdowncrama)
        {
            downcrama=true;
            hasdowncrama=true;
        }
        switch(overType)
        {
            case iDropedBrick:  //已经掉落的砖块
                humanDeadWay=iDropedBrick;
                break;
            case iBombRange:    //被炸弹炸死
                humanDeadWay=iBombRange;
                break;
            case iMonster:      //被怪物打死
                humanDeadWay=iMonster;
                break;
            case iJiGuangRange://激光射死
                humanDeadWay=iMonster;
                break;
        }
    }

    //*****************************************************金币转动及人物移动线程******************************************************
    class ManControlThread extends Thread
    {
        int time=0;
        int span=40;
        @Override
        public void run()
        {
            while(true)
            {
                doSomething();
                try
                {
                    Thread.sleep(1);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        public void doSomething()
        {
            if (GoldRoateThreadFlag)
            {
                if (time%span==0)
                {
                    /**************金币转动角度******************/
                    roateAngle+=5      ;
                    /********判断人物是否移动****************/
                    {
                        if (keyState == 1) {//有UP键按下
                            ZDRMoveZ += ZDRStepAngle;
                            ggdhRoateAngle = 180;
                            goCrama = true;
                        } else if (keyState == 2) {//有down键按下
                            ZDRMoveZ -= ZDRStepAngle;
                            //ramaZLateDistince-=ZDRStepAngle;
                            ggdhRoateAngle = 0;
                            goCrama = true;
                        } else if (keyState == 3) {//有left键按下
                            ZDRMoveX -= ZDRStepAngle;
                            ggdhRoateAngle = 270;
                            XroateCrama = true;
                        } else if (keyState == 4) {//有right按下
                            ZDRMoveX += ZDRStepAngle;
                            ggdhRoateAngle = 90;
                            XroateCrama = true;
                        }
                    }

                    collisionTest(0);
                }
                time++;

                if (!upcrama && !downcrama && !roatecrama)
                {
                    if (goCrama)
                    {
                        if (cramaVz<ZDRStepAngle/span)
                        {
                            cramaVz=0.000005f*cramaZTime;
                            cramaZTime++;
                        }
                        else
                        {
                            cramaVz=ZDRStepAngle/span;
                        }
                        ///前后
                        if (Scz<UpCrama.cz-ZDRMoveZ)
                        {
                            Scz+=cramaVz;
                            Stz+=cramaVz;
                            BigDecimal bg = new BigDecimal(Scz);
                            float f1 = (float) bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            bg=new BigDecimal((UpCrama.cz-ZDRMoveZ));
                            float f2=(float) bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            if (f1>=f2)
                            {
                                goCrama=false;
                                cramaVz=0;
                                cramaZTime=0;
                            }
                        }
                        else
                        {
                            Scz-=cramaVz;
                            Stz-=cramaVz;
                            BigDecimal bg = new BigDecimal(Scz);
                            float f1 = (float) bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            bg=new BigDecimal((UpCrama.cz-ZDRMoveZ));
                            float f2=(float) bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            if (f1<=f2)
                            {
                                goCrama=false;
                                cramaVz=0;
                                cramaZTime=0;
                            }
                        }
                    }
                    if (XroateCrama)
                    {
                        if (cramaVx<ZDRStepAngle/ZDRtxTimes/span)
                        {
                            cramaVx=0.00000015f*cramaXTime;
                            cramaXTime++;
                        }
                        else
                        {
                            cramaVx=ZDRStepAngle/ZDRtxTimes/span;
                        }
                        if (Stx<UpCrama.tx+ZDRMoveX/ZDRtxTimes)
                        {
                            Stx+=cramaVx;
                            BigDecimal bg = new BigDecimal(Stx);
                            float f1 = (float) bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            bg=new BigDecimal((UpCrama.tx+ZDRMoveX/ZDRtxTimes));
                            float f2=(float) bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            //System.out.println(" f1="+f1+"     f2="+f2);
                            if (f1>=f2)
                            {
                                XroateCrama=false;
                                cramaVx=0;
                                cramaXTime=0;
                            }
                        }
                        else
                        {
                            Stx-=cramaVx;
                            BigDecimal bg = new BigDecimal(Stx);
                            float f1 = (float) bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            bg=new BigDecimal((UpCrama.tx+ZDRMoveX/ZDRtxTimes));
                            float f2=(float) bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            //System.out.println(" f1="+f1+"     f2="+f2);
                            if (f1<=f2)
                            {
                                XroateCrama=false;
                                cramaVx=0;
                                cramaXTime=0;
                            }
                        }

                    }
                }
            }
        }
    }
    //***************************************************************砖块掉落线程**************************************************************
    class DropBrickThread extends Thread
    {
        @Override
        public void run()
        {
            while(true)
            {
                doSomething();
                try
                {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        public void doSomething()
        {
            if (DropBrickThreadFlag)
            {
                synchronized (lock)
                {
                    for(int i=0;i<dropBrickLList.size();i++)
                    {
                        if(dropBrickLList.get(i)[2]>shakeNum*5)//10//20
                        {
                            needDeleteDropBrickList.add(dropBrickLList.get(i));
                            continue;
                        }
                        dropBrickLList.get(i)[2]=dropBrickLList.get(i)[2]+1;
                    }
                    for(int i=0;i<needDeleteDropBrickList.size();i++)
                    {
                        dropBrickLList.remove(needDeleteDropBrickList.get(i));
                    }
                    needDeleteDropBrickList.clear();
                    if(++dropThreadTime%2==0)                       //线程每循环三次添加一个新掉落的砖块
                    {
                        if(curr_drop_sum==Constant.DropOrder.length)//判断第一行是否掉落完毕
                        {
                            curr_drop_row++;                        //掉落行号+1
                            curr_drop_sum=Constant.DropOrder.length-startSecondDropNum;     //重置第一行砖块掉落数
                            dropBrickLList.add(new int[]{curr_drop_row,Constant.DropOrder[0],1});
                            curr_drop_sum=1;//计算正在掉落的第一行已经掉落的砖块数量+
                        }
                        else if(curr_drop_sum>=startSecondDropNum)//是否掉落第二行的
                        {
                            dropBrickLList.add(new int[]{curr_drop_row+1,Constant.DropOrder[curr_drop_sum-startSecondDropNum],1});
                        }
                    }
                    if(dropThreadTime%4==0)
                    {
                        dropBrickLList.add(new int[]{curr_drop_row,Constant.DropOrder[curr_drop_sum],1});//新添一个掉落砖块
                        curr_drop_sum++;
                    }

                }
            }
        }
    }

    //*****************************************************************激光发射控制线程*********************************************************
    class JiGuangThread extends Thread
    {
        @Override
        public void run()
        {
            while(true)
            {
                doSomething();
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        public void doSomething()
        {
            ifJiGuang=!ifJiGuang;
            if(!JiGuangThreadFlag)
            {
                for (int i=0;i<al.size();i++)
                {
                    if (i < curr_drop_row - 4)
                    {
                        continue;
                    } else
                        for (int j = 0; j < al.get(i).length; j++)
                        {
                            if(al.get(i)[j]==iJiGuangRange)
                            {
                                al.get(i)[j]=99;
                            }
                        }
                }
            }
        }
    }

    //*********************************************************************金币连吃效果绘制控制******************************************************
    class ControlRunEatGoldTT extends TimerTask
    {
        @Override
        public void run()
        {
            controlRunEatGoldT.cancel();
            ifShowRuneatGold=false;
        }
    }



}
