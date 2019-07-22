package com.bn.ZDR.Util;

import com.bn.ZDR.fbx.core.BNModel;

import com.bn.ZDR.Particle.ParticleBombForDraw;
import com.bn.ZDR.Particle.ParticleBombSystem;
import com.bn.ZDR.Particle.ParticleJiGuangForDraw;
import com.bn.ZDR.Particle.ParticleJiGuangSystem;


import java.util.HashMap;
import java.util.Map;

public class Constant
{
    public static float blurWidth=10.0f;//虚化带的宽度
    public static float blurPosition=6.0f;//虚化带开始距离（表示离摄像机的距离）
    public static float UPBlurPositon=18.0f;
    public static float DownBlurPosition=6f;
    public static float RoateBlurPosition=6.0f;
    public static final int GEN_TEX_WIDTH=1024;
    public static final int GEN_TEX_HEIGHT=1024;
    public static boolean ifXuHua=false;
    public static int XuHuaId;                      //虚幻id
    public static int frameBufferId;                //帧缓冲id
    public static int renderDepthBufferId;          //渲染深度缓冲id

    public static float SCREEN_WIDTH;               //实际屏幕宽度
    public static float SCREEN_HEIGHT;              //实际屏幕高度
    public static ScreenScaleResult ssr;            //屏幕自适应结果对象
    public static float Ratio=0.05f;                //砖块的缩放比
    public static float SideLengh=20*Ratio;         //单位格子的大
    public static boolean isLoadOk=false;           //加载完毕标志位


    public static int currPage=1;
    public static final int OpenPage=1;             //开场界面
    public static final int SetPage=2;              //设置界面
    public static final int ChartPage=3;            //排行榜界面
    public static final int OverPage=4;             //游戏结束界面
    public static final int GamePage=5;             //游戏界面
    public static final int PausePage=6;            //暂停界面



    //控制线程运行的标志位
    public static boolean GoldRoateThreadFlag=false;//金币转动
    public static boolean DropBrickThreadFlag=false;//砖块掉落
    public static boolean JiGuangThreadFlag=false;//激光发射
    public static boolean WelcomeThreadFlag =true;//开场界面摄像机转动
    public static boolean CiQiuGoThreadFlag =false;//刺球怪行进
    public static boolean BulletGoThreadFlag=false;//绘制炸弹



    /**移动步幅*/
    public static float ZDRStepAngle=0.15f;//炸弹人移动步幅
    public static boolean manGoOrNot=false;

    /**炸弹人初始位置*/
    public static float ZDRStartCol=5;//炸弹人初始列坐标<炸弹人位置坐标对应于数组的行列>
    public static float ZDRStartRow=10;//炸弹人初始行坐标
    public static int ZDRtxTimes=4;
    public static float cramaVz;//摄像机z速度
    public static float cramaZTime;
    public static float cramaVx;//摄像机x速度
    public static float cramaXTime;
    public static boolean goCrama=false;
    public static boolean XroateCrama=false;
    /********摄像机9参数矩阵*********/

//    public static float Scx=7.5f;	//摄像机位置x
//    public static float Scy=19.5f;  //摄像机位置y
//    public static float Scz=0.0f;  //摄像机位置z
//    public static float Stx=7.5f;   //摄像机目标点x
//    public static float Sty=0f;     //摄像机目标点y
//    public static float Stz=-5.0f;   //摄像机目标点z
//    public static float Supx=-0.3f;  //摄像机UP向量X分量
//    public static float Supy=5.0f;      //摄像机UP向量Y分量
//    public static float Supz=-1.0f;  //摄像机UP向量Z分量

    public static float Scx=SideLengh*(ZDRStartCol+0.5f);	//摄像机位置x
    public static float Scy=5f;  //摄像机位置y
    public static float Scz=-3f;  //摄像机位置z//刚开始相差7.5
    public static float Stx=SideLengh*(ZDRStartCol+0.5f);   //摄像机目标点x
    public static float Sty=3f;     //摄像机目标点y
    public static float Stz=-15.0f;   //摄像机目标点z
    public static float Supx=-0f;  //摄像机UP向量X分量
    public static float Supy=1.0f;      //摄像机UP向量Y分量
    public static float Supz=-0f;  //摄像机UP向量Z分量


    public static float Hcx=8.0f;	//摄像机位置x
    public static float Hcy=11.0f;  //摄像机位置y
    public static float Hcz=-5f;  //摄像机位置z
    public static float Htx=7.0f;  //摄像机目标点x
    public static float Hty=0f;  //摄像机目标点y
    public static float Htz=-10f;  //摄像机目标点z
    public static float Hupx=-0.25f;  //摄像机UP向量X分量
    public static float Hupy=0.0f;  //摄像机UP向量Y分量
    public static float Hupz=-1.0f;  //摄像机UP向量Z分量



    //软件资源路径
    public static String TexturePath="pic/";        //定义图片路径
    public static String ObjPath="obj/";            //定义obj文件路径
    public static String ShaderPath="shader/";      //定义着色器文件路径

    //纹理Id常量
    public static int ZhaDanRenTextureId;           //炸弹人纹理
    public static int ZhaDanTextureId;              //炸弹纹理
    public static int Game1stLevelLandTextureId;    //第一阶段地面纹理
    public static int Game1stLevelWallTextureId;    //第一阶段墙纹理
    public static int Game1stLevelBoxTextureId;     //第一阶段箱子纹理
    public static int Game2ndLevelWallTextureId;    //第二关墙纹理
    public static int Game2ndLevelBoxTextureId;     //第二关箱子纹理
    public static int Game2ndLevelLandTextureId;    //第二关地面纹理
    public static int Game3ndLevelLandTextureId;    //第三关地面
    public static int Game3ndLevelWallTextureId;    //第三关墙纹理
    public static int Game3ndLevelBoxTextureId;     //第三关箱子纹理
    public static int LoadingTextureId;             //加载界面纹理
    public static int GameGoldTextureId;            //游戏金币纹理
    public static int ButtonDropBombTextureId;      //游戏界面放置炸弹纹理
    public static int ButtonDropTntTextureId;       //游戏界面放置TNT纹理
    public static int RunEatGoldNumberTextureId;    //金币连吃数字绘制纹理
    public static int RunEatGoldChengTextureId;     //金币连吃数字与金币之间乘号
    public static int RunEatGoldJinBiTextureId;     //金币连吃金币绘制纹理
    public static int FireTextureId;                //粒子系统火焰纹理
    public static int JiGuangGuaiTextureId;         //激光怪id
    public static int SetPageTitleTextureId;        //设置页面标题纹理
    public static int SetPagePanelTextureId;        //设置页面设置面板纹理
    public static int SetPagePanelHighTextureId;    //设置界面品质高纹理
    public static int SetPagePanelLowTextureId;     //设置界面品质低纹理
    public static int SetPagePanelYesTextureId;     //设置界面对勾
    public static int SetPageBackTextureId;         //设置界面返回纹理
    public static int ChartTitleTextureId;          //排行榜界面标题
    public static int TextTextureId;                //文字id
    public static int ChartPanelTextureId;          //排行榜界面面板





    /*粒子系统*/
    public static ParticleBombForDraw fpfd;             //粒子系统火焰绘制者
    public static ParticleBombSystem fps;               //粒子系统

    public static ParticleJiGuangForDraw fpfdJG;        //粒子系统激光绘制者
    public static ParticleJiGuangSystem fpsJG;          //粒子系统激光怪


    //骨骼动画
    public static BNModel zhadanrenModel;
    public static BNModel OpenAction;
    public static BNModel UpHead;
    public static BNModel dead;
    public static BNModel relive;


    //所有模型常量
    public static LoadedObjectVertexNormalTexture zhadanren;//炸弹小人
    public static LoadedObjectVertexNormalTexture zhadanrenZhasi;//炸弹人炸死
    public static LoadedObjectVertexNormalTextureBullet zhadan;//炸弹

