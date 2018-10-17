package com.bn.ZDR.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.bn.ZDR.Thread.WelcomeThread;
import com.bn.ZDR.View.MyGLSurfaceView;

import java.util.HashMap;

import static com.bn.ZDR.Util.UserInfoUtil.GetYinxiao;
import static com.bn.ZDR.Util.UserInfoUtil.GetYinyue;

public class MainActivity extends Activity
{

    MyGLSurfaceView myGLSurfaceView;
    public MediaPlayer gameBGM;//游戏背景音乐
    public MediaPlayer openBGM;//开场背景音乐
    public MediaPlayer overBGM;//死亡背景音乐

    public MediaPlayer nowBGM;//当前背景音乐


    SoundPool EQSoundPool;//声音池
    public HashMap<Integer,Integer> soundId;//声音id

    AudioManager amg;

    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏

        //获取屏幕宽度高度
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        Constant.SCREEN_WIDTH = outMetrics.widthPixels;
        Constant.SCREEN_HEIGHT= outMetrics.heightPixels;


        //计算屏幕自适应
        Constant.ssr= ScreenScaleUtil.calScale( Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT);
        //计算游戏界面2D绘制坐标
        Constant.calculatLocationData();

        initEQ();
        initBGM();
        amg=(AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        myGLSurfaceView=new MyGLSurfaceView(MainActivity.this);  //实例化OpenGL布局
        myGLSurfaceView.requestFocus();             //获取焦点
        myGLSurfaceView.setFocusableInTouchMode(true);//设置可触控
        setContentView(myGLSurfaceView);

        //setContentView(welcomeView);            //设置显示界面

    }

    @Override
    public void onResume()
    {
        super.onResume();
//        myGLSurfaceView.currview.onResume()x;
        resumeBGM();
    }

    @Override
    public void onPause()
    {
        super.onPause();
       // myGLSurfaceView.onPause();
        pauseBGM();
    }

    public void initBGM()//加载背景音乐
    {
        openBGM = MediaPlayer.create(this,R.raw.bgm_open);
        gameBGM = MediaPlayer.create(this,R.raw.bgm_game1);
    }

    public void initEQ()//加载音效
    {
        EQSoundPool=new SoundPool(   //创建声音池
                6,					 //同时播放流的最大数量
                AudioManager.STREAM_MUSIC,  //流的类型
                100
        );
        soundId=new HashMap<Integer,Integer>();//创建hashmap
        soundId.put(1, EQSoundPool.load(this, R.raw.explode, 1));//加载声音并添加到hashmap中
        soundId.put(2, EQSoundPool.load(this, R.raw.luodi, 1));
        soundId.put(3, EQSoundPool.load(this, R.raw.button, 1));
        soundId.put(4, EQSoundPool.load(this, R.raw.coin, 1));
        soundId.put(5, EQSoundPool.load(this, R.raw.walk, 1));
        soundId.put(6, EQSoundPool.load(this, R.raw.transfrom,1));
//        soundId.put(6, EQSoundPool.load(this, R.raw.laugh, 1));
//        soundId.put(7, EQSoundPool.load(this, R.raw.chubing, 1));
//        soundId.put(8, EQSoundPool.load(this, R.raw.jianshi, 1));
//        soundId.put(9, EQSoundPool.load(this, R.raw.explode, 1));
//        soundId.put(10, EQSoundPool.load(this,R.raw.shengli, 1));
//        soundId.put(11, EQSoundPool.load(this,R.raw.shibai, 1));
    }

    public void resumeBGM()
    {
        if(!GetYinyue())
        {
            if(nowBGM!=null&&nowBGM.isPlaying())
            {
                nowBGM.pause();
                try
                {
                    nowBGM.prepare();
                }
                catch (Exception  e)
                {
                    e.printStackTrace();
                }
                nowBGM.seekTo(0);
            }
        }
        else
        {
            if(nowBGM!=null&&!nowBGM.isPlaying())
            {
                nowBGM.setLooping(true);
                nowBGM.start();
            }
        }
    }

    public void pauseBGM()
    {
        if (nowBGM.isPlaying())
        {
            nowBGM.pause();
        }
    }

//    public void playEQ(int sound,int loop)
//    {
//        if(!GetYinxiao()) return;
//        //播放声音
//        EQSoundPool.play(soundId.get(sound), 1, 1, 1, loop, 1f);
//    }
    public void playEQ(int sound,int loop)
    {
        if(!GetYinxiao()) return;
        float streamVolumeCurrent=								//获得初始音量
                amg.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax=									//获得最大音量
                amg.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume=streamVolumeCurrent+10/streamVolumeMax;		//计算当前音量
        EQSoundPool.play(soundId.get(sound), volume, volume, 1, loop, 1f);//播放声音
    }

}
