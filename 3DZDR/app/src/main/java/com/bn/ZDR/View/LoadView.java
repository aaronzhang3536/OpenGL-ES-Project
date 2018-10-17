package com.bn.ZDR.View;

import android.app.Activity;
import android.opengl.GLES30;
import android.view.MotionEvent;

import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.Util.LoadUtil;
import com.bn.ZDR.Util.MainActivity;
import com.bn.ZDR.Util.MatrixState2D;
import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.Util.ScreenScaleUtil;
import com.bn.ZDR.Util.TextureManager;
import com.bn.ZDR.Util.TextureRect3D;
import com.bn.ZDR.Util.TextureRectangle2D;
import com.bn.ZDR.fbx.core.BNModel;

import com.bn.ZDR.Particle.*;


import javax.microedition.khronos.opengles.GL10;

import static com.bn.ZDR.Util.Constant.GEN_TEX_HEIGHT;
import static com.bn.ZDR.Util.Constant.GEN_TEX_WIDTH;
import static com.bn.ZDR.Util.Constant.XuHuaId;
import static com.bn.ZDR.Util.Constant.calculatLocationData;
import static com.bn.ZDR.Util.Constant.frameBufferId;
import static com.bn.ZDR.Util.Constant.ifXuHua;
import static com.bn.ZDR.Util.Constant.isLoadOk;
import static com.bn.ZDR.Util.Constant.renderDepthBufferId;


/**
 * Created by Administrator on 2017/7/9.
 */

public class LoadView extends ZDRAbstractView
{
    MyGLSurfaceView mglview;
    MainActivity activity;

    public static TextureRectangle2D loadBg;//底层背景
    int  load_step=0;//加载进度
    public  LoadView(MyGLSurfaceView mglview, MainActivity activity)
    {
        this.mglview=mglview;
        this.activity=activity;
        initView();
    }

    @Override
    public void initView()
    {
        loadBg=new TextureRectangle2D(mglview);
        Constant.LoadingTextureId=TextureManager.getTextures("loading.png");
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        return false;
    }

