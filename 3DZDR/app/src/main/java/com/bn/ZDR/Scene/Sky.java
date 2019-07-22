package com.bn.ZDR.Scene;

import com.bn.ZDR.Util.MatrixState3D;
import com.bn.ZDR.Util.TextureRect3D;
import com.bn.ZDR.View.GameView;
import com.bn.ZDR.View.MyGLSurfaceView;

import static com.bn.ZDR.Util.Constant.OneMapLength;
import static com.bn.ZDR.Util.Constant.sky2Id;
import static com.bn.ZDR.Util.Constant.sky3Id;
import static com.bn.ZDR.Util.Constant.skyId;
import static com.bn.ZDR.Util.Constant.transformScene1;
import static com.bn.ZDR.Util.Constant.transformScene1Time;
import static com.bn.ZDR.Util.Constant.transformScene2;
import static com.bn.ZDR.Util.Constant.transformScene2Time;
import static com.bn.ZDR.Util.Constant.transformScene3;
import static com.bn.ZDR.Util.Constant.transformScene3Time;

/**
 * Created by zhuanxu on 2017/7/24.
 */

public class Sky
{
    MyGLSurfaceView myGLSurfaceView;
    GameView gameView;
    TextureRect3D textureRect3D;
    float UNIT_SIZE=60f;//边长一半
    float underX=7.5f;//x中心
    float underY=10f;//y中心
    float underZ=-5;//z中心
    public Sky(MyGLSurfaceView myGLSurfaceView,GameView gameView)
    {
        this.myGLSurfaceView=myGLSurfaceView;
        this.gameView=gameView;
        textureRect3D=new TextureRect3D(myGLSurfaceView);
    }

    public void drawSelf()
    {
        MatrixState3D.pushMatrix();

        underX=gameView.ZDRX;
        underZ=gameView.ZDRZ;
        //绘制天空盒后面
        MatrixState3D.pushMatrix();
        MatrixState3D.translate(underX, underY, underZ-UNIT_SIZE);
        MatrixState3D.rotate(90,1,0,0);
        MatrixState3D.scale(UNIT_SIZE,1,UNIT_SIZE);
        if ((int)((-gameView.ZDRZ)/OneMapLength%3)==0)
        {
            transformScene1=true;
            transformScene2Time=0;
            transformScene3Time=0;
            transformScene2=false;
            transformScene3=false;
            textureRect3D.drawSelf(skyId);
        }
        else if((int)((-gameView.ZDRZ)/OneMapLength%3)==1)
        {
            transformScene2=true;
            transformScene1Time=0;
            transformScene3Time=0;
            transformScene1=false;
            transformScene3=false;
            textureRect3D.drawSelf(sky2Id);
        }
        else if ((int)((-gameView.ZDRZ)/OneMapLength%3)==2)
        {
            transformScene3=true;
            transformScene2Time=0;
            transformScene1Time=0;
            transformScene2=false;
            transformScene1=false;
            textureRect3D.drawSelf(sky3Id);
        }

        MatrixState3D.popMatrix();
        //绘制天空盒前面
        MatrixState3D.pushMatrix();
        MatrixState3D.translate(underX, underY, underZ+UNIT_SIZE);
        MatrixState3D.rotate(-90,1,0,0);
        MatrixState3D.scale(UNIT_SIZE,1,UNIT_SIZE);
        if ((int)((-gameView.ZDRZ)/OneMapLength%3)==0)
        {
            textureRect3D.drawSelf(skyId);
        }
        else if((int)((-gameView.ZDRZ)/OneMapLength%3)==1)
        {
            textureRect3D.drawSelf(sky2Id);
        }
        else if ((int)((-gameView.ZDRZ)/OneMapLength%3)==2)
        {
            textureRect3D.drawSelf(sky3Id);
        }
        MatrixState3D.popMatrix();
        //绘制天空盒左面
        MatrixState3D.pushMatrix();
        MatrixState3D.translate(underX-UNIT_SIZE,underY,underZ);
        MatrixState3D.rotate(-90, 0, 0, 1);
        MatrixState3D.scale(UNIT_SIZE,1,UNIT_SIZE);
        if ((int)((-gameView.ZDRZ)/OneMapLength%3)==0)
        {
            textureRect3D.drawSelf(skyId);
        }
        else if((int)((-gameView.ZDRZ)/OneMapLength%3)==1)
        {
            textureRect3D.drawSelf(sky2Id);
        }
        else if ((int)((-gameView.ZDRZ)/OneMapLength%3)==2)
        {
            textureRect3D.drawSelf(sky3Id);
        }
        MatrixState3D.popMatrix();
        //绘制天空盒右面
        MatrixState3D.pushMatrix();
        MatrixState3D.translate(underX+UNIT_SIZE, underY, underZ);
        MatrixState3D.rotate(90,0,0,1);
        MatrixState3D.scale(UNIT_SIZE,1,UNIT_SIZE);
        if ((int)((-gameView.ZDRZ)/OneMapLength%3)==0)
        {
            textureRect3D.drawSelf(skyId);
        }
        else if((int)((-gameView.ZDRZ)/OneMapLength%3)==1)
        {
            textureRect3D.drawSelf(sky2Id);
        }
        else if ((int)((-gameView.ZDRZ)/OneMapLength%3)==2)
        {
            textureRect3D.drawSelf(sky3Id);
        }
        MatrixState3D.popMatrix();
        //绘制天空盒下面
        MatrixState3D.pushMatrix();
        MatrixState3D.translate(underX,underY-UNIT_SIZE,underZ);
        MatrixState3D.scale(UNIT_SIZE,1,UNIT_SIZE);
        if ((int)((-gameView.ZDRZ)/OneMapLength%3)==0)
        {
            textureRect3D.drawSelf(skyId);
        }
        else if((int)((-gameView.ZDRZ)/OneMapLength%3)==1)
        {
            textureRect3D.drawSelf(sky2Id);
        }
        else if ((int)((-gameView.ZDRZ)/OneMapLength%3)==2)
        {
            textureRect3D.drawSelf(sky3Id);
        }
        MatrixState3D.popMatrix();
        //绘制天空盒上面
        MatrixState3D.pushMatrix();
        MatrixState3D.translate(underX, underY+UNIT_SIZE, underZ);
        MatrixState3D.rotate(180,1,0,0);
        MatrixState3D.scale(UNIT_SIZE,1,UNIT_SIZE);
        textureRect3D.drawSelf(skyId);
        MatrixState3D.popMatrix();

        MatrixState3D.popMatrix();

        if (transformScene1)
        {
            if (transformScene1Time<1)
            {
                gameView.activity       .playEQ(6,0);
                transformScene1Time++;
                System.out.println("播放切换场景声音1");
            }
        }
        else  if (transformScene2)
        {
            if (transformScene2Time<1)
            {
                gameView.activity.playEQ(6,0);
                transformScene2Time++;
                System.out.println("播放切换场景声音2");
            }
        }
        else  if (transformScene3)
        {
            if (transformScene3Time<1)
            {
                gameView.activity.playEQ(6,0);
                transformScene3Time++;
                System.out.println("播放切换场景声音3");
            }
        }

    }

}
