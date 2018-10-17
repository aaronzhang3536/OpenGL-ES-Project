package com.bn.ZDR.Thread;

import com.bn.ZDR.Scene.CiQiuGoForControl;
import com.bn.ZDR.Util.Constant;
import com.bn.ZDR.View.GameView;

import java.util.ArrayList;

import static com.bn.ZDR.Util.Constant.CiQiuGoThreadFlag;
import static com.bn.ZDR.Util.Constant.SPAN;
import static com.bn.ZDR.Util.Constant.dropLength;

/**
 * Created by zhuanxu on 2017/8/22.
 */

public class CiQiuGoThread extends Thread
{
    ArrayList<CiQiuGoForControl> ciQiuGoForControlsList;//子弹列表
    GameView gameView;
    ArrayList<CiQiuGoForControl> deleteCiQiuList=new ArrayList<CiQiuGoForControl>();
    public boolean flag=true;//循环标志位

    public CiQiuGoThread(ArrayList<CiQiuGoForControl> ciQiuGoForControls, GameView gameView)
    {
        this.ciQiuGoForControlsList =ciQiuGoForControls;
        this.gameView=gameView;
    }

    public void run()
    {
        while(true)
        {//循环定时移动炮弹
            doSometing();
            try
            {
                Thread.sleep(50);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void doSometing()
    {
        if (CiQiuGoThreadFlag)
        {
            try
            {
                deleteCiQiuList.clear();
                for(CiQiuGoForControl ciQiuGoForControl: ciQiuGoForControlsList)
                {
                    if(ciQiuGoForControl.startZoffset<(gameView.ZDRZ-(SPAN+dropLength+1)))
                    {
                        deleteCiQiuList.add(ciQiuGoForControl);
                    }
                    else
                    ciQiuGoForControl.go();
                }
                for (CiQiuGoForControl ciQiuGoForControl:deleteCiQiuList)
                {
                    ciQiuGoForControlsList.remove(ciQiuGoForControl);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