    public static LoadedObjectVertexNormalTexture land;//第一种地面
    public static LoadedObjectVertexNormalTexture wall1;//第一种墙体
    public static LoadedObjectVertexNormalTexture wall2;//第二种墙体
    public static LoadedObjectVertexNormalTexture wall3;//第三种墙体
    public static LoadedObjectVertexNormalTexture box1;//第一种可炸物体(箱子)
    public static LoadedObjectVertexNormalTexture box2;//第二种可炸物体(木桩)
    public static LoadedObjectVertexNormalTexture box3;//第三种可炸物体(箱子)
    public static LoadedObjectVertexNormalTexture buff_gold;//金币buff
    public static LoadedObjectVertexNormalTexture tnt_shui_huo_food;//tnt模型
    public static LoadedObjectVertexNormalTexture ji_guang_guai;//激光怪
    public static LoadedObjectVertexNormalTexture ci_qiu_guai;//刺球怪
    public static LoadedObjectVertexNormalTexture text;//文字模型



    public static int runGoldSum=0;//金币连吃数量
    public static boolean ifShowRuneatGold=false;//是否显示金币连吃效果

    //2D绘制
    public static TextureRectangle2D all2DTextureRectangle;//通用2D绘制按钮或标志板
    public static TextureRectangle2D RunEatGoldChengORJinbi;//金币连吃效果中乘号及金币符号
    //3d矩形
    public static TextureRect3D mandownRect;

    //表示每行砖块掉落顺序
    public static final int [] DropOrder= {3,7,9,2,5,1,6,10,8,4,0};


    /**
     * 表示地图上各种物件的常量
     */
    /**************会引起游戏结束的标志位*****************/
    public static final int iDropedBrick=-1;            //已经掉落的砖块
    public static final int iBombRange=-2;              //炸弹波及范围
    public static final int iMonster=-3;                //怪物
    public static final int iJiGuangRange=-4;           //激光波及范围

    public static final int iWall=0;                    //墙体

    public static final int iBox=1;                     //无实物图
    public static final int iBoxTnt =6;                 //tnt图
    public static final int iBoxShui =7;                //水食物
    public static final int iBoxHuo =9;                 //火食物

    public static final int iBuff_gold=2;               //金币
    public static final int iTnt_food=8;                //tnts实物
    public static final int iHuo_food=10;               //火食物
    public static final int iShui_food=11;              //水食物
    public static final int iJiGuangGuai_down=16;       //激光怪
    public static final int iJiGuangGuai_right=17;      //激光怪
    public static final int iJiGuangGuai_up=18;         //激光怪
    public static final int iJiGuangGuai_left=19;       //激光怪
    public static final int iCiQiu=20;                  //刺球
    public static final int iKongBai=21;                //空白



    /**
     * 游戏界面2D
     */