    @Override
    public void drawView(GL10 gl)
    {
        //清除颜色缓冲和深度缓冲
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT
                | GLES30.GL_COLOR_BUFFER_BIT);
        if(!isLoadOk)
        {
            drawloadview();
        }
        else
        {
            MyGLSurfaceView.currview=new GameView(mglview,activity);
            activity.nowBGM=activity.openBGM;
            activity.resumeBGM();
        }
    }

    public void drawloadview()
    {
        float ratio = Constant.SCREEN_HEIGHT/Constant.SCREEN_WIDTH;         //计算GLSurfaceView的宽高比
        MatrixState3D.setProjectFrustum(-1,1,-ratio,ratio,1,100);  //调用此方法计算产生透视投影矩阵
        MatrixState3D.setCamera( 0,0,2f,0f,0f,0f,0f,1.0f,0.0f);   //设置摄像机位置

        MatrixState2D.setProjectOrtho(-1,1,-ratio,ratio,0,10);//调用此方法计算产生正视投影矩阵
        MatrixState2D.setCamera(0.0f, 0.0f, 3f, 0.0f, 0.0f, 0.0f, 0.0f,1.0f, 0.0f);

        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT|GLES30.GL_COLOR_BUFFER_BIT);//清除深度缓存及颜色缓存

        //绘制背景
        MatrixState2D.pushMatrix();
        loadBg.drawSelf(Constant.LoadingTextureId,Constant.SCREEN_WIDTH/2,Constant.SCREEN_HEIGHT/2,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT,1);
        MatrixState2D.popMatrix();

        LoadSource();//预加载游戏资源
    }

    public void init_All_Texture(int i)
    {
        switch (i)
        {
            case 0:
                Constant.Game1stLevelLandTextureId= TextureManager.getTextures("first_level_game_land.png");//加载纹理
                Constant.Game1stLevelWallTextureId=TextureManager.getTextures("first_level_game_wall.png");
                Constant.GameGoldTextureId=TextureManager.getTextures("iBuff_gold.jpg");
                Constant.Game1stLevelBoxTextureId=TextureManager.getTextures("first_level_game_box.png");
                Constant.ZhaDanRenTextureId=TextureManager.getTextures("zhadanren.png");
                Constant.ButtonDropBombTextureId=TextureManager.getTextures("zhadan.png");
                Constant.ButtonDropTntTextureId=TextureManager.getTextures("tnt.png");
                Constant.RunEatGoldNumberTextureId=TextureManager.getTextures("number_big.png");
                Constant.RunEatGoldChengTextureId=TextureManager.getTextures("runEatGoldCheng.png");
                Constant.RunEatGoldJinBiTextureId=TextureManager.getTextures("runEatGoldJinbi.png");
                break;
            case 10:
                Constant.FireTextureId= TextureManager.getTextures("fire.png");
                Constant.jk_bigId=TextureManager.getTextures("yaogan1.png");
                Constant.jk_smallId=TextureManager.getTextures("yaogan2.png");
                Constant.tntBombId=TextureManager.getTextures("tnt_food.png");
                Constant.jinbi_backgroundId=TextureManager.getTextures("jinbi_background.png");
                Constant.huo_backgroundId=TextureManager.getTextures("huo_background.png");
                Constant.shui_backgroundId=TextureManager.getTextures("shui_background.png");
                Constant.huo_backgroundId2=TextureManager.getTextures("huo_background2.png");
                Constant.shui_backgroundId2=TextureManager.getTextures("shui_background2.png");
                Constant.numberId =TextureManager.getTextures("number.png");
                break;
            case 20:
                Constant.b_huoId=TextureManager.getTextures("b_huo.png");
                Constant.w_huoId=TextureManager.getTextures("w_huo.png");
                Constant.b_shuiId=TextureManager.getTextures("b_shui.png");
                Constant.w_shuiId=TextureManager.getTextures("w_shui.png");
                Constant.ButtonDropTntTextureId2=TextureManager.getTextures("tnt2.png");
                Constant.shui_foodId=TextureManager.getTextures("shui_food.png");
                Constant.huo_foodId=TextureManager.getTextures("huo_food.png");
                Constant.huo_fly_foodId=TextureManager.getTextures("huo_fly_food.png");
                Constant.shui_fly_foodId=TextureManager.getTextures("shui_fly_food.png");
                Constant.tnt_fly_foodId=TextureManager.getTextures("tnt_fly_food.png");
                break;
            case 30:
                Constant.skyId=TextureManager.getTextures("sky.png");
                Constant.cupId=TextureManager.getTextures("cup.png");
                Constant.bestId=TextureManager.getTextures("best.png");
                Constant.final_score_backgroundId=TextureManager.getTextures("final_score_background.png");
                Constant.reliveId=TextureManager.getTextures("relive.png");
                Constant.relivd2Id=TextureManager.getTextures("relive1.png");
                Constant.relive3Id=TextureManager.getTextures("relive2.png");
                Constant.restartId=TextureManager.getTextures("restart.png");
                Constant.Game2ndLevelBoxTextureId =TextureManager.getTextures("second_level_game_box.png");
                Constant.Game2ndLevelWallTextureId =TextureManager.getTextures("second_level_game_wall.png");
                break;
            case 40:
                Constant.Game2ndLevelLandTextureId=TextureManager.getTextures("second_level_game_land.png");
                Constant.ciqiuId=TextureManager.getTextures("ciqiu.png");
                Constant.setId=TextureManager.getTextures("set.png");
                Constant.chartId=TextureManager.getTextures("chart.png");
                Constant.beijingId=TextureManager.getTextures("beijing.png");
                Constant.shouzhiId=TextureManager.getTextures("shouzhi.png");
                Constant.shouId=TextureManager.getTextures("shou.png");
                Constant.bowenId=TextureManager.getTextures("bowen.png");
                Constant.JiGuangGuaiTextureId =TextureManager.getTextures("ji_guang_guai.png");
                Constant.bothSideCubetexture1=TextureManager.getTextures("lansezhengfangxing.png");
                break;
            case 50:
                Constant.bothSideCubetexture2=TextureManager.getTextures("fensezhengfangxing.png");
                Constant.bothSideCubetexture3=TextureManager.getTextures("lusezhengfanxing.png");
                Constant.ZhaDanTextureId=TextureManager.getTextures("zhadanTexture.png");
                Constant.JiGuangGuaiTextureId =TextureManager.getTextures("ji_guang_guai.png");
                Constant.SetPageTitleTextureId=TextureManager.getTextures("set_page_title.png");
                Constant.SetPagePanelTextureId=TextureManager.getTextures("set_page_mianban.png");
                Constant.SetPagePanelHighTextureId=TextureManager.getTextures("set_page_high.png");
                Constant.SetPagePanelLowTextureId=TextureManager.getTextures("set_page_low.png");
                Constant.SetPagePanelYesTextureId=TextureManager.getTextures("set_page_yes.png");
                Constant.SetPageBackTextureId=TextureManager.getTextures("set_page_back.png");
                break;
            case 60:
                Constant.pauseId=TextureManager.getTextures("pause.png");
                Constant.overId=TextureManager.getTextures("jieshu.png");
                Constant.resumeId=TextureManager.getTextures("resume.png");
                Constant.mohuId=TextureManager.getTextures("mohu.png");
                Constant.ChartTitleTextureId=TextureManager.getTextures("chart_title.png");
                Constant.TextTextureId=TextureManager.getTextures("textTexture.png");
                Constant.ChartPanelTextureId=TextureManager.getTextures("chart_page_panel.png");
                Constant.Game3ndLevelBoxTextureId=TextureManager.getTextures("third_level_game_box.png");
                Constant.Game3ndLevelLandTextureId=TextureManager.getTextures("third_level_game_land.png");
                Constant.Game3ndLevelWallTextureId=TextureManager.getTextures("third_level_game_wall.png");
                Constant.sky2Id=TextureManager.getTextures("sky2.png");
                Constant.sky3Id=TextureManager.getTextures("sky3.png");
                Constant.manDownRectId=TextureManager.getTextures("mandown_rect.png");
                break;

            case 70:
                /****************加载模型****************/
                Constant.zhadanren=LoadUtil.loadFromFile("ZDR.obj",mglview.getResources(),mglview) ;
                Constant.zhadanrenZhasi=LoadUtil.loadFromFile("ZDRZhaSi.obj",mglview.getResources(),mglview);
                Constant.zhadan=LoadUtil.loadFromFileBullet("zhadan.obj",mglview.getResources(),mglview);
                Constant.land= LoadUtil.loadFromFile("first_level_game_land.obj", mglview.getResources(),mglview);
                Constant.wall1= LoadUtil.loadFromFile("first_level_game_wall.obj", mglview.getResources(),mglview);
                Constant.box1= LoadUtil.loadFromFile("first_level_game_box.obj", mglview.getResources(),mglview);
                Constant.buff_gold=LoadUtil.loadFromFile("buff_gold.obj", mglview.getResources(),mglview);
                break;
            case 80:
                Constant.tnt_shui_huo_food=LoadUtil.loadFromFile("tnt.obj",mglview.getResources(),mglview);
                Constant.ji_guang_guai=LoadUtil.loadFromFile("JiGuangGuai.obj",mglview.getResources(),mglview);
                Constant.wall2=LoadUtil.loadFromFile("second_level_game_wall.obj",mglview.getResources(),mglview);
                Constant.box2=LoadUtil.loadFromFile("second_level_game_box.obj",mglview.getResources(),mglview);
                Constant.ci_qiu_guai=LoadUtil.loadFromFile("ciqiu.obj",mglview.getResources(),mglview);
                Constant.text=LoadUtil.loadFromFile("text.obj",mglview.getResources(),mglview);
                Constant.box3=LoadUtil.loadFromFile("third_level_game_box.obj",mglview.getResources(),mglview);
                Constant.wall3=LoadUtil.loadFromFile("third_level_game_wall.obj",mglview.getResources(),mglview);
            break;

            case 90:
                /**************************/
                Constant.all2DTextureRectangle=new TextureRectangle2D(mglview);//加载2D绘制部件
                Constant.mandownRect=new TextureRect3D(mglview);
                Constant.RunEatGoldChengORJinbi=new TextureRectangle2D
                        (
                                ScreenScaleUtil.from2DSizeTo3DSize(30,30)[0],
                                ScreenScaleUtil.from2DSizeTo3DSize(30,30)[1],
                                new float[]{0f,0f,0f,1f,1f,0f,1f,1f,},
                                mglview
                        );

                /*************/
                Constant.zhadanrenModel=new BNModel("bngg/ZDRwalk.bnggdh", "bnggPic/zhadanren.png", true, 0.035f,mglview);//加载骨骼动画模型
                Constant.OpenAction=new BNModel("bngg/upHead.bnggdh","bnggPic/zhadanren.png", true, 0.009f,mglview);
                Constant.UpHead=new BNModel("bngg/upHead.bnggdh","bnggPic/zhadanren.png", true, 0.0035f,mglview);
                Constant.dead=new BNModel("bngg/died.bnggdh","bnggPic/zhadanren.png", true, 0.0035f,mglview);
                Constant.relive=new BNModel("bngg/relive.bnggdh","bnggPic/zhadanren.png", true, 0.025f,mglview);
                /**************/
                //炸弹爆炸
                Constant.fpfd=new ParticleBombForDraw(mglview, ParticleBombDataConstant.RADIS);
                Constant.fps=new ParticleBombSystem(Constant.fpfd,ParticleBombDataConstant.COUNT);

                //激光怪
                Constant.fpfdJG=new ParticleJiGuangForDraw(mglview, ParticleJiGuangDataConstant.RADIS);
                Constant.fpsJG=new ParticleJiGuangSystem(Constant.fpfdJG, ParticleJiGuangDataConstant.COUNT);
                break;
            case 100:
                isLoadOk=true;
                if (ifXuHua)
                {
                    initFRBuffers();
                }
                break;
        }

    }

    public void LoadSource()
    {
        init_All_Texture(load_step);
        load_step++;
    }

    public void initFRBuffers()//初始化帧缓冲和渲染缓冲的方法
    {
        int tia[]=new int[1];//用于存放产生的帧缓冲id的数组
        GLES30.glGenFramebuffers(1, tia, 0);//产生一个帧缓冲id
        frameBufferId=tia[0];//将帧缓冲id记录到成员变量中
        //绑定帧缓冲id
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBufferId);

        GLES30.glGenRenderbuffers(1, tia, 0);//产生一个渲染缓冲id
        renderDepthBufferId=tia[0];//将渲染缓冲id记录到成员变量中
        //绑定指定id的渲染缓冲
        GLES30.glBindRenderbuffer(GLES30.GL_RENDERBUFFER, renderDepthBufferId);
        //为渲染缓冲初始化存储
        GLES30.glRenderbufferStorage(GLES30.GL_RENDERBUFFER,
                GLES30.GL_DEPTH_COMPONENT16,GEN_TEX_WIDTH, GEN_TEX_HEIGHT);

        int[] tempIds = new int[1];//用于存放产生纹理id的数组
        GLES30.glGenTextures//产生一个纹理id
                (
                        1,         //产生的纹理id的数量
                        tempIds,   //纹理id的数组
                        0           //偏移量
                );
        XuHuaId=tempIds[0];//将纹理id记录到成员变量
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,XuHuaId);//绑定纹理id
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,//设置MIN采样方式
                GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,//设置MAG采样方式
                GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,//设置S轴拉伸方式
                GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,//设置T轴拉伸方式
                GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexImage2D//设置颜色附件纹理图的格式
                (
                        GLES30.GL_TEXTURE_2D,
                        0,						//层次
                        GLES30.GL_RGBA, 		//内部格式
                        GEN_TEX_WIDTH,			//宽度
                        GEN_TEX_HEIGHT,			//高度
                        0,						//边界宽度
                        GLES30.GL_RGBA,			//格式
                        GLES30.GL_UNSIGNED_BYTE,//每个像素数据格式
                        null
                );
        GLES30.glFramebufferTexture2D		//设置自定义帧缓冲的颜色缓冲附件
                (
                        GLES30.GL_FRAMEBUFFER,
                        GLES30.GL_COLOR_ATTACHMENT0,	//颜色缓冲附件
                        GLES30.GL_TEXTURE_2D,
                        XuHuaId, 						//纹理id
                        0								//层次
                );
        GLES30.glFramebufferRenderbuffer	//设置自定义帧缓冲的深度缓冲附件
                (
                        GLES30.GL_FRAMEBUFFER,
                        GLES30.GL_DEPTH_ATTACHMENT,		//深度缓冲附件
                        GLES30.GL_RENDERBUFFER,			//渲染缓冲
                        renderDepthBufferId				//渲染深度缓冲id
                );
    }

}
