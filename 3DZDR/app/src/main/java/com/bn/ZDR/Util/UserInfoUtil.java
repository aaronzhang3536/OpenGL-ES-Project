package com.bn.ZDR.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_WRITEABLE;
import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;

/**
 * Created by Administrator on 2017/4/3.
 */

public class UserInfoUtil
{
    public static SharedPreferences sp = ContextUtil.getInstance().getSharedPreferences("UID",Context.MODE_MULTI_PROCESS |MODE_WORLD_WRITEABLE);
    //存入数据
    static SharedPreferences.Editor editor = sp.edit();


    public static void CompareSorce(int score)
    {
        if(score>GetThirdScore())
        {
            if(score>GetSecondScore())
            {
                if(score>GetFisrtScore())
                {
                    WriteScore(score, 1);
                }
                else
                {
                    WriteScore(score, 2);
                }

            }
            else
            {
                WriteScore(score, 3);
            }

        }
    }

    public static void WriteScore(int score,int place)
    {
        switch (place)
        {
            case 0:
                break;
            case 1:
                editor.putInt("thirdscore", GetSecondScore());
                editor.commit();
                editor.putInt("secondscore", GetFisrtScore());
                editor.commit();
                editor.putInt("firstscore", score);
                editor.commit();
                System.out.println("111");
                break;
            case 2:
                editor.putInt("thirdscore", GetSecondScore());
                editor.commit();
                editor.putInt("secondscore", score);
                editor.commit();
                System.out.println("222");
                break;
            case 3:
                editor.putInt("thirdscore", score);
                editor.commit();
                System.out.println("333");
                break;
        }
    }

    public static int GetFisrtScore(){return sp.getInt("firstscore",0);}

    public static int GetSecondScore(){return sp.getInt("secondscore",0);}

    public static int GetThirdScore(){return sp.getInt("thirdscore",0);}


    public static void WriteGold(int gold)
    {
        editor.putInt("gold", gold);
        editor.commit();
    }

    public static int GetGold() {return sp.getInt("gold",0);}

    /*游戏品质*/
    public static void WriteGamePinzhi(boolean pinzhi){
        editor.putBoolean("pinzhi", pinzhi);
        editor.commit();
    }

    public static boolean GetPinzhi(){return sp.getBoolean("pinzhi",true);}

    /*游戏音乐*/
    public static void WriteGameYinyue(){
        editor.putBoolean("yinyue", !GetYinyue());
        editor.commit();
    }

    public static boolean GetYinyue(){return sp.getBoolean("yinyue",true);}

    /*游戏音效*/
    public static void WriteGameYinxiao(){
        editor.putBoolean("yinxiao", !GetYinxiao());
        editor.commit();
    }

    public static boolean GetYinxiao(){return sp.getBoolean("yinxiao",true);}


}