    public static final int[][] FirstLevelMap=
            {
                    /*******01********02**************03******************04***************05*************06***************07************08***************09**************10**************11*************12**************13*************14*****************15***/
                    /*01*/{iWall,     iWall,                iWall,              iWall,            iWall,       iWall,             iWall,     iWall,             iWall,                   iWall,       iWall},
                    /*02*/{iWall,     iBoxTnt,              99,                 iBoxShui,         99,          99,                99,        iBoxTnt,           99,                      99,          iWall},
                    /*03*/{iWall,     iTnt_food,            iWall,              99,               iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                    /*04*/{iWall,     iJiGuangGuai_right,   99,                 iHuo_food,        99,          99,                99,        99,                99,                      99,          iWall},
                    /*05*/{iWall,     99,                   iWall,              iBoxTnt,          iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                    /*06*/{iWall,     99,                   99,                 iTnt_food,        99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*07*/{iWall,     99,                   iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   iBox,        iWall},
                    /*08*/{iWall,     iCiQiu,               99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*09*/{iWall,     99,                   iWall,              99,               99,          99,                99,        iBox,              iWall,                   99,          iWall},
                    /*10*/{iWall,     99,                   99,                 99,               iBoxTnt,     99,                99,        99,                99,                      99,          iWall},
                    /*11*/{iWall,     iWall,                iWall,              99,               99,          99,                99,        99,                iWall,                   99,          iWall},
                    /*12*/{iWall,     iBuff_gold,           99,                 iBox,             99,          99,                99,        99,                99,                      99,          iWall},
                    /*13*/{iWall,     iBuff_gold,           iWall,              iWall,            99,       iWall,                99,        iBuff_gold,        iWall,                   99,          iWall},
                    /*14*/{iWall,     iBuff_gold,           99,                 iJiGuangGuai_right,99,         99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*15*/{iWall,     iBuff_gold,           iWall,              iWall,            99,       iWall,                99,        iBox,              iWall,                   99,          iWall},
                    /*16*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*17*/{iWall,     iBox,                 iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                    /*18*/{iWall,     iBuff_gold,           99,                 iJiGuangGuai_down,99,          iBuff_gold,        99,        99,                99,                      iCiQiu,      iWall},
                    /*19*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                    /*20*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*21*/{iWall,     iBoxShui,             iWall,              iWall,            iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                    /*22*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*23*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                    /*24*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*25*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBox,              iWall,                   iWall,       iWall},
                    /*26*/{iWall,     iWall,                99,                 99,               99,          99,                99,        99,                99,                      iWall,       iWall},
                    /*27*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       iBox,              iWall,     99,                99,                   iJiGuangGuai_left,iWall},
                    /*28*/{iWall,     iBuff_gold,           99,                 99,               99,          iBuff_gold,        99,        99,                99,                      iWall,       iWall},
                    /*29*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                    /*30*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*31*/{iWall,     iBuff_gold,           iWall,              iWall,            iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                    /*32*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      iBoxHuo,     iWall},
                    /*33*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                    /*34*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*35*/{iWall,     iBoxTnt,              iWall,              99,               iWall,       99,                iWall,     iBox,              iWall,                   99,          iWall},
                    /*36*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*37*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                    /*38*/{iWall,     iBuff_gold,           99,                 99,               99,          iBuff_gold,        99,        99,                99,                      iBoxHuo,     iWall},
                    /*39*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                    /*40*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                    /*41*/{iWall,     iBoxTnt,              iWall,              99,               iWall,       iWall,             iWall,     iWall,             iWall,                   99,           iWall},
                    /*42*/{iWall,     iBoxTnt,              99,                 iBoxShui,         99,          99,                99,        iBoxTnt,           99,                      99,          iWall},
                    /*43*/{iWall,     iTnt_food,            iWall,              99,               iWall,       99,                iWall,     99,                iWall,                   iBoxTnt,     iWall},
                    /*44*/{iWall,     99,                   99,                 iHuo_food,        99,          99,                99,        99,                99,                      99,          iWall},
                    /*45*/{iWall,     99,                   iWall,              iBoxTnt,          iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                    /*46*/{iWall,     99,                   99,                 iTnt_food,        99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*47*/{iWall,     99,                   iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                    /*48*/{iWall,     iCiQiu,               99,                 99,               99,          iBuff_gold,        99,        99,                99,                      99,          iWall},
                    /*49*/{iWall,     99,                   iWall,              99,               iWall,       iTnt_food,         iWall,     iBox,              iWall,                   99,          iWall},
                    /*50*/{iWall,     99,                   99,                 iWall,            99,          99,                99,        iBuff_gold,        99,                      iBoxShui,    iWall},
            };
    public static final int[][] SecondLevelMap=
                {
                      /*******01********02**************03******************04***************05*************06***************07************08***************09**************10**************11*************12**************13*************14*****************15***/
                      /*01*/{iWall,     99,                   iWall,              iWall,            99,       iWall,             iWall,     iWall,             iWall,                   iWall,       iWall},
                      /*02*/{iWall,     iBoxTnt,              99,                 iBoxShui,         99,          99,                99,        iBoxTnt,           99,                      99,          iWall},
                      /*03*/{iWall,     iTnt_food,            iWall,              99,               iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                      /*04*/{iWall,     iJiGuangGuai_right,   99,                 iHuo_food,        99,          99,                99,        99,                99,                      99,          iWall},
                      /*05*/{iWall,     99,                   iWall,              iBoxTnt,          iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                      /*06*/{iWall,     99,                   99,                 iTnt_food,        99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*07*/{iWall,     99,                   iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   iBox,        iWall},
                      /*08*/{iWall,     iCiQiu,               99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                      /*09*/{iWall,     99,                   iWall,              99,               99,          99,                99,        iBox,              iWall,                   99,          iWall},
                      /*10*/{iWall,     99,                   99,                 99,               iBoxTnt,     99,                99,        99,                99,                      99,          iWall},
                      /*11*/{iWall,     iWall,                iWall,              99,               99,          99,                99,        99,                iWall,                   99,          iWall},
                      /*12*/{iWall,     iBuff_gold,           99,                 iBox,             99,          99,                99,        99,                99,                      iBoxTnt,     iWall},
                      /*13*/{iWall,     iBuff_gold,           iWall,              iWall,            99,       iWall,                99,        iBuff_gold,        iWall,                   99,          iWall},
                      /*14*/{iWall,     iBuff_gold,           99,                 iJiGuangGuai_right,99,         99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*15*/{iWall,     iBuff_gold,           iWall,              iWall,            99,       iWall,                99,        iBox,              iWall,                   99,          iWall},
                      /*16*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                      /*17*/{iWall,     iBox,                 iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                      /*18*/{iWall,     iBuff_gold,           99,                 iJiGuangGuai_down,99,          iBuff_gold,        99,        99,                99,                      iCiQiu,      iWall},
                      /*19*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                      /*20*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                      /*21*/{iWall,     iCiQiu,               iWall,              iWall,            iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                      /*22*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*23*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                      /*24*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*25*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBox,              iWall,                   iWall,       iWall},
                      /*26*/{iWall,     iBox,                 99,                 iWall,            iWall,       iWall,             iWall,     iWall,             iWall,                   iWall,       iWall},
                      /*27*/{iWall,     iBuff_gold,           iWall,              iWall,            iKongBai,    iKongBai,          iKongBai,  iKongBai,          iKongBai,                iKongBai,    iKongBai,},
                      /*28*/{iWall,     iBuff_gold,           99,                 iWall,            iKongBai,    iKongBai,          iKongBai,  iKongBai,          iKongBai,                iKongBai,    iKongBai,},
                      /*29*/{iWall,     iBuff_gold,           iWall,              iWall,            iKongBai,    iKongBai,          iKongBai,  iKongBai,          iKongBai,                iKongBai,    iKongBai,},
                      /*30*/{iWall,     iBuff_gold,           99,                 iWall,            iKongBai,    iKongBai,          iKongBai,  iKongBai,          iKongBai,                iKongBai,    iKongBai,},
                      /*31*/{iWall,     iBuff_gold,           iWall,              iWall,            iWall,       iWall,             iWall,     iWall,             iWall,                   iWall,       iWall},
                      /*32*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*33*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                      /*34*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*35*/{iWall,     iBoxTnt,              iWall,              99,               iWall,       99,                iWall,     iBox,              iWall,                   99,          iWall},
                      /*36*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      iBoxShui,    iWall},
                      /*37*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                      /*38*/{iWall,     iBuff_gold,           99,                 99,               99,          iBuff_gold,        99,        99,                99,                      99,          iWall},
                      /*39*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                      /*40*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                      /*41*/{iWall,     iBoxTnt,              iWall,              99,               iWall,       iWall,             iWall,     iWall,             iWall,                   iBox,        iWall},
                      /*42*/{iWall,     iBoxTnt,              99,                 iBoxShui,         99,          99,                99,        iBoxTnt,           99,                      99,          iWall},
                      /*43*/{iWall,     iTnt_food,            iWall,              99,               iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                      /*44*/{iWall,     99,                   99,                 iHuo_food,        99,          99,                99,        99,                99,                      99,          iWall},
                      /*45*/{iWall,     99,                   iWall,              iBoxTnt,          iWall,       99,                iWall,     99,                iWall,                   iBoxTnt,          iWall},
                      /*46*/{iWall,     99,                   99,                 iTnt_food,        99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*47*/{iWall,     99,                   iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                      /*48*/{iWall,     iCiQiu,               99,                 99,               99,          iBuff_gold,        99,        99,                99,                      99,          iWall},
                      /*49*/{iWall,     99,                   iWall,              99,               iWall,       iTnt_food,         iWall,     iBox,              iWall,                   99,          iWall},
                      /*50*/{iWall,     iWall,                iWall,              iWall,            99,          99,                99,        iWall,             iWall,                   iWall,       iWall},
                };

    public static final int[][] ThirdLevelMap=
            {
                    /*******01********02**************03******************04***************05*************06***************07************08***************09**************10**************11*************12**************13*************14*****************15***/
                      /*01*/{iKongBai,  iKongBai,             iKongBai,           iKongBai,         iWall,       iBox,              iWall,     iKongBai,          iKongBai,                iKongBai,    iKongBai},
                      /*02*/{iKongBai,  iKongBai,             iKongBai,           iKongBai,         iWall,       99,                iWall,     iKongBai,          iKongBai,                iKongBai,    iKongBai},
                      /*03*/{iKongBai,  iKongBai,             iKongBai,           iKongBai,         iWall,       99,                iWall,     iKongBai,          iKongBai,                iKongBai,    iKongBai,},
                      /*04*/{iKongBai,  iKongBai,             iKongBai,           iKongBai,         iWall,       99,                iWall,     iKongBai,          iKongBai,                iKongBai,    iKongBai,},
                      /*05*/{iWall,     iWall,                iWall,              iWall,            iWall,       99,                iWall,     iWall,             iWall,                   iWall,       iWall},
                      /*06*/{iWall,     99,                   99,                 iTnt_food,        99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*07*/{iWall,     99,                   iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   iBox,        iWall},
                      /*08*/{iWall,     iCiQiu,               99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                      /*09*/{iWall,     99,                   iWall,              99,               99,          99,                99,        iBox,              iWall,                   99,          iWall},
                      /*10*/{iWall,     99,                   99,                 99,               iBoxTnt,     99,                99,        99,                99,                      99,          iWall},
                      /*11*/{iWall,     iWall,                iWall,              99,               99,          99,                99,        99,                iWall,                   99,          iWall},
                      /*12*/{iWall,     iBuff_gold,           99,                 iBox,             99,          99,                99,        99,                99,                      99,          iWall},
                      /*13*/{iWall,     iBuff_gold,           iWall,              iWall,            99,       iWall,                99,        iBuff_gold,        iWall,                   99,          iWall},
                      /*14*/{iWall,     iBuff_gold,           99,                 iJiGuangGuai_right,99,         99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*15*/{iWall,     iBuff_gold,           iWall,              iWall,            99,       iWall,                99,        iBox,              iWall,                   99,          iWall},
                      /*16*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                      /*17*/{iWall,     iBox,                 iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                      /*18*/{iWall,     iBuff_gold,           99,                 iJiGuangGuai_down,99,          iBuff_gold,        99,        99,                99,                      iCiQiu,      iWall},
                      /*19*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                      /*20*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                      /*21*/{iWall,     iCiQiu,               iWall,              iWall,            iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                      /*22*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*23*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                      /*24*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*25*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBox,              iWall,                   iWall,       iWall},
                      /*26*/{iWall,     iWall,                99,                 99,               99,          99,                99,        99,                99,                      iWall,       iWall},
                      /*27*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       iBox,              iWall,     99,                99,                   iJiGuangGuai_left,iWall},
                      /*28*/{iWall,     iBuff_gold,           99,                 99,               99,          iBuff_gold,        99,        99,                99,                      iWall,       iWall},
                      /*29*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                      /*30*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                      /*31*/{iWall,     iBuff_gold,           iWall,              iWall,            iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                      /*32*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*33*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                      /*34*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*35*/{iWall,     iBoxTnt,              iWall,              99,               iWall,       99,                iWall,     iBox,              iWall,                   99,          iWall},
                      /*36*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                      /*37*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                      /*38*/{iWall,     iBuff_gold,           99,                 99,               99,          iBuff_gold,        99,        99,                99,                      99,          iWall},
                      /*39*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                      /*40*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                      /*41*/{iWall,     iBoxTnt,              iWall,              99,               iWall,       iWall,             iWall,     iWall,             iWall,                   99,           iWall},
                      /*42*/{iWall,     iBoxTnt,              99,                 iBoxShui,         99,          99,                99,        iBoxTnt,           99,                      99,          iWall},
                      /*43*/{iWall,     iTnt_food,            iWall,              99,               iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                      /*44*/{iWall,     99,                   99,                 iHuo_food,        99,          99,                99,        99,                99,                      99,          iWall},
                      /*45*/{iWall,     99,                   iWall,              iBoxTnt,          iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                      /*46*/{iWall,     99,                   99,                 iTnt_food,        99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                      /*47*/{iWall,     99,                   iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                      /*48*/{iWall,     iCiQiu,               99,                 99,               99,          iBuff_gold,        99,        99,                99,                      99,          iWall},
                      /*49*/{iWall,     99,                   iWall,              99,               iWall,       iTnt_food,         iWall,     iBox,              iWall,                   99,          iWall},
                      /*50*/{iWall,     99,                   99,                 iWall,            99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
            };
    public static final int[][] ForthLevelMap=
            {
                    /*******01********02**************03******************04***************05*************06***************07************08***************09**************10**************11*************12**************13*************14*****************15***/
                    /*01*/{iWall,     iWall,                99,                 iWall,            99,       iBoxShui,             iWall,     iWall,             iWall,                   iWall,       iWall},
                    /*02*/{iWall,     iBoxTnt,              99,                 iBoxShui,         99,          99,                99,        iBoxTnt,           99,                      99,          iWall},
                    /*03*/{iWall,     iTnt_food,            iWall,              99,               iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                    /*04*/{iWall,     iJiGuangGuai_right,   99,                 iHuo_food,        99,          99,                99,        99,                99,                      99,          iWall},
                    /*05*/{iWall,     99,                   iWall,              iBoxTnt,          iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                    /*06*/{iWall,     99,                   99,                 iTnt_food,        99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*07*/{iWall,     99,                   iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   iBox,        iWall},
                    /*08*/{iWall,     iCiQiu,               99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*09*/{iWall,     99,                   iWall,              99,               99,          99,                99,        iBox,              iWall,                   99,          iWall},
                    /*10*/{iWall,     99,                   99,                 99,               iBoxTnt,     99,                99,        99,                99,                      99,          iWall},
                    /*11*/{iWall,     iWall,                iWall,              99,               99,          99,                99,        99,                iWall,                   99,          iWall},
                    /*12*/{iWall,     iBuff_gold,           99,                 iBox,             99,          99,                99,        99,                99,                      99,          iWall},
                    /*13*/{iWall,     iBuff_gold,           iWall,              iWall,            99,       iWall,                99,        iBuff_gold,        iWall,                   99,          iWall},
                    /*14*/{iWall,     iBuff_gold,           99,                 iJiGuangGuai_right,99,         99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*15*/{iWall,     iBuff_gold,           iWall,              iWall,            99,       iWall,                99,        iBox,              iWall,                   99,          iWall},
                    /*16*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*17*/{iWall,     iBox,                 iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                    /*18*/{iWall,     iBuff_gold,           99,                 iJiGuangGuai_down,99,          iBuff_gold,        99,        99,                99,                      iCiQiu,      iWall},
                    /*19*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                    /*20*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*21*/{iWall,     iBoxShui,             iWall,              iWall,            iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                    /*22*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*23*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                    /*24*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*25*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBox,              iWall,                   iWall,       iWall},
                    /*26*/{iWall,     iWall,                99,                 99,               99,          99,                99,        99,                99,                      iWall,       iWall},
                    /*27*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       iBox,              iWall,     99,                99,                   iJiGuangGuai_left,iWall},
                    /*28*/{iWall,     iBuff_gold,           99,                 99,               99,          iBuff_gold,        99,        99,                99,                      iWall,       iWall},
                    /*29*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                    /*30*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*31*/{iWall,     iBuff_gold,           iWall,              iWall,            iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                    /*32*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      iBoxHuo,     iWall},
                    /*33*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       99,                iWall,     iBuff_gold,        iWall,                   99,          iWall},
                    /*34*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*35*/{iWall,     iBoxTnt,              iWall,              99,               iWall,       99,                iWall,     iBox,              iWall,                   99,          iWall},
                    /*36*/{iWall,     iBuff_gold,           99,                 99,               99,          99,                99,        99,                99,                      99,          iWall},
                    /*37*/{iWall,     iBuff_gold,           iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                    /*38*/{iWall,     iBuff_gold,           99,                 99,               99,          iBuff_gold,        99,        99,                99,                      iBoxHuo,     iWall},
                    /*39*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                    /*40*/{iWall,     iBuff_gold,           iWall,              iBox,             iWall,       iBuff_gold,        iWall,     99,                iWall,                   99,          iWall},
                    /*41*/{iWall,     iBoxTnt,              iWall,              99,               iWall,       iWall,             iWall,     iWall,             iWall,                   99,           iWall},
                    /*42*/{iWall,     iBoxTnt,              99,                 iBoxShui,         99,          99,                99,        iBoxTnt,           99,                      99,          iWall},
                    /*43*/{iWall,     iTnt_food,            iWall,              99,               iWall,       99,                iWall,     99,                iWall,                   iBoxTnt,     iWall},
                    /*44*/{iWall,     99,                   99,                 iHuo_food,        99,          99,                99,        99,                99,                      99,          iWall},
                    /*45*/{iWall,     99,                   iWall,              iBoxTnt,          iWall,       99,                iWall,     99,                iWall,                   99,          iWall},
                    /*46*/{iWall,     99,                   99,                 iTnt_food,        99,          99,                99,        iBuff_gold,        99,                      99,          iWall},
                    /*47*/{iWall,     99,                   iWall,              99,               iWall,       iBox,              iWall,     99,                iWall,                   99,          iWall},
                    /*48*/{iWall,     iCiQiu,               99,                 99,               99,          iBuff_gold,        99,        99,                99,                      99,          iWall},
                    /*49*/{iWall,     99,                   iWall,              99,               iWall,       iTnt_food,         iWall,     iBox,              iWall,                   99,          iWall},
                    /*50*/{iWall,     99,                   99,                 iWall,            99,          99,                99,        iBuff_gold,        99,                      iBoxShui,    iWall},
            };



    /*************************************************************************************************************/

    /**
     * 屏幕
     */
    public static float suitWidth;//适应后的宽
    public static float suitHeight;//适应后的高
    public static float ratio;//比例
    public static float sx;
    public static float sy;

    public static float ButtonRangeX;//按钮x方向的比例
    public static float ButtonRangeY;//按钮Y方向的比例

    public static ScreenScaleResult screenScaleResult;//存放屏幕的类

    /**
     * 遥杆
     */
    public static float JK_big_radius;//遥感大半径
    public static float Jk_small_radius;//遥感小半径
    public static float JK_big_yuanxinX;//摇杆大环圆心
    public static float JK_big_yuanxinY;//
    public static float JK_small_yuanxin_left_X;//摇杆小环圆心
    public static float JK_small_yuanxin_left_Y;//左
    public static float JK_small_yuanxin_right_X;
    public static float JK_small_yuanxin_right_Y;//右
    public static float JK_small_yuanxin_top_X;
    public static float JK_small_yuanxin_top_Y;//上
    public static float JK_small_yuanxin_bottom_X;
    public static float JK_small_yuanxin_bottom_Y;//下
    public static float JK_small_yuanxin_Current_X;//当前圆心
    public static float Jk_small_yuanxin_Current_Y;
    /**触控范围*/
    public static float  JK_big_left;//摇杆大环左
    public static float  JK_big_right;//摇杆大环右
    public static float  Jk_big_top;//摇杆大环上
    public static float  Jk_big_bottom;//摇杆大环上


    /**
     * 炸弹纹理位置
     */
    public static float commonBombX;//普通炸弹
    public static float commonBombY;
    public static float commonBombChangdu;
    public static float commonBombKuandu;
    public static float tNtBombX;//TNT炸弹
    public static float tNtBombY;
    public static float tNtBombChangdu;
    public static float tNtBombKuandu;

    /**触控范围*/
    public static float commonBomb_left;
    public static float commonBomb_right;
    public static float commonBomb_top;
    public static float commonBomb_bottom;
    public static float tNtBomb_left;
    public static float tNtBomb_right;
    public static float tNtBomb_top;
    public static float tNtBomb_bottom;


    /***
     * 金币和各种食物位置及背景
     */
    public static float Qianbi_background_changdu;//长宽
    public static float Qianbi_background_kuandu;
    public static float Huo_background_changdu;
    public static float Huo_background_kuandu;
    public static float Shui_background_changdu;
    public static float Shui_background_kuandu;
    public static float Qianbi_background_X;//中心点坐标
    public static float Qianbi_background_Y;
    public static float Huo_background_X;
    public static float Huo_background_Y;
    public static float Shui_background_X;
    public static float Shui_background_Y;

    /**
     * 金币数量
     */
    public static float Qianbi_number_changdu;
    public static float Qianbi_number_kuandu;
    public static float Qianbi_number_X1;
    public static float Qianbi_number_x2;
    public static float Qianbi_number_X3;
    public static float Qianbi_number_x4;
    public static float Qianbi_number_Y;
    public static int goldSum =0;//金币数量

    /***水火食物* */
    public static float Shui_changdu;
    public static float Shui_kuandu;
    public static float Shui_start_X;
    public static float Shui_start_Y;
    public static float Huo_changdu;
    public static float Huo_kuandu;
    public static float Huo_start_x;
    public static float Huo_start_Y;
    public static int shuiNum=0;
    public static int huoNum=0;

    /***tnt数量*/
    public static float tnt_number_changdu;
    public static float tnt_number_kuandu;
    public static float tnt_number_X;
    public static float tnt_number_Y;
    public static int tntNum=0;

    /***得分位置*/
    public static float Get_score_X;
    public static float Get_score_Y;
    public static float Get_score_changdu;
    public static float Get_score_kuandu;
    public static int   get_score=0;
    public static float score=0;

    /***奖杯位置*/
    public static float Cup_changdu;
    public static float Cup_kuandu;
    public static float Cup_x;
    public static float Cup_Y;

    /**BEST位置*/
    public static float best_changdu;
    public static float best_kuandu;
    public static float best_X;
    public static float best_Y;

    /*****最高多分*/
    public static float best_number_changdu;
    public static float best_number_kuandu;
    public static float best_number_X;
    public static float best_number_Y;
    public static int best_score;

    /****最终金币*/
    public static float final_jinbi_background_changdu;
    public static float final_jinbi_background_kuandu;
    public static float final_jinbi_background_X;
    public static float final_jinbi_background_Y;

    /***最终得分*/
    public static float final_score_background_changdu;
    public static float final_score_background_kuandu;
    public static float final_score_background_X;
    public static float final_score_background_Y;

    /***复活*/
    public static float relive_changdu;
    public static float relive_kuandu;
    public static float relive_X;
    public static float relive_Y;
    public static float relive_left;
    public static float relive_right;
    public static float relive_top;
    public static float relive_bottom;

    /**重新开始*/
    public static float restart_changdu;
    public static float restart_kuandu;
    public static float restart_X;
    public static float restart_Y;
    public static float restart_left;
    public static float restart_right;
    public static float restart_top;
    public static float restart_bottom;

    /**最终得分*/
    public static float final_score_changdu;
    public static float fianl_score_kuandu;
    public static float final_score_X;
    public static float final_score_Y;

    /**最终金币*/
    public static float final_jinbi_changdu;
    public static float final_jinbi_kuandu;
    public static float final_jibi_X;
    public static float final_jibi_Y;

    /**Tiny背景**/
    public static float Tiny_beijing_changdu;
    public static float Tiny_beijing_kuandu;
    public static float Tiny_beijing_X;
    public static float Tiny_beijing_Y;
    public static float tiny_roate_angle;

    /**设置*/
    public static float set_changdu;
    public static float set_kuandu;
    public static float set_X;
    public static float set_Y;
    public static float set_left;
    public static float set_right;
    public static float set_top;
    public static float set_bottom;

    /**排行榜*/
    public static float chart_changdu;
    public static float chart_kuandu;
    public static float chart_X;
    public static float chart_Y;
    public static float chart_left;
    public static float chart_right;
    public static float chart_top;
    public static float chart_bottom;
    /**手指和手*/
    public static float shou_changdu;
    public static float shou_kuandu;
    public static float shouzhi_changdu;
    public static float shouzhi_kuandu;
    public static float shou_X;
    public static float shou_Y;
    public static int drawShouOrShouzhi;//0 都不画 1 手 2 手指

    /*设置界面*/
    public static float setPageTitle_changdu;//标题
    public static float setPageTitle_kuandu;
    public static float setPageTitle_x;
    public static float setPageTitle_y;

    public static float setPagePanel_changdu;//设置面板
    public static float setPagePanel_kuandu;
    public static float setPagePanel_x;
    public static float setPagePanel_y;
    public static float setPagePanel_left;
    public static float setPagePanel_right;
    public static float setPagePanel_up;
    public static float setPagePanel_down;


    public static float setPagePinzhi_changdu;//设置品质
    public static float setPagePinzhi_kuandu;
    public static float setPagePinzhi_x;
    public static float setPagePinzhi_y;
    public static float setPagePinZhi_left;
    public static float setPagePinZhi_right;
    public static float setPagePinZhi_up;
    public static float setPagePinZhi_down;


    public static float setPageUpYes_changdu;//设置音乐对勾
    public static float setPageUpYes_kuandu;
    public static float setPageUpYes_x;
    public static float setPageUpYes_y;
    public static float setPageUpYes_left;
    public static float setPageUpYes_right;
    public static float setPageUpYes_up;
    public static float setPageUpYes_down;


    public static float setPageDownYes_changdu;//设置音效对勾
    public static float setPageDownYes_kuandu;
    public static float setPageDownYes_x;
    public static float setPageDownYes_y;
    public static float setPageDownYes_left;
    public static float setPageDownYes_right;
    public static float setPageDownYes_up;
    public static float setPageDownYes_down;



    public static float setPageBack_changdu;//设置界面返回
    public static float setPageBack_kuandu;
    public static float setPageBack_x;
    public static float setPageBack_y;
    public static float setPageBack_left;
    public static float setPageBack_right;
    public static float setPageBack_up;
    public static float setPageBack_down;

    public static float pauseChangdu;//暂停
    public static float pauseKuandu;
    public static float pauseX;
    public static float pauseY;
    public static float pauseleft;
    public static float pauseright;
    public static float pauseup;
    public static float pausedown;

    public static float overChangdu;//结束
    public static float overKuandu;
    public static float overX;
    public static float overY;
    public static float overleft;
    public static float overright;
    public static float overup;
    public static float overdown;

    public static float resumeChangdu;//继续
    public static float resumeKuandu;
    public static float resumeX;
    public static float resumeY;
    public static float resumeleft;
    public static float resumeright;
    public static float resumeup;
    public static float resumedown;



    /*排行榜*/
    public static float chartPageTitle_changdu;//排行榜界面标题
    public static float chartPageTitle_kuandu;
    public static float chartPageTitle_x;
    public static float chartPageTitle_y;

    public static float chartPagePanel_changdu;//排行榜面板
    public static float chartPagePanel_kuandu;
    public static float chartPagePanel_x;
    public static float chartPagePanel_y;
    public static float chartPagePanel_left;
    public static float chartPagePanel_right;
    public static float chartPagePanel_up;
    public static float chartPagePanel_down;

    public static float chartScore_changdu;
    public static float chartScore_kuandu;
    public static float FirstPlace_x;//冠军季军亚军分数绘制位置
    public static float FirstPlace_y;
    public static float SecondPlace_x;
    public static float SecondPlace_y;
    public static float ThirdPlace_x;
    public static float ThirdPlace_y;

    public static boolean updateChart=true;






    /***
     * 前后间距
     */
    public static float SPAN=25*SideLengh;//人前后画的距离
    public static float dropLength=20*SideLengh;
    public static float OriginSPAN=25*SideLengh;
    public static float OriginDropLength=20*SideLengh;
    public static float GameSPAN=9*SideLengh;
    public static float GameDropLength=20*SideLengh;
    public static float mapRoateAngle=-2.5f;
    public static  Map<Integer,Float> startEndMap;//地图起止点
    public static Map<Integer,int[][]> WhichMap;//第几关地图
    public static int OneMapLength=FirstLevelMap.length;
    public static int MapWidth=FirstLevelMap[0].length;//地图横向砖块数量

    /**z转动角度*/
    public static float mango_mapRoate=mapRoateAngle/(SideLengh/ZDRStepAngle);

    /***所吃食物所在的行和列*/
    public static float tnt_current_Position[]=new float[2];
    public static float tnt_distinceX;
    public static float tnt_distinceY;
    public static float huo_current_Postion[]=new float[2];
    public static float huo_distinceX;
    public static float huo_distinceY;
    public static float shui_current_Position[]=new float[2];
    public static float shui_distinceY;
    public static float shui_distinceX;

    /***是否画飞行事物动画**/
    public static boolean drawFlyTnt;
    public static boolean drawFlyHuo;
    public static boolean drawFlyShui;

    /**重新开始变量控制*/
    public static boolean drawMap=true;
    public static boolean ifrestart=false;

    /***是否画人物*/
    public static boolean drawHuman=true;
    public static long lastDrawHumanTime=0;
    /**人物死法*/
    public static int humanDeadWay=0;
    /**复活*/
    public static int reliveTime=0;//复活次数
    public static boolean ifrelive=false;//是否复活
    public static boolean CanTouch=true;//是否支持触控
    public static int bombrange=3;//爆炸范围


    /**设置相机*/
    public static boolean upcrama=false;//是否升高
    public static int upcramaTime;//第几次升高
    public static boolean downcrama=false;//是否降低
    public static int downcramaTime;//降低次数
    public static boolean hasdowncrama=false;//降低完成
    public static boolean roatecrama=false;
    public static int roatecramaTime;//转动次数
    public static boolean dietoaliveUpCrama=false;//复活摄像机移动
    public static int dietoaliveUpCramaTime;
    public static boolean recovercrama =false;//重新开始摄像机移动
    public static int recovercramaTime;

    /**飞行2d*/
    public static boolean hadDraw=false;
    public static int drawTime;
    public static boolean hadDrawDead2D=false;
    public static int drawDead2DTime;
    public static int LongestTime=10;
    public static boolean hadDrawStart2D=false;
    public static boolean hadDrawChart2D=false;
    public static boolean hadDrawSet2D=false;
    public static int hadDrawStart2DTime;
    public static int hadDrawChart2DTiem;
    public static int hadDrawSet2DTime;

    /*激光怪*/
    public static int JiGuangRangeLong=2;

    /**切换场景*/
    public static boolean transformScene1=false;
    public static boolean transformScene2=false;
    public static boolean transformScene3=false;
    public static int transformScene1Time;
    public static int transformScene2Time;
    public static int transformScene3Time;

    /**
     * 计算位置
     */
    public static void calculatLocationData()
    {
        System.out.print("********************高="+SCREEN_HEIGHT+"********************************宽="+SCREEN_WIDTH);
        screenScaleResult=ScreenScaleUtil.calScale(SCREEN_WIDTH,SCREEN_HEIGHT);
        ratio=screenScaleResult.ratio;
        sx=screenScaleResult.lucX;
        sy=screenScaleResult.lucY;
        System.out.println("sx="+sx+"  sy="+sy+"  ratio="+ratio);
        //按钮的xy坐标比例
        if (SCREEN_WIDTH>SCREEN_HEIGHT) {
            ButtonRangeX = SCREEN_HEIGHT / 2;
            ButtonRangeY = SCREEN_HEIGHT / 2;
        }
        else {
            ButtonRangeX=SCREEN_WIDTH/2;
            ButtonRangeY=SCREEN_HEIGHT/2;
        }
        //长方形的宽和长
        /***遥感*/
        JK_big_radius=ButtonRangeX*1f;//
        Jk_small_radius=JK_big_radius*0.4f;//0.5*0.4
        /***炸弹*/
        commonBombChangdu=ButtonRangeX*0.32f;
        commonBombKuandu=ButtonRangeX*0.32f;
        tNtBombChangdu=ButtonRangeX*0.32f;
        tNtBombKuandu=ButtonRangeX*0.32f/70f*100;
        /**食物和金币*/
        Qianbi_background_changdu=ButtonRangeX*0.587f;
        Qianbi_background_kuandu=ButtonRangeX*0.17f;
        Huo_background_changdu=ButtonRangeX*0.587f;
        Huo_background_kuandu=ButtonRangeX*0.13f;
        Shui_background_changdu=ButtonRangeX*0.427f;
        Shui_background_kuandu=ButtonRangeX*0.13f;
        /**金币数量*/
        Qianbi_number_changdu=Qianbi_background_changdu*0.12f;
        Qianbi_number_kuandu=Qianbi_background_kuandu*0.9f;
        /**水火食物*/
        Shui_kuandu=Shui_background_kuandu*0.5f;
        Shui_changdu=Shui_kuandu;
        Huo_kuandu=Huo_background_kuandu*0.5f;
        Huo_changdu=Huo_kuandu;
        /**tnt数量*/
        tnt_number_kuandu=tNtBombKuandu*0.17f;
        tnt_number_changdu=tnt_number_kuandu*0.5f;
        /***得分*/
        Get_score_changdu=ButtonRangeX*0.15f;
        Get_score_kuandu=ButtonRangeX*0.4f;
        /***奖杯*/
        Cup_changdu=0.13f*ButtonRangeX;
        Cup_kuandu=0.16f*ButtonRangeX;
        /***BEST*/
        best_changdu=0.16f*ButtonRangeX;
        best_kuandu=0.08f*ButtonRangeX;
        /**最好得分*/
        best_number_changdu=0.10f*ButtonRangeX;
        best_number_kuandu=0.08f*ButtonRangeX;
        /***最终金币*/
        final_jinbi_background_changdu=0.601f*ButtonRangeX;
        final_jinbi_background_kuandu=0.195f*ButtonRangeX;
        /***最终得分**/
        final_score_background_changdu=1.287f*ButtonRangeX;
        final_score_background_kuandu=0.283f*ButtonRangeX;
        /**复活*/
        relive_changdu=0.787f*ButtonRangeX;
        relive_kuandu=0.352f*ButtonRangeX;
        /**重新开始*/
        restart_changdu=0.352f*ButtonRangeX;
        restart_kuandu=0.352f*ButtonRangeX;
        /***最终金币*/
        final_jinbi_kuandu=0.150f*ButtonRangeX;
        final_jinbi_changdu=final_jinbi_kuandu*0.5f;
        /**最终得分*/
        fianl_score_kuandu=0.230f*ButtonRangeX;
        final_score_changdu=0.55f*fianl_score_kuandu;
        /**排行榜*/
        chart_changdu=0.374f*ButtonRangeX;
        chart_kuandu=0.315f*ButtonRangeX;
        /**设置*/
        set_changdu=0.374f*ButtonRangeX;
        set_kuandu=0.315f*ButtonRangeX;
        /**背景**/
        Tiny_beijing_changdu=1.68f*ButtonRangeX;
        Tiny_beijing_kuandu=0.7f*ButtonRangeX;
        /**手指和手*/
        shou_changdu=0.34f*ButtonRangeX;
        shou_kuandu=0.5f*ButtonRangeX;
        shouzhi_changdu=0.328f*ButtonRangeX;
        shouzhi_kuandu=0.425f*ButtonRangeX;
        /*设置界面的标题*/
        setPageTitle_changdu=ButtonRangeX;
        setPageTitle_kuandu=setPageTitle_changdu/5;
        /*设置界面设置面板*/
        setPagePanel_changdu = ButtonRangeX*3/2;
        setPagePanel_kuandu = ButtonRangeX;
        /*设置面板品质高低*/
        setPagePinzhi_changdu = setPagePanel_changdu/14;
        setPagePinzhi_kuandu = setPagePanel_changdu/14;
        /*设置音乐对勾*/
        setPageUpYes_changdu = setPagePanel_changdu*0.0554528f;
        setPageUpYes_kuandu = setPagePanel_changdu*0.0554528f;
        /*设置音效对勾*/
        setPageDownYes_changdu = setPagePanel_changdu*0.0554528f;
        setPageDownYes_kuandu = setPagePanel_changdu*0.0554528f;
        /*设置界面返回*/
        setPageBack_changdu = ButtonRangeX/3;
        setPageBack_kuandu = 0.8595f*setPageBack_changdu;
        /*排行榜*/
        chartPageTitle_changdu = ButtonRangeX;
        chartPageTitle_kuandu = setPageTitle_changdu/5;
        /*排行榜面板*/
        chartPagePanel_changdu = ButtonRangeX*3/2;
        chartPagePanel_kuandu = ButtonRangeX;
        /*排行榜分数*/
        chartScore_changdu = chartPagePanel_changdu/6;
        chartScore_kuandu = chartPagePanel_changdu*0.1f;
        /*暂停*/
        pauseChangdu=commonBombChangdu*0.8f;
        pauseKuandu=commonBombKuandu*0.8f;
        /*结束*/
        overChangdu=commonBombChangdu;
        overKuandu=commonBombChangdu;
        /*继续*/
        resumeChangdu=ButtonRangeX;
        resumeKuandu=0.14f*ButtonRangeX;


        //中心坐标
        /****遥感*/
        JK_big_yuanxinX=0.5f*JK_big_radius;//大环圆心
        JK_big_yuanxinY=2*ButtonRangeY-(0.5f*JK_big_radius);
        JK_small_yuanxin_left_X=JK_big_yuanxinX-(JK_big_radius/2-Jk_small_radius/2);//左环
        JK_small_yuanxin_left_Y=JK_big_yuanxinY;
        JK_small_yuanxin_right_X=JK_big_yuanxinX+(JK_big_radius/2-Jk_small_radius/2);//右环
        JK_small_yuanxin_right_Y=JK_big_yuanxinY;
        JK_small_yuanxin_top_X=JK_big_yuanxinX;//上圆
        JK_small_yuanxin_top_Y=JK_big_yuanxinY-(JK_big_radius/2-Jk_small_radius/2);
        JK_small_yuanxin_bottom_X=JK_big_yuanxinX;//下圆
        JK_small_yuanxin_bottom_Y=JK_big_yuanxinY+(JK_big_radius/2-Jk_small_radius/2);
        JK_small_yuanxin_Current_X=JK_big_yuanxinX;//当前坐标
        Jk_small_yuanxin_Current_Y=JK_big_yuanxinY;
        /**炸弹*/
        tNtBombX=SCREEN_WIDTH-0.08f*ButtonRangeX-tNtBombChangdu/2;
        tNtBombY=ButtonRangeY+ButtonRangeY-0.08f*ButtonRangeX-tNtBombKuandu/2;
        commonBombX=SCREEN_WIDTH-0.06f*ButtonRangeX-commonBombChangdu/2;
        commonBombY=ButtonRangeY+ButtonRangeY-0.16f*ButtonRangeX-tNtBombKuandu-commonBombKuandu/2;
        /**食物金币*/
        Qianbi_background_X=0.08f*ButtonRangeX+Qianbi_background_changdu/2;
        Qianbi_background_Y=0.06f*ButtonRangeX+Qianbi_background_kuandu/2;
        Huo_background_X=0.08f*ButtonRangeX+Huo_background_changdu/2;
        Huo_background_Y=0.06f*ButtonRangeX*2+Qianbi_background_kuandu+Huo_background_kuandu/2;
        Shui_background_X=0.08f*ButtonRangeX+Shui_background_changdu/2;
        Shui_background_Y=0.06f*ButtonRangeX*3+Qianbi_background_kuandu+Huo_background_kuandu+Shui_background_kuandu/2;
        /***金币*/
        Qianbi_number_Y=0.08f*ButtonRangeX+Qianbi_background_kuandu*0.40f;
        Qianbi_number_X1=0.410f*ButtonRangeX;
        Qianbi_number_x2=Qianbi_number_X1;//-0.5fQianbi_number_changdu;
        Qianbi_number_X3=Qianbi_number_X1-0.5f*Qianbi_number_changdu*1;
        Qianbi_number_x4=Qianbi_number_X1-0.5f*Qianbi_number_changdu*2;
        /***水火食物*/
        Shui_start_X=0.17f*ButtonRangeX;
        Shui_start_Y=Shui_background_Y;
        Huo_start_x=0.17f*ButtonRangeX;
        Huo_start_Y=Huo_background_Y;
        /***tnt数量*/
        tnt_number_X=SCREEN_WIDTH-0.11f*ButtonRangeX-tNtBombChangdu/2;
        tnt_number_Y=ButtonRangeY+ButtonRangeY-0.08f*ButtonRangeX-tNtBombKuandu*0.82f;
        /**得分位置*/
        Get_score_X=0.90f*ButtonRangeX;
        Get_score_Y=0.212f*ButtonRangeX;
        /***best位置*/
        best_X=0.96f*ButtonRangeX;
        best_Y=0.380f*ButtonRangeX;
        /**最好得分位置*/
        best_number_X=1.06f*ButtonRangeX;
        best_number_Y=0.380f*ButtonRangeX;
        /***奖杯位置*/
        Cup_x=1.13f*ButtonRangeX;
        Cup_Y=0.210f*ButtonRangeX;
        /***最终金币*/
        final_jinbi_background_X=SCREEN_WIDTH/2;
        final_jinbi_background_Y=0.037f*ButtonRangeX+final_jinbi_background_kuandu/2;
        /**最终得分**/
        final_score_background_X=SCREEN_WIDTH/2;
        final_score_background_Y=0.045f*ButtonRangeX*2+final_jinbi_background_kuandu+final_score_background_kuandu/2;
        /***复活*/
        relive_X=SCREEN_WIDTH/2-relive_changdu/4;
        relive_Y=SCREEN_HEIGHT-relive_kuandu/2-0.037f*ButtonRangeX;
        /**重新开始*/
        restart_X=SCREEN_WIDTH/2+relive_changdu/2;
        restart_Y=SCREEN_HEIGHT-restart_kuandu/2-0.037f*ButtonRangeX;
        /****最终金币*/
        final_jibi_X=SCREEN_WIDTH/2;
        final_jibi_Y=final_jinbi_background_Y;
        /***最终得分*/
        final_score_X=SCREEN_WIDTH/2+10;
        final_score_Y=final_score_background_Y;
        /**设置*/
        set_X=SCREEN_WIDTH-0.08f*ButtonRangeX-set_changdu/2;
        set_Y=SCREEN_HEIGHT-0.08f*ButtonRangeX-set_kuandu/2;
        /**排行榜*/
        chart_X=0.08f*ButtonRangeX+chart_changdu/2;
        chart_Y=SCREEN_HEIGHT-0.08f*ButtonRangeX-chart_kuandu/2;
        /**Tiny背景**/
        Tiny_beijing_X=SCREEN_WIDTH/2;
        Tiny_beijing_Y=0.5f*ButtonRangeX+Tiny_beijing_kuandu/2;
        /**手指和手*/
        shou_X=SCREEN_WIDTH/2;
        shou_Y=SCREEN_HEIGHT/2;
        /*设置界面的标题*/
        setPageTitle_x=SCREEN_WIDTH/2;
        setPageTitle_y=ButtonRangeY/5;
        /*设置界面设置面板*/
        setPagePanel_x = SCREEN_WIDTH/2;
        setPagePanel_y = SCREEN_HEIGHT/2;
        /*设置面板品质高低*/
        setPagePinzhi_x = 0.265f*setPagePanel_changdu+SCREEN_WIDTH/2;
        setPagePinzhi_y = setPagePanel_y-0.240f*setPagePanel_kuandu;
        /*设置音乐对勾*/
        setPageUpYes_x = setPagePanel_x+0.278210f*setPagePanel_changdu;
        setPageUpYes_y = setPagePanel_y+setPagePanel_kuandu*0.001355f;
        /*设置音效对勾*/
        setPageDownYes_x = setPagePanel_x+0.278210f*setPagePanel_changdu;
        setPageDownYes_y = setPagePanel_y+setPagePanel_kuandu*0.231707f;
        /*设置界面返回*/
        setPageBack_x = ButtonRangeX/15+setPageBack_kuandu/2;
        setPageBack_y = SCREEN_HEIGHT-ButtonRangeX/15-setPageBack_kuandu/2;
        /*排行榜*/
        chartPageTitle_x = SCREEN_WIDTH/2;
        chartPageTitle_y = ButtonRangeY/5;
        /*排行榜面板*/
        chartPagePanel_x = SCREEN_WIDTH/2;
        chartPagePanel_y = SCREEN_HEIGHT/2;
        /*冠军分数*/
        FirstPlace_x = SCREEN_WIDTH/2;
        FirstPlace_y = setPagePanel_y-0.240f*setPagePanel_kuandu;
        /*亚军分数*/
        SecondPlace_x = SCREEN_WIDTH/2;
        SecondPlace_y = setPagePanel_y+setPagePanel_kuandu*0.001355f;
        /*季军分数*/
        ThirdPlace_x = SCREEN_WIDTH/2;
        ThirdPlace_y = setPagePanel_y+setPagePanel_kuandu*0.231707f;
         /*暂停*/
        pauseX=SCREEN_WIDTH-pauseChangdu/2-0.04f*ButtonRangeX;
        pauseY=0.06f*ButtonRangeX+pauseKuandu/2;
        /*结束*/
        overY=0.08f*ButtonRangeX+overKuandu/2;
        overX=SCREEN_WIDTH/2;
        /*继续*/
        resumeX=SCREEN_WIDTH/2;
        resumeY=SCREEN_HEIGHT/2;


        //触控范围
        /**遥感*/
        JK_big_left=JK_big_yuanxinX-4*JK_big_radius/3;
        JK_big_right=JK_big_yuanxinX+4*JK_big_radius/3;
        Jk_big_top=JK_big_yuanxinY-4*JK_big_radius/3;
        Jk_big_bottom=JK_big_yuanxinY+4*JK_big_radius/3;
        /**炸弹*/
        commonBomb_left=commonBombX-commonBombChangdu/2;
        commonBomb_right=commonBombX+commonBombChangdu/2;
        commonBomb_top=commonBombY-commonBombKuandu/2;
        commonBomb_bottom=commonBombY+commonBombKuandu/2;
        tNtBomb_left=tNtBombX-tNtBombChangdu/2;
        tNtBomb_right=tNtBombX+tNtBombChangdu/2;
        tNtBomb_top=tNtBombY-tNtBombKuandu/2;
        tNtBomb_bottom=tNtBombY+tNtBombKuandu/2;
        /****复活*/
        relive_left=relive_X-relive_changdu/2;
        relive_right=relive_X+relive_changdu/2;
        relive_top=relive_Y-relive_kuandu/2;
        relive_bottom=relive_Y+relive_kuandu/2;
        /***重新开始*/
        restart_left=restart_X-restart_changdu/2;
        restart_right=restart_X+restart_changdu/2;
        restart_top=restart_Y-restart_kuandu/2;
        restart_bottom=restart_Y+restart_kuandu/2;
        /**排行榜*/
        chart_left=chart_X-chart_changdu/2;
        chart_right=chart_X+chart_changdu/2;
        chart_top=chart_Y-chart_kuandu/2;
        chart_bottom=chart_Y+chart_kuandu/2;
        /**设置*/
        set_left=set_X-set_changdu/2;
        set_right=set_X+set_changdu/2;
        set_top=set_Y-set_kuandu/2;
        set_bottom=set_Y+set_kuandu/2;
        /*设置界面设置面板的上下左右*/
        setPagePanel_left = setPagePanel_x-setPagePanel_changdu/2;
        setPagePanel_right = setPagePanel_x+setPagePanel_changdu/2;
        setPagePanel_up = setPagePanel_y-setPagePanel_kuandu/2;
        setPagePanel_down = setPagePanel_y+setPagePanel_kuandu/2;
        /*设置界面设置游戏品质*/
        setPagePinZhi_left = setPagePanel_x;
        setPagePinZhi_right = setPagePanel_right;
        setPagePinZhi_up = setPagePanel_up+setPagePanel_kuandu/13*2.5f;
        setPagePinZhi_down = setPagePanel_up+setPagePanel_kuandu/13*4.5f;
        /*设置界面设置游戏音乐*/
        setPageUpYes_left = setPagePanel_x;
        setPageUpYes_right = setPagePanel_right;
        setPageUpYes_up = setPagePanel_up+setPagePanel_kuandu/13*5.0f;
        setPageUpYes_down = setPagePanel_up+setPagePanel_kuandu/13*7.5f;
        /*设置界面设置游戏音效*/
        setPageDownYes_left = setPagePanel_x;
        setPageDownYes_right = setPagePanel_right;
        setPageDownYes_up = setPagePanel_up+setPagePanel_kuandu/13*8.5f;
        setPageDownYes_down = setPagePanel_up+setPagePanel_kuandu/13*10.5f;
        /*设置界面返回键*/
        setPageBack_left = setPageBack_x-setPageBack_changdu/2;
        setPageBack_right = setPageBack_x+setPageBack_changdu/2;
        setPageBack_up = setPageBack_y-setPageBack_changdu/2;
        setPageBack_down = setPageBack_y+setPageBack_changdu/2;
        /*排行榜面板*/
        chartPagePanel_left = chartPagePanel_x-chartPagePanel_changdu/2;
        chartPagePanel_right = chartPagePanel_x+chartPagePanel_changdu/2;;
        chartPagePanel_up = chartPagePanel_y-chartPagePanel_kuandu/2;
        chartPagePanel_down = chartPagePanel_y+chartPagePanel_kuandu/2;
        /*暂停*/
        pauseleft=pauseX-pauseChangdu/2;
        pauseright=pauseX+pauseChangdu/2;
        pauseup=pauseY-pauseKuandu/2;
        pausedown=pauseY+pauseKuandu/2;
        /*结束*/
        overleft=overX-overChangdu/2;
        overright=overX+overChangdu/2;
        overup=overY-overKuandu/2;
        overdown=overY+overKuandu/2;
        /*继续*/
        resumeleft=resumeX-resumeChangdu/2;
        resumeright=resumeX+resumeChangdu/2;
        resumeup=resumeY-resumeKuandu/2;
        resumedown=resumeY+resumeKuandu/2;

        /**
         * 每块地图起止z坐标
         */
        startEndMap=new HashMap<Integer, Float>();
        startEndMap.put(0,-0.5f);//起点
        startEndMap.put(1,-0.5f-OneMapLength*1*SideLengh);//第一块终点
        startEndMap.put(2,-0.5f-OneMapLength*2*SideLengh);
        startEndMap.put(3,-0.5f-OneMapLength*3*SideLengh);
        startEndMap.put(4,-0.5f-OneMapLength*4*SideLengh);
        startEndMap.put(5,-0.5f-OneMapLength*5*SideLengh);

        /**
         * 每关地图
         */
        WhichMap=new HashMap<Integer, int[][]>();
        WhichMap.put(1,FirstLevelMap);
        WhichMap.put(2,SecondLevelMap);
//        WhichMap.put(3,ThirdLevelMap);
//        WhichMap.put(4,SecondLevelMap);
//        WhichMap.put(5,ThirdLevelMap);

        /***缓存*/
        best_score=UserInfoUtil.GetFisrtScore();
        goldSum=UserInfoUtil.GetGold();
        ifXuHua=true;
        UserInfoUtil.WriteGamePinzhi(ifXuHua);

    }

    public static int jk_bigId;//遥感大环
    public static int jk_smallId;//遥感小环
    public static int tntBombId;//tnt食物
    public  static int jinbi_backgroundId;//金币背景
    public static int huo_backgroundId;//火背景
    public static int shui_backgroundId;//水背景
    public static int shui_backgroundId2;//水背景
    public static int huo_backgroundId2;//火背景
    public static int numberId;//金币数量id
    public static int w_shuiId;//白水id
    public static int b_shuiId;//黑水id
    public static int b_huoId;//黑火
    public static int w_huoId;//白水
    public static int ButtonDropTntTextureId2;//游戏界面放置TNT纹理
    public static int shui_foodId;//水食物id
    public static int huo_foodId;//火食物id
    public static int huo_fly_foodId;//飞行 火
    public static int tnt_fly_foodId;//飞行tnt
    public static int shui_fly_foodId;//飞行水
    public static int skyId;//天空
    public static int cupId;//奖杯id
    public static int bestId;//最好的id
    public static int final_score_backgroundId;//最终得分id
    public static int reliveId;//重新复活
    public static int relivd2Id;//重新复活
    public static int relive3Id;//重新复活
    public static int restartId;//重新开始
    public static int ciqiuId;//刺球怪
    public static int setId;//设置
    public static int chartId;//排行榜
    public static int beijingId;//背景
    public static int shouzhiId;//手指
    public static int shouId;//手
    public static int bowenId;//波纹效果
    public static int bothSideCubetexture1;
    public static int bothSideCubetexture2;
    public static int bothSideCubetexture3;
    public static int pauseId;//暂停
    public static int resumeId;//继续
    public static int overId;//结束
    public static int mohuId;//模糊背景
    public static int sky2Id;//天空
    public static int sky3Id;//天空
    public static int manDownRectId;//人下红框
}