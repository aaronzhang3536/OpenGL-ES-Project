package com.bn.ZDR.Util;

import java.util.HashMap;
import java.util.Map;

public class PicOrignData
{
    public static String[] picName={"first_level_game_land.png","first_level_game_wall.jpg"};
    public static int[][] picSize={{386,554},{1024,1024}};
    public static Map<String,int[]> picInfo=new HashMap<String,int[]>();

    static
    {
        for(int i=0;i<picName.length;i++)
        {
            picInfo.put(picName[i],picSize[i]);
        }
    }

    public static int getPicWidth(String pn)
    {
        return picInfo.get(pn)[0];
    }

    public static int getPicHeight(String pn)
    {
        return picInfo.get(pn)[1];
    }
}
